<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form action="/shell" method="post">
	<p>statistics -- /etc/v2ray/shell/tool.sh</p>
	<p>restart -- statistics + restart v2ray </p>
	<p>stop-start -- statistics + stop v2ray and then start </p>
	<p>stop -- statistics + stop v2ray </p>
	<p>start -- start v2ray </p>
	<p>restore -- copy /all/config.json </p>
   <p>Action: 
	<select name="code" id="code">
		<option value="stats">statistics</option>
		<option value="restart">restart</option>
		<option value="stop-start">stop-start</option>
		<option value="stop">stop</option>
		<option value="start">Start</option>
		<option value="restore">restore config.json from /all path</option>
	</select>  
	<input type="submit" value="Update Statistics">
   </p>
   
</form>
