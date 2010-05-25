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
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_folder.gif")
	public ImageResource createFolder();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_folder_disabled.gif")
	public ImageResource createFolderDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/folder_find.gif")
	public ImageResource findFolder();
	
	@Source("com/openkm/frontend/public/img/icon/actions/folder_find_disabled.gif")
	public ImageResource findFolderDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/lock.gif")
	public ImageResource lock();
	
	@Source("com/openkm/frontend/public/img/icon/actions/lock_disabled.gif")
	public ImageResource lockDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/unlock.gif")
	public ImageResource unLock();
	
	@Source("com/openkm/frontend/public/img/icon/actions/unlock_disabled.gif")
	public ImageResource unLockDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_document.gif")
	public ImageResource addDocument();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_document_disabled.gif")
	public ImageResource addDocumentDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/delete.gif")
	public ImageResource delete();
	
	@Source("com/openkm/frontend/public/img/icon/actions/delete_disabled.gif")
	public ImageResource deleteDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/checkout.gif")
	public ImageResource checkout();
	
	@Source("com/openkm/frontend/public/img/icon/actions/checkout_disabled.gif")
	public ImageResource checkoutDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/checkin.gif")
	public ImageResource checkin();
	
	@Source("com/openkm/frontend/public/img/icon/actions/checkin_disabled.gif")
	public ImageResource checkinDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/cancel_checkout.gif")
	public ImageResource cancelCheckout();
	
	@Source("com/openkm/frontend/public/img/icon/actions/cancel_checkout_disabled.gif")
	public ImageResource cancelCheckoutDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/download.gif")
	public ImageResource download();
	
	@Source("com/openkm/frontend/public/img/icon/actions/download_disabled.gif")
	public ImageResource downloadDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/download_pdf.gif")
	public ImageResource downloadPdf();
	
	@Source("com/openkm/frontend/public/img/icon/actions/download_pdf_disabled.gif")
	public ImageResource downloadPdfDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_property_group.gif")
	public ImageResource addPropertyGroup();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_property_group_disabled.gif")
	public ImageResource addPropertyGroupDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/remove_property_group.gif")
	public ImageResource removePropertyGroup();
	
	@Source("com/openkm/frontend/public/img/icon/actions/remove_property_group_disabled.gif")
	public ImageResource removePropertyGroupDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/start_workflow.gif")
	public ImageResource startWorkflow();
	
	@Source("com/openkm/frontend/public/img/icon/actions/start_workflow_disabled.gif")
	public ImageResource startWorkflowDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_subscription.gif")
	public ImageResource addSubscription();
	
	@Source("com/openkm/frontend/public/img/icon/actions/add_subscription_disabled.gif")
	public ImageResource addSubscriptionDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/remove_subscription.gif")
	public ImageResource removeSubscription();
	
	@Source("com/openkm/frontend/public/img/icon/actions/remove_subscription_disabled.gif")
	public ImageResource removeSubscriptionDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/bookmark_go.gif")
	public ImageResource home();
	
	@Source("com/openkm/frontend/public/img/icon/actions/bookmark_go_disabled.gif")
	public ImageResource homeDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/refresh.gif")
	public ImageResource refresh();
	
	@Source("com/openkm/frontend/public/img/icon/actions/refresh_disabled.gif")
	public ImageResource refreshDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/scanner_disabled.gif")
	public ImageResource scannerDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/scanner.gif")
	public ImageResource scanner();
	
	@Source("com/openkm/frontend/public/img/icon/actions/upload_disabled.gif")
	public ImageResource uploaderDisabled();
	
	@Source("com/openkm/frontend/public/img/icon/actions/upload.gif")
	public ImageResource uploader();
	
	@Source("com/openkm/frontend/public/img/icon/chat/chat_disconnected.png")
	public ImageResource chatDisconnected();
	
	@Source("com/openkm/frontend/public/img/icon/chat/chat_connected.png")
	public ImageResource chatConnected();
	
	@Source("com/openkm/frontend/public/img/icon/chat/new_chat_room.png")
	public ImageResource newChatRoom();
	
	@Source("com/openkm/frontend/public/img/icon/chat/add_user.png")
	public ImageResource addUserToChatRoom();
	
	@Source("com/openkm/frontend/public/img/icon/connect.gif")
	public ImageResource openkmConnected();
	
	@Source("com/openkm/frontend/public/img/icon/user_repository.gif")
	public ImageResource repositorySize();
	
	@Source("com/openkm/frontend/public/img/icon/subscribed.gif")
	public ImageResource subscribed();
	
	@Source("com/openkm/frontend/public/img/icon/news_alert.gif")
	public ImageResource newsAlert();
	
	@Source("com/openkm/frontend/public/img/icon/news.gif")
	public ImageResource news();
	
	@Source("com/openkm/frontend/public/img/icon/workflow_alert.gif")
	public ImageResource workflowAlert();
	
	@Source("com/openkm/frontend/public/img/icon/workflow.gif")
	public ImageResource workflow();
	
	@Source("com/openkm/frontend/public/img/icon/actions/warning.gif")
	public ImageResource warning();
	
	@Source("com/openkm/frontend/public/img/icon/toolbar/separator.gif")
	public ImageResource separator();
}
