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

package com.openkm.frontend.client.widget.properties;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTDocument;
import com.openkm.frontend.client.bean.GWTNote;
import com.openkm.frontend.client.config.Config;
import com.openkm.frontend.client.extension.event.HasDocumentEvent;
import com.openkm.frontend.client.service.OKMDocumentService;
import com.openkm.frontend.client.service.OKMDocumentServiceAsync;
import com.openkm.frontend.client.util.Util;
import com.openkm.frontend.client.widget.richtext.RichTextToolbar;

/**
 * Notes
 * 
 * @author jllort
 *
 */
public class Notes extends Composite {
	
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	
	private FlexTable tableNotes;
	private GWTDocument document;
	private Button add;
	private ScrollPanel scrollPanel;
	private RichTextArea richTextArea;
	private RichTextToolbar richTextToolbar;
	private TextArea textArea;
	private VerticalPanel newNotePanel;
	private HTML addNote;
	private Grid gridRichText;
	boolean visibleButtons = true;
	
	public Notes () {
		document = new GWTDocument();
		tableNotes = new FlexTable();
		scrollPanel = new ScrollPanel(tableNotes);
		newNotePanel = new VerticalPanel(); 
		addNote = new HTML("<b>" + Main.i18n("document.add.note") + "</b>");
		richTextArea = new RichTextArea();
		textArea = new TextArea();
		richTextArea.setSize("100%", "14em");
		textArea.setSize("500px", "200px");
		richTextToolbar = new RichTextToolbar(richTextArea);
	    richTextToolbar.setWidth("100%");
	    
	    gridRichText = new Grid(2, 1);
	    gridRichText.setStyleName("cw-RichText");
	    gridRichText.setWidget(0, 0, richTextToolbar);
	    gridRichText.setWidget(1, 0, richTextArea);
	    
		add = new Button(Main.i18n("button.add"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				addNote();
			}
		});
		
		HTML space = new HTML("");
		newNotePanel.add(space);
		newNotePanel.add(addNote);
		newNotePanel.add(gridRichText);
		newNotePanel.add(textArea);
		HTML space2 = new HTML("");
		newNotePanel.add(space2);
		newNotePanel.add(add);
		
		newNotePanel.setCellHeight(space, "40px");
		newNotePanel.setCellHeight(space2, "10px");
		newNotePanel.setCellHorizontalAlignment(addNote, HasAlignment.ALIGN_CENTER);
		newNotePanel.setCellHorizontalAlignment(gridRichText, HasAlignment.ALIGN_CENTER);
		newNotePanel.setCellHorizontalAlignment(add, HasAlignment.ALIGN_CENTER);
		
		add.setStyleName("okm-Button");

		tableNotes.setWidth("100%");
		gridRichText.setStyleName("cw-RichText");
		textArea.setStyleName("okm-Input");
		
		//Show hides panels depending browser to prevent problems with IE
		if (Util.getUserAgent().startsWith("ie")) {
			gridRichText.setVisible(false);
		} else {
			textArea.setVisible(false);
		}
		
		initWidget(scrollPanel);
	}
	
	/**
	 * Sets the document values
	 * 
	 * @param doc The document object
	 */
	public void set(GWTDocument doc) {
		this.document = doc;
		richTextArea.setText("");
		
		while (tableNotes.getRowCount()>0) {
			tableNotes.removeRow(0);
		}
		
		for (Iterator<GWTNote> it = doc.getNotes().iterator(); it.hasNext();) {
			writeNote(it.next());
		}
		
		writeAddNote();
	}
	
	/**
	 * writeAddNote
	 */
	private void writeAddNote() {
		int row = tableNotes.getRowCount();
		tableNotes.setWidget(row, 0, newNotePanel);
		tableNotes.getFlexCellFormatter().setColSpan(row, 0, 2);
		tableNotes.getCellFormatter().setHorizontalAlignment(row, 0, HasAlignment.ALIGN_CENTER);
	}
	
	/**
	 * Writes the note 
	 * 
	 * @param note
	 */
	private void writeNote(GWTNote note) {
		int row = tableNotes.getRowCount();
		tableNotes.setHTML(row, 0, "<b>" + note.getUser() + "</b>");
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		tableNotes.setHTML(row, 1, dtf.format(note.getDate()));
		tableNotes.getCellFormatter().setHorizontalAlignment(row, 1, HasAlignment.ALIGN_RIGHT);
		tableNotes.getRowFormatter().setStyleName(row, "okm-Notes-Title");
		tableNotes.getCellFormatter().setHeight(row, 1, "30");
		tableNotes.getCellFormatter().setVerticalAlignment(row, 0, HasAlignment.ALIGN_BOTTOM);
		tableNotes.getCellFormatter().setVerticalAlignment(row, 1, HasAlignment.ALIGN_BOTTOM);
		row++;
		tableNotes.setHTML(row, 0, "");
		tableNotes.getCellFormatter().setHeight(row, 0, "6");
		tableNotes.getRowFormatter().setStyleName(row, "okm-Notes-Line");
		tableNotes.getFlexCellFormatter().setColSpan(row, 0, 2);
		row++;
		tableNotes.setHTML(row, 0, note.getText());
		tableNotes.getFlexCellFormatter().setColSpan(row, 0, 2);
	}
	
	/**
	 * Lang refresh
	 */
	public void langRefresh() {		
		add.setHTML(Main.i18n("button.add"));
		addNote.setHTML("<b>" + Main.i18n("document.add.note") + "</b>");
	}	
	
	/**
	 * Sets visibility to buttons ( true / false )
	 * 
	 * @param visible The visible value
	 */
	public void setVisibleButtons(boolean visible) {
		visibleButtons = visible;
		add.setVisible(visible);
		addNote.setVisible(visible);
		if (Util.getUserAgent().startsWith("ie")) {
			textArea.setVisible(visible);
		} else {
			gridRichText.setVisible(visible);
		}
	}
	
	/**
	 * Sets the visible add note or not
	 * 
	 * @param visible The visible value
	 */
	public void setVisibleAddNote(boolean visible) {
		boolean addNoteOption = visible && visibleButtons;
		add.setVisible(addNoteOption);
		addNote.setVisible(addNoteOption);
		if (Util.getUserAgent().startsWith("ie")) {
			textArea.setVisible(addNoteOption);
		} else {
			gridRichText.setVisible(addNoteOption);
		}
	}
	
	/**
	 * Callback addNote document
	 */
	final AsyncCallback<Object> callbackAddNote = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {	
			GWTNote note = new GWTNote();
			if (Util.getUserAgent().startsWith("ie")) {
				note.setText(textArea.getText());
			} else {
				note.setText(richTextArea.getHTML());
			}
			note.setDate(new Date());
			note.setUser(Main.get().workspaceUserProperties.getUser());
			tableNotes.removeRow(tableNotes.getRowCount()-1); // Deletes last row = addComment
			writeNote(note);
			writeAddNote();
			richTextArea.setText("");
			textArea.setText("");
			document.getNotes().add(note);
			// If is added first note must adding some icon on filebrowser
			if (!document.isHasNotes()) {
				Main.get().mainPanel.desktop.browser.fileBrowser.addNoteIconToSelectedRow();
				document.setHasNotes(true);
			}
			Main.get().mainPanel.desktop.browser.tabMultiple.tabDocument.fireEvent(HasDocumentEvent.NOTE_ADDED);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("addComment", caught);
		}
	};
	
	/**
	 * addNote document
	 */
	private void addNote() {
		ServiceDefTarget endPoint = (ServiceDefTarget) documentService;
		endPoint.setServiceEntryPoint(Config.OKMDocumentService);
		String noteText = "";
		//Show hides panels depending browser to prevent problems with IE
		if (Util.getUserAgent().startsWith("ie")) {
			noteText = textArea.getText();
		} else {
			noteText = richTextArea.getHTML();
		}
		
		documentService.addNote(document.getPath(), noteText, callbackAddNote);
	}
	
	/**
	 * getNotes
	 * 
	 * @return
	 */
	public Collection<GWTNote> getNotes() {
		return document.getNotes();
	}
}
