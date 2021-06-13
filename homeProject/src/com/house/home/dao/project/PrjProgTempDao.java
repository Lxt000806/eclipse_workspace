package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgTemp;

@SuppressWarnings("serial")
@Repository
public class PrjProgTempDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProgTemp prjProgTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,b.Desc1 CustTypeDescr from tPrjProgTemp a left join tCustType b on a.CustType=b.Code where 1=1 and a.Expired = 'F'";

		if(StringUtils.isNotBlank(prjProgTemp.getNo())){
			sql+=" and a.no =?";
			list.add(prjProgTemp.getNo());
		}
		if(StringUtils.isNotBlank(prjProgTemp.getDescr())){
			sql+="and a.descr like ?";
			list.add("%"+prjProgTemp.getDescr()+"%");
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
	
}
