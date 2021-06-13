package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustTypeGroup;

public interface CustTypeGroupService extends BaseService {

    public Page<Map<String, Object>> findGroupPageBySql(Page<Map<String, Object>> page,
            CustTypeGroup custTypeGroup);

    public Page<Map<String, Object>> findGroupDetailPageBySql(Page<Map<String, Object>> page,
            CustTypeGroup custTypeGroup);

    public Result saveByProcedure(CustTypeGroup custTypeGroup);

}
