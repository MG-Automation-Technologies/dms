<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.dao.AuthDAO"%>
<%@ page import="es.git.openkm.dao.bean.Role"%>
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
  <title>Role list</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		AuthDAO dao = AuthDAO.getInstance();

		out.println("<h1>Roles <span style=\"font-size: 10px;\">(<a href=\"user_list.jsp\">Users</a>)</font></h1>");
				
		try {
			out.println("<table class=\"results\" align=\"center\">");
			out.println("<tr><th>Id</th><th><a href=\"role_edit.jsp?action=c\">New role</a></th></tr>");
			Collection<Role> roles = dao.findAllRoles();
			
			int i = 0;
			for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
				Role rol = it.next();
				out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\"><td>"+rol.getId()+"</td>"+
						"<td><a href=\"role_edit.jsp?action=d&id="+rol.getId()+"\""+">Delete</a>"+
						"</td></tr>");
			}
				
			out.println("</table>");
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
