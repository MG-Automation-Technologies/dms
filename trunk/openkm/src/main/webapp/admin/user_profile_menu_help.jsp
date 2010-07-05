<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Menu help</legend>
  <table>
    <tr>
      <td>Documentation visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.documentationVisible}">
            <input name="up_menu_help_documentation_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_documentation_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Bug tracking visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.bugTrackingVisible}">
            <input name="up_menu_help_bug_tracking_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_bug_tracking_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Support visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.supportVisible}">
            <input name="up_menu_help_support_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_support_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Forum visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.forumVisible}">
            <input name="up_menu_help_forum_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_forum_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Changelog visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.changelogVisible}">
            <input name="up_menu_help_changelog_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_changelog_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Web site visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.webSiteVisible}">
            <input name="up_menu_help_web_site_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_web_site_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>About visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.help.aboutVisible}">
            <input name="up_menu_help_about_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_about_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>