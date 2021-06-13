package com.house.home.dao.project;

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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ProgCheckPlan;
import com.house.home.entity.project.ProgCheckPlanDetail;


@SuppressWarnings("serial")
@Repository
public class XjrwapDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ProgCheckPlan progCheckPlan) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select a.No, a.Type, a.CrtDate, a.CheckCZY, a.Remarks, a.LastUpdatedBy,a.Expired," +
				" a.ActionLog, a.LastUpdate,x1.note TypeDescr,b.zwxm CheckCZYDescr,x2.note IsCheckDeptDescr " +
				" from tProgCheckPlan a " +
				" left join txtdm  x1 on x1.cbm=a.type and x1.id='CHECKPLANTYPE '" +
				" left join tCZYBM  b  on b.czybh=a.CheckCZY " +
				" left join txtdm x2 on x2.cbm=a.IsCheckDept and x2.id='YESNO'" +
				" where 1=1 " ;
		if (progCheckPlan.getDateFrom() != null) {
			sql += " and a.CrtDate>= ? ";
			list.add(progCheckPlan.getDateFrom());
		}
		if (progCheckPlan.getDateTo() != null) {
			sql += " and a.CrtDate < DATEADD(d,1,?) ";
			list.add(progCheckPlan.getDateTo());
		}
		if(StringUtils.isNotBlank(progCheckPlan.getType())){
			sql+=" and a.type = ?";
			list.add(progCheckPlan.getType());
		}
		if(StringUtils.isNotBlank(progCheckPlan.getNo())){
			sql+=" and a.no = ? ";
			list.add(progCheckPlan.getNo());
		}
		if(StringUtils.isNotBlank(progCheckPlan.getCheckCZY())){
			sql+=" and a.checkCZY = ? ";
			list.add(progCheckPlan.getCheckCZY());
		}
		if (StringUtils.isBlank(progCheckPlan.getExpired())
				|| "F".equals(progCheckPlan.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a  order by a.LastUpdate desc";
		}
		
	
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ProgCheckPlanDetail progCheckPlanDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  a.pk,a.planno,a.CustCode,a.Status,a.AppCZY,a.AppDate,a.CheckNo, b.Address,x1.note  StatusDescr" +
				",c.ZWXM AppCZYDescr ,a.AppPk from tProgCheckPlanDetail a" +
				" left outer join tCustomer  b on b.code=a.CustCode" +
				" left outer join txtdm x1 on x1.CBM=a.Status and x1.id='CHECKDTSTATUS' " +
				" left outer join tCZYBM  c on c.CZYBH=a.AppCZY  " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(progCheckPlanDetail.getPlanNo())){
			sql+=" and a.PlanNo =? ";
			list.add(progCheckPlanDetail.getPlanNo());
		}
		
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	
	public Page<Map<String, Object>> findAddPageBySql(
			Page<Map<String, Object>> page, Customer customer,String custCodes,String checkType,String auto
			,String longitude,String latitude,String isCheckDept,String importancePrj) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (	select   ";
		if("1".equals(auto)){
			sql+=" case when b.longitude<>'0' and  b.latitude<>'0' and b.longitude is not null and  b.latitude is not null then " 
				+" convert(decimal(12,3),sqrt((((convert(decimal(12,6),?)-b.longitude)*PI()*12656*cos(((convert(decimal(12,6),?)+b.latitude)/2)*PI()/180)/180)"
				+ "*((convert(decimal(12,6),?)-b.longitude)*PI()*12656*cos (((convert(decimal(12,6),?)+b.latitude)/2)*PI()/180)/180) ) "
				+" + (((convert(decimal(12,6),?)-b.latitude)*PI()*12656/180) *   ((convert(decimal(12,6),?)-b.latitude)*PI()*12656/180) ))) " 
				+" else 100000.00 end as distance ,";
			list.add(longitude);
			list.add(latitude);
			list.add(longitude);
			list.add(latitude);
			list.add(latitude);
			list.add(latitude);
		}else {
			sql+="100000.000 as distance,";
		}
		sql+= "" +
			"b.longitude,b.Latitude,a.Code, a.Address,a.descr custdescr, a.BuilderCode, b.RegionCode2,b.descr builderDescr,  " +
			" b.RegionCode,c.Descr Region1Descr,datediff(dd,isnull(i.date,a.ConfirmBegin),getdate()) noCheckDate ,d.Descr Region2Descr, e1.NameChi ProjectManDescr,dpt2.Desc1 prjDeptDescr" +
			" ,s2.note nowspeeddescr,i.Date lastPrjProgCheckDate , i1.ModifyCount,x2.NOTE PrjItemDescr,i1.CheckCount " +
			" from  tcustomer  a   " +
			" left outer join tBuilder b on b.Code = a.BuilderCode    " +
			" left join ( " +
			" select  m.CustCode, max(m.no) no, count(1) CheckCount, sum(case when IsModify ='1' then 1 else 0 end) ModifyCount from tPrjProgCheck m where m.IsCheckDept= ?" +
			" group by  m.CustCode " +
			" ) i1 on a.code = i1.CustCode  " +
			" left join tPrjProgCheck  i on i.no=i1.no " +
			" left join tXTDM x2 on i.PRJITEM = x2.CBM and x2.ID = 'PRJITEM'  " +
			" left outer join tRegion c on c.Code = b.RegionCode " +
			" left outer join tRegion2 d on d.code = b.RegionCode2 " +
			" left join tEmployee e1 on a.ProjectMan = e1.Number  " +
			
			/*" left join (select MAX(a.PrjItem) prjitem ,a.CustCode from tPrjProg a " +
			" right join ( select  MAX(BeginDate)begindate,CustCode from tPrjProg  group by CustCode " +
			" )b on a.CustCode=b.CustCode and a.BeginDate=b.begindate and a.CustCode=b.CustCode " +
			" group by a.CustCode)sj on a.Code=sj.CustCode " +*/
			
			" LEFT JOIN (  select max(Pk) pk,Custcode from tPrjProg a  " +
			" where a.Begindate=(select max(Begindate) from tPrjProg  where custcode=a.CustCode ) " +
			" group by a.CustCode) pp ON pp.Custcode=a.Code   " +
			" left join tPrjprog sj on sj.pk=pp.pk  "+

			
			" left join tXTDM s2 on s2.cbm= sj.prjitem and s2.ID='PRJITEM'"+
			" left join tDepartment2 dpt2 on e1.Department2 = dpt2.Code " +
			/*" left join (" +
			" select  CustCode, sum(case when IsModify = '1' then 1 else 0 end) ModifyCount " +
			" from  tPrjProgCheck group by CustCode  " +
			" ) j on j.CustCode = a.code" +
			" left join ( " +
			" 	select CustCode, max(Date) Date from tPrjProgCheck where IsCheckDept= " +isCheckDept+
			" 	group by CustCode " +
			" ) k on a.code=k.CustCode " +*/
			" where  a.status='4'  " ;
		list.add(isCheckDept);
		
		if(StringUtils.isNotBlank(custCodes)){
			sql += " and a.code not in " + "('"+custCodes.replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getRegion())){
			sql+=" and b.RegionCode = ? ";
			list.add(customer.getRegion());
		}
		if(StringUtils.isNotBlank(customer.getRegion2())){
			sql+=" and b.RegionCode2 = ? ";
			list.add(customer.getRegion2());
		}
		if(StringUtils.isNotBlank(customer.getBuilderCode())){
			sql+=" and b.code = ? ";
			list.add(customer.getBuilderCode());
		}
		if("3".equals(checkType)){
			if(StringUtils.isNotBlank(customer.getDepartment2())){
				sql+=" and e1.Department2 = ? ";
				list.add(customer.getDepartment2());
			}
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and e1.Department2 = ?";
			list.add(customer.getDepartment2());
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql+=" and e1.department1 = ?";
			list.add(customer.getDepartment1());
		}
		if(customer.getBeginDate()!=null){
			 sql+=" and a.ConfirmBegin < ? ";
			list.add(customer.getBeginDate());
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(importancePrj)){
			if("1".equals(importancePrj)){
				sql+=" and exists ( select  * from tPrjCheckPrjMan in_a where in_a.expired='F' and in_a.ProjectMan=a.ProjectMan )";
			}
		}
		/*if(customer.getDateFrom()!=null){
			sql+=" and not exists(" +
					" select 1 from tPrjProgCheck  where CustCode=a.code and IsCheckDept= ? and date>=dateadd(d,-1,?)" +
					" )";
			list.add(isCheckDept);
			list.add(customer.getDateFrom());
			//System.out.println(customer.getDateFrom());
			//sql+=" and (k.CustCode is null or k.date< ? ) ";
			//list.add(new Timestamp(
			//		DateUtil.startOfTheDay( customer.getDateFrom()).getTime()));		
		}*/
		if(customer.getDateFrom()!=null){
				sql+=" and ( i.Date < ?  or i.date is null ) ";
				list.add(customer.getDateFrom());		
		}
		if(StringUtils.isNotBlank(customer.getConstructStatus())){
			sql += " and a.constructStatus  in " + "('"+customer.getConstructStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
			sql += " and sj.prjitem  in " + "('"+customer.getPrjProgTempNo().replaceAll(",", "','")+"')";
		}else{
			sql+=" and sj.prjitem <> '15' ";
		}
		
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and a.custType in " + "('"+customer.getCustType().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getLayout())){
			sql+=" and a.layout = ? ";
			list.add(customer.getLayout());
		}
		if(StringUtils.isNotBlank(customer.getCheckStatus())){
			sql+=" and not exists(" +
					" select 1 from tProgCheckPlanDetail mx " +
					" left join tProgCheckPlan pc on pc.no=mx.PlanNo  where CustCode=a.code and  pc.IsCheckDept= ?  " +
					" and CrtDate>=dateadd(dd,-1,cast(getdate() as date)) and pc.Expired='F' " +
					" )" ;
			list.add(isCheckDept);
		}			  
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.distance,a.RegionCode,a.RegionCode2,a.BuilderDescr,a.noCheckDate desc ";
		}
		page.setResult(DbUtil.getListBySql(sql, list));
		return page;
//		return this.findPageBySql(page, sql,list.toArray());
	}
	
	public Page<Map<String, Object>> findFroAddPageBySql(
			Page<Map<String, Object>> page, ProgCheckPlan progCheckPlan,String arr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK, a.CustCode,a.AppDate,a.AppCZY,a.Remarks, b.Address," +
					"e1.NameChi ProjectManDescr," +
					" e2.zwxm AppCZYDescr  from tProgCheckApp a  " +
					" left outer join tCustomer b on b.code=a.CustCode   " +
					" left outer join tEmployee e1 on e1.Number=b.ProjectMan  " +
					" left outer join tCZYBM e2 on e2.CZYBH=a.AppCZY   " +
					" WHERE a.Expired='F'   " +
					" and not exists(select 1 from tProgCheckPlanDetail where AppPK =a.PK) " ;
			if(StringUtils.isNotBlank(progCheckPlan.getAddress())){
				sql+=" and b.address like ?";
				list.add("%"+progCheckPlan.getAddress()+"%");
			}
			if(StringUtils.isNotBlank(progCheckPlan.getAppCZY())){
				sql+=" and a.appCZY = ? ";
				list.add(progCheckPlan.getAppCZY());		
			}
			if (progCheckPlan.getDateFrom() != null) {
				sql += " and a.AppDate>= ? ";
				list.add(progCheckPlan.getDateFrom());
			}
			if (progCheckPlan.getDateTo() != null) {
				sql += " and a.AppDate < DATEADD(d,1,?) ";
				list.add(progCheckPlan.getDateTo());
			}
			if(StringUtils.isNotBlank(arr)){
				sql += " and a.pk not in " + "('"+arr.replaceAll(",", "','")+"')";
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += " order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += " order by a.appDate desc";
			}		
		
		
		return this.findPageBySql(page, sql,list.toArray());
	}
	public Page<Map<String, Object>> findPrjPageBySql(
			Page<Map<String, Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.*,e.nameChi projectDescr from tPrjCheckPrjMan a " +
				" left join tEmployee e on e.Number=a.projectMan " +
				" where 1=1 and a.expired= 'F' ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	@SuppressWarnings("deprecation")
	public Result doSave (ProgCheckPlan progCheckPlan) {
		Assert.notNull(progCheckPlan);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pxjrwap_forXml(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, progCheckPlan.getNo());
			call.setString(2, progCheckPlan.getM_umState());
			call.setString(3, progCheckPlan.getType());
			call.setString(4, progCheckPlan.getCheckCZY());
			call.setString(5, progCheckPlan.getRemarks());
			call.setTimestamp(6, progCheckPlan.getCrtDate()== null ? null
					: new Timestamp(progCheckPlan.getCrtDate().getTime()));
			call.setString(7, progCheckPlan.getExpired());
			call.setString(8, progCheckPlan.getM_czy());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, progCheckPlan.getGiftAppDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Integer countFro(){
		String sql = "select count(*) num from tProgCheckApp a  " +
				" left outer join tCustomer b on b.code=a.CustCode   " +
				" left outer join tEmployee e1 on e1.Number=b.ProjectMan  " +
				" left outer join tCZYBM e2 on e2.CZYBH=a.AppCZY   " +
				" WHERE a.Expired='F'   " +
				" and not exists(" +
				" select 1 from tProgCheckPlanDetail where AppPK =a.PK" +
				" ) " ;
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{ });
		if (list!=null && list.size()>0){
			 return (Integer) list.get(0).get("num");
		}
		return null;
	}
	
	public String getIsCheckDept(String czybh){
		String sql = " select 1 IsCheckDept from tCZYBM a left outer join tEmployee b on b.Number=a.EMNum where  " +
				" a.CZYBH=?  and ( b.Department2 in (" +
				" select QZ from tXTCS where id='CheckDept' " +
				" ) or b.Department3 in ("+
				" select QZ from tXTCS where id='CheckDept' "+
				") ) " ;
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{czybh});
		if (list!=null && list.size()>0){
			 return "1";
		}
		return "0";
	}
	
	public void doSavePrjMan(String projectMan,String lastUpdatedBy) {
		String sql = "insert into tPrjCheckPrjMan  ( ProjectMan, LastUpdate, LastUpdatedBy, " +
				" Expired,ActionLog ) " +
				" values  (?,getDate(),?,'F','ADD' ) ";
		this.executeUpdateBySql(sql, new Object[]{projectMan,lastUpdatedBy});
	}
	
	public boolean getPrjManByCode(String prjMan){
		String sql = " select * from tPrjCheckPrjMan where projectMan = ?  and  expired='F' " ;
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{prjMan});
		if (list!=null && list.size()>0){
			 return true ;
		}
		return false;
	}
	
	public void doDelPrjMan(Integer pk) {
		String sql = " delete from tPrjCheckPrjMan where pk= ?  ";
		this.executeUpdateBySql(sql, new Object[]{pk});
	}
	
}
