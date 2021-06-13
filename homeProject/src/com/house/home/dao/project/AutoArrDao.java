package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.AutoArr;

@SuppressWarnings("serial")
@Repository
public class AutoArrDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, AutoArr autoArr) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from tAutoArr a where 1=1 ";
		
		if(autoArr.getPk()!=null){
			sql+=" and a.pk =? ";
			list.add(autoArr.getPk());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.lastupdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
