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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustStakeholder;

@SuppressWarnings("serial")
@Repository
public class CustStakeholderDao extends BaseDao {

	/**
	 * CustStakeholder分页信息
	 * 
	 * @param page
	 * @param custStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustStakeholder custStakeholder,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.pk,a.Role,d.DocumentNo,b.Descr RoleDescr,a.EmpCode,c.NameChi EmpName,"
                   + "a.CustCode,d.Descr CustName,d.Status,e.NOTE StatusDescr,"
                   + "a.LastUpdate,a.LastUpdatedBy,a.Expired,f.Desc1 CustTypeDescr , "
                   + "a.ActionLog,d.Address , g.NOTE EndCodeDescr,case when h.custCode is null then '否' else '是' end isResignCust "
                   + " from tCustStakeholder a  "
                   + " inner join tCustomer d on d.Code=a.CustCode and d.Expired='F' "
                   + " left outer join tRoll b on b.Code=a.Role "
                   + " left outer join tEmployee c on c.Number=a.EmpCode "
                   + " left outer join tXTDM e on d.Status=e.CBM and e.ID='CUSTOMERSTATUS' "
//                   + " left outer join tXTDM f on d.CustType = f.CBM and f.ID = 'CUSTTYPE' " //调用tCustType表获取客户类型 modify by zb
                   + " left outer join tCusttype f on d.CustType = f.Code "
                   + " left outer join tXTDM g on d.EndCode=g.CBM and g.ID='CUSTOMERENDCODE' "
                   + " left join (select custCode from tAgainSign group by custCode) h on h.CustCode = a.CustCode"
                   + " where 1=1 and "+
                   SqlUtil.getCustRight(uc, "d", 0);
    	if (custStakeholder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custStakeholder.getPk());
		}
    	if (StringUtils.isNotBlank(custStakeholder.getRole())) {
			sql += " and a.Role=? ";
			list.add(custStakeholder.getRole());
		}
    	if (StringUtils.isNotBlank(custStakeholder.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(custStakeholder.getEmpCode());
		}
    	if (StringUtils.isNotBlank(custStakeholder.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custStakeholder.getCustCode());
		}
    	if(StringUtils.isNotBlank(custStakeholder.getAddress())){
    		sql+=" and d.address like ? ";
    		list.add("%"+custStakeholder.getAddress()+"%");
    	}
    	if (StringUtils.isNotBlank(custStakeholder.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custStakeholder.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custStakeholder.getExpired()) || "F".equals(custStakeholder.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custStakeholder.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custStakeholder.getActionLog());
		}
    	if(StringUtils.isNotBlank(custStakeholder.getCustStatus())){
			sql += " and d.Status in " + "('"+custStakeholder.getCustStatus().replaceAll(",", "','")+"')";
    	}
    	if(custStakeholder.getDateFrom()!=null){
    		sql+=" and d.endDate>= ?";
    		list.add(new Timestamp(
					DateUtil.startOfTheDay( custStakeholder.getDateFrom()).getTime()));
    	}
    	if(custStakeholder.getDateTo()!=null){
    		sql+=" and d.enddate< ?";
    		list.add(new Timestamp(
					DateUtil.endOfTheDay( custStakeholder.getDateTo()).getTime()));
    	}
    	if(custStakeholder.getSignDateFrom()!=null){
    		sql+=" and d.signDate>= ?";
    		list.add(new Timestamp(
					DateUtil.startOfTheDay( custStakeholder.getSignDateFrom()).getTime()));
    	}
    	if(custStakeholder.getSignDateTo()!=null){
    		sql+=" and d.signDate< ?";
    		list.add(new Timestamp(
					DateUtil.endOfTheDay( custStakeholder.getSignDateTo()).getTime()));
    	}
    	
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CustStakeholder custStakeholder) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.pk,a.Role,b.Descr RoleDescr,a.EmpCode,c.NameChi EmpName,"
                + "a.CustCode,d.Descr CustName,p.desc2 PositionDescr,"
                + "a.LastUpdate,a.LastUpdatedBy,a.Expired,case when c.Type = '3' then '' else c.phone end Phone,"
                + "a.ActionLog,d.Address,d1.Desc2 department1Descr, d2.Desc2 department2Descr, e2.NameChi LeaderDescr, "
                + "e.NOTE EmpStatusDescr "
                + " from tCustStakeholder a  "
                + " left join tRoll b on b.Code=a.Role "
                + " left join tEmployee c on c.Number=a.EmpCode "
                + " left join tCustomer d on d.Code=a.CustCode "
                + " left join tXTDM e on e.ID = 'EMPSTS' and e.CBM = c.Status "
                + " left join tPosition p on c.position=p.code "
                + " left outer join tDepartment1 d1 on d1.Code = c.Department1 "
                + " left outer join tDepartment2 d2 on d2.Code = c.Department2 "
                + " left outer join ("
                + "   select min(Number) EmpCode, Department1, Department2 from tEmployee"
                + "   where IsLead='1' and LeadLevel = '1' and Expired='F' " 
                + "   group by Department1, Department2"
                + " ) g on g.Department1=c.Department1 and g.Department2=c.Department2"
                + " left outer join tEmployee e2 on e2.Number=g.EmpCode"
                + " where a.CustCode = ? ";
		if (StringUtils.isNotBlank(custStakeholder.getCustCode())){
			list.add(custStakeholder.getCustCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 修改干系人存储过程
	 * 
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result updateForProc(CustStakeholder custStakeholder) {
		Assert.notNull(custStakeholder);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pSjgxr_forProc(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, custStakeholder.getM_umState());
			call.setInt(2, custStakeholder.getIsRight());
			call.setInt(3, custStakeholder.getPk());
			call.setString(4, custStakeholder.getCustCode());
			call.setString(5, custStakeholder.getRole());
			call.setString(6, custStakeholder.getEmpCode());
			call.setString(7, custStakeholder.getExpired());
			call.setString(8, custStakeholder.getLastUpdatedBy());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
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

	public int getCount(String custCode, String role) {
		String sql = "select count(*) number from tCustStakeholder where CustCode=? and Role=? and Expired='F'";
		List<Map<String,Object>> list =  this.findBySql(sql, new Object[]{custCode,role});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("number")));
		}
		return 0;
	}

	public List<Map<String,Object>> getListByCustCodeAndRole(String custCode, String role){
		String sql = "select pk,Role,EmpCode,CustCode from tCustStakeholder where CustCode=? and Role=?";
		return this.findBySql(sql, new Object[]{custCode,role});
	}

	public int getPkByCustCodeAndRole(String custCode, String role) {
		List<Map<String,Object>> list = getListByCustCodeAndRole(custCode,role);
		if (list!=null && list.size()>0){
			return (Integer) list.get(0).get("pk");
		}
		return 0;
	}

	public boolean phoneShow(String custCode, String empCode, String status) {
		String sql = "";
		if ("4".equals(status) || "5".equals(status)){
			sql = "select 1 from tCustStakeholder where CustCode=? and EmpCode=?";
		}else{
			sql = "select 1 from tCustStakeholder where CustCode=? and EmpCode=? and (Role='00' or Role='01')";
		}
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,empCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	public void doDelInteEmp(String code ){
		
		String sql=" delete from tCustStakeholder where custCode= ? and role='11' " ;
		
		this.executeUpdateBySql(sql, new Object[]{code});

	}
	
	public void doDelCGEmp(Integer pk){
	
	String sql=" delete from tCustStakeholder where pk= ? " ;
	
	this.executeUpdateBySql(sql, new Object[]{pk});

	}

	public Map<String, Object> getStakeholderInfo(String custCode) {
		String sql=" select isnull(a.MainPlanMan,'') MainPlanMan,isnull(e1.NameChi,'') MainPlanManName,isnull(a.MainBusinessMan,'') MainBusinessMan,isnull(e2.NameChi,'') MainBusinessManName, " 
				+" isnull(a.Mapper,'') Mapper,isnull(e3.NameChi,'') MapperName,isnull(a.Sketcher,'') Sketcher,isnull(e4.NameChi,'') SketcherName,isnull(a.DeepDesignMan,'') DeepDesignMan,isnull(e5.NameChi,'') DeepDesignManDescr, "
				+" isnull(a.JCMan,'') JCMan,isnull(e6.NameChi,'') JCManName,isnull(a.CGDesignCode,'') CGDesignCode,isnull(e8.NameChi,'') CGDesignDescr,isnull(a.RZMan,'') RZMan,isnull(e7.NameChi,'') RZManName, isnull(a.JCZYDesignCode, '')JCZYDesignCode,isnull(e9.NameChi,'') JCZYDesignDescr,"
				+" isnull(a.DesignMan,'') DesignMan,isnull(e10.NameChi,'') DesignManDescr,isnull(a.MeasureMan,'') MeasureMan,isnull(e11.NameChi,'') MeasureManDescr,isnull(a.DeclareMan,'') DeclareMan,isnull(e12.NameChi,'') DeclareManDescr "
				+" from (   select max(case when Role='32' then EmpCode end) MainPlanMan,   max(case when Role='34' then EmpCode end) MainBusinessMan,   " 
				+"  max(case when Role='03' then EmpCode end) Mapper,  max(case when Role='04' then EmpCode end) Sketcher, max(case when Role='64' then EmpCode end) DeepDesignMan,"
				+"  max(case when Role='11' then EmpCode end) JCMan,max(case when Role='61' then EmpCode end) CGDesignCode, max(case when Role='50' then EmpCode end) RZMan, max(case when Role='66' then EmpCode end) JCZYDesignCode, "
				+" max(case when Role='00' then EmpCode end) DesignMan,max(case when Role='02' then EmpCode end) MeasureMan, max(case when Role='31' then EmpCode end) DeclareMan "
				+" from tCustStakeholder where CustCode=? ) a " 
				+" left join tEmployee e1 on e1.Number=a.MainPlanMan " 
				+" left join tEmployee e2 on e2.Number=a.MainBusinessMan "
				+" left join tEmployee e3 on e3.Number=a.Mapper"
				+" left join tEmployee e4 on e4.Number=a.Sketcher"
				+" left join tEmployee e5 on e5.Number=a.DeepDesignMan"
				+" left join tEmployee e6 on e6.Number=a.JCMan"
				+" left join tEmployee e7 on e7.Number=a.RZMan"
				+" left join tEmployee e8 on e8.Number=a.CGDesignCode"
				+" left join tEmployee e9 on e9.Number=a.JCZYDesignCode"
				+" left join tEmployee e10 on e10.Number=a.DesignMan"
				+" left join tEmployee e11 on e11.Number=a.MeasureMan"
				+" left join tEmployee e12 on e12.Number=a.DeclareMan";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	@SuppressWarnings("deprecation")
	public Result updateGcxxglDesigner(CustStakeholder custStakeholder) {
		Assert.notNull(custStakeholder);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pGcxxgl_updateDesigner(?,?,?,?,?,?,?)}");
			call.setString(1, custStakeholder.getM_umState());
			call.setString(2, custStakeholder.getCustCode());
			call.setString(3, custStakeholder.getRole());
			call.setString(4, custStakeholder.getEmpCode());
			call.setString(5, custStakeholder.getLastUpdatedBy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public Result doUpdateMainManager(CustStakeholder custStakeholder) {
		Assert.notNull(custStakeholder);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pGcxxgl_updateMainManager(?,?,?,?,?,?,?)}");
			call.setString(1, custStakeholder.getM_umState());
			call.setString(2, custStakeholder.getCustCode());
			call.setString(3, custStakeholder.getRole());
			call.setString(4, custStakeholder.getEmpCode());
			call.setString(5, custStakeholder.getLastUpdatedBy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public int onlyBusinessMan(String custCode){
		String sql = "select 1 from tCustStakeholder where CustCode= ?" 
		         + " and Role = '01' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return  list.size();
		}
		return 0;
	}
	
	public int  onlyDesigner(String custCode){
		String sql = " select 1 from tCustStakeholder where CustCode= ? "
         + " and Role = '00' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return  list.size();
		}
		return 0;
	}
	
	public String  getRoleByCustCodeAndEmpCode(String custCode,String empCode){
		String sql = " select b.Descr from tCustStakeholder a "
					+" left join tRoll b on a.Role = b.Code "
					+" where a.CustCode = ? and a.EmpCode = ? and a.Role in('00','02')";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,empCode});
		if(list != null && list.size() > 0){
			return  list.get(0).get("Descr").toString();
		}
		return "";
	}
	/**
	 * 通过角色获取链接起来的干系人
	 * @author	created by zb
	 * @date	2020-4-24--下午5:51:52
	 * @param custCode 客户编号
	 * @param role 角色
	 * @return 用/拼接的字符串
	 */
	public String getStakeholderLinkedByRole(String custCode, String role) {
		String sql = "select cast(dbo.fGetEmpNameChi(?, ?) as nvarchar(255)) ManDescr";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode, role});
		if(list != null && list.size() > 0){
			return  list.get(0).get("ManDescr").toString();
		}
		return "";
	}
	
	public boolean hasEmployeeByRole(String custCode, String role, UserContext uc){
		String sql=" select 1 from tcustomer a where code=?  ";
		
		sql += " and " + SqlUtil.getCustRight(uc, "a", role);
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}


}

