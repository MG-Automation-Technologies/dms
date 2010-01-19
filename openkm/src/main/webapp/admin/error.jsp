<%@ page isErrorPage="true" %>
<%@ page import="es.git.openkm.backend.client.OKMException" %>
<%@ page import="es.git.openkm.util.FormatUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <% if (FormatUtil.isMobile(request)) { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/mobile.css" type="text/css" />
  <% } else { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/desktop.css" type="text/css" />
  <% } %>
  <title>OpenKM Error</title>
</head>
<body>
  <table border="0" width="100%" align="center" style="padding-top: 125px">
  <tr><td align="center">
  <table>
    <tr>
      <td colspan="2" align="center" style="padding-top: 25px;">
      	<h2>Application error</h2>
      </td>
    </tr>
    <tr>
      <td><b>Code:</b></td>
      <td><%=((OKMException) exception).getCode() %></td>
    </tr>
    <tr>
      <td><b>Message:</b></td>
      <td><%=((OKMException) exception).getMessage() %></td>
    </tr>
    <tr>
      <td><b>Date:</b></td>
      <td><%= new java.util.Date() %></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><input type="button" value="Return" onclick="javascript:history.go(-1)" /></td>
    </tr>
  </table>
  </td></tr></table>
</body>
</html>