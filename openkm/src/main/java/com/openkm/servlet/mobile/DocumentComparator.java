package com.openkm.servlet.mobile;

import java.util.Comparator;

import com.openkm.bean.Document;
import com.openkm.util.FileUtils;

public class DocumentComparator implements Comparator<Document> {
	private static final Comparator<Document> INSTANCE  = new DocumentComparator();
	
	public static Comparator<Document> getInstance() {
		return INSTANCE;
	}
	
	@Override
	public int compare(Document arg0, Document arg1) {
		String first = FileUtils.getName(arg0.getPath());
		String second = FileUtils.getName(arg1.getPath());
		return first.compareTo(second);
	}
}
