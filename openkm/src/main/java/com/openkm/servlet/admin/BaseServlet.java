package com.openkm.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openkm.core.Config;
import com.openkm.core.HttpSessionManager;

public class BaseServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	/**
	 * Dispatch errors 
	 */
	protected void sendErrorRedirect(HttpServletRequest request, HttpServletResponse response,
			Throwable e) throws ServletException, IOException {
		request.setAttribute ("javax.servlet.jsp.jspException", e);
		ServletContext sc = getServletConfig().getServletContext();
		sc.getRequestDispatcher("/error.jsp").forward(request, response);
	}
	
	public void updateSessionManager(HttpServletRequest request) {
		HttpSessionManager.getInstance().update(request.getSession().getId());
	}
	
	/**
	 * Test if an user can access to administration
	 */
	public static boolean isAdmin(HttpServletRequest request) {
		return request.isUserInRole(Config.DEFAULT_ADMIN_ROLE);
	}
	
	/**
	 * Test if an user can access to administration when configured as SaaS
	 */
	public static boolean isMultipleInstancesAdmin(HttpServletRequest request) {
		return Config.MULTIPLE_INSTANCES && request.getRemoteUser().equals(Config.ADMIN_USER) ||
			!Config.MULTIPLE_INSTANCES && request.isUserInRole(Config.DEFAULT_ADMIN_ROLE);
	}
}
