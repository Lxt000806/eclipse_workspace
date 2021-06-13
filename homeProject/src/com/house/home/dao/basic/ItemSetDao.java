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
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;

@SuppressWarnings("serial")
@Repository
public class ItemSetDao extends BaseDao {

	/**
	 * ItemSet分页信息
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSet itemSet) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.No,a.Descr,a.ItemType1,it1.Descr ItemType1Descr,a.CustType,"
				    + " (case when ct.Desc1 is NULL then '' ELSE ct.Desc1 end) CustTypeDescr,a.Remarks,"
		            + " a.IsOutSet, b.NOTE IsOutSetDescr, "
					+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+ " from tItemSet a "
					+ " left join tCusttype ct on a.CustType=ct.Code "
					+ " left join tItemType1 it1 on it1.Code=a.ItemType1 "
					+ " left join tXTDM b on b.ID = 'YESNO' and a.IsOutSet = b.CBM "
					+ " where 1=1 ";
		
    	if (StringUtils.isNotBlank(itemSet.getItemType1())) {
			sql += " and a.ItemType1= ? ";
			list.add(itemSet.getItemType1());
		}
    	
    	if (StringUtils.isNotBlank(itemSet.getNo())) {
			sql += " and a.no like ? ";
			list.add("%"+itemSet.getNo()+"%");
		}  	
    	if (StringUtils.isNotBlank(itemSet.getDescr())) {
			sql += " and a.descr like ? ";
			list.add("%"+itemSet.getDescr()+"%");
		}
    	
        if (StringUtils.isBlank(itemSet.getExpired()) || "F".equals(itemSet.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//*新增时候查询明细表*/
	public Page<Map<String, Object>> findPageBySqlDetailadd(
			Page<Map<String, Object>> page, ItemSetDetail itemSetDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select a.No,a.ItemCode,b.descr itemcodedescr,a.UnitPrice,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
				+ " a.ActionLog from tItemSetDetail a left join tItem b on a.ItemCode=b.Code "
				+ " where 1=1 and a.Expired='F' ";									  
		if (StringUtils.isNotBlank(itemSetDetail.getNo())) {
			sql += " and a.No=? ";	
			list.add(itemSetDetail.getNo());
		}
	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	//*编辑时候查询明细表*/
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, ItemSetDetail itemSetDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.No,a.ItemCode,b.descr itemcodedescr,a.UnitPrice,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
					+ " a.ActionLog from tItemSetDetail a left join tItem b on a.ItemCode=b.Code"
					+ " where 1=1";									  
		if (StringUtils.isNotBlank(itemSetDetail.getNo())) {
			sql += " and a.No=? ";	
			list.add(itemSetDetail.getNo());
		}
	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked") 
	public ItemSet getByDescr(String descr) {
		String hql = "from ItemSet a where a.expired='F' and a.descr=? ";
		List<ItemSet> list = this.find(hql, new Object[]{descr});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public ItemSet getByDescr1(String descr, String descr1) {
		String hql = "from ItemSet a where a.expired='F' and a.descr=? and a.descr!=?";
		List<ItemSet> list = this.find(hql, new Object[]{descr,descr1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
		
	
	@SuppressWarnings("deprecation")
	public Result doitemsetReturnCheckOut(ItemSet itemSet) {
		Assert.notNull(itemSet);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemSet(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemSet.getM_umState());
			call.setString(2, itemSet.getNo());
			call.setString(3, itemSet.getDescr());
			call.setString(4, itemSet.getItemType1());
			call.setString(5, itemSet.getCustType());
			call.setString(6, itemSet.getRemarks());
			call.setTimestamp(7, new java.sql.Timestamp(itemSet.getLastUpdate().getTime()));
			call.setString(8, itemSet.getLastUpdatedBy());
			call.setString(9, itemSet.getExpired());
			call.setString(10, itemSet.getActionLog());			
			call.setString(11, itemSet.getItemDetailXml());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}

	public List<Map<String, Object>> findItemSetNo(ItemSet itemSet) {
//		String sql ="select rtrim(No) id,Descr name from tItemSet "
//	            +"where Expired='F' and ItemType1=? "
//	           +" and (CustType is null or CustType='' or CustType=?)   order by LastUpdate desc";   
//		return this.findBySql(sql, new Object[]{itemType1,custType});
		List<Object> list = new ArrayList<Object>();
		String sql="select rtrim(No) id,Descr name from tItemSet where Expired='F' ";
		if (StringUtils.isNotBlank(itemSet.getItemType1())) {
			sql += " and ItemType1 =? ";	
			list.add(itemSet.getItemType1());
		}
		if (StringUtils.isNotBlank(itemSet.getCustType())) {
			sql += " and (CustType is null or CustType='' or CustType=?) ";	
			list.add(itemSet.getCustType());
		}
		if (StringUtils.isNotBlank(itemSet.getIsOutSet())) {
			sql += " and IsOutSet=? ";	
			list.add(itemSet.getIsOutSet());
		}
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		return list1;
	}

	

}

