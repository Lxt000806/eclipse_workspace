package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActVarinst;

@SuppressWarnings("serial")
@Repository
public class ActVarinstDao extends BaseDao {

	/**
	 * ActVarinst分页信息
	 * 
	 * @param page
	 * @param actVarinst
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActVarinst actVarinst) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_VARINST a where 1=1 ";

    	if (StringUtils.isNotBlank(actVarinst.getId())) {
			sql += " and a.ID_=? ";
			list.add(actVarinst.getId());
		}
    	if (StringUtils.isNotBlank(actVarinst.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actVarinst.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actVarinst.getExecutionId())) {
			sql += " and a.EXECUTION_ID_=? ";
			list.add(actVarinst.getExecutionId());
		}
    	if (StringUtils.isNotBlank(actVarinst.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actVarinst.getTaskId());
		}
    	if (StringUtils.isNotBlank(actVarinst.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actVarinst.getName());
		}
    	if (StringUtils.isNotBlank(actVarinst.getVarType())) {
			sql += " and a.VAR_TYPE_=? ";
			list.add(actVarinst.getVarType());
		}
    	if (actVarinst.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actVarinst.getRev());
		}
    	if (StringUtils.isNotBlank(actVarinst.getBytearrayId())) {
			sql += " and a.BYTEARRAY_ID_=? ";
			list.add(actVarinst.getBytearrayId());
		}
    	if (StringUtils.isNotBlank(actVarinst.getText())) {
			sql += " and a.TEXT_=? ";
			list.add(actVarinst.getText());
		}
    	if (StringUtils.isNotBlank(actVarinst.getText2())) {
			sql += " and a.TEXT2_=? ";
			list.add(actVarinst.getText2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public ActVarinst getByInstIdandName(String instId, String name) {
		String hql = "from ActVarinst a where a.procInstId=? and name=?";
		List<ActVarinst> list = this.find(hql, new Object[]{instId,name});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

