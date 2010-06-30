<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Dashboard</legend>
  <table>
    <tr>
      <td>User visible</td>
      <td>
        <c:choose>
          <c:when test="${up.dashboard.userVisible}">
            <input name="up_dashboard_user_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_dashboard_user_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Mail visible</td>
      <td>
        <c:choose>
          <c:when test="${up.dashboard.mailVisible}">
            <input name="up_dashboard_mail_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_dashboard_mail_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>News visible</td>
      <td>
        <c:choose>
          <c:when test="${up.dashboard.newsVisible}">
            <input name="up_dashboard_news_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_dashboard_news_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>General visible</td>
      <td>
        <c:choose>
          <c:when test="${up.dashboard.generalVisible}">
            <input name="up_dashboard_general_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_dashboard_general_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Workflow visible</td>
      <td>
        <c:choose>
          <c:when test="${up.dashboard.workflowVisible}">
            <input name="up_dashboard_workflow_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_dashboard_workflow_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Keywords visible</td>
      <td>
        <c:choose>
          <c:when test="${up.dashboard.keywordsVisible}">
            <input name="up_dashboard_keywords_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_dashboard_keywords_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>