package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BankPos;

public interface BankPosService extends BaseService {

	/**BankPos分页信息
	 * @param page
	 * @param bankPos
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BankPos bankPos);
	/**
	 * 更新bankPos
	 * @param bankPos
	 */
	public void doUpdate(BankPos bankPos);
	/**
	 * 检查code是否已存在
	 * @param bankPos
	 * @return
	 */
	public boolean checkExsist(BankPos bankPos);
	/**
	 * 根据code查询BankPos
	 * @param code
	 * @return
	 */
	public Map<String , Object>  getBankPosDetail(String code);
}
