package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemReq;

public interface ItemReqService extends BaseService {

	/**ItemReq分页信息
	 * @param page
	 * @param itemReq
	 * @returnfind
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReq itemReq);
	
	/**软装未下单查询信息
	 * @param page
	 * @param itemReq
	 * @returnfind
	 */
	public Page<Map<String,Object>> findSoftNotAppQueryPageBySql(Page<Map<String,Object>> page, ItemReq itemReq);

	/**采购管理预算导入分页信息
	 * @param page
	 * @param itemReq
	 * @returnfind
	 */
	public Page<Map<String,Object>> findImportPageBySql(Page<Map<String,Object>> page, ItemReq itemReq,String arr);

	/**ItemReq分页信息
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlForClient(Page<Map<String,Object>> page, ItemReq itemReq);
	
	/**
	 * 已有项新增
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findItemReqList(Page<Map<String,Object>> page, ItemReq itemReq);
	/**基础需求
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_jzxq(Page<Map<String,Object>> page, ItemReq itemReq);
	/**材料需求主材需求
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_zcxq(Page<Map<String,Object>> page, ItemReq itemReq);
	/**材料需求及到货分析
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_dhfx(Page<Map<String,Object>> page, ItemReq itemReq);
	/**是否套餐内需求
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public List<ItemReq> getReqList(String custCode,String itemType1);
	
	/**
	 * 如果存在有材料需求，但没有存在已审核通过的材料结算申请，进行提示
	 * @return
	 */
	public String getHintString(String custCode);
	/**
	 * 判断材料需求表（软装和主材），是否存在需求数量<>发货数量
	 * @param custCode
	 * @return
	 */
	public int getCountNum(String custCode); 
	
	/**
	 * 工程完工确认
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_gcwg(Page<Map<String,Object>> page, String custCode);
	
	/**
	 * 工程完工确认
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> getCountDiscDetail(Page<Map<String,Object>> page, Customer customer);
}
