<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ page import="com.openkm.util.FormatUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
  <script type="text/javascript" src="js/jquery.DOMWindow.js"></script>
  <script type="text/javascript">
    $(document).ready(function() {
      $dm = $('.ds').openDOMWindow({
        height:200, width:300,
        eventType:'click',
        overlayOpacity: '57',
        windowSource:'iframe', windowPadding:0
      });
	});
    
    function dialogClose() {
    	$dm.closeDOMWindow();
    }
  </script>
  <title>Repository Restore</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isMultipleInstancesAdmin(request)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Repository restore</h1>
      <form action="RepositoryRestore">
        <table class="form" align="center">
          <tr>
            <td>Filesystem path</td>
            <td><input type="text" size="50" name="fsPath" id="fsPath" value=""/></td>
            <td><a class="ds" href="DataBrowser?action=fs&dst=fsPath"><img src="img/action/browse_fs.png"/></a></td>
          </tr>
          <tr><td colspan="3" align="right"><input type="submit" value="Send"/></td></tr>
        </table>
      </form>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>