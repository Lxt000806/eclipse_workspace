package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ConfItemTypeDtDao;
import com.house.home.entity.project.ConfItemTypeDt;
import com.house.home.service.project.ConfItemTypeDtService;

@SuppressWarnings("serial")
@Service
public class ConfItemTypeDtServiceImpl extends BaseServiceImpl implements ConfItemTypeDtService {

	@Autowired
	private ConfItemTypeDtDao confItemTypeDtDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemTypeDt confItemTypeDt){
		return confItemTypeDtDao.findPageBySql(page, confItemTypeDt);
	}

}
