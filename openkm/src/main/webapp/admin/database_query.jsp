<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.core.Config" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" href="css/style.css" type="text/css" />
  <title>Database Query</title>
</head>
<body>
  <c:set var="isAdmin"><%=request.isUserInRole(Config.DEFAULT_ADMIN_ROLE)%></c:set>
   <c:choose>
    <c:when test="${isAdmin}">
      <h1>Database query</h1>
      <table class="form">
        <tr>
          <td>
            <form action="DatabaseQuery" method="post" enctype="multipart/form-data">
              <table>
                <tr><td><textarea cols="75" rows="5" name="qs">${qs}</textarea></td></tr>
                <tr>
                  <td align="right">
                    <select name="method">
                      <option></option>
                      <c:choose>
                        <c:when test="${method == 'jdbc'}">
                          <option value="jdbc" selected="selected">JDBC</option>
                        </c:when>
                        <c:otherwise>
                          <option value="jdbc">JDBC</option>
                        </c:otherwise>
                      </c:choose>
                      <c:choose>
                        <c:when test="${method == 'hibernate'}">
                          <option value="hibernate" selected="selected">Hibernate</option>
                        </c:when>
                        <c:otherwise>
                          <option value="hibernate">Hibernate</option>
                        </c:otherwise>
                      </c:choose>
                    </select>
                    <input type="submit" value="Send"/>
                  </td>
                </tr>
              </table>
            </form>
          </td>
        </tr>
        <tr class="fuzzy">
          <td colspan="4" align="right">
            <form action="DatabaseQuery" method="post" enctype="multipart/form-data">
              <input type="hidden" name="action" value="import"/>
              <table>
                <tr>
                  <td><input class=":required :only_on_blur" type="file" name="sql-file"/></td>
                  <td><input type="submit" value="Execute update"/></td>
                </tr>
              </table>
            </form>
          </td>
        </tr>
      </table>
      <br/>
      <c:choose>
        <c:when test="${rows != null}">
          <center>Row Count: ${rows}</center>
          <c:if test="${not empty errors}">
            <table class="results" width="100%">
              <tr><th>Line</th><th>SQL</th><th>Error</th></tr>
              <c:forEach var="error" items="${errors}" varStatus="row">
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <td>${error.ln}</td>
                <td>${error.sql}</td>
                <td>${error.msg}</td>
              </tr>
            </c:forEach>
            </table>
          </c:if>
        </c:when>
        <c:otherwise>
          <table class="results" width="100%">
            <tr>
              <c:forEach var="col" items="${columns}">
                <th>${col}</th>
              </c:forEach>
            </tr>
            <c:forEach var="result" items="${results}" varStatus="row">
              <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                <c:forEach var="tp" items="${result}">
                  <td>${tp}</td>
                </c:forEach>
              </tr>
            </c:forEach>
          </table>
        </c:otherwise>
      </c:choose>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>