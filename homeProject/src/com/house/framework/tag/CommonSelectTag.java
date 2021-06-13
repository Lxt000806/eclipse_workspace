package com.house.framework.tag;

import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;

/**
 *选择下拉框
 */
public class CommonSelectTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	/** 标签id和name */
	protected String id;
	
	/** 默认值 */
	protected String value;
	
	/** 不显示的值(多个逗号分隔) */
	protected String unShowValue;
	
	/** 显示的值(多个逗号分隔) */
	protected String showValue;
	
	/** 样式 */
	protected String style;
	
	/** 样式class */
	protected String classStyle;
	
	/** 选择空值提示信息 */
	protected String headerLabel = "请选择...";
	
	/** 宽度 */
	protected String width = "160px";
	
	/** 只读模式，只会生成文本和<input type="hidden">的html*/
	protected String readonly;
	
	/** 显示值的长度,只读时才有用*/
	protected String cutLength;
	
	/** 不可用模式，值为true 会在select 中加入disabled = disabled 属性*/
	protected String disabled;
	
	/** onchange事件 */
	protected String onchange;
	
	/** onclick事件 */
	protected String onclick;

	/**
	 * 判断是否为不显示的选项
	 * @param aaa102
	 * @return
	 */
	protected boolean isUnShowValue(String str) {
		boolean result = false;
		if (StringUtils.isNotBlank(showValue)) {
			String[] svalues = showValue.split(",");
			if (svalues.length > 0) {
				for (String svalue : svalues) {
					if (svalue.equals(str)) {
						result = false;
						return result;
					}else{
						result = true;
					}
				}
				return result;
			}
		}else{
			if (StringUtils.isNotBlank(unShowValue)) {
				String[] svalues = unShowValue.split(",");
				if (svalues.length > 0) {
					for (String svalue : svalues) {
						if (svalue.equals(str)) {
							result = true;
							return result;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取下拉框只读html
	 * @param showStr
	 * @return
	 */
	protected String getReadonlyStr(String showStr){
		StringBuffer html = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(cutLength) && StringUtils.isNotBlank(showStr)){
				int length = Integer.parseInt(cutLength);
				if (showStr.length() > length){
					showStr = showStr.substring(0,length) + "...";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		html.append(showStr).append("<input type=\"hidden\" id=\"").append(id)
		.append("\" name=\"").append(id).append("\" value=\"").append(value==null?"":value.trim()).append("\" />");
		return html.toString();
	}
	
	/**
	 * 获取下拉框可用或不用html
	 * @return
	 */
	protected String getSelectStr(){
		StringBuffer html = new StringBuffer();
		if (disabled != null && disabled.equalsIgnoreCase("true")) {
			html.append("<input type=\"hidden\"")
				.append(" id=\"").append(id).append("\"")
				.append(" name=\"").append(id).append("\"")
				.append(" value=\"").append(value==null?"":value.trim()).append("\"")
				.append(">")
				.append("<select id=\"").append(id).append("_disabled").append("\"")
				.append(" name=\"").append(id).append("_disabled").append("\"");
				if(StringUtils.isNotBlank(style)){
					html.append(" style=\"").append("width:").append(width).append(";")
					.append(style).append("\"");
				}else{
					html.append(" style=\"").append("width:").append(width).append(";\"");
				}
				if(StringUtils.isNotBlank(classStyle)){
					html.append(" class=\"").append(classStyle).append("\"");
				}else{
					html.append(" class=\"disabled\"");
				}
				if(StringUtils.isNotBlank(onchange)){
					html.append(" onchange=\"").append(onchange).append("\"");
				}
				html.append(" disabled=\"true\">");
		} else {
			html.append("<select id=\"").append(id).append("\"")
				.append(" name=\"").append(id).append("\"");
				if(StringUtils.isNotBlank(style)){
					html.append(" style=\"").append("width:").append(width).append(";")
					.append(style).append("\"");
				}else{
					html.append(" style=\"").append("width:").append(width).append(";\"");
				}
				if(StringUtils.isNotBlank(classStyle)){
					html.append(" class=\"").append(classStyle).append("\"");
				}
				if(StringUtils.isNotBlank(onchange)){
					html.append(" onchange=\"").append(onchange).append("\"");
				}
				if (StringUtils.isNotBlank(onclick)) {
					html.append(" onclick=\"").append(onclick).append("\"");
				}
				html.append(">");
		}
		if(StringUtils.isNotBlank(headerLabel)){
			html.append("\t<option value=\"\">").append(headerLabel).append("</option>\n");
		}
		return html.toString();
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

	public String getClassStyle() {
		return classStyle;
	}

	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	public String getHeaderLabel() {
		return headerLabel;
	}

	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getCutLength() {
		return cutLength;
	}

	public void setCutLength(String cutLength) {
		this.cutLength = cutLength;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getUnShowValue() {
		return unShowValue;
	}

	public void setUnShowValue(String unShowValue) {
		this.unShowValue = unShowValue;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}

}
