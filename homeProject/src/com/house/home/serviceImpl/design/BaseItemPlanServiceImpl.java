package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.BaseItemPlanDao;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.BaseItemPlanService;

@SuppressWarnings("serial")
@Service
public class BaseItemPlanServiceImpl extends BaseServiceImpl implements BaseItemPlanService {

	@Autowired
	private BaseItemPlanDao baseItemPlanDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemPlan baseItemPlan){
		return baseItemPlanDao.findPageBySql(page, baseItemPlan);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_jzys(
			Page<Map<String, Object>> page, BaseItemPlan baseItemPlan) {
		return baseItemPlanDao.findPageBySql_jzys(page,baseItemPlan);
	}

	@Override
	public boolean hasBaseItemPlan(String custCode) {
		return baseItemPlanDao.hasBaseItemPlan(custCode);
	}

	@Override
	public Map<String, Object> getBaseFeeComp(String custCode, String descr) {
		// TODO Auto-generated method stub
		return baseItemPlanDao.getBaseFeeComp(custCode,descr);
	}

	@Override
	public Result doBaseItemForProc(Customer customer) {
		// TODO Auto-generated method stub
		return baseItemPlanDao.doBaseItemForProc(customer);
	}
	@Override
	public Result doBaseItemTCForProc(Customer customer) {
		// TODO Auto-generated method stub
		return baseItemPlanDao.doBaseItemTCForProc(customer);
	}

	@Override
	public long getBaseItemPlanCount(BaseItemPlan baseItemPlan) {
		// TODO Auto-generated method stub
		return baseItemPlanDao.getBaseItemPlanCount(baseItemPlan);
	}

    @Override
    public double calculateWallArea(String custCode) {
        return baseItemPlanDao.calculateWallArea(custCode);
    }

	@Override
	public String getBaseItemPlanAutoQty(BaseItemPlan baseItemPlan) {

		return baseItemPlanDao.getBaseItemPlanAutoQty(baseItemPlan);
	}

    
}
