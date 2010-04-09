/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
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

package es.git.openkm.frontend.client.widget.foldertree;

import java.util.Vector;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import es.git.openkm.frontend.client.Main;

/**
 * ExtendedTree captures right button and marks a popup flag
 * 
 * @author jllort
 * 
 */
public class ExtendedTree extends Tree {

	private boolean flagPopup = false;
	public int mouseX = 0;
	public int mouseY = 0;
	private boolean dragged = false;
	
	/**
	 * ExtendedTree
	 */
	public ExtendedTree() {
		super();
		
		sinkEvents(Event.MOUSEEVENTS);
		
		// Adds mouse listener to determine drag & drop start
		addMouseListener(new MouseListenerAdapter() {
            public void onMouseDown(Widget sender, int x, int y) {
            	dragged = true;
                super.onMouseDown(sender, x, y);
            }
		});
	}
	
	/**
	 * isShowPopUP
	 * 
	 * @return true or false popup flag
	 */
	public boolean isShowPopUP() {
		return flagPopup;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.EventListener#onBrowserEvent(com.google.gwt.user.client.Event)
	 */
	public void onBrowserEvent(Event event) {
		
		// When de button mouse is released
		if (DOM.eventGetType(event) == Event.ONMOUSEDOWN) {
			// When de button mouse is released
			mouseX = DOM.eventGetClientX(event);
			mouseY = DOM.eventGetClientY(event);
			
			switch (DOM.eventGetButton(event)){
				case Event.BUTTON_RIGHT:
					DOM.eventPreventDefault(event); // Prevent to fire event to browser
					flagPopup = true;
					break;
				default:
					flagPopup = false;
			}
		}
		
		// Prevent folder creation or renaming propagate actions to other tree nodes
		int action = Main.get().activeFolderTree.getFolderAction();
		if (action != FolderTree.ACTION_CREATE && action != FolderTree.ACTION_RENAME ) {
			super.onBrowserEvent(event);
		}
	} 
	
	/**
	 * isDragged Returns true or false if is dragged
	 * 
	 * @return Return dragged value
	 */
	public boolean isDragged() {
		return dragged;
	}
	
	/**
	 * unsetDraged
	 * 
	 * Sets dragged flag to false;
	 */
	public void unsetDraged() {
		this.dragged = false;
	}
	
	/**
	 * elementClicked
	 * 
	 * Returns the treeItem when and element is clicked, used to capture drag and drop tree Item
	 * 
	 * @param element
	 * @return
	 */
	public TreeItem elementClicked(Element element) {
        Vector chain = new Vector();
        collectElementChain(chain, this.getElement(), element);
        TreeItem item = findItemByChain(chain, 0, null);
        return item;
	}
	
	/**
	 * collectElementChain
	 * 
	 * @param chain 
	 * @param elementRoot 
	 * @param element 
	 */
	private void collectElementChain(Vector chain, Element elementRoot, Element element) {
        if ((element == null) || element== elementRoot)
                return;

        collectElementChain(chain, elementRoot, DOM.getParent(element));
        chain.add(element);
	}
	
	/**
	 * findItemByChain
	 * 
	 * @param chain
	 * @param idx
	 * @param root
	 * @return
	 */
	private TreeItem findItemByChain(Vector chain, int idx, TreeItem root) {
        if (idx == chain.size())
                return root;

        Element hCurElem = (Element) chain.get(idx);

        if (root == null) {
            for (int i = 0, n = this.getItemCount(); i < n; ++i) {
                TreeItem child = this.getItem(i);
                if (child.getElement()==hCurElem) {
                    TreeItem retItem = findItemByChain(chain, idx + 1, child);
                    if (retItem == null)
                            return child;
                    return retItem;
                }
            }
        } else {
            for (int i = 0, n = root.getChildCount(); i < n; ++i) {
                TreeItem child = root.getChild(i);
                if (child.getElement()==hCurElem) {
                    TreeItem retItem = findItemByChain(chain, idx + 1, root.getChild(i));
                    if (retItem == null)
                    	return child;
                    return retItem;
                }
            }
        }

        return findItemByChain(chain, idx + 1, root);
}
}