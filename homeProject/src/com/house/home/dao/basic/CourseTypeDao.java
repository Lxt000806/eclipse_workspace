package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.CourseType;
import com.house.home.entity.basic.RcvAct;

import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class CourseTypeDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CourseType courseType) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code,a.Descr ,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from  tCourseType a where 1=1 ";	

		if (StringUtils.isNotBlank(courseType.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+courseType.getCode()+"%");
		}
		
		if (StringUtils.isNotBlank(courseType.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + courseType.getDescr() + "%");
		}
		
		if (StringUtils.isBlank(courseType.getExpired()) 
				|| "F".equals(courseType.getExpired())) {
			sql += " and a.expired='F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public CourseType getByCode(String code) {
		String hql = "from CourseType a where a.code=? ";
		List<CourseType> list = this.find(hql, new Object[]{code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public CourseType getByDescr(String descr, String code) {
		String hql = "from CourseType a where a.descr=? and a.code <> ?";
		List<CourseType> list = this.find(hql, new Object[]{descr,code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}

}
