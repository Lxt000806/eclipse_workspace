package com.house.home.dao.salary;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.salary.SalaryScheme;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@SuppressWarnings("serial")
@Repository
public class SalarySchemeDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select a.pk,a.Descr,c.descr SalarySchemeTypeDescr,b.Desc2 cmpDescr,a.Remarks,a.BeginMon," +
				" a.EndMon,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,x1.note statusDescr " +
				" from tSalaryScheme a " +
				" left join tCompany b on b.Code = a.CmpCode " +
				" left join tSalarySchemeType c on c.Code = a.SalarySchemeType " +
				" left join tXtdm x1 on x1.cbm = a.status and x1.id = 'SALENABLESTAT'" +
				" where 1=1" +
				" " ;
		
		if(StringUtils.isNotBlank(salaryScheme.getDescr())){
			sql+=" and a.descr like ? ";
			list.add("%"+salaryScheme.getDescr()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findSalaryItemBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select a.Code, a.Descr,a.lastUpdate from tSalaryItem a " +
				"  " +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(salaryScheme.getSalaryItemCodes())){
			sql+=" and a.Code not in ('"+ salaryScheme.getSalaryItemCodes().replace(",", "','")+"')";
		}
		
		if(StringUtils.isNotBlank(salaryScheme.getSelItemCodes())){
			sql+=" and a.Code in ('"+ salaryScheme.getSelItemCodes().replace(",", "','")+"')";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findEmpListBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select c.EmpName,c.EmpCode,d.Descr cmpDescr,e.Desc2 dept1Descr,f.Desc2 dept2Descr,c.JoinDate,c.LeaveDate," +
				" a.lastUpdate,g.descr PosiClassDescr " +
				" from  tSalarySchemeEmp a" +
				" left join tSalaryScheme b on b.PK = a.SalaryScheme" +
				" left join tSalaryEmp c on c.EmpCode = a.SalaryEmp" +
				" left join tConSignCmp d on d.Code = c.ConSignCmp" +
				" left join tDepartment1 e on c.Department1 = e.Code" +
				" left join tDepartment2 f on f.Code = c.Department2 " +
				" left join tSalaryPosiClass g on g.pk = c.PosiClass " +
				" where 1=1 " ;
		
		if (salaryScheme.getPk() != null) {
			sql+=" and a.SalaryScheme = ? ";
			list.add(salaryScheme.getPk());
		}
		if(StringUtils.isNotBlank(salaryScheme.getDepartment1())){
			sql+=" and c.Department1 in ('"+salaryScheme.getDepartment1().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(salaryScheme.getDepartment2())){
			sql+=" and c.Department2 in ('"+salaryScheme.getDepartment2().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(salaryScheme.getQueryCondition())){
			sql+=" and (c.EmpCode like ? or c.EmpName like ? or c.IDNum like ? or c.FinancialCode like ?)";
			list.add("%"+salaryScheme.getQueryCondition()+"%");
			list.add("%"+salaryScheme.getQueryCondition()+"%");
			list.add("%"+salaryScheme.getQueryCondition()+"%");
			list.add("%"+salaryScheme.getQueryCondition()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPaymentListBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select a.PK,a.descr,a.SalaryScheme,a.PayMode,a.SeqNo,a.Remarks,a.FilterFormula,a.FilterFormulaShow," +
				" a.RealPaySalaryItem,a.SalaryItemList, a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog," +
				" b.Descr schemeDescr,x.NOTE payModeDescr,c.Descr SalaryItemDescr " +
				" from tSalaryPaymentDef a " +
				" left join tSalaryScheme b on b.PK = a.SalaryScheme " +
				" left join tSalaryItem c on c.Code = a.RealPaySalaryItem" +
				" left join tXTDM x on x.id= 'SALPAYMODE' and x.cbm = a.PayMode" +
				" where 1=1 " ;
		
		if (salaryScheme.getPk() != null) {
			sql+=" and a.SalaryScheme = ? ";
			list.add(salaryScheme.getPk());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.seqno ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findSchemeItemBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = "select * from (" +
				" select a.PK,a.SalaryScheme,a.SalaryItem,a.DispSeq,a.IsShow,a.LastUpdate,a.LastUpdatedBy," +
				" a.Expired,a.ActionLog, b.Descr salaryItemDescr, a.IsRptShow, a.RptDispSeq from tSalarySchemeItem a" +
				" left join tSalaryItem b on b.Code = a.SalaryItem" +
				" where 1=1" ;
		
		if (salaryScheme.getPk()!=null) {
			sql+=" and a.SalaryScheme = ? ";
			list.add(salaryScheme.getPk());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.DispSeq ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(SalaryScheme salaryScheme) {
		Assert.notNull(salaryScheme);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalaryScheme(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, salaryScheme.getM_umState());
			call.setInt(2, salaryScheme.getPk() == null ? 0: salaryScheme.getPk());
			call.setString(3, salaryScheme.getDescr());
			call.setString(4, salaryScheme.getSalarySchemeType());
			call.setString(5, salaryScheme.getCmpCode());
			call.setString(6, salaryScheme.getRemarks());
			call.setString(7, salaryScheme.getStatus());
			call.setInt(8, salaryScheme.getBeginMon()== null ? 0: salaryScheme.getBeginMon());
			call.setInt(9, salaryScheme.getEndMon() == null ? 0: salaryScheme.getEndMon());
			call.setString(10, salaryScheme.getLastUpdatedBy());
			call.setString(11, salaryScheme.getExpired());
			call.setString(12, salaryScheme.getSalaryItemXml());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR); 
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
	
	@SuppressWarnings("deprecation")
	public Result doSavePayment(SalaryScheme salaryScheme) {
		Assert.notNull(salaryScheme);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalaryScheme_payment(?,?,?,?,?,?)}");
			call.setString(1, salaryScheme.getM_umState());
			call.setInt(2, salaryScheme.getPk() == null ? 0: salaryScheme.getPk());
			call.setString(3, salaryScheme.getLastUpdatedBy());
			call.setString(4, salaryScheme.getSalaryItemXml());
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

}
