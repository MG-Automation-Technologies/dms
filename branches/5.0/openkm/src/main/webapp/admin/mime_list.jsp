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
  <title>Mime types</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:url value="MimeType" var="urlReset">
        <c:param name="action" value="reset"/>
      </c:url>
      <h1>Mime Types <span style="font-size: 10px;">(<a href="${urlReset}">Reset</a>)</span></h1>
      <table class="results" width="70%">
        <tr>
          <th>Name</th><th>Image</th><th>Extensions</th>
          <th width="50px">
            <c:url value="MimeType" var="urlCreate">
              <c:param name="action" value="create"/>
            </c:url>
            <a href="${urlCreate}"><img src="img/action/new.png" alt="New mime type" title="New mime type"/></a>
          </th>
        </tr>
        <c:forEach var="mt" items="${mimeTypes}" varStatus="row">
          <c:url value="/mime/${mt.name}" var="urlIcon">
          </c:url>
          <c:url value="MimeType" var="urlEdit">
            <c:param name="action" value="edit"/>
            <c:param name="mt_id" value="${mt.id}"/>
          </c:url>
          <c:url value="MimeType" var="urlDelete">
            <c:param name="action" value="delete"/>
            <c:param name="mt_id" value="${mt.id}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${mt.name}</td>
            <td align="center"><img src="${urlIcon}"/></td>
            <td>${mt.extensions}</td>
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