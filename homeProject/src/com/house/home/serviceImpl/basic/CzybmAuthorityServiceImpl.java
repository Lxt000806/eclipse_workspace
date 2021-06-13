package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CzybmAuthorityDao;
import com.house.home.entity.basic.CzybmAuthority;
import com.house.home.service.basic.CzybmAuthorityService;

@SuppressWarnings("serial")
@Service
public class CzybmAuthorityServiceImpl extends BaseServiceImpl implements CzybmAuthorityService {

	@Autowired
	private CzybmAuthorityDao czybmAuthorityDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzybmAuthority czybmAuthority){
		return czybmAuthorityDao.findPageBySql(page, czybmAuthority);
	}

}
