package com.house.framework.tag;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.house.framework.commons.cache.DepartmentCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Department3;

public class Department3Tag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	/** 二级部门编码 */
	private String dictCode;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		DepartmentCacheManager departmentCacheManager = 
				(DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		if (readonly != null && readonly.equalsIgnoreCase("true")){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			Department3 department3 = (Department3) departmentCacheManager.get("department3_"+value);
			if (department3 == null)
				return SKIP_BODY;
			String str = department3.getDesc2();
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			List<Department3> list = (List<Department3>) departmentCacheManager.get("department2_"+dictCode+"_list");
			
			if (list==null) {
				list = Lists.newArrayList();
			}
			for (Department3 o:list){
				if (!isUnShowValue(o.getCode())){
					if (value != null && value.equals(o.getCode())) {
						html.append("<option selected=\"true\"");
					} else {
						html.append("<option");
					}
					html.append(" value=\"").append(o.getCode()).append("\">");
					html.append(o.getCode()).append(" ");
					html.append(o.getDesc2());
					html.append("</option>");
				}
			}
			html.append("</select>");
			html.append("<script type='text/javascript'> \n");
			html.append("$(function(){\n");
			html.append("$('#department2').searchableSelect();\n");
			html.append("})\n");
			html.append("</script>");
		}
		
		try {
			pageContext.getOut().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

}
