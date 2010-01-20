<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.jcr.Session"%>
<%@page import="com.openkm.core.SessionManager"%>
<%@page import="com.openkm.api.OKMFolder"%>
<%@page import="com.openkm.bean.Folder"%>
<%@page import="com.openkm.util.FileUtils"%>
<%@page import="com.openkm.util.FormatUtil"%>
<%@include file="session.jsp"%>
<% 
  request.setCharacterEncoding("UTF-8");
  String token = (String) session.getAttribute("token");
  Session jcrSession = SessionManager.getInstance().get(token);
  String path = request.getParameter("path");
  OKMFolder okmFolder = OKMFolder.getInstance();
  int i = 0;

  if (path == null || path.equals("")) {
    path = "/okm:root";
  }
  
  path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
  Folder fld = okmFolder.getProperties(token, path);
  
  StringBuilder sb = new StringBuilder();
  for (Iterator<String> it = fld.getSubscriptors().iterator(); it.hasNext();) {
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
  <table class="results" >
    <tr><th colspan="2">Folder <span style="font-weight: normal"><%=fld.getPath()%></span></th></tr>
    <tr><th>Property</th><th>Value</th></tr>
    <tr class="odd"><td><b>UUID</b></td><td><%=fld.getUuid()%></td></tr>
    <tr class="even"><td><b>Name</b></td><td><%=FileUtils.getName(fld.getPath())%></td></tr>
    <tr class="odd"><td><b>Parent</b></td><td><%=FileUtils.getParent(fld.getPath())%></td></tr>
    <tr class="even"><td><b>Created</b></td><td><%=FormatUtil.formatDate(fld.getCreated())%> by <%=fld.getAuthor()%></td></tr>
    <tr class="odd"><td><b>Subscribed</b></td><td><%=fld.isSubscribed()?"Yes":"No"%></td></tr>
  </table>
  <table class="results">
    <tr><th>Subscribed users</th></tr>
    <%=sb%>
  </table>
</body>
</html>