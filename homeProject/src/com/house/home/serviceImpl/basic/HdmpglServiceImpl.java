package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.HdmpglDao;
import com.house.home.entity.basic.Hdmpgl;
import com.house.home.service.basic.HdmpglService;

@SuppressWarnings("serial")
@Service
public class HdmpglServiceImpl extends BaseServiceImpl implements HdmpglService  {

	@Autowired
	private HdmpglDao hdmpglDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl,UserContext uc) {
		return hdmpglDao.findPageBySql(page,hdmpgl,uc);
	}

	@Override
	public void doCallBack(Integer pk,String lastUpdatedBy) {
		this.hdmpglDao.doCallBack(pk,lastUpdatedBy);
	}

	@Override
	public void doCreate(String actNo,Integer length,String prefix , Integer ticketNum,String lastUpdatedBy) {
			
		hdmpglDao.doCreate(actNo,length,prefix,ticketNum,lastUpdatedBy);
	}

	@Override
	public boolean isRepetition(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo) {
		return hdmpglDao.isRepetition( prefix, numFrom, numTo, length, actNo);
	}

	@Override
	public boolean isSend(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo) {
		return hdmpglDao.isSend(prefix, numFrom, numTo, length, actNo);
	}

	@Override
	public void doDeleteTickets(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo) {
		hdmpglDao.doDeleteTickets( prefix, numFrom, numTo, length, actNo);
	}

	@Override
	public void doSign(Integer pk, String lastUpdatedBy,String status) {
		hdmpglDao.doSign(pk,lastUpdatedBy,status);
	}

	@Override
	public boolean hasTicket(String prefix, Integer numFrom, Integer numTo,
			Integer length, String actNo) {
		return hdmpglDao.hasTicket(prefix, numFrom, numTo, length, actNo);
	}

	@Override
	public String getPk(String actNo, String ticketNo) {
		return hdmpglDao.getPk(actNo,ticketNo);
	}

	@Override
	public Map<String, Object> getValidActNum() {
		return hdmpglDao.getValidActNum();
	}

	@Override
	public Page<Map<String, Object>> goDetail_OrderJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		return hdmpglDao.findDetail_OrderJqGrid(page, hdmpgl);
	}

	@Override
	public boolean isExistPhone(String actNo, String phone) {
		return hdmpglDao.isExistPhone(actNo, phone);
	}
	
	@Override
	public Page<Map<String, Object>> goDetail_giftJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		return hdmpglDao.findDetail_giftJqGrid(page, hdmpgl);
	}

	@Override
	public Page<Map<String, Object>> findOrderDetailJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		return hdmpglDao.findOrderDetailJqGrid(page,hdmpgl);
	}

	@Override
	public Page<Map<String, Object>> findGiftDetailJqGrid(
			Page<Map<String, Object>> page, Hdmpgl hdmpgl) {
		return hdmpglDao.findGiftDetailJqGrid(page,hdmpgl);
	}
	
	
	
	
	
}
