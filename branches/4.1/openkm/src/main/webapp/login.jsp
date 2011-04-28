<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="es.git.openkm.core.Config"%>
<%@ page import="es.git.openkm.util.FormatUtil"%>
<%@ page import="es.git.openkm.frontend.client.lang.Lang"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="Shortcut icon" href="<%=request.getContextPath() %>/favicon.ico" />
  <% if (FormatUtil.isMobile(request)) { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/mobile.css" type="text/css" />
  <% } else { %>
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/desktop.css" type="text/css" />
  <% } %>
  <% 
  	LinkedHashMap<String, String> langs = new LinkedHashMap<String, String>();
    langs.put("Bosnian", Lang.LANG_bs_BA);
    langs.put("Català", Lang.LANG_ca_ES);
    langs.put("Chinese simple", Lang.LANG_zh_CN);
    langs.put("Chinese traditional", Lang.LANG_zh_TW);
    langs.put("Deutsch", Lang.LANG_de_DE);
    langs.put("English", Lang.LANG_en_GB);
    langs.put("Español", Lang.LANG_es_ES);
    langs.put("Español Colombia", Lang.LANG_co_ES);
    langs.put("Farsi", Lang.LANG_fa_FA);
    langs.put("Français", Lang.LANG_fr_FR);
    langs.put("Gallego", Lang.LANG_gl_ES);
    langs.put("Greece", Lang.LANG_el_GR);
    langs.put("Hungarian", Lang.LANG_hu_HU);
    langs.put("Italian", Lang.LANG_it_IT);
    langs.put("Japanese", Lang.LANG_ja_JP);
    langs.put("Latvian", Lang.LANG_lv_LV);
    langs.put("Macedonian", Lang.LANG_mk_MK);
    langs.put("Nederlands", Lang.LANG_nl_NL);
    langs.put("Polish", Lang.LANG_pl_PL);
    langs.put("Português do Brasil", Lang.LANG_pt_BR);
    langs.put("Romanian", Lang.LANG_ro_RO);
    langs.put("Russian", Lang.LANG_ru_RU);
    langs.put("Serbian", Lang.LANG_sr_BA);
    langs.put("Swedish", Lang.LANG_sv_SE);
    langs.put("Turkish", Lang.LANG_tr_TR);
    Locale locale = request.getLocale();
    Cookie[] cookies = request.getCookies();
    String preset = null;
    
    for (int i=0; i<cookies.length; i++) {
    	if (cookies[i].getName().equals("lang")) {
    		preset = cookies[i].getValue();
    	}
    }
    
    if (preset == null) {
    	preset = locale.getLanguage()+"_"+locale.getCountry();
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
        <label for="j_username">User</label><br/>
        <input name="j_username" id="j_username" type="text" <%=(Config.SYSTEM_LOGIN_LOWERCASE.equals("on"))?"onkeyup=\"makeLowercase();\"":""%>/><br/><br/>
        <label for="j_password">Password</label><br/>
        <input name="j_password" id="j_password" type="password"/><br/><br/>
        <% if (!FormatUtil.isMobile(request)) { %> 
          <label for="j_language">Language</label><br/>
          <select name="j_language" id="j_language">
			<%
				for (Iterator<String> it = langs.keySet().iterator(); it.hasNext(); ) {
					String key = it.next();
					String value = langs.get(key);
					String selected = "";
					if (preset.equalsIgnoreCase(value) || preset.substring(0, 2).equalsIgnoreCase(value.substring(0, 2))) {
						selected = "selected";
					}
					out.print("<option "+selected+" value=\""+value+"\">"+key+"</option>");
				}
			%>
          </select>
        <% } %>
        <input value="Login" name="submit" type="submit"/><br/>
      </form>
    </div>
  </div>

  <% if (Config.SYSTEM_DEMO.equalsIgnoreCase("on")) { %>
    <jsp:include flush="true" page="login_demo_users.jsp"/>
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

  	function getBrowserLanguage() {
  	    var lang = navigator.language ? navigator.language : navigator.userLanguage;
	    if (lang) {
		    return lang;
		} else {
	  		return "en";
		}
  	}
  </script>
</body>
</html>