package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ProjectManQltFeeWithHoldRule;

@SuppressWarnings("serial")
@Repository
public class ProjectManQltFeeWithHoldRuleDao extends BaseDao {

	/**
	 * ProjectManQltFeeWithHoldRule 分页信息
	 * @param page
	 * @param ProjectManQltFeeWithHoldRule 
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProjectManQltFeeWithHoldRule projectManQltFeeWithHoldRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.Type,a.QltFeeFrom,a.QltFeeTo ,a.CommiAmountFrom,a.CommiAmountTo,a.Amount,a.QltFeeLimitAmount,IsSupvr, "
			+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,x1.NOTE TypeDescr,X2.NOTE IsSupvrDescr "
			+" from tProjectManQltFeeWithHoldRule a " 
			+" left join tXTDM x1 on a.Type=x1.CBM and x1.ID='WKQLTFEETYPE' "
			+" left join tXTDM x2 on a.IsSupvr=x2.CBM and x2.ID='PRJMANTYPE' "
			+" where 1=1 ";

    	if (StringUtils.isNotBlank(projectManQltFeeWithHoldRule.getType())) {
			sql += " and a.Type=? ";
			list.add(projectManQltFeeWithHoldRule.getType());
		}
    	if (StringUtils.isNotBlank(projectManQltFeeWithHoldRule.getIsSupvr())) {
			sql += " and a.isSupvr=? ";
			list.add(projectManQltFeeWithHoldRule.getIsSupvr());
		}
		if (StringUtils.isBlank(projectManQltFeeWithHoldRule.getExpired()) || "F".equals(projectManQltFeeWithHoldRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Type,a.IsSupvr,a.QltFeeFrom,a.CommiAmountFrom";
		}	
		return this.findPageBySql(page, sql, list.toArray());
	}
}

