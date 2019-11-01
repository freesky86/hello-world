package com.freesky.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.freesky.dto.AccountBean;
import com.freesky.utils.JsonParser;

public class AccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511128453578482772L;
	
	private static final Logger logger = Logger.getLogger(AccountController.class);
	
	//change from "ssr" to "net"
	//change from "net" to "apple"
	private final String PHONE_NUMBER = "apple";
	private final String CODE_VALIDATE = "apple";
	
	private final String SSR_FOLDER = "/net/";
	
	private Map<String, Integer> visitMap = new HashMap<String, Integer>();
	
	private String ssrFile;
	
	private AccountBean bean = new AccountBean();

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String port = request.getParameter("port");
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");

		bean.clear();
		bean.setCode(code);
		bean.setPhone(phone);
		bean.setPort(port);
		String remoteIp = request.getHeader("X-Forwarded-For");
		if (null == remoteIp || remoteIp.trim().isEmpty()) {
			remoteIp = request.getRemoteAddr();
		}
		bean.setAddr(remoteIp);

		request.setAttribute("accountBean", bean);

		String dispatcher = validate();

		RequestDispatcher rd = request.getRequestDispatcher(dispatcher);
		rd.forward(request, response);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
    public void init() throws ServletException {
//		path = getServletContext().getRealPath("/");
		logger.info("--------read /config/config.properties--------");
		String path = getServletContext().getRealPath("/");
		String propertiesFile = path + File.separator + "config" + File.separator + "config.properties";
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(propertiesFile));
			ssrFile = pps.getProperty("SSR_CONFIG_FILE");
			
		} catch (FileNotFoundException e) {
			logger.error("Error visiter.properties file not found: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error: visiter.properties file not access: " + e.getMessage());
		}
    }
	

	private String validate() {
		String dispatcher = SSR_FOLDER;
		if (null == bean.getPort()) {
			dispatcher += "account.jsp";
		} else	if (PHONE_NUMBER.equals(bean.getPhone()) && CODE_VALIDATE.equals(bean.getCode())) {
			try {
				Integer portNumber = Integer.parseInt(bean.getPort());
				AccountBean obj = JsonParser.readJSON(ssrFile).get(portNumber);
				bean.setPassword(obj.getPassword());
				bean.setMethod(obj.getMethod());
				bean.setObfs(obj.getObfs());
				bean.setProtocol(obj.getProtocol());
				visitMap.remove(obj.getAddr());
				dispatcher += "account-detail.jsp";
			} catch(Exception e) {
				addVisitNumber(bean.getAddr());
				dispatcher += "account-error.jsp";
			}
		} else {
			addVisitNumber(bean.getAddr());
			dispatcher += "account-error.jsp";
		}
		
		if (getVisitNumber(bean.getAddr()) >= 3) {
			dispatcher = SSR_FOLDER + "error-forbidden.html";
		} else if (getVisitNumber(bean.getAddr()) > 1) {
			// 2 times
			bean.setErrMsg("你已经连续2次提交错误信息，请立即关闭浏览器！");
		} else if (getVisitNumber(bean.getAddr()) > 0) {
			// 1 time
			bean.setErrMsg("你的IP地址 [" + bean.getAddr() + "] 已被记录，请确认你有权限访问！");
		}
		
		return dispatcher;
	}
	
	
	private int getVisitNumber(String ip) {
		Integer num = this.visitMap.get(ip);
		if (null == num) {
			return 0;
		}
		return num;
	}
	
	private void addVisitNumber(String ip) {
		Integer num = getVisitNumber(ip);
		num++;
		this.visitMap.put(ip, num);
	}
}
