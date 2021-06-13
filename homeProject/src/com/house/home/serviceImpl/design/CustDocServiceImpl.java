package com.house.home.serviceImpl.design;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.EmployeeDao;
import com.house.home.dao.basic.PersonMessageDao;
import com.house.home.dao.design.CustDocDao;
import com.house.home.dao.design.CustomerDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.CustDoc;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.CustDocService;

@SuppressWarnings("serial")
@Service
public class CustDocServiceImpl extends BaseServiceImpl implements CustDocService{
	
	@Autowired
	private CustDocDao custDocDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private PersonMessageDao personMessageDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,CustDoc custDoc,UserContext uc) {
		
		return custDocDao.findPageBySql(page,custDoc,uc);
	}

	@Override
	public Page<Map<String, Object>> findDocPageBySql(
			Page<Map<String, Object>> page, CustDoc custDoc,String docType1) {
		return custDocDao.findDocPageBySql(page, custDoc,docType1);
	}
	
	@Override
	public Page<Map<String, Object>> findChgPageBySql(
			Page<Map<String, Object>> page, CustDoc custDoc,String docType1,UserContext uc) {
		return custDocDao.findChgPageBySql(page, custDoc,docType1,uc);
	}
	
	@Override
	public Page<Map<String, Object>> goDocChgConByJqGrid(
			Page<Map<String, Object>> page, CustDoc custDoc) {

		return custDocDao.goDocChgConByJqGrid(page, custDoc);
	}

	@Override
	public void doDeleteDoc(String custCode, String docName) {
		custDocDao.doDeleteDoc(custCode,docName);
		
	}
	
	@Override
	public void doEditDescr(int pk, String descr,String czybh) {
		custDocDao.doEditDescr(pk,descr,czybh);

	}

	@Override
	public Map<String, Object> getMinDocType2(String docType1) {
		return custDocDao.getMinDocType2(docType1);
	}

	@Override
	public Map<String, Object> getDocType2Info(String docType2) {
		return custDocDao.getDocType2Info(docType2);
	}

	@Override
	public void doSaveDesignPic(String custCode, String lastUpdatedBy) {
		custDocDao.doSaveDesignPic(custCode,lastUpdatedBy);
	}

	@Override
	public void updateDesignPic(String custCode, String status,
			String submitCZY, String confirmCZY, String confirmRemarks,
			String LastUpdatedby,String isFullColorDraw, Integer drawQty, Integer draw3dQty) {
		if("2".equals(status) ){
			custDocDao.updateDesignPicSub(custCode,status,submitCZY,confirmCZY,confirmRemarks,LastUpdatedby);
		}else if("3".equals(status)||"4".equals(status)){
            custDocDao.updateDesignPicCon(custCode, status, submitCZY,
                    confirmCZY, confirmRemarks, LastUpdatedby, isFullColorDraw,
                    drawQty,draw3dQty);
		}
		custDocDao.updateDesignDocStatus(custCode, LastUpdatedby,status);
	
	}
	
	@Override
	public void updateCustDocStatus(String custCode, String LastUpdatedby) {
		custDocDao.updateCustDocStatus(custCode,LastUpdatedby);
	}

	@Override
	public void doRetConfirm(String custCode, String status, String submitCZY,
			String confirmCZY, String confirmRemarks, String LastUpdatedby) {
		 this.custDocDao.doRetComfirm(custCode, status, submitCZY, confirmCZY, confirmRemarks, LastUpdatedby);
	}

	@Override
	public List<Map<String, Object>> hasManageAreaRight(String czy) {
		return custDocDao.hasManageAreaRight(czy);
	}

	@Override
	public void updateInnerArea(Customer customer) {
		custDocDao.updateInnerArea(customer);
	}

	@Override
	public boolean getIsAllowChg(String custCode) {
		return custDocDao.getIsAllowChg(custCode);
	}
	
	@Override
	public boolean getIsAllowCommi(String custCode) {
		return custDocDao.getIsAllowCommi(custCode);
	}
	
	@Override
	public void doConfirmPass(CustDoc custDoc) {
	    
	    custDocDao.updateDesignPicPrg(custDoc);
	    
        custDocDao.updateCustDoc(custDoc);
        
        // 增加图纸变更审核通过时发消息通知项目经理功能
        // 张海洋 20200603
        Customer customer = customerDao.get(Customer.class, custDoc.getCustCode());
        Employee employee = employeeDao.get(Employee.class, custDoc.getLastUpdatedBy());
        
        // 如果此楼盘没项目经理直接返回
        // 张海洋 20200613
        if (StringUtils.isNotBlank(customer.getProjectMan())) {
            
            PersonMessage personMessage = new PersonMessage();
            personMessage.setMsgType("19");
            personMessage.setMsgText(customer.getAddress() +
                    "：施工图纸变更已审核通过，确认人：" + employee.getNameChi());
     
            personMessage.setMsgRelCustCode(customer.getCode());
            personMessage.setCrtDate(new Date());
            personMessage.setSendDate(new Date());
            personMessage.setRcvType("1");
            personMessage.setRcvCzy(customer.getProjectMan());
            personMessage.setRcvStatus("0");
            personMessage.setIsPush("1");
            personMessage.setPushStatus("0");
                    
            personMessageDao.save(personMessage);
        }
        
	}
	
	@Override
    public void doConfirmBack(CustDoc custDoc) {
	    
        custDocDao.updateCustDoc(custDoc);
	}
	
	@Override
	public void doFinishChg(CustDoc custDoc, String lastUpdatedBy) {
		custDocDao.doFinishChg(custDoc,lastUpdatedBy);		
	}

	@Override
	public void updateCustCon(String custCode, String upDocType2, UserContext userContext) {
		List<Map<String, Object>> custDocList = custDocDao.findCustDoc(custCode, upDocType2);
		Date firstDocDate = new Date();
		CustCon custCon = new CustCon();
		Customer customer = this.get(Customer.class, custCode);
		if (custDocList.size() > 0) {
			firstDocDate = DateUtil.DateFormatString(custDocList.get(0).get("UploadDate").toString());
			if ("3".equals(upDocType2) && null == customer.getPlaneDate()) { //传的时候没有平面时间就纪录上去 20200107
				customer.setPlaneDate(firstDocDate);
				this.update(customer);
			}
			if (custDocList.size() == 1) {
				if ("3".equals(upDocType2)) {
					custCon.setRemarks(userContext.getZwxm()+"修改平面图信息");
				} else if ("10".equals(upDocType2)) {
					custCon.setRemarks(userContext.getZwxm()+"修改框图信息");
				}
				custCon.setCustCode(custCode);
				custCon.setConDate(new Date());
				custCon.setConMan(userContext.getCzybh());
				custCon.setLastUpdate(firstDocDate);
				custCon.setLastUpdatedBy(userContext.getCzybh());
				custCon.setActionLog("ADD");
				custCon.setExpired("F");
				custCon.setType("2"); //信息修改
				this.save(custCon);
			}
		}
	}

	@Override
	public void doExpiredDoc(String deleteId, UserContext userContext) {
		CustDoc custDoc = this.get(CustDoc.class, Integer.parseInt(deleteId));
		custDoc.setExpired("T");
		custDoc.setLastUpdate(new Date());
		custDoc.setLastUpdatedBy(userContext.getCzybh());
		custDoc.setActionLog("Edit");
		this.update(custDoc);
	}

	@Override
	public void doDrawNoChg(CustDoc custDoc, String lastUpdatedBy) {
	    
		custDocDao.doDrawNoChg(custDoc, lastUpdatedBy);
		
		// 增加图纸未变更时发消息通知项目经理功能
		// 张海洋 20200603
		Customer customer = customerDao.get(Customer.class, custDoc.getCustCode());
		Employee employee = employeeDao.get(Employee.class, lastUpdatedBy);
		
		PersonMessage personMessage = new PersonMessage();
		personMessage.setMsgType("19");
		personMessage.setMsgText(customer.getAddress() +
		        "：施工图纸确认不进行变更，确认人：" + employee.getNameChi());
 
		personMessage.setMsgRelCustCode(customer.getCode());
		personMessage.setCrtDate(new Date());
		personMessage.setSendDate(new Date());
		personMessage.setRcvType("1");
		personMessage.setRcvCzy(customer.getProjectMan());
		personMessage.setRcvStatus("0");
		personMessage.setIsPush("1");
		personMessage.setPushStatus("0");
				
		personMessageDao.save(personMessage);
	}

	@Override
	public boolean getIsAllowAdd(String custCode) {
		return custDocDao.getIsAllowAdd(custCode);
	}
	
}
