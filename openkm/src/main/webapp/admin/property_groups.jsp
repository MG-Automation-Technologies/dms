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
  <title>Property Group</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Property groups</h1>
        <c:if test="${empty pGroups}">
          <table class="results" width="90%">
            <tr><th colspan="2">Property group label</th><th colspan="3">Property group name</th><th colspan="1">Property group visible</th></tr>
            <tr><th>Label</th><th>Name</th><th>Value</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>
          </table>
        </c:if>
        <c:forEach var="pGroup" items="${pGroups}">
          <table class="results" width="80%">
            <tr><th colspan="2">Property group label</th><th colspan="3">Property group name</th><th colspan="1">Property group visible</th></tr>
            <tr class="fuzzy">
              <td colspan="2" align="center"><b>${pGroup.key.label}</b></td>
              <td colspan="3" align="center"><b>${pGroup.key.name}</b></td>
              <td colspan="1" align="center">
                <c:choose>
                  <c:when test="${pGroup.key.visible}"><img src="img/true.png"/></c:when>
                  <c:otherwise><img src="img/false.png"/></c:otherwise>
                </c:choose>
              </td>
            </tr>
            <tr><th>Label</th><th>Name</th><th>Width</th><th>Height</th><th>Field</th><th>Others</th></tr>
            <c:forEach var="pgForm" items="${pGroup.value}" varStatus="row">
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td>${pgForm.label}</td>
                <td>${pgForm.name}</td>
                <td>${pgForm.width}</td>
                <td>${pgForm.height}</td>
                <td>${pgForm.field}</td>
                <td>${pgForm.others}</td>
              </tr>
            </c:forEach>
          </table>
          <br/>
        </c:forEach>
      <br/>
      <h2 style="text-align: center;">Register property group</h2>
      <form action="PropertyGroups">
        <table class="form" align="center">
          <tr><td>Property Group definition path <input type="text" size="50" name="pgPath" value=""/></td></tr>
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