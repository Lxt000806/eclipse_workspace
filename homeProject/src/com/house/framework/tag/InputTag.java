package com.house.framework.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;

public class InputTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	/** 标签id和name */
	private String id;
	/** 默认值 */
	private String value;
	/** 样式 */
	private String style;
	/** 样式class */
	private String classStyle;
	/** 只读*/
	private String readonly;
	/** 异步请求url*/
	private String url;
	/** 弹窗标题*/
	private String title;
	/** 返回的key字段名*/
	private String code;
	/** 返回的描述字段*/
	private String descr;
	/** 返回的描述值，如果有设置jquery验证方法，则描述会自动获取*/
	private String descrValue;
	
	public int doStartTag() throws JspException {
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		html.append("<input type=\"text\"")
			.append(" id=\"").append(id).append("\"")
			.append(" name=\"").append(id).append("\"")
			.append(" value=\"").append(value==null?"":value).append("\"")
			.append(" style=\"").append("width:60px;margin-right:3px;\"");
		if(StringUtils.isNotBlank(readonly) && readonly.equals("true")){
			html.append(" readonly=\"readonly\"");
		}
		html.append("><input type=\"text\" style=\"width:90px;\" id=\"")
			.append(id).append("_input\" name=\"").append(id).append("_input\"")
			.append(" value=\"").append(descrValue==null?"":descrValue).append("\"/>");
		if(StringUtils.isNotBlank(readonly) && readonly.equals("true")){
			
		}else{
			html.append("<a href=\"javascript:void(0)\" class=\"a1\" onclick=\"Global.Dialog.fetch('").append(id).append("','")
			.append(url).append("','").append(title).append("','").append(code).append("','").append(descr)
			.append("')\">【选取】</a>");
		}
		//html.append("<script type=\"text/javascript\">$(function(){$(\"#").append(id).append("\").valid && $(\"#").append(id).append("\").valid();});</script>");
		try {
			pageContext.getOut().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClassStyle() {
		return classStyle;
	}

	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescrValue() {
		return descrValue;
	}

	public void setDescrValue(String descrValue) {
		this.descrValue = descrValue;
	}

}
