package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;


public interface MainItemSetService extends BaseService {

    public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
            ItemSetDetail itemSetDetail);

    public Result doSaveByProcedure(ItemSet itemSet);

}

