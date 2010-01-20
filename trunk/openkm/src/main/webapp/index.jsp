<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="general-error.jsp"%>
<%@ page import="com.openkm.core.Config"%>
<%@ page import="com.openkm.util.FormatUtil"%>
<%
	String url = null;
	
	if (FormatUtil.isMobile(request)) {
		url = "mobi/index.jsp";
	} else {
		url = "com.openkm.frontend.Main/index.jsp";
	}
	
	if (!Config.DEFAULT_LANG.equals("")) {
		url += "?lang=" + Config.DEFAULT_LANG;
	}
	
	response.sendRedirect(url);
%>
