package com.house.home.dao.commi;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.commi.DesignCommiRule;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class DesignCommiRuleDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DesignCommiRule designCommiRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from	(select	a.PK, a.Role, a.Department, a.EmpCode, a.Prior, a.PreCommiPer, a.CheckCommiType, a.CheckCommiPer, a.FloatRulePK," +
				"	a.SubsidyPer, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,a.remarks, " +
				"	 b.NameChi, c.Desc1 deptDescr, d.Descr roleDescr, x1.NOTE typeDescr, e.descr FloatRuleDescr" +
				"	from tDesignCommiRule a" +
				"	left join tEmployee b on b.Number = a.EmpCode" +
				"	left join tDepartment c on c.Code = a.Department" +
				"	left join tRoll d on d.Code = a.Role" +
				"	left join tDesignCommiFloatRule e on e.PK = a.FloatRulePK" +
				"	left join tXTDM x1 on x1.CBM = a.CheckCommiType and x1.ID = 'COMMIRULETYPE'" +
				"	where 1 = 1" ;
    	
		if(StringUtils.isNotBlank(designCommiRule.getDepartment())){
			sql+=" and a.Department in ("+SqlUtil.resetStatus(designCommiRule.getDepartment())+")";
		}
		
		if(StringUtils.isNotBlank(designCommiRule.getCheckCommiType())){
			sql+=" and a.CheckCommiType = ? ";
			list.add(designCommiRule.getCheckCommiType());
		}
		if(StringUtils.isNotBlank(designCommiRule.getRole())){
			sql+=" and a.Role = ? ";
			list.add(designCommiRule.getRole());
		}
		if (StringUtils.isBlank(designCommiRule.getExpired())
				|| "F".equals(designCommiRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}
