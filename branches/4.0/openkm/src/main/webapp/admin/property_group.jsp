<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="javax.jcr.Session" %>
<%@ page import="es.git.openkm.util.UserActivity"%>
<%@ page import="es.git.openkm.api.OKMPropertyGroup"%>
<%@ page import="es.git.openkm.bean.MetaData"%>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.core.SessionManager"%>
<%@ page import="es.git.openkm.module.direct.DirectRepositoryModule" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Property Group</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String) session.getAttribute("token");
		Session jcrSession = SessionManager.getInstance().get(token);
		String pgPath = request.getParameter("pgPath");
		
		if (pgPath != null && !pgPath.equals("")) {
			pgPath = new String(pgPath.getBytes("ISO-8859-1"), "UTF-8");
		}
		
		out.println("<h1>Property groups</h1>");
		out.println("<table class=\"results\" align=\"center\">");
		out.println("<tr><th colspan=\"2\">Property group name</th></tr>");
		out.println("<tr><th>Property name</th><th>Property metadata</th></tr>");
		OKMPropertyGroup okmPG = OKMPropertyGroup.getInstance();
	
		Collection<String> pGroups = okmPG.getAllGroups(token);
		for (Iterator<String> itGrp = pGroups.iterator(); itGrp.hasNext(); ) {
			String pGroup = itGrp.next();
			out.println("<tr class=\"fuzzy\"><td colspan=\"2\" align=\"center\"><b>"+pGroup+"</b></td></tr>");
			
			HashMap<String, MetaData> mData =  okmPG.getMetaData(token, pGroup);
			int i = 0;
			for (Iterator<String> itMD = mData.keySet().iterator(); itMD.hasNext(); ) {
				String key = itMD.next();
				MetaData value = mData.get(key);
				out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\"><td>"+key+"</td><td>"+value+"</td></tr>");
			}
		}
		
		out.println("</table>");
		out.println("<hr>");
		out.println("<center><h2>Register property group</h2></center>");
		out.println("<form action=\"property_group.jsp\">");
		out.println("<table class=\"form\" align=\"center\">");
		out.println("<tr><td>");
		out.println("Property Group definition path <input type=\"text\" size=\"50\" name=\"pgPath\" value=\""+(pgPath==null?"":pgPath)+"\" >");
		out.println("</td></tr>");
		out.println("<tr><td align=\"right\">");
		out.println("<input type=\"submit\" value=\"Send\">");
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</form>");

		try {
			if (pgPath != null && !pgPath.equals("")) {
				FileInputStream fis = new FileInputStream(pgPath);
				DirectRepositoryModule.registerCustomNodeTypes(jcrSession, fis);
				out.println("<div class=\"ok\">Property Group definition '"+new File(pgPath).getAbsolutePath()+"' registered'<div>");
			}
			
			// Activity log
			UserActivity.log(jcrSession, "PROPERTY_GROUP", pgPath, null);
		} catch (Exception e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>