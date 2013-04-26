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
  <script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
  <script src="../js/vanadium-min.js" type="text/javascript"></script>
  <title>Omr Template</title>
  <script type="text/javascript">
    $(document).ready(function() {
    	$('#alert').hide();
	});
    
    function showAlert() {
    	$('#alert').show();
    }
  </script>
</head>
<body> 
<c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <ul id="breadcrumb">
        <li class="path">
          <a href="Omr">Omr template</a>
        </li>
        <li class="path">
          <c:choose>
            <c:when test="${action == 'check'}">Process Template Check</c:when>
          </c:choose>
        </li>
      </ul>
      <br/>
  	<form action="Omr" method="post" enctype="multipart/form-data">
  	<input type="hidden" name="action" value="${action}"/>
        <input type="hidden" name="om_id" value="${om.id}"/>
        <table class="form" width="425px">     
          <tr>
		  	<td valign="top">Upload Form</td>
		  	<td valign="top">
		  		<input class=":required :only_on_blur" type="file" name="file"/>
		  	</td>
		  </tr>
          <tr>
            <td colspan="2" align="right">
              <div id="buttons">
              	<input type="button" onclick="javascript:window.history.back()" value="Cancel"/>
              	<input onclick="javascript:showAlert();" type="submit" value="Send"/>
              </div>
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