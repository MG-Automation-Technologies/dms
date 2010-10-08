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
  <title>Reports</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
  <u:constantsMap className="com.openkm.util.ReportUtil" var="ReportUtil"/>
  <c:choose>
    <c:when test="${isAdmin}">
      <h1>Reports</h1>
      <table class="results" width="70%">
        <tr>
          <th>Name</th><th>Type</th><th>File Name</th><th>Active</th>
          <th width="130px">
            <c:url value="Report" var="urlCreate">
              <c:param name="action" value="create"/>
            </c:url>
            <a href="${urlCreate}"><img src="img/action/new.png" alt="New report" title="New report"/></a>
          </th>
        </tr>
        <c:forEach var="rp" items="${reports}" varStatus="row">
          <c:url value="Report" var="urlEdit">
            <c:param name="action" value="edit"/>
            <c:param name="rp_id" value="${rp.id}"/>
          </c:url>
          <c:url value="Report" var="urlDelete">
            <c:param name="action" value="delete"/>
            <c:param name="rp_id" value="${rp.id}"/>
          </c:url>
          <c:url value="Report" var="urlExecutePdf">
            <c:param name="action" value="execute"/>
            <c:param name="rp_id" value="${rp.id}"/>
            <c:param name="out" value="${ReportUtil.PDF_OUTPUT}"/>
          </c:url>
          <c:url value="Report" var="urlExecuteRtf">
            <c:param name="action" value="execute"/>
            <c:param name="rp_id" value="${rp.id}"/>
            <c:param name="out" value="${ReportUtil.ODT_OUTPUT}"/>
          </c:url>
          <c:url value="Report" var="urlExecuteCsv">
            <c:param name="action" value="execute"/>
            <c:param name="rp_id" value="${rp.id}"/>
            <c:param name="out" value="${ReportUtil.CSV_OUTPUT}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${rp.name}</td><td>${rp.type}</td><td>${rp.fileName}</td>
            <td align="center">
              <c:choose>
                <c:when test="${rp.active}">
                  <img src="img/true.png" alt="Active" title="Active"/>
                </c:when>
                <c:otherwise>
                  <img src="img/false.png" alt="Inactive" title="Inactive"/>
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              <a href="${urlEdit}"><img src="img/action/edit.png" alt="Edit" title="Edit"/></a>
              &nbsp;
              <a href="${urlDelete}"><img src="img/action/delete.png" alt="Delete" title="Delete"/></a>
              &nbsp;
              <a href="${urlExecutePdf}"><img src="img/action/pdf.png" alt="Generate PDF" title="Generate PDF"/></a>
              &nbsp;
              <a href="${urlExecuteRtf}"><img src="img/action/rtf.png" alt="Generate RTF" title="Generate RTF"/></a>
              &nbsp;
              <a href="${urlExecuteCsv}"><img src="img/action/csv.png" alt="Generate CSV" title="Generate CSV"/></a>
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