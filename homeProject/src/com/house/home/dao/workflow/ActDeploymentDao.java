package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActDeployment;

@SuppressWarnings("serial")
@Repository
public class ActDeploymentDao extends BaseDao {

	/**
	 * ActDeployment分页信息
	 * 
	 * @param page
	 * @param actDeployment
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActDeployment actDeployment) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_RE_DEPLOYMENT a where 1=1 ";

    	if (StringUtils.isNotBlank(actDeployment.getId())) {
			sql += " and a.ID_=? ";
			list.add(actDeployment.getId());
		}
    	if (StringUtils.isNotBlank(actDeployment.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actDeployment.getName());
		}
    	if (StringUtils.isNotBlank(actDeployment.getCategory())) {
			sql += " and a.CATEGORY_=? ";
			list.add(actDeployment.getCategory());
		}
    	if (actDeployment.getDeployTime() != null) {
			sql += " and a.DEPLOY_TIME_=? ";
			list.add(actDeployment.getDeployTime());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

