package com.house.framework.tag;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.house.framework.commons.cache.CustTypeCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.CustType;

public class CustTypeTag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	
	/** 过期标志 */
	private String expired="";
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		
		CustTypeCacheManager custTypeCacheManager = (CustTypeCacheManager)SpringContextHolder.getBean("custTypeCacheManager");
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		if (readonly != null && readonly.equalsIgnoreCase("true")){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			CustType custType = (CustType) custTypeCacheManager.get(value);
			if (custType == null)
				return SKIP_BODY;
			String str = custType.getDesc1();
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			List<CustType> list = (List<CustType>) custTypeCacheManager.get("custType");
			
			if (list==null) {
				list = Lists.newArrayList();
			}
			for (CustType o:list){
				if (!isUnShowValue(o.getCode()) && (StringUtils.isBlank(expired) || expired.equals(o.getExpired()))){
					if (value != null && value.equals(o.getCode().trim())) {
						html.append("<option selected=\"true\"");
					} else {
						html.append("<option");
					}
					html.append(" value=\"").append(o.getCode().trim()).append("\">");
					html.append(o.getCode().trim()).append(" ");
					html.append(o.getDesc1().trim());
					html.append("</option>");
				}
			}
			html.append("</select>");
		}
		
		try {
			pageContext.getOut().println(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}
	
}
