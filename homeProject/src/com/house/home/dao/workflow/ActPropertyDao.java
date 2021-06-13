package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActProperty;

@SuppressWarnings("serial")
@Repository
public class ActPropertyDao extends BaseDao {

	/**
	 * ActProperty分页信息
	 * 
	 * @param page
	 * @param actProperty
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProperty actProperty) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_GE_PROPERTY a where 1=1 ";

    	if (StringUtils.isNotBlank(actProperty.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actProperty.getName());
		}
    	if (StringUtils.isNotBlank(actProperty.getValue())) {
			sql += " and a.VALUE_=? ";
			list.add(actProperty.getValue());
		}
    	if (actProperty.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actProperty.getRev());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Name";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

