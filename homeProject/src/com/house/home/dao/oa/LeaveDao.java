package com.house.home.dao.oa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.oa.Leave;

@SuppressWarnings("serial")
@Repository
public class LeaveDao extends BaseDao {

	/**
	 * Leave分页信息
	 * 
	 * @param page
	 * @param leave
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Leave leave) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from OA_LEAVE a where 1=1 ";

    	if (leave.getId() != null) {
			sql += " and a.ID=? ";
			list.add(leave.getId());
		}
    	if (StringUtils.isNotBlank(leave.getProcessInstanceId())) {
			sql += " and a.PROCESS_INSTANCE_ID=? ";
			list.add(leave.getProcessInstanceId());
		}
    	if (StringUtils.isNotBlank(leave.getUserId())) {
			sql += " and a.USER_ID=? ";
			list.add(leave.getUserId());
		}
    	if (leave.getStartTime() != null) {
			sql += " and a.START_TIME=? ";
			list.add(leave.getStartTime());
		}
    	if (leave.getEndTime() != null) {
			sql += " and a.END_TIME=? ";
			list.add(leave.getEndTime());
		}
    	if (StringUtils.isNotBlank(leave.getLeaveType())) {
			sql += " and a.LEAVE_TYPE=? ";
			list.add(leave.getLeaveType());
		}
    	if (StringUtils.isNotBlank(leave.getReason())) {
			sql += " and a.REASON=? ";
			list.add(leave.getReason());
		}
    	if (leave.getApplyTime() != null) {
			sql += " and a.APPLY_TIME=? ";
			list.add(leave.getApplyTime());
		}
    	if (leave.getRealityStartTime() != null) {
			sql += " and a.REALITY_START_TIME=? ";
			list.add(leave.getRealityStartTime());
		}
    	if (leave.getRealityEndTime() != null) {
			sql += " and a.REALITY_END_TIME=? ";
			list.add(leave.getRealityEndTime());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Id";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public Leave getByProcessInstanceId(String id) {
		String hql = "from Leave a where a.processInstanceId=?";
		List<Leave> list = this.find(hql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

