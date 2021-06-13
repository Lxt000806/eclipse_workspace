package com.house.home.serviceImpl.oa;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.oa.OaAllDao;
import com.house.home.entity.oa.OaAll;
import com.house.home.service.oa.OaAllService;

@SuppressWarnings("serial")
@Service
public class OaAllServiceImpl extends BaseServiceImpl implements OaAllService {

	@Autowired
	private OaAllDao oaAllDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, OaAll oaAll){
		return oaAllDao.findPageBySql(page, oaAll);
	}

}
