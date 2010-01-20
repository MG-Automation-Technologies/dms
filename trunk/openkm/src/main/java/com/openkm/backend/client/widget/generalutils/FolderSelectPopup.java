/**
 *  Copyright (C) 2006  GIT Consultors
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

package com.openkm.backend.client.widget.generalutils;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.backend.client.Main;

public class FolderSelectPopup extends DialogBox  {
	
	public static final int NAVIGATOR_TAXONOMY 	= 0;
	public static final int NAVIGATOR_TEMPLATES = 1;
	
	public static final int ACTION_NONE = -1;
	public static final int	ACTION_IMPORT = 0;
	public static final int ACTION_EXPORT = 1;
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private HorizontalPanel hListPanel;
	private HorizontalPanel hContextPanel;
	private ScrollPanel scrollDirectoryPanel;
	private VerticalPanel verticalDirectoryPanel;
	private FolderSelectTree folderSelectTree;
	private Button cancelButton;
	private Button actionButton;
	private ListBox contextListBox;
	private HTML contextTxt;
	private int action = ACTION_NONE;

	
	public FolderSelectPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();		
		vPanel.setWidth("300");
		vPanel.setHeight("200");
		hPanel = new HorizontalPanel();
		hListPanel  = new HorizontalPanel();
		hContextPanel = new HorizontalPanel();
		
		contextTxt = new HTML(Main.i18n("general.util.folder.destination"));
		contextListBox = new ListBox();
		contextListBox.setStyleName("okm-Select");
		contextListBox.addItem(Main.i18n("general.util.folder.destination.taxonomy"),""+NAVIGATOR_TAXONOMY);
		contextListBox.addItem(Main.i18n("general.util.folder.destination.templates"),""+NAVIGATOR_TEMPLATES);
		contextListBox.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent arg0) {
					folderSelectTree.changeView(Integer.parseInt(contextListBox.getValue(contextListBox.getSelectedIndex())));
				}
			}
		);
		hContextPanel.add(contextTxt);
		hContextPanel.add(new HTML("&nbsp;&nbsp;"));
		hContextPanel.add(contextListBox);
		hContextPanel.setCellVerticalAlignment(contextTxt, HasVerticalAlignment.ALIGN_MIDDLE);
		
		hListPanel.add(hContextPanel);
		hListPanel.setWidth("290");
		
		scrollDirectoryPanel = new ScrollPanel();
		scrollDirectoryPanel.setSize("290", "150");
		scrollDirectoryPanel.setStyleName("okm-Popup-text");
		verticalDirectoryPanel = new VerticalPanel();
		verticalDirectoryPanel.setSize("100%", "100%");
		folderSelectTree = new FolderSelectTree();
		folderSelectTree.setSize("100%", "100%");
				
		verticalDirectoryPanel.add(folderSelectTree);
		scrollDirectoryPanel.add(verticalDirectoryPanel);
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		actionButton = new Button(Main.i18n("button.select"), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setRepositoryPath(folderSelectTree.getActualPath(), false);
			}
		});
		
		vPanel.add(new HTML("<br>"));
		vPanel.add(hListPanel);
		vPanel.add(new HTML("<br>"));
		vPanel.add(scrollDirectoryPanel);
		vPanel.add(new HTML("<br>"));
		hPanel.add(cancelButton);
		HTML space = new HTML();
		space.setWidth("50");
		hPanel.add(space);
		hPanel.add(actionButton);
		vPanel.add(hPanel);
		vPanel.add(new HTML("<br>"));
		
		vPanel.setCellHorizontalAlignment(hListPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(scrollDirectoryPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(hPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHeight(scrollDirectoryPanel, "150");

		cancelButton.setStyleName("okm-Input");
		actionButton.setStyleName("okm-Input");

		super.hide();
		setWidget(vPanel);
	}
	
	/**
	 * Sets the repository path
	 */
	public void setRepositoryPath(String actualPath, boolean refresh) {
		switch (action) {
			case ACTION_IMPORT:
				Main.get().centerPanel.generalUtilsPanel.generalUtils.importRepositoryPath.setText(actualPath);
				hide();
				break;
			
			case ACTION_EXPORT:
				Main.get().centerPanel.generalUtilsPanel.generalUtils.exportRepositoryPath.setText(actualPath);
				hide();
				break;
		}
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		contextTxt.setHTML(Main.i18n("search.context"));
		setText(Main.i18n("general.util.select.folder.destination"));
		cancelButton.setText(Main.i18n("button.cancel"));
		
		actionButton.setText(Main.i18n("button.select"));
		
		contextListBox.setItemText(0,Main.i18n("general.util.folder.destination.taxonomy"));
		contextListBox.setItemText(1,Main.i18n("general.util.folder.destination.templates"));
	}
	
	/**
	 * Shows the popup 
	 */
	public void show(int action) {
		this.action = action;
		int left = (Window.getClientWidth()-300) / 2;
		int top = (Window.getClientHeight()-200) / 2;
		setPopupPosition(left, top);
		setText(Main.i18n("general.util.select.folder.destination"));
		
		// Resets to initial tree value
		folderSelectTree.reset();
		super.show();
	}

	
	/**
	 * Enables or disables move buttom
	 * 
	 * @param enable
	 */
	public void enable(boolean enable) {
		actionButton.setEnabled(enable);
	}
}