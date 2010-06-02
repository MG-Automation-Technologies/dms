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
 * DocumentEventConstant
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
		static final int EVENT_CHANGE_CATEGORY 	= 2;
		static final int EVENT_CHANGE_KEYWORD  	= 3;
		static final int EVENT_PANEL_RESIZED   	= 4;
		static final int EVENT_TAB_CHANGED    	= 5;
		static final int EVENT_SECURITY_CHANGE 	= 6;
		
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
	DocumentEventConstant CATEGORY_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_CHANGE_CATEGORY);
	DocumentEventConstant KEYWORD_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_CHANGE_KEYWORD);
	DocumentEventConstant PANEL_RESIZED = new DocumentEventConstant(DocumentEventConstant.EVENT_PANEL_RESIZED);
	DocumentEventConstant TAB_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_TAB_CHANGED);
	DocumentEventConstant SECURITY_CHANGED = new DocumentEventConstant(DocumentEventConstant.EVENT_SECURITY_CHANGE);
	
	/**
	 * @param event
	 */
	void fireEvent(DocumentEventConstant event);
}