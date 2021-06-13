package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiCustStakeholderProvideDao;
import com.house.home.entity.commi.CommiCustStakeholderProvide;
import com.house.home.service.commi.CommiCustStakeholderProvideService;

@SuppressWarnings("serial")
@Service
public class CommiCustStakeholderProvideServiceImpl extends BaseServiceImpl implements CommiCustStakeholderProvideService {

	@Autowired
	private CommiCustStakeholderProvideDao commiCustStakeholderProvideDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholderProvide commiCustStakeholderProvide){
		return commiCustStakeholderProvideDao.findPageBySql(page, commiCustStakeholderProvide);
	}

}
