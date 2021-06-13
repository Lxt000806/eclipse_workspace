package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.CustProfit;

@SuppressWarnings("serial")
@Repository
public class CustProfitDao extends BaseDao {

	/**
	 * CustProfit分页信息
	 * 
	 * @param page
	 * @param custProfit
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustProfit custProfit) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCustProfit a where 1=1 ";

    	if (StringUtils.isNotBlank(custProfit.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custProfit.getCustCode());
		}
    	if (custProfit.getBaseDiscPer() != null) {
			sql += " and a.BaseDiscPer=? ";
			list.add(custProfit.getBaseDiscPer());
		}
    	if (custProfit.getBaseDisc1() != null) {
			sql += " and a.BaseDisc1=? ";
			list.add(custProfit.getBaseDisc1());
		}
    	if (custProfit.getBaseDisc2() != null) {
			sql += " and a.BaseDisc2=? ";
			list.add(custProfit.getBaseDisc2());
		}
    	if (custProfit.getDesignFee() != null) {
			sql += " and a.DesignFee=? ";
			list.add(custProfit.getDesignFee());
		}
    	if (custProfit.getGift() != null) {
			sql += " and a.Gift=? ";
			list.add(custProfit.getGift());
		}
    	if (custProfit.getContainBase() != null) {
			sql += " and a.ContainBase=? ";
			list.add(custProfit.getContainBase());
		}
    	if (custProfit.getContainMain() != null) {
			sql += " and a.ContainMain=? ";
			list.add(custProfit.getContainMain());
		}
    	if (custProfit.getContainSoft() != null) {
			sql += " and a.ContainSoft=? ";
			list.add(custProfit.getContainSoft());
		}
    	if (custProfit.getContainInt() != null) {
			sql += " and a.ContainInt=? ";
			list.add(custProfit.getContainInt());
		}
    	if (custProfit.getContainCup() != null) {
			sql += " and a.ContainCup=? ";
			list.add(custProfit.getContainCup());
		}
    	if (custProfit.getContainMainServ() != null) {
			sql += " and a.ContainMainServ=? ";
			list.add(custProfit.getContainMainServ());
		}
    	if (custProfit.getColorDrawFee() != null) {
			sql += " and a.ColorDrawFee=? ";
			list.add(custProfit.getColorDrawFee());
		}
    	if (custProfit.getRemoteFee() != null) {
			sql += " and a.RemoteFee=? ";
			list.add(custProfit.getRemoteFee());
		}
    	if (custProfit.getBaseDisc() != null) {
			sql += " and a.BaseDisc=? ";
			list.add(custProfit.getBaseDisc());
		}
    	if (custProfit.getMainCost() != null) {
			sql += " and a.MainCost=? ";
			list.add(custProfit.getMainCost());
		}
    	if (custProfit.getJobPer() != null) {
			sql += " and a.JobPer=? ";
			list.add(custProfit.getJobPer());
		}
    	if (custProfit.getBasePro() != null) {
			sql += " and a.BasePro=? ";
			list.add(custProfit.getBasePro());
		}
    	if (custProfit.getMainPro() != null) {
			sql += " and a.MainPro=? ";
			list.add(custProfit.getMainPro());
		}
    	if (custProfit.getServPro() != null) {
			sql += " and a.ServPro=? ";
			list.add(custProfit.getServPro());
		}
    	if (custProfit.getIntPro() != null) {
			sql += " and a.IntPro=? ";
			list.add(custProfit.getIntPro());
		}
    	if (custProfit.getCupPro() != null) {
			sql += " and a.CupPro=? ";
			list.add(custProfit.getCupPro());
		}
    	if (custProfit.getSoftPro() != null) {
			sql += " and a.SoftPro=? ";
			list.add(custProfit.getSoftPro());
		}
    	if (custProfit.getManagePro() != null) {
			sql += " and a.ManagePro=? ";
			list.add(custProfit.getManagePro());
		}
    	if (custProfit.getDesignPro() != null) {
			sql += " and a.DesignPro=? ";
			list.add(custProfit.getDesignPro());
		}
    	if (custProfit.getAllPro() != null) {
			sql += " and a.AllPro=? ";
			list.add(custProfit.getAllPro());
		}
    	if (custProfit.getDesignCalPer() != null) {
			sql += " and a.DesignCalPer=? ";
			list.add(custProfit.getDesignCalPer());
		}
    	if (custProfit.getCostPer() != null) {
			sql += " and a.CostPer=? ";
			list.add(custProfit.getCostPer());
		}
    	if (custProfit.getBaseCalPer() != null) {
			sql += " and a.BaseCalPer=? ";
			list.add(custProfit.getBaseCalPer());
		}
    	if (custProfit.getMainCalPer() != null) {
			sql += " and a.MainCalPer=? ";
			list.add(custProfit.getMainCalPer());
		}
    	if (custProfit.getServProPer() != null) {
			sql += " and a.ServProPer=? ";
			list.add(custProfit.getServProPer());
		}
    	if (custProfit.getServCalPer() != null) {
			sql += " and a.ServCalPer=? ";
			list.add(custProfit.getServCalPer());
		}
    	if (custProfit.getJobCtrl() != null) {
			sql += " and a.JobCtrl=? ";
			list.add(custProfit.getJobCtrl());
		}
    	if (custProfit.getJobLowPer() != null) {
			sql += " and a.JobLowPer=? ";
			list.add(custProfit.getJobLowPer());
		}
    	if (custProfit.getJobHighPer() != null) {
			sql += " and a.JobHighPer=? ";
			list.add(custProfit.getJobHighPer());
		}
    	if (custProfit.getIntProPer() != null) {
			sql += " and a.IntProPer=? ";
			list.add(custProfit.getIntProPer());
		}
    	if (custProfit.getIntCalPer() != null) {
			sql += " and a.IntCalPer=? ";
			list.add(custProfit.getIntCalPer());
		}
    	if (custProfit.getCupProPer() != null) {
			sql += " and a.CupProPer=? ";
			list.add(custProfit.getCupProPer());
		}
    	if (custProfit.getCupCalPer() != null) {
			sql += " and a.CupCalPer=? ";
			list.add(custProfit.getCupCalPer());
		}
    	if (custProfit.getSoftProPer() != null) {
			sql += " and a.SoftProPer=? ";
			list.add(custProfit.getSoftProPer());
		}
    	if (custProfit.getSoftCalPer() != null) {
			sql += " and a.SoftCalPer=? ";
			list.add(custProfit.getSoftCalPer());
		}
    	if (custProfit.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custProfit.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custProfit.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custProfit.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custProfit.getExpired()) || "F".equals(custProfit.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custProfit.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custProfit.getActionLog());
		}
    	if (custProfit.getPrepay() != null) {
			sql += " and a.Prepay=? ";
			list.add(custProfit.getPrepay());
		}
    	if (StringUtils.isNotBlank(custProfit.getPayType())) {
			sql += " and a.PayType=? ";
			list.add(custProfit.getPayType());
		}
    	if (StringUtils.isNotBlank(custProfit.getPosition())) {
			sql += " and a.position=? ";
			list.add(custProfit.getPosition());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.CustCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Result doCustProfitForProc(CustProfit custProfit) {
		Assert.notNull(custProfit);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustProfit(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1,custProfit.getCustCode());
			call.setDouble(2, custProfit.getBaseDiscPer()==null?0:custProfit.getBaseDiscPer());
			call.setDouble(3, custProfit.getBaseDisc1()==null?0:custProfit.getBaseDisc1());
			call.setDouble(4, custProfit.getBaseDisc2()==null?0:custProfit.getBaseDisc2());
			call.setDouble(5, custProfit.getDesignFee()==null?0:custProfit.getDesignFee());
			call.setDouble(6, custProfit.getPrepay()==null?0:custProfit.getPrepay());
			call.setDouble(7, custProfit.getGift()==null?0:custProfit.getGift());
			call.setInt(8, custProfit.getContainBase()==null?0:1);
			call.setInt(9, custProfit.getContainMain()==null?0:1);
			call.setInt(10, custProfit.getContainSoft()==null?0:1);
			call.setInt(11, custProfit.getContainInt()==null?0:1);
			call.setInt(12, custProfit.getContainCup()==null?0:1);
			call.setInt(13, custProfit.getContainMainServ()==null?0:1);
			call.setString(14, custProfit.getPayType());
			call.setString(15, custProfit.getPosition());
			call.setString(16,custProfit.getLastUpdatedBy());
			call.setDouble(17,custProfit.getStdDesignFee()==null?0:custProfit.getStdDesignFee());
			call.setDouble(18,custProfit.getReturnDesignFee()==null?0:custProfit.getReturnDesignFee());
			call.setDouble(19,custProfit.getTax()==null?0:custProfit.getTax());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				Map<String, Object> map=list.get(0);
				result.setCode(map.get("ret").toString());
				result.setInfo(map.get("errmsg").toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

}

