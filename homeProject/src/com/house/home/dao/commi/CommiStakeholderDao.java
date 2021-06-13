package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiStakeholder;

@SuppressWarnings("serial")
@Repository
public class CommiStakeholderDao extends BaseDao {

	/**
	 * CommiStakeholder分页信息
	 * 
	 * @param page
	 * @param commiStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiStakeholder commiStakeholder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CommiNo,a.EmpCode,a.Department,a.Role,a.EffectiveCardinal, "
				+"a.CommiPer,a.Multiple,a.SubsidyPer,a.Remarks,a.IsModified,a.LastUpdate, "
				+"a.DesignAgainSubsidyPer,a.RecordCommiPer,a.CheckCommiPer,a.CheckFloatRulePK,"
				+"a.LastUpdatedBy,a.Expired,a.ActionLog,b.NameChi EmpName, "
				+"d.Descr RoleDescr,e.NOTE IsModifiedDescr,f.NOTE IsBearAgainCommiDescr,"
				+"h.Descr CheckFloatRuleDescr,g.NOTE CheckCommiTypeDescr,i.Mon CommiMon, "
				+"j.Desc2 Dept1Descr,k.Desc2 Dept2Descr,j.DispSeq Dept1DispSeq,k.DispSeq Dept2DispSeq,"
				+"l.Desc2 Dept3Descr,a.RightCommiPer,a.RecordRightCommiPer "
				+"from tCommiStakeholder a  "
				+"left join tEmployee b on a.EmpCode = b.Number  "
				+"left join tRoll d on a.Role = d.Code "
				+"left join tXTDM e on e.ID = 'YESNO' and a.IsModified = e.CBM "
				+"left join tXTDM f on f.ID = 'YESNO' and a.IsBearAgainCommi = f.CBM "
				+"left join tXTDM g on g.ID = 'COMMIRULETYPE' and a.CheckCommiType = g.CBM "
				+"left join tDesignCommiFloatRule h on a.CheckFloatRulePK = h.PK "
				+"left join tCommiCycle i on a.CommiNo = i.No "
				+"left join tDepartment1 j on a.Department1 = j.Code "
				+"left join tDepartment2 k on a.Department2 = k.Code "
				+"left join tDepartment3 l on a.Department3 = l.Code "
				+"where 1=1 ";
    	if (commiStakeholder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiStakeholder.getPk());
		}
    	if (commiStakeholder.getMon() != null) {
			sql += " and i.Mon=? ";
			list.add(commiStakeholder.getMon());
		}
    	if (StringUtils.isNotBlank(commiStakeholder.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiStakeholder.getCommiNo());
		}
    	if (StringUtils.isNotBlank(commiStakeholder.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(commiStakeholder.getEmpCode());
		}
    	if (StringUtils.isNotBlank(commiStakeholder.getDepartment())) {
			sql += " and a.Department=? ";
			list.add(commiStakeholder.getDepartment());
		}
    	if (StringUtils.isNotBlank(commiStakeholder.getRole())) {
			sql += " and a.Role=? ";
			list.add(commiStakeholder.getRole());
		}
    	if (commiStakeholder.getEffectiveCardinal() != null) {
			sql += " and a.EffectiveCardinal=? ";
			list.add(commiStakeholder.getEffectiveCardinal());
		}
    	if (commiStakeholder.getCommiPer() != null) {
			sql += " and a.CommiPer=? ";
			list.add(commiStakeholder.getCommiPer());
		}
    	if (commiStakeholder.getMultiple() != null) {
			sql += " and a.Multiple=? ";
			list.add(commiStakeholder.getMultiple());
		}
    	if (commiStakeholder.getSubsidyPer() != null) {
			sql += " and a.SubsidyPer=? ";
			list.add(commiStakeholder.getSubsidyPer());
		}
    	if (StringUtils.isNotBlank(commiStakeholder.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(commiStakeholder.getRemarks());
		}
    	if (StringUtils.isNotBlank(commiStakeholder.getIsModified())) {
			sql += " and a.IsModified=? ";
			list.add(commiStakeholder.getIsModified());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Dept1DispSeq,Dept2DispSeq,a.Role ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

}

