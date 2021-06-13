package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

public interface DesignConService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer, UserContext userContext);

    Page<Map<String, Object>> findConPageBySql(Page<Map<String, Object>> page, Customer customer);

    Map<String, Object> getDesignConInfoByCustCode(String custCode, UserContext userContext);

}
