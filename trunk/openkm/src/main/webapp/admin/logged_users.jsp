<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Logged users</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Logged users</h1>
      <table class="results" width="80%">
        <tr><th>User</th><th>Session id</th><th>Remote IP</th><th>Remote host</th><th>Creation</th><th>Last accessed</th></tr>
        <c:forEach var="se" items="${sessions}" varStatus="row">
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${se.user}</td><td>${se.id}</td><td>${se.ip}</td><td>${se.host}</td>
            <td><fmt:formatDate value="${se.creation.time}" type="both"/></td>
            <td><fmt:formatDate value="${se.lastAccessed.time}" type="both"/></td>
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