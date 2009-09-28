<%@ page import="es.git.openkm.dao.AbstractDAO" %>
<%@ page import="es.git.openkm.dao.ActivityDAO" %>
<%@ page import="es.git.openkm.dao.AuthDAO" %>
<%@ page import="es.git.openkm.dao.DashboardStatsDAO" %>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.ResultSetMetaData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Database Query</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String statement = request.getParameter("statement");
		String ds = request.getParameter("ds")==null?"":request.getParameter("ds");

		out.println("<h1>Database query</h1>");
		out.println("<form action=\"database_query.jsp\">");
		out.println("<table class=\"form\" align=\"center\">");
		out.println("<tr><td><textarea cols=\"75\" rows=\"5\" name=\"statement\">"+(statement==null?"":statement)+"</textarea></td></tr>");
		out.println("<tr><td align=\"right\">Datasource <select name=\"ds\">"+
				"<option "+(ds.equals("OKMActivityDS")?"selected":"")+" value=\"OKMActivityDS\">User Activity</option>"+
				"<option "+(ds.equals("OKMAuthDS")?"selected":"")+" value=\"OKMAuthDS\">User Auth</option>"+
				"<option "+(ds.equals("OKMDashboardStatsDS")?"selected":"")+" value=\"OKMDashboardStatsDS\">Dashboard Stats</option>"+
				"</select>");
		out.println("<input type=\"submit\" value=\"Send\"></td></tr>");
		out.println("</table>");
		out.println("</form>");
		
		if (statement != null) {
			statement = new String(statement.getBytes("ISO-8859-1"), "UTF-8");
			AbstractDAO dao = null;
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			if (ds.equals("OKMActivityDS")) {
				dao = ActivityDAO.getInstance();
			} else if (ds.equals("OKMAuthDS")) {
				dao = AuthDAO.getInstance();
			} else if (ds.equals("OKMDashboardStatsDS")) {
				dao = DashboardStatsDAO.getInstance();
			}
			
			try {
				con = dao.getConnection();
				stmt = con.prepareStatement(statement);
				out.println("<br/>");
				
				if (statement.toUpperCase().indexOf("INSERT") > -1 ||
						statement.toUpperCase().indexOf("UPDATE") > -1 ||
						statement.toUpperCase().indexOf("DELETE") > -1) {
					int ret = stmt.executeUpdate();
					out.println("Row Count: "+ret);
				} else {
					rs = stmt.executeQuery();
					ResultSetMetaData meta = rs.getMetaData();
					
					
					out.println("<table class=\"results\">");
					out.print("<tr>");
					for (int i=1; i<meta.getColumnCount()+1; i++) {
						out.print("<th>"+meta.getColumnName(i)+"</th>");
					}
					out.println("</tr>");
					
					int i = 0;
					while (rs.next()) {
						out.println("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
						for (int j=1; j<meta.getColumnCount()+1; j++) {
							out.print("<td>"+rs.getString(j)+"</td>");
						}
						out.println("</tr>");
					}
				
					out.println("</table>");
				}
			} catch (SQLException e) {
				out.println("<div class=\"error\">"+e.getMessage()+"<div>");
			} finally {
				dao.closeResultSet(rs);
				dao.closeStatement(stmt);
				dao.closeConnection(con);
			}
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>