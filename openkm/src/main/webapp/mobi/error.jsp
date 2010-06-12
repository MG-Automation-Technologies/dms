<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.openkm.core.Config"%>
<%@page import="com.openkm.core.SessionManager"%>
<%@page import="com.openkm.core.UserAlreadyLoggerException"%>
<%@page import="com.openkm.bean.SessionInfo"%>
<%
  String msg = "Unknown error";
  String logged = null;
  
  if (exception != null) {
    msg = exception.getMessage();
  }
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/mobile.css" type="text/css" />
  <title>OpenKM Error</title>
</head>
<body>
  <div id="box-error">
    <div id="logo"></div>
    <div id="text-error">
      <center><img src="<%=request.getContextPath()%>/img/error.png"/></center>
    </div>
    <div id="form-error">
      <div id="error"><%=msg%></div>
      <% if (logged != null) { %>
      <p><%=logged%></p>
      <center><input type="button" value="Go to login" onclick="document.location='<%=request.getContextPath()%>/'"/></center>
      <% } else { %>
      <center><input type="button" value="Go back" onclick="history.go(-1)"/></center>
      <% } %>
    </div>
  </div>
</body>
</html>