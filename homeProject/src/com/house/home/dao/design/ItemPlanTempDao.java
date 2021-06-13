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
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.entity.insales.BaseItem;
@SuppressWarnings("serial")
@Repository
public class ItemPlanTempDao  extends BaseDao  {
	/**
	 * ItemPlan分页信息
	 * 
	 * @param page
	 * @param itemPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp) {
		List<Object> list = new ArrayList<Object>();
	
		String sql ="select a.no,a.descr,a.remark from tItemPlanTemp a where 1=1 " ;
	
    	if (StringUtils.isNotBlank(itemPlanTemp.getItemType1())) {
			sql += " and a.itemType1=? ";
			list.add(itemPlanTemp.getItemType1());
		}
    	if(StringUtils.isNotBlank(itemPlanTemp.getDescr())){
			sql+=" and a.descr like ?";
			list.add("%"+itemPlanTemp.getDescr()+"%");
		}
    	if (StringUtils.isBlank(itemPlanTemp.getExpired()) || "F".equals(itemPlanTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp) {
		List<Object> list = new ArrayList<Object>();
	
		String sql ="select * from ( select a.No,a.Descr,a.Remark,a.ItemType1,b.Descr ItemType1Descr," +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired," +
				" a.ActionLog from tItemPlanTemp a " +
				" left outer join tItemType1 b on a.ItemType1=b.Code " +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(itemPlanTemp.getDescr())){
			sql+=" and a.descr like ?";
			list.add("%"+itemPlanTemp.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(itemPlanTemp.getItemType1())){
			sql+=" and a.itemType1 = ? ";
			list.add(itemPlanTemp.getItemType1());
		}
		if (StringUtils.isBlank(itemPlanTemp.getExpired())
				|| "F".equals(itemPlanTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, ItemPlanTemp itemPlanTemp) {
		List<Object> list = new ArrayList<Object>();
	
		String sql =" Select a.actionLog,a.pk,a.No,a.ItemCode,b.Descr ItemCodeDescr,a.Qty,a.DispSeq," +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired from tItemPlanTempDetail a  " +
				" left outer join tItem b on b.Code=a.ItemCode " +
				" where no = ? " +
				" ORDER BY a.DispSeq " ;
		list.add(itemPlanTemp.getNo());
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(ItemPlanTemp itemPlanTemp) {
		Assert.notNull(itemPlanTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClbjmb_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPlanTemp.getM_umState());
			call.setString(2, itemPlanTemp.getNo());
			call.setString(3, itemPlanTemp.getDescr());
			call.setString(4, itemPlanTemp.getItemType1());
			call.setString(5, itemPlanTemp.getRemark());
			call.setString(6, itemPlanTemp.getLastUpdatedBy());
			call.setString(7, itemPlanTemp.getExpired());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setString(10, itemPlanTemp.getItemPlanTempDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
}
