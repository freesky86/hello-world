<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>User Operation</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
  <h1>用户账号管理</h1>
</div>
<div class="container">
	<h3>请输入要添加用户的端口号和管理员密码</h3>
	<h3 style="color:red">${userBean.errMsg}</h3>
	<h3 style="color:green">${userBean.info}</h3>
	<%@ include file="userForm.jsp" %>
</div>
</body>
</html>