package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjProgTemp;


public interface PrjProgTempService extends BaseService{
		public Page<Map<String , Object>> findPageBySql(
				Page<Map<String , Object>>page ,PrjProgTemp prjProgTemp );
		
}
