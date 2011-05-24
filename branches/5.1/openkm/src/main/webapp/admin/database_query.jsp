<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.openkm.servlet.admin.BaseServlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="favicon.ico" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <link rel="stylesheet" type="text/css" href="js/codemirror/lib/codemirror.css" />
  <link rel="stylesheet" type="text/css" href="js/codemirror/mode/plsql/plsql.css" />
  <style type="text/css">
    .CodeMirror { width: 500px; height: 150px; background-color: #f8f6c2; }
    .activeline { background: #f0fcff !important; }
  </style>
  <script type="text/javascript" src="js/codemirror/lib/codemirror.js"></script>
  <script type="text/javascript" src="js/codemirror/mode/plsql/plsql.js"></script>
  <script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function() {
      var cm = CodeMirror.fromTextArea(document.getElementById('qs'), {
          lineNumbers: true,
      	  matchBrackets: true,
          indentUnit: 4,
          mode: "text/x-plsql",
          onCursorActivity: function() {
        	cm.setLineClass(hlLine, null);
            hlLine = cm.setLineClass(cm.getCursor().line, "activeline");
          }
        }
      );
      var hlLine = cm.setLineClass(0, "activeline");
      
      if ($('#method').val() == 'jdbc') {
        $('#tables').show();
      } else {
        $('#tables').hide();
      }
      
      $('#method').change(function() {
        if ($(this).val() == 'jdbc') {
          $('#tables').show();
        } else {
          $('#tables').hide();
        }
      });
      
      $('#tables').change(function() {
        if ($(this).val() == '') {
          //$('#qs').text('');
          cm.setValue('');
        } else {
          //$('#qs').text('SELECT * FROM ' + $(this).val() + ';');
          cm.setValue('SELECT * FROM ' + $(this).val() + ';');
        }
      });
    });
  </script>
  <title>Database Query</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isMultipleInstancesAdmin(request)%></c:set>
   <c:choose>
    <c:when test="${isAdmin}">
      <h1>Database query</h1>
      <table class="form">
        <tr>
          <td>
            <form action="DatabaseQuery" method="post" enctype="multipart/form-data">
              <table>
                <tr>
                  <td colspan="2">
                    <textarea cols="75" rows="5" name="qs" id="qs">${qs}</textarea>
                  </td>
                </tr>
                <tr>
                  <td align="left">
                    <select id="tables">
                      <option></option>
                      <c:forEach var="table" items="${tables}">
                        <option value="${table}">${table}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <td align="right">
                    <select name="method" id="method">
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
                  <td><input type="submit" value="Execute SQL script"/></td>
                </tr>
              </table>
            </form>
          </td>
        </tr>
      </table>
      <br/>
      <c:forEach var="gResult" items="${globalResults}">
        <c:if test="${gResult.sql != null}">
          <div class="ok"><center><c:out value="${gResult.sql}"/></center></div>
          <br/>
        </c:if>
        <c:choose>
          <c:when test="${gResult.rows != null}">
            <div class="ok"><center>Row Count: ${gResult.rows}</center></div>
            <br/>
            <c:if test="${not empty gResult.errors}">
              <table class="results" width="100%">
                <tr><th>Line</th><th>SQL</th><th>Error</th></tr>
                <c:forEach var="error" items="${gResult.errors}" varStatus="row">
                <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                  <td>${error.ln}</td>
                  <td>${error.sql}</td>
                  <td>${error.msg}</td>
                </tr>
              </c:forEach>
              </table>
            </c:if>
            <br/>
          </c:when>
          <c:otherwise>
            <table class="results" width="100%">
              <tr>
                <c:forEach var="col" items="${gResult.columns}">
                  <th>${col}</th>
                </c:forEach>
              </tr>
              <c:forEach var="result" items="${gResult.results}" varStatus="row">
                <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
                  <c:forEach var="tp" items="${result}">
                  <td>${tp}</td>
                  </c:forEach>
                </tr>
              </c:forEach>
            </table>
            <br/>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <div class="error"><h3>Only admin users allowed</h3></div>
    </c:otherwise>
  </c:choose>
</body>
</html>