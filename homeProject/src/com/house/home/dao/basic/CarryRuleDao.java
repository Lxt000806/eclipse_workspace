package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Types;
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
import com.house.home.entity.basic.CarryRule;
import com.house.home.entity.basic.CarryRuleFloor;
import com.house.home.entity.basic.CarryRuleItem;

@SuppressWarnings("serial")
@Repository
public class CarryRuleDao extends BaseDao {

	/**
	 * ItemSet分页信息
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CarryRule carryRule) {
		List<Object> list = new ArrayList<Object>();

		String sql =  "select a.No,a.ItemType1,it1.Descr ItemType1Descr,a.ItemType2,it2.Descr ItemType2Descr,a.CarryType," +
				" x1.NOTE CarryTypedescr,x4.note CalTypeDescr, x5.NOTE SendTypeDescr, " +
				" a.SendType,a.DistanceType,x3.NOTE DistanceTypedescr,a.Remarks," +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.SupplCode,sp.descr Suppldescr from tCarryRule a" +
				" left join tItemType1 it1 on a.ItemType1=it1.Code " +
				" left join tItemType2 it2 on a.ItemType2=it2.Code " +
				" left join tXTDM x1 on x1.CBM=a.CarryType and x1.ID='CarType'" +
				" left join tXTDM x4 on x4.CBM=a.CalType and x4.ID='CarryCalType'" +
				" left join tXTDM x5 on x5.CBM=a.SendType and x5.ID='ITEMAPPSENDTYPE'" + //发货方式 add by zb on 20190402
				" left join tXTDM x3 on x3.CBM=a.DistanceType and x3.ID='DistanceType'  "+							
    	        " left join tSupplier sp on sp.Code=a.SupplCode "+
				" where a.expired='F' "; 
		
		if (StringUtils.isNotBlank(carryRule.getItemType1())) {
			sql += " and a.ItemType1= ? ";
			list.add(carryRule.getItemType1());
		}
		if (StringUtils.isNotBlank(carryRule.getItemType2())) {
			sql += " and a.ItemType2= ? ";
			list.add(carryRule.getItemType2());
		}
    	
    	if (StringUtils.isNotBlank(carryRule.getNo())) {
			sql += " and a.no like ? ";
			list.add("%"+carryRule.getNo()+"%");
		}  	
    	
    	if (StringUtils.isNotBlank(carryRule.getCarryType())) {
			sql += " and a.CarryType = ? ";
			list.add(carryRule.getCarryType());
		}
    	
    	if (StringUtils.isNotBlank(carryRule.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%"+carryRule.getRemarks()+"%");
		}
    	
    	if (StringUtils.isNotBlank(carryRule.getDistanceType())) {
			sql += " and a.DistanceType = ? ";
			list.add(carryRule.getDistanceType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//新增时候查询明细表
	public Page<Map<String, Object>> findPageBySqlDetailadd(
			Page<Map<String, Object>> page, CarryRuleFloor carryRuleFloor) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select a.No,a.BeginFloor,a.EndFloor,a.CardAmount,a.IncValue,a.LastUpdate," +
				" a.LastUpdatedBy,a.Expired,a.ActionLog from tCarryRuleFloor a "
				+ " where 1=1 and a.Expired='F' ";									  
		if (StringUtils.isNotBlank(carryRuleFloor.getNo())) {
			sql += " and a.No=? ";	
			list.add(carryRuleFloor.getNo());
		}else{
			sql +=" and a.No='' ";
		}
	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	//编辑楼层查询明细表
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, CarryRuleFloor carryRuleFloor) {
		List<Object> list = new ArrayList<Object>();

		String sql =  "  select a.No,a.BeginFloor,a.EndFloor,a.CardAmount,a.IncValue,a.LastUpdate," +
				" a.LastUpdatedBy,a.Expired,a.ActionLog from tCarryRuleFloor a "
		    	+ " where 1=1 and a.Expired='F' "; 									  					
		if (StringUtils.isNotBlank(carryRuleFloor.getNo())) {
			sql += " and a.No=? ";	
			list.add(carryRuleFloor.getNo());
		}else{
			sql +=" and a.No='' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//编辑材料类型3搬运费规则匹配材料查询明细表
		public Page<Map<String, Object>> findPageBySqlItem3(
				Page<Map<String, Object>> page, CarryRuleItem carryRuleItem) {
			List<Object> list = new ArrayList<Object>();
			String sql =  " select a.PK,a.No,a.ItemType3,b.descr ItemType3Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tCarryRuleItem a " +
					" left join tItemType3 b on a.ItemType3=b.Code  "
			    	+ " where 1=1 and a.Expired='F' "; 									  					
			if (StringUtils.isNotBlank(carryRuleItem.getNo())) {
				sql += " and a.No=? ";	
				list.add(carryRuleItem.getNo());
			}else{
				sql +=" and a.No='' ";
			}
			
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " order by a.No ";
			}
			return this.findPageBySql(page, sql, list.toArray());
		}

	@SuppressWarnings("unchecked") 
	public CarryRule getByNo(String No,String ItemType1, String ItemType2,
			String CarryType,String DistanceType, String sendType) {
		String hql = "from CarryRule a where a.expired='F' and a.No <> ? and a.itemType1=? and a.itemType2 =? and a.carryType=? and a.distanceType= ? and a.sendType = ? ";
		List<CarryRule> list = this.find(hql, new Object[]{No,ItemType1,ItemType2,CarryType,DistanceType,sendType});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public CarryRule getByNo2(String ItemType1, String ItemType2,
			String CarryType, String DistanceType, String sendType) {
		String hql = "from CarryRule a where a.expired='F' and a.itemType1=? and a.itemType2 =? and a.carryType=? and a.distanceType= ? and a.sendType = ? ";
		List<CarryRule> list = this.find(hql, new Object[]{ItemType1,ItemType2,CarryType,DistanceType,sendType});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public CarryRuleItem getByNo1(String fieldJson) {
		String hql = "from CarryRuleItem a where a.expired='F' and a.itemType3 in ? ";
		List<CarryRuleItem> list = this.find(hql, new Object[]{fieldJson});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
		
	
	@SuppressWarnings("deprecation")
	public Result docarryRuleReturnCheckOut(CarryRule carryRule) {
		Assert.notNull(carryRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCarryRule(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, carryRule.getM_umState());
			call.setString(2, carryRule.getNo());	
			call.setString(3, carryRule.getItemType1());
			call.setString(4, carryRule.getItemType2());
			call.setString(5, carryRule.getCarryType());
			call.setString(6, carryRule.getDistanceType());
			call.setString(7, carryRule.getRemarks());
			call.setTimestamp(8, new java.sql.Timestamp(carryRule.getLastUpdate().getTime()));
			call.setString(9, carryRule.getLastUpdatedBy());
			call.setString(10, carryRule.getExpired());
			call.setString(11, carryRule.getActionLog());
			call.setString(12, carryRule.getCalType());	
			call.setString(13, carryRule.getSendType());
			call.setString(14, carryRule.getItemAppsendDetailJson());
			call.setString(15, carryRule.getSalesInvoiceDetailJson());

			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			
			call.setString(18, carryRule.getSupplCode());
			call.setString(19, carryRule.getwHCode());
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

