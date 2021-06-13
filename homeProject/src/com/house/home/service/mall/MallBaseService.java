package com.house.home.service.mall;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.APIKey;

public interface MallBaseService extends BaseService {

	public List<Map<String, Object>> getWorkType12List();

}
