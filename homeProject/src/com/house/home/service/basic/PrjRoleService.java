package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrjRole;
import com.house.home.entity.basic.PrjRolePrjItem;
import com.house.home.entity.basic.PrjRoleWorkType12;


public interface PrjRoleService extends BaseService {

	/**carryRule分页信息
	 * @param page
	 * @param carryRule
	 * CarryRuleFloor
	 * CarryRuleItem
	 * @return
	 */
	//   add by hc  2017 /11/22  begin 
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjRole prjRole);//主表	
	public Page<Map<String,Object>> findPageByksxzSql(Page<Map<String,Object>> page, PrjRole prjRole);//快速新增施工表
	public Page<Map<String,Object>> findPageByksworkSql(Page<Map<String,Object>> page, PrjRole prjRole);//快速新增工种分类表
	public PrjRole getByCode(String code);  //新增时验证编号是否重复
	public PrjRole getByDescr(String descr);//新增时验证名称是否重复
	public PrjRole getByDescr1(String descr, String descr1); //编辑时验证名称是否重复
	public Result doPrjRoleSave(PrjRole prjRole); //保存
	public Page<Map<String,Object>> findPageBySqlPrjItem(Page<Map<String,Object>> page, PrjRolePrjItem prjRolePrjItem); // 施工项目表	
	public Page<Map<String,Object>> findPageBySqlPrjWork(Page<Map<String,Object>> page, PrjRoleWorkType12 prjRoleWorkType12); //工种类型表
	public Result deleteForProc(PrjRole prjRole);
	
	//  add by hc  2017/11/22  end
	
	


}

