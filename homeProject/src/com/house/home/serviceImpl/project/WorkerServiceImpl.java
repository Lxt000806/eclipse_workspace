package com.house.home.serviceImpl.project;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.AddWokerCostEvt;
import com.house.home.client.service.evt.WokerCostApplyEvt;
import com.house.home.client.service.evt.WorkerAppEvt;
import com.house.home.client.service.evt.WorkerSignInEvt;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.design.CustomerDao;
import com.house.home.dao.project.WorkerDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.entity.project.WorkSign;
import com.house.home.entity.project.Worker;
import com.house.home.entity.project.WorkerProblemPic;
import com.house.home.entity.query.WorkSignPic;
import com.house.home.service.project.WorkerService;

@SuppressWarnings("serial")
@Service
public class WorkerServiceImpl extends BaseServiceImpl implements WorkerService {

	@Autowired
	private WorkerDao workerDao;	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private PersonMessageDao personMessageDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySql(page, worker);
	}
	
	public Page<Map<String,Object>> findMemberPageBySql(Page<Map<String,Object>> page, Worker worker) {
	    return workerDao.findMemberPageBySql(page, worker);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySqlList(
			Page<Map<String, Object>> page, Worker worker, UserContext userContext) {
		return workerDao.findPageBySqlList(page, worker, userContext);
	}

	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, Worker worker){
		return workerDao.findCodePageBySql(page, worker);
	}
	
	public Page<Map<String,Object>> findOnDoDetailPageBySql(Page<Map<String,Object>> page, String workerCode ,String department2){
		return workerDao.findOnDoDetailPageBySql(page, workerCode,department2);
	}
	
	@Override
	public Page<Map<String,Object>> findWorkerWorkType12PageBySql(Page<Map<String,Object>> page, String workerCode){
		return workerDao.findWorkerWorkType12PageBySql(page, workerCode);
	}
	
	@Override
	public Page<Map<String,Object>> findWorkType12PageBySql(Page<Map<String,Object>> page, String workType12Strings){
		return workerDao.findWorkType12PageBySql(page, workType12Strings);
	}

	@Override
	public Worker getByPhoneAndMm(String phone, String mm) {
		// TODO Auto-generated method stub
		return workerDao.getByPhoneAndMm(phone, mm);
	}

	@Override
	public Worker getByPhone(String phone) {
		// TODO Auto-generated method stub
		return workerDao.getByPhone(phone);
	}

	@Override
	public Page<Map<String, Object>> getSiteConstructList(Page<Map<String, Object>> page, String code,String status,String address) {
		// TODO Auto-generated method stub
		return workerDao.getSiteConstructList(page,code,status,address);
	}

	@Override
	public Map<String, Object> getWorkerPrjItem(WorkerAppEvt evt) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = workerDao.getWorkerPrjItem(evt);
        int i = 0;
		for(; i<list.size();i++){
			if(list.get(i).get("CrtDate") == null || i==list.size()-1){
				return list.get(i);
			}
		}
		return null;
	}
	@Override
	public Map<String,Object> getCustWorkInfo(String workerCode,String custCode,Integer pk){
		return workerDao.getCustWorkInfo(workerCode,custCode,pk);
	}
	@Override
	public boolean addWokerCost(AddWokerCostEvt evt){
		boolean result = true;
		try {
			
			// 申请金额均保留两位数
			evt.setWorkAppAmount(new BigDecimal(evt.getWorkAppAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			
			PreWorkCostDetail pwcd = new PreWorkCostDetail();
			pwcd.setCustCode(evt.getCustCode());
			pwcd.setSalaryType(evt.getSalaryType());
			pwcd.setWorkType2(evt.getWorkType2());
			pwcd.setWorkerCode(evt.getWorkerCode());
			pwcd.setActName(evt.getActName());
			pwcd.setCardId(evt.getCardID());
			pwcd.setAppAmount(evt.getWorkAppAmount());
			pwcd.setStatus(evt.getStatus());
			pwcd.setApplyMan(evt.getApplyMan());
			pwcd.setApplyDate(new Date());
			pwcd.setIsWithHold("0");
			pwcd.setCfmAmount(evt.getWorkAppAmount());
			pwcd.setIsWorkApp(evt.getIsWorkApp());
			pwcd.setWorkAppAmount(evt.getWorkAppAmount());
			pwcd.setCustWkPk(evt.getCustWkPk());
			pwcd.setIsAutoConfirm(evt.getIsAutoConfirm());
			pwcd.setLastUpdate(new Date());
			pwcd.setLastUpdatedBy(evt.getWorkerCode());
			pwcd.setExpired("F");
			pwcd.setActionLog("ADD");
			pwcd.setCardId2(evt.getCardID2());
			pwcd.setRemarks(evt.getRemarks());
			workerDao.save(pwcd);
			int pk=pwcd.getPk();
			Customer customer = customerDao.get(Customer.class,evt.getCustCode());
			String projectMan = customer.getProjectMan();
			if (StringUtils.isNotBlank(projectMan)){
				PersonMessage personMessage = new PersonMessage();
				personMessage.setMsgType("12");
				personMessage.setMsgText(customer.getAddress()+":您有一条新的工人工资申请单，请立即处理。");
				personMessage.setMsgRelNo(pk+"");
				personMessage.setMsgRelCustCode(evt.getCustCode());
				personMessage.setCrtDate(new Date());
				personMessage.setSendDate(new Date());
				personMessage.setRcvType("1");
				personMessage.setRcvCzy(projectMan);
				personMessage.setIsPush("1");
				personMessage.setPushStatus("0");
				personMessage.setRcvStatus("0");
				personMessageDao.save(personMessage);
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	@Override
	public Page<Map<String,Object>> getWokerCostApply(Page<Map<String,Object>> page,WokerCostApplyEvt evt){
		return workerDao.getWokerCostApply(page,evt);
	}
	@Override
	public List<Map<String,Object>> getWokerCostApply(String custCode,String salaryType,String workerCode,Integer custWkPk,String isWorkApp, String workType2, String workType12){
		return workerDao.getWokerCostApply(custCode,salaryType,workerCode,custWkPk,isWorkApp, workType2, workType12);
	}
	
	@Override
	public List<Map<String,Object>> getWokerCostApplyWorkType12(String custCode,String salaryType,String workerCode,Integer custWkPk,String isWorkApp, String workType2, String workType12){
		return workerDao.getWokerCostApplyWorkType12(custCode, salaryType, workerCode, custWkPk, isWorkApp, workType2, workType12);
	}

	public Map<String,Object> getWorkSignInCount(Integer custWkPk,String custCode,String workerCode){
		return workerDao.getWorkSignInCount(custWkPk,custCode,workerCode);
	}

	public Map<String,Object> getPrjItem2MaxSeq(String workType12){
		return workerDao.getPrjItem2MaxSeq(workType12);
	}
	
	public List<Map<String,Object>> findRegion(int type,String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.workerDao.findRegion(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.workerDao.findRegion2(param);
		}
		
		return resultList;
	}

//  add by hc  各种施工情况分析   2017/12/04   begin 	
	public Page<Map<String,Object>> findworkTypeConstructDetail(
			Page<Map<String,Object>> page, Worker worker,String orderBy,String direction){
		return workerDao.findworkTypeConstructDetail(page, worker,orderBy,direction);
	}
	
	public Page<Map<String,Object>> findbuilder(
			Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySqlbuilder(page, worker);
	}
	public Page<Map<String,Object>> findarrange(
			Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySqlarrange(page, worker);
	}
	public Page<Map<String,Object>> findcomplete(
			Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySqlcomplete(page, worker);
	}
	public Page<Map<String,Object>> findconfirmAmount(
			Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySqlconfirmAmount(page, worker);
	}
	public Page<Map<String,Object>> findcrtDate(
			Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySqlcrtDate(page, worker);
	}
	public Page<Map<String,Object>> findnoPass(
			Page<Map<String,Object>> page, Worker worker){
		return workerDao.findPageBySqlnoPass(page, worker);
	}
	@Override
	public List<Map<String, Object>> getWorkType12(String czyBH) {
		return workerDao.getWorkType12(czyBH);
	}

// add by hc 2017/12/04 end    班组施工情况分析
	
	@Override
	public Map<String,Object> getCanApplyTimes(AddWokerCostEvt evt){
		return workerDao.getCanApplyTimes(evt);
	}

	@Override
	public String getBefWorkType12Emp(String code, String workType12) {
		// TODO Auto-generated method stub
		return workerDao.getBefWorkType12Emp(code, workType12);
	}
	
	@Override
	public List<Map<String, Object>> getWorkPrjItemList(Integer custWkPk){
		return workerDao.getWorkPrjItemList(custWkPk);
	}

	@Override
	public Map<String, Object> existPrjProgConfirm(Integer custWkPk){
		return workerDao.existPrjProgConfirm(custWkPk);
	}

	@Override
	public Worker getByIdnum(String idnum) {
		return workerDao.getByIdnum(idnum);
	}

	@Override
	public Page<Map<String, Object>> getWorkerProblemList(
			Page<Map<String, Object>> page, Integer custWkPk) {
		// TODO Auto-generated method stub
		return workerDao.getWorkerProblemList(page,custWkPk);
	}

	@Override
	public Result doSave(Worker worker) {
		return workerDao.doSave(worker);
	}


	@Override
	public Page<Map<String, Object>> getItemBatchList(
			Page<Map<String, Object>> page, String itemType1,int custWkPk) {
		// TODO Auto-generated method stub
		return workerDao.getItemBatchList(page,itemType1,custWkPk);
	}

	public List<Map<String, Object>> getRatedSalaryList(CustWorker custWorker,String appType) {
		return workerDao.getRatedSalaryList(custWorker,appType);
	}

	public Map<String, Object> getRatedSalary(CustWorker custWorker,String appType) {
		return workerDao.getRatedSalary(custWorker,appType);
	}

	public Map<String, Object> getAllowGetMatrail(CustWorker custWorker) {
		return workerDao.getAllowGetMatrail(custWorker);
	}

	@Override
	public void saveWorkSign(WorkSign workSign, String photoList,String photoCodeList) {
		if(StringUtils.isNotBlank(photoList)){
			String[] arr = photoList.split(",");
			String[] code = new String[0];
			if(photoCodeList!=null){
				code =photoCodeList.split(","); 
			}
			for(int i=0;i<arr.length;i++){
				WorkSignPic workSignPic=new WorkSignPic();
				workSignPic.setNo(workSign.getNo());
				workSignPic.setLastUpdate(new Date());
				workSignPic.setLastUpdateBy("1");
				workSignPic.setExpired("F");
				workSignPic.setIsPushCust("1");
				workSignPic.setActionLog("ADD");
				workSignPic.setPhotoName(arr[i]);
				workSignPic.setIsSendYun("1");
				workSignPic.setSendDate(new Date());
				if(i<code.length){
					workSignPic.setTechCode(code[i]);
				}
				workSignPic.setLastUpdateBy(workSign.getLastUpdatedBy());
				workerDao.save(workSignPic);
			}
		}
		
		this.save(workSign);
	}
	
	public Map<String, Object> getBefSameWorker(CustWorker custWorker) {
		return workerDao.getBefSameWorker(custWorker);
	}
	
	public Map<String, Object> getAppAmount(CustWorker custWorker) {
		WorkType2 workType2=workerDao.get(WorkType2.class,custWorker.getWorkType2());
		if("0".equals(workType2.getSalaryCtrlType())){//根据工资控制类型算申请金额
			return workerDao.getAppAmountByWt12(custWorker);
		}
		return workerDao.getAppAmountByWt2(custWorker);
	}
	
	public Map<String, Object> getSalaryCtrl(CustWorker custWorker) {
		return workerDao.getSalaryCtrl(custWorker);
	}
	
	public List<Map<String, Object>> getTechPhotoList(CustWorker custWorker) {
		return workerDao.getTechPhotoList(custWorker);
	}
	
	public List<Map<String, Object>> getNotCompeletePrjItem(WorkerAppEvt evt) {
		return workerDao.getNotCompeletePrjItem(evt);
	}

	@Override
	public List<Map<String, Object>> getWorkType2(WorkerAppEvt evt) {
		return workerDao.getWorkType2(evt);
	}
	
	@Override
	public String getSeqNo(String tableName){
		return super.getSeqNo(tableName);
	}
	
	public boolean getWorkSignByCustWkPk(int custWkPk){
		return workerDao.getWorkSignByCustWkPk(custWkPk);
	}

	@Override
	public Double getWaterArea(WorkerSignInEvt evt) {
		return workerDao.getWaterArea(evt);
	}

	@Override
	public Result saveWorkerWorkType12ForProc(Worker worker, String xmlData) {
		return workerDao.saveWorkerWorkType12ForProc(worker,xmlData);
	}

	@Override
	public Map<String, Object> getAllowSecondSignIn(Integer custWkPk) {
		return workerDao.getAllowSecondSignIn(custWkPk);
	}

	@Override
	public List<Map<String,Object>>  getWorkerRatedSalaryList(String custCode) {
		return workerDao.getWorkerRatedSalaryList(custCode);
	}
	
	@Override
	public List<Map<String,Object>> getWorkerRatedSalary(CustWorker custWorker) {
		return workerDao.getWorkerRatedSalary(custWorker);
	}
	
	@Override
	public List<Map<String,Object>> getInstallInfoList(CustWorker custWorker) {
		return workerDao.getInstallInfoList(custWorker);
	}

    @Override
    public boolean checkCanDeleteMember(String memberCode) {
        
        return workerDao.checkCanDeleteMember(memberCode);
    }
    
    @Override
    public List<Map<String, Object>> getWorkSignPic(String no) {
    	return workerDao.getWorkSignPic(no);
    }
	
}
