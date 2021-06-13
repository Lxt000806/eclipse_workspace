package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.EmpWorkPlan;
import com.house.home.entity.finance.PerfCycle;

@SuppressWarnings("serial")
@Repository
public class EmpWorkPlanDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * EmpWorkPlan分页信息
	 * 
	 * @param page
	 * @param empWorkPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan) {
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.PlanCZY,a.PlanCZYType,a.PlanBeginDate,a.ActionLog,a.Expired,a.LastUpdate, "
				+"a.LastUpdatedBy,b.NameChi PlanCZYDescr,c.NOTE PlanCZYTypeDescr " 
				+"from tEmpWorkPlan a " 
				+"left join tEmployee b on a.PlanCZY=b.Number " 
				+"left join tDepartment d on b.Department=d.Code "
				+"left join tXTDM c on a.PlanCZYType=c.CBM and c.ID='PLANCZYTYPE' where 1=1 ";
		sql+=SqlUtil.getCustRightByCzy(uc, "a.PlanCZY", "d.Path");
    	if (empWorkPlan.getDateFrom() != null) {
			sql += " and a.PlanBeginDate>=? ";
			list.add(empWorkPlan.getDateFrom());
		}
    	if (empWorkPlan.getDateTo() != null) {
			sql += " and a.PlanBeginDate<dateadd(d,1,?) ";
			list.add(empWorkPlan.getDateTo());
		}
    	if (StringUtils.isNotBlank(empWorkPlan.getPlanCzy())) {
			sql += " and a.PlanCZY=? ";
			list.add(empWorkPlan.getPlanCzy());
		}
		if (StringUtils.isBlank(empWorkPlan.getExpired()) || "F".equals(empWorkPlan.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(empWorkPlan.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(empWorkPlan.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.PlanBeginDate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 是否存在本周计划
	 * @author cjg
	 * @date 2020-1-3
	 * @param planCzy
	 * @return
	 */
	public String isExistsThisPlan(EmpWorkPlan empWorkPlan) {
		String sql = "select 1 from tEmpWorkPlan where PlanCZY=? and PlanCZYType=? " 
					+"and datediff(d,PlanBeginDate,getdate())<7 and datediff(d,PlanBeginDate,getdate())>=0 ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{empWorkPlan.getPlanCzy(),empWorkPlan.getPlanCzyType()});
		if (list != null && list.size() > 0) {
			return "2";
		}
		return "1";
	}
	/**
	 * 是否存在下周计划
	 * @author cjg
	 * @date 2020-1-3
	 * @param planCzy
	 * @return
	 */
	public String isExistsNextPlan(EmpWorkPlan empWorkPlan) {
		String sql = "select 1 from tEmpWorkPlan where PlanCZY=? and PlanCZYType=? " 
					+"and datediff(d,PlanBeginDate,getdate())<0  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{empWorkPlan.getPlanCzy(),empWorkPlan.getPlanCzyType()});
		if (list != null && list.size() > 0) {
			return "1";
		}
		return "0";
	}
	/**
	 * 保存
	 * @param empWorkPlan
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(EmpWorkPlan empWorkPlan) {
		Assert.notNull(empWorkPlan);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pEmpWorkPlan(?,?,?,?,?,?,?,?)}");
			call.setInt(1, empWorkPlan.getPk()==null?0:empWorkPlan.getPk());
			call.setString(2, empWorkPlan.getM_umState());	
			call.setString(3, empWorkPlan.getLastUpdatedBy());
			call.setTimestamp(4, empWorkPlan.getPlanBeginDate()==null?null:new Timestamp(empWorkPlan.getPlanBeginDate().getTime()));
			call.setString(5, empWorkPlan.getPlanCzyType());
			call.setString(6, empWorkPlan.getEmpWorkPlanDetailJson());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/**
	 * EmpWorkPlanDetail分页信息
	 * 
	 * @param page
	 * @param empWorkPlan
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PlanPK,b.NOTE weekdescr,a.WeekDay week,a.PlanAddCust, "
				+"a.PlanContactCust,a.PlanMeasure,a.PlanArrive,a.PlanSet,a.PlanSign, "
				+"dateadd(d,a.WeekDay,c.PlanBeginDate) date "
				+"from tEmpWorkPlanDetail a  "
				+"left join tXTDM b on a.WeekDay=b.CBM and b.ID='WEEKDAY'  "
				+"inner join tEmpWorkPlan c on a.PlanPK=c.PK where a.PlanPK=? ";
		list.add(empWorkPlan.getPk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.week";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * EmpWorkPlanDetail分页信息
	 * 
	 * @param page
	 * @param empWorkPlan
	 * @return
	 */
	public Page<Map<String,Object>> findViewPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan) {
		List<Object> list = new ArrayList<Object>();
		String role="1".equals(empWorkPlan.getPlanCzyType())?"BusinessMan":"DesignMan";
		String sql = "select * from (select a.PlanPK,b.NOTE WeekDescr,a.WeekDay Week,a.PlanAddCust, "
				+"a.PlanContactCust,a.PlanMeasure,a.PlanArrive,a.PlanSet,a.PlanSign,  "
				+"dateadd(d,a.WeekDay,c.PlanBeginDate) Date,isnull(d.RealMeasure,0)RealMeasure, "
				+"isnull(e.RealArrive,0)RealArrive,isnull(f.RealSet,0)RealSet, "
				+"isnull(g.RealSign,0)RealSign,isnull(g.ContractFee,0)ContractFee, "
				+"isnull(h.ConNum,0)ConNum,isnull(i.RealContactCust,0)RealContactCust,isnull(j.RealAddCust,0)RealAddCust, "
				+"isnull(k.PlaneNum,0)PlaneNum,isnull(l.OnTimePlaneNum,0)OnTimePlaneNum "
				+"from tEmpWorkPlanDetail a  " 
				+"left join tXTDM b on a.WeekDay=b.CBM and b.ID='WEEKDAY' "  
				+"inner join tEmpWorkPlan c on a.PlanPK=c.PK "
				+"left join ( "
				+"	select count(1)RealMeasure,"+role+",convert(nvarchar(10),MeasureDate,120)MeasureDate " 
				+"	from tCustomer " 
				+"	group by "+role+",convert(nvarchar(10),MeasureDate,120) "
				+")d on c.PlanCZY=d."+role+" and d.MeasureDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)RealArrive,"+role+",convert(nvarchar(10),VisitDate,120)VisitDate  "
				+"	from tCustomer  "
				+"	group by "+role+",convert(nvarchar(10),VisitDate,120) "
				+")e on c.PlanCZY=e."+role+" and e.VisitDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)RealSet,"+role+",convert(nvarchar(10),SetDate,120)SetDate  "
				+"	from tCustomer  "
				+"	group by "+role+",convert(nvarchar(10),SetDate,120) "
				+")f on c.PlanCZY=f."+role+" and f.SetDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)RealSign,"+role+",convert(nvarchar(10),SignDate,120)SignDate ,sum(ContractFee)ContractFee "
				+"	from tCustomer  "
				+"	group by "+role+",convert(nvarchar(10),SignDate,120) "
				+")g on c.PlanCZY=g."+role+" and g.SignDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)ConNum,ConMan,convert(nvarchar(10),ConDate,120)ConDate  "
				+"	from tCustCon  "
				+"	where Type in ('1','3') "
				+"	group by ConMan,convert(nvarchar(10),ConDate,120) "
				+")h on c.PlanCZY=h.ConMan and h.ConDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(distinct case when isnull(CustCode,'')='' then ResrCustCode else CustCode end)RealContactCust,ConMan,convert(nvarchar(10),ConDate,120)ConDate  "
				+"	from tCustCon  "
				+"	where Type in ('1','3') "
				+"	group by ConMan,convert(nvarchar(10),ConDate,120) "
				+")i on c.PlanCZY=i.ConMan and i.ConDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)RealAddCust,"+role+",convert(nvarchar(10),CrtDate,120)CrtDate  "
				+"	from tCustomer  "
				+"	group by "+role+",convert(nvarchar(10),CrtDate,120) "
				+")j on c.PlanCZY=j."+role+" and j.CrtDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)PlaneNum,DesignMan,convert(nvarchar(10),PlaneDate,120)PlaneDate  "
				+"	from tCustomer  "
				+"	group by DesignMan,convert(nvarchar(10),PlaneDate,120) "
				+")k on c.PlanCZY=k.DesignMan and k.PlaneDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"left join ( "
				+"	select count(1)OnTimePlaneNum,DesignMan,convert(nvarchar(10),PlaneDate,120)PlaneDate  "
				+"	from tCustomer  "
				+"	where DATEDIFF(d,MeasureDate,PlaneDate)<=3 "
				+"	group by DesignMan,convert(nvarchar(10),PlaneDate,120) "
				+")l on c.PlanCZY=l.DesignMan and l.PlaneDate=convert(nvarchar(10),dateadd(d,a.WeekDay,c.PlanBeginDate),120) "
				+"where a.PlanPK=? ";
		list.add(empWorkPlan.getPk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.week";
		}
		page.setResult(this.findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		System.out.println(sql);
		System.out.println(list);
		return page;
	}
}

