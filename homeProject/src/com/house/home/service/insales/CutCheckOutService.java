package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.CutCheckOut;

public interface CutCheckOutService extends BaseService {

	/**CutCheckOut分页信息
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CutCheckOut cutCheckOut);
	
	/**
	 * 新增明细列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goAddDetailJqGrid(Page<Map<String,Object>> page, CutCheckOut cutCheckOut);
	
	/**
	 * 根据规格查切割类型和切割费
	 * @param cutCheckOut
	 * @return
	 */
	public List<Map<String, Object>> getCutTypeBySize(CutCheckOut cutCheckOut);
	
	/**
	 * 保存
	 * @param cutCheckOut
	 * @return
	 */
	public Result doSaveProc(CutCheckOut cutCheckOut);
	
	/**
	 * 明细列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, CutCheckOut cutCheckOut);
	
	/**
	 * 入库保存
	 * @param cutCheckOut
	 * @return
	 */
	public Result doCheckIn(CutCheckOut cutCheckOut) ;
	
	/**
	 * 入库列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridCheckIn(Page<Map<String,Object>> page, CutCheckOut cutCheckOut);
	
	/**
	 * 入库明细列表
	 * 
	 * @param page
	 * @param cutCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridCheckInDtl(Page<Map<String,Object>> page, CutCheckOut cutCheckOut);
}
