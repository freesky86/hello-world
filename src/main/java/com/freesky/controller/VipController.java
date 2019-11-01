package com.freesky.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.freesky.dto.LoginBean;

public class VipController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511128453578482772L;
	
	private String KEYSTORE = "aZmR#kOTQ1^Mm123";
	private final String SSR_FOLDER = "/net/";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		String name = request.getParameter("name");
		String password = request.getParameter("password");

		LoginBean bean = new LoginBean();
		bean.setName(name);
		bean.setPassword(password);
		request.setAttribute("bean", bean);

		String dispatcher = validate(bean);

		RequestDispatcher rd = request.getRequestDispatcher(dispatcher);
		rd.forward(request, response);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	

	private String validate(LoginBean bean) {
		String dispatcher = SSR_FOLDER;
		if (null == bean.getPassword()) {
			dispatcher += "login.jsp";
		} else	if (bean.getPassword().equals(KEYSTORE)) {
			dispatcher += "vip-000xas.html";
		} else {
			dispatcher += "login-error.jsp";
		}
		
		return dispatcher;
	}

}
