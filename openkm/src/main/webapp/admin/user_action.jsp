<%@ page import="com.openkm.core.SessionManager"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="com.openkm.util.JCRUtils"%>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.User"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="com.openkm.dao.bean.Role"%>
<%@ page import="com.openkm.core.DatabaseException"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="javax.jcr.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>User action</title>
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
			String usr_id = request.getParameter("usr_id") != null?request.getParameter("usr_id"):"";
			String usr_name = request.getParameter("usr_name") != null?request.getParameter("usr_name"):"";
			String usr_pass = request.getParameter("usr_pass") != null?request.getParameter("usr_pass"):"";
			String usr_email = request.getParameter("usr_email") != null?request.getParameter("usr_email"):"";
			boolean usr_active = request.getParameter("usr_active")!=null && request.getParameter("usr_active").equals("on");
			String[] usr_roles = request.getParameterValues("usr_roles") != null?request.getParameterValues("usr_roles"):new String[]{};
			Set<Role> usr_rolesList = new HashSet<Role>();
			
			for (int i=0; i<usr_roles.length; i++) {
				Role rol = new Role();
				rol.setId(new String(usr_roles[i].getBytes("ISO-8859-1"), "UTF-8"));
				usr_rolesList.add(rol);
			}
			
			User usr = new User();
			usr.setId(new String(usr_id.getBytes("ISO-8859-1"), "UTF-8"));
			usr.setName(new String(usr_name.getBytes("ISO-8859-1"), "UTF-8"));
			usr.setPass(new String(usr_pass.getBytes("ISO-8859-1"), "UTF-8"));
			usr.setEmail(new String(usr_email.getBytes("ISO-8859-1"), "UTF-8"));
			usr.setActive(usr_active);
			usr.setRoles(usr_rolesList);
			
			try {
				if (action.equals("c")) {
					AuthDAO.createUser(usr);
					
					for (Iterator<Role> it = usr.getRoles().iterator(); it.hasNext(); ) {
						AuthDAO.grantRole(usr.getId(), it.next().getId());
					}
				} else if (action.equals("u")) {
					AuthDAO.updateUser(usr);
					if (!usr.getPass().equals("")) AuthDAO.updateUserPassword(usr.getId(), usr.getPass());
					//AuthDAO.deleteUserRoles(usr);
					
					for (Iterator<Role> it = usr.getRoles().iterator(); it.hasNext(); ) {
						AuthDAO.grantRole(usr.getId(), it.next().getId());
					}
				} else if (action.equals("d")) {
					AuthDAO.deleteUser(usr.getId());
				}
				
				// Activity log
				UserActivity.log(jcrSession, "USER_ACTION", usr.toString(), action);
				
				response.sendRedirect("user_list.jsp");
			} catch (DatabaseException e) {
				out.println("<div class=\"error\">"+e.getMessage()+"</div>");
			}
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
