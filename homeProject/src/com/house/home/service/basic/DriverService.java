package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Driver;

public interface DriverService extends BaseService {
	public Driver getByPhoneAndMm(String phone, String mm);
	public Driver getByPhone(String phone);
	
	/**Driver分页信息
	 * @param page
	 * @param driver
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Driver driver);
	
	public Driver getByName(String nameChi);
	
	public List<Driver> findByNoExpired();
	
	/**
	 * @Description: TODO 司机信息存储过程
	 * @author	created by zb
	 * @date	2018-11-1--下午4:55:16
	 * @param driver
	 * @return
	 */
	public Result doSave(Driver driver);
	
	/**
	 * @Description: TODO 检查唯一值手机号码是否重复
	 * @author	created by zb
	 * @date	2018-11-5--下午3:58:03
	 * @param phone
	 * @return
	 */
	public Boolean checkPhone(String phone);
	
}
