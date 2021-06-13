package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiNotProvideCustStakeholderDao;
import com.house.home.entity.commi.CommiNotProvideCustStakeholder;
import com.house.home.service.commi.CommiNotProvideCustStakeholderService;

@SuppressWarnings("serial")
@Service
public class CommiNotProvideCustStakeholderServiceImpl extends BaseServiceImpl implements CommiNotProvideCustStakeholderService {

	@Autowired
	private CommiNotProvideCustStakeholderDao commiNotProvideCustStakeholderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiNotProvideCustStakeholder commiNotProvideCustStakeholder){
		return commiNotProvideCustStakeholderDao.findPageBySql(page, commiNotProvideCustStakeholder);
	}

}
