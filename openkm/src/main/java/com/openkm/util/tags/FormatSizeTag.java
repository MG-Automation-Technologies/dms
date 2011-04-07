package com.openkm.util.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.openkm.util.FormatUtil;

@SuppressWarnings("serial")
public class FormatSizeTag extends TagSupport {
	private long size;
	
	@Override
	public int doStartTag() {
		String ret = FormatUtil.formatSize(size);
		
		try {
			pageContext.getOut().write(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Tag.SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
		size = 0;
	}
	
	public long getSize() {
		return size;
	}
	
	public void setSize(long size) {
		this.size = size;
	}
}