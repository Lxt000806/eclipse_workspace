package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;

@SuppressWarnings("serial")
@Repository
public class WorkCostDetailDao extends BaseDao {

	/**
	 * 基础人工成本明细表
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select *,a.custCtrl-a.CustCost LeaveCustCost,a.AllCustCtrl - a.AllCustCost AllLeaveCustCost," +
				" round(a.custCtrl* isnull(a.CtrlPer,1.1), 2) CustCtrl_Kzx from (select t.PK,t.No,t.CustCode,t1.status custStatus,t1.CheckStatus,t1.DocumentNo,t1.Descr CustDescr,t1.Address,t1.SignDate,t.ApplyMan,e3.NameChi ApplyManDescr,"
				+ "t.SalaryType,t2.Descr SalaryTypeDescr,t3.WorkType1,t6.Descr WorkType1Descr,t.LastUpdate,t.LastUpdatedBy,t.CardID2,"
				+ "t.WorkType2,t3.Descr WorkType2Descr,t.AppAmount,t.Remarks,t.ActName,t.CardID,t4.IBM IsWithHold,t4.NOTE IsWithHoldDescr,t.WithHoldNo,t8.Note IsSignDescr,t.IsConfirm,"
				+ "t.ConfirmAmount,t.ConfirmRemark,t.Status,t5.NOTE statusdescr,t.Expired,t.ActionLog,t.RefPrePk,t7.Note CheckStatusDescr,t1.iswateritemctrl,"
				+ "isnull(t.QualityFee,0) QualityFee,isnull(t.ConfirmAmount,0)-isnull(t.QualityFee,0) RealAmount,t.WorkerCode,t.FaultType,t.FaultMan,case when t.FaultType='1' then t13.NameChi when t.FaultType='2' then t16.NameChi else '' end FaultManDescr,"
				+ "t14.NOTE FaultTypeDescr,isnull(t15.QualityFee,0) PrjQualityFee,t10.ProjectMan RefProjectMan,t17.NameChi RefProjectManDescr,"
				+ "case when wcq.WorkType12 is not null then isnull(wcq.QualityFeeBegin,0) else isnull(twt.QualityFeeBegin,0) end QualityFeeBegin,t9.note iswateritemctrldescr," 
				+ "case when t3.SalaryCtrlType='0' then isnull(de12.workerPlanOffer,0) else isnull(de2.workerPlanOffer,0) end workerPlanOffer, "
				+ "t.refCustCode,t.RefFixDutyManPk,t10.Descr refCustDescr,t10.address refAddress,tw.IdNum,t3.worktype12,twcc.CtrlPer,tw.NameChi3,tw.NameChi4,tw.CardId3,tw.CardId4,t.WorkCostImportItemNo, "
				+ "t12.Desc1 CustTypeDescr ";
		if (!"view".equals(workCostDetail.getButton()) && !"sign".equals(workCostDetail.getButton())) {//查看和出纳签字时不查询这些字段
			sql += ",isnull(dbo.fGetCustBaseCtrl_Com(t.CustCode,1,t.WorkType2),0) CustCtrl,isnull(dbo.fGetCustBaseCost_Com(t.CustCode,1,t.WorkType2),0) CustCost,"
					+ "isnull(dbo.fGetCustBaseCtrl_Com(t.CustCode,3,''),0) AllCustCtrl,isnull(dbo.fGetCustBaseCost_Com(t.CustCode,3,''),0) AllCustCost, "
					+ "t11.hasPlan,t12.Type custTypeType ";
		}else{
			sql+=",0 CustCtrl,0 CustCost,0 AllCustCost,0 AllCustCtrl ";
		}
		sql += "from tWorkCostDetail t "
				+ "left outer join tCustomer t1 on t1.Code=t.CustCode "
				+ "left outer join tSalaryType t2 on t2.Code=t.SalaryType "
				+ "left outer join tWorkType2 t3 on t3.Code=t.WorkType2 "
				+ "left outer join tWorkType1 t6 on t6.Code=t3.WorkType1 "
				+ "left outer join tXTDM t4 on t4.IBM=t.IsWithHold and t4.ID = 'YESNO' "
				+ "left outer join tXTDM t5 on t5.IBM=t.Status and t5.ID = 'WorkCostStatus'"
				+ "left outer join tXTDM t7 on t7.IBM=t1.CheckStatus and t7.ID = 'CheckStatus'"
				+ "left outer join tXTDM t9 on t9.IBM=t1.iswateritemctrl and t9.ID = 'YESNO'"
				+ "left outer join tEmployee e3 on t.ApplyMan=e3.Number "
				+ "left outer join tWorker tw on tw.Code=t.WorkerCode "
				+ "left outer join tWorkType12 twt on twt.Code=tw.WorkType12 "
				+ "left outer join tWorkCsfQuality wcq on wcq.WorkType12=tw.WorkType12 and tw.WorkerClassify=wcq.WorkerClassify "
				+ "left outer join tXTDM t8 on t8.IBM=tw.IsSign and t8.ID = 'WSIGNTYPE'"
				+ "left outer join tWorkCostCtrl twcc on twcc.WorkType2=t.WorkType2 and twcc.IsSign=tw.IsSign "
				+ "left outer join tCustomer t10 on t10.Code=t.RefCustCode "
				+ "left outer join (select 1 hasPlan,CustCode from tBaseCheckItemPlan group by CustCode) t11 on t.CustCode=t11.CustCode "
				+ "left outer join tCustType t12 on t12.Code=t1.CustType "
				+ "left outer join tEmployee t13 on t.FaultMan=t13.Number "
				+ "left outer join tXTDM t14 on t.FaultType=t14.IBM and t14.ID='FAULTTYPE' "
				+ "left outer join tWorker t15 on t13.Number=t15.EmpCode "
				+ "left outer join tWorker t16 on t.FaultMan=t16.Code "
				+ "left outer join tEmployee t17 on t10.ProjectMan=t17.Number "
				+ "left outer join (  SELECT CustCode,SUM(a.offerpri*qty) workerPlanOffer,b.worktype12  "
				+ "	 from tBaseCheckItemPlan a,   tBaseCheckItem b   WHERE a.BaseCheckItemCode=b.Code "
				+ "GROUP BY Custcode,b.worktype12) de12  ON de12.custcode=t1.code and t3.worktype12=de12.worktype12 "
				+ "left outer join (select isnull(sum(isnull(a.Qty, 0) * isnull(a.OfferPri, 0)),0) workerPlanOffer,a.CustCode, e.OfferWorkType2 "
				+ "from tBaseCheckItemPlan a "
				+ "left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "  
				+ "left join tBaseItemType2 e on b.BaseItemType2=e.Code "
				+ "group by a.CustCode, e.OfferWorkType2) de2 on t.CustCode=de2.CustCode and de2.OfferWorkType2=t.WorkType2 "
				+ "where 1=1 and t.No=? ) a order by a.DocumentNo ";
		list.add(workCostDetail.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 按账号汇总
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> findCardListBySql(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.CardID,isnull(a.CardID2,'') CardID2,a.ActName,sum(a.ConfirmAmount) ConfirmAmount,sum(a.QualityFee) QualityFee, "
				+ "sum(a.ConfirmAmount-a.QualityFee) RealAmount,b.CardId3,b.CardId4,b.NameChi3,b.NameChi4 "
				+ "from tWorkCostDetail a "
				+ "left join tWorker b on a.WorkerCode=b.Code "
				+ "where 1=1 and Status='2' and No=? "
				+ "group by a.ActName,a.CardID,isnull(a.CardID2,''),b.CardId3,b.CardId4,b.NameChi3,b.NameChi4  ";
		list.add(workCostDetail.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 统计单项工种余额、单项工种发包、单项工种累计支出、总发包余额、总发包、总支出
	 * 
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public Map<String, Object> findCostByCodeWork(String custCode,
			String workType2) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select isnull(dbo.fGetCustBaseCtrl_Com(?,3,''),0) AllCustCtrl,isnull(dbo.fGetCustBaseCost_Com(?,3,''),0) AllCustCost,"
				+ "(isnull(dbo.fGetCustBaseCtrl_Com(?,3,''),0)-isnull(dbo.fGetCustBaseCost_Com(?,3,''),0)) AllLeaveCustCost";
		if (StringUtils.isNotBlank(workType2)) {
			sql += ",isnull(dbo.fGetCustBaseCtrl_Com(?,1,?),0) CustCtrl,isnull(dbo.fGetCustBaseCost_Com(?,1,?),0) CustCost,"
					+ "(isnull(dbo.fGetCustBaseCtrl_Com(?,1,?),0)-isnull(dbo.fGetCustBaseCost_Com(?,1,?),0)) LeaveCustCost";
			list = this.findListBySql(sql, new Object[] { custCode, custCode,
					custCode, custCode, custCode, workType2, custCode,
					workType2, custCode, workType2, custCode, workType2 });
		} else {
			list = this.findListBySql(sql, new Object[] { custCode, custCode,
					custCode, custCode });
		}
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 查询prj相关
	 * 
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public Map<String, Object> findPrjByCodeWork(String custCode,
			String workType2) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select b.PrjItem,c.NOTE PrjItemDescr,b.EndDate,"
				+ " b.ConfirmCZY,d.ZWXM ConfirmCZYDescr,b.ConfirmDate "
				+ " from tWorkType2 a"
				+ " left join tPrjProg b on a.PrjItem=b.PrjItem and b.CustCode=?"
				+ " left join tXTDM c on a.PrjItem=c.CBM and c.ID='PrjItem'"
				+ " left join tCZYBM d on b.ConfirmCZY=d.CZYBH"
				+ " where a.Code=?";
		list = this.findListBySql(sql, new Object[] { custCode, workType2 });
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 统计合同总价
	 * 
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public Map<String, Object> findTotalAcountByCodeWork(String custCode,
			String workType2) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select Amount from tWorkCon where CustCode =? and WorkType2=?";
		list = this.findListBySql(sql, new Object[] { custCode, workType2 });
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 统计已领工资
	 * 
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public Map<String, Object> findGotAcountByCodeWork(String custCode,
			String workType2) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select sum(ConfirmAmount) ConfirmAmount from tWorkCostDetail where Status = '2' and CustCode =? and WorkType2=?";
		list = this.findListBySql(sql, new Object[] { custCode, workType2 });
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 预扣金额计算
	 * 
	 * @param workCostDetail
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> findYukou(WorkCostDetail workCostDetail) {
		Assert.notNull(workCostDetail);
		Connection conn = null;
		CallableStatement call = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pZcrgcbmx_withHoldAmount(?,?,?,?,?)}");// pGdxj_fx
			// 工地巡检分析
			call.setInt(1, workCostDetail.getWithHoldNo());
			call.setInt(2,
					workCostDetail.getPk() == null ? 0 : workCostDetail.getPk());
			call.setString(3, workCostDetail.getWorkCostDetailJson());
			call.registerOutParameter(4, Types.DOUBLE);
			call.registerOutParameter(5, Types.DOUBLE);
			call.execute();
			map.put("ret", call.getDouble("ret") + "");
			map.put("Amount", call.getDouble("Amount") + "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return map;
	}

	/**
	 * 从id对应的表根据code查descr
	 * 
	 * @param value
	 * @param id
	 * @return
	 */
	public Map<String, Object> findDescr(String value, String id) {

		String sql = "select Descr from t" + id + " where Code=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { value });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * 满足查询时间的防水工资及补贴列表
	 * 
	 * @param year
	 * @param month
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> findSubsidyByDate(String year,
			String month, WorkCostDetail workCostDetail) {
		Date startDate = DateUtil.startOfAMonth(new Integer(year).intValue(),
				new Integer(month).intValue() - 1);
		Date endDate = DateUtil.endOfAMonth(new Integer(year).intValue(),
				new Integer(month).intValue() - 1);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pWorkCost_fs(?,?,?,?,?,?)}");
			call.setString(1, workCostDetail.getCustCodes());
			call.setTimestamp(2, startDate==null?null:new Timestamp(startDate.getTime()));
			call.setTimestamp(3, endDate==null?null:new Timestamp(endDate.getTime()));
			call.setString(4, workCostDetail.getLastUpdatedBy());
			call.setString(5, workCostDetail.getIsSalary());
			call.setString(6, workCostDetail.getIsFacingSubsidy());
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}

	/**
	 * 基础人工成本明细分页查询
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql2(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select t.lastupdate,t.PK,t.CustCode,t1.Descr CustCodeDescr,t1.Address,t.ApplyMan,e1.NameChi ApplyManDescr,t.Status,t5.NOTE StatusDescr,t.SalaryType,t2.Descr SalaryTypeDescr, "
				+ " t3.WorkType1,t4.Descr WorkType1Descr,t.WorkType2,t3.Descr WorkType2Descr,t.AppAmount,t.ConfirmAmount, "
				+ " t0.No,t0.DocumentNo,t0.ConfirmCZY,e2.NameChi ConfirmCZYDescr,t0.ConfirmDate,t.ConfirmRemark,t0.Date, "
				+ " t.ActName,t0.PayDate ,dp.desc1 Department2descr,t.Remarks,t6.Note IsSignDescr,t.QualityFee,t1.iswateritemctrl,"
				+ " t.ConfirmAmount-t.QualityFee RealAmount,ct.Address refAddress,tc.Desc2 CmpDescr "
				+ " from tWorkCostDetail t  "
				+ " left outer join tWorkCost t0 on t0.No=t.No  "
				+ " left outer join tWorker tw on tw.Code=t.WorkerCode  "
				+ " left outer join tCustomer t1 on t1.Code=t.CustCode  "
				+ " left outer join tSalaryType t2 on t2.Code=t.SalaryType "
				+ " left outer join tWorkType2 t3 on t3.Code=t.WorkType2 "
				+ " left outer join tWorkType1 t4 on t4.Code=t3.WorkType1 "
				+ " left outer join tXTDM t5 on t5.IBM=t.Status and t5.ID = 'WorkCostStatus' "
				+ " left outer join tXTDM t6 on t6.IBM=tw.IsSign and t6.ID ='YESNO' "
				+ " left outer join tEmployee e1 on t.ApplyMan=e1.Number "
				+ " left outer join tEmployee e2 on t0.ConfirmCZY=e2.Number "
				+ " left outer join tCustomer ct on t.RefCustCode=ct.Code "
				+ " left outer join tBuilder m on t1.BuilderCode=m.Code "
				+ " left outer join tRegion tr on tr.code=m.regioncode " 
				+ " left outer join tCompany tc on tc.code=tr.cmpCode " 
				+ " left outer join tDepartment2 dp on dp.code=e1.Department2 where 1=1 ";
		if (StringUtils.isNotBlank(workCostDetail.getAddress())) {
			sql += " and t1.address like ?  ";
			list.add("%" + workCostDetail.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(workCostDetail.getRefAddress())) {
			sql += " and ct.address like ?  ";
			list.add("%" + workCostDetail.getRefAddress() + "%");
		}
		if (StringUtils.isNotBlank(workCostDetail.getApplyMan())) {
			sql += " and t.ApplyMan = ? ";
			list.add(workCostDetail.getApplyMan());
		}
		if (StringUtils.isNotBlank(workCostDetail.getSalaryType())) {
			sql += " and t.salaryType = ? ";
			list.add(workCostDetail.getSalaryType());
		}
		if (StringUtils.isNotBlank(workCostDetail.getRemarks())) {
			sql += " and t.remarks like ? ";
			list.add("%" + workCostDetail.getRemarks() + "%");
		}
		if (StringUtils.isNotBlank(workCostDetail.getWorkType1())) {
			sql += " and t3.workType1 = ? ";
			list.add(workCostDetail.getWorkType1());
		}
		if (StringUtils.isNotBlank(workCostDetail.getWorkType2())) {
			sql += " and t.WorkType2 = ? ";
			list.add(workCostDetail.getWorkType2());
		}
		if (StringUtils.isNotBlank(workCostDetail.getActName())) {
			sql += " and t.ActName = ? ";
			list.add(workCostDetail.getActName());
		}
		if (StringUtils.isNotBlank(workCostDetail.getStatus())) {
			// sql += " and t.Status = ? ";
			// list.add(workCostDetail.getStatus());
			sql += " and t.Status in " + "('"+workCostDetail.getStatus().replaceAll(",", "','")+"')";
		}
		if (workCostDetail.getDateFrom() != null) {
			sql += " and t0.Date>= ? ";
			list.add(workCostDetail.getDateFrom());
		}
		if (workCostDetail.getDateTo() != null) {
			sql += " and t0.Date< ? ";
			list.add(DateUtil.addInteger(workCostDetail.getDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(workCostDetail.getWorkerCode())) {
			sql += " and t.WorkerCode = ? ";
			list.add(workCostDetail.getWorkerCode());
		}
		if (StringUtils.isNotBlank(workCostDetail.getNo())) {
			sql += " and t0.No like ? ";
			list.add("%" + workCostDetail.getNo() + "%");
		}
		if (StringUtils.isNotBlank(workCostDetail.getDocumentNo())) {
			sql += " and t0.DocumentNo like ? ";
			list.add("%" + workCostDetail.getDocumentNo() + "%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 基础人工成本明细--取消
	 * 
	 * @param pk
	 * @param lastUpdatedBy
	 * @param confirmRemark
	 * @param refPrePk
	 */
	public void cancel(String pk, String lastUpdatedBy, String confirmRemark,
			String refPrePk) {
		String sql = " update tWorkCostDetail set Status ='3',ConfirmAmount=0,QualityFee=0,ConfirmRemark=?,LastUpdate=getDate(),LastUpdatedBy=?,ActionLog='EDIT' where PK=?  "
				+ "update tPreWorkCostDetail set Status = '8',LastUpdate=getDate(),LastUpdatedBy=?,ActionLog='EDIT' where PK=?";
		this.executeUpdateBySql(sql, new Object[] { confirmRemark,
				lastUpdatedBy, pk, lastUpdatedBy, refPrePk });
	}

	/**
	 * 基础人工成本明细--恢复
	 * 
	 * @param pk
	 * @param lastUpdatedBy
	 * @param confirmRemark
	 * @param refPrePk
	 */
	public void renew(String pk, String lastUpdatedBy, String confirmRemark,
			String refPrePk) {
		String sql = " update tWorkCostDetail set Status ='1',ConfirmAmount=AppAmount,LastUpdate=getDate(),LastUpdatedBy=?,ActionLog='EDIT' where PK=?  "
				+ "update tPreWorkCostDetail set Status = '5',LastUpdate=getDate(),LastUpdatedBy=?,ActionLog='EDIT' where PK=?";
		this.executeUpdateBySql(sql, new Object[] { lastUpdatedBy, pk,
				lastUpdatedBy, refPrePk });
	}

	/**
	 * 查询状态为3取消的明细
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> hasStatus3(WorkCostDetail workCostDetail) {
		String sql = "select * from tWorkCostDetail t where 1=1 and t.No=? and t.status='3'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { workCostDetail.getNo() });
		return list;
	}

	/**
	 * 是否允许进行非预扣的工人工资审批
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> isAllowConfirm(
			WorkCostDetail workCostDetail) {
		String sql = "select DISTINCT t1.address "
				+ "from tWorkCostDetail t "
				+ "left outer join tCustomer t1 on t1.Code=t.CustCode "
				+ "where 1=1 and t.IsWithHold='0' and t.No=? and t.status='1' and (t1.CheckStatus='3' or t1.CheckStatus='4')";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { workCostDetail.getNo() });
		return list;
	}

	/**
	 * 质保金超出的工人
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> overQualityFeeWorker(
			WorkCostDetail workCostDetail) {
		Assert.notNull(workCostDetail);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetQualityFee(?)}");
			call.setString(1, workCostDetail.getNo());
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}

	/**
	 * 优秀班组奖励累计总额超过单项人工发包额的10%
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> overCustCtrl(WorkCostDetail workCostDetail) {
		String sql = "select a.CustCode,a.WorkType2,c.Address,c.DocumentNo,wt1.Descr WorkType1Descr, wt2.Descr WorkType2Descr, "
				+ "isnull(a.ConfirmAmount,0)+isnull(b.ConfirmAmount,0) ret1,isnull(dbo.fGetCustBaseCtrl(a.CustCode, 1, a.WorkType2), 0) ret2 "
				+ "from (select * FROM tWorkCostDetail WHERE no=?) a "
				+ "left outer join ( "
				+ "select no,CustCode,WorkType2,SalaryType,sum(ConfirmAmount) ConfirmAmount  "
				+ "from tWorkCostDetail where Status='2' "
				+ "group by CustCode,WorkType2,SalaryType,no "
				+ ") b on a.CustCode=b.CustCode and a.WorkType2=b.WorkType2 and a.SalaryType=b.SalaryType "
				+ "left outer join tCustomer c on a.CustCode=c.Code "
				+ "left outer join tWorkType2 wt2 on wt2.Code = a.WorkType2 "
				+ "left outer join tWorkType1 wt1 on wt1.Code = wt2.WorkType1 "
				+ "where a.SalaryType='05' and a.Status<>'3'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { workCostDetail.getNo() });
		return list;
	}

	/**
	 * 预扣领取金额超过 预扣金额-预扣已发放金额
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> overWithHold(WorkCostDetail workCostDetail) {
		String sql = "		select c.Address,c.DocumentNo,wt1.Descr WorkType1Descr, wt2.Descr WorkType2Descr from "
				+ "(select * FROM tWorkCostDetail WHERE no=? ) a "
				+ "inner join tPrjWithHold b on a.WithHoldNo=b.PK "
				+ "left join ( "
				+ "select sum(ConfirmAmount) ConfirmAmount,a.WithHoldNo from tWorkCostDetail a "
				+ "inner join tWorkCost b on b.No=a.No "
				+ "where a.WithHoldNo>0 and b.Status='2' group by a.WithHoldNo "
				+ ") d on a.WithHoldNo=d.WithHoldNo "
				+ "left outer join tCustomer c on a.CustCode=c.Code "
				+ "left outer join tWorkType2 wt2 on wt2.Code = a.WorkType2 "
				+ "left outer join tWorkType1 wt1 on wt1.Code = wt2.WorkType1 "
				+ "left outer join ( "
				+ "select s.WithHoldNo,sum(s.ConfirmAmount) ConfirmAmount from "
				+ "(select * FROM tWorkCostDetail WHERE no=?)s group by WithHoldNo "
				+ ") e on a.WithHoldNo=e.WithHoldNo "
				+ "where e.ConfirmAmount>b.Amount-isnull(d.ConfirmAmount,0)";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {
				workCostDetail.getNo(), workCostDetail.getNo() });
		return list;
	}

	/**
	 * 是否福州
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isFz() {
		String sql = "select 1 from tXTCS where ID='CmpnyCode' and QZ='01'";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {});
		return list;
	}

	// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//	/**
//	 * 是否有新增管理权限
//	 * 
//	 * @param workCostDetail
//	 * @return
//	 */
//	public List<Map<String, Object>> hasAddManageRight(
//			WorkCostDetail workCostDetail) {
//		String sql = "select * from tCZYGNQX where MKDM='0316' and GNMC='新增管理' and CZYBH=?";
//		List<Map<String, Object>> list = this.findBySql(sql,
//				new Object[] { workCostDetail.getLastUpdatedBy() });
//		return list;
//	}

	/**
	 * 该卡号是否已申请过其他工种类型2的工资
	 * 
	 * @param workCostDetail
	 * @return workType2
	 */
	public String isMultiWorkType1(WorkCostDetail workCostDetail) {
		Assert.notNull(workCostDetail);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pZcrgcbmx_khsfsq(?,?,?)}");
			call.setString(1, workCostDetail.getCardId());
			call.setString(2, workCostDetail.getNo());
			call.setString(3, workCostDetail.getWorkCostDetailJson());
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			if (list.size() != 0 && list != null) {
				if (list.get(0).get("worktype1") != null)
					return list.get(0).get("worktype1").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}

	/**
	 * 工资类型1
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findWorkType1(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code id,a.Descr name from tWorkType1 a where Expired='F'  ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and a.code not  in (" + param.get("pCode") + ") ";
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 工资类型2
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findWorkType2(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code id,a.Descr name  from tWorkType2 a where a.expired='F' and CalType='1' ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and a.workType1=?";
			list.add((String) param.get("pCode"));
		}
		sql += " order by a.Code  ";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 基础人工成本明细--审核
	 * 
	 * @param pk
	 * @param col
	 * @param value
	 */
	public void doCheck(String pk, String col, String value) {
		String sql = " update tWorkCostDetail set " + col + "=? where pk=?";
		this.executeUpdateBySql(sql, new Object[] { value, pk });
	}
	
	/**
	 * 生成水电材料奖惩
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> createWaterItem(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pZcrgcbmx_waterItem ? ";
		list.add(workCostDetail.getWorkCostDetailJson());
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}
	/**
	 * 是否已生成水电材料奖惩
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> isCreatedWaterItem( WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pZcrgcbmx_waterItem ? ";
		list.add(workCostDetail.getWorkCostDetailJson());
		return findListBySql(sql, list.toArray());
	}
	/**
	 * 定额工资明细
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goDeByWt12JqGrid(Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.OfferPri,d.Descr UomDescr, isnull(a.Qty, 0) * isnull(a.OfferPri, 0) TotalPrice, "
				+"b.Descr BaseItemDescr, c.Descr FixAreaDescr, a.Qty "
				+"from tBaseCheckItemPlan a "
				+"left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code  "
				+"left join tFixArea c on c.PK = a.FixAreaPK "
				+"left join tUOM d on d.Code = b.Uom "
				+"where a.CustCode=? and b.worktype12  =(select WorkType12 from tWorkType2 where Code=?)";
		list.add(workCostDetail.getCustCode());
		list.add(workCostDetail.getWorkType2());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 定额工资明细
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goDeByWt2JqGrid(Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.OfferPri,d.Descr UomDescr, isnull(a.Qty, 0) * isnull(a.OfferPri, 0) TotalPrice, "
				+"b.Descr BaseItemDescr, c.Descr FixAreaDescr, a.Qty "
				+"from tBaseCheckItemPlan a "
				+"left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "  
				+"left join tBaseItemType2 e on b.BaseItemType2=e.Code "
				+"left join tFixArea c on c.PK = a.FixAreaPK " 
				+"left join tUOM d on d.Code = b.Uom " 
				+"where a.CustCode=? and e.OfferWorkType2=?";
		list.add(workCostDetail.getCustCode());
		list.add(workCostDetail.getWorkType2());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 批量生成申请明细
	 * 
	 * @param year
	 * @param month
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> findBatchCrtDetailByDate(String year,
			String month, WorkCostDetail workCostDetail) {
		Date startDate = DateUtil.startOfAMonth(new Integer(year).intValue(),
				new Integer(month).intValue() - 1);
		Date endDate = DateUtil.endOfAMonth(new Integer(year).intValue(),
				new Integer(month).intValue() - 1);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBatchCrtDetail(?,?,?,?)}");
			call.setString(1, workCostDetail.getWorkCostImportItemNo());
			call.setTimestamp(2, startDate==null?null:new Timestamp(startDate.getTime()));
			call.setTimestamp(3, endDate==null?null:new Timestamp(endDate.getTime()));
			call.setString(4, workCostDetail.getWorkCostDetailJson());
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	/**
	 * 班组成员工资明细
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goMemberJqGrid(
			Page<Map<String, Object>> page, WorkCostDetail workCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.NameChi,a.IDNum,a.CardID,a.Bank,Amount,isnull(CashAmount,0)CashAmount,isnull(CmpAmount,0)CmpAmount, " 
				+"b.Descr LaborCmpDescr,c.NameChi GroupLeaderName,d.Descr WorkType12Descr,a.WorkType12,a.WorkerMemCode Code,a.LaborCmpCode, " 
				+"isnull(e.ThisYearProvidedAmount,0)ThisYearProvidedAmount ,cast((select QZ from tXTCS where ID='WORKPROVIDE')as money)-isnull(e.ThisYearProvidedAmount,0) ThisYearUnProvideAmount," 
				+"isnull(f.ThisMonProvidedAmount,0)ThisMonProvidedAmount,a.WorkerCode "
				+"from tWorkerCostProvide a  " 
				+"left join tLaborCompny b on a.LaborCmpCode=b.Code " 
				+"left join tWorker c on a.WorkerCode=c.Code " 
				+"left join tWorkType12 d on a.WorkType12=d.Code " 
				+"left join ( " 
				+"	select sum(CmpAmount) ThisYearProvidedAmount,in_a.WorkerMemCode,in_a.LaborCmpCode " 
				+"	from tWorkerCostProvide in_a  " 
				+"  inner join tWorkCost in_b on in_a.WorkCostNo=in_b.No "
				+"	where in_b.PayDate>=dateadd(yy,datediff(yy,0,getdate()),0) and in_b.PayDate<=getdate()  and in_b.Status='2'" 
				+"	group by in_a.WorkerMemCode,in_a.LaborCmpCode " 
				+")e on a.WorkerMemCode=e.WorkerMemCode and a.LaborCmpCode=e.LaborCmpCode " 
				+"left join ( " 
				+"	select sum(CmpAmount) ThisMonProvidedAmount,in_a.WorkerMemCode,in_a.LaborCmpCode " 
				+"	from tWorkerCostProvide in_a  " 
				+"  inner join tWorkCost in_b on in_a.WorkCostNo=in_b.No "
				+"	where in_b.PayDate>=dateadd(mm,datediff(mm,0,getdate()),0) and in_b.PayDate<=getdate()  and in_b.Status='2'" 
				+"	group by in_a.WorkerMemCode,in_a.LaborCmpCode " 
				+")f on a.WorkerMemCode=f.WorkerMemCode and a.LaborCmpCode=f.LaborCmpCode "
				+"where a.WorkCostNo=? ";
		list.add(workCostDetail.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 生成班组成员工资明细
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> getMemberSalary( WorkCostDetail workCostDetail) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCrtMemberSalary(?)}");
			call.setString(1, workCostDetail.getNo());
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil.resultSetToList(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	
	/**
	 * 工资出账处理保存
	 * @param workCostDetail
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doWorkerCostProvide (WorkCostDetail workCostDetail) {
		Assert.notNull(workCostDetail);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pWorkerCostProvide(?,?,?,?,?)}");
			call.setString(1, workCostDetail.getNo());
			call.setString(2, workCostDetail.getLastUpdatedBy());
			call.setString(3, workCostDetail.getWorkCostDetailJson());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 出账查询
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goCheckOutJqGrid(Page<Map<String, Object>> page, WorkCost workCost) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from (";
		if("1".equals(workCost.getType())){
			sql+="select a.NameChi WorkerMemName,a.Bank,a.IDNum,a.CardID,b.LaborCmpCode, "
				+"c.Descr LaborCmpDescr,sum(a.CashAmount)CashAmount,sum(a.CmpAmount)CmpAmount," 
				+"b.NameChi GroupLeaderName,e.Descr WorkType12Descr "
				+"from tWorkerCostProvide a  "
				+"left join tWorker b on a.WorkerCode=b.Code  "
				+"left join tLaborCompny c on a.LaborCmpCode=c.Code "
				+"left join tWorkCost d on a.WorkCostNo=d.No "
				+"left join tWorkType12 e on b.WorkType12=e.Code ";
		}else{
			sql+="select a.NameChi WorkerMemName,a.Bank,a.IDNum,a.CardID,b.LaborCmpCode,b.NameChi GroupLeaderName, "
				+"c.Descr LaborCmpDescr,a.CashAmount,a.CmpAmount,d.DocumentNo,d.ConfirmDate,d.PayDate,d.No," 
				+"e.Descr WorkType12Descr "
				+"from tWorkerCostProvide a  "
				+"left join tWorker b on a.WorkerCode=b.Code  "
				+"left join tLaborCompny c on a.LaborCmpCode=c.Code "
				+"left join tWorkCost d on a.WorkCostNo=d.No "
				+"left join tWorkType12 e on b.WorkType12=e.Code ";
		}
		sql+="where 1=1 ";
		if (StringUtils.isNotBlank(workCost.getNo())) {
			sql += " and d.No like ? ";
			list.add("%" + workCost.getNo() + "%");
		}
		if (StringUtils.isNotBlank(workCost.getDocumentNo())) {
			sql += " and d.DocumentNo like ? ";
			list.add("%" + workCost.getDocumentNo() + "%");
		}
		if (StringUtils.isNotBlank(workCost.getLaborCmpCode())) {
			sql += " and a.LaborCmpCode = ? ";
			list.add(workCost.getLaborCmpCode());
		}
		if (workCost.getPayDateFrom() != null){
			sql += " and d.PayDate>= ? ";
			list.add(workCost.getPayDateFrom());
		}
		if (workCost.getPayDateTo() != null){
			sql += " and d.PayDate< ? ";
			list.add(DateUtil.addInteger(workCost.getPayDateTo(),
					Calendar.DATE, 1));
		}
		if (workCost.getConfirmDateFrom() != null){
			sql += " and d.ConfirmDate>= ? ";
			list.add(workCost.getConfirmDateFrom());
		}
		if (workCost.getConfirmDateTo() != null){
			sql += " and d.ConfirmDate< ? ";
			list.add(DateUtil.addInteger(workCost.getConfirmDateTo(),
					Calendar.DATE, 1));
		}
		if("1".equals(workCost.getType())){
			sql+="group by a.NameChi,a.Bank,a.IDNum,a.CardID,b.LaborCmpCode,c.Descr,b.NameChi,e.Descr";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a ";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 劳务分包公司
	 * @param cutCheckOut
	 * @return
	 */
	public List<Map<String, Object>> getLaborCompny() {
		String sql = "select a.Code ,a.Descr from tLaborCompny a where a.Expired='F' ";
		List<Map<String, Object>> list = this.findListBySql(sql,
				new Object[] { });
		return list;
	}
}
