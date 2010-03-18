<%@ page import="es.git.openkm.util.UserActivity"%>
<%@ page import="es.git.openkm.core.Config" %>
<%@ page import="es.git.openkm.core.SessionManager"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="javax.jcr.Session" %>
<%@ page import="javax.jcr.Node" %>
<%@ page import="javax.jcr.Value" %>
<%@ page import="javax.jcr.ValueFactory" %>
<%@ page import="javax.jcr.PropertyType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String) session.getAttribute("token");
		Session jcrSession = SessionManager.getInstance().get(token);
		ValueFactory vf = jcrSession.getValueFactory();
		String path = request.getParameter("path");
		String property = request.getParameter("property");
		String value = request.getParameter("value");
		String type = request.getParameter("type");
		String multiple = request.getParameter("multiple");
		
		if (path != null && property != null && value != null) {
			path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			property = new String(property.getBytes("ISO-8859-1"), "UTF-8");
			value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			int xtype = Integer.parseInt(type);
			boolean xmultiple = Boolean.parseBoolean(multiple);
			
			try {
				Node node = jcrSession.getRootNode().getNode(path.substring(1));
			
				if (xtype == PropertyType.STRING) {
					if (xmultiple) {
						StringTokenizer st = new StringTokenizer(value, ",");
						Value[] values = new Value[st.countTokens()];
						
						for (int i=0 ; st.hasMoreTokens(); i++) {
							values[i] = vf.createValue(st.nextToken().trim());
							response.getWriter().println(" "+values[i].getString());
						}
					
						node.setProperty(property, values);
					} else {
						node.setProperty(property, value);
					}
				} else if (xtype == PropertyType.BOOLEAN) {
					if (xmultiple) {
							
					} else {
						node.setProperty(property, Boolean.parseBoolean(value));
					}
				}
			
				node.save();
				
				// Activity log
				UserActivity.log(jcrSession, "REPOSITORY_SET", node.getPath(), property+"("+type+", "+multiple+")"+" : "+value);
				
				response.sendRedirect("repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8"));
			} catch (Exception e) {
				out.println("<div class=\"error\">"+e.getMessage()+"<div>");
			}
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
