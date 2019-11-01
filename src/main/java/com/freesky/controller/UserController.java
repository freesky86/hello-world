package com.freesky.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.freesky.dto.UserBean;
import com.freesky.utils.JsonParser;

/**
 * 添加和删除用户需要修改的文件包括：
 * 1. config.json
 * 2. mudb.json
 * 3. all.sh
 * 
 * @author Freesky
 *
 */
public class UserController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7141484044055456497L;

	private static final Logger logger = Logger.getLogger(UserController.class);
	
	private final String ADD = "add";
	private final String Exceed_Limit = "exceed";
	private final String Expired = "expire";
	
	private final String USER_FOLDER = "/user/";

	private final UserBean bean = new UserBean();
	
	private String ssrFile;
	private String v2rayFile;
	private String v2rayAllFile;
	private String shellFile;
	private String resultFile;
	private String password;

	@Override
	public void init() {
		
		logger.info("--------read /config/config.properties--------");
		String path = getServletContext().getRealPath("/");
		String propertiesFile = path + File.separator + "config" + File.separator + "config.properties";
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(propertiesFile));
			ssrFile = pps.getProperty("SSR_CONFIG_FILE");
			v2rayFile = pps.getProperty("V2RAY_CONFIG_FILE");
			v2rayAllFile = pps.getProperty("V2RAY_ALL_CONFIG_FILE");
			shellFile = pps.getProperty("V2RAY_SHELL_ALL");
			resultFile = pps.getProperty("V2RAY_STATS_RESULT");
			password = pps.getProperty("password");
			
		} catch (FileNotFoundException e) {
			logger.error("Error visiter.properties file not found: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error: visiter.properties file not access: " + e.getMessage());
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String operation = request.getParameter("operation");
		String port = request.getParameter("port");
		String password = request.getParameter("password");
		String limit = request.getParameter("limit");
	
		
		bean.clear();
		bean.setLimit(limit);
		bean.setOperation(operation);
		bean.setPassword(password);
		bean.setPort(port);

		request.setAttribute("userBean", bean);
		
		String dispatcher = validate();
		RequestDispatcher rd = request.getRequestDispatcher(dispatcher);
		rd.forward(request, response);
	}

	private String validate() {
		String dispatcher = USER_FOLDER;
		if (null == bean.getPort()) {
			dispatcher += "user.jsp";
		} else if (bean.getPort().trim().isEmpty()) {
			bean.setErrMsg("Please input port number.");
			dispatcher += "user.jsp";
		} else if (!validate(bean.getPort().trim())) {
			bean.setErrMsg("Please input correct port number [6001, 6050].");
			dispatcher += "user.jsp";
		} else	if (bean.getPassword().trim().isEmpty()) {
			bean.setErrMsg("Please input password.");
			dispatcher += "user.jsp";
		} else if (!bean.getPassword().trim().equals(password)) {
			bean.setErrMsg("Please input correct password.");
			dispatcher += "user.jsp";
		} else {
			String port = bean.getPort();
			String opertation = bean.getOperation();
			String limit = bean.getLimit();
			if (opertation.equals(ADD)) {
				if (null == limit || limit.trim().isEmpty()) {
					bean.setErrMsg("You are Hacker!!!");
				} else {
					boolean isAdded = addUser();
					if (isAdded) {
						bean.setInfo("User " + port + " is successfully added!");
					} else {
						bean.setErrMsg("User " + port + " already exists!");
					}
				}
			} else if (opertation.equals(Expired)) {
				boolean isDeleted = deleteUser();
				if (isDeleted) {
					bean.setInfo("User " + port + " is successfully deleted!");
				} else {
					bean.setErrMsg("User " + port + " NOT found!");
				}
			} else if (opertation.equals(Exceed_Limit)) {
				boolean isDeleted = softDeleteUser();
				if (isDeleted) {
					bean.setInfo("User " + port + " is successfully deleted temporarily!");
				} else {
					bean.setErrMsg("User " + port + " NOT found!");
				}
			}
			dispatcher += "user.jsp";
		}
				
		return dispatcher;
	}
	
	private boolean validate(String portNumber) {
		int port;
		try {
			port = Integer.parseInt(portNumber);
		} catch (Exception e) {
			return false;
		}
		
//		if (port > 6001 && port < 6051) {
		if (port >= 1001 && port <= 9999) {
			return true;
		}
		
		return false;
	}
	
	private boolean deleteUser() {
		int port = Integer.parseInt(bean.getPort());
		// 1. delete from ssr mudb.json
		boolean isDeleted = JsonParser.deleteSsrJson(ssrFile, port);
		if (!isDeleted) {
			return isDeleted;
		}
		// 2. delete from v2ray config.json
		JsonParser.deleteV2rayJson(v2rayFile, port);

		// also delete from all path
		JsonParser.deleteV2rayJson(v2rayAllFile, port);
		// 3. delete from all.sh
		JsonParser.deleteAllShell(shellFile, port);
		
		return isDeleted;
	}
	
	private boolean softDeleteUser() {
		int port = Integer.parseInt(bean.getPort());

		// only delete from v2ray config.json
		boolean isDeleted = JsonParser.deleteV2rayJson(v2rayFile, port);
		if (!isDeleted) {
			return isDeleted;
		}
		
		return isDeleted;
	}
	
	private boolean addUser() {
		int port = Integer.parseInt(bean.getPort());
		int limit = Integer.parseInt(bean.getLimit());
		// 1. add ssr mudb.json
		boolean isAdded = JsonParser.addSsrJson(ssrFile, port);
		if (!isAdded) {
			return isAdded;
		}
		
		// 2. add v2ray config.json
		JsonParser.addV2rayJson(v2rayFile, port, limit);
		JsonParser.addV2rayJson(v2rayAllFile, port, limit);
		
		// 3. add all.sh
		JsonParser.appendShell(shellFile, port, limit);
		
		return true;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
