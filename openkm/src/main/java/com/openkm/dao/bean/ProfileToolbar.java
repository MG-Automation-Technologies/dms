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

package com.openkm.dao.bean;

import java.io.Serializable;

public class ProfileToolbar implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean createFolder;
	private boolean findFolder;
	private boolean findDocument;
	private boolean download;
	private boolean downloadPdf;
	private boolean print;
	private boolean lock;
	private boolean unlock;
	private boolean addDocument;
	private boolean checkout;
	private boolean checkin;
	private boolean cancelCheckout;
	private boolean delete;
	private boolean addPropertyGroup;
	private boolean removePropertyGroup;
	private boolean startWorkflow;
	private boolean addSubscription;
	private boolean removeSubscription;
	private boolean refresh;
	private boolean home;
	private boolean scanner;
	private boolean uploader;

	public boolean isCreateFolder() {
		return createFolder;
	}

	public void setCreateFolder(boolean createFolder) {
		this.createFolder = createFolder;
	}

	public boolean isFindFolder() {
		return findFolder;
	}

	public void setFindFolder(boolean findFolder) {
		this.findFolder = findFolder;
	}
	
	public boolean isFindDocument() {
		return findDocument;
	}

	public void setFindDocument(boolean findDocument) {
		this.findDocument = findDocument;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isUnlock() {
		return unlock;
	}

	public void setUnlock(boolean unlock) {
		this.unlock = unlock;
	}

	public boolean isAddDocument() {
		return addDocument;
	}

	public void setAddDocument(boolean addDocument) {
		this.addDocument = addDocument;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	public boolean isCheckin() {
		return checkin;
	}

	public void setCheckin(boolean checkin) {
		this.checkin = checkin;
	}

	public boolean isCancelCheckout() {
		return cancelCheckout;
	}

	public void setCancelCheckout(boolean cancelCheckout) {
		this.cancelCheckout = cancelCheckout;
	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(boolean download) {
		this.download = download;
	}

	public boolean isDownloadPdf() {
		return downloadPdf;
	}

	public void setDownloadPdf(boolean downloadPdf) {
		this.downloadPdf = downloadPdf;
	}

	public boolean isAddPropertyGroup() {
		return addPropertyGroup;
	}

	public void setAddPropertyGroup(boolean addPropertyGroup) {
		this.addPropertyGroup = addPropertyGroup;
	}

	public boolean isRemovePropertyGroup() {
		return removePropertyGroup;
	}

	public void setRemovePropertyGroup(boolean removePropertyGroup) {
		this.removePropertyGroup = removePropertyGroup;
	}

	public boolean isStartWorkflow() {
		return startWorkflow;
	}

	public void setStartWorkflow(boolean startWorkflow) {
		this.startWorkflow = startWorkflow;
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

	public boolean isHome() {
		return home;
	}

	public void setHome(boolean home) {
		this.home = home;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public boolean isScanner() {
		return scanner;
	}

	public void setScanner(boolean scanner) {
		this.scanner = scanner;
	}

	public boolean isUploader() {
		return uploader;
	}

	public void setUploader(boolean uploader) {
		this.uploader = uploader;
	}
	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("createFolder="); sb.append(createFolder);
		sb.append(", findFolder="); sb.append(findFolder);
		sb.append(", findDocument="); sb.append(findDocument);
		sb.append(", download="); sb.append(download);
		sb.append(", downloadPdf="); sb.append(downloadPdf);
		sb.append(", print="); sb.append(print);
		sb.append(", addDocument="); sb.append(addDocument);
		sb.append(", lock="); sb.append(lock);
		sb.append(", unlock="); sb.append(unlock);
		sb.append(", checkout="); sb.append(checkout);
		sb.append(", cancelCheckout="); sb.append(cancelCheckout);
		sb.append(", delete="); sb.append(delete);
		sb.append(", addPropertyGroup="); sb.append(addPropertyGroup);
		sb.append(", removePropertyGroup="); sb.append(removePropertyGroup);
		sb.append(", startWorkflow="); sb.append(startWorkflow);
		sb.append(", addSubscription="); sb.append(addSubscription);
		sb.append(", removeSubscription="); sb.append(removeSubscription);
		sb.append(", home="); sb.append(home);
		sb.append(", refresh="); sb.append(refresh);
		sb.append(", scanner="); sb.append(scanner);
		sb.append(", uploader="); sb.append(uploader);
		sb.append("}");
		return sb.toString();
	}
}
