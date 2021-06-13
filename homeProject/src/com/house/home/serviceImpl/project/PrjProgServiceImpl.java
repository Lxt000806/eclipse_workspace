package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetNoSendItemEvt;
import com.house.home.dao.project.PrjProgDao;
import com.house.home.entity.project.CustCheck;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.ProgTempDt;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.design.Customer;
import com.house.home.service.project.PrjProgService;

@SuppressWarnings("serial")
@Service
public class PrjProgServiceImpl extends BaseServiceImpl implements PrjProgService {

	@Autowired
	private PrjProgDao prjProgDao;
	
	
	@Override
	public Page<Map<String, Object>> findUpdateStopPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg,UserContext uc) {
		// TODO Auto-generated method stub
		return prjProgDao.findUpdateStopPageBySql(page, prjProg,uc);
	}

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProg prjProg){
		return prjProgDao.findPageBySql(page, prjProg);
	}
	
	public Page<Map<String,Object>> findBuilderRepPageBySql(Page<Map<String,Object>> page, PrjProg prjProg){
		return prjProgDao.findBuilderRepPageBySql(page, prjProg);
	}
	
	public Page<Map<String,Object>> findCustComplainPageBySql(Page<Map<String,Object>> page, PrjProg prjProg){
		return prjProgDao.findCustComplainPageBySql(page, prjProg);
	}
	
	public Page<Map<String,Object>> findConfirmPageBySql(Page<Map<String,Object>> page, PrjProg prjProg,UserContext uc){
		return prjProgDao.findConfirmPageBySql(page, prjProg,uc);
	}
	
	public Page<Map<String,Object>> findPrjProgUpdateJDPageBySql(Page<Map<String,Object>> page, PrjProg prjProg){
		return prjProgDao.findPrjProgUpdateJDPageBySql(page, prjProg);
	}	
	
	public Page<Map<String,Object>> findPrjLogPageBySql(Page<Map<String,Object>> page, PrjProg prjProg){
		return prjProgDao.findPrjLogPageBySql(page, prjProg);
	}	

	public Page<Map<String, Object>> findPageByProjectMan(
			Page<Map<String, Object>> page, String projectMan) {
		return prjProgDao.findPageByProjectMan(page, projectMan);
	}

	public Map<String,Object> getPrjProgByPk(Integer pk) {
		return prjProgDao.getPrjProgByPk(pk);
	}

	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, String code) {
		return prjProgDao.findPageByCustCode(page,code);
	}

	public Map<String, Object> getPrjProgAlarm(String code, String prjItem, String dayType) {
		return prjProgDao.getPrjProgAlarm(code,prjItem,dayType);
	}

	public Result updatePrjProgForProc(int pk, String dayType, Date curDate,String czybh,String custCode,String prjItem) {
		 Result result = prjProgDao.updatePrjProgForProc(pk,dayType,curDate,czybh);
		 //remove by zzr 2017/12/19 10:41 改用触发器自动生成工地结算单 begin
/*		 if(custCode != null && prjItem != null && "1".equals(dayType) && !prjProgDao.isExistsCustCheck(custCode)){
			 if(prjItem.equals("16")){
				 CustCheck custCheck = new CustCheck();
				 custCheck.setCustCode(custCode);
				 custCheck.setAppDate(new Date());
				 custCheck.setMainStatus("1");
				 custCheck.setSoftSatus("1");
				 custCheck.setIntStatus("1");
				 custCheck.setFinStatus("1");
				 custCheck.setActionLog("ADD");
				 custCheck.setLastUpdate(new Date());
				 custCheck.setLastUpdatedBy(czybh);
				 custCheck.setExpired("F");
				 prjProgDao.save(custCheck);
			 }
		 }*/
		 //remove by zzr 2017/12/19 10:41 改用触发器自动生成工地结算单 end
		 return result;
	}

	@Override
	public Map<String, Object> getDelayAndRemain(String projectMan, String code) {
		return prjProgDao.getDelayAndRemain(projectMan, code);
	}

	@Override
	public Map<String, Object> getPrjProgByCodeAndPrjItem(String code,
			String prjItem) {
		return prjProgDao.getPrjProgByCodeAndPrjItem(code,prjItem);
	}

	@Override
	public List<Map<String, Object>> getPrjProgByCodeAndPrjItemDescr(
			String custCode, String prjItemDescr) {
		return prjProgDao.getPrjProgByCodeAndPrjItemDescr(custCode,prjItemDescr);
	}
	
	@Override
	public Result doPrjProgUpdate (PrjProg prjProg){
		return prjProgDao.doUpdate(prjProg);
	}

	@Override
	public Map<String, Object> getPrjProgCurrentById(String custCode){
		return prjProgDao.getPrjProgCurrentById(custCode);
	}
	
	@Override
	public void doSavePrjProg(String code,Date planBegin,Date planEnd, String lastUpDatedBy ,String tempNo,String prjProgTempType) {
		if(prjProgTempType.equals("1")){
			prjProgDao.doSavePrjProg(code ,planBegin,planEnd,lastUpDatedBy,tempNo);
		}else{
			prjProgDao.doSavePrjProgNew(code ,planBegin,lastUpDatedBy,tempNo);
		}

	}
	
	@Override
	public void doSavePrjProgBeginDate() {
		prjProgDao.doSavePrjProgBeginDate();
	}
	
	@Override
	public void doPostPone(Integer postPoneDate,Integer postPoneEndDate,String custCode,Date planBegin){
		prjProgDao.doPostPone(postPoneDate,postPoneEndDate,custCode,planBegin);
		
	}
	
	@Override
	public void doDelPicture(String photoName){
		prjProgDao.doDelPicture(photoName);
		
	}
	@Override
	public Map<String,Object> getMaxPk(String custCode){
		return prjProgDao.getMaxPk(custCode);
	}


	public List<PrjItem1> getPrjItem1List(){
		return prjProgDao.getPrjItem1List();
	}
	
	@Override
	public Map<String,Object> isConfirm(String custCode){
		return prjProgDao.isConfirm(custCode);
	}
	
	@Override
	public void doUpdateConfirm(String custCode , String prjItem, String confirmCZY,String confirmDesc,String prjLevel,String isPass) {
		if("1".equals(isPass)){
			 prjProgDao.isPassConfirm(custCode,prjItem,confirmCZY,confirmDesc,prjLevel);
		}else{
			prjProgDao.notPassConfirm(custCode,prjItem,confirmDesc,confirmCZY);
		}
	}
	
	@Override
	public void doReturnCheck(int pk,String czybh){
		prjProgDao.doReturnCheck(pk,czybh);
	}

	@Override
	public boolean getPrjProgPK(String code) {
		return prjProgDao.getPrjProgPK(code);
	}

	@Override
	public List<Map<String,Object>> getNoSendItem(GetNoSendItemEvt evt){
		return prjProgDao.getNoSendItem(evt);
	}

	@Override
	public Result doUpdateCustStatus(PrjProg prjProg) {
		// TODO Auto-generated method stub
		return prjProgDao.doUpdateCustStatus(prjProg);
	}

	@Override
	public void updateIsPushCust(int pk,String isPushCust) {
		if("1".equals(isPushCust)){
			isPushCust="0";
		}else{
			isPushCust="1";
		}
		
		prjProgDao.updateIsPushCust(pk,isPushCust);

	}

	@Override
	public void updateIsPushCustAll(PrjProgPhoto prjProgPhoto) {
		prjProgDao.updateIsPushCustAll(prjProgPhoto);

	}

	@Override
	public Result doProgArrange(PrjProg prjProg) {
		return prjProgDao.doProgArrange(prjProg);
	}
	
	public Page<Map<String, Object>> findLongTimeStopPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjProgDao.findLongTimeStopPageBySql(page,customer);
	}

	public Page<Map<String, Object>> findWaitFirstCheckPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjProgDao.findWaitFirstCheckPageBySql(page,customer);
	}
	
	public Page<Map<String, Object>> findWaitCustWorkAppPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjProgDao.findWaitCustWorkAppPageBySql(page,customer);
	}
	
	public Page<Map<String, Object>> findTimeOutEndPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjProgDao.findTimeOutEndPageBySql(page,customer);
	}
	
	@Override
	public Page<Map<String,Object>> getJobOrderList(Page<Map<String,Object>> page, String custCode, String prjItem,String workType12){
		return prjProgDao.getJobOrderList(page, custCode, prjItem, workType12);
	}

	@Override
	public Page<Map<String, Object>> getAlarmPrjItemList(
			Page<Map<String, Object>> page, String custCode) {
		return prjProgDao.getAlarmPrjItemList(page, custCode);
	}

	@Override
	public Page<Map<String, Object>> getAlarmWorkType12List(
			Page<Map<String, Object>> page, String custCode) {
		return prjProgDao.getAlarmWorkType12List(page, custCode);
	}

	@Override
	public ProgTempDt getProgTemDt(ProgTempDt progTempDt) {
		return prjProgDao.getProgTemDt(progTempDt);
	}
}
