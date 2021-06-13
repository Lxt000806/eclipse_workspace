package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemTransform;

public interface ItemTransformService extends BaseService{

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            ItemTransform itemTransform);

    Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,
            ItemTransform itemTransform);

    void doSave(ItemTransform itemTransform);

    void doUpdate(ItemTransform itemTransform);

}
