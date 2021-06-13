/**
 * 
 */
package com.house.home.serviceImpl.basic;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.UomDao;
import com.house.home.entity.basic.Uom;
import com.house.home.service.basic.UomService;

/**
 * @author lenovo-l821
 *
 */
@Service
public class UomServiceImpl extends BaseServiceImpl implements UomService {

	@Autowired 
	private UomDao uomDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Uom uom) {
		// TODO Auto-generated method stub
		return uomDao.findPageBySql(page, uom);
	}

	@Override
	public boolean valideUom(String id) {
		// TODO Auto-generated method stub
		return uomDao.valideUom(id);
	}
	
	
}
