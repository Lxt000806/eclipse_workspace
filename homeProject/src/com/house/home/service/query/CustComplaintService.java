package com.house.home.service.query;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetCustComplaintListEvt;
import com.house.home.entity.project.CustComplaint;

public interface CustComplaintService extends BaseService {

	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, CustComplaint custComplaint);
	
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, CustComplaint custComplaint);

	public Page<Map<String, Object>> findComplaintDetailPageBySql(Page<Map<String, Object>> page, CustComplaint custComplaint);

	public Page<Map<String, Object>> getCustComplaintList(Page<Map<String, Object>> page, GetCustComplaintListEvt evt, UserContext uc);
	
	public Map<String, Object> getCustComplaintDetail(String no);
	
	public List<Map<String, Object>> getCustComplaint(String no);

	public List<Map<String, Object>> getCustEval(String custCode);

	public List<Map<String, Object>> getCustAddress(String identity,String phone);
	
	public List<Map<String, Object>> getCustComplaintDetailList(String no);
	
	public Map<String, Object>getCustInfo(String custCode);
	
	public Result doSave(CustComplaint custComplaint);
	
	public Result doUpdate(CustComplaint custComplaint);

	public Page<Map<String, Object>> getCustComplaintList_forCust(Page<Map<String, Object>> page, GetCustComplaintListEvt evt);

}
