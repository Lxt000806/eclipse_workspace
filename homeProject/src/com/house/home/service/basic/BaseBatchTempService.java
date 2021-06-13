package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseBatchTemp;

public interface BaseBatchTempService extends BaseService {

	/**BaseBatchTemp分页信息
	 * @param page
	 * @param baseBatchTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp);
	/**
	 * 模板区域分页信息
	 * 
	 * @param page
	 * @param baseBatchTemp
	 * @return
	 */
	public Page<Map<String,Object>> goAreaJqGrid(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp);
	/**
	 * 模板信息
	 * 
	 * @param page
	 * @param baseBatchTemp
	 * @return
	 */
	public Page<Map<String,Object>> goItemJqGrid(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp);
	/**
	 * 不是1.固定一项，只能有一个区域类型
	 * @param baseBatchTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExistType(BaseBatchTemp baseBatchTemp);
	/**
	 * 区域名称不能重复
	 * @param baseBatchTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExistDescr(BaseBatchTemp baseBatchTemp);
	/**
	 * 保存
	 * 
	 * @param baseBatchTemp
	 * @return
	 */
	public Result doSaveProc(BaseBatchTemp baseBatchTemp);
	/**
	 * 更新顺序
	 * @param baseBatchTemp
	 * @return
	 */
	public void updateDispSeq(BaseBatchTemp baseBatchTemp) ;
	/**
	 * 名称不能重复
	 * @param baseBatchTemp
	 * @return
	 */
	public List<Map<String, Object>> checkExistTempDescr(BaseBatchTemp baseBatchTemp);
}
