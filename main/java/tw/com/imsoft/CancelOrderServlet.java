package tw.com.imsoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 類別名稱：example-shop-cancelOrder
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebServlet("/cancelOrder")
public class CancelOrderServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderNo = request.getParameter("orderNo");
		 
		try (Connection conn = Database.getConnection()) {
			String updateStatusSql = "UPDATE SHOPCAR SET ORDER_STATUS = '不成立' WHERE ORDER_NO = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(updateStatusSql)) {
				pstmt.setString(1, orderNo);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/order");
	}
		 
}

