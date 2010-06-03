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

package com.openkm.frontend.client.extension.event;

/**
 * HasDocumentEvent
 * 
 * 
 * @author jllort
 *
 */

public interface HasDocumentEvent {
	
	/**
	 * DocumentEventConstant
	 * 
	 * @author jllort
	 *
	 */
	public static class DocumentEventConstant {
		
		static final int EVENT_CHANGE_DOCUMENT 	= 1;
		static final int EVENT_ADD_KEYWORD  	= 2;
		static final int EVENT_REMOVE_KEYWORD  	= 3;
		static final int EVENT_ADD_CATEGORY 	= 4;
		static final int EVENT_REMOVE_CATEGORY 	= 5;
		static final int EVENT_PANEL_RESIZED   	= 6;
		static final int EVENT_TAB_CHANGED    	= 7;
		static final int EVENT_SECURITY_CHANGE 	= 8;
		static final int EVENT_NOTE_ADDED 		= 9;
		
		private int type = 0;
		
		/**
		 * DocumentEventConstant
		 * 
		 * @param type
		 */
		private DocumentEventConstant(int type) {
			this.type = type;
		}
		
		public int getType(){
			return type;
		}
	}
	
	DocumentEventConstant DOCUMENT_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_CHANGE_DOCUMENT);
	DocumentEventConstant KEYWORD_ADDED = new DocumentEventConstant(DocumentEventConstant.EVENT_ADD_KEYWORD);
	DocumentEventConstant KEYWORD_REMOVED = new DocumentEventConstant(DocumentEventConstant.EVENT_REMOVE_KEYWORD);
	DocumentEventConstant CATEGORY_ADDED = new DocumentEventConstant(DocumentEventConstant.EVENT_ADD_CATEGORY);
	DocumentEventConstant CATEGORY_REMOVED = new DocumentEventConstant(DocumentEventConstant.EVENT_REMOVE_CATEGORY);
	DocumentEventConstant PANEL_RESIZED = new DocumentEventConstant(DocumentEventConstant.EVENT_PANEL_RESIZED);
	DocumentEventConstant TAB_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_TAB_CHANGED);
	DocumentEventConstant SECURITY_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_SECURITY_CHANGE);
	DocumentEventConstant NOTE_ADDED = new DocumentEventConstant(DocumentEventConstant.EVENT_NOTE_ADDED);
	
	/**
	 * @param event
	 */
	void fireEvent(DocumentEventConstant event);
}