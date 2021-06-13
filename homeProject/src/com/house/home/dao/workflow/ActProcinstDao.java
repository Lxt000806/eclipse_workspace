package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActProcinst;

@SuppressWarnings("serial")
@Repository
public class ActProcinstDao extends BaseDao {

	/**
	 * ActProcinst分页信息
	 * 
	 * @param page
	 * @param actProcinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProcinst actProcinst) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_PROCINST a where 1=1 ";

    	if (StringUtils.isNotBlank(actProcinst.getId())) {
			sql += " and a.ID_=? ";
			list.add(actProcinst.getId());
		}
    	if (StringUtils.isNotBlank(actProcinst.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actProcinst.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actProcinst.getBusinessKey())) {
			sql += " and a.BUSINESS_KEY_=? ";
			list.add(actProcinst.getBusinessKey());
		}
    	if (StringUtils.isNotBlank(actProcinst.getProcDefId())) {
			sql += " and a.PROC_DEF_ID_=? ";
			list.add(actProcinst.getProcDefId());
		}
    	if (actProcinst.getStartTime() != null) {
			sql += " and a.START_TIME_=? ";
			list.add(actProcinst.getStartTime());
		}
    	if (actProcinst.getEndTime() != null) {
			sql += " and a.END_TIME_=? ";
			list.add(actProcinst.getEndTime());
		}
    	if (actProcinst.getDuration() != null) {
			sql += " and a.DURATION_=? ";
			list.add(actProcinst.getDuration());
		}
    	if (StringUtils.isNotBlank(actProcinst.getStartUserId())) {
			sql += " and a.START_USER_ID_=? ";
			list.add(actProcinst.getStartUserId());
		}
    	if (StringUtils.isNotBlank(actProcinst.getStartActId())) {
			sql += " and a.START_ACT_ID_=? ";
			list.add(actProcinst.getStartActId());
		}
    	if (StringUtils.isNotBlank(actProcinst.getEndActId())) {
			sql += " and a.END_ACT_ID_=? ";
			list.add(actProcinst.getEndActId());
		}
    	if (StringUtils.isNotBlank(actProcinst.getSuperProcessInstanceId())) {
			sql += " and a.SUPER_PROCESS_INSTANCE_ID_=? ";
			list.add(actProcinst.getSuperProcessInstanceId());
		}
    	if (StringUtils.isNotBlank(actProcinst.getDeleteReason())) {
			sql += " and a.DELETE_REASON_=? ";
			list.add(actProcinst.getDeleteReason());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

