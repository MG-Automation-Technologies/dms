package com.openkm.servlet.mobile;

import java.util.Comparator;

import com.openkm.bean.Folder;
import com.openkm.util.FileUtils;

public class FolderComparator implements Comparator<Folder> {
	private static final Comparator<Folder> INSTANCE  = new FolderComparator();
	
	public static Comparator<Folder> getInstance() {
		return INSTANCE;
	}
	
	@Override
	public int compare(Folder arg0, Folder arg1) {
		String first = FileUtils.getName(arg0.getPath());
		String second = FileUtils.getName(arg1.getPath());
		return first.compareTo(second);
	}
}
