package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemChg;

public interface ItemChgService extends BaseService {

	/**ItemChg分页信息
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChg itemChg,UserContext uc);
	/**ItemChg明细分页信息
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, ItemChg itemChg,UserContext uc);
	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, ItemChg itemChg);

	public Page<Map<String,Object>> findPlzjyjPageBySql(Page<Map<String,Object>> page, ItemChg itemChg);

	/**ItemChg参考业绩分页信息
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findReferencePageBySql(Page<Map<String,Object>> page, ItemChg itemChg);
	/**
	 * 材料增减审核
	 * @param itemChg
	 */
	public void doComfirm(ItemChg itemChg,String status);
	/**
	 * 调用存储过程操作材料增减（新增、修改、审核）
	 * @param itemChg
	 * @return
	 */
	public Result doItemChgForProc(ItemChg itemChg,String status);
	/**
	 * 是否允许增减
	 */
	public boolean isAllowChg(Customer customer);
	
	public List<Map<String, Object>> getChangeParameterItemType2(String custCode,String itemType1,String isService) ;

	public boolean getItemChgStatus(String no);
	
	/**
	 * 获取客户申请状态的材料增减单数量
	 * @param custCode
	 * @return
	 */
	public int getCountByCustCode(String custCode);
	
	/**
	 * 是否有申请状态的增减项单
	 * @param custCode
	 * @return
	 */
	public boolean hasOpenRecord(String custCode);
	
	public String getArfCustCodeList();
	
	public boolean existsItemCmp(String itemCode ,String custCode);
	
	public Result doPlzjyj(ItemChg itemChg);
	/**
	 * 查看软装材料状态
	 * @author	created by zb
	 * @date	2020-3-18--下午6:01:37
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findItemStatusPageBySql(Page<Map<String, Object>> page,
			ItemChg itemChg);
	/**
	 * 获取增减干系人列表
	 * @author	created by zb
	 * @date	2020-4-20--下午4:41:28
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findItemChgStakeholderPageBySql(Page<Map<String, Object>> page,
			ItemChg itemChg);
	
	public Result doItemChgTempProc(ItemChg itemchg);
	
	public Result doRegenFromPrePlanTemp(ItemChg itemChg);
	
	public boolean getExistsTemp(String custCode,String no);
	
	public Double getAmountByItemType(String itemType1,String custCode) ;
	
    public Page<Map<String,Object>> findSetDeductions(Page<Map<String, Object>> page, ItemChg itemChg);
    
	public Result doGenChgMainItemSet(ItemChg itemChg);
    
}
