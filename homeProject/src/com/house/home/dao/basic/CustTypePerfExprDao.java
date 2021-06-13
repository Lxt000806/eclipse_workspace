package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustTypePerfExpr;

@SuppressWarnings("serial")
@Repository
public class CustTypePerfExprDao extends BaseDao {

	/**
	 * CustTypePerfExpr分页信息
	 * 
	 * @param page
	 * @param custTypePerfExpr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustTypePerfExpr custTypePerfExpr) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.CustType,a.BeginDate,a.EndDate,a.PerfExpr,a.PerfExprRemarks,a.ChgPerfExpr,a.ChgPerfExprRemarks, "+
					 " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Desc1 CustTypeDescr "+
					 " from tCustTypePerfExpr a "+
					 " left join tCusttype b on a.CustType = b.Code " +
					 " where 1=1 ";
		if (custTypePerfExpr.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custTypePerfExpr.getPk());
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(custTypePerfExpr.getCustType());
		}
    	if (custTypePerfExpr.getBeginDate() != null) {
			sql += " and a.BeginDate=? ";
			list.add(custTypePerfExpr.getBeginDate());
		}
    	if (custTypePerfExpr.getEndDate() != null) {
			sql += " and a.EndDate=? ";
			list.add(custTypePerfExpr.getEndDate());
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getPerfExpr())) {
			sql += " and a.PerfExpr=? ";
			list.add(custTypePerfExpr.getPerfExpr());
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getPerfExprRemarks())) {
			sql += " and a.PerfExprRemarks=? ";
			list.add(custTypePerfExpr.getPerfExprRemarks());
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getChgPerfExpr())) {
			sql += " and a.ChgPerfExpr=? ";
			list.add(custTypePerfExpr.getChgPerfExpr());
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getChgPerfExprRemarks())) {
			sql += " and a.ChgPerfExprRemarks=? ";
			list.add(custTypePerfExpr.getChgPerfExprRemarks());
		}
    	if (custTypePerfExpr.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custTypePerfExpr.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custTypePerfExpr.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custTypePerfExpr.getExpired()) || "F".equals(custTypePerfExpr.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custTypePerfExpr.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custTypePerfExpr.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

