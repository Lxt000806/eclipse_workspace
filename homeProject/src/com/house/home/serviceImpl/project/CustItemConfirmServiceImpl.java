package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ConfItemTimeDao;
import com.house.home.dao.project.ConfItemTypeDao;
import com.house.home.dao.project.CustItemConfDateDao;
import com.house.home.dao.project.CustItemConfirmDao;
import com.house.home.entity.project.ConfItemType;
import com.house.home.entity.project.CustItemConfDate;
import com.house.home.entity.project.CustItemConfProg;
import com.house.home.entity.project.CustItemConfProgDt;
import com.house.home.entity.project.CustItemConfirm;
import com.house.home.service.project.ConfItemTimeService;
import com.house.home.service.project.ConfItemTypeService;
import com.house.home.service.project.CustItemConfirmService;

@SuppressWarnings("serial")
@Service
public class CustItemConfirmServiceImpl extends BaseServiceImpl implements CustItemConfirmService {

	@Autowired
	private CustItemConfirmDao custItemConfirmDao;
	@Autowired
	private ConfItemTypeDao confItemTypeDao;
	@Autowired
	private CustItemConfDateDao custItemConfDateDao;
	@Autowired
	private ConfItemTimeDao confItemTimeDao;
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfirm custItemConfirm){
		return custItemConfirmDao.findPageBySql(page, custItemConfirm);
	}

	@Override
	public void doItemConfirm(CustItemConfDate custItemConfDate) {
		String no = custItemConfirmDao.getSeqNo("tCustItemConfProg");
		String[] types=custItemConfDate.getConfItemType().split(",");
		CustItemConfProg custItemConfProg=new CustItemConfProg();
		custItemConfProg.setNo(no);
		custItemConfProg.setCustCode(custItemConfDate.getCustCode());
		custItemConfProg.setConfirmDate(custItemConfDate.getConfirmDate());
		custItemConfProg.setItemConfStatus(custItemConfDate.getStatus());
		custItemConfProg.setRemarks(custItemConfDate.getRemarks());
		custItemConfProg.setLastUpdate(new Date());
		custItemConfProg.setLastUpdatedBy(custItemConfDate.getLastUpdatedBy());
		custItemConfProg.setActionLog("ADD");
		custItemConfProg.setExpired("F");
		this.save(custItemConfProg);
		for(String type:types){
			CustItemConfProgDt custItemConfProgDt=new CustItemConfProgDt();
			custItemConfProgDt.setConfProgNo(no);
			custItemConfProgDt.setConfItemType(type);
			custItemConfProgDt.setLastUpdate(new Date());
			custItemConfProgDt.setLastUpdatedBy(custItemConfDate.getLastUpdatedBy());
			custItemConfProgDt.setActionLog("ADD");
			custItemConfProgDt.setExpired("F");
			CustItemConfirm custItemConfirm=new CustItemConfirm();
			custItemConfirm.setCustCode(custItemConfDate.getCustCode());
			custItemConfirm.setConfItemType(type);
			custItemConfirm.setItemConfStatus(custItemConfDate.getStatus());
			custItemConfirm.setLastUpdate(new Date());
			custItemConfirm.setLastUpdatedBy(custItemConfDate.getLastUpdatedBy());
			custItemConfirm.setActionLog("ADD");
			custItemConfirm.setExpired("F");
			ConfItemType confItemType=confItemTypeDao.get(ConfItemType.class, type);
			this.save(custItemConfProgDt);
			this.save(custItemConfirm);
		}
		List<Map<String,Object>> codeList=confItemTimeDao.findConfItemCodeListBySql(custItemConfDate.getCustCode());
		for(Map<String,Object> map:codeList){
			String code=map.get("code").toString();
			CustItemConfDate confDate=new CustItemConfDate();
			confDate.setCustCode(custItemConfDate.getCustCode());
			confDate.setItemTimeCode(code);
			confDate.setConfirmDate(custItemConfDate.getConfirmDate());
			confDate.setLastUpdate(new Date());
			confDate.setLastUpdatedBy(custItemConfDate.getLastUpdatedBy());
			confDate.setActionLog("ADD");
			confDate.setExpired("F");
			custItemConfDateDao.save(confDate);
		}
	
	}

	@Override
	public void doItemCancel(CustItemConfDate custItemConfDate) {
		String no = custItemConfirmDao.getSeqNo("tCustItemConfProg");
		String[] types=custItemConfDate.getConfItemType().split(",");
		CustItemConfProg custItemConfProg=new CustItemConfProg();
		custItemConfProg.setNo(no);
		custItemConfProg.setCustCode(custItemConfDate.getCustCode());
		custItemConfProg.setConfirmDate(custItemConfDate.getConfirmDate());
		custItemConfProg.setItemConfStatus("1");
		custItemConfProg.setLastUpdate(new Date());
		custItemConfProg.setLastUpdatedBy(custItemConfDate.getLastUpdatedBy());
		custItemConfProg.setActionLog("ADD");
		custItemConfProg.setExpired("F");
		this.save(custItemConfProg);
		for(String type:types){
			CustItemConfProgDt custItemConfProgDt=new CustItemConfProgDt();
			custItemConfProgDt.setConfProgNo(no);
			custItemConfProgDt.setConfItemType(type);
			custItemConfProgDt.setLastUpdate(new Date());
			custItemConfProgDt.setLastUpdatedBy(custItemConfDate.getLastUpdatedBy());
			custItemConfProgDt.setActionLog("ADD");
			custItemConfProgDt.setExpired("F");
			CustItemConfirm custItemConfirm=custItemConfirmDao.getCustItemConfirm(custItemConfDate.getCustCode(), type);
			ConfItemType confItemType=confItemTypeDao.get(ConfItemType.class, type);
			CustItemConfDate confDate=custItemConfDateDao.getCustItemConfDate(custItemConfDate.getCustCode(), confItemType.getItemTimeCode());
			this.save(custItemConfProgDt);
			this.delete(custItemConfirm);
			if(confDate!=null){
				this.delete(confDate);
			}
		}
		
	}

	@Override
	public CustItemConfirm getCustItemConfirm(String custCode,
			String confItemType) {
		// TODO Auto-generated method stub
		return custItemConfirmDao.getCustItemConfirm(custCode,confItemType);
	}

	@Override
	public Page<Map<String, Object>> findConfirmResultPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		// TODO Auto-generated method stub
		return custItemConfirmDao.findConfirmResultPageBySql(page,custCode);
	}

	@Override
	public Page<Map<String, Object>> findConfirmStatusPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		// TODO Auto-generated method stub
		return custItemConfirmDao.findConfirmStatusPageBySql(page,custCode) ;
	}

	@Override
	public Page<Map<String, Object>> findCustConfirmResultPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		return custItemConfirmDao.findCustConfirmResultPageBySql(page,custCode);
	}

}
