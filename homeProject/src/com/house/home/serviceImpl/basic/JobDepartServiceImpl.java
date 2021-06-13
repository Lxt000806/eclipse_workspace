package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.JobDepartDao;
import com.house.home.entity.basic.JobDepart;
import com.house.home.service.basic.JobDepartService;

@Service
public class JobDepartServiceImpl extends BaseServiceImpl implements JobDepartService{
    
    @Autowired
    private JobDepartDao jobDepartDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, JobDepart jobDepart) {
        
        return jobDepartDao.findPageBySql(page, jobDepart);
    }

}
