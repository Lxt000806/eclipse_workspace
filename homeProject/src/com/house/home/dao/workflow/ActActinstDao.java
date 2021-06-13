package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActActinst;

@SuppressWarnings("serial")
@Repository
public class ActActinstDao extends BaseDao {

	/**
	 * ActActinst分页信息
	 * 
	 * @param page
	 * @param actActinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActActinst actActinst) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_ACTINST a where 1=1 ";

    	if (StringUtils.isNotBlank(actActinst.getId())) {
			sql += " and a.ID_=? ";
			list.add(actActinst.getId());
		}
    	if (StringUtils.isNotBlank(actActinst.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actActinst.getProcDefId());
		}
    	if (StringUtils.isNotBlank(actActinst.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actActinst.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actActinst.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actActinst.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actActinst.getActId())) {
			sql += " and a.ACT_ID_=? ";
			list.add(actActinst.getActId());
		}
    	if (StringUtils.isNotBlank(actActinst.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actActinst.getTaskId());
		}
    	if (StringUtils.isNotBlank(actActinst.getCallProcInstId())) {
			sql += " and a.CALL_PROC_INST_ID_=? ";
			list.add(actActinst.getCallProcInstId());
		}
    	if (StringUtils.isNotBlank(actActinst.getActName())) {
			sql += " and a.ACT_NAME_=? ";
			list.add(actActinst.getActName());
		}
    	if (StringUtils.isNotBlank(actActinst.getActType())) {
			sql += " and a.ACT_TYPE_=? ";
			list.add(actActinst.getActType());
		}
    	if (StringUtils.isNotBlank(actActinst.getAssignee())) {
			sql += " and a.ASSIGNEE_=? ";
			list.add(actActinst.getAssignee());
		}
    	if (actActinst.getStartTime() != null) {
			sql += " and a.START_TIME_=? ";
			list.add(actActinst.getStartTime());
		}
    	if (actActinst.getEndTime() != null) {
			sql += " and a.END_TIME_=? ";
			list.add(actActinst.getEndTime());
		}
    	if (actActinst.getDuration() != null) {
			sql += " and a.DURATION_=? ";
			list.add(actActinst.getDuration());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

