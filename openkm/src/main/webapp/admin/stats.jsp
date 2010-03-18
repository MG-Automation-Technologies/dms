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
  <title>Statistics</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		String token = (String) session.getAttribute("token");
%>
	<h1>Statistics</h1>
	<%-- <h2>Repository</h2> --%>
	<table align="center">
	  <tr>
	    <td><img src="../OKMStatsGraphServletAdmin?t=0"/></td>
	    <td><img src="../OKMStatsGraphServletAdmin?t=1"/></td>
	    <td><img src="../OKMStatsGraphServletAdmin?t=2"/></td>
	  </tr>
	</table>
	
	<%-- <h2>System</h2> --%>
	<table align="center">
	  <tr>
	    <td><img src="../OKMStatsGraphServletAdmin?t=3"/></td>
	    <td><img src="../OKMStatsGraphServletAdmin?t=4"/></td>
	  </tr>
	</table>
<%
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>