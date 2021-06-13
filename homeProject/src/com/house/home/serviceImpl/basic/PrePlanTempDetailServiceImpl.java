package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PrePlanTempDetailDao;
import com.house.home.entity.basic.PrePlanTempDetail;
import com.house.home.service.basic.PrePlanTempDetailService;

@SuppressWarnings("serial")
@Service
public class PrePlanTempDetailServiceImpl extends BaseServiceImpl implements PrePlanTempDetailService {

	@Autowired
	private PrePlanTempDetailDao prePlanTempDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlanTempDetail prePlanTempDetail){
		return prePlanTempDetailDao.findPageBySql(page, prePlanTempDetail);
	}

	@Override
	public List<Map<String, Object>> findDetailByNo(PrePlanTempDetail prePlanTempDetail) {
		return prePlanTempDetailDao.findDetailByNo(prePlanTempDetail);
	}

	@Override
	public List<Map<String, Object>> getQtyByCutType(PrePlanTempDetail prePlanTempDetail) {
		return prePlanTempDetailDao.getQtyByCutType(prePlanTempDetail);
	}

}
