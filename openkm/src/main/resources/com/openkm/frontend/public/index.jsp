<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.api.OKMAuth" %>
<%@ page import="com.openkm.core.Config" %>
<%@ page errorPage="/general-error.jsp" %>
<%
	if (Config.SESSION_MANAGER) {
		if (session.getAttribute("token") == null) {
			String token = OKMAuth.getInstance().login();
			session.setAttribute("token", token);
		}
	}
%>
<jsp:include flush="true" page="index.html" />
