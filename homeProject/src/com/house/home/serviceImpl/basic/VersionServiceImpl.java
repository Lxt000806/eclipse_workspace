package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.VersionDao;
import com.house.home.entity.basic.Version;
import com.house.home.service.basic.VersionService;

@SuppressWarnings("serial")
@Service
public class VersionServiceImpl extends BaseServiceImpl implements VersionService {

	@Autowired
	private VersionDao versionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Version version){
		return versionDao.findPageBySql(page, version);
	}

	

}
