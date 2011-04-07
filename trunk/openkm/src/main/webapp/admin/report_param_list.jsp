<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Report Parameters</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:url value="Report" var="urlReportList">
      </c:url>
      <h1>Report parameters <span style="font-size: 10px;">(<a href="${urlReportList}">Reports</a>)</span></h1>
      <table class="results" width="60%">
        <tr>
          <th>Label</th><th>Name</th><th>Type</th>
          <th width="50px">
            <c:url value="Report" var="urlCreate">
              <c:param name="action" value="paramCreate"/>
              <c:param name="rp_id" value="${rp_id}"/>
            </c:url>
            <a href="${urlCreate}"><img src="img/action/new.png" alt="New parameter" title="New parameter"/></a>
          </th>
        </tr>
        <c:forEach var="rpp" items="${params}" varStatus="row">
          <c:url value="Report" var="urlEdit">
            <c:param name="action" value="paramEdit"/>
            <c:param name="rpp_id" value="${rpp.id}"/>
          </c:url>
          <c:url value="Report" var="urlDelete">
            <c:param name="action" value="paramDelete"/>
            <c:param name="rpp_id" value="${rpp.id}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${rpp.label}</td><td>${rpp.name}</td><td>${rpp.type}</td>
            <td>
              <a href="${urlEdit}"><img src="img/action/edit.png" alt="Edit" title="Edit"/></a>
              &nbsp;
              <a href="${urlDelete}"><img src="img/action/delete.png" alt="Delete" title="Delete"/></a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>