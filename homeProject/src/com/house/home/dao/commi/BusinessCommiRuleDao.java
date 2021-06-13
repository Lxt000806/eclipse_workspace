package com.house.home.dao.commi;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.commi.BusinessCommiRule;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class BusinessCommiRuleDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BusinessCommiRule businessCommiRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.pk, a.Role, a.PosiType, a.Department, a.EmpCode, a.Prior, a.Type, a.CommiPer, a.SubsidyPer," +
				"	a.DesignAgainSubsidyPer, a.FloatRulePK, a.IsBearAgainCommi, a.RecordCommiPer, a.LastUpdate,a.Remarks," +
				"	a.LastUpdatedBy, a.Expired, a.ActionLog ,b.NameChi, c.Desc1 deptDescr, d.Descr roleDescr,x1.NOTE typeDescr,x2.NOTE IsBearAgainCommiDescr," +
				"	f.Desc2 PosiTypeDescr, e.descr FloatRuleDescr,a.RightCommiPer,a.RecordRightCommiPer " +
				" from tBusinessCommiRule a " +
				" left join tEmployee b on b.Number = a.EmpCode" +
				" left join tDepartment c on c.Code = a.Department" +
				" left join tRoll d on d.Code = a.Role" +
				" left join tBusinessCommiFloatRule e on e.PK = a.FloatRulePK" +
				" left join tXTDM x1 on x1.cbm = a.Type and x1.ID = 'COMMIRULETYPE'" +
				" left join tXTDM x2 on x2.cbm = a.IsBearAgainCommi and x2.ID = 'YESNO'" +
				" left join tPosition f on f.Code = a.PosiType" +
				" where 1=1 ";
    	
		if(StringUtils.isNotBlank(businessCommiRule.getDepartment())){
			sql+=" and a.Department in ("+SqlUtil.resetStatus(businessCommiRule.getDepartment())+")";
		}
		
		if(StringUtils.isNotBlank(businessCommiRule.getPosiType())){
			sql+=" and a.PosiType in ("+SqlUtil.resetStatus(businessCommiRule.getPosiType())+")";
		}
		
		if(StringUtils.isNotBlank(businessCommiRule.getType())){
			sql+=" and a.Type = ? ";
			list.add(businessCommiRule.getType());
		}
		
		if(StringUtils.isNotBlank(businessCommiRule.getRole())){
			sql+=" and a.Role = ? ";
			list.add(businessCommiRule.getRole());
		}
		if (StringUtils.isBlank(businessCommiRule.getExpired())
				|| "F".equals(businessCommiRule.getExpired())) {
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
