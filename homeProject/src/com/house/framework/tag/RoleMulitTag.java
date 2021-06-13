package com.house.framework.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.house.framework.commons.cache.RoleCacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.Role;

/**
 * 角色下拉多选树
 *
 */
public class RoleMulitTag extends CommonMulitTag {
	private static final long serialVersionUID = 1L;
	/** 查询条件 */
	public String sysCode = "";
	
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
		RoleCacheManager roleCacheManager = (RoleCacheManager)SpringContextHolder.getBean("roleCacheManager");
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", this.virtualRootId);
		item.put("pId", "");
		item.put("name", this.virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		List<Role> list = (List<Role>) roleCacheManager.get("role");
		if(list != null && list.size() > 0){
			for(Role role : list){
				if(role != null && (StringUtils.isBlank(sysCode) 
						|| CommonConstant.PLATFORM_ALL.equals(role.getSysCode())
						|| sysCode.equals(role.getSysCode()))){
					item = new HashMap<String, Object>();
					item.put("id", role.getRoleId());
					item.put("pId", this.virtualRootId);
					item.put("name", role.getRoleId()+" "+role.getRoleName());
					rsList.add(item);
				}
			}
		}
		return JSON.toJSONString(rsList);
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
}
