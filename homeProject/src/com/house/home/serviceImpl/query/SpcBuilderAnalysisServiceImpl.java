package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemSignCountDao;
import com.house.home.dao.query.SpcBuilderAnalysisDao;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemSignCountService;
import com.house.home.service.query.SpcBuilderAnalysisService;
@Service
@SuppressWarnings("serial")
public class SpcBuilderAnalysisServiceImpl extends BaseServiceImpl implements
    SpcBuilderAnalysisService {
	@Autowired
	private SpcBuilderAnalysisDao spcBuilderAnalysisDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,SpcBuilder spcBuilder) {
		return spcBuilderAnalysisDao.findPageBySql(page,spcBuilder);
	}

	@Override
	public Page<Map<String, Object>> findSpcBuilderAnalysisDetailPageBySql(
			Page<Map<String, Object>> page, SpcBuilder spcBuilder) {
		return spcBuilderAnalysisDao.findSpcBuilderAnalysisDetailPageBySql(page,spcBuilder);
	}

}
