package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

public interface DiscAmtQueryService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer,
            UserContext userContext);

    Page<Map<String, Object>> findPrjRiskFundExpensePageBySql(Page<Map<String, Object>> page,
            Customer customer);

    Page<Map<String, Object>> findExtraGiftAmountPageBySql(Page<Map<String, Object>> page,
            Customer customer);

    Page<Map<String, Object>> findDiscAmountExpensePageBySql(Page<Map<String, Object>> page,
            Customer customer);

    Page<Map<String, Object>> findFrontendRiskFundExpensePageBySql(Page<Map<String, Object>> page,
            Customer customer);

}
