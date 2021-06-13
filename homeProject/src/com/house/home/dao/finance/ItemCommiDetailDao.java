package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.ItemCommiDetail;

@SuppressWarnings("serial")
@Repository
public class ItemCommiDetailDao extends BaseDao {

	/**
	 * ItemCommiDetail分页信息
	 * 
	 * @param page
	 * @param itemCommiDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiDetail itemCommiDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemCommiDetail a where 1=1 ";

    	if (itemCommiDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemCommiDetail.getPk());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getCalNo())) {
			sql += " and a.CalNo=? ";
			list.add(itemCommiDetail.getCalNo());
		}
    	if (itemCommiDetail.getRefRulePk() != null) {
			sql += " and a.RefRulePk=? ";
			list.add(itemCommiDetail.getRefRulePk());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemCommiDetail.getCustCode());
		}
    	if (itemCommiDetail.getItemRefPk() != null) {
			sql += " and a.ItemRefPK=? ";
			list.add(itemCommiDetail.getItemRefPk());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getRole())) {
			sql += " and a.Role=? ";
			list.add(itemCommiDetail.getRole());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(itemCommiDetail.getEmpCode());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getPeriod())) {
			sql += " and a.Period=? ";
			list.add(itemCommiDetail.getPeriod());
		}
    	if (itemCommiDetail.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(itemCommiDetail.getAmount());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemCommiDetail.getRemarks());
		}
    	if (itemCommiDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemCommiDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemCommiDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemCommiDetail.getExpired()) || "F".equals(itemCommiDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemCommiDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemCommiDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, ItemCommiDetail itemCommiDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Pk,a.CalNo,a.RefRulePK,f.Descr,a.ItemRefPK,"
                +"a.CustCode,a.Role,c.Descr RoleDescr,a.EmpCode,d.NameChi EmpName,a.Period,e.NOTE PeriodDescr,"
                +"a.Amount,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
                +"from tItemCommiDetail a "
                +"left join tRoll c on c.Code=a.Role "
                +"left join tEmployee d on d.Number=a.Empcode "
                +"left join tXTDM e on e.CBM=a.Period and e.ID='COMMIDTLPERIOD' "
                +"left join tMailRule f on f.pk=a.RefRulePk "
                +"where a.CalNo=? and a.CustCode=? ";
		if (StringUtils.isNotBlank(itemCommiDetail.getCalNo()) && StringUtils.isNotBlank(itemCommiDetail.getCustCode())) {
			list.add(itemCommiDetail.getCalNo());
    		list.add(itemCommiDetail.getCustCode());
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

