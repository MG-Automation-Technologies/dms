<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="general-error.jsp"%>
<%@ page import="com.openkm.core.Config"%>
<%@ page import="com.openkm.util.FormatUtil"%>
<%
	String url = null;
	String docPath = request.getParameter("docPath");
	
	if (FormatUtil.isMobile(request)) {
		url = "mobi/index.jsp";
	} else {
		url = "com.openkm.frontend.Main/index.jsp";
	}
	
	if (docPath != null) {
		url += "?docPath=" + URLEncoder.encode(docPath, "UTF-8");
	}
	
	if (!Config.DEFAULT_LANG.equals("")) {
		if (docPath != null) {
			url += "&lang=" + Config.DEFAULT_LANG;
		} else {
			url += "?lang=" + Config.DEFAULT_LANG;
		}
	}
	
	response.sendRedirect(url);
%>
