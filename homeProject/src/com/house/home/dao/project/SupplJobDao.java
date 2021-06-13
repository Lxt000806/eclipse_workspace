package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.project.SupplJob;

@SuppressWarnings("serial")
@Repository
public class SupplJobDao extends BaseDao {

	/**
	 * SupplJob分页信息
	 * 
	 * @param page
	 * @param supplJob
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SupplJob supplJob) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from( SELECT a.no,c.Address,a.JobType,f.Descr JobTypeDescr, e.NOTE,b.date,b.RecvDate,b.PlanDate,"
				+ " b.CompleteDate,a.Remarks rwRemarks,b.Remarks,b.SupplRemarks,b.pk,b.Status,g.nameChi projectManDescr,g.phone projectManPhone,"
		        + " c.Code CustCode, c.Descr CustName, c.Mobile1 CustMobile1 "
				+ " FROM tPrjJob a "
				+ " INNER JOIN tSupplJob b on a.no = b.PrjJobNo "
				+ " LEFT JOIN tCustomer c on a.CustCode=c.Code " 
				+ " left join tEmployee g on g.Number = c.ProjectMan" 
				+ " LEFT JOIN tXTDM e ON e.id='SUPPLJOBSTS' and b.Status=e.IBM "
				+ " LEFT JOIN tJobType  f on f.Code=a.JobType where 1=1 ";
		
		if (StringUtils.isNotBlank(supplJob.getSupplCode())) {
			sql += " and b.SupplCode=? ";
			list.add(supplJob.getSupplCode());
		}
		if (supplJob.getDateFrom() != null) {
			sql += " and b.date>=?";
			list.add(supplJob.getDateFrom());
		}
		if (supplJob.getDateTo() != null) {
			sql += " and b.date<?";
			list.add(DateUtil.addInteger(supplJob.getDateTo(), Calendar.DATE, 1));
		}
		if (supplJob.getRecvDateFrom() != null) {
			sql += " and b.RecvDate>=?";
			list.add(supplJob.getRecvDateFrom());
		}
		if (supplJob.getRecvDateTo() != null) {
			sql += " and b.RecvDate<?";
			list.add(DateUtil.addInteger(supplJob.getRecvDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(supplJob.getAddress())) {
			sql += " and c.address like ?  ";
			list.add("%" + supplJob.getAddress() + "%");
		}
		if (supplJob.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(supplJob.getPk());
		}
		if (StringUtils.isNotBlank(supplJob.getPrjJobNo())) {
			sql += " and a.PrjJobNo=? ";
			list.add(supplJob.getPrjJobNo());
		}
		if (StringUtils.isNotBlank(supplJob.getStatus())) {
			sql += " and b.Status in ("+supplJob.getStatus()+")";
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) sort order by sort." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) sort order by sort.no";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 执行接收，退回，完成的存储过程
	 * 
	 * @param no
	 * @param pk
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void doExec(String type, String no, Integer pk, String recvDate,
			String supplRemarks, String planDate, String lastUpdatedBy) {
		Connection conn = null;
		CallableStatement call = null; 
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSupplDeal(?,?,?,?,?,?,?)}");// pDealCustComp
			call.setString(1, type);
			call.setInt(2, pk);
			call.setString(3, recvDate);
			call.setString(4, supplRemarks);
			call.setString(5, planDate);
			call.setString(6, lastUpdatedBy.trim());
			call.setString(7, no);
			call.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
	}
	
	public void doDelSuppl(Integer pk) {
		String sql = " delete from tSupplJob where pk= ? and status in('1','2','0') ";
		this.executeUpdateBySql(sql, new Object[]{pk});
	}
	/**
	 * SupplJob分页信息
	 * 
	 * @param page
	 * @param supplJob
	 * @return
	 */
	public Page<Map<String, Object>> findCupboardPageBySql(
			Page<Map<String, Object>> page, SupplJob supplJob) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (SELECT a.date,c.Address,a.JobType,b.RecvDate,b.PlanDate,e.Descr JobTypeDescr,g.NOTE,"
				+"b.CompleteDate,b.Status "
				+"FROM tPrjJob a "
				+"INNER JOIN tSupplJob b on a.no = b.PrjJobNo "
				+"LEFT JOIN tCustomer c on a.CustCode=c.Code " 
				+"LEFT JOIN tJobType  e on e.Code=a.JobType "
				+"LEFT JOIN tXTDM g ON g.id='SUPPLJOBSTS' and b.Status=g.IBM "
				+"WHERE 1=1 and a.JobType='08' " 
				+" and  exists (select 1 from tCustIntProg d where d.CustCode=c.Code and d.TableSpl=?) "
				+"and b.RecvDate>=? and b.RecvDate<=?";
		list.add(supplJob.getSupplCode());
		list.add(DateUtil.startOfTheDay(DateUtil.addDay(new Date(), -7)));
		list.add(new Date());
		if (StringUtils.isNotBlank(supplJob.getStatus())) {
			sql += " and b.Status in ("+supplJob.getStatus()+")";
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.date";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public void doUpdate(SupplJob supplJob) {
		String sql = " update tSupplJob set SupplRemarks=? where pk= ?";
		this.executeUpdateBySql(sql, new Object[]{supplJob.getSupplRemarks(),supplJob.getPk()});
	}
}
