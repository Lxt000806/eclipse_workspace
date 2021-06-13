package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ConfItemTypeDao;
import com.house.home.entity.project.ConfItemType;
import com.house.home.service.project.ConfItemTypeService;

@SuppressWarnings("serial")
@Service
public class ConfItemTypeServiceImpl extends BaseServiceImpl implements ConfItemTypeService {

	@Autowired
	private ConfItemTypeDao confItemTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemType confItemType){
		return confItemTypeDao.findPageBySql(page, confItemType);
	}

}
