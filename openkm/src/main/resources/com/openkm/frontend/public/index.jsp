<%@ page import="com.openkm.api.OKMAuth" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("token") == null) {
		String token = OKMAuth.getInstance().login();
		session.setAttribute("token", token);
	}
%>
