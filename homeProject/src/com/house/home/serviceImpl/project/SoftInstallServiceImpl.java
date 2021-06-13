package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.SoftInstallDao;
import com.house.home.entity.project.SoftInstall;
import com.house.home.service.project.SoftInstallService;

@SuppressWarnings("serial")
@Service
public class SoftInstallServiceImpl extends BaseServiceImpl implements SoftInstallService {

	@Autowired
	private SoftInstallDao softInstallDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftInstall softInstall){
		return softInstallDao.findPageBySql(page, softInstall);
	}

}
