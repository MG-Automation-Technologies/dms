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

package es.git.openkm.frontend.client.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;
import es.git.openkm.frontend.client.util.Util;

/**
 * Image viewer popup
 * 
 * @author jllort
 *
 */
public class ImageViewerPopup extends DialogBox implements HasAllMouseHandlers {

	private static final double ZOOM_RATIO = 0.25;
	private static final double ZOOM_RATIO_MINOR = 0.01;
	private static final int MARGIN_HEIGHT = 160;
	private static final int MARGIN_WIDTH = 80;

	private HorizontalPanel panel;
	private HorizontalPanel hPanel;
	private VerticalPanel vPanel;
	private ScrollPanel scrollPanel;
	private Button button;
	private Image image;
	private int height = 0;
	private int width = 0;
	private HTML zoomIn;
	private HTML zoomOut;
	private double actual_ratio = 0;
	private boolean loaded = false;
	private ListBox listZoom;
	private double[] zoomValues = {0, 0.25, 0.5, 0.75, 1, 1.5, 2, 3, 4 };
	private String[] zoomTextValues = {"-", "25%", "50%", "75%", "100%", "150%", "200%", "300%", "400%"};
	private int actualZoom = -1;
	
	/**
	 * Image viewer
	 */
	public ImageViewerPopup() {
		// Establishes auto-close when click outside
		super(false,true);
		
		/**
		 * Zoom in handler
		 */
		ClickHandler zoomInListener = new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (loaded && actualZoom<zoomValues.length) {
					if (actual_ratio >= ZOOM_RATIO) {
						actualZoom++;
						actual_ratio = zoomValues[actualZoom];
					} else {
						actual_ratio += ZOOM_RATIO_MINOR;
						if (actual_ratio==ZOOM_RATIO) {
							actualZoom = 1;
						} else {
							actualZoom = 0;
						}
					}
					resize();
					center();
					listZoom.setSelectedIndex(actualZoom);
				}
			}
		};
		
		/**
		 * Zoom out handler
		 */
		ClickHandler zoomOutListener = new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				if (loaded && actual_ratio>0.1) {
					if (actual_ratio > ZOOM_RATIO) {
						actualZoom--;
						actual_ratio = zoomValues[actualZoom];
					} else {
						actual_ratio -= ZOOM_RATIO_MINOR;
						actualZoom = 0;
					}
					resize();
					center();
					listZoom.setSelectedIndex(actualZoom);
				}
			}
		};
		
		vPanel = new VerticalPanel();
		image = new Image();
		scrollPanel = new ScrollPanel(image);
		setText(Main.i18n("image.viewer.label"));
		button = new Button(Main.i18n("button.close"), new ClickHandler() { 
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		button.setStyleName("okm-Input");
		
		zoomIn = new HTML(Util.imageHTML("img/icon/actions/zoom_in.gif",Main.i18n("image.viewer.zoom.in")));
		zoomOut = new HTML(Util.imageHTML("img/icon/actions/zoom_out.gif",Main.i18n("image.viewer.zoom.out")));
		zoomIn.addClickHandler(zoomInListener);
		zoomOut.addClickHandler(zoomOutListener);
		
		zoomIn.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-selected");
			}
		});
		
		zoomIn.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-selected");
			}
		});
		
		zoomOut.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.addStyleName("okm-ToolBar-selected");
			}
		});
		
		zoomOut.addMouseOutHandler(new MouseOutHandler(){
			@Override
			public void onMouseOut(MouseOutEvent event) {
				Widget sender = (Widget) event.getSource();
				sender.removeStyleName("okm-ToolBar-selected");
			}
		});
		
		zoomIn.setStyleName("okm-ToolBar-button");
		zoomOut.setStyleName("okm-ToolBar-button");		
		panel = new HorizontalPanel();
		panel.setHeight("30");
		panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		panel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		panel.addStyleName("okm-ToolBar");
		panel.add(space());
		panel.add(zoomIn);
		panel.add(space());
		panel.add(zoomOut);
		panel.add(space());
		panel.add(separator());
		panel.add(space());
		listZoom = new ListBox();
		for (int i=0; i<zoomValues.length; i++ ) {
			listZoom.addItem(zoomTextValues[i],""+i);
		}
		listZoom.setStyleName("okm-Input");
		listZoom.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent arg0) {
				actualZoom = Integer.parseInt(listZoom.getValue(listZoom.getSelectedIndex()));
				if (actualZoom > 0) {
					actual_ratio = zoomValues[actualZoom];
					resize();
					center();
				}
			}			
		});
		
		panel.add(listZoom);
		panel.add(space());
		panel.add(separator());
		
		hPanel = new HorizontalPanel();
		hPanel.addStyleName("okm-ToolBar");
		hPanel.setWidth("100%");
		hPanel.add(panel);

		//vPanel.setWidth("400px");
		//vPanel.setHeight("220px");
		
		vPanel.add(hPanel);
		vPanel.add(scrollPanel);
		vPanel.add(new HTML("<BR>"));
		vPanel.add(button);
		vPanel.add(new HTML("<BR>"));

		vPanel.setCellHorizontalAlignment(button, HasAlignment.ALIGN_CENTER);
		vPanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		vPanel.setCellVerticalAlignment(scrollPanel, HasAlignment.ALIGN_MIDDLE);

		hide();
		setWidget(vPanel);
	}
	
	/**
	 * Language refresh
	 */
	public void langRefresh() {
		setText(Main.i18n("image.viewer.label"));
		button.setHTML(Main.i18n("button.close"));
		zoomIn.setHTML(Util.imageHTML("img/icon/actions/zoom_in.gif",Main.i18n("image.viewer.zoom.in")));
		zoomOut.setHTML(Util.imageHTML("img/icon/actions/zoom_out.gif",Main.i18n("image.viewer.zoom.out")));
	}
	
	/**
	 * Set the image file to displayscrollPanel.setHeight(""+tmpHeight);
	 * 
	 * @param imageUrl The image url
	 */
	public void setImageFile(String imageUrl) {
		loaded = false;
		actualZoom = 4;
		actual_ratio = zoomValues[actualZoom];
		listZoom.setSelectedIndex(actualZoom); // Select by default at stating 100%
		scrollPanel.remove(image);
		image = new Image(imageUrl);
		scrollPanel.add(image); // Always remove image and set newer
		image.addLoadHandler(new LoadHandler(){
			@Override
			public void onLoad(LoadEvent arg0) {
				// Only executes on first time loading, because onLoad every time is image size changed is fired
				// and don't wants to make nothing on this cases
				if (!loaded) {
					loaded = true;
					getImageRealSize(); // Saves the real image size
					
					int tmpHeight = height;
					int tmpWidth = width; 
					
					// Trying to adapt to max window view
					boolean canResize = true;
					boolean resizeNeeded = false;
					while ((Window.getClientHeight()<tmpHeight || Window.getClientWidth()<tmpWidth) && canResize) {
						if (actualZoom==1) {
							canResize = false;
						} else {
							resizeNeeded = true;
							hide();
							actualZoom--;
							actual_ratio = zoomValues[actualZoom];
							tmpHeight = (new Double(height*actual_ratio)).intValue();
							tmpWidth = (new Double(width*actual_ratio)).intValue();							
						}
					}
					
					if (resizeNeeded) {
						resize();
						center();
					}
					
					listZoom.setSelectedIndex(actualZoom); // Select the real zoom index on startup
	
					// Setting scroll max size width / height ( using max window capabilities )
					if (Window.getClientWidth()-MARGIN_WIDTH >=tmpWidth) {
						scrollPanel.setWidth(""+tmpWidth);
					} else {
						scrollPanel.setWidth(""+(Window.getClientWidth()-MARGIN_WIDTH));
					}
					
					if (Window.getClientHeight()-MARGIN_HEIGHT >=tmpHeight) {
						scrollPanel.setHeight(""+tmpHeight);
					} else {
						scrollPanel.setHeight(""+(Window.getClientHeight()-MARGIN_HEIGHT));
					}
				}
			}
		});
	}
	
	/**
	 * Gets the image real size
	 */
	private void getImageRealSize() {
		height = image.getHeight();
		width = image.getWidth();
	}
	
	/**
	 * resize image
	 */
	private void resize() {
		int newHeight = (new Double(height*actual_ratio)).intValue();
		int newWidth = (new Double(width*actual_ratio)).intValue();
		
		image.setHeight(""+newHeight);
		image.setWidth(""+newWidth);
		
		// Setting scroll max size width / height ( using max window capabilities )
		if (Window.getClientWidth()-MARGIN_WIDTH >=newWidth) {
			scrollPanel.setWidth(""+newWidth);
		} else {
			scrollPanel.setWidth(""+(Window.getClientWidth()-MARGIN_WIDTH));
		}
		
		if (Window.getClientHeight()-MARGIN_HEIGHT >=newHeight) {
			scrollPanel.setHeight(""+newHeight);
		} else {
			scrollPanel.setHeight(""+(Window.getClientHeight()-MARGIN_HEIGHT));
		}
	}
	
	/**
	 * Gets the HTML space code
	 * 
	 * @return Space tool bar code
	 */
	private HTML space() {
		HTML space = new HTML(" ");
		space.setStyleName("okm-ToolBar-space");
		return space;
	}
	
	/**
	 * Gets the HTML separator code
	 * 
	 * @return Separator tool bar
	 */
	private HTML separator() {
		HTML space = new HTML(Util.imageHTML("img/icon/toolbar/separator.gif"));
		return space;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseDownHandlers#addMouseDownHandler(com.google.gwt.event.dom.client.MouseDownHandler)
	 */
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
	    return addDomHandler(handler, MouseDownEvent.getType());
	}
	  
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseMoveHandlers#addMouseMoveHandler(com.google.gwt.event.dom.client.MouseMoveHandler)
	 */
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseOutHandlers#addMouseOutHandler(com.google.gwt.event.dom.client.MouseOutHandler)
	 */
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
	    return addDomHandler(handler, MouseOutEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseOverHandlers#addMouseOverHandler(com.google.gwt.event.dom.client.MouseOverHandler)
	 */
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
	    return addDomHandler(handler, MouseOverEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseUpHandlers#addMouseUpHandler(com.google.gwt.event.dom.client.MouseUpHandler)
	 */
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
	    return addDomHandler(handler, MouseUpEvent.getType());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.HasMouseWheelHandlers#addMouseWheelHandler(com.google.gwt.event.dom.client.MouseWheelHandler)
	 */
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
	    return addDomHandler(handler, MouseWheelEvent.getType());
	}
}
