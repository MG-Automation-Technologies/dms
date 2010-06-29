<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
 <legend>User Quota</legend>
 <table>
   <tr>
      <td>Quota enabled</td>
      <td>
        <c:choose>
          <c:when test="${up.userQuotaEnabled}">
            <input name="up_user_quota_enabled" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_user_quota_enabled" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Quota size</td>
      <td><input name="up_user_quota_limit" size="10" value="${up.userQuotaLimit}"/></td>
    </tr>
 </table>
</fieldset>