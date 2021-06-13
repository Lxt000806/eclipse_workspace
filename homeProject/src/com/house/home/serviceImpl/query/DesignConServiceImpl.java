package com.house.home.serviceImpl.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.home.dao.query.DesignConDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DesignConService;

@Service
@SuppressWarnings("serial")
public class DesignConServiceImpl extends BaseServiceImpl implements DesignConService {
    
    @Autowired
    private DesignConDao designConDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer, UserContext userContext) {
        
        return designConDao.findPageBySql(page, customer, userContext);
    }

    @Override
    public Page<Map<String, Object>> findConPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        
        return designConDao.findConPageBySql(page, customer);
    }
    
    @Override
    public Map<String, Object> getDesignConInfoByCustCode(String custCode, UserContext userContext) {
        
        return designConDao.getDesignConInfoByCustCode(custCode, userContext);
    }

}
