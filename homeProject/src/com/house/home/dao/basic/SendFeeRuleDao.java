package com.house.home.dao.basic;

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
import com.house.home.entity.basic.SendFeeRule;

@SuppressWarnings("serial")
@Repository
public class SendFeeRuleDao extends BaseDao {

	/**
	 * SendFeeRule分页信息
	 * 
	 * @param page
	 * @param sendFeeRule
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SendFeeRule sendFeeRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.No,a.ItemType1,a.ItemType2,a.CalType,a.Expired,a.LastUpdate,a.LastUpdatedBy,"
				+ "a.ActionLog,a.Price,b.Descr ItemType1Descr,c.Descr ItemType2Descr,d.NOTE CalTypeDescr,a.Remarks, "
				+ "e.NOTE sendTypeDescr,f.NOTE smallSendTypeDescr,smallSendMaxValue,smallSendFeeAdj "
				+ "from tSendFeeRule a "
				+ "left join tItemType1 b on a.ItemType1=b.Code "
				+ "left join tItemType2 c on a.ItemType2=c.Code "
				+ "left join tXTDM d on a.CalType=d.CBM and d.ID='SendFeeCalType' "
				+ "left join tXTDM e on a.sendType=e.CBM and e.ID='ITEMAPPSENDTYPE' "
				+ "left join tXTDM f on a.smallSendType=f.CBM and f.ID='SmallSendType' "
				+ "where 1=1 ";

		if (StringUtils.isNotBlank(sendFeeRule.getNo())) {
			sql += " and a.No=? ";
			list.add(sendFeeRule.getNo());
		}
		if (StringUtils.isNotBlank(sendFeeRule.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(sendFeeRule.getItemType1());
		}
		if (StringUtils.isNotBlank(sendFeeRule.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(sendFeeRule.getItemType2());
		}
		if (StringUtils.isNotBlank(sendFeeRule.getCalType())) {
			sql += " and a.CalType=? ";
			list.add(sendFeeRule.getCalType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * SendFeeRuleItem分页信息
	 * 
	 * @param page
	 * @param sendFeeRule
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, SendFeeRule sendFeeRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.No,a.ItemType3,a.Expired,a.LastUpdate,a.LastUpdatedBy,"
				+ "a.ActionLog,b.Descr ItemType3Descr "
				+ "from tSendFeeRuleItem a "
				+ "left join tItemType3 b on a.ItemType3=b.Code "
				+ "where a.No=? ";
		list.add(sendFeeRule.getNo());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 保存
	 * 
	 * @param sendFeeRule
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(SendFeeRule sendFeeRule) {
		Assert.notNull(sendFeeRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPsfgzgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, sendFeeRule.getNo());
			call.setString(2, sendFeeRule.getM_umState());	
			call.setString(3, sendFeeRule.getItemType1());
			call.setString(4, sendFeeRule.getItemType2());
			call.setString(5, sendFeeRule.getCalType());
			call.setDouble(6, sendFeeRule.getPrice()!=null?sendFeeRule.getPrice():0);
			call.setString(7, sendFeeRule.getRemarks());
			call.setString(8, sendFeeRule.getLastUpdatedBy());
			call.setString(9, sendFeeRule.getSendFeeRuleItemJson());
			
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			
			call.setString(12, sendFeeRule.getSendType());
			call.setString(13, sendFeeRule.getSmallSendType());
			call.setDouble(14, sendFeeRule.getSmallSendMaxValue()!=null?sendFeeRule.getSmallSendMaxValue():0);
			call.setDouble(15, sendFeeRule.getSmallSendFeeAdj()!=null?sendFeeRule.getSmallSendFeeAdj():0);
			call.setString(16, sendFeeRule.getSupplCode());
			call.setString(17, sendFeeRule.getwHCode());
			call.setInt(18, sendFeeRule.getBeginTimes() != null ? sendFeeRule.getBeginTimes() : 0);
			call.setInt(19, sendFeeRule.getEndTimes() != null ? sendFeeRule.getEndTimes() : 0);
			call.setDouble(20, sendFeeRule.getCardAmount() != null ? sendFeeRule.getCardAmount() : 0.0);
			call.setDouble(21, sendFeeRule.getIncValue() != null ? sendFeeRule.getIncValue() : 0.0);
			
			call.execute();
			result.setCode(String.valueOf(call.getInt(10)));
			result.setInfo(call.getString(11));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
}

