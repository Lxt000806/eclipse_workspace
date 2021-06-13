package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.dao.project.CustWorkerAppDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCustLog;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.CustWorkerAppService;

@SuppressWarnings("serial")
@Service
public class  CustWorkerAppServiceImpl extends BaseServiceImpl implements CustWorkerAppService {
	
	@Autowired 
	private CustWorkerAppDao custWorkerAppDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp,String czybh, UserContext uc) {
		return custWorkerAppDao.findPageBySql(page, custWorkerApp,czybh, uc);
	}
	
	@Override
	public Page<Map<String, Object>> findPrintPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.findPrintPageBySql(page, custWorkerApp);
	}
	
	@Override
	public Page<Map<String, Object>> getReturnDetail(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.getReturnDetail(page, custWorkerApp);
	}
	
	@Override
	public Page<Map<String, Object>> findWorkerDetailPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.findWorkerDetailPageBySql(page, custWorkerApp);
	}
	

	@Override
	public Page<Map<String, Object>> findNoArrGdPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.findNoArrGdPageBySql(page, custWorkerApp);
	}

	@Override
	public Page<Map<String, Object>> findHasArrGdPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.findHasArrGdPageBySql(page, custWorkerApp);
	}

	@Override
	public Page<Map<String, Object>> findNoArrGRPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.findNoArrGRPageBySql(page,custWorkerApp);
	}
	
	@Override
	public Page<Map<String, Object>> findCustWorkerAppPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {

		return custWorkerAppDao.findCustWorkerAppPageBySql(page, custWorkerApp);
	}
	
	@Override
	public Page<Map<String, Object>> findAppNoArrangePageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {

		return custWorkerAppDao.findAppNoArrangePageBySql(page, custWorkerApp);
	}
	
	@Override
	public Map<String, Object> getCustPk() {
		return custWorkerAppDao.getCustPk();
	}

	@Override
	public CustWorkerApp getByCode(String custCode,String workType12) {
		return custWorkerAppDao.getByCode(custCode,workType12);
	}
	
	@Override
	public CustWorkerApp getByWorkerCode(String custCode,String workType12,String workerCode) {
		return custWorkerAppDao.getByWorkerCode(custCode,workType12,workerCode);
	}
	
	@Override
	public void doDel(Integer pk) {
		 custWorkerAppDao.doDel(pk);
	}
	
	@Override
	public void doReturn(Integer pk) {
		 custWorkerAppDao.doReturn(pk);
	}
	
	@Override
	public void doCancel(Integer pk,String lastUpdatedBy) {
		 custWorkerAppDao.doCancel(pk,lastUpdatedBy);
	}
	
	@Override
	public Page<Map<String,Object>> getWorkerAppList(Page<Map<String,Object>> page,CustWorkerAppEvt evt){
		return custWorkerAppDao.getWorkerAppList(page,evt);
	}

	public boolean addWorkerApp(CustWorkerAppEvt evt){
		/**
		 * 允许成品工人重复申请
		 * && !"09".equals(evt.getWorkType12())
		 * add by zzr 2018/05/07
		 */
		if(custWorkerAppDao.existWorkApp(evt.getCustCode(), evt.getWorkType12(), "0,1") && !"09".equals(evt.getWorkType12())){
			return false;
		}else{
			CustWorkerApp custWorkerApp = new CustWorkerApp();
			custWorkerApp.setCustCode(evt.getCustCode());
			custWorkerApp.setWorkType12(evt.getWorkType12());
			custWorkerApp.setStatus("1");
			custWorkerApp.setAppComeDate(evt.getAppComeDate());
			custWorkerApp.setRemarks(evt.getRemark());
			custWorkerApp.setAppDate(new Date());
			custWorkerApp.setLastUpdate(new Date());
			custWorkerApp.setLastUpdatedBy(evt.getProjectMan());
			custWorkerApp.setActionLog("Add");
			custWorkerApp.setExpired("F");
			custWorkerAppDao.save(custWorkerApp);
			
			if("1".equals(evt.getFromInfoAdd())){
				PersonMessage pm = custWorkerAppDao.get(PersonMessage.class, evt.getInfoPk());
				pm.setRcvDate(new Date());
				pm.setRcvStatus("1");
				pm.setDealNo(custWorkerApp.getPk().toString());
				custWorkerAppDao.update(pm);
			}
			return true; 
		}
	}
	

	public Map<String,Object> getWorkerAppByPk(Integer pk){
		return custWorkerAppDao.getWorkerAppByPk(pk);
	}
	

	public boolean updateWorkerApp(CustWorkerAppEvt evt){
		CustWorkerApp custWorkerApp = custWorkerAppDao.get(CustWorkerApp.class, evt.getPk());
		if("0".equals(custWorkerApp.getStatus()) && "1".equals(evt.getStatus())){
			custWorkerApp.setAppDate(new Date());
		}
		if("1".equals(evt.getOpSign())){/*evt.getOpSign() != null && evt.getOpSign().equals("1")*/
			if(!(custWorkerApp.getCustCode().trim().equals(evt.getCustCode()) && custWorkerApp.getWorkType12().trim().equals(evt.getWorkType12()))){		/**
				 * 允许成品工人重复申请
				 * && !"09".equals(evt.getWorkType12())
				 * add by zzr 2018/05/07
				 */
				if(custWorkerAppDao.existWorkApp(evt.getCustCode(), evt.getWorkType12(), "0,1") && !"09".equals(evt.getWorkType12())){
					return false;
				}
			}
			custWorkerApp.setCustCode(evt.getCustCode());
			custWorkerApp.setWorkType12(evt.getWorkType12());
			custWorkerApp.setAppComeDate(evt.getAppComeDate());
			custWorkerApp.setRemarks(evt.getRemark());
			if(StringUtils.isNotBlank(evt.getStatus())){
				custWorkerApp.setStatus("1");
			}
		}else{
			custWorkerApp.setStatus(evt.getStatus());
		}
		custWorkerApp.setLastUpdate(new Date());
		custWorkerApp.setLastUpdatedBy(evt.getProjectMan());
		custWorkerApp.setActionLog("Edit");
		custWorkerAppDao.update(custWorkerApp);
		return true;
	}
	
	public boolean judgeProgTemp(CustWorkerAppEvt evt){
		List<Map<String,Object>> list = custWorkerAppDao.judgeProgTemp(evt);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}

/*	public boolean existWorkApp(CustWorkerAppEvt evt){
		return custWorkerAppDao.existWorkApp(evt.getCustCode(), evt.getWorkType12(),null);
	}*/
	
	public Map<String,Object> checkCustPay(CustWorkerAppEvt evt,String payNum){
		return custWorkerAppDao.checkCustPay(evt,payNum);
	}

	public Map<String, Object> getConstructDay(String custCode,String workerCode){
		
		return custWorkerAppDao.getConstructDay(custCode,workerCode);
	}

	@Override
	public void doAutoArr(String lastUpdatedBy) {
		
		custWorkerAppDao.doAutoArr(lastUpdatedBy);
	}

	@Override
	public Page<Map<String, Object>> findQQGZLPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.find_qqgzl_PageBySql(page, customer);
	}
	
	@Override
	public Page<Map<String, Object>> findFSGZLPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.find_fsgzl_PageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findSMGZLPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.find_smgzl_PageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findMZGZLPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.find_mzgzl_PageBySql(page, customer);
	}

	@Override
	public Map<String,Object> specialReqForApply(CustWorkerAppEvt evt){
		return custWorkerAppDao.specialReqForApply(evt);
	}
	
	@Override
	public Map<String,Object> getCustPayType(String custCode){
		return custWorkerAppDao.getCustPayType(custCode);
	}
	
	@Override
	public Map<String,Object> isSignEmp(String czybh){
		return custWorkerAppDao.isSignEmp(czybh);
	}

	@Override
	public String getWorkTypeConDay(String custCode, String workType12) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.getWorkTypeConDay(custCode, workType12);
	}

	@Override
	public boolean getNeedWorkType2Req(String custCode, String workType12) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.getNeedWorkType2Req(custCode, workType12);
	}

	@Override
	public Page<Map<String, Object>> findWorkerArrPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.findWorkerArrPageBySql(page,customer);
	}
	
	@Override
	public Map<String,Object> judgeBefWorkType12(String custCode, String workType12){
		return custWorkerAppDao.judgeBefWorkType12(custCode, workType12);
	}

	@Override
	public String isNeedZF(String custCode) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.isNeedZF(custCode);
	}

	@Override
	public Page<Map<String, Object>> findZFDetailPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.findZFDetailPageBySql(page, customer);
	}
	
	
	
	@Override
	public Page<Map<String, Object>> findItemArrPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.findItemArrPageBySql(page,custWorkerApp);
	}

	@Override
	public Page<Map<String, Object>> findWorkTypeBefPageBySql(
			Page<Map<String, Object>> page, CustWorkerApp custWorkerApp) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.findWorkTypeBefPageBySql(page,custWorkerApp);
	}

	@Override
	public List<Map<String, Object>> befWorkType12Conf(String custCode,
			String workType12) {
		return custWorkerAppDao.befWorkType12Conf(custCode, workType12);
	}
	
	@Override
	public List<Map<String, Object>> itemSatisfy(String custCode,
			String workType12) {
		return custWorkerAppDao.itemSatisfy(custCode, workType12);
	}

	@Override
	public Page<Map<String, Object>> findItemArrDetailPageBySql(
			Page<Map<String, Object>> page, Integer pk,String custCode) {
		// TODO Auto-generated method stub
		return custWorkerAppDao.findItemArrDetailPageBySql(page,pk,custCode);
	
	}
	
	public String getAppComeDate(String custCode,String workType12){
		return custWorkerAppDao.getAppComeDate(custCode,workType12);
	}

	@Override
	public void readMsg(CustWorkerApp custWorkerApp) {
		custWorkerAppDao.readMsg(custWorkerApp);
	}

	@Override
	public List<Map<String, Object>> getBefTaskComplete(CustWorkerApp custWorkerApp) {
		return custWorkerAppDao.getBefTaskComplete(custWorkerApp);
	}

	@Override
	public Page<Map<String, Object>> goDeJqGrid(Page<Map<String, Object>> page, CustWorker custWorker) {
		return custWorkerAppDao.goDeJqGrid(page,custWorker);
	}

	@Override
	public String getCanArr(String custCode, String workType12, String type) {
		return custWorkerAppDao.getCanArr(custCode, workType12, type);
	}

	@Override
	public Map<String, Object> getCustWorkerAppDataByCustWorkPk(Integer custWkPk) {

		return custWorkerAppDao.getCustWorkerAppDataByCustWorkPk(custWkPk);
	}
	
	
	
}









