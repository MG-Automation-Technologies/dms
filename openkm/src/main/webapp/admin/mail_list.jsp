<%@ page import="com.openkm.core.Config"%>
<%@ page import="com.openkm.util.WebUtil"%>
<%@ page import="com.openkm.dao.AuthDAO"%>
<%@ page import="com.openkm.dao.bean.MailAccount"%>
<%@ page import="com.openkm.dao.MailAccountDAO"%>
<%@ page import="com.openkm.core.DatabaseException"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Mail accounts</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String user = WebUtil.getString(request, "user");
		out.println("<h1>Mail accounts <span style=\"font-size: 10px;\">(<a href=\"user_list.jsp\">Users</a>)</font></h1>");
				
		try {
			out.println("<table class=\"results\" width=\"80%\">");
			out.println("<tr><th>OKM user</th><th>Mail host</th><th>Mail user</th><th>Mail password</th>");
			out.println("<th>Mail folder</th><th>Active</th>");
			out.println("<th width=\"25px\"><a href=\"mail_edit.jsp?action=c&user="+user+"\"><img src=\"img/action/new.png\" alt=\"New account\" title=\"New account\"/></a></th></tr>");
			Collection<MailAccount> mailAccounts = null;
			
			if (user != null && !user.equals("")) {
				mailAccounts = MailAccountDAO.findByUser(user, false);
			} else {
				mailAccounts = MailAccountDAO.findAll(false);
			}
			
			int i = 0;
			for (Iterator<MailAccount> it = mailAccounts.iterator(); it.hasNext(); ) {
				MailAccount ma = it.next();
				out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\"><td>"+ma.getUser()+"</td><td>"+ma.getMailHost()+"</td><td>"+ma.getMailUser()+"</td>"+
						"<td>"+ma.getMailPassword()+"</td><td>"+ma.getMailFolder()+"</td><td>"+ma.isActive()+"</td>"+
						"<td><a href=\"mail_edit.jsp?action=u&id="+ma.getId()+"\"><img src=\"img/action/edit.png\" alt=\"Edit\" title=\"Edit\"/></a>"+
						" &nbsp; <a href=\"mail_edit.jsp?action=d&id="+ma.getId()+"\"><img src=\"img/action/delete.png\" alt=\"Delete\" title=\"Delete\"/></a></td></tr>");
			}
				
			out.println("</table>");
		} catch (DatabaseException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
