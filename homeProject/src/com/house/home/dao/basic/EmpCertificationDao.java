package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.EmpCertification;
@SuppressWarnings("serial")
@Repository
public class EmpCertificationDao extends BaseDao {

	/**
	 * 
	 * 
	 * @param page
	 * @param EmpCertification
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EmpCertification empCertification) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.pk,a.number,a.certification," +
				"a.lastupdatedby,a.expired,a.actionlog,a.lastupdate  " +
				" from tEmpCertification a"+
				" where 1=1 ";
		if(StringUtils.isNotBlank(empCertification.getNumber())){
			sql +=" and a.number =?";
			list.add(empCertification.getNumber());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.lastUpdate";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}

