package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActIdentityLinkHi;

@SuppressWarnings("serial")
@Repository
public class ActIdentityLinkHiDao extends BaseDao {

	/**
	 * ActIdentityLinkHi分页信息
	 * 
	 * @param page
	 * @param actIdentityLinkHi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActIdentityLinkHi actIdentityLinkHi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_IDENTITYLINK a where 1=1 ";

    	if (StringUtils.isNotBlank(actIdentityLinkHi.getId())) {
			sql += " and a.ID_=? ";
			list.add(actIdentityLinkHi.getId());
		}
    	if (StringUtils.isNotBlank(actIdentityLinkHi.getGroupId())) {
			sql += " and a.GROUP_ID_=? ";
			list.add(actIdentityLinkHi.getGroupId());
		}
    	if (StringUtils.isNotBlank(actIdentityLinkHi.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actIdentityLinkHi.getType());
		}
    	if (StringUtils.isNotBlank(actIdentityLinkHi.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(actIdentityLinkHi.getUserId());
		}
    	if (StringUtils.isNotBlank(actIdentityLinkHi.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actIdentityLinkHi.getTaskId());
		}
    	if (StringUtils.isNotBlank(actIdentityLinkHi.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actIdentityLinkHi.getProcInstId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

