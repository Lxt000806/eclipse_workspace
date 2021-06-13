package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BaseBatchTempDao;
import com.house.home.entity.basic.BaseBatchTemp;
import com.house.home.service.basic.BaseBatchTempService;

@SuppressWarnings("serial")
@Service
public class BaseBatchTempServiceImpl extends BaseServiceImpl implements BaseBatchTempService {

	@Autowired
	private BaseBatchTempDao baseBatchTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseBatchTemp baseBatchTemp){
		return baseBatchTempDao.findPageBySql(page, baseBatchTemp);
	}

	@Override
	public Page<Map<String, Object>> goAreaJqGrid(Page<Map<String, Object>> page, BaseBatchTemp baseBatchTemp) {
		return baseBatchTempDao.goAreaJqGrid(page, baseBatchTemp);
	}

	@Override
	public Page<Map<String, Object>> goItemJqGrid(Page<Map<String, Object>> page, BaseBatchTemp baseBatchTemp) {
		return baseBatchTempDao.goItemJqGrid(page, baseBatchTemp);
	}

	@Override
	public List<Map<String, Object>> checkExistType(BaseBatchTemp baseBatchTemp) {
		return baseBatchTempDao.checkExistType(baseBatchTemp);
	}

	@Override
	public List<Map<String, Object>> checkExistDescr(BaseBatchTemp baseBatchTemp) {
		return baseBatchTempDao.checkExistDescr(baseBatchTemp);
	}

	@Override
	public Result doSaveProc(BaseBatchTemp baseBatchTemp) {
		return baseBatchTempDao.doSaveProc(baseBatchTemp);
	}

	@Override
	public void updateDispSeq(BaseBatchTemp baseBatchTemp) {
		 baseBatchTempDao.updateDispSeq(baseBatchTemp);
	}

	@Override
	public List<Map<String, Object>> checkExistTempDescr(BaseBatchTemp baseBatchTemp) {
		return baseBatchTempDao.checkExistTempDescr(baseBatchTemp);
	}

}
