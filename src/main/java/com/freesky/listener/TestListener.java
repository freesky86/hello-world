package com.freesky.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent evt) {
		System.out.println("--destroy test listener.");		
	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {
		System.out.println("--init test listener.");
		String value = evt.getServletContext().getInitParameter("testInit");
		System.out.println("-------test Listener read init param: " + value);
	}

}
