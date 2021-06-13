package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SoftPerfPrePerDao;
import com.house.home.entity.basic.SoftPerfPrePer;
import com.house.home.service.basic.SoftPerfPrePerService;

@SuppressWarnings("serial")
@Service
public class SoftPerfPrePerServiceImpl extends BaseServiceImpl implements SoftPerfPrePerService {

	@Autowired
	private SoftPerfPrePerDao softPerfPrePerDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftPerfPrePer softPerfPrePer){
		return softPerfPrePerDao.findPageBySql(page, softPerfPrePer);
	}

	@Override
	public boolean hasDepartment1(SoftPerfPrePer softPerfPrePer) {
		return softPerfPrePerDao.hasDepartment1(softPerfPrePer);
	}

}
