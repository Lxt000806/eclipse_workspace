package com.house.framework.tag;

import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.house.framework.commons.cache.PositionCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Position;

public class PositionTag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		PositionCacheManager positionCacheManager = 
				(PositionCacheManager)SpringContextHolder.getBean("positionCacheManager");
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		if (readonly != null && readonly.equalsIgnoreCase("true")){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			Position position = (Position) positionCacheManager.get(value);
			if (position == null)
				return SKIP_BODY;
			String str = position.getDesc2();
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			List<Position> list = (List<Position>) positionCacheManager.get("position");
			
			if (list==null) {
				list = Lists.newArrayList();
			}
			for (Position o:list){
				if (!isUnShowValue(o.getCode())){
					if (value != null && value.equals(o.getCode().trim())) {
						html.append("<option selected=\"true\"");
					} else {
						html.append("<option");
					}
					html.append(" value=\"").append(o.getCode().trim()).append("\">");
					html.append(o.getCode().trim()).append(" ");
					html.append(o.getDesc2().trim());
					html.append("</option>");
				}
			}
			html.append("</select>");
			html.append("<script type='text/javascript'> \n");
			html.append("$(function(){\n");
			html.append("$('#position').searchableSelect();\n");
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

}
