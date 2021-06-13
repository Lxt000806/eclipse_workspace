package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;

import com.house.home.client.service.evt.WorkerEvalListEvt;
import com.house.home.entity.project.ConfExceptionLog;
import com.house.home.entity.project.WorkerEval;

@SuppressWarnings("serial")
@Repository
public class ConfExceptionLogDao extends BaseDao {

	/*
	 * 审核异常日志表
	 * */

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ConfExceptionLog confExceptionLog) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Address, c.Desc1 CustTypeDescr, xt.NOTE TypeDescr, a.RefNo, a.PayNum, a.BalanceAmount, "
					+" a.Cost, a.Remarks, a.LastUpdate, a.LastUpdatedBy "
					+" from  tConfExceptionLog a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tCustType c on b.CustType = c.Code "
					+" left join tXTDM xt on a.Type = xt.CBM and xt.ID = 'CONFEXCELOGTYPE' "
					+" where a.Expired = 'F' ";
		if (StringUtils.isNotBlank(confExceptionLog.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+ confExceptionLog.getAddress() +"%");
		}
		if (null != confExceptionLog.getDateFrom()) {
			sql += " and a.LastUpdate >= ? ";
			list.add(confExceptionLog.getDateFrom());
		}
		if (null != confExceptionLog.getDateTo()) {
			sql += " and a.LastUpdate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(confExceptionLog.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(confExceptionLog.getType())){
			sql += " and  a.Type in " + "('"+confExceptionLog.getType().replace(",", "','" )+ "')";
		}
		
		sql += "  order by a.LastUpdate desc ";
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}
