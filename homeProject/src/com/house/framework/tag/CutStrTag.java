package com.house.framework.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;

public class CutStrTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String value;
	private int length;
	
	
	public int doStartTag() {
		String newvalue = "";
		
		if(StringUtils.isNotBlank(value)){
			if(value.length() > length){
				newvalue = value.substring(0, length);
				newvalue += "...";
			}else{
				newvalue = value;
			}
		}
		JspWriter out = pageContext.getOut();
		try{
			out.print(newvalue);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


}
