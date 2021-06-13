package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SocialInsurParamDao;
import com.house.home.entity.salary.SocialInsurParam;
import com.house.home.service.salary.SocialInsurParamService;

@SuppressWarnings("serial")
@Service
public class SocialInsurParamServiceImpl extends BaseServiceImpl implements SocialInsurParamService {
    
    @Autowired
    private SocialInsurParamDao socialInsurParamDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SocialInsurParam socialInsurParam) {
        
        return socialInsurParamDao.findPageBySql(page, socialInsurParam);
    }

    @Override
    public List<SocialInsurParam> findSocialInsurParamsByDescr(String socialInsurParamDescr) {
        return socialInsurParamDao.findSocialInsurParamsByDescr(socialInsurParamDescr);
    }

}
