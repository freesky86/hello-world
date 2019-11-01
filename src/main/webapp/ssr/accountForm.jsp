<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form action="/ssr/account" method="post">

   <p>编码字符串: <input name="port" size="20"></p>
   <p>手机号码: &nbsp;&nbsp;&nbsp;&nbsp;<input name="phone" size="20"> <input type="submit" value="发送验证码"> </p>
   <p>6位数字验证码: <input name="code" size="10"></p>
   <br /> 
   <input type="submit">
</form>
