package com.openkm.frontend.client.util;

import java.util.Comparator;

import com.openkm.frontend.client.bean.GWTBookmark;

/**
 * BookmarkComparator
 * 
 * @author jllort
 *
 */
public class BookmarkComparator implements Comparator<GWTBookmark> {
	private static final Comparator<GWTBookmark> INSTANCE  = new BookmarkComparator();
	
	public static Comparator<GWTBookmark> getInstance() {
		return INSTANCE;
	}

	public int compare(GWTBookmark arg0, GWTBookmark arg1) {		
		// Compare first with type, and second for name
		if (!arg0.getType().equals(arg0.getType())) {
			return arg1.getType().compareTo(arg0.getType()); // inverse comparation
		} else {
			return arg0.getName().toUpperCase().compareTo(arg1.getName().toUpperCase());
		}
	}
}