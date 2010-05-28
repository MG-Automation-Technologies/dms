<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.bean.Document"%>
<%@ page import="com.openkm.util.WebUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Repository View</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:set var="contentType"><%=Document.CONTENT_TYPE%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Repository</h1>
      <h2>Info</h2>
      <ul>
        <li><b>Path</b>: ${breadcrumb}</li>
        <li><b>Depth</b>: ${node.depth}</li>
        <li><b>Type</b>: ${fn:toUpperCase(node.primaryNodeType.name)}</li>
        <c:if test="${node.locked}"><li><b>Locked</b></li></c:if>
      </ul>
      <h2>Actions</h2>
      <ul>
        <c:if test="${node.depth > 0}">
          <c:url value="Repository" var="urlRemoveContent">
            <c:param name="path" value="${node.path}"/>
            <c:param name="action" value="remove_content"/>
          </c:url>
          <c:url value="Repository" var="urlRemoveCurrent">
            <c:param name="path" value="${node.path}"/>
            <c:param name="action" value="remove_current"/>
          </c:url>
          <li><a href="${urlRemoveContent}">Remove contents</a></li>
          <li><a href="${urlRemoveCurrent}">Remove current</a></li>
        </c:if>
        <c:if test="${node.locked && holdsLock}">
          <c:url value="Repository" var="urlUnlock">
            <c:param name="path" value="${node.path}"/>
            <c:param name="action" value="unlock"/>
          </c:url>
          <li><a href="${urlUnlock}">Unlock</a></li>
        </c:if>
      </ul>
      <h2>Properties</h2>
      <table class="results" width="90%">
        <tr><th>Type</th><th>Multiple</th><th>Protected</th><th>Name</th><th>Value</th></tr>
        <c:forEach var="property" items="${properties}" varStatus="row">
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${property.type}</td>
            <td><c:if test="${property.multiple}"><img src="img/true.png"/></c:if></td>
            <td><c:if test="${property.protected}"><img src="img/true.png"/></c:if></td>
            <td>${property.name}</td>
            <td>${property.value}</td>
          </tr>
        </c:forEach>
      </table>
      <h2>Children</h2>
      <table class="results" width="90%">
        <tr><th>Type</th><th>Locked</th><th>CheckedOut</th><th>Name</th></tr>
        <c:forEach var="child" items="${children}" varStatus="row">
          <c:url value="Repository" var="urlList">
            <c:param name="path" value="${child.path}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${fn:toUpperCase(child.primaryNodeType.name)}</td>
            <td><c:if test="${child.locked}"><img src="img/true.png"/></c:if></td>
            <td><c:if test="${child.primaryNodeType.name == contentType && child.checkedOut}"><img src="img/true.png"/></c:if></td>
            <td><a href="${urlList}">${child.name}</a></td>
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