<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.Role"%>
<%@ page import="com.openkm.core.DatabaseException"%>
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
  <title>Role edit</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String action = request.getParameter("action");
		
		try {
			Role rol = new Role();
			
			if ((action.equals("u") || action.equals("d")) && id != null) {
				id = new String(id.getBytes("ISO-8859-1"), "UTF-8");
				rol = AuthDAO.findRoleByPk(id);
			}
			
			if (action.equals("c")) {
				out.println("<h1>Create role</h1>");
			} else if (action.equals("u")) {
				out.println("<h1>Update role</h1>");
			} else if (action.equals("d")) {
				out.println("<h1>Delete role</h1>");
			}
			
			out.println("<form action=\"role_action.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\""+action+"\">");
			out.println("<table class=\"form\" width=\"280px\" align=\"center\">");
			out.println("<tr><td>Id</td><td width=\"100%\"><input class=\":required :only_on_blur\" name=\"rol_id\" value=\""+rol.getId()+"\" "+(action.equals("c")?"":"readonly")+"></td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"right\">");
			out.println("<input type=\"button\" onclick=\"javascript:window.history.back()\" value=\"Cancel\">");
			out.println("<input type=\"submit\" value=\"Send\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
		} catch (DatabaseException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
