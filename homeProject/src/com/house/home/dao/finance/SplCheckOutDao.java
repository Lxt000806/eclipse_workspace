package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.insales.Purchase;
import com.sun.org.apache.xpath.internal.operations.And;


@SuppressWarnings("serial")
@Repository
public class SplCheckOutDao extends BaseDao {

	/**
	 * SplCheckOut分页信息
	 * 
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SplCheckOut splCheckOut) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.no,a.SplCode,b.Descr SplCodedescr,"
		           + "a.Date,a.PayType,c.NOTE PayTypeDescr,a.BeginDate,a.EndDate,"
		           + "a.PayAmount,isnull(a.OtherCost,0) OtherCost,a.Remark,"
		           + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Status,d.Note StatusDescr,"
		           + "a.PayCZY,a.PayDate,e.NameChi PayCZYDescr,a.DocumentNo,a.ConfirmDate,"
		           + "a.PaidAmount,a.NowAmount,a.PreAmount,a.PayAmount-a.PaidAmount PayBalance " 
		           + " from tSplCheckOut a "
		           + " left outer join tSupplier b on a.SplCode=b.Code "
		           + " left outer join tXTDM d on a.Status=d.CBM and d.ID='SPLCKOTSTATUS' "
		           + " left outer join (select * from tXTDM where ID='PAYTYPE') c on a.PayType=c.CBM "
		           + " left outer join tEmployee e on e.number=a.PayCZY where a.Expired='F' ";

    	if (StringUtils.isNotBlank(splCheckOut.getNo())) {
			sql += " and a.No=? ";
			list.add(splCheckOut.getNo());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getSplCode())) {
			sql += " and a.SplCode=? ";
			list.add(splCheckOut.getSplCode());
		}else{
			return null;
		}
    	if (splCheckOut.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(splCheckOut.getDateFrom());
		}
		if (splCheckOut.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addDateOneDay(splCheckOut.getDateTo()));
		}
    	if (StringUtils.isNotBlank(splCheckOut.getPayType())) {
			sql += " and a.PayType=? ";
			list.add(splCheckOut.getPayType());
		}
    	if (splCheckOut.getPayAmount() != null) {
			sql += " and a.PayAmount=? ";
			list.add(splCheckOut.getPayAmount());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(splCheckOut.getRemarks());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(splCheckOut.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getAppCZY())) {
			sql += " and a.appCzy=? ";
			list.add(splCheckOut.getAppCZY());
		}
		if ("F".equals(splCheckOut.getExpired())) {
			sql += " and exists (select 1 from tPurchase x where x.CheckOutNo=a.No and x.RemainAmount<>0) ";
		}
    	if (StringUtils.isNotBlank(splCheckOut.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(splCheckOut.getActionLog());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(splCheckOut.getRemark());
		}
    	if (splCheckOut.getOtherCost() != null) {
			sql += " and a.OtherCost=? ";
			list.add(splCheckOut.getOtherCost());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getStatus())) {
			String str = SqlUtil.resetStatus(splCheckOut.getStatus());
			sql += " and a.status in (" + str + ")";
		}
    	if (StringUtils.isNotBlank(splCheckOut.getConfirmCzy())) {
			sql += " and a.ConfirmCZY=? ";
			list.add(splCheckOut.getConfirmCzy());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getDocumentNo())) {
			sql += " and a.DocumentNo=? ";
			list.add(splCheckOut.getDocumentNo());
		}
    	if (StringUtils.isNotBlank(splCheckOut.getPayCzy())) {
			sql += " and a.PayCZY=? ";
			list.add(splCheckOut.getPayCzy());
		}
    	if (splCheckOut.getPaidAmount() != null) {
			sql += " and a.PaidAmount=? ";
			list.add(splCheckOut.getPaidAmount());
		}
    	if (splCheckOut.getNowAmount() != null) {
			sql += " and a.NowAmount=? ";
			list.add(splCheckOut.getNowAmount());
		}
    	if (splCheckOut.getPreAmount() != null) {
			sql += " and a.PreAmount=? ";
			list.add(splCheckOut.getPreAmount());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 采购单结算保存
	 * @param itemApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveSplCheckOutForProc(SplCheckOut splCheckOut) {
		Assert.notNull(splCheckOut);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGysjs_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, splCheckOut.getM_umState());
			call.setString(2, splCheckOut.getNo());
			call.setString(3, splCheckOut.getSplCode());
			call.setString(4, splCheckOut.getPayType());
			call.setTimestamp(5, splCheckOut.getBeginDate()==null?null:new Timestamp(splCheckOut.getBeginDate().getTime()));
			call.setTimestamp(6, splCheckOut.getEndDate()==null?null:new Timestamp(splCheckOut.getEndDate().getTime()));
			call.setDouble(7, splCheckOut.getOtherCost()==null?0:splCheckOut.getOtherCost());
			call.setString(8, splCheckOut.getRemark());
			call.setString(9, splCheckOut.getLastUpdatedBy());
			call.setString(10, splCheckOut.getDocumentNo());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.setString(13, splCheckOut.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * 供应商结算选择查询
	 * @author	created by zb
	 * @date	2018-11-22--下午4:08:09
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.No,a.SplCode,b.Descr SplDescr,a.Date, " +
					 "a.OtherCost,a.PayAmount,a.PaidAmount, a.NowAmount, " +
					 "a.PreAmount,b.PrepayBalance,a.Remark,a.LastUpdate " +
					 "from tSplCheckOut a " +
					 "left join tSupplier b on a.SplCode=b.Code " +
					 "where a.Expired='F' and a.Status='2' " +
					 "and exists (select 1 from tPurchase c where c.CheckOutNo=a.No and c.RemainAmount<>0)";
		if (StringUtils.isNotBlank(splCheckOut.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+splCheckOut.getNo()+"%");
		}			 
		if (StringUtils.isNotBlank(splCheckOut.getSplCode())) {
			sql += " and a.SplCode=? ";
			list.add(splCheckOut.getSplCode());
		}
		if (StringUtils.isNotBlank(splCheckOut.getStatus())) {
			String str = SqlUtil.resetStatus(splCheckOut.getStatus());
			sql += " and a.status in (" + str + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	

	/**
	 * 供应商录入采购费用明细
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_purchaseFeeDetail(
			Page<Map<String, Object>> page, Purchase purchase) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select  SupplFeeType, b.Descr SupplFeeTypeDescr, Amount, Remarks "
					+ " from tPurchaseFeeDetail a "
					+ " left join tSupplFeeType b on a.SupplFeeType = b.code " 
					+ " where puno =? and GenerateType = '1' "
					+ " union "
					+ " select  a.code SupplFeeType, a.Descr SupplFeeTypeDescr, 0 Amount, '' Remarks "
					+ " from    tSupplFeeType a "
					+ " where  not exists ( select 1 from   tPurchaseFeeDetail pfd where  pfd.puno = ? " 
					+ " and pfd.generatetype = '1' and pfd.SupplFeeType = a.code)";					
		list.add(purchase.getNo());
		list.add(purchase.getNo());
		if (StringUtils.isNotBlank(purchase.getSupplFeeType())) {
			String str = SqlUtil.resetStatus(purchase.getSupplFeeType());
			sql += " and(a.code in (" + str + ") or a.IsDefault='1') ";
			
		}else{
			sql += " and a.IsDefault='1' ";
		}	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.SupplFeeType ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 采购单其他费用保存
	 * @param itemApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveSplOtherCostForProc(Purchase purchase) {
		Assert.notNull(purchase);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSplAddOtherCost(?,?,?,?,?)}");
			call.setString(1, purchase.getNo());
			call.setString(2, purchase.getLastUpdatedBy());
			call.setString(3, purchase.getPurchaseDetailXml());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	

}

