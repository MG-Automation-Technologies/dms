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
  <script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
  <script src="js/vanadium-min.js" type="text/javascript"></script>
  <script type="text/javascript">
    $(document).ready(function(){
      $("#check").click(function(event) {
        $("#dest").html('Checking....');
        $("#dest").load('MailAccount', {action: "check", ma_mhost: $('#ma_mhost').val(), ma_muser: $('#ma_muser').val(), ma_mpassword: $('#ma_mpassword').val(), ma_mfolder: $('#ma_mfolder').val()});
      });
    })
  </script>
  <title>Mail account</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:choose>
        <c:when test="${action == 'create'}"><h1>Create mail account</h1></c:when>
        <c:when test="${action == 'edit'}"><h1>Edit mail account</h1></c:when>
        <c:when test="${action == 'delete'}"><h1>Delete mail account</h1></c:when>
      </c:choose>
      <form action="MailAccount" id="form">
        <input type="hidden" name="action" id="action" value="${action}"/>
        <input type="hidden" name="persist" value="${persist}"/>
        <input type="hidden" name="ma_id" value="${ma.id}"/>
        <input type="hidden" name="ma_user" value="${ma.user}"/>
        <table class="form" width="345px" align="center">
          <tr>
            <td nowrap="nowrap">Mail host</td>
            <td><input class=":required :only_on_blur" name="ma_mhost" id="ma_mhost" value="${ma.mailHost}"/></td>
          </tr>
          <tr>
            <td nowrap="nowrap">Mail user</td>
            <td><input class=":required :only_on_blur" name="ma_muser" id="ma_muser" value="${ma.mailUser}"/></td>
          </tr>
          <tr>
            <td nowrap="nowrap">Mail password</td>
            <td><input class=":required :only_on_blur" name="ma_mpassword" id="ma_mpassword" type="password" value="${ma.mailPassword}"/></td>
          </tr>
          <tr>
            <td nowrap="nowrap">Mail folder</td>
            <td><input name="ma_mfolder" id="ma_mfolder" value="${ma.mailFolder}"/></td>
          </tr>
          <tr>
            <td>Active</td>
            <td>
              <c:choose>
                <c:when test="${ma.active}">
                  <input name="ma_active" type="checkbox" checked="checked"/>
                </c:when>
                <c:otherwise>
                  <input name="ma_active" type="checkbox"/>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="right">
              <input type="button" id="check" value="Check"/>
              <input type="button" onclick="javascript:window.history.back()" value="Cancel"/>
              <input type="submit" value="Send"/>
            </td>
          </tr>
        </table>
      </form>
      <br/>
      <div class="warn" style="text-align: center;" id="dest"></div>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>