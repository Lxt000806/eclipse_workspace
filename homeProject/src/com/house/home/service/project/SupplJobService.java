package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.SupplJob;

public interface SupplJobService extends BaseService {

	/**SupplJob分页信息
	 * @param page
	 * @param supplJob
	 * @return
	 */
	public void doDelSuppl(Integer pk);
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplJob supplJob);
	
	public void  doExec(String type,String no,Integer pk, String recvDate,String supplRemarks,String planDate,String lastUpdatedBy); 
	
	public Page<Map<String, Object>> findCupboardPageBySql(Page<Map<String, Object>> page, SupplJob supplJob);
	
	public void doUpdate(SupplJob supplJob);

	/**
	 * 根据客户编号获取此客户楼盘的工地密码
	 * 
	 * @param custCode 客户编号
	 * @return 工地密码
	 */
    String getBuildPassByCustCode(String custCode);
}
