package com.house.framework.tag;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;

public class DataListTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String value;
	private int length;
	
	public int doStartTag() {
		@SuppressWarnings("unused")
		String newvalue = "";
		if(StringUtils.isNotBlank(value)){
			if(value.length() > length){
				newvalue = value.substring(0, length);
				newvalue += "...";
			}else{
				newvalue = value;
			}
		}
		List<String> lista = new ArrayList<String>();
    	lista.add("aaa");
    	lista.add("bbb");
    	lista.add("ccc");
    	//Object sa = JSONArray.toJSON(lista);
		newvalue = "<c:set var=\"itemName\" value=\"kkk\"></c:set>";
		newvalue = lista.toString();
		pageContext.setAttribute("itemName", "kkk");
		JspWriter out = pageContext.getOut();
		try{
			out.print(lista);
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
