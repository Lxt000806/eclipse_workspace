package com.house.framework.tag;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.google.common.collect.Lists;
import com.house.framework.commons.utils.SpringContextHolder;

public class DataSelectTag extends CommonSelectTag {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DataSelectTag.class);
	/** 实体类名*/
	private String className;
	
	/** option value值对应的字段 */
	private String keyColumn="id";
	
	/** option 显示的值对应的字段 */
	private String valueColumn="name";
	
	/** 筛选条件 */
	private String filterValue;
	
	/** 实体类路径 */
	private String[] entityPath = {
			"com.house.framework.entity",
			"com.house.home.entity.basic",
			"com.house.home.entity.design",
			"com.house.home.entity.insales",
			"com.house.home.entity.project",
			"com.house.home.entity.query",
			"com.house.home.entity.finance"
			};
	
	public int doStartTag() throws JspException {
		HibernateTemplate hibernateTemplate = 
				(HibernateTemplate) SpringContextHolder.getApplicationContext().getBean("hibernateTemplate");
		StringBuffer html = new StringBuffer("");
		if(StringUtils.isBlank(id) || StringUtils.isBlank(className) 
				|| StringUtils.isBlank(keyColumn) || StringUtils.isBlank(valueColumn))
			return SKIP_BODY;
		Class<?> clazz = null;
		for (String str:entityPath){
			try{
				clazz = Class.forName(str+"."+className);
			} catch (ClassNotFoundException e) {
				logger.debug(e.getMessage());
			}
			if (clazz != null){
				break;
			}
		}
		if (clazz == null)
			return SKIP_BODY;
		int ikeyColumn = -1,ivalueColumn = -1;
		Field[] fields = clazz.getDeclaredFields();//获取实体类的所有属性，返回Field数组 
		AccessibleObject.setAccessible(fields, true);//获取private属性，要将setAccessible设置为true. 默认的值为false
		for (int i=0;i<fields.length;i++){//遍历所有属性
			if (fields[i].getName().equals(keyColumn)){
				ikeyColumn = i;
			}
			if (fields[i].getName().equals(valueColumn)){
				ivalueColumn = i;
			}
		}
		if (ikeyColumn==-1 || ivalueColumn==-1){
			return SKIP_BODY;
		}
		if (readonly != null && readonly.equalsIgnoreCase("true")){
			if(StringUtils.isBlank(value))
				return SKIP_BODY;
			Object obj = null;
			String hql = "from "+clazz.getSimpleName()+" where "+keyColumn+"="+value;
			List<?> list = hibernateTemplate.find(hql);
			if (list != null && list.size()>0){
				obj = list.get(0);
			}
			if (obj == null)
				return SKIP_BODY;
			String str = "";
			try {
				str = fields[ivalueColumn].get(obj).toString();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			html.append(getReadonlyStr(str));
		}else{
			html.append(getSelectStr());

			List<?> list = null;
			if (StringUtils.isNotBlank(filterValue)){
				StringBuffer sb = new StringBuffer();
				sb.append("from ").append(clazz.getSimpleName()).append(" where ").append(filterValue);
				list = hibernateTemplate.find(sb.toString());
			}else{
				list = hibernateTemplate.loadAll(clazz);
			}
			if (list==null) {
				list = Lists.newArrayList();
			}
			List<String> checkList = new ArrayList<String>();//用来判断skey1是否重复
			for (Object o:list){
				String skey1="",skey2="";
				try {
					if (fields[ikeyColumn].get(o)!=null && fields[ivalueColumn].get(o)!=null){
						skey1 = fields[ikeyColumn].get(o).toString();
						skey2 = fields[ivalueColumn].get(o).toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				if (StringUtils.isNotBlank(skey1) && checkList.indexOf(skey1)==-1){
					checkList.add(skey1);
				}else{
					continue;
				}
				
				if (!isUnShowValue(skey1) || (value != null && skey2 != null && value.equals(skey1))) {
					if ((value != null && skey2 != null && value.equals(skey1))||(value != null && skey2 != null && value.equals(skey1.trim()))) {
						html.append("<option selected=\"true\"");
					} else {
						html.append("<option");
					}
					html.append(" value=\"").append(skey1).append("\">");
					html.append(skey1).append(" ");
					html.append(skey2);
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

}
