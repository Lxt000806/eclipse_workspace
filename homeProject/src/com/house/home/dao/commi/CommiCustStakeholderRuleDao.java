package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholderRule;

@SuppressWarnings("serial")
@Repository
public class CommiCustStakeholderRuleDao extends BaseDao {

	/**
	 * CommiCustStakeholderRule分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholderRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholderRule commiCustStakeholderRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.SignCommiNo,a.CustCode,a.EmpCode,a.Role,a.WeightPer,a.CommiPer,a.SubsidyPer,a.Multiple, "
				+"a.CheckCommiPer,a.CommiProvidePer,a.SubsidyProvidePer,a.MultipleProvidePer,a.TotalProvideAmount,a.CrtNo, "
				+"a.LastUpdateNo,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Address,c.NameChi EmpName, "
				+"d.Descr RoleDescr,e.ExprRemarks PreCommiExpr,f.ExprRemarks CheckCommiExpr,g.Descr CheckFloatRule,h.NameChi OldStakeholderDescr, "
				+"i.NameChi OldStakeholder2Descr,j.NOTE IsBearAgainCommiDescr,k.NOTE IsModifiedDescr,l.NOTE CheckCommiTypeDescr, "
				+"m.Mon CrtMon,n.Mon SignCommiMon,o.Mon LastUpdateMon,a.RightCommiPer,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr,d3.Desc2 Dept3Descr  "
				+"from tCommiCustStakeholderRule a  "
				+"left join tCustomer b on a.CustCode = b.Code "
				+"left join tEmployee c on a.EmpCode = c.Number "
				+"left join tRoll d on a.Role = d.Code "
				+"left join tCommiExpr e on a.PreCommiExprPK = e.PK "
				+"left join tCommiExpr f on a.CheckCommiExprPK = f.PK "
				+"left join tDesignCommiFloatRule g on a.CheckFloatRulePK = g.PK "
				+"left join tEmployee h on a.OldStakeholder = h.Number "
				+"left join tEmployee i on a.OldStakeholder2 = i.Number "
				+"left join tXTDM j on a.IsBearAgainCommi = j.CBM and j.ID = 'YESNO' "
				+"left join tXTDM k on a.IsModified = k.CBM and k.ID = 'YESNO' "
				+"left join tXTDM l on a.CheckCommiType = l.CBM and l.ID = 'COMMIRULETYPE' "
				+"left join tCommiCycle m on a.CrtNo = m.No "
				+"left join tCommiCycle n on a.SignCommiNo = n.No "
				+"left join tCommiCycle o on a.LastUpdateNo = o.No "
				+"left join tDepartment1 d1 on a.Department1 = d1.Code "
				+"left join tDepartment2 d2 on a.Department2 = d2.Code "
				+"left join tDepartment3 d3 on a.Department3 = d3.Code "
				+"where 1=1 ";

    	if (commiCustStakeholderRule.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiCustStakeholderRule.getPk());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderRule.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+commiCustStakeholderRule.getAddress()+"%");
		}
    	if (commiCustStakeholderRule.getMon() != null) {
    		sql += " and exists(" 
					+"	select 1 from tCommiCustStakeholder in_a " 
    				+"  left join tCommiCycle in_b on in_a.CommiNo = in_b.No "
					+"	where in_b.Mon = ? and in_a.CustCode = a.CustCode and in_a.SignCommiNo = a.SignCommiNo " 
					+") ";
			list.add(commiCustStakeholderRule.getMon());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderRule.getCrtNo())) {
    		sql += " and exists(" 
					+"	select 1 from tCommiCustStakeholder in_a " 
					+"	where in_a.CommiNo = ? and in_a.CustCode = a.CustCode and in_a.SignCommiNo = a.SignCommiNo" 
					+") ";
			list.add(commiCustStakeholderRule.getCrtNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderRule.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(commiCustStakeholderRule.getCustCode());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholderRule.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(commiCustStakeholderRule.getEmpCode());
		}
    	if ("1".equals(commiCustStakeholderRule.getOnlyHitAgainMan())) {
			sql+="and exists("
				+"	select 1 from tCommiCustStakeholderRule in_a "
				+"	where a.CustCode = in_a.CustCode and a.SignCommiNo = in_a.SignCommiNo "
				+"	group by in_a.SignCommiNo,in_a.CustCode "
				+"  having sum(case when in_a.Role = '01' then 1 else 0 end) >= 2  "
				+"	and sum(case when in_a.Role = '24' then 1 else 0 end) >= 1 "
				+")";
		}
    	System.out.println(sql);
    	System.out.println(list);
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * CommiCustStakeholderRule分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholderRule
	 * @return
	 */
	public Page<Map<String,Object>> goRuleJqGrid(Page<Map<String,Object>> page, CommiCustStakeholderRule commiCustStakeholderRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ("
				+"select a.PK,CardinalFrom,CardinalTo,CommiPer,b.Remarks "
			    +"from tDesignCommiFloatRuleHis a "
				+"inner join tDesignCommiFloatRuleDetailHis b on a.PK=b.FloatRuleHisPK "
				+"where a.CommiNo=? and a.OriginalPK=? ";

    	list.add(commiCustStakeholderRule.getCrtNo());
    	list.add(commiCustStakeholderRule.getCheckFloatRulePk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.PK ";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}

}

