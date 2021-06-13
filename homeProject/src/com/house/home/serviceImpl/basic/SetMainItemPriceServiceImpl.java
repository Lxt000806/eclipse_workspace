package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.SetMainItemPriceDao;
import com.house.home.entity.basic.SetMainItemPrice;
import com.house.home.service.basic.SetMainItemPriceService;

@SuppressWarnings("serial")
@Service
public class SetMainItemPriceServiceImpl extends BaseServiceImpl implements SetMainItemPriceService {

	@Autowired
	private SetMainItemPriceDao setMainItemPriceDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SetMainItemPrice setMainItemPrice){
		return setMainItemPriceDao.findPageBySql(page, setMainItemPrice);
	}

	@Override
	public List<Map<String, Object>> getIsRepeatArea(String custType,
			Double area) {
		return setMainItemPriceDao.getIsRepeatArea(custType, area);
	}

	@Override
	public void doSave(SetMainItemPrice setMainItemPrice,
			UserContext userContext) {
		setMainItemPrice.setLastUpdate(new Date());
		setMainItemPrice.setLastUpdatedBy(userContext.getCzybh());
		setMainItemPrice.setExpired("F");
		setMainItemPrice.setActionLog("ADD");
		this.save(setMainItemPrice);
	}

	@Override
	public void doUpdate(SetMainItemPrice setMainItemPrice,
			UserContext userContext) {
		setMainItemPrice.setLastUpdate(new Date());
		setMainItemPrice.setLastUpdatedBy(userContext.getCzybh());
		setMainItemPrice.setActionLog("Edit");
		this.update(setMainItemPrice);
	}

	@Override
	public void doDelete(String deleteIds) {
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				SetMainItemPrice setMainItemPrice = this.get(SetMainItemPrice.class, (int) Double.parseDouble(deleteId));
				if(setMainItemPrice == null)
					continue;
				this.delete(setMainItemPrice);
			}
		}
	}

	@Override
	public Result doSaveBatch(SetMainItemPrice setMainItemPrice) {
		return setMainItemPriceDao.doSaveBatch(setMainItemPrice);
	}

}
