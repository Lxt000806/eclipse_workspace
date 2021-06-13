package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.Table;

public interface TableService extends BaseService {

	/**获取所有字段
	 * @param page
	 * @param table
	 * @return
	 */
	public List<Map<String,Object>> getColumns(Table table);
	
	/**
	 * 获取ColModel
	 * @param table
	 * @return
	 */
	public List<Map<String, Object>> getColModel(Table table);
	
	/**
	 * 获取表格操作员映射表
	 * 
	 * @param table
	 * @return
	 */
	public Map<String, Object> getTableCzyMapper(Table table);
	
	/**
	 * 保存
	 * @param table
	 * @return
	 */
	public Result doSave(Table table);
}