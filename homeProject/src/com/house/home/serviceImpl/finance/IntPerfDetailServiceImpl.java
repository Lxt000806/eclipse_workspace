package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.IntPerfDetailDao;
import com.house.home.entity.finance.IntPerfDetail;
import com.house.home.service.finance.IntPerfDetailService;

@SuppressWarnings("serial")
@Service
public class IntPerfDetailServiceImpl extends BaseServiceImpl implements IntPerfDetailService {

	@Autowired
	private IntPerfDetailDao intPerfDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntPerfDetail intPerfDetail){
		return intPerfDetailDao.findPageBySql(page, intPerfDetail);
	}

	@Override
	public Map<String, Object> findMapBySql(String pk) {
		return intPerfDetailDao.findMapBySql(pk);
	}

	@Override
	public void doUpdate(IntPerfDetail intPerfDetail) {
		intPerfDetailDao.doUpdate(intPerfDetail);
	}

	@Override
	public void doGetIntPerDetail(String no, String lastUpdatedBy,String prjPerfNo) {
		intPerfDetailDao.doGetIntPerDetail(no, lastUpdatedBy,prjPerfNo);
	}

	@Override
	public Page<Map<String, Object>> findReportPageBySql(Page<Map<String, Object>> page, IntPerfDetail intPerfDetail) {
		return intPerfDetailDao.findReportPageBySql(page, intPerfDetail);
	}

	@Override
	public Map<String, Object> findDisc(IntPerfDetail intPerfDetail) {
		return intPerfDetailDao.findDisc(intPerfDetail);
	}

}
