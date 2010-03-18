<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="javax.jcr.Session"%>
<%@page import="es.git.openkm.core.SessionManager"%>
<%@page import="es.git.openkm.api.OKMSearch"%>
<%@page import="es.git.openkm.bean.Document"%>
<%@page import="es.git.openkm.bean.QueryResult"%>
<%@page import="es.git.openkm.util.FileUtils"%>
<%@page import="es.git.openkm.util.FormatUtil"%>
<%@include file="session.jsp"%>
<%
  request.setCharacterEncoding("UTF-8");
  String token = (String) session.getAttribute("token");
  Session jcrSession = SessionManager.getInstance().get(token);
  String query = request.getParameter("query");
  OKMSearch okmSearch = OKMSearch.getInstance();
  StringBuilder sb = new StringBuilder();
  int i = 0;

  if (query != null && !query.equals("")) {
    query = new String(query.getBytes("ISO-8859-1"), "UTF-8");
	for (Iterator<QueryResult> it = okmSearch.findByContent(token, query).iterator(); it.hasNext(); ) {
	  QueryResult qr = it.next();
	  Document doc = qr.getDocument();
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
    <tr><th colspan="4">Query: <span style="font-weight: normal"><%=query%></span></th></tr>
    <tr><th></th><th>Name</th><th></th><th>Size</th></tr>
    <%=sb.toString()%>
  </table>
</body>
</html>