package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BasePlanTempDetailDao;
import com.house.home.entity.basic.BasePlanTempDetail;
import com.house.home.service.basic.BasePlanTempDetailService;

@SuppressWarnings("serial")
@Service
public class BasePlanTempDetailServiceImpl extends BaseServiceImpl implements BasePlanTempDetailService {

	@Autowired
	private BasePlanTempDetailDao basePlanTempDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BasePlanTempDetail basePlanTempDetail){
		return basePlanTempDetailDao.findPageBySql(page, basePlanTempDetail);
	}

	@Override
	public List<Map<String, Object>> findDetailByNo(BasePlanTempDetail basePlanTempDetail) {
		return basePlanTempDetailDao.findDetailByNo(basePlanTempDetail);
	}

}
