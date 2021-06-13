package com.house.home.dao.finance;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CustTax;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
@Repository
public class CustTaxDao extends BaseDao{

	/**
	 * @Description: TODO 税务信息登记分页查询
	 * @author	created by zb
	 * @date	2018-8-10--下午4:54:30
	 * @param page
	 * @param custTax
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustTax custTax, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		//ContractFeeRepType
		String sql = " select a.*,case when a.ThisDesignAmount<0 or istaxcomplete='是' then 0 else a.ThisDesignAmount end ThisDesignAmount_overZero,case when a.ThisPrjAmount<0 or istaxcomplete='是' then 0 else a.ThisPrjAmount end ThisPrjAmount_overZero " +
				" from (select * from (select a.*,case when a.EndCode='4' and a.paidamount=a.sumDesignAmount+a.sumPrjAmount then '是' when a.EndCode<>'4' " +
				" and a.custCountCost<>0 and a.custCountCost=a.sumDesignAmount+a.sumPrjAmount then '是' else '否' end istaxcomplete, " +
				" case when a.CheckStatus in('2','3','4') then a.DesignFee-a.sumDesignAmount else " +
				" case when a.PaidNum='1' or a.PaidNum='0' then 0 when a.PaidNum='2' then a.DesignFee-a.sumDesignAmount " +
				" when a.PaidNum='3' then a.DesignFee-a.sumDesignAmount else 0 end end ThisDesignAmount,  " +
				" case when a.CheckStatus in('2','3','4') then a.PaidAmount-a.DesignFee-a.sumPrjAmount else case when a.PaidNum='1' then 0  " +
				" when a.PaidNum='2' then a.FirstPay-a.DesignFee-a.sumPrjAmount " +
				" when a.PaidNum='3' then a.FirstPay+a.SecondPay-a.DesignFee-a.sumPrjAmount " +
				" when a.PaidNum='4' and a.sumPrjAmount=0 then a.FirstPay+a.SecondPay-a.DesignFee-a.sumPrjAmount " +
				" else 0 end end ThisPrjAmount,ContractFeeAddedTax+CustDesignFee TotalContractFee  " +
				" from (select b.EndCode EndCode,a.DocumentNo, b.Descr, b.Address, b.CustType, tx1.NOTE CustTypeDescr, b.Status, tx2.NOTE StatusDescr, " +
				" a.PayeeCode, c.Descr PayeeCodeDescr, a.LaborCompny,lc.Descr LaborCompnyDescr, a.LaborAmount, a.IsPubReturn, tx6.NOTE IsPubReturnDescr, " +
				" a.ReturnAmount, a.CustCode, b.ContractFee, b.ContractFee + b.Tax -case when ct.ContractFeeRepType='2' then b.ReturnDesignFee else 0 end ContractFeeAddedTax, " +
				" b.ConstructType, tx3.NOTE ConstructTypeDescr, b.SetDate, b.SignDate, " +
				" b.ConfirmBegin, b.CheckStatus, tx4.NOTE CheckStatusDescr, b.CustCheckDate, d.Adress, b.Layout, tx5.NOTE LayoutDescr, " +
				" b.Area, b.StdDesignFee, b.BaseFee, b.BaseDisc, b.MainFee, b.IntegrateFee, b.CupboardFee, b.SoftFee, " +
				" b.MainServFee," +
				" case when b.CheckStatus = '1' then 0 else isnull(b.ContractFee,0) + isnull(b.DesignFee,0) + " +
				"    isnull(bic.SumChgAmount,0) + isnull(ic.SumChgAmount,0) + isnull(cfc.SumChgAmount, 0) + b.Tax end custCountCost, " +
				" isnull(ci.sumDesignAmount,0) sumDesignAmount,isnull(ci.sumPrjAmount,0) sumPrjAmount,a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, " +  		// 已结算的不计算合同变更设计费
				" ct.CmpnyName, b.ContractDay, d.RegionCode, e.Descr RegionDescr, case when ct.ContractFeeRepType='1' then b.DesignFee else b.StdDesignFee end + case when b.CheckStatus = '1' then isnull(cdf.ChgDesignFee, 0)else 0 end DesignFee, " +//应开票设计费
				" case when ct.ContractFeeRepType='1' then b.DesignFee else b.StdDesignFee end CustDesignFee,"+//设计费
				" isnull(bic.SumChgAmount,0) + isnull(ic.SumChgAmount,0) + isnull(cfc.SumChgAmount,0) SumChgAmount, " + // 增加产品线归属字段
				" isnull(f.PaidAmount,0)PaidAmount, b.FirstPay, b.SecondPay, b.ThirdPay, b.FourPay, b.DesignMan, " +
				" g.NameChi DesignDescr, b.BusinessMan, g2.NameChi BusinessDescr, b.AgainMan, g3.NameChi AgainDescr, tx7.NOTE CanCancelDescr, " + //add by zb 20181121
				//" case when isnull(f.PaidAmount,0)>=b.FirstPay and isnull(f.PaidAmount,0)<b.FirstPay+b.SecondPay then '1' when isnull(f.PaidAmount,0)>=b.FirstPay+b.SecondPay and isnull(f.PaidAmount,0)<b.FirstPay+b.SecondPay+b.ThirdPay then '2' when isnull(f.PaidAmount,0)>=b.FirstPay+b.SecondPay+b.ThirdPay then '3' else '0' end PaidNum "+
				" case when b.FourPay>0 and isnull(f.PaidAmount,0)>=b.DesignFee+b.FirstPay+b.SecondPay+b.ThirdPay+b.FourPay then '4'" +
				"      when b.ThirdPay>0 and isnull(f.PaidAmount,0)>=b.DesignFee+b.FirstPay+b.SecondPay+b.ThirdPay then '3'" +
				"	   when b.SecondPay>0 and isnull(f.PaidAmount,0)>=b.DesignFee+b.FirstPay+b.SecondPay then '2'" +
				"	   when b.FirstPay>0 and isnull(f.PaidAmount,0)>=b.DesignFee+b.FirstPay then '1'" +
				"	   else '0' end PaidNum " +
				" from tCustTax a " +
				" left join tCustomer b on b.Code = a.CustCode " +
				" left join tCusttype ct on ct.Code = b.CustType " +
				" left join tTaxPayee c on c.Code = a.PayeeCode " +
				" left join tBuilder d on d.Code = b.BuilderCode " +
				" left outer join (select sum(Amount) SumChgAmount,CustCode from tBaseItemChg where Status='2' group by CustCode ) bic on bic.CustCode=b.Code " +
				" left outer join (select sum(Amount) SumChgAmount,CustCode from tItemChg where Status='2' group by CustCode ) ic on ic.CustCode=b.Code " +
				" left outer join (select sum(ChgAmount) SumChgAmount,CustCode from tConFeeChg where status='CONFIRMED' and ChgType<>'3' group by CustCode ) cfc " +
				" on cfc.CustCode=b.Code " +
				" left join (" +
				"   select sum(case when TaxService='设计费' then Amount else 0 end) sumDesignAmount," +
				"   sum(case when TaxService='工程款' then Amount else 0 end) sumPrjAmount, custCode " +
				"   from tCustInvoice " +
				"   where expired = 'F' " +
				"   group by CustCode" +
				" ) ci on ci.CustCode = b.Code " +
				" left join (" +
				"   select CustCode,sum(ChgAmount) ChgDesignFee " +
				"   from tConFeeChg " + 
				"   where ChgType='1' and Status='CONFIRMED' " +
				"   group by CustCode " +
				" ) cdf on a.CustCode=cdf.CustCode "+
				" left join tXTDM tx1 on tx1.CBM = b.CustType and tx1.ID = 'CUSTTYPE' " +
				" left join tXTDM tx2 on tx2.CBM = b.Status and tx2.ID = 'CUSTOMERSTATUS' " +
				" left join tXTDM tx3 on tx3.CBM = b.ConstructType and tx3.ID = 'CONSTRUCTTYPE' " +
				" left join tXTDM tx4 on tx4.CBM = b.CheckStatus and tx4.ID = 'CheckStatus' " +
				" left join tXTDM tx5 on tx5.CBM = b.Layout and tx5.ID = 'LAYOUT' " +
				" left join tXTDM tx6 on tx6.CBM = a.IsPubReturn and tx6.ID = 'YESNO' " +
				" left join tXTDM tx7 on tx7.CBM = b.CanCancel and tx7.ID = 'YESNO'" +
				" left join tLaborCompny lc on lc.Code = a.LaborCompny" +//劳务分包公司信息配置到劳务分包公司表
				" left join tRegion e on e.Code = d.RegionCode" +
				" left join (select  CustCode,sum(Amount) PaidAmount from tCustPay group by CustCode)f on f.CustCode = a.CustCode " +
				" left join tEmployee g on g.Number = b.DesignMan" +
				" left join tEmployee g2 on g2.Number = b.BusinessMan" +
				" left join tEmployee g3 on g3.Number = b.AgainMan" +
				" left join tPayRule pr on pr.PayType = b.PayType and pr.CustType=b.CustType " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(custTax.getExpired()) || "F".equals(custTax.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(customer.getDescr())) {
			sql += " and b.descr like ? ";
			list.add("%"+customer.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(custTax.getDocumentNo())) {
			sql += " and a.DocumentNo like ? ";
			list.add("%"+custTax.getDocumentNo()+"%");
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			sql += " and b.Status in ('"+customer.getStatus().replace(",", "','")+"') ";
		}
		if (customer.getSetDateFrom()!=null) {
			sql += " and b.SetDate >= ? ";
			list.add(customer.getSetDateFrom());
		}
		if(customer.getSetDateTo()!=null){
			sql += " and b.SetDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getSetDateTo()).getTime()));
		}
		if(customer.getSignDateFrom()!=null){
			sql += " and b.SignDate >= ? ";
			list.add(customer.getSignDateFrom());
		}
		if(customer.getSignDateTo()!=null){
			sql += " and b.SignDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getSignDateTo()).getTime()));
		}
		if (customer.getCustCheckDateFrom()!=null) {
			sql += " and b.CustCheckDate >= ? ";
			list.add(customer.getCustCheckDateFrom());
		}
		if(customer.getCustCheckDateTo()!=null){
			sql += " and b.CustCheckDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custTax.getPayeeCode())) {
			sql += " and a.PayeeCode = ? ";
			list.add(custTax.getPayeeCode());
		}
		if (StringUtils.isNotBlank(customer.getCheckStatus())) {
			sql += " and b.CheckStatus in ('"+customer.getCheckStatus().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(custTax.getLaborCompny())) {
			sql += " and a.LaborCompny in ('"+custTax.getLaborCompny().replace(",", "','")+"') ";
		}
		if (custTax.getLaborDateFrom()!=null) {
			sql += " and a.LaborDate >= ? ";
			list.add(custTax.getLaborDateFrom());
		}
		if(custTax.getLaborDateTo()!=null){
			sql += " and a.LaborDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custTax.getLaborDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and b.custtype in ('"+customer.getCustType().replace(",", "','")+"') ";
		}
		sql += " ) a ";
		sql += " ) a ";
		sql+=" )a where 1=1 ";
		if("1".equals(custTax.getUnShow())){
			sql+=" and a.ThisDesignAmount>0 or a.ThisPrjAmount>0 ";
		}
		if ("1".equals(custTax.getIsTaxComplete())) {
			sql += "and a.isTaxComplete='是' ";
		} else if("0".equals(custTax.getIsTaxComplete())) {
			sql += "and a.isTaxComplete='否' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate desc ";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description:  税务信息登记存储过程
	 * @author	created by zb
	 * @date	2018-8-14--上午10:14:07
	 * @param custTax
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(CustTax custTax) {
		Assert.notNull(custTax);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustTax(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, custTax.getCustCode());
			call.setString(2, custTax.getM_umState());
			call.setString(3, custTax.getDocumentNo());
			call.setString(4, custTax.getPayeeCode());
			call.setString(5, custTax.getLaborCompny());
			call.setDouble(6, custTax.getLaborAmount());
			call.setString(7, custTax.getIsPubReturn());
			call.setDouble(8, custTax.getReturnAmount());
			call.setString(9, custTax.getRemarks());
			call.setTimestamp(10, custTax.getLastUpdate()==null?null : new Timestamp(custTax.getLastUpdate().getTime()));
			call.setString(11, custTax.getLastUpdatedBy());
			call.setString(12, custTax.getExpired());
			call.setString(13, custTax.getActionLog());
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.NVARCHAR);
			call.setString(16, custTax.getDetailJson());
			call.setString(17, custTax.getOldCustCode());
			call.setString(18,custTax.getLaborJson());
			//System.out.println(custTax.getLaborJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(14)));
			result.setInfo(call.getString(15));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description:  发票明细查询
	 * @author	created by zb
	 * @date	2018-8-14--上午11:24:38
	 * @param page
	 * @param custTax
	 * @return
	 */
	public Page<Map<String, Object>> findDetailByCode(
			Page<Map<String, Object>> page, CustTax custTax) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * " +
				" from tCustInvoice " +
				" where CustCode = ? " +
				" and Expired = 'F' ";
		
		list.add(custTax.getCustCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by lastupdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: 发票查询jqGrid
	 * @author	created by zb
	 * @param page
	 * @param custTax
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findInvoicePageBySql(
			Page<Map<String, Object>> page, CustTax custTax, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from ( " +
				" select a.Date,b.Address,a.InvoiceNo,a.Amount,a.TaxPer, " +
				" isnull(a.Amount,0) / (1 + isnull(a.TaxPer,0)) NoTaxAmount, " +
				" isnull(a.Amount,0) * (1 - 1 / (1 + isnull(a.TaxPer,0))) TaxAmount,a.LastUpdate," +
				" d.Descr PayeeCodeDescr,lc.Descr LaborCompnyDescr,a.TaxService,c.LaborAmount " +
				" from tCustInvoice a " +
				" left join tCustomer b on b.Code = a.CustCode " +
				" left join tCustTax c on c.CustCode = b.Code " +
				" left join tTaxPayee d on d.Code = c.PayeeCode " +
				" left join tLaborCompny lc on lc.Code = c.LaborCompny" +
				" where 1=1 ";
		
		if (StringUtils.isBlank(custTax.getExpired()) || "F".equals(custTax.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if (custTax.getDateFrom() != null) {
			sql += " and a.Date >= ? ";
			list.add(custTax.getDateFrom());
		}
		if(custTax.getDateTo()!=null){
			sql += " and a.Date <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custTax.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custTax.getInvoiceNo())) {
			sql += " and a.InvoiceNo like ? ";
			list.add("%"+custTax.getInvoiceNo()+"%");
		}
		if (StringUtils.isNotBlank(custTax.getLaborCompny())) {
			sql += " and c.LaborCompny in ('"+custTax.getLaborCompny().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(custTax.getPayeeCode())) {
			sql += " and c.PayeeCode = ? ";
			list.add(custTax.getPayeeCode());
		}
		if (StringUtils.isNotBlank(custTax.getTaxService())) {
			sql += "and a.TaxService like ? ";
			list.add("%"+custTax.getTaxService().trim()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description:  显示付款信息记录
	 * @author	created by zb
	 * @date	2018-9-4--上午11:33:01
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String, Object>> findCustPayPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from(select b.CustCode,a.Descr,Address,a.FirstPay,a.SecondPay,a.thirdPay,a.FourPay, " +
				" a.ContractFee,b.Amount,b.Date,a.Code,b.LastUpdate,b.LastUpdatedBy, " +
				" b.Remarks,isnull(PK,0) as PK,b.IsCheckOut,b.PayCheckOutNo, " +
				" b.RcvAct,c.Descr RcvActDescr,b.PosCode,d.Descr PosDescr,b.ProcedureFee,AddDate,b.PayNo " +
				" from tCustomer a " +
				" inner join tCustPay b on b.CustCode = a.Code " +
				" left outer join tRcvAct c on b.RcvAct=c.Code " +
				" left outer join tBankPos d on b.PosCode=d.Code " +
				" where b.CustCode = ? ";
		
		list.add(custCode);
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 劳务分包开票信息
	 * @author cjg
	 * @date 2019-9-20
	 * @param page
	 * @param custTax
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findLaborPageBySql(Page<Map<String, Object>> page, CustTax custTax) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select date,amount,lastupdate,lastupdatedby,actionlog,expired from tCustLaborInvoice " +
				" where CustCode=? ";
		list.add(custTax.getCustCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 导入开票明细
	 * @author	created by zb
	 * @date	2019-12-10--下午3:29:04
	 * @param custTax
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doCustInvoice(CustTax custTax) {
		Assert.notNull(custTax);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pImportCustInvoice(?,?,?,?)}");
			call.setString(1, custTax.getLastUpdatedBy());
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.setString(4, custTax.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 导入劳务分包开票
	 * @param custTax
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doCustLaborInvoice(CustTax custTax) {
		Assert.notNull(custTax);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pImportCustLaborInvoice(?,?,?,?)}");
			call.setString(1, custTax.getLastUpdatedBy());
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.setString(4, custTax.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 劳务分包查询
	 * @param page
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String, Object>> goLaborCtrlListJqGrid(
			Page<Map<String, Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLaborCtrlList(?,?,?,?,?)}");
			call.setString(1, customer.getAddress().trim());
			call.setTimestamp(2, customer.getCustCheckDateFrom() == null ? null : new Timestamp(
					customer.getCustCheckDateFrom().getTime()));
			call.setTimestamp(3, customer.getCustCheckDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
			call.setString(4, customer.getPayeeCode());
			call.setString(5, customer.getCheckStatus());
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
	
	/**
	 * 查询劳务分包公司列表
	 * 
	 * @return
	 */
    public List<Map<String, Object>> findLaborCompanyList() {
        return findBySql("select * from tLaborCompny where Expired = 'F'",
                new Object[] {});
    }
}
