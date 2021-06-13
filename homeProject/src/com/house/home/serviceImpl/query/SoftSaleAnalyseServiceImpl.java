package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.SoftSaleAnalyseDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.SoftSaleAnalyseService;

@Service 
@SuppressWarnings("serial")
public class SoftSaleAnalyseServiceImpl extends BaseServiceImpl implements SoftSaleAnalyseService {
    
    @Autowired
    private SoftSaleAnalyseDao softSaleAnalyseDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        return softSaleAnalyseDao.findPageBySql(page, customer);
    }

}
