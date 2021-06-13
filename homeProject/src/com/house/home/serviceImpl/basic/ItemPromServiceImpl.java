package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemPromDao;
import com.house.home.entity.basic.ItemProm;
import com.house.home.service.basic.ItemPromService;

@SuppressWarnings("serial")
@Service
public class ItemPromServiceImpl extends BaseServiceImpl implements ItemPromService {

	@Autowired
	private ItemPromDao itemPromDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemProm itemProm){
		return itemPromDao.findPageBySql(page, itemProm);
	}

	@Override
	public List<Map<String, Object>> checkIsExists(ItemProm itemProm) {
		return itemPromDao.checkIsExists(itemProm);
	}

	@Override
	public List<Map<String, Object>> findListByPk(Integer pk) {
		return itemPromDao.findListByPk(pk);
	}

	@Override
	public List<Map<String, Object>> checkIsItemCode(ItemProm itemProm) {
		return itemPromDao.checkIsItemCode(itemProm);
	}

	@Override
	public void updatePrice(ItemProm itemProm) {
		itemPromDao.updatePrice(itemProm);
	}

	@Override
	public Page<Map<String, Object>> itemQuery(Page<Map<String, Object>> page,ItemProm itemProm, String itemRight) {
		return itemPromDao.itemQuery(page, itemProm, itemRight);
	}

	@Override
	public List<Map<String, Object>> findItemData(String itemCode) {
		return itemPromDao.findItemData(itemCode);
	}

	@Override
	public Map<String, Object> importExcel(ItemProm itemProm) {
		return itemPromDao.importExcel(itemProm);
	}
}
