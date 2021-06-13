package com.house.home.serviceImpl.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.design.CustomerDao;
import com.house.home.dao.design.ItemPlanDao;
import com.house.home.dao.insales.ItemAppDao;
import com.house.home.dao.insales.ItemPreAppDao;
import com.house.home.entity.basic.CustGift;
import com.house.home.entity.basic.CustGiftItem;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.design.ItemPlanService;

@SuppressWarnings("serial")
@Service
public class ItemPlanServiceImpl extends BaseServiceImpl implements ItemPlanService {

	@Autowired
	private ItemPlanDao itemPlanDao;
	@Autowired
	private ItemPreAppDao itemPreAppDao;
	@Autowired
	private ItemAppDao itemAppDao;
	@Autowired
	private CustomerDao customerDao;
	
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlan itemPlan,UserContext uc){
		return itemPlanDao.findPageBySql(page, itemPlan,uc);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_zcys(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		return itemPlanDao.findPageBySql_zcys(page,itemPlan);
	}

	@Override
	public boolean hasItemPlan(String custCode) {
		return itemPlanDao.hasItemPlan(custCode);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_rzys(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findPageBySql_rzys(page,itemPlan);
	}

	@Override
	public Result doItemForProc(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doItemForProc(itemPlan);
	}

	@Override
	public void importDetail(Map<String, Object> data) {
		// TODO Auto-generated method stub
		itemPlanDao.importDetail(data);
	}

	@Override
	public Page<Map<String, Object>> findItemPlanList(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findItemPlanList(page,itemPlan);
	}

	@Override
	public Result doBaseBatchTempProc(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doBaseBatchTempProc(itemPlan);
	}
	@Override
	public Result doBaseItemTempProc(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doBaseItemTempProc(itemPlan);
	}
	
	@Override
	public Result doCopyPlan(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doCopyPlan(itemPlan);
	}
	
	@Override
	public Result doCopyPlanPre(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doCopyPlanPre(itemPlan);
	}

	@Override
	public Result doPrePlanTemp(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doPrePlanTemp(itemPlan);
	}
	@Override
	public Result doItemTCForProc(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doItemTCForProc(itemPlan);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_ckyj(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findPageBySql_ckyj(page,itemPlan);
	}



	@Override
	public long getItemPlanCount(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.getItemPlanCount(itemPlan);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_zcmlfx(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findPageBySql_zcmlfx(page,itemPlan);
	}
	
	@Override
	public Map<String, Object> findBySql_mlfx(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findBySql_mlfx(itemPlan);
	}

	@Override
	public Result doClearPlanForProc(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doClearPlanForProc(itemPlan);
	}

	@Override
	public Map<String, Object> findBySql_GetFourCustPay(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findBySql_GetFourCustPay(itemPlan);
	}

	@Override
	public Map<String,Object> getPayRemark(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.getPayRemark(itemPlan);
	}

	@Override
	public List<Map<String, Object>> getFixAreaTypes() {
		// TODO Auto-generated method stub
		return itemPlanDao.getFixAreaTypes();
	}	


	@Override
	public Map<String, Object> findBySql_GetItemPlanQty(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findBySql_GetItemPlanQty(itemPlan);
	}
	
	@Override
	public Result doRegenFromPrePlanTemp(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doRegenFromPrePlanTemp(itemPlan);
	}
	@Override
	public Result doRegenBasePlanFromPrePlanTemp(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doRegenBasePlanFromPrePlanTemp(itemPlan);
	}

	@Override
	public Map<String, Object> findBySql_GetBaseItemPlanQty(BaseItemPlan baseItemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findBySql_GetBaseItemPlanQty(baseItemPlan);
		
	}

	@Override
	public Result doBaseAndItemPlanBak(ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.doBaseAndItemPlanBak(itemPlan);
	}

	@Override
	public Page<Map<String, Object>> findPlanBakPageBySql(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		// TODO Auto-generated method stub
		return itemPlanDao.findPlanBakPageBySql(page, itemPlan);
	}

	@Override
	public List<Map<String, Object>> getPrePlanAreaDescr(String custCode) {
			return itemPlanDao.getPrePlanAreaDescr(custCode);
	}

	@Override
	public boolean getDelNotify(Integer pk, String custCode) {
		return itemPlanDao.getDelNotify(pk,custCode);
	}

	@Override
	public String getTax(Customer customer) {
		return itemPlanDao.getTax(customer);
	}

	@Override
	public List<Map<String, Object>> getContractFeeRepType(Customer customer) {
		return itemPlanDao.getContractFeeRepType(customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_giftByDescr(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		return itemPlanDao.findPageBySql_giftByDescr(page, itemPlan);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_giftItem(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		return itemPlanDao.findPageBySql_giftItem(page, itemPlan);	
	}

	@Override
	public boolean hasGiftItem(String custCode) {
		return itemPlanDao.hasGiftItem(custCode);
	}
	
	@Override
	public Page<Map<String, Object>> getCustGiftJqGrid(
			Page<Map<String, Object>> page, CustGift custGift) {
		return itemPlanDao.getCustGiftJqGrid(page,custGift);
	}
	
	@Override
	public Page<Map<String, Object>> getCustGiftAllJqGrid(
			Page<Map<String, Object>> page, CustGift custGift) {
		return itemPlanDao.getCustGiftAllJqGrid(page,custGift);
	}

	@Override
	public Page<Map<String, Object>> getItemGiftJqgrid(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		return itemPlanDao.getItemGiftJqgrid(page,itemPlan);
	}

	@Override
	public Page<Map<String, Object>> getCustGiftItemJqgrid(
			Page<Map<String, Object>> page, CustGiftItem custGiftItem) {

		return itemPlanDao.getCustGiftItemJqgrid(page,custGiftItem);
	}

	@Override
	public double getMaxDiscExpr(Integer giftPk, double area,String custType) {
		return itemPlanDao.getMaxDiscExpr(giftPk,area,custType);
	}

	@Override
	public Result getBaseItemTempDiff(ItemPlan itemPlan) {
		return itemPlanDao.getBaseItemTempDiff(itemPlan);
	}

	@Override
	public Result getItemTempDiff_ZC(ItemPlan itemPlan) {
	
		return itemPlanDao.getItemTempDiff_ZC(itemPlan);
	}

	@Override
	public Result doBaseItemSetAddProc(ItemPlan itemPlan) {

		return itemPlanDao.doBaseItemSetAddProc(itemPlan);
	}

	@Override
	public Page<Map<String, Object>> goSoftSendJqGrid(
			Page<Map<String, Object>> page, String custCode) {
		return itemPlanDao.goSoftSendJqGrid(page, custCode);
	}

	@Override
	public Result doSoftSend(ItemPreMeasure itemPreMeasure,String xml) {
		//领料新增
		itemPreMeasure.setCallType("1");
		Result result = itemPreAppDao.AddllForProc(itemPreMeasure,xml);
		if ("1".equals(result.getCode())){
			String no=result.getInfo();
			itemPreMeasure.setM_umState("C");
			itemPreMeasure.setOtherCost(0.0);
			itemPreMeasure.setAppNo(result.getInfo());
			itemPreMeasure.setItemAppStatus("OPEN");
			//领料审核
			result= itemPreAppDao.AddllForProc(itemPreMeasure,xml);
			if ("1".equals(result.getCode())){ 
				//领料发货
				result=itemAppDao.doSendBatchForXml(no, itemPreMeasure.getWareHouseCode(), 
				        itemPreMeasure.getItemType1(), itemPreMeasure.getCustCode(),
				        itemPreMeasure.getSendDate(), "", "", itemPreMeasure.getAppCzy(), 
				        xml, "","","", "");	 
				//归档
				if ("1".equals(result.getCode())&& itemPlanDao.isSendComplete(itemPreMeasure.getCustCode())){
					Customer customer=new Customer();
					customer.setCode(itemPreMeasure.getCustCode());
					customer.setEndCode("3");
					customer.setM_umState("J");
					customer.setFromStatus("4");
					customer.setToStatus("5");
					customer.setLastUpdatedBy(itemPreMeasure.getAppCzy());
					customer.setRealDesignFee(0.0);
					result = customerDao.doGcwg_jz(customer);
				}
			}
		}	
		return result;
	}

	@Override
	public Page<Map<String, Object>> findProcListJqGrid(
			Page<Map<String, Object>> page, Customer customer) {

		return itemPlanDao.findProcListJqGrid(page, customer);
	}

	@Override
	public boolean checkProcStatus(ItemPlan itemPlan) {

		return itemPlanDao.checkProcStatus(itemPlan);
	}

	@Override
	public List<Map<String, Object>> getCustAgreement(Customer customer) {

		return itemPlanDao.getCustAgreement(customer);
	}

	@Override
	public List<Map<String, Object>> checkCommiConstruct(ItemPlan itemPlan) {

		return itemPlanDao.checkCommiConstruct(itemPlan);
	}

	@Override
	public Map<String, Object> getBefAmountByCustCodeItemType(ItemPlan itemPlan) {

		return itemPlanDao.getBefAmountByCustCodeItemType(itemPlan);
	}

	@Override
	public List<Map<String, Object>> getClearInvList(ItemPlan itemPlan, String isClearInv) {

		return itemPlanDao.getClearInvList(itemPlan, isClearInv);
	}

	@Override
	public List<Map<String, Object>> getSaleDiscApproveDetail(ItemPlan itemPlan) {

		return itemPlanDao.getSaleDiscApproveDetail(itemPlan) ;
	}

	@Override
	public List<Map<String, Object>> getWfProcDetail(ItemPlan itemPlan) {

		return itemPlanDao.getWfProcDetail(itemPlan);
	}

	@Override
	public String getSaleDiscApproveStatus(Customer customer) {

		return itemPlanDao.getSaleDiscApproveStatus(customer);
	}	
	
	@Override
	public boolean hasItemPlanQtyUnequal(String custCode) {
		return itemPlanDao.hasItemPlanQtyUnequal(custCode);
	}

	@Override
	public boolean hasBasePlanQtyUnequal(String custCode) {
		return itemPlanDao.hasBasePlanQtyUnequal(custCode);
	}	
	
}
