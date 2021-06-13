package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.RollDao;
import com.house.home.entity.basic.Roll;
import com.house.home.service.basic.RollService;

@SuppressWarnings("serial")
@Service
public class RollServiceImpl extends BaseServiceImpl implements RollService {

	@Autowired
	private RollDao rollDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Roll roll){
		return rollDao.findPageBySql(page, roll);
	}

	@Override
	public Map<String, Object> getByCode(String code) {
		return rollDao.getByCode(code);
	}

}
