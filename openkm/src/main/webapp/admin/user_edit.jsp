<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.dao.AuthDAO"%>
<%@ page import="es.git.openkm.dao.bean.Role"%>
<%@ page import="es.git.openkm.dao.bean.User"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <title>User edit</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		AuthDAO dao = AuthDAO.getInstance();
		
		try {
			User usr = new User();
			
			if (action.equals("u") || action.equals("d")) {
				usr = dao.findUserByPk(id);
			}
			
			if (action.equals("c")) {
				out.println("<h1>Create user</h1>");
			} else if (action.equals("u")) {
				out.println("<h1>Update user</h1>");
			} else if (action.equals("d")) {
				out.println("<h1>Delete user</h1>");
			}
			
			out.println("<form action=\"user_action.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\""+action+"\">");
			out.println("<table class=\"form\" width=\"320px\" align=\"center\">");
			out.println("<tr><td>Id</td><td><input class=\":required\" name=\"usr_id\" value=\""+usr.getId()+"\" "+(action.equals("c")?"":"readonly")+"></td></tr>");
			out.println("<tr><td>Password</td><td><input type=\"password\" name=\"usr_pass\" value=\"\"></td></tr>");
			out.println("<tr><td>Name</td><td><input class=\":required\" name=\"usr_name\" value=\""+usr.getName()+"\"></td></tr>");
			out.println("<tr><td>Mail</td><td><input class=\":email :required :only_on_blur\" name=\"usr_email\" value=\""+usr.getEmail()+"\"></td></tr>");
			out.println("<tr><td>Active</td><td><input name=\"usr_active\" type=\"checkbox\" "+(usr.isActive()?"checked":"")+"></td></tr>");
			out.println("<tr><td>Roles</td><td><select multiple name=\"usr_roles\">");
			
			Collection<Role> roles = dao.findAllRoles();
			for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
				Role rol = it.next();
				out.println("<option value=\""+rol.getId()+"\" "+(usr.getRoles().contains(rol.getId())?"selected":"")+">"+rol.getId()+"</option>");
			}
			
			out.println("</select></td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"right\">");
			out.println("<input type=\"button\" onclick=\"javascript:window.history.back()\" value=\"Cancel\">");
			out.println("<input type=\"submit\" value=\"Send\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
