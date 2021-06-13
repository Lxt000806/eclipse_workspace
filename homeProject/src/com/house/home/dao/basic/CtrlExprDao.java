package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CtrlExpr;

@SuppressWarnings("serial")
@Repository
public class CtrlExprDao extends BaseDao {

	/**
	 * CtrlExpr分页信息
	 * 
	 * @param page
	 * @param ctrlExpr
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CtrlExpr ctrlExpr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.CustType,a.BeginDate,a.EndDate,a.CtrlExpr,a.CtrlExprRemarks,a.SetCtrlExpr,a.LastUpdatedBy," //增加发包公式说明 add by zb
				+ "a.LastUpdate,a.Expired,a.ActionLog,b.Desc1 CustTypeDescr from tCtrlExpr a " +
				"left join tCustType b on a.CustType=b.Code where 1=1 ";

		if (ctrlExpr.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(ctrlExpr.getPk());
		}
		if (StringUtils.isNotBlank(ctrlExpr.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(ctrlExpr.getCustType());
		}
		if (ctrlExpr.getBeginDate() != null) {
			sql += " and a.BeginDate=? ";
			list.add(ctrlExpr.getBeginDate());
		}
		if (ctrlExpr.getEndDate() != null) {
			sql += " and a.EndDate=? ";
			list.add(ctrlExpr.getEndDate());
		}
		if (StringUtils.isNotBlank(ctrlExpr.getCtrlExpr())) {
			sql += " and a.CtrlExpr=? ";
			list.add(ctrlExpr.getCtrlExpr());
		}
		if (StringUtils.isNotBlank(ctrlExpr.getSetCtrlExpr())) {
			sql += " and a.SetCtrlExpr=? ";
			list.add(ctrlExpr.getSetCtrlExpr());
		}
		if (StringUtils.isNotBlank(ctrlExpr.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(ctrlExpr.getLastUpdatedBy());
		}
		if (ctrlExpr.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(ctrlExpr.getLastUpdate());
		}
		if (StringUtils.isBlank(ctrlExpr.getExpired())
				|| "F".equals(ctrlExpr.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(ctrlExpr.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(ctrlExpr.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.Pk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}
