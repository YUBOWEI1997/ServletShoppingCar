<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
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

table, th, td {
	border: solid 1px #000;
	padding: 10px;
}

table {
	border-collapse: collapse;
	caption-side: bottom;
	width: 100%;
}

caption {
	font-size: 16px;
	font-weight: bold;
	padding-top: 5px;
}
</style>
</head>
<body>

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
	<hr />
	<table>
		<thead>
			<tr>
				<th>序號</th>
				<th>購買商品</th>
				<th>單價</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Map<String, Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
			if (cart == null) {
			    cart = new ArrayList<>();
			}
			double totalAmount = 0;
			for (int i = 0; i < cart.size(); i++) {
				Map<String, Object> news = cart.get(i);
				Object obj = news.get("PROD_AMT");
				double price = obj != null ? Double.parseDouble(obj.toString().replace(",", "")) : 0.0;
				totalAmount += price;
			}
			%>
		<% if (request.getAttribute("errorMessage") != null) { %>
    <script>
        alert('<%= request.getAttribute("errorMessage") %>');
    </script>
<% } %>
			<%
			int index = 1;
			for (int i = 0; i < cart.size(); i++) {
				Map<String, Object> item = cart.get(i);
				if (item != null && item.get("PROD_NAME") != null && item.get("PROD_AMT") != null) {
			%>
			<tr>
				<td><%=index++%></td>
<td><a href='index?prodname=<%=item.get("PROD_NAME")%>'><%=item.get("PROD_NAME")%></a></td>				<td style="text-align: right;"><%=String.format("%,.0f", Double.parseDouble(item.get("PROD_AMT").toString().replace(",", "")))%></td>
				<td><a href="removeItem?prodName=<%=item.get("PROD_NAME")%>">移除</a></td>
			</tr>
			<%
			}
			}
			%>
			<tr>
				<td></td>
				<td></td>
				<td style="text-align: right;">合計金額：<span style="color: red;"><%=String.format("%,.0f", totalAmount)%></span></td>
				<form action='/example-shop/InsertOrder' method="get">
					<td><button type="submit" formaction="InsertOrder">確認訂單</button></td>
				</form>
			</tr>
		</tbody>

	</table>

</body>
</html>
