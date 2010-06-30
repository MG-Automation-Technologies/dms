<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Toolbar</legend>
  <table>
    <tr>
      <td>Create folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.createFolderVisible}">
            <input name="up_toolbar_create_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_create_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Find folder visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.findFolderVisible}">
            <input name="up_toolbar_find_folder_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_find_folder_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Download visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.downloadVisible}">
            <input name="up_toolbar_download_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_download_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Download PDF visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.downloadPdfVisible}">
            <input name="up_toolbar_download_pdf_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_download_pdf_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Lock visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.lockVisible}">
            <input name="up_toolbar_lock_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_lock_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Unlock visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.unlockVisible}">
            <input name="up_toolbar_unlock_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_unlock_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>

    <tr>
      <td>Add document visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.addDocumentVisible}">
            <input name="up_toolbar_add_document_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_add_document_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Check-in visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.checkInVisible}">
            <input name="up_toolbar_check_in_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_check_in_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Check-out visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.checkOutVisible}">
            <input name="up_toolbar_check_out_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_check_out_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Cancel check-out visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.cancelCheckOutVisible}">
            <input name="up_toolbar_cancel_check_out_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_cancel_check_out_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Delete visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.deleteVisible}">
            <input name="up_toolbar_delete_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_delete_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Add property group visible</td>
      <td>
        <c:choose>
          <c:when test="${up.toolbar.addPropertyGroupVisible}">
            <input name="up_toolbar_add_property_group_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_toolbar_add_property_group_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>

  </table>
</fieldset>