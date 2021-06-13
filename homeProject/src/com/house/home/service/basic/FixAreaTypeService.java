package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.FixAreaType;

public interface FixAreaTypeService extends BaseService{

    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, FixAreaType fixAreaType);
    
    public boolean updateCheckDescrIsExist(String code, String descr);
    
    public boolean saveCheckDescrIsExist(String descr);
}
