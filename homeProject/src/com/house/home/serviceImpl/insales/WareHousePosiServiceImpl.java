package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.OperateWareHousePosiEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.dao.insales.WareHousePosiDao;
import com.house.home.entity.insales.WareHousePosi;
import com.house.home.service.insales.WareHousePosiService;


@SuppressWarnings("serial")
@Service
public class WareHousePosiServiceImpl extends BaseServiceImpl implements WareHousePosiService {

	@Autowired
	private WareHousePosiDao wareHousePosiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHousePosi wareHousePosi){
		return wareHousePosiDao.findPageBySql(page, wareHousePosi);
	}

	@Override
	public Page<Map<String, Object>> getWareHousePosiList(
			Page<Map<String, Object>> page, String whCode, String idCode,String desc1) {
		// TODO Auto-generated method stub
		return wareHousePosiDao.getWareHousePosiList(page, whCode, idCode,desc1);
	}

	@Override
	public void operateWareHousePosi(BaseResponse respon,
			OperateWareHousePosiEvt evt) {
		wareHousePosiDao.operateWareHousePosi(respon, evt);
		
	}

	@Override
	public Page<Map<String, Object>> findPageByleftSql(
			Page<Map<String, Object>> page, WareHousePosi wareHousePosi) {
		return wareHousePosiDao.findPageByleftSql(page,wareHousePosi);
	}

	@Override
	public boolean checkDesc1PK(String desc1, Integer pk) {
		return wareHousePosiDao.checkDesc1PK(desc1,pk);
	}

	@Override
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, WareHousePosi wareHousePosi) {
		return wareHousePosiDao.findCodePageBySql(page,wareHousePosi);
	}

}
