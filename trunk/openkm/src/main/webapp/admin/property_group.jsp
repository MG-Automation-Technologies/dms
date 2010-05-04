<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="javax.jcr.Session" %>
<%@ page import="com.openkm.util.FormUtils"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="com.openkm.api.OKMPropertyGroup"%>
<%@ page import="com.openkm.bean.form.FormElement"%>
<%@ page import="com.openkm.bean.PropertyGroup"%>
<%@ page import="com.openkm.bean.form.Input"%>
<%@ page import="com.openkm.bean.form.Button"%>
<%@ page import="com.openkm.bean.form.Select"%>
<%@ page import="com.openkm.bean.form.Option"%>
<%@ page import="com.openkm.bean.form.TextArea"%>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.core.SessionManager"%>
<%@ page import="com.openkm.module.direct.DirectRepositoryModule" %>
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
		out.println("<table class=\"results\" width=\"80%\">");
		OKMPropertyGroup okmPG = OKMPropertyGroup.getInstance();
		FormUtils.resetPropertyGroupsForms();
		
		try {
			Collection<PropertyGroup> pGroups = okmPG.getAllGroups(token);
			
			if (pGroups.isEmpty()) {
				out.println("<tr><th colspan=\"3\">Property group label</th><th colspan=\"4\">Property group name</th></tr>");
				out.println("<tr><th>Label</th><th>Name</th><th>Value</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>");
			}
			
			for (Iterator<PropertyGroup> itGrp = pGroups.iterator(); itGrp.hasNext(); ) {
				PropertyGroup pGroup = itGrp.next();
				out.println("<tr><th colspan=\"3\">Property group label</th><th colspan=\"4\">Property group name</th></tr>");
				out.println("<tr class=\"fuzzy\">");
				out.println("<td colspan=\"3\" align=\"center\"><b>"+pGroup.getLabel()+"</b></td>");
				out.println("<td colspan=\"4\" align=\"center\"><b>"+pGroup.getName()+"</b></td>");
				out.println("</tr>");
				
				out.println("<tr><th>Label</th><th>Name</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>");
				Collection<FormElement> mData = okmPG.getPropertyGroupForm(token, pGroup.getName());
				int i = 0;
				for (Iterator<FormElement> itMD = mData.iterator(); itMD.hasNext(); ) {
					FormElement fe = itMD.next();
					out.print("<tr class=\""+(i++%2==0?"odd":"even")+"\">");
					out.print("<td>"+fe.getLabel()+"</td>");
					out.print("<td>"+fe.getName()+"</td>");
					out.print("<td>"+fe.getWidth()+"</td>");
					out.print("<td>"+fe.getHeight()+"</td>");
					
					if (fe instanceof Input) {
						Input input = (Input) fe;
						out.print("<td>Input</td>");
						out.print("<td>");
						out.print("<i>Type:</i> "+input.getType()+"<br/>");
						out.print("<i>Value:</i> "+input.getValue());
						out.print("</td>");
					} else if (fe instanceof TextArea) {
						TextArea textArea = (TextArea) fe;
						out.print("<td>TextArea</td>");
						out.print("<td>");
						out.print("<i>Value:</i> "+textArea.getValue());
						out.print("</td>");
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
		} catch (Exception e) {
			out.println("<div class=\"error\">"+e.getMessage()+"<div>");
		}
		
		out.println("</table>");
		out.println("<hr>");
		out.println("<h2><center>Register property group</center></h2>");
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
				// Check xml property groups definition
				FormUtils.resetPropertyGroupsForms();
				FormUtils.parsePropertyGroupsForms();
				
				// If it is ok, register it
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
