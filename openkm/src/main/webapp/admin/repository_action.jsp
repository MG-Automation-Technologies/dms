<%@ page import="com.openkm.bean.Folder"%>
<%@ page import="com.openkm.bean.Document"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="com.openkm.util.JCRUtils"%>
<%@ page import="com.openkm.api.OKMScripting"%>
<%@ page import="com.openkm.core.SessionManager" %>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="javax.jcr.NodeIterator"%>
<%@ page import="javax.jcr.Session" %>
<%@ page import="javax.jcr.Node" %>
<%@ page import="javax.jcr.Property" %>
<%@ page import="javax.jcr.Value" %>
<%@ page import="javax.jcr.lock.Lock" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String) session.getAttribute("token");
		String path = request.getParameter("path");
		String action = request.getParameter("action");
		Session jcrSession = null;
				
		if (path != null && action != null) {
			path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			
			try {
				if (Config.SESSION_MANAGER) {
					jcrSession = SessionManager.getInstance().get(token);
				} else {
					jcrSession = JCRUtils.getSession();
				}
				
				Node node = jcrSession.getRootNode().getNode(path.substring(1));
				
				if (action.equals("unlock")) {
					Lock lock = node.getLock();
					SessionManager sm = SessionManager.getInstance();
					String userToken = sm.getTokenByUserId(lock.getLockOwner());
					
					if (userToken != null) {
						Session userSession = sm.get(userToken);
					
						if (userSession != null) {
							node = userSession.getRootNode().getNode(path.substring(1));
						}
					} else {
						Node userConfig = jcrSession.getRootNode().getNode("okm:home/"+lock.getLockOwner()+"/okm:config");
						Property p = userConfig.getProperty("okm:lockTokens");
						Value[] values = p.getValues();
						
						for (int i=0; i<values.length; i++) {
							jcrSession.addLockToken(values[i].getString());
						}
					}
					
					node.unlock();
				} else if (action.equals("checkin")) {
					node.checkin();
				} else if (action.equals("remove_content")) {
					for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
						Node child = ni.nextNode();
						
						if (child.isNodeType(Document.TYPE) || child.isNodeType(Folder.TYPE)) {
							child.remove();
							node.save();
						}
					}
				} else if (action.equals("remove_current")) {
					Node parent = node.getParent();
					node.remove();
					parent.save();
					path = parent.getPath();
				} else if (action.equals("setScript")) {
					OKMScripting.getInstance().setScript(token, path, Config.DEFAULT_SCRIPT);
				} else if (action.equals("removeScript")) {
					OKMScripting.getInstance().removeScript(token, path);
				}
	
				// Activity log
				UserActivity.log(jcrSession, "REPOSITORY_ACTION", node.getPath(), action);
				
				response.sendRedirect("repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8"));
			} catch (Exception e) {
				response.getWriter().println("<pre>");
				e.printStackTrace(response.getWriter());
				response.getWriter().println("</pre>");
			} finally {
				if (!Config.SESSION_MANAGER) {
					JCRUtils.logout(jcrSession);
				}
			}
		}
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
