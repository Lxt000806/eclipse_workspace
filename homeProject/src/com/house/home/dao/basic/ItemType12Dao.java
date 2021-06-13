package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
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
import com.house.home.entity.basic.ItemType12;

@SuppressWarnings("serial")
@Repository
public class ItemType12Dao extends BaseDao {

	/**
	 * ItemType12分页信息
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType12 itemType12) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.Code,a.ItemType1,it1.Descr ItemType1Descr,a.Descr,a.LastUpdatedBy,a.LastUpdate,"
					+ " a.ActionLog,a.Expired,a.DispSeq,a.ProPer,b.Descr installFeeTypeDescr,c.Descr transFeeTypeDescr " 
					+ " from tItemType12 a "
					+ " left join tItemType1 it1 on it1.Code=a.ItemType1 "
					+ " left join tLaborFeeType b on b.Code=a.installFeeType "
					+ " left join tLaborFeeType c on c.Code=a.transFeeType "
					+ " where 1=1 and a.Expired='F' ";							
		
    	if (StringUtils.isNotBlank(itemType12.getItemType1())) {
			sql += " and a.ItemType1= ? ";
			list.add(itemType12.getItemType1());
		}
    	    	
    	if (StringUtils.isNotBlank(itemType12.getDescr())) {
			sql += " and a.descr like ? ";
			list.add("%"+itemType12.getDescr()+"%");
		}   
    	
    	if (StringUtils.isNotBlank(itemType12.getCode())) {
			sql += " and a.code like ? ";
			list.add("%"+itemType12.getCode()+"%");
		} 
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.dispseq asc,a.code asc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

	@SuppressWarnings("unchecked") 
	public ItemType12 getByDescr(String descr) {
		String hql = "from ItemType12 a where a.expired='F' and   a.descr=? ";
		List<ItemType12> list = this.find(hql, new Object[]{descr});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked") 
	public ItemType12 getByDescr1(String descr,String descr1) {
		String hql = "from ItemType12 a where a.expired='F' and   a.descr=?  and a.descr!=?";
		List<ItemType12> list = this.find(hql, new Object[]{descr,descr1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	@SuppressWarnings("deprecation")
	public Result doitemtype12ReturnCheckOut(ItemType12 itemType12) {
		Assert.notNull(itemType12);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemType12(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemType12.getM_umState());
			call.setString(2, itemType12.getCode());			
			call.setString(3, itemType12.getItemType1());
			call.setString(4, itemType12.getDescr());
			call.setString(5, itemType12.getLastUpdatedBy());
			call.setTimestamp(6, new java.sql.Timestamp(itemType12.getLastUpdate().getTime()));
			call.setString(7, itemType12.getActionLog());	
			call.setString(8, itemType12.getExpired());
			call.setInt(9, itemType12.getDispseq());
			call.setFloat(10, itemType12.getProper());					
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.setString(13, itemType12.getInstallFeeType());
			call.setString(14, itemType12.getTransFeeType());
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
	//联动
	public List<Map<String,Object>> findItemByType12(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType12 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
}

