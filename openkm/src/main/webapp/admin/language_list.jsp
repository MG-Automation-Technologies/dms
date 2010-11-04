<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <title>Language List</title>
</head>
<body>
	<c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  	<c:choose>
  		<c:when test="${isAdmin}">
  			
  			<h1>Language list <span style="font-size: 10px;"></h1>
  		
	  		<table class="results" width="80%">
	        <tr>
	          <th>Language</th><th>Description</th><th>Translations</th><th></th>
	        </tr>
	        
	        <c:forEach var="langs" items="${langs}" varStatus="row">
	         	<c:url value="Language" var="langEdit">
	            	<c:param name="action" value="langEdit"/>
	            	<c:param name="lg_language" value="${langs.language}"/>
	          	</c:url>
	          	<c:url value="Language" var="langDelete">
	            	<c:param name="action" value="langDelete"/>
	            	<c:param name="lg_language" value="${langs.language}"/>
	          	</c:url>
	        	<tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
	        		<td>${langs.language}</td><td>${langs.description}</td><td>${fn:length(langs.translations)}</td>
	        		<td align="center">
	        			<a href="${langEdit}"><img src="img/action/edit.png" alt="Edit" title="Edit"/></a>
                		&nbsp;
                		<a href="${langDelete}"><img src="img/action/delete.png" alt="Delete" title="Delete"/></a>
                		&nbsp;
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