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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustVisit;

@SuppressWarnings("serial")
@Repository
public class CustVisitDao extends BaseDao{

	/**
	 * custVisit_list分页查询
	 * @param page
	 * @param custVisit
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
			CustVisit custVisit) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select a.no,a.CustCode,b.Address,b.Descr CustDescr,b.CustType,c.Desc1 CustTypeDescr,a.VisitType,xt1.NOTE VisitTypeDescr, " +
				"a.Remarks,a.Status,xt2.NOTE StatusDescr,a.Satisfaction,xt3.NOTE SatisfactionDescr,b.Mobile1 CustPhone,b.BeginDate,b.checkOutDate, " +
				"a.VisitCZY,e3.NameChi VisitCZYDescr,a.VisitDate,b.ProjectMan,e1.NameChi ProjectManDescr,dt1.desc1 gcDeptDescr,a.IsComplete, " +
				"b.DesignMan,e2.NameChi DesignManDescr,dt2.Desc1 designDeptDescr,e1.Phone ProjectManPhone,e2.Phone DesignManPhone, " +
				"a.LastUpdate,a.LastUpdatedBy,e1.Department2 gcDept,e2.Department2 DesignDept " +
				"from tCustVisit a " +
				"left join tCustomer b on a.CustCode=b.Code " +
				"left join tCusttype c on b.CustType=c.Code " +
				"left join txtdm xt1 on a.VisitType=xt1.cbm and xt1.ID='VISITTYPE' " +
				"left join txtdm xt2 on a.Status=xt2.cbm and xt2.ID='VISITSTATUS' " +
				"left join txtdm xt3 on a.Satisfaction=xt3.cbm and xt3.ID='VISITSATIS' " +
				"left join tEmployee e1 on b.ProjectMan=e1.Number " +
				"left join tDepartment2 dt1 on e1.Department2=dt1.Code " +
				"left join tEmployee e2 on b.DesignMan=e2.Number " +
				"left join tDepartment2 dt2 on e2.Department2=dt2.Code " +
				"left join tEmployee e3 on a.VisitCZY=e3.Number " +
				"where 1=1 ";
		
		if (StringUtils.isBlank(custVisit.getExpired()) || "F".equals(custVisit.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(custVisit.getNo())) {
			sql += " and a.no = ? ";
			list.add(custVisit.getNo());
		}
		if (custVisit.getDateFrom() != null) {
			sql += " and a.VisitDate >= ? ";
			list.add(custVisit.getDateFrom());
		}
		if (custVisit.getDateTo() != null) {
			sql += " and a.VisitDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay(custVisit.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custVisit.getStatus())) {
			sql += " and a.status = ? ";
			list.add(custVisit.getStatus());
		}
		if (StringUtils.isNotBlank(custVisit.getVisitCZY())) {
			sql += " and a.visitCzy = ? ";
			list.add(custVisit.getVisitCZY());
		}
		if (StringUtils.isNotBlank(custVisit.getVisitType())) {
			sql += " and a.visitType = ? ";
			list.add(custVisit.getVisitType());
		}
		if (StringUtils.isNotBlank(custVisit.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+custVisit.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(custVisit.getDepartment2())) {
			sql += " and e1.Department2 in ('"+custVisit.getDepartment2().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 开工回访sql
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findSearchCust1BySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select 0 as IsCheck,a.Code,a.Address,a.CustType,b.Desc1 CustTypeDescr,a.ConfirmBegin, " +
				" a.ProjectMan,c.NameChi ProjectManDescr, d.Desc1 gcDeptDescr,xt.NOTE PrjItemDescr,xt2.Note VisitTypeDescr, " +
				" tv2.VisitDate, a.LastUpdate " +
				" from tcustomer a " +
				" left join tCusttype b on a.CustType=b.Code " +
				" left join tEmployee c on a.ProjectMan=c.Number " +
				" left join tDepartment2 d on c.Department2=d.Code " +
				" left join (select m.CustCode, max(PrjItem) PRJITEM from tPrjProg m where m.BeginDate = ( select max(BeginDate) " +
				" from tPrjProg q where q.CustCode = m.CustCode and BeginDate < getdate() ) group by m.CustCode) h on a.code = h.CustCode " +
				" left join tXTDM xt on h.prjItem = xt.CBM and xt.ID='PRJITEM' " +
				" left join (select CustCode,max(VisitType) VisitType from tCustVisit group by CustCode) tv on a.Code=tv.CustCode " +
				" left join txtdm xt2 on tv.VisitType=xt2.cbm and xt2.ID='VISITTYPE' " +
				" left join tCustVisit tv2 on tv.CustCode=tv2.CustCode and tv.VisitType=tv2.VisitType " +
				" where 1=1 and a.status='4' and a.Expired='F' and not exists (select 1 from tCustVisit where Expired='T' and CustCode=a.Code) ";
		
		if (StringUtils.isBlank(customer.getExpired()) || "F".equals(customer.getExpired())) {
			sql += " and not exists(select 1 from tCustVisit where VisitType='1' and CustCode=a.Code) ";
		}
		/*回访日期*/
		if (customer.getDateFrom() != null) {
			sql += " and tv2.VisitDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null) {
			sql += " and tv2.VisitDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
		}
		/*开工日期*/
		if (customer.getBeginDateFrom() != null) {
			sql += " and a.ConfirmBegin >= ? ";
			list.add(customer.getBeginDateFrom());
		}
		if (customer.getBeginDateTo() != null) {
			sql += " and a.ConfirmBegin <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getBeginDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in ('"+customer.getCustType().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		/*判断客户选择表中是否已存在(借haveCheck用一下)*/
		if (StringUtils.isNotBlank(customer.getHaveCheck())) {
			sql += " and a.Code not in ('"+customer.getHaveCheck().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 中期回访和后期回访sql
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findSearchCust2BySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select 0 as IsCheck,a.Code,a.Address,a.CustType,b.Desc1 CustTypeDescr,a.ConfirmBegin, " +
				" a.ProjectMan,c.NameChi ProjectManDescr, d.Desc1 gcDeptDescr,xt.NOTE PrjItemDescr,xt2.Note VisitTypeDescr, " +
				" tv2.VisitDate, a.LastUpdate " +
				" from tcustomer a " +
				" left join tCusttype b on a.CustType=b.Code " +
				" left join tEmployee c on a.ProjectMan=c.Number " +
				" left join tDepartment2 d on c.Department2=d.Code " +
				" left join (select m.CustCode, max(PrjItem) PRJITEM from tPrjProg m where m.BeginDate = ( select max(BeginDate) " +
				" from tPrjProg q where q.CustCode = m.CustCode and BeginDate < getdate() ) group by m.CustCode) h on a.code = h.CustCode " +
				" left join tPrjProg prj on h.PRJITEM=prj.PrjItem and h.CustCode=prj.CustCode " +
				" left join tXTDM xt on h.prjItem = xt.CBM and xt.ID='PRJITEM' " +
				" left join (select CustCode,max(VisitType) VisitType from tCustVisit group by CustCode) tv on a.Code=tv.CustCode " +
				" left join txtdm xt2 on tv.VisitType=xt2.cbm and xt2.ID='VISITTYPE' " +
				" left join tCustVisit tv2 on tv.CustCode=tv2.CustCode and tv.VisitType=tv2.VisitType " +
				" where 1=1 and a.Expired='F' " +
				" and not exists (select 1 from tCustVisit where Expired='T' and CustCode=a.Code) ";
		
		if (StringUtils.isBlank(customer.getExpired()) || "F".equals(customer.getExpired())) {
			if ("2".equals(customer.getVisitType())) {
				sql += " and not exists(select 1 from tCustVisit where VisitType='2' and CustCode=a.Code) ";
			}else {
				sql += " and not exists(select 1 from tCustVisit where VisitType='3' and CustCode=a.Code) ";
			}
		}
		if (StringUtils.isNotBlank(customer.getPrjItem())) {
			sql += " and h.PrjItem= ? ";
			list.add(customer.getPrjItem());
		}
		if (customer.getEndDateFrom() != null) {
			sql += " and prj.EndDate >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(customer.getEndDateFrom()).getTime()));/*获取这天的第一秒*/
		}
		if (customer.getEndDateTo() != null) {
			sql += " and prj.EndDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getEndDateTo()).getTime()));/*获取这天最后一秒*/
		}
		if (customer.getDateFrom() != null) {
			sql += " and tv2.VisitDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null) {
			sql += " and tv2.VisitDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
		}
		if (customer.getBeginDateFrom() != null) {
			sql += " and a.ConfirmBegin >= ? ";
			list.add(customer.getBeginDateFrom());
		}
		if (customer.getBeginDateTo() != null) {
			sql += " and a.ConfirmBegin <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getBeginDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in ('"+customer.getCustType().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		/*判断客户选择表中是否已存在(借haveCheck用一下)*/
		if (StringUtils.isNotBlank(customer.getHaveCheck())) {
			sql += " and a.Code not in ('"+customer.getHaveCheck().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 结算回访sql
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findSearchCust3BySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select 0 as IsCheck,a.Code,a.Address,a.CustType,b.Desc1 CustTypeDescr,a.ConfirmBegin, " +
				" a.ProjectMan,c.NameChi ProjectManDescr, d.Desc1 gcDeptDescr,xt.NOTE PrjItemDescr,xt2.Note VisitTypeDescr, " +
				" tv2.VisitDate, a.LastUpdate " +
				" from tcustomer a " +
				" left join tCusttype b on a.CustType=b.Code " +
				" left join tEmployee c on a.ProjectMan=c.Number " +
				" left join tDepartment2 d on c.Department2=d.Code " +
				" left join (select m.CustCode, max(PrjItem) PRJITEM from tPrjProg m where m.BeginDate = ( select max(BeginDate) " +
				" from tPrjProg q where q.CustCode = m.CustCode and BeginDate < getdate() ) group by m.CustCode) h on a.code = h.CustCode " +
				" left join tXTDM xt on h.prjItem = xt.CBM and xt.ID='PRJITEM' " +
				" left join (select CustCode,max(VisitType) VisitType from tCustVisit group by CustCode) tv on a.Code=tv.CustCode " +
				" left join txtdm xt2 on tv.VisitType=xt2.cbm and xt2.ID='VISITTYPE' " +
				" left join tCustVisit tv2 on tv.CustCode=tv2.CustCode and tv.VisitType=tv2.VisitType " +
				" where 1=1 and a.Expired='F' " +
				" and not exists (select 1 from tCustVisit where Expired='T' and CustCode=a.Code) ";
		
		if (StringUtils.isBlank(customer.getExpired()) || "F".equals(customer.getExpired())) {
			sql += " and not exists(select 1 from tCustVisit where VisitType='4' and CustCode=a.Code) ";
		}
		if (customer.getCheckOutDateFrom() != null) {
			sql += " and a.checkOutDate >= ? ";
			list.add(customer.getCheckOutDateFrom());
		}
		if (customer.getCheckOutDateTo() != null) {
			sql += " and a.checkOutDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getCheckOutDateTo()).getTime()));
		}
		if (customer.getDateFrom() != null) {
			sql += " and tv2.VisitDate >= ? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null) {
			sql += " and tv2.VisitDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in ('"+customer.getCustType().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		/*判断客户选择表中是否已存在(借haveCheck用一下)*/
		if (StringUtils.isNotBlank(customer.getHaveCheck())) {
			sql += " and a.Code not in ('"+customer.getHaveCheck().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 新增计划存储过程
	 * @author	created by zb
	 * @date	2018-7-17--下午4:55:10
	 * @param custVisit
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(CustVisit custVisit) {
		Assert.notNull(custVisit);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustVisitSave_forXml(?,?,?,?,?,?,?)}");
			call.setString(1, custVisit.getNo());
			call.setString(2, custVisit.getM_umState());
			call.setString(3, custVisit.getVisitType());
			call.setString(4, custVisit.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, custVisit.getDetailJson());
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

	/**
	 * TODO 通过code对工程信息进行查询
	 * @author	created by zb
	 * @date	2018-7-20--上午10:50:56
	 * @param page
	 * @param code
	 * @return
	 */
	public Page<Map<String, Object>> findPrjItemByCode(
			Page<Map<String, Object>> page, String code) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select a.Code,a.Address,a.CustType,b.Desc1 CustTypeDescr,a.BeginDate,a.ProjectMan,c.NameChi ProjectManDescr, " +
				" d.Desc1 gcDeptDescr,xt.NOTE PrjItemDescr, " +
				" case when ((a.CheckoutDate is not null ) and ( a.ConfirmBegin is not null)) " +
				" then isnull(datediff(day, a.ConfirmBegin, a.CheckoutDate), 0) - a.ConstructDay - ( " +
				" select cast(QZ as int) from tXTCS where ID = 'AddConstDay') " +
				" when a.CheckoutDate is null and a.ConfirmBegin is not null " +
				" then datediff(day, a.ConfirmBegin, getdate()) - a.ConstructDay - (select cast(QZ as int) from tXTCS where ID = 'AddConstDay' " +
				" ) else null end DelayNum " +
				" from tcustomer a " +
				" left join tCusttype b on a.CustType=b.Code " +
				" left join tEmployee c on a.ProjectMan=c.Number " +
				" left join tDepartment2 d on c.Department2=d.Code " +
				" left join (select m.CustCode, max(PrjItem) PRJITEM from tPrjProg m where m.BeginDate = ( select max(BeginDate) " +
				" from tPrjProg q where q.CustCode = m.CustCode and BeginDate < getdate() ) group by m.CustCode) h on a.code=h.CustCode " +
				" left join tXTDM xt on h.PRJITEM=xt.CBM and xt.ID='PRJITEM' " +
				" left join tCustVisit tv on a.Code=tv.CustCode where a.code= ? ";
		
		if (StringUtils.isNotBlank(code)) {
			list.add(code);
		}else {
			return null;
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 通过no查找客户问题
	 * @author	created by zb
	 * @date	2018-8-3--上午11:14:56
	 * @param page
	 * @param no
	 * @return
	 */
	public Page<Map<String, Object>> findCustProblemByNo(
			Page<Map<String, Object>> page, String no) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select a.pk,a.no,a.PromSource,xt1.NOTE PromSourceDescr,a.Status,xt2.NOTE StatusDescr,a.PromType1,pt1.Descr PromType1Descr, " +
				" a.promtype2,pt2.Descr PromType2Descr,a.PromRsn,pr.Descr PromRsnDescr,a.SupplCode,sp.Descr SupplDescr,a.InfoDate, " +
				" a.DealCZY,em.NameChi DealCzyDescr,a.PlanDealDate,a.DealRemarks,a.DealDate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.LastUpdate  " +
				" from tCustProblem a " +
				" left join tXTDM xt1 on a.PromSource=xt1.CBM and xt1.ID='PROMSOURCE' " +
				" left join tXTDM xt2 on a.Status=xt2.CBM and xt2.ID='PROMSTATUS' " +
				" left join tPromType1 pt1 on a.PromType1=pt1.Code " +
				" left join tPromType2 pt2 on a.PromType2=pt2.Code " +
				" left join tPromRsn pr on a.PromRsn=pr.Code " +
				" left join tSupplier sp on a.SupplCode=sp.Code " +
				" left join tEmployee em on a.DealCZY=em.Number where a.no=? ";
		
		if (StringUtils.isNotBlank(no)) {
			list.add(no);
		} else {
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 回访处理储存过程
	 * @author	created by zb
	 * @date	2018-8-5--下午3:49:47
	 * @param custVisit
	 * @return
	 */
	//@SuppressWarnings("deprecation") 忽略弃用标签
	public Result doUpdate(CustVisit custVisit) {
		Assert.notNull(custVisit);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustVisitUpdate_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, custVisit.getNo());
			call.setString(2, custVisit.getM_umState());
			call.setString(3, custVisit.getCustCode());
			call.setString(4, custVisit.getVisitType());
			call.setString(5, custVisit.getStatus());
			call.setString(6, custVisit.getVisitCZY());
			call.setTimestamp(7, custVisit.getVisitDate()==null?null : new Timestamp(custVisit.getVisitDate().getTime()));//由long转为time
			call.setString(8, custVisit.getSatisfaction());
			call.setString(9, custVisit.getIsComplete());
			call.setString(10, custVisit.getRemarks());
			call.setTimestamp(11, custVisit.getLastUpdate()==null?null : new Timestamp(custVisit.getLastUpdate().getTime()));
			call.setString(12, custVisit.getExpired());
			call.setString(13, custVisit.getActionLog());
			call.setString(14, custVisit.getLastUpdatedBy());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
			call.setString(17, custVisit.getDetailJson());
//			System.out.println(custVisit.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(15)));
			result.setInfo(call.getString(16));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description: TODO 明细列表分页查询
	 * @author	created by zb
	 * @date	2018-8-6--上午10:54:05
	 * @param page
	 * @param custVisit
	 * @param custProblem 
	 * @return
	 */
	public Page<Map<String, Object>> findDetailListPageBySql(
			Page<Map<String, Object>> page, CustVisit custVisit, CustProblem custProblem) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from(select a.no,a.CustCode,b.Address,b.Descr CustDescr,b.CustType,c.Desc1 CustTypeDescr,a.VisitType,xt1.NOTE VisitTypeDescr, " +
				" a.Remarks,a.Status,xt2.NOTE StatusDescr,a.Satisfaction,xt3.NOTE SatisfactionDescr,b.Mobile1 CustPhone,b.BeginDate,b.checkOutDate, " +
				" a.VisitCZY,e3.NameChi VisitCZYDescr,a.VisitDate,b.ProjectMan,e1.NameChi ProjectManDescr,dt1.desc1 gcDeptDescr,a.IsComplete, " +
				" b.DesignMan,e2.NameChi DesignManDescr,dt2.Desc1 designDeptDescr,e1.Phone ProjectManPhone,e2.Phone DesignManPhone, " +
				" a.LastUpdate,a.LastUpdatedBy,e1.Department2 gcDept,e2.Department2 DesignDept, " +
				" cp.Status PromStatus,xt4.Note PromStatusDescr,cp.PromType1,pt1.Descr PromType1Descr,cp.PromType2,pt2.Descr PromType2Descr, " +
				" cp.PromRsn,pr.Descr PromRsnDescr,cp.SupplCode,sp.Descr SupplCodeDescr,cp.DealRemarks,b.Mobile1 " +
				" from tCustVisit a " +
				" left join tCustomer b on a.CustCode=b.Code " +
				" left join tCusttype c on b.CustType=c.Code " +
				" left join txtdm xt1 on a.VisitType=xt1.cbm and xt1.ID='VISITTYPE' " +
				" left join txtdm xt2 on a.Status=xt2.cbm and xt2.ID='VISITSTATUS' " +
				" left join txtdm xt3 on a.Satisfaction=xt3.cbm and xt3.ID='VISITSATIS' " +
				" left join tEmployee e1 on b.ProjectMan=e1.Number " +
				" left join tDepartment2 dt1 on e1.Department2=dt1.Code " +
				" left join tEmployee e2 on b.DesignMan=e2.Number " +
				" left join tDepartment2 dt2 on e2.Department2=dt2.Code " +
				" left join tEmployee e3 on a.VisitCZY=e3.Number " +
				" left join tCustProblem cp on a.No=cp.No " +
				" left join txtdm xt4 on cp.Status=xt4.cbm and xt4.ID='PROMSTATUS' " +
				" left join tPromType1 pt1 on cp.PromType1=pt1.Code " +
				" left join tPromType2 pt2 on cp.PromType2=pt2.Code " +
				" left join tPromRsn pr on cp.PromRsn=pr.Code " +
				" left join tSupplier sp on cp.SupplCode=sp.Code " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(custVisit.getExpired()) || "F".equals(custVisit.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (custVisit.getDateFrom() != null) {
			sql += " and a.VisitDate >= ? ";
			list.add(custVisit.getDateFrom());
		}
		if (custVisit.getDateTo() != null) {
			sql += " and a.VisitDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay(custVisit.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(custVisit.getStatus())) {
			sql += " and a.status = ? ";
			list.add(custVisit.getStatus());
		}
		if (StringUtils.isNotBlank(custVisit.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+custVisit.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(custVisit.getDepartment2())) {
			sql += " and e1.Department2 in ('"+custVisit.getDepartment2().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(custVisit.getPromStatus())) {
			sql += " and cp.status = ? ";
			list.add(custVisit.getPromStatus());
		}
		if (StringUtils.isNotBlank(custVisit.getVisitType())) {
			sql += " and a.VisitType in ('"+custVisit.getVisitType().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(custProblem.getPromType2())) {
			sql += " and cp.PromType2 = ? ";
			list.add(custProblem.getPromType2());
		}
		if (StringUtils.isNotBlank(custProblem.getPromType1())) {
			sql += " and cp.PromType1 = ? ";
			list.add(custProblem.getPromType1());
		}
		if (StringUtils.isNotBlank(custProblem.getPromRsn())) {
			sql += " and cp.PromRsn = ? ";
			list.add(custProblem.getPromRsn());
		}
		if (StringUtils.isNotBlank(custProblem.getSupplCode())) {
			sql += " and cp.SupplCode = ? ";
			list.add(custProblem.getSupplCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
