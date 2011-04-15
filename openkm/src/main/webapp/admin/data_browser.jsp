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
  <title>Data Browser</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:set var="row">1</c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Data browser</h1>
      <table class="results" width="70%">
        <tr>
          <th>Name</th><th width="50px"></th>
        </tr>
        <c:forEach var="fld" items="${folders}">
          <c:url value="DataBrowser" var="urlBrowse">
            <c:param name="action" value="${action}"/>
            <c:param name="path" value="${fld.path}"/>
          </c:url>
          <c:set var="row">${row + 1}</c:set>
          <tr class="${row % 2 == 0 ? 'even' : 'odd'}">
            <td><a href="${urlBrowse}">${fld.name}</a></td>
            <td align="center">
              <a href="${urlBrowse}"><img src="img/true.png" alt="Select" title="Select"/></a>
            </td>
          </tr>
        </c:forEach>
        <c:forEach var="doc" items="${documents}">
          <c:url value="DataBrowser" var="urlBrowse">
            <c:param name="action" value="${action}"/>
            <c:param name="path" value="${doc.path}"/>
          </c:url>
          <c:set var="row">${row + 1}</c:set>
          <tr class="${row % 2 == 0 ? 'even' : 'odd'}">
            <td>${doc.name}</td>
            <td align="center">
              <a href="${urlBrowse}"><img src="img/true.png" alt="Select" title="Select"/></a>
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