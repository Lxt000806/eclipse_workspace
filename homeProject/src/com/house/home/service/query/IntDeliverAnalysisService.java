package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.SupplJob;

public interface IntDeliverAnalysisService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SupplJob supplJob, UserContext userContext);
}
