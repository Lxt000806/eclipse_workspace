package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActMemberShip;

@SuppressWarnings("serial")
@Repository
public class ActMemberShipDao extends BaseDao {

	/**
	 * ActMemberShip分页信息
	 * 
	 * @param page
	 * @param actMemberShip
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActMemberShip actMemberShip) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_ID_MEMBERSHIP a where 1=1 ";

    	if (StringUtils.isNotBlank(actMemberShip.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(actMemberShip.getUserId());
		}
    	if (StringUtils.isNotBlank(actMemberShip.getGroupId())) {
			sql += " and a.GROUP_ID_=? ";
			list.add(actMemberShip.getGroupId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.UserId";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

