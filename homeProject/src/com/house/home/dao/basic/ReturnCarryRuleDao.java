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
import com.house.home.entity.basic.ReturnCarryRule;

@SuppressWarnings("serial")
@Repository
public class ReturnCarryRuleDao extends BaseDao {

	/**
	 * ItemType12分页信息
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ReturnCarryRule returnCarryRule) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.No,a.BeginValue,a.EndValue,a.PriceType,x1.Note PriceTypeDescr,a.Price,a.Remarks,a.LastUpdate," +
					  " a.LastUpdatedBy,a.Expired,a.ActionLog,a.cardAmount,a.incValue,x2.note calTypeDescr from tReturnCarryRule a " +
					  " left join tXtdm x1 on a.PriceType=x1.CBM and x1.ID='PriceType' " +
					  " left join tXtdm x2 on a.calType=x2.CBM and x2.ID='ReCarryCalType' " +
					  " where 1=1 and a.Expired='F' ";						
		    	    	   
		if (StringUtils.isNotBlank(returnCarryRule.getNo())) {
			sql += " and a.no like ? ";
			list.add("%"+returnCarryRule.getNo()+"%");
		}
		
		if (StringUtils.isNotBlank(returnCarryRule.getPriceType())) {
			sql += " and a.pricetype= ? ";
			list.add(returnCarryRule.getPriceType());
		}
		
		if (returnCarryRule.getBeginValue() != ' ') {
			sql += " and a.BeginValue >= ? ";
			list.add(returnCarryRule.getBeginValue());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.lastupdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String,Object>> findItemPageBySql(Page<Map<String,Object>> page, String no) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.no,a.itemtype2,a.itemtype3,a.LastUpdate," +
					  " a.LastUpdatedBy,a.Expired,a.ActionLog,b.Descr itemtype2desc,b.itemType1,c.Descr itemtype3desc " +
					  " from tReturnCarryRuleItem a " +
					  " left join tItemType2 b on a.itemType2 = b.code" +
					  " left join tItemType3 c on a.itemType3 = c.code " +
					  " where 1=1 ";						   
		if (StringUtils.isNotBlank(no)) {
			sql += " and a.no=? ";
			list.add(no);
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.lastupdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	

	private CharSequence parseString(float beginValue) {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unchecked") 
	public ReturnCarryRule getByNo(String No,String No1) {
		String hql = "from ReturnCarryRule a where a.expired='F' and  a.No=? and a.No != ? ";
		List<ReturnCarryRule> list = this.find(hql, new Object[]{No,No1});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	

	
	@SuppressWarnings("deprecation")
	public Result doReturnCarryRuleReturnCheckOut(ReturnCarryRule returnCarryRule) {
		Assert.notNull(returnCarryRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pReturnCarryRule(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, returnCarryRule.getM_umState());
			call.setString(2, returnCarryRule.getNo());			
			call.setFloat(3, returnCarryRule.getBeginValue());
			call.setFloat(4, returnCarryRule.getEndValue());
			call.setString(5, returnCarryRule.getPriceType());
			call.setFloat(6, returnCarryRule.getPrice());		
			call.setString(7, returnCarryRule.getRemarks());
			call.setTimestamp(8, new java.sql.Timestamp(returnCarryRule.getLastUpdate().getTime()));
			call.setString(9, returnCarryRule.getLastUpdatedBy());
			call.setString(10, returnCarryRule.getExpired());
			call.setString(11, returnCarryRule.getActionLog());		
			call.setFloat(12, returnCarryRule.getCardAmount());
			call.setFloat(13, returnCarryRule.getIncValue());
			call.setString(14, returnCarryRule.getCalType());	
			call.setString(15, returnCarryRule.getReturnCarryRuleDetailJson());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
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

