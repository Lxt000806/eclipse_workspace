package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.home.bean.design.CustPaypreSaveBean;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.design.CustPayPre;

@SuppressWarnings("serial")
@Repository
public class CustPayPreDao extends BaseDao {

	/**
	 * CustPayPre分页信息
	 * 
	 * @param page
	 * @param custPayPre
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustPayPre custPayPre) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCustPayPre a where 1=1 ";

    	if (custPayPre.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custPayPre.getPk());
		}
    	if (StringUtils.isNotBlank(custPayPre.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custPayPre.getCustCode());
		}
    	if (StringUtils.isNotBlank(custPayPre.getPayType())) {
			sql += " and a.PayType=? ";
			list.add(custPayPre.getPayType());
		}
    	if (custPayPre.getBasePer() != null) {
			sql += " and a.BasePer=? ";
			list.add(custPayPre.getBasePer());
		}
    	if (custPayPre.getItemPer() != null) {
			sql += " and a.ItemPer=? ";
			list.add(custPayPre.getItemPer());
		}
    	if (custPayPre.getDesignFee() != null) {
			sql += " and a.DesignFee=? ";
			list.add(custPayPre.getDesignFee());
		}
    	if (custPayPre.getPrePay() != null) {
			sql += " and a.PrePay=? ";
			list.add(custPayPre.getPrePay());
		}
    	if (custPayPre.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custPayPre.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custPayPre.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custPayPre.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custPayPre.getExpired()) || "F".equals(custPayPre.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custPayPre.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custPayPre.getActionLog());
		}
    	if (custPayPre.getBasePay() != null) {
			sql += " and a.BasePay=? ";
			list.add(custPayPre.getBasePay());
		}
    	if (custPayPre.getItemPay() != null) {
			sql += " and a.ItemPay=? ";
			list.add(custPayPre.getItemPay());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public CustPayPre getCustPayPre(String custCode, String payType) {
		String hql="from CustPayPre where custCode=? and payType=? ";
		List<CustPayPre> list = this.find(hql, new Object[]{custCode,payType});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Result doCustPaypreForProc(CustPaypreSaveBean custPaypreSaveBean) {
		Assert.notNull(custPaypreSaveBean);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustPaypre(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1,custPaypreSaveBean.getCustCode());
			call.setDouble(2, custPaypreSaveBean.getBasePer1()==null?0:custPaypreSaveBean.getBasePer1());
			call.setDouble(3, custPaypreSaveBean.getItemPer1()==null?0:custPaypreSaveBean.getItemPer1());
			call.setDouble(4, custPaypreSaveBean.getDesignFee1()==null?0:custPaypreSaveBean.getDesignFee1());
			call.setDouble(5, custPaypreSaveBean.getPrePay1()==null?0:custPaypreSaveBean.getPrePay1());
			call.setDouble(6, custPaypreSaveBean.getBasePer2()==null?0:custPaypreSaveBean.getBasePer2());
			call.setDouble(7, custPaypreSaveBean.getItemPer2()==null?0:custPaypreSaveBean.getItemPer2());
			call.setDouble(8, custPaypreSaveBean.getDesignFee2()==null?0:custPaypreSaveBean.getDesignFee2());
			call.setDouble(9, custPaypreSaveBean.getPrePay2()==null?0:custPaypreSaveBean.getPrePay2());
			call.setDouble(10, custPaypreSaveBean.getBasePer3()==null?0:custPaypreSaveBean.getBasePer3());
			call.setDouble(11, custPaypreSaveBean.getItemPer3()==null?0:custPaypreSaveBean.getItemPer3());
			call.setDouble(12, custPaypreSaveBean.getDesignFee3()==null?0:custPaypreSaveBean.getDesignFee3());
			call.setDouble(13, custPaypreSaveBean.getPrePay3()==null?0:custPaypreSaveBean.getPrePay3());
			call.setDouble(14, custPaypreSaveBean.getBasePer4()==null?0:custPaypreSaveBean.getBasePer4());
			call.setDouble(15, custPaypreSaveBean.getItemPer4()==null?0:custPaypreSaveBean.getItemPer4());
			call.setDouble(16, custPaypreSaveBean.getDesignFee4()==null?0:custPaypreSaveBean.getDesignFee4());
			call.setDouble(17, custPaypreSaveBean.getPrePay4()==null?0:custPaypreSaveBean.getPrePay4());
			call.setDouble(18, custPaypreSaveBean.getBasePay1()==null?0:custPaypreSaveBean.getBasePay1());
			call.setDouble(19, custPaypreSaveBean.getItemPay1()==null?0:custPaypreSaveBean.getItemPay1());
			call.setDouble(20, custPaypreSaveBean.getBasePay2()==null?0:custPaypreSaveBean.getBasePay2());
			call.setDouble(21, custPaypreSaveBean.getItemPay2()==null?0:custPaypreSaveBean.getItemPay2());
			call.setDouble(22, custPaypreSaveBean.getBasePay3()==null?0:custPaypreSaveBean.getBasePay3());
			call.setDouble(23, custPaypreSaveBean.getItemPay3()==null?0:custPaypreSaveBean.getItemPay3());
			call.setDouble(24, custPaypreSaveBean.getBasePay4()==null?0:custPaypreSaveBean.getBasePay4());
			call.setDouble(25, custPaypreSaveBean.getItemPay4()==null?0:custPaypreSaveBean.getItemPay4());
			call.setString(26,custPaypreSaveBean.getLastUpdatedBy());
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

