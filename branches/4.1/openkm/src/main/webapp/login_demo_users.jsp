<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.git.openkm.bean.SessionInfo" %>
<%@ page import="es.git.openkm.util.FormatUtil" %>
<%@ page import="es.git.openkm.core.SessionManager" %>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<table border="0" cellpadding="2" cellspacing="0" align="center" class="demo">
<%
  List<String> users = new ArrayList<String>();
  boolean userAvailable = false;
  out.println("<tr><td class=\"demo_alert\">");
  out.println("This demo is available for testing purposes.<br>");
  out.println("These documents can be removed at any time, <br>");
  out.println("so don't expect your document to be here tomorrow.<br>");
  out.println("</td></tr>");
  out.println("<tr><td class=\"demo_title\">- LOGGED USERS -</td></tr>");
  out.println("<tr><td>");

  SessionManager sm = SessionManager.getInstance();

  if (sm.getTokens().isEmpty()) {
    out.print("<b>No users logged, all users are available.</b>");
  } else {
    out.println("<table class=\"demo_list\" width=\"100%\" align=\"center\">");
    out.println("<tr><th>User</th><th>Login</th><th>Last action</th></tr>");
    for (Iterator<String> it = sm.getTokens().iterator(); it.hasNext(); ) {
      String token = it.next();
      SessionInfo si = sm.getInfo(token);
      out.print("<tr><td>"+si.getSession().getUserID()+"</td><td>"+
        FormatUtil.formatDate(si.getCreation())+"</td><td>"+
        FormatUtil.formatDate(si.getAccess())+"</td</tr>");
      users.add(si.getSession().getUserID());
    }
    out.println("</table>");
  }

  out.println("</td></tr>");
  out.println("<tr><td class=\"demo_title\">- AVAILABLE DEMO USERS -</td></tr>");
  out.println("<tr><td><b>If you need you own user, please <a href=\"http://www.openkm.com/Contact/\">contact us</a>.</b></td></tr>");
  out.println("<tr><td>");

  String availables = "<table class=\"demo_list\" align=\"center\">";
  availables += "<tr><th>User</th><th>Password</th></tr>";
  for (int i=0; i<Config.SYSTEM_DEMO_USERS; i++) {
    String userID = "user" + String.format("%02d", i);
    if (!users.contains(userID)) {
      availables += "<tr><td>" + userID + "</td><td>pass" + String.format("%02d", i) +"</td></tr>";
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
