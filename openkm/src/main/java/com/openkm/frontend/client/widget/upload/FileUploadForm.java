/**
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
package com.openkm.frontend.client.widget.upload;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.contants.service.RPCService;

/**
 * FileUploadForm
 * 
 * @author jllort
 *
 */
public class FileUploadForm extends Composite {
	
	private FormPanel uploadForm;
	private VerticalPanel mainPanel;
	private TextBox inputPath;
	private TextBox inputAction;
	private TextBox inputRenameDocument;
	private FileUpload fileUpload;
	
	/**
	 * FileUploadForm
	 */
	public FileUploadForm(FileUpload fileUpload, String size) {
		this.fileUpload = fileUpload;
		fileUpload.setStyleName("okm-Input");
		fileUpload.getElement().setAttribute("size", size);
		// Set the name of the upload file form element
		fileUpload.setName("uploadFormElement");
		
		uploadForm = new FormPanel();
		mainPanel = new VerticalPanel();
		inputPath = new TextBox();
		inputAction = new TextBox();
		inputRenameDocument = new TextBox();
		
		// Set Form details
		// Set the action to call on submit
		uploadForm.setAction(RPCService.FileUploadService);
		// Set the form encoding to multipart to indicate a file upload
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		// Set the method to Post
		uploadForm.setMethod(FormPanel.METHOD_POST);
		
		inputPath.setName("path");
		inputPath.setVisible(false);
		mainPanel.add(inputPath);
		
		inputAction.setName("action");
		inputAction.setVisible(false);
		mainPanel.add(inputAction);
		
		inputRenameDocument.setName("rename");
		inputRenameDocument.setVisible(false);
		mainPanel.add(inputRenameDocument);
		
		mainPanel.add(fileUpload);
		
		uploadForm.setWidget(mainPanel);
		
		initWidget(uploadForm);
	}
	
	/**
	 * addSubmitCompleteHandler
	 * 
	 * @param submitCompleHandler
	 */
	public void addSubmitCompleteHandler(SubmitCompleteHandler submitCompleHandler) {
		uploadForm.addSubmitCompleteHandler(submitCompleHandler);
	}
	
	/**
	 * setEncoding
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encodingType) {
		uploadForm.setEncoding(encodingType);
	}
	
	/**
	 * Set the path
	 * @param path String path
	 */
	public void setPath(String path) {
		inputPath.setText(path);
	}
	
	/**
	 * setAction
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		inputAction.setText(action);
	}
	
	/**
	 * setRename
	 * 
	 * @param rename
	 */
	public void setRename(String rename) {
		if (rename!=null && !rename.equals("")) {
			inputRenameDocument.setText(rename);
		}
	}
	
	/**
	 * getFileName
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileUpload.getFilename();
	}
	
	public void submit() {
		uploadForm.submit();
	}
}

