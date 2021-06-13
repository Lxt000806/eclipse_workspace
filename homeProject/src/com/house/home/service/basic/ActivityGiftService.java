package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.DoUpdateActGiftEvt;
import com.house.home.entity.design.ActivityGift;

public interface ActivityGiftService extends BaseService {
	
	public Page<Map<String,Object>> getActivityGiftList(Page<Map<String,Object>> page, String  actNo,String ticketNo,String tokenCzyDescr);

	public Page<Map<String,Object>> getActGiftList(Page<Map<String,Object>> page,String actNo , String  type,String itemCodeDescr);
	
	public Map<String,Object> doSaveActivityGift(ActivityGift activityGift);
	
	public Map<String,Object> getActGiftDetail(String itemCode);
	
	public Map<String,Object> getActGiftDetailByPk(Integer pk);
	
	public Map<String,Object> doUpdateActGift(DoUpdateActGiftEvt evt,String czybh);
	
	public boolean existCmpActGift(String actNo,String itemCode);
}
