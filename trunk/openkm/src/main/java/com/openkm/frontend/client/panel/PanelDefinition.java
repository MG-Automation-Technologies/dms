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

package com.openkm.frontend.client.panel;

/**
 * Defines contants values for the diferent panels
 * 
 * @author jllort
 *
 */
public class PanelDefinition {
	
	// Navigator stack panel definition
	public static final int NAVIGATOR_TAXONOMY 		= 0; // Number indicates ordering
	public static final int NAVIGATOR_THESAURUS		= 1;
	public static final int NAVIGATOR_TEMPLATES 	= 2;
	public static final int NAVIGATOR_PERSONAL  	= 3;
	public static final int NAVIGATOR_MAIL  		= 4;
	public static final int NAVIGATOR_TRASH  		= 5;
	public static final int NAVIGATOR_ALL_CONTEXT 	= 6; // Used to indicate operation to all context ( search ) really it's not a panel
	public static final int SEARCH_SAVED 			= 0;
	public static final int SEARCH_USER_NEWS		= 1;
	public static final int NUMBER_OF_STACKS 		= 6;
	public static final int STACK_HEIGHT     		= 22;
	
}