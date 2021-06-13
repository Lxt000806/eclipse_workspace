package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustStakeholderHis;

@SuppressWarnings("serial")
@Repository
public class CustStakeholderHisDao extends BaseDao {

	/**
	 * CustStakeholderHis分页信息
	 * 
	 * @param page
	 * @param custStakeholderHis
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustStakeholderHis custStakeholderHis) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCustStakeholderHis a where 1=1 ";

    	if (custStakeholderHis.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custStakeholderHis.getPk());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getOperType())) {
			sql += " and a.OperType=? ";
			list.add(custStakeholderHis.getOperType());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getRole())) {
			sql += " and a.Role=? ";
			list.add(custStakeholderHis.getRole());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getOldRole())) {
			sql += " and a.OldRole=? ";
			list.add(custStakeholderHis.getOldRole());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(custStakeholderHis.getEmpCode());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getOldEmpCode())) {
			sql += " and a.OldEmpCode=? ";
			list.add(custStakeholderHis.getOldEmpCode());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custStakeholderHis.getCustCode());
		}
    	if (custStakeholderHis.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custStakeholderHis.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custStakeholderHis.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custStakeholderHis.getExpired()) || "F".equals(custStakeholderHis.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custStakeholderHis.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custStakeholderHis.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page,
			CustStakeholderHis custStakeholderHis) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.pk,a.OperType,g.NOTE OperTypeDescr,"
	            + " a.Role,b.Descr RoleDescr,a.EmpCode,c.NameChi EmpName,"
	            + " a.OldRole,e.Descr OldRoleDescr,a.OldEmpCode,f.NameChi OldEmpName,"
	            + " a.CustCode,d.Descr CustName,"
	            + " a.LastUpdate,a.LastUpdatedBy,a.Expired,"
	            + " a.ActionLog,d.Address from tCustStakeholderHis a  "
	            + " left outer join tRoll b on b.Code=a.Role "
	            + " left outer join tEmployee c on c.Number=a.EmpCode "
	            + " left outer join tCustomer d on d.Code=a.CustCode "
	            + " left outer join tRoll e on e.Code=a.OldRole "
	            + " left outer join tEmployee f on f.Number=a.OldEmpCode "
	            + " left outer join tXTDM g on a.OperType=g.CBM and g.ID='CUSTSTHHISTYPE' "
	            + " where a.CustCode = ? ";
		if (StringUtils.isNotBlank(custStakeholderHis.getCustCode())){
			list.add(custStakeholderHis.getCustCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

