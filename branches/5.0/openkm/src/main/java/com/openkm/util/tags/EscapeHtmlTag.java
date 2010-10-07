package com.openkm.util.tags;

import java.io.IOException;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.openkm.util.FormatUtil;

@SuppressWarnings("serial")
public class EscapeHtmlTag extends TagSupport {
	private String string;
	
	@Override
	public int doStartTag() {
		String ret = FormatUtil.escapeHtml(string);
		
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
		string = null;
	}
	
	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}
}