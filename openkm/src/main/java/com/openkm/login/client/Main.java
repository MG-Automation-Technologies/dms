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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
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
	
	public static final int LANG_BOSNIAN 				= 0;
	public static final int LANG_CATALAN				= 1;
	public static final int LANG_CHINESE_SIMPLE 		= 2;
	public static final int LANG_CHINESE_TRADITIONAL	= 3;
	public static final int LANG_DEUTSCH				= 4;
	public static final int LANG_ENGLISH				= 5;		
	public static final int LANG_ESPANOL				= 6;
	public static final int LANG_ESPANOL_COLOMBIA		= 7;
	public static final int LANG_FARSI					= 8;
	public static final int LANG_FRANCAIS				= 9;
	public static final int LANG_GALLEGO				= 10;
	public static final int LANG_GREECE					= 11;
	public static final int LANG_HUNGARIAN				= 12;
	public static final int LANG_ITALIAN				= 13;
	public static final int LANG_JAPANESE				= 14;
	public static final int LANG_LATVIAN				= 15;
	public static final int LANG_MACEDONIAN				= 16;
	public static final int LANG_NEDERALANDS			= 17;
	public static final int LANG_POLISH					= 18;
	public static final int LANG_PORTUGUES_BRASIL		= 19;
	public static final int LANG_ROMANIAN				= 20;
	public static final int LANG_RUSSIAN				= 21;
	public static final int LANG_SERBIAN				= 22;
	public static final int LANG_SWEDISH				= 23;
	public static final int LANG_TURKISH				= 24;
	
	public static final String LOGIN_PAGE_TEXT	= "<title>OpenKM Login</title>";
	
	public VerticalPanel vPanel = new VerticalPanel();
	public HTML msgError = new HTML("Authentication");
	public HTML msgError1 = new HTML("error");
	public Status status = new Status();
	
	public void onModuleLoad() {
		final FormPanel formPanel = new FormPanel();
		VerticalPanel vPanelData = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		final TextBox userName = new TextBox();
		PasswordTextBox password = new PasswordTextBox();
		Button loginButton = new Button("Login");
		Image lock = new Image("img/lock.png");
		Image logo = new Image("img/logo_big.gif");
		FlexTable table = new FlexTable();
		final ListBox langList = new ListBox();
		final String url = GWT.getHostPageBaseURL().substring(0, GWT.getHostPageBaseURL().indexOf("/OpenKM/")+8) + "com.openkm.frontend.Main/index.jsp";

		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				msgError.setVisible(false);
				msgError1.setVisible(false);
				status.setFlagTryAutentication();
				formPanel.submit();
			}
		});
		
		formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
				rb.setCallback(new RequestCallback() {
					public void onResponseReceived(Request request, Response response) {
						if (!response.getText().contains(LOGIN_PAGE_TEXT)) {
							Window.Location.assign(url+"?lang="+langList.getValue(langList.getSelectedIndex()));
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
		
		langList.addItem("Bosnian", LANG_bs_BA);
		langList.addItem("Català", LANG_ca_ES);
		langList.addItem("Chinese simple", LANG_zh_CN);
		langList.addItem("Chinese traditional", LANG_zh_TW);
		langList.addItem("Deutsch", LANG_de_DE);
		langList.addItem("English", LANG_en_GB);		
		langList.addItem("Español", LANG_es_ES);
		langList.addItem("Español Colombia", LANG_co_ES);
		langList.addItem("Farsi", LANG_fa_FA);
		langList.addItem("Français", LANG_fr_FR);
		langList.addItem("Gallego", LANG_gl_ES);
		langList.addItem("Greece", LANG_el_GR);
		langList.addItem("Hungarian", LANG_hu_HU);
		langList.addItem("Italian", LANG_it_IT);
		langList.addItem("Japanese", LANG_ja_JP);
		langList.addItem("Latvian", LANG_lv_LV);
		langList.addItem("Macedonian", LANG_mk_MK);
		langList.addItem("Nederlands", LANG_nl_NL);
		langList.addItem("Polish", LANG_pl_PL);
		langList.addItem("Português do Brasil", LANG_pt_BR);
		langList.addItem("Romanian", LANG_ro_RO);
		langList.addItem("Russian", LANG_ru_RU);
		langList.addItem("Serbian", LANG_sr_BA);
		langList.addItem("Swedish ", LANG_sv_SE);
		langList.addItem("Turkish ", LANG_tr_TR);
		
		table.setCellPadding(0);
		table.setCellSpacing(0);
		table.setWidget(0, 0, new HTML("<b>Language</b>"));
		table.setWidget(1, 0, langList);
		table.setWidget(2, 0, new HTML("&nbsp;"));
		table.setWidget(3, 0, new HTML("<b>User name</b>"));
		table.setWidget(4, 0, userName);
		table.setWidget(5, 0, new HTML("&nbsp;"));
		table.setWidget(6, 0, new HTML("<b>Password</b>"));
		table.setWidget(7, 0, password);
		table.setWidget(8, 0, new HTML("&nbsp;"));
		table.setWidget(9, 0, loginButton);
		
		table.getCellFormatter().setHeight(2, 0, "10");
		table.getCellFormatter().setHeight(5, 0, "10");
		table.getCellFormatter().setHeight(7, 0, "10");
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
		
		int posLeft = (Window.getClientWidth()-400)/2;
		int posRight = (Window.getClientHeight()-315)/2;
		
		RootPanel.get().add(vPanel);
		RootPanel.get().setWidgetPosition(vPanel, posLeft, posRight);
		
		vPanel.setSize("400px", "315px");
		langList.setSelectedIndex(evaluateLang(getBrowserLanguage()));
		
		// If user is authenticated must jump to page
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
		rb.setCallback(new RequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				if (!response.getText().contains(LOGIN_PAGE_TEXT)) {
					Window.Location.assign(url+"?lang="+langList.getValue(langList.getSelectedIndex()));
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
		
		Window.alert(isDemo());
		if (isLowercase().equals("on")) {
			userName.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					userName.setText(userName.getText().toLowerCase());
				}
			});
		}
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
		int code = LANG_ENGLISH; 
		
		if (LANG_es_ES.equalsIgnoreCase(lang) || LANG_es_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2)))  {
			code = LANG_ESPANOL;
		} else if (LANG_ca_ES.equalsIgnoreCase(lang) || LANG_ca_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_CATALAN;
		} else if (LANG_gl_ES.equalsIgnoreCase(lang) || LANG_gl_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_GALLEGO;
		} else if (LANG_en_GB.equalsIgnoreCase(lang) || LANG_en_GB.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_ENGLISH;
		} else if (LANG_fr_FR.equalsIgnoreCase(lang) || LANG_fr_FR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_FRANCAIS;
		} else if (LANG_pt_BR.equalsIgnoreCase(lang) || LANG_pt_BR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_PORTUGUES_BRASIL;
		} else if (LANG_nl_NL.equalsIgnoreCase(lang) || LANG_nl_NL.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_NEDERALANDS;
		} else if (LANG_fa_FA.equalsIgnoreCase(lang) || LANG_fa_FA.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_FARSI;
		} else if (LANG_de_DE.equalsIgnoreCase(lang) || LANG_de_DE.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_DEUTSCH;
		} else if (LANG_it_IT.equalsIgnoreCase(lang) || LANG_it_IT.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_ITALIAN;
		} else if (LANG_zh_CN.equalsIgnoreCase(lang) || (LANG_zh_CN.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2)) && !LANG_zh_TW.equalsIgnoreCase(lang) )) {
			code = LANG_CHINESE_SIMPLE;
		} else if (LANG_sv_SE.equalsIgnoreCase(lang) || LANG_sv_SE.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_SWEDISH;
		} else if (LANG_sr_BA.equalsIgnoreCase(lang) || LANG_sr_BA.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_SERBIAN;
		} else if (LANG_tr_TR.equalsIgnoreCase(lang) || LANG_tr_TR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_TURKISH;
		} else if (LANG_ja_JP.equalsIgnoreCase(lang) || LANG_ja_JP.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_JAPANESE;
		} else if (LANG_ro_RO.equalsIgnoreCase(lang) || LANG_ro_RO.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_ROMANIAN;
		} else if (LANG_pl_PL.equalsIgnoreCase(lang) || LANG_pl_PL.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_POLISH;
		} else if (LANG_hu_HU.equalsIgnoreCase(lang) || LANG_hu_HU.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_HUNGARIAN;
		} else if (LANG_el_GR.equalsIgnoreCase(lang) || LANG_el_GR.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_GREECE;
		} else if (LANG_zh_TW.equalsIgnoreCase(lang) || LANG_zh_TW.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_CHINESE_TRADITIONAL;
		} else if (LANG_lv_LV.equalsIgnoreCase(lang) || LANG_lv_LV.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_LATVIAN;
		} else if (LANG_mk_MK.equalsIgnoreCase(lang) || LANG_mk_MK.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_MACEDONIAN;
		} else if (LANG_co_ES.equalsIgnoreCase(lang) || LANG_co_ES.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_ESPANOL_COLOMBIA;
		} else if (LANG_ru_RU.equalsIgnoreCase(lang) || LANG_ru_RU.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_RUSSIAN;
		} else if (LANG_bs_BA.equalsIgnoreCase(lang) || LANG_bs_BA.substring(0, 2).equalsIgnoreCase(lang.substring(0, 2))) {
			code = LANG_BOSNIAN;
		} 
		
		return code;
	}
	

	public static native String isDemo() /*-{
		return $wnd.demo;
	}-*/;
	
	public static native String isLowercase() /*-{
		return $wnd.lowercase;
	}-*/;
}