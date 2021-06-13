package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CmpCustTypeDao;
import com.house.home.entity.basic.CmpCustType;
import com.house.home.service.basic.CmpCustTypeService;

@SuppressWarnings("serial")
@Service
public class CmpCustTypeServiceImpl extends BaseServiceImpl implements CmpCustTypeService {

	@Autowired
	private CmpCustTypeDao cmpCustTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CmpCustType cmpCustType){
		return cmpCustTypeDao.findPageBySql(page, cmpCustType);
	}

	@Override
	public List<Map<String, Object>> checkExist(CmpCustType cmpCustType) {
		return cmpCustTypeDao.checkExistType(cmpCustType);
	}

	@Override
	public String getLogoFile(String custCode) {
		
		return cmpCustTypeDao.getLogoFile(custCode);
	}

}
