package com.house.home.serviceImpl.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ModifyConfirmQueryEvt;
import com.house.home.client.service.evt.PrjProgCheckQueryEvt;
import com.house.home.client.service.evt.PrjProgCheckUpdateEvt;
import com.house.home.client.service.evt.SitePreparationEvt;
import com.house.home.client.service.evt.WorkerListEvt;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.design.CustomerDao;
import com.house.home.dao.project.PrjProgCheckDao;
import com.house.home.dao.project.PrjProgPhotoDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.BuilderRep;
import com.house.home.entity.project.CustCheckData;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.project.PrjProgCheckService;

@SuppressWarnings("serial")
@Service
public class PrjProgCheckServiceImpl extends BaseServiceImpl implements PrjProgCheckService {

	@Autowired
	private PrjProgCheckDao prjProgCheckDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private PersonMessageDao personMessageDao;
	@Autowired
	private PrjProgPhotoDao prjProgPhotoDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck){
		return prjProgCheckDao.findPageBySql(page, prjProgCheck);
	}
	
	public Page<Map<String,Object>> findConfirmPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,UserContext uc){
		return prjProgCheckDao.findConfirmPageBySql(page, prjProgCheck,uc);
	}
	
	public Page<Map<String,Object>> findPrjCheckPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck){
		return prjProgCheckDao.findPrjCheckPageBySql(page, prjProgCheck);
	}
	
	public Page<Map<String,Object>> findCheckPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,UserContext uc){
		return prjProgCheckDao.findCheckPageBySql(page, prjProgCheck,uc);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<Map<String, Object>> findPageByLastUpdatedBy(Page page,
			String id,String address,String isModify,String custCode) {
		return prjProgCheckDao.findPageByLastUpdatedBy(page,id,address,isModify,custCode);
	}

	@Override
	public void addPrjProgCheck(PrjProgCheck prjProgCheck,ProgCheckPlanDetail progCheckPlanDetail,String isExecute,String pk) {
			String no = super.getSeqNo("tPrjProgCheck");
			Date nowTime= new Date();
			PrjProgCheck pc = new PrjProgCheck();
			pc.setNo(no);
			pc.setCustCode(prjProgCheck.getCustCode());
			pc.setPrjItem(prjProgCheck.getPrjItem());
			pc.setRemarks(prjProgCheck.getRemarks());
			pc.setAddress(prjProgCheck.getAddress());
			pc.setBuildStatus(prjProgCheck.getBuildStatus());
			pc.setDate(nowTime);
			pc.setLastUpdate(new Date());
			pc.setLastUpdatedBy(prjProgCheck.getLastUpdatedBy());
			pc.setActionLog("ADD");
			pc.setExpired("F");
			pc.setAppCzy(prjProgCheck.getLastUpdatedBy());
			pc.setIsModify(prjProgCheck.getIsModify());
			pc.setModifyTime(prjProgCheck.getModifyTime());
			pc.setModifyComplete("0");
			pc.setArtProm(prjProgCheck.getArtProm());
			pc.setSafeProm(prjProgCheck.getSafeProm());
			pc.setVisualProm(prjProgCheck.getVisualProm());
			pc.setErrPosi(prjProgCheck.getErrPosi());
			pc.setIsUpPrjProg(prjProgCheck.getIsUpPrjProg());
			pc.setLongitude(prjProgCheck.getLongitude());
			pc.setLatitude(prjProgCheck.getLatitude());
			
			pc.setKitchDoorType(prjProgCheck.getKitchDoorType());
			pc.setBalconyTitle(prjProgCheck.getBalconyTitle());
			pc.setIsWood(prjProgCheck.getIsWood());
			pc.setIsBuildWall(prjProgCheck.getIsBuildWall());
			pc.setToiletNum(prjProgCheck.getToiletNum());
			pc.setBedroomNum(prjProgCheck.getBedroomNum());
			pc.setBalconyNum(prjProgCheck.getBalconyNum());
			pc.setCigaNum(progCheckPlanDetail.getCigaNum());
			if(prjProgCheck.getIsPushCust()==null){
				pc.setIsPushCust("0".equals(prjProgCheck.getIsModify())?"1":"0");
			}else{
				pc.setIsPushCust(prjProgCheck.getIsPushCust());
			}

			/**
			 * 新增巡检不更新进度表时间
			 * remove by zzr 2018/04/17
			 */
/*			if(!"16".equals(prjProgCheck.getPrjItem()) && "1".equals(prjProgCheck.getIsUpPrjProg())){
				PrjProg pg =  this.get(PrjProg.class, Integer.parseInt(pk));
				pg.setBeginDate(new Date());
				pg.setLastUpdate(new Date());
				pg.setLastUpdatedBy(prjProgCheck.getLastUpdatedBy());
				pg.setActionLog("EDIT");
				pg.setExpired("F");
				this.update(pg);
			}*/
			
			if(prjProgCheckDao.isCheckDepart(pc.getAppCzy()) == true){
				pc.setIsCheckDept("1");
			}else{
				if(prjProgCheckDao.isInDepart(pc.getAppCzy(), "3")==true){
					pc.setIsCheckDept("0");
				}else{
					pc.setIsCheckDept("2");
				}
			}
			if("1".equals(isExecute)){
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");   
				String date = sDateFormat.format(nowTime); 
				ProgCheckPlanDetail pcpd = this.get(ProgCheckPlanDetail.class, progCheckPlanDetail.getPk());
				pcpd.setStatus(progCheckPlanDetail.getStatus());
				pcpd.setAppCZY(progCheckPlanDetail.getAppCZY());
				try{
					pcpd.setAppDate(sDateFormat.parse(date));
				}catch (Exception e) {
					e.printStackTrace();
				}
				pcpd.setCheckNo(no);
				pcpd.setLastUpdate(new Date());
				pcpd.setActionLog("ADT");
				pcpd.setExpired("F");
				pcpd.setLastUpdatedBy(progCheckPlanDetail.getLastUpdatedBy());
				this.update(pcpd);
			}
			
			//需要整改，自动取楼盘对应项目经理的操作员编号，填入整改人。
			//向该楼盘项目经理发送一条通知。楼盘:您有一条新的整改单，请立即处理。
			if ("1".equals(pc.getIsModify())){
				Customer customer = customerDao.get(Customer.class, pc.getCustCode());
				String projectMan = customer.getProjectMan();
				if (StringUtils.isNotBlank(projectMan)){
					pc.setCompCzy(projectMan);
					PersonMessage personMessage = new PersonMessage();
					personMessage.setMsgType("7");
					personMessage.setMsgText(customer.getAddress()+":您有一条新的整改单，请立即处理。");
					personMessage.setMsgRelNo(no);
					personMessage.setMsgRelCustCode(pc.getCustCode());
					personMessage.setCrtDate(new Date());
					personMessage.setSendDate(new Date());
					personMessage.setRcvType("1");
					personMessage.setRcvCzy(projectMan);
					personMessage.setIsPush("1");
					personMessage.setPushStatus("0");
					personMessage.setRcvStatus("0");
					personMessageDao.save(personMessage);
				}
			}
			/**
			 * 增加房间信息填写
			 * add by zzr 2018/04/19
			 */
			CustCheckData custCheckData = prjProgCheckDao.get(CustCheckData.class, pc.getCustCode());
			if(custCheckData == null){
				custCheckData = new CustCheckData();
				custCheckData.setCustCode(pc.getCustCode());
				custCheckData.setActionLog("ADD");
				custCheckData.setExpired("F");
			}else{
				custCheckData.setActionLog("EDIT");
			}
			custCheckData.setLastUpdate(new Date());
			custCheckData.setLastUpdatedBy(prjProgCheck.getLastUpdatedBy());
			if(StringUtils.isNotBlank(pc.getKitchDoorType())){
				custCheckData.setKitchDoorType(pc.getKitchDoorType());
			}
			if(StringUtils.isNotBlank(pc.getBalconyTitle())){
				custCheckData.setBalconyTitle(pc.getBalconyTitle());
			}
			if(StringUtils.isNotBlank(pc.getIsWood())){
				custCheckData.setIsWood(pc.getIsWood());
			}
			if(StringUtils.isNotBlank(pc.getIsBuildWall())){
				custCheckData.setIsBuildWall(pc.getIsBuildWall());
			}
			if(pc.getToiletNum() != null && pc.getToiletNum() > 0){
				custCheckData.setToiletNum(pc.getToiletNum());
			}
			if(pc.getBedroomNum() != null && pc.getBedroomNum() > 0){
				custCheckData.setBedroomNum(pc.getBedroomNum());
			}
			if(pc.getBalconyNum() != null && pc.getBalconyNum() > 0){
				custCheckData.setBalconyNum(pc.getBalconyNum());
			}
			if("ADD".equals(custCheckData.getActionLog())){
				prjProgCheckDao.save(custCheckData);
			}else{
				prjProgCheckDao.update(custCheckData);
			}
			
			prjProgCheckDao.save(pc);
			
			String str = prjProgCheck.getPhotoString();
			if (StringUtils.isNotBlank(str)){
				String[] arr = str.split(",");
				for (String photoName: arr){
					PrjProgPhoto prjProgPhoto = new PrjProgPhoto();
					prjProgPhoto.setCustCode(prjProgCheck.getCustCode());
					prjProgPhoto.setPrjItem(prjProgCheck.getPrjItem());
					prjProgPhoto.setPhotoName(photoName);
					prjProgPhoto.setType("3");//工地巡检
					prjProgPhoto.setRefNo(no);
					prjProgPhoto.setLastUpdate(new Date());
					prjProgPhoto.setLastUpdatedBy(prjProgCheck.getLastUpdatedBy());
					prjProgPhoto.setActionLog("ADD");
					prjProgPhoto.setExpired("F");
					prjProgPhoto.setIsPushCust("1");
					prjProgPhoto.setIsSendYun("1");
					prjProgPhoto.setSendDate(new Date());
					prjProgPhotoDao.save(prjProgPhoto);
					prjProgPhoto = null;
				}
			}
	}

	@Override
	public Map<String, Object> getByNo(String id, String czybh) {
		return prjProgCheckDao.getByNo(id, czybh);
	}

	@Override
	public Page<Map<String, Object>> findPageByCzy(
			Page<Map<String, Object>> page, String czy) {
		// TODO Auto-generated method stub
		return prjProgCheckDao.findPageByCzy(page,czy);
	}

	@Override
	public Map<String, Object> getSiteModifyDetailById(String id) {
		// TODO Auto-generated method stub
		return prjProgCheckDao.getSiteModifyDetailById(id);
	}

	@Override
	public void saveSiteModify(PrjProgCheck prjProgCheck, String photoName) {
		if(StringUtils.isNotBlank(photoName)){
			String[] photoNameList=photoName.split(",");
			for(String str:photoNameList){
				PrjProgPhoto photo=new PrjProgPhoto();
				photo.setCustCode(prjProgCheck.getCustCode());
				photo.setPrjItem(prjProgCheck.getPrjItem());
				photo.setPhotoName(str);
				photo.setLastUpdatedBy(prjProgCheck.getCompCzy());
				photo.setActionLog("Add");
				photo.setExpired("F");
				photo.setType("4");
				photo.setRefNo(prjProgCheck.getNo());
				photo.setLastUpdate(new Date());
				photo.setIsPushCust("1");
				photo.setIsSendYun("1");
				photo.setSendDate(new Date());
				prjProgPhotoDao.save(photo);
			}
			
			
		}
		//更新消息状态
		PersonMessage personMessage=new PersonMessage();
		personMessage.setMsgType("7");
		personMessage.setMsgRelNo(prjProgCheck.getNo());
		List<PersonMessage> list=personMessageDao.getPersonMessageList(personMessage);
		for(PersonMessage message:list){
			message.setRcvStatus("1");
			message.setRcvDate(new Date());
			personMessageDao.update(message);
		}
		prjProgCheckDao.update(prjProgCheck);
	}

	@Override
	public Page<Map<String, Object>> findModifiedPageByCzy(
			Page<Map<String, Object>> page, String czy,String address) {
		// TODO Auto-generated method stub
		return prjProgCheckDao.findModifiedPageByCzy(page,czy,address);
	}

	@Override
	public void updatePrjProgCheck(PrjProgCheck prjProgCheck,
			PrjProgCheckUpdateEvt evt) {
		
			/**
			 * 增加房间信息填写
			 * add by zzr 2018/04/19
			 */
			CustCheckData custCheckData = prjProgCheckDao.get(CustCheckData.class, prjProgCheck.getCustCode());

			custCheckData.setActionLog("EDIT");
			custCheckData.setLastUpdate(new Date());
			custCheckData.setLastUpdatedBy(prjProgCheck.getLastUpdatedBy());
			
			if(prjProgCheck.getKitchDoorType() != null){
				custCheckData.setKitchDoorType(evt.getKitchDoorType());
				prjProgCheck.setKitchDoorType(evt.getKitchDoorType());
			}
			if(prjProgCheck.getBalconyTitle() != null){
				custCheckData.setBalconyTitle(evt.getBalconyTitle());
				prjProgCheck.setBalconyTitle(evt.getBalconyTitle());
			}
			if(prjProgCheck.getIsWood() != null){
				custCheckData.setIsWood(evt.getIsWood());
				prjProgCheck.setIsWood(evt.getIsWood());
			}
			if(prjProgCheck.getIsBuildWall() != null){
				custCheckData.setIsBuildWall(evt.getIsBuildWall());
				prjProgCheck.setIsBuildWall(evt.getIsBuildWall());
			}
			if(prjProgCheck.getToiletNum() != null && prjProgCheck.getToiletNum() > 0){
				custCheckData.setToiletNum(evt.getToiletNum());
				prjProgCheck.setToiletNum(evt.getToiletNum());
			}
			if(prjProgCheck.getBedroomNum() != null && prjProgCheck.getBedroomNum() > 0){
				custCheckData.setBedroomNum(evt.getBedroomNum());
				prjProgCheck.setBedroomNum(evt.getBedroomNum());
			}
			if(prjProgCheck.getBalconyNum() != null && prjProgCheck.getBalconyNum() > 0){
				custCheckData.setBalconyNum(evt.getBalconyNum());
				prjProgCheck.setBalconyNum(evt.getBalconyNum());
			}
			
			prjProgCheckDao.update(custCheckData);		
		
		    String photoNames=evt.getPhotoString();
			if (StringUtils.isNotBlank(photoNames)){
				String[] arr = photoNames.split(",");
				for (String photoName: arr){
					PrjProgPhoto prjProgPhoto = prjProgPhotoDao.getByPhotoName(photoName);
					if(prjProgPhoto==null){
						prjProgPhoto = new PrjProgPhoto();
						prjProgPhoto.setCustCode(prjProgCheck.getCustCode());
						prjProgPhoto.setPrjItem(prjProgCheck.getPrjItem());
						prjProgPhoto.setPhotoName(photoName);
						prjProgPhoto.setType("3");//工地巡检
						prjProgPhoto.setRefNo(prjProgCheck.getNo());
						prjProgPhoto.setLastUpdate(new Date());
						prjProgPhoto.setLastUpdatedBy(prjProgCheck.getLastUpdatedBy());
						prjProgPhoto.setActionLog("ADD");
						prjProgPhoto.setExpired("F");
						prjProgPhoto.setIsPushCust("1");
						prjProgPhoto.setIsSendYun("1");
						prjProgPhoto.setSendDate(new Date());
						prjProgPhotoDao.save(prjProgPhoto);
					}
					
				}
			}
			if ("0".equals(prjProgCheck.getIsModify())&&"1".equals(evt.getIsModify())){
				Customer customer = customerDao.get(Customer.class, prjProgCheck.getCustCode());
				String projectMan = customer.getProjectMan();
				if (StringUtils.isNotBlank(projectMan)){
					prjProgCheck.setCompCzy(projectMan);
					PersonMessage personMessage = new PersonMessage();
					personMessage.setMsgType("7");
					personMessage.setMsgText(customer.getAddress()+":您有一条新的整改单，请立即处理。");
					personMessage.setMsgRelNo(prjProgCheck.getNo());
					personMessage.setMsgRelCustCode(prjProgCheck.getCustCode());
					personMessage.setCrtDate(new Date());
					personMessage.setSendDate(new Date());
					personMessage.setRcvType("1");
					personMessage.setRcvCzy(projectMan);
					personMessage.setIsPush("1");
					personMessage.setPushStatus("0");
					personMessage.setRcvStatus("0");
					personMessageDao.save(personMessage);
				}
			}else if("1".equals(prjProgCheck.getIsModify())&&"0".equals(evt.getIsModify())){
				prjProgCheck.setCompCzy(null);
				PersonMessage personMessage=new PersonMessage();
				personMessage.setMsgType("7");
				personMessage.setMsgRelNo(prjProgCheck.getNo());
				List<PersonMessage> list=personMessageDao.getPersonMessageList(personMessage);
				for(PersonMessage message:list){
					personMessageDao.delete(message);
				}
			}
			prjProgCheck.setIsModify(evt.getIsModify());
			prjProgCheck.setIsPushCust(evt.getIsPushCust());
			prjProgCheckDao.update(prjProgCheck);
	}

	@Override
	public void updateSiteModify(PrjProgCheck prjProgCheck) {
		prjProgCheckDao.update(prjProgCheck);
		Customer customer = customerDao.get(Customer.class, prjProgCheck.getCustCode());
		PersonMessage personMessage = new PersonMessage();
		personMessage.setMsgType("7");
		personMessage.setMsgText(customer.getAddress()+":你的整改单需要重新整改，请立即处理。");
		personMessage.setMsgRelNo(prjProgCheck.getNo());
		personMessage.setMsgRelCustCode(prjProgCheck.getCustCode());
		personMessage.setCrtDate(new Date());
		personMessage.setSendDate(new Date());
		personMessage.setRcvType("1");
		personMessage.setRcvCzy(prjProgCheck.getCompCzy());
		personMessage.setIsPush("1");
		personMessage.setPushStatus("0");
		personMessage.setRcvStatus("0");
		personMessageDao.save(personMessage);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<Map<String, Object>> getModifyList(Page page,PrjProgCheckQueryEvt evt){
		return prjProgCheckDao.getModifyList(page, evt.getAddress(), evt.getCustCode());
	}
	public List<Map<String,Object>> isBegin(String custCode,String prjItem){
		return prjProgCheckDao.isBegin(custCode,prjItem);
	}

	@Override
	public Page<Map<String, Object>> getModifyConfirmList(Page page,
			ModifyConfirmQueryEvt evt) {
		// TODO Auto-generated method stub
		return prjProgCheckDao.getModifyConfirmList(page,evt);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<Map<String,Object>> getSitePreparationList(Page page,SitePreparationEvt evt){
		return prjProgCheckDao.getSitePreparationList(page,evt);
	}
	@Override
	public String hasSitePrepartion(SitePreparationEvt evt){
		List<Map<String,Object>> list = prjProgCheckDao.sitePreparationJudgeDate(evt.getType(),evt.getCustCode(),evt.getBeginDate(), evt.getEndDate());
		if(list != null && list.size()>0){
			if(evt.getType().equals("1")){
				return "该时间段已经进行过停工报备";
			}else{
				return "该时间段已经进行过安全报备";
			}
		}
		BuilderRep br = new BuilderRep();
		br.setCustCode(evt.getCustCode());
		br.setType(evt.getType());
		br.setBuildStatus(evt.getBuildStatus());
		br.setBeginDate(evt.getBeginDate());
		br.setEndDate(evt.getEndDate());
		br.setRemark(evt.getRemark());
		br.setLastUpdate(new Date());
		br.setLastUpdatedBy(evt.getProjectMan());
		br.setExpired("F");
		br.setActionLog("ADD");
		prjProgCheckDao.save(br);
		return "success";
	}
	@Override
	public Map<String,Object> getSitePrepartionById(SitePreparationEvt evt){
		return prjProgCheckDao.getSitePrepartionById(evt);
	}
	@Override
	public Map<String,Object> getCustCheck(SitePreparationEvt evt){
		return prjProgCheckDao.getCustCheck(evt);
	}
	
	@Override
	public Page<Map<String,Object>> getCustWorkerList(Page page,WorkerListEvt evt){
		return prjProgCheckDao.getCustWorkerList(page,evt);
	}

	//    add by hc  工地巡检分析   2017/11/14   begin 

	@Override       //工地巡检  --- 统计方式检索
	public Page<Map<String, Object>> findPageBySqlTJFS(
			Page<Map<String, Object>> page, PrjProgCheck prjProgCheck,String orderBy,String direction) {
		return prjProgCheckDao.findPageBySql_gdxj_Tjfs(page, prjProgCheck,orderBy,direction);
	}

	@Override   //工地巡检  --- 取到 RemainModifyTime
	public Map<String, Object> getRemainModifyTime(String No) {
		return prjProgCheckDao.findRemainModifyTime(No);
	}
	//    add by hc  工地巡检分析   2017/11/14   end 

	@Override
	public Map<String,Object> getIntProgress(String custCode){
		return prjProgCheckDao.getIntProgress(custCode);
	}
	
	@Override
	public Map<String, Object> getRoomInfo(String custCode, String czybh){
		return prjProgCheckDao.getRoomInfo(custCode, czybh);
	}
	
	@Override
	public Map<String,Object> getCustLoan(String custCode){
		return prjProgCheckDao.getCustLoan(custCode);
	}

	@Override
	public Page<Map<String, Object>> getRegionList(
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		return prjProgCheckDao.getRegionList(page);
	}

	@Override
	public Map<String, Object> getIntProduce(String custCode, String supplCode) {
		return prjProgCheckDao.getIntProduce(custCode,supplCode);
	}
	
	
}
