package com.freesky.servlet;

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

public class SsrServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2744683101696856074L;
	
	private static final Logger logger = Logger.getLogger(SsrServlet.class);
	
	private Long visitNumber = 26156L;
	
	@Override
	public void init() {
		logger.info("--------read /net/vister.properties to get visit number--------");
		String path = getServletContext().getRealPath("/");
		String propertiesFile = path + File.separator + "net" + File.separator + "visiter.properties"; 
		Properties pps = new Properties();
		try {
			pps.load(new FileInputStream(propertiesFile));
			String visitNum = pps.getProperty("NET_VISIT_NUM");
			visitNumber = Long.parseLong(visitNum);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("--SSR visitNumber: " + visitNumber);
		visitNumber++;
		if (visitNumber >= Long.MAX_VALUE - 1) {
			visitNumber = 1L;
		}
		
		request.setAttribute("visitNumber", visitNumber);
		RequestDispatcher dispatcher = request.getRequestDispatcher("net/ssr.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
