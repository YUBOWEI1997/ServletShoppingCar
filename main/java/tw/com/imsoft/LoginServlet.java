package tw.com.imsoft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 類別名稱：example-shop-Login
 * 版本資訊：1.0
 * 程式內容說明：購物車開發
 * 程式開發人員：余柏緯
 * 程式修改記錄
 * 版本 日期    程式開發人員 修改說明
 * 1.0 2023-03-29 余柏緯  初始建立
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//61.216.84.217:1534/ORCL", "demo", "123456");

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username == null || password == null) {
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
                rd.forward(request, response);
            } else {
                String sql = ("SELECT * FROM SHOP_CUSTOMER WHERE USERNAME= ? AND PASSWORD= ?");
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    session.setAttribute("isLogin", true);
                    ShopCustomerBean user = new ShopCustomerBean(username);
                    session.setAttribute("user", user);
                    response.sendRedirect("/example-shop");
                    System.out.println("登入成功");
                } else {
                    request.setAttribute("Msg", "輸入錯誤");
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
                    rd.forward(request, response);
                    System.out.println("登入失敗");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}