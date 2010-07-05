<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Menu edit</legend>
  <table>
    <tr>
      <td>Lock visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.lockVisible}">
            <input name="up_menu_edit_lock_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_lock_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Unlock visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.unlockVisible}">
            <input name="up_menu_edit_unlock_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_unlock_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Check-in visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.checkInVisible}">
            <input name="up_menu_edit_check_in_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_check_in_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Check-out visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.checkOutVisible}">
            <input name="up_menu_edit_check_out_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_check_out_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Cancel check-out visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.cancelCheckOutVisible}">
            <input name="up_menu_edit_cancel_check_out_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_cancel_check_out_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Delete visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.deleteVisible}">
            <input name="up_menu_edit_delete_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_delete_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Rename visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.renameVisible}">
            <input name="up_menu_edit_rename_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_rename_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Copy visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.copyVisible}">
            <input name="up_menu_edit_copy_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_copy_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Move visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.moveVisible}">
            <input name="up_menu_edit_move_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_move_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add subscription visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.addSubscriptionVisible}">
            <input name="up_menu_edit_add_subscription_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_add_subscription_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Remove subscription visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.removeSubscriptionVisible}">
            <input name="up_menu_edit_remove_subscription_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_remove_subscription_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add property group visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.addPropertyGroupVisible}">
            <input name="up_menu_edit_add_property_group_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_add_property_group_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Remove property group visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.edit.removePropertyGroupVisible}">
            <input name="up_menu_edit_remove_property_group_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_edit_remove_property_group_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>