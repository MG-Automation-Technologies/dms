<%@ page import="java.util.Calendar" %>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.core.SessionManager" %>
<%@ page import="es.git.openkm.core.UserAlreadyLoggerException" %>
<%@ page import="es.git.openkm.bean.SessionInfo" %>
<%@ page import="es.git.openkm.util.FormatUtil"%>
<%@ page isErrorPage="true" %>
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
  <title>Login error</title>
</head>
<body>
  <table border="0" width="100%" align="center" style="padding-top: 125px">
  <tr><td align="center">
  <form name="login" method="post" action="j_security_check">
    <table>
      <tr>
      	<td colspan="2" align="center"><img src="<%=request.getContextPath() %>/img/logo_big.gif" border="0" /></td>
      </tr>
      <tr>
        <td colspan="2" align="center" style="padding-top: 25px;">
        <% if (exception != null) { %>
        	<h2><%=exception.getMessage()%></h2>
        	<% if (exception instanceof UserAlreadyLoggerException) { %>
        	  <% SessionManager sm = SessionManager.getInstance(); %>
        	  <% String token = sm.getTokenByUserId(request.getRemoteUser()); %>
        	  <% if (token != null) { %>
        	    <% SessionInfo si = sm.getInfo(token);  %>
        	    <%="<i>Session inactive from</i> "+FormatUtil.formatDate(si.getAccess())+"<br/>" %>
        	    <% Calendar expiration = (Calendar) si.getAccess().clone(); %>
        	    <% expiration.add(Calendar.SECOND, Config.SESSION_EXPIRATION); %>
        	    <%="<i>Session will expire at</i> "+FormatUtil.formatDate(expiration)+"<br/>" %>
        	  <% } %>
        	<% } %>
        <% } else { %>
        	<h2>Unknown error</h2>
        <% } %>
        <% session.invalidate(); %>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center"><a href="<%=request.getContextPath()%>">Go to login page</a></td>
      </tr>
    </table>
  </form>
  </td></tr></table>
</body>
</html>