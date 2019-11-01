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
import com.freesky.dto.V2RayAccountBean;
import com.freesky.utils.JsonParser;

public class V2RayAccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511128453578482772L;
	
	private static final Logger logger = Logger.getLogger(V2RayAccountController.class);
	
	private Map<String, Integer> visitMap = new HashMap<String, Integer>();
	
	private final String JSON_FOLDER = "/net/";
	
	private String ssrFile;
	private String v2rayFile;
	
	private V2RayAccountBean bean = new V2RayAccountBean();

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String port = request.getParameter("port");
		String password = request.getParameter("password");

		bean.clear();
		bean.setPort(port);
		bean.setPassword(password);
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
			v2rayFile = pps.getProperty("V2RAY_CONFIG_FILE");
			
		} catch (FileNotFoundException e) {
			logger.error("Error visiter.properties file not found: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error: visiter.properties file not access: " + e.getMessage());
		}
    }
	
	private String validate() {
		String dispatcher = JSON_FOLDER;
		if (null == bean.getPort() || null == bean.getPassword()) {
			dispatcher += "v2ray-account.jsp";
		} else {
			try {
				Integer portNumber = Integer.parseInt(bean.getPort());
				//read SSR configuration file mudb.json
				AccountBean accountBean = JsonParser.readJSON(ssrFile).get(portNumber);
				
				if (bean.getPassword().equals(accountBean.getPassword())) {
					V2RayAccountBean v2rayBean = JsonParser.readV2RayJSON(v2rayFile, bean.getPort()).get(bean.getPort());
					bean.setId(v2rayBean.getId());
					visitMap.remove(bean.getAddr());
					dispatcher += "v2ray-account-detail.jsp";
				} else {
					addVisitNumber();
					dispatcher += "v2ray-account-error.jsp";
				}
			} catch(Exception e) {
				//port is not a number.
				//no v2ray id exist.
				addVisitNumber();
				dispatcher += "v2ray-account-error.jsp";
			}
		} 
		
		if (getVisitNumber(bean.getAddr()) >= 3) {
			dispatcher = JSON_FOLDER + "error-forbidden.html";
		} else if (getVisitNumber(bean.getAddr()) > 1) {
			// 2 times
			bean.setErrMsg("你已经连续2次提交错误信息，请立即关闭浏览器！");
		} else if (getVisitNumber(bean.getAddr()) > 0) {
			// 1 time
			bean.setErrMsg("你的IP地址 [" + bean.getAddr() + "] 已被记录，请确认你有权限访问！");
		}
		
		return dispatcher;
	}
	
	
	public int getVisitNumber(String ip) {
		Integer num = this.visitMap.get(ip);
		if (null == num) {
			return 0;
		}
		return num;
	}
	
	/**
	 * visitNumber + 1 for this ip.
	 * 
	 */
	public void addVisitNumber() {
		Integer num = getVisitNumber(bean.getAddr());
		num++;
		this.visitMap.put(bean.getAddr(), num);
	}
}
