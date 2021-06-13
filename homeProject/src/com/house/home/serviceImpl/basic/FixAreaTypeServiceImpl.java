package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.FixAreaTypeDao;
import com.house.home.entity.basic.FixAreaType;
import com.house.home.service.basic.FixAreaTypeService;

@Service
public class FixAreaTypeServiceImpl extends BaseServiceImpl implements FixAreaTypeService  {
    
    @Autowired
    private FixAreaTypeDao fixAreaTypeDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, FixAreaType fixAreaType) {
        
        return fixAreaTypeDao.findPageBySql(page, fixAreaType);
    }
    
    @Override
    public boolean updateCheckDescrIsExist(String code, String descr) {
        
        if(fixAreaTypeDao.get(FixAreaType.class, code).getDescr().equals(descr)) return false;
        
        return fixAreaTypeDao.getByDescr(descr).size() > 0;
    }

    @Override
    public boolean saveCheckDescrIsExist(String descr) {
        return fixAreaTypeDao.getByDescr(descr).size() != 0;
    }

}
