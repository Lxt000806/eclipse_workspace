package com.house.home.service.basic;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.Logo;

public interface LogoService extends BaseService {

	/**
	 * 保存登录及模块打开日志
	 * @param logo
	 * @param handler
	 * @param ex
	 */
	public void saveLog(Logo logo, Object handler, Exception ex);
	
}
