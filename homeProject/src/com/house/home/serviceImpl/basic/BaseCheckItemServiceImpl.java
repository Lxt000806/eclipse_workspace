package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BaseCheckItemDao;
import com.house.home.entity.basic.BaseCheckItem;
import com.house.home.entity.basic.BaseCheckItemWorkerClassify;
import com.house.home.service.basic.BaseCheckItemService;
@SuppressWarnings("serial")
@Service
public class BaseCheckItemServiceImpl extends BaseServiceImpl implements BaseCheckItemService {
    @Autowired 
    private BaseCheckItemDao baseCheckItemDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BaseCheckItem baseCheckItem) {
		// TODO Auto-generated method stub
		return baseCheckItemDao.findPageBySql(page, baseCheckItem);
	}
	@Override
	public Page<Map<String, Object>> getItemBaseType(
			Page<Map<String, Object>> page, BaseCheckItem baseCheckItem) {
		return baseCheckItemDao.getItemBaseType(page, baseCheckItem);
	}
	@Override
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, BaseCheckItem baseCheckItem) {
		return baseCheckItemDao.findCodePageBySql(page, baseCheckItem);
	}
	@Override
	public void doUpdateWorkerClassify(BaseCheckItem baseCheckItem) {
		//先删除原有的工人分类
		baseCheckItemDao.doDeleteWorkClassify(baseCheckItem);
		
		//重新添加工人分类
		String[] workerClassifys = baseCheckItem.getWorkerClassify().split(",");
		for (int i = 0; i < workerClassifys.length; i++) {
			BaseCheckItemWorkerClassify baseCheckItemWorkerClassify = new BaseCheckItemWorkerClassify();
			baseCheckItemWorkerClassify.setBaseCheckItemCode(baseCheckItem.getCode());
			baseCheckItemWorkerClassify.setWorkerClassify(workerClassifys[i]);
			baseCheckItemWorkerClassify.setLastUpdate(new Date());
			baseCheckItemWorkerClassify.setLastUpdatedBy(baseCheckItem.getLastUpdatedBy());
			baseCheckItemWorkerClassify.setExpired("F");
			baseCheckItemWorkerClassify.setActionLog("ADD");
			this.save(baseCheckItemWorkerClassify);
		}	
	}
	
	@Override
	public String getWorkerClassify(String code) {
		return baseCheckItemDao.getWorkerClassify(code);
	}
	
}
