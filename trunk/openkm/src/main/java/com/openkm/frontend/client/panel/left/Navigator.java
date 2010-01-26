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

package com.openkm.frontend.client.panel.left;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.panel.ExtendedSizeComposite;
import com.openkm.frontend.client.panel.PanelDefinition;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.categories.CategoriesTree;
import com.openkm.frontend.client.widget.foldertree.Status;
import com.openkm.frontend.client.widget.mail.MailTree;
import com.openkm.frontend.client.widget.personal.PersonalTree;
import com.openkm.frontend.client.widget.taxonomy.TaxonomyTree;
import com.openkm.frontend.client.widget.template.TemplateTree;
import com.openkm.frontend.client.widget.thesaurus.ThesaurusTree;
import com.openkm.frontend.client.widget.trash.TrashTree;

/**
 * Navigator panel
 * 
 * @author jllort
 *
 */
public class Navigator extends ExtendedSizeComposite {

	public ExtendedStackPanel stackPanel;
	public ExtendedScrollPanel scrollTaxonomyPanel;
	public ScrollPanel scrollCategoriesPanel;
	public ScrollPanel scrollThesaurusPanel;
	private ScrollPanel scrollTrashPanel;
	private ExtendedScrollPanel scrollTemplatePanel;
	private ExtendedScrollPanel scrollMyDocumentsPanel;
	private ExtendedScrollPanel scrollMailPanel;
	public VerticalPanel verticalTaxonomyPanel;
	public VerticalPanel verticalCategoriesPanel;
	public VerticalPanel verticalThesaurusPanel;
	public VerticalPanel verticalTrashPanel;
	public VerticalPanel verticalTemplatePanel;
	public VerticalPanel verticalMyDocumentsPanel;
	public VerticalPanel verticalMailPanel;
	public TaxonomyTree taxonomyTree;
	public CategoriesTree categoriesTree;
	public ThesaurusTree thesaurusTree;
	public TemplateTree templateTree;
	public PersonalTree personalTree;
	public TrashTree trashTree;
	public MailTree mailTree;
	public Status status;
	
	public Navigator() {
		stackPanel = new ExtendedStackPanel();
		scrollTaxonomyPanel = new ExtendedScrollPanel();
		scrollTaxonomyPanel.setSize("100%", "100%");
		scrollCategoriesPanel = new ScrollPanel();
		scrollCategoriesPanel.setSize("100%", "100%");
		scrollThesaurusPanel = new ScrollPanel();
		scrollThesaurusPanel.setSize("100%", "100%");
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
		verticalCategoriesPanel = new VerticalPanel();
		verticalCategoriesPanel.setSize("100%", "100%");
		verticalThesaurusPanel = new VerticalPanel();
		verticalThesaurusPanel.setSize("100%", "100%");
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
		thesaurusTree = new ThesaurusTree();
		thesaurusTree.setSize("100%", "100%");
		categoriesTree = new CategoriesTree();
		categoriesTree.setSize("100%", "100%");
		taxonomyTree = new TaxonomyTree();
		taxonomyTree.setSize("100%", "100%");
		
		verticalTaxonomyPanel.add(taxonomyTree);
		scrollTaxonomyPanel.add(verticalTaxonomyPanel);
		verticalCategoriesPanel.add(categoriesTree);
		scrollCategoriesPanel.add(verticalCategoriesPanel);
		verticalThesaurusPanel.add(thesaurusTree);
		scrollThesaurusPanel.add(verticalThesaurusPanel);
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
		stackPanel.add(scrollCategoriesPanel, Util.createHeaderHTML("img/icon/stackpanel/table_key.gif", Main.i18n("leftpanel.label.categories")), true);
		stackPanel.add(scrollThesaurusPanel, Util.createHeaderHTML("img/icon/stackpanel/book_open.gif", Main.i18n("leftpanel.label.thesaurus")), true);
		stackPanel.add(scrollTemplatePanel, Util.createHeaderHTML("img/icon/stackpanel/template.gif", Main.i18n("leftpanel.label.templates")), true);
		stackPanel.add(scrollMyDocumentsPanel, Util.createHeaderHTML("img/icon/stackpanel/personal.gif", Main.i18n("leftpanel.label.my.documents")), true);
		stackPanel.add(scrollMailPanel, Util.createHeaderHTML("img/icon/stackpanel/email.gif", Main.i18n("leftpanel.label.mail")), true);
		stackPanel.add(scrollTrashPanel, Util.createHeaderHTML("img/icon/stackpanel/bin.gif", Main.i18n("leftpanel.label.trash")), true);
		stackPanel.showStack(0);
		stackPanel.setStyleName("okm-StackPanel");
		//stackPanel.addStyleName("okm-DisableSelect"); // This style causes problem with cursor at renaming folder
		stackPanel.setFirsTime(false); 
		
		initWidget(stackPanel);
	}
	
	// Public methods to access between objects
	/**
	 * Refresh language descriptions
	 */
	public void langRefresh() {	
		stackPanel.setStackText(0, Util.createHeaderHTML("img/icon/stackpanel/chart_organisation.gif", Main.i18n("leftpanel.label.taxonomy")), true);
		stackPanel.setStackText(1, Util.createHeaderHTML("img/icon/stackpanel/table_key.gif", Main.i18n("leftpanel.label.categories")), true);
		stackPanel.setStackText(2, Util.createHeaderHTML("img/icon/stackpanel/book_open.gif", Main.i18n("leftpanel.label.thesaurus")), true);
		stackPanel.setStackText(3, Util.createHeaderHTML("img/icon/stackpanel/template.gif", Main.i18n("leftpanel.label.templates")), true);
		stackPanel.setStackText(4, Util.createHeaderHTML("img/icon/stackpanel/personal.gif", Main.i18n("leftpanel.label.my.documents")), true);
		stackPanel.setStackText(5, Util.createHeaderHTML("img/icon/stackpanel/email.gif", Main.i18n("leftpanel.label.mail")), true);
		stackPanel.setStackText(6, Util.createHeaderHTML("img/icon/stackpanel/bin.gif", Main.i18n("leftpanel.label.trash")), true);
		taxonomyTree.langRefresh();
		categoriesTree.langRefresh();
		thesaurusTree.langRefresh();
		personalTree.langRefresh();
		templateTree.langRefresh();
		mailTree.langRefresh();
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
		scrollCategoriesPanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
		scrollThesaurusPanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
		scrollMyDocumentsPanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
		scrollTemplatePanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
		scrollMailPanel.setSize(""+(width-2), ""+(height-2-(PanelDefinition.NUMBER_OF_STACKS * PanelDefinition.STACK_HEIGHT)));
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