package com.house.home.dao.query;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

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
import com.house.home.entity.design.Customer;
@SuppressWarnings("serial")
@Repository
public class PrjDelayAnalyDao extends BaseDao{

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
			call = conn.prepareCall("{Call pPrjDelayAnaly(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getDepartment2());
			call.setString(2, customer.getStatus());
			call.setTimestamp(3, customer.getBeginDateFrom() == null ? null : new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDateFrom()).getTime()));
			call.setTimestamp(4, customer.getBeginDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getBeginDateTo()).getTime()));
			call.setTimestamp(5, customer.getConfirmDateFrom() == null ? null : new Timestamp(
					DateUtil.startOfTheDay(customer.getConfirmDateFrom()).getTime()));
			call.setTimestamp(6, customer.getConfirmDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getConfirmDateTo()).getTime()));
			call.setTimestamp(7, customer.getEndDateFrom() == null ? null : new Timestamp(
					DateUtil.startOfTheDay(customer.getEndDateFrom()).getTime()));
			call.setTimestamp(8, customer.getEndDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getEndDateTo()).getTime()));
			call.setInt(9, customer.getAreaFrom()==null?-1:customer.getAreaFrom());
			call.setInt(10, customer.getAreaTo()==null?-1:customer.getAreaTo());
			call.setString(11, customer.getCustType());
			call.setInt(12, page.getPageSize());
			call.setInt(13, page.getPageNo());
			call.setString(14, page.getPageOrderBy());
			call.setString(15, page.getPageOrder() == ""?"asc":"desc");
			call.registerOutParameter(16, Types.INTEGER);
			call.setInt(17, customer.getConstructDay()==null?-1:customer.getConstructDay());
			call.setString(18, customer.getAddress());
			call.setString(19, customer.getSearchType());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(call.getInt(16));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}

	
	public Map<String,Object> getMoreInfo(String custCode){
		String sql ="select case when s.NOTE is null then '' else s.NOTE end BuildStatusDescr from tCustomer a"
				+" left join "
				+"	(select max(pk)pk,custcode from " 
				+" tbuilderrep a " 
				+" where  a.begindate between convert(varchar(100),getdate(),23) and  dateadd(d,1,dateadd(second,-1,convert(datetime,convert(varchar(100),getdate(),23))))  " 
				+" group by a.custcode ) br1 on br1.custcode=a.code  " 
				+" left join tbuilderrep br on br.pk=br1.pk " 
				+" left join txtdm s on s.cbm=br.buildstatus and s.id='buildstatus' "
				+" where a.Code = ? ";
		List<Map<String, Object>> list = this.findBySql(sql.toLowerCase(),new Object[] { custCode });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Integer getDelayDays(String custCode) {
		String sql = "  select"
		            + "     datediff(day, i.AssessmentStartDate, j.AssessmentEndDate) + 1 "
		            + "         - k.HolidayDays - a.ConstructDay - l.QZ delayDays "
                    + " from tCustomer a "
                    + " left join ( "
                    + "     select in_a.CustCode, min(in_a.ComeDate) ComeDate "
                    + "     from tCustWorker in_a "
                    + "     left join tWorkType12 in_b on in_b.Code = in_a.WorkType12 "
                    + "     where in_b.BeginCheck = '1' "
                    + "     group by in_a.CustCode "
                    + " ) cw on cw.CustCode = a.Code "
                    + " left join tPrjProg h on h.PrjItem = '16' and h.CustCode = a.Code "
                    + " outer apply ( "
                    + "     select "
                    + "         case when cw.ComeDate is not null then cw.ComeDate "
                    + "              else a.ConfirmBegin "
                    + "         end AssessmentStartDate "
                    + " ) i "
                    + " outer apply ( "
                    + "     select "
                    + "         case when h.ConfirmDate is not null then h.ConfirmDate "
                    + "              when a.CheckOutDate is not null then a.CheckOutDate "
                    + "              else getdate() "
                    + "         end AssessmentEndDate "
                    + " ) j "
                    + " outer apply ( "
                    + "     select count(in_a.Date) HolidayDays "
                    + "     from tCalendar in_a "
                    + "     where in_a.HoliType = '3' "
                    + "         and in_a.Date >= i.AssessmentStartDate "
                    + "         and in_a.Date <= j.AssessmentEndDate "
                    + " ) k "
                    + " left join tXTCS l on l.ID = 'AddConstDay' "
                    + " where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0 && list.get(0).get("delayDays") != null){
			return Double.valueOf(list.get(0).get("delayDays").toString()).intValue();
		}
		return null;
	} 

}
