package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemTransferHeaderDao;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemTransferHeader;
import com.house.home.service.insales.ItemTransferHeaderService;
@SuppressWarnings("serial")
@Service
public class ItemTransferHeaderServiceImpl extends BaseServiceImpl implements ItemTransferHeaderService{
	@Autowired
	private ItemTransferHeaderDao itemTransferHeaderDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemTransferHeader itemTransferHeader) {
		return itemTransferHeaderDao.findPageBySql(page, itemTransferHeader);
	}
	
	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page,
			ItemTransferHeader itemTransferHeader) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.findDetailPageBySql(page, itemTransferHeader);
	}



	@Override
	public Map<String, Object> getFromQty(String itCode, String fromWHCode) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.getFromQty(itCode, fromWHCode);
	}
	
	@Override
	public String getToQty(String itCode, String toWHCode) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.getToQty(itCode, toWHCode);
	}

	@Override
	public String getPostQty(String itCode, String fromWHCode) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.getPosiQty(itCode, fromWHCode);
	}

	@Override
	public Result doSaveItemTransfer(ItemTransferHeader itemTransferHeader) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.saveItemTransfer(itemTransferHeader);
	}
	
	@Override
	public Result doUpdateItemTransfer(ItemTransferHeader itemTransferHeader) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.saveItemTransfer(itemTransferHeader);
	}
	
	@Override
	public Result doSaveCheckItemTransfer(ItemTransferHeader itemTransferHeader) {
		// TODO Auto-generated method stub
		return itemTransferHeaderDao.saveItemTransfer(itemTransferHeader);
	}

	@Override
	public Page<Map<String, Object>> findOrderBySupplPageBySql(
			Page<Map<String, Object>> page, ItemTransferHeader itemTransferHeader) {
		return itemTransferHeaderDao.findOrderBySupplPageBySql(page, itemTransferHeader);
	}

	
	
	
}
