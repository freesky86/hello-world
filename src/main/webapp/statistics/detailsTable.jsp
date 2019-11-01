<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>Statistics Details</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
  <h1>流量统计</h1>
</div>
<div class="container">
	<%@ include file="shellForm.jsp" %>
	<table>
	  <tr>
	    <th>${statsBean.title}</th>
	  </tr>
	 </table>
	<table>	  
	  <c:forEach var="obj" items="${statsBean.list}" varStatus="vs">
	  <tr>
	    <td align="right">${obj.name} &nbsp;</td>
	    <td align="left">${obj.value}</td>
	  </tr>
		<c:if test="${vs.count % 2 == 0}">
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		</c:if>
	  </c:forEach>
	</table>
	<br />
</div>
</body>
</html>