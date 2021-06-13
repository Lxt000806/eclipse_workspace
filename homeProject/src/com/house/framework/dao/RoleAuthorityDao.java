package com.house.framework.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.entity.RoleAuthority;

@SuppressWarnings("serial")
@Repository
public class RoleAuthorityDao extends BaseDao {
	/**
	 * readme mysql 删除语句需要去掉别名
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RoleAuthorityDao.class);

	public void clearByRoleId(Long roleId) {
		if(roleId == null)
			return ;
		String hql = "delete from RoleAuthority  where roleId = ?";
		this.executeUpdate(hql, roleId);
	}
	
	public void clearByAuthorityId(Long authorityId) {
		if(authorityId == null)
			return ;
		String hql = "delete from RoleAuthority  where authorityId = ?";
		this.executeUpdate(hql, authorityId);
	}
	
	@SuppressWarnings("unchecked")
	public RoleAuthority getByRoleIdAndAuthId(Long roleId,Long authId){
		String hql = "from RoleAuthority ra where ra.roleId = ? and ra.authorityId = ?";
		List<RoleAuthority> list = this.find(hql, new Object[]{roleId,authId});
		if(list == null || list.size() < 1)
			return null;
		return list.get(0);
	}
	
	
	public void delByRoleIdAndAuthId(Long roleId, Long authId){
		String sql = "delete from TSYS_ROLE_AUTHORITY where role_id = ? and authority_id = ?";
		this.executeUpdateBySql(sql, new Object[]{roleId, authId});
	}
	
}
