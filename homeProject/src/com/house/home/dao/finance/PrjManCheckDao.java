package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PrjCheck;

@SuppressWarnings("serial")
@Repository
public class PrjManCheckDao extends BaseDao {

	/**
	 * prjManCheck list列表信息
	 * @param page
	 * @param prjManCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from (select a.Code,a.DocumentNo,a.Descr,a.Address,a.Status,d.NOTE StatusDescr,a.ProjectMan,e3.NameChi ProjectManDescr,a.CheckStatus,d1.NOTE CheckStatusDescr,a.DesignFee,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
		  + " p.AppCZY,e4.NameChi AppCZYDescr,p.Date,p.ConfirmCZY,e5.NameChi ConfirmCZYDescr,p.ConfirmDate,"
		  + " (isnull(a.BaseFee_Dirct,0)+isnull(a.BaseFee_Comp,0)) BaseFee,(isnull(a.BaseFee_Dirct,0)+isnull(a.BaseFee_Comp,0)+isnull(p.BaseChg,0)) BaseFee_Dirct,"
		  + " ((isnull(a.MainFee,0)-isnull(a.MainDisc,0))*isnull(ContainMain,0)+(isnull(a.SoftFee,0)-isnull(a.SoftDisc,0))*isnull(ContainSoft,0)+"
		  + " (isnull(a.IntegrateFee,0)-isnull(a.IntegrateDisc,0))*isnull(containInt,0)+(isnull(a.CupboardFee,0)-isnull(a.CupboardDisc,0))*Isnull(ContainCup,0)) MainAmount, "
		  + " (isnull(a.ChgMainFee,0)+isnull(a.ChgSoftFee,0)+isnull(a.ChgIntFee,0)+isnull(a.ChgCupFee,0)) ChgMainAmount,"
		  + " isnull(p.BaseChg,0) BaseChg,isnull(p.BaseCtrlAmt,0) BaseCtrlAmt,isnull(p.Cost,0) Cost,isnull(p.WithHold,0) WithHold,isnull(p.MainCoopFee,0) MainCoopFee,isnull(p.RecvFee,0) RecvFee,isnull(p.QualityFee,0) QualityFee,"
		  + " isnull(p.AccidentFee,0) AccidentFee,isnull(p.MustAmount,0) MustAmount,isnull(p.RealAmount,0) RealAmount,a.ProjectCtrlAdj,p.Remarks,p.IsProvide,p.ProvideNo,p.LastUpdate,p.LastUpdatedBy,p.Expired,p.ActionLog "
		  + " ,a.IsItemUp,d2.NOTE IsItemUpDescr,p.CtrlExprRemarks, p.CtrlExprWithNum,ct.type custTypeType , "
		  + " a.MainSetFee,a.LongFee,isnull(p.AllSetAdd, 0) AllSetAdd,isnull(p.AllSetMinus, 0) AllSetMinus," 
		  + " isnull(p.AllItemAmount, 0) AllItemAmount, isnull(p.AllManageFee_Base, 0) AllManageFee_Base,isnull(p.UpgWithHold, 0) UpgWithHold, " 
		  + " isnull(p.BaseCost, 0) BaseCost,isnull(p.MainCost, 0) MainCost, isnull(p.Cost, 0)-isnull(p.BaseCost, 0) MainCost_Js,"
		  + " isnull(case when a.InnerArea>0 then a.InnerArea else  a.Area*ct.InnerAreaPer end ,0) InnerArea "
		  + " ,d3.note IswaterItemctrlDescr , d4.note IswaterctrlDescr,ct.PrjCtrlType,d5.note IsSalaryConfirmDescr,a.CheckOutDate "
		  + " ,isnull(p.recvFee_FixDuty,0) recvFeeFixDuty, isnull(b.DesignFixDutyAmount, 0) DesignFixDutyAmount,ck.SalaryConfirmDate,a.CustCheckDate, "
		  + " ct.Desc1 CustTypeDescr, a.Layout, d6.NOTE LayoutDescr, "
		  + " case when exists(select 1 from tCustStakeholderHis in_a where in_a.Role = '20' and in_a.OperType = '2' and in_a.CustCode = a.Code) "
		  + "      then '是' else '否' end HasChangedSupervisor "
		  + " from tCustomer a "
		  + " inner join tCustType ct on a.CustType=ct.Code "
		  + " left outer join tPrjCheck p on a.Code=p.CustCode "
		  + " left outer join tEmployee e1 on a.DesignMan=e1.Number "
		  + " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
		  + " left outer join tEmployee e3 on a.ProjectMan=e3.Number "
		  + " left outer join tEmployee e4 on p.AppCZY=e4.Number "
		  + " left outer join tEmployee e5 on p.ConfirmCZY=e5.Number "
		  + " left outer join tCustCheck ck on a.Code=ck.CustCode "
		  + " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
		  + " left outer join tXTDM d1 on a.CheckStatus=d1.CBM and d1.ID='CheckStatus' "
		  + " left outer join tXTDM d2 on a.IsItemUp=d2.CBM and d2 .ID='YESNO' "
		  + " left outer join tXTDM d3 on a.IswaterItemctrl=d3.CBM and d3.ID='YESNO' " 
          + " left outer join tXTDM d4 on a.Iswaterctrl=d4.CBM and d4.ID='YESNO' "
          + " left outer join tXTDM d5 on ck.IsSalaryConfirm=d5.CBM and d5.ID='YESNO' "
          + " left outer join tXTDM d6 on a.Layout = d6.CBM and d6.ID = 'LAYOUT' "
          + " outer apply ("
          + "     select sum(in_b.Amount) DesignFixDutyAmount "
          + "     from tFixDuty in_a "
          + "     left join tFixDutyMan in_b on in_a.No = in_b.No "
          + "     where in_a.Status = '7' and Type='2' "
          + "         and in_a.CustCode = a.Code "
          + " ) b "
		  + " where a.Status='5' and a.CheckStatus in('2','3','4') ";
		
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql+= " and a.designMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.businessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress().trim() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCheckStatus())) {
			sql += " and a.CheckStatus in " + "('"+customer.getCheckStatus().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in " + "('"+customer.getCustType().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(customer.getIsOutSet())) {
			sql += " and ct.PrjCtrlType=? ";
			list.add(customer.getIsOutSet()); //原客户类型改为发包方式
		}
		if (customer.getDateFrom() != null){
			sql += " and p.Date>= ? ";
			list.add(customer.getDateFrom());
		}
    	if (customer.getDateTo() != null){
			sql += " and p.Date< ? ";
			list.add(DateUtil.addDateOneDay(customer.getDateTo()));
		}
    	if (customer.getConfirmDateFrom() != null){
			sql += " and p.ConfirmDate>= ? ";
			list.add(customer.getConfirmDateFrom());
		}
    	if (customer.getConfirmDateTo() != null){
			sql += " and p.ConfirmDate< ? ";
			list.add(DateUtil.addDateOneDay(customer.getConfirmDateTo()));
		}
    	if (StringUtils.isNotBlank(customer.getIsSalaryConfirm())) {
    		if("0".equals(customer.getIsSalaryConfirm())){
    			sql += " and (ck.IsSalaryConfirm=? or isnull(ck.IsSalaryConfirm,'')='')";
    			list.add(customer.getIsSalaryConfirm());
    		}else{
    			sql += " and ck.IsSalaryConfirm=? ";
    			list.add(customer.getIsSalaryConfirm());
    		}
			
		}
    	if (customer.getCheckOutDateFrom() != null){
			sql += " and a.CheckOutDate>= ? ";
			list.add(customer.getCheckOutDateFrom());
		}
    	if (customer.getCheckOutDateTo() != null){
			sql += " and a.CheckOutDate< ? ";
			list.add(DateUtil.addDateOneDay(customer.getCheckOutDateTo()));
		}
    	if (customer.getCustCheckDateFrom() != null){
			sql += " and a.CustCheckDate>= ? ";
			list.add(customer.getCustCheckDateFrom());
		}
    	if (customer.getCustCheckDateTo() != null){
			sql += " and a.CustCheckDate< ? ";
			list.add(DateUtil.addDateOneDay(customer.getCustCheckDateTo()));
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//预扣列表                                                              
	public Page<Map<String, Object>>  findPageBySql_prjWithHold(
			Page<Map<String, Object>> page, PrjCheck prjCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from( "; 
			sql+= " select t.PK,t.CustCode,t.WorkType2,t1.Descr WorkType2Descr,t1.WorkType1,t2.Descr WorkType1Descr,t.Type,t3.NOTE TypeDescr,"
	                   + " t.Amount,t.Remarks,t.LastUpdate,t.LastUpdatedBy,t.Expired,t.ActionLog,t.isCreate,t4.NOTE isCreateDescr,t.ItemAppNo"
	                   +" from tPrjWithHold t "
	                   + " left outer join tWorkType2 t1 on t1.Code=t.WorkType2 "
	                   + " left outer join tWorkType1 t2 on t2.Code=t1.WorkType1 "
	                   + " left outer join tXTDM t3 on t3.IBM=t.Type and t3.ID = 'PrjWithHoldType' "
	                   + " left outer join tXTDM t4 on t4.IBM=t.isCreate and t4.ID = 'YESNO' "
	                   + " where 1=1 and t.CustCode=? ";
			if (StringUtils.isNotBlank(prjCheck.getCustCode())) {
				 	list.add(prjCheck.getCustCode());
			}else{
				return null;
			}
	   if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
	   }else{
			sql += ")a order by a.LastUpdate ";
	   }
	   return this.findPageBySql(page, sql, list.toArray());
	}


	public Map<String, Object> getSendQty(String itemCode,String custCode) {
		String sql = "select  isnull(sum (case when b.OutType='2' then case when b.Status='RETURN'  then -isnull(a.qty,0) "
                     + " else 0  end else isnull(a.qty,0) end ),0)sendQty from tGiftAppDetail a inner join  tGiftApp b on a.No=b.no "
                     + " where  a.ItemCode=?  and b.CustCode=?  and b.Status<>'CANCEL'    ";		
		List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{itemCode,custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 质保金汇总数据
	 * @param  
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_totalQualityFee(Page<Map<String,Object>> page, PrjCheck prjCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select * from (select  c.ProjectMan,e.NameChi ProjectManDescr,isnull(sum(pc.QualityFee),0) QualityFee,isnull(sum(pc.AccidentFee),0) AccidentFee "
				+ " from tCustomer c "
				+ " inner join tEmployee e on c.ProjectMan=e.Number "
				+ " inner join tPrjCheck pc on c.Code=pc.CustCode "
				+ " where c.CheckStatus='4' " ;
               
    	if (StringUtils.isNotBlank(prjCheck.getProjectMan())) {
			sql += " and c.ProjectMan = ? ";
			list.add(prjCheck.getProjectMan());
		}
    	sql = sql+"group by c.ProjectMan,e.NameChi" ; 
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 质保金明细数据
	 * @param  
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_qualityDetail(Page<Map<String,Object>> page, PrjCheck prjCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select * from (select c.DocumentNo,c.Address,c.ProjectMan,e.NameChi ProjectManDescr, "
				+ " isnull(pc.QualityFee,0) QualityFee,isnull(pc.AccidentFee,0) AccidentFee "
				+ " from tCustomer c "
				+ " inner join tEmployee e on c.ProjectMan=e.Number "
				+ " inner join tPrjCheck pc on c.Code=pc.CustCode " 
				+ " where c.CheckStatus='4' " ;
		
		if (StringUtils.isNotBlank(prjCheck.getProjectMan())) {
			sql += " and  Number= ? ";
			list.add(prjCheck.getProjectMan());
		}else{
			return  null;	
		} 
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 获取项目名称列表
	 * @param  
	 * @return
	 */
	public List<Map<String,Object>> getTypeDescr(){
		String sql=" select wt1.Descr+'|'+wt2.Descr ColumnName from tWorkType2 wt2 left outer join tWorkType1 wt1 on wt1.Code=wt2.WorkType1  " +
				  " where wt1.Descr + '|' + wt2.Descr<>'拆除及其它|已领人工' order by wt2.WorkType1,wt2.DispSeq ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			 return list;
		}
		return null;
	}
	/**
	 * 获取项目名称列表
	 * @param  
	 * @return
	 */
	public List<Map<String,Object>> getWorkType1Descr(){
		String sql=" select Descr  from tWorkType1  where Expired='F' order by DispSeq ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			 return list;
		}
		return null;
	}
	
	/**
	 * 项目经理明细查询			
	 * @param page
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySqlPrjCheckDetail(Page<Map<String,Object>> page, PrjCheck  prjCheck) {
		Assert.notNull(prjCheck);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjCheckDetailQuery(?)}");
			call.setString(1, prjCheck.getCustCode());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	//提示未审核人工单，不允许结算
	public boolean isCheckWorkCost(String custCode) {
		String sql =" select 1 from tWorkCost t1  " +
				" inner join tWorkCostDetail t2 on t1.No=t2.No  " +
				" where t1.Status='1' and t2.SalaryType<>'05' " +
				" and t2.CustCode=?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {custCode});
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}
	//提示未审核人工单，不允许结算
	public boolean isAbnormalItemApp(String custCode,String custTypeType) {
		String sql =" select 1 from tItemApp where CustCode=?  " 
				   + "and (Status='OPEN' or Status='CONFIRMED' or Status='CONRETURN') " ;
		if ("2".equals(custTypeType)){
			sql=sql+" and (ItemType1='JZ' or ItemType1='ZC') ";
		}else {
			sql=sql+" and ItemType1='JZ' ";
		}  
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {custCode});
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}
	
	//结算操作
	@SuppressWarnings("deprecation")
	public Result doPrjCheckForProc(PrjCheck prjCheck) {
		Assert.notNull( prjCheck);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pXmjljs(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prjCheck.getM_umState());
			call.setString(2, prjCheck.getCustCode());	
			call.setString(3, prjCheck.getRemarks());
			call.setString(4, prjCheck.getAppCzy());
			call.setTimestamp(5, prjCheck.getDate() == null ? null : new Timestamp(
					prjCheck.getDate().getTime()));
			call.setString(6, prjCheck.getConfirmCzy());
			call.setTimestamp(7, prjCheck.getConfirmDate() == null ? null : new Timestamp(
					prjCheck.getConfirmDate().getTime()));
			call.setString(8, prjCheck.getIsProvide());
			call.setString(9, prjCheck.getProvideNo());
			call.setString(10, prjCheck.getLastUpdatedBy());
			call.setString(11, prjCheck.getExpired());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	//结算操作
		@SuppressWarnings("deprecation")
		public Result doPrjCheckForProc_sdj(PrjCheck prjCheck) {
			Assert.notNull( prjCheck);
			Result result = new Result();
			Connection conn = null;
			CallableStatement call = null;
			try {
				HibernateTemplate hibernateTemplate = SpringContextHolder
						.getBean("hibernateTemplate");
				Session session = SessionFactoryUtils.getSession(
						hibernateTemplate.getSessionFactory(), true);
				conn = session.connection();
				call = conn.prepareCall("{Call pXmjljs_sdj(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				call.setString(1, prjCheck.getM_umState());
				call.setString(2, prjCheck.getCustCode());	
				call.setString(3, prjCheck.getRemarks());
				call.setString(4, prjCheck.getAppCzy());
				call.setTimestamp(5, prjCheck.getDate() == null ? null : new Timestamp(
						prjCheck.getDate().getTime()));
				call.setString(6, prjCheck.getConfirmCzy());
				call.setTimestamp(7, prjCheck.getConfirmDate() == null ? null : new Timestamp(
						prjCheck.getConfirmDate().getTime()));
				call.setString(8, prjCheck.getIsProvide());
				call.setString(9, prjCheck.getProvideNo());
				call.setString(10, prjCheck.getLastUpdatedBy());
				call.setString(11, prjCheck.getExpired());
				call.registerOutParameter(12, Types.INTEGER);
				call.registerOutParameter(13, Types.NVARCHAR);
				call.execute();
				result.setCode(String.valueOf(call.getInt(12)));
				result.setInfo(call.getString(13));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtil.close(null, call, conn);
			}
			return result;
		}
		
		/**
		 * 获取QualityFee,MustAmount,AccidentFee
		 * @param custCode
		 * @return
		 */
		public Map<String, Object> getQualityFee(String custCode) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String sql = "select QualityFee,MustAmount,AccidentFee from tPrjCheck where CustCode=? ";
			list = this.findListBySql(sql, new Object[] { custCode});
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
		/**
		 * 获取PrjCheck
		 * @param custCode
		 * @return
		 */
		public Map<String, Object> getPrjCheck(String custCode,String prjCtrlType) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String sql="";
			if("1".equals(prjCtrlType)){
				 sql = " SELECT CustCode,BaseChg,BaseCtrlAmt,Cost,WithHold,MainCoopFee,RecvFee,QualityFee, " 
						   + " AccidentFee,MustAmount,RealAmount,Remarks,IsProvide,ProvideNo,recvFee_FixDuty recvFeeFixDuty from tPrjCheck "
						   + " where CustCode=? ";
			}else{
				 sql = "SELECT CustCode,isnull(BaseChg,0) BaseChg,isnull(BaseCtrlAmt,0) BaseCtrlAmt,isnull(Cost,0) Cost, " 
						   + " isnull(WithHold,0) WithHold,isnull(RecvFee,0) RecvFee,isnull(QualityFee,0) QualityFee,"
				           + " isnull(AccidentFee,0) AccidentFee,isnull(MustAmount,0) MustAmount,isnull(RealAmount,0) RealAmount,Remarks,IsProvide,ProvideNo,LastUpdate,"
				           + " LastUpdatedBy,Expired,ActionLog,isnull(AllSetMinus,0) AllSetMinus,isnull(AllSetAdd,0) AllSetAdd,"
				           + " isnull(AllManageFee_Base,0) AllManageFee_Base,isnull(AllItemAmount,0) AllItemAmount,isnull(UpgWithHold,0) UpgWithHold,CtrlExprRemarks,CtrlExprWithNum, "
				           + " isnull(BaseCost,0)BaseCost , isnull(Cost, 0)-isnull(BaseCost, 0) MainCost,isnull(recvFee_FixDuty,0) recvFeeFixDuty from tPrjCheck "
						   + " where CustCode=? ";	
			}
			list = this.findListBySql(sql, new Object[] {custCode});
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
	   //项目经理关联不到工人表或关联到多个，不能保存
		public boolean isOneWorker(String custCode) {
			String sql =" select count(1) ret,a.Code from tCustomer a,tworker b where a.ProjectMan=b.EmpCode and a.Code=? "
					   + " group by a.Code having count(1)=1  ";
			List<Map<String, Object>> list = this.findBySql(sql, new Object[] {custCode});
			if (list != null && list.size() > 0) {
				return true;
			}
			return false;
		}
		//工人过期或离职，当质保金不为0时，不能保存
		public boolean isQualityFeeZero(String custCode){
			String sql =" select 1  from tcustomer c inner join tworker tw on c.ProjectMan=tw.EmpCode inner join tPrjCheck d on c.Code=d.CustCode " 
						+ " where (tw.Expired='T' or tw.IsLeave='1') and d.QualityFee<>0 and c.code=? ";
			List<Map<String, Object>> list = this.findBySql(sql, new Object[] {custCode});
			if (list != null && list.size() > 0) {
				return false;
			}
			return true;
		}
		//单独用于项目经理提成领取获取部分数据
		@SuppressWarnings("unchecked")
		public Map<String, Object> findBySql(String custCode){
			List<Object> param = new ArrayList<Object>();
			String sql= "select a.Code,a.Descr,a.Address,a.Status,d.NOTE StatusDescr,a.CheckStatus,d1.NOTE CheckStatusDescr,a.DesignFee,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
			          + "(isnull(a.BaseFee_Dirct,0)+isnull(a.BaseFee_Comp,0)) BaseFee,(isnull(a.BaseFee_Dirct,0)+isnull(a.BaseFee_Comp,0)+isnull(p.BaseChg,0)) BaseFee_Dirct,"
			          + "(isnull(a.MainFee,0)+isnull(a.MainDisc,0)+isnull(a.SoftFee,0)+isnull(a.SoftDisc,0)+isnull(a.SoftOther,0)+isnull(a.IntegrateFee,0)+isnull(a.IntegrateDisc,0)+isnull(a.CupboardFee,0)+isnull(a.CupBoardDisc,0)) MainAmount,"
			          + "(isnull(a.ChgMainFee,0)+isnull(a.ChgSoftFee,0)+isnull(a.ChgIntFee,0)+isnull(a.ChgCupFee,0)) ChgMainAmount,"
			          + "isnull(p.BaseChg,0) BaseChg,isnull(p.BaseCtrlAmt,0) BaseCtrlAmt,isnull(p.Cost,0) Cost,isnull(p.WithHold,0) WithHold,isnull(p.MainCoopFee,0) MainCoopFee,isnull(p.RecvFee,0) RecvFee,isnull(p.QualityFee,0) QualityFee,"
			          + "isnull(p.AccidentFee,0) AccidentFee,isnull(p.MustAmount,0) MustAmount,isnull(p.RealAmount,0) RealAmount,p.Remarks,p.IsProvide,p.ProvideNo,p.LastUpdate,p.LastUpdatedBy,p.Expired,p.ActionLog,isnull(p.recvFee_FixDuty,0) recvFeeFixDuty "
			          + "from tCustomer a "
			          + "left outer join tPrjCheck p on a.Code=p.CustCode "
			          + "left outer join tEmployee e1 on a.DesignMan=e1.Number "
			          + "left outer join tEmployee e2 on a.BusinessMan=e2.Number "
			          + "left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
			          + "left outer join tXTDM d1 on a.CheckStatus=d1.CBM and d1.ID='CheckStatus' "
			          + " where a.Code=? ";
			param.add(custCode);
			List<Map<String, Object>> list = this.findBySql(sql,param.toArray());
			if (list.size()>0 && list!=null) {
				return  list.get(0);
			}
			return null;
			//return this.findListBySql(sql,list1);	
		}
		public Map<String, Object> getRemainQualityFee(String custCode) {
			String sql = " select isnull(tw.QualityFee,0)+isnull(prj.QualityFee,0) RemainQualityFee," +
						  "	isnull(tw.AccidentFee,0)+isnull(prj.AccidentFee,0) RemainAccidentFee  from tcustomer c "
	                     + " left join tworker tw on c.ProjectMan=tw.EmpCode "
	                     + " left join (select sum(isnull(pc.qualityfee,0))qualityfee,sum(isnull(pc.AccidentFee,0)) accidentFee, in_c.ProjectMan "
	                     + "	from tprjcheck pc  "
	                     + "	inner join tcustomer in_c on in_c.code=pc.CustCode "
	            		 + "	where  in_c.code<>? and in_c.checkstatus in('2','3') "
	            		 + "	group by in_c.ProjectMan "
	            		 + " )prj on prj.ProjectMan=c.ProjectMan "
	            		 + " where C.Code=? ";
			List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{custCode,custCode});
			if (list!=null && list.size()>0){
				return list.get(0);
			}
			return null;
		}
		
		/**
		 * prjManCheck 设计定责列表信息
		 * @param page
		 * @param prjManCheck
		 * @return
		 */
		public Page<Map<String,Object>> findFixDutyPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck) {
			List<Object> list = new ArrayList<Object>();
			String sql = " select a.No, a.AppDate, e1.NameChi AppCzyDescr,a.Remarks FixDutyRemarks,e2.NameChi FixDutyManDescr, "
						+" b.Amount, b.RiskFund, b.Remark FixDutyManRemarks "
						+" from tFixDuty a "
						+" left join tFixDutyMan b on a.No = b.No "
						+" left join tEmployee e1 on a.AppCZY = e1.Number "
						+" left join tEmployee e2 on b.EmpCode = e2.Number "
						+" where a.Status = '7' and a.Type ='2' and CustCode = ? ";
			list.add(prjCheck.getCustCode());
			return this.findPageBySql(page, sql, list.toArray());
		}
		
		public Page<Map<String,Object>> findFixDutyDetailPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck) {
			List<Object> list = new ArrayList<Object>();
			String sql = " select c.Descr BaseItemDescr, a.Qty, a.OfferPri, a.Material , (a.OfferPri+a.Material)*a.Qty TotalAmount , "
						+" isnull(d.AdditionsQty, 0) AdditionsQty, isnull(d.AdditionsAmount, 0) AdditionsAmount "
						+" from tFixDutyDetail a "
						+" left join tFixDuty b on a.no = b.No "
						+" left join tBaseItem c on a.BaseItemCode = c.Code "
						+" left join (select  in_a.CustCode,in_b.BaseItemCode,sum(in_b.Qty) AdditionsQty, sum(in_b.Qty*(in_b.UnitPrice+in_b.Material)) AdditionsAmount "
						+" 	from tBaseItemChg in_a "
						+" 	left join tBaseItemChgDetail in_b on in_a.No = in_b.No "
						+" 	group by in_a.CustCode,in_b.BaseItemCode "
						+" 	having sum(in_b.Qty) >= 0 " 
						+" ) d on b.CustCode = d.CustCode and d.BaseItemCode = a.BaseItemCode"
						+" where b.Type = '2' and b.Status = '7' and b.CustCode = ? ";
			list.add(prjCheck.getCustCode());
			return this.findPageBySql(page, sql, list.toArray());
		}
		
		public Page<Map<String,Object>> findBaseItemChgDetailPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck) {
			List<Object> list = new ArrayList<Object>();
			String sql = " select c.Descr BaseItemDescr, a.Qty, a.UnitPrice, a.Material, a.Qty*(a.UnitPrice+a.Material) AdditionsAmount from tBaseItemChgDetail a " 
						+" left join tBaseItemChg b on a.No = b.No "
						+" left join tBaseItem c on a.BaseItemCode = c.Code "
						+" where a.IsOutSet = '1' and b.Status='2' and a.Qty > 0 and b.CustCode = ? ";
			list.add(prjCheck.getCustCode());
			return this.findPageBySql(page, sql, list.toArray());
		}
}

