package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActEventSubscr;

@SuppressWarnings("serial")
@Repository
public class ActEventSubscrDao extends BaseDao {

	/**
	 * ActEventSubscr分页信息
	 * 
	 * @param page
	 * @param actEventSubscr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActEventSubscr actEventSubscr) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_RU_EVENT_SUBSCR a where 1=1 ";

    	if (StringUtils.isNotBlank(actEventSubscr.getId())) {
			sql += " and a.ID_=? ";
			list.add(actEventSubscr.getId());
		}
    	if (actEventSubscr.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actEventSubscr.getRev());
		}
    	if (StringUtils.isNotBlank(actEventSubscr.getEventType())) {
			sql += " and a.EVENT_TYPE_=? ";
			list.add(actEventSubscr.getEventType());
		}
    	if (StringUtils.isNotBlank(actEventSubscr.getEventName())) {
			sql += " and a.EVENT_NAME_=? ";
			list.add(actEventSubscr.getEventName());
		}
    	if (StringUtils.isNotBlank(actEventSubscr.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actEventSubscr.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actEventSubscr.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actEventSubscr.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actEventSubscr.getActivityId())) {
			sql += " and a.ACTIVITY_ID_=? ";
			list.add(actEventSubscr.getActivityId());
		}
    	if (StringUtils.isNotBlank(actEventSubscr.getConfiguration())) {
			sql += " and a.CONFIGURATION_=? ";
			list.add(actEventSubscr.getConfiguration());
		}
    	if (actEventSubscr.getCreated() != null) {
			sql += " and a.CREATED_=? ";
			list.add(actEventSubscr.getCreated());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

