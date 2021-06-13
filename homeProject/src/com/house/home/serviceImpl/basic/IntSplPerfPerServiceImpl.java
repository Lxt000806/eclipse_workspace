package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.IntSplPerfPerDao;
import com.house.home.entity.basic.IntSplPerfPer;
import com.house.home.service.basic.IntSplPerfPerService;

@SuppressWarnings("serial")
@Service
public class IntSplPerfPerServiceImpl extends BaseServiceImpl implements IntSplPerfPerService {

	@Autowired
	private IntSplPerfPerDao intSplPerfPerDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntSplPerfPer intSplPerfPer){
		return intSplPerfPerDao.findPageBySql(page, intSplPerfPer);
	}

}
