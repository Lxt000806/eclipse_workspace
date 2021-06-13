package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActIdentityLink;

@SuppressWarnings("serial")
@Repository
public class ActIdentityLinkDao extends BaseDao {

	/**
	 * ActIdentityLink分页信息
	 * 
	 * @param page
	 * @param actIdentityLink
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActIdentityLink actIdentityLink) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_RU_IDENTITYLINK a where 1=1 ";

    	if (StringUtils.isNotBlank(actIdentityLink.getId())) {
			sql += " and a.ID_=? ";
			list.add(actIdentityLink.getId());
		}
    	if (actIdentityLink.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actIdentityLink.getRev());
		}
    	if (StringUtils.isNotBlank(actIdentityLink.getGroupId())) {
			sql += " and a.GROUP_ID_=? ";
			list.add(actIdentityLink.getGroupId());
		}
    	if (StringUtils.isNotBlank(actIdentityLink.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actIdentityLink.getType());
		}
    	if (StringUtils.isNotBlank(actIdentityLink.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(actIdentityLink.getUserId());
		}
    	if (StringUtils.isNotBlank(actIdentityLink.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actIdentityLink.getTaskId());
		}
    	if (StringUtils.isNotBlank(actIdentityLink.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actIdentityLink.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actIdentityLink.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actIdentityLink.getProcDefId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

