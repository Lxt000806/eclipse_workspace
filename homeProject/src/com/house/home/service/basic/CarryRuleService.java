package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CarryRule;
import com.house.home.entity.basic.CarryRuleFloor;
import com.house.home.entity.basic.CarryRuleItem;


public interface CarryRuleService extends BaseService {

	/**carryRule分页信息
	 * @param page
	 * @param carryRule
	 * CarryRuleFloor
	 * CarryRuleItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CarryRule carryRule);	
	public Page<Map<String,Object>> findPageBySqlDetailadd(Page<Map<String,Object>> page, CarryRuleFloor carryRuleFloor);
	
	public CarryRule getByNo(String No,String ItemType1,String ItemType2,String CarryType,String DistanceType, String sendType);
	
	public CarryRule getByNo2(String ItemType1,String ItemType2,String CarryType,String DistanceType, String sendType);
	
	public CarryRuleItem getByNo1(String FieldJson);
	
	public Page<Map<String,Object>> findPageBySqlDetail(Page<Map<String,Object>> page, CarryRuleFloor carryRuleFloor);	
	
	public Page<Map<String,Object>> findPageBySqlItem3(Page<Map<String,Object>> page, CarryRuleItem carryRuleItem);	
	
	public Result docarryRuleSave(CarryRule carryRule);
	
	public Result deleteForProc(CarryRule carryRule);

}

