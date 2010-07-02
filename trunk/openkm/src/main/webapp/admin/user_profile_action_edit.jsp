<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Action</legend>
  <table>
    <tr>
      <td>Create folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.createFolderVisible}">
            <input name="up_action_create_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_create_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Find folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.findFolderVisible}">
            <input name="up_action_find_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_find_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Go folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.goFolderVisible}">
            <input name="up_action_go_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_go_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Download visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.downloadVisible}">
            <input name="up_action_download_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_download_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Download PDF visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.downloadPdfVisible}">
            <input name="up_action_download_pdf_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_download_pdf_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Lock visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.lockVisible}">
            <input name="up_action_lock_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_lock_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Unlock visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.unlockVisible}">
            <input name="up_action_unlock_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_unlock_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add document visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.addDocumentVisible}">
            <input name="up_action_add_document_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_add_document_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Delete visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.deleteVisible}">
            <input name="up_action_delete_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_delete_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Check-in visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.checkInVisible}">
            <input name="up_action_check_in_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_check_in_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Check-out visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.checkOutVisible}">
            <input name="up_action_check_out_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_check_out_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Cancel check-out visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.cancelCheckOutVisible}">
            <input name="up_action_cancel_check_out_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_cancel_check_out_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Rename visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.renameVisible}">
            <input name="up_action_rename_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_rename_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Copy visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.copyVisible}">
            <input name="up_action_copy_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_copy_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Move visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.moveVisible}">
            <input name="up_action_move_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_move_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Purge visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.purgeVisible}">
            <input name="up_action_purge_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_purge_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Purge trash visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.purgeTrashVisible}">
            <input name="up_action_purge_trash_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_purge_trash_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Restore visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.restoreVisible}">
            <input name="up_action_restore_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_restore_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add property group visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.addPropertyGroupVisible}">
            <input name="up_action_add_property_group_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_add_property_group_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Remove property group visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.removePropertyGroupVisible}">
            <input name="up_action_remove_property_group_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_remove_property_group_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Start workflow visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.startWorkflowVisible}">
            <input name="up_action_start_workflow_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_start_workflow_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add subscription visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.addSubscriptionVisible}">
            <input name="up_action_add_subscription_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_add_subscription_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Remove subscription visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.removeSubscriptionVisible}">
            <input name="up_action_remove_subscription_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_remove_subscription_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Refresh visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.refreshVisible}">
            <input name="up_action_refresh_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_refresh_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Go home visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.goHomeVisible}">
            <input name="up_action_go_home_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_go_home_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Set home visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.setHomeVisible}">
            <input name="up_action_set_home_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_set_home_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add bookmark visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.addBookmarkVisible}">
            <input name="up_action_add_bookmark_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_add_bookmark_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Manage bookmarks visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.manageBookmarksVisible}">
            <input name="up_action_manage_bookmarks_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_manage_bookmarks_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Scanner visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.scannerVisible}">
            <input name="up_action_scanner_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_scanner_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Uploader visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.uploaderVisible}">
            <input name="up_action_uploader_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_uploader_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Export visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.exportVisible}">
            <input name="up_action_export_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_export_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Create from template visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.createFromTemplateVisible}">
            <input name="up_action_create_from_template_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_create_from_template_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Send document link visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.sendDocumentLinkVisible}">
            <input name="up_action_send_document_link_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_send_document_link_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Send document attachment visible</td>
      <td>
        <c:choose>
          <c:when test="${up.action.sendDocumentAttachmentVisible}">
            <input name="up_action_send_document_attachment_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_action_send_document_attachment_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>