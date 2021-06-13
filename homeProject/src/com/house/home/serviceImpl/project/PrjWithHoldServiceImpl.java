package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.PrjWithHoldDao;
import com.house.home.entity.project.PrjWithHold;
import com.house.home.service.project.PrjWithHoldService;

@SuppressWarnings("serial")
@Service
public class PrjWithHoldServiceImpl extends BaseServiceImpl implements PrjWithHoldService {

	@Autowired
	private PrjWithHoldDao prjWithHoldDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjWithHold prjWithHold){
		return prjWithHoldDao.findPageBySql(page, prjWithHold);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_ykd(
			Page<Map<String, Object>> page, PrjWithHold prjWithHold) {
		return prjWithHoldDao.findPageBySql_ykd(page,prjWithHold);
	}

	@Override
	public Map<String, Object> findByPk(int pk) {
		return prjWithHoldDao.findByPk(pk);
	}

	@Override
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, PrjWithHold prjWithHold) {
		return prjWithHoldDao.findCodePageBySql(page, prjWithHold);
	}

}
