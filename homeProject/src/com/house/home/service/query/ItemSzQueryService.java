package com.house.home.service.query;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
public interface ItemSzQueryService extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer, UserContext uc);
	
	public Map<String,Object> getXmjljsxx(String code);
	
	public List<Map<String,Object>> goJcszxxJqGrid(String custCode);
	
	public List<Map<String,Object>> goYshfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
	public List<Map<String,Object>> goYshfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
	public List<Map<String,Object>> goZjhfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
	public List<Map<String,Object>> goZjhfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
	public List<Map<String,Object>> goZfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
	public List<Map<String,Object>> goZfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
    public List<Map<String, Object>> goRgcbmxJqGrid(Page<Map<String, Object>> page,
            String costType, String workType1Name, String custCode, String workType2);
	
	public List<Map<String,Object>> goClcbmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	
	public List<Map<String,Object>> goLlmxJqGrid(Page<Map<String,Object>> page,String no,String custCode);
	
	public List<Map<String,Object>> goRgfymxJqGrid(Page<Map<String,Object>> page,String custCode);
	
	public Map<String,Object> getCustPayPlan(String custCode);
	
	public List<Map<String,Object>> goKhfkJqGrid(Page<Map<String,Object>> page,String custCode);

	public List<Map<String,Object>> goYxxmjljlJqGrid(Page<Map<String,Object>> page,String custCode);

	public List<Map<String,Object>> goYkmxJqGrid(Page<Map<String,Object>> page,String custCode);

	public Map<String,Object> getZjzje(String custCode);

	public Map<String,Object> getHasPay(String custCode);
	
	public Map<String,Object> getJccbzcxx(String custCode);
	
	public Map<String,Object> getSzhz(String custCode);

	public List<Map<String,Object>> goJcbgJqGrid(Page<Map<String,Object>> page,String custCode);
	
	public List<Map<String,Object>> goJcclcbmxJqGrid(Page<Map<String,Object>> page,String custCode,String workType2);
	
	public List<Map<String,Object>> goTcnclcbmxJqGrid(Page<Map<String,Object>> page,String custCode,String itemType1);
	
	public List<Map<String,Object>> goJcxqJqGrid(Page<Map<String,Object>> page,String workType1,String baseItem,String custCode,String isOutSet);
	
    public List<Map<String, Object>> goRgcbmxTcJqGrid(Page<Map<String, Object>> page, String costType,
            String custCode, String workType2);
	
	public List<Map<String,Object>> goRgfymxTcJqGrid(Page<Map<String,Object>> page,String custCode);
	
	public List<Map<String,Object>> goZcrzszxxJqGrid(String custCode);
	
	public List<Map<String,Object>> goLldJqGrid(Page<Map<String,Object>> page,String custCode,String type,String itemType2,String itemAppStatus);
	
	public List<Map<String,Object>> goLldLlmxJqGrid(Page<Map<String,Object>> page,String no,String custCode);
	
	public List<Map<String,Object>> goClzjJqGrid(Page<Map<String,Object>> page,String custCode,String type);
	
	public List<Map<String,Object>> goClzjZjmxJqGrid(Page<Map<String,Object>> page,String no);
	
	public List<Map<String,Object>> goRgfymxZcrzszxxJqGrid(Page<Map<String,Object>> page,String custCode,String itemType1,String laborFeeStatus);
	
	public List<Map<String,Object>> goRzmxJqGrid(String custCode);
	
	public List<Map<String,Object>> goCgdJqGrid(Page<Map<String,Object>> page,String custCode,String itemType2,String purchaseStatus,String isCheckOut,String checkOutStatus);
	
	public List<Map<String,Object>> goCgdmxJqGrid(Page<Map<String,Object>> page,String no);

	/**
	 * 水电发包定额
	 * @author	created by zb
	 * @date	2018-11-26--下午4:32:14
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String, Object>> findWaterContractQuotaJqGrid(Page<Map<String, Object>> page, String custCode,String workType2);
	/**
	 * 统计卧室和卫生间数量
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getFixAreaTypeCount(String custCode);
	/**
	 * 套餐内主材奖惩
	 * @author cjg
	 * @date 2019-4-22
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String, Object>> goTcnzcjcJqGrid(Page<Map<String, Object>> page, String custCode);
	/**
	 * 查询公式和结果
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getExprByCust( String custCode, String type);
	/**
	 * 售后材料明细
	 * @author cjg
	 * @date 2019-11-1
	 * @param page
	 * @param workType1Name
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public List<Map<String,Object>> goShclmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	/**
	 * 售后人工明细
	 * @author cjg
	 * @date 2019-11-1
	 * @param page
	 * @param workType1Name
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public List<Map<String,Object>> goShrgmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2);
	/**
	 * 是否关联楼盘
	 * @author cjg
	 * @date 2019-11-6
	 * @param custCode
	 * @return
	 */
	public List<Map<String,Object>> isRefCustCode(String custCode);
	/**
	 * 人工是否关联楼盘
	 * @author cjg
	 * @date 2019-12-20
	 * @param custCode
	 * @return
	 */
	public List<Map<String,Object>> isRefCustCode_wc(String custCode);
	/**
	 * 施工补贴
	 * @author cjg
	 * @date 2020-7-13
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String, Object>> goSgbtJqGrid(Page<Map<String, Object>> page, String custCode);
}
