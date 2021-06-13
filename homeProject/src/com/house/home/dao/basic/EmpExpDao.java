package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.EmpExp;

@SuppressWarnings("serial")
@Repository
public class EmpExpDao extends BaseDao {

	/**
	 * Activity分页信息
	 * 
	 * @param page
	 * @param activity
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EmpExp empExp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.pk,a.number,a.company,a.position,a.salary,a.leaversn,a.begindate," +
				"a.enddate,a.lastupdatedby,a.expired,a.actionlog,a.lastupdate  " +
				" from tempExp a"+
				" where 1=1 ";
		if(StringUtils.isNotBlank(empExp.getNumber())){
			sql +=" and a.number =?";
			list.add(empExp.getNumber());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.lastUpdate";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}

