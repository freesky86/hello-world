<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Switch to New Account</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
  <h1>V2Ray账号查询</h1>
</div>
<div class="container">
   <h3>请输入SSR(纸飞机)账号的端口号和密码.</h3>
<!--    <p style="color: red">端口号和密码可打开SSR Windows客户端查看</p> -->
    <%@ include file="v2ray-accountForm.jsp" %>
</div>
</body>
</html>