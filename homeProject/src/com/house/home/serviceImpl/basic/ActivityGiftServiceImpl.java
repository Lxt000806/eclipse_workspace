package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.client.service.evt.DoUpdateActGiftEvt;
import com.house.home.dao.basic.ActivityGiftDao;
import com.house.home.entity.design.ActivityGift;
import com.house.home.entity.design.ActivitySet;
import com.house.home.service.basic.ActivityGiftService;

@SuppressWarnings("serial")
@Service
public class ActivityGiftServiceImpl extends BaseServiceImpl implements ActivityGiftService {

	@Autowired
	private ActivityGiftDao activityGiftDao;

	@Override
	public Page<Map<String,Object>> getActivityGiftList(Page<Map<String,Object>> page, String  actNo,String ticketNo,String tokenCzyDescr){
		return activityGiftDao.getActivityGiftList(page,actNo,ticketNo,tokenCzyDescr);
	}

	@Override
	public Page<Map<String,Object>> getActGiftList(Page<Map<String,Object>> page,String actNo, String  type,String itemCodeDescr){
		return activityGiftDao.getActGiftList(page,actNo,type,itemCodeDescr);
	}
	
	@Override
	public Map<String,Object> doSaveActivityGift(ActivityGift activityGift){
		Map<String,Object> result = new HashMap<String, Object>();
		if(activityGiftDao.exisitActivityGift(activityGift.getActNo(),activityGift.getTicketNo(), activityGift.getItemCode())){
			result.put("returnCode", "400001");
			result.put("returnInfo", "已经添加礼品,无法重复提交");
			return result;
		}
		activityGift.setLastUpdate(new Date());
		activityGift.setExpired("F");
		activityGift.setActionLog("ADD");
		activityGiftDao.save(activityGift);
		result.put("returnCode", "1");
		return result;
	}
	
	@Override
	public Map<String,Object> getActGiftDetail(String itemCode){
		return activityGiftDao.getActGiftDetail(itemCode);
	}
	
	@Override
	public Map<String,Object> getActGiftDetailByPk(Integer pk){
		return activityGiftDao.getActGiftDetailByPk(pk);
	}
	
	@Override
	public Map<String,Object> doUpdateActGift(DoUpdateActGiftEvt evt,String czybh){
		Map<String,Object> result = new HashMap<String, Object>();
		ActivityGift activityGift = this.get(ActivityGift.class, evt.getPk());
		Date currDate = DateUtil.startOfTheDay(new Date());
		Date tokenDate = DateUtil.startOfTheDay(activityGift.getLastUpdate());
		if(DateUtil.dateDiff(currDate, tokenDate) >= 1){
			result.put("returnCode", "400001");
			if("cancel".equals(evt.getOpFlag())){
				result.put("returnInfo","只能作废当天的记录");
			}else{
				result.put("returnInfo","只能修改当天的记录");
			}
			return result;
		}
/*		if(!activityGift.getLastUpdatedBy().trim().equals(czybh)){
			result.put("returnCode", "400001");
			result.put("returnInfo", "只有登记人才能操作");
			return result;
		}*/
		
		if("cancel".equals(evt.getOpFlag())){
			activityGift.setExpired("T");
			activityGift.setLastUpdate(new Date());
			activityGift.setLastUpdatedBy(czybh);
			activityGift.setActionLog("EDIT");
			this.update(activityGift);
		}else{
			if(!activityGift.getItemCode().trim().equals(evt.getItemCode()) && activityGiftDao.exisitActivityGift(activityGift.getActNo(),activityGift.getTicketNo(), evt.getItemCode())){
				result.put("returnCode", "400001");
				result.put("returnInfo", "已经添加礼品,无法重复提交");
				return result;
			}
			activityGift.setItemCode(evt.getItemCode());
			activityGift.setQty(evt.getQty());
			activityGift.setLastUpdate(new Date());
			activityGift.setLastUpdatedBy(czybh);
			activityGift.setActionLog("EDIT");
			this.update(activityGift);
		}
		result.put("returnCode", "1");
		return result;
	}
	
	@Override
	public boolean existCmpActGift(String actNo,String itemCode){
		return activityGiftDao.existCmpActGift(actNo,itemCode);
	}
}
