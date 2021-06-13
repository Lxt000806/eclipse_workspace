package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.CommiDetail;

@SuppressWarnings("serial")
@Repository
public class CommiDetailDao extends BaseDao {

	/**
	 * CommiDetail分页信息
	 * 
	 * @param page
	 * @param commiDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiDetail commiDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCommiDetail a where 1=1 ";

    	if (commiDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiDetail.getPk());
		}
    	if (StringUtils.isNotBlank(commiDetail.getCalNo())) {
			sql += " and a.CalNo=? ";
			list.add(commiDetail.getCalNo());
		}
    	if (commiDetail.getRefRulePk() != null) {
			sql += " and a.RefRulePk=? ";
			list.add(commiDetail.getRefRulePk());
		}
    	if (StringUtils.isNotBlank(commiDetail.getType())) {
			sql += " and a.Type=? ";
			list.add(commiDetail.getType());
		}
    	if (StringUtils.isNotBlank(commiDetail.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(commiDetail.getCustCode());
		}
    	if (StringUtils.isNotBlank(commiDetail.getSino())) {
			sql += " and a.SINo=? ";
			list.add(commiDetail.getSino());
		}
    	if (StringUtils.isNotBlank(commiDetail.getRole())) {
			sql += " and a.Role=? ";
			list.add(commiDetail.getRole());
		}
    	if (StringUtils.isNotBlank(commiDetail.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(commiDetail.getEmpCode());
		}
    	if (StringUtils.isNotBlank(commiDetail.getPeriod())) {
			sql += " and a.Period=? ";
			list.add(commiDetail.getPeriod());
		}
    	if (commiDetail.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(commiDetail.getAmount());
		}
    	if (StringUtils.isNotBlank(commiDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(commiDetail.getRemarks());
		}
    	if (commiDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(commiDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(commiDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(commiDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(commiDetail.getExpired()) || "F".equals(commiDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(commiDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(commiDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CommiDetail commiDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Pk,a.CalNo,a.RefRulePK,f.Descr,a.Type,b.NOTE TypeDescr,"
                +"a.CustCode,a.SINo,a.Role,c.Descr RoleDescr,a.EmpCode,d.NameChi EmpName,a.Period,e.NOTE PeriodDescr,"
                +"a.Amount,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
                +"from tCommiDetail a "
                +"left join tXTDM b on b.CBM=a.Type and b.ID='COMMIDTLTYPE' "
                +"left join tRoll c on c.Code=a.Role "
                +"left join tEmployee d on d.Number=a.Empcode "
                +"left join tXTDM e on e.CBM=a.Period and e.ID='COMMIDTLPERIOD' "
                +"left join (select pk,Descr,'1' Type from tAmountRule "
                +"union all select pk,Descr,'2' Type from tMailRule) f "
                +"on a.RefRulePk=f.pk and a.type=f.Type "
                +"where a.CalNo=? and a.CustCode=? ";
		if (StringUtils.isNotBlank(commiDetail.getCalNo()) && StringUtils.isNotBlank(commiDetail.getCustCode())) {
			list.add(commiDetail.getCalNo());
    		list.add(commiDetail.getCustCode());
    	}else{
    		return null;
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}

