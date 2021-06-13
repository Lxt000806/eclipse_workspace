package com.house.framework.tag;

import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.entity.DictItem;

/**
 * 字典控件,用户需提供字典编码，或查询sql，获取数据集
 */
public class DictTag extends CommonSelectTag {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DictTag.class);
	
	private static final String SQL_VALUE_KEY = "SQL_VALUE_KEY";
	private static final String SQL_LABEL_KEY = "SQL_LABEL_KEY";
	
	private static final String LOGIC_TRUE = "true";
	
	/** 字典编码 */
	private String dictCode;
	
	/** sql查询语句 */
	private String sql;
	
	/** sql对应返回值得key 为空sqlValue */
	private String sqlValueKey = DictTag.SQL_VALUE_KEY;
	
	/** sql对应返回值 为空sqlLable*/
	private String sqlLableKey = DictTag.SQL_LABEL_KEY;
	
	public int doStartTag() throws JspException {
		StringBuffer html = new StringBuffer();
		if(StringUtils.isBlank(id))
			return SKIP_BODY;
		if(LOGIC_TRUE.equals(readonly)){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			String str = DictCacheUtil.getItemLabel(dictCode, value) == null ? "":DictCacheUtil.getItemLabel(dictCode, value);
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());
			if(StringUtils.isBlank(dictCode)){
				if(StringUtils.isBlank(sql))
					return SKIP_BODY;
				
				JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
				List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
				
				if(list != null && list.size() > 0){
					for(Map<String, Object> map : list){
						html.append(this.bulidOption(map.get(sqlValueKey), map.get(sqlLableKey), value, false));
					}
				}
			}else{
				List<DictItem> list = DictCacheUtil.getItemsByDictCode(dictCode.trim());
				if(list != null && list.size() > 0){
					for(DictItem dictItem : list){
						if(dictItem != null)
							if(dictItem.getItemCode() != null){
								html.append(this.bulidOption(dictItem.getItemCode(), dictItem.getItemLabel(), value, !CommonConstant.LOGIC_YES.equals(dictItem.getStatus())));
							}else{
								html.append(this.bulidOption(dictItem.getItemValue(), dictItem.getItemLabel(), value, !CommonConstant.LOGIC_YES.equals(dictItem.getStatus())));
							}
					}
				}
			}
			html.append("</select>");
		}
		html.append("<script type='text/javascript'> \n");
		html.append("$(function(){\n");
		// 登录界面没有导入common.jsp界面 所以操作员类别取消下拉搜索
		html.append("if('"+id+"'!='czylb'){\n");
		html.append("$('#"+id+"').searchableSelect();\n");
		html.append("}\n");
		html.append("})\n");
		html.append("</script>");
		try {
			pageContext.getOut().print(html.toString());
		} catch (java.io.IOException e) {
			e.printStackTrace();
			logger.error("生成字典 ["+html.toString()+"] 出错", e);
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}
	
	private String bulidOption(Object value, Object label, String selectValue, boolean disabled){
		if(value == null || disabled || isUnShowValue(value.toString().trim()))
			return "";
		
		String val = value.toString().trim();
		String option="\t<option value=\""+val+"\"";
		if(val.equals(selectValue)){
			option += " selected = \"selected\"";
		}
		option +=">"+label+"</option>\n";
		return option;
	}
	
	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSqlValueKey() {
		return sqlValueKey;
	}

	public void setSqlValueKey(String sqlValueKey) {
		this.sqlValueKey = sqlValueKey;
	}

	public String getSqlLableKey() {
		return sqlLableKey;
	}

	public void setSqlLableKey(String sqlLableKey) {
		this.sqlLableKey = sqlLableKey;
	}

}
