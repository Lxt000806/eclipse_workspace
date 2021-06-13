package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CzrzDao;
import com.house.home.entity.basic.Czrz;
import com.house.home.service.basic.CzrzService;

@SuppressWarnings("serial")
@Service
public class CzrzServiceImpl extends BaseServiceImpl implements CzrzService {

	@Autowired
	private CzrzDao czrzDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Czrz czrz){
		return czrzDao.findPageBySql(page, czrz);
	}

}
