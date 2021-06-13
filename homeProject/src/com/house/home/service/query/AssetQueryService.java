package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.query.AssetQuery;

public interface AssetQueryService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, AssetQuery assetQuery,
            UserContext userContext);

}
