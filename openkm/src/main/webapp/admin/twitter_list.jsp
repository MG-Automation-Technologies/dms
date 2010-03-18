<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="es.git.openkm.util.WebUtil"%>
<%@ page import="es.git.openkm.dao.AuthDAO"%>
<%@ page import="es.git.openkm.dao.bean.TwitterAccount"%>
<%@ page import="java.sql.SQLException"%>
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
  <title>Twitter accounts</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String user = WebUtil.getString(request, "user");
		AuthDAO dao = AuthDAO.getInstance();
		out.println("<h1>Twitter accounts <span style=\"font-size: 10px;\">(<a href=\"user_list.jsp\">Users</a>)</font></h1>");
				
		try {
			out.println("<table class=\"results\" width=\"80%\">");
			out.println("<tr><th>OKM user</th><th>Twitter user</th><th>Active</th>");
			out.println("<th width=\"25px\"><a href=\"twitter_edit.jsp?action=c&user="+user+"\"><img src=\"img/action/new.png\" alt=\"New account\" title=\"New account\"/></a></th></tr>");
			Collection<TwitterAccount> twitterAccounts = null;
			
			if (user != null && !user.equals("")) {
				twitterAccounts = dao.findTwitterAccountsByUser(user, false);
			} else {
				twitterAccounts = dao.findAllTwitterAccounts(false);
			}
			
			int i = 0;
			for (Iterator<TwitterAccount> it = twitterAccounts.iterator(); it.hasNext(); ) {
				TwitterAccount ta = it.next();
				out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\"><td>"+ta.getUser()+"</td><td>"+ta.getTwitterUser()+"</td><td>"+ta.isActive()+"</td>"+
						"<td><a href=\"twitter_edit.jsp?action=u&id="+ta.getId()+"\""+"><img src=\"img/action/edit.png\" alt=\"Edit\" title=\"Edit\"/></a>"+
						" &nbsp; <a href=\"twitter_edit.jsp?action=d&id="+ta.getId()+"\""+"><img src=\"img/action/delete.png\" alt=\"Delete\" title=\"Delete\"/></a></td></tr>");
			}
				
			out.println("</table>");
		} catch (SQLException e) {
			out.println("<div class=\"error\">"+e.getMessage()+"</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
