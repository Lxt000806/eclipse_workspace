package com.house.home.serviceImpl.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.EndProfPerDao;
import com.house.home.entity.finance.EndProfPer;
import com.house.home.service.finance.EndProfPerService;

/** 材料毛利率 */
@SuppressWarnings("serial")
@Service(value = "endProfPerServiceImpl")
public class EndProfPerServiceImpl extends BaseServiceImpl implements EndProfPerService{

    @Autowired
    private EndProfPerDao endProfPerDao;
    
    @Override
    public Page<Map<String, Object>> findPageBySql(
            Page<Map<String, Object>> page, EndProfPer endProfPer) {
       return endProfPerDao.findPageBySql(page, endProfPer);
    }

    @Override
    public Map<String, Object> getEndProfPerDetail(String code) {
       return endProfPerDao.getEndProfPerDetail(code);
    }
    
    @Override
    public List<Result> doSaveBatch(List<EndProfPer> endProfPerList, String importTypes) {
        List<Result> resultList = new ArrayList<Result>();
        for(EndProfPer endProfPer : endProfPerList){
            Result result = endProfPerDao.doSaveBatch(endProfPer, importTypes);
            resultList.add(result);
        }
        return resultList;
    }
}
