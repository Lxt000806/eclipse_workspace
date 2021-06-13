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
import com.house.home.service.design.CustTagService;
import com.house.home.service.design.ResrCustService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.workflow.DepartmentService;

/**
 * 下拉选项树控件
 *
 */
public class MulitSelectCustTag extends CommonMulitTag {
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
		CustTagService custTagService=(CustTagService) SpringContextHolder.getBean("custTagServiceImpl");
		List<Map<String, Object>> custTagsList = custTagService.findCustTagsForTree();
		System.out.println(custTagsList);
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", this.virtualRootId);
		item.put("pId", "");
		item.put("name", this.virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		rsList.addAll(custTagsList);
		return JSON.toJSONString(rsList);
	}
	
}
