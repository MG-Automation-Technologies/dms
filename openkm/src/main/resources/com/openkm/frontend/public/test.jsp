<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.openkm.api.OKMAuth" %>
<%
	if (session.getAttribute("token")==null) {
		session.setAttribute("token", OKMAuth.getInstance().login());
	}
%>