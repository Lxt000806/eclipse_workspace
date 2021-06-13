package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.EndProfPer;

/** 材料毛利率 */
public interface EndProfPerService extends BaseService{

    Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EndProfPer endProfPer);
    
    Map<String, Object> getEndProfPerDetail(String code);
    
    List<Result> doSaveBatch(List<EndProfPer> endProfPerList, String importTypes);
}
