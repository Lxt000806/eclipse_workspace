package com.house.framework.dao;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.entity.RoleUser;

@SuppressWarnings("serial")
@Repository
public class RoleUserDao extends BaseDao {

	private static final Logger logger = LoggerFactory.getLogger(RoleUserDao.class);
	
	/**
	 * 清空 TSYS_ROLE_USER 表中所有角色ID 为 roleId 的记录
	 * 即 清空角色所有用户
	 * @param roleId
	 */
	public void clearByRoleId(Long roleId) {
		if(roleId == null)
			return ;
		logger.debug("清空角色 ID={} 所有用户", roleId);
		String hql = "delete from RoleUser where roleId = ?";
		this.executeUpdate(hql, roleId);
	}
	
	/**
	 * 清空 TSYS_ROLE_USER 表中所有用户ID 为 userId 的记录
	 * 即 清空用户所有角色
	 * @param roleId
	 */
	public void clearByUserId(Long userId) {
		if(userId == null)
			return ;
		logger.debug("清空用户 ID={} 所有角色", userId);
		String hql = "delete from RoleUser where userId = ?";
		this.executeUpdate(hql, userId);
	}
	
	/**
	 * 根据用户ID和角色ID获取 用户角色对象
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RoleUser getByRoleIdAndUserId(String roleId, String userId){
		String hql = "from RoleUser t where t.roleId = ? and t.userId = ?";
		List<RoleUser> list = this.find(hql, new Object[]{roleId, userId});
		if(list == null || list.size() < 1)
			return null;
		return list.get(0);
	}
	
	/**
	 * 表TSYS_ROLE_USER的角色ID排序后 group by 用户ID 然后以逗号分隔返回字符串
	 * 此方法仅在 Tomcat启动时将 所有的角色组对应权限集合载入缓存 时调用
	 * @return
	 */
	public List<Map<String,Object>> getRoleIdsGroupByUserId() {
		String sql ="select distinct (rtrim(xmlagg(xmlelement(e, t.role_id || ','))" +
					"                       .extract('//text()')," +
					"                       ',')) as USER_ROLE_IDS" +
					"  from (select t1.role_id, t1.user_id" +
					"          from tsys_role_user t1" +
					"         order by t1.user_id, t1.role_id) t" +
					" group by t.user_id";
		return this.findBySql(sql);
	}

	public void deleteByCzybh(String czybh) {
		String hql = "delete from RoleUser where czybh = ?";
		this.executeUpdate(hql, new Object[]{czybh});
		
	}

	@SuppressWarnings("unchecked")
	public String getRolesByCzybh(String czybh) {
		StringBuffer result = new StringBuffer("");
		String hql = "from RoleUser where czybh = ?";
		List<RoleUser> list = this.find(hql, new Object[]{czybh});
		if (list!=null && list.size()>0){
			for (RoleUser u : list){
				result.append(u.getRoleId()).append(",");
			}
			int length = result.length();
			if (result.charAt(length-1)==','){
				result.deleteCharAt(length-1);
			}
		}
		return result.toString();
	}
}
