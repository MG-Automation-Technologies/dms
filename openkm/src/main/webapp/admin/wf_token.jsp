<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.api.OKMWorkflow"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="es.git.openkm.bean.workflow.ProcessDefinition"%>
<%@ page import="es.git.openkm.bean.workflow.Transition"%>
<%@ page import="es.git.openkm.bean.workflow.Token"%>
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
  <title>Workflow Tokens</title>
</head>
<body>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String)session.getAttribute("token");
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String transition = request.getParameter("transition");
		String node = request.getParameter("node");
						
		if (action != null && !action.equals("")) {
			if (action.equals("signal") && id != null && !id.equals("")) {
				if (transition != null && !transition.equals("")) {
					transition = new String(transition.getBytes("ISO-8859-1"), "UTF-8");
				} else {
					transition = null;
				}
				OKMWorkflow.getInstance().sendTokenSignal(token, Long.parseLong(id), transition);
				response.sendRedirect("wf_token.jsp?id="+id);
			} else if (action.equals("move") && id != null && !id.equals("") && node != null && !node.equals("")) {
				node = new String(node.getBytes("ISO-8859-1"), "UTF-8");
				OKMWorkflow.getInstance().setTokenNode(token, Long.parseLong(id), node);
				response.sendRedirect("wf_token.jsp?id="+id);
			} else {
				response.sendRedirect("wf_token.jsp?id="+id);
			}
		} else {
			Token t = OKMWorkflow.getInstance().getToken(token, Long.parseLong(id));
			ProcessInstance pi = t.getProcessInstance();
			ProcessDefinition pd = pi.getProcessDefinition();
			out.println("<table>");
			out.println("<tr>");
			out.println("<td><h1>Token</h1></td><td>");
			out.println(" &nbsp; ");
			out.println("<a href=\""+request.getRequestURL()+"?"+request.getQueryString()+"\"><img src=\"img/action/reload.png\" alt=\"Reload\" title=\"Reload\"/></a>");
			out.println(" &nbsp; ");
			out.println("<a href=\"javascript:history.back(1)\"><img src=\"img/action/back.png\" alt=\"Back\" title=\"Back\"/></a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Token ID</th><th>Current Node</th><th>Process Instance</th><th>Process</th><th>Status</th><th>Start Date</th></tr>");
			out.print("<tr class=\"odd\">");
			out.print("<td>"+t.getId()+"</td>");
			out.print("<td>"+t.getNode()+"</td>");
			out.print("<td><a href=\"wf_procins.jsp?id="+pi.getId()+"\">"+pi.getId()+"</a></td>");
			out.print("<td><a href=\"wf_procdef.jsp?id="+pd.getId()+"\">"+pd.getName()+" v"+pd.getVersion()+"</a></td>");
			out.print("<td><b>");
			
			if (t.getEnd() != null) out.print("Ended");
			if (t.getEnd() != null && t.isSuspended()) out.print(" (was suspended)");
			if (t.getEnd() == null)
				if (t.isSuspended()) out.print("Suspended"); else out.print("Running");
			
			out.print("</b></td>");
			out.print("<td>"+(t.getStart()!=null?t.getStart().getTime():"")+"</td>");
			out.println("</tr>");
			out.println("</table>");
						
			Collection<Transition> colT = t.getAvailableTransitions();
			out.println("<h2>Transitions</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>ID</th><th>Name</th><th>Target Node</th><th width=\"25px\">Actions</th></tr>");

			int i = 0;
			for (Iterator<Transition> it = colT.iterator(); it.hasNext(); ) {
				Transition tr = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+tr.getId()+"</td>");
				out.print("<td>"+(tr.getName()!=null?tr.getName():"")+"</td>");
				out.print("<td>"+tr.getTo()+"</td>");
				out.print("<td>");
				
				if (!t.isSuspended()) {
					out.print("<a href=\"wf_token.jsp?action=signal&id="+t.getId()+"&transition="+(tr.getName()!=null?tr.getName():"")+"\"><img src=\"img/action/signal.png\" alt=\"Signal\" title=\"Signal\"/></a>");
				}
				
				out.print("</td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
			
			Collection<String> colN = t.getProcessInstance().getProcessDefinition().getNodes();
			out.println("<h2>Nodes</h2>");
			out.println("<table class=\"results\" width=\"90%\">");
			out.println("<tr><th>Name</th><th width=\"25px\">Actions</th></tr>");

			i = 0;
			for (Iterator<String> it = colN.iterator(); it.hasNext(); ) {
				String nodeName = it.next();
				out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
				out.print("<td>"+(t.getNode().equals(nodeName)?"<b>"+nodeName+"</b>":nodeName)+"</td>");
				out.print("<td>");
				
				if (!t.isSuspended() && !t.getNode().equals(nodeName)) {
					out.print("<a href=\"wf_token.jsp?action=move&id="+t.getId()+"&node="+nodeName+"\"><img src=\"img/action/move.png\" alt=\"Move to this node\" title=\"Move to this node\"/></a>");
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