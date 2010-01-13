<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.api.OKMWorkflow"%>
<%@ page import="es.git.openkm.bean.form.FormField"%>
<%@ page import="es.git.openkm.bean.form.Input"%>
<%@ page import="es.git.openkm.bean.form.TextArea"%>
<%@ page import="es.git.openkm.bean.form.Select"%>
<%@ page import="es.git.openkm.bean.form.Button"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessDefinition"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Workflow Process Definition</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String)session.getAttribute("token");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String iid = request.getParameter("iid");
				
		if (action != null && !action.equals("")) {
			if (action.equals("resume") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
				OKMWorkflow.getInstance().resumeProcessInstance(token, Long.parseLong(iid));
				response.sendRedirect("wf_procdef.jsp?id="+id);
			} else if (action.equals("suspend") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
				OKMWorkflow.getInstance().suspendProcessInstance(token, Long.parseLong(iid));
				response.sendRedirect("wf_procdef.jsp?id="+id);
			} else if (action.equals("end") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
				response.sendRedirect("wf_procdef.jsp?id="+id);
			} else if (action.equals("delete") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
				OKMWorkflow.getInstance().deleteProcessInstance(token, Long.parseLong(iid));
				response.sendRedirect("wf_procdef.jsp?id="+id);
			}
		} else {
			ProcessDefinition pd = OKMWorkflow.getInstance().getProcessDefinition(token, Long.parseLong(id));
			out.println("<table>");
			out.println("<tr>");
			out.println("<td><h1>Process Definition</h1></td><td><a href=\"\">Reload</a> - <a href=\"javascript:history.back(1)\">Back</a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Process ID</th><th>Name</th><th>Description</th><th>Version</th></tr>");
			out.println("<tr class=\"odd\"><td>"+pd.getId()+"</td><td>"+pd.getName()+"</td><td>"+(pd.getDescription()!=null?pd.getDescription():"")+"</td><td>"+pd.getVersion()+"</td></tr>");
			out.println("</table>");
						
			Collection<ProcessInstance> col = OKMWorkflow.getInstance().findProcessInstances(token, Long.parseLong(id));
			out.println("<h2>Process Instances</h2>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Instance ID</th><th>Key</th><th>Status</th><th>Start Date</th><th>End Date</th><th>Actions</th></tr>");

			int i = 0;
			for (Iterator<ProcessInstance> it = col.iterator(); it.hasNext(); ) {
				ProcessInstance pi = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+pi.getId()+"</td>");
				out.print("<td>"+(pi.getKey()!=null?pi.getKey():"")+"</td>");
				out.print("<td><b>");
				
				if (pi.getEnd() != null && pi.isSuspended()) out.print("Ended (was suspended)");
				else if (pi.getEnd() != null && !pi.isSuspended()) out.print("Ended");
				else if (pi.getEnd() == null && pi.isSuspended()) out.print("Suspended");
				else if (pi.getEnd() == null && !pi.isSuspended()) out.print("Running");
				
				out.print("</b></td>");
				out.print("<td>"+(pi.getStart()!=null?pi.getStart().getTime():"")+"</td>");
				out.print("<td>"+(pi.getEnd()!=null?pi.getEnd().getTime():"")+"</td>");
				out.print("<td>");
				out.print("<a href=\"wf_procins.jsp?id="+pi.getId()+"\">Examine</a>");
				out.print(" - ");
				out.print("<a href=\"wf_procdef.jsp?action=delete&id="+pd.getId()+"&iid="+pi.getId()+"\">Delete</a>");
				
				if (pi.getEnd() == null) {
					out.print(" - ");
					out.print("<a href=\"wf_procdef.jsp?action=end&id="+pd.getId()+"&iid="+pi.getId()+"\">End</a>");
				}
				
				out.print(" - ");
				
				if (pi.isSuspended()) {
					out.print("<a href=\"wf_procdef.jsp?action=resume&id="+pd.getId()+"&iid="+pi.getId()+"\">Resume</a>");
				} else {
					out.print("<a href=\"wf_procdef.jsp?action=suspend&id="+pd.getId()+"&iid="+pi.getId()+"\">Suspend</a>");
				}
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			Map<String, Collection<FormField>> forms = OKMWorkflow.getInstance().getProcessDefinitionForms(token, Long.parseLong(id));
			out.println("<h2>Forms</h2>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Task</th><th>Form</th></tr>");

			i = 0;
			for (Iterator<String> it = forms.keySet().iterator(); it.hasNext(); ) {
				String task = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+task+"</td>");
				out.print("<td>");
				
				out.println("<table class=\"results\">");
				out.println("<tr><th>Label</th><th>Name</th><th>Value</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>");

				int j = 0;
				for (Iterator<FormField> it2 = forms.get(task).iterator(); it2.hasNext(); ) {
					FormField ff = it2.next();
					out.print("<tr class=\""+(j++%2==0?"odd":"even")+"\">");
					out.print("<td>"+ff.getLabel()+"</td>");
					out.print("<td>"+ff.getName()+"</td>");
					out.print("<td>"+ff.getValue()+"</td>");
					out.print("<td>"+ff.getWidth()+"</td>");
					out.print("<td>"+ff.getHeight()+"</td>");
					
					if (ff instanceof Input) {
						Input input = (Input) ff;
						out.print("<td>Input</td>");
						out.print("<td><i>Type:</i> "+input.getType());
					} else if (ff instanceof TextArea) {
						TextArea textArea = (TextArea) ff;
						out.print("<td>TextArea</td>");
					} else if (ff instanceof Select) {
						Select select = (Select) ff;
						out.print("<td>Select</td>");
						out.print("<td><i>Type:</i> "+select.getType()+", ");
						out.print("<i>Options:</i> "+select.getOptions()+"</td>");
					} else if (ff instanceof Button) {
						Button button = (Button) ff;
						out.print("<td>Button</td>");
						out.print("<td><i>Type:</i> "+button.getType());
					}
					out.println("</tr>");
				}
				
				out.println("</table>");
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			out.println("<h2>Process Image</h2>");
			out.println("<img src=\"/OpenKM"+Config.INSTALL+"/OKMWorkflowViewServletAdmin?id="+pd.getId()+"\" />");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>