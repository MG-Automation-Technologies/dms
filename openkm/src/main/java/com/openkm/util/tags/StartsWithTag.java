package com.openkm.util.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class StartsWithTag extends TagSupport {
	private String string;
	private String prefix;
	
	@Override
	public int doStartTag() {
		boolean ret = string.startsWith(prefix);

		try {
			pageContext.getOut().write(Boolean.toString(ret));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Tag.SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();
		string = null;
		prefix = null;
	}
	
	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}