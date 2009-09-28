<%@ page import="es.git.openkm.core.SessionManager"%>
<%@ page import="es.git.openkm.util.UserActivity"%>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.dao.AuthDAO"%>
<%@ page import="es.git.openkm.dao.bean.User"%>
<%@ page import="java.sql.SQLException" %>
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
		Session jcrSession = SessionManager.getInstance().get(token);
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String usr_id = request.getParameter("usr_id") != null?request.getParameter("usr_id"):"";
		String usr_name = request.getParameter("usr_name") != null?request.getParameter("usr_name"):"";
		String usr_pass = request.getParameter("usr_pass") != null?request.getParameter("usr_pass"):"";
		String usr_email = request.getParameter("usr_email") != null?request.getParameter("usr_email"):"";
		boolean usr_active = request.getParameter("usr_active")!=null && request.getParameter("usr_active").equals("on");
		List<String> usr_roles = request.getParameterValues("usr_roles")==null?new ArrayList<String>():Arrays.asList(request.getParameterValues("usr_roles"));
		
		AuthDAO dao = AuthDAO.getInstance();
		User usr = new User();
		usr.setId(new String(usr_id.getBytes("ISO-8859-1"), "UTF-8"));
		usr.setName(new String(usr_name.getBytes("ISO-8859-1"), "UTF-8"));
		usr.setPass(new String(usr_pass.getBytes("ISO-8859-1"), "UTF-8"));
		usr.setEmail(new String(usr_email.getBytes("ISO-8859-1"), "UTF-8"));
		usr.setActive(usr_active);
		usr.setRoles(usr_roles);

		try {
			if (action.equals("c")) {
				dao.createUser(usr);
				
				for (Iterator<String> it = usr.getRoles().iterator(); it.hasNext(); ) {
					dao.grantRole(usr.getId(), it.next());
				}
			} else if (action.equals("u")) {
				dao.updateUser(usr);
				if (!usr.getPass().equals("")) dao.updatePassword(usr);
				dao.deleteUserRoles(usr);
				
				for (Iterator<String> it = usr.getRoles().iterator(); it.hasNext(); ) {
					dao.grantRole(usr.getId(), it.next());
				}
			} else if (action.equals("d")) {
				dao.deleteUser(usr);
			}
			
			// Activity log
			UserActivity.log(jcrSession, "USER_ACTION", usr.toString(), action);
			
			response.sendRedirect("user_list.jsp");
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
