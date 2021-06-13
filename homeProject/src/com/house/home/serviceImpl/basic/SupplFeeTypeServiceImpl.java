package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SupplFeeTypeDao;
import com.house.home.entity.basic.SupplFeeType;
import com.house.home.service.basic.SupplFeeTypeService;

@SuppressWarnings("serial")
@Service
public class SupplFeeTypeServiceImpl extends BaseServiceImpl implements SupplFeeTypeService {

	@Autowired
	private SupplFeeTypeDao supplFeeTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplFeeType supplFeeType){
		return supplFeeTypeDao.findPageBySql(page, supplFeeType);
	}

}
