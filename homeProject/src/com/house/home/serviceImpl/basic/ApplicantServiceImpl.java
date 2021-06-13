package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ApplicantDao;
import com.house.home.entity.basic.Applicant;
import com.house.home.service.basic.ApplicantService;

@Service
public class ApplicantServiceImpl extends BaseServiceImpl implements ApplicantService{
    
    @Autowired
    private ApplicantDao applicantDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(
            Page<Map<String, Object>> page, Applicant applicant) {
        
        return applicantDao.findPageBySql(page, applicant);
    }
    
    @Override
    public Map<String, Object> findMapByCode(String code) {
        // TODO Auto-generated method stub
        return applicantDao.findMapByCode(code);
    }
    
    @Override
    public List<Applicant> getByIdNum(String idNum){
        return applicantDao.getByIdNum(idNum);
    }

}
