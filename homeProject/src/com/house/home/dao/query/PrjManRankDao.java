package com.house.home.dao.query;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class PrjManRankDao extends BaseDao{
	/**
	 * 项目经理排名
	 * @author	created by zb
	 * @date	2019-5-15--下午3:12:20
	 * @param page
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pProjectManRanking(?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getDepartment2());
			call.setTimestamp(2, customer.getDateFrom() == null ? null : new Timestamp(
					DateUtil.startOfTheDay(customer.getDateFrom()).getTime()));
			call.setTimestamp(3, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setInt(4, page.getPageSize());
			call.setInt(5, page.getPageNo());
			call.setString(6, page.getPageOrderBy());
			call.setString(7, page.getPageOrder() == ""?"desc":"asc");
			call.registerOutParameter(8, Types.INTEGER);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(call.getInt(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	/**
	 * 查看结算明细
	 * @author	created by zb
	 * @date	2019-5-17--上午10:43:33
	 * @param page
	 * @param employee
	 * @return
	 */
	public Page<Map<String, Object>> findCheckOutDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		String sql = "select a.Address,a.Area,a.CustType,c.Desc1 CustTypeDescr,a.ConfirmBegin,f.ConfirmDate EndCheckDate, " +
					"a.EndDate,a.CustCheckDate, a.ConstructDay, g.EarliestComeDate BeginCheckDate, " +
					"datediff(day, isnull(g.EarliestComeDate, a.ConfirmBegin), isnull(f.ConfirmDate, a.EndDate)) " +
                    "     - isnull(h.NewYearDays, 0) + 1 WorkDay, " +
					"case when datediff(day, isnull(g.EarliestComeDate, a.ConfirmBegin), isnull(f.ConfirmDate, a.EndDate)) " +
                    "     - isnull(h.NewYearDays, 0) + 1 <= a.ConstructDay then '是' else '否' end IsOnTime, " +
					"isnull(d.ItemChg,0) ItemChg,isnull(e.BaseItemChg,0) BaseItemChg " +
					"from tCustomer a " +
					"left join tEmployee b on b.Number = a.projectMan  " +
					"left join tCusttype c on c.Code = a.CustType  " +
					"left join ( " +
					"	select CustCode, sum(Amount) ItemChg from tItemChg " +
					"	where Status='2' " +
					"	group by CustCode " +
					") d on d.CustCode=a.Code " +
					"left join ( " +
					"	select CustCode, sum(Amount) BaseItemChg from tBaseItemChg " +
					"	where Status='2' " +
					"	group by CustCode " +
					") e on a.Code=e.CustCode " +
					"left join tPrjProg f on f.CustCode=a.Code and f.ConfirmDate is not null and f.PrjItem='16' " +	//竣工验收
					" left join ( " +
					"     select in_a.CustCode, min(in_a.ComeDate) EarliestComeDate " +
					"     from tCustWorker in_a " +
					"     left join tWorkType12 in_b on in_a.WorkType12 = in_b.Code " +
					"     where in_b.BeginCheck = '1' " +
					"     group by in_a.CustCode " +
					" ) g on a.Code = g.CustCode " +
					" outer apply ( " +
					"     select count(in_a.Date) NewYearDays " +
					"     from tCalendar in_a " +
					"     where in_a.Date >= convert(nvarchar(10), isnull(g.EarliestComeDate, a.ConfirmBegin), 120) " +
					"         and in_a.Date <= convert(nvarchar(10), isnull(f.ConfirmDate, a.EndDate), 120) " +
					"         and in_a.HoliType = '3' " +
					" ) h " +
					"where a.EndDate is not null and a.EndDate>=? and a.EndDate<? " +
					"and a.ProjectMan=? ";
		return this.findPageBySql(page, sql, new Object[]{DateUtil.startOfTheDay(employee.getDateFrom()),
				DateUtil.endOfTheDay(employee.getDateTo()),employee.getNumber()});
	}
	/**
	 * 翻单明细
	 * @author	created by zb
	 * @date	2019-5-17--上午11:22:41
	 * @param page
	 * @param employee
	 * @return
	 */
	public Page<Map<String, Object>> findAgainDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		String sql = "select e.address,e.area,e.CustType,f.Desc1 custtypedescr,a.Type,tx.NOTE TypeDescr, " +
					"a.AchieveDate,a.Quantity,a.ContractFee, " +
					"b.LeaderCode,c2.NameChi LeaderName,c.Department2,d.Desc1 Department2Descr " +
					"from tPerformance a " +
					"inner join tPerfStakeholder b on a.PK=b.PerfPK and b.role in ('01','24') " +
					"left join tEmployee c on b.EmpCode = c.Number  " +
					"left join tEmployee c2 on b.LeaderCode = c2.Number  " +
					"left join tDepartment2 d on c.Department2 = d.Code  " +
					"left join tCustomer e on a.CustCode = e.Code  " +
					"left join tCusttype f on f.Code = e.CustType  " +
					"left join tXTDM tx on tx.ID='PERFTYPE' and a.Type=tx.CBM " +
					"where a.AchieveDate>=? and a.AchieveDate<? " +
					"and b.EmpCode=? and a.Type in ('1','4','5') " ; //只算1.正常业绩、4.退单扣业绩、5.重签扣业绩
		return this.findPageBySql(page, sql, new Object[]{DateUtil.startOfTheDay(employee.getDateFrom()),
				DateUtil.endOfTheDay(employee.getDateTo()),employee.getNumber()});
	}
	/**
	 * 二次销售
	 * @author	created by zb
	 * @date	2019-5-17--上午11:23:02
	 * @param page
	 * @param employee
	 * @return
	 */
	public Page<Map<String, Object>> findTwoSaleDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		String sql = "select a.Address,a.Area,a.SignDate,isnull(c.Amount,0) Amount " +
					"from tCustomer a " +
					"inner join tCusttype b on a.CustType=b.Code " +
					"inner join ( " +
					"	select CustCode, sum(Amount) Amount " +
					"	from tCustPay where AddDate>=? and AddDate<? " +
					"	group by CustCode " +
					") c on a.Code=c.CustCode " +
					"where b.IsAddAllInfo='0' and a.BusinessMan=? ";
		return this.findPageBySql(page, sql, new Object[]{DateUtil.startOfTheDay(employee.getDateFrom()),
				DateUtil.endOfTheDay(employee.getDateTo()),employee.getNumber()});
	}
	/**
	 * 安排单量
	 * @author	created by zb
	 * @date	2019-5-17--上午11:23:13
	 * @param page
	 * @param employee
	 * @return
	 */
	public Page<Map<String, Object>> findSetUpDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		String sql = "select a.address,a.area,a.CustType,b.Desc1 custtypedescr,c.LastUpdate " +
					"from tCustomer a " +
					"inner join tCusttype b on a.CustType=b.Code " +
					"inner join tCustStakeholder c on c.CustCode=a.Code and c.EmpCode=a.ProjectMan and c.Role='20' " +
					"where c.LastUpdate is not null and c.LastUpdate <> '' and c.LastUpdate>? " +
					"and c.LastUpdate<? and a.ProjectMan=? ";
		return this.findPageBySql(page, sql, new Object[]{DateUtil.startOfTheDay(employee.getDateFrom()),
				DateUtil.endOfTheDay(employee.getDateTo()),employee.getNumber()});
	}
	/**
	 * 在建明细
	 * @author	created by zb
	 * @date	2019-5-17--上午11:23:37
	 * @param page
	 * @param employee
	 * @return
	 */
	public Page<Map<String, Object>> findWorkingDetailPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		String sql = "select a.address,a.area,a.CustType,b.Desc1 custtypedescr,a.ConfirmBegin,a.ConstructDay, " +
					"    case when datediff(day, isnull(g.EarliestComeDate, a.ConfirmBegin), getdate()) " +
                    "        - isnull(h.NewYearDays, 0) + 1 > a.ConstructDay then '是' else '否' end IsLate, " +
                    "    g.EarliestComeDate BeginCheckDate " +
					"from tCustomer a " +
					"inner join tCusttype b on a.CustType=b.Code " +
                    "left join ( " +
                    "     select in_a.CustCode, min(in_a.ComeDate) EarliestComeDate " +
                    "     from tCustWorker in_a " +
                    "     left join tWorkType12 in_b on in_a.WorkType12 = in_b.Code " +
                    "     where in_b.BeginCheck = '1' " +
                    "     group by in_a.CustCode " +
                    " ) g on a.Code = g.CustCode " +
                    " outer apply ( " +
                    "     select count(in_a.Date) NewYearDays " +
                    "     from tCalendar in_a " +
                    "     where in_a.Date >= convert(nvarchar(10), isnull(g.EarliestComeDate, a.ConfirmBegin), 120) " +
                    "         and in_a.Date <= convert(nvarchar(10), getdate(), 120) " +
                    "         and in_a.HoliType = '3' " +
                    " ) h " +
					"where a.ConfirmBegin is not null and a.ConfirmBegin <> '' and a.ConfirmBegin<=getdate() " +
					"and a.Status='4' and a.ProjectMan=? ";
		return this.findPageBySql(page, sql, new Object[]{employee.getNumber()});
	}

}
