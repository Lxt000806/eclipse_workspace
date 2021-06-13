package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.IntProduce;

public interface IntProduceService extends BaseService {
	
	/**
	 * 获取生产进度登记显示列表
	 * @author	created by zb
	 * @date	2020-2-28--下午2:56:03
	 * @param page
	 * @param intProduce
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page,
			IntProduce intProduce);
	/**
	 * 生产进度登记新增保存
	 * @author	created by zb
	 * @date	2020-3-4--上午10:06:21
	 * @param intProduce
	 */
	public void doIntProdSave(IntProduce intProduce);
	/**
	 * 根据楼盘、供应商、是否橱柜查询客户集成进度表
	 * @author	created by zb
	 * @date	2020-3-4--上午10:11:28
	 * @param intProduce
	 * @return
	 */
	public List<Map<String, Object>> getCustIntProg(IntProduce intProduce);
	/**
	 * 根据楼盘、供应商、是否橱柜三个信息查询集成生产信息
	 * @author	created by zb
	 * @date	2020-3-4--上午10:31:10
	 * @param intProduce
	 * @return
	 */
	public List<Map<String, Object>> getIntProduce(IntProduce intProduce);
	/**
	 * 生产进度登记新增修改
	 * @author	created by zb
	 * @date	2020-3-4--上午11:36:30
	 * @param intProduce
	 */
	public void doIntProdUpdate(IntProduce intProduce);

}
