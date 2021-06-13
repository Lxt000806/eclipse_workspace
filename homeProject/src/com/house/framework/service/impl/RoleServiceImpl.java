package com.house.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.dao.RoleAuthorityDao;
import com.house.framework.dao.RoleDao;
import com.house.framework.dao.RoleUserDao;
import com.house.framework.entity.Role;
import com.house.framework.entity.RoleAuthority;
import com.house.framework.entity.RoleUser;
import com.house.framework.service.RoleService;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;

@Service
public class RoleServiceImpl implements RoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleAuthorityDao roleAuthorityDao;
	@Autowired
	private RoleUserDao roleUserDao;
	
	//@LoggerAnnotation(okLog="添加 ID=$args[0].roleId roleName=$args[0].roleName 成功", errLog="添加 roleName=$args[0].roleName 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void save(Role role) {
		Assert.notNull(role);
		
		Reflections.trim(role);
		role.setGenTime(new Date());
		role.setOrderNo(0L);
		this.roleDao.save(role);
	}
	
	//@LoggerAnnotation(okLog="更新 ID=$args[0].roleId roleName=$args[0].roleName 成功", errLog="更新 ID=$args[0].roleId roleName=$args[0].roleName 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void update(Role role) {
		Assert.notNull(role);
		
		Reflections.trim(role);
		if(role.getOrderNo() == null)
			role.setOrderNo(0L);
		role.setUpdateTime(new Date());
		this.roleDao.update(role);
	}
	
	//@LoggerAnnotation(okLog="删除 IDS=$args[0] 成功", errLog="删除 IDS=$args[0] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void delete(List<Long> roleIds){
		if(roleIds == null || roleIds.size()<1)
			return ;
		for(Long roleId : roleIds){
			if(roleId != null){
				Role role = this.get(roleId);
				if(role == null)
					continue;
				if(CommonConstant.SUPER_ADMIN.equals(role.getRoleCode()) || CommonConstant.ONE_ADMIN.equals(role.getRoleCode()) || CommonConstant.TWO_ADMIN.equals(role.getRoleCode())){
					throw new RuntimeException("系统内置角色，不能删除");
				}
				this.roleDao.delete(this.get(roleId));
				this.clearRoleAuths(roleId);
				this.clearRoleUsers(roleId);
			}
		}
	}
	
	public Role get(Long roleId){
		if(roleId == null)
			return null;
		return this.roleDao.get(Role.class, roleId);
	}
	
	@SuppressWarnings("rawtypes")
	public Page findPage(Page page, Role role){
		Assert.notNull(role);
		return this.roleDao.findPage(page, role);
	}
	
	@SuppressWarnings(value="unchecked")
	public List<Role> getAll(){
		String hql = "from Role order by orderNo";
		return this.roleDao.find(hql);
	}
	
	@SuppressWarnings(value="unchecked")
	public List<Role> getByCreaterId(Long createrId){
		String hql = "from Role r where r.createrId = ? order by orderNo";
		return this.roleDao.find(hql, createrId);
	}
	
	//@LoggerAnnotation(okLog="为地市新建角色 roleName=$args[0].roleName 地市编码 cityCodes=#foreach($cityCode in $args[1])$cityCode,#end 成功", errLog="为地市新建角色 roleName=$args[0].roleName 地市编码 cityCodes=$args[1] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public List<Long> saveRoleBatch(Role role, String[] cityCodes) {
		List<Long> list = null;
		//如果地市编码数组为空，则添加一条与属于添加者地市的记录
		if(cityCodes == null || cityCodes.length<1){
			logger.debug("用户添加角色时未选定地市，默认将该用户所属地市设置为该角色所属地市");
			list = new ArrayList<Long>(1);
			UserContext uc = UserContextHolder.getUserContext();
			role.setCityCode(uc.getCityCode());
			role.setCreaterId(uc.getCzybh());
			this.save(role);
			list.add(role.getRoleId());
			return list;
		}
		list = new ArrayList<Long>(cityCodes.length);
		Role r = null;
		for (int i = 0; i < cityCodes.length; i++) {
			if(StringUtils.isNotBlank(cityCodes[i])){
				r = new Role();
				r.setCityCode(cityCodes[i]);
				r.setGenTime(new Date());
				r.setOrderNo(0L);
				r.setCreaterId(role.getCreaterId());
				r.setRemark(role.getRemark());
				r.setRoleCode(role.getRoleCode()+"_"+cityCodes[i]);
				r.setRoleName(role.getRoleName());
				r.setSysCode(role.getSysCode());
				this.roleDao.save(r);
				list.add(r.getRoleId());
			}
		}
		return list;
	}
	
	
	//@LoggerAnnotation(okLog="设置角色权限 ID=$args[0] 权限 authorityIds=$args[1] 成功", errLog="分配角色权限 ID=$args[0] 权限 authorityIds=$args[1] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void setRoleAuths(Long roleId, List<Long> authorityIds) {
		this.clearRoleAuths(roleId);
		this.addRoleAuths(roleId, authorityIds);
	}
	
	/**
	 * 设置角色权限
	 */
	//@LoggerAnnotation(okLog="分配角色权限 ID=$args[0] 添加权限 addAuthIds=$args[1] 删除权限 delAuthIds=$args[2] 成功", errLog="分配角色权限 ID=$args[0] 添加权限 addAuthIds=$args[1] 删除权限 delAuthIds=$args[2] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void setRoleAuths(Long roleId, List<Long> addAuthIds, List<Long> delAuthIds){
		Assert.notNull(roleId,"角色ID不能为空");
		if(delAuthIds != null && delAuthIds.size()>0){
			for(Long delAuthId : delAuthIds){
				this.roleAuthorityDao.delByRoleIdAndAuthId(roleId, delAuthId);
			}
		}
		if(addAuthIds != null && addAuthIds.size()>0){
			this.addRoleAuths(roleId, addAuthIds);
		}
	}
	
	//@LoggerAnnotation(okLog="清除角色权限 ID=$args[0] 成功", errLog="清除角色权限 ID=$args[0] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void clearRoleAuths(Long roleId) {
		Assert.notNull(roleId,"角色Id不能为空");
		this.roleAuthorityDao.clearByRoleId(roleId);
	}
	
	//@LoggerAnnotation(okLog="添加角色权限 ID=$args[0] 权限 authorityIds=$args[1] 成功", errLog="添加角色权限 ID=$args[0] 权限 authorityIds=$args[1] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void addRoleAuths(Long roleId, List<Long> authorityIds){
		Assert.notNull(roleId,"角色Id不能为空");
		if(authorityIds == null || authorityIds.size()<1)
			return ;
		RoleAuthority roleAuthority = null;
		for(Long authId : authorityIds){
			if(authId != null){
				//查询数据库中是否已经有此条记录
				roleAuthority = this.roleAuthorityDao.getByRoleIdAndAuthId(roleId, authId);
				//已经存在记录，则不再新增
				if(roleAuthority != null){
					continue;
				}
				
				roleAuthority = new RoleAuthority();
				roleAuthority.setRoleId(roleId);
				roleAuthority.setAuthorityId(authId);
				roleAuthority.setGenTime(new Date(new java.util.Date().getTime()));
				this.roleAuthorityDao.save(roleAuthority);
			}
		}
	}
	
	@SuppressWarnings(value="unchecked")
	public List<Long> getAuthIdsByRoleId(Long roleId) {
		if(roleId == null)
			return null;
		String hql ="select a.authorityId from RoleAuthority a where a.roleId = ? ";
		return this.roleAuthorityDao.find(hql, new Object[]{roleId});
	}
	

	//@LoggerAnnotation(okLog="设置角色用户 ROLEID=$args[0] 用户 USER_IDS=$args[1] 成功", errLog="设置角色用户 ROLEID=$args[0] 用户 USER_IDS=$args[1] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void setRoleUsers(Long roleId, List<String> userIds) {
		this.clearRoleUsers(roleId);
		this.addRoleUsers(roleId, userIds);
	}
	
	//@LoggerAnnotation(okLog="清除角色用户 ROLEID=$args[0] 成功", errLog="清除角色用户 ROLEID=$args[0] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void clearRoleUsers(Long roleId) {
		Assert.notNull(roleId,"角色Id不能为空");
		this.roleUserDao.clearByRoleId(roleId);
	}
	
	//@LoggerAnnotation(okLog="设置角色用户 ROLEID=$args[0] 用户 USER_IDS=$args[1] 成功", errLog="添加角色用户 ROLEID=$args[0] 用户 USER_IDS=$args[1] 失败", modelCode=LogModuleConstant.ROLE_MODULE)
	public void addRoleUsers(Long roleId, List<String> userIds){
		Assert.notNull(roleId,"角色Id不能为空");
		if(userIds == null || userIds.size()<1)
			return ;
		RoleUser roleUser = null;
		for(String userId : userIds){
			if(userId != null){
				roleUser = new RoleUser();
				roleUser.setRoleId(roleId);
				roleUser.setCzybh(userId);
				roleUser.setGenTime(new Date(new java.util.Date().getTime()));
				this.roleUserDao.save(roleUser);
			}
		}
	}
	
	@SuppressWarnings(value="unchecked")
	public List<Long> getUserIdsByRoleId(Long roleId) {
		if(roleId == null)
			return null;
		String hql="select a.czybh from RoleUser a where a.roleId = ? ";
		return this.roleUserDao.find(hql, new Object[]{roleId});
	}
	
	public List<Role> getByUserId(Long userId){
		if(userId == null)
			return null;
		return this.roleDao.getByUserId(userId);
	}
	

	public Role getByRoleCode(String roleCode) {
		if(StringUtils.isBlank(roleCode))
			return null;
		return this.roleDao.getByRoleCode(roleCode);
	}

	public List<Map<String, Object>> getRoleIdsGroupByUserId() {
		return this.roleUserDao.getRoleIdsGroupByUserId();
	}

	public String getRolesByCzybh(String czybh) {
		return roleUserDao.getRolesByCzybh(czybh);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getAuthIdsByCzybh(String czybh) {
		if(czybh == null)
			return null;
		String hql ="select a.authorityId from RoleAuthority a where a.roleId in (select b.roleId from RoleUser b where b.czybh=?) ";
		return this.roleAuthorityDao.find(hql, new Object[]{czybh});
	}

	@Override
	public List<Map<String, Object>> getQX_RoleQX(Long roleId, int ptbh) {

		return roleDao.getQX_RoleQX(roleId, ptbh);
	}

	@Override
	public Result doAppRight(Role role) {
		
		return roleDao.doAppRight(role);
	}

	@Override
	public List<Map<String, Object>> isUsedByCzy(String roleIds) {
		
		return roleDao.isUsedByCzy(roleIds);
	}

	@Override
	public List<Map<String, Object>> isUsedByAuth(String roleIds) {
		return roleDao.isUsedByAuth(roleIds);
	}

	@Override
	public List<Map<String, Object>> getRoleList(String id) {
		return roleDao.getRoleList(id);
	}

	@Override
	public Result doCopyRight(Role role) {
		return roleDao.doCopyRight(role);
	}

	@Override
	public List<Map<String, Object>> getMenuByRoId(Long roleId) {
		return roleDao.getMenuByRoId(roleId);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Page goFastMenuJqGrid(Page page, Long roleId) {
		return roleDao.goFastMenuJqGrid(page, roleId);
	}

	@Override
	public Result doSaveFastMenu(Role role) {
		return roleDao.doSaveFastMenu(role);
	}

	
	@Override
	public List<Map<String, Object>> getFastMenus(String czybh){
		return roleDao.getFastMenus(czybh);
	}
}
