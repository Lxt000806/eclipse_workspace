package com.house.framework.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.fastjson.JSON;
import com.house.framework.commons.cache.XtdmCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtdm;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.workflow.DepartmentService;

/**
 * 下拉选项树控件
 *
 */
public class MulitSelectDepatment extends CommonMulitTag {
	private static final long serialVersionUID = 1L;
	
	public int doStartTag() throws JspException {
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		
		StringBuilder strb = new StringBuilder();
		strb.append(this.bulidHtml())
			.append("\n<script type='text/javascript'>\n")
			.append(this.bulidTreeSetting())
			.append("var zNodes_"+id+" = ").append(this.bulidNodes()).append(";\n\n\n")
			.append(this.bulidBeforeCheck())
			.append(this.bulidOnCheck())
			.append(this.bulidMenuDisplay())
			.append(this.bulidOnReady())
			.append(appendAllDom == true ? "setTimeout(function(){appendAllDom(\""+id+"\");}, 200);" : "")
			.append("</script>\n\n");
		try {
			pageContext.getOut().print(strb.toString());
		} catch (java.io.IOException e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	@SuppressWarnings("unchecked")
	protected String bulidNodes(){
		DepartmentService departmentService=(DepartmentService) SpringContextHolder.getBean("departmentServiceImpl");
		//EmployeeService employeeService=(EmployeeService) SpringContextHolder.getBean("employeeServiceImpl");
		List<Map<String, Object>> departmentList = departmentService.findDepartmentNoExpired();
		//List<Map<String, Object>> employeeList = employeeService.findEmployeeExpired();

		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", this.virtualRootId);
		item.put("pId", "");
		item.put("name", this.virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		//rsList.addAll(employeeList);
		rsList.addAll(departmentList);
		return JSON.toJSONString(rsList);
	}
	
	protected String bulidOnCheck(){
		String strb = "";
		strb += "function onCheck_"+id+"(e, treeId, treeNode) {\n" +
				"	var zTree = zTree_"+id+";\n" +
				"	var ids = [];\n" +
				"	var names = [];\n" +
				"	var nodes = zTree.getCheckedNodes(true);\n" +
				"	for (var i=0, l=nodes.length; i<l; i++) {\n" +
				"		if(nodes[i].name=='请选择'){\n" +
				"			continue;\n" +
				"		}\n" +
				"		ids.push(nodes[i]['id']);\n" +
				"		names.push(nodes[i]['name']);\n" +
				"	}\n" +
				"	$(\"#"+id+"\").val(ids.join(','));\n" +
				"	$(\"#"+id+"_NAME\").val(names.join(','));\n";
		
		if(StringUtils.isNotBlank(onCheck)){
		strb += "	if(typeof("+onCheck+") == 'function') {\n" +
				"		" +onCheck+"(e, treeId, treeNode);\n" +
				"	}\n";
		}
		strb += "}\n\n";
		return strb;
	}
	
	protected String bulidTreeSetting(){
		String strb = "";
		strb += "var zTree_"+id+" = null;\n" +
				"var setting_"+id+" = {\n" +
				"	check: {\n" +
				"		enable: true,\n" +
				"		chkboxType: {\"Y\":\"s\", \"N\":\"ps\"}\n" +
				"	},\n" +
				"	view: {\n" +
				"		dblClickExpand: false\n" +
				"	},\n" +
				"	data: {\n" +
				"		simpleData: {\n" +
				"			enable: true,\n" +
				"		}\n" +
				"	},\n" +
				"	callback: {\n" +
				"		beforeCheck: beforeCheck_"+id+",\n" +
				"		onCheck: onCheck_"+id+"\n" +
				"	}\n" +
				"};\n\n";
		return strb;
	}
}
