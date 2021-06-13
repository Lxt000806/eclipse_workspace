package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BuilderGroupDao;
import com.house.home.entity.basic.BuilderGroup;
import com.house.home.service.basic.BuilderGroupService;

@SuppressWarnings("serial")
@Service
public class BuilderGroupServiceImpl extends BaseServiceImpl implements BuilderGroupService {

	@Autowired
	private BuilderGroupDao builderGroupDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderGroup builderGroup){
		return builderGroupDao.findPageBySql(page, builderGroup);
	}
	
	public BuilderGroup getByDescr(String descr){
		return builderGroupDao.getByDescr(descr);
	}

	public List<BuilderGroup> findByNoExpired() {
		return builderGroupDao.findByNoExpired();
	}

}
