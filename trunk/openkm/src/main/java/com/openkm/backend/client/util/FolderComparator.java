package com.openkm.backend.client.util;

import java.util.Comparator;

import com.openkm.backend.client.bean.GWTFolder;

public class FolderComparator implements Comparator<GWTFolder> {
	private static final Comparator<GWTFolder> INSTANCE  = new FolderComparator();
	
	public static Comparator<GWTFolder> getInstance() {
		return INSTANCE;
	}

	public int compare(GWTFolder arg0, GWTFolder arg1) {
		return arg0.getName().compareTo(arg1.getName());
	}
}