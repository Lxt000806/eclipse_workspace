package com.house.home.dao.finance;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.finance.SupplierPay;
@SuppressWarnings("serial")
@Repository
public class SupplierPayDao extends BaseDao{

	/**
	 * 供应商分页详细查询
	 * @author	created by zb
	 * @date	2018-11-22--上午11:02:05
	 * @param page
	 * @param supplierPay
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SupplierPay supplierPay) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.No,a.CheckOutNo,a.Status,b.NOTE StatusDescr, " +
					 "	round(a.PaidAmount,2) PaidAmount,a.NowAmount,a.PreAmount,a.DocumentNo, " +
					 "	a.AppEmp,e1.NameChi AppEmpDescr,a.AppDate, " +
					 "	a.ConfirmEmp,e2.NameChi ConfirmEmpDescr,a.ConfirmDate, " +
					 "	a.PayEmp,e3.NameChi PayEmpDescr,a.PayDate,a.Remarks, " +
					 "	c.SplCode,d.Descr SplDescr,k.WfProcInstNo, " +
					 "	a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,e.Descr supplTypeDescr " +
					 " from tSupplierPay a " +
					 "	left join tXTDM b on b.ID='SPLPAYSTATUS' and a.Status=b.CBM " +
					 "	left join tEmployee e1 on a.AppEmp=e1.Number  " +
					 "	left join tEmployee e2 on a.ConfirmEmp=e2.Number " +
					 "	left join tEmployee e3 on a.PayEmp=e3.Number " +
					 "	left join tSplCheckOut c on a.CheckOutNo=c.No " +
					 "	left join tSupplier d on c.SplCode=d.Code " +
					 "  left join tItemType1 e on d.ItemType1=e.Code "+
					 " left join (select max(WfProcInstNo) wfProcInstNo,a.RefNo from tWfCust_PurchaseExpense a group by a.RefNo" +
					 "	) k on k.RefNo = a.CheckOutNo "+
					 "where a.Expired='F' ";
		if (StringUtils.isNotBlank(supplierPay.getStatus())) {
			sql += " and a.Status in ('"+supplierPay.getStatus().replace(",", "','")+"')";
		}
		if (null != supplierPay.getAppDateFrom()) {
			sql += " and a.AppDate >= ? ";
			list.add(supplierPay.getAppDateFrom());
		}
		if (null != supplierPay.getAppDateTo()) {
			sql += " and a.AppDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(supplierPay.getAppDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(supplierPay.getNo())) {
			sql += " and a.No = ? ";
			list.add(supplierPay.getNo());
		}
		if (StringUtils.isNotBlank(supplierPay.getDocumentNo())) {
			sql += " and a.DocumentNo like ? ";
			list.add("%"+supplierPay.getDocumentNo()+"%");
		}
		if (StringUtils.isNotBlank(supplierPay.getCheckOutNo())) {
			sql += " and c.No = ? ";
			list.add(supplierPay.getCheckOutNo());
		}
		if (StringUtils.isNotBlank(supplierPay.getSplCode())) {
			sql += " and d.Code = ? ";
			list.add(supplierPay.getSplCode());
		}
		sql += " and d.ItemType1 in ('"+supplierPay.getItemType1().replace(",", "','")+"') ";
		if (StringUtils.isNotBlank(supplierPay.getRemarks())) {
			sql += " and a.remarks like ? ";
			list.add("%"+supplierPay.getRemarks()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 供应商付款明细
	 * @author	created by zb
	 * @date	2018-11-30--下午5:40:38
	 * @param page
	 * @param supplierPay
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, SupplierPay supplierPay) {
		Assert.notNull(supplierPay);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSupplierPayDetail(?,?,?)}");
			call.setString(1, supplierPay.getLastUpdatedBy());
			call.setString(2, supplierPay.getCheckOutNo());
			call.setString(3, supplierPay.getNo());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(page.getResult().size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return page;
	}

	/**
	 * 根据付款总金额分配本次付款
	 * @author	created by zb
	 * @date	2018-12-6--下午4:11:28
	 * @param supplierPay
	 * @return
	 */
	public List<Map<String, Object>> doSetPaidAmount(SupplierPay supplierPay) {
		Assert.notNull(supplierPay);
		Connection conn = null;
		CallableStatement call = null;
		List<Map<String, Object>> list = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAllocPaidAmount_forXml(?,?)}");
			call.setDouble(1, supplierPay.getPaidAmount());
			call.setString(2, supplierPay.getDetailJson());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			list = BeanConvertUtil.resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return list;
	}

	/**
	 * 根据结算单号获取供应商和预付款余额
	 * @author	created by zb
	 * @date	2018-12-12--下午4:43:47
	 * @param supplierPay
	 * @return
	 */
	public Map<String, Object> getSplInfo(SupplierPay supplierPay) {
		String sql = "select b.Descr,b.PrepayBalance from tSplCheckOut a " +
				"inner join tSupplier b on a.SplCode=b.Code " +
				"where a.No=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplierPay.getCheckOutNo()});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * SumPaidAmount_1
	 * @author	created by zb
	 * @date	2018-12-12--下午5:40:48
	 * @param supplierPay
	 * @return
	 */
	public Map<String, Object> getSumPaidAmount(SupplierPay supplierPay) {
		String sql = "select sum(PaidAmount) SumPaidAmount from tSupplierPay " +
				"where CheckOutNo=? and Status<>'3' and PayEmp is null " +
				"and No<>? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplierPay.getCheckOutNo(), supplierPay.getNo()});
		if (list!=null && list.size()>0 && null != list.get(0).get("SumPaidAmount")){
			return list.get(0);
		}
		return null;
	}

	/**
	 * SumPaidAmount_2
	 * @author	created by zb
	 * @date	2018-12-12--下午5:40:48
	 * @param supplierPay
	 * @return
	 */
	public Map<String, Object> getSumPaidAmount2(SupplierPay supplierPay) {
		String sql = "select sum(PaidAmount) SumPaidAmount from tSupplierPay " +
				"where CheckOutNo=? and Status='2' and PayEmp is null " +
				"and No<>? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplierPay.getCheckOutNo(), supplierPay.getNo()});
		if (list!=null && list.size()>0 && null != list.get(0).get("SumPaidAmount")){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * SumRemainAmount
	 * @author	created by zb
	 * @date	2018-12-12--下午5:48:43
	 * @param supplierPay
	 * @return
	 */
	public Map<String, Object> getSumRemainAmount(SupplierPay supplierPay) {
		String sql = "select sum(RemainAmount) SumRemainAmount from tPurchase " +
				"where CheckOutNo=? " ;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplierPay.getCheckOutNo()});
		if (list!=null && list.size()>0 && null != list.get(0).get("SumRemainAmount")){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 走pGysfkgl存储过程
	 */
	public Result doSave(SupplierPay supplierPay) {
		Assert.notNull(supplierPay);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGysfkgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, supplierPay.getM_umState());
			call.setString(2, supplierPay.getNo());
			call.setString(3, supplierPay.getCheckOutNo());
			call.setString(4, supplierPay.getStatus());
			call.setDouble(5, supplierPay.getPaidAmount());
			call.setDouble(6, supplierPay.getNowAmount());
			call.setDouble(7, supplierPay.getPreAmount());
			call.setString(8, supplierPay.getDocumentNo());
			call.setString(9, supplierPay.getAppEmp());
			call.setString(10, supplierPay.getConfirmEmp());
			call.setString(11, supplierPay.getPayEmp());
			call.setTimestamp(12, supplierPay.getPayDate()==null?
					null:new Timestamp(supplierPay.getPayDate().getTime()));//由long转为time
			call.setString(13, supplierPay.getRemarks());
			call.setString(14, supplierPay.getLastUpdatedBy());
			call.setString(15, supplierPay.getExpired());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setString(18, supplierPay.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(16)));
			result.setInfo(call.getString(17));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
}
