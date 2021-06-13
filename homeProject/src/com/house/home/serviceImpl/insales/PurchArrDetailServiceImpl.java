package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PurchArrDetailDao;
import com.house.home.entity.insales.PurchArrDetail;
import com.house.home.service.insales.PurchArrDetailService;

@SuppressWarnings("serial")
@Service
public class PurchArrDetailServiceImpl extends BaseServiceImpl implements PurchArrDetailService {

	@Autowired
	private PurchArrDetailDao purchArrDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchArrDetail purchArrDetail){
		return purchArrDetailDao.findPageBySql(page, purchArrDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageByPano(
			Page<Map<String, Object>> page, String pano) {
		return purchArrDetailDao.findPageByPano(page,pano);
	}

}
