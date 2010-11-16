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
  <title>Configuration</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Configuration</h1>
      <table class="results" width="70%">
        <tr>
          <th>Key</th><th>Type</th><th>Value</th>
          <th width="50px">
            <c:url value="Config" var="urlCreate">
              <c:param name="action" value="create"/>
            </c:url>
            <a href="${urlCreate}"><img src="img/action/new.png" alt="New configuration property" title="New configuration property"/></a>
          </th>
        </tr>
        <c:forEach var="cfg" items="${configs}" varStatus="row">
          <c:url value="Config" var="urlEdit">
            <c:param name="action" value="edit"/>
            <c:param name="cfg_key" value="${cfg.key}"/>
          </c:url>
          <c:url value="Config" var="urlDelete">
            <c:param name="action" value="delete"/>
            <c:param name="cfg_key" value="${cfg.key}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${cfg.key}</td><td>${cfg.type}</td>
            <td align="center">
              <c:choose>
                <c:when test="${cfg.type == 'CheckBox'}">
                  <c:choose>
                    <c:when test="${cfg.value == 'true'}">
                      <img src="img/true.png" alt="Active" title="Active"/>
                    </c:when>
                    <c:otherwise>
                      <img src="img/false.png" alt="Inactive" title="Inactive"/>
                    </c:otherwise>
                  </c:choose>
                </c:when>
                <c:otherwise>${cfg.value}</c:otherwise>
              </c:choose>
            </td>
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