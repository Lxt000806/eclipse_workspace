package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CzySpcBuilderDao;
import com.house.home.entity.basic.CzySpcBuilder;
import com.house.home.service.basic.CzySpcBuilderService;

@SuppressWarnings("serial")
@Service
public class CzySpcBuilderServiceImpl extends BaseServiceImpl implements CzySpcBuilderService {

	@Autowired
	private CzySpcBuilderDao czySpcBuilderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzySpcBuilder czySpcBuilder){
		return czySpcBuilderDao.findPageBySql(page, czySpcBuilder);
	}

	@Override
	public CzySpcBuilder getByCzybhAndSpcBuilder(String czybh, String spcBuilder) {
		return czySpcBuilderDao.getByCzybhAndSpcBuilder(czybh, spcBuilder);
	}

}
