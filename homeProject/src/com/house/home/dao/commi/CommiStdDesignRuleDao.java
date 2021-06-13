package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiBasePersonal;
import com.house.home.entity.commi.CommiStdDesignRule;

@SuppressWarnings("serial")
@Repository
public class CommiStdDesignRuleDao extends BaseDao {

	/**
	 * CommiStdDesignRule分页信息
	 * 
	 * @param page
	 * @param commiStdDesignRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiStdDesignRule commiStdDesignRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK, a.Descr, a.StdDesignFeeAmount, a.StdDesignFeePrice," +
				" a.LastUpdate, a.LastUpdatedBy,a.Expired,a.ActionLog " +
				" from tCommiStdDesignRule a where 1=1  ";

    	if (StringUtils.isNotBlank(commiStdDesignRule.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(commiStdDesignRule.getDescr());
		}
    	
		if (StringUtils.isBlank(commiStdDesignRule.getExpired()) || "F".equals(commiStdDesignRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by  a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by  a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean checkExistDescr(CommiStdDesignRule commiStdDesignRule) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		if("A".equals(commiStdDesignRule.getM_umState())){
			sql = "select 1 from tCommiStdDesignRule where descr =? ";
			list.add(commiStdDesignRule.getDescr());
		}else {
			sql = "select 1 from tCommiStdDesignRule where descr =? and pk<>? ";
			list.add(commiStdDesignRule.getDescr());
			list.add(commiStdDesignRule.getPk());
		}
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if (list1!=null && list1.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkExistDrawFeeStdRuleByCommiStdDesignRulePK(Integer commiStdDesignRulePK) {
		String sql = "select 1 from tDrawFeeStdRule where CommiStdDesignRulePK =?  ";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] { commiStdDesignRulePK });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	

}

