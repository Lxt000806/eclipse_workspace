package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MainCommiPerc;
import com.house.home.entity.basic.MainCommiPercIndeSale;

@SuppressWarnings("serial")
@Repository
public class MainCommiPercIndeSaleDao extends BaseDao {

	/**
	 * MainCommiPercIndeSale分页信息
	 * 
	 * @param page
	 * @param mainCommiPercIndeSale
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiPercIndeSale mainCommiPercIndeSale) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.PK, a.Code, a.Descr, a.SaleType,a.MainCommiPerc,a.ServCommiPerc,a.ElecCommiPerc, "
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, x1.note SaleTypeDescr "
				+ " from tMainCommiPerc_IndeSale a"
				+ " left join txtdm x1 on x1.id='COMMISALETYPE' and a.SaleType=x1.cbm "
				+ " where 1=1 ";

    	if (mainCommiPercIndeSale.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(mainCommiPercIndeSale.getPk());
		}
    	if (StringUtils.isNotBlank(mainCommiPercIndeSale.getSaleType())) {
			sql += " and a.SaleType=? ";
			list.add(mainCommiPercIndeSale.getSaleType());
		}
    	if (mainCommiPercIndeSale.getMainCommiPerc() != null) {
			sql += " and a.MainCommiPerc=? ";
			list.add(mainCommiPercIndeSale.getMainCommiPerc());
		}
    	if (mainCommiPercIndeSale.getServCommiPerc() != null) {
			sql += " and a.ServCommiPerc=? ";
			list.add(mainCommiPercIndeSale.getServCommiPerc());
		}
    	if (mainCommiPercIndeSale.getElecCommiPerc() != null) {
			sql += " and a.ElecCommiPerc=? ";
			list.add(mainCommiPercIndeSale.getElecCommiPerc());
		}
    	if (mainCommiPercIndeSale.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(mainCommiPercIndeSale.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(mainCommiPercIndeSale.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(mainCommiPercIndeSale.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(mainCommiPercIndeSale.getExpired()) || "F".equals(mainCommiPercIndeSale.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(mainCommiPercIndeSale.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(mainCommiPercIndeSale.getActionLog());
		}
    	if (StringUtils.isNotBlank(mainCommiPercIndeSale.getCode())) {
			sql += " and a.Code=? ";
			list.add(mainCommiPercIndeSale.getCode());
		}
    	if (StringUtils.isNotBlank(mainCommiPercIndeSale.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(mainCommiPercIndeSale.getDescr());
		}
    	if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.Code  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean checkExistMainCommiPercIndeSale(MainCommiPercIndeSale mainCommiPercIndeSale) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		if("A".equals(mainCommiPercIndeSale.getM_umState())||"C".equals(mainCommiPercIndeSale.getM_umState())){
			sql = "select 1 from tMainCommiPerc_IndeSale where Code=? and  SaleType=? ";
			list.add(mainCommiPercIndeSale.getCode());
			list.add(mainCommiPercIndeSale.getSaleType());
		}else if("M".equals(mainCommiPercIndeSale.getM_umState())){
			sql = "select 1 from tMainCommiPerc_IndeSale where Code =? and SaleType=? and pk<>?" ;
			list.add(mainCommiPercIndeSale.getCode());
			list.add(mainCommiPercIndeSale.getSaleType());
			list.add(mainCommiPercIndeSale.getPk());
		}
		List<Map<String,Object>> listResult= this.findBySql(sql, list.toArray());
		if (listResult!=null && listResult.size()>0){
			return true;
		}else{
			return false;
		}
	}

}

