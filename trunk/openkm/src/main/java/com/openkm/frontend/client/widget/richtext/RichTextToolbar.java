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

package com.openkm.frontend.client.widget.richtext;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
public class RichTextToolbar extends Composite {

  /**
   * This {@link ImageBundle} is used for all the button icons. Using an image
   * bundle allows all of these images to be packed into a single image, which
   * saves a lot of HTTP requests, drastically improving startup time.
   */
  public interface Images extends ImageBundle {

    AbstractImagePrototype bold();

    AbstractImagePrototype createLink();

    AbstractImagePrototype hr();

    AbstractImagePrototype indent();

    AbstractImagePrototype insertImage();

    AbstractImagePrototype italic();

    AbstractImagePrototype justifyCenter();

    AbstractImagePrototype justifyLeft();

    AbstractImagePrototype justifyRight();

    AbstractImagePrototype ol();

    AbstractImagePrototype outdent();

    AbstractImagePrototype removeFormat();

    AbstractImagePrototype removeLink();

    AbstractImagePrototype strikeThrough();

    AbstractImagePrototype subscript();

    AbstractImagePrototype superscript();

    AbstractImagePrototype ul();

    AbstractImagePrototype underline();
  }

  /**
   * We use an inner EventListener class to avoid exposing event methods on the
   * RichTextToolbar itself.
   */
  private KeyUpHandler keyUpHandler = new KeyUpHandler(){
	@Override
	public void onKeyUp(KeyUpEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == richText) {
	        // We use the RichTextArea's onKeyUp event to update the toolbar status.
	        // This will catch any cases where the user moves the cursur using the
	        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
	        updateStatus();
	    }
	}
  };
  
  private ClickHandler clickHandler = new ClickHandler(){
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == bold) {
	        basic.toggleBold();
	      } else if (sender == italic) {
	        basic.toggleItalic();
	      } else if (sender == underline) {
	        basic.toggleUnderline();
	      } else if (sender == subscript) {
	        basic.toggleSubscript();
	      } else if (sender == superscript) {
	        basic.toggleSuperscript();
	      } else if (sender == strikethrough) {
	        extended.toggleStrikethrough();
	      } else if (sender == indent) {
	        extended.rightIndent();
	      } else if (sender == outdent) {
	        extended.leftIndent();
	      } else if (sender == justifyLeft) {
	        basic.setJustification(RichTextArea.Justification.LEFT);
	      } else if (sender == justifyCenter) {
	        basic.setJustification(RichTextArea.Justification.CENTER);
	      } else if (sender == justifyRight) {
	        basic.setJustification(RichTextArea.Justification.RIGHT);
	      } else if (sender == insertImage) {
	        String url = Window.prompt("Enter an image URL:", "http://");
	        if (url != null) {
	          extended.insertImage(url);
	        }
	      } else if (sender == createLink) {
	        String url = Window.prompt("Enter a link URL:", "http://");
	        if (url != null) {
	          extended.createLink(url);
	        }
	      } else if (sender == removeLink) {
	        extended.removeLink();
	      } else if (sender == hr) {
	        extended.insertHorizontalRule();
	      } else if (sender == ol) {
	        extended.insertOrderedList();
	      } else if (sender == ul) {
	        extended.insertUnorderedList();
	      } else if (sender == removeFormat) {
	        extended.removeFormat();
	      } else if (sender == richText) {
	        // We use the RichTextArea's onKeyUp event to update the toolbar status.
	        // This will catch any cases where the user moves the cursur using the
	        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
	        updateStatus();
	      }
	}
  };
  
  private ChangeHandler changeHandler = new ChangeHandler(){
	@Override
	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == backColors) {
	        basic.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
	        backColors.setSelectedIndex(0);
	      } else if (sender == foreColors) {
	        basic.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
	        foreColors.setSelectedIndex(0);
	      } else if (sender == fonts) {
	        basic.setFontName(fonts.getValue(fonts.getSelectedIndex()));
	        fonts.setSelectedIndex(0);
	      } else if (sender == fontSizes) {
	        basic.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
	        fontSizes.setSelectedIndex(0);
	      }
	}
	  
  };

  private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[] {
      RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
      RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
      RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
      RichTextArea.FontSize.XX_LARGE};

  private Images images = (Images) GWT.create(Images.class);

  private RichTextArea richText;
  private RichTextArea.BasicFormatter basic;
  private RichTextArea.ExtendedFormatter extended;

  private VerticalPanel outer = new VerticalPanel();
  private HorizontalPanel topPanel = new HorizontalPanel();
  private HorizontalPanel bottomPanel = new HorizontalPanel();
  private ToggleButton bold;
  private ToggleButton italic;
  private ToggleButton underline;
  private ToggleButton subscript;
  private ToggleButton superscript;
  private ToggleButton strikethrough;
  private PushButton indent;
  private PushButton outdent;
  private PushButton justifyLeft;
  private PushButton justifyCenter;
  private PushButton justifyRight;
  private PushButton hr;
  private PushButton ol;
  private PushButton ul;
  private PushButton insertImage;
  private PushButton createLink;
  private PushButton removeLink;
  private PushButton removeFormat;

  private ListBox backColors;
  private ListBox foreColors;
  private ListBox fonts;
  private ListBox fontSizes;

  /**
   * Creates a new toolbar that drives the given rich text area.
   * 
   * @param richText the rich text area to be controlled
   */
  public RichTextToolbar(RichTextArea richText) {
    this.richText = richText;
    this.basic = richText.getBasicFormatter();
    this.extended = richText.getExtendedFormatter();

    outer.add(topPanel);
    outer.add(bottomPanel);
    topPanel.setWidth("100%");
    bottomPanel.setWidth("100%");

    initWidget(outer);
    setStyleName("gwt-RichTextToolbar");
    richText.addStyleName("hasRichTextToolbar");

    if (basic != null) {
      topPanel.add(bold = createToggleButton(images.bold(), "Negrita"));
      topPanel.add(italic = createToggleButton(images.italic(), "Italica"));
      topPanel.add(underline = createToggleButton(images.underline(), "Subrayado"));
      topPanel.add(subscript = createToggleButton(images.subscript(), "Sub"));
      topPanel.add(superscript = createToggleButton(images.superscript(), "Super"));
      topPanel.add(justifyLeft = createPushButton(images.justifyLeft(), "Izquierda"));
      topPanel.add(justifyCenter = createPushButton(images.justifyCenter(),"Centro"));
      topPanel.add(justifyRight = createPushButton(images.justifyRight(),"Derecha"));
    }

    if (extended != null) {
      topPanel.add(strikethrough = createToggleButton(images.strikeThrough(), "Marcar"));
      topPanel.add(indent = createPushButton(images.indent(), "Identar"));
      topPanel.add(outdent = createPushButton(images.outdent(), "Desidentar"));
      topPanel.add(hr = createPushButton(images.hr(), "HR"));
      topPanel.add(ol = createPushButton(images.ol(), "OL"));
      topPanel.add(ul = createPushButton(images.ul(), "UL"));
      topPanel.add(insertImage = createPushButton(images.insertImage(),"Insertar imagen"));
      topPanel.add(createLink = createPushButton(images.createLink(), "Crear enlace"));
      topPanel.add(removeLink = createPushButton(images.removeLink(), "Eliminar enlace"));
      topPanel.add(removeFormat = createPushButton(images.removeFormat(),"Eliminar formato"));
    }

    if (basic != null) {
      bottomPanel.add(backColors = createColorList("Background"));
      bottomPanel.add(foreColors = createColorList("Foreground"));
      bottomPanel.add(fonts = createFontList());
      bottomPanel.add(fontSizes = createFontSizes());

      // We only use these listeners for updating status, so don't hook them up
      // unless at least basic editing is supported.
      richText.addKeyUpHandler(keyUpHandler);
      richText.addClickHandler(clickHandler);
    }
  }

  private ListBox createColorList(String caption) {
    ListBox lb = new ListBox();
    lb.addChangeHandler(changeHandler);
    lb.setVisibleItemCount(1);

    lb.addItem(caption);
    lb.addItem("Blanco", "white");
    lb.addItem("Negro", "black");
    lb.addItem("Rojo", "red");
    lb.addItem("Verde", "green");
    lb.addItem("Amarillo", "yellow");
    lb.addItem("Azul", "blue");
    return lb;
  }

  private ListBox createFontList() {
    ListBox lb = new ListBox();
    lb.addChangeHandler(changeHandler);
    lb.setVisibleItemCount(1);

    lb.addItem("Fuente", "");
    lb.addItem("Fuente normal", "");
    lb.addItem("Times New Roman", "Times New Roman");
    lb.addItem("Arial", "Arial");
    lb.addItem("Courier New", "Courier New");
    lb.addItem("Georgia", "Georgia");
    lb.addItem("Trebuchet", "Trebuchet");
    lb.addItem("Verdana", "Verdana");
    return lb;
  }

  private ListBox createFontSizes() {
    ListBox lb = new ListBox();
    lb.addChangeHandler(changeHandler);
    lb.setVisibleItemCount(1);

    lb.addItem("Size");
    lb.addItem("XX-Small");
    lb.addItem("XS-Small");
    lb.addItem("Small");
    lb.addItem("Medium");
    lb.addItem("Large");
    lb.addItem("Xlarge");
    lb.addItem("XXLarge");
    return lb;
  }

  private PushButton createPushButton(AbstractImagePrototype img, String tip) {
    PushButton pb = new PushButton(img.createImage());
    pb.addClickHandler(clickHandler);
    pb.setTitle(tip);
    return pb;
  }

  private ToggleButton createToggleButton(AbstractImagePrototype img, String tip) {
    ToggleButton tb = new ToggleButton(img.createImage());
    tb.addClickHandler(clickHandler);
    tb.setTitle(tip);
    return tb;
  }

  /**
   * Updates the status of all the stateful buttons.
   */
  private void updateStatus() {
    if (basic != null) {
      bold.setDown(basic.isBold());
      italic.setDown(basic.isItalic());
      underline.setDown(basic.isUnderlined());
      subscript.setDown(basic.isSubscript());
      superscript.setDown(basic.isSuperscript());
    }

    if (extended != null) {
      strikethrough.setDown(extended.isStrikethrough());
    }
  }
}
