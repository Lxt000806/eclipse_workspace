package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.interceptor.SessionFactory;
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
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.BaseBatchTemp;
import com.house.home.entity.basic.ItemAppConfRule;
import com.house.home.entity.basic.ItemAppConfRuleDetail;

@SuppressWarnings("serial")
@Repository
public class ItemAppConfRuleDao extends BaseDao {

	/**
	 * ItemAppConfRule分页信息
	 * 
	 * @param page
	 * @param ItemAppConfRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,ItemAppConfRule itemAppConfRule,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select a.No, a.ItemType1, a.CustType,a.PayType,d.NOTE PayTypedescr,a.PayNum, a.PayPer, a.Prior, " + 
		   "a.DiffAmount, a.ItemCost, a.LastUpdate, a.LastUpdatedBy, a.Expired, " +
	       "a.ActionLog, b.descr ItemType1descr, c.desc1 Custtypedescr,a.remarks " +
	       "from tItemAppConfRule a " +
	       "left outer join tItemType1 b on b.code = a.ItemType1 " +
	       "left outer join txtdm d on a.PayType=d.CBM and d.ID='TIMEPAYTYPE' " +
	       "left outer join tCusttype c on c.code = a.CustType " +
	       "where 1=1 " ;
		String[] arr = uc.getItemRight().trim().split(",");
		String itemRight = "";
		for(String str:arr) itemRight +="'"+str+"',";
		System.out.println(arr+"\n"+"ss");
		sql+=" and a.itemType1 in ("+itemRight.substring(0,itemRight.length()-1)+") ";
		if (StringUtils.isNotBlank(itemAppConfRule.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppConfRule.getNo());
		}
		if (StringUtils.isNotBlank(itemAppConfRule.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(itemAppConfRule.getItemType1());
		}
		if (StringUtils.isNotBlank(itemAppConfRule.getCustType())) {
			sql += " and a.CustType=? ";
			list.add(itemAppConfRule.getCustType().toString());
		}
		if (StringUtils.isNotBlank(itemAppConfRule.getPayType())) {
			sql += " and a.PayType=? ";
			list.add(itemAppConfRule.getPayType());
		}
		if (StringUtils.isBlank(itemAppConfRule.getExpired()) || "F".equals(itemAppConfRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.lastUpDate desc ";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	//保存修改存储过程
	public Result doItemAppConfRuleForProc(ItemAppConfRule itemAppConfRule){
		Assert.notNull(itemAppConfRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call=null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlshgzGl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1,itemAppConfRule.getM_umState());
			call.setString(2,itemAppConfRule.getNo());
			call.setString(3,itemAppConfRule.getItemType1());
			call.setString(4,itemAppConfRule.getCustType());
			call.setString(5,itemAppConfRule.getPayNum());
			call.setDouble(6,itemAppConfRule.getPayPer()==null?0:itemAppConfRule.getPayPer());
			call.setDouble(7, itemAppConfRule.getPrior()==null?0:itemAppConfRule.getPrior());
			call.setDouble(8, itemAppConfRule.getDiffAmount()==null?0:itemAppConfRule.getDiffAmount());
			call.setDouble(9, itemAppConfRule.getItemCost()==null?0:itemAppConfRule.getItemCost());
			call.setString(10, itemAppConfRule.getRemarks());
			call.setString(11, itemAppConfRule.getExpired());
			call.setString(12, itemAppConfRule.getLastUpdatedBy());
			call.setString(13, itemAppConfRule.getPayType());
			call.setString(14, itemAppConfRule.getItemAppConfRuleJson());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
			System.out.println(itemAppConfRule.getItemAppConfRuleJson()+"SSSSD");
			call.execute();
			result.setCode(String.valueOf(call.getInt(15)));
			result.setInfo(call.getString(16));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}	
	//明细表查询
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,ItemAppConfRuleDetail itemAppConfRuleDetail) {
		String sql = "";
		List<Object> list = new ArrayList<Object>();
		 /*select  a.PK, a.No, a.ItemType2, a.ItemType3, a.ItemDesc, a.LastUpdate, '
         + ' a.LastUpdatedBy, a.Expired, a.ActionLog, b.descr ItemType2descr, '
         + ' c.descr ItemType3descr   '
         + ' from  #tItemAppConfRuleDetail a   '
         + ' left outer join tItemType2 b on b.code = a.ItemType2 '
         + ' left outer join tItemType3 c on c.code = a.ItemType3 
*/		
		 sql = "select * from (select a.PK, a.No, a.ItemType2, a.ItemType3, a.ItemDesc, a.LastUpdate, "
			 + " a.LastUpdatedBy, a.Expired, a.ActionLog, b.descr ItemType2desc, "
             + " c.descr ItemType3desc "
             + " from  tItemAppConfRuleDetail a "
             + " left outer join tItemType2 b on b.code = a.ItemType2 "
             + " left outer join tItemType3 c on c.code = a.ItemType3 "
             + " where 1=1 ";
		 if (StringUtils.isNotBlank(itemAppConfRuleDetail.getNo())) {
			 sql += " and a.No=? ";
			 list.add(itemAppConfRuleDetail.getNo());
		 }
		 if (StringUtils.isNotBlank(page.getPageOrderBy())){
			 sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
	 	 }else{
			 sql += ") a order by a.lastUpDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}

