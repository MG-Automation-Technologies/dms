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

package es.git.openkm.frontend.client.widget.properties;

import java.util.Date;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.bean.GWTDocument;
import es.git.openkm.frontend.client.bean.GWTNote;
import es.git.openkm.frontend.client.config.Config;
import es.git.openkm.frontend.client.service.OKMDocumentService;
import es.git.openkm.frontend.client.service.OKMDocumentServiceAsync;
import es.git.openkm.frontend.client.widget.richtext.RichTextToolbar;

/**
 * Notes
 * 
 * @author jllort
 *
 */
public class Notes extends Composite {
	
	private final OKMDocumentServiceAsync documentService = (OKMDocumentServiceAsync) GWT.create(OKMDocumentService.class);
	
	private FlexTable tableNotes;
	private FlexTable table;
	private GWTDocument document;
	private Button add;
	private ScrollPanel scrollPanel;
	private RichTextArea richTextArea;
	private RichTextToolbar toolbar;
	private VerticalPanel newNotePanel;
	private HTML addNote;
	private Grid gridRichText;
	
	public Notes () {
		document = new GWTDocument();
		table = new FlexTable();
		tableNotes = new FlexTable();
		scrollPanel = new ScrollPanel(table);
		newNotePanel = new VerticalPanel(); 
		addNote = new HTML("<b>" + Main.i18n("document.add.note") + "</b>");
		richTextArea = new RichTextArea();
		richTextArea.setSize("100%", "14em");
		toolbar = new RichTextToolbar(richTextArea);
	    toolbar.setWidth("100%");
	    
	    gridRichText = new Grid(2, 1);
	    gridRichText.setStyleName("cw-RichText");
	    gridRichText.setWidget(0, 0, toolbar);
	    gridRichText.setWidget(1, 0, richTextArea);
	    
		add = new Button(Main.i18n("button.add"), new ClickListener() {
			public void onClick(Widget sender) {
				addNote();
			}
		});
		
		HTML space = new HTML("");
		newNotePanel.add(space);
		newNotePanel.add(addNote);
		newNotePanel.add(gridRichText);
		HTML space2 = new HTML("");
		newNotePanel.add(space2);
		newNotePanel.add(add);
		
		newNotePanel.setCellHeight(space, "40px");
		newNotePanel.setCellHeight(space2, "10px");
		newNotePanel.setCellHorizontalAlignment(addNote, HasAlignment.ALIGN_CENTER);
		newNotePanel.setCellHorizontalAlignment(gridRichText, HasAlignment.ALIGN_CENTER);
		newNotePanel.setCellHorizontalAlignment(add, HasAlignment.ALIGN_CENTER);
		
		add.setStyleName("okm-Button");

		table.setWidget(0, 0, tableNotes);

		tableNotes.setStyleName("okm-DisableSelect");
		tableNotes.setWidth("100%");
		table.setWidth("100%");
		gridRichText.setStyleName("cw-RichText");
		
		initWidget(scrollPanel);
	}
	
	/**
	 * Set the WordWarp for all the row cells
	 * 
	 * @param row The row cell
	 * @param columns Number of row columns
	 * @param warp
	 * @param table The table to change word wrap
	 */
	private void setRowWordWarp(int row, int columns, boolean warp, FlexTable table) {
		CellFormatter cellFormatter = table.getCellFormatter();
		for (int i=0; i<columns; i++) {
			cellFormatter.setWordWrap(row, i, false);
		}
	}
	
	/**
	 * Sets the document values
	 * 
	 * @param doc The document object
	 */
	public void set(GWTDocument doc) {
		this.document = doc;
		richTextArea.setText("");
		
		while (table.getRowCount()>0) {
			table.removeRow(0);
		}
		
		for (Iterator<GWTNote> it = doc.getNotes().iterator(); it.hasNext();) {
			writeNote(it.next());
		}
		
		writeAddNote();
		
		// Sets wordWrap for al rows
		for (int i=0; i<tableNotes.getRowCount(); i++) {
			setRowWordWarp(i, 1, true, tableNotes);
		}
	}
	
	/**
	 * writeAddNote
	 */
	private void writeAddNote() {
		int row = table.getRowCount();
		table.setWidget(row, 0, newNotePanel);
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
		table.getCellFormatter().setHorizontalAlignment(row, 0, HasAlignment.ALIGN_CENTER);
	}
	
	/**
	 * Writes the note 
	 * 
	 * @param note
	 */
	private void writeNote(GWTNote note) {
		int row = table.getRowCount();
		row++;
		table.setHTML(row, 0, "<b>" + note.getUser() + "</b>");
		DateTimeFormat dtf = DateTimeFormat.getFormat(Main.i18n("general.date.pattern"));
		table.setHTML(row, 1, dtf.format(note.getDate()));
		table.getCellFormatter().setHorizontalAlignment(row, 1, HasAlignment.ALIGN_RIGHT);
		table.getRowFormatter().setStyleName(row, "okm-Notes-Title");
		table.getCellFormatter().setHeight(row, 1, "30");
		table.getCellFormatter().setVerticalAlignment(row, 0, HasAlignment.ALIGN_BOTTOM);
		table.getCellFormatter().setVerticalAlignment(row, 1, HasAlignment.ALIGN_BOTTOM);
		row++;
		table.setHTML(row, 0, "");
		table.getCellFormatter().setHeight(row, 0, "6");
		table.getRowFormatter().setStyleName(row, "okm-Notes-Line");
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
		row++;
		table.setHTML(row, 0, note.getText());
		table.getFlexCellFormatter().setColSpan(row, 0, 2);
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
	public void setVisibleButtons(boolean visible){
		add.setVisible(visible);
	}
	
	/**
	 * Callback addNote document
	 */
	final AsyncCallback<Object> callbackAddNote = new AsyncCallback<Object>() {
		public void onSuccess(Object result) {	
			GWTNote note = new GWTNote();
			note.setText(richTextArea.getHTML());
			note.setDate(new Date());
			note.setUser(Main.get().workspaceUserProperties.getUser());
			table.removeRow(table.getRowCount()-1); // Deletes last row = addComment
			writeNote(note);
			writeAddNote();
			richTextArea.setText("");
			document.getNotes().add(note);
			// If is added first note must adding some icon on filebrowser
			if (!document.isHasNotes()) {
				Main.get().mainPanel.browser.fileBrowser.addNoteIconToSelectedRow();
				document.setHasNotes(true);
			}
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
		documentService.addNote(document.getPath(), richTextArea.getHTML() , callbackAddNote);
	}
}
