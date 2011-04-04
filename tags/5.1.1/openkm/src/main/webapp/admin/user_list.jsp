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
  <title>User List</title>
</head>
<body>
  <c:set var="isAdmin"><%=BaseServlet.isAdmin(request)%></c:set>
  <c:choose>
    <c:when test="${isAdmin}">
      <c:url value="Auth" var="urlRoleList">
        <c:param name="action" value="roleList"/>
      </c:url>
      <h1>User list <span style="font-size: 10px;">(<a href="${urlRoleList}">Roles</a>)</span></h1>
      <c:url value="Auth" var="urlUserList">
        <c:param name="action" value="userList"/>
      </c:url>
      <form action="${urlUserList}">
        <table class="form">
          <tr>
            <td>Role</td>
            <td>
              <select name="roleFilter">
                <option value=""></option>
                <c:forEach var="role" items="${roles}">
                  <c:choose>
                    <c:when test="${role.id == roleFilter}">
                      <option value="${role.id}" selected="selected">${role.id}</option>
                    </c:when>
                    <c:otherwise>
                      <option value="${role.id}">${role.id}</option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
            </td>
          </tr>
          <tr><td colspan="2" align="right"><input type="submit" value="Seach"/></td></tr>
        </table>
      </form>
      <br/>
      <table class="results" width="80%">
        <tr>
          <th>Id</th><th>Name</th><th>Mail</th><th>Roles</th><th width="25px">Active</th>
          <th width="130px">
            <c:url value="Auth" var="urlCreate">
              <c:param name="action" value="userCreate"/>
            </c:url>
            <c:if test="${db}">
              <a href="${urlCreate}"><img src="img/action/new.png" alt="New user" title="New user"/></a>
            </c:if>
          </th>
        </tr>
        <c:forEach var="user" items="${users}" varStatus="row">
          <c:url value="Auth" var="urlEdit">
            <c:param name="action" value="userEdit"/>
            <c:param name="usr_id" value="${user.id}"/>
          </c:url>
          <c:url value="Auth" var="urlDelete">
            <c:param name="action" value="userDelete"/>
            <c:param name="usr_id" value="${user.id}"/>
          </c:url>
          <c:url value="Auth" var="urlActive">
            <c:param name="action" value="userActive"/>
            <c:param name="usr_id" value="${user.id}"/>
            <c:param name="roleFilter" value="${roleFilter}"/>
            <c:param name="usr_active" value="${!user.active}"/>
          </c:url>
          <c:url value="UserConfig" var="urlConfig">
            <c:param name="uc_user" value="${user.id}"/>
          </c:url>
          <c:url value="MailAccount" var="urlMail">
            <c:param name="ma_user" value="${user.id}"/>
          </c:url>
          <c:url value="TwitterAccount" var="urlTwitter">
            <c:param name="ta_user" value="${user.id}"/>
          </c:url>
          <tr class="${row.index % 2 == 0 ? 'even' : 'odd'}">
            <td>${user.id}</td><td>${user.name}</td><td>${user.email}</td>
            <td>
              <c:forEach var="role" items="${user.roles}">
                ${role.id}
              </c:forEach>
            </td>
            <td align="center">
              <c:choose>
                <c:when test="${user.active}">
                  <a href="${urlActive}"><img src="img/true.png" alt="Active" title="Active"/></a>
                </c:when>
                <c:otherwise>
                  <a href="${urlActive}"><img src="img/false.png" alt="Inactive" title="Inactive"/></a>
                </c:otherwise>
              </c:choose>
            </td>
            <td align="center">
              <c:if test="${db}">
                <a href="${urlEdit}"><img src="img/action/edit.png" alt="Edit" title="Edit"/></a>
                &nbsp;
                <a href="${urlDelete}"><img src="img/action/delete.png" alt="Delete" title="Delete"/></a>
                &nbsp;
              </c:if>
              <a href="${urlConfig}"><img src="img/action/config.png" alt="User config" title="User config"/></a>
              &nbsp;
              <a href="${urlMail}"><img src="img/action/email.png" alt="Mail accounts" title="Mail accounts"/></a>
              &nbsp;
              <a href="${urlTwitter}"><img src="img/action/twitter.png" alt="Twitter accounts" title="Twitter accounts"/></a>
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