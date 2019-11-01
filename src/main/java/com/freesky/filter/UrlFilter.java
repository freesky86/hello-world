package com.freesky.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlFilter implements Filter {

	private static final String HTTP_HEADER = "http://";
	private static final String SSR_PREFIX = "net";

	@Override
	public void destroy() {
		System.out.println("--destroy test filter.");

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String url = request.getRequestURL().toString();
//		if (url.contains("69.171.72.236")) {
//			RequestDispatcher dispatcher = request.getRequestDispatcher("server.html");
//			dispatcher.forward(request, response);
//			
//			return;
//		}
		//104.224.144.232
		if (url.contains("104.224.144.232") || url.contains("fw123.tk")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("nginx.html");
			dispatcher.forward(request, response);
		}
		if (url.contains("v2.ifreesky.cn")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("v2.html");
			dispatcher.forward(request, response);
		}
		
		if (!url.endsWith("/")) {
			url = url + "/";
		}
		if (url.startsWith("xwc", HTTP_HEADER.length())) {
			// xwc.ifreesky.cn/
			// xwc.whitecoin.cn/
			String subUrl = url.substring(HTTP_HEADER.length());
			int pos = subUrl.indexOf("/");
			subUrl = subUrl.substring("xwc".length() + 1, pos); // ifreesky.cn

			String newUrl = HTTP_HEADER + subUrl + "/" + "xwc"; // http://ifreesky.cn/xwc
			
			response.sendRedirect(newUrl);
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/xwc");
//			dispatcher.forward(request, response);
		} else if (url.startsWith(SSR_PREFIX, "http://".length())) {
			// ssr.ifreesky.cn/
			// ssr.whitecoin.cn/
			String subUrl = url.substring(HTTP_HEADER.length());
			int pos = subUrl.indexOf("/");
			subUrl = subUrl.substring(SSR_PREFIX.length() + 1, pos); // abc.com

			String newUrl = HTTP_HEADER + subUrl + "/" + SSR_PREFIX;
			
			response.sendRedirect(newUrl);
			
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/ssr");
//			dispatcher.forward(request, response);
		} else {
			chain.doFilter(req, resp);
		}

		System.out.println(response.getContentType());
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("--init test Filter.");

	}

}
