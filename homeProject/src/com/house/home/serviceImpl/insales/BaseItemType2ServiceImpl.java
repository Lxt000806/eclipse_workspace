package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.BaseItemType2Dao;
import com.house.home.entity.insales.BaseItemType2;
import com.house.home.service.insales.BaseItemType2Service;

@SuppressWarnings("serial")
@Service
public class BaseItemType2ServiceImpl extends BaseServiceImpl implements BaseItemType2Service {

	@Autowired
	private BaseItemType2Dao baseItemType2Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemType2 baseItemType2){
		return baseItemType2Dao.findPageBySql(page, baseItemType2);
	}

	@Override
	public boolean valideBaseItemType(String code) {
		// TODO Auto-generated method stub
		return baseItemType2Dao.validBaseItemCode(code);
	}

	@Override
	public Page<Map<String, Object>> getBaseItemType2(
			Page<Map<String, Object>> page, String code) {
		return baseItemType2Dao.getBaseItemType2(page, code);
	}
}
