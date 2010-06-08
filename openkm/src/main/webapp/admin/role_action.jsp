<%@ page import="com.openkm.core.SessionManager"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.Role"%>
<%@ page import="com.openkm.core.DatabaseException"%>
<%@ page import="javax.jcr.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.openkm.util.JCRUtils"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Role action</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		String token = (String) session.getAttribute("token");
		Session jcrSession = null;

		try {
			if (Config.SESSION_MANAGER) {
				jcrSession = SessionManager.getInstance().get(token);
			} else {
				jcrSession = JCRUtils.getSession();
			}
			
			request.setCharacterEncoding("UTF-8");
			String action = request.getParameter("action");
			String rol_id = request.getParameter("rol_id") != null?request.getParameter("rol_id"):"";
			
			Role rol = new Role();
			rol.setId(new String(rol_id.getBytes("ISO-8859-1"), "UTF-8"));
			
			if (action.equals("c")) {
				AuthDAO.createRole(rol);
			} else if (action.equals("d")) {
				AuthDAO.deleteRole(rol.getId());
			}
			
			// Activity log
			UserActivity.log(jcrSession.getUserID(), "ROLE_ACTION", rol.toString(), action);
			
			response.sendRedirect("role_list.jsp");
		} catch (DatabaseException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		} finally {
			if (!Config.SESSION_MANAGER) {
				JCRUtils.logout(jcrSession);
			}
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
