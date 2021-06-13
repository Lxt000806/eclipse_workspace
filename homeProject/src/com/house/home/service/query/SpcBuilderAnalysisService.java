package com.house.home.service.query;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SpcBuilder;

public interface SpcBuilderAnalysisService extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, SpcBuilder spcBuilder);
	public Page<Map<String,Object>> findSpcBuilderAnalysisDetailPageBySql(Page<Map<String, Object>> page,SpcBuilder spcBuilder);
}
