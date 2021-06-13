package com.house.home.dao.basic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.CustGift;
import com.house.home.entity.insales.Purchase;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class CustGiftDao extends BaseDao{

	@SuppressWarnings("deprecation")
	public Result doCustGiftSave(CustGift custGift) {
		Assert.notNull(custGift);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemPlan_custGift(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, custGift.getpK() == null ? 0 :custGift.getpK());
			call.setInt(2, custGift.getGiftPK() == null ? 0 : custGift.getGiftPK());
			call.setString(3, custGift.getType());
			call.setString(4, custGift.getQuoteModule());
			call.setDouble(5, custGift.getDiscAmount()==null?0:custGift.getDiscAmount());
			call.setDouble(6, custGift.getSaleAmount()==null?0:custGift.getSaleAmount());
			call.setDouble(7, custGift.getTotalCost()==null?0:custGift.getTotalCost());
			call.setString(8, custGift.getDiscAmtType());
			call.setString(9, custGift.getPerfDiscType());
			call.setDouble(10, custGift.getPerfDiscPer()==null?0:custGift.getPerfDiscPer());
			call.setDouble(11, custGift.getPerfDiscAmount()==null?0:custGift.getPerfDiscAmount());
			call.setDouble(12, custGift.getCalcDiscCtrlPer()==null?0:custGift.getCalcDiscCtrlPer());
			call.setString(13, custGift.getIsSoftToken());
			call.setString(14, custGift.getM_umState());
			call.setString(15, custGift.getLastUpdatedBy());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setString(18, custGift.getDetailXml());
			call.setString(19,custGift.getCustCode());
			call.setString(20,custGift.getRemarks());
			call.setString(21,custGift.getMaxDiscAmtExpr());
			call.setInt(22,custGift.getDispSeq() ==null ? 0:custGift.getDispSeq());
			call.setDouble(23, custGift.getProjectAmount()==null?0:custGift.getProjectAmount());
			call.setString(24,custGift.getDiscAmtCalcType());
			call.setString(25,custGift.getIsAdvance());
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
	
	/**
	 * 删除一个客户的所有赠品
	 * 
	 * @param custCode
	 */
	public void deleteAllGiftsByCustCode(String custCode) {
	    String sql = ""
	            + "delete from tCustGiftItem "
	            + "from tCustGiftItem a "
	            + "inner join tCustGift b on b.PK = a.CustGiftPK "
	            + "where b.CustCode = ? "
	            + System.lineSeparator()
	            + "delete from tCustGift "
	            + "where CustCode = ? ";
	    
	    executeUpdateBySql(sql, custCode, custCode);
	    
	    flush();
	}

}
