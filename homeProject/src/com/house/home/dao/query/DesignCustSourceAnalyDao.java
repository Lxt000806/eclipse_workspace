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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;


@SuppressWarnings("serial")
@Repository
public class DesignCustSourceAnalyDao extends BaseDao {
	
	/**
	 * 设计客户来源分析列表信息
	 * @param page
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer, UserContext uc) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDesignCustSource(?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setString(3, customer.getDepartment1());
			call.setString(4, customer.getDepartment2());
			call.setString(5, customer.getStatistcsMethod());
			call.setString(6, uc.getCzybh());
			call.setString(7, uc.getCustRight());
			call.setString(8, customer.getDesignMan());
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
	 * 来客明细
	 * @author	created by zb
	 * @date	2019-9-9--下午5:52:52
	 * @param page
	 * @param employee
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findCrtDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee, UserContext uc) {
		List<Object> list = new ArrayList<Object>(); 
		String sql = "select * from (select cc.Code,cc.Address,cc.Area,cc.CustType,ct.Desc1 CustTypeDescr,cc.VisitDate,em2.NameChi BusinessMan,em2.Department1, "
					+"dm12.Desc1 Department1Descr,em2.Department2,dm22.Desc1 Department2Descr,em.NameChi DesignMan, "
					+"em.Department1 DesignDepartment1,dm1.Desc1 DesignDepartment1Descr,em.Department2 DesignDepartment2,dm2.Desc1 DesignDepartment2Descr "
					+"from tCustomer cc "
					+"left join tCusttype ct on ct.Code=cc.CustType "
					+"inner join tCustStakeholder cs on cs.CustCode = cc.Code and cs.Role='01' "
					+"inner join tEmployee em2 on em2.Number=cs.EmpCode "
					+"left join tDepartment1 dm12 on em2.Department1 = dm12.Code "
					+"left join tDepartment2 dm22 on em2.Department2 = dm22.Code "
					+"inner join tCustStakeholder cs3 on cs3.CustCode = cc.Code and cs3.Role='00' "
					+"inner join tEmployee em on cs3.EmpCode = em.Number "
					+"left join tDepartment1 dm1 on em.Department1 = dm1.Code "
					+"left join tDepartment2 dm2 on em.Department2 = dm2.Code "
					+"where cc.Expired='F' and ct.IsAddAllInfo='1' and cc.Status <> '1' ";
		if (null != employee.getDateFrom()) {
			sql += " and cc.VisitDate >= ? ";
			list.add(employee.getDateFrom());
		}
		if (null != employee.getDateTo()) {
			sql += " and cc.VisitDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(employee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(employee.getDepartment1Code())) {
			sql += " and dm12.code = ? ";
			list.add(employee.getDepartment1Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment2Code())) {
			sql += " and dm22.code = ? ";
			list.add(employee.getDepartment2Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment1())) {
			sql += " and dm1.code in ('"+employee.getDepartment1().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(employee.getDepartment2())) {
			sql += " and dm2.code in ('"+employee.getDepartment2().replace(",", "','")+"') ";
		}
		sql += "and (?=3 or (?=2 and exists( "
			 + "  select 1 from temployee in_a  "
			 + "  inner join tCZYDept in_b on (in_a.department1=in_b.department1 )  "
			 + "  and ((in_a.department2=in_b.department2) or (in_b.department2='') or (in_b.department2 is null))  "
			 + "  and ((in_a.department3=in_b.department3) or (in_b.department3='') or (in_b.department3 is null)) "
			 + "  and in_b.CZYBH=? "
			 + "  and in_a.number=em.Number "
			 + "))) ";
		list.add(uc.getCustRight());
		list.add(uc.getCustRight());
		list.add(uc.getCzybh());
		if (StringUtils.isNotBlank(employee.getDesignMan())) {
			 sql += " and cs3.EmpCode=? ";
			 list.add(employee.getDesignMan());
		}
		if (StringUtils.isNotBlank(employee.getNumber())) {
			 sql += " and cs.EmpCode=? ";
			 list.add(employee.getNumber());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " )a order by a.VisitDate ";
		}
		return findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 下定明细
	 * @author	created by zb
	 * @date	2019-9-9--下午5:50:25
	 * @param page
	 * @param employee
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findSetDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee, UserContext uc) {
		List<Object> list = new ArrayList<Object>(); 
		String sql = "select * from (select cc.Code,cc.Address,cc.Area,cc.CustType,ct.Desc1 CustTypeDescr,cc.SetDate,em2.NameChi BusinessMan,em2.Department1, "
					+"dm12.Desc1 Department1Descr,em2.Department2,dm22.Desc1 Department2Descr,em.NameChi DesignMan, "
					+"em.Department1 DesignDepartment1,dm1.Desc1 DesignDepartment1Descr,em.Department2 DesignDepartment2,dm2.Desc1 DesignDepartment2Descr "
					+"from tCustomer cc "
					+"left join tCusttype ct on ct.Code=cc.CustType "
					+"inner join tCustStakeholder cs on cs.CustCode=cc.Code and cs.Role='01' "
					+"inner join tEmployee em2 on em2.Number=cs.EmpCode "
					+"left join tDepartment1 dm12 on em2.Department1 = dm12.Code "
					+"left join tDepartment2 dm22 on em2.Department2 = dm22.Code "
					+"inner join tCustStakeholder cs3 on cs3.CustCode = cc.Code and cs3.Role='00' "
					+"inner join tEmployee em on cs3.EmpCode = em.Number "
					+"left join tDepartment1 dm1 on em.Department1 = dm1.Code "
					+"left join tDepartment2 dm2 on em.Department2 = dm2.Code "
					+"where cc.Expired='F' and ct.IsAddAllInfo='1' ";
		if (null != employee.getDateFrom()) {
			sql += " and cc.SetDate >= ? ";
			list.add(employee.getDateFrom());
		}
		if (null != employee.getDateTo()) {
			sql += " and cc.SetDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(employee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(employee.getDepartment1Code())) {
			sql += " and dm12.code = ? ";
			list.add(employee.getDepartment1Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment2Code())) {
			sql += " and dm22.code = ? ";
			list.add(employee.getDepartment2Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment1())) {
			sql += " and dm1.code in ('"+employee.getDepartment1().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(employee.getDepartment2())) {
			sql += " and dm2.code in ('"+employee.getDepartment2().replace(",", "','")+"') ";
		}
		sql += "and (?=3 or (?=2 and exists( "
			 + "  select 1 from temployee in_a  "
			 + "  inner join tCZYDept in_b on (in_a.department1=in_b.department1 )  "
			 + "  and ((in_a.department2=in_b.department2) or (in_b.department2='') or (in_b.department2 is null))  "
			 + "  and ((in_a.department3=in_b.department3) or (in_b.department3='') or (in_b.department3 is null)) "
			 + "  and in_b.CZYBH=? "
			 + "  and in_a.number=em.Number "
			 + "))) ";
		list.add(uc.getCustRight());
		list.add(uc.getCustRight());
		list.add(uc.getCzybh());
		if (StringUtils.isNotBlank(employee.getDesignMan())) {
			 sql += " and cs3.EmpCode=? ";
			 list.add(employee.getDesignMan());
		}
		if (StringUtils.isNotBlank(employee.getNumber())) {
			 sql += " and cs.EmpCode=? ";
			 list.add(employee.getNumber());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " )a order by a.SetDate ";
		}
		return findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 签单明细
	 * @author	created by zb
	 * @date	2019-9-9--下午5:50:37
	 * @param page
	 * @param employee
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findSignDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee, UserContext uc) {
		List<Object> list = new ArrayList<Object>(); 
		String sql = "select * from (select cc.Code,cc.Address,cc.Area,cc.CustType,ct.Desc1 CustTypeDescr,cc.SignDate,em2.NameChi BusinessMan,em2.Department1, "
					+"dm12.Desc1 Department1Descr,em2.Department2,dm22.Desc1 Department2Descr,em.NameChi DesignMan, "
					+"em.Department1 DesignDepartment1,dm1.Desc1 DesignDepartment1Descr,em.Department2 DesignDepartment2,dm2.Desc1 DesignDepartment2Descr,cc.ContractFee "
					+"from tCustomer cc "
					+"left join tCusttype ct on ct.Code=cc.CustType   "
					+"inner join tCustStakeholder cs on cs.CustCode=cc.Code and cs.Role='01' "
					+"inner join tEmployee em2 on em2.Number=cs.EmpCode "
					+"left join tDepartment1 dm12 on em2.Department1 = dm12.Code "
					+"left join tDepartment2 dm22 on em2.Department2 = dm22.Code "
					+"inner join tCustStakeholder cs3 on cs3.CustCode = cc.Code and cs3.Role='00' "
					+"inner join tEmployee em on cs3.EmpCode = em.Number "
					+"left join tDepartment1 dm1 on em.Department1 = dm1.Code "
					+"left join tDepartment2 dm2 on em.Department2 = dm2.Code "
					+"where cc.Expired='F' and ct.IsAddAllInfo='1' "
					+"and not exists (select  1 from  tAgainSign where CustCode=cc.Code) ";
		if (null != employee.getDateFrom()) {
			sql += " and cc.SignDate >= ? ";
			list.add(employee.getDateFrom());
		}
		if (null != employee.getDateTo()) {
			sql += " and cc.SignDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(employee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(employee.getDepartment1Code())) {
			sql += " and dm12.code = ? ";
			list.add(employee.getDepartment1Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment2Code())) {
			sql += " and dm22.code = ? ";
			list.add(employee.getDepartment2Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment1())) {
			sql += " and dm1.code in ('"+employee.getDepartment1().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(employee.getDepartment2())) {
			sql += " and dm2.code in ('"+employee.getDepartment2().replace(",", "','")+"') ";
		}
		sql += "and (?=3 or (?=2 and exists( "
			 + "  select 1 from temployee in_a  "
			 + "  inner join tCZYDept in_b on (in_a.department1=in_b.department1 )  "
			 + "  and ((in_a.department2=in_b.department2) or (in_b.department2='') or (in_b.department2 is null))  "
			 + "  and ((in_a.department3=in_b.department3) or (in_b.department3='') or (in_b.department3 is null)) "
			 + "  and in_b.CZYBH=? "
			 + "  and in_a.number=em.Number "
			 + "))) ";
		list.add(uc.getCustRight());
		list.add(uc.getCustRight());
		list.add(uc.getCzybh());
		if (StringUtils.isNotBlank(employee.getDesignMan())) {
			 sql += " and cs3.EmpCode=? ";
			 list.add(employee.getDesignMan());
		}
		if (StringUtils.isNotBlank(employee.getNumber())) {
			 sql += " and cs.EmpCode=? ";
			 list.add(employee.getNumber());
		}
		sql += "union all "
		  	+  "select cc.Code,cc.Address,cc.Area,cc.CustType,ct.Desc1 CustTypeDescr,asn.SignDate,em2.NameChi BusinessMan,em2.Department1, "
			+"dm12.Desc1 Department1Descr,em2.Department2,dm22.Desc1 Department2Descr,em.NameChi DesignMan, "
			+"em.Department1 DesignDepartment1,dm1.Desc1 DesignDepartment1Descr,em.Department2 DesignDepartment2,dm2.Desc1 DesignDepartment2Descr,Asn.ContractFee "
			+  "from tCustomer cc "
			+  "inner join tCustStakeholder cs on cs.CustCode=cc.Code and cs.Role='01' "
			+  "inner join tEmployee em2 on em2.Number=cs.EmpCode "
			+  "left join tDepartment1 dm12 on em2.Department1 = dm12.Code "
			+  "left join tDepartment2 dm22 on em2.Department2 = dm22.Code "
			+  "inner join (select custcode,min(pk) minpk from tAgainSign group by CustCode) AsMin on cc.Code =AsMin.Custcode "
			+  "inner join tAgainSign Asn on asmin.minpk=asn.pk ";
			if (null != employee.getDateFrom()) {
				sql += " and asn.SignDate >= ? ";
				list.add(employee.getDateFrom());
			}
			if (null != employee.getDateTo()) {
				sql += " and asn.SignDate <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(employee.getDateTo()).getTime()));
			}
		sql += "inner join tCustType ct on cc.Custtype=ct.code "
			+  "inner join tCustStakeholder cs3 on cs3.CustCode = cc.Code and cs3.Role='00' "
			+  "inner join tEmployee em on cs3.EmpCode = em.Number "
			+  "left join tDepartment1 dm1 on em.Department1 = dm1.Code "
			+  "left join tDepartment2 dm2 on em.Department2 = dm2.Code "
			+  "where cc.Expired='F' and ct.IsAddAllInfo='1' ";
		if (null != employee.getDateFrom()) {
			sql += " and asn.SignDate >= ? ";
			list.add(employee.getDateFrom());
		}
		if (null != employee.getDateTo()) {
			sql += " and asn.SignDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(employee.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(employee.getDepartment1Code())) {
			sql += " and dm12.code = ? ";
			list.add(employee.getDepartment1Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment2Code())) {
			sql += " and dm22.code = ? ";
			list.add(employee.getDepartment2Code());
		}
		if (StringUtils.isNotBlank(employee.getDepartment1())) {
			sql += " and dm1.code in ('"+employee.getDepartment1().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(employee.getDepartment2())) {
			sql += " and dm2.code in ('"+employee.getDepartment2().replace(",", "','")+"') ";
		}
		sql += "and (?=3 or (?=2 and exists( "
			 + "  select 1 from temployee in_a  "
			 + "  inner join tCZYDept in_b on (in_a.department1=in_b.department1 )  "
			 + "  and ((in_a.department2=in_b.department2) or (in_b.department2='') or (in_b.department2 is null))  "
			 + "  and ((in_a.department3=in_b.department3) or (in_b.department3='') or (in_b.department3 is null)) "
			 + "  and in_b.CZYBH=? "
			 + "  and in_a.number=em.Number "
			 + "))) ";
		list.add(uc.getCustRight());
		list.add(uc.getCustRight());
		list.add(uc.getCzybh());
		if (StringUtils.isNotBlank(employee.getDesignMan())) {
			 sql += " and cs3.EmpCode=? ";
			 list.add(employee.getDesignMan());
		}
		if (StringUtils.isNotBlank(employee.getNumber())) {
			 sql += " and cs.EmpCode=? ";
			 list.add(employee.getNumber());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " )a order by a.SignDate ";
		}
		return findPageBySql(page, sql, list.toArray());
	} 	
	
}

