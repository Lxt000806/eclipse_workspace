package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemPreAppDetailDao;
import com.house.home.entity.insales.ItemPreAppDetail;
import com.house.home.service.insales.ItemPreAppDetailService;

@SuppressWarnings("serial")
@Service
public class ItemPreAppDetailServiceImpl extends BaseServiceImpl implements ItemPreAppDetailService {

	@Autowired
	private ItemPreAppDetailDao itemPreAppDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreAppDetail itemPreAppDetail){
		return itemPreAppDetailDao.findPageBySql(page, itemPreAppDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id, String supplCode) {
		return itemPreAppDetailDao.findPageByNo(page,id,supplCode);
	}

	@Override
	public List<ItemPreAppDetail> findByNo(String no) {
		return itemPreAppDetailDao.findByNo(no);
	}

	@Override
	public Map<String,Object> getByPk(int pk) {
		return itemPreAppDetailDao.getByPk(pk);
	}

	@Override
	public String existsChange(String no) {
		return itemPreAppDetailDao.existsChange(no);
	}

	@Override
	public List<Map<String, Object>> findSqlByNo(String no) {
		return itemPreAppDetailDao.findSqlByNo(no);
	}

	@Override
	public List<Map<String, Object>> existsOutSetItem(String no) {
		return itemPreAppDetailDao.existsOutSetItem(no);
	}

	@Override
	public List<Map<String, Object>> existsInSetItem(String no) {
		return itemPreAppDetailDao.existsInSetItem(no);
	}

}
