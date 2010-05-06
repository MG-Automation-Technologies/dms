<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.api.OKMWorkflow"%>
<%@ page import="com.openkm.bean.form.FormElement"%>
<%@ page import="com.openkm.bean.form.Input"%>
<%@ page import="com.openkm.bean.form.TextArea"%>
<%@ page import="com.openkm.bean.form.Select"%>
<%@ page import="com.openkm.bean.form.Option"%>
<%@ page import="com.openkm.bean.form.Button"%>
<%@ page import="com.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="com.openkm.bean.workflow.ProcessDefinition"%>
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
			out.println("<td><h1>Process Definition</h1></td><td>");
			out.println(" &nbsp; ");
			out.println("<a href=\""+request.getRequestURL()+"?"+request.getQueryString()+"\"><img src=\"img/action/reload.png\" alt=\"Reload\" title=\"Reload\"/></a>");
			out.println(" &nbsp; ");
			out.println("<a href=\"javascript:history.back(1)\"><img src=\"img/action/back.png\" alt=\"Back\" title=\"Back\"/></a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Process ID</th><th>Name</th><th>Description</th><th>Version</th></tr>");
			out.println("<tr class=\"odd\"><td>"+pd.getId()+"</td><td>"+pd.getName()+"</td><td>"+(pd.getDescription()!=null?pd.getDescription():"")+"</td><td>"+pd.getVersion()+"</td></tr>");
			out.println("</table>");
						
			Collection<ProcessInstance> col = OKMWorkflow.getInstance().findProcessInstances(token, Long.parseLong(id));
			out.println("<h2>Process Instances</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Instance ID</th><th>Key</th><th>Status</th><th>Start Date</th><th>End Date</th><th width=\"100px\">Actions</th></tr>");

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
				out.print("<a href=\"wf_procins.jsp?id="+pi.getId()+"\"><img src=\"img/action/examine.png\" alt=\"Examine\" title=\"Examine\"/></a>");
				out.print(" &nbsp; ");
				out.print("<a href=\"wf_procdef.jsp?action=delete&id="+pd.getId()+"&iid="+pi.getId()+"\"><img src=\"img/action/delete.png\" alt=\"Delete\" title=\"Delete\"/></a>");
				
				if (pi.getEnd() == null) {
					out.print(" &nbsp; ");
					out.print("<a href=\"wf_procdef.jsp?action=end&id="+pd.getId()+"&iid="+pi.getId()+"\"><img src=\"img/action/end.png\" alt=\"End\" title=\"End\"/></a>");
				}
				
				out.print(" &nbsp; ");
				
				if (pi.isSuspended()) {
					out.print("<a href=\"wf_procdef.jsp?action=resume&id="+pd.getId()+"&iid="+pi.getId()+"\"><img src=\"img/action/resume.png\" alt=\"Resume\" title=\"Resume\"/></a>");
				} else {
					out.print("<a href=\"wf_procdef.jsp?action=suspend&id="+pd.getId()+"&iid="+pi.getId()+"\"><img src=\"img/action/suspend.png\" alt=\"Suspend\" title=\"Suspend\"/></a>");
				}
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			Map<String, Collection<FormElement>> forms = OKMWorkflow.getInstance().getProcessDefinitionForms(token, Long.parseLong(id));
			out.println("<h2>Forms</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Task</th><th>Form</th></tr>");

			i = 0;
			for (Iterator<String> it = forms.keySet().iterator(); it.hasNext(); ) {
				String task = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+task+"</td>");
				out.print("<td>");
				
				out.println("<table class=\"results\" width=\"100%\">");
				out.println("<tr><th>Label</th><th>Name</th><th>Value</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>");

				int j = 0;
				for (Iterator<FormElement> it2 = forms.get(task).iterator(); it2.hasNext(); ) {
					FormElement fe = it2.next();
					out.print("<tr class=\""+(j++%2==0?"odd":"even")+"\">");
					out.print("<td>"+fe.getLabel()+"</td>");
					out.print("<td>"+fe.getName()+"</td>");
					//out.print("<td>"+fe.getValue()+"</td>");
					out.print("<td>"+fe.getWidth()+"</td>");
					out.print("<td>"+fe.getHeight()+"</td>");
					
					if (fe instanceof Input) {
						Input input = (Input) fe;
						out.print("<td>Input</td>");
						out.print("<td><i>Type:</i> "+input.getType()+"</td>");
					} else if (fe instanceof TextArea) {
						TextArea textArea = (TextArea) fe;
						out.print("<td>TextArea</td>");
						out.print("<td></td>");
					} else if (fe instanceof Select) {
						Select select = (Select) fe;
						out.print("<td>Select</td>");
						out.print("<td><i>Type:</i> "+select.getType()+", ");
						out.print("<i>Options:</i><ul>");
						for (Iterator<Option> itOpt = select.getOptions().iterator(); itOpt.hasNext(); ) {
							Option opt = itOpt.next();
							out.print("<li><i>Label:</i> "+opt.getLabel()+", <i>Value:</i> "+opt.getValue()+"</li>");
						}
						out.print("</ul></td>");
					} else if (fe instanceof Button) {
						Button button = (Button) fe;
						out.print("<td>Button</td>");
						out.print("<td><i>Type:</i> "+button.getType()+"</td>");
					}
					out.println("</tr>");
				}
				
				out.println("</table>");
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			out.println("<h2>Process Image</h2>");
			out.println("<center>");
			out.println("<img src=\"/OpenKM"+Config.INSTALL+"/WorkflowGraph?id="+pd.getId()+"\" />");
			out.println("</center>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
