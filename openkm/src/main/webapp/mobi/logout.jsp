<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp" %>
<%@page import="es.git.openkm.api.OKMAuth"%>
<%
  String token = (String) session.getAttribute("token");
  OKMAuth okmAuth = OKMAuth.getInstance();
  okmAuth.logout(token);
  session.invalidate();
  response.sendRedirect(request.getContextPath());
%>