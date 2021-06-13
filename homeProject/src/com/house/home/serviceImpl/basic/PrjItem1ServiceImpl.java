package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PrjItem1Dao;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.service.basic.PrjItem1Service;

@SuppressWarnings("serial")
@Service
public class PrjItem1ServiceImpl extends BaseServiceImpl implements PrjItem1Service {

	@Autowired
	private PrjItem1Dao prjItem1Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjItem1 prjItem1){
		return prjItem1Dao.findPageBySql(page, prjItem1);
	}

}
