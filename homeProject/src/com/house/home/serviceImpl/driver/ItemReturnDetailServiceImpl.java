package com.house.home.serviceImpl.driver;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.driver.ItemReturnDetailDao;
import com.house.home.entity.driver.ItemReturnDetail;
import com.house.home.service.driver.ItemReturnDetailService;

@SuppressWarnings("serial")
@Service
public class ItemReturnDetailServiceImpl extends BaseServiceImpl implements ItemReturnDetailService {

	@Autowired
	private ItemReturnDetailDao itemReturnDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReturnDetail itemReturnDetail){
		return itemReturnDetailDao.findPageBySql(page, itemReturnDetail);
	}

	@Override
	public Map<String, Object> getReturnDetailById(String No) {
		return itemReturnDetailDao.getReturnDetailById(No);
	}

	@Override
	public List<Map<String, Object>> getMaterialList(String No) {
		return itemReturnDetailDao.getMaterialList(No) ;
	}

	@Override
	public List<Map<String, Object>> getReturnMaterial(String No) {
		// TODO Auto-generated method stub
		return itemReturnDetailDao.getReturnMaterial(No) ;
	}

	@Override
	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id) {
		return itemReturnDetailDao.findPageByNo(page,id);
	}

	@Override
	public Map<String, Object> getByPk(int pk) {
		return itemReturnDetailDao.getByPk(pk);
	}

	@Override
	public Page<Map<String, Object>> findReturnDetailByNo(
			Page<Map<String, Object>> page, ItemReturnDetail itemReturnDetail) {
		return itemReturnDetailDao.findReturnDetailByNo(page, itemReturnDetail);
	}


}
