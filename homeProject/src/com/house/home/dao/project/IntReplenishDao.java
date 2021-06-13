package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.WorkerArrange;

@SuppressWarnings("serial")
@Repository
public class IntReplenishDao extends BaseDao {

	/**
	 * 主表查询
	 * @author	created by zb
	 * @date	2019-11-22--下午4:34:34
	 * @param page
	 * @param intReplenish
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntReplenish intReplenish) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.IsCupboard,tx.NOTE IsCupboardDescr,b.Address, "
					+"a.Status,tx2.NOTE StatusDescr,a.Source,tx3.NOTE SourceDescr, "
					+"a.Remarks,b.ProjectMan,c.NameChi ProjectManDescr,c.Phone, "
					+"a.BoardOKDate,a.BoardOKRemarks,a.ReadyDate,a.ServiceDate, "
					+"a.ServiceMan,c3.NameChi ServiceManDescr,a.ResPart,tx4.NOTE ResPartDescr,a.FinishDate, "
					+"a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,c4.NameChi AppCZYDescr,a.Date, "
					+"c5.NameChi FixDutyManDescr,a.FixDutyDate,e.Descr RegionDescr "
					+"from tIntReplenish a "
					+"left join tXTDM tx on tx.ID='YESNO' and tx.CBM=a.IsCupboard "
					+"left join tCustomer b on b.Code=a.CustCode "
					+"left join tXTDM tx2 on tx2.ID='IntRepStatus' and tx2.CBM=a.Status "
					+"left join txtdm tx3 on tx3.ID='IntRepSource' and tx3.CBM=a.Source "
					+"left join txtdm tx4 on tx4.ID='IntRepResPart' and tx4.CBM=a.ResPart "
					+"left join tEmployee c on c.Number=b.ProjectMan "
					+"left join tEmployee c3 on c3.Number=a.ServiceMan "
					+"left join tEmployee c4 on a.AppCZY=c4.Number "
					+"left join tEmployee c5 on a.FixDutyMan=c5.Number "
					+"left join tBuilder d on b.BuilderCode=d.Code "
					+"left join tRegion e on d.RegionCode=e.Code "
					+"where 1=1 ";
		if (StringUtils.isBlank(intReplenish.getExpired()) || "F".equals(intReplenish.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(intReplenish.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+intReplenish.getAddress().trim()+"%");
		}
		if (StringUtils.isNotBlank(intReplenish.getIsCupboard())) {
			sql += " and a.IsCupboard = ? ";
			list.add(intReplenish.getIsCupboard());
		}
		if (null != intReplenish.getBoardOKDateFrom()) {
			sql += " and a.BoardOKDate >= ? ";
			list.add(intReplenish.getBoardOKDateFrom());
		}
		if (null != intReplenish.getBoardOKDateTo()) {
			sql += " and a.BoardOKDate <= ? ";
			list.add(intReplenish.getBoardOKDateTo());
		}
		if (null != intReplenish.getReadyDateFrom()) {
			sql += " and a.ReadyDate >= ? ";
			list.add(intReplenish.getReadyDateFrom());
		}
		if (null != intReplenish.getReadyDateTo()) {
			sql += " and a.ReadyDate <= ? ";
			list.add(intReplenish.getReadyDateTo());
		}
		if (null != intReplenish.getFinishDateFrom()) {
			sql += " and a.FinishDate >= ? ";
			list.add(intReplenish.getFinishDateFrom());
		}
		if (null != intReplenish.getFinishDateTo()) {
			sql += " and a.FinishDate <= ? ";
			list.add(intReplenish.getFinishDateTo());
		}
		if (StringUtils.isNotBlank(intReplenish.getStatus())) {
			sql += " and a.Status in ('"+intReplenish.getStatus().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(intReplenish.getRegion())) {
			sql += " and d.RegionCode in ('"+intReplenish.getRegion().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(intReplenish.getIntDepartment2())) {
			sql += " and exists ( "
				+"select 1 from tCustStakeholder in_a "
				+"left join tEmployee in_b on in_b.Number = in_a.EmpCode "
				+"where in_a.CustCode=a.CustCode and in_a.Role in ('11', '61')  "//只要集成、橱柜设计师有一个符合就行
				+"and in_b.Department2 in ('"+intReplenish.getIntDepartment2().replace(",", "','")+"')) ";
		}
		if ("1".equals(intReplenish.getIsFixDuty())){
			sql += " and a.FixDutyDate is not null and a.FixDutyDate <> '' ";
		}else if("0".equals(intReplenish.getIsFixDuty())){
			sql += " and a.FixDutyDate is null or a.FixDutyDate = '' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 补货明细录入保存
	 * @author	created by zb
	 * @date	2019-11-20--下午3:03:25
	 * @param intReplenish
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(IntReplenish intReplenish) {
		Assert.notNull(intReplenish);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pIntReplenish(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, intReplenish.getM_umState());
			call.setString(2, intReplenish.getNo());
			call.setString(3, intReplenish.getIsCupboard());
			call.setString(4, intReplenish.getSource());
			call.setString(5, intReplenish.getResPart());
			call.setString(6, intReplenish.getRemarks());
			call.setString(7, intReplenish.getDetailJson());
			call.setString(8, intReplenish.getLastUpdatedBy());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.setString(11, intReplenish.getStatus());
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
	/**
	 * 保存时，如果该补货单的所有补货明细都到货了，则更新补货主表的货齐时间、状态（已到货）。
	 * @author	created by zb
	 * @date	2019-11-21--下午2:49:08
	 * @param pk
	 */
	public void updateReadyDate(Integer pk) {
		try {
			String sql = " update tIntReplenish set Status='3',ReadyDate=b.ArriveDate "
						+"from tIntReplenish a "
						+"inner join tIntReplenishDT b on b.No=a.No "
						+"where b.PK=? and not exists( "
						+"	select 1 from tIntReplenishDT in_a where in_a.No=a.No and in_a.ArriveDate is null "
						+") ";
			 this.executeUpdateBySql(sql, new Object[]{pk});
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void updateArrivedIntReplenish(IntReplenish intReplenish) {  
	    try {
			String sql = "update tIntReplenish "
					+" set Status='4', ActionLog = 'Edit', ServiceDate = ?, ServiceManType = ?, ServiceMan = ?,LastUpDate = ?, LastUpdatedBy = ? "
					+" where status = '3' and CustCode =? and no <> ?  ";
			 this.executeUpdateBySql(sql, new Object[]{
				 intReplenish.getServiceDate(),intReplenish.getServiceManType(),
				 intReplenish.getServiceMan(),intReplenish.getLastUpdate(),
				 intReplenish.getLastUpdatedBy(),intReplenish.getCustCode(),intReplenish.getNo()
			 });
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("deprecation")
	public Result doSign(IntReplenish intReplenish) {
		Assert.notNull(intReplenish);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pIntReplenish_sign_forXml(?,?,?,?,?,?)}");
			call.setString(1, intReplenish.getNo());
			call.setString(2, intReplenish.getLastUpdatedBy());
			call.setString(3, intReplenish.getBoardOKRemarks());
			call.setString(4, intReplenish.getDetailJson());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			System.out.println(intReplenish.getDetailJson());
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

}
