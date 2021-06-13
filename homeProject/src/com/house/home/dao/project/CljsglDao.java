package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemCheck;

@Repository
@SuppressWarnings("serial")
public class CljsglDao extends BaseDao{
	
	/**
	 * 材料结算管理
	 * */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemCheck itemCheck ,String itemRight) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select b.CheckStatus ,a.No,a.CustCode,b.DocumentNo,b.Address,b.Area,b.Status CustStatus,c.NOTE CustStatusDescr,b.Descr CustDescr," +
				" a.ItemType1,d.Descr ItemType1Descr,a.Status,e.NOTE StatusDescr,a.AppEmp,f.NameChi AppEmpDescr, a.Date,a.AppRemark,a.ConfirmEmp," +
				"g.NameChi ConfirmEmpDescr,a.ConfirmDate,a.ConfirmRemark, a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.IsItemUp," +
				"x1.Note IsItemUpDescr,b.EndDate,b.CustCheckDate from tItemCheck a " +
				" left join tCustomer b on b.Code=a.CustCode " +
				" left join tXTDM c on c.ID='CUSTOMERSTATUS' and c.CBM=b.Status " +
				" left join tItemType1 d on d.Code=a.ItemType1 " +
				" left join tXTDM e on e.ID='ITEMCHECKSTATUS' and e.CBM=a.Status " +
				" left join tEmployee f on f.Number=a.AppEmp " +
				" left join tEmployee g on g.Number=a.ConfirmEmp " +
				" left join tEmployee h on h.Number=b.DesignMan " +
				" left join tXTDM x1 on b.IsItemUp=x1.cbm and x1.id='YESNO'  " +
				" where a.Expired='F' and 1=1 ";
		if(StringUtils.isNotBlank(itemRight)){
			sql += " and d.code in " + "('"+itemRight.replace(",", "','" )+ "')";
			
		}
		if (StringUtils.isNotBlank(itemCheck.getCustCode())) {
			sql += " and a.custCode=? ";	
			list.add(itemCheck.getCustCode());
		}
		if (StringUtils.isNotBlank(itemCheck.getItemType1())) {
			sql += " and a.ItemType1=? ";	
			list.add(itemCheck.getItemType1());
		}	
		if (StringUtils.isNotBlank(itemCheck.getDepartment1())) {
			sql += " and h.Department1 in " + "('"+itemCheck.getDepartment1().replace(",", "','" )+ "')";
		}
		if (!"".equals(itemCheck.getAppDateFrom())&&(itemCheck.getAppDateFrom()!=null)){
			sql += " and a.date>= ? ";	
			list.add(itemCheck.getAppDateFrom());			
		}
		if (!"".equals(itemCheck.getAppDateTo())&& (itemCheck.getAppDateTo()!=null)){
			sql += " and a.date< dateadd(d,1,?) ";	
			list.add(itemCheck.getAppDateTo());			
		}
		if (itemCheck.getCustCheckDateFrom() != null) {
			sql += " and b.CustCheckDate>= ? ";
			list.add(itemCheck.getCustCheckDateFrom() );
		}
		if (itemCheck.getCustCheckDateTo()!= null) {
			sql += " and b.CustCheckDate< ? ";
			list.add(DateUtil.addInteger(itemCheck.getCustCheckDateTo(), Calendar.DATE, 1));
		}
		if (itemCheck.getEndDateFrom() != null) {
			sql += " and b.endDate>= ? ";
			list.add(itemCheck.getEndDateFrom() );
		}
		if (itemCheck.getEndDateTo()!= null) {
			sql += " and b.endDate< ? ";
			list.add(DateUtil.addInteger(itemCheck.getEndDateTo(), Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemCheck.getCustType())) {
			sql += " and b.CustType in " + "('"+itemCheck.getCustType().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(itemCheck.getStatus())) {
			sql += " and a.Status in " + "('"+itemCheck.getStatus().replace(",", "','" )+ "')";
		}		
		if (StringUtils.isNotBlank(itemCheck.getCustStatus())) {
			String str = SqlUtil.resetStatus(itemCheck.getCustStatus());
			sql += " and b.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料结算确认
	 * */
	public Page<Map<String, Object>> findPageBySqlCljsqr(
			Page<Map<String, Object>> page, ItemCheck itemCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select a.ItemCode,b.ItemType1,t1.Descr ItemType1Descr,b.ItemType2,t2.Descr ItemType2Descr,a.FixAreaPK,t3.Descr FixAreaDescr,"
				+" b.Descr ItemDescr,b.Uom,u.Descr UnitDescr,a.Qty,a.SendQty,a.Qty-a.SendQty as lessQty,a.UnitPrice,a.Cost,a.Remark, "
		        +"t2.ItemType12, c.Descr ItemType12Descr "
		        +"from tItemReq a "
				+"left outer join tItem b on a.ItemCode=b.Code  "
				+"left outer join tUOM u on u.Code = b.UOM  "
				+"left outer join tItemType1 t1 on b.ItemType1=t1.Code  "
				+"left outer join tItemType2 t2 on b.ItemType2=t2.Code  "
				+"left outer join tFixArea t3 on a.FixAreaPK=t3.PK  "
				+"left outer join tItemType12 c on t2.ItemType12 = c.Code "
				+"where 1=1 and a.Expired='F' and a.qty <> a.sendQty ";
		if (StringUtils.isNotBlank(itemCheck.getCustCode())) {
			sql += " and a.custCode=? ";	
			list.add(itemCheck.getCustCode());
		}
		if (StringUtils.isNotBlank(itemCheck.getItemType1())) {
			sql += " and a.ItemType1=? ";	
			list.add(itemCheck.getItemType1());
		}			
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by b.ItemType1,a.ItemCode ";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料结算优惠分摊
	 * */
	public Page<Map<String, Object>> findPageBySqlCljsDiscCost(
			Page<Map<String, Object>> page, ItemCheck itemCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select  0 IsCheck, a.PK, a.CustCode, a.FixAreaPK, a.IntProdPK, a.ItemCode, a.ItemType1, a.Qty, a.SendQty, a.Cost, "
			        + " a.UnitPrice, a.BefLineAmount, a.Markup, a.LineAmount, d.Remark, a.LastUpdate, a.LastUpdatedBy, a.Expired, " 
			        + " a.ActionLog, a.ProcessCost, a.DispSeq, a.IsService,  round(a.Qty * a.UnitPrice * a.Markup / 100, 0) TmpLineAmount, a.IsCommi, a.DiscCost, a.IsOutSet, "
			        + " b.Descr FixAreaDescr, e.Descr Uom, d.IsFixPrice, d.MarketPrice, f.descr ItemType1descr, d.Descr ItemDescr, "
			        + " g.descr ItemType2descr "
			        + " from    tItemReq a "
			        + " left outer join tFixArea b on b.PK = a.FixAreaPK "
			        + " left outer join tItem d on d.Code = a.ItemCode "
			        + " left outer join tBrand br on br.code = d.sqlCode "
			        + " left outer join tUom e on d.Uom = e.Code "
			        + " left outer join tItemType1 f on f.Code = d.ItemType1 "
			        + " left outer join tItemType2 g on g.Code = d.ItemType2 "
			        + " where 1=1 " ;
		if (StringUtils.isNotBlank(itemCheck.getCustCode())) {
			sql += " and a.custCode=? ";	
			list.add(itemCheck.getCustCode());
		}
		if (StringUtils.isNotBlank(itemCheck.getItemType1())) {
			sql += " and a.ItemType1=? ";	
			list.add(itemCheck.getItemType1());
		}			
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料结算明细—查看
	 * */
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select c.Code CustCode,c.Descr CustDescr,c.Address,d1.Code Dept1Code,d1.Desc2 Dept1Descr, " +
				" round((1.0/num),2) CustCount,round((Area*1.0/num),2) Area, round((isnull(d.LineAmount,0)/num),2) LineAmount," +
				" round((isnull(d.AllCost,0)/num),2) AllCost, round((isnull(d.LineAmount,0)/num)-(isnull(d.AllCost,0)/num),2) Profit  " +
				" from tCustomer c " +
				" inner join tCustStakeholder cs on c.Code=cs.CustCode inner join ( 	select CustCode, Role, count(1) num from tCustStakeholder 	" +
				" where Expired='F' and Role='00' group by CustCode, Role ) cs2 on cs2.CustCode = cs.CustCode and cs2.Role = cs.Role " +
				" inner join tEmployee e on e.Number=cs.EmpCode inner join tDepartment1 d1 on d1.Code=e.Department1 " +
				" inner join tItemType1 it1 on it1.Code='"+customer.getItemType1() +"'"+
				" inner join (   select ir.CustCode,sum(ir.LineAmount) LineAmount,sum(ir.Qty*ir.Cost+ir.ProcessCost+ir.DiscCost) AllCost  	" +
				" from tItemReq ir 	where ir.IsService=0 and ir.ItemType1='"+customer.getItemType1()+"' and ir.Qty<>0  	group by ir.CustCode ) d on d.CustCode=c.Code " +
				" where 1=1 ";
		if (StringUtils.isNotBlank(customer.getDepartment1())) {
			sql += " and d1.Code=? ";	
			list.add(customer.getDepartment1());
		}		
		if (!"".equals(customer.getCustCheckDateFrom())){
			sql += " and c.CustCheckDate>= ? ";	
			list.add(customer.getCustCheckDateFrom());			
		}
		if (!"".equals(customer.getCustCheckDateTo())){
			sql += " and c.CustCheckDate< dateadd(day,1,?) ";	
			list.add(customer.getCustCheckDateTo());			
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and c.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and e.Department2= ? ";	
			list.add(customer.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by c.CustCheckDate";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料结算管理保存操作
	 * */
	@SuppressWarnings("deprecation")
	public Result docljsglCheckOut(ItemCheck itemCheck) {
		Assert.notNull(itemCheck);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCljsgl_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemCheck.getM_umState());
			call.setString(2, itemCheck.getNo());
			call.setString(3, itemCheck.getCustCode());
			call.setString(4, itemCheck.getItemType1());
			call.setString(5, itemCheck.getAppRemark());
			call.setString(6, itemCheck.getAppEmp());
			call.setString(7, itemCheck.getConfirmRemark());
			call.setString(8, itemCheck.getConfirmEmp());
			call.setString(9, itemCheck.getLastUpdatedBy());
			call.setString(10, itemCheck.getExpired());
			call.setString(11, itemCheck.getIsItemUp());
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
	
	@SuppressWarnings("deprecation")
	public Result doCljsglDiscCost(ItemCheck itemCheck) {
		Assert.notNull(itemCheck);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCljsgl_DiscCost_forXml(?,?,?,?,?,?,?)}");
			call.setString(1, itemCheck.getItemType1());
			call.setString(2, itemCheck.getCustCode());
			call.setFloat(3, itemCheck.getDiscCost());
			call.setString(4, itemCheck.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, XmlConverUtil.jsonToXmlNoHead(itemCheck.getSalesInvoiceDetailJson()));
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	public Map<String,Object> IsContinueWhenReqQtyNotEqualSendQty(String custCode,String itemType1){
		String sql="select count(1) ret from tItemReq t where 1=1 and t.Expired='F' and t.qty <> t.sendQty and t.CustCode=? and t.ItemType1=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,itemType1});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	public boolean getHasNotConfirmedItemChg(String custCode,String itemType1){
		String sql="select 1 from tItemChg ic"
                 + " where ic.Status<>'2' and ic.Status<>'3'" //Status: 变更单状态(1.已申请 2.已审核 3.已取消)
                 + " and ic.CustCode= ? " 
                 + " and ic.ItemType1= ? " ;
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,itemType1});
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	
}
