<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.openkm.com/tags/utils" prefix="u" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <title>OpenKM Mobile</title>
  <link href="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/mobile/themes/iphone/iphone.css" rel="stylesheet" />
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojo/dojo.xd.js" djConfig="isDebug:true, parseOnLoad:true"></script>
  <script type="text/javascript">
    // Use the lightweight parser
    dojo.require("dojox.mobile.parser");
    // Require Dojo mobile
    dojo.require("dojox.mobile");
    //Require the compat if the client isn't Webkit-based
    dojo.requireIf(!dojo.isWebKit,"dojox.mobile.compat");
  </script>
</head>
<body>
  <div id="settings" dojoType="dojox.mobile.View" selected="true">
    <h1 dojoType="dojox.mobile.Heading">Repository Browser</h1>
    <ul dojoType="dojox.mobile.RoundRectList">
      <!-- List folders -->
      <c:forEach var="fld" items="${folderChilds}">
        <li dojoType="dojox.mobile.ListItem" id="${fld.uuid}" data-action="fldprop">
          <c:url value="Handler" var="urlBrowse">
            <c:param name="path" value="${fld.path}"/>
          </c:url>
          <c:url value="Handler" var="urlProperties">
            <c:param name="action" value="fldprop"/>
            <c:param name="uuid" value="${fld.uuid}"/>
          </c:url>
          <c:choose>
            <c:when test="${fld.hasChilds}"><c:set var="fldImg" value="menuitem_childs.gif"/></c:when>
            <c:otherwise><c:set var="fldImg" value="menuitem_empty.gif"/></c:otherwise>
          </c:choose>
          <img src="../frontend/img/${fldImg}" class="ui-li-icon"/>
          <a href="${urlBrowse}" data-transition="slide"><u:getName path="${fld.path}"/></a>
          <a href="${urlProperties}" data-role="button"></a>
        </li>
      </c:forEach>
      <!-- List documents -->
      <c:forEach var="doc" items="${documentChilds}">
        <li dojoType="dojox.mobile.ListItem" id="${doc.uuid}" data-action="docprop">
          <c:url value="/frontend/Download" var="urlDownload">
            <c:if test="${doc.convertibleToPdf}">
              <c:param name="toPdf"/>
            </c:if>
            <c:param name="id" value="${doc.path}"/>
          </c:url>
          <c:url value="Handler" var="urlProperties">
            <c:param name="action" value="docprop"/>
            <c:param name="uuid" value="${doc.uuid}"/>
          </c:url>
          <c:url value="/mime/${doc.mimeType}" var="urlIcon"></c:url>
          <c:set var="size"><u:formatSize size="${doc.actualVersion.size}"/></c:set>
          <img src="${urlIcon}" class="ui-li-icon"/>
          <a href="${urlDownload}" data-ajax="false"><u:getName path="${doc.path}"/></a>
        </li>
      </c:forEach>
    </ul>
  </div>
</body>
</html>