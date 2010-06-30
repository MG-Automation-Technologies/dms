<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Tab</legend>
  <table>
    <tr>
      <td>Desktop visible</td>
      <td>
        <c:choose>
          <c:when test="${up.tab.desktopVisible}">
            <input name="up_tab_desktop_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_tab_desktop_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Search visible</td>
      <td>
        <c:choose>
          <c:when test="${up.tab.searchVisible}">
            <input name="up_tab_search_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_tab_search_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Dashboard visible</td>
      <td>
        <c:choose>
          <c:when test="${up.tab.dashboardVisible}">
            <input name="up_tab_dashboard_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_tab_dashboard_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>