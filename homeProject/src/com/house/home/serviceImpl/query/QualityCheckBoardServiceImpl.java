package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.QualityCheckBoardDao;
import com.house.home.entity.project.CustWorker;
import com.house.home.service.query.QualityCheckBoardService;

@Service
@SuppressWarnings("serial")
public class QualityCheckBoardServiceImpl extends BaseServiceImpl implements QualityCheckBoardService{
	@Autowired
	private QualityCheckBoardDao qualityCheckBoardDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, CustWorker custWorker) {
		return qualityCheckBoardDao.findPageBySql(page, custWorker);
	}

	@Override
	public Page<Map<String, Object>> findDetailBySql(Page<Map<String, Object>> page, CustWorker custWorker) {
		return qualityCheckBoardDao.findDetailBySql(page, custWorker);
	}

}
