package com.house.framework.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.DocFolder;
import com.house.home.service.basic.DocFolderService;

/**
 * 客户类型下拉多选树
 *
 */
public class DocFolderMulitTag extends CommonMulitTag {

	private static final long serialVersionUID = 1L;
	/** 查询条件 */
	public String expired="";
	
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
			.append(this.bulidOnClick())
			.append("</script>\n\n");
		try {
			pageContext.getOut().print(strb.toString());
		} catch (java.io.IOException e) {
			e.printStackTrace();
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	protected String bulidNodes(){
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		DocFolderService docFolderService=(DocFolderService) SpringContextHolder.getBean("docFolderServiceImpl");
		Map<String,Object> item = new HashMap<String, Object>();
		List<DocFolder> list =  docFolderService.getAll();
		if(list != null && list.size() > 0){
			for(DocFolder docFolder : list){
				if(docFolder != null &&(StringUtils.isBlank(expired)
				  || expired.equals(docFolder.getExpired()))){
					item = new HashMap<String, Object>();
					item.put("id", docFolder.getPk());
					item.put("pId", docFolder.getParentPk());
					item.put("name",docFolder.getFolderName());
					rsList.add(item);
				}
			}
		}
		return JSON.toJSONString(rsList);
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}	
}
