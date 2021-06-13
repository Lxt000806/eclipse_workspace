package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.ItemType12;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.BuilderRep;

@SuppressWarnings("serial")
@Repository
public class BuilderRepDao extends BaseDao {

	/**
	 * 明日施工计划查看
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderRep builderrep) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.pk,a.CustCode,s2.note nowspeeddescr,c.NameChi ProjectManDescr,c.phone Projectphone,b.address,d.Desc1 Department2," +
				"(case when a.Type is NULL then '' ELSE a.Type end) Type,x2.NOTE TypeDescr," +
				"a.BuildStatus,(case when x1.Note is NULL then '' ELSE x1.Note end) BuildStatusDescr," +
				"a.BeginDate,a.EndDate,a.LastUpdate,a.LastUpdatedBy,a.Expired," +
				"a.ActionLog,isnull(cast(dbo.fGetDept2Descr(b.Code, '11') as nvarchar(100)),'') IntDept2Descr," +
				"isnull(cast(dbo.fGetEmpNameChi(b.Code, '11') as nvarchar(1000)),'') IntDesignDescr," +
				"isnull(cast(dbo.fGetEmpNameChi(b.Code, '34') as nvarchar(1000)),'') ZCHousekeeper,a.DealRemark,a.Remark from dbo.tBuilderRep a " +
				"left join dbo.tCustomer b on a.CustCode = b.Code " +
				"left join tEmployee c on b.ProjectMan = c.Number " +
				"left join tDepartment2 d on c.Department2 = d.code " +
				"left join dbo.tXTDM x1 on a.BuildStatus=x1.CBM and x1.ID='buildstatus' " +
				"left join dbo.tXTDM x2 on a.Type = x2.CBM and x2.id='BLDREPTYPE'  " +
				"left join (select   max(a.PrjItem) prjitem, a.CustCode " +
				"		from     tPrjProg a " +
				"		right join (select  max(BeginDate) begindate, CustCode " +
				"					from    tPrjProg " + 
				"					group by CustCode " +
				"				   ) b on a.CustCode = b.CustCode " +
				"						  and a.BeginDate = b.begindate " +
				"						  and a.CustCode = b.CustCode " +
				"		group by a.CustCode " +
				"		) sj on b.Code = sj.CustCode " +               
				"		left join tXTDM s2 on s2.cbm = sj.prjitem and s2.ID = 'PRJITEM' " +
				"where 1=1  and a.expired='F'";							
		
    	if (StringUtils.isNotBlank(builderrep.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+builderrep.getAddress()+"%");
		}    
    	if (StringUtils.isNotBlank(builderrep.getProjectManDescr())) {
			sql += " and c.number= ? ";
			list.add(builderrep.getProjectManDescr());
		} 
    	
    	if (StringUtils.isNotBlank(builderrep.getDepartment2())) {
			sql += " and d.code= ? ";
			list.add(builderrep.getDepartment2());
		}
    	
    	if (StringUtils.isNotBlank(builderrep.getType())) {
			sql += " and a.type in " + "('"+builderrep.getType().replace(",", "','" )+ "')";
		}
    	if (StringUtils.isNotBlank(builderrep.getBuildStatus())) {
			sql += " and a.buildStatus in " + "('"+builderrep.getBuildStatus().replace(",", "','" )+ "')";
		}
    	if (builderrep.getBeginDate() != null) {
			sql += " and a.LastUpdate>= ? ";
			list.add(builderrep.getBeginDate());
		}
		if (builderrep.getEndDate() != null) {
			sql += " and a.LastUpdate<= ? ";
			list.add(builderrep.getEndDate());
		}  
		if (StringUtils.isNotBlank(builderrep.getDepartment2jc())) {
			sql += " and exists ( select 1 from   tCustStakeholder inner join dbo.tEmployee e on EmpCode = e.Number " +
					"where  Role = '11' and CustCode = b.Code and e.Department2 = ? ) ";
			list.add(builderrep.getDepartment2jc());
		}
		if (StringUtils.isNotBlank(builderrep.getZcHousekeeper())) {
			sql += " and exists ( select 1 from   tCustStakeholder inner join tEmployee e on EmpCode = e.Number " +
					"where  Role = '34' and CustCode = b.Code and e.Number = ? ) ";
			list.add(builderrep.getZcHousekeeper());
		}
		if (("".equals(builderrep.getExpired())) || ("F".equals(builderrep.getExpired()))) {
			sql += " and (a.begindate>=Convert(varchar(10),Getdate(),111) or a.enddate>=Convert(varchar(10),Getdate(),111))";			
		}
		
		sql += " order by a.LastUpdate desc";
		

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 工地停工排行查看
	 * 
	 * @param page
	 * @param builderRep
	 * @return
	 *//*
	public Page<Map<String,Object>> findPageBySqlgdtgph(Page<Map<String,Object>> page, BuilderRep builderrep) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select  a.CustCode,a.ProjectManDescr,a.Address,a.Department2,a.Department2Descr,sum(stopday) downday "
				+ "   from   (select BuildStatus ,c.NameChi ProjectManDescr, b.address,c.Department2, d.Desc1 Department2Descr,a.CustCode, a.BeginDate, a.EndDate, "
				+ "   datediff(day, "
				+ "   case when a.BeginDate < '"+builderrep.getBeginDate()+"' "
				+ "   then '"+builderrep.getBeginDate()+"' "
				+ "   else a.BeginDate "
				+ "   end, "
				+ "   case when a.EndDate >= '"+builderrep.getEndDate()+"' "
				+ "   then '"+builderrep.getEndDate()+"' "
				+ "   else a.enddate "
				+ "   end) + 1 stopday "
				+ "   from   dbo.tBuilderRep a " 
				+ "   left join dbo.tCustomer b on a.CustCode = b.Code "
				+ "   left join tEmployee c on b.ProjectMan = c.Number "
				+ "   left join tDepartment2 d on c.Department2 = d.code "
				+ "   where  type = '1' and a.BuildStatus not in ('1','2') ";
				if (StringUtils.isNotBlank(builderrep.getDepartment2())) {
					sql += " and c.Department2= ? ";
					list.add(builderrep.getDepartment2());
				};
				sql+= "   and ((a.BeginDate >=  '"+builderrep.getBeginDate()+"' "
                + "   and a.EndDate < dateadd(day,1,Convert(varchar(10),'"+builderrep.getEndDate()+"',111)) "
                + "   ))) a "
				+ "   group by a.CustCode,a.ProjectManDescr,a.Address,a.Department2,a.Department2Descr "
				+ "   ";								    
		
	//	sql += " order by CustCode desc ";
		

		return this.findPageBySql(page, sql, list.toArray());
	}*/
	/**
	 * 报备情况分析
	 * 
	 * @param page
	 * @param builderRep
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_gdbb_brbb(Page<Map<String,Object>> page, BuilderRep builderRep) {
		Assert.notNull(builderRep);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGdbb_fx(?,?,?,?,?,?)}");//gdbb_brbb 工地报备_本日报备
			call.setString(1, builderRep.getType());
			call.setString(2, builderRep.getDepartment2());
			call.setTimestamp(3, new java.sql.Timestamp(builderRep.getBeginDate().getTime()));
			call.setTimestamp(4, new java.sql.Timestamp(builderRep.getEndDate().getTime()));
			call.setString(5, builderRep.getTgyy());
			call.setString(6, builderRep.getBrtg());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
			
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	

	/**  工地报备分析
	 * 工地停工排行的查看页面表格
	 * 
	 * @param page
	 * @param builderRep
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqltgphView(Page<Map<String,Object>> page, BuilderRep builderrep) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select b.address,a.begindate,a.endDate,a.BuildStatus,x1.Note BuildStatusDescr,a.Remark from tbuilderrep a "
					+ " left join tcustomer b on a.custcode=b.code "
				    + " left join tXTDM x1 on a.BuildStatus=x1.CBM and x1.id='BUILDSTATUS' "
					+ " where a.BuildStatus not in ('1','2') and a.custcode= '"+builderrep.getCustCode().trim()+"' ";
					if (builderrep.getBeginDate() != null) {
						sql += " and a.begindate>= ? ";
						list.add(builderrep.getBeginDate());
					}
					if (builderrep.getEndDate() != null) {
						sql += " and a.enddate<dateadd(dd,1,?) ";
						list.add(builderrep.getEndDate());
					}
					if (StringUtils.isNotBlank(builderrep.getTgyy())) {
						sql += " and a.buildStatus in " + "('"+builderrep.getTgyy().replace(",", "','" )+ "')";
					}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	@SuppressWarnings("unchecked") 
	public ItemType12 getByDescr(String descr) {
		String hql = "from ItemType12 a where a.expired='F' and   a.descr=? ";
		List<ItemType12> list = this.find(hql, new Object[]{descr});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public ItemType12 getByDescr1(String descr,String descr1) {
		String hql = "from ItemType12 a where a.expired='F' and   a.descr=?  and a.descr!=?";
		List<ItemType12> list = this.find(hql, new Object[]{descr,descr1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	@SuppressWarnings("deprecation")
	public Result doitemsetReturnCheckOut(BuilderRep builderRep)  {
		Assert.notNull(builderRep);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBuilderRep(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, builderRep.getM_umState());
			call.setLong(2, builderRep.getPk());			
			call.setTimestamp(3, new java.sql.Timestamp(builderRep.getBeginDate().getTime()));
			call.setTimestamp(4, new java.sql.Timestamp(builderRep.getEndDate().getTime()));
			call.setString(5, builderRep.getLastUpdatedBy());
			call.setTimestamp(6, new java.sql.Timestamp(builderRep.getLastUpdate().getTime()));
			call.setString(7, builderRep.getActionLog());
			call.setString(8, builderRep.getExpired());
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

