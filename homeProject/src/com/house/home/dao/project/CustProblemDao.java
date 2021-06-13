package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.CustProblem;
import com.sun.org.apache.xpath.internal.operations.And;

@SuppressWarnings("serial")
@Repository
public class CustProblemDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustProblem custProblem, UserContext uc) {
		
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from(select  a.pk, a.No, a.PromSource, a.Status, a.PromType1, a.PromType2,"
				+ " a.PromRsn, a.SupplCode, a.CrtDate, a.InfoDate, a.DealCZY, a.PlanDealDate, "
				+ "a.DealRemarks, a.DealDate, b.Descr PromType1Descr, f.Descr PromRsnDescr,c.Descr PromType2Descr,"
				+ " d.Descr SupplDescr, e.NameChi DealCZYDescr,x1.note StatusDescr ,a.LastUpdatedBy, a.Expired,"
				+ " a.ActionLog, a.LastUpdate,e2.NameChi ProjectManDescr,g.Source,g.CrtDate JsCrtDate,dpt.Desc1 ProjectDept2,"
				+ "h.Address,h.Descr CustDescr,a.RcvDate,h.status AddessStatus,h.CustType,h.Mobile1,dpt2.Desc1 ProjectDept3,e3.NameChi DesignManDescr,"
				+ "e3.Phone DesignManPhone,e2.Phone ProjectManPhone,h.CheckOutDate,g.Status CompStatus,g.Remarks,g.CustCode,CrtCZY "
				+ "from  tCustProblem a "
				+ "left outer join tPromType1 b on b.code = a.PromType1 "
				+ "left outer join tPromType2 c on c.code = a.PromType2 "
				+ "left outer join tSupplier d on d.Code = a.SupplCode "
				+ "left outer join tEmployee e on e.Number = a.DealCZY "
				+ "left outer join tPromRsn f on f.code = a.PromRsn "
				+ "left outer join tXTDM x1 on x1.cbm = a.status and x1.id='PROMSTATUS' "
				+ "left outer join tCustComplaint g on a.no=g.no "
				+ "left outer join tCustomer h on h.Code=g.CustCode "
				+ "left outer join tEmployee e1 on e1.Number = g.CrtCZY "
				+ "left outer join tEmployee e2 on  e2.Number=h.ProjectMan "
				+ "left outer join tEmployee e3 on e3.Number = h.DesignMan  "
				+ "left outer join tDepartment2 dpt on  dpt.code=e2.Department2 "
				+ "left outer join tDepartment2 dpt2 on  dpt2.code=e3.Department2 "
				+ "left outer join tDepartment dpt3 on dpt3.code=e.Department "
				+ "where 1=1 and PromSource='2' ";
		if (StringUtils.isNotBlank(custProblem.getDealCZY())) {
			sql += " and a.dealCZY=?";
			list.add(custProblem.getDealCZY());
		}
		if (custProblem.getRcvDateFrom() != null) {
			sql += " and RcvDate>=?";
			list.add(custProblem.getRcvDateFrom());
		}
		if (custProblem.getRcvDateTo() != null) {
			sql += " and RcvDate<?";
			list.add(DateUtil.addInteger(custProblem.getRcvDateTo(),
					Calendar.DATE, 1));
		}
		if (custProblem.getInfoDateFrom() != null) {
			sql += " and InfoDate>=?";
			list.add(custProblem.getInfoDateFrom());
		}
		if (custProblem.getInfoDateTo() != null) {
			sql += " and InfoDate<?";
			list.add(DateUtil.addInteger(custProblem.getInfoDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(custProblem.getStatus())) {
			sql += " and a.Status in (" + custProblem.getStatus() + ")";
		}
		if (StringUtils.isNotBlank(custProblem.getAddress())) {
			sql += " and h.Address like ?";
			list.add("%"+custProblem.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(custProblem.getDepartment1())) {
			sql += " and e.Department1=?";
			list.add(custProblem.getDepartment1());
		}
		if (StringUtils.isNotBlank(custProblem.getDepartment2())) {
			sql += " and e.Department2=?";
			list.add(custProblem.getDepartment2());
		}
		if(uc != null && StringUtils.isNotBlank(uc.getCustRight())&& StringUtils.isBlank(custProblem.getAppQuery())){
			sql+=SqlUtil.getCustRightByCzy(uc, "a.dealCZY", "dpt3.Path");
		}
		if(StringUtils.isNotBlank(custProblem.getAppQuery()) && StringUtils.isNotBlank(custProblem.getAppQueryType())){
			if("1".equals(custProblem.getAppQueryType())){
				sql+=" and a.DealCzy = ? ";
				list.add(uc.getCzybh());
			} 
			if("2".equals(custProblem.getAppQueryType())){
				sql+= " and exists( " +
						"	select 1 from tEmployee in_a " +
						"	where in_a.number = ? " +
						"	and (" +
						"			(" +
						"				(in_a.department2 is null or in_a.department2 = '') " +
						"					and in_a.department1 = e.department1" +
						"			) or (in_a.department2 is not null and in_a.department2 <> '' and in_a.department2 = e.department2)" +
						"	)" +
						")";
				list.add(uc.getCzybh());
			}
		}
		if(StringUtils.isNotBlank(custProblem.getAppQuery())){
			sql+=" and a.status not in ('1','3')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")k order by k." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")k order by k.CustDescr ";
		}
		return this.findPageBySql(page, sql, list.toArray());

	}

	/**
	 * 客户投诉处理
	 * 
	 * @param no
	 * @param pk
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void dealCustCompaint(String no, int pk, String dealDate,
			String dealRemarks) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDealCustComp(?,?,?,?)}");// pDealCustComp
			call.setString(1, no);
			call.setInt(2, pk);
			call.setString(3, dealDate);
			call.setString(4, dealRemarks);
			call.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
	}

	public void doRcv(String planDealDate, Integer pk, Date rcvDate,
			String dealRemarks) {
		String sql = " update tCustProblem set planDealDate=?,rcvDate=?,Status=?,dealRemarks=? where pk=?";
		this.executeUpdateBySql(sql, new Object[] { planDealDate, rcvDate, "2",
				dealRemarks, pk });
	}

	public void doUpdate(CustProblem custProblem) {
		String sql = " update tCustProblem set dealRemarks=?,lastupdate=?,lastupdatedby=?,expired=?,actionlog=? where pk=?";
		this.executeUpdateBySql(
				sql,
				new Object[] { custProblem.getDealRemarks(),
						custProblem.getLastUpdate(),
						custProblem.getLastUpdatedBy(),
						custProblem.getExpired(), custProblem.getActionLog(),
						custProblem.getPk() });
	}
	
	public Page<Map<String, Object>> findAllBySql(
			Page<Map<String, Object>> page, CustProblem custProblem) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  a.pk, a.No, a.PromSource, a.Status, a.PromType1, a.PromType2, a.PromRsn, " +
				" a.SupplCode, a.CrtDate, a.InfoDate, a.DealCZY, a.PlanDealDate,   a.DealRemarks," +
				" a.DealDate, b.Descr PromType1Descr, f.Descr PromRsnDescr,  c.Descr PromType2Descr, " +
				"d.Descr SupplDescr, e.NameChi DealCZYDescr,x1.note StatusDescr , a.LastUpdatedBy, a.Expired, " +
				"a.ActionLog, a.LastUpdate  from  tCustProblem a  " +
				"left outer join tCustComplaint cc on a.No=cc.No  " +
				"left outer join tPromType1 b on b.code = a.PromType1 " +
				"left outer join tPromType2 c on c.code = a.PromType2  " +
				"left outer join tSupplier d on d.Code = a.SupplCode  " +
				"left  outer join tEmployee e on e.Number = a.DealCZY " +
				"left outer join tPromRsn f on f.code = a.PromRsn " +
				"left outer join tXTDM x1 on x1.cbm = a.status and x1.id='PROMSTATUS' " +
				"where a.PromType1='02' and cc.CustCode =? ";
		list.add(custProblem.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String,Object>> findPromType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tpromType1 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findPromType2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tpromType2 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.promType1= ? ";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findPromRsn(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tPromRsn a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.promType1= ? ";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
}
