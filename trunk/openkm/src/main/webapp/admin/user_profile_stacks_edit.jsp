<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset>
  <legend>Stacks</legend>
  <table>
    <tr>
      <td>Categories visible</td>
      <td>
        <c:choose>
          <c:when test="${up.stackCategoriesVisible}">
            <input name="up_stack_categories_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_stack_categories_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Thesaurus visible</td>
      <td>
        <c:choose>
          <c:when test="${up.stackThesaurusVisible}">
            <input name="up_stack_thesaurus_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_stack_thesaurus_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Templates visible</td>
      <td>
        <c:choose>
          <c:when test="${up.stackTemplatedVisible}">
            <input name="up_stack_templates_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_stack_templates_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Personal visible</td>
      <td>
        <c:choose>
          <c:when test="${up.stackPersonalVisible}">
            <input name="up_stack_personal_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_stack_personal_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
    <tr>
      <td>Mail visible</td>
      <td>
        <c:choose>
          <c:when test="${up.stackMailVisible}">
            <input name="up_stack_mail_visible" type="checkbox" checked="checked"/>
          </c:when>
          <c:otherwise>
            <input name="up_stack_mail_visible" type="checkbox"/>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</fieldset>