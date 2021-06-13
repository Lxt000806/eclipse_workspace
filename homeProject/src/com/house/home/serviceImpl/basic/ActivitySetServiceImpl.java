package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.dao.basic.ActivitySetDao;
import com.house.home.entity.design.ActivitySet;
import com.house.home.service.basic.ActivitySetService;

@SuppressWarnings("serial")
@Service
public class ActivitySetServiceImpl extends BaseServiceImpl implements ActivitySetService {

	@Autowired
	private ActivitySetDao activitySetDao;

	@Override
	public Page<Map<String,Object>> getActivitySetList(Page<Map<String,Object>> page, String  actNo,String ticketNo,String tokenCzyDescr){
		return activitySetDao.getActivitySetList(page,actNo,ticketNo,tokenCzyDescr);
	}

	@Override
	public Page<Map<String,Object>> getActSupplList(Page<Map<String,Object>> page,String actNo, String  supplType,String supplCodeDescr){
		return activitySetDao.getActSupplList(page,actNo,supplType,supplCodeDescr);
	}

	@Override
	public Map<String,Object> doSaveActivitySet(ActivitySet activitySet){
		Map<String,Object> result = new HashMap<String, Object>();
		if(activitySetDao.exisitActivitySet(activitySet.getActNo(),activitySet.getTicketNo(), activitySet.getSupplCode())){
			result.put("returnCode", "400001");
			result.put("returnInfo", "已经在该供应商处下定,无法重复提交");
			return result;
		}
		activitySet.setLastUpdate(new Date());
		activitySet.setExpired("F");
		activitySet.setActionLog("ADD");
		activitySetDao.save(activitySet);
		result.put("returnCode", "1");
		return result;
	}
	
	@Override
	public Map<String,Object> getActSupplDetail(String supplCode){
		return activitySetDao.getActSupplDetail(supplCode);
	}
	
	@Override
	public Map<String,Object> getActSetDetail(Integer pk){
		return activitySetDao.getActSetDetail(pk);
	}
	
	@Override
	public Map<String,Object> doActSetCancel(Integer pk,String czybh){
		Map<String,Object> result = new HashMap<String, Object>();
		ActivitySet activitySet = this.get(ActivitySet.class, pk);
		Date currDate = DateUtil.startOfTheDay(new Date());
		Date tokenDate = DateUtil.startOfTheDay(activitySet.getLastUpdate());
		if(DateUtil.dateDiff(currDate, tokenDate) >= 1){
			result.put("returnCode", "400001");
			result.put("returnInfo","只能作废当天的记录");
			return result;
		}
		if(!activitySet.getLastUpdatedBy().trim().equals(czybh)){
			result.put("returnCode", "400001");
			result.put("returnInfo", "只有登记人才能操作");
			return result;
		}
		activitySet.setExpired("T");
		activitySet.setLastUpdate(new Date());
		activitySet.setLastUpdatedBy(czybh);
		activitySet.setActionLog("EDIT");
		this.update(activitySet);
		result.put("returnCode", "1");
		return result;
	}
}
