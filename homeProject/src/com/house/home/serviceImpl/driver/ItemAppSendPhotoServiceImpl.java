package com.house.home.serviceImpl.driver;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.driver.ItemAppSendPhotoDao;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.service.driver.ItemAppSendPhotoService;
@SuppressWarnings("serial")
@Service
public class ItemAppSendPhotoServiceImpl extends BaseServiceImpl implements
		ItemAppSendPhotoService {
	@Autowired
	private ItemAppSendPhotoDao itemAppSendPhotoDao;
	@Override
	public List<Map<String, Object>> getPhotoList(String no,String type) {
		// TODO Auto-generated method stub
		return itemAppSendPhotoDao.getPhotoList(no,type);
	}
	@Override
	public ItemSendPhoto getByPhotoName(String id) {
		// TODO Auto-generated method stub
		return itemAppSendPhotoDao.getByPhotoName(id);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemSendPhoto itemSendPhoto) {
		return itemAppSendPhotoDao.findPageBySql(page, itemSendPhoto);
	}
	@Override
	public void delPhotoList(String id) {
		itemAppSendPhotoDao.delPhotoList(id);
	}
	

}
