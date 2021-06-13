package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemProcess;
import com.house.home.entity.insales.ItemProcessDetail;
import com.house.home.entity.insales.SalesInvoice;

public interface ItemProcessService extends BaseService {

	/**ItemProcess分页信息
	 * @param page
	 * @param itemProcess
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemProcess itemProcess);
	
	/**
	 * ItemProcessDetail分页信息
	 * @param page
	 * @param itemProcess
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemProcessDetail(Page<Map<String,Object>> page, ItemProcessDetail itemProcessDetail);
	
	/**
	 * ItemProcessDetail分页信息
	 * @param page
	 * @param itemProcess
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemProcessSourceDetail(Page<Map<String,Object>> page, ItemProcessDetail itemProcessDetail);
	
	
	/**
	 * 通过材料转换信息生成员材料明细
	 * @param page
	 * @param itemProcess
	 * @return
	 */
	public Page<Map<String,Object>> getSourceItemByTransform(Page<Map<String,Object>> page, ItemProcessDetail itemProcessDetail);
	
	/**
	 * 新增、编辑、审核存储过程
	 * @date
	 * @param salesInvoice
	 * @return
	 */
	public Result doSave(ItemProcess itemProcess);
}