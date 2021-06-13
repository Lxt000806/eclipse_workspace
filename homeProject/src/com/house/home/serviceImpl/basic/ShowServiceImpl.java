package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.dao.basic.ShowDao;
import com.house.home.entity.basic.APIKey;
import com.house.home.service.basic.ShowService;

@SuppressWarnings("serial")
@Service
public class ShowServiceImpl extends BaseServiceImpl implements ShowService {

	@Autowired
	private ShowDao showDao;


	@Override
	public Page<Map<String, Object>> getShowBuilds(Page<Map<String, Object>> page, GetShowBuildsEvt evt){
		return showDao.getShowBuilds(page, evt);
	}

	@Override
	public List<Map<String, Object>> getPrjProgConfirm(String custCode){
		return showDao.getPrjProgConfirm(custCode);
	}
	
	@Override
	public List<Map<String, Object>> getPrjProgConfirmPhoto(String prjProgConfirmNo, Integer number){
		return showDao.getPrjProgConfirmPhoto(prjProgConfirmNo, number);
	}
	
	@Override
	public List<Map<String, Object>> getCityAppUrlList(Double longitude, Double latitude){
		return showDao.getCityAppUrlList(longitude, latitude);
	}
	
	@Override
	public List<Map<String, Object>> getPrjItem1List(){
		return showDao.getPrjItem1List();
	}
	
	@Override
	public Page<Map<String, Object>> getDesignDemoList(Page<Map<String, Object>> page, GetDesignDemoListEvt evt){
		return showDao.getDesignDemoList(page, evt);
	}
	
	@Override
	public Map<String, Object> getDesignDemoDetail(String no){
		return showDao.getDesignDemoDetail(no);
	}
	
	@Override
	public List<Map<String, Object>> getDesignDemoDetailPhotos(String no){
		return showDao.getDesignDemoDetailPhotos(no);
	}
	
	@Override
	public Map<String, Object> getAPIKey(String apiKey){
		return showDao.getAPIKey(apiKey);
	}
}
