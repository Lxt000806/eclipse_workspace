package com.house.home.service.design;

import java.util.Map;


import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.FixArea;

public interface FixAreaService extends BaseService {

	/**FixArea分页信息
	 * @param page
	 * @param fixArea
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, FixArea fixArea);
	/**
	 * 根据名称判断装修区域是否已经存在
	 */
	public boolean isExisted(FixArea fixArea);
	/**
	 * 新增装修区域
	 */
	public Result addtFixArea(FixArea fixArea);
	/**
	 * 插入装修区域
	 */
	public Result insertFixArea(FixArea fixArea);
	/**
	 * 删除装修区域
	 */
	public Map<String,Object> deleteFixArea(FixArea fixArea);
	/**
	 * 自动增加水电项目、土建项目、安装项目、综合项目的装修区域
	 */
	public void addRegular_FixArea(String custCode,String czy);
	/**
	 * 自动增加主材预算的默认装修区域
	 */
	public void addItem_FixArea(String custCode,String itemAreaDesc,String czy,String itemType1);
	
	public int getFixAreaPk(String itemType1, String custCode, String descr, Integer isService);
}
