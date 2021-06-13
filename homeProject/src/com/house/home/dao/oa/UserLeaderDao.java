package com.house.home.dao.oa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.oa.UserLeader;

@SuppressWarnings("serial")
@Repository
public class UserLeaderDao extends BaseDao {

	/**
	 * UserLeader分页信息
	 * 
	 * @param page
	 * @param userLeader
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, UserLeader userLeader) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.USER_ID_ userid,a.LEADER_ID_ leaderid,b.zwxm username,c.zwxm leadername from OA_USER_LEADER a left join tczybm b "
				+"on a.USER_ID_=b.czybh left join tczybm c on a.LEADER_ID_=c.czybh where 1=1 ";

    	if (StringUtils.isNotBlank(userLeader.getUserId())) {
			sql += " and a.USER_ID_=? ";
			list.add(userLeader.getUserId());
		}
    	if (StringUtils.isNotBlank(userLeader.getLeaderId())) {
			sql += " and a.LEADER_ID_=? ";
			list.add(userLeader.getLeaderId());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.leaderid";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> findByLeaderId(String czybh) {
		String sql = "select a.USER_ID_ userid,a.LEADER_ID_ leaderid,b.zwxm from OA_USER_LEADER a inner join tczybm b on "
				+"a.USER_ID_=b.czybh where a.LEADER_ID_=?";
		return this.findBySql(sql, new Object[]{czybh});
	}

	@SuppressWarnings("unchecked")
	public UserLeader getByUserIdAndLeaderId(String userId, String leaderId) {
		String hql = "from UserLeader where userId=? and leaderId=?";
		List<UserLeader> list = this.find(hql, new Object[]{userId,leaderId});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

