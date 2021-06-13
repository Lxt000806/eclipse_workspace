package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActTask;

@SuppressWarnings("serial")
@Repository
public class ActTaskDao extends BaseDao {

	/**
	 * ActTask分页信息
	 * 
	 * @param page
	 * @param actTask
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActTask actTask) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.ID_,a.PROC_DEF_ID_,a.NAME_,a.TASK_DEF_KEY_,a.ASSIGNEE_,a.CREATE_TIME_,b.id leaveId,"
			+"b.LEAVE_TYPE,b.USER_ID,b.APPLY_TIME,b.START_TIME,b.END_TIME,c.VERSION_,c.key_,c.SUSPENSION_STATE_ "
			+"from ACT_RU_TASK a "
			+"inner join OA_LEAVE b on a.PROC_INST_ID_=b.PROCESS_INSTANCE_ID "
			+"inner join ACT_RE_PROCDEF c on a.PROC_DEF_ID_=c.ID_ "
			+"inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = a.ID_ "
			+"where I.TYPE_ = 'candidate' ";

		if (StringUtils.isNotBlank(actTask.getAssignee())) {
			sql += " and (a.ASSIGNEE_=? or a.ASSIGNEE_ is null) "
				+"and ( I.USER_ID_=? or I.GROUP_ID_ IN (select GROUP_ID_ from ACT_ID_MEMBERSHIP where USER_ID_=?) )";
			list.add(actTask.getAssignee());
			list.add(actTask.getAssignee());
			list.add(actTask.getAssignee());
		}else{
			return null;
		}
    	if (StringUtils.isNotBlank(actTask.getProcDefKey())) {
			sql += " and c.key_=? ";
			list.add(actTask.getProcDefKey());
		}else{
			return null;
		}
    	if (StringUtils.isNotBlank(actTask.getId())) {
			sql += " and a.ID_=? ";
			list.add(actTask.getId());
		}
    	if (actTask.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actTask.getRev());
		}
    	if (StringUtils.isNotBlank(actTask.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actTask.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actTask.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actTask.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actTask.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actTask.getProcDefId());
		}
    	if (StringUtils.isNotBlank(actTask.getName())) {
			sql += " and a.NAME_ like ? ";
			list.add("%"+actTask.getName()+"%");
		}
    	if (StringUtils.isNotBlank(actTask.getParentTaskId())) {
			sql += " and a.PARENT_TASK_ID_=? ";
			list.add(actTask.getParentTaskId());
		}
    	if (StringUtils.isNotBlank(actTask.getDescription())) {
			sql += " and a.DESCRIPTION_=? ";
			list.add(actTask.getDescription());
		}
    	if (StringUtils.isNotBlank(actTask.getTaskDefKey())) {
			sql += " and a.TASK_DEF_KEY_=? ";
			list.add(actTask.getTaskDefKey());
		}
    	if (StringUtils.isNotBlank(actTask.getOwner())) {
			sql += " and a.OWNER_=? ";
			list.add(actTask.getOwner());
		}
    	if (StringUtils.isNotBlank(actTask.getDelegation())) {
			sql += " and a.DELEGATION_=? ";
			list.add(actTask.getDelegation());
		}
    	if (actTask.getPriority() != null) {
			sql += " and a.PRIORITY_=? ";
			list.add(actTask.getPriority());
		}
    	if (actTask.getCreateTime() != null) {
			sql += " and a.CREATE_TIME_=? ";
			list.add(actTask.getCreateTime());
		}
    	if (actTask.getDueDate() != null) {
			sql += " and a.DUE_DATE_=? ";
			list.add(actTask.getDueDate());
		}
    	if (actTask.getSuspensionState() != null) {
			sql += " and a.SUSPENSION_STATE_=? ";
			list.add(actTask.getSuspensionState());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by id_ desc,create_time_ desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public ActTask getByProcInstId(String procInstId) {
		String hql = "from ActTask where procInstId=?";
		List<ActTask> list = this.find(hql, new Object[]{procInstId});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String, Object>> findTodoTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		List<Object> list = new ArrayList<Object>();
		
    	if (StringUtils.isBlank(actTask.getAssignee())) {
			return null;
		}
    	return null;
		/*String sql = "select * from (select a.ID_,a.PROC_DEF_ID_,a.NAME_,a.TASK_DEF_KEY_,a.ASSIGNEE_,a.CREATE_TIME_,"
				+"b.USER_ID,b.APPLY_TIME,b.status,c.VERSION_,c.key_,c.SUSPENSION_STATE_,b.PROC_INST_ID_ PROCESS_INSTANCE_ID,"
				+"tc.zwxm,c.Name_ ProcName,xt.Note statusDescr,tc2.zwxm assigneeName "
				+"from ACT_RU_TASK a "
				+"inner join OA_All b on a.PROC_INST_ID_=b.PROC_INST_ID_ "
				+"inner join ACT_RE_PROCDEF c on a.PROC_DEF_ID_=c.ID_ "
				+"left join tczybm tc on tc.czybh=b.user_id "
				+"left join tczybm tc2 on tc2.czybh=a.ASSIGNEE_ "
				+"left join txtdm xt on b.status=xt.cbm and xt.id='OAALLSTATUS' "
				+"where a.ASSIGNEE_=? and c.SUSPENSION_STATE_=1 union "
				+"select a.ID_,a.PROC_DEF_ID_,a.NAME_,a.TASK_DEF_KEY_,a.ASSIGNEE_,a.CREATE_TIME_,"
				+"b.USER_ID,b.APPLY_TIME,b.status,c.VERSION_,c.key_,c.SUSPENSION_STATE_,b.PROC_INST_ID_ PROCESS_INSTANCE_ID,"
				+"tc.zwxm,c.Name_ ProcName,xt.Note statusDescr,tc2.zwxm assigneeName "
				+"from ACT_RU_TASK a "
				+"inner join OA_All b on a.PROC_INST_ID_=b.PROC_INST_ID_ "
				+"inner join ACT_RE_PROCDEF c on a.PROC_DEF_ID_=c.ID_ "
				+"inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = a.ID_ "
				+"left join tczybm tc on tc.czybh=b.user_id "
				+"left join tczybm tc2 on tc2.czybh=a.ASSIGNEE_ "
				+"left join txtdm xt on b.status=xt.cbm and xt.id='OAALLSTATUS' "
				+"where a.ASSIGNEE_ is null and c.SUSPENSION_STATE_=1 and I.TYPE_='candidate' "
				+"and (I.USER_ID_=? or I.GROUP_ID_ IN (select GROUP_ID_ from ACT_ID_MEMBERSHIP where USER_ID_=?) ) ";

		list.add(actTask.getAssignee());
		list.add(actTask.getAssignee());
		list.add(actTask.getAssignee());
		sql += ") a where 1=1 ";
		
		//不能审批自己，可以重新申请
		sql += "and (a.USER_ID<>? or a.status='3') ";
		list.add(actTask.getAssignee());
		
		if (StringUtils.isNotBlank(actTask.getUserName())) {
			sql += " and a.zwxm like ? ";
			list.add("%"+actTask.getUserName()+"%");
		}
		if (StringUtils.isNotBlank(actTask.getUserId())) {
			sql += " and a.user_id=? ";
			list.add(actTask.getUserId());
		}
		if (StringUtils.isNotBlank(actTask.getProcName())) {
			sql += " and a.procName like ? ";
			list.add("%"+actTask.getProcName()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by create_time_ desc";
		}

		return this.findPageBySql(page, sql, list.toArray());*/
	}

	public Page<Map<String, Object>> findFinishedTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		List<Object> list = new ArrayList<Object>();
    	if (StringUtils.isBlank(actTask.getAssignee())) {
			return null;
		}
		String sql = "select * from (select c.ID_,c.PROC_DEF_ID_,d.NAME_,d.TASK_DEF_KEY_,d.ASSIGNEE_,d.create_time_,"
				+"b.USER_ID,b.APPLY_TIME,b.status,b.PROC_INST_ID_,def.VERSION_,def.key_,def.SUSPENSION_STATE_,"
				+"tc.zwxm,def.Name_ procName,xt.Note statusDescr,tc2.zwxm assigneeName "
				+"from OA_All b "
				+"inner join (select max(ID_) ID_,PROC_INST_ID_,PROC_DEF_ID_ from ACT_HI_TASKINST where ASSIGNEE_=? "
				+"group by PROC_INST_ID_,PROC_DEF_ID_) c on b.PROC_INST_ID_=c.PROC_INST_ID_ "
				+"left join ACT_RU_TASK d on c.PROC_INST_ID_=d.PROC_INST_ID_ "
				+"left join tczybm tc on tc.czybh=b.user_id "
				+"left join tczybm tc2 on tc2.czybh=d.ASSIGNEE_ "
				+"left join ACT_RE_PROCDEF def on c.PROC_DEF_ID_=def.ID_ "
				+"left join txtdm xt on b.status=xt.cbm and xt.id='OAALLSTATUS' ";

		list.add(actTask.getAssignee());
		sql += ") a where 1=1 ";
		if (StringUtils.isNotBlank(actTask.getProcName())) {
			sql += " and a.procName like ? ";
			list.add("%"+actTask.getProcName()+"%");
		}
		if (actTask.getDateFrom() != null) {
			sql += " and a.APPLY_TIME>=CONVERT(VARCHAR(10),?,120) ";
			list.add(actTask.getDateFrom());
		}
		if (actTask.getDateTo() != null) {
			sql += " and a.APPLY_TIME<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(actTask.getDateTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by APPLY_TIME desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findMyTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		List<Object> list = new ArrayList<Object>();
    	if (StringUtils.isBlank(actTask.getUserId())) {
			return null;
		}
    	String sql = "select * from (select c.ID_,c.PROC_DEF_ID_,d.NAME_,d.TASK_DEF_KEY_,d.ASSIGNEE_,d.create_time_,"
				+"b.USER_ID,b.APPLY_TIME,b.status,b.PROC_INST_ID_,b.finish_time,def.VERSION_,def.key_,def.SUSPENSION_STATE_,"
				+"tc.zwxm,def.Name_ procName,xt.Note statusDescr,tc2.zwxm assigneeName "
				+"from OA_All b "
				+"inner join (select max(ID_) ID_,PROC_INST_ID_,PROC_DEF_ID_ from ACT_HI_TASKINST "
				+"group by PROC_INST_ID_,PROC_DEF_ID_) c on b.PROC_INST_ID_=c.PROC_INST_ID_ "
				+"left join ACT_RU_TASK d on c.PROC_INST_ID_=d.PROC_INST_ID_ "
				+"left join tczybm tc on tc.czybh=b.user_id "
				+"left join tczybm tc2 on tc2.czybh=d.ASSIGNEE_ "
				+"left join ACT_RE_PROCDEF def on c.PROC_DEF_ID_=def.ID_ "
				+"left join txtdm xt on b.status=xt.cbm and xt.id='OAALLSTATUS' "
				+"where b.user_id=? ";

		list.add(actTask.getUserId());
		sql += ") a where 1=1 ";
		if (StringUtils.isNotBlank(actTask.getProcName())) {
			sql += " and a.procName like ? ";
			list.add("%"+actTask.getProcName()+"%");
		}
		if (StringUtils.isNotBlank(actTask.getStatus())) {
			sql += " and a.status=? ";
			list.add(actTask.getStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by APPLY_TIME desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findAllTasks(
			Page<Map<String, Object>> page, ActTask actTask) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select c.ID_,c.PROC_DEF_ID_,d.NAME_,d.TASK_DEF_KEY_,d.ASSIGNEE_,d.create_time_,"
				+"b.USER_ID,b.APPLY_TIME,b.status,b.PROC_INST_ID_,b.finish_time,def.VERSION_,def.key_,def.SUSPENSION_STATE_,"
				+"tc.zwxm,def.Name_ procName,xt.Note statusDescr,tc2.zwxm assigneeName "
				+"from OA_All b "
				+"inner join (select max(ID_) ID_,PROC_INST_ID_,PROC_DEF_ID_ from ACT_HI_TASKINST "
				+"group by PROC_INST_ID_,PROC_DEF_ID_) c on b.PROC_INST_ID_=c.PROC_INST_ID_ "
				+"left join ACT_RU_TASK d on c.PROC_INST_ID_=d.PROC_INST_ID_ "
				+"left join tczybm tc on tc.czybh=b.user_id "
				+"left join tczybm tc2 on tc2.czybh=d.ASSIGNEE_ "
				+"left join ACT_RE_PROCDEF def on c.PROC_DEF_ID_=def.ID_ "
				+"left join txtdm xt on b.status=xt.cbm and xt.id='OAALLSTATUS' "
				+"where 1=1 ";

		sql += ") a where 1=1 ";
		if (StringUtils.isNotBlank(actTask.getUserName())) {
			sql += " and a.zwxm like ? ";
			list.add("%"+actTask.getUserName()+"%");
		}
		if (StringUtils.isNotBlank(actTask.getUserId())) {
			sql += " and a.user_id=? ";
			list.add(actTask.getUserId());
		}
		if (StringUtils.isNotBlank(actTask.getProcName())) {
			sql += " and a.procName like ? ";
			list.add("%"+actTask.getProcName()+"%");
		}
		if (StringUtils.isNotBlank(actTask.getStatus())) {
			sql += " and a.status=? ";
			list.add(actTask.getStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by APPLY_TIME desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

