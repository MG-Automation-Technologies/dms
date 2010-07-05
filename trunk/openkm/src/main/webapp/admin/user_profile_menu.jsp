<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Menu</legend>
  <table>
    <tr>
      <td>File visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.fileVisible}">
            <input name="up_menu_file_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Edit visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.editVisible}">
            <input name="up_menu_edit_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Tools visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.toolsVisible}">
            <input name="up_menu_tools_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_tools_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Bookmark visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.bookmarksVisible}">
            <input name="up_menu_bookmarks_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_bookmarks_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Help visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.helpVisible}">
            <input name="up_menu_help_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_help_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>