package com.house.framework.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.house.framework.commons.cache.DepartmentCacheManager;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.dao.basic.EmployeeDao;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.service.basic.EmployeeService;

public class Department2Tag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	
	/** 一级部门编码 */
	private String dictCode;
	private String depType;

	@Autowired
	private EmployeeService employeeService;
	
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
			Department2 department2 = (Department2) departmentCacheManager.get("department2_"+value);
			if (department2 == null)
				return SKIP_BODY;
			String str = department2.getDesc2();
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			List<Department2> list = null;
			if (StringUtils.isNotBlank(depType)) {
				list = (List<Department2>) departmentCacheManager.get("depType_"+depType+"_list");
			} else {
				list = (List<Department2>) departmentCacheManager.get("department1_"+dictCode+"_list");
			}
			if (list==null) {
				list = Lists.newArrayList();
			}
			for (Department2 o:list){
				if (!isUnShowValue(o.getCode())){
					if (value != null && value.equals(o.getCode())) {
						html.append("<option selected=\"true\"");
					} else {
						html.append("<option");
					}
					html.append(" value=\"").append(o.getCode()).append("\">");
					html.append(o.getCode()).append(" ");
					html.append(o.getDesc2()).append("  ");
					html.append(o.getDepLeader());
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

	public String getDepType() {
		return depType;
	}

	public void setDepType(String depType) {
		this.depType = depType;
	}

}
