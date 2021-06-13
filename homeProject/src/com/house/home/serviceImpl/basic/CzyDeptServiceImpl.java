package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CzyDeptDao;
import com.house.home.entity.basic.CzyDept;
import com.house.home.service.basic.CzyDeptService;

@SuppressWarnings("serial")
@Service
public class CzyDeptServiceImpl extends BaseServiceImpl implements CzyDeptService {

	@Autowired
	private CzyDeptDao czyDeptDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyDept czyDept){
		return czyDeptDao.findPageBySql(page, czyDept);
	}

	public List<CzyDept> findByCzybh(String czybh) {
		return czyDeptDao.findByCzybh(czybh);
	}

	public void deleteByCzybh(String czybh) {
		czyDeptDao.deleteByCzybh(czybh);
	}

}
