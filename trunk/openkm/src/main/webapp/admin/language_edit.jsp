<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <title>Language edit</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:choose>
        <c:when test="${action == 'create'}"><h1>Create language</h1></c:when>
        <c:when test="${action == 'edit'}"><h1>Edit language</h1></c:when>
        <c:when test="${action == 'delete'}"><h1>Delete language</h1></c:when>
      </c:choose>
      <form action="Language" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="persist" value="${persist}"/>
        <table class="form" width="372px">
          <tr>
            <td>Id</td>
            <td width="100%">
              <c:choose>
                <c:when test="${action != 'create'}">
                  <input size="5" class=":required :only_on_blur" name="lg_id" value="${lang.id}" readonly="readonly"/>
                </c:when>
                <c:otherwise>
                  <input class=":required :only_on_blur" name="lg_id" value=""/>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr>
            <td>Name</td>
            <td><input class=":required :only_on_blur" name="lg_name" value="${lang.name}"/></td>
          </tr>
          <tr>
            <td>Flag</td>
            <td>
              <c:choose>
                <c:when test="${action == 'create'}">
                  <input class=":required :only_on_blur" type="file" name="image"/>
                </c:when>
                <c:otherwise>
                  <c:url value="Language" var="urlFlag">
                    <c:param name="action" value="flag"/>
                    <c:param name="lg_id" value="${lang.id}"/>
                  </c:url>
                  <table cellpadding="0" cellspacing="0"><tr><td><img src="${urlFlag}"/>&nbsp;</td><td><input type="file" name="image"/></td></tr></table>
                </c:otherwise>
              </c:choose>
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