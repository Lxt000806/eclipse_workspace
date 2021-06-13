package com.house.home.dao.salary;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryDataAdjust;
import com.house.home.entity.salary.SalaryStatusCtrl;

@SuppressWarnings("serial")
@Repository
public class SalaryCalcDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (" +
				" select a.pk,b.EmpCode,b.EmpName,c.Desc2 dept1Descr,d.Desc2 dept2Descr,e.Descr positionClassDescr,b.JoinDate,b.LeaveDate," +
				" f.Descr,b.BasicSalary ,a.lastUpdate " +
				" from tSalaryData a " +
				" left join tSalaryEmp b on b.EmpCode = a.SalaryEmp" +
				" left join tDepartment1 c on c.Code = b.Department1" +
				" left join tDepartment2 d on d.Code = b.Department2" +
				" left join tSalaryPosiClass e on e.PK = b.PosiClass" +
				" left join tSalaryMon f on f.SalaryMon = a.SalaryMon " +
				" where 1=1" ;
		/*if(salaryData.getSalaryMon() != null){
			sql+=" and a.salaryMon = ?";
			list.add(salaryData.getSalaryMon());
		}
		if(salaryData.getSalaryScheme() != null){
			sql+=" and a.salaryScheme = ? ";
			list.add(salaryData.getSalaryScheme());
		}*/

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getPaymentDetail(
			Page<Map<String, Object>> page, SalaryData salaryData) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (" +
				" select b.EmpName,a.SalaryEmp,a.SalaryMon,d.Descr schemeDescr,a.AmountPaid,a.ActualAmount,a.ActName,a.CardID,a.Remarks " +
				" from tSalaryPayment a " +
				" left join tSalaryEmp b on b.EmpCode = a.SalaryEmp" +
				" left join tSalaryPaymentDef c on c.PK = a.PaymentDef" +
				" left join tSalaryScheme d on d.PK = a.SalaryScheme " +
				" where 1=1 " ;
		if(salaryData.getSalaryMon() != null){
			sql+=" and a.salaryMon = ?";
			list.add(salaryData.getSalaryMon());
		}
		
		if(salaryData.getPaymentDef() != null){
			sql += " and a.paymentDef = ? ";
			list.add(salaryData.getPaymentDef());
		} else {
			sql+="1 <> 1";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getSalaryChgByJqgrid(
			Page<Map<String, Object>> page, SalaryData salaryData) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (select d.Idnum,a.pk,a.ActionLog,a.Expired,a.LastUpdate,a.LastUpdatedBy,a.Remarks,a.AdjustValue," +
				" d.EmpName,a.SalaryEmp,b.Descr itemDescr, c.Descr schemeDescr " +
				" from tSalaryDataAdjust a" +
				" left join tSalaryItem b on b.Code = a.SalaryItem" +
				" left join tSalaryScheme c on c.PK = a.SalaryScheme " +
				" left join tSalaryEmp d on d.EmpCode = a.SalaryEmp  " +
				" where 1=1 and a.expired = 'F' " ;
		if(salaryData.getSalaryMon() != null){
			sql+=" and a.salaryMon = ?";
			list.add(salaryData.getSalaryMon());
		}
		if(StringUtils.isNotBlank(salaryData.getSalaryItem())){
			sql+=" and a.salaryItem = ? ";
			list.add(salaryData.getSalaryItem());
		}
		if(StringUtils.isNotBlank(salaryData.getQueryCondition())){
			sql+=" and (d.empName like ? or d.EmpCode like ? or d.IdNum like ?) ";
			list.add("%"+salaryData.getQueryCondition()+"%");
			list.add("%"+salaryData.getQueryCondition()+"%");
			list.add("%"+salaryData.getQueryCondition()+"%");
		}
		
		if(salaryData.getPaymentDef() != null){
			sql += " and a.paymentDef = ? ";
			list.add(salaryData.getPaymentDef());
		}  
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Integer getCalcMon(){
		
		String sql = "select min(SalaryMon) salaryMon from tSalaryMon a where status = '2'";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size()>0){
			return Integer.parseInt(list.get(0).get("salaryMon").toString());
		}
		
		return null;
	}
	
	public Integer getMaxCalcMon(){
		
		String sql = "select max(SalaryMon) SalaryMon from tSalaryStatusCtrl where Status = '3'";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size()>0){
			return Integer.parseInt(list.get(0).get("SalaryMon").toString());
		}
		
		return null;
	}

	public Integer getSalaryScheme(){
	
		String sql = "select min(pk) pk from tSalaryScheme a where status = '1'";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size()>0){
			return Integer.parseInt(list.get(0).get("pk").toString());
		}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Result doCalc(SalaryData salaryData) {
		Assert.notNull(salaryData);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalary_Run_Main(?,?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0: salaryData.getSalaryMon());
			call.setString(2, salaryData.getSalarySchemeType());
			call.setInt(3, salaryData.getSalaryScheme() == null ? 0: salaryData.getSalaryScheme());
			call.setString(4, salaryData.getCalcAll());
			call.setString(5, salaryData.getLastUpdatedBy());
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
	public Result doCheck(SalaryData salaryData) {
		Assert.notNull(salaryData);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalary_Settle(?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0: salaryData.getSalaryMon());
			call.setString(2, salaryData.getSalarySchemeType());
			call.setInt(3, salaryData.getSalaryScheme() == null ? 0: salaryData.getSalaryScheme());
			call.setString(4, salaryData.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR); 
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
	
	@SuppressWarnings("deprecation")
	public Result doCheckReturn(SalaryData salaryData) {
		Assert.notNull(salaryData);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalary_SettleRollback(?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0: salaryData.getSalaryMon());
			call.setString(2, salaryData.getSalarySchemeType());
			call.setInt(3, salaryData.getSalaryScheme() == null ? 0: salaryData.getSalaryScheme());
			call.setString(4, salaryData.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR); 
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
	
	
	@SuppressWarnings("deprecation")
	public Result doChgEmpSalary(SalaryDataAdjust salaryDataAdjust) {
		Assert.notNull(salaryDataAdjust);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalary_CalcByEmp(?,?,?,?,?,?)}");
			call.setInt(1, salaryDataAdjust.getSalaryMon() == null ? 0: salaryDataAdjust.getSalaryMon());
			call.setInt(2, salaryDataAdjust.getSalaryScheme() == null ? 0 :salaryDataAdjust.getSalaryScheme());
			call.setString(3, salaryDataAdjust.getSalaryEmp());
			call.setString(4, salaryDataAdjust.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR); 
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
	
	@SuppressWarnings("deprecation")
	public Result doSchemeEmpSave(SalaryData salaryData) {
		Assert.notNull(salaryData);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalarySchemeEmpSet(?,?,?,?,?,?)}");
			call.setString(1,salaryData.getM_umState());
			call.setInt(2, salaryData.getSalaryScheme() == null ? 0 :salaryData.getSalaryScheme());
			call.setString(3, salaryData.getLastUpdatedBy());
			call.setString(4, salaryData.getSalaryEmpXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR); 
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
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getMainPageData(SalaryData salaryData) {
		
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		
		Assert.notNull(salaryData);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRpt_Salary_Main(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0 :salaryData.getSalaryMon());
			call.setInt(2, salaryData.getSalaryScheme() == null ? 0 :salaryData.getSalaryScheme());
			call.setString(3, salaryData.getDept1Code());
			call.setString(4, salaryData.getEmpStatus());
			call.setTimestamp(5, salaryData.getDateFrom() == null ? null
					: new Timestamp(salaryData.getDateFrom().getTime()));
			call.setTimestamp(6, salaryData.getDateTo() == null ? null
					: new Timestamp(salaryData.getDateTo().getTime()));
			call.setString(7,salaryData.getPositionClass());
			call.setString(8,salaryData.getEmpName());
			call.setString(9,"");
			call.setString(10, salaryData.getIsRptShow());
			call.setString(11, salaryData.getSalaryStatus());
			call.execute();
			ResultSet rs = call.getResultSet();
			res=BeanConvertUtil.resultSetToList(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		
		return res;
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getPaymentDetailPageData(SalaryData salaryData) {
		
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		Assert.notNull(salaryData);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRpt_Salary_Payment(?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0 :salaryData.getSalaryMon());
			call.setInt(2, salaryData.getSalaryScheme() == null ? 0 :salaryData.getSalaryScheme());
			call.setInt(3, salaryData.getPaymentDef() == null ? 0 :salaryData.getPaymentDef());
			call.setString(4, salaryData.getEmpStatus());
			call.setTimestamp(5, salaryData.getDateFrom() == null ? null
					: new Timestamp(salaryData.getDateFrom().getTime()));
			call.setTimestamp(6, salaryData.getDateTo() == null ? null
					: new Timestamp(salaryData.getDateTo().getTime()));
			call.setString(7,salaryData.getEmpName());
			call.setString(8,salaryData.getSalaryStatus());
			call.setString(9,salaryData.getCompany());
			call.setString(10, salaryData.getBelongType());
			call.execute();
			ResultSet rs = call.getResultSet();
			res=BeanConvertUtil.resultSetToList(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		
		return res;
	}
	
	public String getCmpEmpCount(Integer salaryScheme){
		
		String sql = "select '上次计算时间：'+ isnull(convert(nvarchar,a.processTime,120 ),'') cmpEmpInfo from (" +
				"	select c.processTime from tSalaryEmp a" +
				"	left join (select max(b.ProcessTime) processTime from tSalaryProcessLog b where b.ProcessType ='1' and SalaryScheme = ? " +
				" 	)c on 1=1 " +
				"	where 1=1 and exists(select 1 from tSalarySchemeEmp in_a where in_a.SalaryEmp = a.empCode and in_a.salaryScheme = ? )" +
				"	group by c.processTime" +
				" ) a";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryScheme, salaryScheme});
		if(list != null && list.size()>0){
			if(list.get(0).get("cmpEmpInfo") != null){
				return list.get(0).get("cmpEmpInfo").toString();
			}
		}
		
		return "符合人数为：0";
	}
	
	public Integer chekcStatusCtrl(SalaryStatusCtrl salaryStatusCtrl){
		
		String sql = " select pk,SalaryMon, SalaryScheme, Status, ProcessTime from tSalaryStatusCtrl where SalaryMon = ? and SalaryScheme = ?";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryStatusCtrl.getSalaryMon(),salaryStatusCtrl.getSalaryScheme()});
		if(list != null && list.size()>0){
			return Integer.parseInt(list.get(0).get("pk").toString());
		}
		
		return null;
	}
	
	public List<Map<String,Object>> getSalaryStatusCtrl(SalaryData salaryData){
		
		String sql = "select '状态:'+b.NOTE +', 上次操作时间:'+ convert(nvarchar(20),a.ProcessTime,120 ) schemeInfo,b.NOTE status from tSalaryStatusCtrl a" +
				" left join tXTDM b on b.id = 'SALMONSTAT' and b.CBM = a.Status" +
				" left join (select max(in_a.pk) pk, in_a.SalaryMon,in_a.SalaryScheme " +
				"			from tSalaryProcessLog in_a group by in_a.SalaryMon,in_a.SalaryScheme" +
				"		)c on c.SalaryMon = a.SalaryMon and c.SalaryScheme = a.SalaryScheme " +
				" left join tSalaryProcessLog d on d.PK = c.pk" +
				" left join tSalaryScheme e on e.pk=a.SalaryScheme " +
				" where 1=1 " ; 
		// where a.SalaryMon = ? and a.SalaryScheme = ? ";
		// return this.findBySql(sql, new Object[]{salaryData.getSalaryMon(), salaryData.getSalaryScheme()});
		List<Object> params = new ArrayList<Object>();
		
		if (salaryData.getSalaryMon()!=null) {
			sql += "and a.SalaryMon = ? ";
			params.add(salaryData.getSalaryMon());
		}
		if (salaryData.getSalaryScheme()!=null) {
			sql += "and a.SalaryScheme = ? ";
			params.add(salaryData.getSalaryScheme());
		}
		if (StringUtils.isNotBlank(salaryData.getSalarySchemeType())) {
			sql += "and e.salarySchemeType = ? ";
			params.add(salaryData.getSalarySchemeType());
		}
		if (StringUtils.isNotBlank(salaryData.getStatus())) {
			sql += "and a.Status = ? ";
			params.add(salaryData.getStatus());
		}
		
		return this.findBySql(sql, params.toArray());
	
	}
	
	public List<Map<String, Object>> getPaymentSubreport(SalaryData salaryData){
		
		String sql = "Select * from tSalaryPaymentDefHis a where a.SalaryMon = ? and a.SalaryScheme = ? order by a.SeqNo";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryData.getSalaryMon(), salaryData.getSalaryScheme()});
		if(list != null && list.size()>0){
			return list;
		}
		
		return null;
	}
	
	public Long doDelSalaryChg(String pks){
		String sql = " delete from tSalaryDataAdjust where pk in ('"+pks.replaceAll(",", "','")+"')";
		Long res = this.executeUpdateBySql(sql, new Object[]{});
		return res;
	}
	
	public List<Map<String, Object>> getSalaryChgDataByPks(String pks){
		
		String sql = "select SalaryMon,SalaryScheme,SalaryEmp from tSalaryDataAdjust where pk  in ('" + pks.replaceAll(",", "','")+ "')";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size()>0){
			return list;
		}
		
		return null;
	}	
	
	@SuppressWarnings("deprecation")
	public Result doImportSalaryChg(SalaryData salaryData) {
		Assert.notNull(salaryData);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalary_SalaryDataAjustImport(?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0: salaryData.getSalaryMon());
			call.setInt(2, salaryData.getSalaryScheme() == null ? 0 :salaryData.getSalaryScheme());
			call.setString(3, salaryData.getLastUpdatedBy());
			call.setString(4, salaryData.getSalaryEmpXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR); 
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

	public String getSalaryEmpByIdNum(String idNum){
		
		String sql = "select empCode  from tSalaryEmp where idnum = ?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{idNum});
		if(list != null && list.size()>0){
			return list.get(0).get("empCode").toString();
		}
		
		return "";
	}	
	
	public String IsLastCheckedCtrl(SalaryData salaryData){
		
		String sql = " select 1 from tSalaryStatusCtrl "
			+" where SalaryMon = ? and SalaryScheme = ?  "
			+" and PK = ( " 
			+"	 select top 1 a.PK "
			+" 	 from tSalaryStatusCtrl a  "
			+"   left join tSalaryMon b on a.SalaryMon = b.SalaryMon "
			+"   left join tSalaryScheme c on a.SalaryScheme = c.PK "
			+"   left join tSalarySchemeType d on c.SalarySchemeType = d.Code "
			+"   where a.Status='3'   "
			+"   order by a.SalaryMon desc,d.DispSeq desc "
			+" )";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryData.getSalaryMon(),salaryData.getSalaryScheme()});
		if(list != null && list.size()>0){
			return "1";
		}
		
		return "0";
	}

	public String getFirstCalcTime(Integer salaryMon) {
		String sql = " select FirstCalcTime from tSalaryMon where SalaryMon = ?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryMon});
//		Date date = (Date) list.get(0).get("FirstCalcTime");
		if(list != null && list.size()>0 && list.get(0).get("FirstCalcTime") != null){
			return "1";
		}
		return "0";
	}	
	
	public String getSalaryStatus(SalaryData salaryData){
		
		String sql = " select pk, SalaryMon, SalaryScheme, Status, ProcessTime from tSalaryStatusCtrl where SalaryMon = ? and SalaryScheme = ?";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryData.getSalaryMon(),salaryData.getSalaryScheme()});
		if(list != null && list.size()>0){
			return list.get(0).get("Status").toString();
		}
		
		return "";
	}
}
