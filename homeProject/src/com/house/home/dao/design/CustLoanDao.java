package com.house.home.dao.design;

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
import com.house.home.entity.basic.Item;
import com.house.home.entity.design.CustLoan;

@SuppressWarnings("serial")
@Repository
public class CustLoanDao extends BaseDao {

	/**
	 * CustLoan分页信息
	 * 
	 * @param page
	 * @param custLoan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustLoan custLoan) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.CustCode, a.AgreeDate, a.Bank, a.FollowRemark, a.SignRemark, a.ConfuseRemark, a.Amount, a.FirstAmount, "
				+ " a.FirstDate, a.SecondAmount, a.SecondDate, a.Remark, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog ,"
				+ " CAST(dbo.fGetEmpNameChi(a.custCode, '00') AS NVARCHAR(100)) DesignManDescr ,"
				+ " CAST(dbo.fGetEmpNameChi(a.custCode, '01') AS NVARCHAR(100)) BusinessManDescr ,"
				+ " b.Descr custDescr,b.Status , x1.note StatusDescr,b.address from  tCustLoan  a "
				+ " left outer join tCustomer b on a.CustCode=b.Code "
				+ " left outer join txtdm x1  on x1.id='CUSTOMERSTATUS' and x1.cbm=b.Status "
				+ " where 1=1 ";

    	if (StringUtils.isNotBlank(custLoan.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custLoan.getCustCode());
		}
    	if (custLoan.getAgreeDate() != null) {
			sql += " and a.AgreeDate=? ";
			list.add(custLoan.getAgreeDate());
		}
    	if (StringUtils.isNotBlank(custLoan.getBank())) {
			sql += " and a.Bank like ? ";	
			list.add("%" + custLoan.getBank() + "%");
		}
    	if (StringUtils.isNotBlank(custLoan.getFollowRemark())) {
			sql += " and a.FollowRemark=? ";
			list.add(custLoan.getFollowRemark());
		}
    	if (StringUtils.isNotBlank(custLoan.getSignRemark())) {
			sql += " and a.SignRemark=? ";
			list.add(custLoan.getSignRemark());
		}
    	if (StringUtils.isNotBlank(custLoan.getConfuseRemark())) {
			sql += " and a.ConfuseRemark=? ";
			list.add(custLoan.getConfuseRemark());
		}
    	if (custLoan.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(custLoan.getAmount());
		}
    	if (custLoan.getFirstAmount() != null) {
			sql += " and a.FirstAmount=? ";
			list.add(custLoan.getFirstAmount());
		}
    	if (custLoan.getFirstDate() != null) {
			sql += " and a.FirstDate=? ";
			list.add(custLoan.getFirstDate());
		}
    	if (custLoan.getSecondAmount() != null) {
			sql += " and a.SecondAmount=? ";
			list.add(custLoan.getSecondAmount());
		}
    	if (custLoan.getSecondDate() != null) {
			sql += " and a.SecondDate=? ";
			list.add(custLoan.getSecondDate());
		}
    	if (StringUtils.isNotBlank(custLoan.getRemark())) {
			sql += " and a.Remark=? ";
			list.add(custLoan.getRemark());
		}
    	if (custLoan.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custLoan.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custLoan.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custLoan.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(custLoan.getExpired()) || "F".equals(custLoan.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(custLoan.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custLoan.getActionLog());
		}
    	if (StringUtils.isNotBlank(custLoan.getStatusDescr())) {
			sql += " and b.Status in " + "('"+custLoan.getStatusDescr().replaceAll(",", "','")+"')";
			
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate,a.Bank";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 批量导入调过程
	 * @param custTypeItem
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (CustLoan custLoan) {
		Assert.notNull(custLoan);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCustLoan_import(?,?,?,?)}");
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(2, Types.NVARCHAR);
			call.setString(3, custLoan.getLastUpdatedBy());
			call.setString(4, custLoan.getDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(1)));
			result.setInfo(call.getString(2));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 判断客户编号是否重复
	 * @param custTypeItem
	 * @return
	 */
	public boolean isExistCustCode(CustLoan custLoan) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
			sql = "select 1 from tCustLoan where CustCode =? ";
			list.add(custLoan.getCustCode());
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if (list1!=null && list1.size()>0){
			return true;
		}else{
			return false;
		}
	}

}

