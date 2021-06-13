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
import com.house.home.entity.basic.SendTime;

@SuppressWarnings("serial")
@Repository
public class SendTimeDao extends BaseDao {

	/**
	 * SendTime分页信息
	 * 
	 * @param page
	 * @param sendTime
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SendTime sendTime) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.No,a.ItemType1,b.Descr Itemtype1Descr,a.ProductType,xt1.NOTE ProductTypeDescr, "
				+ "a.IsSetItem,xt2.NOTE IsSetItemDescr,a.SendDay,a.Prior,a.Remarks, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog  "
				+ "from tSendTime a left join titemtype1 b on a.ItemType1=b.Code  "
				+ "left join tXTDM xt1 on a.ProductType=xt1.CBM and xt1.ID='APPPRODUCTTYPE' "
				+ "left join tXTDM xt2 on a.IsSetItem=xt2.CBM and xt2.ID='YESNO' "
				+ "where 1=1";

		if (StringUtils.isNotBlank(sendTime.getNo())) {
			sql += " and a.No=? ";
			list.add(sendTime.getNo());
		}
		if (StringUtils.isNotBlank(sendTime.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(sendTime.getItemType1());
		}
		if (StringUtils.isNotBlank(sendTime.getProductType())) {
			sql += " and a.ProductType=? ";
			list.add(sendTime.getProductType());
		}
		if (StringUtils.isBlank(sendTime.getExpired())
				|| "F".equals(sendTime.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(sendTime.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(sendTime.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.No desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * SendTimeDetail分页信息
	 * 
	 * @param page
	 * @param sendTime
	 * @return
	 */
	public Page<Map<String, Object>> goDetailGrid(Page<Map<String, Object>> page, SendTime sendTime) {
		List<Object> list = new ArrayList<Object>();

		String sql = "  select * from(select a.PK,a.ItemType2,a.ItemDesc,a.LastUpdate,b.Descr itemType2Descr, "
				+ "a.LastUpdatedBy,a.Expired,a.ActionLog from tSendTimeDetail a  "
				+ "left join tItemType2 b on a.ItemType2=b.Code where No=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a ";
		}
		list.add(sendTime.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 
	 * 供应商是否引用发货时限
	 * @param sendTime
	 * @return
	 */
	public List<Map<String, Object>> isSupplierTime(SendTime sendTime) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select pk from tSupplierTime where SendTimeNo=? ";
		list.add(sendTime.getNo());
		return this.findBySql(sql, list.toArray());
	}
	/**
	 * 保存
	 * 
	 * @param sendTime
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(SendTime sendTime) {
		Assert.notNull(sendTime);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pFhsx_forXml(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, sendTime.getNo());
			call.setString(2, sendTime.getM_umState());	
			call.setString(3, sendTime.getItemType1());
			call.setString(4, sendTime.getProductType());
			call.setString(5, sendTime.getIsSetItem());
			call.setInt(6, sendTime.getSendDay());
			call.setInt(7, sendTime.getPrior());
			call.setString(8, sendTime.getRemarks());
			call.setString(9, sendTime.getLastUpdatedBy());
			call.setString(10, sendTime.getSendTimeDetailJson());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
}

