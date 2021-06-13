package com.house.framework.tag;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.house.framework.commons.cache.DepartmentCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Department1;

public class Department1Tag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	private String depType;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		DepartmentCacheManager departmentCacheManager = (DepartmentCacheManager)SpringContextHolder.getBean("departmentCacheManager");
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		if (readonly != null && readonly.equalsIgnoreCase("true")){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			Department1 department1 = (Department1) departmentCacheManager.get("department1_"+value);
			if (department1 == null)
				return SKIP_BODY;
			String str = department1.getDesc2();
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			
			List<Department1> list = null;
			
			if (StringUtils.isNotBlank(depType)) {
				list = (List<Department1>) departmentCacheManager.get("depTypeDept1_"+depType+"_list");
			} else {
				list = (List<Department1>) departmentCacheManager.get("department1");
			}
			
			if (list==null) {
				list = Lists.newArrayList();
			}
			for (Department1 o:list){
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
			html.append("$('#department1').searchableSelect();\n");
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

	public String getDepType() {
		return depType;
	}

	public void setDepType(String depType) {
		this.depType = depType;
	}


}
