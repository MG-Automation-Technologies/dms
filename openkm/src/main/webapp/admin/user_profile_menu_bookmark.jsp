<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Menu bookmark</legend>
  <table>
    <tr>
      <td>Manage bookmarks visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.bookmark.manageBookmarksVisible}">
            <input name="up_menu_bookmark_manage_bookmarks_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_bookmark_manage_bookmarks_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add bookmark visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.bookmark.addBookmarkVisible}">
            <input name="up_menu_bookmark_add_bookmark_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_bookmark_add_bookmark_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Set home visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.bookmark.setHomeVisible}">
            <input name="up_menu_bookmark_set_home_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_bookmark_set_home_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Go home visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.bookmark.goHomeVisible}">
            <input name="up_menu_bookmark_go_home_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_bookmark_go_home_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>