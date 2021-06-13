package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppSendDetailDao;
import com.house.home.entity.insales.ItemAppSendDetail;
import com.house.home.service.insales.ItemAppSendDetailService;

@SuppressWarnings("serial")
@Service
public class ItemAppSendDetailServiceImpl extends BaseServiceImpl implements ItemAppSendDetailService {

	@Autowired
	private ItemAppSendDetailDao itemAppSendDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppSendDetail itemAppSendDetail){
		return itemAppSendDetailDao.findPageBySql(page, itemAppSendDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String no) {
		return itemAppSendDetailDao.findPageByNo(page,no);
	}

	@Override
	public Page<Map<String, Object>> findSendDetailByNo(
			Page<Map<String, Object>> page, ItemAppSendDetail itemAppSendDetail) {
		return itemAppSendDetailDao.findSendDetailByNo(page, itemAppSendDetail);
	}

	@Override
	public Page<Map<String, Object>> findDetailBySqlNoAPP(
			Page<Map<String, Object>> page, ItemAppSendDetail itemAppSendDetail) {
		return itemAppSendDetailDao.findDetailBySqlNoAPP(page, itemAppSendDetail);
	}

}
