package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.IntInstallConDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntInstallConService;

@Service
@SuppressWarnings("serial")
public class IntInstallConServiceImpl extends BaseServiceImpl implements
        IntInstallConService {
    
    @Autowired
    private IntInstallConDao intInstallConDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer, UserContext userContext) {

        return intInstallConDao.findPageBySql(page, customer, userContext);
    }

}
