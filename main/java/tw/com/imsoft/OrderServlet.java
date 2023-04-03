package tw.com.imsoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 類別名稱：example-shop-OrderServlet
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebServlet("/InsertOrder")
public class OrderServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Map<String, Object>> cartList = (List<Map<String, Object>>) session.getAttribute("cart");

		if (cartList == null) {
			cartList = new ArrayList<>();
		}
		String username = ((ShopCustomerBean) session.getAttribute("user")).getUsername();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String orderNo = "P" + sdf.format(new Date());

		// 將時間格式轉換為timestamp格式
		Timestamp orderTime = new Timestamp(new Date().getTime());

		// 計算購物車商品總額
		double totalAmount = 0;
		for (Map<String, Object> cartMap : cartList) {
			if (cartMap.get("PROD_AMT") != null) {
				double prodAmt = Double.parseDouble(cartMap.get("PROD_AMT").toString().replace(",", ""));
				totalAmount += prodAmt;
			} else {
				System.out.println("Warning: cartMap item with null PROD_AMT value");
			}
		}
		if (totalAmount == 0) {
			request.setAttribute("errorMessage", "您並未購買商品");
			request.getRequestDispatcher("WEB-INF/jsp/shoppingcar.jsp").forward(request, response);
			return;
		}

		// 新增資料到ShopCar_DTL
		String orderStatus = "成立";
		try (Connection conn = getConnection()) {
			StringBuilder insertShopCarDtl = new StringBuilder();
			insertShopCarDtl.append(" INSERT INTO SHOPCAR_DTL ");
			insertShopCarDtl.append(" (ORDER_NO , ORDER_SEQ , ORDER_PROD_CODE , ORDER_PROD_NAME , ORDER_PROD_AMT) ");
			insertShopCarDtl.append(" VALUES (?, ?, ?, ?, ?) ");
			int orderseq = 1;
			for (Map<String, Object> item : cartList) {
				if (item != null && item.get("PROD_NAME") != null && item.get("PROD_AMT") != null) {
					String orderProdName = item.get("PROD_NAME").toString();
					String orderProdCode = item.get("PROD_CODE").toString();
					double orderProdAmt = Double.parseDouble(item.get("PROD_AMT").toString().replace(",", ""));
					try (PreparedStatement pstmt = conn.prepareStatement(insertShopCarDtl.toString())) {
						pstmt.setString(1, orderNo);
						pstmt.setInt(2, orderseq++);
						pstmt.setString(3, orderProdCode);
						pstmt.setString(4, orderProdName);
						pstmt.setDouble(5, orderProdAmt);
						pstmt.executeUpdate();
					}
				} else {
					System.out.println("不符合資料庫條件");
				}
			}

			// 新增資料到ShopCar
			StringBuilder insertShopCar = new StringBuilder();
			insertShopCar.append(" INSERT INTO SHOPCAR ");
			insertShopCar.append(" (ORDER_NO , ORDER_TIME , ORDER_TOTAL_AMT , ORDER_STATUS , ORDER_USER ,IS_DEL ) ");
			insertShopCar.append(" VALUES (?, ?, ?, ?, ?, ?) ");
			try (PreparedStatement pstmt = conn.prepareStatement(insertShopCar.toString())) {
				pstmt.setString(1, orderNo);
				pstmt.setTimestamp(2, orderTime);
				pstmt.setDouble(3, totalAmount);
				pstmt.setString(4, orderStatus);
				pstmt.setString(5, username);
				pstmt.setString(6, "N");
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/order");
	}

		Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@//61.216.84.217:1534/ORCL", "demo", "123456");
	}

}
