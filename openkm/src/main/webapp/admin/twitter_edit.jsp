<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.util.WebUtil"%>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.TwitterAccount"%>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <title>Twitter account</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		int id = WebUtil.getInt(request, "id");
		String user = WebUtil.getString(request, "user");
		AuthDAO dao = AuthDAO.getInstance();
		
		try {
			TwitterAccount ta = new TwitterAccount();
			
			if (action.equals("u") || action.equals("d")) {
				ta = dao.findTwitterAccountByPk(id);
			} else {
				ta.setUser(user);
			}
			
			if (action.equals("c")) {
				out.println("<h1>Create twitter account</h1>");
			} else if (action.equals("u")) {
				out.println("<h1>Update twitter account</h1>");
			} else if (action.equals("d")) {
				out.println("<h1>Delete twitter account</h1>");
			}
			
			out.println("<form action=\"twitter_action.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\""+action+"\">");
			out.println("<input type=\"hidden\" name=\"ta_id\" value=\""+id+"\">");
			out.println("<table class=\"form\" width=\"330px\">");
			out.println("<tr><td>OKM user</td><td><input class=\":required\" name=\"ta_user\" value=\""+ta.getUser()+"\" readonly></td></tr>");
			out.println("<tr><td>Twitter user</td><td><input class=\":required\" name=\"ta_tuser\" value=\""+ta.getTwitterUser()+"\"></td></tr>");
			out.println("<tr><td>Active</td><td><input name=\"ta_active\" type=\"checkbox\" "+(ta.isActive()?"checked":"")+"></td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"right\">");
			out.println("<input type=\"button\" onclick=\"javascript:window.history.back()\" value=\"Cancel\">");
			out.println("<input type=\"submit\" value=\"Send\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
