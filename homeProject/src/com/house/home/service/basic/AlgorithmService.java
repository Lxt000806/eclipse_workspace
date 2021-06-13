package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Algorithm;

public interface AlgorithmService extends BaseService {

	/**Algorithm分页信息
	 * @param page
	 * @param algorithm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Algorithm algorithm);
	/**
	 * Algorithm明细分页信息
	 * 
	 * @param page
	 * @param algorithm
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, Algorithm algorithm);
	/**
	 * 判断是否存在descr
	 * @param algorithm
	 * @return
	 */
	public boolean hasDescr(Algorithm algorithm);
	/**
	 * 保存
	 * @param algorithm
	 * @return
	 */
	public Result doSave(Algorithm algorithm);
	/**
	 * 判断是否切割费
	 * 
	 * @param algorithm
	 * @return
	 */
	public List<Map<String, Object>> checkIsCalCutFee(Algorithm algorithm);
}
