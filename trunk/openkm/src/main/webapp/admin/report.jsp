<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.servlet.admin.ExecuteReportServlet" %>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Reports</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Reports</h1>
      <form action="ExecuteReport">
        <table class="form" align="center">
          <tr><td>Report
              <select name="jasperFile">
                <option value="<%=ExecuteReportServlet.REPORT_LOCKED_DOCUMENTS%>">Locked documents</option>
                <option value="<%=ExecuteReportServlet.REPORT_SUBSCRIBED_DOCUMENTS%>">Subscribed documents</option>
                <option value="<%=ExecuteReportServlet.REPORT_REGISTERED_USERS%>">Registered users</option>
                <option value="<%=ExecuteReportServlet.REPORT_WORKFLOW_WORKLOAD%>">Workflow workload</option>
              </select>
          </td></tr>
          <tr><td align="right"><input type="submit" value="Send"/></td></tr>
        </table>
      </form>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>