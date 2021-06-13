package com.house.framework.commons.factory;

import com.house.framework.service.AuthorityService;
import com.house.framework.service.DictItemService;
import com.house.framework.service.DictService;
import com.house.framework.service.MenuService;
import com.house.framework.service.OperLogService;
import com.house.framework.service.RoleService;
import com.house.framework.service.impl.AuthorityServiceImpl;
import com.house.framework.service.impl.DictItemServiceImpl;
import com.house.framework.service.impl.DictServiceImpl;
import com.house.framework.service.impl.MenuServiceImpl;
import com.house.framework.service.impl.OperLogServiceImpl;
import com.house.framework.service.impl.RoleServiceImpl;

public final class ServiceFactory {

	/**
	 * 权限服务工厂
	 * 
	 * @return
	 */
	public static AuthorityService getAuthorityService() {
		return new AuthorityServiceImpl();
	}

	/**
	 * 角色服务工厂
	 * 
	 * @return
	 */
	public static RoleService getRoleService() {
		return new RoleServiceImpl();
	}

	/**
	 * 数据字典项服务工厂
	 * 
	 * @return
	 */
	public static DictItemService getDictItemService() {
		return new DictItemServiceImpl();
	}

	/**
	 * 数据字典服务工厂
	 * 
	 * @return
	 */
	public static DictService getDictService() {
		return new DictServiceImpl();
	}
	
	/**
	 * 菜单服务工厂
	 * 
	 * @return
	 */
	public static MenuService getMenuService() {
		return new MenuServiceImpl();
	}
	
	/**
	 * 操作日志服务工厂
	 * 
	 * @return
	 */
	public static OperLogService getOperLogService() {
		return new OperLogServiceImpl();
	}
	
}
