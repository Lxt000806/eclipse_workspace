package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemTypeConfirm;
import com.house.home.entity.basic.SpcBuilder;

public interface ItemTypeConfirmService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,ItemTypeConfirm itemTypeConfirm);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,ItemTypeConfirm itemTypeConfirm);

	public Result doSave(ItemTypeConfirm itemTypeConfirm);

	
}
