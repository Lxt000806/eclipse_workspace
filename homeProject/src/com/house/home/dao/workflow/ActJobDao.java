package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActJob;

@SuppressWarnings("serial")
@Repository
public class ActJobDao extends BaseDao {

	/**
	 * ActJob分页信息
	 * 
	 * @param page
	 * @param actJob
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActJob actJob) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_RU_JOB a where 1=1 ";

    	if (StringUtils.isNotBlank(actJob.getId())) {
			sql += " and a.ID_=? ";
			list.add(actJob.getId());
		}
    	if (actJob.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actJob.getRev());
		}
    	if (StringUtils.isNotBlank(actJob.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actJob.getType());
		}
    	if (actJob.getLockExpTime() != null) {
			sql += " and a.LOCK_EXP_TIME_=? ";
			list.add(actJob.getLockExpTime());
		}
    	if (StringUtils.isNotBlank(actJob.getLockOwner())) {
			sql += " and a.LOCK_OWNER_=? ";
			list.add(actJob.getLockOwner());
		}
    	if (actJob.getExclusive() != null) {
			sql += " and a.EXCLUSIVE_=? ";
			list.add(actJob.getExclusive());
		}
    	if (StringUtils.isNotBlank(actJob.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actJob.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actJob.getProcessInstanceId())) {
			sql += " and a.PROCESS_INSTANCE_ID_=? ";
			list.add(actJob.getProcessInstanceId());
		}
    	if (StringUtils.isNotBlank(actJob.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actJob.getProcDefId());
		}
    	if (actJob.getRetries() != null) {
			sql += " and a.RETRIES_=? ";
			list.add(actJob.getRetries());
		}
    	if (StringUtils.isNotBlank(actJob.getExceptionStackId())) {
			sql += " and a.EXCEPTION_STACK_ID_=? ";
			list.add(actJob.getExceptionStackId());
		}
    	if (StringUtils.isNotBlank(actJob.getExceptionMsg())) {
			sql += " and a.EXCEPTION_MSG_=? ";
			list.add(actJob.getExceptionMsg());
		}
    	if (actJob.getDuedate() != null) {
			sql += " and a.DUEDATE_=? ";
			list.add(actJob.getDuedate());
		}
    	if (StringUtils.isNotBlank(actJob.getRepeat())) {
			sql += " and a.REPEAT_=? ";
			list.add(actJob.getRepeat());
		}
    	if (StringUtils.isNotBlank(actJob.getHandlerType())) {
			sql += " and a.HANDLER_TYPE_=? ";
			list.add(actJob.getHandlerType());
		}
    	if (StringUtils.isNotBlank(actJob.getHandlerCfg())) {
			sql += " and a.HANDLER_CFG_=? ";
			list.add(actJob.getHandlerCfg());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

