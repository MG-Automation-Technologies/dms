<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Menu file</legend>
  <table>
    <tr>
      <td>Create folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.createFolderVisible}">
            <input name="up_menu_file_create_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Find folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.findFolderVisible}">
            <input name="up_menu_file_find_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_find_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Go folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.goFolderVisible}">
            <input name="up_menu_file_go_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_go_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Download visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.downloadVisible}">
            <input name="up_menu_file_download_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_download_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Download PDF visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.downloadPdfVisible}">
            <input name="up_menu_file_download_pdf_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_download_pdf_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add document visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.addDocumentVisible}">
            <input name="up_menu_file_add_document_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_add_document_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Purge visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.purgeVisible}">
            <input name="up_menu_file_purge_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_purge_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Purge trash visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.purgeTrashVisible}">
            <input name="up_menu_file_purge_trash_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_purge_trash_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Restore visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.restoreVisible}">
            <input name="up_menu_file_restore_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_restore_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Start workflow visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.startWorkflowVisible}">
            <input name="up_menu_file_start_workflow_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_start_workflow_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Refresh visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.refreshVisible}">
            <input name="up_menu_file_refresh_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_refresh_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Scanner visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.scannerVisible}">
            <input name="up_menu_file_scanner_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_scanner_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Uploader visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.uploaderVisible}">
            <input name="up_menu_file_uploader_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_uploader_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Export visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.exportVisible}">
            <input name="up_menu_file_export_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_export_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Create from template visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.createFromTemplateVisible}">
            <input name="up_menu_file_create_from_template_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_create_from_template_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Send document link visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.sendDocumentLinkVisible}">
            <input name="up_menu_file_send_document_link_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_send_document_link_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Send document attachment visible</td>
      <td>
        <c:choose>
          <c:when test="${up.menu.file.sendDocumentAttachmentVisible}">
            <input name="up_menu_file_send_document_attachment_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_menu_file_send_document_attachment_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>