package com.impalapay.airtel.servlet.api.test;

import java.io.*;

import javax.servlet.*;

/**
 * <p>
 * Test/example for sending a servlet response in Json formart
 * <p>
 * Copyright (c) ImpalaPay LTD., September 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */
public class DemoServlet implements Servlet {

	ServletConfig config = null;

	@Override
	public void init(ServletConfig config) {
		// TODO Auto-generated method stub
		this.config = config;

		System.out.println("the servelet has been initialised");

	}

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws IOException {
		// TODO Auto-generated method stub
		res.setContentType("application/json");
		PrintWriter out = res.getWriter();
		out.println("{");
		out.println("\"First Name\": \"Devesh\",");
		out.println("\"Last Name\": \"Sharma\",");
		out.println("\"Reason\": \"admission\"");
		out.println("}");
		out.close();

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("servlet is destroyed");

	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return config;

	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return "copyright 2013-2020";
	}

}
