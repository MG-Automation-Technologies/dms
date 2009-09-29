<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.git.openkm.bean.SessionInfo" %>
<%@ page import="es.git.openkm.core.SessionManager" %>
<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<table border="0" cellpadding="2" cellspacing="0" align="center" class="demo">
<%
  List<String> users = new ArrayList<String>();
  boolean userAvailable = false;
  out.println("<tr><td class=\"demo_alert\">");
  out.println("This is a development demo where you can test exciting new features<br>");
  out.println("</td></tr>");
  out.println("<tr><td class=\"demo_title\">- LOGGED USERS -</td></tr>");
  out.println("<tr><td>");

  SessionManager sm = SessionManager.getInstance();

  if (sm.getTokens().isEmpty()) {
    out.print("No users logged, all users are available");
  } else {
    out.println("<table class=\"demo_list\" align=\"center\">");
    out.println("<tr><th>User</th><th>Creation</th><th>Last access</th></tr>");
    for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
      String token = it.next();
      SessionInfo si = sm.getInfo(token);
      if (!users.equals(Config.ADMIN_USER)) {
        out.print("<tr><td>"+si.getSession().getUserID()+"</td><td>"+si.getCreation().getTime()+"</td><td>"+si.getAccess().getTime()+"</td</tr>");
      }
      users.add(si.getSession().getUserID());
    }
    out.println("</table>");
  }

  out.println("</td></tr>");
  out.println("<tr><td class=\"demo_title\">- AVAILABLE USERS -</td></tr>");
  out.println("<tr><td>");

  String availables = "<table class=\"demo_list\" align=\"center\">";
  availables += "<tr><th>User</th><th>Password</th></tr>";
  for (int i=0; i<10; i++) {
    String userID = "user" + i;
    if (!users.contains(userID) && !users.equals("system")){
      availables += "<tr><td>" + userID + "</td><td>pass" + i +"</td></tr>";
      userAvailable = true;
    }
  }
  availables += "</table>";

  if (!userAvailable) {
    out.println("At this moment all users are logged, please be patient");
  } else {
    out.println(availables);
  }

  out.println("</td></tr>");
  %>
</table>
