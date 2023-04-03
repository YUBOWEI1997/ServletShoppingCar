package tw.com.imsoft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 類別名稱：example-shop 
 * 版本資訊：1.0 程式內容說明：購物車開發 
 * 程式開發人員：余柏緯 
 * 程式修改記錄 版本 日期 程式開發人員 修改說明
 * 1.0        2023-03-29 余柏緯 初始建立
 */
@WebServlet("/")
public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@//61.216.84.217:1534/ORCL", "demo", "123456");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM SHOP_PRODUCTS");
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(true);


			// 顯示所有商品
			while (rs.next()) {
				Map<String, Object> news = new HashMap<String, Object>();
				String prodname = rs.getString("PROD_NAME");
				String proddesc = rs.getString("PROD_DESC");
				double prodamt = rs.getDouble("PROD_AMT");
				String prodprice = nf.format(prodamt);
				String imglink = rs.getString("IMG_LINK");
				String prodcode = rs.getString("PROD_CODE");
				news.put("PROD_NAME", prodname);
				news.put("PROD_DESC", proddesc);
				news.put("PROD_AMT", prodprice);
				news.put("IMG_LINK", imglink);
				news.put("PROD_CODE", prodcode);
				newsList.add(news);
			}

			// 建立購物車
			HttpSession session = request.getSession();
			List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
			if (cart == null) {
				ArrayList<Map<String, Object>> cartList = new ArrayList<>();
				session.setAttribute("cart", cartList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("newsList", newsList);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		rd.forward(request, response);
	}
}