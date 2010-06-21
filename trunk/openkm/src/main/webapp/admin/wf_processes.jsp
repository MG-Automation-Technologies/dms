<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.api.OKMWorkflow" %>
<%@ page import="com.openkm.bean.workflow.ProcessDefinition" %>
<%@ page import="com.openkm.bean.workflow.ProcessInstance" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF-8");
	String token = (String)session.getAttribute("token");
	String action = request.getParameter("action");
	String id = request.getParameter("id");
	
	if (action != null && !action.equals("")) {
		if (action.equals("start") && id != null && !id.equals("")) {
			List<FormElement> variables = new ArrayList<FormElement>();
			ProcessInstance pi = OKMWorkflow.getInstance().runProcessDefinition(Long.parseLong(id), null, variables);
			response.sendRedirect("wf_procins.jsp?id="+pi.getId());
		} else if (action.equals("delete") && id != null && !id.equals("")) {
			OKMWorkflow.getInstance().deleteProcessDefinition(Long.parseLong(id));
			response.sendRedirect("wf_processes.jsp");
		}
	} else {
		pageContext.setAttribute("processDefinitions", OKMWorkflow.getInstance().findAllProcessDefinitions());
	}
	
	pageContext.setAttribute("mimeAccept", Config.mimeAccept);
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.util.List"%>
<%@page import="com.openkm.bean.form.FormElement"%>
<%@page import="java.util.ArrayList"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Workflow Process Definition Browser</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <table>
        <tr>
          <td><h1>Process Definitions</h1></td>
          <td> &nbsp; <a href="wf_processes.jsp"><img src="img/action/reload.png" alt="Reload" title="Reload"/></a></td>
        </tr>
      </table>
      <table class="results" width="90%">
        <tr><th>Process ID</th><th>Process Name</th><th>Version</th><th width="75px">Actions</th></tr>
        <c:forEach var="pd" items="${processDefinitions}" varStatus="row">
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${pd.id}</td>
            <td>${pd.name}</td>
            <td>${pd.version}</td>
            <td>
              <a href="wf_procdef.jsp?id=${pd.id}"><img src="img/action/examine.png" alt="Examine" title="Examine"/></a>
              &nbsp;
              <a href="wf_processes.jsp?action=delete&id=${pd.id}"><img src="img/action/delete.png" alt="Delete" title="Delete"/></a>
              &nbsp;
              <a href="wf_processes.jsp?action=start&id=${pd.id}"><img src="img/action/start.png" alt="Start" title="Start"/></a>
            </td>
          </tr>
        </c:forEach>
      </table>
      <hr/>
      <h2 style="text-align: center">Upload process definition</h2>
      <form action="RegisterWorkflow" method="post" enctype="multipart/form-data">
        <table class="form">
          <tr><td><input type="file" name="definition"/></td></tr>
          <tr><td align="right"><input type="submit" value="Upload"/></td></tr>
        </table>
      </form>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>