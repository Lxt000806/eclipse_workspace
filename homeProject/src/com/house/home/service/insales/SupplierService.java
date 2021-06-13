package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Supplier;

public interface SupplierService extends BaseService {

	/**Supplier分页信息
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Supplier supplier,String itemRight);
	
	public String getItemType1(String czybh);

	/**
	 * 供应商分页查询
	 * @author	created by zb
	 * @date	2018-12-25--下午4:40:49
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String,Object>> findSupplierPageBySql(Page<Map<String, Object>> page,
			Supplier supplier);

	/**
	 * 供应商存储过程
	 * @author	created by zb
	 * @date	2018-12-27--下午5:07:29
	 * @param supplier
	 * @return
	 */
	public Result doSave(Supplier supplier);

	/**
	 * 供应商管理查看详细列表
	 * @author	created by zb
	 * @date	2018-12-28--上午11:17:46
	 * @param page
	 * @param supplier
	 */
	public Page<Map<String,Object>> findPrepayTranJqGridBySql(Page<Map<String, Object>> page,
			Supplier supplier);

	/**
	 * 发货时限设定详细查询
	 * @author	created by zb
	 * @date	2018-12-29--上午10:13:47
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String,Object>> findSupTimeJqGridBySql(Page<Map<String, Object>> page,
			Supplier supplier);

	/**
	 * 发货时限保存
	 * @author	created by zb
	 * @date	2018-12-29--下午3:57:52
	 * @param supplier
	 * @return
	 */
	public Result doDDDSave(Supplier supplier);
	/**
	 * 获取供应商供应公司编号
	 * @author	created by zb
	 * @date	2019-4-29--下午6:27:45
	 * @param page
	 * @param supplier
	 * @return
	 */
	public Page<Map<String,Object>> getCmpCode(Page<Map<String, Object>> page, Supplier supplier);
}
