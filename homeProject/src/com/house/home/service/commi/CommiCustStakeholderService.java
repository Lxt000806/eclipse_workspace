package com.house.home.service.commi;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholder;

public interface CommiCustStakeholderService extends BaseService {

	/**CommiCustStakeholder分页信息
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder);
	
	/**
	 * 调整金额
	 * 
	 * @param commiCustStakeholder
	 */
	public void adjustAmount(CommiCustStakeholder commiCustStakeholder);
	
	/**
	 * 设计费提成
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goDesignFeeJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder); 
	
	/**
	 * 历史业绩提成
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder);
	
	/**
	 * 基础个性化提成
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goBasePersonalJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder);
	
	/**
	 * 基础个性化提成干系人
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goStakeholderJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder);
}
