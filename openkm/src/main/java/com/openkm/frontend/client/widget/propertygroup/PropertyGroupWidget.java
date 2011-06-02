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

package com.openkm.frontend.client.widget.propertygroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.openkm.frontend.client.Main;
import com.openkm.frontend.client.bean.GWTCheckBox;
import com.openkm.frontend.client.bean.GWTFormElement;
import com.openkm.frontend.client.bean.GWTInput;
import com.openkm.frontend.client.bean.GWTPropertyGroup;
import com.openkm.frontend.client.bean.GWTSelect;
import com.openkm.frontend.client.bean.GWTSuggestBox;
import com.openkm.frontend.client.bean.GWTTextArea;
import com.openkm.frontend.client.extension.event.HasPropertyGroupEvent;
import com.openkm.frontend.client.extension.event.handler.PropertyGroupHandlerExtension;
import com.openkm.frontend.client.extension.event.hashandler.HasPropertyGroupHandlerExtension;
import com.openkm.frontend.client.service.OKMPropertyGroupService;
import com.openkm.frontend.client.service.OKMPropertyGroupServiceAsync;
import com.openkm.frontend.client.widget.form.FormManager;

import eu.maydu.gwt.validation.client.ValidationProcessor;

/**
 * PropertyGroupWidget
 * 
 * @author jllort
 *
 */
public class PropertyGroupWidget extends Composite implements HasPropertyGroupEvent, HasPropertyGroupHandlerExtension  {
	
	private final OKMPropertyGroupServiceAsync propertyGroupService = (OKMPropertyGroupServiceAsync) GWT.create(OKMPropertyGroupService.class);
	
	private String docPath;
	private CellFormatter cellFormatter;
	private PropertyGroupWidgetToFire propertyGroupWidgetToFire;
	private List<PropertyGroupHandlerExtension> propertyGroupHandlerExtensionList;
	private Map<String,String> valuesMap = new HashMap<String, String>();
	private GWTPropertyGroup propertyGroup;
	private FormManager manager;
	
	/**
	 * PropertyGroup
	 * 
	 * @param docPath The document path
	 * @param propertyGroup The group
	 * @param widget Widget at first row
	 * @param PropertyGroupWidgetToFire widget with methods to be fired
	 * @param valuesMap map of initial values
	 */
	public PropertyGroupWidget(String docPath, GWTPropertyGroup propertyGroup, Widget widget, PropertyGroupWidgetToFire propertyGroupWidgetToFire, 
			Map<String,String> valuesMap) {
		this.valuesMap = valuesMap;
		start(docPath, propertyGroup, widget, propertyGroupWidgetToFire);
	}
	
	/**
	 * PropertyGroup
	 * 
	 * @param docPath The document path
	 * @param propertyGroup The group 
	 * @param widget Widget at first row
	 * @param PropertyGroupWidgetToFire widget with methods to be fired
	 */
	public PropertyGroupWidget(String docPath, GWTPropertyGroup propertyGroup, Widget widget, PropertyGroupWidgetToFire propertyGroupWidgetToFire) {	
		start(docPath, propertyGroup, widget, propertyGroupWidgetToFire);
	}
	
	/**
	 * start
	 * 
	 * @param docPath
	 * @param propertyGroup
	 * @param widget
	 * @param propertyGroupWidgetToFire
	 */
	private void start(String docPath, GWTPropertyGroup propertyGroup, Widget widget, PropertyGroupWidgetToFire propertyGroupWidgetToFire) {
		propertyGroupHandlerExtensionList = new ArrayList<PropertyGroupHandlerExtension>();
		manager = new FormManager();
		this.docPath = docPath;
		this.propertyGroup = propertyGroup;
		this.propertyGroupWidgetToFire = propertyGroupWidgetToFire;
		
		FlexTable table = manager.getTable();
		table.setWidth("100%");

		table.setWidget(0, 0, widget);
		table.getFlexCellFormatter().setColSpan(0,0,2);
			
		// Widget format
		table.getCellFormatter().setHorizontalAlignment(0,0,HasAlignment.ALIGN_CENTER);
		table.getCellFormatter().setVerticalAlignment(0,0,HasAlignment.ALIGN_MIDDLE);
		
		RowFormatter rowFormatter = table.getRowFormatter();
		rowFormatter.setStyleName(0, "okm-Security-Title");
		
		cellFormatter = table.getCellFormatter(); // Gets the cell formatter
			
		// Format borders and margins
		cellFormatter.addStyleName(0,0,"okm-Security-Title-RightBorder");
		
		initWidget(table);
	}

	/**
	 * Gets asyncronous to group properties
	 */
	final AsyncCallback<List<GWTFormElement>> callbackGetProperties = new AsyncCallback<List<GWTFormElement>>() {
		public void onSuccess(List<GWTFormElement> result){			
			for (GWTFormElement formElement : result) {
				// Loading initial values
				if (!valuesMap.isEmpty() && valuesMap.containsKey(formElement.getName())) {
					if (formElement instanceof GWTTextArea) {
						((GWTTextArea)formElement).setValue(valuesMap.get(formElement.getName()));
					} else if (formElement instanceof GWTInput) {
						((GWTInput)formElement).setValue(valuesMap.get(formElement.getName()));
					} else if (formElement instanceof GWTSuggestBox) {
						((GWTSuggestBox)formElement).setValue(valuesMap.get(formElement.getName()));
					} else if (formElement instanceof GWTCheckBox) {
						((GWTCheckBox)formElement).setValue(new Boolean(valuesMap.get(formElement.getName())));
					} else if (formElement instanceof GWTSelect) {
						// Not implemented
					} 
				}
			}
			
			manager.setFormElements(result);
			manager.draw(propertyGroup.isReadonly());
			
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedGetProperties();
			}
			fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_GET_PROPERTIES);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("getMetaData", caught);
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedGetProperties();
			}
		}
	};
	
	/**
	 * Gets asyncronous to set properties
	 */
	final AsyncCallback<Object> callbackSetProperties = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedSetProperties();
			}
			fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_CHANGED);
		}

		public void onFailure(Throwable caught) {
			if (propertyGroupWidgetToFire!=null) {
				propertyGroupWidgetToFire.finishedSetProperties();
			}
			Main.get().showError("setProperties", caught);
		}
	};
	
	/**
	 * Gets asyncronous to remove document group properties
	 */
	final AsyncCallback<Object> callbackRemoveGroup = new AsyncCallback<Object>() {
		public void onSuccess(Object result){
			fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_REMOVED);
		}

		public void onFailure(Throwable caught) {
			Main.get().showError("callbackRemoveGroup", caught);
		}
	};
	
	/**
	 * edit
	 */
	public void edit() {
		manager.edit();
		fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_EDIT);
	}
	
	/**
	 * updateFormElementsValuesWithNewer
	 * 
	 * @return
	 */
	public List<GWTFormElement> updateFormElementsValuesWithNewer() {
		return manager.updateFormElementsValuesWithNewer();
	}
	
	
	/**
	 * Sets the properties values
	 */
	public void setProperties() {
		manager.updateFormElementsValuesWithNewer();
		manager.draw(propertyGroup.isReadonly());
		propertyGroupService.setProperties(docPath, propertyGroup.getName(), manager.updateFormElementsValuesWithNewer(), callbackSetProperties);
	}
	
	/**
	 * Cancel edition and restores values
	 */
	public void cancelEdit() {
		manager.draw(propertyGroup.isReadonly());
		fireEvent(HasPropertyGroupEvent.PROPERTYGROUP_CANCEL_EDIT);
	}
	
	/**
	 * Gets all group properties 
	 */
	public void getProperties() {
		propertyGroupService.getProperties(docPath, propertyGroup.getName(), callbackGetProperties);
	}

	/**
	 * Remove the document property group
	 */
	public void removeGroup() {
		propertyGroupService.removeGroup(docPath, propertyGroup.getName(), callbackRemoveGroup);
	}
	
	/**
	 * Gets the group name
	 * 
	 * @return The group name
	 */
	public String getGrpName(){
		return propertyGroup.getName();
	}
	
	/**
	 * getValidationProcessor
	 * 
	 * @return
	 */
	public ValidationProcessor getValidationProcessor() {
		return manager.getValidationProcessor();
	}
	

	@Override
	public void fireEvent(PropertyGroupEventConstant event) {
		for (Iterator<PropertyGroupHandlerExtension> it = propertyGroupHandlerExtensionList.iterator(); it.hasNext();) {
			it.next().onChange(event);
		}
	}

	@Override
	public void addPropertyGroupHandlerExtension(PropertyGroupHandlerExtension handlerExtension) {
		propertyGroupHandlerExtensionList.add(handlerExtension);
	}
}
