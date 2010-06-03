<%@ page import="com.openkm.core.SessionManager"%>
<%@ page import="com.openkm.core.Config"%>
<%@ page import="com.openkm.util.WebUtil"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.MailAccount"%>
<%@ page import="com.openkm.dao.MailAccountDAO"%>
<%@ page import="com.openkm.core.DatabaseException"%>
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
		String action = WebUtil.getString(request, "action");
		int ma_id = WebUtil.getInt(request, "ma_id");
		String ma_user = WebUtil.getString(request, "ma_user");
		String ma_mhost = WebUtil.getString(request, "ma_mhost");
		String ma_muser = WebUtil.getString(request, "ma_muser");
		String ma_mpass = WebUtil.getString(request, "ma_mpass");
		String ma_mfolder = WebUtil.getString(request, "ma_mfolder");
		boolean ma_active = WebUtil.getBoolean(request, "ma_active");

		MailAccount ma = new MailAccount();
		ma.setId(ma_id);
		ma.setUser(new String(ma_user.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailHost(new String(ma_mhost.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailUser(new String(ma_muser.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailPassword(new String(ma_mpass.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setMailFolder(new String(ma_mfolder.getBytes("ISO-8859-1"), "UTF-8"));
		ma.setActive(ma_active);
		
		try {
			if (action.equals("c")) {
				MailAccountDAO.create(ma);
			} else if (action.equals("u")) {
				MailAccountDAO.update(ma);
			} else if (action.equals("d")) {
				MailAccountDAO.delete(ma_id);
			}
			
			// Activity log
			UserActivity.log(jcrSession, "USER_MAIL_ACTION", ma.toString(), action);
			
			response.sendRedirect("mail_list.jsp?user="+ma.getUser());
		} catch (DatabaseException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
