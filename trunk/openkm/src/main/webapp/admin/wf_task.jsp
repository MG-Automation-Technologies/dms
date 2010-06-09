<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.api.OKMWorkflow"%>
<%@ page import="com.openkm.util.FormatUtil"%>
<%@ page import="com.openkm.bean.workflow.ProcessDefinition"%>
<%@ page import="com.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="com.openkm.bean.workflow.TaskInstance"%>
<%@ page import="com.openkm.bean.workflow.Transition"%>
<%@ page import="com.openkm.bean.workflow.Comment"%>
<%@ page import="com.openkm.bean.form.FormElement"%>
<%@ page import="com.openkm.bean.form.Input"%>
<%@ page import="com.openkm.bean.form.TextArea"%>
<%@ page import="com.openkm.bean.form.Select"%>
<%@ page import="com.openkm.bean.form.Option"%>
<%@ page import="com.openkm.bean.form.Button"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Workflow Tasks</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String)session.getAttribute("token");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String message = request.getParameter("message");
		String name = request.getParameter("name");
		String value = request.getParameter("value");
						
		if (action != null && !action.equals("")) {
			if (action.equals("addComment") && id != null && !id.equals("") && message != null && !message.equals("")) {
				message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().addTaskInstanceComment(Long.parseLong(id), message);
				response.sendRedirect("wf_task.jsp?id="+id);
			} else if (action.equals("removeVar") && id != null && !id.equals("") && name != null && !name.equals("")) {
				name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().removeTaskInstanceVariable(Long.parseLong(id), name);
				response.sendRedirect("wf_task.jsp?id="+id);
			} else if (action.equals("addVar") && id != null && !id.equals("") && name != null && !name.equals("") && value != null && !value.equals("")) {
				name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().addTaskInstanceVariable(Long.parseLong(id), name, value);
				response.sendRedirect("wf_task.jsp?id="+id);
			} else if (action.equals("endTask") && id != null && !id.equals("") && name != null && !name.equals("")) {
				name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().endTaskInstance(Long.parseLong(id), name);
				response.sendRedirect("wf_task.jsp?id="+id);
			} else {
				response.sendRedirect("wf_task.jsp?id="+id);
			}
		} else {
			TaskInstance ti = OKMWorkflow.getInstance().getTaskInstance(Long.parseLong(id));
			ProcessInstance pi = ti.getProcessInstance();
			ProcessDefinition pd = pi.getProcessDefinition();
			out.println("<table>");
			out.println("<tr>");
			out.println("<td><h1>Task</h1></td><td>");
			out.println(" &nbsp; ");
			out.println("<a href=\""+request.getRequestURL()+"?"+request.getQueryString()+"\"><img src=\"img/action/reload.png\" alt=\"Reload\" title=\"Reload\"/></a>");
			out.println(" &nbsp; ");
			out.println("<a href=\"javascript:history.back(1)\"><img src=\"img/action/back.png\" alt=\"Back\" title=\"Back\"/></a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Task ID</th><th>Name</th><th>Status</th><th>Assigned To</th><th>Token</th><th>Process Instance</th><th>Process</th><th>Created Date</th></tr>");
			out.print("<tr class=\"odd\">");
			out.print("<td>"+ti.getId()+"</td>");
			out.print("<td>"+ti.getName()+"</td>");
			out.print("<td><b>");
			
			if (ti.getEnd() != null) out.print("Ended");
			if (ti.getEnd() != null && ti.isSuspended()) out.print(" (was suspended)");
			if (ti.getEnd() == null && ti.getStart() == null) out.print("Not Started");
			if (ti.getStart() == null && ti.isSuspended()) out.print(" (suspended)");
			if (ti.getEnd() == null && ti.getStart() != null)
				if (ti.isSuspended()) out.print("Suspended"); else out.print("Running");
			
			out.print("</b></td>");
			out.print("<td>"+ti.getActorId()+"</td>");
			out.print("<td><a href=\"wf_token.jsp?id="+ti.getToken().getId()+"\">"+ti.getToken().getId()+"</a></td>");
			out.print("<td><a href=\"wf_procins.jsp?id="+pi.getId()+"\">"+pi.getId()+"</a></td>");
			out.print("<td><a href=\"wf_procdef.jsp?id="+pd.getId()+"\">"+pd.getName()+" v"+pd.getVersion()+"</a></td>");
			out.print("<td>"+(ti.getCreate()!=null?ti.getCreate().getTime():"")+"</td>");
			out.println("</table>");
			
			Map<String, List<FormElement>> forms = OKMWorkflow.getInstance().getProcessDefinitionForms(ti.getProcessInstance().getProcessDefinition().getId());
			List<FormElement> form = forms.get(ti.getName());
			out.println("<h2>Task Form</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Label</th><th>Name</th><th>Value</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>");

			if (form != null) {
				int i = 0;
				for (Iterator<FormElement> it = form.iterator(); it.hasNext(); ) {
					FormElement fe = it.next();
					out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
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
			}
			
			out.println("</table>");
			
			List<Comment> colC = ti.getComments();
			out.println("<h2>Comments</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Actor ID</th><th>Time</th><th>Comment</th></tr>");
			
			int i = 0;
			for (Iterator<Comment> it = colC.iterator(); it.hasNext(); ) {
				Comment c = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+c.getActorId()+"</td>");
				out.print("<td>"+c.getTime().getTime()+"</td>");
				out.print("<td>"+c.getMessage()+"</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			out.println("<br>");
			
			out.println("<form action=\"wf_task.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\"addComment\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\">");
			out.println("<table class=\"form\">");
			out.println("<tr><td>");
			out.println("<textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea>");
			out.println("</td></tr>");
			out.println("<tr><td align=\"right\">");
			out.println("<input type=\"submit\" value=\"Add comment\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
			
			Map<String, Object> vars = ti.getVariables();
			out.println("<h2>Process Variables</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Name</th><th>Value</th><th width=\"25px\">Actions</th></tr>");
			
			i = 0;
			for (Iterator<String> it = vars.keySet().iterator(); it.hasNext(); ) {
				String key = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+key+"</td>");
				out.print("<td>"+FormatUtil.formatObject(vars.get(key))+"</td>");
				out.print("<td>");
				out.print("<a href=\"wf_task.jsp?action=removeVar&id="+ti.getId()+"&name="+key+"\"><img src=\"img/action/delete.png\" alt=\"Remove\" title=\"Remove\"/></a>");
				out.print("</td>");
				out.println("</tr>");
			}
						
			out.println("</table>");
			out.println("<br>");
			
			out.println("<form action=\"wf_task.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\"addVar\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\">");
			out.println("<table class=\"form\">");
			out.println("<tr>");
			out.println("<td>Name <input type=\"text\" name=\"name\"></td>");
			out.println("<td>Value <input type=\"text\" name=\"value\"></td>");
			out.println("</tr>");
			out.println("<tr><td colspan=\"2\" align=\"right\">");
			out.println("<input type=\"submit\" value=\"Add variable\">");
			out.println("</td></tr>");
			out.println("</table>");
			out.println("</form>");
			
			List<Transition> colT = ti.getAvailableTransitions();
			out.println("<h2>Transitions</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>ID</th><th>Name</th><th>Target Node</th><th width=\"25px\">Actions</th></tr>");
			
			i = 0;
			for (Iterator<Transition> it = colT.iterator(); it.hasNext(); ) {
				Transition trans = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+trans.getId()+"</td>");
				out.print("<td>"+trans.getName()+"</td>");
				out.print("<td>"+trans.getTo()+"</td>");
				out.print("<td>");
				
				if (!ti.isSuspended()) {
					out.print("<a href=\"wf_task.jsp?action=endTask&id="+ti.getId()+"&name="+trans.getName()+"\"><img src=\"img/action/end.png\" alt=\"End task\" title=\"End task\"/></a>");
				}
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
