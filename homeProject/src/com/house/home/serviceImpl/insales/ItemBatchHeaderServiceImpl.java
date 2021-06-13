package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemBatchHeaderDao;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.service.insales.ItemBatchHeaderService;

@SuppressWarnings("serial")
@Service
public class ItemBatchHeaderServiceImpl extends BaseServiceImpl implements ItemBatchHeaderService {

	@Autowired
	private ItemBatchHeaderDao itemBatchHeaderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchHeader itemBatchHeader){
		return itemBatchHeaderDao.findPageBySql(page, itemBatchHeader);
	}

	@Override
	public Result doSaveProc(ItemBatchHeader itemBatchHeader) {
		return itemBatchHeaderDao.doSaveProc(itemBatchHeader);
	}

	@Override
	public List<Map<String, Object>> getItemType2(String itCode) {
		return itemBatchHeaderDao.getItemType2(itCode);
	}

	@Override
	public boolean hasChgItemBatch(String no) {
		return itemBatchHeaderDao.hasChgItemBatch(no);
	}

}
