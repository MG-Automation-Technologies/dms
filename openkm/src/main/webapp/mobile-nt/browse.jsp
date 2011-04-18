<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />  
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />  
  <title>OpenKM</title>
  <link rel="apple-touch-icon" href="img/condor.jpg" />
  <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a4.1/jquery.mobile-1.0a4.1.min.css" />
  <script src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
  <script src="http://code.jquery.com/mobile/1.0a4.1/jquery.mobile-1.0a4.1.min.js"></script>
</head>
<body>
  <div data-role="page">
    <div data-role="header">
      <h1>OpenKM</h1>
    </div>
    <div data-role="content">
      <ul data-role="listview">
        <!-- List folders -->
        <c:forEach var="fld" items="${folderChilds}">
          <li>
            <c:url value="Handler" var="urlBrowse">
              <c:param name="path" value="${fld.path}"/>
            </c:url>
            <c:choose>
              <c:when test="${fld.hasChilds}"><c:set var="fldImg" value="menuitem_childs.gif"/></c:when>
              <c:otherwise><c:set var="fldImg" value="menuitem_empty.gif"/></c:otherwise>
            </c:choose>
            <img src="../frontend/img/${fldImg}" class="ui-li-icon"/>
            <a href="${urlBrowse}" data-transition="slide"><u:getName path="${fld.path}"/></a>
          </li>
        </c:forEach>
        <!-- List documents -->
        <c:forEach var="doc" items="${documentChilds}">
          <li>
            <c:url value="/frontend/Download" var="urlDownload">
              <c:if test="${doc.convertibleToPdf}">
                <c:param name="toPdf"/>
              </c:if>
              <c:param name="id" value="${doc.path}"/>
            </c:url>
            <c:url value="/mime/${doc.mimeType}" var="urlIcon">
            </c:url>
            <img src="${urlIcon}" class="ui-li-icon"/>
            <a href="${urlDownload}" data-transition="slide"><u:getName path="${doc.path}"/></a>
          </li>
        </c:forEach>
      </ul>
    </div>
    <div data-role="footer">
      <h4>Footer</h4>
    </div>		
  </div>
</body>
</html>