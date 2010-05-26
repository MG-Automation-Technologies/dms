<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.Config" %>
<%@ page import="com.openkm.util.WebUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>LogCat</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Repository</h1>
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
      <h2>Childs</h2>
      <table class="results" width="90%">
        <tr><th>Type</th><th>Locked</th><th>CheckedOut</th><th>Name</th></tr>
        <c:forEach var="child" items="${childs}" varStatus="row">
          <c:url value="Repository" var="urlList">
            <c:param name="path" value="${child.path}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${child.type}</td>
            <td><c:if test="${child.locked}"><img src="img/true.png"/></c:if></td>
            <td><c:if test="${child.checkedOut}"><img src="img/true.png"/></c:if></td>
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