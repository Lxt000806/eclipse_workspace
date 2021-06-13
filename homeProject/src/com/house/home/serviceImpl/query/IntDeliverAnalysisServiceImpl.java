package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.IntDeliverAnalysisDao;
import com.house.home.entity.project.SupplJob;
import com.house.home.service.query.IntDeliverAnalysisService;

@Service
@SuppressWarnings("serial")
public class IntDeliverAnalysisServiceImpl extends BaseServiceImpl implements
        IntDeliverAnalysisService {
    
    @Autowired
    private IntDeliverAnalysisDao intDeliverAnalysisDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SupplJob supplJob, UserContext userContext) {

        return intDeliverAnalysisDao.findPageBySql(page, supplJob, userContext);
    }

}
