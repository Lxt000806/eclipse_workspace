package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemHasBeenShippedDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemHasBeenShippedService;

@Service 
@SuppressWarnings("serial")
public class ItemHasBeenShippedServiceImpl extends BaseServiceImpl implements ItemHasBeenShippedService {
    
    @Autowired
    private ItemHasBeenShippedDao itemHasBeenShippedDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            Customer customer) {
        return itemHasBeenShippedDao.findPageBySql(page, customer);
    }

}
