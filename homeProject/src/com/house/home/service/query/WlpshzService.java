package com.house.home.service.query;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Item;
import com.house.home.entity.project.ItemChg;

public interface WlpshzService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Item item);

	public Page<Map<String,Object>> findTilePageBySql(Page<Map<String,Object>> page, String date);

	public Page<Map<String,Object>> findToiletPageBySql(Page<Map<String,Object>> page, String date);

	public Page<Map<String,Object>> findFloorPageBySql(Page<Map<String,Object>> page, String date);

}
