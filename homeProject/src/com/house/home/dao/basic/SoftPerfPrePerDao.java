package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SoftPerfPrePer;

@SuppressWarnings("serial")
@Repository
public class SoftPerfPrePerDao extends BaseDao {

	/**
	 * SoftPerfPrePer分页信息
	 * 
	 * @param page
	 * @param softPerfPrePer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftPerfPrePer softPerfPrePer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.Department1,a.IsOutSideEmp," +
				" a.Per,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, dp1.desc1 Department1descr,x1.note IsOutSideEmpDescr  " +
				" from tSoftPerfPrePer a" +
				" left join tDepartment1 dp1 on dp1.Code=a.Department1"+
				" left join tXtdm x1 on x1.id='YESNO' and x1.cbm=a.IsOutSideEmp"+
				" where 1=1 ";
    	if (StringUtils.isNotBlank(softPerfPrePer.getDepartment1())) {
			sql += " and a.Department1=? ";
			list.add(softPerfPrePer.getDepartment1());
		}
    	if (StringUtils.isNotBlank(softPerfPrePer.getIsOutSideEmp())) {
			sql += " and a.IsOutSideEmp=? ";
			list.add(softPerfPrePer.getIsOutSideEmp());
		}
    	
		if (StringUtils.isBlank(softPerfPrePer.getExpired()) || "F".equals(softPerfPrePer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean hasDepartment1(SoftPerfPrePer softPerfPrePer) {
		String sql =" select * from tSoftPerfPrePer where 1=1 ";
	
		List<Object> params = new ArrayList<Object>();
		
		if (softPerfPrePer.getPk()!=null) {
			sql += "and pk<> ? ";
			params.add(softPerfPrePer.getPk());
		}
		if (StringUtils.isNotBlank(softPerfPrePer.getDepartment1())) {
			sql += " and Department1=? ";
			params.add(softPerfPrePer.getDepartment1());
		}
		
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
}

