package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CarryRuleDao;
import com.house.home.entity.basic.CarryRule;
import com.house.home.entity.basic.CarryRuleFloor;
import com.house.home.entity.basic.CarryRuleItem;
import com.house.home.service.basic.CarryRuleService;

@SuppressWarnings("serial")
@Service
public class CarryRuleServiceImpl extends BaseServiceImpl implements CarryRuleService {

	@Autowired
	private CarryRuleDao carryRuleDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CarryRule carryRule) {
		return carryRuleDao.findPageBySql(page, carryRule);
	}
	
	public Page<Map<String, Object>> findPageBySqlDetailadd(
	Page<Map<String, Object>> page, CarryRuleFloor carryRuleFloor) {
		return carryRuleDao.findPageBySqlDetailadd(page, carryRuleFloor);	
	}	
		

	@Override
	public Result docarryRuleSave(CarryRule carryRule) {
		// TODO Auto-generated method stub
		return carryRuleDao.docarryRuleReturnCheckOut(carryRule);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, CarryRuleFloor carryRuleFloor) {
		// TODO Auto-generated method stub
		return carryRuleDao.findPageBySqlDetail(page, carryRuleFloor);
	}

	@Override
	public Result deleteForProc(CarryRule carryRule) {
		// TODO Auto-generated method stub
		return carryRuleDao.docarryRuleReturnCheckOut(carryRule);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlItem3(
			Page<Map<String, Object>> page, CarryRuleItem carryRuleItem) {
		// TODO Auto-generated method stub
		return carryRuleDao.findPageBySqlItem3(page, carryRuleItem);
	}

	@Override
	public CarryRule getByNo(String No,String ItemType1, String ItemType2,
			String CarryType,String DistanceType, String sendType) {
		// TODO Auto-generated method stub
		return carryRuleDao.getByNo(No,ItemType1,ItemType2,CarryType,DistanceType,sendType);
	}

	@Override
	public CarryRule getByNo2(String ItemType1, String ItemType2,
			String CarryType,String DistanceType, String sendType) {
		// TODO Auto-generated method stub
		return carryRuleDao.getByNo2(ItemType1,ItemType2,CarryType,DistanceType,sendType);
	}
	
	@Override
	public CarryRuleItem getByNo1(String FieldJson) {
		// TODO Auto-generated method stub
		return carryRuleDao.getByNo1(FieldJson);
	}

}
 
