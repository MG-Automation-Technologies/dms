<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.git.openkm.bean.SessionInfo" %>
<%@ page import="es.git.openkm.core.SessionManager" %>
<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<br><br>
<table border="0" cellpadding="2" cellspacing="0" align="center" class="demo">
<% 
	List<String> users = new ArrayList<String>();
	boolean userAvailable = false;
	out.println("<tr><td class=\"demoAlert\" style=\"text-align: center; padding: 10px\">");
	out.println("This is a development demo where you can test experimental new features.<br>");
	out.println("It it still buggy and can crash. ");
	out.println("Please report bugs to <a href=\"http://www.openkm.com/component/option,com_mantis/Itemid,36/\">OpenKM website</a>.");
	out.println("</td></tr>");
	out.println("<tr><td class=\"demoTitle\" align=\"center\"><u>- DEMO INFORMATION -</u></td></tr>");
	out.println("<tr><td class=\"demo\" style=\"text-align: center; padding: 10px\">User notifications and document / folder subscriptions<br>are disabled</td></tr>");
	out.println("<tr><td class=\"demoTitle\" align=\"center\">LOGGED USERS</td></tr>");
	out.println("<tr><td class=\"demo\"><ul>");
	
	SessionManager sm = SessionManager.getInstance();

	for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
		String token = it.next();
		SessionInfo si = sm.getInfo(token);
		if (!users.equals(Config.ADMIN_USER)) {
			out.print("<li>"+si.getSession().getUserID()+" [CREATION: "+si.getCreation().getTime()+", ACCESS: "+si.getAccess().getTime()+"] ");
		}
		users.add(si.getSession().getUserID());
	}
	
	// Message if all users are available
	if (sm.getTokens().isEmpty()) {
		out.print("<li>No users logged, all users are available</li>");
	}
	
	out.println("</ul></td></tr>");
	out.println("<tr><td class=\"demoTitle\" align=\"center\">AVAILABLE USERS</td></tr>");
	out.println("<tr><td class=\"demo\"><ul>");
	
	for (int i=0; i<10; i++) {
		String userID = "user" + i;
		if (!users.contains(userID) && !users.equals("system")){
			out.print("<li>Login: " + userID + " / Password: pass" + i +"</li>");
			userAvailable = true;
		}
	}
	
	// Message if all users are logged
	if (!userAvailable) {
		out.println("<li>At this moment all users are logged, please be patient</li>");
	}
	
	out.println("</ul></td></tr>");
	
  %>
</table>