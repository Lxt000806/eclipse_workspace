package com.house.home.dao.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.AgainSign;

@SuppressWarnings("serial")
@Repository
public class AgainSignDao extends BaseDao {

	/**
	 * AgainSign分页信息
	 * 
	 * @param page
	 * @param againSign
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AgainSign againSign) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tAgainSign a where 1=1 ";

    	if (againSign.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(againSign.getPk());
		}
    	if (StringUtils.isNotBlank(againSign.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(againSign.getCustCode());
		}
    	if (StringUtils.isNotBlank(againSign.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(againSign.getCustType());
		}
    	if (StringUtils.isNotBlank(againSign.getContractType())) {
			sql += " and a.ContractType=? ";
			list.add(againSign.getContractType());
		}
    	if (againSign.getContractFee() != null) {
			sql += " and a.ContractFee=? ";
			list.add(againSign.getContractFee());
		}
    	if (againSign.getSignDate() != null) {
			sql += " and a.SignDate=? ";
			list.add(againSign.getSignDate());
		}
    	if (StringUtils.isNotBlank(againSign.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(againSign.getRemarks());
		}
    	if (againSign.getNewSignDate() != null) {
			sql += " and a.NewSignDate=? ";
			list.add(againSign.getNewSignDate());
		}
    	if (StringUtils.isNotBlank(againSign.getHadCalcPerf())) {
			sql += " and a.HadCalcPerf=? ";
			list.add(againSign.getHadCalcPerf());
		}
    	if (againSign.getPerfPk() != null) {
			sql += " and a.PerfPK=? ";
			list.add(againSign.getPerfPk());
		}
    	if (StringUtils.isNotBlank(againSign.getHadCalcBackPerf())) {
			sql += " and a.HadCalcBackPerf=? ";
			list.add(againSign.getHadCalcBackPerf());
		}
    	if (againSign.getBackPerfPk() != null) {
			sql += " and a.BackPerfPK=? ";
			list.add(againSign.getBackPerfPk());
		}
    	if (againSign.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(againSign.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(againSign.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(againSign.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(againSign.getExpired()) || "F".equals(againSign.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(againSign.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(againSign.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, AgainSign againSign) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.CustCode,a.CustType,b.Desc1 CustTypeDescr,a.ContractType,x1.NOTE ContractTypeDescr,"
			    +"a.ContractFee,a.SignDate,a.NewSignDate,a.Remarks,a.HadCalcPerf,x2.NOTE HadCalcPerfDescr,a.PerfPK,"
			    +"a.HadCalcBackPerf,x3.NOTE HadCalcBackPerfDescr,a.BackPerfPK,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,"
			    +"a.DocumentNo,a.DesignFee,a.BaseFee_Comp,a.BaseFee_Dirct,a.LongFee,a.MainFee,a.MainDisc,a.MainServFee,"
				+"a.SoftFee,a.SoftDisc,a.SoftFee_Furniture,a.IntegrateFee,a.IntegrateDisc,a.CupboardFee,a.CupboardDisc,"
				+"a.FirstPay,a.BaseDisc, a.Area, a.Tax "
			    +"from tAgainSign a "
			    +"left join tCusttype b on a.CustType=b.Code "
			    +"left join tXTDM x1 on a.ContractType=x1.cbm and x1.ID='CONTYPE' "
			    +"left join tXTDM x2 on a.HadCalcPerf=x2.cbm and x2.id='YESNO' "
			    +"left join tXTDM x3 on a.HadCalcPerf=x3.cbm and x3.id='YESNO' "
			    +"where a.CustCode=? ";
		if (StringUtils.isNotBlank(againSign.getCustCode())){
			list.add(againSign.getCustCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.NewSignDate";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

