package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.PrjCheck;
import com.house.home.entity.finance.PrjProvide;

public interface PrjProvideService extends BaseService {

	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProvide prjProvide);
	//所有条目
	public Page<Map<String,Object>> goJqGrid_toPrjProCheck(Page<Map<String,Object>> page,PrjCheck prjCheck);
	//审核通过的条目
	public Page<Map<String,Object>> goJqGrid_toPrjProDetail(Page<Map<String,Object>> page,PrjCheck prjCheck);
    /**
     * 调用存储过程操作项目经理提成领取（新增 修改 ）
     * @param prjprovide
     * return Result
     */
	public Result doPrjProvideForProc(PrjProvide prjProvide);
	/**
	 * 调用存储过程操作 项目经理提成领取（审核 ，反审核 ）
	 */
	public Result doPrjProvideCheckForProc(PrjProvide prjProvide);
}
