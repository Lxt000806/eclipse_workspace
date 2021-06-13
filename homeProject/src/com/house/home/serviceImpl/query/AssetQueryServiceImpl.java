package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.query.AssetQuery;
import com.house.home.dao.query.AssetQueryDao;
import com.house.home.service.query.AssetQueryService;

@Service
@SuppressWarnings("serial")
public class AssetQueryServiceImpl extends BaseServiceImpl implements AssetQueryService {
    
    @Autowired
    private AssetQueryDao assetQueryDao;
    
    @Override
    public Page<Map<String, Object>> findPageBySql(
            Page<Map<String, Object>> page, AssetQuery assetQuery,
            UserContext userContext) {
        
        assetQuery.setCzybh(userContext.getCzybh());
        
        return assetQueryDao.findPageBySql(page, assetQuery);
    }

}
