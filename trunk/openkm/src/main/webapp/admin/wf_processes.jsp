<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.api.OKMWorkflow"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessDefinition"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Workflow Process Definition Browser</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String)session.getAttribute("token");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		
		if (action != null && !action.equals("")) {
			if (action.equals("start") && id != null && !id.equals("")) {
				HashMap<String, String> variables = new HashMap<String, String>();
				ProcessInstance pi = OKMWorkflow.getInstance().runProcessDefinition(token, Long.parseLong(id), variables);
				response.sendRedirect("wf_procins.jsp?id="+pi.getId());
			} else if (action.equals("delete") && id != null && !id.equals("")) {
				OKMWorkflow.getInstance().deleteProcessDefinition(token, Long.parseLong(id));
				response.sendRedirect("wf_processes.jsp");
			}
		} else {
			Collection<ProcessDefinition> col = OKMWorkflow.getInstance().findAllProcessDefinitions(token);
			out.println("<table>");
			out.println("<tr>");
			out.println("<td><h1>Process Definitions</h1></td><td><a href=\"\">Reload</a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<table class=\"results\">");
			out.print("<tr><th>Process ID</th><th>Process Name</th><th>Version</th><th>Actions</th></tr>");

			int i = 0;
			for (Iterator<ProcessDefinition> it = col.iterator(); it.hasNext(); ) {
				ProcessDefinition pd = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+pd.getId()+"</td>");
				out.print("<td>"+pd.getName()+"</td>");
				out.print("<td>"+pd.getVersion()+"</td>");
				out.print("<td>");
				out.print("<a href=\"wf_procdef.jsp?id="+pd.getId()+"\">Examine</a>");
				out.print(" - ");
				out.print("<a href=\"wf_processes.jsp?action=delete&id="+pd.getId()+"\">Delete</a>");
				out.print(" - ");
				out.print("<a href=\"wf_processes.jsp?action=start&id="+pd.getId()+"\">Start</a>");
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			out.println("<hr>");
			out.println("<center><h2>Upload process definition</h2></center>");
			out.println("<form action=\"/OpenKM"+Config.INSTALL+"/OKMWorkflowUploadServletAdmin\" method=\"POST\" enctype='multipart/form-data'>");
			out.println("<table class=\"form\" align=\"center\">");
			out.println("<tr><td>");
			out.println("<input type=\"file\" name=\"definition\">");
			out.println("</td></tr>");
			out.println("<tr><td align=\"right\">");
			out.println("<input type=\"submit\" value=\"Upload\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>