package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustTypeGroupDao;
import com.house.home.entity.basic.CustTypeGroup;
import com.house.home.service.basic.CustTypeGroupService;

@Service
@SuppressWarnings("serial")
public class CustTypeGroupServiceImpl extends BaseServiceImpl implements CustTypeGroupService {
    
    @Autowired
    private CustTypeGroupDao custTypeGroupDao;

    public Page<Map<String, Object>> findGroupPageBySql(Page<Map<String, Object>> page,
            CustTypeGroup custTypeGroup) {

        return custTypeGroupDao.findGroupPageBySql(page, custTypeGroup);
    }

    @Override
    public Page<Map<String, Object>> findGroupDetailPageBySql(Page<Map<String, Object>> page,
            CustTypeGroup custTypeGroup) {

        return custTypeGroupDao.findGroupDetailPageBySql(page, custTypeGroup);
    }

    @Override
    public Result saveByProcedure(CustTypeGroup custTypeGroup) {
        
        return custTypeGroupDao.saveByProcedure(custTypeGroup);
    }

}
