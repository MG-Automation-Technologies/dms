/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
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

package es.git.openkm.frontend.client.util;

import es.git.openkm.frontend.client.bean.Coordenates;
import es.git.openkm.frontend.client.panel.ExtendedSizeComposite;

public class Util {
	
	/**
	 * Set size to panel object
	 * 
	 * @param panel The panel to set size
	 * @param coord The coordenates of the panel on browser
	 */
	public static void setSize(ExtendedSizeComposite panel, Coordenates coord) {
		panel.setSize(coord.getWidth(),coord.getHeight());
	}
	
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
		return "<img align=\"absmidle\" style=\"margin-right:4px\" src=\"img/icon/mime/"+mime+".gif\"'>";
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
			   + imageUrl.toLowerCase() + "'>" + text;
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
	public static String windowOpen(String text, String uri){
		return "<span onclick=\"javascript:window.open('"+ uri +"')\">" + text + "</span>";
	}
	
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
    
    public static native void createMediaPlayer(String mediaUrl, String width, String height) /*-{    
        	
    	$wnd.swfobject.embedSWF("/OpenKM/js/mediaplayer/player-licensed.swf", "mediaplayercontainer", width, height, "9.0.0", "/OpenKM/js/mediaplayer/expressinstall.swf", {file:mediaUrl,autostart:"true",width:width,height:height}, {allowscriptaccess:"always",allowfullscreen: "true"}, {id:"jsmediaplayer",name:"jsmediaplayer"});
    }-*/;
    
    public static native void createPDFViewer(String pdfUrl, String width, String height) /*-{
    	pdfUrl = encodeURIComponent(pdfUrl);
		$wnd.swfobject.embedSWF("/OpenKM/js/zviewer/zviewer.swf", "pdfviewercontainer", width, height, "9.0.0", "/OpenKM/js/mediaplayer/expressinstall.swf", {doc_url:pdfUrl}, {allowFullScreen:"true",menu:"false",bgcolor:"#efefef"}, {id:"jspdfviewer",name:"jspdfviewer"});
	}-*/;
       
    public static native void copyToClipboard(String text) /*-{
    	new $wnd.copyToClipboard(text);
	}-*/;
}
