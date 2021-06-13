package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActTaskinst;

@SuppressWarnings("serial")
@Repository
public class ActTaskinstDao extends BaseDao {

	/**
	 * ActTaskinst分页信息
	 * 
	 * @param page
	 * @param actTaskinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActTaskinst actTaskinst) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_TASKINST a where 1=1 ";

    	if (StringUtils.isNotBlank(actTaskinst.getId())) {
			sql += " and a.ID_=? ";
			list.add(actTaskinst.getId());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actTaskinst.getProcDefId());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getTaskDefKey())) {
			sql += " and a.TASK_DEF_KEY_=? ";
			list.add(actTaskinst.getTaskDefKey());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actTaskinst.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actTaskinst.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getParentTaskId())) {
			sql += " and a.PARENT_TASK_ID_=? ";
			list.add(actTaskinst.getParentTaskId());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actTaskinst.getName());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getDescription())) {
			sql += " and a.DESCRIPTION_=? ";
			list.add(actTaskinst.getDescription());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getOwner())) {
			sql += " and a.OWNER_=? ";
			list.add(actTaskinst.getOwner());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getAssignee())) {
			sql += " and a.ASSIGNEE_=? ";
			list.add(actTaskinst.getAssignee());
		}
    	if (actTaskinst.getStartTime() != null) {
			sql += " and a.START_TIME_=? ";
			list.add(actTaskinst.getStartTime());
		}
    	if (actTaskinst.getClaimTime() != null) {
			sql += " and a.CLAIM_TIME_=? ";
			list.add(actTaskinst.getClaimTime());
		}
    	if (actTaskinst.getEndTime() != null) {
			sql += " and a.END_TIME_=? ";
			list.add(actTaskinst.getEndTime());
		}
    	if (actTaskinst.getDuration() != null) {
			sql += " and a.DURATION_=? ";
			list.add(actTaskinst.getDuration());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getDeleteReason())) {
			sql += " and a.DELETE_REASON_=? ";
			list.add(actTaskinst.getDeleteReason());
		}
    	if (actTaskinst.getPriority() != null) {
			sql += " and a.PRIORITY_=? ";
			list.add(actTaskinst.getPriority());
		}
    	if (actTaskinst.getDueDate() != null) {
			sql += " and a.DUE_DATE_=? ";
			list.add(actTaskinst.getDueDate());
		}
    	if (StringUtils.isNotBlank(actTaskinst.getFormKey())) {
			sql += " and a.FORM_KEY_=? ";
			list.add(actTaskinst.getFormKey());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findByProcInstId(Page<Map<String,Object>> page, String procInstId) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select d.ID_,d.PROC_DEF_ID_,d.NAME_,d.TASK_DEF_KEY_,d.ASSIGNEE_,d.start_time_,d.end_time_,"
				+"b.USER_ID,b.APPLY_TIME,b.status,b.PROC_INST_ID_,b.finish_time,def.VERSION_,def.key_,def.SUSPENSION_STATE_,"
				+"tc.zwxm,def.Name_ procName,xt.Note statusDescr,tc2.zwxm assigneeName "
				+"from OA_All b "
				+"left join ACT_HI_TASKINST d on b.PROC_INST_ID_=d.PROC_INST_ID_ "
				+"left join tczybm tc on tc.czybh=b.user_id "
				+"left join tczybm tc2 on tc2.czybh=d.ASSIGNEE_ "
				+"left join ACT_RE_PROCDEF def on d.PROC_DEF_ID_=def.ID_ "
				+"left join txtdm xt on b.status=xt.cbm and xt.id='OAALLSTATUS' "
				+"where b.PROC_INST_ID_=? ";

		sql += ") a where 1=1 ";
		list.add(procInstId);
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by start_time_";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

}

