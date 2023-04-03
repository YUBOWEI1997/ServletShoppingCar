package tw.com.imsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 類別名稱：example-shop-buyNow
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebServlet("/buynow")
public class BuyNowServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String prodName = request.getParameter("prodName");
		double prodAmt = Double.parseDouble(
				request.getParameter("prodAmt") != null ? request.getParameter("prodAmt").replace(",", "") : "0");
		String prodCode = request.getParameter("prodCode");

		HttpSession session = request.getSession();
		List<Map<String, Object>> cartList = (List<Map<String, Object>>) session.getAttribute("cart");
		if (cartList == null) {
			cartList = new ArrayList<>();
		}

		// 檢查購物車是否有相同商品
		boolean isExist = false;
		for (Map<String, Object> item : cartList) {
			if (Objects.equals(item.get("PROD_NAME"), prodName)) {
				isExist = true;
				break;
			}
		}
		if (isExist) {
			session.setAttribute("message", "商品已經存在，無法加入至購物車");
		} else {
			Map<String, Object> item = new HashMap<>();
			item.put("PROD_NAME", prodName);
			item.put("PROD_AMT", prodAmt);
			item.put("PROD_CODE", prodCode);
			cartList.add(item);
			session.setAttribute("cart", cartList);
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

}