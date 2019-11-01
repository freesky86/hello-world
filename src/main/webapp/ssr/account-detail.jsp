<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>SSR Account Deatil</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
  <h1>科学上网账号配置信息</h1>
</div>
<div class="container">
	<p>服务器 69.171.71.44</p>
	<p>端口 ${accountBean.port}</p>
	<p>密码 ${accountBean.password}</p>
</div>
</body>
</html>

