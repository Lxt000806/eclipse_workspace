package com.house.home.dao.commi;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.commi.DesignCommiFloatRule;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class DesignCommiFloatRuleDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DesignCommiFloatRule designCommiFloatRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select PK, Descr, Remarks, LastUpdate, LastUpdatedBy, Expired, ActionLog " +
				" from tDesignCommiFloatRule a " +
				" where 1=1 ";
    	
		if(StringUtils.isNotBlank(designCommiFloatRule.getDescr())){
			sql+=" and a.descr like ? ";
			list.add("%"+designCommiFloatRule.getDescr()+"%");
		}
		
		if (StringUtils.isBlank(designCommiFloatRule.getExpired())
				|| "F".equals(designCommiFloatRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(DesignCommiFloatRule designCommiFloatRule) {
		Assert.notNull(designCommiFloatRule);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDesignCommiFloatRule(?,?,?,?,?,?,?,?)}");
			call.setString(1, designCommiFloatRule.getM_umState());
			call.setInt(2, designCommiFloatRule.getPk()==null? 0:designCommiFloatRule.getPk());
			call.setString(3, designCommiFloatRule.getDescr());
			call.setString(4, designCommiFloatRule.getRemarks());
			call.setString(5, designCommiFloatRule.getLastUpdatedBy());
			call.setString(6, designCommiFloatRule.getDetailXml());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR); 
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public List<Map<String, Object>> getFloatRuleSelection(){
		
		String sql = "select PK, Descr from tDesignCommiFloatRule where expired = 'F' ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		
		return list;
	}
}
