package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Applicant;

public interface ApplicantService extends BaseService{

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Applicant applicant);
    
    public Map<String, Object> findMapByCode(String code);

    public List<Applicant> getByIdNum(String idNum);
}
