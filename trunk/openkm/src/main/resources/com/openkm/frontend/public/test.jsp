<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.openkm.api.OKMAuth" %>
<%@page import="com.openkm.core.UserAlreadyLoggerException" %>
<%
	try {
		if (session.getAttribute("token")==null) {
			session.setAttribute("token", OKMAuth.getInstance().login());
		}
	} catch (UserAlreadyLoggerException e) {
		%>User already logged<%
	}
%>