package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActInfo;

@SuppressWarnings("serial")
@Repository
public class ActInfoDao extends BaseDao {

	/**
	 * ActInfo分页信息
	 * 
	 * @param page
	 * @param actInfo
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActInfo actInfo) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_ID_INFO a where 1=1 ";

    	if (StringUtils.isNotBlank(actInfo.getId())) {
			sql += " and a.ID_=? ";
			list.add(actInfo.getId());
		}
    	if (actInfo.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actInfo.getRev());
		}
    	if (StringUtils.isNotBlank(actInfo.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(actInfo.getUserId());
		}
    	if (StringUtils.isNotBlank(actInfo.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actInfo.getType());
		}
    	if (StringUtils.isNotBlank(actInfo.getKey())) {
			sql += " and a.KEY_=? ";
			list.add(actInfo.getKey());
		}
    	if (StringUtils.isNotBlank(actInfo.getValue())) {
			sql += " and a.VALUE_=? ";
			list.add(actInfo.getValue());
		}
    	if (StringUtils.isNotBlank(actInfo.getParentId())) {
			sql += " and a.PARENT_ID_=? ";
			list.add(actInfo.getParentId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

