package com.house.home.dao.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActComment;

@SuppressWarnings("serial")
@Repository
public class ActCommentDao extends BaseDao {

	/**
	 * ActComment分页信息
	 * 
	 * @param page
	 * @param actComment
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActComment actComment) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ACT_HI_COMMENT a where 1=1 ";

    	if (StringUtils.isNotBlank(actComment.getId())) {
			sql += " and a.ID_=? ";
			list.add(actComment.getId());
		}
    	if (StringUtils.isNotBlank(actComment.getType())) {
			sql += " and a.TYPE_=? ";
			list.add(actComment.getType());
		}
    	if (actComment.getTime() != null) {
			sql += " and a.TIME_=? ";
			list.add(actComment.getTime());
		}
    	if (StringUtils.isNotBlank(actComment.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(actComment.getUserId());
		}
    	if (StringUtils.isNotBlank(actComment.getTaskId())) {
			sql += " and a.TASK_ID_=? ";
			list.add(actComment.getTaskId());
		}
    	if (StringUtils.isNotBlank(actComment.getProcInstId())) {
			sql += " and a.PROC_INST_ID_=? ";
			list.add(actComment.getProcInstId());
		}
    	if (StringUtils.isNotBlank(actComment.getAction())) {
			sql += " and a.ACTION_=? ";
			list.add(actComment.getAction());
		}
    	if (StringUtils.isNotBlank(actComment.getMessage())) {
			sql += " and a.MESSAGE_=? ";
			list.add(actComment.getMessage());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

