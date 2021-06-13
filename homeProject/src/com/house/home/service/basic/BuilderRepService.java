package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.BuilderRep;


public interface BuilderRepService extends BaseService {


	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,  BuilderRep builderRep);
	public Page<Map<String,Object>> findPageBySqlBrbb(Page<Map<String,Object>> page,  BuilderRep builderRep);
	public Page<Map<String,Object>> findPageBySqltgphView(Page<Map<String,Object>> page,  BuilderRep builderRep);
	public Result deleteForProc(BuilderRep builderRep);
}

