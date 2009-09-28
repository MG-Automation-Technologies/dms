<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="general-error.jsp"%>
<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="es.git.openkm.util.FormatUtil"%>
<%
	String url = null;
	
	if (FormatUtil.isMobile(request)) {
		url = "mobi/index.jsp";
	} else {
		url = "es.git.openkm.frontend.Main/index.jsp";
	}
	
	if (!Config.DEFAULT_LANG.equals("")) {
		url += "?lang=" + Config.DEFAULT_LANG;
	}
	
	response.sendRedirect(url);
%>
