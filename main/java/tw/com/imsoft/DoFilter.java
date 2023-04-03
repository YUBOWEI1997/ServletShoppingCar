package tw.com.imsoft;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 類別名稱：example-shop-filter
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebFilter(urlPatterns = { "/order","/orderdetail","/shoppingcar","/orderselect"})
public class DoFilter extends HttpFilter {
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		Boolean isLogin = (Boolean) session.getAttribute("isLogin");
		
		// 如果已經登入，則繼續處理請求
		if (isLogin != null && isLogin == true) {
			fc.doFilter(request, response);
		} else {
			// 如果沒有登入，則轉到登入頁面
			response.sendRedirect("/example-shop/login");
		}
	}

}