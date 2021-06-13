package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BuilderNum;

public interface BuilderNumService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderNum builderNum);
	
	public Page<Map<String,Object>> findNumByCode(Page<Map<String,Object>> page,String builderDelivCode);
	/**
	 * 批量增加楼号信息
	 * 
	 * @param qz 前缀
	 * @param beginNum 开始序号
	 * @param endNum 结束序号
	 * @param builderDelivCode 批次号
	 * @param lastUpdatedBy 最后修改人
	 */
	public void multiAdd(String qz, Integer beginNum, Integer endNum,String builderDelivCode,String lastUpdatedBy);
	/**
	 * 删除buildnum
	 * @param beginNum
	 */
	public void deleteBuilderNum(BuilderNum builderNum) ;
}
