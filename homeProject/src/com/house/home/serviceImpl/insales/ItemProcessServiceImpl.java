package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemProcessDao;
import com.house.home.entity.insales.ItemProcess;
import com.house.home.entity.insales.ItemProcessDetail;
import com.house.home.service.insales.ItemProcessService;

@SuppressWarnings("serial")
@Service
public class ItemProcessServiceImpl extends BaseServiceImpl implements ItemProcessService {

	@Autowired
	private ItemProcessDao itemProcessDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemProcess itemProcess){
		return itemProcessDao.findPageBySql(page, itemProcess);
	}

	@Override
	public Page<Map<String, Object>> getSourceItemByTransform(
			Page<Map<String, Object>> page, ItemProcessDetail itemProcessDetail) {
		return itemProcessDao.getSourceItemByTransform(page, itemProcessDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_itemProcessDetail(
			Page<Map<String, Object>> page, ItemProcessDetail itemProcessDetail) {
		return itemProcessDao.findPageBySql_itemProcessDetail(page, itemProcessDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_itemProcessSourceDetail(
			Page<Map<String, Object>> page, ItemProcessDetail itemProcessDetail) {
		return itemProcessDao.findPageBySql_itemProcessSourceDetail(page, itemProcessDetail);
	}

	@Override
	public Result doSave(ItemProcess itemProcess) {
		return itemProcessDao.doSave(itemProcess);
	}

}
