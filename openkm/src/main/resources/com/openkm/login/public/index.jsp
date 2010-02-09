<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.util.FormatUtil"%>
<%@ page import="com.openkm.core.Config"%>
<%@ page import="com.openkm.bean.SessionInfo" %>
<%@ page import="com.openkm.core.SessionManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%
	String basePath = request.getRequestURL().toString().substring(0,request.getRequestURL().toString().lastIndexOf("/")+1);
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath %>"/>
  		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-cache" />
    	<meta http-equiv="Pragma" content="no-cache" />
    	<meta http-equiv="Expires" content="0" />   
		<link rel="Shortcut icon" href="../favicon.ico" />
		<% if (FormatUtil.isMobile(request)) { %>
  			<link rel="stylesheet" href="mobile.css" type="text/css" />
  		<% } else { %>
  			<link rel="stylesheet" href="desktop.css" type="text/css" />
  		<% } %>
		<title>OpenKM Login</title>
		<script language="JavaScript">
			var demo = "<%=Config.SYSTEM_DEMO %>";
			var lowercase = "<%=Config.SYSTEM_LOGIN_LOWERCASE %>";
			var docPath = "<%=request.getParameter("docPath")!=null?request.getParameter("docPath"):"" %>";
			var fldPath = "<%=request.getParameter("fldPath")!=null?request.getParameter("fldPath"):"" %>";
			var isMobil = "<%=FormatUtil.isMobile(request)?"on":"off" %>"
		</script>
	</head>
	<body>
		<div id="users">
		    <% if (Config.SYSTEM_DEMO.equals("on")) { %>
			<table border="0" cellpadding="2" cellspacing="0" align="center" class="demo">
			<%
			  List<String> users = new ArrayList<String>();
			  boolean userAvailable = false;
			  out.println("<tr><td class=\"demo_alert\">");
			  out.println("This is a development demo where you can test exciting new features<br>");
			  out.println("</td></tr>");
			  out.println("<tr><td class=\"demo_title\">- LOGGED USERS -</td></tr>");
			  out.println("<tr><td>");
			
			  SessionManager sm = SessionManager.getInstance();
			
			  if (sm.getTokens().isEmpty()) {
			    out.print("No users logged, all users are available");
			  } else {
			    out.println("<table class=\"demo_list\" align=\"center\">");
			    out.println("<tr><th>User</th><th>Creation</th><th>Last access</th></tr>");
			    for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
			      String token = it.next();
			      SessionInfo si = sm.getInfo(token);
			      if (!users.equals(Config.ADMIN_USER)) {
			        out.print("<tr><td>"+si.getSession().getUserID()+"</td><td>"+si.getCreation().getTime()+"</td><td>"+si.getAccess().getTime()+"</td</tr>");
			      }
			      users.add(si.getSession().getUserID());
			    }
			    out.println("</table>");
			  }
			
			  out.println("</td></tr>");
			  out.println("<tr><td class=\"demo_title\">- AVAILABLE USERS -</td></tr>");
			  out.println("<tr><td>");
			
			  String availables = "<table class=\"demo_list\" align=\"center\">";
			  availables += "<tr><th>User</th><th>Password</th></tr>";
			  for (int i=0; i<10; i++) {
			    String userID = "user" + i;
			    if (!users.contains(userID) && !users.equals("system")){
			      availables += "<tr><td>" + userID + "</td><td>pass" + i +"</td></tr>";
			      userAvailable = true;
			    }
			  }
			  availables += "</table>";
			
			  if (!userAvailable) {
			    out.println("At this moment all users are logged, please be patient");
			  } else {
			    out.println(availables);
			  }
			
			  out.println("</td></tr>");
			  %>
			  <% } %>
			</table>
		</div>
		<script language="javascript" src="com.openkm.login.Main.nocache.js"></script>
	</body>
</html>