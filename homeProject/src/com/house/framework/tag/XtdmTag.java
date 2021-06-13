package com.house.framework.tag;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import com.house.framework.commons.cache.BuilderCacheManager;
import com.house.framework.commons.cache.XtdmCacheManager;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.BuilderNum;
import com.house.home.entity.basic.Xtdm;

public class XtdmTag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	
	/** 系统代码编码 */
	private String dictCode;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		XtdmCacheManager xtdmCacheManager = (XtdmCacheManager)SpringContextHolder.getBean("xtdmCacheManager");
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id) || StringUtils.isBlank(dictCode))
			return SKIP_BODY;
		if (readonly != null && readonly.equalsIgnoreCase("true")){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			Xtdm xtdm = (Xtdm) xtdmCacheManager.get(dictCode+"_"+value.trim());
			if (xtdm == null)
				return SKIP_BODY;
			String str = xtdm.getNote();
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			List<Xtdm> list = (List<Xtdm>) xtdmCacheManager.get(dictCode+"list");
			if (list==null) {
				list = Lists.newArrayList();
			}else{
				Collections.sort(list, new Comparator<Xtdm>() {
		            public int compare(Xtdm arg0, Xtdm arg1) {
		                return arg0.getDispSeq().compareTo(arg1.getDispSeq());
		            }
		        });
			}
			for (Xtdm o:list){
				if (!isUnShowValue(o.getCbm())){
					if (value != null && value.trim().equals(o.getCbm())) {
						html.append("<option selected=\"true\"");
					} else {
						html.append("<option");
					}
					html.append(" value=\"").append(o.getCbm()).append("\">");
					html.append(o.getCbm()).append(" ");
					html.append(o.getNote());
					html.append("</option>");
				}
			}
			html.append("</select>");
			html.append("<script type='text/javascript'> \n");
			html.append("$(function(){\n");
			// 登录界面没有导入common.jsp界面 所以操作员类别取消下拉搜索
			html.append("if('"+id+"'!='czylb'){\n");
			html.append("$('#"+id+"').searchableSelect();\n");
			html.append("}\n");
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
