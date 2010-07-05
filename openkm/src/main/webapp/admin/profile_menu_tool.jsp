<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Menu tool</legend>
  <table>
    <tr>
      <td>Change skin visible</td>
      <td>
        <c:choose>
          <c:when test="${prf.menu.tool.changeSkinVisible}">
            <input name="prf_menu_tool_change_skin_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="prf_menu_tool_change_skin_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Debug visible</td>
      <td>
        <c:choose>
          <c:when test="${prf.menu.tool.debugVisible}">
            <input name="prf_menu_tool_debug_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="prf_menu_tool_debug_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Administration visible</td>
      <td>
        <c:choose>
          <c:when test="${prf.menu.tool.administrationVisible}">
            <input name="prf_menu_tool_administration_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="prf_menu_tool_administration_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>