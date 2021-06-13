package com.house.home.serviceImpl.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustomerQueryEvt;
import com.house.home.client.service.evt.GetCustomerEvt;
import com.house.home.dao.design.CustomerDao;
import com.house.home.dao.design.ResrCustDao;
import com.house.home.dao.finance.DiscTokenDao;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;

@SuppressWarnings("serial")
@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ResrCustDao resrCustDao;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private DiscTokenDao discTokenDao;
	
	@Autowired
	private CustStakeholderService custStakeholderService;
	
	public Page<Map<String,Object>> findPrjProgPageBySql(Page<Map<String,Object>> page, Customer customer,UserContext uc){
		return customerDao.findPrjProgPageBySql(page, customer,uc);
	}
	
	public Page<Map<String,Object>> findSoftNotAppQueryPageBySql(Page<Map<String,Object>> page, Customer customer){
		return customerDao.findSoftNotAppQueryPageBySql(page, customer);
	}
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, 
			Customer customer, UserContext uc){
		return customerDao.findPageBySql(page, customer, uc);
	}
	
	public Page<Map<String,Object>> findPageForOABySql(Page<Map<String,Object>> page, 
			Customer customer,UserContext uc){
		return customerDao.findPageForOABySql(page, customer,uc);
	}
	
	public Page<Map<String,Object>> findPageBySql_client(Page<Map<String,Object>> page, 
			Customer customer, UserContext uc){
		return customerDao.findPageBySql_client(page, customer, uc);
	}

	public Result saveForProc(Customer customer) {
		return customerDao.saveForProc(customer);
	}

	public Result updateForProc(Customer customer) {
		return customerDao.updateForProc(customer);
	}

	public Customer getByDocumentNo(String documentNo) {
		return customerDao.getByDocumentNo(documentNo);
	}

	public Map<String,Object> getByAddress(String address,String czybh) {
		return customerDao.getByAddress(address,czybh);
	}

	public Page<Map<String,Object>> findPageBySql_xxcx(Page<Map<String,Object>> page, 
			Customer customer, UserContext uc){
		return customerDao.findPageBySql_xxcx(page, customer, uc);
	}

	public Page<Map<String, Object>> findPageBySql_code(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		return customerDao.findPageBySql_code(page, customer, uc);
	}

	public Map<String, Object> getCustomerPayByCode(String code) {
		return customerDao.getCustomerPayByCode(code);
	}

	public Double getCustomerZjzjeByCode(String id) {
		return customerDao.getCustomerZjzjeByCode(id);
	}

	public Double getCustomerHaspayByCode(String id) {
		return customerDao.getCustomerHaspayByCode(id);
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemReq(
			Page<Map<String, Object>> page, String code, String itemType1, String itemDescr,String itemType2) {
		return customerDao.findPageByCustomerCode_itemReq(page,code,itemType1,itemDescr,itemType2);
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemChange(
			Page<Map<String, Object>> page, String code, String itemType1, String itemType2) {
		return customerDao.findPageByCustomerCode_itemChange(page,code,itemType1, itemType2);
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemApp(
			Page<Map<String, Object>> page, String code, String itemType1,String refCustCode) {
		return customerDao.findPageByCustomerCode_itemApp(page,code,itemType1,refCustCode);
	}

	public List<Map<String, Object>> getPayInfoDetailListByCode(String id) {
		return customerDao.getPayInfoDetailListByCode(id);
	}

	@Override
	public List<Map<String, Object>> getGxrListByCode(String id, UserContext uc) {
		return customerDao.getGxrListByCode(id, uc);
	}

	@Override
	public Page<Map<String, Object>> findPageByCustomerCode_baseItemReq(
			Page<Map<String, Object>> page, String custCode, String fixAreaDescr) {
		return customerDao.findPageByCustomerCode_baseItemReq(page,custCode,fixAreaDescr);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByCustomerCode_areaDescr(
			Page<Map<String, Object>> page, String custCode, String fixAreaDescr) {
		return customerDao.findPageByCustomerCode_areaDescr(page,custCode,fixAreaDescr);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByCustomerCode_baseItemChange(
			Page<Map<String, Object>> page, String custCode) {
		return customerDao.findPageByCustomerCode_baseItemChange(page,custCode);
	}

	@Override
	public Result getCustomerCostByCode(String id) {
		return customerDao.getCustomerCostByCode(id);
	}

	@Override
	public List<Map<String, Object>> getByUserCustRight(String id,String status,String address) {
		return customerDao.getByUserCustRight(id,status,address);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_custRight(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		return customerDao.findPageBySql_custRight(page, customer, uc);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<Map<String, Object>> getCheckCustomerByPage(Page page,String id, String status,
			String address) {
		return customerDao.getCheckCustomerByPage(page,id,status,address);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page<Map<String, Object>> getPrjCheckCustomerByPage(Page page,String id, String status,
			String address) {
		return customerDao.getPrjCheckCustomerByPage(page,id,status,address);
	}

	@Override
	public Page<Map<String, Object>> getAllCustomerList(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		return customerDao.getAllCustomerList(page, customer,uc);
	}

	@Override
	public Customer getCustomerByMobile1(String phone) {
		return customerDao.getCustomerByMobile1(phone);
	}

	@Override
	public Map<String, Object> getCustomerByCode(String code) {
		return customerDao.getCustomerByCode(code);
	}

	@Override
	public Map<String, Object> getCustomerByCode_htxx(String code) {
		return customerDao.getCustomerByCode_htxx(code);
	}

	@Override
	public Page<Map<String, Object>> findPageByCustCode_payDetail(Page<Map<String, Object>> page,
			String custCode) {
		return customerDao.findPageByCustCode_payDetail(page,custCode);
	}

	@Override
	public Map<String, Object> getCustomerByCode_gcxx(String code) {
		return customerDao.getCustomerByCode_gcxx(code);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_ykCustomer(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_ykCustomer(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_ykCustomer_new(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_ykCustomer_new(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_job(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		return customerDao.findPageBySql_job(page, customer, uc);
	}

	@Override
	public Result deleteForProc(Customer customer) {
		return customerDao.deleteForProc(customer);
	}

	@Override
	public Page<Map<String, Object>> getExecuteCustomerList(
			Page<Map<String, Object>> page, CustomerQueryEvt evt) {
		// TODO Auto-generated method stub
		return customerDao.getExecuteCustomerList(page, evt);
	}

	@Override
	public void updateForJcysTcProc(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.updateForJcysTcProc(customer);
	}

	@Override
	public void prjProgDeleteCustCode(String code) {
		customerDao.prjProgDeleteCustCode(code);
	}
	@Override
	public Page<Map<String, Object>> findItemConfirmPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		// TODO Auto-generated method stub
		return customerDao.findItemConfirmPageBySql(page,customer,uc);
	}

	


	public Page<Map<String,Object>> getCustomerByConditions(Page<Map<String,Object>> page,GetCustomerEvt evt){
		return customerDao.getCustomerByConditions(page, evt);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_report(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		// TODO Auto-generated method stub
		return customerDao.findPageBySql_report(page,customer,uc);
	}
	
	//add by hc 2018/01/03 begin
	@Override       //来客统计  --- 统计方式检索
	public Page<Map<String, Object>> findPageBySqlTJFS(
			Page<Map<String, Object>> page, Customer customer,String orderBy,String direction) {
		return customerDao.findPageBySql_lktj_Tjfs(page, customer,orderBy,direction);
	}
	//end by hc 2018/01/03 end

	@Override
	public String getCustTypeTypeByCode(String custCode) {
		return customerDao.getCustTypeTypeByCode(custCode);
	}
	
	@Override
	public List<Map<String, Object>> getRollList(){
		return customerDao.getRollList();
	}

	@Override
	public boolean getIsDelivBuilder(String builderCode) {
		// TODO Auto-generated method stub
		return customerDao.getIsDelivBuilder(builderCode);
	}

	@Override
	public String getAddressType(String builderCode) {
		// TODO Auto-generated method stub
		return customerDao.getAddressType(builderCode);
	}

	@Override
	public boolean getIsExistBuilderNum(String builderCode, String builderNum) {
		// TODO Auto-generated method stub
		return customerDao.getIsExistBuilderNum(builderCode,builderNum);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_gcwg(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_gcwg(page, customer);
	}

	@Override
	public Map<String, Object> getCustomerByCode_gcwg(String code) {
		return customerDao.getCustomerByCode_gcwg(code);
	}

	@Override
	public Customer getChgData(Customer customer) {
		return customerDao.getChgData(customer);
	}

	@Override
	public Result doGcwg_jz(Customer customer) {
		return customerDao.doGcwg_jz(customer);
	}

	@Override
	public Result doGcwg_Khjs(Customer customer) {
		return customerDao.doGcwg_Khjs(customer);
	}
	
	@Override
	public Map<String, Object> getShouldBanlance(String code){
		return customerDao.getShouldBanlance(code);
	}

	@Override
	public List<Map<String, Object>> getHasZCReq(String code) {
		// TODO Auto-generated method stub
		return customerDao.getHasZCReq(code);
	}

	@Override
	public String isNeedPlanSendDate(String custCode) {
		// TODO Auto-generated method stub
		return customerDao.getIsNeedPlanSendDate(custCode);
	}

	@Override
	public String getIsDefaultStatic() {
		// TODO Auto-generated method stub
		return customerDao.getIsDefaultStatic();
	}

	public Page<Map<String, Object>> findPageBySql_sghtxx(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		return customerDao.findPageBySql_sghtxx(page, customer, uc);
	}

	@Override
	public Map<String, Object> findMapByCode(String code) {
		return customerDao.findMapByCode(code);
	}

	@Override
	public Double getJzyjjs(String custCode) {
		return customerDao.getJzyjjs(custCode);
	}

	@Override
	public String getZsgStatus(String custCode,String status) {
		// TODO Auto-generated method stub
		return customerDao.getZsgStatus(custCode,status);
	}

	@Override
	public void doQzjs(Customer customer) {
		customerDao.doQzjs(customer);
		discTokenDao.updateDiscTokenStaus(customer.getCode(), "5");
	}

	@Override
	public void doQzjsth(Customer customer) {
		customerDao.doQzjsth(customer);
		discTokenDao.updateDiscTokenStaus(customer.getCode(), "2");
	}
	
	@Override
	public String getConBackNotify(String custCode,String status,String endCode,String m_umstatus) {
		return customerDao.getConBackNotify(custCode,status,endCode,m_umstatus);
	}

	@Override
	public Result doSghtxx_return(Customer customer) {
		return customerDao.doSghtxx_return(customer);
	}

	@Override
	public String getJzNotify(String custCode, String status) {
		return customerDao.getJzNotify(custCode,status);
	}

	@Override
	public String getDesignPicStatus(String custCode) {
		return customerDao.getDesignPicStatus(custCode);
	}

	@Override
	public String checkDesignFee(String custCode) {
		return customerDao.checkDesignFee(custCode);
	}

	@Override
	public String getDesignFeeType(String custType, String payType) {
		return customerDao.getDesignFeeType(custType,payType);
	}

	@Override
	public Result doSaveZsg(Customer customer) {
		return customerDao.doSaveZsg(customer);
	}

	@Override
	public Result doSaveJz(Customer customer) {
		
		return customerDao.doSaveJz(customer);
	}

	@Override
	public String getJzReturnNotify(String custCode, String status,
			String endCode) {
		return customerDao.getJzReturnNotify(custCode,status,endCode);
	}

	@Override
	public String getOldStatus(String custCode) {
		return customerDao.getOldStatus(custCode);
	}

	@Override
	public Result doJzReturn(Customer customer) {
		return customerDao.doSaveJz(customer);
	}

	@Override
	public String resignNotify(String custCode, String status, String endCode) {
		// TODO Auto-generated method stub
		return customerDao.getReSignNotify(custCode,status,endCode);
	}

	@Override
	public Result doReSign(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.doReSign(customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_viewResign(Page page,
			Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.findPageBySql_viewResign(page,customer);
	}

	@Override
	public String getPosiDesignFee(String position, String custType) {
		// TODO Auto-generated method stub
		return customerDao.getPosiDesignFee(position, custType);
	}

	@Override
	public Map<String, Object> getFourPay(String custCode, String payType) {
		return customerDao.getFourPay(custCode,payType);
	}

	@Override
	public Integer getCustConsDay(String custCode) {
		// TODO Auto-generated method stub
		return customerDao.getCustConsDay(custCode);
	}
	
	public List<Map<String, Object>> getItemReqCount(String custCode) {
		return customerDao.getItemReqCount(custCode);
	}

	@Override
	public Page<Map<String, Object>> getPrjReport(Page page, String custCode) {
		// TODO Auto-generated method stub
		return customerDao.getPrjReport(page,custCode);
	}

	@Override
	public Page<Map<String, Object>> findItemSendListPage(Page page,
			String custCode,String itemType1) {
		// TODO Auto-generated method stub
		return customerDao.findItemSendListPage(page,custCode,itemType1);
	}
	
	public List<Map<String, Object>> getItemType12ReqCount(String custCode) {
		return customerDao.getItemType12ReqCount(custCode);
	}
	
	@Override
	public Page<Map<String, Object>> findItemSendDetailListPage(Page page,
			String no) {
		// TODO Auto-generated method stub
		return customerDao.findItemSendDetailListPage(page,no);
	}

	@Override
	public Page<Map<String, Object>> getConstractPayPage(Page page,
			String custCode) {
		// TODO Auto-generated method stub
		return customerDao.getConstractPayPage(page,custCode);
	}

	@Override
	public Result doUpdateContractFee(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.doUpdaetContractFee(customer);
	}

	public List<Map<String, Object>> getJCReqCount(String custCode) {
		return customerDao.getJCReqCount(custCode);
	}
				
	public List<Map<String, Object>> getCustomerItemType12DetailNeeds(Customer customer) {
		return customerDao.getCustomerItemType12DetailNeeds(customer);
	}
	
	public List<Map<String,Object>> getBuildPassword(String code,int custWkPk){
		return customerDao.getBuildPassword(code,custWkPk);
	}

	@Override
	public boolean getMatchConDay(String custCode) {
		return customerDao.getMatchConDay(custCode);
	}

	@Override
	public void updateCustAccountPk(String mobile1) {
		// TODO Auto-generated method stub
		customerDao.updateCustAccountPk(mobile1);
	}

	public Page<Map<String, Object>> findPageByCustomerCode_itemTypeByCustCode(
			Page<Map<String, Object>> page, String code) {
		return customerDao.findPageByCustomerCode_itemTypeByCustCode(page,code);
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemType2ByCustCode(
			Page<Map<String, Object>> page, String code,String itemType1) {
		return customerDao.findPageByCustomerCode_itemType2ByCustCode(page,code,itemType1);
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemTypeChgByCustCode(
			Page<Map<String, Object>> page, String code) {
		return customerDao.findPageByCustomerCode_itemTypeChgByCustCode(page,code);
	}
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemType2ChgByCustCode(
			Page<Map<String, Object>> page, String code,String itemType1) {
		return customerDao.findPageByCustomerCode_itemType2ChgByCustCode(page,code,itemType1);
	}
	
	public Page<Map<String, Object>> findItemType1ListPage(
			Page<Map<String, Object>> page) {
		return customerDao.findItemType1ListPage(page);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySql_jcys(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_jcys(page, customer);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySql_detailReport(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_detailReport(page, customer);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySql_detailReportMX(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_detailReportMX(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_jczj(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_jczj(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_jczjDetail(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_jczjDetail(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_jczfb(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_jczfb(page, customer);
	}
	
	@Override
	public Page<Map<String, Object>> getReqCtrlList(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.getReqCtrlList(page, customer);
	}

	@Override
	public void updateCarryFloor(Customer customer) {
		customerDao.updateCarryFloor(customer);
	}
	
	@Override
	public Page<Map<String, Object>> getTechPhotoUrlList(Page page, String custCode) {
		return customerDao.getTechPhotoUrlList(page,custCode);
	}

	@Override
	public Result doSaveReqCtrlPer(Customer customer) {
		return customerDao.doSaveReqCtrlPer(customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_modifyPhone(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_modifyPhone(page, customer);
	}

	@Override
	public String getConStatAndIsAddAll(String code) {
		return customerDao.getConStatAndIsAddAll(code);
	}

	@Override
	public Map<String, Object> getGiftDiscAmount(String custCode) {
		return customerDao.getGiftDiscAmount(custCode);
	}

	@Override
	public Map<String, Object> getContractInfo(String code) {
		// TODO Auto-generated method stub
		return customerDao.getContractInfo(code);
	}

	public Page<Map<String, Object>> getBaseCheckItemPlanList(Page<Map<String, Object>> page,String custCode,String workType12){
		return customerDao.getBaseCheckItemPlanList(page,custCode,workType12);
	}
	
	public boolean getExistBuilderNum(String builderCode){
		return customerDao.getExistBuilderNum(builderCode);
	}

	@Override
	public Double getRiskFund(String id) {
		return customerDao.getRiskFund(id);
	}

	@Override
	public Page<Map<String, Object>> findRiskFundPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findRiskFundPageBySql(page, customer);
	}

	@Override
	public String getPayeeCode(String code) {
		return customerDao.getPayeeCode(code);
	}

	@Override
	public Customer isHoliConstruct(final Customer customer) {
		String builderCode = customer.getBuilderCode();
		String builderNum = customer.getBuilderNum();
		customer.setIsHoliConstruct(customerDao.isHoliConstruct(builderCode, builderNum));
		return customer;
	}

	@Override
	public Map<String,Object> isNeedAgainMan(String code) {
		return customerDao.isNeedAgainMan(code);
	}

	@Override
	public String hasPay(String code) {
		return customerDao.hasPay(code);
	}

	@Override
	public Map<String, Object> getWaterMarkInfo(String code) {
		return customerDao.getWaterMarkInfo(code);
	}

	@Override
	public Map<String, Object> getPrjRegion(String code) {
		return customerDao.getPrjRegion(code);
	}

    @Override
    public Result doMeasure(HttpServletRequest request,
            HttpServletResponse response, Customer customer, UserContext userContext) {

        Customer unmodifiedCustomer = customerDao.get(Customer.class, customer.getCode());
        
        if (unmodifiedCustomer.getMeasureDate() != null) {
            return new Result(Result.FAIL_CODE, "该楼盘已量房");
        }
        
        if (unmodifiedCustomer.getStatus().equals("4") ||
                unmodifiedCustomer.getStatus().equals("5")) {
            return new Result(Result.FAIL_CODE, "合同施工或已归档状态的楼盘不允许量房");
        }
        
        unmodifiedCustomer.setMeasureDate(customer.getMeasureDate());
        unmodifiedCustomer.setLastUpdate(new Date());
        unmodifiedCustomer.setLastUpdatedBy(userContext.getCzybh());
        unmodifiedCustomer.setActionLog("EDIT");
        
        return new Result(Result.SUCCESS_CODE, "确认量房成功");
    }
    
    

	@Override
    public Result doUpdateBusinessMan(Customer customer, String businessMan,
            String againMan, UserContext userContext) {
	    
        if (StringUtils.isNotBlank(againMan)
                && StringUtils.isNotBlank(customer.getAgainMan())
                && !againMan.equals(customer.getAgainMan())) {
            
            CustStakeholder custStakeholder = new CustStakeholder();
            custStakeholder.setM_umState("M");
            custStakeholder.setIsRight(0);
            custStakeholder.setCustCode(customer.getCode());
            custStakeholder.setRole("24");
            custStakeholder.setEmpCode(againMan);
            custStakeholder.setLastUpdatedBy(userContext.getCzybh());
            custStakeholder.setPk(custStakeholderService
                    .getPkByCustCodeAndRole(customer.getCode(), "24"));
            custStakeholder.setExpired("F");
            Result result = custStakeholderService.updateForProc(custStakeholder);
            
            if (!"1".equals(result.getCode())) {
                throw new RuntimeException(result.getInfo());
            }
        }
        
        CustStakeholder custStakeholder = new CustStakeholder();
        custStakeholder.setM_umState("M");
        custStakeholder.setIsRight(0);
        custStakeholder.setCustCode(customer.getCode());
        custStakeholder.setRole("01");
        custStakeholder.setEmpCode(businessMan);
        custStakeholder.setLastUpdatedBy(userContext.getCzybh());
        custStakeholder.setPk(custStakeholderService
                .getPkByCustCodeAndRole(customer.getCode(), "01"));
        custStakeholder.setExpired("F");
        Result result = custStakeholderService.updateForProc(custStakeholder);
        
        if (!"1".equals(result.getCode())) {
            throw new RuntimeException(result.getInfo());
        }
        
        return result;
    }

    @Override
	public Map<String, Object> getMaxDiscByCustCode(String code) {
		// TODO Auto-generated method stub
		return customerDao.getMaxDiscByCustCode(code);
	}
	
    @Override
	public List<Map<String, Object>> getCustNetCnlList() {
		return resrCustDao.findAllNetChannels();
	}
    
    @Override
	public List<Map<String, Object>> getCustNetCnlListBySource(String source) {
		return resrCustDao.findNetChannelsBySource(source);
	}

	@Override
	public Result doWfCustReSign(Customer customer) {
		
		return customerDao.doWfCustReSign(customer);
	}

	@Override
	public String getEndCodeByCustCode(String custCode) {

		return customerDao.getEndCodeByCustCode(custCode);
	}

	@Override
	public String checkAddress(Customer customer,  UserContext userContext) {
		String returnInfo = "";
		CustType custType = get(CustType.class, customer.getCustType());
		if(userContext.isSuperAdmin() || czybmService.hasGNQXByCzybh(userContext.getCzybh(), "0201", "修改楼盘信息") || "0".equals(customer.getIsAddAllInfo())){ // 独立销售的楼盘不需要验证楼盘规则
			return returnInfo;
		}
		
		String str = customer.getBuilderCode();
		if (StringUtils.isNotBlank(str)){
			Builder builder = get(Builder.class, str);
			if (builder!=null){
				if ("2".equals(builder.getAddressType())){
					//楼盘前缀必须是项目名称
					int i = customer.getAddress().indexOf(builder.getDescr());
					if (i!=0){
						returnInfo = "楼盘前缀必须是项目名称";
					}
				}else if("3".equals(builder.getAddressType())){
					String address = customer.getAddress();
					//楼盘前缀必须是项目名称，且后面是数字、字母和#，#在中间
					int i = address.indexOf(builder.getDescr());
					if (i==0){
						String regex = "^[0-9a-zA-Z]+#[0-9a-zA-Z]+$";
						if (custType!=null && "0".equals(custType.getIsAddAllInfo()) && "2".equals(customer.getSaleType())){
							regex = "^[0-9a-zA-Z]+#[0-9a-zA-Z]+"+custType.getDesc1()+"$";
						}
						String ss = address.substring(builder.getDescr().length());
						Pattern pattern = Pattern.compile(regex);
						Matcher m = pattern.matcher(ss);
						boolean flag = m.find();
						if(!flag){
							returnInfo = "楼盘前缀必须是项目名称，且后面是数字、字母和#，#在中间";
						}
					}else{
						returnInfo = "楼盘前缀必须是项目名称，且后面是数字、字母和#，#在中间";
					}
				}else if("4".equals(builder.getAddressType())){
					String address = customer.getAddress();
					//楼盘前缀必须是项目名称，且后面是数字、字母和#，#在中间
					int i = address.indexOf(builder.getDescr());
					if (i==0){
						String regex = "^[0-9a-zA-Z]+[0-9a-zA-Z]+$";
						if (custType!=null && "0".equals(custType.getIsAddAllInfo()) && "2".equals(customer.getSaleType())){
							regex = "^[0-9a-zA-Z]+[0-9a-zA-Z]+"+custType.getDesc1()+"$";
						}
						String ss = address.substring((builder.getDescr()+customer.getBuilderNum()).length());
						Pattern pattern = Pattern.compile(regex);
						Matcher m = pattern.matcher(ss);
						boolean flag = m.find();
						if(!flag){
							returnInfo = "楼盘前缀必须是项目名称，且后面是数字、字母和#，#在中间";
						}
					}else{
						returnInfo = "楼盘前缀必须是项目名称，且后面是数字、字母和#，#在中间";
					}
				}
			}
		}
		
		return returnInfo;
	}

	@Override
	public String getContractStatus(Customer customer) {

		return customerDao.getContractStatus(customer);
	}

	@Override
	public Map<String, Object> getPerfEstimate(String custCode, String type, String empCode) {
		return customerDao.getPerfEstimate(custCode, type, empCode);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_basePersonalEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_basePersonalEstimate(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_zcEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_zcEstimate(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_rzEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_rzEstimate(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_jcEstimate(
			Page<Map<String, Object>> page, Customer customer) {
		return customerDao.findPageBySql_jcEstimate(page, customer);
	}

	@Override
	public boolean isSignDateAfterNewCommiDate(String custCode) {
		return customerDao.isSignDateAfterNewCommiDate(custCode);
	}

	@Override
	public Result doWfProcSaveZsg(Customer customer) {

		return customerDao.doWfProcSaveZsg(customer);
	}

	
}

