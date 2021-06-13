package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActExecution;

@SuppressWarnings("serial")
@Repository
public class ActExecutionDao extends BaseDao {

	/**
	 * ActExecution分页信息
	 * 
	 * @param page
	 * @param actExecution
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActExecution actExecution) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_RU_EXECUTION a where 1=1 ";

    	if (StringUtils.isNotBlank(actExecution.getId())) {
			sql += " and a.ID_=? ";
			list.add(actExecution.getId());
		}
    	if (actExecution.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actExecution.getRev());
		}
    	if (StringUtils.isNotBlank(actExecution.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actExecution.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actExecution.getBusinessKey())) {
			sql += " and a.BUSINESS_KEY_=? ";
			list.add(actExecution.getBusinessKey());
		}
    	if (StringUtils.isNotBlank(actExecution.getParentId())) {
			sql += " and a.PARENT_ID_=? ";
			list.add(actExecution.getParentId());
		}
    	if (StringUtils.isNotBlank(actExecution.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actExecution.getProcDefId());
		}
    	if (StringUtils.isNotBlank(actExecution.getSuperExec())) {
			sql += " and a.SUPER_EXEC_=? ";
			list.add(actExecution.getSuperExec());
		}
    	if (StringUtils.isNotBlank(actExecution.getActId())) {
			sql += " and a.ACT_ID_=? ";
			list.add(actExecution.getActId());
		}
    	if (actExecution.getIsActive() != null) {
			sql += " and a.IS_ACTIVE_=? ";
			list.add(actExecution.getIsActive());
		}
    	if (actExecution.getIsConcurrent() != null) {
			sql += " and a.IS_CONCURRENT_=? ";
			list.add(actExecution.getIsConcurrent());
		}
    	if (actExecution.getIsScope() != null) {
			sql += " and a.IS_SCOPE_=? ";
			list.add(actExecution.getIsScope());
		}
    	if (actExecution.getIsEventScope() != null) {
			sql += " and a.IS_EVENT_SCOPE_=? ";
			list.add(actExecution.getIsEventScope());
		}
    	if (actExecution.getSuspensionState() != null) {
			sql += " and a.SUSPENSION_STATE_=? ";
			list.add(actExecution.getSuspensionState());
		}
    	if (actExecution.getCachedEntState() != null) {
			sql += " and a.CACHED_ENT_STATE_=? ";
			list.add(actExecution.getCachedEntState());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

