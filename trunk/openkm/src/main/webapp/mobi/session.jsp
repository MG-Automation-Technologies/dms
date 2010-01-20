<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp" %>
<%@page import="com.openkm.api.OKMAuth"%>
<% 
  if (session.getAttribute("token") == null) {
    String token = OKMAuth.getInstance().login();
    session.setAttribute("token", token);
  }
%>
