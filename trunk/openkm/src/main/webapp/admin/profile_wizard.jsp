<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Wizard</legend>
  <table>
    <tr>
      <td>Property groups</td>
      <td>
        <input name="prf_wizard_property_groups" size="32" value="${prf.wizard.propertyGroups}"/>
      </td>
    </tr>
    <tr>
      <td>Keywords</td>
      <td>
        <c:choose>
          <c:when test="${prf.wizard.keywordsEnabled}">
            <input name="prf_wizard_keywords" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="prf_wizard_keywords" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Categories</td>
      <td>
        <c:choose>
          <c:when test="${prf.wizard.categoriesEnabled}">
            <input name="prf_wizard_categories" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="prf_wizard_categories" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>