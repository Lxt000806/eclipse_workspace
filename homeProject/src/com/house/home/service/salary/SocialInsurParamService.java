package com.house.home.service.salary;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SocialInsurParam;

public interface SocialInsurParamService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SocialInsurParam socialInsurParam);

    List<SocialInsurParam> findSocialInsurParamsByDescr(String socialInsurParamDescr);
}
