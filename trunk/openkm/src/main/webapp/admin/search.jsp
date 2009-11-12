<%@ page import="es.git.openkm.module.direct.DirectRepositoryModule" %>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="org.apache.jackrabbit.JcrConstants"%>
<%@ page import="javax.jcr.Workspace" %>
<%@ page import="javax.jcr.Session" %>
<%@ page import="javax.jcr.Value" %>
<%@ page import="javax.jcr.Property"%>
<%@ page import="javax.jcr.Node" %>
<%@ page import="javax.jcr.query.Query" %>
<%@ page import="javax.jcr.query.QueryManager" %>
<%@ page import="javax.jcr.query.QueryResult" %>
<%@ page import="javax.jcr.query.RowIterator" %>
<%@ page import="javax.jcr.query.Row" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Repository Search</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String statement = request.getParameter("statement");
		String type = request.getParameter("type");

		out.println("<h1>Search</h1>");
		out.println("<form action=\"search.jsp\">");
		out.println("<table class=\"form\" align=\"center\">");
		out.println("<tr>");
		out.println("<td><b>Predefined</b></td>");
		out.println("<td>");
		out.println("<a href=\"search.jsp?type=xpath&statement="+URLEncoder.encode("/jcr:root/okm:root//element(*,okm:document)[@jcr:lockOwner]/@jcr:lockOwner", "UTF-8")+"\">Locked documents</a>");
		out.println(" - ");
		out.println("<a href=\"search.jsp?type=xpath&statement="+URLEncoder.encode("/jcr:root/okm:root//element(*, okm:document)[@okm:author='okmAdmin']/(@jcr:lockOwner|@jcr:created)", "UTF-8")+"\">Documents created by user 'okmAdmin'</a>");
		out.println(" - ");
		out.println("<a href=\"search.jsp?type=xpath&statement="+URLEncoder.encode("/jcr:root/okm:root//element(*, okg:test)", "UTF-8")+"\">Documents with property group 'okg:test'</a>");
		out.println(" - ");
		out.println("<a href=\"search.jsp?type=xpath&statement="+URLEncoder.encode("/jcr:root/okm:root//element(*, mix:notification)/@okm:subscriptors", "UTF-8")+"\">Documents subscribed</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><b>Query</b></td>");
		out.println("<td><textarea cols=\"100\" name=\"statement\">"+(statement==null?"":statement)+"</textarea></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td><b>Type</b></td>");
		out.println("<td>");
		out.println("<select name=\"type\">");
		out.println("<option value=\"xpath\" "+("xpath".equals(type)?"selected":"")+">XPath</option>");
		out.println("<option value=\"sql\" "+("sql".equals(type)?"selected":"")+">SQL</option>");
		out.println("</select>");
		out.println("<a target=\"_blank\" href=\"http://people.apache.org/~mreutegg/jcr-query-translator/translator.html\">JCR Query Translator</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Send\"></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<br/>");
		
		if (statement != null && type != null) {
			try {
				Session jcrSession = DirectRepositoryModule.getSystemSession();
				Workspace workspace = jcrSession.getWorkspace();
				QueryManager queryManager = workspace.getQueryManager();
				Query query = queryManager.createQuery(statement, type);
				QueryResult result = query.execute();
				RowIterator it = result.getRows();
			
				out.println("<b>Count</b>: "+it.getSize());
				out.println("<table class=\"results\" align=\"center\">");
				out.print("<tr>");
				String[] columns = result.getColumnNames();
			
				for (int i=0; i<columns.length; i++) {
					out.print("<th>"+columns[i]+"</th>");
				}

				out.println("</tr>");
				int i = 0;
				while (it.hasNext()) {
					Row row = it.nextRow();
					out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");

					for (int j=0; j<columns.length; j++) {
						if (columns[j].startsWith("jcr:")) {
							// Get property from row
							out.println("<td>"+(row.getValue(columns[j])!=null?row.getValue(columns[j]).getString():"NULL")+"</td>");
						} else {
							// Get property from node
							String path = row.getValue(JcrConstants.JCR_PATH).getString();
							Node node = jcrSession.getRootNode().getNode(path.substring(1));
							Property prop = node.getProperty(columns[j]);
							
							if (prop != null) {
								if (prop.getDefinition().isMultiple()) {
									Value[] values = prop.getValues();
									out.print("<td>");
									for (int k=0; k<values.length-1; k++) {
										out.print(values[k].getString()+", ");
									}
									out.print(values[values.length-1].getString());
									out.print("</td>");
								} else {
									out.print("<td>"+(prop.getValue()!=null?prop.getValue().getString():"NULL")+"</td>");
								}
							}
						}
					}
				
					out.println("</tr>");
				}
			
				out.println("</table>");
			} catch (Exception e) {
				out.println("<div class=\"error\">"+e.getMessage()+"</div>");
			}
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>