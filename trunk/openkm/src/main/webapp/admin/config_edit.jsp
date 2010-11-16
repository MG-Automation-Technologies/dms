<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <title>Configuration</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:choose>
        <c:when test="${action == 'create'}"><h1>Create configuration</h1></c:when>
        <c:when test="${action == 'edit'}"><h1>Edit configuration</h1></c:when>
        <c:when test="${action == 'delete'}"><h1>Delete configuration</h1></c:when>
      </c:choose>
      <form action="Config">
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="persist" value="${persist}"/>
        <table class="form" width="425px">
          <tr>
            <td nowrap="nowrap">Key</td>
            <td><input size="50" class=":required :only_on_blur" name="cfg_key" value="${cfg.key}"/></td>
          </tr>
          <tr>
            <td>Type</td>
            <td>
              <select name="cfg_type">
                <c:forEach var="type" items="${types}">
                  <c:choose>
                    <c:when test="${cfg.type == type.key}">
                      <option value="${type.key}" selected="selected">${type.value}</option>
                    </c:when>
                    <c:otherwise>
                      <option value="${type.key}">${type.value}</option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr>
            <td>Value</td>
            <td>
              <textarea rows="5" cols="50" name="cfg_value">${cfg.value}</textarea>
              <!--
              <c:choose>
                <c:when test="${cfg.value == 'true'}">
                  <input name="cfg_value" type="checkbox" checked="checked"/>
                </c:when>
                <c:otherwise>
                  <input name="cfg_value" type="checkbox"/>
                </c:otherwise>
              </c:choose>
              -->
            </td>
          </tr>
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