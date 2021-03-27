/**
 * 
 */
package com.freesky.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freesky.dto.Statistics;
import com.freesky.utils.FileUtils;

/**
 * @author Freesky
 *
 */
public class StatisticsController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6015274845839310824L;
	
	private static final String STATS_FOLDER = "/statistics/";
	
	private final String PHONE_NUMBER = "sum";
	private final String CODE_VALIDATE = "sum";
	
	private Statistics bean = new Statistics();
	
	private Map<String, Integer> visitMap = new HashMap<String, Integer>();

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		
		bean.clear();
		bean.setCode(code);
		bean.setPhone(phone);
		String remoteIp = request.getHeader("X-Forwarded-For");
		if (null == remoteIp || remoteIp.trim().isEmpty()) {
			remoteIp = request.getRemoteAddr();
		}
		bean.setAddr(remoteIp);
		
		request.setAttribute("statsBean", bean);
		
		String dispatcher = validate();
		RequestDispatcher rd = request.getRequestDispatcher(dispatcher);
		rd.forward(request, response);
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private String validate() {
		String dispatcher = STATS_FOLDER;
		if (null == bean.getPhone()) {
			dispatcher += "stats.jsp";
		} else	if (PHONE_NUMBER.equals(bean.getPhone()) && CODE_VALIDATE.equals(bean.getCode())) {
//			readFile2Table();
//			dispatcher += "details.jsp";
			
			readFile2Bean();
			dispatcher += "detailsTable.jsp";
		} else {
			addVisitNumber();
			dispatcher += "error.jsp";
		}
		
		if (getVisitNumber() >= 3) {
			dispatcher = STATS_FOLDER + "error-forbidden.html";
		} else if (getVisitNumber() > 1) {
			// 2 times
			bean.setErrMsg("你已经连续2次提交错误信息，请立即关闭浏览器！");
		} else if (getVisitNumber() > 0) {
			// 1 time
			bean.setErrMsg("你的IP地址 [" + bean.getAddr() + "] 已被记录，请确认你有权限访问！");
		}
		
		return dispatcher;
	}
	
	private void addVisitNumber() {
		String ip = bean.getAddr();
		Integer num = getVisitNumber();
		num++;
		this.visitMap.put(ip, num);
	}
	
	private int getVisitNumber() {
		String ip = bean.getAddr();
		Integer num = this.visitMap.get(ip);
		if (null == num) {
			return 0;
		}
		return num;
	}
		
	private void readFile2Bean() {
		FileUtils.readFile2Bean(bean);
	}
}
