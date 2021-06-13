package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ItemChgDetailDao;
import com.house.home.entity.project.ItemChgDetail;
import com.house.home.service.project.ItemChgDetailService;

@SuppressWarnings("serial")
@Service
public class ItemChgDetailServiceImpl extends BaseServiceImpl implements ItemChgDetailService {

	@Autowired
	private ItemChgDetailDao itemChgDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChgDetail itemChgDetail){
		return itemChgDetailDao.findPageBySql(page, itemChgDetail);
	}

	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page,String id) {
		return itemChgDetailDao.findPageByNo(page,id);
	}

	@Override
	public Page<Map<String, Object>> findPageByNo_khxx(
			Page<Map<String, Object>> page, String no, String costRight) {
		return itemChgDetailDao.findPageByNo_khxx(page,no,costRight);
	}

	@Override
	public void importDetail(Map<String, Object> data) {
		// TODO Auto-generated method stub
		itemChgDetailDao.importDetail(data);
	}

}
