package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppTempAreaDetailDao;
import com.house.home.entity.insales.ItemAppTempAreaDetail;
import com.house.home.service.insales.ItemAppTempAreaDetailService;

@SuppressWarnings("serial")
@Service
public class ItemAppTempAreaDetailServiceImpl extends BaseServiceImpl implements ItemAppTempAreaDetailService {

	@Autowired
	private ItemAppTempAreaDetailDao itemAppTempAreaDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTempAreaDetail itemAppTempAreaDetail){
		return itemAppTempAreaDetailDao.findPageBySql(page, itemAppTempAreaDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, String iatNo, String custCode) {
		// TODO Auto-generated method stub
		return itemAppTempAreaDetailDao.findPageBySql(page,iatNo,custCode) ;
	}

	@Override
	public Page<Map<String, Object>> findConfItemTypePageBySql(
			Page<Map<String, Object>> page, String batchCode, String custCode) {
		// TODO Auto-generated method stub
		return itemAppTempAreaDetailDao.findConfItemTypePageBySql(page,batchCode,custCode);
	}

	@Override
	public Page<Map<String, Object>> findConfItemCodePageBySql(
			Page<Map<String, Object>> page, String confCode, String custCode,String flag,String m_umStatus) {
		// TODO Auto-generated method stub
		return itemAppTempAreaDetailDao.findConfItemCodePageBySql(page,confCode.substring(0,confCode.length() - 1),custCode,flag,m_umStatus);
	}

	@Override
	public Page<Map<String, Object>> findCheckAppItemAllPageBySql(
			Page<Map<String, Object>> page, String appItemCodes,String itemCode , String custCode,String isSetItem) {
		// TODO Auto-generated method stub
		return itemAppTempAreaDetailDao.findCheckAppItemAllPageBySql(page,appItemCodes.substring(0,appItemCodes.length() - 1),itemCode,custCode,isSetItem);
	}
	
	
	
	

}
