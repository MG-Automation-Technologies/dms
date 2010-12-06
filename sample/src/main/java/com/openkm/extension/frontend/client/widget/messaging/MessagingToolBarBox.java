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

package com.openkm.extension.frontend.client.widget.messaging;

import java.util.List;

import com.openkm.frontend.client.extension.widget.ToolBarBoxExtension;

/**
 * MessagingToolBarBox
 * 
 * @author jllort
 *
 */
public class MessagingToolBarBox {
	
	public static final int PROPOSED_QUERY_NONE 		= -1;
	public static final int PROPOSED_QUERY_SAVE_SEARCH 	= 0;
	public static final int PROPOSED_QUERY_USER_NEWS 	= 1;
	private static MessagingToolBarBox singleton;
	
	/**
	 * MessagingToolBarBox
	 */
	public MessagingToolBarBox(List<String> uuidList) {
		singleton = this;
	}
	
	/**
	 * ToolBarBoxExtension
	 * 
	 * @return
	 */
	public ToolBarBoxExtension getToolBarBox() {
		return null;
	}
	
	/**
	 * getExtensions
	 * 
	 * @return
	 */
	public List<Object> getExtensions() {
		return null;
	}
	
	/**
	 * isRegistered
	 * 
	 * @param uuidList
	 * @return
	 */
	public static boolean isRegistered(List<String> uuidList) {
		return false;
	}
	
	/**
	 * MessagingToolBarBox
	 * 
	 * @return
	 */
	public static MessagingToolBarBox get() {	
		return singleton;
	}
	
	/**
	 * executeProposeSubscription
	 */
	public void executeProposeSubscription(String uuid) {
	}
	
	/**
	 * executeProposeQuery
	 * 
	 * @param type
	 */
	public void executeProposeQuery(int type) {
	}
}