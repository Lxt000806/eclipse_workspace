package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class MainSaleAnalyseDao extends BaseDao {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pMainSaleAnalyse(?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom()==null?null:new Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo()==null?null:new Timestamp(customer.getDateTo().getTime()));
			call.setString(3, customer.getRole());
			call.setString(4, customer.getStatistcsMethod());
			call.setString(5, customer.getItemType12());
			call.setString(6, customer.getCustType());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	public Page<Map<String, Object>> findItemPlanSql(Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select ip.CustCode,ip.ProjectQty,ip.Qty,case when ip.IsOutSet='1' then ip.UnitPrice else i.ProjectCost end UnitPrice,ip.ProcessCost,ip.Cost,"
		          + " case when ip.IsOutSet='1' then ip.BefLineAmount else i.ProjectCost*ip.Qty end BefLineAmount,"
		          + " ip.Markup,case when ip.IsOutSet='1' then ip.LineAmount else round(round(i.ProjectCost*ip.Qty*ip.Markup/100,0)+ip.ProcessCost,0) end LineAmount,"
		          + " round(ip.Qty* case when ip.IsOutSet='1' then ip.UnitPrice else i.ProjectCost end * ip.Markup/100,0) TmpLineAmount,"
		          + " ip.DispSeq,ip.Remark,ip.LastUpdate,ip.LastUpdatedBy,ip.ActionLog,ip.Expired,"
		          + " c.Address,fa.Descr FixAreaDescr,ip.ItemCode,i.Descr ItemDescr,"
		          + " u.Descr Uom,sql.Descr SqlCodeDescr,ip.Qty*ip.Cost TotalCost,x1.NOTE IsOutSetDescr,"
		          + " s.Descr SupplierDescr,e.NameChi EmpName,d1.Desc2 Dept1Descr "
		          + " from tItemPlan ip"
		          + " inner join tCustomer c on ip.CustCode=c.Code"
		          + " left join tCustStakeholder d on c.Code = d.CustCode "
		          + " inner join tItem i on ip.ItemCode=i.Code"
		          + " left join tBrand sql on sql.code = i.sqlcode"
		          + " left join tFixArea fa on fa.Pk = ip.FixAreaPK"
		          + " left join tUom u on i.Uom = u.Code"
		          + " left join tXTDM x1 on x1.ID='YESNO' and x1.CBM=ip.IsOutSet "
		          + " left join tEmployee e on d.EmpCode=e.Number "
		          + " left join tDepartment1 d1 on e.Department1=d1.Code "
		          + " left join tSupplier s on i.SupplCode=s.Code "
		          + " left join tItemType2 it2 on i.ItemType2 = it2.Code "
		          + " left join tCustType ct on c.CustType = ct.Code "
		          + " where c.SignDate is not null and ip.Expired='F' and ct.IsPartDecorate = '3' " 
		          + " and i.ItemType2 <> '0267' and ip.ItemType1 = 'ZC' "
		          + " and not exists ( "
		          + " 	select 1 from tXTCS in_a where in_a.ID = 'CmpCustCode' and ',' + in_a.QZ + ',' like '%' + c.Code + '%' "
				  + " ) " ;
		
		if(customer.getDateFrom()!=null){
			sql+=" and c.SignDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+="and c.SignDate < dateAdd(d,1,?)";
			list.add(customer.getDateTo());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql+=" and c.custType in "+ "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getItemType12())){
			sql+=" and it2.ItemType12 in "+ "('"+customer.getItemType12().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			sql+=" and i.SupplCode=? ";
			list.add(customer.getSupplierCode());
		}
		if(StringUtils.isNotBlank(customer.getEmpCode())){
			sql+=" and d.EmpCode = ?" ;
			list.add(customer.getEmpCode());
		}
		if(StringUtils.isNotBlank(customer.getRole())){
			sql+=" and d.Role = ?" ;
			list.add(customer.getRole());
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql+=" and e.Department1 = ?" ;
			list.add(customer.getDepartment1());
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 增减明细
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findChgDetailSql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = "select ic.No,c.Address,icd.ReqPK,icd.FixAreaPK,fa.Descr FixAreaDescr,"
          + " icd.ItemCode,i.Descr ItemDescr,u.Descr Uom,icd.Qty,"
          + " icd.Cost,case when icd.IsOutSet='1' then icd.UnitPrice else i.ProjectCost end UnitPrice,"
          + " case when icd.IsOutSet='1' then icd.BefLineAmount else i.ProjectCost*icd.Qty end BefLineAmount,icd.Markup,"
          + " case when icd.IsOutSet='1' then icd.LineAmount else round(round(i.ProjectCost*icd.Qty*icd.Markup/100,0)+icd.ProcessCost,0) end LineAmount,"
          + " icd.ProcessCost,(icd.Qty* case when icd.IsOutSet='1' then icd.UnitPrice else i.ProjectCost end * icd.Markup/100) TmpLineAmount,"
          + " icd.Remarks,icd.LastUpdate,icd.LastUpdatedBy,icd.ActionLog,icd.Expired,s.Descr SupplierDescr,"
          + " icd.Qty*icd.Cost TotalCost,x1.NOTE IsOutSetDescr,e.NameChi EmpName,d1.Desc2 Dept1Descr "
          + " from tItemChg ic"
          + " inner join tItemChgDetail icd on ic.No = icd.No"
          + " inner join tCustomer c on ic.CustCode=c.Code"
          + " left join tCustStakeholder d on c.Code = d.CustCode "
          + " inner join tItem i on icd.ItemCode = i.Code"
          + " left outer join tFixArea fa on fa.PK=icd.FixAreaPK "
          + " left outer join tBrand sql on sql.code=i.SqlCode"
          + " left outer join tUom u on i.Uom=u.Code"
          + " left join tXTDM x1 on x1.ID='YESNO' and x1.CBM=icd.IsOutSet "
          + " left join tEmployee e on d.EmpCode=e.Number "
          + " left join tDepartment1 d1 on e.Department1=d1.Code "
          + " left join tSupplier s on i.SupplCode=s.Code "
          + " left join tItemType2 it2 on i.ItemType2 = it2.Code "
          + " left join tCustType ct on c.CustType = ct.Code "
          + " where ic.Status = '2' and ic.expired='F' and ct.IsPartDecorate = '3' "
          + " and i.ItemType2 <> '0267' and ic.ItemType1 = 'ZC' "
          + " and not exists ( "
          + " 	select 1 from tXTCS in_a where in_a.ID = 'CmpCustCode' and ',' + in_a.QZ + ',' like '%' + c.Code + '%' "
		  + " ) " ;
		
		if(customer.getDateFrom()!=null){
			sql+=" and ic.ConfirmDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+="and ic.ConfirmDate < dateAdd(d,1,?)";
			list.add(customer.getDateTo());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql+=" and c.custType in "+ "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getItemType12())){
			sql+=" and it2.ItemType12 in "+ "('"+customer.getItemType12().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(customer.getSupplierCode())){
			sql+=" and i.SupplCode=? ";
			list.add(customer.getSupplierCode());
		}
		if(StringUtils.isNotBlank(customer.getEmpCode())){
			sql+=" and d.EmpCode = ?" ;
			list.add(customer.getEmpCode());
		}
		if(StringUtils.isNotBlank(customer.getRole())){
			sql+=" and d.Role = ?" ;
			list.add(customer.getRole());
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql+=" and e.Department1 = ?" ;
			list.add(customer.getDepartment1());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	
}
