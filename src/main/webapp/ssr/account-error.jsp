<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>SSR Account Error</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
  <h1>科学上网账号查询</h1>
</div>
<div class="container">
	<h2 style="color:red">${accountBean.errMsg}</h2>
	<%@ include file="accountForm.jsp" %>
</div>
</body>
</html>
