package com.freesky.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7987101380222972584L;

	@Override
	public void init() {
		System.out.println("---------init test servlet----------");
		
	}

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("--doGet() in test servlet. " + request.getMethod());
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		System.out.println(ipAddress);
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getRemoteHost());
		
		String value = this.getServletConfig().getInitParameter("testInit");
		System.out.println("-----test servlet read init param: " + value);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
