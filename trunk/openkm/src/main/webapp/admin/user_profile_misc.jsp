<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Misc</legend>
  <table>
    <tr>
      <td>Quota limit</td>
      <td><input class=":integer :only_on_blur" name="up_user_quota" value="${up.misc.userQuota}"/></td>
    </tr>
    <tr>
      <td>Advanced filters</td>
      <td>
        <c:choose>
          <c:when test="${up.misc.advancedFilters}">
            <input name="up_advanced_filter" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_advanced_filter" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>