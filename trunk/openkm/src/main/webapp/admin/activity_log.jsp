<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.dao.ActivityDAO" %>
<%@ page import="es.git.openkm.dao.bean.ActivityFilter" %>
<%@ page import="es.git.openkm.dao.bean.Activity" %>
<%@ page import="es.git.openkm.module.direct.DirectAuthModule" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <link rel="stylesheet" type="text/css" href="js/jscalendar/calendar-win2k-1.css" />
  <script type="text/javascript" src="js/jscalendar/calendar.js"></script>
  <script type="text/javascript" src="js/jscalendar/lang/calendar-en.js"></script>
  <script type="text/javascript" src="js/jscalendar/calendar-setup.js"></script>
  <title>Activity Log</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String date_begin = request.getParameter("date_begin");
		String date_end = request.getParameter("date_end");
		String user = request.getParameter("user");
		String action = request.getParameter("action");
		
		out.println("<h1>Activity log</h1>");
		out.println("<form action=\"activity_log.jsp\">");
		out.println("<table class=\"form\" align=\"center\">");
		out.println("<tr><td>");
		out.print("From ");
		out.print("<input type=\"text\" name=\"date_begin\" id=\"date_begin\" value=\""+(date_begin==null?"":date_begin)+"\" size=\"15\" readonly=\"true\" >");
		out.println("<img src=\"js/jscalendar/img.gif\" id=\"f_trigger_begin\" align=\"absmiddle\" />");
		out.print(" To ");
		out.print("<input type=\"text\" name=\"date_end\" id=\"date_end\" value=\""+(date_end==null?"":date_end)+"\" size=\"15\" readonly=\"true\" >");
		out.println("<img src=\"js/jscalendar/img.gif\" id=\"f_trigger_end\" align=\"absmiddle\" />");
		out.println(" User <select name=\"user\" >");
		out.println("<option value=\"\" ></option>");
		out.println("<option value=\""+Config.SYSTEM_USER+"\" "+(Config.SYSTEM_USER.equals(user)?"selected":"")+">"+Config.SYSTEM_USER+"</option>");
		
		Collection<String> users = new DirectAuthModule().getUsers(Config.SYSTEM_USER);
		
		for (Iterator<String> it = users.iterator(); it.hasNext(); ) {
			String lUser = it.next();
			out.println("<option value=\""+lUser+"\" "+(lUser.equals(user)?"selected":"")+">"+lUser+"</option>");
		}
		
		out.println("</select>");
		out.println(" Action <select name=\"action\" >");
		out.println("<option value=\"\" ></option>");
		String actions[] = { "Auth", "LOGIN", "LOGOUT", "SESSION_EXPIRATION",
				"Document", "CANCEL_CHECKOUT_DOCUMENT", 
				"CHECKIN_DOCUMENT", "CHECKOUT_DOCUMENT", "CREATE_DOCUMENT", "DELETE_DOCUMENT", 
				"GET_CHILD_DOCUMENTS", "GET_DOCUMENT_CONTENT", "GET_DOCUMENT_CONTENT_BY_VERSION", 
				"GET_DOCUMENT_PROPERTIES", "GET_DOCUMENT_VERSION_HISTORY", "GET_PROPERTY_GROUP_PROPERTIES", 
				"LOCK_DOCUMENT", "MOVE_DOCUMENT", "PURGE_DOCUMENT", "RENAME_DOCUMENT", 
				"SET_DOCUMENT_CONTENT", "SET_DOCUMENT_PROPERTIES", "UNLOCK_DOCUMENT", 
				"Folder", "COPY_FOLDER", "CREATE_FOLDER", "DELETE_FOLDER", "GET_CHILD_FOLDERS", 
				"GET_FOLDER_CONTENT_INFO", "GET_FOLDER_PROPERTIES", "MOVE_FOLDER", "PURGE_FOLDER", "RENAME_FOLDER", 
				"Admin", "PROPERTY_GROUP", "REPOSITORY_ACTION", "REPOSITORY_EDIT", "REPOSITORY_SET",
				"REPOSITORY_VIEW", "USER_ACTION" };
		
		for (int i=0 ; i<actions.length; i++) {
			if (actions[i].equals("Auth") || actions[i].equals("Document") || actions[i].equals("Folder") || actions[i].equals("Admin")) {
				out.println("<optgroup label=\""+actions[i]+"\">");
			} else {
				out.println("<option value=\""+actions[i]+"\" "+(actions[i].equals(action)?"selected":"")+">"+actions[i]+"</option>");	
			}
		}
		
		out.println("</select>");
		out.println("</td></tr>");
		out.println("<tr><td align=\"right\">");
		out.println("<input type=\"submit\" value=\"Send\">");
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<br/>");
		
		if (date_begin != null && !date_begin.equals("") && date_end != null && !date_end.equals("")) {
			try {
				out.println("<table class=\"results\" align=\"center\">");
				out.println("<tr><th>Date</th><th>User</th><th>Token</th><th>Action</th><th>Item</th><th>Params</th></tr>");
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ActivityDAO dao = ActivityDAO.getInstance();
				ActivityFilter filter = new ActivityFilter();
				Calendar begin = Calendar.getInstance();
				begin.setTime(sdf.parse(date_begin));
				filter.setActDateBegin(begin);
				Calendar end = Calendar.getInstance();
				end.setTime(sdf.parse(date_end));
				filter.setActDateEnd(end);
				filter.setActUser(user);
				filter.setActAction(action);
				Collection<Activity> al = dao.findByFilter(filter);
				
				int i = 0;
				for (Iterator<Activity> it = al.iterator(); it.hasNext(); ) {
					Activity vo = it.next();
					out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\"><td>"+vo.getActDate().getTime()+"</td><td>"+vo.getActUser()+"</td><td>"+
							vo.getActToken()+"</td><td>"+vo.getActAction()+"</td><td>"+
							vo.getActItem()+"</td><td>"+vo.getActParams()+"</td></tr>");
				}
				
				out.println("</table>");
			} catch (SQLException e) {
				out.println("<div class=\"error\">"+e.getMessage()+"</div>");
			}
		} else {
			out.println("<div class=\"error\">You must select a date range</div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
  <script type="text/javascript">
    Calendar.setup({
      inputField : "date_begin",
      ifFormat   : "%Y-%m-%d",
      button     : "f_trigger_begin"
    });
    Calendar.setup({
      inputField : "date_end",
      ifFormat   : "%Y-%m-%d",
      button     : "f_trigger_end"
    });
  </script>
</body>
</html>