<%@page import="java.util.HashMap"%>
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
<script>
	function cancelOrder(orderNo) {
		if (confirm("確定取消訂單嗎？")) {
			window.location.href = "/example-shop/cancelOrder?orderNo="
					+ orderNo;
		}
	}
</script>
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
				<th>訂單編號</th>
				<th>訂單日期</th>
				<th>訂單金額</th>
				<th>訂單狀態</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Map<String, Object>> orderList = (List<Map<String, Object>>) request.getAttribute("orderList");
			if (orderList != null) {
				for (Map<String, Object> order : orderList) {
			%>
			<tr>
				<td><%=order.get("ORDER_NO")%></td>
				<td><%=order.get("ORDER_TIME")%></td>
				<td style="text-align: right;"><%=order.get("ORDER_TOTAL_AMT")%></td>
				<td style="text-align: right;"><%=order.get("ORDER_STATUS")%></td>
<td><button onclick="location.href='orderdetail?orderNo=<%=order.get("ORDER_NO")%>'">訂單明細</button>&nbsp;
					<button onclick="cancelOrder('<%=order.get("ORDER_NO")%>')">訂單取消</button>
			</tr>
			<%
			}
			}
			%>
		</tbody>
	</table>
</body>
</html>