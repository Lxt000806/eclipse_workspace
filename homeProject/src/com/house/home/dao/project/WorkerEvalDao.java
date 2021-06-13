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
import com.house.home.entity.project.WorkerEval;

@SuppressWarnings("serial")
@Repository
public class WorkerEvalDao extends BaseDao {

	/*
	 * 工地工人评价
	 * */

	public Page<Map<String,Object>>  getWorkerEvalList(
			Page<Map<String, Object>> page,WorkerEvalListEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from tWorkerEval where CustWkPk= ?";
		list.add(evt.getCustWkPk());
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(WorkerEval workerEval) {
		Assert.notNull(workerEval);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall(
				"{Call pWorkerEval(?,?,?,?,?,?,?,?,?,?,?)}");/*9个?*/
			call.setString(1,workerEval.getCustCode());
			call.setString(2,workerEval.getWorkerCode());
			call.setInt(3,workerEval.getCustWkPk());
			call.setString(4, workerEval.getType());
			call.setString(5,workerEval.getEvaMan());
			call.setInt(6, workerEval.getScore());
			call.setString(7, workerEval.getExpired());
			call.setString(8,workerEval.getRemark());
			call.setString(9,workerEval.getM_umState());
			call.setInt(10,workerEval.getHealthScore()==null?0:workerEval.getHealthScore());
			call.setInt(11,workerEval.getToolScore()==null?0:workerEval.getToolScore());
			call.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description: 工人评价管理查询
	 * @author	created by zb
	 * @date	2018-11-14--上午10:03:15
	 * @param page
	 * @param workerEval
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkerEval workerEval) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(" +
				"select a.PK,a.CustCode,c.Descr CustName,a.WorkerCode,b.NameChi WorkerDescr,a.CustWkPk,a.Date,a.EvaMan, "+
				"e.NameChi EvaManDescr,a.Score,a.Type,tx.NOTE TypeDescr,a.LastUpdate,a.Expired,a.ActionLog, "+
				"a.Remark,a.EvalWorker,b2.NameChi EvalWorkerDescr,a.HealthScore,a.ToolScore,c.Address,f.WorkType12,g.Descr WorkType12Descr "+
				"from tWorkerEval a "+
				"left join tWorker b on b.Code = a.WorkerCode " +
				"left join tCustomer c on c.Code = a.CustCode "+
				"left join tEmployee e on e.Number = a.EvaMan "+
				"left join tXTDM tx on tx.CBM = a.Type and tx.ID = 'EVALSRC' "+
				"left join tWorker b2 on b2.Code = a.EvalWorker " +
				"left join tCustWorker f on a.CustWkPk=f.PK " +
				"left join tWorkType12 g on f.WorkType12=g.Code " +
				"where 1=1 ";
		if (null != workerEval.getPK()) {
			sql += " and a.PK = ? ";
			list.add(workerEval.getPK());
		}
		if (StringUtils.isNotBlank(workerEval.getWorkerCode())) {
			sql += " and a.WorkerCode = ? ";
			list.add(workerEval.getWorkerCode());
		}
		if (null != workerEval.getDateFrom()) {
			sql += " and a.Date >= ? ";
			list.add(workerEval.getDateFrom());
		}
		if (null != workerEval.getDateTo()) {
			sql += " and a.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(workerEval.getDateTo()).getTime()));
		}
		if (StringUtils.isBlank(workerEval.getExpired()) || "F".equals(workerEval.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(workerEval.getWorkType12())){
			sql += " and  f.WorkType12 in " + "('"+workerEval.getWorkType12().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
