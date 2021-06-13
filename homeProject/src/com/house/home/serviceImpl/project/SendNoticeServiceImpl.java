package com.house.home.serviceImpl.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.SendNoticeDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjJob;
import com.house.home.service.project.SendNoticeService;
@Service
@SuppressWarnings("serial")
public class SendNoticeServiceImpl extends BaseServiceImpl implements SendNoticeService {
	@Autowired
	private SendNoticeDao sendNoticeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return sendNoticeDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return sendNoticeDao.findPageBySql_detail(page,customer);
	}

	@Override
	public Map<String, Object> getItemAppInfo(String iaNo) {
		return sendNoticeDao.getItemAppInfo(iaNo);
	}

	@Override
	public Page<Map<String, Object>> goItemAppJqGrid(
			Page<Map<String, Object>> page, Customer customer) {
		return sendNoticeDao.goItemAppJqGrid(page, customer);
	}

	@Override
	public Result doSendNotice(PrjJob prjJob) {
		return sendNoticeDao.doSendNotice(prjJob);
	}
	
	@Override
	public List<Map<String,Object>> findPrjTypeByItemType1Auth(int type,
			String pCode,UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.sendNoticeDao.findItemType1Auth(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.sendNoticeDao.findPrjType(param);
		}
		return resultList;
	}
	
}
