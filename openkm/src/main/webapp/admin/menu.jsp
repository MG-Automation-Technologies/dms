<%@page import="com.openkm.principal.DatabasePrincipalAdapter"%>
<%@page import="com.openkm.core.Config"%>
<table width="100%" border="0">
  <tr><td align="center">
  <a target="frame" href="home.jsp"><img src="img/toolbar/home.png" title="Home"></a>
  &nbsp;
  <a target="frame" href="config.jsp"><img src="img/toolbar/config.png" title="Configuration"></a>
  &nbsp;
  <a target="frame" href="MimeType"><img src="img/toolbar/mime.png" title="Mime types"></a>
  &nbsp;
  <a target="frame" href="stats.jsp"><img src="img/toolbar/stats.png" title="Statistics"></a>
  &nbsp;
  <a target="frame" href="scripting.jsp"><img src="img/toolbar/scripting.png" title="Scripting"></a>
  &nbsp;
  <a target="frame" href="search.jsp"><img src="img/toolbar/search.png" title="Repository Search"></a>
  &nbsp;
  <a target="frame" href="RepositoryView"><img src="img/toolbar/view.png" title="Repository view"></a>
  &nbsp;
  <a target="frame" href="PropertyGroups"><img src="img/toolbar/properties.png" title="Property groups"></a>
  &nbsp;
  <a target="frame" href="LoggedUsers"><img src="img/toolbar/logged.png" title="Logged users"></a>
  &nbsp;
<% if (Config.PRINCIPAL_ADAPTER.equals(DatabasePrincipalAdapter.class.getCanonicalName())) { %>
  <a target="frame" href="Auth"><img src="img/toolbar/users.png" title="Users"></a>
  &nbsp;
<% } %>
  <a target="frame" href="DatabaseQuery"><img src="img/toolbar/database.png" title="Database query"></a>
  &nbsp;
  <a target="frame" href="report.jsp"><img src="img/toolbar/report.png" title="Reports"></a>
  &nbsp;
  <a target="frame" href="ActivityLog"><img src="img/toolbar/activity.png" title="Activity log"></a>
  &nbsp;
  <a target="frame" href="Workflow"><img src="img/toolbar/workflow.png" title="Workflow"></a>
  &nbsp;
  <a target="frame" href="generate_thesaurus.jsp"><img src="img/toolbar/thesaurus.png" title="Generate thesaurus"></a>
  &nbsp;
  <a target="frame" href="repository_import.jsp"><img src="img/toolbar/import.png" title="Repository import"></a>
  &nbsp;
  <a target="frame" href="repository_export.jsp"><img src="img/toolbar/export.png" title="Repository export"></a>
  &nbsp;
  <a target="frame" href="repository_backup.jsp"><img src="img/toolbar/backup.png" title="Repository backup"></a>
  <%-- &nbsp; --%>
  <%-- <a href="<%=request.getContextPath()%>/"><img src="img/toolbar/exit.png" title="Exit"></a> --%>
  </td></tr>
</table>