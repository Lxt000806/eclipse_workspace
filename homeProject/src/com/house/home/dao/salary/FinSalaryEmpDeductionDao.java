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
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.salary.SalaryEmpDeduction;

@SuppressWarnings("serial")
@Repository
public class FinSalaryEmpDeductionDao extends BaseDao {

	/**
	 * FinSalaryEmpDeduction分页信息
	 * 
	 * @param page
	 * @param finSalaryEmpDeduction
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpDeduction finSalaryEmpDeduction) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select e.Descr ConSignCmpDescr,a.SalaryEmp,c.EmpName,f.Desc2 Department1Descr, "
					+"b.Descr DeductType2Descr,a.Amount,a.SalaryMon,a.DeductDate,a.LastUpdate,a.LastUpdatedBy, "
					+"a.Expired,a.ActionLog,a.Remarks,a.PK,c.FinancialCode,i.descr SalaryschemeTypeDescr "
					+"from tSalaryEmpDeduction a  "
					+"left join tSalaryDeductType2 b on a.DeductType2=b.Code "
					+"left join tSalaryEmp c on a.SalaryEmp=c.EmpCode "
					+"left join tConSignCmp e on c.ConSignCmp=e.Code "
					+"left join tDepartment1 f on c.Department1=f.Code " 
					+"left join tSalaryschemeType i on i.Code=a.SalaryschemeType "
					+"where a.DeductType1='1' ";

    	if (finSalaryEmpDeduction.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(finSalaryEmpDeduction.getPk());
		}
    	if(StringUtils.isNotBlank(finSalaryEmpDeduction.getQueryCondition())){
			sql+=" and ( c.EmpName like ? or c.EmpCode like ? or c.IDNum like ? or c.FinancialCode like ? )";
			list.add("%"+finSalaryEmpDeduction.getQueryCondition()+"%");
			list.add("%"+finSalaryEmpDeduction.getQueryCondition()+"%");
			list.add("%"+finSalaryEmpDeduction.getQueryCondition()+"%");
			list.add("%"+finSalaryEmpDeduction.getQueryCondition()+"%");
		}
    	if (finSalaryEmpDeduction.getSalaryMon() != null) {
			sql += " and a.SalaryMon=?";
			list.add(finSalaryEmpDeduction.getSalaryMon());
		}
    	if (StringUtils.isNotBlank(finSalaryEmpDeduction.getDeductType2())) {
			sql += " and a.DeductType2=? ";
			list.add(finSalaryEmpDeduction.getDeductType2());
		}
    	if (StringUtils.isNotBlank(finSalaryEmpDeduction.getConSignCmp())) {
			sql += " and c.ConSignCmp=? ";
			list.add(finSalaryEmpDeduction.getConSignCmp());
		}
    	if (StringUtils.isNotBlank(finSalaryEmpDeduction.getDepartment1())) {
			sql += " and c.Department1 in ("+SqlUtil.resetStatus(finSalaryEmpDeduction.getDepartment1())+")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.SalaryEmp asc,a.DeductDate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查扣款科目
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getDeductType2(String deductType2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from tSalaryDeductType2  where Descr=?  ";
		list.add(deductType2);
		return this.findBySql(sql,list.toArray());
		
	}

	/**
	 * Excel导入调过程
	 * @param finSalaryEmpDeduction
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (SalaryEmpDeduction finSalaryEmpDeduction) {
		Assert.notNull(finSalaryEmpDeduction);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pFinSalaryEmpDeduction_import(?,?,?,?,?,?,?)}");
			call.setString(1, finSalaryEmpDeduction.getLastUpdatedBy());
			call.setInt(2, finSalaryEmpDeduction.getSalaryMon());
			call.setString(3, finSalaryEmpDeduction.getDetailXml());
			call.setString(4, finSalaryEmpDeduction.getDeductType1());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, finSalaryEmpDeduction.getSalarySchemeType());
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

