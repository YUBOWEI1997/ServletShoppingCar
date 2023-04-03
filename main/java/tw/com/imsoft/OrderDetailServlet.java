package tw.com.imsoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 類別名稱：example-shop-orderdetail
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
	@WebServlet("/orderdetail")
	public class OrderDetailServlet extends HttpServlet {

		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String orderNo = request.getParameter("orderNo");
			HttpSession session = request.getSession();
			ShopCustomerBean user = (ShopCustomerBean) session.getAttribute("user");
			if (user == null) {
				response.sendRedirect("login.jsp");
				return;
			}

			List<Map<String, Object>> orderItemList = new ArrayList<>();
			try (Connection conn = getConnection()) {
				String sql = "SELECT ORDER_PROD_NAME, ORDER_PROD_AMT FROM SHOPCAR_DTL WHERE ORDER_NO = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, orderNo);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						Map<String, Object> orderItem = new HashMap<>();
						orderItem.put("PROD_NAME", rs.getString("ORDER_PROD_NAME"));
						orderItem.put("PROD_AMT", rs.getDouble("ORDER_PROD_AMT"));
						orderItemList.add(orderItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			request.setAttribute("orderNo", orderNo);
			request.setAttribute("orderItemList", orderItemList);
			request.getRequestDispatcher("/WEB-INF/jsp/orderdetail.jsp").forward(request, response);
		}

		private Connection getConnection() throws Exception {
			Class.forName("oracle.jdbc.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@//61.216.84.217:1534/ORCL", "demo", "123456");
		}
		
	}
