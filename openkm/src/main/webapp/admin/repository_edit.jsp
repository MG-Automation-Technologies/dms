<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Repository Edit</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Repository edit</h1>
      <form action="RepositoryView">
        <input type="hidden" name="action" value="save"/>
        <input type="hidden" name="path" value="${node.path}"/>
        <input type="hidden" name="property" value="${property.name}"/>
        <table class="form" width="350px">
          <tr><td>Node</td><td><i>${node.path}</i></td></tr>
          <tr><td>Property</td><td><i>${property.name}</i></td></tr>
          <c:choose>
            <c:when test="${multiple}">
              <tr>
                <td>Value</td>
                <td><textarea cols="75" rows="15" name="value">${value}</textarea></td>
              </tr>
            </c:when>
            <c:otherwise>
              <tr><td>Value</td><td><input size="64" type="text" name="value" value="${value}"/></td></tr>
            </c:otherwise>
          </c:choose>
          <tr>
            <td colspan="2" align="right">
              <input type="button" onclick="javascript:window.history.back()" value="Cancel"/>
              <input type="submit" value="Send"/>
            </td>
          </tr>
        </table>
      </form>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>