package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemSendBatchDao;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.service.insales.ItemSendBatchService;

@SuppressWarnings("serial")
@Service
public class ItemSendBatchServiceImpl extends BaseServiceImpl implements ItemSendBatchService {

	@Autowired
	private ItemSendBatchDao itemSendBatchDao;

	@Override
	public Page<Map<String,Object>> getJqGrid(Page<Map<String,Object>> page,ItemSendBatch itemSendBatch){
		return itemSendBatchDao.getJqGrid(page,itemSendBatch);
	}

	@Override
	public Page<Map<String,Object>> goReturnDetailJqGrid(Page<Map<String,Object>> page,ItemSendBatch itemSendBatch){
		return itemSendBatchDao.goReturnDetailJqGrid(page,itemSendBatch);
	}

	@Override
	public List<Map<String, Object>> checkDriver(ItemSendBatch itemSendBatch) {
		return itemSendBatchDao.checkDriver(itemSendBatch);
	}

	@Override
	public Result doSaveProc(ItemSendBatch itemSendBatch) {
		return itemSendBatchDao.doSaveProc(itemSendBatch);
	}
	
}
