package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ProfitParaDao;
import com.house.home.entity.basic.ProfitPara;
import com.house.home.service.basic.ProfitParaService;

@Service
public class ProfitParaServiceImpl extends BaseServiceImpl implements ProfitParaService{

    @Autowired
    private ProfitParaDao profitParaDao;
    
    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, ProfitPara profitPara) {
        
        return profitParaDao.findPageBySql(page, profitPara);
    }

}
