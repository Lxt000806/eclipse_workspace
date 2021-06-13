package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.GetCustomerInfoEvt;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.dao.basic.APIDao;
import com.house.home.dao.basic.ShowDao;
import com.house.home.entity.basic.APIKey;
import com.house.home.service.basic.APIService;
import com.house.home.service.basic.ShowService;

@SuppressWarnings("serial")
@Service
public class APIServiceImpl extends BaseServiceImpl implements APIService {

	@Autowired
	private APIDao apiDao;


	@Override
	public Page<Map<String, Object>> getCustomerInfo(Page<Map<String, Object>> page, GetCustomerInfoEvt evt){
		return this.apiDao.getCustomerInfo(page, evt);
	}

	@Override
	public List<Map<String, Object>> getCustomerDetailInfo(String custCode, Date dateFrom, Date dateTo, String prjItems){
		return this.apiDao.getCustomerDetailInfo(custCode, dateFrom, dateTo, prjItems);
	}

	@Override
	public List<Map<String, Object>> getPrjProgPhoto(String custCode){
		return this.apiDao.getPrjProgPhoto(custCode);
	}
}
