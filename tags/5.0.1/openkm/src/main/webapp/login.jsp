<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.Map.Entry"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="com.openkm.core.Config"%>
<%@ page import="com.openkm.util.FormatUtil"%>
<%@ page import="com.openkm.frontend.client.lang.Lang"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  <% if (FormatUtil.isMobile(request)) { %>
  <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0"/>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/mobile.css" type="text/css" />
  <% } else { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/desktop.css" type="text/css" />
  <% } %>
  <% 
    LinkedHashMap<String, String> langs = new LinkedHashMap<String, String>();
    langs.put("Arabic", Lang.LANG_ar_PS);
    langs.put("Bosnian", Lang.LANG_bs_BA);
    langs.put("Català", Lang.LANG_ca_ES);
    langs.put("Chinese simple", Lang.LANG_zh_CN);
    langs.put("Chinese traditional", Lang.LANG_zh_TW);
    langs.put("Czech", Lang.LANG_cs_CZ);
    langs.put("Deutsch", Lang.LANG_de_DE);
    langs.put("Dutch", Lang.LANG_nl_BE);
    langs.put("English", Lang.LANG_en_GB);
    langs.put("English USA", Lang.LANG_en_US);
    langs.put("Español", Lang.LANG_es_ES);
    langs.put("Español Colombia", Lang.LANG_co_ES);
    langs.put("Euskera",Lang.LANG_eu_ES);
    langs.put("Farsi", Lang.LANG_fa_FA);
    langs.put("Français", Lang.LANG_fr_FR);
    langs.put("Gallego", Lang.LANG_gl_ES);
    langs.put("Greece", Lang.LANG_el_GR);
    langs.put("Hungarian", Lang.LANG_hu_HU);
    langs.put("Indonesian", Lang.LANG_id_ID);
    langs.put("Italian", Lang.LANG_it_IT);
    langs.put("Japanese", Lang.LANG_ja_JP);
    langs.put("Latvian", Lang.LANG_lv_LV);
    langs.put("Lithuanian", Lang.LANG_lt_LT);
    langs.put("Macedonian", Lang.LANG_mk_MK);
    langs.put("Polish", Lang.LANG_pl_PL);
    langs.put("Português", Lang.LANG_pt_PT);
    langs.put("Português do Brasil", Lang.LANG_pt_BR);
    langs.put("Romanian", Lang.LANG_ro_RO);
    langs.put("Russian", Lang.LANG_ru_RU);
    langs.put("Serbian", Lang.LANG_sr_BA);
    langs.put("Slovack", Lang.LANG_sk_SK);
    langs.put("Swedish", Lang.LANG_sv_SE);
    langs.put("Thai", Lang.LANG_th_TH);
    langs.put("Turkish", Lang.LANG_tr_TR);
    langs.put("Vietnamese", Lang.LANG_zh_VN);
    Locale locale = request.getLocale();
    Cookie[] cookies = request.getCookies();
    String preset = null;
    
    if (cookies != null) {
    	for (int i=0; i<cookies.length; i++) {
    		if (cookies[i].getName().equals("lang")) {
    			preset = cookies[i].getValue();
    		}
    	}
    }
    
    if (preset == null) {
    	preset = locale.getLanguage()+"-"+locale.getCountry();
    }
  %>
  <title>OpenKM Login</title>
</head>
<body onload="document.forms[0].elements[0].focus()">
  <div id="box">
    <div id="logo"></div>
    <div id="error"><%=request.getParameter("error")!=null?"Authentication error":"" %></div>
    <div id="text">
      <center><img src="<%=request.getContextPath() %>/img/lock.png"/></center>
      <p>Welcome to OpenKM !</p>
      <p>Use a valid user and password to access to OpenKM user Desktop.</p>
    </div>
    <div id="form">
      <form name="login" method="post" action="j_security_check" onsubmit="setCookie()">
      <% if (Config.SYSTEM_MAINTENANCE) { %>
        <table border="0" cellpadding="2" cellspacing="0" align="center" class="demo" style="width: 100%">
        <tr><td class="demo_alert">System under maintenance</td></tr>
        </table>
        <input name="j_username" id="j_username" type="hidden" value="<%=Config.SYSTEM_LOGIN_LOWERCASE?Config.ADMIN_USER.toLowerCase():Config.ADMIN_USER%>"/><br/>
      <% } else { %>
        <label for="j_username">User</label><br/>
        <input name="j_username" id="j_username" type="text" <%=Config.SYSTEM_LOGIN_LOWERCASE?"onchange=\"makeLowercase();\"":""%>/><br/><br/>
      <% } %>
        <label for="j_password">Password</label><br/>
        <input name="j_password" id="j_password" type="password"/><br/><br/>
        <% if (!FormatUtil.isMobile(request)) { %> 
          <label for="j_language">Language</label><br/>
          <select name="j_language" id="j_language">
			<%
				String whole = null;
            	String part = null;
            	
            	// Match whole locale
				for (Entry<String, String> lang : langs.entrySet()) {
					String id = lang.getValue();
					
					if (preset.equalsIgnoreCase(id)) {
						whole = id;
					} else if (preset.substring(0, 2).equalsIgnoreCase(id.substring(0, 2))) {
						part = id;
					}
				}
				
				// Select selected
	            for (Entry<String, String> lang : langs.entrySet()) {
	              String id = lang.getValue();
	              String selected = "";
	              
	              if (whole != null && id.equalsIgnoreCase(whole)) {
	                selected = "selected";
	              } else if (whole == null && part != null && id.equalsIgnoreCase(part)) {
	                selected = "selected";
	              } else if (whole == null && part == null && "en-GB".equals(id)) {
	                selected = "selected";
	              }
	              
	              out.print("<option "+selected+" value=\""+id+"\">"+lang.getKey()+"</option>");
	            }
			%>
          </select>
        <% } %>
        <input value="Login" name="submit" type="submit"/><br/>
      </form>
    </div>
  </div>
  
  <% if (Config.SYSTEM_DEMO) { %>
    <jsp:include flush="true" page="login_demo_users.jsp"/>
  <% } else if (!Config.HIBERNATE_HBM2DDL.equals("none")) { %>
    <table border="0" cellpadding="2" cellspacing="0" align="center" class="demo">
      <tr><td class="demo_title">WARNING</td></tr>
      <tr><td class="demo_alert"><%=Config.PROPERTY_HIBERNATE_HBM2DDL%> = <%=Config.HIBERNATE_HBM2DDL%></td></tr>
    </table>
  <% } %>
  
  <script type="text/javascript">
  	function makeLowercase() {
  	  	var username = document.getElementById('j_username'); 
  	  	username.value = username.value.toLowerCase();
	}

  	function setCookie() {
  	    var exdate = new Date();
  	    var value = document.getElementById('j_language').value;
  	    exdate.setDate(exdate.getDate() + 7);
  	    document.cookie="lang="+escape(value)+";expires="+exdate.toUTCString();
  	}
  </script>
</body>
</html>
