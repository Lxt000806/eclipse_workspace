package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.basic.AssetType;

public interface AssetTypeService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, AssetType assetType);

    Result doSave(AssetType assetType, UserContext userContext);

    Result doUpdate(AssetType assetType, UserContext userContext);

}
