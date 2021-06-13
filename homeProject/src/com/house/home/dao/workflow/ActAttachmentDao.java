package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActAttachment;

@SuppressWarnings("serial")
@Repository
public class ActAttachmentDao extends BaseDao {

	/**
	 * ActAttachment分页信息
	 * 
	 * @param page
	 * @param actAttachment
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActAttachment actAttachment) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_ATTACHMENT a where 1=1 ";

    	if (StringUtils.isNotBlank(actAttachment.getId())) {
			sql += " and a.ID_=? ";
			list.add(actAttachment.getId());
		}
    	if (actAttachment.getRev() != null) {
			sql += " and a.REV_=? ";
			list.add(actAttachment.getRev());
		}
    	if (StringUtils.isNotBlank(actAttachment.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(actAttachment.getUserId());
		}
    	if (StringUtils.isNotBlank(actAttachment.getName())) {
			sql += " and a.NAME_=? ";
			list.add(actAttachment.getName());
		}
    	if (StringUtils.isNotBlank(actAttachment.getDescription())) {
			sql += " and a.DESCRIPTION_=? ";
			list.add(actAttachment.getDescription());
		}
    	if (StringUtils.isNotBlank(actAttachment.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actAttachment.getType());
		}
    	if (StringUtils.isNotBlank(actAttachment.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actAttachment.getTaskId());
		}
    	if (StringUtils.isNotBlank(actAttachment.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actAttachment.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actAttachment.getUrl())) {
			sql += " and a.URL_=? ";
			list.add(actAttachment.getUrl());
		}
    	if (StringUtils.isNotBlank(actAttachment.getContentId())) {
			sql += " and a.CONTENT_ID_=? ";
			list.add(actAttachment.getContentId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

