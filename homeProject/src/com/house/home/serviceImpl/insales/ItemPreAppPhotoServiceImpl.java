package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemPreAppPhotoDao;
import com.house.home.entity.insales.ItemPreAppPhoto;
import com.house.home.service.insales.ItemPreAppPhotoService;

@SuppressWarnings("serial")
@Service
public class ItemPreAppPhotoServiceImpl extends BaseServiceImpl implements ItemPreAppPhotoService {

	@Autowired
	private ItemPreAppPhotoDao itemPreAppPhotoDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreAppPhoto itemPreAppPhoto){
		return itemPreAppPhotoDao.findPageBySql(page, itemPreAppPhoto);
	}

	@Override
	public List<Map<String, Object>> getPhotoList(String no) {
		return itemPreAppPhotoDao.getPhotoList(no);
	}

	@Override
	public ItemPreAppPhoto getByPhotoName(String id) {
		return itemPreAppPhotoDao.getByPhotoName(id);
	}

}
