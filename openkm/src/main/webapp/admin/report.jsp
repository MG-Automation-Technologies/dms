<%@ page import="java.io.File" %>
<%@ page import="es.git.openkm.core.SessionManager" %>
<%@ page import="es.git.openkm.backend.client.config.Config"%>
<%@ page import="es.git.openkm.util.impexp.RepositoryExporter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Reports</title>
</head>
<body>
<%
	if (request.isUserInRole(es.git.openkm.core.Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		
		out.println("<h1>Reports</h1>");
		out.println("<form action=\""+Config.OKMReportServletAdmin+"\">");
		out.println("<table class=\"form\" align=\"center\">");
		out.println("<tr><td>");
		out.println("Report <select name=\"jasperFile\">");
		out.println("<option value=\""+Config.REPORT_LOCKED_DOCUMENTS+"\">Locked documents</option>");
		out.println("<option value=\""+Config.REPORT_SUBSCRIBED_DOCUMENTS+"\">Subscribed documents</option>");
		out.println("<option value=\""+Config.REPORT_REGISTERED_USERS+"\">Registered users</option>");
		out.println("</select>");
		out.println("</td></tr>");
		out.println("<tr><td align=\"right\">");
		out.println("<input type=\"submit\" value=\"Send\">");
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</form>");
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>