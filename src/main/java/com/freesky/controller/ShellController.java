package com.freesky.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ShellController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7305471438606607261L;

	private static final Logger logger = Logger.getLogger(ShellController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String code = request.getParameter("code");
		// String[] shellCommand = new String[] {"sh", "-c",
		// "/etc/v2ray/shell/tool.sh"};
		String statsShellCommand = "/etc/v2ray/shell/tool.sh";
		String stopCommand = "service v2ray stop";
		String startCommand = "service v2ray start";
		String restoreCommand = "cp /etc/v2ray/all/config.json /etc/v2ray";

		String restartCommand = "service v2ray restart";
		if ("stats".equals(code)) {
			exec(statsShellCommand);
		} else if ("restart".equals(code)) {
			exec(statsShellCommand);

			exec(restartCommand);
		} else if ("stop-start".equals(code)) {
			exec(statsShellCommand);

			exec(stopCommand);
			exec(startCommand);
		} else if ("stop".equals(code)) {
			exec(statsShellCommand);

			exec(stopCommand);
		} else if ("start".equals(code)) {
			exec(startCommand);
		} else if ("restore".equals(code)) {
			exec(restoreCommand);
		}

		String dispatcher = "/stats";
		RequestDispatcher rd = request.getRequestDispatcher(dispatcher);
		rd.forward(request, response);
	}

	private void exec(String command) {
		Runtime runTime = Runtime.getRuntime();
		if (runTime == null) {
			logger.error("Fail to Create runtime!");
			System.err.println("Fail to Create runtime!");
		}
		try {
			Process process = runTime.exec(command);

			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.info(line);
				System.out.println(line);

			}

			is.close();

			isr.close();

			br.close();

			logger.info("----------execute command------------");
			logger.info(command);
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			System.err.println(ex.getMessage());
		}
	}

//	private void exec(String[] command) {
//		Runtime runTime = Runtime.getRuntime();
//		if (runTime == null) {
//			logger.error("Fail to Create runtime!");
//			System.err.println("Fail to Create runtime!");
//		}
//		try {
//			runTime.exec(command);
//			logger.info("----------execute command------------");
//			logger.info(command);
//		} catch (IOException ex) {
//			logger.error(ex.getMessage());
//			System.err.println(ex.getMessage());
//		}
//	}
	
}
