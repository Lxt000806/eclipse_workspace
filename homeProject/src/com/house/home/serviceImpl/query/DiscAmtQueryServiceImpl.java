package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.DiscAmtQueryDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DiscAmtQueryService;

@Service
@SuppressWarnings("serial")
public class DiscAmtQueryServiceImpl extends BaseServiceImpl implements DiscAmtQueryService {

    @Autowired
    private DiscAmtQueryDao discAmtQueryDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer, UserContext userContext) {

        return discAmtQueryDao.findPageBySql(page, customer, userContext);
    }

    @Override
    public Page<Map<String, Object>> findExtraGiftAmountPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        return discAmtQueryDao.findExtraGiftAmountPageBySql(page, customer);
    }

    @Override
    public Page<Map<String, Object>> findDiscAmountExpensePageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        return discAmtQueryDao.findDiscAmountExpensePageBySql(page, customer);
    }

    @Override
    public Page<Map<String, Object>> findFrontendRiskFundExpensePageBySql(
            Page<Map<String, Object>> page, Customer customer) {
        
        return discAmtQueryDao.findFrontendRiskFundExpensePageBySql(page, customer);
    }
    
    @Override
    public Page<Map<String, Object>> findPrjRiskFundExpensePageBySql(
            Page<Map<String, Object>> page, Customer customer) {
        
        return discAmtQueryDao.findPrjRiskFundExpensePageBySql(page, customer);
    }

}
