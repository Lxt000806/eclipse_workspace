package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustIntProg;

public interface CustIntProgService extends BaseService {

	/**CustIntProg分页信息
	 * @param page
	 * @param custIntProg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustIntProg custIntProg);
	
	/**CustIntProg详情
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Map<String , Object>  getCustIntProgDetail(String custCode);

	/**根据材质查计划天数
	 * @param itemType12
	 * @param material
	 * @return
	 */
	public Map<String, Object> findSenddaysByMaterial(String material,String itemType12);
	
	/**制单拖期录入
	 * @param custIntProg
	 * @return
	 */
	public void doDelayAdd(CustIntProg custIntProg);
	
	/**根据custcode判断是否已录入该客户信息
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String , Object>>  checkRegistered(String custCode) ;
	
	/**根据custcode判断该客户集成或橱柜有没有拖期
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String , Object>>  checkDelayed(String custCode);
	
	/**执行保存的存储过程
	 * @param page
	 * @param custIntProg
	 * @return
	 */
	public Result doSaveProc(CustIntProg custIntProg);
}
