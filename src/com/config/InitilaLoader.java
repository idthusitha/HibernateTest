package com.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class InitilaLoader
 */

public class InitilaLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig cfg) throws ServletException {
		System.out.println("<================start InitilaLoader init===============================>");

		HBUtility hbutil = null;
		hbutil = new HBUtility();
		try {
			System.out.println("start Created Hibernate Session Factory");
			hbutil.createHbSessionFactory();
			System.out.println("end Created Hibernate Session Factory");
		} catch (Exception e) {
			System.out.println("Could not create Hibernate Session Factory. Error: " + e.toString());
			e.printStackTrace();
		}
		hbutil = null;
		System.out.println("<================end InitilaLoader init===============================>");
		
		
		System.out.println("<================start test hibernate===============================>");
		HibernateEHCacheMain.testHiberbante();
		System.out.println("<================end test hibernate===============================>");
	}
}
