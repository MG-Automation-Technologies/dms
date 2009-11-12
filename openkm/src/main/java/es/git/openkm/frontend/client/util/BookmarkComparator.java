package es.git.openkm.frontend.client.util;

import java.util.Comparator;

import es.git.openkm.frontend.client.bean.GWTBookmark;

public class BookmarkComparator implements Comparator {
	private static final Comparator INSTANCE  = new BookmarkComparator();
	
	public static Comparator getInstance() {
		return INSTANCE;
	}

	public int compare(Object arg0, Object arg1) {
		GWTBookmark first = ((GWTBookmark) arg0);
		GWTBookmark second = ((GWTBookmark) arg1);
		
		// Compare first with type, and second for name
		if (!first.getType().equals(second.getType())) {
			return second.getType().compareTo(first.getType()); // inverse comparation
		} else {
			return first.getName().toUpperCase().compareTo(second.getName().toUpperCase());
		}
	}
}