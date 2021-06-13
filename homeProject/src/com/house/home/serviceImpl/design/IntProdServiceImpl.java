package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.IntProdDao;
import com.house.home.entity.design.IntProd;
import com.house.home.service.design.IntProdService;

@SuppressWarnings("serial")
@Service
public class IntProdServiceImpl extends BaseServiceImpl implements IntProdService {

	@Autowired
	private IntProdDao intProdDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntProd intProd){
		return intProdDao.findPageBySql(page, intProd);
	}

	@Override
	public boolean isExisted(IntProd intProd) {
		// TODO Auto-generated method stub
		return intProdDao.isExisted(intProd);
	}

}
