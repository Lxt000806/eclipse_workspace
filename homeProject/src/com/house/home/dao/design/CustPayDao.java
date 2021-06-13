package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustPayDao extends BaseDao {

	/**
	 * CustPay分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.DocumentNo,a.Code,a.Descr,a.Address,a.Status,a.LayOut,a.Area,a.ContractFee,a.DesignFee,b.HasPay, "
				+ "a.FirstPay+isnull(case when tpr.DesignFeeType = '2'" 
				+ " then a.StdDesignFee  else a.DesignFee  end, 0)" 
				+ "           * ( case when exists ( select 1" 
				+ "          from tPayRuleDetail" 
				+ "          where No = tpr.No and IsRcvDesignFee = '1' and Expired = 'F' and PayNum = '1')"
				+ "          then 1 else 0 end ) firstPay,a.SecondPay+ isnull( "
				+ "	case when tpr.DesignFeeType = '2' then a.StdDesignFee  else a.DesignFee  end, 0) * ( "
				+ "	case when exists ( select 1 from tPayRuleDetail  where No = tpr.No and IsRcvDesignFee = '1' "
				+ "	and Expired = 'F' and PayNum = '2') then 1 else 0 end ) SecondPay,a.ThirdPay + isnull( "
				+ "	case when tpr.DesignFeeType = '2' then a.StdDesignFee  else a.DesignFee  end, 0) * ( "
				+ "	case when exists ( select 1 from tPayRuleDetail  where No = tpr.No and IsRcvDesignFee = '1' "
				+ "	and Expired = 'F' and PayNum = '3') then 1 else 0 end ) ThirdPay,a.FourPay + isnull( "
				+ "	case when tpr.DesignFeeType = '2' then a.StdDesignFee  else a.DesignFee  end, 0) * ( "
				+ "	case when exists ( select 1 from tPayRuleDetail  where No = tpr.No and IsRcvDesignFee = '1' "
				+ "	and Expired = 'F' and PayNum = '4') then 1 else 0 end ) FourPay,a.DesignMan,a.BusinessMan,a.ReturnDesignFee,a.PayType, "
				+ "BaseFee_Dirct,BaseFee_Comp,ManageFee,MainFee,SoftFee,IntegrateFee,CupboardFee, "
				+ "CupBoardDisc+IntegrateDisc+SoftDisc+MainDisc+BaseDisc SumDisc,tr.Descr RegionDescr, "
				+ "a.LastUpdate,a.LastUpdatedBy,d.NOTE StatusDescr,a.CustType,i.NOTE CustTypeDescr, "
				+ "e1.NameChi DesignManDescr,dbo.fGetEmpNameChi(a.code,'01') BusinessManDescr,a.AgainMan,dbo.fGetEmpNameChi(a.code,'24') AgainManDescr,a.SetDate, "
				+ "a.RepairCardDate,a.RepairCardMan,e5.NameChi RepairCardManDescr,a.CDDate,e6.NameChi CDManDescr,a.CDMan,a.RepairRemark,h.note EndCodeDescr,a.CrtDate,IsUpPosiPic, "
				+ "a.ProjectMan ,e4.NameChi ProjectManDescr,a.ConsEndDate, a.CheckOutDate ,a.ConfirmBegin,DiscRemark,a.CanCancel, "
				+ "isnull(bic.SumChgAmount,0)+isnull(ic.SumChgAmount,0)+isnull(cfc.SumChgAmount,0) as zjxje,PayRemark,x1.note CanCancelDescr, "
				+ "dbo.fGetEmpNameChi(a.code,'63') SceneDesignMan,cc.ContractStatusDescr "
				+ "from tCustomer a "
				+ "left outer join (select CustCode,isnull(sum(Amount),0) HasPay from tCustPay group by CustCode) b on  b.CustCode = a.Code "
				+ "left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ "left outer join tXTDM h on a.EndCode=h.CBM and h.ID='CUSTOMERENDCODE' "
				+ "left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ "left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ "left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ "left outer join tEmployee e4 on a.ProjectMan=e4.Number "
				+ "left outer join tEmployee e5 on a.RepairCardMan=e5.Number "
				+ "left outer join tEmployee e6 on a.CDMan=e6.Number "
				+ "left outer join tXTDM i on a.CustType = i.CBM and i.ID = 'CUSTTYPE' "
				+ "left outer join (select sum(Amount) SumChgAmount,CustCode "
				+ "from tBaseItemChg where Status='2' group by CustCode ) bic on bic.CustCode=a.Code "
				+ "left outer join (select sum(Amount) SumChgAmount,CustCode "
				+ "from tItemChg where Status='2' group by CustCode ) ic on ic.CustCode=a.Code "
				+ "left outer join (select sum(ChgAmount) SumChgAmount,CustCode "
				+ "from tConFeeChg where status='CONFIRMED' group by CustCode ) cfc on cfc.CustCode=a.Code "
				+ "left outer join tXTDM x1 on a.CanCancel=x1.cbm and x1.id='yesno' "
				+ "left outer join tBuilder tb on a.BuilderCode=tb.code "
				+ "left outer join tRegion tr on tb.RegionCode=tr.code "
				+ "left join tPayRule tpr on tpr.CustType=a.Custtype and tpr.PayType=a.PayType "
				+ "outer apply ( " 
				+ "	 select top 1 in_b.NOTE ContractStatusDescr,in_a.Status ContractStatus "
				+ "	 from tCustContract in_a "
				+ "	 left join tXTDM in_b on in_a.Status = in_b.CBM and in_b.ID = 'CONTRACTSTAT' "
				+ "	 where a.Code = in_a.CustCode and  in_a.ConType = '2'  "
				+ "  order by in_a.PK desc "
				+ ") cc "
				+ "where 1=1";
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			if("0".equals(customer.getSearchType())){
				sql+=" and exists(select 1 from tCustStakeholder m where m.CustCode = a.Code and m.Role = '00' " +
						"and exists(select 1 from tEmployee te1 where te1.Department1 =? ";
				list.add(customer.getDepartment1());
			}else if("1".equals(customer.getSearchType())){
				sql+=" and exists(select 1 from tCustStakeholder m where m.CustCode = a.Code and m.Role = '01' " +
						"and exists(select 1 from tEmployee te1 where te1.Department1 =? ";
				list.add(customer.getDepartment1());
			}
			if(StringUtils.isNotBlank(customer.getDepartment2())){
				sql += " and te1.Department2=? ";
				list.add(customer.getDepartment2());
			}
			if(StringUtils.isNotBlank(customer.getDepartment3())){
				sql += " and te1.Department3=? ";
				list.add(customer.getDepartment3());
			}
			sql+=" and m.empCode = te1.Number)) ";
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + customer.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and (a.Address like ? or a.realAddress like ? )";
			list.add("%" + customer.getAddress() + "%");
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and (a.Code like ? or a.address like ?) ";
			list.add("%"+customer.getCode()+"%");
			list.add("%"+customer.getCode()+"%");
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());
		}
		if (StringUtils.isNotBlank(customer.getDocumentNo())) {
			sql += " and a.DocumentNo like ? ";
			list.add("%" + customer.getDocumentNo() + "%");
		}
		if (StringUtils.isNotBlank(customer.getSource())) {
			sql += " and a.Source=? ";
			list.add(customer.getSource());
		}
		if (customer.getSetDateFrom()!=null) {
			sql += " and a.SetDate >= ? ";
			list.add(customer.getSetDateFrom());
		}
		if(customer.getSetDateTo()!=null){
			sql += " and a.SetDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getSetDateTo()).getTime()));
		}
		if(customer.getSignDateFrom()!=null){
			sql += " and a.SignDate >= ? ";
			list.add(customer.getSignDateFrom());
		}
		if(customer.getSignDateTo()!=null){
			sql += " and a.SignDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getSignDateTo()).getTime()));
		}
		if(customer.getCustCheckDateFrom()!=null){
			sql += " and a.CustCheckDate >= ? ";
			list.add(customer.getCustCheckDateFrom());
		}
		if(customer.getCustCheckDateTo()!=null){
			sql += " and a.CustCheckDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isBlank(customer.getPurchStatus())){
			if (StringUtils.isNotBlank(customer.getStatus())) {
				String str = SqlUtil.resetStatus(customer.getStatus());
				sql += " and a.status in (" + str + ")";
			}
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getContractStatus())){
			sql += " and cc.ContractStatus = ? ";
			list.add(customer.getContractStatus());
		}
		return this.findPageByJdbcTemp(page, sql.toLowerCase(), "a.Code asc", list.toArray());
	}

	public boolean hasCustPay(String custCode) {
		String sql = "select 1 from tCustPay where Expired='F' and  Custcode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 付款信息分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPayInfoPageBySql(Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select b.CustCode,a.Descr,Address,a.FirstPay,a.SecondPay,a.thirdPay,a.FourPay, "
				+ "a.ContractFee,b.Amount,b.Date,a.Code, "
				+ "b.LastUpdate,b.LastUpdatedBy,x.NOTE TypeDescr, "
				+ "b.Remarks,isnull(b.PK,0) as PK,b.IsCheckOut,b.PayCheckOutNo, "
				+ "b.RcvAct,c.Descr RcvActDescr,b.PosCode,d.Descr PosDescr,b.ProcedureFee,b.AddDate,b.PayNo, "
				+ "e.PK CustPayTranPK,b.discTokenNo, b.PrintCZY, f.ZWXM printCZYName, b.PrintDate, "
				+ "b.WfProcInstNo "
				+ "from tCustomer a "
				+ "inner join tCustPay b on b.CustCode = a.Code "
				+ "left outer join tRcvAct c on b.RcvAct=c.Code "
				+ "left outer join tBankPos d on b.PosCode=d.Code  "
				+ "left join tXTDM x on x.ID='CPTRANTYPE' and b.Type=x.CBM "
				+ "left join tCustPayTran e on e.CustPayPK=b.PK "
				+ "left join tCZYBM f on b.PrintCZY = f.CZYBH "
				+ "where b.CustCode =? "
				+ "order by b.AddDate asc ";
		list.add(customer.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 增减信息分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findChgInfoPageBySql(Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select * from (select a.date,b.SM ItemType1Descr,a.amount from tItemChg a "
				+ "left outer join txtcs b on b.id=a.ItemType1 "
				+ "where status='2' and a.CustCode=?)a "
				+ "union all "
				+ "select * from (select date,'基础' ItemType1Descr,amount from tBaseItemChg "
				+ "where status='2' and CustCode=?)b "
				+ "union all "
				+ "select * from(select date,'合同费用' ItemType1Descr,ChgAmount amount from tConFeeChg "
				+ "where status='CONFIRMED' and CustCode=?)c )d ";
		list.add(customer.getCode());
		list.add(customer.getCode());
		list.add(customer.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 增减申请分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findChgAppPageBySql(Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select * from (select a.date,b.SM ItemType1Descr,a.amount from tItemChg a "
				+ "left outer join txtcs b on b.id=a.ItemType1 "
				+ "where status='1' and a.CustCode=?)a "
				+ "union all "
				+ "select * from(select date,'基础' ItemType1Descr,amount from tBaseItemChg "
				+ "where status='1' and CustCode=?)b "
				+ "union all "
				+ "select * from(select date,'合同费用' ItemType1Descr,ChgAmount amount from tConFeeChg "
				+ "where status='OPEN' and CustCode=?)c)d";
		list.add(customer.getCode());
		list.add(customer.getCode());
		list.add(customer.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 收款账号下拉框
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findRcvAct(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select Code id,Descr name from tRcvAct where Expired='F' ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and code not  in (" + param.get("pCode") + ") ";
		}
		sql += " order by Code";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * pos机
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findPos(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select Code id,Descr name from tBankPos where Expired='F' ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and RcvAct=? ";
			list.add((String) param.get("pCode"));
		}
		sql += " order by Code";
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 查收款单号
	 * 
	 * @param payNo
	 * @return
	 */
	public List<Map<String, Object>> findPayNo(String payNo,String pk) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tCustPay where payno=? ";
		list.add(payNo);
		if(StringUtils.isNotBlank(pk)){
			sql+="and pk <> ?";
			list.add(pk);
		}
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 查bankPos相关
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findBankPos(String code) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select MinFee,MaxFee,FeePerc,AcquireFeePerc from tBankPos where Code=? ";
		list.add(code);
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 修改付款计划
	 * @param customer
	 * @return
	 */
	public void updatePayPlan(Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCustomer set FirstPay = ?,SecondPay=?,ThirdPay=?, FourPay=?,PayRemark=? where Code=?";
		list.add(customer.getFirstPay());
		list.add(customer.getSecondPay());
		list.add(customer.getThirdPay());
		list.add(customer.getFourPay());
		list.add(customer.getPayRemark());
		list.add(customer.getCode());
		this.executeUpdateBySql(sql, list.toArray());
	}
	
	/**
	 * 查设计费类型
	 * 
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> findDesignFeeType(Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select case when isnull(prd1.IsRcvDesignFee,'0')='1' or isnull(prd2.IsRcvDesignFee,'0')='1' or isnull(prd3.IsRcvDesignFee,'0')='1' or isnull(prd4.IsRcvDesignFee,'0')='1' then pr.DesignFeeType else '3' end  DesignFeeType  "
			    + " from tPayRule pr  "
		        + " left outer join tPayRuleDetail prd1 on prd1.no=pr.No and prd1.paynum='1' "
		        + " left outer join tPayRuleDetail prd2 on prd2.no=pr.No and prd2.paynum='2' "
		        + " left outer join tPayRuleDetail prd3 on prd3.no=pr.No and prd3.paynum='3' "
		        + " left outer join tPayRuleDetail prd4 on prd4.no=pr.No and prd4.paynum='4' "
				+ " where pr.CustType=? and pr.PayType=? ";
		list.add(customer.getCustType());
		list.add(customer.getPayType());
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 查custpay主界面列表
	 * 
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> findListBySql(Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.DocumentNo,a.Code,a.Descr,a.Address,a.Status,a.LayOut,a.Area,a.ContractFee,a.DesignFee,b.HasPay, "
				+ "a.FirstPay,a.SecondPay,a.ThirdPay,a.FourPay,a.DesignMan,a.BusinessMan,a.ReturnDesignFee,a.PayType, "
				+ "BaseFee_Dirct,BaseFee_Comp,ManageFee,MainFee,SoftFee,IntegrateFee,CupboardFee, "
				+ "CupBoardDisc+IntegrateDisc+SoftDisc+MainDisc+BaseDisc SumDisc,tr.Descr RegionDescr, "
				+ "a.LastUpdate,a.LastUpdatedBy,d.NOTE StatusDescr,a.CustType,i.NOTE CustTypeDescr, "
				+ "e1.NameChi DesignManDescr,dbo.fGetEmpNameChi(a.code,'01') BusinessManDescr,a.AgainMan,e3.NameChi AgainManDescr,a.SetDate, "
				+ "a.RepairCardDate,a.RepairCardMan,e5.NameChi RepairCardManDescr,a.CDDate,a.CDMan,e6.NameChi CDManDescr,a.RepairRemark,h.note EndCodeDescr,a.CrtDate,IsUpPosiPic, "
				+ "a.ProjectMan ,e4.NameChi ProjectManDescr,d2.Desc2 ProjectManDept2,a.ConsEndDate, a.CheckOutDate ,a.ConfirmBegin,DiscRemark,a.CanCancel, "
				+ "isnull(bic.SumChgAmount,0)+isnull(ic.SumChgAmount,0)+isnull(cfc.SumChgAmount,0) as zjxje,PayRemark,x1.note CanCancelDescr,b.minPayDate "
				+ "from tCustomer a "
				+ "left outer join (select CustCode,isnull(sum(Amount),0) HasPay,min(date) minPayDate from tCustPay group by CustCode) b on  b.CustCode = a.Code "
				+ "left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ "left outer join tXTDM h on a.EndCode=h.CBM and h.ID='CUSTOMERENDCODE' "
				+ "left outer join tEmployee e1 on a.DesignMan=e1.Number "
				+ "left outer join tEmployee e2 on a.BusinessMan=e2.Number "
				+ "left outer join tEmployee e3 on a.AgainMan=e3.Number "
				+ "left outer join tEmployee e4 on a.ProjectMan=e4.Number "
				+ "left outer join tEmployee e5 on a.RepairCardMan=e5.Number "
				+ "left outer join tEmployee e6 on a.CDMan=e6.Number "
				+ "left outer join tDepartment2 d2 on e4.department2=d2.Code "
				+ "left outer join tXTDM i on a.CustType = i.CBM and i.ID = 'CUSTTYPE' "
				+ "left outer join (select sum(Amount) SumChgAmount,CustCode "
				+ "from tBaseItemChg where Status='2' group by CustCode ) bic on bic.CustCode=a.Code "
				+ "left outer join (select sum(Amount) SumChgAmount,CustCode "
				+ "from tItemChg where Status='2' group by CustCode ) ic on ic.CustCode=a.Code "
				+ "left outer join (select sum(ChgAmount) SumChgAmount,CustCode "
				+ "from tConFeeChg where status='CONFIRMED' group by CustCode ) cfc on cfc.CustCode=a.Code "
				+ "left outer join tXTDM x1 on a.CanCancel=x1.cbm and x1.id='yesno' "
				+ "left outer join tBuilder tb on a.BuilderCode=tb.code "
				+ "left outer join tRegion tr on tb.RegionCode=tr.code "
				+ "where a.Code=? ";
		list.add(customer.getCode());
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 修改客户资料
	 * 
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doUpdateProc(Customer customer) {
		Assert.notNull(customer);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pKhfk_Xgkh_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getM_umState());
			call.setString(2, customer.getCode());	
			call.setString(3, customer.getStatus());
			call.setTimestamp(4, customer.getSetDate()==null?null:new Timestamp(customer.getSetDate().getTime()));
			call.setString(5, customer.getCanCancel());
			call.setString(6, customer.getDesignMan());
			call.setString(7, customer.getBusinessMan());
			call.setString(8, customer.getAgainMan());
			call.setString(9, customer.getOldDesignMan());
			call.setString(10, customer.getOldBusinessMan());
			call.setString(11, customer.getOldAgainman());
			call.setString(12, customer.getLastUpdatedBy());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, customer.getFromStatus());
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	/**
	 * 查客户状态
	 * 
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> findCustStatus(Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select status,setDate from tCustomer where code=? ";
		list.add(customer.getCode());
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 修改保修卡
	 * 
	 * @param customer
	 * @return
	 */
	public void updateRepairCard(Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCustomer set RepairCardMan=?,RepairCardDate=?,CDMan=?,CDDate=?,"
				+ "RepairRemark=?,ConsEndDate=?,IsUpPosiPic=? where Code=?";
		list.add(customer.getRepairCardMan());
		list.add(customer.getRepairCardDate());
		list.add(customer.getCdman());
		list.add(customer.getCddate());
		list.add(customer.getRepairRemark());
		list.add(customer.getConsEndDate());
		list.add(customer.getIsUpPosiPic());
		list.add(customer.getCode());
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 客户付款明细查询
	 * 
	 * @param page
	 * @param custPay
	 * @return
	 */
	public Page<Map<String,Object>> findDetailQueryBySql(Page<Map<String,Object>> page, CustPay custPay) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select  a.DocumentNo, a.Code, b.CustCode, tCt.Desc1 CustTypeDescr, a.Descr, a.Address, a.Status, "
				+ "d.NOTE StatusDescr, b.PayCheckOutNo, c.DocumentNo PayCheckOutDocumentNo, bp.Descr PosDescr, "
				+ "b.PayNo, a.EndCode, h.note EndCodeDescr, b.Date fkDate, b.Amount fkAmount, b.Remarks, "
				+ "b.LastUpdate, b.LastUpdatedBy, e.Descr RcvActDescr, AddDate, ProcedureFee, "
				+ "b.Amount - ProcedureFee ActualAmount, a.SetDate,tr.Descr RegionDescr "
				+ "from tCustPay b "
				+ "left outer join tCustomer a on a.Code = b.CustCode "
				+ "left outer join tPayCheckOut c on c.no = b.PayCheckOutNo "
				+ "left outer join tCustType tCt on a.CustType = tCt.Code "
				+ "left outer join tXTDM d on a.Status = d.CBM "
				+ "and d.ID = 'CUSTOMERSTATUS' "
				+ "left outer join tXTDM h on a.EndCode = h.CBM "
				+ "and h.ID = 'CUSTOMERENDCODE' "
				+ "left outer join tRcvAct e on b.RcvAct = e.Code "
				+ "left outer join tBankPos bp on b.PosCode = bp.Code "
				+ "left outer join tBuilder tb on a.BuilderCode=tb.code "
				+ "left outer join tRegion tr on tb.RegionCode=tr.code "
				+ "where 1=1 ";
		if (StringUtils.isNotBlank(custPay.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + custPay.getAddress() + "%");
		}
		if(custPay.getDateFrom()!=null){
			sql += " and b.Date >= ? ";
			list.add(custPay.getDateFrom());
		}
		if(custPay.getDateTo()!=null){
			sql += " and b.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custPay.getDateTo()).getTime()));
		}
		if(custPay.getLastUpdateFrom()!=null){
			sql += " and b.LastUpdate >= ? ";
			list.add(custPay.getLastUpdateFrom());
		}
		if(custPay.getLastUpdateTo()!=null){
			sql += " and b.LastUpdate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custPay.getLastUpdateTo()).getTime()));
		}
		if(custPay.getAddDateFrom()!=null){
			sql += " and b.AddDate >= ? ";
			list.add(custPay.getAddDateFrom());
		}
		if(custPay.getAddDateTo()!=null){
			sql += " and b.AddDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custPay.getAddDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custPay.getRcvAct())) {
			sql += " and b.RcvAct in ("+SqlUtil.resetStatus(custPay.getRcvAct())+")";
		}
		if (StringUtils.isNotBlank(custPay.getType())) {
			sql += " and b.Type=? ";
			list.add(custPay.getType());
		}
		if (StringUtils.isNotBlank(custPay.getCustType())) {
			String str = SqlUtil.resetStatus(custPay.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(custPay.getIsCheckOut())) {
			sql += " and b.IsCheckOut=? ";
			list.add(custPay.getIsCheckOut());
		}
		
		if (StringUtils.isNotBlank(custPay.getPayCheckoutStatus())) {
            sql += " and c.Status = ? ";
            list.add(custPay.getPayCheckoutStatus());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 保存对公退款
	 * @author	created by zb
	 * @date	2018-12-7--下午5:25:31
	 * @param isPubReturn
	 * @param custCode 
	 * @return
	 */
	public Boolean doIsPubReturnSave(String isPubReturn, String custCode) {
		Long result = (long) 0;
		try {
			String sql = " update tCustTax set IsPubReturn = ? where CustCode = ? ";
			result = this.executeUpdateBySql(sql, new Object[]{isPubReturn, custCode});
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 客户增减信息按照时间排序（工程进度拖延分析查看用）
	 * @author	created by zb
	 * @date	2019-11-8--下午3:53:32
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findChgInfoOrderDatePageBySql(Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select * from (select a.date,b.SM ItemType1Descr,a.amount from tItemChg a "
				+ "left outer join txtcs b on b.id=a.ItemType1 "
				+ "where status='2' and a.CustCode=?)a "
				+ "union all "
				+ "select * from (select date,'基础' ItemType1Descr,amount from tBaseItemChg "
				+ "where status='2' and CustCode=?)b "
				+ "union all "
				+ "select * from(select date,'合同费用' ItemType1Descr,ChgAmount amount from tConFeeChg "
				+ "where status='CONFIRMED' and CustCode=?)c )d "
				+ "order by d.Date ";
		list.add(customer.getCode());
		list.add(customer.getCode());
		list.add(customer.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 客户付款批量打印JqGrid
	 * @author cjg
	 * @date 2020-1-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageByQPrintSql(Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.Code CustCode,a.Address,a.DocumentNo,a.Descr CustDescr,b.Desc1 CustTypeDescr, "
				+"c.NOTE EndCodeDescr,d.NOTE StatusDescr,a.SignDate,a.ToConstructDate, g.Desc2 CompanyDescr " 
				+"from tCustomer a " 
				+"left join tCusttype b on a.CustType=b.Code "
				+"left join tXTDM c on a.EndCode=c.CBM and c.ID='CUSTOMERENDCODE' "
				+"left join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+"left join tBuilder e on a.BuilderCode = e.Code "
				+"left join tRegion f on e.RegionCode = f.Code "
				+"left join tCompany g on f.CmpCode = g.Code " 
				+"left join tTaxPayee h on h.code = a.PayeeCode "
				+"where 1=1 ";
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+="and a.Address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		
		if (StringUtils.isNotBlank(customer.getCompany())) {
		    sql += "and h.Code = ? ";
		    list.add(customer.getCompany());
		}
		
		if(StringUtils.isNotBlank(customer.getDocumentNoFrom())){
			sql+="and a.DocumentNo>=? ";
			list.add(customer.getDocumentNoFrom());
		}
		if(StringUtils.isNotBlank(customer.getDocumentNoTo())){
			sql+="and a.DocumentNo<=? ";
			list.add(customer.getDocumentNoTo());
		}
		if(StringUtils.isNotBlank(customer.getStatus())){
			sql+="and a.Status in ("+SqlUtil.resetStatus(customer.getStatus())+") ";
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql+="and a.CustType in ("+SqlUtil.resetStatus(customer.getCustType())+") ";
		}
		if(customer.getToConstructDateFrom()!=null){
			sql+="and a.ToConstructDate>=? ";
			list.add(customer.getToConstructDateFrom());
		}
		if(customer.getToConstructDateTo()!=null){
			sql+="and a.ToConstructDate<dateadd(d,1,?) ";
			list.add(customer.getToConstructDateTo());
		}
		if(customer.getSignDateFrom()!=null){
			sql+="and a.SignDate>=? ";
			list.add(customer.getSignDateFrom());
		}
		if(customer.getSignDateTo()!=null){
			sql+="and a.SignDate<dateadd(d,1,?) ";
			list.add(customer.getSignDateTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 获取客户已付款金额
	 * @param custCode 
	 * @return type 1设计定金，2工程款
	 */
	public double getPayDesignFee(String custCode, String type) {
		String sql = " select isnull(sum(amount),0.0) amount from tCustPay where CustCode=? and type=? "; 
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {custCode,type});
		if (list != null && list.size() > 0) {
			return Double.valueOf(list.get(0).get("amount").toString());
		} else {
			return 0.0;
		}
	}
	

	public String getPayTimesByCustCode(String custCode){
		
		String sql = " select case when (isnull(count(*),0)+1)<10 then '0' else '' end + cast(isnull(count(*),0)+1 as nvarchar(10))  payTimes from tCustPay where  CustCode = ? "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {custCode});
		if (list != null && list.size() > 0) {
			return list.get(0).get("payTimes").toString();
		} else {
			return "00";
		}
	}
	
	public List<Map<String, Object>> getPayInfo(CustPay custPay){
		
		String sql = " select * from twfCUst_CustPayConfirm a" +
				" left join tWfProcInst b on b.No = a.WfProcInstNo" +
				" where a.PayDate = ? and " +
				"	(a.ReceiveActName = ? or a.RcvActCode = ? ) and a.PayAmount = ? and a.PayActName = ? " +
				" and (b.IsPass = '1' or ('1' = ? and b.status<>'3' and (b.isPass <> '0' or b.isPass is null) )) "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {custPay.getPayDate(),custPay.getReceiveActName(),custPay.getRcvAct(),
															custPay.getPayAmount(),custPay.getPayActName(),custPay.getFlag()});
		if (list != null && list.size() > 0) {
			return list;
		}
		
		return null;
	}
	
	public Page<Map<String, Object>> findProcListJqGrid(
            Page<Map<String, Object>> page, CustPay custPay) {
        
        List<Object> parameters = new ArrayList<Object>();
        String sql = "select * from (" +
        		" select a.wfprocinstno,EmpCode,EMpName,Phone,CustCode,CustDescr,Address, PayDate+' '+PayTime paydate,ReceiveActName," +
        		" PayActName,PayAmount,payDetail from tWfCust_CustPayConfirm a " +
        		" left join tWfProcInst b on b.No = a.WfProcInstNo "+
        		" where 1 = 1  and (a.PayDate = ? " +
                " and a.ReceiveActName = ? and a.PayAmount = ? and a.PayActName = ? )";
        
        parameters.add(custPay.getPayDate());
        parameters.add(custPay.getReceiveActName());
        parameters.add(custPay.getPayAmount());
        parameters.add(custPay.getPayActName());
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a ";
        }
        
        return this.findPageBySql(page, sql, parameters.toArray());
    }
	
	/**
	 * 付款信息导入调过程
	 * @param custTypeItem
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (CustPay custPay) {
		Assert.notNull(custPay);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustPay_import(?,?,?,?)}");
			call.setString(1, custPay.getLastUpdatedBy());
			call.setString(2, custPay.getDetailXml());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Page<Map<String, Object>> findPayBillQueryBySql(
			Page<Map<String, Object>> page, CustPay custPay) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( " 
				+" select PK,Convert(varchar(100),Date,120)+' '+x1.NOTE+'转账:'+cast(Amount as nvarchar(10))+'元' Descr,payno,getdate() PrintDate,a.Date,a.Amount,x1.NOTE TypeDescr " 
				+" from tCustPay a " 
				+" left join tXTDM x1 on x1.ID='CPTRANTYPE' and a.Type=x1.CBM  "
				+" where 1=1  and isnull(PrintCZY,'')='' and isnull(a.WfProcInstNo,'')<>'' ";    
		if(StringUtils.isNotBlank(custPay.getCustCode())){
			sql+=" and a.CustCode = ? ";
			list.add(custPay.getCustCode());
		}
		if(custPay.getPk() !=null ){
			sql+=" and a.pk = ? ";
			list.add(custPay.getPk());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Double getProcConfirmAmount(String no){
		String sql = "select sum(ConfirmAmount)ConfirmAmount from tWfCust_PrjRefundDtl where WfProcInstNo = ? group by WfProcInstNo" +
				"	union" +
				"	select sum(ConfirmAmount)ConfirmAmount from tWfCust_PrjReturnOrderDtl where WfProcInstNo = ? group by WfProcInstNo" +
				"	union" +
				"	select sum(ConfirmAmount)ConfirmAmount from tWfCust_PrjReturnSetDtl where WfProcInstNo = ? group by WfProcInstNo"; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {no, no, no});
		if (list != null && list.size() > 0 && list.get(0).get("ConfirmAmount") != null 
				&& StringUtils.isNotBlank(list.get(0).get("ConfirmAmount").toString())) {
			return Double.parseDouble(list.get(0).get("ConfirmAmount").toString());
		}
		return 0.0;
	}
}

