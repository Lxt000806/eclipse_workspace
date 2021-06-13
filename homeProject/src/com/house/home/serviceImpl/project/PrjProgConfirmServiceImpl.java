package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetConfirmInfoEvt;
import com.house.home.client.service.evt.SavePrjProgConfirmEvt;
import com.house.home.dao.basic.EmployeeDao;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.basic.XtdmDao;
import com.house.home.dao.project.PrjProgConfirmDao;
import com.house.home.dao.project.PrjProgDao;
import com.house.home.dao.project.PrjProgPhotoDao;
import com.house.home.entity.basic.ConfirmCustomFiled;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.WorkerMessage;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ConfirmCustomFiledValue;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgConfirm;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.service.project.PrjProgConfirmService;
@SuppressWarnings("serial")
@Service
public class PrjProgConfirmServiceImpl extends BaseServiceImpl implements
		PrjProgConfirmService {
	@Autowired
	private PrjProgConfirmDao prjProgConfirmDao;
	@Autowired
	private PrjProgPhotoDao prjProgPhotoDao;
	@Autowired
	private PrjProgDao prjProgDao;
	@Autowired
	private XtdmDao xtdmDao;
	@Autowired
	private PersonMessageDao personMessageDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Override
	public Page<Map<String, Object>> getSiteConfirmListById(
			Page<Map<String, Object>> page, String czy,String custCode, String fromPageFlag) {
		// TODO Auto-generated method stub
		return prjProgConfirmDao.getSiteConfirmListById(
				page, czy,custCode, fromPageFlag);
	}

	@Override
	public Page<Map<String, Object>> getPrjItemDescr(
			Page<Map<String, Object>> page, String custCode,String prjRole,String prjItem) {
		// TODO Auto-generated method stub
		return prjProgConfirmDao.getPrjItemDescr(page,custCode,prjRole,prjItem) ;
	}
	
	@Override
	public Page<Map<String, Object>> getWorkerAppPrjItemDescr(
			Page<Map<String, Object>> page, String custCode,String prjRole,String prjItem,String workerApp) {
		// TODO Auto-generated method stub
		return prjProgConfirmDao.getWorkerAppPrjItemDescr(page,custCode,prjRole,prjItem,workerApp) ;
	}

	public String getWorkerCode(String custCode,String prjItem){
		return prjProgConfirmDao.getWorkerCode(custCode, prjItem);
	}
	
	@Override
	public void savePrjProgConfirm(PrjProgConfirm prjProgConfirm,String photoName,String type,Date endDate,JSONArray confirmCustomFiledValue) {
		WorkerMessage workerMessage=new WorkerMessage();
		Page<Map<String,Object>> page =null;
		String rcvCZYPrjItem = prjProgConfirm.getPrjItem();
		if("28".equals(rcvCZYPrjItem)){
			rcvCZYPrjItem="10";
		}
		//项目经理消息 
		PersonMessage personMessage=new PersonMessage();
		Customer customer=this.get(Customer.class, prjProgConfirm.getCustCode());
		Xtdm xtdm=xtdmDao.getByIdAndCbm("PRJITEM",prjProgConfirm.getPrjItem());
		
		Map<String,Object> map=prjProgDao.getPrjProgByCodeAndPrjItem(prjProgConfirm.getCustCode(),prjProgConfirm.getPrjItem());
		PrjProg prjProg=new PrjProg();
		BeanConvertUtil.mapToBean(map, prjProg);
		
		if("1".equals(prjProgConfirm.getIsPass())){
			//验证通过
//			Map<String,Object> map=prjProgDao.getPrjProgByCodeAndPrjItem(prjProgConfirm.getCustCode(),prjProgConfirm.getPrjItem());
//			PrjProg prjProg=new PrjProg();
//			 Customer customer=this.get(Customer.class, prjProgConfirm.getCustCode());
//			Xtdm xtdm=xtdmDao.getByIdAndCbm("PRJITEM",prjProgConfirm.getPrjItem());
			workerMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收通过");
			workerMessage.setMsgRelCustCode(customer.getCode());
//			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), prjProgConfirm.getPrjItem()));
			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), rcvCZYPrjItem));
			
			//项目经理消息 
			personMessage.setMsgType("19");
			personMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收通过。");
			
//			BeanConvertUtil.mapToBean(map, prjProg);
			prjProg.setConfirmDate(new Date());
			prjProg.setConfirmCZY(prjProgConfirm.getLastUpdatedBy());
			prjProg.setConfirmDesc(prjProgConfirm.getRemarks());
			if (null == prjProg.getEndDate()) {	//如果节点结束日期为空，设置为验收通过时间 add by zb on 20200416
				prjProg.setEndDate(new Date());
			}
			if(endDate != null){
				prjProg.setEndDate(endDate);
			}
			prjProg.setPrjLevel(prjProgConfirm.getPrjLevel());
			if(null!=prjProg.getPk()){
				prjProgDao.update(prjProg);
			}
		}else{
			//验收不通过
//			Map<String,Object> map=prjProgDao.getPrjProgByCodeAndPrjItem(prjProgConfirm.getCustCode(),prjProgConfirm.getPrjItem());
//			PrjProg prjProg=new PrjProg();
//			BeanConvertUtil.mapToBean(map, prjProg);
			prjProg.setEndDate(null);
			prjProg.setConfirmDesc(prjProgConfirm.getRemarks());
			if(null!=prjProg.getPk()){
				prjProgDao.update(prjProg);
			}
//			Customer customer=this.get(Customer.class, prjProgConfirm.getCustCode());
			workerMessage.setMsgRelCustCode(customer.getCode());
//			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), prjProgConfirm.getPrjItem()));
			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), rcvCZYPrjItem));
			workerMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收不通过");

//			Xtdm xtdm=xtdmDao.getByIdAndCbm("PRJITEM",prjProgConfirm.getPrjItem());
			Employee employee=employeeDao.get(Employee.class, customer.getProjectMan());
			 //项目经理消息  放到下面
//			PersonMessage personMessage=new PersonMessage();
			personMessage.setMsgType("10");
			personMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收不通过，请进入进度页面查看原因，并整改。");
//			workerMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收不通过");
//			personMessage.setMsgRelNo(prjProgConfirm.getNo());
			personMessage.setMsgRelCustCode(prjProg.getCustCode());
//			personMessage.setCrtDate(new Date());
//			personMessage.setSendDate(new Date());
//			personMessage.setRcvType("1");
//			personMessage.setRcvCzy(customer.getProjectMan());
//			personMessage.setIsPush("3".equals(prjProgConfirm.getFromCzy())?"0":"1");
//			personMessage.setPushStatus("0");
//			personMessage.setRcvStatus("0");
//			if(!"3".equals(prjProgConfirm.getFromCzy())){
//				personMessageDao.save(personMessage);	
//			}
			//工程部经理
			Employee leader=employeeDao.getDepartmentLeader(employee.getDepartment2());
			if(leader!=null){
				PersonMessage personMessage2=new PersonMessage();
				personMessage2.setMsgType("10");
				personMessage2.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收不通过，项目经理："+employee.getNameChi()+".");
				personMessage2.setMsgRelNo(prjProgConfirm.getNo());
				personMessage2.setMsgRelCustCode(prjProg.getCustCode());
				personMessage2.setCrtDate(new Date());
				personMessage2.setSendDate(new Date());
				personMessage2.setRcvType("3");
				personMessage2.setRcvCzy(leader.getNumber());
				personMessage2.setIsPush("1");
				personMessage2.setPushStatus("0");
				personMessage2.setRcvStatus("0");
				personMessageDao.save(personMessage2);	
			 }
			
		}
		if(StringUtils.isNotBlank(photoName)){
			String[] photoNameList=photoName.split(",");
			for(String str:photoNameList){
				PrjProgPhoto photo=new PrjProgPhoto();
				photo.setCustCode(prjProgConfirm.getCustCode());
				photo.setPrjItem(prjProgConfirm.getPrjItem());
				photo.setRefNo(prjProgConfirm.getNo());
				photo.setActionLog("Add");
				photo.setExpired("F");
				photo.setType("2");
				photo.setLastUpdate(new Date());
				photo.setLastUpdatedBy(prjProgConfirm.getLastUpdatedBy());
				photo.setPhotoName(str);
				photo.setIsPushCust("1");
				photo.setIsSendYun("1");
				photo.setSendDate(new Date());
				prjProgPhotoDao.save(photo);
			}
			
		}
		
		if(confirmCustomFiledValue != null) {
			for (int i = 0; i < confirmCustomFiledValue.size(); i++) {
				ConfirmCustomFiledValue ccfv = new ConfirmCustomFiledValue();
				JSONObject jsonObject = (JSONObject)confirmCustomFiledValue.get(i);
				ccfv.setPrjProgConfirmNo(prjProgConfirm.getNo());
				ccfv.setConfirmCustomFiledCode(jsonObject.get("code").toString());
				ccfv.setValue(jsonObject.get("value").toString());
				ccfv.setLastUpdate(new Date());
				ccfv.setLastUpdatedBy(prjProgConfirm.getLastUpdatedBy());
				ccfv.setActionLog("ADD");
				ccfv.setExpired("F");
				this.save(ccfv);
			}
		}
		
		Map<String,Object> prjConfirmAppMap = prjProgConfirmDao.prjConfirmAppExist(prjProgConfirm.getCustCode(),prjProgConfirm.getPrjItem());
		if(prjConfirmAppMap != null){
			PrjConfirmApp prjConfirmApp = new PrjConfirmApp();
			BeanConvertUtil.mapToBean(prjConfirmAppMap, prjConfirmApp);
			prjConfirmApp.setRefConfirmNo(prjProgConfirm.getNo());
			prjConfirmApp.setStatus("2");
			prjProgConfirmDao.update(prjConfirmApp);
		}
		prjProgConfirmDao.save(prjProgConfirm);
		
		workerMessage.setMsgType("2");
		workerMessage.setMsgRelNo(prjProgConfirm.getNo());
		workerMessage.setCrtDate(new Date());
		workerMessage.setSendDate(new Date());
		workerMessage.setRcvStatus("0");
		workerMessage.setIsPush("1");
		workerMessage.setPushStatus("0");
		if(StringUtils.isNotBlank(workerMessage.getRcvCZY())){
			this.save(workerMessage);
		}
		
		personMessage.setMsgRelNo(prjProgConfirm.getNo());
		personMessage.setMsgRelCustCode(prjProg.getCustCode());
		personMessage.setCrtDate(new Date());
		personMessage.setSendDate(new Date());
		personMessage.setRcvType("1");
		personMessage.setRcvCzy(customer.getProjectMan());
		personMessage.setIsPush("3".equals(prjProgConfirm.getFromCzy())?"0":"1");
		personMessage.setPushStatus("0");
		personMessage.setRcvStatus("0");
		if(!"3".equals(prjProgConfirm.getFromCzy())){
			personMessageDao.save(personMessage);	
		}
	}

	@Override
	public Map<String, Object> getSiteConfirmDetail(String no, String platform, String czybh, String custCode, String prjItem) {
		// TODO Auto-generated method stub
		return prjProgConfirmDao.getSiteConfirmDetail(no, platform, czybh, custCode, prjItem);
	}

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgConfirm prjProgConfirm){
		return prjProgConfirmDao.findPageBySql(page, prjProgConfirm);
	}
	
	public Page<Map<String,Object>> findConfirmPageBySql(Page<Map<String,Object>> page, PrjProgConfirm prjProgConfirm,UserContext uc){
		return prjProgConfirmDao.findConfirmPageBySql(page, prjProgConfirm,uc);
	}
	
	public Page<Map<String,Object>> findPrjConfirmPageBySql(Page<Map<String,Object>> page, PrjProgConfirm prjProgConfirm){
		return prjProgConfirmDao.findPrjConfirmPageBySql(page, prjProgConfirm);
	}

	public Page<Map<String,Object>> findConfirmAppPageBySql(Page<Map<String,Object>> page, PrjConfirmApp prjConfirmApp, UserContext uc){
		return prjProgConfirmDao.findConfirmAppPageBySql(page, prjConfirmApp, uc);
	}

	public Map<String,Object> prjConfirmAppExist(String custCode,String prjItem){
		return prjProgConfirmDao.prjConfirmAppExist(custCode,prjItem);
	}
	
	public Map<String,Object> checkPrjConfirmPassProg(String custCode,String prjItem){
		return prjProgConfirmDao.checkPrjConfirmPassProg(custCode,prjItem);
	}
	public List<Map<String,Object>> getConfirmPrjItem(String custCode){
		return prjProgConfirmDao.getConfirmPrjItem(custCode);
	}
	
	public Map<String,Object> getConfirmInfo(String custCode,String prjItem){
		return prjProgConfirmDao.getConfirmInfo(custCode,prjItem);
	}
	
	public Page<Map<String,Object>> getPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole,String regionCode,String isLeaveProblem){
		return prjProgConfirmDao.getPrjConfirmByPage(page,id,address,prjItem,prjRole,regionCode,isLeaveProblem);
	}
	
	public Page<Map<String,Object>> getPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole){
		return prjProgConfirmDao.getPrjConfirmByPage(page,id,address,prjItem,prjRole,"","");
	}
	
	public Page<Map<String,Object>> getWorkerAppPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole){
		return prjProgConfirmDao.getWorkerAppPrjConfirmByPage(page,id,address,prjItem,prjRole);
	}
	
	public void updatePrjProgConfirm(SavePrjProgConfirmEvt evt){
		PrjProgConfirm prjProgConfirm=prjProgConfirmDao.get(PrjProgConfirm.class, evt.getNo());
		Map<String,Object> map = null;
		PrjProg prjProg = null;
		WorkerMessage workerMessage=new WorkerMessage();
		Customer ct=this.get(Customer.class, evt.getCustCode());
		String rcvCZYPrjItem = evt.getPrjItem();
		if("28".equals(rcvCZYPrjItem)){
			rcvCZYPrjItem="10";
		}
		Customer customer=this.get(Customer.class, evt.getCustCode());
		Xtdm xtdm=xtdmDao.getByIdAndCbm("PRJITEM", evt.getPrjItem());
/*		if(!prjProgConfirm.getPrjItem().equals(evt.getPrjItem())){
			map=prjProgDao.getPrjProgByCodeAndPrjItem(prjProgConfirm.getCustCode(),prjProgConfirm.getPrjItem()); //将原节点进度验收相关字段清空
			prjProg=new PrjProg();
			BeanConvertUtil.mapToBean(map, prjProg);
			prjProg.setConfirmCZY(null);
			prjProg.setConfirmDate(null);
			prjProg.setPrjLevel(null);
			prjProg.setConfirmDesc(null);
			prjProgDao.merge(prjProg);
			
			List<String> photos = prjProgPhotoDao.getPhotoListByRefNo(evt.getNo(), evt.getPortalType()); //原先对应节点上传的图片改成新节点
			if(photos != null && photos.size()>0){
				for(int i=0;i< photos.size();i++){
					PrjProgPhoto prjProgPhoto = prjProgPhotoDao.getByPhotoName(photos.get(i));
					prjProgPhoto.setPrjItem(evt.getPrjItem());
					prjProgPhotoDao.update(prjProgPhoto);
				}
			}
		}*/
		
		if(StringUtils.isNotBlank(evt.getPhotoNameList())){     //新增新图片
			String[] newPhotos = evt.getPhotoNameList().split(",");
			for(String str:newPhotos){
				PrjProgPhoto photo=new PrjProgPhoto();
				photo.setCustCode(evt.getCustCode());
				photo.setPrjItem(evt.getPrjItem());
				photo.setRefNo(evt.getNo());
				photo.setActionLog("Add");
				photo.setExpired("F");
				photo.setType("2");
				photo.setLastUpdate(new Date());
				photo.setLastUpdatedBy(evt.getLastUpdatedBy());
				photo.setPhotoName(str);
				photo.setIsPushCust("1");
				photo.setIsSendYun("1");
				photo.setSendDate(new Date());
				prjProgPhotoDao.save(photo);
			}
		}
		
		if(evt.getConfirmCustomFiledValue() != null){
			for (int i = 0; i < evt.getConfirmCustomFiledValue().size(); i++) {
				JSONObject jsonObject = (JSONObject)evt.getConfirmCustomFiledValue().get(i);
				ConfirmCustomFiledValue confirmCustomFiledValue = this.get(ConfirmCustomFiledValue.class, Integer.parseInt(jsonObject.get("confirmCustomFiledValuePk").toString()));
				if(!confirmCustomFiledValue.getValue().equals(jsonObject.get("value").toString())) {
					confirmCustomFiledValue.setValue(jsonObject.get("value").toString());
					confirmCustomFiledValue.setLastUpdate(new Date());
					confirmCustomFiledValue.setLastUpdatedBy(evt.getLastUpdatedBy());
					confirmCustomFiledValue.setActionLog("EDIT");
					this.update(confirmCustomFiledValue);
				}
			}
		}
		

		PersonMessage personMessage = new PersonMessage();
		personMessage.setMsgRelNo(evt.getNo());
		personMessage.setMsgRelCustCode(evt.getCustCode());
		personMessage.setMsgType("10");
		List<PersonMessage> list = personMessageDao.getPersonMessageList(personMessage);   //删除生成的消息
		personMessage.setMsgType("19");
		List<PersonMessage> msgTypelist19 = personMessageDao.getPersonMessageList(personMessage);   //删除生成的消息
		personMessageDao.deleteWorkerMessage(personMessage.getMsgRelNo());
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				personMessageDao.delete(list.get(i));
			}
		}
		// 通知消息
		if(msgTypelist19 != null && msgTypelist19.size() > 0){
			for(int i=0;i<msgTypelist19.size();i++){
				personMessageDao.delete(msgTypelist19.get(i));
			}
		}
		if("1".equals(evt.getIsPass())){
			map=prjProgDao.getPrjProgByCodeAndPrjItem(evt.getCustCode(),evt.getPrjItem());  
			prjProg=new PrjProg();
			BeanConvertUtil.mapToBean(map, prjProg);
			prjProg.setConfirmDate(new Date());
			prjProg.setConfirmCZY(evt.getLastUpdatedBy());
			prjProg.setConfirmDesc(evt.getRemarks());
			prjProg.setPrjLevel(evt.getPrjLevel());
			prjProgDao.merge(prjProg);
			
			//项目经理消息
			personMessage=new PersonMessage();
			personMessage.setMsgType("19");
			personMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收通过。");
			personMessage.setMsgRelNo(evt.getNo());
			personMessage.setMsgRelCustCode(prjProg.getCustCode());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvType("1");
			personMessage.setRcvCzy(customer.getProjectMan());
			personMessage.setIsPush("3".equals(evt.getFromCzy())?"0":"1");
			personMessage.setPushStatus("0");
			personMessage.setRcvStatus("0");
			if(!"3".equals(prjProgConfirm.getFromCzy())){
				personMessageDao.save(personMessage);
			}
		}else{
			map=prjProgDao.getPrjProgByCodeAndPrjItem(evt.getCustCode(),evt.getPrjItem());
			prjProg=new PrjProg();
			BeanConvertUtil.mapToBean(map, prjProg);
			prjProg.setEndDate(null);
			prjProg.setConfirmCZY(null);
			prjProg.setConfirmDate(null);
			prjProg.setPrjLevel(null);
			prjProg.setConfirmDesc(evt.getRemarks());
			prjProgDao.merge(prjProg);
//			Customer customer=this.get(Customer.class, prjProg.getCustCode());
//			Xtdm xtdm=xtdmDao.getByIdAndCbm("PRJITEM",prjProg.getPrjItem());
			Employee employee=employeeDao.get(Employee.class, customer.getProjectMan());
			//项目经理消息
			personMessage=new PersonMessage();
			personMessage.setMsgType("10");
			personMessage.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收不通过，请进入进度页面查看原因，并整改。");
			personMessage.setMsgRelNo(evt.getNo());
			personMessage.setMsgRelCustCode(prjProg.getCustCode());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvType("1");
			personMessage.setRcvCzy(customer.getProjectMan());
			personMessage.setIsPush("3".equals(evt.getFromCzy())?"0":"1");
			personMessage.setPushStatus("0");
			personMessage.setRcvStatus("0");
			if(!"3".equals(prjProgConfirm.getFromCzy())){
				personMessageDao.save(personMessage);
			}
			//工程部经理
			Employee leader=employeeDao.getDepartmentLeader(employee.getDepartment2());
			if(leader!=null){
				PersonMessage personMessage2=new PersonMessage();
				personMessage2.setMsgType("10");
				personMessage2.setMsgText(customer.getAddress()+":【"+xtdm.getNote()+"】节点验收不通过，项目经理："+employee.getNameChi()+".");
				personMessage2.setMsgRelNo(evt.getNo());
				personMessage2.setMsgRelCustCode(prjProg.getCustCode());
				personMessage2.setCrtDate(new Date());
				personMessage2.setSendDate(new Date());
				personMessage2.setRcvType("3");
				personMessage2.setRcvCzy(leader.getNumber());
				personMessage2.setIsPush("1");
				personMessage2.setPushStatus("0");
				personMessage2.setRcvStatus("0");
				personMessageDao.save(personMessage2);	
			}	
		}
		if("1".equals(evt.getIsPass())){
			Xtdm xt=xtdmDao.getByIdAndCbm("PRJITEM",evt.getPrjItem());
			workerMessage.setMsgRelCustCode(evt.getCustCode());
			workerMessage.setMsgRelNo(evt.getNo());
			workerMessage.setCrtDate(new Date());
			workerMessage.setSendDate(new Date());
			workerMessage.setMsgType("2");
			workerMessage.setMsgText(ct.getAddress()+":【"+xt.getNote()+"】节点验收通过");
			workerMessage.setRcvStatus("0");
			workerMessage.setIsPush("1");
			workerMessage.setPushStatus("0");
//			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), evt.getPrjItem()));
			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), rcvCZYPrjItem));
			
			this.save(workerMessage);
		}else {
			Xtdm xt=xtdmDao.getByIdAndCbm("PRJITEM",evt.getPrjItem());
			workerMessage.setMsgRelCustCode(evt.getCustCode());
			workerMessage.setMsgRelNo(evt.getNo());
			workerMessage.setCrtDate(new Date());
			workerMessage.setSendDate(new Date());
			workerMessage.setMsgType("2");
			workerMessage.setMsgText(ct.getAddress()+":【"+xt.getNote()+"】节点验收未通过");
			workerMessage.setRcvStatus("0");
			workerMessage.setIsPush("1");
			workerMessage.setPushStatus("0");
			// workerMessage.setRcvCZY(getWorkerCode(evt.getCustCode(), evt.getPrjItem()));
			workerMessage.setRcvCZY(getWorkerCode(prjProgConfirm.getCustCode(), rcvCZYPrjItem));
			this.save(workerMessage);
		}
		
		prjProgConfirm.setPrjItem(evt.getPrjItem());
		prjProgConfirm.setRemarks(evt.getRemarks());
		prjProgConfirm.setAddress(evt.getAddress());
		prjProgConfirm.setIsPass(evt.getIsPass());
		prjProgConfirm.setIsPushCust(evt.getIsPushCust());
		prjProgConfirm.setPrjWorkable(evt.getPrjWorkable());
		prjProgConfirm.setLastUpdate(new Date());
		if("16".equals(evt.getPrjItem())){
			prjProgConfirm.setAppCheck(evt.getAppCheck());
		}else{
			prjProgConfirm.setAppCheck("0");
		}
		if("1".equals(evt.getIsPass())){
			prjProgConfirm.setPrjLevel(evt.getPrjLevel());
		}else{
			prjProgConfirm.setPrjLevel(null);
		}
		prjProgConfirm.setLastUpdatedBy(evt.getLastUpdatedBy());
		prjProgConfirm.setActionLog("EDIT");
		prjProgConfirmDao.update(prjProgConfirm);
	}
	
	@Override
	public boolean existCustPrjItemPass(String custCode, String prjItem){
		return prjProgConfirmDao.existCustPrjItemPass(custCode, prjItem);
	}
	
	@Override
	public Map<String, Object> getSiteConfirmStatus(String custCode, String prjItem){
		return prjProgConfirmDao.getSiteConfirmStatus(custCode, prjItem);
	}

	@Override
	public String getAllowItemApp(String id) {
		// TODO Auto-generated method stub
		return prjProgConfirmDao.getAllowItemApp(id);
	}
	
	@Override
	public List<Map<String, Object>> findNoSendYunPics(String type){
		return prjProgConfirmDao.findNoSendYunPics(type);
	}
	
	@Override
	public void setPrjConfirmPhotoIsPushCust(){
		prjProgConfirmDao.setPrjConfirmPhotoIsPushCust();
	}
	
	@Override
	public void setPrjProgConfirmIsPushCust(){
		prjProgConfirmDao.setPrjProgConfirmIsPushCust();
	}
	
	@Override
	public String getSeqNo(String tableName){
		return super.getSeqNo(tableName);
	}
	
	@Override
	public List<Map<String, Object>> getConfirmCustomFiledList(String prjItem, String prjProgConfirmNo) {
		return prjProgConfirmDao.getConfirmCustomFiledList(prjItem,prjProgConfirmNo);
	}
	
	@Override
	public List<Map<String, Object>> getPrjProgPhoto(String no) {
		return prjProgConfirmDao.getPrjProgPhoto(no);
	}
}
