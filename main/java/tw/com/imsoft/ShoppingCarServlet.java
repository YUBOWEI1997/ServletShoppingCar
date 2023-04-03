package tw.com.imsoft;

import java.io.IOException;
import java.util.ArrayList;
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
 * 類別名稱：example-shop-shoppingCar
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebServlet("/shoppingcar")
public class ShoppingCarServlet extends HttpServlet {

	// 進入購物車
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Map<String, Object>> cartList = (List<Map<String, Object>>) session.getAttribute("cart");
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/shoppingcar.jsp");
		rd.forward(request, response);
	}
	
}
