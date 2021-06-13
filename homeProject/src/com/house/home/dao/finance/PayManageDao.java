package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Types;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.SupplierPrepay;
import com.house.home.entity.finance.SupplierPrepayDetail;
import com.house.home.entity.insales.Supplier;

@SuppressWarnings("serial")
@Repository
public class PayManageDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;

	/**
	 * paymanage分页信息
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay) {
		List<Object> list = new ArrayList<Object>();
		
		String sql =  " select a.NO,a.ItemType1,d.Descr bItemType1, a.Type, b.note bType,a.Status,c.note bStatus, " +
				" a.Remarks,a.AppEmp,g.zwxm bAppEmp,a.AppDate,a.ConfirmEmp,  h.zwxm bConfirmEmp,a.ConfirmDate," +
				" a.payEmp,f.zwxm bpayEmp ,a.DocumentNo,a.PayDate, a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				" ,case when x2.note is null then x.note else x2.note end discapprovestatus, x.note ProcStatusDescr,l.no wfProcInstNo " +
				" from tSupplierPrepay a   " +
				" left outer join tXTDM b ON b.CBM=a.Type AND b.id='SPLPREPAYTYPE'  " +
				" left outer join tXTDM c ON c.CBM=a.Status AND c.ID='SPLPREPAYSTATUS'   " +
				" left outer join titemtype1 d on  d.code=a.ItemType1 " +
				" LEFT OUTER JOIN tCZYBM f ON f.CZYBH=a.PayEmp  " +
				" LEFT OUTER JOIN tCZYBM g ON g.CZYBH=a.AppEmp " +
				" LEFT OUTER JOIN tCZYBM h ON h.CZYBH=a.ConfirmEmp " +
				" left join (select max(WfProcInstNo) wfProcInstNo,a.RefNo from tWfCust_PurchaseAdvance a group by a.RefNo) k on k.RefNo = a.No "
                + " left join tWfProcInst l on l.No = k.WfProcInstNo " 
                + " left join ("
                + "  select max(pk) pk,WfProcInstNo from tWfProcTrack where actionType in ('4','5','6') group by  WfProcInstNo "
                + ")m on m.WfProcInstNo = l.no"
                + " left join tWfProcTrack n on m.pk = n.pk "
                + " left join tXtdm x2 on x2.id = 'PROCACTTYPE' and x2.cbm = n.ActionType "
                + " left join tXtdm x on x.id = 'WFPROCINSTSTAT' and x.cbm = l.status "
				+ " where 1=1 and a.expired='F'  ";							
		
    	if (StringUtils.isNotBlank(supplierPrepay.getDocumentNo())) {
			sql += " and a.DocumentNo like ? ";
			list.add("%"+supplierPrepay.getDocumentNo()+"%");
		}
    	
    	if (StringUtils.isNotBlank(supplierPrepay.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(supplierPrepay.getItemType1());
		}  
    	if (StringUtils.isNotBlank(supplierPrepay.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+supplierPrepay.getNo()+"%");
		}
    	
    	if (StringUtils.isNotBlank(supplierPrepay.getType())) {
			sql += " and a.Type = ? ";
			list.add(supplierPrepay.getType());
		} 
    	if (StringUtils.isNotBlank(supplierPrepay.getPuNo())) {
			sql += " and exists(select 1 from tSupplierPrepayDetail in_a where a.No = in_a.No and in_a.PUNo like ?) ";
			list.add("%"+supplierPrepay.getPuNo()+"%");
		} 
    	if (supplierPrepay.getAppDate()!=null) {
			sql += " and a.AppDate >= ? ";
			list.add(supplierPrepay.getAppDate());
		}
    	if (supplierPrepay.getAppdate1()!=null) {
			sql += " and a.AppDate < ? ";
			list.add(DateUtil.addDateOneDay(supplierPrepay.getAppdate1()));
		}
    	if (StringUtils.isNotBlank(supplierPrepay.getStatus())) {
			sql += " and a.Status in " + "('"+supplierPrepay.getStatus().replace(",", "','" )+ "')";
		}
    	if (StringUtils.isNotBlank(supplierPrepay.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(supplierPrepay.getItemType1());
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and a.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

	//编辑时候查询明细表
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, SupplierPrepayDetail supplierPrepayDetail) {
		List<Object> list = new ArrayList<Object>();
		 String sql = null;
		if ("1".equals(supplierPrepayDetail.getType())) {
			sql = "select a.PK,a.No,a.Supplier,b.Descr SplDescr,a.PUNo,a.PUNo punoFormat,a.Status,c.NOTE StatusDescr,"
					 + " a.Amount,a.AftAmount,b.PrepayBalance,a.Remarks,case when e.PK is null then b.CardId else e.RcvCardId end cardid," +
					 "		case when e.PK is null then b.Bank else e.RcvBank end Bank," +
					 "		case when e.PK is null then b.actname else e.RcvActName end actName,"
					 + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					 + " from tSupplierPrepayDetail a "
					 + " left join tXTDM c on a.Status=c.CBM and c.ID='SPLPDSTATUS' "
					 + " left join (select max(in_b.pk) pk,in_a.RefNo,in_b.SupplCode " 
					 + " 	from tWfCust_PurchaseAdvance in_a" 
					 + " 	left join tWfCust_PurchaseAdvanceDtl in_b on in_b.WfProcInstNo = in_a.WfProcInstNo " 
					 + "	left join tWfProcInst in_c on in_c.No = in_a.WfProcInstNo "
					 + "	where 1=1 "
					 + " 	group by in_a.RefNo, in_b.SupplCode"
					 + " ) d on d.SupplCode = a.Supplier and d.RefNo = a.No"
					 + " left join tWfCust_PurchaseAdvanceDtl e on e.PK = d.pk"
					 + " left join tSupplier b on case when d.SupplCode is null then a.Supplier else d.supplCode end = b.Code" 
					 + " where 1=1";
		}else if("2".equals(supplierPrepayDetail.getType())){
			sql =  "  select a.PK,a.No,c.Code Supplier ,c.Descr SplDescr,a.PUNo,a.PUNo punoFormat,a.Status,d.NOTE StatusDescr,"
              + " a.Amount,a.AftAmount,b.RemainAmount,b.FirstAmount,a.Remarks," +
              " case when g.PK is null then c.CardId else g.RcvCardId end cardid," +
              "		case when g.PK is null then c.Bank else g.RcvBank end Bank," +
              "		case when g.PK is null then c.actname else g.RcvActName end actName, "
              + " case when b.Type='S' then b.Amount else -b.Amount end+b.OtherCost+b.OtherCostAdj SumAmount, "
              + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,e.NOTE PUStatusDescr "
              + " from tSupplierPrepayDetail a "
              + " left join tPurchase b on a.PUNo=b.No "
              + " left join tXTDM d on a.Status=d.CBM and d.ID='SPLPDSTATUS' "
              + " left join tXTDM e on b.Status=e.CBM and e.ID='PURCHSTATUS' "
              + " left join (select max(in_b.pk) pk,in_a.RefNo,in_b.SupplCode " 
			  + " 	from tWfCust_PurchaseAdvance in_a" 
			  + " 	left join tWfCust_PurchaseAdvanceDtl in_b on in_b.WfProcInstNo = in_a.WfProcInstNo" 
			  + "	left join tWfProcInst in_c on in_c.No = in_a.WfProcInstNo "
			  + "	where 1=1 "
			  + " 	group by in_a.RefNo, in_b.SupplCode"
			  + " ) f on f.SupplCode = a.Supplier and f.RefNo = a.No"
			  + " left join tWfCust_PurchaseAdvanceDtl g on g.PK = f.pk"
			  + " left join tSupplier c on case when f.SupplCode is null then b.Supplier else f.supplCode end = c.Code "
              + " where 1=1";
		}
										  
		if (StringUtils.isNotBlank(supplierPrepayDetail.getNo())) {
			sql += " and a.No=? ";	
			list.add(supplierPrepayDetail.getNo());
		}
	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	
	//明细查询
	public Page<Map<String, Object>> findPageBySqlMxSelect(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		List<Object> list = new ArrayList<Object>();
		 String sql = null;
		
		sql = " select * from(select  a.No, a.ItemType1, a.Type, x1.NOTE TypeDescr, it1.Descr ItemType1Descr, a.AppDate, b.PK, d.Code Supplier,  "
			+ " case when a.Type = '1' then d.Descr " 
			+ "  else f.Descr "
			+ " end SplDescr, b.PUNo, b.Status, x2.NOTE StatusDescr, b.Amount, b.AftAmount, a.PayDate, b.Remarks, b.LastUpdate, "
			+ " b.LastUpdatedBy, b.Expired, b.ActionLog,a.DocumentNo,a.ConfirmDate  "
			+ " from    tSupplierPrepay a "
			+ "     inner join tSupplierPrepayDetail b on a.No = b.No "
			+ " left join tXTDM x1 on x1.ID = 'SPLPREPAYTYPE' "
			+ "                   and a.Type = x1.CBM "
			+ " left join tItemType1 it1 on a.ItemType1 = it1.Code "
			+ " left join tSupplier d on b.Supplier = d.Code"
			+ " left join tXTDM x2 on x2.ID = 'SPLPDSTATUS' "
			+ " and b.Status = x2.CBM"
			+ " left join tPurchase e on b.PUNo = e.No"
			+ " left join tSupplier f on e.Supplier = f.Code"
			+ " where 1=1";
		if (StringUtils.isNotBlank(supplierPrepay.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(supplierPrepay.getItemType1());
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and a.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
		if (StringUtils.isNotBlank(supplierPrepay.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+supplierPrepay.getNo()+"%");
		}
		if (StringUtils.isNotBlank(supplierPrepay.getPuNo())) {
			sql += " and  b.puNo like ? ";
			list.add("%"+supplierPrepay.getPuNo()+"%");
		}
		if (StringUtils.isNotBlank(supplierPrepay.getType())) {
			sql += " and a.Type = ? ";
			list.add(supplierPrepay.getType());
		}
		if (StringUtils.isNotBlank(supplierPrepay.getSplCode())) {
			sql += " and d.Code = ? ";
			list.add(supplierPrepay.getSplCode());
		}
		if (StringUtils.isNotBlank(supplierPrepay.getStatus())) {
			sql += " and a.Status in " + "('"+supplierPrepay.getStatus().replace(",", "','" )+ "')";
		}	
		if (supplierPrepay.getPayDateFrom() != null){
			sql += " and a.PayDate>= ? ";
			list.add(supplierPrepay.getPayDateFrom());
		}
    	if (supplierPrepay.getPayDateTo() != null){
			sql += " and a.PayDate< ? ";
			list.add(DateUtil.addDateOneDay(supplierPrepay.getPayDateTo()));
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a  order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate DESC ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//余额查询
	public Page<Map<String, Object>> findPageBySqlYeSelect(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		List<Object> list = new ArrayList<Object>();
		 String sql = null;
		
		sql = " select * from ( select a.Code,a.Descr,a.Address,a.Contact,a.Phone1,a.Phone2,a.Fax1,a.Fax2,a.Mobile1,a.Mobile2," +
				"a.Email1,a.Email2,a.ItemType1,b.Descr ItemType1Descr,a.IsSpecDay,c.NOTE IsSpecDayDescr,a.SpecDay," +
				"a.BillCycle,a.PrepayBalance,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, " +
				"isnull(d.SumFirstAmount,0) SumFirstAmount,a.PrepayBalance+isnull(d.SumFirstAmount,0) SumPrepay from tSupplier a  " +
				"left outer join (select * from tXTDM where ID='YESNO') c on a.IsSpecDay=c.CBM  " +
				"left outer join tItemType1 b on b.code=a.ItemType1  " +
				"left outer join (   select a.Supplier,sum(a.FirstAmount) SumFirstAmount from tPurchase a   " +
				"left join tSplCheckOut b on b.No=a.CheckOutNo   where  a.Status<>'CANCEL' and (a.IsCheckOut='0' or b.status='1') " +
				"group by a.Supplier ) d on d.Supplier=a.Code " +
				"where 1=1 and a.Expired='F'  ";				
		if (StringUtils.isNotBlank(supplierPrepay.getCode())) {
			sql += " and a.Code = ? ";
			list.add(supplierPrepay.getCode());
		}
		if (StringUtils.isNotBlank(supplierPrepay.getDescr())) {
			sql += " and  a.Descr like ? ";
			list.add("%"+supplierPrepay.getDescr()+"%");
		}

		if (StringUtils.isNotBlank(supplierPrepay.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(supplierPrepay.getItemType1());
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and a.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	
	//余额查询_变动明细表查询
	public Page<Map<String, Object>> findPageBySqlYeChangeSelect(
			Page<Map<String, Object>> page, Supplier supplier) {
		List<Object> list = new ArrayList<Object>();
		String sql = null;
		sql = "select a.PK, a.Date, a.SplCode, a.PrefixCode, b.Desc2 PrefixDesc, a.Document, a.TrsAmount, a.AftAmount," +
				" a.Remarks,a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog from tPrepayTransaction a " +
				"left join tPrefix b on a.PrefixCode = b.Prefix "+
				"where 1=1 and a.Expired='F'  ";
		
		if (StringUtils.isNotBlank(supplier.getCode())) {
			sql += " and a.SplCode = ? ";
			list.add(supplier.getCode());
		}
		if (supplier.getDateF() != null ) {
			sql += " and  a.Date >= ?";
			list.add(supplier.getDateF());
		}
		if (supplier.getDateT()!= null) {
			sql += " and a.Date < ?";
			list.add(DateUtil.addDateOneDay(supplier.getDateT()));
		}				
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doPayManageReturnCheckOut(SupplierPrepay supplierPrepay) {
		Assert.notNull(supplierPrepay);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pYfjgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, supplierPrepay.getM_umState());
			call.setString(2, supplierPrepay.getNo());
			call.setString(3, supplierPrepay.getItemType1());
			call.setString(4, supplierPrepay.getType());
			call.setString(5, supplierPrepay.getStatus());
			call.setString(6, supplierPrepay.getRemarks());
			call.setString(7, supplierPrepay.getAppEmp());
			call.setString(8, supplierPrepay.getConfirmEmp());
			call.setString(9, supplierPrepay.getPayEmp());
			call.setTimestamp(10, new java.sql.Timestamp(supplierPrepay.getPayDate().getTime()));
			call.setString(11, supplierPrepay.getDocumentNo());
			call.setString(12, supplierPrepay.getLastUpdatedBy());
			call.setString(13, supplierPrepay.getSupplierPrepayDetailXml());
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.NVARCHAR);
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
	
	public List<Map<String, Object>> getDetailOrderBySupplCode(SupplierPrepay sypplierPrepay){
		
		String sql=" select sum(a.Amount) PayAmount,b.Descr SupplDescr,a.Supplier,isnull(c.RcvBank,b.Bank) Bank,isnull(c.RcvcardId, b.CardId) CardId," +
				" isnull(c.RcvActName,b.ActName) ActName,min(a.pk)  " +
				" from tSupplierPrepayDetail  a" +
				" left join tSupplier b on b.Code = a.Supplier " +
				" left join (" +
				"	select b.* from (" +
				"		select max(b.PK) pk from twfcust_purchaseAdvance a" +
				"		left join twfcust_purchaseAdvanceDtl b on b.WfProcInstNo = a.WfProcInstNo" +
				"		where a.RefNo = ?" +
				"		group by b.SupplCode" +
				" 	) a " +
				" 	left join tWfCust_PurchaseAdvanceDtl b on b.PK = a.pk" +
				" ) c on c.SupplCode = a.Supplier " +
				" where a.No = ? " +
				" group by a.Supplier,b.Descr,isnull(c.RcvBank,b.Bank),isnull(c.RcvcardId, b.CardId),isnull(c.RcvActName,b.ActName)  order by min(a.pk) ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{sypplierPrepay.getNo(), sypplierPrepay.getNo()});
		
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public Page<Map<String, Object>> findProcListJqGrid(
            Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		" select WfProcInstNo, EmpCode, EmpName, RefNo, ApplyDate, ItemType1, Type, AdvanceAmount," +
        		"		AdvanceRemarks, DeptCode, DeptDescr, Remarks, b.ActProcDefId " +
        		" from tWfCust_PurchaseAdvance a " +
        		" left join tWfProcInst b on b.No = a.WfProcInstNo "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(supplierPrepay.getNo())) {
            sql += " and a.RefNo = ? ";
            parameters.add(supplierPrepay.getNo());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.WfProcInstNo desc";
        }
        
        return this.findPageBySql(page, sql, parameters.toArray());
    }
	
	public Page<Map<String, Object>> getSupplAccountJqGrid(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.RcvActName, a.RcvCardId, a.RcvBank from tWfCust_PurchaseAdvanceDtl a" +
				" left join tWfProcInst b on b.No = a.WfProcInstNo" +
				" where b.StartUserId = ?  and a.SupplCode = ? " +
				" and (isnull(a.RcvActName,'')<>'' or isnull(a.RcvCardId,'')<>'' or isnull(a.RcvBank,'')<>'')" +
				" group by a.RcvActName, a.RcvCardId, a.RcvBank ";
		list.add(supplierPrepay.getAppEmp());
		list.add(supplierPrepay.getSplCode());

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 采购记录
	 * @param page
	 * @param supplierPrepay
	 * @return
	 */
	public Page<Map<String, Object>> getPuJqGrid(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( "
					+"	select row_number() over(partition by ITCode order by Date desc) rowNum,* " 
					+"	from ( "
					+"		select a.ITCode,e.Descr,a.UnitPrice LastUnitPrice,b.UnitPrice ThisUnitPrice,a.PUNo,c.Date  "
					+"		from tPurchaseDetail a   "
					+"		inner join tPurchaseDetail b on a.ITCode = b.ITCode   "
					+"		left join tPurchase c on a.PUNo = c.No  "
					+"		left join tPurchase d on b.PUNo = d.No "
					+"		left join tItem e on a.ITCode = e.Code  "
					+"		where b.PUNo = ? and a.PUNo <> b.PUNo and c.Type = 'S' and c.ArriveDate < d.ArriveDate "
					+"	)a  "
					+")b where rowNum = 1 ";
		list.add(supplierPrepay.getPuNo());

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += "  ";
		}
		List<Map<String, Object>> result = this.findBySql(sql.toLowerCase(), list.toArray());
		page.setResult(result);
		page.setTotalCount(result.size());
		return page;
	}
	
	public String getPunos(String no, String supplCode){
		String puno = "";
		String sql=" select PUNo  from tSupplierPrepayDetail where No = ? and Supplier = ? and puno is not null and puno <> ''";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{no, supplCode});
		
		if(list!=null && list.size()>0){
			for(int i = 0; i< list.size(); i++){
				if(i==0){
					puno = list.get(i).get("PUNo").toString();
				} else {
					puno+=","+list.get(i).get("PUNo").toString();
				}
			}
			return puno;
		}
		return "";
	}
}

