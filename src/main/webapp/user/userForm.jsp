<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form action="/userOperation" method="post">
	<p>操作: 
		<select name="operation" id="operation">
			<option value="add">Add User</option>
			<option value="exceed">Exceed Limit, delete temporarily</option>
			<option value="expire">Expired, Delete permanently</option>
		</select>
	</p>
   	<p>端号: <input name="port" size="10" value="${userBean.port}"></p>
   	<p>密码: <input name="password" size="20" value="${userBean.password}" type="password"></p>
   	<p>流量限制: 
		<select name="limit" id="limit">
			<option value="10">10G/month</option>
			<option value="20">20G/month</option>
		</select>
	</p>
   	<br /> 
   <input type="submit">
</form>
