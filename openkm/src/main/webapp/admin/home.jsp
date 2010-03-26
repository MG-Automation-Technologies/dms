<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="es.git.openkm.util.WarUtils"%>
<%@ page import="es.git.openkm.api.OKMRepository"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Main</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		String token = (String) session.getAttribute("token");
%>
	<h1>OpenKM Administration</h1>
	<table width="200px" class="form" style="margin-top: 25px">
		<tr><td><b>OpenKM Enterprise Edition</b></td></tr>
		<tr><td>Version: <%=WarUtils.getAppVersion() %></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&copy; 2006-2010  OpenKM</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td><b>Support</b></td></tr>
		<tr><td><a href="mailto:support@openkm.com">support@openkm.com</a></td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td><b>Installation ID</b></td></tr>
		<tr><td><%=OKMRepository.getInstance().getUuid(token) %></td></tr>
	</table>
<%
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>