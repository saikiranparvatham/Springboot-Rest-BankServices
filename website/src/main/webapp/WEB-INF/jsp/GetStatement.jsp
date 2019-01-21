<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="getaccounts" method="get">
	
	<input type="submit"/>
</form>

<table>
<tr>
<th>transactionId</th>
<th>accountNumber</th>
<th>amount</th>
<th>transactionType</th>
<th>transactionDate</th>
<th>transactionDetails</th>
<th>currentBalance</th>
</tr>

<tr>
<td>${transaction.getTransactionId()}</td>
<td>${ transaction.getAccountNumber()}</td></tr>
</table>

</body>
</html>