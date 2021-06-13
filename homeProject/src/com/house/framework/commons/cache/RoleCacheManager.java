package com.house.framework.commons.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.entity.Role;
import com.house.framework.service.RoleService;

@Component
public class RoleCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleCacheManager.class);
	
	@Autowired
	private RoleService roleService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_ROLE_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化角色数据开始");
		List<Role> listRole = roleService.getAll();
		for (Role role : listRole){
			this.put(role.getRoleId(), role);
		}
		this.put("role",listRole);
		logger.info("初始化角色数据结束");
	}
}
