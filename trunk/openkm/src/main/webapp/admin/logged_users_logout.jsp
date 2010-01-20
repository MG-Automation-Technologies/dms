<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.module.direct.DirectAuthModule" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = request.getParameter("token");
		
		if (token != null) {
			token = new String(token.getBytes("ISO-8859-1"));
			
			try {
				new DirectAuthModule().logout(token);
				response.sendRedirect("logged_users.jsp");
			} catch (Exception e) {
				e.printStackTrace(response.getWriter());
			}
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
