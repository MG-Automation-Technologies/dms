<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <title>User Profile</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:choose>
        <c:when test="${action == 'create'}"><h1>Create profile</h1></c:when>
        <c:when test="${action == 'edit'}"><h1>Edit profile</h1></c:when>
        <c:when test="${action == 'delete'}"><h1>Delete profile</h1></c:when>
      </c:choose>
      <form action="UserProfile">
        <input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="persist" value="${persist}"/>
        <input type="hidden" name="up_id" value="${up.id}"/>
        <table class="form" width="350px">
          <tr><td>
            <fieldset>
              <legend>General</legend>
              <table>
                <tr>
                  <td>Name</td>
                  <td><input class=":required :only_on_blur" name="up_name" value="${up.name}"/></td>
                </tr>
                <tr>
                  <td>Active</td>
                  <td>
                    <c:choose>
                      <c:when test="${up.active}">
                        <input name="up_active" type="checkbox" checked="checked"/>
                      </c:when>
                      <c:otherwise>
                        <input name="up_active" type="checkbox"/>
                      </c:otherwise>
                    </c:choose>
                  </td>
                </tr>
              </table>
            </fieldset>
            
            <!-- USER QUOTA -->
            <jsp:include page="profile_quota_edit.jsp"/>
            
            <!-- WIZARD -->
            <jsp:include page="profile_wizard_edit.jsp"/>
            
            <!-- CHAT -->
            <jsp:include page="profile_chat_edit.jsp"/>
            
            <!-- STACKS -->
            <jsp:include page="profile_stacks_edit.jsp"/>
            
            <!-- MENU -->
            <jsp:include page="profile_menu_edit.jsp"/>
                        
            <table>
              <tr>
                <td colspan="1" align="right">
                  <input type="button" onclick="javascript:window.history.back()" value="Cancel"/>
                  <input type="submit" value="Send"/>
                </td>
              </tr>
            </table>
          </td></tr>
        </table>
      </form>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>