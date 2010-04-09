/**
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
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

package es.git.openkm.frontend.client.widget.searchin;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * FolderSelectPopup
 * 
 * @author jllort
 *
 */
public class FolderSelectPopup extends DialogBox  {
	
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private HorizontalPanel hListPanel;
	private ScrollPanel scrollDirectoryPanel;
	private VerticalPanel verticalDirectoryPanel;
	private FolderSelectTree folderSelectTree;
	private Button cancelButton;
	private Button actionButton;
	
	/**
	 * FolderSelectPopup
	 */
	public FolderSelectPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		vPanel = new VerticalPanel();		
		vPanel.setWidth("300");
		vPanel.setHeight("200");
		hPanel = new HorizontalPanel();
		hListPanel  = new HorizontalPanel();
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
		
		cancelButton = new Button(Main.i18n("button.cancel"), new ClickListener() {
			public void onClick(Widget sender) {
				hide();
			}
		});
		
		actionButton = new Button(Main.i18n("button.select"), new ClickListener() {
			public void onClick(Widget sender) {
				setRepositoryPath(folderSelectTree.getActualPath(), false);
			}	public static final int NAVIGATOR_TAXONOMY 	= 0;
			public static final int NAVIGATOR_TEMPLATES = 1;
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
		Main.get().mainPanel.search.searchIn.path.setText(actualPath);
		hide();
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("search.folder.filter"));
		cancelButton.setText(Main.i18n("button.cancel"));
		actionButton.setText(Main.i18n("button.select"));
	}
	
	/**
	 * Shows the popup 
	 */
	public void show() {
		int left = (Window.getClientWidth()-300) / 2;
		int top = (Window.getClientHeight()-200) / 2;
		setPopupPosition(left, top);
		setText(Main.i18n("search.folder.filter"));
		
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