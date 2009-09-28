<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.dao.AuthDAO"%>
<%@ page import="es.git.openkm.dao.bean.MailAccount"%>
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
  <title>Mail account</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String user = request.getParameter("user");
		String mhost = request.getParameter("mhost");
		String muser = request.getParameter("muser");
		String action = request.getParameter("action");
		if (user != null) user = new String(user.getBytes("ISO-8859-1"), "UTF-8");
		if (mhost != null) mhost = new String(mhost.getBytes("ISO-8859-1"), "UTF-8");
		if (muser != null) muser = new String(muser.getBytes("ISO-8859-1"), "UTF-8");
		AuthDAO dao = AuthDAO.getInstance();
		
		try {
			MailAccount ma = new MailAccount();
			
			if (action.equals("u") || action.equals("d")) {
				ma = dao.findMailAccountByPk(user, mhost, muser);
			}
			
			if (action.equals("c")) {
				out.println("<h1>Create mail account</h1>");
			} else if (action.equals("u")) {
				out.println("<h1>Update mail account</h1>");
			} else if (action.equals("d")) {
				out.println("<h1>Delete mail account</h1>");
			}
			
			out.println("<form action=\"mail_action.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\""+action+"\">");
			out.println("<table class=\"form\" width=\"345px\" align=\"center\">");
			out.println("<tr><td>OKM user</td><td><input class=\":required\" name=\"ma_user\" value=\""+user+"\" readonly></td></tr>");
			out.println("<tr><td>Mail host</td><td><input class=\":required\" name=\"ma_mhost\" value=\""+ma.getMailHost()+"\" "+(action.equals("c")?"":"readonly")+"></td></tr>");
			out.println("<tr><td>Mail user</td><td><input class=\":required\" name=\"ma_muser\" value=\""+ma.getMailUser()+"\""+(action.equals("c")?"":"readonly")+"></td></tr>");
			out.println("<tr><td>Mail password</td><td><input class=\":required\" type=\"password\" name=\"ma_mpass\" value=\""+ma.getMailPassword()+"\"></td></tr>");
			out.println("<tr><td>Mail folder</td><td><input name=\"ma_mfolder\" value=\""+ma.getMailFolder()+"\"></td></tr>");
			out.println("<tr><td>Active</td><td><input name=\"ma_active\" type=\"checkbox\" "+(ma.isActive()?"checked":"")+"></td></tr>");
			out.println("<tr><td colspan=\"2\" align=\"right\">");
			out.println("<input type=\"button\" onclick=\"javascript:window.history.back()\" value=\"Cancel\">");
			out.println("<input type=\"submit\" value=\"Send\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
