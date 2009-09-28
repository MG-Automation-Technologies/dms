<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="es.git.openkm.util.FormatUtil"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  <% if (FormatUtil.isMobile(request)) { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/mobile.css" type="text/css" />
  <% } else { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/desktop.css" type="text/css" />
  <% } %>
  <title>OpenKM Login</title>
</head>
<body onload="document.forms[0].elements[0].focus()">
  <div id="box">
    <div id="logo"></div>
    <div id="error"><%=request.getParameter("error")!=null?"Authentication error":"" %></div>
    <div id="text">
      <center><img src="<%=request.getContextPath() %>/img/lock.png"/></center>
      <p>Welcome to OpenKM !</p>
      <p>Use a valid user and password to access to OpenKM user Desktop.</p>
    </div>
    <div id="form">
      <form name="login" method="post" action="j_security_check">
        <label for="j_username">User</label><br/>
        <input name="j_username" id="j_username" type="text"/><br/><br/>
        <label for="j_password">Password</label><br/>
        <input name="j_password" id="j_password" type="password"/><br/><br/>
        <input value="Login" name="submit" type="submit"/><br/>
      </form>
    </div>
  </div>
  
  <% if (Config.SYSTEM_DEMO.equalsIgnoreCase("on")) { %>				
		<jsp:include flush="true" page="login_demo_users.jsp"/>
  <% } %>
  
</body>
</html>