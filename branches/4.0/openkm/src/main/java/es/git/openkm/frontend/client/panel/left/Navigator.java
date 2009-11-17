/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
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

package es.git.openkm.frontend.client.panel.left;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.panel.ExtendedSizeComposite;
import es.git.openkm.frontend.client.panel.PanelDefinition;
import es.git.openkm.frontend.client.util.Util;
import es.git.openkm.frontend.client.widget.foldertree.Status;
import es.git.openkm.frontend.client.widget.mail.MailTree;
import es.git.openkm.frontend.client.widget.personal.PersonalTree;
import es.git.openkm.frontend.client.widget.taxonomy.TaxonomyTree;
import es.git.openkm.frontend.client.widget.template.TemplateTree;
import es.git.openkm.frontend.client.widget.trash.TrashTree;

/**
 * Navigator panel
 * 
 * @author jllort
 *
 */
public class Navigator extends ExtendedSizeComposite {

	public ExtendedStackPanel stackPanel;
	public ExtendedScrollPanel scrollTaxonomyPanel;
	private ScrollPanel scrollTrashPanel;
	private ScrollPanel scrollTemplatePanel;
	private ScrollPanel scrollMyDocumentsPanel;
	private ScrollPanel scrollMailPanel;
	public VerticalPanel verticalTaxonomyPanel;
	public VerticalPanel verticalTrashPanel;
	public VerticalPanel verticalTemplatePanel;
	public VerticalPanel verticalMyDocumentsPanel;
	public VerticalPanel verticalMailPanel;
	public TaxonomyTree taxonomyTree;
	public TemplateTree templateTree;
	public PersonalTree personalTree;
	public TrashTree trashTree;
	public MailTree mailTree;
	public Status status;
	
	public Navigator() {
		stackPanel = new ExtendedStackPanel();
		scrollTaxonomyPanel = new ExtendedScrollPanel();
		scrollTaxonomyPanel.setSize("100%", "100%");
		scrollTrashPanel = new ScrollPanel();
		scrollTrashPanel.setSize("100%", "100%");
		scrollTemplatePanel = new ExtendedScrollPanel();
		scrollTemplatePanel.setSize("100%", "100%");
		scrollMyDocumentsPanel = new ExtendedScrollPanel();
		scrollMyDocumentsPanel.setSize("100%", "100%");
		scrollMailPanel = new ExtendedScrollPanel();
		scrollMailPanel.setSize("100%", "100%");
		verticalTaxonomyPanel = new VerticalPanel();
		verticalTaxonomyPanel.setSize("100%", "100%");
		verticalTrashPanel = new VerticalPanel();
		verticalTrashPanel.setSize("100%", "100%");
		verticalTemplatePanel = new VerticalPanel();
		verticalTemplatePanel.setSize("100%", "100%");
		verticalMyDocumentsPanel = new VerticalPanel();
		verticalMyDocumentsPanel.setSize("100%", "100%");
		verticalMailPanel = new VerticalPanel();
		verticalMailPanel.setSize("100%", "100%");
		
		status = new Status();
		status.setStyleName("okm-StatusPopup");
		trashTree = new TrashTree();
		trashTree.setSize("100%", "100%");
		templateTree = new TemplateTree();
		templateTree.setSize("100%", "100%");
		mailTree = new MailTree();
		mailTree.setSize("100%", "100%");
		personalTree = new PersonalTree();
		personalTree.setSize("100%", "100%");
		taxonomyTree = new TaxonomyTree();
		taxonomyTree.setSize("100%", "100%");
		
		verticalTaxonomyPanel.add(taxonomyTree);
		scrollTaxonomyPanel.add(verticalTaxonomyPanel);
		verticalTrashPanel.add(trashTree);
		scrollTrashPanel.add(verticalTrashPanel);
		scrollTrashPanel.addStyleName("okm-DisableSelect"); // Disables drag and drop browser text selection
		verticalTemplatePanel.add(templateTree);
		scrollTemplatePanel.add(verticalTemplatePanel);
		verticalMyDocumentsPanel.add(personalTree);
		scrollMyDocumentsPanel.add(verticalMyDocumentsPanel);
		verticalMailPanel.add(mailTree);
		scrollMailPanel.add(verticalMailPanel);
		
		stackPanel.add(scrollTaxonomyPanel, Util.createHeaderHTML("img/icon/stackpanel/chart_organisation.gif", Main.i18n("leftpanel.label.taxonomy")), true);
		stackPanel.add(scrollTemplatePanel, Util.createHeaderHTML("img/icon/stackpanel/template.gif", Main.i18n("leftpanel.label.templates")), true);
		stackPanel.add(scrollMyDocumentsPanel, Util.createHeaderHTML("img/icon/stackpanel/personal.gif", Main.i18n("leftpanel.label.my.documents")), true);
		stackPanel.add(scrollMailPanel, Util.createHeaderHTML("img/icon/stackpanel/email.gif", Main.i18n("leftpanel.label.mail")), true);
		stackPanel.add(scrollTrashPanel, Util.createHeaderHTML("img/icon/stackpanel/bin.gif", Main.i18n("leftpanel.label.trash")), true);
		stackPanel.showStack(0);
		stackPanel.setStyleName("okm-StackPanel");
		//stackPanel.addStyleName("okm-DisableSelect");
		stackPanel.setFirsTime(false); 
		
		initWidget(stackPanel);
	}
	
	// Public methods to access between objects
	/**
	 * Refresh language descriptions
	 */
	public void langRefresh() {	
		stackPanel.setStackText(0, Util.createHeaderHTML("img/icon/stackpanel/chart_organisation.gif", Main.i18n("leftpanel.label.taxonomy")), true);
		stackPanel.setStackText(1, Util.createHeaderHTML("img/icon/stackpanel/template.gif", Main.i18n("leftpanel.label.templates")), true);
		stackPanel.setStackText(2, Util.createHeaderHTML("img/icon/stackpanel/personal.gif", Main.i18n("leftpanel.label.my.documents")), true);
		stackPanel.setStackText(3, Util.createHeaderHTML("img/icon/stackpanel/email.gif", Main.i18n("leftpanel.label.mail")), true);
		stackPanel.setStackText(4, Util.createHeaderHTML("img/icon/stackpanel/bin.gif", Main.i18n("leftpanel.label.trash")), true);
		taxonomyTree.langRefresh();
		personalTree.langRefresh();
		templateTree.langRefresh();
		trashTree.langRefresh();
	}
	
	/**
	 * Resizes all objects on the widget the panel and the tree
	 * 
	 * @param width The widget width
	 * @param height The widget height
	 */
	public void setSize(int width, int height) {
		stackPanel.setSize(""+width, ""+height);
		// Substract 2 pixels for borders on stackPanel
		scrollTaxonomyPanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
		scrollTrashPanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
	}
	
	/**
	 * Gets the stack index value
	 * 
	 * @return The stack index value
	 */
	public int getStackIndex() {
		return stackPanel.getStackIndex();
	}
}