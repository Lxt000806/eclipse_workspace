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
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.ConFeeChg;

@SuppressWarnings("serial")
@Repository
public class ConFeeChgDao extends BaseDao {

	/**
	 * ConFeeChg分页信息
	 * 
	 * @param page
	 * @param conFeeChg
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ConFeeChg conFeeChg, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.Code,a.Descr,a.DocumentNO,a.Address,a.Area,b.PK,b.ChgType,d.Note ChgTypeDescr,b.ChgAmount,b.Status, "
				+ "d1.Note StatusDescr,b.ConfirmCZY,c.NameChi ConfirmCZYDescr,b.ConfirmDate,d3.note custstatusdescr, "
				+ "b.AppCZY,c1.NameChi AppCZYDescr,b.Date,b.Remarks,b.LastUpdatedBy,c2.NameChi LastUpdatedByDescr,b.LastUpdate,b.Expired, "
				+ "b.ActionLog,b.ChgNo,b.ItemType1,b.IsService,b.IsCupboard,b.PerfPK,b.IscalPerf,d2.NOTE IscalPerfDescr,a.status custstatus "
				+ "from tConFeeChg b "
				+ "left outer join tCustomer a on b.CustCode=a.Code "
				+ "left outer join tEmployee c on b.ConfirmCZY=c.Number "
				+ "left outer join tEmployee c1 on b.AppCZY=c1.Number "
				+ "left outer join tEmployee c2 on b.LastUpdatedBy=c2.Number "
				+ "left outer join tXTDM d on b.ChgType=d.CBM and d.ID='CHGTYPE' "
				+ "left outer join tXTDM d1 on b.Status=d1.CBM and d1.ID='CHGSTATUS' "
				+ "left outer join tXTDM d2 on b.IscalPerf=d2.CBM and d2.ID='YESNO' "
				+ "left outer join tXTDM d3 on a.status=d3.CBM and d3.ID='customerstatus' "
				+ "where 1=1 and (a.LastUpdatedby="+ uc.getCzybh()+ " or "+ SqlUtil.getCustRight(uc, "b", 0) + " ) ";
		if (StringUtils.isNotBlank(conFeeChg.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+conFeeChg.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(conFeeChg.getCustCode())) {
			sql += " and b.CustCode=? ";
			list.add(conFeeChg.getCustCode());
		}
		if (StringUtils.isNotBlank(conFeeChg.getChgType())) {
			sql += " and b.ChgType=? ";
			list.add(conFeeChg.getChgType());
		}
		if (StringUtils.isNotBlank(conFeeChg.getStatus())) {
			sql += " and b.status in ('"
					+ conFeeChg.getStatus().trim().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isBlank(conFeeChg.getExpired())
				|| "F".equals(conFeeChg.getExpired())) {
			sql += " and b.Expired='F' ";
		}
		if (StringUtils.isNotBlank(conFeeChg.getItemType1())) {
			sql += " and b.ItemType1=? ";
			list.add(conFeeChg.getItemType1());
		}
		if (StringUtils.isNotBlank(conFeeChg.getIsService())) {
			sql += " and b.IsService=? ";
			list.add(conFeeChg.getIsService());
		}
		if (StringUtils.isNotBlank(conFeeChg.getIsCupboard())) {
			sql += " and b.IsCupboard=? ";
			list.add(conFeeChg.getIsCupboard());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, String custCode) {
		String sql = "select * from (select a.Code,a.Descr,a.DocumentNO,a.Address,a.Area,b.PK,b.ChgType,d.Note ChgTypeDescr,b.ChgAmount,"
				+ "b.Status,d1.Note StatusDescr,b.ConfirmCZY,c.NameChi ConfirmCZYDescr,b.ConfirmDate,"
				+ "b.AppCZY,c1.NameChi AppCZYDescr,b.Date,b.Remarks,b.LastUpdatedBy,c2.NameChi LastUpdatedByDescr,"
				+ "b.LastUpdate,b.Expired,b.ActionLog "
				+ " from tConFeeChg b "
				+ " left outer join tCustomer a on b.CustCode=a.Code "
				+ " left outer join tEmployee c on b.ConfirmCZY=c.Number "
				+ " left outer join tEmployee c1 on b.AppCZY=c1.Number "
				+ " left outer join tEmployee c2 on b.LastUpdatedBy=c2.Number "
				+ " left outer join tXTDM d on b.ChgType=d.CBM and d.ID='CHGTYPE' "
				+ " left outer join tXTDM d1 on b.Status=d1.CBM and d1.ID='CHGSTATUS' "
				+ " where b.CustCode=? and b.Expired='F' ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.date desc";
		}
		return this.findPageBySql(page, sql, new Object[] { custCode });
	}
	/**
	 * 检查是否能够计算业绩
	 * @param conFeeChg
	 * @return
	 */
	public List<Map<String,Object>> checkPerformance(ConFeeChg conFeeChg){
		List<Object> list = new ArrayList<Object>();
		String sql = "select Status,PerfPK,IsCalPerf,ChgType from tConFeeChg where pk=?";
		list.add(conFeeChg.getPk());
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 检查是否能保存计算业绩
	 * @param conFeeChg
	 * @return
	 */
	public List<Map<String,Object>> checkSavePerformance(ConFeeChg conFeeChg){
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tConFeeChg where PK=? and Status='CONFIRMED' and PerfPK is null";
		list.add(conFeeChg.getPk());
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 保存计算业绩
	 * @param conFeeChg
	 * @return
	 */
	public void doPerformance(ConFeeChg conFeeChg){
		List<Object> list = new ArrayList<Object>();
		String sql = "update tConFeeChg set IsCalPerf=?, LastUpdatedBy=?, "
                    +"LastUpdate=getdate(), ActionLog='EDIT' "
                    +"where PK=? and Status='CONFIRMED' and PerfPK is null";
		list.add(conFeeChg.getIscalPerf());
		list.add(conFeeChg.getLastUpdatedBy());
		list.add(conFeeChg.getPk());
		this.executeUpdateBySql(sql, list.toArray());
	}
	/**
	 * 保存，审核，审核取消，反审核
	 * 
	 * @param conFeeChgDetail
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(ConFeeChg conFeeChg) {
		Assert.notNull(conFeeChg);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pHtfygl_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, conFeeChg.getM_umState());
			call.setInt(2, conFeeChg.getPk()==null?0:conFeeChg.getPk());	
			call.setString(3, conFeeChg.getCustCode());
			call.setString(4, conFeeChg.getChgType());
			call.setDouble(5, conFeeChg.getChgAmount());
			call.setString(6, conFeeChg.getStatus());
			call.setString(7, conFeeChg.getRemarks());
			call.setString(8, conFeeChg.getAppCzy());
			call.setString(9, conFeeChg.getConfirmCzy());
			call.setString(10, conFeeChg.getLastUpdatedBy());
			call.setString(11, conFeeChg.getItemType1());
			call.setString(12, conFeeChg.getIsService());
			call.setString(13, conFeeChg.getIsCupboard());
			call.setString(14, conFeeChg.getChgNo());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
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
}
