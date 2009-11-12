<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="javax.jcr.Session"%>
<%@page import="es.git.openkm.core.SessionManager"%>
<%@page import="es.git.openkm.api.OKMDocument"%>
<%@page import="es.git.openkm.api.OKMFolder"%>
<%@page import="es.git.openkm.bean.Folder"%>
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
  OKMFolder okmFolder = OKMFolder.getInstance();
  StringBuilder sb = new StringBuilder();
  String ctxTaxonomy = "/okm:root";
  String ctxTemplates = "/okm:templates";
  String ctxPersonal = "/okm:home/"+jcrSession.getUserID()+"/okm:personal";
  String ctxMail = "/okm:home/"+jcrSession.getUserID()+"/okm:mail";
  String ctxTrash = "/okm:home/"+jcrSession.getUserID()+"/okm:trash";
  int i = 0;

  if (path == null || path.equals("")) {
	  path = "/okm:root";
  }
  
  path = new String(path.getBytes("ISO-8859-1"), "UTF-8");
    
  for (Iterator<Folder> it = okmFolder.getChilds(token, path).iterator(); it.hasNext(); ) {
    Folder fld = it.next();
    sb.append("<tr class=\"");
    sb.append(i++%2==0?"odd":"even");
    sb.append("\"><td width=\"18px\">");
    sb.append("<img src=\""+request.getContextPath()+"/es.git.openkm.frontend.Main/img/");
    sb.append(fld.getHasChilds()?"menuitem_childs.gif":"menuitem_empty.gif");
    sb.append("\">");
    sb.append("</td><td width=\"100%\" onclick=\"document.location='browse.jsp?path=");
    sb.append(URLEncoder.encode(fld.getPath(), "UTF-8"));
    sb.append("'\">");
    sb.append(FileUtils.getName(fld.getPath()));
    sb.append("</td><td><a href=\"fld-properties.jsp?path=");
    sb.append(URLEncoder.encode(fld.getPath(), "UTF-8"));
    sb.append("\"><img src=\"img/properties.png\"></a></td><td nowrap=\"nowrap\"></td></tr>");
  }
  
  for (Iterator<Document> it = okmDocument.getChilds(token, path).iterator(); it.hasNext(); ) {
    Document doc = it.next();
    sb.append("<tr class=\"");
    sb.append(i++%2==0?"odd":"even");
    sb.append("\"><td width=\"18px\">");
    sb.append("<img src=\""+request.getContextPath()+"/es.git.openkm.frontend.Main/img/icon/mime/"+doc.getMimeType()+".gif\">");
    sb.append("</td><td width=\"100%\" onclick=\"document.location='");
    sb.append(request.getContextPath());
    sb.append("/OKMDownloadServlet?toPdf&id=");
    sb.append(URLEncoder.encode(doc.getPath(), "UTF-8"));
    sb.append("'\">");
    sb.append(FileUtils.getName(doc.getPath()));
    sb.append("</td><td><a href=\"doc-properties.jsp?path=");
    sb.append(URLEncoder.encode(doc.getPath(), "UTF-8"));
    sb.append("\"><img src=\"img/properties.png\"></a></td><td nowrap=\"nowrap\">");
    sb.append(FormatUtil.formatSize(doc.getActualVersion().getSize()));
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
  <table width="100%" cellpadding="2" cellspacing="2">
    <tr>
      <td>
        <form id="context" action="browse.jsp">
          <select name="path" onchange="document.getElementById('context').submit()">
            <option value="<%=ctxTaxonomy%>" <%=path.startsWith(ctxTaxonomy)?"selected":""%>>Taxonomy</option>
            <option value="<%=ctxTemplates%>" <%=path.startsWith(ctxTemplates)?"selected":""%>>Templates</option>
            <option value="<%=ctxPersonal%>" <%=path.startsWith(ctxPersonal)?"selected":""%>>My documents</option>
            <!-- <option value="<%=ctxMail%>" <%=path.startsWith(ctxMail)?"selected":""%>>E-mail</option> -->
            <option value="<%=ctxTrash%>" <%=path.startsWith(ctxTrash)?"selected":""%>>Trash</option>
          </select>
        </form>
      </td>
      <td><form id="search" action="search.jsp"><input name="query" type="text"/><input type="image" src="img/search.png" style="vertical-align: middle; border: 0;"/></form></td>
      <td></td>
      <td align="right"><a href="logout.jsp"><img src="img/logout.png" /></a></td>
    </tr>
  </table>
  <table class="results">
    <tr><th colspan="4">Path: <span style="font-weight: normal"><%=path %></span></th></tr>
    <tr><th></th><th>Name</th><th style="width: 25px"></th><th>Size</th></tr>
    <%=sb.toString()%>
  </table>
</body>
</html>