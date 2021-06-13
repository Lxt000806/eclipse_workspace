package com.house.home.dao.commi;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.DesignCommiFloatRuleDetail;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class DesignCommiFloatRuleDetailDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DesignCommiFloatRuleDetail designCommiFloatRuleDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select Pk, FloatRulePK, Remarks, CardinalFrom, CardinalTo, CommiPer, " +
				" LastUpdate, LastUpdatedBy, Expired, ActionLog " +
				" from tDesignCommiFloatRuleDetail a " +
				" where 1=1 ";
    	
		if(designCommiFloatRuleDetail.getFloatRulePK() != null){
			sql+=" and a.FloatRulePk = ? ";
			list.add(designCommiFloatRuleDetail.getFloatRulePK());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk"; 
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}
