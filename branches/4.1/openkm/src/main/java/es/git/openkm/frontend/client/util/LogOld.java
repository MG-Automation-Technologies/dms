/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

public class LogOld {
    private Class clazz = null;
    
    public LogOld(Class clazz) {
    	this.clazz = clazz;
    }
    
    public void debug(String str) {
    	debug(clazz, str);
    }

    public void info(String str) {
    	info(clazz, str);
    }

    public void warn(String str) {
    	warn(clazz, str);
    }
    
    public void error(String str) {
    	error(clazz, str);
    }
    
	private native void debug(Class clazz, String str) /*-{
		if (window.console) {
			var d = new Date();
			var date = d.getHours()+':'+d.getMinutes()+':'+d.getSeconds()+','+d.getMilliseconds();
			console.debug(date+" ["+clazz+"] "+str);
		}
	}-*/; 

	private static native void info(Class clazz, String str) /*-{
		if (window.console) {
			var d = new Date();
			var date = d.getHours()+':'+d.getMinutes()+':'+d.getSeconds()+','+d.getMilliseconds();
			console.info(date+" ["+clazz+"] "+str);
		}
	}-*/; 
	
	private static native void warn(Class clazz, String str) /*-{
		if (window.console) {
			var d = new Date();
			var date = d.getHours()+':'+d.getMinutes()+':'+d.getSeconds()+','+d.getMilliseconds();
			console.warn(date+" ["+clazz+"] "+str);
		}
	}-*/; 
	
	private static native void error(Class clazz, String str) /*-{
		if (window.console) {
			var d = new Date();
			var date = d.getHours()+':'+d.getMinutes()+':'+d.getSeconds()+','+d.getMilliseconds();
			console.error(date+" ["+clazz+"] "+str);
		}
	}-*/;
}
