package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.WorkCost;

@SuppressWarnings("serial")
@Repository
public class WorkCostDao extends BaseDao {

	/**
	 * WorkCost分页信息
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCost workCost) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from( SELECT t.No,t.Type,t1.NOTE TypeDescr,t.AppCZY,e1.NameChi AppCZYDescr,t.Date,t.Remarks,t.Status," +
				"t2.NOTE StatusDescr, t.ConfirmCZY,e2.NameChi ConfirmCZYDescr,t.ConfirmDate,t.DocumentNo,t.PayCZY," +
				"e3.NameChi PayCZYDescr,t.PayDate,t.IsSysCrt,t3.NOTE IsSysCrtDescr,t.CommiNo,t.LastUpdate,t.LastUpdatedBy," +
				"t.Expired,t.ActionLog from tWorkCost t " +
				"left outer join tXTDM t1 on t1.IBM = t.Type and t1.ID = 'WorkCostType' " +
				"left outer join tXTDM t2 on t2.IBM = t.Status and t2.ID = 'WorkCostStatus' " +
				"left outer join tXTDM t3 on t3.IBM = t.IsSysCrt and t3.ID = 'YESNO' " +
				"left outer join tEmployee e1 on t.AppCZY=e1.Number " +
				"left outer join tEmployee e2 on t.ConfirmCZY=e2.Number " +
				"left outer join tEmployee e3 on t.PayCZY=e3.Number " +
				"WHERE 1=1";

    	if (StringUtils.isNotBlank(workCost.getNo())) {
			sql += " and t.No=? ";
			list.add(workCost.getNo());
		}
    	if (StringUtils.isNotBlank(workCost.getType())) {
			sql += " and t.Type=? ";
			list.add(workCost.getType());
		}
    	if (StringUtils.isNotBlank(workCost.getAppCzy())) {
			sql += " and t.AppCZY=? ";
			list.add(workCost.getAppCzy());
		}
    	if (workCost.getDateFrom() != null){
			sql += " and t.Date>= ? ";
			list.add(workCost.getDateFrom());
		}
		if (workCost.getDateTo() != null){
			sql += " and t.Date< ? ";
			list.add(DateUtil.addInteger(workCost.getDateTo(),
					Calendar.DATE, 1));
		}
		if (workCost.getPayDateFrom() != null){
			sql += " and t.PayDate>= ? ";
			list.add(workCost.getPayDateFrom());
		}
		if (workCost.getPayDateTo() != null){
			sql += " and t.PayDate< ? ";
			list.add(DateUtil.addInteger(workCost.getPayDateTo(),
					Calendar.DATE, 1));
		}
		if (workCost.getConfirmDateFrom() != null){
			sql += " and t.ConfirmDate>= ? ";
			list.add(workCost.getConfirmDateFrom());
		}
		if (workCost.getConfirmDateTo() != null){
			sql += " and t.ConfirmDate< ? ";
			list.add(DateUtil.addInteger(workCost.getConfirmDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(workCost.getStatus())) {
			sql += " and t.Status in (" + workCost.getStatus() + ")";
		}
    	if (StringUtils.isNotBlank(workCost.getRemarks())) {
			sql += " and t.Remarks=? ";
			list.add(workCost.getRemarks());
		}
    	if (StringUtils.isNotBlank(workCost.getConfirmCzy())) {
			sql += " and t.ConfirmCZY=? ";
			list.add(workCost.getConfirmCzy());
		}
    	if (workCost.getConfirmDate() != null) {
			sql += " and t.ConfirmDate=? ";
			list.add(workCost.getConfirmDate());
		}
    	if(StringUtils.isNotBlank(workCost.getDocumentNo())){	
			sql+=" and t.DocumentNo like ?  ";
			list.add("%"+workCost.getDocumentNo()+"%");
		}
    	if (StringUtils.isNotBlank(workCost.getPayCzy())) {
			sql += " and t.PayCZY=? ";
			list.add(workCost.getPayCzy());
		}
    	if (workCost.getPayDate() != null) {
			sql += " and t.PayDate=? ";
			list.add(workCost.getPayDate());
		}
    	if (StringUtils.isNotBlank(workCost.getIsSysCrt())) {
			sql += " and t.IsSysCrt=? ";
			list.add(workCost.getIsSysCrt());
		}
    	if (StringUtils.isNotBlank(workCost.getCommiNo())) {
			sql += " and t.CommiNo=? ";
			list.add(workCost.getCommiNo());
		}
    	if (workCost.getLastUpdate() != null) {
			sql += " and t.LastUpdate=? ";
			list.add(workCost.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(workCost.getLastUpdatedBy())) {
			sql += " and t.LastUpdatedBy=? ";
			list.add(workCost.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(workCost.getExpired()) || "F".equals(workCost.getExpired())) {
			sql += " and t.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(workCost.getActionLog())) {
			sql += " and t.ActionLog=? ";
			list.add(workCost.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")s order by s."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")s order by s.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 通过员工号查员工姓名
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public String findNameByEmnum(String emnum){
		String sql="select NameChi from tEmployee where Number=?";
		return this.findListBySql(sql, new Object[]{emnum}).get(0).get("NameChi").toString();
	}
	/**
	 * 人工成本汇总--选择账号
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql2(Page<Map<String,Object>> page, WorkCost workCost) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select t.No,t.Type,t1.NOTE TypeDescr,t.AppCZY,e1.NameChi AppCZYDescr,t.Date,t.Remarks,t.Status,t2.NOTE StatusDescr, "
                       +" t.ConfirmCZY,e2.NameChi ConfirmCZYDescr,t.ConfirmDate,t.DocumentNo,t.PayCZY,e3.NameChi PayCZYDescr,t.PayDate,t.IsSysCrt,t3.NOTE IsSysCrtDescr, "
                       +" t.CommiNo,t.LastUpdate,t.LastUpdatedBy,t.Expired,t.ActionLog  "
                       +" from tWorkCost t   "
                       +" left outer join tXTDM t1 on t1.IBM = t.Type and t1.ID = 'WorkCostType' "
                       +" left outer join tXTDM t2 on t2.IBM = t.Status and t2.ID = 'WorkCostStatus'  "
                       +" left outer join tXTDM t3 on t3.IBM = t.IsSysCrt and t3.ID = 'YESNO' "
                       +" left outer join tEmployee e1 on t.AppCZY=e1.Number  "
                       +" left outer join tEmployee e2 on t.ConfirmCZY=e2.Number  "
                       +" left outer join tEmployee e3 on t.PayCZY=e3.Number  "
                       +" where 1=1 and t.Status='2' ";
		if (workCost.getConfirmDateFrom() != null){
			sql += " and t.ConfirmDate>= ? ";
			list.add(workCost.getConfirmDateFrom());
		}
		if (workCost.getConfirmDateTo() != null){
			sql += " and t.ConfirmDate< ? ";
			list.add(DateUtil.addInteger(workCost.getConfirmDateTo(),
					Calendar.DATE, 1));
		}
    	if(StringUtils.isNotBlank(workCost.getDocumentNo())){	
			sql+=" and t.DocumentNo like ?  ";
			list.add("%"+workCost.getDocumentNo()+"%");
		}
    	if (StringUtils.isNotBlank(workCost.getType())) {
			sql += " and t.Type=? ";
			list.add(workCost.getType().trim());
		}
    	sql+=" order by t.LastUpdate desc";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 按账号汇总
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql3(Page<Map<String,Object>> page, WorkCost workCost) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.ActName,replace(b.CardID,' ','')CardID,sum(b.ConfirmAmount) ConfirmAmount,c.IDNum,d.Descr WorkType12Descr,sum(b.ConfirmAmount)-sum(b.QualityFee) ActuallySendAmount   " 
					   +" from (select t.No,t.Type,t1.NOTE TypeDescr,t.AppCZY,e1.NameChi AppCZYDescr,t.Date,t.Remarks,t.Status,t2.NOTE StatusDescr, "
                       +" t.ConfirmCZY,e2.NameChi ConfirmCZYDescr,t.ConfirmDate,t.DocumentNo,t.PayCZY,e3.NameChi PayCZYDescr,t.PayDate,t.IsSysCrt,t3.NOTE IsSysCrtDescr, "
                       +" t.CommiNo,t.LastUpdate,t.LastUpdatedBy,t.Expired,t.ActionLog"
                       +" from tWorkCost t   "
                       +" left outer join tXTDM t1 on t1.IBM = t.Type and t1.ID = 'WorkCostType' "
                       +" left outer join tXTDM t2 on t2.IBM = t.Status and t2.ID = 'WorkCostStatus'  "
                       +" left outer join tXTDM t3 on t3.IBM = t.IsSysCrt and t3.ID = 'YESNO' "
                       +" left outer join tEmployee e1 on t.AppCZY=e1.Number  "
                       +" left outer join tEmployee e2 on t.ConfirmCZY=e2.Number  "
                       +" left outer join tEmployee e3 on t.PayCZY=e3.Number  " 
                       +" where 1=1 and t.Status='2' ) a " 
                       +" inner join tWorkCostDetail b on a.no=b.no" 
                       +" inner join tWorker c on b.WorkerCode=c.Code "
                       +" inner join tWorkType12 d on c.WorkType12=d.Code"
                       +" where a.No in("+workCost.getNos()+") and b.Status='2' "
                       +" group by b.ActName,b.CardID,c.IDNum,d.Descr";
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 保存，审核，审核取消，反审核，出纳签字
	 * 
	 * @param workCostDetail
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(WorkCost workCost) {
		Assert.notNull(workCost);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pZcrycbgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, workCost.getM_umState());
			call.setString(2, workCost.getNo());	
			call.setString(3, workCost.getType());
			call.setString(4, workCost.getAppCzy());
			call.setTimestamp(5, workCost.getDate()==null?null:new Timestamp(workCost.getDate().getTime()));
			call.setString(6, workCost.getRemarks());
			call.setString(7, workCost.getStatus());
			call.setString(8, workCost.getConfirmCzy());
			call.setString(9, workCost.getDocumentNo());
			call.setString(10, workCost.getPayCzy());
			call.setTimestamp(11, workCost.getPayDate()==null?null:new Timestamp(workCost.getPayDate().getTime()));
			call.setString(12, workCost.getLastUpdatedBy());
			call.setString(13, workCost.getExpired());
			call.setString(14, workCost.getWorkCostDetailJson());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(15)));
			result.setInfo(call.getString(16));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	/**
	 * 导入定责工资查询
	 * @author	created by zb
	 * @date	2019-7-19--下午3:20:24
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String, Object>> goFixDutyJqGrid(
			Page<Map<String, Object>> page, FixDuty fixDuty) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.*,b.Descr WorkType2Descr,b.WorkType1,c.Descr WorkType1Descr from( "
					+ "select a.No FDNo,d.Address,a.CustCode,c.WorkType12,e.Descr WorkType12Descr, "
					+ "a.AppWorkerCode,f.NameChi AppWorkDescr,f.CardID,f.CardId2, "
					/* 直接用工人表（tWorker）的NameChi,该NameChi不会没有 modify by zb on 20200224
					+ "case when i.ActName is null or i.ActName='' then f.NameChi else i.ActName end ActName, "*/
					+ "f.NameChi ActName, "
					+ "a.DutyMan,g.NameChi DutyManDescr,a.DutyDate,b.FaultType,x.NOTE FaultTypeDescr,'01' SalaryType, "
					+ "case b.FaultType when '1' then (case when (h.type='4' or g2.Position = '07' ) and b.RiskFund<>0 then "
					+ "  (case c.WorkType12 when '01' then '025' when '02' then '026' "
					+ "	 when '03' then '027' when '04' then '028' when '05' then '029' "
					+ "	 when '12' then '030' when '27' then '041' when '14' then '043' "
					+ "  when '13' then '029' when '20' then '029' end) else "
					+ "	 (case when b.IsSalary='1' then e.OfferWorkType2 else '037' end) end) "
					+ "when '3' then (case c.WorkType12 when '01' then '025' when '02' then '026' "
					+ "	 when '03' then '027' when '04' then '028' when '05' then '029' "
					+ "	 when '12' then '030' when '27' then '041' when '14' then '043' "
					+ "  when '13' then '029' when '20' then '029' end) end WorkType2, "
					+ "b.Amount AppAmount, "
					+ "b.EmpCode,g2.NameChi EmpDescr,h.Desc2 position,b.WorkerCode,f2.NameChi WorkDescr, "
					+ "b.SupplCode,j.Descr SupplDescr,b.IsSalary,x2.NOTE IsSalaryDescr,d.ProjectMan ApplyMan, "
					+ "g3.NameChi ApplyManDescr,'0' IsWithHold,'1' Status,'申请' StatusDescr,'0' IsConfirm,b.PK RefFixDutyManPk, "
					+ "d.Descr CustDescr,d.DocumentNo,d.Status custStatus,d.SignDate,d.CheckStatus,x3.NOTE CheckStatusDescr, "
					+ "'工人工费' SalaryTypeDescr,x5.NOTE IsSignDescr,'否' IsWithHoldDescr,d.IsWaterItemCtrl, "
					+ "x6.NOTE IsWaterItemCtrlDescr,f.IDNum,e2.QualityFeeBegin,e.Descr+'定责工资' remarks, "
					+ "k.Desc1 CustTypeDescr "
					+ "from tFixDuty a "
					+ "inner join tFixDutyMan b on b.No=a.No "
					+ "left join tCustWorker c on c.PK=a.CustWkPk "
					+ "left join tCustomer d on d.Code=a.CustCode "
					+ "left join tWorkType12 e on e.Code=c.WorkType12 "
					+ "left join tWorker f on f.Code=a.AppWorkerCode "
					+ "left join tEmployee g on g.Number=a.DutyMan "
					+ "left join tXTDM x on x.ID='FAULTTYPE' and b.FaultType=x.CBM "
					+ "left join tEmployee g2 on g2.Number=b.EmpCode "
					+ "left join tPosition h on h.Code=g2.Position "
					+ "left join tXTDM x2 on x2.ID='YESNO' and x2.CBM=b.IsSalary "
					+ "left join tWorker f2 on f2.Code=b.WorkerCode "
					+ "left join tEmployee g3 on g3.Number=d.ProjectMan "
					+ "left join tWorkCard i on i.CardID=f.CardID "
					+ "left join tSupplier j on j.Code=b.SupplCode "
					+ "left join tXTDM x3 on x3.ID='CheckStatus' and x3.CBM=d.CheckStatus "
					+ "left join tXTDM x5 on x5.cbm =f.isSign and x5.ID='WSIGNTYPE' "
					+ "left join tXTDM x6 on x6.cbm =d.IsWaterItemCtrl and x6.ID='YESNO' "
					+ "left join tWorkType12 e2 on e2.Code=f.WorkType12 "
					+ "left join tCustType k on k.Code = d.CustType "
					+ "where a.Status='6' and a.Type='1' "
					//设计师承担，但使用风控基金金额不为0，也要生成工资单。add by zb on 20191029
					+ "and (((h.Type <> '4' or (h.type='4' and b.RiskFund<>0)) and b.FaultType='1') or b.FaultType='3') "
					+ "and not exists ( "
					+ "  select * from tWorkCostDetail in_a "
					+ "  where in_a.Expired='F' and in_a.RefFixDutyManPk<>0 and in_a.RefFixDutyManPk=b.PK) ";
		if (StringUtils.isNotBlank(fixDuty.getKeys())) {
			sql += "and not exists (select 1 from tFixDutyDetail in_a " +
					"where in_a.PK=b.PK and in_a.PK in ('"+fixDuty.getKeys().replace(",", "','")+"')) ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a left join tWorkType2 b on b.Code=a.WorkType2 "
				+	"left join tWorkType1 c on c.Code=b.WorkType1 "
				+	"order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a left join tWorkType2 b on b.Code=a.WorkType2 "
				+	"left join tWorkType1 c on c.Code=b.WorkType1 "
				+	"order by a.FDNo ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 是否水电发放
	 * @author	created by zb
	 * @date	2020-3-27--上午10:09:35
	 * @param custCode
	 * @return
	 */
	public String isWaterCostPay(String custCode) {
		String sql = "select 1 "
					+"from tWorkCostDetail a left join tWorkCost b on b.No=a.No "
					+"where a.CustCode=? and a.Status='2' and b.PayDate is not null ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (null != list && list.size()>0) {
			return "1";
		}
		return "0";
	}
}

