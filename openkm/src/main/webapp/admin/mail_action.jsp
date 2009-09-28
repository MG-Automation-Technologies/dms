<%@ page import="es.git.openkm.core.SessionManager"%>
<%@ page import="es.git.openkm.util.UserActivity"%>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.dao.AuthDAO"%>
<%@ page import="es.git.openkm.dao.bean.MailAccount"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javax.jcr.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>User action</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		String token = (String) session.getAttribute("token");
		Session jcrSession = SessionManager.getInstance().get(token);
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String ma_user = request.getParameter("ma_user") != null?request.getParameter("ma_user"):"";
		String ma_mhost = request.getParameter("ma_mhost") != null?request.getParameter("ma_mhost"):"";
		String ma_muser = request.getParameter("ma_muser") != null?request.getParameter("ma_muser"):"";
		String ma_mpass = request.getParameter("ma_mpass") != null?request.getParameter("ma_mpass"):"";
		String ma_mfolder = request.getParameter("ma_mfolder") != null?request.getParameter("ma_mfolder"):"";
		boolean ma_active = request.getParameter("ma_active")!=null && request.getParameter("ma_active").equals("on");

		AuthDAO dao = AuthDAO.getInstance();
		MailAccount ma = new MailAccount();
		ma.setUser(new String(ma_user.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailHost(new String(ma_mhost.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailUser(new String(ma_muser.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailPassword(new String(ma_mpass.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailFolder(new String(ma_mfolder.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setActive(ma_active);
		
		try {
			if (action.equals("c")) {
				dao.createMailAccount(ma);
			} else if (action.equals("u")) {
				dao.updateMailAccount(ma);
			} else if (action.equals("d")) {
				dao.deleteMailAccount(ma);
			}
			
			// Activity log
			UserActivity.log(jcrSession, "USER_MAIL_ACTION", ma.toString(), action);
			
			response.sendRedirect("mail_list.jsp?user="+ma.getUser());
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
