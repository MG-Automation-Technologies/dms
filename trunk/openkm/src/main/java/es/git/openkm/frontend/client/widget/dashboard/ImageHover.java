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

package es.git.openkm.frontend.client.widget.dashboard;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesMouseEvents;

/**
 * Image hovering on mouse over
 * 
 * @author jllort
 *
 */
public class ImageHover extends Image implements HasClickHandlers, SourcesMouseEvents {
	private MouseListenerCollection mouseListeners;
	private String url = "";
	private String urlHover = "";
	
	public ImageHover(String url, String urlHover) {
		super(url);
		sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
		this.url = url;
		this.urlHover = urlHover;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addHandler(handler, ClickEvent.getType());
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SourcesMouseEvents#addMouseListener(com.google.gwt.user.client.ui.MouseListener)
	 */
	public void addMouseListener(MouseListener listener) {
	    if (mouseListeners == null) {
	      mouseListeners = new MouseListenerCollection();
	    }
	    mouseListeners.add(listener);
	  }
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.SourcesMouseEvents#removeMouseListener(com.google.gwt.user.client.ui.MouseListener)
	 */
	public void removeMouseListener(MouseListener listener) {
	    if (mouseListeners != null) {
	      mouseListeners.remove(listener);
	    }
	  }
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event) {
	    switch (DOM.eventGetType(event)) {    	
	    	case Event.ONMOUSEDOWN:
	        case Event.ONMOUSEUP:
	        case Event.ONMOUSEMOVE:
	        case Event.ONMOUSEOVER:
	        	if (!super.getUrl().equals(urlHover)) {
	        		super.setUrl(urlHover);
	        	}
	        	if (mouseListeners != null) {
	        		mouseListeners.fireMouseEvent(this, event);
		        }
		        break;
	        case Event.ONMOUSEOUT: {
	        	if (!super.getUrl().equals(url)) {
	        		super.setUrl(url);
	        	}
	        	if (mouseListeners != null) {
	        		mouseListeners.fireMouseEvent(this, event);
	        	}
	          break;
	        }
	    	
	    }
	    super.onBrowserEvent(event);
	}
}