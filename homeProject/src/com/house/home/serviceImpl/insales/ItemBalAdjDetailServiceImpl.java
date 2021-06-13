package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemBalAdjDetailDao;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.service.insales.ItemBalAdjDetailService;

@SuppressWarnings("serial")
@Service
public class ItemBalAdjDetailServiceImpl extends BaseServiceImpl implements ItemBalAdjDetailService{
	@Autowired
	private ItemBalAdjDetailDao itemBalAdjDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBalAdjDetail itemBalAdjDetail){
		return itemBalAdjDetailDao.findPageBySql(page, itemBalAdjDetail);
	}
									
	public Page<Map<String,Object>> findSupplierPageBySql(Page<Map<String,Object>> page, ItemBalAdjDetail itemBalAdjDetail){
		return itemBalAdjDetailDao.findSupplierPageBySql(page, itemBalAdjDetail);
	}

	public Map<String, Object> getAvgCost(String whCode, String itCode){
		return itemBalAdjDetailDao.getAvgCost(whCode,itCode);
	}
	@Override
	public Map<String,Object> getPosiQty(String whCode,String itCode){
		return itemBalAdjDetailDao.getPosiQty(whCode,itCode);
	}
	@Override
	public Map<String,Object> getAllQty(String whCode,String itCode){
		return itemBalAdjDetailDao.getAllQty(whCode,itCode);
	}

	@Override
	public Page<Map<String, Object>> detailQuery(
			Page<Map<String, Object>> page, ItemBalAdjDetail itemBalAdjDetail) {
		return itemBalAdjDetailDao.detailQuery(page, itemBalAdjDetail);
	}
	
	
}
