<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.SessionManager" %>
<%@ page errorPage="/general-error.jsp" %>
<% SessionManager.getInstance().add(request); %>
<% com.openkm.api.OKMAuth.getInstance().login(); %>
<jsp:include flush="true" page="index.html" />
