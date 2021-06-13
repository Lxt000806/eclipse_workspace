package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.SpecItemReq;

public interface SpecItemReqService extends BaseService{

	/**
	 * @Description:集成解单管理分页查询
	 * @author	created by zb
	 * @date	2018-10-7--下午5:26:40
	 * @param page
	 * @param specItemReq
	 * @return
	 */
	public Page<Map<String , Object>> findPageBySql(Page<Map<String, Object>> page, SpecItemReq specItemReq);

	/**
	 * @Description:详细分页查询
	 * @author	created by zb
	 * @date	2018-10-9--上午11:58:13
	 * @param page
	 * @param custCode
	 * @param iscupboard 
	 * @return
	 */
	public Page<Map<String , Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			String custCode, String iscupboard);
	
	/**
	 * @Description:根据成品名称获取成品pk
	 * @author	created by zb
	 * @date	2018-10-11--下午6:17:17
	 * @param custCode
	 * @param productName
	 * @return
	 */
	public Map<String, Object> getIntProd(String custCode, String productName);
	
	/**
	 * @Description:根据客户编号、材料编号获取已下单数量
	 * @author	created by zb
	 * @date	2018-10-12--上午10:57:31
	 * @param custCode
	 * @param itemCode
	 * @return
	 */
	public Map<String, Object> getAppQty(String custCode, String itemCode);

	/**
	 * @Description:保存
	 * @author	created by zb
	 * @date	2018-10-13--上午10:58:44
	 * @param specItemReq
	 * @return
	 */
	public Result doSave(SpecItemReq specItemReq);
	/**
	 * 明细查询
	 * @author cjg
	 * @date 2019-8-15
	 * @param page
	 * @param specItemReq
	 * @return
	 */
	public Page<Map<String, Object>> goDetailQuery(Page<Map<String, Object>> page, SpecItemReq specItemReq);
	/**
	 * 查询干系人（解单员）
	 * @author cjg
	 * @date 2019-8-15
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getStakeholderInfo(String custCode) ;

}
