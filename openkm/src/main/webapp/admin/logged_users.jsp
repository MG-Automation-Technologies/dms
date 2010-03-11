<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.core.SessionManager" %>
<%@ page import="com.openkm.bean.SessionInfo" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Logged users</title>
</head>
<body>
<% 
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		out.println("<h1>Logged users</h1>");
		out.println("<table class=\"results\" width=\"80%\">");
		out.println("<tr><th>UID</th><th>Token</th><th>Creation</th><th>Last access</th><th></th></tr>");
		
		SessionManager sm = SessionManager.getInstance();
		int i = 0;
		for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
			String token = it.next();
			SessionInfo si = sm.getInfo(token);
			out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
			out.print("<td>"+si.getSession().getUserID()+"</td>");
			out.print("<td>"+token+"</td>");
			out.print("<td>"+si.getCreation().getTime()+"</td");
			out.print("<td>"+si.getAccess().getTime()+"</td>");
			out.print("<td><a href=\"logged_users_logout.jsp?token="+URLEncoder.encode(token, "UTF-8")+"\">");
			out.print("<img src=\"img/action/logout.png\" alt=\"Logout\" title=\"Logout\"/></a></td>");
			out.print("</tr>");
		}
		
		out.println("</table>");
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>