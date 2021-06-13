package com.house.home.dao.workflow;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.entity.Menu;
import com.house.home.entity.workflow.WfProcCopy;

@SuppressWarnings("serial")
@Repository
public class WfProcCopyDao extends BaseDao {
	
	public List<WfProcCopy> findListByWfProcNo(String wfProcNo, String taskKey) {
		String hql = "from WfProcCopy where wfProcNo = ? and taskKey = ? and CopyCzy is not null and CopyCzy <>'' ";
		List<WfProcCopy> list = this.find(hql, new Object[]{wfProcNo, taskKey});
		return list;
	}
	
	public void doSaveCopyByGroup(String wfProcInstNo,String wfProcNo, String taskKey,String lastUpdatedBy) {
		String sqlItem = " insert into tWfProcInstCopy ( WfProcInstNo, TaskKey, CopyCZY, CopyDate," +
				" RcvDate, RcvStatus, LastUpdate, LastUpdatedBy,  Expired, ActionLog ) " +
				" select ?,?,c.Czybh,getdate(), " +
				"	null,'0',getdate(),?,'F','ADD' from tWfProcCopy a " +
				" left join ACT_ID_MEMBERSHIP b on b.Group_id_ = a.CopyGroupId" +
				" left join tCzybm c on c.Czybh = b.User_Id_ " +
				" where a.wfProcNo = ? and a.TaskKey = ? and CopyGroupId is not null and CopyGroupId <> '' ";
		this.executeUpdateBySql(sqlItem, new Object[]{wfProcInstNo, taskKey,lastUpdatedBy,wfProcNo,taskKey});
	}
	
}
