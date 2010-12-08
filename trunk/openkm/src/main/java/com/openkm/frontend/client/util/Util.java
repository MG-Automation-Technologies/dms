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

package com.openkm.frontend.client.util;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.contants.service.RPCService;

public class Util {
	
	/**
	 * Generates HTML for item with an attached icon.
	 * 
	 * @param imageUrl the url of the icon image
	 * @param title the title of the item
	 * @return the resultant HTML
	 */
	public static String imageItemHTML(String imageUrl, String title) {
		return "<span style='text-align:left; margin-right:4px;'><img align=\"absmidle\" style='margin-right:4px; white-space:nowrap;' src='" + imageUrl.toLowerCase() + "'>" + title + "</span>";
	}
	
	/**
	 * Generates HTML for item with an attached icon.
	 * 
	 * @param imageUrl the url of the icon image
	 * @param title the title of the item
	 * @return the resultant HTML
	 */
	public static String imageItemHTML(String imageUrl, String title, String align) {
		return "<span style='text-align:left; margin-right:4px;'><img align=\""+align+"\" style='margin-right:4px; white-space:nowrap;' src='" + imageUrl.toLowerCase() + "'>" + title + "</span>";
	}
	
	/**
	 * Generates HTML for item with an attached icon.
	 * 
	 * @param imageUrl the url of the icon image
	 * @return the resultant HTML
	 */
	public static String imageItemHTML(String imageUrl) {
		return "<img align=\"absmidle\" style='margin-right:4px' src='" + imageUrl.toLowerCase() + "'>";
	}
	
	/**
	 * Generates HTML image code with style.
	 * 
	 * @param imageUrl the url of the icon image
	 * @param alt image alt
	 * @param style the style of the image
	 * @return the resultant HTML
	 */
	public static String imageHTML(String imageUrl, String alt, String style) {
		if (!style.equals("")){
			return "<img align=\"absmidle\"" + style + " src='" + imageUrl.toLowerCase() + "'>";
		} else {
			return imageHTML(imageUrl, alt);
		}
	}
	
	/**
	 * Generates HTML image code with style.
	 * 
	 * @param imageUrl the url of the icon image
	 * @param alt the image alt
	 * @return the resultant HTML
	 */
	public static String imageHTML(String imageUrl, String alt) {
		return "<img border=\"0\" align=\"absmidle\" alt=\""+ alt +"\" title=\""+ alt +"\" src='" + imageUrl.toLowerCase() + "'>";
	}
	
	/**
	 * Generates HTML image code with style.
	 * 
	 * @param imageUrl the url of the icon image
	 * @return the resultant HTML
	 */
	public static String imageHTML(String imageUrl) {
		return imageHTML(imageUrl, "");
	}
	
	/**
	 * Generate HTML icon for mime-type document
	 * 
	 * @param mime The document mime-type
	 * @return the html image of mime-type file
	 */
	public static String mimeImageHTML(String mime) {
		return "<img align=\"absmidle\" style=\"margin-right:4px\" src=\""+Main.CONTEXT+"/mime/"+mime+"\"'>";
	}
	
	/**
	 * Return the menu html value
	 * 
	 * @param imageUrl The image url
	 * @param text The text value
	 * @return
	 */
	public static String flagMenuHTML(String flag, String text) {
		return "<img style='margin-right:8px; margin-left:2px; vertical-align:middle;' "+
		        "src=\""+Main.CONTEXT+"/flag/"+flag+"\"'>" + text;
	}
	
	/**
	 * Return the menu html value
	 * 
	 * @param imageUrl The image url
	 * @param text The text value
	 * @return
	 */
	public static String menuHTML(String imageUrl, String text) {
		return "<img style='margin-right:8px; margin-left:2px; vertical-align:middle;' src='" 
			   + imageUrl + "'>" + text;
	}
	
	/**
	   * Creates an HTML fragment that places an image & caption together, for use
	   * in a group header.
	   * 
	   * @param imageUrl the url of the icon image to be used
	   * @param caption the group caption
	   * @return the header HTML fragment
	   */
	public static String createHeaderHTML(String imageUrl, String caption) {
		return "<table align='left'><tr>" + "<td><img src='" + imageUrl + "'></td>"
	      + "<td style='vertical-align:middle'><b style='white-space:nowrap; cursor: default;'>"
	      + caption + "</b></td>" + "</tr></table>";
	}
	
	/**
	 * Creates an HTML to opens a url with text on a new window
	 * 
	 * @param text The text url description
	 * @param uri The url to open
	 * @return
	 */
	public static String windowOpen(String text, String uri) {
		return "<span onclick=\"javascript:window.open('"+ uri +"')\">" + text + "</span>";
	}
	
	/**
	 * isJREInstalled
	 * 
	 * @return
	 */
	public static boolean isJREInstalled() {
		String[] jreList = getJREs();
		if (jreList != null && jreList.length > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Download file
	 * 
	 * @param path
	 * @param params
	 */
	public static void downloadFile(String path, String params) {
		if (!params.equals("") && !params.endsWith("&")) {
			params += "&";
		}
		
		final Element downloadIframe = RootPanel.get("__download").getElement(); 
		String url = RPCService.DownloadServlet + "?" + params + "id=" + URL.encodeComponent(path);
		DOM.setElementAttribute(downloadIframe, "src", url); 
	}
	
	/**
	 * Download file
	 * 
	 * @param path
	 * @param params
	 */
	public static void downloadFilePdf(String uuid) {
		final Element downloadIframe = RootPanel.get("__download").getElement(); 
		String url = RPCService.ConverterServlet + "?toPdf=1&uuid=" + URL.encodeComponent(uuid);
		DOM.setElementAttribute(downloadIframe, "src", url); 
	}
	
	/**
	 * markHTMLTextAsBold
	 * 
	 * @param text
	 * @param mark
	 */
	public static String getTextAsBoldHTML(String text, boolean mark) {
		if (mark) {
			return "<b>" + text + "</b>"; 
		} else {
			return text;
		}
	}
	
	/**
	 * Change on fly the actual css
	 * 
	 * @param title The css name
	 */
	public static native void changeCss(String title) /*-{
		new $wnd.changeCss(title);
	}-*/;
	
	/**
	 * Format file size in Bytes, KBytes or MBytes. 
	 * @param size The file size in bytes.
	 * @return The formated file size.
	 */
	public static native String formatSize(double size) /*-{
	    if (size / 1024 < 1) {
			str = size + " Bytes";
		} else if (size / 1048576 < 1) {
			str = (size / 1024).toFixed(1) + " KB";
		} else if (size / 1073741824 < 1) {
			str = (size / 1048576).toFixed(1) + " MB";
		} else if (size / 1099511627776 < 1) {
			str = (size / 1073741824).toFixed(1) + " GB";
		} else {
			str = "BIG";
		}
		
		return str;
	}-*/;
	
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
	 *  returns 'opera', 'safari', 'ie6', 'ie7', 'gecko' or 'unknown'.
	 */
    public static native String getUserAgent() /*-{
        try {
            if ( window.opera ) return 'opera';
            var ua = navigator.userAgent.toLowerCase();
            if ( ua.indexOf('chrome') != -1 ) return 'chrome';
            if ( ua.indexOf('webkit' ) != -1 ) return 'safari';
            if ( ua.indexOf('msie 6.0') != -1 ) return 'ie6';
            if ( ua.indexOf('msie 7.0') != -1 ) return 'ie7';
            if ( ua.indexOf('msie 8.0') != -1 ) return 'ie8';
            if ( ua.indexOf('gecko') != -1 ) return 'gecko';
            return 'unknown';
        } catch ( e ) { return 'unknown' }
    }-*/;
    
    public static native void removeMediaPlayer() /*-{    
    	$wnd.swfobject.removeSWF("jsmediaplayer");
	}-*/;
    
   public static native void createMediaPlayer(String mediaUrl, String mediaProvider, String width, String height) /*-{    	
    	$wnd.swfobject.embedSWF("/OpenKM/js/mediaplayer/player.swf", "mediaplayercontainer", width, height, "9.0.0", "/OpenKM/js/mediaplayer/expressinstall.swf", {file:mediaUrl,provider:mediaProvider,autostart:"true",width:width,height:height}, {allowscriptaccess:"always",allowfullscreen: "true"}, {id:"jsmediaplayer",name:"jsmediaplayer"});
    }-*/;
    
    public static native void createPDFViewerZviewer(String pdfUrl, String width, String height) /*-{
    	pdfUrl = encodeURIComponent(pdfUrl);
		$wnd.swfobject.embedSWF("/OpenKM/js/zviewer/zviewer.swf", "pdfviewercontainer", width, height, "9.0.0", "/OpenKM/js/mediaplayer/expressinstall.swf", {doc_url:pdfUrl}, {allowFullScreen:"true",menu:"false",bgcolor:"#efefef"}, {id:"jspdfviewer",name:"jspdfviewer"});
	}-*/;
    
    public static native void createPDFViewerFlexPaper(String pdfUrl, String width, String height) /*-{
	pdfUrl = encodeURIComponent(pdfUrl);
	$wnd.swfobject.embedSWF("/OpenKM/js/flexpaper/FlexPaperViewer.swf", "pdfviewercontainer",width, height,"10.0.0", "playerProductInstall.swf",
                  {
            		SwfFile : pdfUrl,
				  	Scale : 0.6, 
				  	ZoomTransition : "easeOut",
				  	ZoomTime : 0.5,
  				  	ZoomInterval : 0.1,
  				  	FitPageOnLoad : false,
  				  	FitWidthOnLoad : true,
  				  	PrintEnabled : true,
  				  	FullScreenAsMaxWindow : false,
  				  	ProgressiveLoading : true,
  				  
  				  	PrintToolsVisible : true,
  				  	ViewModeToolsVisible : true,
  				  	ZoomToolsVisible : true,
  				  	FullScreenVisible : true,
  				  	NavToolsVisible : true,
  				  	CursorToolsVisible : true,
  				  	SearchToolsVisible : true,
  				    localeChain: "en_US"
  	  			  }, 
  	  			  {
  	  	  			quality:"high",
  	  	  			bgcolor:"#ffffff",
  	  	  			allowscriptaccess:"sameDomain",
  	  	  			allowfullscreen:"true"
  	  	  	  	  }, 
  	  	  	  	  {
  	  	  	  	  	id:"FlexPaperViewer",
  	  	  	  	    name:"FlexPaperViewer"
  	  	  	  	  });
	}-*/;
    
    public static native String[] getJREs() /*-{
		return $wnd.deployJava.getJREs();
	}-*/;  
    
    public static native void createLinkClipboardButton(String textToCopy, String containerName) /*-{
		$wnd.swfobject.embedSWF("/OpenKM/clippy.swf", containerName, 14, 14, "9.0.0", "/OpenKM/clippy.swf", {text:textToCopy}, {quality:"high",scale:"noscale",bgcolor:"#FFFFFF"}, {id:"clippy",name:"clippy"});
	}-*/;
    
    public static native void createURLClipboardButton(String textToCopy) /*-{
		$wnd.swfobject.embedSWF("/OpenKM/clippy.swf", "urlclipboardcontainer", 14, 14, "9.0.0", "/OpenKM/clippy.swf", {text:textToCopy}, {quality:"high",scale:"noscale",bgcolor:"#FFFFFF"}, {id:"clippy",name:"clippy"});
	}-*/;
    
    public static native void createWebDavClipboardButton(String textToCopy) /*-{
		$wnd.swfobject.embedSWF("/OpenKM/clippy.swf", "webdavclipboardcontainer", 14, 14, "9.0.0", "/OpenKM/clippy.swf", {text:textToCopy}, {quality:"high",scale:"noscale",bgcolor:"#FFFFFF"}, {id:"clippy",name:"clippy"});
	}-*/;
    
    public static native void createFolderURLClipboardButton(String textToCopy) /*-{
		$wnd.swfobject.embedSWF("/OpenKM/clippy.swf", "folderurlclipboardcontainer", 14, 14, "9.0.0", "/OpenKM/clippy.swf", {text:textToCopy}, {quality:"high",scale:"noscale",bgcolor:"#FFFFFF"}, {id:"clippy",name:"clippy"});
	}-*/;
    
    public static native void createFolderWebDavClipboardButton(String textToCopy) /*-{
		$wnd.swfobject.embedSWF("/OpenKM/clippy.swf", "folderwebdavclipboardcontainer", 14, 14, "9.0.0", "/OpenKM/clippy.swf", {text:textToCopy}, {quality:"high",scale:"noscale",bgcolor:"#FFFFFF"}, {id:"clippy",name:"clippy"});
	}-*/;
}
