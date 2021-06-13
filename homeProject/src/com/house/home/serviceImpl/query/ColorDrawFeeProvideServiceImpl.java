package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ColorDrawFeeProvideDao;
import com.house.home.dao.query.DdzhfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ColorDrawFeeProvideService;
import com.house.home.service.query.DdzhfxService;

@SuppressWarnings("serial")
@Service
public class ColorDrawFeeProvideServiceImpl extends BaseServiceImpl implements ColorDrawFeeProvideService {

	@Autowired
	private ColorDrawFeeProvideDao colorDrawFeeProvideDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return colorDrawFeeProvideDao.findPageBySql(page, customer);
	}
	

}
