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
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class LeaveEmpCustManageDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
			Customer customer, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select a.code custCode,a.Address,c.Desc1 custTypedescr,x1.note layOUtDescr,a.area,eb.NameChi businessManDescr" +
				" ,bd.Desc1 businessDept,ef.namechi againManDescr , ed.NameChi designManDescr , dd.Desc1 designDept" +
				" ,x2.note sourceDescr,a.Remarks,a.CrtDate,a.SetDate,a.signdate,x3.note endCodeDescr,x4.note statusDescr," +
				" cd.pk dpk,cb.pk bpk " +
				"  from tCustomer a " +
				" left join tCustStakeholder cd on cd.CustCode = a.Code and cd.role = '00' " +
				" left join tCustStakeholder cb on cb.CustCode = a.Code and cb.role = '01' " +
				" left join tEmployee ed on ed.Number= cd.empCode" +
				" left join temployee eb on eb.number = cb.EmpCode " +
				" left join tCusttype c on c.Code = a.CustType" +
				" left join temployee ef on ef.Number = a.AgainMan " +
				" left join tDepartment2 dd on dd.code = ed.Department2 " +
				" left join tDepartment2 bd on bd.Code = eb.Department2" +
				" left join tXTDM x1 on x1.id='Layout' and x1.cbm = a.Layout" +
				" left join tXTDM x2 on x2.id='CUSTOMERSOURCE' and x2.cbm = a.Layout " +
				" left join tXtdm x3 on x3.id='CUSTOMERENDCODE' and x3.cbm = a.endCode " +
				" left join tXTDM x4 on x4.id='CUSTOMERSTATUS' and x4.cbm = a.status " +
				" where 1=1 and "+
				SqlUtil.getCustRight(uc, "a", 0);

		if(StringUtils.isNotBlank(customer.getShowType())){
			if("1".equals(customer.getShowType())){//设计师离职
				sql+=" and ed.LeaveDate is not null and ed.status = 'SUS' ";
				if(StringUtils.isNotBlank(customer.getDepartment1())){
					sql+=" and ed.Department1 = ? ";
					list.add(customer.getDepartment1());
				}
				if(StringUtils.isNotBlank(customer.getDepartment2())){
					sql+=" and ed.Department2 = ? ";
					list.add(customer.getDepartment2());
				}
				if(customer.getDateFrom() != null){
					sql+=" and ed.LeaveDate >= ? ";
					list.add(new Timestamp(
							DateUtil.startOfTheDay( customer.getDateFrom()).getTime()));
				}
				if(customer.getDateTo() != null){
					sql+=" and ed.LeaveDate <= ?";
					list.add(new Timestamp(
							DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
				}
				if(StringUtils.isNotBlank(customer.getStakeholder())){
					sql+=" and ed.Number = ? ";
					list.add(customer.getStakeholder());
				}
			}else if("2".equals(customer.getShowType())){//业务员离职
				sql+=" and eb.LeaveDate is not null and eb.status = 'SUS' ";
				if(StringUtils.isNotBlank(customer.getDepartment1())){
					sql+=" and eb.Department1 = ? ";
					list.add(customer.getDepartment1());
				}
				if(StringUtils.isNotBlank(customer.getDepartment2())){
					sql+=" and eb.Department2 = ? ";
					list.add(customer.getDepartment2());
				}
				if(customer.getDateFrom() != null){
					sql+=" and eb.LeaveDate >= ? ";
					list.add(new Timestamp(
							DateUtil.startOfTheDay( customer.getDateFrom()).getTime()));
				}
				if(customer.getDateTo() != null){
					sql+=" and eb.LeaveDate <= ?";
					list.add(new Timestamp(
							DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
				}
				if(StringUtils.isNotBlank(customer.getStakeholder())){
					sql+=" and eb.Number = ? ";
					list.add(customer.getStakeholder());
				}
				
			}
		}else{
			sql+=" and 1=2";
		}
		if(StringUtils.isNotBlank(customer.getStatus())){
			sql+=" and a.status in ('"+customer.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql+=" and a.custtype in ('"+customer.getCustType().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getEndRemark())){
			sql+=" and a.endRemark like ? ";
			list.add("%"+customer.getEndRemark()+"%");
		}
		if(StringUtils.isNotBlank(customer.getEndCode())){
			sql+=" and a.endCode = ? ";
			list.add(customer.getEndCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.custCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result updateStakeholder(CustStakeholder custStakeholder) {
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
}
