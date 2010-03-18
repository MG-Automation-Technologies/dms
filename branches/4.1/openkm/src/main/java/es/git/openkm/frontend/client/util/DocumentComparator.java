package es.git.openkm.frontend.client.util;

import java.util.Comparator;

import es.git.openkm.frontend.client.bean.GWTDocument;

public class DocumentComparator implements Comparator {
	private static final Comparator INSTANCE  = new DocumentComparator();
	
	public static Comparator getInstance() {
		return INSTANCE;
	}
	
	public int compare(Object arg0, Object arg1) {
		GWTDocument first = ((GWTDocument) arg0);
		GWTDocument second = ((GWTDocument) arg1);
		return first.getName().compareTo(second.getName());
	}
}
