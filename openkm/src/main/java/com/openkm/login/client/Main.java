/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.login.client;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * @author jllort
 *
 */
public class Main implements EntryPoint {
	
	private static Main singleton;
	
	/**
	 * @return singleton Main instance 
	 */
	public static Main get() {
		return singleton;
	}
	
	// Languages
	public static final String LANG_es_ES = "es-ES"; 
	public static final String LANG_ca_ES = "ca-ES";
	public static final String LANG_gl_ES = "gl-ES";
	public static final String LANG_en_GB = "en-GB";
	public static final String LANG_fr_FR = "fr-FR";
	public static final String LANG_pt_BR = "pt-BR";
	public static final String LANG_nl_NL = "nl-NL";
	public static final String LANG_fa_FA = "fa-FA";
	public static final String LANG_de_DE = "de-DE";
	public static final String LANG_it_IT = "it-IT";
	public static final String LANG_zh_CN = "zh-CN";
	public static final String LANG_sv_SE = "sv-SE";
	public static final String LANG_sr_BA = "sr-BA";
	public static final String LANG_tr_TR = "tr-TR";
	public static final String LANG_ja_JP = "ja-JP";
	public static final String LANG_ro_RO = "ro-RO";
	public static final String LANG_pl_PL = "pl-PL";
	public static final String LANG_hu_HU = "hu-HU";
	public static final String LANG_el_GR = "el-GR";
	public static final String LANG_zh_TW = "zh-TW";
	public static final String LANG_lv_LV = "lv-LV";
	public static final String LANG_mk_MK = "mk-KM";
	public static final String LANG_co_ES = "co-ES";
	public static final String LANG_ru_RU = "ru-RU";
	public static final String LANG_bs_BA = "bs-BA";
	public static final String LANG_cs_CZ = "cs-CZ";
	public static final String LANG_en_US = "en-US";
	
	public static final String LOGIN_PAGE_TEXT	= "<title>OpenKM Login</title>";
	
	public VerticalPanel vPanel = new VerticalPanel();
	public HTML msgError = new HTML("Authentication");
	public HTML msgError1 = new HTML("error");
	public Status status = new Status();
	public final FormPanel formPanel = new FormPanel();
	public final static Map<String,String> languages = new LinkedHashMap<String,String>();
	private LoginImageBundle loginImageBundle = (LoginImageBundle) GWT.create(LoginImageBundle.class);
	
	public void onModuleLoad() {
		VerticalPanel vPanelData = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		final TextBox userName = new TextBox();
		PasswordTextBox password = new PasswordTextBox();
		Button loginButton = new Button("Login");
		Image lock = loginImageBundle.lockIcon().createImage();
		Image logo = loginImageBundle.logoBigIcon().createImage();
		FlexTable table = new FlexTable();
		final ListBox langList = new ListBox();
		final String urlJump = GWT.getHostPageBaseURL().substring(0, GWT.getHostPageBaseURL().indexOf("/OpenKM/")+8) + "com.openkm.frontend.Main/index.html";
		final String urlJumpMobi = GWT.getHostPageBaseURL().substring(0, GWT.getHostPageBaseURL().indexOf("/OpenKM/")+8) + "/mobi/index.jsp";
		final String urlTest = GWT.getHostPageBaseURL().substring(0, GWT.getHostPageBaseURL().indexOf("/OpenKM/")+8) + "com.openkm.frontend.Main/test.jsp";
		final boolean isMobile = getIsMobil().equals("on");

		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				submit();
			}
		});
		
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, urlTest);
				rb.setCallback(new RequestCallback() {
					public void onResponseReceived(Request request, Response response) {
						if (!response.getText().contains(LOGIN_PAGE_TEXT)) {
							String urlToJump = "";
							// Setting jump base url
							if (isMobile) {
								urlToJump = urlJumpMobi;
							} else {
								urlToJump = urlJump;
							}
							// Setting language
							urlToJump += "?lang="+langList.getValue(langList.getSelectedIndex());
							
							// Setting docpath or folderpath
							if (!getDocPath().equals("")) {
								urlToJump += "&docPath=" + getDocPath();
							}
							if (!getFldPath().equals("")) {
								urlToJump += "&fldPath=" + getFldPath();
							}
							Window.Location.assign(urlToJump);
						} else  {
							msgError.setVisible(true);
							msgError1.setVisible(true);
						}
						status.unsetFlagTryAutentication();
					}
		
					public void onError(Request request, Throwable caught) {
						Window.alert("Error on response receibed :" + caught.getMessage());
						throw new UnsupportedOperationException("Not supported yet.");
					}
				});
		
				try {
					rb.send();
				} catch (RequestException ex) {
					Window.alert("Error on request :" + ex.getMessage());
				}
			}
		});
		
		userName.setName("j_username");
		password.setName("j_password");
		formPanel.setAction("j_security_check");
		formPanel.setMethod(FormPanel.METHOD_POST);
		
		languages.put(LANG_bs_BA,"Bosnian");
		languages.put(LANG_ca_ES,"Català");
		languages.put(LANG_zh_CN,"Chinese simple");
		languages.put(LANG_zh_TW,"Chinese traditional");
		languages.put(LANG_cs_CZ,"Czech");
		languages.put(LANG_de_DE,"Deutsch");
		languages.put(LANG_en_GB,"English");
		languages.put(LANG_en_US,"English USA");
		languages.put(LANG_es_ES,"Español");
		languages.put(LANG_co_ES,"Español Colombia");
		languages.put(LANG_fa_FA,"Farsi");
		languages.put(LANG_fr_FR,"Français");
		languages.put(LANG_gl_ES,"Gallego");
		languages.put(LANG_el_GR,"Greece");
		languages.put(LANG_hu_HU,"Hungarian");
		languages.put(LANG_it_IT,"Italian");
		languages.put(LANG_ja_JP,"Japanese");
		languages.put(LANG_lv_LV,"Latvian");
		languages.put(LANG_mk_MK,"Macedonian");
		languages.put(LANG_nl_NL,"Nederlands");
		languages.put(LANG_pl_PL,"Polish");
		languages.put(LANG_pt_BR,"Português do Brasil");
		languages.put(LANG_ro_RO,"Romanian");
		languages.put(LANG_ru_RU,"Russian");
		languages.put(LANG_sr_BA,"Serbian");
		languages.put(LANG_sv_SE,"Swedish");
		languages.put(LANG_tr_TR,"Turkish");
		
		for (Iterator<String> it = languages.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			langList.addItem(languages.get(key), key );
		}
		
		table.setCellPadding(0);
		table.setCellSpacing(0);
		
		table.setWidget(0, 0, new HTML("<b>User name</b>"));
		table.setWidget(1, 0, userName);
		table.setWidget(2, 0, new HTML("&nbsp;"));
		table.setWidget(3, 0, new HTML("<b>Password</b>"));
		table.setWidget(4, 0, password);
		table.setWidget(5, 0, new HTML("&nbsp;"));
		table.setWidget(6, 0, new HTML("<b>Language</b>"));
		table.setWidget(7, 0, langList);
		table.setWidget(8, 0, new HTML("&nbsp;"));
		table.setWidget(9, 0, loginButton);
		
		userName.setWidth("200px");
		password.setWidth("200px");
		langList.setWidth("200px");
		
		table.getCellFormatter().setHeight(2, 0, "10");
		table.getCellFormatter().setHeight(5, 0, "10");
		table.getCellFormatter().setHeight(7, 0, "10");
		table.getCellFormatter().setHorizontalAlignment(9, 0, HasAlignment.ALIGN_RIGHT);
		table.setWidth("200px");
		
		SimplePanel spTable = new SimplePanel();
		spTable.add(table);
		
		HTML msg = new HTML("Welcome to OpenKM !");
		HTML msg1 = new HTML("Use a valid user and");
		HTML msg2 = new HTML("password to access to");
		HTML msg3 = new HTML("OpenKM user Desktop.");
		
		formPanel.add(spTable);
		VerticalPanel leftPanel = new VerticalPanel();
		leftPanel.add(lock);
		leftPanel.add(msg);
		leftPanel.add(new HTML("&nbsp;"));
		leftPanel.add(msg1);
		leftPanel.add(msg2);
		leftPanel.add(msg3);
		leftPanel.add(new HTML("&nbsp;"));
		leftPanel.add(msgError);
		leftPanel.add(msgError1);
		
		msgError.setVisible(false);
		msgError1.setVisible(false);
		
		leftPanel.setCellHorizontalAlignment(lock, HasAlignment.ALIGN_CENTER);
		leftPanel.setCellHorizontalAlignment(msg, HasAlignment.ALIGN_CENTER);
		leftPanel.setCellHorizontalAlignment(msg1, HasAlignment.ALIGN_CENTER);
		leftPanel.setCellHorizontalAlignment(msg2, HasAlignment.ALIGN_CENTER);
		leftPanel.setCellHorizontalAlignment(msg3, HasAlignment.ALIGN_CENTER);
		leftPanel.setCellHorizontalAlignment(msgError, HasAlignment.ALIGN_CENTER);
		leftPanel.setCellHorizontalAlignment(msgError1, HasAlignment.ALIGN_CENTER);
		
		hPanel.add(leftPanel);
		hPanel.add(formPanel);
		vPanelData.add(hPanel);
		
		hPanel.setCellWidth(leftPanel, "150px");
		hPanel.setCellHorizontalAlignment(leftPanel, HasAlignment.ALIGN_CENTER);
		
		vPanel.add(logo);
		vPanel.add(vPanelData);
		
		vPanel.setCellHeight(logo, "125");
		vPanel.setCellHorizontalAlignment(logo, HasAlignment.ALIGN_CENTER);
		vPanel.setCellVerticalAlignment(logo, HasAlignment.ALIGN_MIDDLE);

		userName.setStyleName("okm-Input");
		password.setStyleName("okm-Input");
		langList.setStyleName("okm-Input");
		loginButton.setStyleName("okm-Button");
		vPanel.setStyleName("okm-Login-Box");
		spTable.setStyleName("okm-Form-Box");
		msgError.setStylePrimaryName("okm-Error");
		msgError1.setStylePrimaryName("okm-Error");
		status.setStyleName("okm-StatusPopup");
		
		// Setting widget panel values
		int panelWidth = 400;
		int panelHeight = 315;
		
		// Changes in demo UI
		if (isDemo().equals("on")) {
			Widget users = RootPanel.get("users");
			vPanel.add(users);
			panelHeight = panelHeight*2;
		} else {
			Widget users = RootPanel.get("users");
			users.setVisible(false);
		}
		
		// Case user is using mobile
		if (isMobile) {
			logo = loginImageBundle.logoSmallIcon().createImage();
			
			leftPanel.setVisible(false);
			hPanel.remove(leftPanel);
			
			vPanel.add(msgError);
			vPanel.add(msgError1);
			
			vPanel.setCellHorizontalAlignment(vPanelData, HasHorizontalAlignment.ALIGN_CENTER);
			vPanel.setCellHorizontalAlignment(msgError, HasHorizontalAlignment.ALIGN_CENTER);
			vPanel.setCellHorizontalAlignment(msgError1, HasHorizontalAlignment.ALIGN_CENTER);
			
			table.setWidth("155px");
			vPanel.setCellHeight(logo, "55");
			panelWidth = 200;
			panelHeight = 250;
		}
		
		// Calculating position
		int posLeft = (Window.getClientWidth()-panelWidth)/2;
		int posTop = (Window.getClientHeight()-panelHeight)/3;
		
		// Correcting position negative desviation
		if (posLeft<0) { posLeft=0; }
		if (posTop<0) { posTop=0; }
		
		RootPanel.get().add(vPanel);
		RootPanel.get().setWidgetPosition(vPanel, posLeft, posTop);
		
		vPanel.setSize(panelWidth+"px", panelHeight+"px");
		langList.setSelectedIndex(evaluateLang(getBrowserLanguage()));
		
		// If user is authenticated must jump to page
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, urlTest);
		rb.setCallback(new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				if (!response.getText().contains(LOGIN_PAGE_TEXT)) {
					String urlToJump = "";
					// Setting jump base url
					if (isMobile) {
						urlToJump = urlJumpMobi;
					} else {
						urlToJump = urlJump;
					}
					// Setting language
					urlToJump += "?lang="+langList.getValue(langList.getSelectedIndex());
					
					// Setting docpath or folderpath
					if (!getDocPath().equals("")) {
						urlToJump += "&docPath=" + getDocPath();
					}
					if (!getFldPath().equals("")) {
						urlToJump += "&fldPath=" + getFldPath();
					}
					Window.Location.assign(urlToJump);
					Window.Location.assign(urlToJump);
				} 			
			}

			public void onError(Request request, Throwable caught) {
				Window.alert("Error on response receibed :" + caught.getMessage());
				throw new UnsupportedOperationException("Not supported yet.");
			}
		});

		try {
			rb.send();
		} catch (RequestException ex) {
			Window.alert("Error on request :" + ex.getMessage());
		}
		
		if (isLowercase().equals("on")) {
			userName.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					userName.setText(userName.getText().toLowerCase());
				}
			});
		}
		
		userName.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode()==(char)KeyCodes.KEY_ENTER) {
					submit();
				}
			}
		});
		
		password.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode()==(char)KeyCodes.KEY_ENTER) {
					submit();
				}
			}
		});
		
		userName.setFocus(true);
	}
	
	/**
	 * submit
	 */
	private void submit() {
		msgError.setVisible(false);
		msgError1.setVisible(false);
		status.setFlagTryAutentication();
		formPanel.submit();
	}
	
	/**
	 * Get browser language 
	 * @return The language in ISO 639 format.
	 */
	public static native String getBrowserLanguage() /*-{
		var lang = navigator.language? navigator.language : navigator.userLanguage;
		
		if (lang) {
			return lang;
		} else {
		  	return "en";
		}
	}-*/;
	
	/**
	 * evaluateLang
	 * 
	 * @param lang
	 * @return The code lang detected
	 */
	public static int evaluateLang(String lang) {
		int code = 0; 
		
		for (Iterator<String> it = languages.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			
			if (key.equalsIgnoreCase(lang) || key.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
				// Chinese special case
				if (!(key.equalsIgnoreCase(LANG_zh_CN) && !LANG_zh_TW.equalsIgnoreCase(lang)) &&
					!(key.equalsIgnoreCase(LANG_en_GB) && !LANG_en_US.equalsIgnoreCase(lang))) {
					return code;
				}
			}
			code++;
		}
		
		return 6; // By default english
	}
	

	public static native String isDemo() /*-{
		return $wnd.demo;
	}-*/;
	
	public static native String isLowercase() /*-{
		return $wnd.lowercase;
	}-*/;
	
	public static native String getDocPath() /*-{
		return $wnd.docPath;
	}-*/;
	
	public static native String getFldPath() /*-{
		return $wnd.fldPath;
	}-*/;
	
	public static native String getIsMobil() /*-{
		return $wnd.isMobil;
	}-*/;
}