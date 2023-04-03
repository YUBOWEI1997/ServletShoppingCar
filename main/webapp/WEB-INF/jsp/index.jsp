<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
* {
	transition: all 0.3s ease-in-out;
}

.container {
	clear: both;
	overflow: auto;
}

nav {
	float: right;
}

.logo img {
	float: left;
}

ul li {
	display: inline-block;
	padding: 10px;
	font-size: 20px;
	font-family: raleway;
}

ul li:hover {
	color: red;
}
</style>
</head>
<body>
	<!-- 	<form action='/example-shop/index' method="get"> -->
	<div class="container">
		<div class="logo">
			<img
				src="https://atimetoshop.com/wp-content/uploads/2019/06/A-time-to-shop-Logo.png"
				alt="" width="200" />
		</div>
		<nav>
			<ul>
				<li onclick="location.href='/example-shop/'">首頁</li>
				<li onclick="location.href='order'">訂單查詢</li>
				<li onclick="location.href='shoppingcar'">購物車</li>
			</ul>
		</nav>
	</div>
	<main>
	<%
	String filterProdName = request.getParameter("prodname");
	List<Map<String, Object>> newsList = (List<Map<String, Object>>) request.getAttribute("newsList");
	for (int i = 0; i < newsList.size(); i++) {
		Map<String, Object> news = newsList.get(i);
		String currentProdName = (String) news.get("PROD_NAME");

		if (filterProdName == null || filterProdName.equalsIgnoreCase(currentProdName)) {
		%>
		<main>
			<% if (session.getAttribute("message") != null) { %>
    <script>
        alert('<%= session.getAttribute("message") %>');
    </script>
    <% session.removeAttribute("message"); %>
<% } %>
			<!-- Your existing code here -->
		</main>
		<div style="width: 1000px; margin: 0 auto;">
			<%
			if (request.getAttribute("Msg") != null) {
			%>
			<p style="color: red;"><%=request.getAttribute("Msg")%></p>
			<%
			}
			%>
			<table>
				<tr>

					<td rowspan="3" style="vertical-align: top; padding-top: 10px;">
						<img src="<%=news.get("IMG_LINK")%>" width="200"
						style="padding-right: 20px;">
					</td>
					<td style="vertical-align: middle; height: 40px;"><span
						style="color: blue"><%=news.get("PROD_NAME")%> <input
							type="hidden" name="prodname" value="<%=news.get("PROD_NAME")%>">
					</span></td>
				</tr>
				<tr>
					<td style="vertical-align: top; height: 120px; width: 800px;">
						<%=news.get("PROD_DESC")%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle; text-align: right;">
						<hr> <b>價格：<span style="color: red"><%=news.get("PROD_AMT")%>
								<input type="hidden" name="prodamt"
								value="<%=news.get("PROD_AMT")%>"> </span></b> <a
						href="buynow?prodName=<%=news.get("PROD_NAME")%>&prodAmt=<%=news.get("PROD_AMT")%>&prodCode=<%=news.get("PROD_CODE")%>">
							<img style="vertical-align: middle;"
							src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4A1jV-g_TxYq3gIIuT76Tatt-iawfwsFmioSCYM_6wm--wRfk0eiOMQUMSdIpNBOq8vw&usqp=CAU"
							width="120">

					</a>
					</td>
				</tr>
				<%
				}
		}
				%>
			</table>
		</div>
	</main>
</body>
</html>
