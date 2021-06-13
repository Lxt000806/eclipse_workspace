package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.home.client.service.evt.DoCompleteCustServiceEvt;
import com.house.home.client.service.evt.DoSaveCustServiceEvt;
import com.house.home.client.service.evt.DoUpdateCustServiceEvt;
import com.house.home.dao.project.CustServiceDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustService;
import com.house.home.service.project.CustServiceService;

@SuppressWarnings("serial")
@Service 
public class CustServiceServiceImpl extends BaseServiceImpl implements CustServiceService {
	@Autowired
	private  CustServiceDao custServiceDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustService custService) {
		return custServiceDao.findPageBySql(page, custService);
	}

	@Override
	public Map<String, Object> getCustDetailByCode(String code) {
		return custServiceDao.getCustDetailByCode(code);
	}

	@Override
	public Map<String, Object> doSaveCustService(DoSaveCustServiceEvt evt){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String no = this.getSeqNo("tCustService");
		
		CustService custService = new CustService();
		
		custService.setNo(no);
		custService.setCustCode(evt.getCustCode());
		if(StringUtils.isNotBlank(evt.getCustCode())){
			Customer customer = custServiceDao.get(Customer.class, evt.getCustCode());
			if(customer != null){
				custService.setAddress(customer.getAddress());
			}
		}
		custService.setStatus("0");
		custService.setType("9");
		custService.setRemarks(evt.getRemarks());
		custService.setRepMan("1");
		custService.setRepDate(new Date());
		custService.setLastUpdate(new Date());
		custService.setLastUpdatedBy("1");
		custService.setExpired("F");
		custService.setActionLog("ADD");
		
		custServiceDao.save(custService);
		resultMap.put("returnCode", "000000");
		resultMap.put("returnInfo", "申请成功请耐心等待");
		return resultMap;
	}
	
	@Override
	public Map<String, Object> getCustServiceDetail(String no){
		return custServiceDao.getCustServiceDetail(no);
	}
	
	@Override
	public Map<String, Object> doUpdateCustService(DoUpdateCustServiceEvt evt){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CustService custService = custServiceDao.get(CustService.class, evt.getNo());
		//custService.setType(evt.getType());
		custService.setRemarks(evt.getRemarks());
		custService.setLastUpdate(new Date());
		custService.setLastUpdatedBy("1");
		custServiceDao.update(custService);
		resultMap.put("returnCode", "000000");
		resultMap.put("returnInfo", "申请修改成功");
		return resultMap;
	}

	@Override
	public Map<String, Object> goComplete(String no) {
		
		return custServiceDao.goComplete(no);
	}

	@Override
	public Page<Map<String, Object>> AppfindBySql(
			Page<Map<String, Object>> page, CustService custService) {
		
		return custServiceDao.AppfindBySql(page, custService);
	}

}
