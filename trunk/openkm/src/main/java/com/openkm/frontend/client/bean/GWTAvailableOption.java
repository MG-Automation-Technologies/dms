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

package com.openkm.frontend.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * GWTAvailableOption
 * 
 * @author jllort
 *
 */
public class GWTAvailableOption implements IsSerializable  {
	
	public boolean createFolderOption = true;
	public boolean findFolderOption = true;
	public boolean downloadOption = true;
	public boolean downloadPdfOption = true;
	public boolean lockOption = true;
	public boolean unLockOption = true;
	public boolean addDocumentOption = true;
	public boolean checkoutOption = true;
	public boolean checkinOption = true;
	public boolean cancelCheckoutOption = true;
	public boolean deleteOption = true;
	public boolean addPropertyGroupOption = true;
	public boolean removePropertyGroupOption = true;
	public boolean addSubscription = true;
	public boolean removeSubscription = true;
	public boolean homeOption = true;
	public boolean refreshOption = true;
	public boolean workflowOption = true;
	public boolean scannerOption = true;
	public boolean uploaderOption = true;
	public boolean renameOption = true;
	public boolean copyOption = true;
	public boolean moveOption = true;
	public boolean addBookmarkOption = true;
	public boolean setHome = true;
	public boolean export = true;
	public boolean mediaPlayer = true;
	public boolean imageViewer = true;
	public boolean gotoFolder = true;
	public boolean createFromTemplate = true;
	public boolean purge = true;
	public boolean restore = true;
	public boolean purgeTrash = true;

	public GWTAvailableOption() {
	}

	public boolean isCreateFolderOption() {
		return createFolderOption;
	}

	public void setCreateFolderOption(boolean createFolderOption) {
		this.createFolderOption = createFolderOption;
	}

	public boolean isFindFolderOption() {
		return findFolderOption;
	}

	public void setFindFolderOption(boolean findFolderOption) {
		this.findFolderOption = findFolderOption;
	}

	public boolean isDownloadOption() {
		return downloadOption;
	}

	public void setDownloadOption(boolean downloadOption) {
		this.downloadOption = downloadOption;
	}

	public boolean isDownloadPdfOption() {
		return downloadPdfOption;
	}

	public void setDownloadPdfOption(boolean downloadPdfOption) {
		this.downloadPdfOption = downloadPdfOption;
	}

	public boolean isLockOption() {
		return lockOption;
	}

	public void setLockOption(boolean lockOption) {
		this.lockOption = lockOption;
	}

	public boolean isUnLockOption() {
		return unLockOption;
	}

	public void setUnLockOption(boolean unLockOption) {
		this.unLockOption = unLockOption;
	}

	public boolean isAddDocumentOption() {
		return addDocumentOption;
	}

	public void setAddDocumentOption(boolean addDocumentOption) {
		this.addDocumentOption = addDocumentOption;
	}

	public boolean isCheckoutOption() {
		return checkoutOption;
	}

	public void setCheckoutOption(boolean checkoutOption) {
		this.checkoutOption = checkoutOption;
	}

	public boolean isCheckinOption() {
		return checkinOption;
	}

	public void setCheckinOption(boolean checkinOption) {
		this.checkinOption = checkinOption;
	}

	public boolean isCancelCheckoutOption() {
		return cancelCheckoutOption;
	}

	public void setCancelCheckoutOption(boolean cancelCheckoutOption) {
		this.cancelCheckoutOption = cancelCheckoutOption;
	}

	public boolean isDeleteOption() {
		return deleteOption;
	}

	public void setDeleteOption(boolean deleteOption) {
		this.deleteOption = deleteOption;
	}

	public boolean isAddPropertyGroupOption() {
		return addPropertyGroupOption;
	}

	public void setAddPropertyGroupOption(boolean addPropertyGroupOption) {
		this.addPropertyGroupOption = addPropertyGroupOption;
	}

	public boolean isRemovePropertyGroupOption() {
		return removePropertyGroupOption;
	}

	public void setRemovePropertyGroupOption(boolean removePropertyGroupOption) {
		this.removePropertyGroupOption = removePropertyGroupOption;
	}

	public boolean isAddSubscription() {
		return addSubscription;
	}

	public void setAddSubscription(boolean addSubscription) {
		this.addSubscription = addSubscription;
	}

	public boolean isRemoveSubscription() {
		return removeSubscription;
	}

	public void setRemoveSubscription(boolean removeSubscription) {
		this.removeSubscription = removeSubscription;
	}

	public boolean isHomeOption() {
		return homeOption;
	}

	public void setHomeOption(boolean homeOption) {
		this.homeOption = homeOption;
	}

	public boolean isRefreshOption() {
		return refreshOption;
	}

	public void setRefreshOption(boolean refreshOption) {
		this.refreshOption = refreshOption;
	}

	public boolean isWorkflowOption() {
		return workflowOption;
	}

	public void setWorkflowOption(boolean workflowOption) {
		this.workflowOption = workflowOption;
	}

	public boolean isScannerOption() {
		return scannerOption;
	}

	public void setScannerOption(boolean scannerOption) {
		this.scannerOption = scannerOption;
	}

	public boolean isUploaderOption() {
		return uploaderOption;
	}

	public void setUploaderOption(boolean uploaderOption) {
		this.uploaderOption = uploaderOption;
	}
	
	public boolean isRenameOption() {
		return renameOption;
	}

	public void setRenameOption(boolean renameOption) {
		this.renameOption = renameOption;
	}
	
	public boolean isMoveOption() {
		return moveOption;
	}

	public void setMoveOption(boolean moveOption) {
		this.moveOption = moveOption;
	}

	public boolean isCopyOption() {
		return copyOption;
	}

	public void setCopyOption(boolean copyOption) {
		this.copyOption = copyOption;
	}
	
	public boolean isAddBookmarkOption() {
		return addBookmarkOption;
	}

	public void setAddBookmarkOption(boolean addBookmarkOption) {
		this.addBookmarkOption = addBookmarkOption;
	}
	
	public boolean isSetHome() {
		return setHome;
	}

	public void setSetHome(boolean setHome) {
		this.setHome = setHome;
	}
	
	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}
	
	public boolean isMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(boolean mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	public boolean isImageViewer() {
		return imageViewer;
	}

	public void setImageViewer(boolean imageViewer) {
		this.imageViewer = imageViewer;
	}
	
	public boolean isGotoFolder() {
		return gotoFolder;
	}

	public void setGotoFolder(boolean gotoFolder) {
		this.gotoFolder = gotoFolder;
	}
	
	public boolean isCreateFromTemplate() {
		return createFromTemplate;
	}

	public void setCreateFromTemplate(boolean createFromTemplate) {
		this.createFromTemplate = createFromTemplate;
	}
	
	public boolean isPurge() {
		return purge;
	}

	public void setPurge(boolean purge) {
		this.purge = purge;
	}

	public boolean isRestore() {
		return restore;
	}

	public void setRestore(boolean restore) {
		this.restore = restore;
	}
	
	public boolean isPurgeTrash() {
		return purgeTrash;
	}

	public void setPurgeTrash(boolean purgeTrash) {
		this.purgeTrash = purgeTrash;
	}
}