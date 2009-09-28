<%@ page import="java.io.File" %>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.core.SessionManager" %>
<%@ page import="es.git.openkm.util.impexp.RepositoryImporter" %>
<%@ page import="es.git.openkm.util.impexp.HTMLInfoDecorator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Repository Import</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String repoPath = request.getParameter("repoPath");
		String fsPath = request.getParameter("fsPath");
		
		if (repoPath != null && !repoPath.equals("")) {
			repoPath = new String(repoPath.getBytes("ISO-8859-1"), "UTF-8");
		}
		
		if (fsPath != null && !fsPath.equals("")) {
			fsPath = new String(fsPath.getBytes("ISO-8859-1"), "UTF-8");
		}
		
		out.println("<h1>Repository import</h1>");
		out.println("<form action=\"repository_import.jsp\">");
		out.println("<table class=\"form\" align=\"center\">");
		out.println("<tr><td>");
		out.println("Filesystem path <input type=\"text\" name=\"fsPath\" value=\""+(fsPath==null?"":fsPath)+"\" >");
		out.println("Repository path <input type=\"text\" name=\"repoPath\" value=\""+(repoPath==null?"/okm:root":repoPath)+"\" >");
		out.println("</td></tr>");
		out.println("<tr><td align=\"right\">");
		out.println("<input type=\"submit\" value=\"Send\">");
		out.println("</td></tr>");
		out.println("</table>");
		out.println("</form>");

		try {
			if (repoPath != null && !repoPath.equals("") && fsPath != null && !fsPath.equals("")) {
				SessionManager sm = SessionManager.getInstance();
				String token = sm.getTokenByUserId(Config.SYSTEM_USER);
				out.println("<hr/>");
				RepositoryImporter.importDocuments(token, new File(fsPath), repoPath, out, new HTMLInfoDecorator());
				out.println("<hr/>");
				out.println("<span class=\"ok\">Filesystem '"+new File(fsPath).getAbsolutePath()+"' imported into '"+repoPath+"'</span>");
			}
		} catch (Exception e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>