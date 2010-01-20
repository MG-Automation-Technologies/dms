<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.api.OKMRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <style type="text/css">
    html, body, div, iframe { margin: 0; padding: 0; width: 100%; }
    iframe { width: 100%; border: none; visibility: hidden; }
    div#menu { border-bottom: 1px solid #A5A596; background-color: #E5E5E1; padding-top: 5px; }
  </style>
  <title>OpenKM Administration</title>
  <script type="text/javascript">
    window.addEventListener("load", function() { setTimeout(loaded, 100) }, true);
    function loaded() {
      var frame = document.getElementById('frame');
      var menu = document.getElementById('menu');
      var height = Math.max(
              Math.max(document.body.scrollHeight, document.documentElement.scrollHeight),
              Math.max(document.body.offsetHeight, document.documentElement.offsetHeight),
              Math.max(document.body.clientHeight, document.documentElement.clientHeight)
          );
      frame.style.height=(height-menu.offsetHeight)+'px';
      frame.style.visibility='visible';
    }
  </script>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
%>
	<div id="menu"><%@include file="menu.jsp" %></div>
	<div><iframe id="frame" name="frame" src="home.jsp"></iframe></div>
<%
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>