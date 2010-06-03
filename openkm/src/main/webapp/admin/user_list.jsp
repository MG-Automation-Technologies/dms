<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.Role"%>
<%@ page import="com.openkm.dao.bean.User"%>
<%@ page import="com.openkm.core.DatabaseException"%>
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
  <title>User list</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String roleFilter = request.getParameter("roleFilter");

		out.println("<h1>Users <span style=\"font-size: 10px;\">(<a href=\"role_list.jsp\">Roles</a>)</font></h1>");
		out.println("<form action=\"user_list.jsp\">");
		out.println("<table class=\"form\">");
		out.println("<tr><td>Role</td><td><select name=\"roleFilter\">");
		out.println("<option value=\"\" ></option>");
		
		Collection<Role> roles = AuthDAO.findAllRoles();
		for (Iterator<Role> it = roles.iterator(); it.hasNext(); ) {
			Role rol = it.next();
			out.println("<option value=\""+rol.getId()+"\" "+(rol.getId().equals(roleFilter)?"selected":"")+">"+rol.getId()+"</option>");
		}
		
		out.println("</select></td></tr>");
		out.println("<tr><td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Seach\"></td></tr>");
		out.println("</table>");
		out.println("<br/>");
				
		try {
			out.println("<table class=\"results\" width=\"80%\">");
			out.println("<tr><th>Id</th><th>Name</th><th>Mail</th><th>Roles</th><th width=\"25px\">Active</th>");
			out.println("<th width=\"100px\"><a href=\"user_edit.jsp?action=c\"><img src=\"img/action/new.png\" alt=\"New user\" title=\"New user\"/></a></th></tr>");
			Collection<User> users = null;
			
			if (roleFilter == null || roleFilter.equals("")) {
				users = AuthDAO.findAllUsers(false);
			} else {
				users = AuthDAO.findUsersByRole(false, roleFilter);
			}
			
			int i = 0;
			for (Iterator<User> it = users.iterator(); it.hasNext(); ) {
				User usr = it.next();
				out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\"><td>"+usr.getId()+"</td><td>"+usr.getName()+"</td><td>"+usr.getEmail()+
						"</td><td>"+usr.getRoles()+"</td><td>"+
						(usr.isActive()?"<img src=\"img/true.png\" alt=\"Active\" title=\"Active\"/>":
							"<img src=\"img/false.png\" alt=\"Inactive\" title=\"Inactive\"/>")+"</td>"+
						"<td><a href=\"user_edit.jsp?action=u&id="+usr.getId()+"\""+"><img src=\"img/action/edit.png\" alt=\"Edit\" title=\"Edit\"/></a>"+
						" &nbsp; <a href=\"user_edit.jsp?action=d&id="+usr.getId()+"\""+"><img src=\"img/action/delete.png\" alt=\"Delete\" title=\"Delete\"/></a>"+
						" &nbsp; <a href=\"mail_list.jsp?user="+usr.getId()+"\"><img src=\"img/action/email.png\" alt=\"Mail accounts\" title=\"Mail accounts\"/></a>"+
						" &nbsp; <a href=\"twitter_list.jsp?user="+usr.getId()+"\"><img src=\"img/action/twitter.png\" alt=\"Twitter accounts\" title=\"Twitter accounts\"/></a>"+
						"</td></tr>");
			}
			
			out.println("</table>");
		} catch (DatabaseException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
