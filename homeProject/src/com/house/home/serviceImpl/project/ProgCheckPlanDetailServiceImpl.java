package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ProgCheckPlanDetailDao;
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.project.ProgCheckPlanDetailService;

@SuppressWarnings("serial")
@Service
public class ProgCheckPlanDetailServiceImpl extends BaseServiceImpl implements ProgCheckPlanDetailService {

	@Autowired
	private ProgCheckPlanDetailDao progCheckPlanDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProgCheckPlanDetail progCheckPlanDetail){
		return progCheckPlanDetailDao.findPageBySql(page, progCheckPlanDetail);
	}

	@Override
	public ProgCheckPlanDetail getByAppPk(Integer appPk) {
		return progCheckPlanDetailDao.getByAppPk(appPk);
	}

}
