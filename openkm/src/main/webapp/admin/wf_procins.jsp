<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.api.OKMWorkflow"%>
<%@ page import="es.git.openkm.api.OKMAuth"%>
<%@ page import="es.git.openkm.util.FormatUtil"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="es.git.openkm.bean.workflow.TaskInstance"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessDefinition"%>
<%@ page import="es.git.openkm.bean.workflow.Comment"%>
<%@ page import="es.git.openkm.bean.workflow.Token"%>
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
  <title>Workflow Process Instances Browser</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String)session.getAttribute("token");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String tid = request.getParameter("tid");
		String message = request.getParameter("message");
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		String actor = request.getParameter("actor");
				
		if (action != null && !action.equals("")) {
			if (action.equals("resumeTask") && id != null && !id.equals("") && tid != null && !tid.equals("")) {
				OKMWorkflow.getInstance().resumeTaskInstance(token, Long.parseLong(tid));
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("suspendTask") && id != null && !id.equals("") && tid != null && !tid.equals("")) {
				OKMWorkflow.getInstance().suspendTaskInstance(token, Long.parseLong(tid));
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("start") && id != null && !id.equals("") && tid != null && !tid.equals("")) {
				OKMWorkflow.getInstance().startTaskInstance(token, Long.parseLong(tid));
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("end") && id != null && !id.equals("") && tid != null && !tid.equals("")) {
				OKMWorkflow.getInstance().endTaskInstance(token, Long.parseLong(tid), null);
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("addComment") && id != null && !id.equals("") && tid != null && !tid.equals("") && message != null && !message.equals("")) {
				message = new String(message.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().addTokenComment(token, Long.parseLong(tid), message);
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("removeVar") && id != null && !id.equals("") && name != null && !name.equals("")) {
				name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().removeProcessInstanceVariable(token, Long.parseLong(id), name);
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("addVar") && id != null && !id.equals("") && name != null && !name.equals("") && value != null && !value.equals("")) {
				name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().addProcessInstanceVariable(token, Long.parseLong(id), name, value);
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("resumeToken") && id != null && !id.equals("") && tid != null && !tid.equals("")) {
				OKMWorkflow.getInstance().resumeToken(token, Long.parseLong(tid));
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("suspendToken") && id != null && !id.equals("") && tid != null && !tid.equals("")) {
				OKMWorkflow.getInstance().suspendToken(token, Long.parseLong(tid));
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else if (action.equals("setActor") && id != null && !id.equals("") && tid != null && !tid.equals("") && actor != null && !actor.equals("")) {
				actor = new String(actor.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().setTaskInstanceActorId(token, Long.parseLong(tid), actor);
				response.sendRedirect("wf_procins.jsp?id="+id);
			} else {
				response.sendRedirect("wf_procins.jsp?id="+id);
			}
		} else {
			ProcessInstance pi = OKMWorkflow.getInstance().getProcessInstance(token, Long.parseLong(id));
			out.println("<table>");
			out.println("<tr>");
			out.println("<td><h1>Process Instance</h1></td><td><a href=\"\">Reload</a> - <a href=\"javascript:history.back(1)\">Back</a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Instance ID</th><th>Key</th><th>Process</th><th>Status</th><th>Start Date</th><th>End Date</th></tr>");
			out.print("<tr class=\"odd\"><td>"+pi.getId()+"</td><td>"+(pi.getKey()!=null?pi.getKey():"")+"</td>");
			ProcessDefinition pd = pi.getProcessDefinition();
			out.print("<td><a href=\"wf_procdef.jsp?id="+pd.getId()+"\">"+pd.getName()+" v"+pd.getVersion()+"</a></td>");
			out.print("<td><b>");
			
			if (pi.getEnd() != null && pi.isSuspended()) out.print("Ended (was suspended)");
			else if (pi.getEnd() != null && !pi.isSuspended()) out.print("Ended");
			else if (pi.getEnd() == null && pi.isSuspended()) out.print("Suspended");
			else if (pi.getEnd() == null && !pi.isSuspended()) out.print("Running");
			
			out.print("</b></td>");
			out.println("<td>"+(pi.getStart()!=null?pi.getStart().getTime():"")+"</td><td>"+(pi.getEnd()!=null?pi.getEnd().getTime():"")+"</td></tr>");
			out.println("</table>");

			Collection<TaskInstance> colTi = OKMWorkflow.getInstance().findTaskInstances(token, Long.parseLong(id));
			out.println("<h2>Tasks Instances</h2>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>ID</th><th>Name</th><th>Pooled Actors</th><th>Assigned To</th><th>Status</th><th>Start Date</th><th>End Date</th><th>Actions</th></tr>");

			int i = 0;
			for (Iterator<TaskInstance> it = colTi.iterator(); it.hasNext(); ) {
				TaskInstance ti = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+ti.getId()+"</td>");
				out.print("<td>"+ti.getName()+"</td>");
				out.print("<td>"+(ti.getPooledActors().isEmpty()?"":ti.getPooledActors())+"</td>");
				out.print("<td>");
				
				if (ti.getEnd() != null) {
					out.print(ti.getActorId());
				} else {
					out.print("<form id=\"setActor\" action=\"wf_procins.jsp\">");
					out.print("<input type=\"hidden\" name=\"action\" value=\"setActor\">");
					out.print("<input type=\"hidden\" name=\"id\" value=\""+pi.getId()+"\">");
					out.print("<input type=\"hidden\" name=\"tid\" value=\""+ti.getId()+"\">");
					out.print("<select name=\"actor\" onchange=\" document.getElementById('setActor').submit()\">");
					out.print("<option>-</option>");
					Collection<String> colU = OKMAuth.getInstance().getUsers(token);
					
					for (Iterator<String> itU = colU.iterator(); itU.hasNext(); ) {
						String user = itU.next();
						if (user.equals(ti.getActorId())) {
							out.print("<option selected>"+user+"</option>");
						} else {
							out.print("<option>"+user+"</option>");
						}
					}
					
					out.print("</select>");
					out.print("</form>");
				}
				
				out.print("</td>");
				out.print("<td><b>");
				
				if (ti.getEnd() != null) out.print("Ended");
				if (ti.getEnd() != null && ti.isSuspended()) out.print(" (was suspended)");
				if (ti.getEnd() == null && ti.getStart() == null) out.print("Not Started");
				if (ti.getStart() == null && ti.isSuspended()) out.print(" (suspended)");
				if (ti.getEnd() == null && ti.getStart() != null)
					if (ti.isSuspended()) out.print("Suspended"); else out.print("Running");
								
				out.print("</b></td>");
				out.print("<td>"+(ti.getStart()!=null?ti.getStart().getTime():"")+"</td>");
				out.print("<td>"+(ti.getEnd()!=null?ti.getEnd().getTime():"")+"</td>");
				out.print("<td>");
				out.print("<a href=\"wf_task.jsp?id="+ti.getId()+"\">Examine</a>");
								
				if (!ti.isSuspended() && ti.getEnd() == null) {
					out.print(" - ");
					out.print("<a href=\"wf_procins.jsp?action=suspendTask&id="+pi.getId()+"&tid="+ti.getId()+"\">Suspend</a>");
				}

				if (ti.isSuspended() && ti.getEnd() == null) {
					out.print(" - ");
					out.print("<a href=\"wf_procins.jsp?action=resumeTask&id="+pi.getId()+"&tid="+ti.getId()+"\">Resume</a>");
				}
				
				if (ti.getStart() == null && ti.getEnd() == null) {
					out.print(" - ");
					out.print("<a href=\"wf_procins.jsp?action=start&id="+pi.getId()+"&tid="+ti.getId()+"\">Start</a>");
				}
				
				if (ti.getStart() != null && ti.getEnd() == null && !ti.isSuspended()) {
					out.print(" - ");
					out.print("<a href=\"wf_procins.jsp?action=end&id="+pi.getId()+"&tid="+ti.getId()+"\">End</a>");
				}
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			Collection<Comment> colC = pi.getRootToken().getComments();
			out.println("<h2>Comments</h2>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Actor ID</th><th>Time</th><th>Comment</th></tr>");
			
			i = 0;
			for (Iterator<Comment> it = colC.iterator(); it.hasNext(); ) {
				Comment c = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+c.getActorId()+"</td>");
				out.print("<td>"+c.getTime().getTime()+"</td>");
				out.print("<td>"+c.getMessage()+"</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			out.println("<table align=\"center\">");
			out.println("<tr><td>");
			out.println("<form action=\"wf_procins.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\"addComment\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\">");
			out.println("<input type=\"hidden\" name=\"tid\" value=\""+pi.getRootToken().getId()+"\">");
			out.println("<textarea name=\"message\" cols=\"50\" rows=\"5\"></textarea><br>");
			out.println("<input type=\"submit\" value=\"Add comment\">");
			out.println("</form>");
			out.println("</td></tr>");
			out.println("</table>");
			
			Collection<Token> colT = pi.getAllTokens();
			out.println("<h2>Tokens</h2>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Token ID</th><th>Parent</th><th>Node</th><th>Status</th><th>Start Date</th><th>End Date</th><th>Actions</th></tr>");
			
			i = 0;
			for (Iterator<Token> it = colT.iterator(); it.hasNext(); ) {
				Token t = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+t.getId()+"</td>");
				out.print("<td>"+(t.getParent()!=null?t.getParent():"(no parent)")+"</td>");
				out.print("<td>"+t.getNode()+"</td>");
				out.print("<td><b>");
				
				if (t.getEnd() != null) out.print("Ended");
				if (t.getEnd() != null && t.isSuspended()) out.print(" (was suspended)");
				if (t.getEnd() == null)
					if (t.isSuspended()) out.print("Suspended"); else out.print("Running");
				
				out.print("</b></td>");
				out.print("<td>"+(t.getStart()!=null?t.getStart().getTime():"")+"</td>");
				out.print("<td>"+(t.getEnd()!=null?t.getEnd().getTime():"")+"</td>");
				out.print("<td>");
				out.print("<a href=\"wf_token.jsp?id="+t.getId()+"\">Examine</a>");
				
				if (t.getEnd() == null) {
					out.print(" - ");
					out.print("<a href=\"wf_procdef.jsp?action=end&id="+pd.getId()+"&iid="+pi.getId()+"\">End</a>");
				}
				
				out.print(" - ");

				if (t.isSuspended()) {
					out.print("<a href=\"wf_procins.jsp?action=resumeToken&id="+pi.getId()+"&tid="+t.getId()+"\">Resume</a>");
				} else {
					out.print("<a href=\"wf_procins.jsp?action=suspendToken&id="+pi.getId()+"&tid="+t.getId()+"\">Suspend</a>");
				}

				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			Map<String, Object> vars = pi.getVariables();
			out.println("<h2>Process Variables</h2>");
			out.println("<table class=\"results\">");
			out.println("<tr><th>Name</th><th>Value</th><th>Actions</th></tr>");
			
			i = 0;
			for (Iterator<String> it = vars.keySet().iterator(); it.hasNext(); ) {
				String key = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+key+"</td>");
				out.print("<td>"+FormatUtil.formatObject(vars.get(key))+"</td>");
				out.print("<td>");
				out.print("<a href=\"wf_procins.jsp?action=removeVar&id="+pi.getId()+"&name="+key+"\">Remove</a>");
				out.print("</td>");
				out.println("</tr>");
			}
						
			out.println("</table>");
			
			out.println("<table align=\"center\">");
			out.println("<tr><td>");
			out.println("<form action=\"wf_procins.jsp\">");
			out.println("<input type=\"hidden\" name=\"action\" value=\"addVar\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\""+id+"\">");
			out.println("Name <input type=\"text\" name=\"name\">");
			out.println("Value <input type=\"text\" name=\"value\">");
			out.println("<input type=\"submit\" value=\"Add variable\">");
			out.println("</form>");
			out.println("</td></tr>");
			out.println("</table>");
			
			out.println("<h2>Process Image</h2>");
			out.println("<img src=\"/OpenKM"+Config.INSTALL+"/OKMWorkflowViewServletAdmin?id="+pd.getId()+"&node="+pi.getRootToken().getNode()+"\" />");
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>