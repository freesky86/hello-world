<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh">
<head>
  <title>学习十九大VIP</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="../resource/css/bootstrap.min.css">
  <script src="../resource/js/jquery-3.2.1.min.js"></script>
  <script src="../resource/js/bootstrap.min.js"></script>
</head>
<body>

<div class="jumbotron text-center">
  <h1>网上学习娱乐</h1>
  <h2>免责声明</h2> 
  <p>本网站提供的方法仅供学习和娱乐之用。不得从事商业用途。</p> 
  <p>请大家自律使用，不得违反中华人民共和国法律法规和治安条例。否则，后果自负。</p> 
</div>
<div class="container">
	<p>Please input the right keystore.</p>
	<%@ include file="form.jsp" %>
</div>
<jsp:include page="../_footer.jsp"></jsp:include>
</body>
</html>