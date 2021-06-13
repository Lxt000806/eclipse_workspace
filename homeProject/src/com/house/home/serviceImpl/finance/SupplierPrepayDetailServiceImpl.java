package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.BaseItemPlanBakDao;
import com.house.home.dao.design.FixAreaDao;
import com.house.home.dao.finance.SupplierPrepayDetailDao;
import com.house.home.entity.design.FixArea;
import com.house.home.entity.finance.SupplierPrepayDetail;
import com.house.home.service.design.FixAreaService;
import com.house.home.service.finance.SupplierPrepayDetailService;

@SuppressWarnings("serial")
@Service
public class SupplierPrepayDetailServiceImpl extends BaseServiceImpl implements SupplierPrepayDetailService {
	@Autowired
	private SupplierPrepayDetailDao supplierPrepayDetailDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			SupplierPrepayDetail supplierPrepayDetail) {
	
		return supplierPrepayDetailDao.findPageBySql(page, supplierPrepayDetail);
	}

	
}
