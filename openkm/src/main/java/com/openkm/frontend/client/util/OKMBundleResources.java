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

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * OKMBundleResources
 * 
 * @author jllort
 *
 */
public interface OKMBundleResources extends ClientBundle {
	
	public static final OKMBundleResources INSTANCE =  GWT.create(OKMBundleResources.class);
	
	@Source("com/openkm/frontend/public/img/icon/actions/delete.gif")
	public ImageResource deleteIcon();
	
	@Source("com/openkm/frontend/public/img/icon/stackpanel/book_open.gif")
	public ImageResource bookOpenIcon();
	
	@Source("com/openkm/frontend/public/img/icon/stackpanel/table_key.gif")
	public ImageResource tableKeyIcon();
	
	@Source("com/openkm/frontend/public/img/icon/toolbar/user.png")
	public ImageResource userIcon();

	@Source("com/openkm/frontend/public/img/icon/toolbar/mail.png")
	public ImageResource mailIcon();

	@Source("com/openkm/frontend/public/img/icon/toolbar/news.png")
	public ImageResource newsIcon();

	@Source("com/openkm/frontend/public/img/icon/toolbar/general.png")
	public ImageResource generalIcon();

	@Source("com/openkm/frontend/public/img/icon/toolbar/workflow.png")
	public ImageResource workflowIcon();
	
	@Source("com/openkm/frontend/public/img/icon/toolbar/keyword_map.png")
	public ImageResource keywordMapIcon();
}
