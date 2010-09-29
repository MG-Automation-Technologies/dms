package com.openkm.util.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.openkm.util.FileUtils;

@SuppressWarnings("serial")
public class GetParentTag extends TagSupport {
	private String path;
	
	@Override
	public int doStartTag() {
		String ret = FileUtils.getParent(path);
		
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
		path = null;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}