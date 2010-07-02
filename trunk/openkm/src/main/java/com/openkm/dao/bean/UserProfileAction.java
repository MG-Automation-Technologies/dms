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

package com.openkm.dao.bean;

import java.io.Serializable;

public class UserProfileAction implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean createFolderVisible;
	private boolean findFolderVisible;
	private boolean goFolderVisible;
	private boolean downloadVisible;
	private boolean downloadPdfVisible;
	private boolean lockVisible;
	private boolean unlockVisible;
	private boolean addDocumentVisible;
	private boolean checkInVisible;
	private boolean checkOutVisible;
	private boolean cancelCheckOutVisible;
	private boolean deleteVisible;
	private boolean addPropertyGroupVisible;
	private boolean removePropertyGroupVisible;
	private boolean startWorkflowVisible;
	private boolean addSubscriptionVisible;
	private boolean removeSubscriptionVisible;
	private boolean refreshVisible;
	private boolean goHomeVisible;
	private boolean setHomeVisible;
	private boolean scannerVisible;
	private boolean uploaderVisible;
	private boolean renameVisible;
	private boolean copyVisible;
	private boolean moveVisible;
	private boolean addBookmarkVisible;
	private boolean exportVisible;
	private boolean createFromTemplateVisible;
	private boolean purgeVisible;
	private boolean restoreVisible;
	private boolean purgeTrashVisible;
	private boolean sendDocumentLinkVisible;
	private boolean skinVisible;
	private boolean debugVisible;
	private boolean administrationVisible;
	private boolean manageBookmarksVisible;
	private boolean helpVisible;
	private boolean documentationVisible;
	private boolean bugReportVisible;
	private boolean supportRequestVisible;
	private boolean publicForumVisible;
	private boolean versionChangesVisible;
	private boolean projectWebVisible;
	private boolean aboutVisible;
	
	public boolean isCreateFolderVisible() {
		return createFolderVisible;
	}

	public void setCreateFolderVisible(boolean createFolderVisible) {
		this.createFolderVisible = createFolderVisible;
	}

	public boolean isFindFolderVisible() {
		return findFolderVisible;
	}

	public void setFindFolderVisible(boolean findFolderVisible) {
		this.findFolderVisible = findFolderVisible;
	}

	public boolean isGoFolderVisible() {
		return goFolderVisible;
	}

	public void setGoFolderVisible(boolean goFolderVisible) {
		this.goFolderVisible = goFolderVisible;
	}
	
	public boolean isDownloadVisible() {
		return downloadVisible;
	}

	public void setDownloadVisible(boolean downloadVisible) {
		this.downloadVisible = downloadVisible;
	}

	public boolean isDownloadPdfVisible() {
		return downloadPdfVisible;
	}

	public void setDownloadPdfVisible(boolean downloadPdfVisible) {
		this.downloadPdfVisible = downloadPdfVisible;
	}

	public boolean isLockVisible() {
		return lockVisible;
	}

	public void setLockVisible(boolean lockVisible) {
		this.lockVisible = lockVisible;
	}

	public boolean isUnlockVisible() {
		return unlockVisible;
	}

	public void setUnlockVisible(boolean unlockVisible) {
		this.unlockVisible = unlockVisible;
	}

	public boolean isAddDocumentVisible() {
		return addDocumentVisible;
	}

	public void setAddDocumentVisible(boolean addDocumentVisible) {
		this.addDocumentVisible = addDocumentVisible;
	}

	public boolean isCheckInVisible() {
		return checkInVisible;
	}

	public void setCheckInVisible(boolean checkInVisible) {
		this.checkInVisible = checkInVisible;
	}

	public boolean isCheckOutVisible() {
		return checkOutVisible;
	}

	public void setCheckOutVisible(boolean checkOutVisible) {
		this.checkOutVisible = checkOutVisible;
	}

	public boolean isCancelCheckOutVisible() {
		return cancelCheckOutVisible;
	}

	public void setCancelCheckOutVisible(boolean cancelCheckOutVisible) {
		this.cancelCheckOutVisible = cancelCheckOutVisible;
	}

	public boolean isDeleteVisible() {
		return deleteVisible;
	}

	public void setDeleteVisible(boolean deleteVisible) {
		this.deleteVisible = deleteVisible;
	}

	public boolean isAddPropertyGroupVisible() {
		return addPropertyGroupVisible;
	}

	public void setAddPropertyGroupVisible(boolean addPropertyGroupVisible) {
		this.addPropertyGroupVisible = addPropertyGroupVisible;
	}

	public boolean isRemovePropertyGroupVisible() {
		return removePropertyGroupVisible;
	}

	public void setRemovePropertyGroupVisible(boolean removePropertyGroupVisible) {
		this.removePropertyGroupVisible = removePropertyGroupVisible;
	}

	public boolean isStartWorkflowVisible() {
		return startWorkflowVisible;
	}

	public void setStartWorkflowVisible(boolean startWorkflowVisible) {
		this.startWorkflowVisible = startWorkflowVisible;
	}

	public boolean isAddSubscriptionVisible() {
		return addSubscriptionVisible;
	}

	public void setAddSubscriptionVisible(boolean addSubscriptionVisible) {
		this.addSubscriptionVisible = addSubscriptionVisible;
	}

	public boolean isRemoveSubscriptionVisible() {
		return removeSubscriptionVisible;
	}

	public void setRemoveSubscriptionVisible(boolean removeSubscriptionVisible) {
		this.removeSubscriptionVisible = removeSubscriptionVisible;
	}

	public boolean isRefreshVisible() {
		return refreshVisible;
	}

	public void setRefreshVisible(boolean refreshVisible) {
		this.refreshVisible = refreshVisible;
	}

	public boolean isGoHomeVisible() {
		return goHomeVisible;
	}

	public void setGoHomeVisible(boolean goHomeVisible) {
		this.goHomeVisible = goHomeVisible;
	}
	
	public boolean isSetHomeVisible() {
		return setHomeVisible;
	}

	public void setSetHomeVisible(boolean setHomeVisible) {
		this.setHomeVisible = setHomeVisible;
	}

	public boolean isScannerVisible() {
		return scannerVisible;
	}

	public void setScannerVisible(boolean scannerVisible) {
		this.scannerVisible = scannerVisible;
	}

	public boolean isUploaderVisible() {
		return uploaderVisible;
	}

	public void setUploaderVisible(boolean uploaderVisible) {
		this.uploaderVisible = uploaderVisible;
	}

	public boolean isRenameVisible() {
		return renameVisible;
	}

	public void setRenameVisible(boolean renameVisible) {
		this.renameVisible = renameVisible;
	}

	public boolean isCopyVisible() {
		return copyVisible;
	}

	public void setCopyVisible(boolean copyVisible) {
		this.copyVisible = copyVisible;
	}

	public boolean isMoveVisible() {
		return moveVisible;
	}

	public void setMoveVisible(boolean moveVisible) {
		this.moveVisible = moveVisible;
	}

	public boolean isAddBookmarkVisible() {
		return addBookmarkVisible;
	}

	public void setAddBookmarkVisible(boolean addBookmarkVisible) {
		this.addBookmarkVisible = addBookmarkVisible;
	}
	
	public boolean isExportVisible() {
		return exportVisible;
	}

	public void setExportVisible(boolean exportVisible) {
		this.exportVisible = exportVisible;
	}

	public boolean isCreateFromTemplateVisible() {
		return createFromTemplateVisible;
	}

	public void setCreateFromTemplateVisible(boolean createFromTemplateVisible) {
		this.createFromTemplateVisible = createFromTemplateVisible;
	}

	public boolean isPurgeVisible() {
		return purgeVisible;
	}

	public void setPurgeVisible(boolean purgeVisible) {
		this.purgeVisible = purgeVisible;
	}

	public boolean isRestoreVisible() {
		return restoreVisible;
	}

	public void setRestoreVisible(boolean restoreVisible) {
		this.restoreVisible = restoreVisible;
	}

	public boolean isPurgeTrashVisible() {
		return purgeTrashVisible;
	}

	public void setPurgeTrashVisible(boolean purgeTrashVisible) {
		this.purgeTrashVisible = purgeTrashVisible;
	}

	public boolean isSendDocumentLinkVisible() {
		return sendDocumentLinkVisible;
	}

	public void setSendDocumentLinkVisible(boolean sendDocumentLinkVisible) {
		this.sendDocumentLinkVisible = sendDocumentLinkVisible;
	}

	public boolean isSkinVisible() {
		return skinVisible;
	}

	public void setSkinVisible(boolean skinVisible) {
		this.skinVisible = skinVisible;
	}

	public boolean isDebugVisible() {
		return debugVisible;
	}

	public void setDebugVisible(boolean debugVisible) {
		this.debugVisible = debugVisible;
	}

	public boolean isAdministrationVisible() {
		return administrationVisible;
	}

	public void setAdministrationVisible(boolean administrationVisible) {
		this.administrationVisible = administrationVisible;
	}

	public boolean isManageBookmarksVisible() {
		return manageBookmarksVisible;
	}

	public void setManageBookmarksVisible(boolean manageBookmarksVisible) {
		this.manageBookmarksVisible = manageBookmarksVisible;
	}

	public boolean isHelpVisible() {
		return helpVisible;
	}

	public void setHelpVisible(boolean helpVisible) {
		this.helpVisible = helpVisible;
	}

	public boolean isDocumentationVisible() {
		return documentationVisible;
	}

	public void setDocumentationVisible(boolean documentationVisible) {
		this.documentationVisible = documentationVisible;
	}

	public boolean isBugReportVisible() {
		return bugReportVisible;
	}

	public void setBugReportVisible(boolean bugReportVisible) {
		this.bugReportVisible = bugReportVisible;
	}

	public boolean isSupportRequestVisible() {
		return supportRequestVisible;
	}

	public void setSupportRequestVisible(boolean supportRequestVisible) {
		this.supportRequestVisible = supportRequestVisible;
	}

	public boolean isPublicForumVisible() {
		return publicForumVisible;
	}

	public void setPublicForumVisible(boolean publicForumVisible) {
		this.publicForumVisible = publicForumVisible;
	}

	public boolean isVersionChangesVisible() {
		return versionChangesVisible;
	}

	public void setVersionChangesVisible(boolean versionChangesVisible) {
		this.versionChangesVisible = versionChangesVisible;
	}

	public boolean isProjectWebVisible() {
		return projectWebVisible;
	}

	public void setProjectWebVisible(boolean projectWebVisible) {
		this.projectWebVisible = projectWebVisible;
	}

	public boolean isAboutVisible() {
		return aboutVisible;
	}

	public void setAboutVisible(boolean aboutVisible) {
		this.aboutVisible = aboutVisible;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("createFolderVisible="); sb.append(createFolderVisible);
		sb.append(", findFolderVisible="); sb.append(findFolderVisible);
		sb.append(", goFolderVisible="); sb.append(goFolderVisible);
		sb.append(", downloadVisible="); sb.append(downloadVisible);
		sb.append(", downloadPdfVisible="); sb.append(downloadPdfVisible);
		sb.append(", lockVisible="); sb.append(lockVisible);
		sb.append(", unlockVisible="); sb.append(unlockVisible);
		sb.append(", addDocumentVisible="); sb.append(addDocumentVisible);
		sb.append(", checkInVisible="); sb.append(checkInVisible);
		sb.append(", checkOutVisible="); sb.append(checkOutVisible);
		sb.append(", cancelCheckOutVisible="); sb.append(cancelCheckOutVisible);
		sb.append(", deleteVisible="); sb.append(deleteVisible);
		sb.append(", addPropertyGroupVisible="); sb.append(addPropertyGroupVisible);
		sb.append(", removePropertyGroupVisible="); sb.append(removePropertyGroupVisible);
		sb.append(", startWorkflowVisible="); sb.append(startWorkflowVisible);
		sb.append(", addSubscriptionVisible="); sb.append(addSubscriptionVisible);
		sb.append(", removeSubscriptionVisible="); sb.append(removeSubscriptionVisible);
		sb.append(", refreshVisible="); sb.append(refreshVisible);
		sb.append(", goHomeVisible="); sb.append(goHomeVisible);
		sb.append(", setHomeVisible="); sb.append(setHomeVisible);
		sb.append(", scannerVisible="); sb.append(scannerVisible);
		sb.append(", uploaderVisible="); sb.append(uploaderVisible);
		sb.append(", renameVisible="); sb.append(renameVisible);
		sb.append(", copyVisible="); sb.append(copyVisible);
		sb.append(", moveVisible="); sb.append(moveVisible);
		sb.append(", addBookmarkVisible="); sb.append(addBookmarkVisible);
		sb.append("}");
		return sb.toString();
	}
}
