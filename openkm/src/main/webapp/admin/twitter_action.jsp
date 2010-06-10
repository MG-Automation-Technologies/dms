<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="com.openkm.util.WebUtil"%>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.TwitterAccount"%>
<%@ page import="com.openkm.dao.TwitterAccountDAO"%>
<%@ page import="com.openkm.core.DatabaseException"%>
<%@ page import="javax.jcr.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.openkm.util.JCRUtils"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>User action</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String action = WebUtil.getString(request, "action");
		int ta_id = WebUtil.getInt(request, "ta_id");
		String ta_user = WebUtil.getString(request, "ta_user");
		String ta_tuser = WebUtil.getString(request, "ta_tuser");
		boolean ta_active = WebUtil.getBoolean(request, "ta_active");

		TwitterAccount ta = new TwitterAccount();
		ta.setId(ta_id);
		ta.setUser(new String(ta_user.getBytes("ISO-8859-1"), "UTF-8"));
		ta.setTwitterUser(new String(ta_tuser.getBytes("ISO-8859-1"), "UTF-8"));
		ta.setActive(ta_active);
		Session jcrSession = null;
		
		try {
			jcrSession = JCRUtils.getSession();
			
			if (action.equals("c")) {
				TwitterAccountDAO.create(ta);
			} else if (action.equals("u")) {
				TwitterAccountDAO.update(ta);
			} else if (action.equals("d")) {
				TwitterAccountDAO.delete(ta_id);
			}
			
			// Activity log
			UserActivity.log(jcrSession.getUserID(), "USER_TWITTER_ACTION", ta.toString(), action);
			
			response.sendRedirect("twitter_list.jsp?user="+ta.getUser());
		} catch (DatabaseException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		} finally {
			JCRUtils.logout(jcrSession);
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
