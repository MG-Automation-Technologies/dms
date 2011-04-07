<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Misc</legend>
  <table>
    <tr>
      <td>Quota limit</td>
      <td><input class=":integer :only_on_blur" name="prf_misc_user_quota" value="${prf.misc.userQuota}"/></td>
    </tr>
    <tr>
      <td>Advanced filters</td>
      <td>
        <c:choose>
          <c:when test="${prf.misc.advancedFilters}">
            <input name="prf_misc_advanced_filter" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="prf_misc_advanced_filter" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Web skin</td>
      <td>
        <select name="prf_misc_web_skin">
          <c:choose>
            <c:when test="${prf.misc.webSkin == 'default'}">
              <option value="default" selected="selected">default</option>
            </c:when>
            <c:otherwise><option value="default">default</option></c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${prf.misc.webSkin == 'test'}">
              <option value="test" selected="selected">test</option>
            </c:when>
            <c:otherwise><option value="test">test</option></c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${prf.misc.webSkin == 'mediumfont'}">
              <option value="mediumfont" selected="selected">mediumfont</option>
            </c:when>
            <c:otherwise><option value="mediumfont">mediumfont</option></c:otherwise>
          </c:choose>
          <c:choose>
            <c:when test="${prf.misc.webSkin == 'bigfont'}">
              <option value="bigfont" selected="selected">bigfont</option>
            </c:when>
            <c:otherwise><option value="bigfont">bigfont</option></c:otherwise>
          </c:choose>
        </select>
      </td>
    </tr>
  </table>
</fieldset>