<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.openkm.core.SessionManager" %>
<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% SessionManager.getInstance().add(request); %>
<% com.openkm.api.OKMAuth.getInstance().login(); %>
<c:redirect url="Handler"/>
