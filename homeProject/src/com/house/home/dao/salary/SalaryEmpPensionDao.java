package com.house.home.dao.salary;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryEmpPension;

@SuppressWarnings("serial")
@Repository
public class SalaryEmpPensionDao extends BaseDao {

	/**
	 * SalaryEmpPension分页信息
	 * 
	 * @param page
	 * @param salaryEmpPension
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpPension salaryEmpPension) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Pk,a.SalaryEmp,b.EmpName,c.NOTE TypeDescr,a.Amount,a.BeginMon,a.EndMon, "
					+"a.Remarks,a.Expired,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.EffectDate "  
					+"from tSalaryEmpPension a  "
					+"left join tSalaryEmp b on a.SalaryEmp=b.EmpCode "
					+"left join tXTDM c on a.Type=c.CBM and c.ID='SALPENSIONTYPE' "
					+"where 1=1 ";

    	if (salaryEmpPension.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(salaryEmpPension.getPk());
		}
    	if(StringUtils.isNotBlank(salaryEmpPension.getQueryCondition())){
			sql+=" and ( b.EmpName like ? or b.EmpCode like ? or b.IDNum like ? )";
			list.add("%"+salaryEmpPension.getQueryCondition()+"%");
			list.add("%"+salaryEmpPension.getQueryCondition()+"%");
			list.add("%"+salaryEmpPension.getQueryCondition()+"%");
		}
    	if (StringUtils.isNotBlank(salaryEmpPension.getType())) {
			sql += " and a.Type=? ";
			list.add(salaryEmpPension.getType());
		}
    	if (salaryEmpPension.getBeginMon() != null) {
			sql += " and a.BeginMon<=? and a.EndMon>=?";
			list.add(salaryEmpPension.getBeginMon());
			list.add(salaryEmpPension.getBeginMon());
		}
		if (StringUtils.isBlank(salaryEmpPension.getExpired()) || "F".equals(salaryEmpPension.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 月份区间是否重叠
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isExistsMon(SalaryEmpPension salaryEmpPension) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tSalaryEmpPension "
				+"where SalaryEmp=? and Type=?  and ((? between EndMon and BeginMon ) " +
				"	or (? between EndMon and BeginMon ) or (beginMon between ? and ?) or (endMon between ? and ?)" +
				" )";
		list.add(salaryEmpPension.getSalaryEmp());
		list.add(salaryEmpPension.getType());
		list.add(salaryEmpPension.getBeginMon());
		list.add(salaryEmpPension.getEndMon());
		list.add(salaryEmpPension.getBeginMon());
		list.add(salaryEmpPension.getEndMon());
		list.add(salaryEmpPension.getBeginMon());
		list.add(salaryEmpPension.getEndMon());
		if(salaryEmpPension.getPk()!=null){
			sql+=" and PK<>? ";
			list.add(salaryEmpPension.getPk());
		}
		return this.findBySql(sql,list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doImportSalaryEmpPension(SalaryEmpPension salaryEmpPension) {
		Assert.notNull(salaryEmpPension);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalary_SalaryEmpPensionImport(?,?,?,?)}");
			call.setString(1, salaryEmpPension.getLastUpdatedBy());
			call.setString(2, salaryEmpPension.getSalaryEmpPensionXml());
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
	
	public List<Map<String, Object>> isCheckMon(SalaryEmpPension salaryEmpPension) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from tSalaryStatusCtrl  where SalaryMon = ? and Status = '3'";
		list.add(salaryEmpPension.getBeginMon());
		return this.findBySql(sql,list.toArray());
	}
}

