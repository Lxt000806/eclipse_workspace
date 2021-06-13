package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustProblemEvt;
import com.house.home.dao.project.CustProblemDao;
import com.house.home.entity.project.CustComplaint;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustService;
import com.house.home.service.project.CustProblemService;

@SuppressWarnings("serial")
@Service
public class CustProblemServiceImpl extends BaseServiceImpl implements CustProblemService{

	@Autowired
	private CustProblemDao custProblemDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustProblem custProblem, UserContext uc) {
		
		return custProblemDao.findPageBySql(page,custProblem, uc);
	}

	@Override
	public void dealCustCompaint(String no, int pk,String dealDate,String dealRemarks) {
		custProblemDao.dealCustCompaint(no, pk,dealDate,dealRemarks);
	}

	@Override
	public void doRcv(String planDealDate, Integer pk, Date rcvDate,String dealRemarks) {
		custProblemDao.doRcv(planDealDate, pk, rcvDate,dealRemarks);
		
	}

	@Override
	public void doUpdate(CustProblem custProblem) {
		custProblemDao.doUpdate(custProblem);
	}

	@Override
	public Page<Map<String, Object>> findAllBySql(
			Page<Map<String, Object>> page, CustProblem custProblem) {
		return custProblemDao.findAllBySql(page, custProblem);
	}
	
	public List<Map<String,Object>> findPromType1(int type,String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.custProblemDao.findPromType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.custProblemDao.findPromType2(param);
		}
		return resultList;
	}
	
	public List<Map<String,Object>> findPromRsn(int type,String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.custProblemDao.findPromType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.custProblemDao.findPromRsn(param);
		}
		return resultList;
	}

	@Override
	public void doToCustService(CustProblemEvt evt, UserContext uc) {

		CustComplaint custComplaint = new CustComplaint();
		CustService custService = new CustService();
		String no = this.getSeqNo("tCustService");
		
		custService.setNo(no);
		custService.setCustCode(evt.getCustCode());
		custService.setStatus("1");
		custService.setType(evt.getType());
		custService.setDealMan(evt.getServiceDealMan());
		custService.setDealDate(evt.getServiceDealDate());
		custService.setUndertaker(evt.getUndertaker());
		custService.setAddress(evt.getAddress());
		custService.setRepDate(evt.getRepDate());
		custService.setRepMan(evt.getRepEmp());
		custService.setLastUpdate(new Date());
		custService.setLastUpdatedBy(uc.getCzybh());
		custService.setExpired("F");
		custService.setActionLog("ADD");
		custService.setCustProblemPK(evt.getPk());
		custService.setServiceMan(evt.getServiceEmp());
		custService.setRemarks(evt.getRemarks());
		
		CustProblem custProblem = new CustProblem();
		String remarks = "";
		custProblem = this.get(CustProblem.class, evt.getPk());
		if(custProblem != null){
			custProblem.setStatus("2");
			String year = ((Integer)DateUtil.getYear(new Date())).toString();
			String month = ((Integer)DateUtil.getMonth(new Date())).toString();
			String day = ((Integer)DateUtil.getDay(new Date())).toString();
			String dateString = year.substring(2,4)+"年"+month+"月"+day+"日";
			
			remarks = DateUtil.DateToString(new Date(), "yy年MM月dd日")+ dateString+uc.getZwxm()+"：转售后，处理人："+evt.getServiceDealMan()+"。";
			custProblem.setDealRemarks(remarks+"\r\n"+custProblem.getDealRemarks());
			custProblem.setLastUpdate(new Date());
			custProblem.setLastUpdatedBy(uc.getCzybh());
			custProblem.setActionLog("Edit");
			this.update(custProblem);
			custComplaint = this.get(CustComplaint.class, custProblem.getNo());
			custService.setRemarks(custComplaint.getRemarks());
		}
		
		this.save(custService);
	}
}
