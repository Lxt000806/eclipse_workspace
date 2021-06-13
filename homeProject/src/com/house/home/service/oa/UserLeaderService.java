package com.house.home.service.oa;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.oa.UserLeader;

public interface UserLeaderService extends BaseService {

	/**UserLeader分页信息
	 * @param page
	 * @param userLeader
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, UserLeader userLeader);

	public List<Map<String, Object>> findByLeaderId(String czybh);
	
	public UserLeader getByUserIdAndLeaderId(String userId,String leaderId);
	
}
