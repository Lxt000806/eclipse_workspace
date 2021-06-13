package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActVariable;

@SuppressWarnings("serial")
@Repository
public class ActVariableDao extends BaseDao {

	/**
	 * ActVariable分页信息
	 * 
	 * @param page
	 * @param actVariable
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActVariable actVariable) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_RU_VARIABLE a where 1=1 ";

    	if (StringUtils.isNotBlank(actVariable.getId())) {
			sql += " and a.ID_=? ";
			list.add(actVariable.getId());
		}
    	if (actVariable.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actVariable.getRev());
		}
    	if (StringUtils.isNotBlank(actVariable.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actVariable.getType());
		}
    	if (StringUtils.isNotBlank(actVariable.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actVariable.getName());
		}
    	if (StringUtils.isNotBlank(actVariable.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actVariable.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actVariable.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actVariable.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actVariable.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actVariable.getTaskId());
		}
    	if (StringUtils.isNotBlank(actVariable.getBytearrayId())) {
			sql += " and a.BYTEARRAY_ID_=? ";
			list.add(actVariable.getBytearrayId());
		}
    	if (StringUtils.isNotBlank(actVariable.getText())) {
			sql += " and a.TEXT_=? ";
			list.add(actVariable.getText());
		}
    	if (StringUtils.isNotBlank(actVariable.getText2())) {
			sql += " and a.TEXT2_=? ";
			list.add(actVariable.getText2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

