package com.openkm.servlet.frontend.util;

import com.openkm.frontend.client.bean.GWTDocument;

public class DocumentComparator extends CultureComparator<GWTDocument> {
	
	protected DocumentComparator(String locale) {
		super(locale);
	}
	
	public static DocumentComparator getInstance(String locale) {
		try {
			DocumentComparator comparator = (DocumentComparator) CultureComparator.getInstance(DocumentComparator.class, locale);
			return comparator;
		}
		catch (Exception e) {
			return new DocumentComparator(locale);
		}
	}
	
	public static DocumentComparator getInstance() {
		DocumentComparator instance = getInstance(CultureComparator.DEFAULT_LOCALE);
		return instance;
	}

	public int compare(GWTDocument arg0, GWTDocument arg1) {
		GWTDocument first = arg0;
		GWTDocument second = arg1;
		
		int result = collator.compare(first.getName(), second.getName());
		
		return result;
	}
}