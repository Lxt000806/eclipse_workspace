package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetNoSendItemEvt;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.ProgTempDt;

@SuppressWarnings("serial")
@Repository
public class PrjProgDao extends BaseDao {

	/**
	 * PrjProg分页信息
	 * 
	 * @param page
	 * @param prjProg
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select tppp.photonum,a.*,s1.Descr PrjDescr,e.NameChi ConfirmDescr, " +
				" x.Note prjLevelDescr, isnull(f.SpaceDay,0) SpaceDay " +
				"  from tPrjProg a " +
				" left join (select COUNT(*) photoNum,CustCode,PrjItem from tPrjProgPhoto where type='1' " +
				" group by CustCode,PrjItem )tppp on tppp.custcode=a.custcode and tppp.prjitem= a.prjitem " +
				" left join tPrjItem1 s1 on s1.Code= a.PrjItem " +
				" left join tEmployee e on e.number=a.ConfirmCZY " +
				" left join tXTDM x on x.cbm=a.PrjLevel and x.id='PRJLEVEL' " +
				" left join tProgTempDt f on f.TempNo=? and f.PrjItem=a.PrjItem " +	//怎加间隔日期查询 add by zb on 20200403
				" where 1=1 ";
		list.add(prjProg.getPrjProgTempNo());
		if (prjProg.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(prjProg.getPk());
		}
		if (StringUtils.isNotBlank(prjProg.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjProg.getCustCode());
		}
		if (StringUtils.isNotBlank(prjProg.getPrjItem())) {
			sql += " and a.PrjItem=? ";
			list.add(prjProg.getPrjItem());
		}
		if (prjProg.getDateFrom() != null) {
			sql += " and a.PlanBegin>= ? ";
			list.add(prjProg.getDateFrom());
		}
		if (prjProg.getDateTo() != null) {
			sql += " and a.PlanBegin<= ? ";
			list.add(prjProg.getDateTo());
		}
		if (prjProg.getDateFrom() != null) {
			sql += " and a.BeginDate>= ? ";
			list.add(prjProg.getDateFrom());
		}
		if (prjProg.getDateTo() != null) {
			sql += " and a.BeginDate<= ? ";
			list.add(prjProg.getDateTo());
		}
		if (prjProg.getDateFrom() != null) {
			sql += " and a.PlanEnd>= ? ";
			list.add(prjProg.getDateFrom());
		}
		if (prjProg.getDateTo() != null) {
			sql += " and a.PlanEnd<= ? ";
			list.add(prjProg.getDateTo());
		}
		if (prjProg.getDateFrom() != null) {
			sql += " and a.EndDate>= ? ";
			list.add(prjProg.getDateFrom());
		}
		if (prjProg.getDateTo() != null) {
			sql += " and a.EndDate<= ? ";
			list.add(prjProg.getDateTo());
		}
		if (prjProg.getDateFrom() != null) {
			sql += " and a.LastUpdate>= ? ";
			list.add(prjProg.getDateFrom());
		}
		if (prjProg.getDateTo() != null) {
			sql += " and a.LastUpdate<= ? ";
			list.add(prjProg.getDateTo());
		}
		if (StringUtils.isNotBlank(prjProg.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjProg.getLastUpdatedBy());
		}
		if (StringUtils.isNotBlank(prjProg.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjProg.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.planBegin,a.prjItem";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	//没限制客户权限
	/*public Page<Map<String, Object>> findUpdateStopPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pGetPrjStop ";
		page.setResult(findListBySql(sql, list.toArray())); 			
		page.setTotalCount(page.getResult().size());
		return page;
		
	}*/
	
	public Page<Map<String, Object>> findUpdateStopPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.*,b.ConStaDate from dbo.getPrjStopTable() a" +
				" left join tcustomer b on b.code = a.CustCode " +
				" where 1=1 and "+
				 SqlUtil.getCustRight(uc, "b", 0);


		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findBuilderRepPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*, x1.note buildStatusdescr " +
				"from tbuilderRep a " +
				"left join tXTDM  x1 on x1.cbm = a.buildStatus and x1.id = 'buildstatus' " +
				"left join ( " +
				"    select b.Code, b.Descr, " +
				"    cast(dateadd(day, 1, case when b.Code='01' then a.ConfirmBegin else c.ConfirmDate end) as date) LastPrjItemConfirmDate, " +
				"    cast(isnull(d.ConfirmDate, getdate()) as date) EndPrjItemConfirmDate, a.Code CustCode " +
				"    from tCustomer a " +
				"    left join tProgStage b on 1 = 1 " +
				"    left join tPrjProg c on b.LastPrjItem = c.PrjItem and a.Code = c.CustCode " +
				"    left join tPrjProg d on b.EndPrjItem = d.PrjItem and a.Code = d.CustCode " +
				") b on a.CustCode = b.CustCode " +
				"where 1 = 1 and a.BuildStatus <> '1' " +
				"and a.BeginDate >= b.LastPrjItemConfirmDate and a.BeginDate <= b.EndPrjItemConfirmDate ";

		if(StringUtils.isNotBlank(prjProg.getCustCode())){
			sql+="and a.custCode = ? ";
			list.add(prjProg.getCustCode());
		}
		
		if (StringUtils.isNotBlank(prjProg.getProgStage())) {
            sql += "and b.Code = ? ";
            list.add(prjProg.getProgStage());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by a.pk desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findCustComplainPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.*,x1.note statusDescr,e.namechi crtczydescr from tCustComplaint a " +
				" left join tXTDM x1 on x1.cbm =a.status and x1.id='COMPSTATUS'" +
				" left join temployee e on e.number =a.crtczy " +
				" where 1=1 ";

		if(StringUtils.isNotBlank(prjProg.getCustCode())){
			sql+=" and a.custCode=?";
			list.add(prjProg.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "   order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by CrtDate";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

	public Page<Map<String, Object>> findConfirmPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.lastupdate,a.pk,a.CustCode,a.Expired,b.Address,b.ProjectMan,f.Department2,f.NameChi ProjectManDescr, a.PrjItem," +
				" a.PlanBegin,a.BeginDate,a.PlanEnd,a.EndDate,d.ZWXM ConfirmCZYDescr, isnull(PhotoCount, 0) PhotoCount, a.ConfirmCZY,a.ConfirmDate,a.ConfirmDesc " +
				" ,p.LastUpdate PhotoLastUpdate ,dpt.desc1 DepartmentDescr,c.NOTE PrjItemDescr " +
				"from tPrjProg a " +
				" left join tCustomer b on a.CustCode=b.Code" +
				" left join tXTDM c on a.PrjItem = c.CBM and c.ID = 'PRJITEM'" +
				" left join tCZYBM d on a.ConfirmCZY=d.CZYBH" +
				" left join (select  a.CustCode, a.PrjItem, count(*) PhotoCount from tPrjProg a inner join tPrjProgPhoto b on a.CustCode=b.CustCode and a.PrjItem=b.PrjItem where b.type='1' group by a.CustCode, a.PrjItem) e on a.CustCode = e.CustCode and a.PrjItem = e.PrjItem " +
				" left join tEmployee f on b.ProjectMan=f.Number" +
				" left join  tDepartment2 dpt on dpt.code=f.Department2" +
				" left  join(select max(LastUpdate)LastUpdate,CustCode,PrjItem  from tPrjProgPhoto group by CustCode ,PrjItem ) p on p.CustCode=a.CustCode and p.PrjItem =a.PrjItem " +
				" where 1=1  and a.EndDate is not null and " +
				 SqlUtil.getCustRight(uc, "b", 0);


		if (StringUtils.isBlank(prjProg.getExpired())
				|| "F".equals(prjProg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(prjProg.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%" + prjProg.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(prjProg.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjProg.getCustCode());
		}
		if (StringUtils.isNotBlank(prjProg.getDepartment2())) {
			sql += " and f.Department2=? ";
			list.add(prjProg.getDepartment2());
		}
		if (StringUtils.isNotBlank(prjProg.getPrjItem())) {
			String str = SqlUtil.resetStatus(prjProg.getPrjItem());
			sql += " and a.PrjItem in (" + str + ")";
		}
		if (StringUtils.isNotBlank(prjProg.getProjectMan())) {
			sql += " and b.projectMan=? ";
			list.add(prjProg.getProjectMan());
		}
		if (StringUtils.isNotBlank(prjProg.getNotConfirm())) {//未验收
			sql += " and a.ConfirmCZY is  null ";
		} 
		if (StringUtils.isNotBlank(prjProg.getNotCompleted())) {//未竣工
			sql += " and a.prjItem <>'16' ";
		} 
		if (StringUtils.isNotBlank(prjProg.getNotSign())) {//不显示施工班组
			sql +=  "  and not exists( select 1 from  tCustWorker cwk left join tWorker wk on cwk.WorkerCode = wk.Code " +
					" left join tWorkType12 wk12 on cwk.WorkType12 = wk12.Code  " +
					" left join tPrjProg prj on wk12.PrjItem = prj.PrjItem and cwk.CustCode = prj.CustCode  " +
					"where cwk.CustCode=a.CustCode and wk.IsSign='1' and prj.PrjItem=a.PRJITEM " +
					") ";
		} 
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
		
	}
	
	
	public Page<Map<String, Object>> findPrjProgUpdateJDPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select  a.CustCode,b.Address,d.Department2,b.ProjectMan,d.NameChi ProjectManDescr, c.NOTE PrjItemDescr,a.PlanBegin,a.BeginDate, a.PlanEnd,a.EndDate,a.LastUpdate  " +
				" from tPrjprog a " +
				" left join tCustomer b on a.CustCode=b.Code" +
				" left join tXTDM c on a.PrjItem = c.CBM and c.ID = 'PRJITEM'" +
				" left join tEmployee d on b.ProjectMan=d.Number " +
				"" +
				" where 1=1  and (a.BeginDate is not null or a.EndDate is not null) ";
		
		if (StringUtils.isNotBlank(prjProg.getDepartment2())) {
			sql += " and d.Department2 = ? ";
			list.add(prjProg.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(prjProg.getProjectMan())) {
			sql += " and b.Address = ? ";
			list.add(prjProg.getProjectMan());
		}
		
		if (prjProg.getDateFrom() != null) {
			sql += " and a.LastUpdate>= ? ";
			list.add(prjProg.getDateFrom());
		}
		
		if (prjProg.getDateTo() != null) {
			sql += " and a.LastUpdate<= ? ";
			list.add(prjProg.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String, Object>> findPrjLogPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg) {
		List<Object> list = new ArrayList<Object>();
		String sql = " exec  pGcjdfx ?,?,? ";
		list.add(prjProg.getCustCode());
		list.add(prjProg.getIsDelay());
		list.add(prjProg.getPrjJobType());
		page.setResult(findListBySql(sql, list.toArray())); 			
		page.setTotalCount(page.getResult().size());
		return page;
	}
		
	public Page<Map<String, Object>> findPageByProjectMan(
			Page<Map<String, Object>> page, String projectMan) {
		String sql = "select c.Code,c.Address,c.ProgPK,c.ConfirmBegin,d.Pk,e.NOTE,d.PlanBegin,d.BeginDate,"
				+ "case when d.prjItem='16' and d.EndDate is not null then datediff(day,d.PlanEnd,d.Enddate) else "
				+ "(case when DATEDIFF(day,d.PlanBegin,d.BeginDate)>DATEDIFF(day,d.PlanEnd,getdate()) "
				+ "then DATEDIFF(day,d.PlanBegin,d.BeginDate) "
				+ "else DATEDIFF(day,d.PlanEnd,getdate()) end) end Delay "
				+ "from ( "
				+ "select a.Code,a.Address,a.ConfirmBegin,(select max(Pk) from tPrjProg f where f.CustCode=a.Code and f.Begindate="
				+ "(select max(Begindate) from tPrjProg b where b.CustCode=a.Code) "
				+ " ) ProgPK from tCustomer a where a.ProjectMan=? "
				+ " and (a.Status='4' or (a.Status='5' and a.EndCode='3' and DateDiff(day,a.EndDate,GetDate())<=10)) ) c "
				+ "left join tPrjProg d on c.ProgPK=d.Pk "
				+ "left join tXTDM e on d.PRJITEM = e.CBM and e.ID = 'PRJITEM' "
				+ "order by c.ConfirmBegin desc";
		return this.findPageBySql(page, sql, new Object[] { projectMan });

	}

	public Map<String, Object> getPrjProgByPk(Integer pk) {
		String sql ="select e.Descr prjItemDescr,e.prjphotoNum,a.*,CASE WHEN a.BeginDate IS NULL THEN '未开始' " 
			    +" WHEN a.enddate IS NOT NULL and a.ConfirmCZY is null  THEN '已完工' "
			    +" WHEN a.EndDate is not null and a.ConfirmCZY is not null then '已验收' ELSE '施工中' END prjStatus  from tPrjProg a left join tPrjItem1 e on a.PRJITEM = e.Code where pk=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { pk });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, String code) {
		String sql = "select * from (select a.*,b.address,t.Note PrjItemDescr,t2.note prjLevelDescr, "
				+"CASE WHEN a.EndDate is not null and (a.ConfirmDate is not null or a.ConfirmCZY is not null) then '已验收' "  
			    +"WHEN a.enddate IS NOT NULL and (a.ConfirmDate is null or a.ConfirmCZY is null) THEN '已完工' "
			    +"WHEN a.BeginDate IS NULL THEN '未开始' ELSE '施工中' END prjStatus,"
			    +"tc.zwxm "
				+"from tPrjProg a inner join tCustomer b on a.CustCode=b.code and a.CustCode=? "
				+"left join tXTDM t on a.PrjItem = t.IBM and t.ID = 'PRJITEM' "
				+"left join tXTDM t2 on a.PrjLevel = t2.IBM and t2.ID = 'PRJLEVEL' "
				+"left join tCZYBM tc on tc.czybh=a.confirmCzy " +
				" where 1=1  ";
				

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		}else{
			sql += ") a order by a.PlanBegin,a.PrjItem";
		}
		return this.findPageBySql(page, sql, new Object[] { code });
	}

	public Map<String, Object> getPrjProgAlarm(String code, String prjItem,String dayType) {
		String sql = "select d.*,xt.Note dayTypeDescr,a.address,a.Code,a.projectMan from tCustomer a inner join tPrjProgTemp b on a.PrjProgTempNo=b.No "
				+ "inner join tProgTempAlarm d on b.No=d.tempNo "
				+ "left join txtdm xt on d.dayType=xt.cbm and xt.id='ALARMDAYTYPE' "
				+ "where a.Code=? and d.PrjItem=? and d.dayType=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code,prjItem,dayType});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public Result updatePrjProgForProc(int pk, String dayType, Date curDate,String czybh) {
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
					.prepareCall("{Call pUpdatePrjProg_forProc(?,?,?,?,?,?)}");
			call.setInt(1, pk);
			call.setString(2, dayType);
			call.setTimestamp(3, curDate == null ? null : new Timestamp(curDate.getTime()));
			call.setString(4, czybh);
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
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

	public Map<String, Object> getDelayAndRemain(String projectMan, String code) {
		Xtcs xtcs=this.get(Xtcs.class, "AddConstDay");
		List<Object> lists=new ArrayList<Object>();
		String sql="select *,a.ConstructDate - a.progressDate remainDate" +
				"  from(	select	 case when d.prjItem='16' and d.EndDate is not null then datediff(day,d.PlanEnd,d.Enddate) else "
				+"		(case when DATEDIFF(day,d.PlanBegin,d.BeginDate)>DATEDIFF(day,d.PlanEnd,getdate()) " 
				+"		then DATEDIFF(day,d.PlanBegin,d.BeginDate) "
				+"		else DATEDIFF(day,d.PlanEnd,getdate()) end) end Delay, "
				+" 		case when cw.ComeDate is null " 
				+"				then case when ((e.checkoutdate is not null)" 
				+"								and (e.confirmbegin is not null)" 
				+"							)" 
				+"						then isnull(datediff(day, e.confirmbegin, e.checkoutdate), 0) " 
				+"								- (select count(1) from tCalendar where Date >= e.ConfirmBegin and Date <= e.CheckOutDate and HoliType = '3') + 1 " 
				+"						when e.checkoutdate is null" 
				+"								and e.confirmbegin is not null" 
				+"								then datediff(day, e.confirmbegin, getdate()) " 
				+"  							- (select count(1) from tCalendar where Date >= e.ConfirmBegin and Date <= getdate() and HoliType = '3') + 1 "	
				+"						else null" 
				+"					end " 
				+"				else " 
				+"					case when e.checkoutdate is not null" 
				+"						then isnull(datediff(day, cw.ComeDate, e.checkoutdate), 0) " 
				+"								- (select count(1) from tCalendar where Date >= cw.ComeDate and Date <= e.CheckOutDate and HoliType = '3') + 1 " 
				+"						when e.checkoutdate is null" 
				+"						then datediff(day, cw.ComeDate, getdate()) " 
				+"  							- (select count(1) from tCalendar where Date >= cw.ComeDate and Date <= getdate() and HoliType = '3') + 1 "	
				+"						else null" 
				+"					end " 
				+"				end as progressDate, " 
				+"		e.ConstructDay+ "+xtcs.getQz()+" ConstructDate "
				+"		 from  (select a.Code,a.Address,a.ConfirmBegin,(select max(Pk) from tPrjProg f where f.CustCode=a.Code and f.Begindate= "
				+"		(select max(Begindate) from tPrjProg b where b.CustCode=a.Code)) ProgPK from tCustomer a  ";
		if(StringUtils.isNotBlank(projectMan)){
			sql+=" where a.ProjectMan=?";
			lists.add(projectMan);
		}
		sql+=") c 	left join tPrjProg d on c.ProgPK=d.Pk "
				 +" inner join  tCustomer e on e.Code=d.CustCode "
				 +" left join (select a.CustCode,min(a.ComeDate) ComeDate from tCustWorker a " 
				 +" 	left join tWorkType12 b on b.Code = a.WorkType12" 
				 +"		where b.beginCheck = '1'" 
				 +" 	group by a.CustCode" 
				 +" ) cw on cw.CustCode= e.Code"
				 +"  where d.CustCode=? ) a";
		lists.add(code);
		List<Map<String, Object>> list = this.findBySql(sql,
				lists.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> getPrjProgByCodeAndPrjItem(String code,
			String prjItem) {
		String sql ="SELECT * FROM tPrjProg WHERE CustCode=? AND  PrjItem=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code,prjItem});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getPrjProgByCodeAndPrjItemDescr(
			String custCode, String prjItemDescr) {
		if (StringUtils.isBlank(custCode)){
			return null;
		}
		String sql ="select a.prjItem,b.NOTE prjItemDescr "
			+"from tPrjProg a left join tXTDM b on a.PrjItem=b.CBM and b.ID='PRJITEM' "
			+"where a.CustCode=? ";
		if (StringUtils.isNotBlank(prjItemDescr)){
			sql += " and b.NOTE like '%" + prjItemDescr + "%' order by a.planBegin";
			return this.findBySql(sql, new Object[]{custCode});
		}else{
			sql += " order by a.planBegin";
			return this.findBySql(sql, new Object[]{custCode});
		}
		
	}
	
	/**
	 * 工程进度编辑 #param 
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doUpdate(PrjProg prjProg) {
		Assert.notNull(prjProg);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjProg_forXml(?,?,?,?)}");
			call.setString(1, prjProg.getCustCode());
			
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.setString(4, prjProg.getPrjProgXml());
			//String arrString= prjProg.getPrjProgXml();
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Map<String,Object> getPrjProgCurrentById(String custCode){
		String sql = "select  p.CustCode ,n.note ,p.PRJITEM,w.Phone,w.nameChi "
				+" from( " 
				+" select m.CustCode,( "
				+" 	select top 1 PRJITEM "  
				+" 	from  dbo.tPrjProg q "     
				+" 	where   q.CustCode = m.CustCode "  
				+" 	and BeginDate < getdate() " 
				+" 	order by BeginDate desc,PlanBegin desc ,PK desc " 
				+" ) PRJITEM "      
				+" from  dbo.tPrjProg m "   
				+" group by  m.CustCode" 
				+" ) p "
				+" left join dbo.tXTDM n on p.PRJITEM = n.CBM and n.ID = 'PRJITEM' "
				+" LEFT JOIN dbo.tPrjItem1 pi1 ON pi1.Code = p.PRJITEM "
				+" LEFT JOIN dbo.tWorkType12 wt12 ON pi1.worktype12 = wt12.Code "
				+" LEFT JOIN dbo.tCustWorker cw ON cw.WorkType12 = wt12.Code AND cw.CustCode = p.CustCode "
				+" LEFT JOIN dbo.tWorker w ON w.Code = cw.WorkerCode "
				+" where p.CustCode=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	/*
	  String sql = " insert into tPrjProg(CustCode,PrjItem,PlanBegin,PlanEnd, " +
													"LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
									"  select ?,PrjItem,DateAdd(d,(PlanBegin-1), ?),DateAdd(d,(planEnd-1),?), " +
									"getDate(),?,'F','add'  from  tProgTempDt  where tempNo= ? " ;*/
	/**
	 * 工程进度——模板设定生成静态模板
	 * 
	 * */
	public void doSavePrjProg(String code,Date planBegin,Date planEnd, String lastUpDatedBy ,String tempNo ) {
		String sql = " insert into tPrjProg(CustCode,PrjItem,PlanBegin,PlanEnd, LastUpdate,LastUpdatedBy,Expired,ActionLog) " +
						" select ? ,PrjItem,DateAdd(d,(PlanBegin-1+newspaceday-SpaceDay), ?)," +
						" DateAdd(d,(planEnd-1+newspaceday-SpaceDay),?),getDate(),?,'F','add'" +
						" from (select a.PrjItem,a.PlanBegin,a.SpaceDay,a.PlanEnd,(case when a.spaceDay<>0 then" +
						"  b.PlanEnd+a.spaceDay else b.planEnd end) bplanEnd, " +
						" (case when b.PlanEnd+a.SpaceDay<>0 then " +
						" (select isNull(SUM(c.SpaceDay),0) from tProgTempDt c where c.TempNo= ? and c.PlanBegin<b.PlanEnd ) else 0 end )newspaceday" +
						" From tProgTempDt a" +
						" left join tProgTempDt b on b.PK=a.PK where a.TempNo = ? ) b";
													
		this.executeUpdateBySql(sql, new Object[]{code,planBegin ,planEnd,lastUpDatedBy,tempNo,tempNo});
	}
	
	/**
	 * 工程进度——模板设定生成动态模板
	 * 
	 * */
	public void doSavePrjProgNew(String code,Date planBegin, String lastUpDatedBy ,String tempNo ) {
		String sql = " exec psetPrjModel ?,?,?,?";						
		this.executeUpdateBySql(sql, new Object[]{code,tempNo,planBegin,lastUpDatedBy});
	}
	
	
	
	
	public void doSavePrjProgBeginDate() {
		String sql = " update tPrjProg set BeginDate=PlanBegin where LastUpdate=(select a.lastupdate " +
				"	from (select top 1 max(LastUpdate) lastupdate ,MIN(PrjItem) prjitem from tPrjProg )a  )and PrjItem=(select a.prjitem" +
				"	from (select top 1 max(LastUpdate) lastupdate ,MIN(PrjItem) prjitem from tPrjProg )a  )";
		this.executeUpdateBySql(sql);
	}
	
	
	/**
	 * 工程进度 顺延
	 * 
	 * */
	public void doPostPone(Integer postPoneDate ,Integer postPoneEndDate ,String custCode,Date planBegin) {
		String sql=" update tPrjProg set planBegin = DateAdd(d,?,planBegin)," +
									"planEnd = DateAdd(d,?,PlanEnd) where CustCode= ? and planBegin >= ? ";
	
		this.executeUpdateBySql(sql, new Object[]{postPoneDate,postPoneEndDate,custCode,planBegin});

	}
	
	public void doDelPicture(String photoName ){
		
		String sql=" delete from tPrjProgPhoto where PhotoName= ? " ;
		
		this.executeUpdateBySql(sql, new Object[]{photoName});

	}
	
	
	public Map<String,Object> getMaxPk(String custCode){
		String sql=" select top 1 PK,PlanBegin,PrjItem from tPrjProg " +
							"where CustCode = ? order by PlanBegin,PK ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	
	public List<PrjItem1> getPrjItem1List(){
		String hql = " FROM PrjItem1 WHERE IsConfirm='1' AND Expired='F' ORDER BY Seq ";
		return this.find(hql, null);
	}
	
	
	public Map<String,Object> isConfirm(String custCode){
		String sql=" select pk From tPrjProg where custCode=? and ( EndDate is not null or ConfirmDate is not null )";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	
	public boolean getPrjProgPK(String code){
		String sql=" select * from tPrjProg where custCode = ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	
	public void isPassConfirm(String custCode,String prjItem,String confirmCZY,String ConfirmDesc,String prjLevel) {
		String sql = "update tprjProg set confirmDesc=?,prjlevel = ?,lastUpdate=getDate(),lastUpdatedBy=?, " +
				"ConfirmCZY=case when isnull(ConfirmCZY, '') = '' then ? else ConfirmCZY end, " + 
				"ConfirmDate=case when isnull(ConfirmDate, '') = '' then getdate() else ConfirmDate end " + 
				" where custCode=? and prjItem = ? ";
		this.executeUpdateBySql(sql, new Object[]{ConfirmDesc,prjLevel,confirmCZY, confirmCZY,custCode,prjItem});
	}
	                                 
	public void notPassConfirm(String custCode,String prjItem,String confirmDesc,String confirmCZY) {
		String sql = "update tprjProg set confirmCZY = null,confirmDesc = ?,prjlevel = null,confirmDate = null,lastUpdate=getDate(),lastUpdatedBy=? " +
				"where custCode=? and prjItem = ? ";
		this.executeUpdateBySql(sql, new Object[]{confirmDesc,confirmCZY,custCode,prjItem});
	}
	
	
	public void doReturnCheck(int pk ,String czybh) {
		String sql = " update tPrjProg set confirmCZY=null,confirmDate=null,confirmDesc=null," +
				"prjLevel=null,lastUpdatedBy=?,lastupdate=getdate() where pk=? ";
		this.executeUpdateBySql(sql, new Object[]{czybh,pk});
	}
	
	public boolean isExistsCustCheck(String custCode){
		boolean result = false;
		String sql = " SELECT 1 FROM dbo.tCustCheck WHERE CustCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			result = true;
		}
		return result;
	}

	public List<Map<String,Object>> getNoSendItem(GetNoSendItemEvt evt){
		List<Object> params = new ArrayList<Object>();
		String sql = " select distinct ia.itemType1,i.Descr itemDescr,it1.Descr itemType1Descr "
				   + " from tItemApp ia "
				   + " left join tItemAppDetail iap on ia.No = iap.No "
				   + " left join titem i on i.Code = iap.ItemCode "
				   + " left join tItemType1 it1 on it1.Code = ia.ItemType1 "
				   + " where ia.CustCode = ? and ia.Status in ('OPEN','CONFIRMED','CONRETURN') "
				   + " order by ia.itemType1 asc ";
		params.add(evt.getCustCode());
		return this.findBySql(sql, params.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doUpdateCustStatus(PrjProg prjProg) {
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
					.prepareCall("{Call pUpdateCustStatus(?,?,?,?)}");
			call.setString(1, prjProg.getLastUpdatedBy());
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR);
			call.setString(4, prjProg.getPrjProgXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public void updateIsPushCust(int pk,String isPushCust) {
		float i = 0l;
		String sql = " update tPrjProgPhoto set  IsPushCust = ?,lastUpdate = getDate() where pk = ? ";
		i = this.executeUpdateBySql(sql, new Object[]{isPushCust, pk});
		if(i > 0){
			sql = " update tPrjProgConfirm set lastUpdate = getDate(),IsPushCust =  " +
					" case when (select count(1) from tPrjProgPhoto " +
					" where RefNo = a.no and IsPushCust ='1')>0 then '1' else '0' end " +
					" from tPrjProgConfirm a where a.no  = (select refno from tPrjProgPhoto where pk = ? )";
					this.executeUpdateBySql(sql, new Object[]{pk});
		}
	}
	
	public void updateIsPushCustAll(PrjProgPhoto prjProgPhoto) {
		String sql = " update tPrjProgPhoto set  IsPushCust = ?,lastUpdate = getDate()" +
				" where CustCode = ? and PrjItem = ? and Type = ? and Expired = 'F' and refNo = ? ";
		this.executeUpdateBySql(sql, new Object[]{prjProgPhoto.getIsPushCust(),prjProgPhoto.getCustCode(),prjProgPhoto.getPrjItem()
				,prjProgPhoto.getType(),prjProgPhoto.getRefNo()});
		
		String sql_con = " update tPrjProgConfirm set isPushCust = ? ,lastUpdate = getDate() where no = ? ";
		this.executeUpdateBySql(sql_con, new Object[]{prjProgPhoto.getIsPushCust(),prjProgPhoto.getRefNo()});
	}
	/**
	 * 计划进度编排保存
	 * @author cjg
	 * @date 2019-9-13
	 * @param prjProg
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doProgArrange(PrjProg prjProg) {
		Assert.notNull(prjProg);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pProgArrange(?,?,?,?,?,?)}");
			call.setString(1, prjProg.getCustCode());
			call.setTimestamp(2, prjProg.getConfirmBegin()==null?null:new Timestamp(prjProg.getConfirmBegin().getTime()));
			call.setInt(3, prjProg.getConstructDay());
			call.setString(4, prjProg.getPrjProgJson());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
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
	
	public Page<Map<String, Object>> findLongTimeStopPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql= "select * from ("
		        +"select a.Code,a.Address,c.NameChi ProjectManDescr,a.ConfirmBegin, "
				+" b.LastSignDate,b.LastSignWorkType12Descr,d.LastConfirmPrjItemDescr, "
				+" datediff(day,b.LastSignDate,getdate()) stopDays ,a.PrgRemark,a.PrgRmkDate,e.Desc1 CustTypeDescr,a.Area,xt1.NOTE StatusDescr,xt2.NOTE ConstructStatusDescr, "
				+" f.Desc1 Department2Descr,d.PrjItem, "
				+" case when cw.ComeDate is null " 
				+"				then case when ((a.checkoutdate is not null)" 
				+"								and (a.confirmbegin is not null)" 
				+"							)" 
				+"						then isnull(datediff(day, a.confirmbegin, a.checkoutdate), 0) - a.ConstructDay" 
				+"								- dbo.fGetConstDay() - (select count(1) from tCalendar where Date >= a.ConfirmBegin and Date <= a.CheckOutDate and HoliType = '3') " 
				+"						when a.checkoutdate is null" 
				+"								and a.confirmbegin is not null" 
				+"								then datediff(day, a.confirmbegin, getdate()) - a.ConstructDay - dbo.fGetConstDay()" 
				+"  								- (select count(1) from tCalendar where Date >= a.ConfirmBegin and Date <= getdate() and HoliType = '3') "	
				+"						else null" 
				+"					end " 
				+"				else " 
				+"					case when a.checkoutdate is not null" 
				+"						then isnull(datediff(day, cw.ComeDate, a.checkoutdate), 0) - a.ConstructDay" 
				+"								- dbo.fGetConstDay() - (select count(1) from tCalendar where Date >= cw.ComeDate and Date <= a.CheckOutDate and HoliType = '3') " 
				+"						when a.checkoutdate is null" 
				+"						then datediff(day, cw.ComeDate, getdate()) - a.ConstructDay - dbo.fGetConstDay()" 
				+"  								- (select count(1) from tCalendar where Date >= cw.ComeDate and Date <= getdate() and HoliType = '3') "	
				+"						else null" 
				+"					end " 
				+"				end as delayDays " 
				+" from tCustomer a " 
				+" left join (	select a.CustCode,a.CrtDate LastSignDate,c.Descr LastSignWorkType12Descr from tWorkSign a " 
				+" 			 left join tCustWorker b on a.CustWkPk=b.PK " 
				+" 			 left join tWorkType12 c on b.WorkType12=c.Code " 
				+" 			 where a.CrtDate=(select max(CrtDate) from tWorkSign where CustCode=a.CustCode group by CustCode) ) b on b.CustCode=a.Code "
				+" left join tEmployee c on a.ProjectMan=c.Number " 
				+" left join (select in_a.CustCode,in_b.Descr LastConfirmPrjItemDescr,in_a.PrjItem,in_b.IsConfirm from tPrjProgConfirm in_a "
				+" 			left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code "
				+" 			where in_a.Date=(select max(Date) from tPrjProgConfirm where CustCode=in_a.CustCode  and IsPass='1' group by CustCode)) d on d.CustCode=a.Code "
				+" left join tCusttype e on a.CustType=e.Code "
				+" left join tXTDM xt1 on xt1.CBM=a.status and xt1.ID='CUSTOMERSTATUS' "
				+" left join tXTDM xt2 on xt2.CBM=a.ConstructStatus  and xt2.ID='CONSTRUCTSTATUS' "
				+" left join tDepartment2 f on c.Department2=f.Code "
				+" left join (select a.CustCode,min(a.ComeDate) ComeDate from tCustWorker a " 
				+"			left join tWorkType12 b on b.Code = a.WorkType12" 
				+"			where b.beginCheck = '1'" 
				+" 			group by a.CustCode) cw on cw.CustCode= a.Code" 
				+" where a.status='4' and a.ConfirmBegin is not null and d.IsConfirm='1' ";
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and c.Department2 in('" + customer.getDepartment2().replace(",", "', '") + "') ";
		}
		
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.ProjectMan = ? ";
			list.add(customer.getProjectMan());
		}
		
		if(StringUtils.isNotBlank(customer.getConstructStatus())){
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql+=" and a.ConstructStatus in (" + str + ")";
		}
		
		if(customer.getStopDays()!=0){
			sql+=" and datediff(day,b.LastSignDate,getdate())> ?";
			list.add(customer.getStopDays());
		}
		
		if(customer.getPrgRmkDateTo()!=null){
			sql+=" and a.PrgRmkDate< ? ";
			list.add(customer.getPrgRmkDateTo());
		}
		
		if(StringUtils.isNotBlank(customer.getPrjItem())){
			String str = SqlUtil.resetStatus(customer.getPrjItem());
			sql+=" and d.PrjItem in (" + str + ")";
		}
		
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.LastSignDate desc";
        }
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findWaitFirstCheckPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from ("
		        +" select d.Address,e.NameChi ProjectManDescr,f.NameChi WorkerName,b.Descr WorkType12Descr, a.EndDate,g.Desc1 CustTypeDescr, "
				+" d.Area,h.Desc1 Department2Descr,ws.WorkSignDate,d.Code "
				+" from tCustWorker a "
				+" left join tWorkType12 b on a.WorkType12=b.Code "
				+" left join tPrjItem1 c on b.PrjItem=c.Code "
				+" left join tCustomer d on a.CustCode=d.Code "
				+" left join tEmployee e on d.ProjectMan=e.Number "
				+" left join tWorker f on a.WorkerCode=f.Code "
				+" left join tCusttype g on d.CustType=g.Code "
				+" left join tDepartment2 h on e.Department2=h.Code "
				+" left join (select SignCZY,CustCode,in_b.WorkType12,max(CrtDate)PrjPassDate from tSignIn in_a left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code where IsPass='1'  "
				+" 			 group by SignCZY,CustCode,in_b.WorkType12 ) si on si.SignCZY=d.ProjectMan and si.CustCode=a.CustCode and a.WorkType12=si.WorkType12 "
				+" left join (  select max(a.CrtDate)WorkSignDate,a.CustWkPk from tWorkSign a left join tPrjItem2 b on a.PrjItem2=b.Code where  a.IsComplete='1' "
                +" group by a.CustWkPk,b.worktype12 having count(a.PK)=(select count(1) from tPrjItem2 where worktype12=b.worktype12) ) ws on ws.custWkPk=a.PK  "
				+" where ws.WorkSignDate is not null and a.EndDate is"
				+" null and si.PrjPassDate is null and c.IsFirstPass='1' and d.Status='4' and exists ( "
				+" 		select 1 from tPrjProg in_a where in_a.CustCode=a.CustCode and in_a.PrjItem=c.Code "
				+" ) ";
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and e.Department2 in('" + customer.getDepartment2().replace(",", "', '") + "') ";
		}
		
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and d.ProjectMan = ? ";
			list.add(customer.getProjectMan());
		}
		
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.WorkSignDate desc";
        }
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findWaitCustWorkAppPageBySql(
			Page<Map<String, Object>> page, Customer customer){
		List<Object> list = new ArrayList<Object>();
		String sql= "select * from ("
		        +" select a.Address,d.Desc1 CustTypeDescr,a.Area,e.NameChi ProjectManDescr,f.Desc1 Department2Descr,g.Descr WaitApplyWorkType12Descr, "
				+" b.Date BefWorkType12Date ,h.Descr BefWorkType12Descr,a.Code,a.PrgRemark,a.PrgRmkDate "
				+" from tCustomer a "
				+" left join (select in_d.WorkType12,in_b.Worktype12 BefWorktype12,in_a.CustCode,in_a.Date from tPrjProgConfirm in_a "
				+" 			left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code "
				+" 			left join tWorkType12 in_c on in_b.worktype12=in_c.Code "
				+" 			left join tBefWorkType12 in_d on in_c.code=in_d.BefWorktype12"
				+" 			where in_a.Date=(select max(Date) from tPrjProgConfirm where CustCode=in_a.CustCode group by CustCode) and in_a.IsPass='1'  and in_d.IsNext='1' ) b on a.Code=b.CustCode " 
				+"				and not exists(select 1 from tCustWorkerApp cwa where cwa.WorkType12 in(select WorkType12 from tBefWorkType12 where BefWorktype12=b.BefWorktype12) and cwa.CustCode=a.Code)" 
				+" left join tCustWorkerApp c on b.WorkType12=c.WorkType12 and a.Code=c.CustCode "
				+" left join tCusttype d on a.CustType=d.Code "
				+" left join tEmployee e on a.ProjectMan=e.Number "
				+" left join tDepartment2 f on e.Department2=f.Code "
				+" left join tWorkType12 g on b.WorkType12=g.Code "
				+" left join tWorkType12 h on b.BefWorkType12=h.Code "
				+" where b.WorkType12 is not null and c.WorkType12 is null and a.Status='4' ";
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and e.Department2 in('" + customer.getDepartment2().replace(",", "', '") + "') ";
		}
		
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.ProjectMan = ? ";
			list.add(customer.getProjectMan());
		}
		
		if(StringUtils.isNotBlank(customer.getConstructStatus())){
			String str = SqlUtil.resetStatus(customer.getConstructStatus());
			sql+=" and a.ConstructStatus in (" + str + ")";
		}
		
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.BefWorkType12Date desc";
        }
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findTimeOutEndPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from( "
				+" select a.pk,b.address,d.Desc1 CustTypeDescr,c.NameChi ProjectManDescr,i.Desc1 Department2Descr,f.Descr WorkType12Descr, "
				+" e.NameChi WorkerName,a.ComeDate,a.PlanEnd,b.Area,b.Code,b.Status CustomerStatus, "
				+" case when a.EndDate is null then datediff(day,a.ComeDate,getdate())+1-a.ConstructDay else datediff(day,a.ComeDate,a.EndDate)+1-a.ConstructDay end TimeOutDays, "
				+" si.PrjPassDate,a.EndDate,g.EarliestSignDate,h.LastSignDate,xt1.NOTE StatusDescr,a.Status,j.Remark,c.Department2,b.ProjectMan,a.WorkType12,ws.WorkSignDate "
				+" from tCustWorker a  "
				+" left join tCustomer b on a.CustCode=b.Code "
				+" left join tEmployee c on b.ProjectMan=c.Number "
				+" left join tCusttype d on b.CustType=d.Code "
				+" left join tWorker e on a.WorkerCode=e.Code "
				+" left join tWorkType12 f on a.WorkType12=f.Code "
				+" left join (select min(CrtDate) EarliestSignDate,CustWkPk from tWorkSign group by CustWkPk) g on g.CustWkPk=a.PK "
				+" left join (select max(CrtDate) LastSignDate,CustWkPk from tWorkSign  group by CustWkPk) h on h.CustWkPk=a.PK "
				+" left join tDepartment2 i on c.Department2=i.Code "
				+" left join (select a.CustWkPk,a.Remark from tWorkerProblem a where a.Date=(select max(Date) lastDate from tWorkerProblem where a.CustWkPk=CustWkPk group by CustWkPk)) j on j.CustWkPk=a.PK "
				+" left join (select SignCZY,CustCode,in_b.WorkType12,max(CrtDate)PrjPassDate from tSignIn in_a left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code where IsPass='1'  "
				+" 		 group by SignCZY,CustCode,in_b.WorkType12 ) si on si.SignCZY=b.ProjectMan and si.CustCode=a.CustCode and a.WorkType12=si.WorkType12 "
				+" left join tXTDM xt1 on a.Status = xt1.CBM and xt1.ID='CUSTWKSTATUS' "
				+" left join (  select max(a.CrtDate)WorkSignDate,a.CustWkPk from tWorkSign a left join tPrjItem2 b on a.PrjItem2=b.Code where  a.IsComplete='1' "
                +" group by a.CustWkPk,b.worktype12 having count(a.PK)=(select count(1) from tPrjItem2 where worktype12=b.worktype12) ) ws on ws.custWkPk=a.PK  "
				+" )a where 1=1 and a.EndDate is null and a.CustomerStatus='4' ";
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and a.Department2 in('" + customer.getDepartment2().replace(",", "', '") + "') ";
		}
		
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.ProjectMan = ? ";
			list.add(customer.getProjectMan());
		}
		
		if(StringUtils.isNotBlank(customer.getCustWorkerCustStatus())){
			String str = SqlUtil.resetStatus(customer.getCustWorkerCustStatus());
			sql+=" and a.Status in (" + str + ")";
		}
		
		if(customer.getTimeOutDays()!=0){
			sql+=" and a.TimeOutDays > ?";
			list.add(customer.getTimeOutDays());
		}
		
		if(StringUtils.isNotBlank(customer.getWorkType12())){
			String str = SqlUtil.resetStatus(customer.getWorkType12());
			sql+=" and a.WorkType12 in (" + str + ")";
		}
		
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += " order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += " order by a.PlanEnd desc";
        }
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String,Object>> getJobOrderList(Page<Map<String,Object>> page, String custCode, String prjItem,String workType12) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select c.Descr PrjItemDescr,a.DayType,t1.NOTE dayTypeDescr,a.AddDay,a.MsgTextTemp,d.RcvDate, "
				+" case when a.RemindCZYType='0' then g.Descr else e.NameChi end NameChi,d.SendDate, "
				+" case when d.RcvDate is not null then '1' when d.SendDate is null or d.SendDate > getdate() then '3' else '2' end timeoutFlag, "
				+" case when a.AddDay>=0 then "
				+" case t1.cbm when 1 then '开始' when 2 then '结束' when 3 then '验收' when 4 then '计划结束' end +'+'+convert(varchar,abs(a.AddDay))+'天' "
				+" else case t1.cbm when 1 then '开始' when 2 then '结束' when 3 then '验收' when 4 then '计划结束' end +'-'+convert(varchar,abs(a.AddDay))+'天' end AlarmDate "  
				+" from tProgTempAlarm a "
				+" inner join tProgTempDt b on a.PrjItem=b.PrjItem and b.TempNo = (select in_a.PrjProgTempNo from tCustomer in_a where in_a.Code= ? ) "
				+" left join tPrjItem1 c on b.PrjItem = c.Code "
				+" left join tPersonMessage d on a.PK = d.MsgRelNo and d.MsgRelCustCode = ? and d.MsgType='1' "
				+" left join tEmployee e on a.CZYBH = e.Number  left join tRoll g on a.Role = g.Code "
				+" left join tXTDM t1 on a.DayType = t1.IBM and t1.ID='ALARMDAYTYPE' "
				+" where 1=1 and a.TempNo=(select in_a.PrjProgTempNo from tCustomer in_a where in_a.Code= ? ) " ;
		list.add(custCode);
		list.add(custCode);
		list.add(custCode);
		if(StringUtils.isNotBlank(prjItem)){
			sql += " and c.Code = ? ";
			list.add(prjItem);
		}
		if(StringUtils.isNotBlank(workType12)){
			sql += " and c.workType12 = ? ";
			list.add(workType12);
		}
		sql += " order by  b.DispSeq,a.DayType,a.AddDay,a.PK";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getAlarmPrjItemList(Page<Map<String,Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select b.Code,b.Descr from tPrjProg a "
				+" left join tPrjItem1 b on a.PrjItem = b.Code "
				+" where a.CustCode = ? "
				+" order by b.Seq ";
		list.add(custCode);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getAlarmWorkType12List(Page<Map<String,Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select c.Code,c.Descr from tPrjProg a "
				+" left join tPrjItem1 b on a.PrjItem = b.Code "
				+" inner join tWorkType12 c on b.worktype12 = c.Code "
				+" where a.CustCode = ? group by c.Code,c.Descr,c.DispSeq "
				+" order by c.DispSeq " ;
		list.add(custCode);
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 获取工程进度模板明细
	 * @author	created by zb
	 * @date	2020-4-6--上午9:22:28
	 * @param progTempDt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProgTempDt getProgTemDt(ProgTempDt progTempDt) {
		String sql = "from ProgTempDt where tempNo=? and prjItem=?";
		List<ProgTempDt> list = this.find(sql, new Object[]{progTempDt.getTempNo(), progTempDt.getPrjItem()});
		if (null != list && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
