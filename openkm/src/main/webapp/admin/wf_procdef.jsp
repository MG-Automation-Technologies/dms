<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.api.OKMWorkflow"%>
<%@ page import="com.openkm.bean.form.FormElement"%>
<%@ page import="com.openkm.bean.form.Input"%>
<%@ page import="com.openkm.bean.form.TextArea"%>
<%@ page import="com.openkm.bean.form.Select"%>
<%@ page import="com.openkm.bean.form.Option"%>
<%@ page import="com.openkm.bean.form.Button"%>
<%@ page import="com.openkm.bean.workflow.ProcessInstance"%>
<%@ page import="com.openkm.bean.workflow.ProcessDefinition"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	request.setCharacterEncoding("UTF-8");
	String token = (String)session.getAttribute("token");
	String action = request.getParameter("action");
	String id = request.getParameter("id");
	String iid = request.getParameter("iid");
	
	if (action != null && !action.equals("")) {
		if (action.equals("resume") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
			OKMWorkflow.getInstance().resumeProcessInstance(Long.parseLong(iid));
			response.sendRedirect("wf_procdef.jsp?id="+id);
		} else if (action.equals("suspend") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
			OKMWorkflow.getInstance().suspendProcessInstance(Long.parseLong(iid));
			response.sendRedirect("wf_procdef.jsp?id="+id);
		} else if (action.equals("end") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
			response.sendRedirect("wf_procdef.jsp?id="+id);
		} else if (action.equals("delete") && id != null && !id.equals("") && iid != null && !iid.equals("")) {
			OKMWorkflow.getInstance().deleteProcessInstance(Long.parseLong(iid));
			response.sendRedirect("wf_procdef.jsp?id="+id);
		}
	} else {
		pageContext.setAttribute("pd", OKMWorkflow.getInstance().getProcessDefinition(Long.parseLong(id)));
		pageContext.setAttribute("processInstances", OKMWorkflow.getInstance().findProcessInstances(Long.parseLong(id)));
		pageContext.setAttribute("processDefinitionForms", OKMWorkflow.getInstance().getProcessDefinitionForms(Long.parseLong(id)));
	}
	
	pageContext.setAttribute("mimeAccept", Config.mimeAccept);
%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Workflow Process Definition</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
   <c:choose>
    <c:when test="${isAdmin}">
      <table>
        <tr>
          <td><h1>Process Definition</h1></td><td>
          &nbsp;
          <a href="wf_procdef.jsp?<%=request.getQueryString()%>"><img src="img/action/reload.png" alt="Reload" title="Reload"/></a>
          &nbsp;
          <a href="javascript:history.back(1)"><img src="img/action/back.png" alt="Back" title="Back"/></a></td>
        </tr>
      </table>
      <table class="results" width="90%">
        <tr><th>Process ID</th><th>Name</th><th>Description</th><th>Version</th></tr>
        <tr class="odd"><td>${pd.id}</td><td>${pd.name}</td><td>${pd.description}</td><td>${pd.version}</td></tr>
      </table>
      <h2>Process Instances</h2>
      <table class="results" width="90%">
        <tr><th>Instance ID</th><th>Key</th><th>Status</th><th>Start Date</th><th>End Date</th><th width="100px">Actions</th></tr>
        <c:forEach var="pi" items="${processInstances}" varStatus="row">
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${pi.id}</td>
            <td>${pi.key}</td>
            <td>
              <b>
                <c:choose>
                  <c:when test="${pi.end != null && pi.suspended}">Ended (was suspended)</c:when>
                  <c:when test="${pi.end != null && !pi.suspended}">Ended</c:when>
                  <c:when test="${pi.end == null && pi.suspended}">Suspended</c:when>
                  <c:when test="${pi.end == null && !pi.suspended}">Running</c:when>  
                </c:choose>
              </b>
            </td>
            <td><fmt:formatDate value="${pi.start.time}" type="both"/></td>
            <td><fmt:formatDate value="${pi.end.time}" type="both"/></td>
            <td>
              <a href="wf_procins.jsp?id=${pi.id}"><img src="img/action/examine.png" alt="Examine" title="Examine"/></a>
              &nbsp;
              <a href="wf_procdef.jsp?action=delete&id=${pd.id}&iid=${pi.id}"><img src="img/action/delete.png" alt="Delete" title="Delete"/></a>
              <c:if test="${pi.end == null}">
                &nbsp;
                <a href="wf_procdef.jsp?action=end&id=${pd.id}&iid=${pi.id}"><img src="img/action/end.png" alt="End" title="End"/></a>
              </c:if>
              &nbsp;
              <c:choose>
                <c:when test="${pi.suspended}">
                  <a href="wf_procdef.jsp?action=resume&id=${pd.id}&iid=${pi.id}"><img src="img/action/resume.png" alt="Resume" title="Resume"/></a>
                </c:when>
                <c:otherwise>
                  <a href="wf_procdef.jsp?action=suspend&id=${pd.id}&iid=${pi.id}"><img src="img/action/suspend.png" alt="Suspend" title="Suspend"/></a>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>	
        </c:forEach>
      </table>
      <h2>Forms</h2>
      <table class="results" width="90%">
        <tr><th>Task</th><th>Form</th></tr>
        <c:forEach var="pdf" items="${processDefinitionForms}" varStatus="row">
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${pdf.key}</td>
            <td>
              <table class="results" width="100%">
                <tr><th>Label</th><th>Name</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>
                <c:forEach var="fe" items="${pdf.value}" varStatus="row">
                  <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                    <td>${fe.label}</td>
                    <td>${fe.name}</td>
                    <td>${fe.width}</td>
                    <td>${fe.height}</td>
                    <c:choose>
                      <c:when test="${fe.class.name == 'com.openkm.bean.form.Input'}">
                        <td>Input</td>
                        <td><i>Type:</i> <%=((Input)pageContext.getAttribute("fe")).getType()%></td>
                      </c:when>
                      <c:when test="${fe.class.name == 'com.openkm.bean.form.TextArea'}">
                        <td>TextArea</td>
                        <td></td>
                      </c:when>
                      <c:when test="${fe.class.name == 'com.openkm.bean.form.Select'}">
                        <td>Select</td>
                        <td>
                          <i>Type:</i> <%=((Select)pageContext.getAttribute("fe")).getType()%>
                          <i>Options:</i>
                          <ul>
                          <% for (Iterator<Option> itOpt = ((Select)pageContext.getAttribute("fe")).getOptions().iterator(); itOpt.hasNext(); ) { %>
                          <% Option opt = itOpt.next(); %>
                          <li><i>Label:</i> <%=opt.getLabel()%>, <i>Value:</i> <%=opt.getValue()%></li>
                          <% } %>
                          </ul>
                        </td>
                      </c:when>
                      <c:when test="${fe.class.name == 'com.openkm.bean.form.Button'}">
                        <td>Button</td>
                        <td><i>Type:</i> <%=((Button)pageContext.getAttribute("fe")).getType()%></td>
                      </c:when>
                    </c:choose>
                  </tr>
                </c:forEach>
              </table>
            </td>
          </tr>
        </c:forEach>
      </table>
      <h2>Process Image</h2>
      <center><img src="WorkflowGraph?id=${pd.id}"/></center>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>