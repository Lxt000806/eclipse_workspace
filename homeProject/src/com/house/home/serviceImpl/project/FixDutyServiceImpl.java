package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.FixDutyEvt;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.project.FixDutyDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.project.FixDuty;
import com.house.home.service.project.FixDutyService;

@SuppressWarnings("serial")
@Service 
public class FixDutyServiceImpl extends BaseServiceImpl implements FixDutyService {
	@Autowired
	private  FixDutyDao fixDutyDao;
	@Autowired
	private PersonMessageDao personMessageDao;
	
	@Override
	public Page<Map<String, Object>> getFixDutyList(
			Page<Map<String, Object>> page, FixDutyEvt evt) {
		return fixDutyDao.getFixDutyList(page,evt);
	}
	
	@Override
	public Page<Map<String, Object>> getFixDutyListForPrjMan(
			Page<Map<String, Object>> page, FixDutyEvt evt) {
		return fixDutyDao.getFixDutyListForPrjMan(page,evt);
	}

	@Override
	public Page<Map<String, Object>> getFixDutyDetailList(
			Page<Map<String, Object>> page, FixDutyEvt evt) {
		return fixDutyDao.getFixDutyDetailList(page,evt);
	}
	
	@Override
	public Page<Map<String, Object>> getBaseCheckItemList(
			Page<Map<String, Object>> page, FixDutyEvt evt) {
		return fixDutyDao.getBaseCheckItemList(page,evt);
	}

	@Override
	public Result saveForProc(FixDuty fixDuty, String xml) {
		return fixDutyDao.saveForProc(fixDuty,xml);
	}

	@Override
	public Page<Map<String, Object>> getFixDutyList(
			Page<Map<String, Object>> page, FixDuty fixDuty, UserContext uc) {
		// TODO Auto-generated method stub
		return fixDutyDao.getFixDutyList(page, fixDuty, uc);
	}
	
	@Override
	public Page<Map<String, Object>> getFixDutyDetail(
			Page<Map<String, Object>> page, FixDuty fixDuty) {
		// TODO Auto-generated method stub
		return fixDutyDao.getFixDutyDetail(page, fixDuty);
	}
	
	@Override
	public Page<Map<String, Object>> getBaseCheckItemList(
			Page<Map<String, Object>> page, String baseCheckItemCode,String custCode) {
		// TODO Auto-generated method stub
		return fixDutyDao.getBaseCheckItemList(page, baseCheckItemCode,custCode);
	}

	@Override
	public Result doUpdateFixDutyDetail(FixDuty fixDuty, String xmlData) {
		return fixDutyDao.doUpdateFixDutyDetail(fixDuty,xmlData);
	}

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, FixDuty fixDuty){
		return fixDutyDao.findPageBySql(page, fixDuty);
	}

	@Override
	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty) {
		return fixDutyDao.goDetailJqGrid(page, fixDuty);
	}

	@Override
	public Page<Map<String, Object>> goManJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty) {
		return fixDutyDao.goManJqGrid(page, fixDuty);
	}

	@Override
	public Long updateOneStatus(FixDuty fixDuty) {
		return fixDutyDao.updateOneStatus(fixDuty);
	}

	@Override
	public Result updateMultyStatus(FixDuty fixDuty) {
		return	fixDutyDao.doUpdateMultyStatus(fixDuty);
	}

	@Override
	public String cancel(FixDuty fixDuty) {
		String returnNumString = fixDutyDao.cancel(fixDuty);
		if("2".equals(fixDuty.getStatus())){
			deletePersonMessage(fixDuty);
		}
		
		return returnNumString;
	}

	@Override
	public Result doSaveProc(FixDuty fixDuty) {
		return fixDutyDao.doSaveProc(fixDuty);
	}

	@Override
	public Page<Map<String, Object>> goDetailQueryJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty) {
		return fixDutyDao.goDetailQueryJqGrid(page, fixDuty);
	}

	@Override
	public Page<Map<String, Object>> getPrjItemDescr(
			Page<Map<String, Object>> page, String czybh) {
		// TODO Auto-generated method stub
		return fixDutyDao.getPrjItemDescr(page, czybh);
	}
	
	public Page<Map<String,Object>> getBaseCheckItemDetailList(Page<Map<String,Object>> page,String custCode,String workType12){
		return fixDutyDao.getBaseCheckItemDetailList(page,custCode,workType12);
	}
	
	public List<Map<String, Object>> checkHasFixDuty(FixDuty fixDuty){
		return fixDutyDao.checkHasFixDuty(fixDuty);
	}
	
	public String updateFixDutyDetail(FixDuty fixDuty){
		return fixDutyDao.updateFixDutyDetail(fixDuty);
	}
	
	public Map<String, Object> getFixDutyByCustCode(String custCode){
		return fixDutyDao.getFixDutyByCustCode(custCode);
	}

	@Override
	public double getConfirmAmount(String custCode, String workerCode) {
		return fixDutyDao.getConfirmAmount(custCode,workerCode);
	}

	@Override
	public Boolean isWorkCostDetail(String no) {
		return fixDutyDao.isWorkCostDetail(no);
	}

	@Override
	public Map<String, Object> getOtherRiskFund(String custCode,String no) {
		return fixDutyDao.getOtherRiskFund(custCode,no);
	}

	@Override
	public Result doSaveDeignDuty(FixDuty fixDuty) {
		return fixDutyDao.doSaveDeignDuty(fixDuty);
	}
	
	@Override
	public void recoveryFixDuty(FixDuty fixDuty){
		 this.update(fixDuty);
		 deletePersonMessage(fixDuty);
	}
	
	private void deletePersonMessage(FixDuty fixDuty){
		PersonMessage pm = new PersonMessage();
		pm.setMsgType("1");
		pm.setMsgRelNo(fixDuty.getNo());
		pm.setProgmsgType("8");
		PersonMessage personMessage = personMessageDao.getPersonMessageByCondition(pm);
		if(personMessage != null){
			this.delete(personMessage);
		}
	}
	
	@Override
	public Map<String, Object> getFixDutyDetailInfo(String no) {
		return fixDutyDao.getFixDutyDetailInfo(no);
	}

	@Override
	public Page<Map<String, Object>> getDepartment2List(
			Page<Map<String, Object>> page) {
		return fixDutyDao.getDepartment2List(page);
	}

    @Override
    public long countDesignDuties(String custCode) {
        return fixDutyDao.countDesignDuties(custCode);
    }

    @Override
    public Map<String, Object> summarizeDiscounts(String custCode, String fixDutyNo) {
        return fixDutyDao.summarizeDiscounts(custCode, fixDutyNo);
    }
	
}
