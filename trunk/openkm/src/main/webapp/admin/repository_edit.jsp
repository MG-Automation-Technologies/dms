<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.core.SessionManager"%>
<%@ page import="com.openkm.bean.Scripting"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="javax.jcr.Session" %>
<%@ page import="javax.jcr.Node" %>
<%@ page import="javax.jcr.Property" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <title>Repository Browser</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String) session.getAttribute("token");
		Session jcrSession = SessionManager.getInstance().get(token);
		String path = request.getParameter("path");
		String property = request.getParameter("property");
		String value = request.getParameter("value");
		String type = request.getParameter("type");
		String multiple = request.getParameter("multiple");
		
		if (path != null && property != null && value != null) {
			path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			property = new String(property.getBytes("ISO-8859-1"), "UTF-8");
			value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			
			Node node = jcrSession.getRootNode().getNode(path.substring(1));
			Property prop = node.getProperty(property);
			
			out.println("<h1>Edit repository property</h1>");
			out.println("<form action=\"repository_set.jsp\">");
			out.println("<input type=\"hidden\" name=\"type\" value=\""+type+"\">");
			out.println("<input type=\"hidden\" name=\"multiple\" value=\""+multiple+"\">");
			out.println("<input type=\"hidden\" name=\"path\" value=\""+path+"\">");
			out.println("<input type=\"hidden\" name=\"property\" value=\""+property+"\">");
			out.println("<table class=\"form\" width=\"250px\" align=\"center\">");
			out.println("<tr><td>Path</td><td><i>"+node.getPath()+"</i></td></tr>");
			out.println("<tr><td>Property</td><td><i>"+prop.getName()+"</i></td></tr>");
			
			if (prop.getName().equals(Scripting.SCRIPT_CODE)) {
				out.print("<tr><td>Value</td><td>");
				out.print("<textarea cols=\"75\" rows=\"15\" name=\"value\">");
				out.print(value);
				out.print("</textarea></td></tr>");
			} else {
				out.println("<tr><td>Value</td><td><input type=\"text\" name=\"value\" value=\""+value+"\"></td></tr>");
			}
			
			out.println("<tr><td colspan=\"2\" align=\"right\">");
			out.println("<input type=\"button\" onclick=\"javascript:window.history.back()\" value=\"Cancel\">");
			out.println("<input type=\"submit\" value=\"Send\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
			
			// Activity log
			UserActivity.log(jcrSession, "REPOSITORY_EDIT", node.getPath(), property+" : "+value);
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>