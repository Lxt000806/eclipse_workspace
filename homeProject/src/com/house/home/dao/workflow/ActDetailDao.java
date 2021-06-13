package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActDetail;

@SuppressWarnings("serial")
@Repository
public class ActDetailDao extends BaseDao {

	/**
	 * ActDetail分页信息
	 * 
	 * @param page
	 * @param actDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActDetail actDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_DETAIL a where 1=1 ";

    	if (StringUtils.isNotBlank(actDetail.getId())) {
			sql += " and a.ID_=? ";
			list.add(actDetail.getId());
		}
    	if (StringUtils.isNotBlank(actDetail.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actDetail.getType());
		}
    	if (StringUtils.isNotBlank(actDetail.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actDetail.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actDetail.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actDetail.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actDetail.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actDetail.getTaskId());
		}
    	if (StringUtils.isNotBlank(actDetail.getActInstId())) {
			sql += " and a.ACT_INST_ID_=? ";
			list.add(actDetail.getActInstId());
		}
    	if (StringUtils.isNotBlank(actDetail.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actDetail.getName());
		}
    	if (StringUtils.isNotBlank(actDetail.getVarType())) {
			sql += " and a.VAR_TYPE_=? ";
			list.add(actDetail.getVarType());
		}
    	if (actDetail.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actDetail.getRev());
		}
    	if (actDetail.getTime() != null) {
			sql += " and a.TIME_=? ";
			list.add(actDetail.getTime());
		}
    	if (StringUtils.isNotBlank(actDetail.getBytearrayId())) {
			sql += " and a.BYTEARRAY_ID_=? ";
			list.add(actDetail.getBytearrayId());
		}
    	if (StringUtils.isNotBlank(actDetail.getText())) {
			sql += " and a.TEXT_=? ";
			list.add(actDetail.getText());
		}
    	if (StringUtils.isNotBlank(actDetail.getText2())) {
			sql += " and a.TEXT2_=? ";
			list.add(actDetail.getText2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

