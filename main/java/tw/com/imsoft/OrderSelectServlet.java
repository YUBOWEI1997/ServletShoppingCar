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
 * 類別名稱：example-shop-orderSelect
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebServlet("/order")
public class OrderSelectServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String, Object>> orderList = new ArrayList<>();
		HttpSession session = request.getSession();
		ShopCustomerBean user = (ShopCustomerBean) session.getAttribute("user");
		String username = user.getUsername();

		try (Connection conn = getConnection()) {
			String sql = "SELECT * FROM SHOPCAR WHERE ORDER_USER = ? ORDER BY ORDER_TIME DESC";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, username);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Map<String, Object> order = new HashMap<>();
					order.put("ORDER_NO", rs.getString("ORDER_NO"));
					order.put("ORDER_TIME", new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
							.format(rs.getTimestamp("ORDER_TIME")));
					order.put("ORDER_TOTAL_AMT", String.format("%,.0f", rs.getDouble("ORDER_TOTAL_AMT")));
					order.put("ORDER_STATUS", rs.getString("ORDER_STATUS"));
					orderList.add(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/WEB-INF/jsp/order.jsp").forward(request, response);
	}

	private Connection getConnection() throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@//61.216.84.217:1534/ORCL", "demo", "123456");
	}

}