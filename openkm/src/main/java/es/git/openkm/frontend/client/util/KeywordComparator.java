package es.git.openkm.frontend.client.util;

import java.util.Comparator;

import es.git.openkm.frontend.client.bean.GWTKeyword;

public class KeywordComparator implements Comparator {
	private static final Comparator INSTANCE  = new KeywordComparator();
	
	public static Comparator getInstance() {
		return INSTANCE;
	}
	
	public int compare(Object arg0, Object arg1) {
		GWTKeyword first = ((GWTKeyword) arg0);
		GWTKeyword second = ((GWTKeyword) arg1);
		return first.getKeyword().compareTo(second.getKeyword());
	}
}