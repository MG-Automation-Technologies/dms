/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2011  Paco Avila & Josep Llort
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

package com.openkm.frontend.client.widget.chat;

import com.google.gwt.user.client.ui.DialogBox;

/**
 * ChatRoomDialogBox
 * 
 * @author jllort
 *
 */
public class ChatRoomDialogBox extends DialogBox implements HasTranslations, HasChatRoom {
	
	/**
	 * ChatRoomDialogBox
	 * 
	 * @param autoHide
	 * @param modal
	 */
	public ChatRoomDialogBox(boolean autoHide, boolean modal) {
		super(autoHide, modal);
	}
	
	@Override
	public void langRefresh() {
	}

	@Override
	public String getRoom() {
		return "";
	}

	@Override
	public void setChatRoomActive(boolean active) {
	}
}