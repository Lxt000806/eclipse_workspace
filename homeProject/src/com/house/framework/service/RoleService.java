package com.house.framework.service;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Role;
import com.house.home.entity.basic.Czybm;

public interface RoleService {

	/**
	 * 保存角色信息
	 * @param role
	 */
	public void save(Role role);
	
	/**
	 * 更新角色
	 * @param role
	 */
	public void update(Role role);
	
	/**
	 * 删除角色，同时删除 角色用户，角色操作
	 * @param roleIds
	 */
	public void delete(List<Long> roleIds);
	
	/**
	 * 获取角色信息
	 * @param roleId
	 * @return
	 */
	public Role get(Long roleId);
	
	/**
	 * 角色列表
	 * @param page
	 * @param role
	 * @return
	 */
	@SuppressWarnings(value={ "rawtypes" })
	public Page findPage(Page page, Role role);
	
	/**
	 * 根据角色编码获取角色
	 * @param roleCode
	 * @return
	 */
	public Role getByRoleCode(String roleCode);
	
	/**
	 * 超级管理员或省级管理员为 选中的城市增加角色
	 * @param cityCodes 需要则加角色的城市
	 * @param role 角色的具体信息
	 */
	public List<Long> saveRoleBatch(Role role, String[] cityCodes);
	
	/**
	 * 获取所有的角色
	 * @return
	 */
	public List<Role> getAll();
	
	/**
	 * 获取默认创建的所有角色
	 * @param createrId
	 * @return
	 */
	public List<Role> getByCreaterId(Long createrId);
	
	
	//----------------------------------角色权限------------------------------------------
	
	/**
	 * 设置角色权限，会存在误删除情况，不推荐
	 * 1.删除角色原有权限
	 * 2.加入角色新的权限
	 */
	public void setRoleAuths(Long roleId, List<Long> authorityIds);
	
	/**
	 * 设置角色权限，包括新增哪些权限，删除哪些权限
	 * @param roleId  角色ID
	 * @param addIds  新增的权限ID列表
	 * @param delIds  删除的权限ID列表
	 */
	public void setRoleAuths(Long roleId, List<Long> addIds, List<Long> delIds);
	
	/**
	 * 清除角色的所有权限
	 * @param roleId
	 */
	public void clearRoleAuths(Long roleId);
	
	/**
	 * 为角色增加权限
	 * @param roleId
	 * @param authorityIds
	 */
	public void addRoleAuths(Long roleId, List<Long> authorityIds);
	
	/**
	 * 获取角色所拥有的权限的ID列表
	 * @param roleId
	 * @return 权限ID列表
	 */
	public List<Long> getAuthIdsByRoleId(Long roleId);
	
	/**
	 * 获取操作员拥有的权限的ID列表
	 * @param roleId
	 * @return 权限ID列表
	 */
	public List<Long> getAuthIdsByCzybh(String czybh);
	
	//--------------------------------------角色用户--------------------------------------
	
	/**
	 * 设置角色用户
	 */
	public void setRoleUsers(Long roleId, List<String> userIds);
	
	/**
	 * 清除角色所有用户
	 * @param roleId
	 */
	public void clearRoleUsers(Long roleId);
	
	/**
	 * 分配角色用户
	 * @param roleId
	 * @param userIds
	 */
	public void addRoleUsers(Long roleId, List<String> userIds);
	
	/**
	 * 获取角色下所拥有的ID列表
	 * @param roleId
	 * @return 用户ID列表
	 */
	public List<Long> getUserIdsByRoleId(Long roleId);
	
	/**
	 * 获取用户所有的角色，按角色ID排序
	 * @param userId
	 * @return
	 */
	public List<Role> getByUserId(Long userId);
	
	/**
	 * 表TSYS_ROLE_USER的角色ID排序后 group by 用户ID 然后以逗号分隔返回字符串
	 * 此方法仅在 Tomcat启动时将 所有的角色组对应权限集合载入缓存 时调用
	 * @return
	 */
	public List<Map<String,Object>> getRoleIdsGroupByUserId();

	/**获取操作员所有的角色，多个逗号隔开
	 * @param czybh
	 * @return
	 */
	public String getRolesByCzybh(String czybh);
	
	public List<Map<String, Object>> getQX_RoleQX(Long roleId, int ptbh);
	
	public Result doAppRight(Role role);
	
	/**
	 * 是否分配给操作员
	 * @param czybh 操作员编号
	 * @return
	 */
	public List<Map<String, Object>> isUsedByCzy(String roleIds);
	
	/**
	 * 是否已分配权限
	 * @param czybh 操作员编号
	 * @return
	 */
	public List<Map<String, Object>> isUsedByAuth(String roleIds);
	
	public List<Map<String, Object>> getRoleList(String id);
	
	public Result doCopyRight(Role role);
	
	/**
	 * 根据角色Id获取菜单
	 * @param roleId 角色编号
	 * @return
	 */
	public List<Map<String, Object>> getMenuByRoId(Long roleId);
	
	/**
	 * 快捷菜单查询
	 * @param page
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page goFastMenuJqGrid(Page page, Long roleId);
	
	/**
	 * 保存快捷菜单
	 * @param role
	 * @return
	 */
	public Result doSaveFastMenu(Role role);
	
	public List<Map<String, Object>> getFastMenus(String czybh);
	
}
