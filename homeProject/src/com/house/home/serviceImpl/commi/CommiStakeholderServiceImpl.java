package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiStakeholderDao;
import com.house.home.entity.commi.CommiStakeholder;
import com.house.home.service.commi.CommiStakeholderService;

@SuppressWarnings("serial")
@Service
public class CommiStakeholderServiceImpl extends BaseServiceImpl implements CommiStakeholderService {

	@Autowired
	private CommiStakeholderDao commiStakeholderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiStakeholder commiStakeholder){
		return commiStakeholderDao.findPageBySql(page, commiStakeholder);
	}

}
