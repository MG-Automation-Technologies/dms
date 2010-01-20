<%@ page import="com.openkm.module.direct.DirectFolderModule" %>
<%@ page import="com.openkm.api.OKMFolder"%>
<%@ page import="com.openkm.bean.ContentInfo" %>
<%@ page import="com.openkm.bean.Document" %>
<%@ page import="com.openkm.bean.Folder" %>
<%@ page import="com.openkm.bean.Scripting"%>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.core.SessionManager"%>
<%@ page import="com.openkm.util.UserActivity"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="javax.jcr.Session" %>
<%@ page import="javax.jcr.Value" %>
<%@ page import="javax.jcr.Node" %>
<%@ page import="javax.jcr.NodeIterator" %>
<%@ page import="javax.jcr.Property" %>
<%@ page import="javax.jcr.PropertyIterator" %>
<%@ page import="javax.jcr.PropertyType" %>
<%@ page import="javax.jcr.RepositoryException"%>
<%@ page import="javax.jcr.nodetype.NodeType" %>
<%@ page import="javax.jcr.nodetype.PropertyDefinition" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico"/>
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Repository View</title>
</head>
<body>
<%!
  public String formatSize(long size) {
	DecimalFormat sf = new DecimalFormat("#0.0");
	String str;
		
	if (size / 1024 < 1) {
		str = size + " Bytes";
	} else if (size / 1048576 < 1) {
		str = sf.format(size / 1024.0) + " KBytes";
	} else if (size / 1073741824 < 1) {
		str = sf.format(size / 1048576.0) + " MBytes";
	} else if (size /  1099511627776L < 1) {
		str = sf.format(size / 1073741824.0) + " GBytes";
	} else {
		str = "BIG";
	}
			
	return str;
  }

  public String createPath(String path) throws Exception {
    int idx = path.lastIndexOf('/');
    
    if (idx > 0) {
      String name = path.substring(idx+1);
      String parent = path.substring(0, idx);
      return createPath(parent)+" / <a href=\"repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8")+"\">"+name+"</a>";
    } else {
      if (!path.substring(1).equals("")) {
        return "/ <a href=\"repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8")+"\">"+path.substring(1)+"</a>";
      } else {
        return "/";
      }
    }
  }
%>
<% 
	if (request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)) {
		request.setCharacterEncoding("UTF-8");
		String token = (String) session.getAttribute("token");
		Session jcrSession = SessionManager.getInstance().get(token);
		String path = request.getParameter("path");
		String uuid = request.getParameter("uuid");
		Node node = null;
		
		if (request.getParameter("stats") != null) {
			if (request.getParameter("stats").equals("0")) {
				session.removeAttribute("stats");
			} else {
				session.setAttribute("stats", true);
			}
		}
		
		if (path != null && !path.equals("")) {
			path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
			node = jcrSession.getRootNode().getNode(path.substring(1));
		} else if (uuid != null && !uuid.equals("")) {
			node = jcrSession.getNodeByUUID(uuid);
			path = node.getPath();
		} else {
			node = jcrSession.getRootNode();
		}
		
		out.println("<h1>Repository view</h1>");
		out.println("<h2>Info");

		if (node.getDepth() > 0) {
			out.println("<span style=\"font-size: 10px;\"> - (<a href=\"repository_action.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&action=remove_content\">Remove contents</a>)</span>");
			out.println("<span style=\"font-size: 10px;\"> - (<a href=\"repository_action.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&action=remove_current\">Remove current</a>)</span>");
		}
		
		if (node.isLocked()) {
			if (node.holdsLock()) {
				out.println("<span style=\"font-size: 10px;\"> - Locked (<a href=\"repository_action.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&action=unlock\">Unlock</a>)</span>");
			} else {
				out.println("<span style=\"font-size: 10px;\"> - Locked</span>");
			}
		}
	
		if (node.isNodeType(Document.CONTENT_TYPE) && node.isCheckedOut()) {
			out.println("<span style=\"font-size: 10px;\"> - CheckedOut (<a href=\"repository_action.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&action=checkin\">Checkin</a>)</span>");
		}
		
		out.println("</h2>");
		out.println(" - <b>Path</b>: "+createPath(node.getPath())+"<br>");
		out.println(" - <b>Depth</b>: "+node.getDepth()+"<br>");
		out.println(" - <b>Type</b>: "+node.getPrimaryNodeType().getName()+"<br>");
			
		if (node.getDepth() == 0) {
			out.println(" - <b>Parent</b>:<br>");
		} else if (node.getDepth() == 1) {
			out.println(" - <b>Parent</b>: <a href=\"repository_view.jsp?path=\">ROOT</a><br>");
		} else {
			out.println(" - <b>Parent</b>: <a href=\"repository_view.jsp?path="+URLEncoder.encode(node.getParent().getPath(), "UTF-8")+"\">"+node.getParent().getName()+"</a><br>");
		}
	
		if (node.isNodeType(Folder.TYPE)) {
			out.println("<h2>Statistics");
			if (session.getAttribute("stats") != null) {
				out.println("<span style=\"font-size: 10px;\"> - (<a href=\"repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&stats=0\">Deactivate</a>)</span>");
				out.println("</h2>");
				ContentInfo ci = OKMFolder.getInstance().getContentInfo(token, node.getPath());
				out.println(" - <b>Size</b>: "+formatSize(ci.getSize())+"<br>");
				out.println(" - <b>Folders</b>: "+ci.getFolders()+"<br>");
				out.println(" - <b>Documents</b>: "+ci.getDocuments()+"<br>");
				out.println(" - <b>Mails</b>: "+ci.getMails()+"<br>");
			} else  {
				out.println("<span style=\"font-size: 10px;\"> - (<a href=\"repository_view.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&stats=1\">Activate</a>)</span>");
				out.println("</h2>");
			}
		}
		
		out.println("<h2>Mixin");
		
		if (node.isNodeType(Document.TYPE)) {
			if (node.isNodeType(Scripting.TYPE)) {
				out.println("<span style=\"font-size: 10px;\"> - <a href=\"repository_action.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&action=removeScript\">Remove Script</a></span>");
			} else {
				out.println("<span style=\"font-size: 10px;\"> - <a href=\"repository_action.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&action=setScript\">Set Script</a></span>");
			}
		}

		out.println("</h2>");
		NodeType []nt = node.getMixinNodeTypes();

		if (nt.length == 0) {
			out.println(" + <b>(none)</b><br>");
		} else {
			for (int i=0; i<nt.length; i++) {
				out.println(" + <b>"+nt[i].getName()+"</b><br>");
			}
		}
		
		out.println("<h2>Properties</h2>");
		
		String value = "";
		
		for (PropertyIterator pi = node.getProperties(); pi.hasNext(); ) {
			Property p = pi.nextProperty();
			PropertyDefinition pd = p.getDefinition();
			Value[] v = null;
			out.print(" + <b>"+p.getName()+"</b> : ");
			
			if (pd.isMultiple()) {
				v = p.getValues();
			}
			
			switch (pd.getRequiredType()) {
				case PropertyType.STRING:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE STRING) ");
						value = "";
						
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getString()+", ");
							value += v[i].getString()+", ";
						}
						
						if (v.length > 0) {
							value += v[v.length-1].getString();
							out.println(v[v.length-1].getString());
						} else {
							out.println();
						}
					} else {
						value = p.getString();
						out.println("(STRING) "+p.getString());
					}
					
					if (pd.isProtected()) {
						out.println(" - PROTECTED<br>");
					} else if (node.isLocked()) {
						out.println("<br>");
					} else {
						out.println(" - <a href=\"repository_edit.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&property="+p.getName()+"&type="+PropertyType.STRING+"&multiple="+pd.isMultiple()+"&value="+URLEncoder.encode(value, "UTF-8")+"\">Edit</a><br>");
					}
					break;
				case PropertyType.BINARY:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE BINARY) ");
						
						for (int i=0; i<v.length-1; i++) {
							out.println("DATA, ");
						}
						
						if (v.length > 0) {
							out.println("DATA<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(BINARY) DATA<br>");
					}
					break;
				case PropertyType.DATE:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE DATE) ");
						
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getDate().getTime()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getDate().getTime()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(DATE) "+p.getDate().getTime()+"<br>");
					}
					break;
				case PropertyType.DOUBLE:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE DOUBLE) ");
	
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getDouble()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getDouble()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(DOUBLE) "+p.getDouble()+"<br>");
					}
					break;
				case PropertyType.LONG:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE LONG) ");
	
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getLong()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getLong()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(LONG) "+p.getLong()+"<br>");
					}
					break;
				case PropertyType.BOOLEAN:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE BOOLEAN) ");
						value = "";
	
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getBoolean()+", ");
							value += v[i].getBoolean()+", ";
						}
						
						if (v.length > 0) {
							value += v[v.length-1].getBoolean();
							out.println(v[v.length-1].getBoolean());
						} else {
							out.println();
						}
					} else {
						value = Boolean.toString(p.getBoolean());
						out.println("(BOOLEAN) "+p.getBoolean());
					}
					
					if (pd.isProtected()) {
						out.println(" - PROTECTED<br>");
					} else if (node.isLocked()) {
						out.println("<br>");
					} else {
						out.println(" - <a href=\"repository_edit.jsp?path="+URLEncoder.encode(path, "UTF-8")+"&property="+p.getName()+"&type="+PropertyType.BOOLEAN+"&multiple="+pd.isMultiple()+"&value="+URLEncoder.encode(value, "UTF-8")+"\">Edit</a><br>");
					}
					break;
				case PropertyType.NAME:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE NAME) ");
	
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getString()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(NAME) "+p.getString()+"<br>");
					}
					break;
				case PropertyType.PATH:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE PATH) ");
	
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getString()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(PATH) "+p.getString()+"<br>");
					}
					break;
				case PropertyType.REFERENCE:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE REFERENCE) ");
						
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getString()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(REFERENCE) <a href=\"repository_view.jsp?uuid="+p.getString()+"\">"+p.getString()+"</a><br>");
					}
					break;
				case PropertyType.UNDEFINED:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE UNDEFINED) ");
	
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getString()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(UNDEFINED) "+((p.getString().length() < 100)?p.getString():"...")+"<br>");
					}
					break;
				default:
					if (pd.isMultiple()) {
						out.print("(MULTIPLE UNKNOWN) ");
						
						for (int i=0; i<v.length-1; i++) {
							out.println(v[i].getString()+", ");
						}
						
						if (v.length > 0) {
							out.println(v[v.length-1].getString()+"<br>");
						} else {
							out.println("<br>");
						}
					} else {
						out.println("(UNKNOWN) "+p.getType()+"<br>");
					}
					break;
			}
		}
		
		out.println("<h2>Childs</h2>");
		out.println("<ul>");
		
		for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
			Node child = ni.nextNode();
			
			if (child.isNodeType(Document.TYPE)) {
				out.println("<li>["+child.getPrimaryNodeType().getName().toUpperCase()+"] <a href=\"repository_view.jsp?path="+
						URLEncoder.encode(child.getPath(), "UTF-8")+"\">"+
						child.getName()+"</a>"+
						(child.isLocked()?" - Locked ":"")+"</li>");
			} else if (child.isNodeType(Document.CONTENT_TYPE)) {
				out.println("<li>["+child.getPrimaryNodeType().getName().toUpperCase()+"] <a href=\"repository_view.jsp?path="+
						URLEncoder.encode(child.getPath(), "UTF-8")+"\">"+
						child.getName()+"</a>"+
						(child.isLocked()?" - Locked ":"")+
						(child.isCheckedOut()?" - CheckedOut ":"")+"</li>");
			} else if (child.isNodeType(Folder.TYPE)) {
				out.println("<li>["+child.getPrimaryNodeType().getName().toUpperCase()+"] <a href=\"repository_view.jsp?path="+
						URLEncoder.encode(child.getPath(), "UTF-8")+"\">"+
						child.getName()+"</a></li>");
			} else {
				out.println("<li>["+child.getPrimaryNodeType().getName().toUpperCase()+"] <a href=\"repository_view.jsp?path="+
						URLEncoder.encode(child.getPath(), "UTF-8")+"\">"+
						child.getName()+"</a></li>");
			}
		}
		
		out.println("</ul>");
		
		// Activity log
		UserActivity.log(jcrSession, "REPOSITORY_VIEW", node.getPath(), null);
	} else {
		out.println("<div class=\"error\"><h3>Only admin users allowed</h3></div>");
	}
%>
</body>
</html>
