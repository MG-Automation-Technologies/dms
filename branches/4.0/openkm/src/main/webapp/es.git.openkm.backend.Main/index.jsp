<%@ page import="es.git.openkm.api.OKMAuth" %>
<%@ page errorPage="/general-error.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("token") == null) {
		String token = OKMAuth.getInstance().login();
		session.setAttribute("token", token);
	}
%>
<jsp:include flush="true" page="index.html" />
