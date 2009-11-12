package es.git.openkm.backend.client.util;

import java.util.Comparator;

import es.git.openkm.backend.client.bean.GWTFolder;

public class FolderComparator implements Comparator {
	private static final Comparator INSTANCE  = new FolderComparator();
	
	public static Comparator getInstance() {
		return INSTANCE;
	}

	public int compare(Object arg0, Object arg1) {
		GWTFolder first = ((GWTFolder) arg0);
		GWTFolder second = ((GWTFolder) arg1);
		return first.getName().compareTo(second.getName());
	}
}