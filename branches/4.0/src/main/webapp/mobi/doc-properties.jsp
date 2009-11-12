<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.jcr.Session"%>
<%@page import="es.git.openkm.core.SessionManager"%>
<%@page import="es.git.openkm.api.OKMDocument"%>
<%@page import="es.git.openkm.bean.Document"%>
<%@page import="es.git.openkm.util.FileUtils"%>
<%@page import="es.git.openkm.util.FormatUtil"%>
<%@include file="session.jsp"%>
<% 
  request.setCharacterEncoding("UTF-8");
  String token = (String) session.getAttribute("token");
  Session jcrSession = SessionManager.getInstance().get(token);
  String path = request.getParameter("path");
  OKMDocument okmDocument = OKMDocument.getInstance();
  int i = 0;

  if (path == null || path.equals("")) {
    path = "/okm:root";
  }
  
  path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
  Document doc = okmDocument.getProperties(token, path);
  String status = "Available";
  
  if (doc.isCheckedOut()) {
	  status = "Checkout by "+doc.getLockInfo().getOwner();
  } else if (doc.isLocked()) {
	  status = "Locked by "+doc.getLockInfo().getOwner();
  }
  
  StringBuilder sb = new StringBuilder();
  for (Iterator<String> it = doc.getSubscriptors().iterator(); it.hasNext();) {
    sb.append("<tr class=\"");
    sb.append(i++%2==0?"odd":"even");
    sb.append("\"><td>");
    sb.append(it.next());
    sb.append("</td></tr>");
  }
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>OpenKM Mobile</title>
</head>
<body>
  <table class="results">
    <tr><th colspan="2">Document <span style="font-weight: normal"><%=doc.getPath()%></span></th></tr>
    <tr><th>Property</th><th>Value</th></tr>
    <tr class="odd"><td><b>UUID</b></td><td><%=doc.getUuid()%></td></tr>
    <tr class="even"><td><b>Name</b></td><td><%=FileUtils.getName(doc.getPath())%></td></tr>
    <tr class="odd"><td><b>Folder</b></td><td><%=FileUtils.getParent(doc.getPath())%></td></tr>
    <tr class="even"><td><b>Size</b></td><td><%=FormatUtil.formatSize(doc.getActualVersion().getSize())%></td></tr>
    <tr class="odd"><td><b>Created</b></td><td><%=FormatUtil.formatDate(doc.getActualVersion().getCreated())%> by <%=doc.getActualVersion().getAuthor()%></td></tr>
    <tr class="even"><td><b>Modified</b></td><td><%=FormatUtil.formatDate(doc.getLastModified())%> by <%=doc.getAuthor()%></td></tr>
    <tr class="odd"><td><b>MIME</b></td><td><%=doc.getMimeType()%></td></tr>
    <tr class="even"><td><b>Keywords</b></td><td><%=doc.getKeywords()%></td></tr>
    <tr class="odd"><td><b>Status</b></td><td><%=status%></td></tr>
    <tr class="even"><td><b>Subscribed</b></td><td><%=doc.isSubscribed()?"Yes":"No"%></td></tr>
  </table>
  <table class="results">
    <tr><th>Subscribed users</th></tr>
    <%=sb%>
  </table>
</body>
</html>