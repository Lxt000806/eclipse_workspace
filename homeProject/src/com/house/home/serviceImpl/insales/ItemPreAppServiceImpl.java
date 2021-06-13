package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemPreAppDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.insales.ItemPreAppService;

@SuppressWarnings("serial")
@Service
public class ItemPreAppServiceImpl extends BaseServiceImpl implements ItemPreAppService {

	@Autowired
	private ItemPreAppDao itemPreAppDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreApp itemPreApp){
		return itemPreAppDao.findPageBySql(page, itemPreApp);
	}

	@Override
	public Result saveForProc(ItemPreApp itemPreApp, String xml) {
		return itemPreAppDao.saveForProc(itemPreApp,xml);
	}

	@Override
	public Result checkForProc(ItemPreApp itemPreApp, String xml) {
		return itemPreAppDao.checkForProc(itemPreApp, xml);
	}
	
	//20171011领料预申请管理
	public Page<Map<String,Object>> findItemPreAppManageListPageBySql(Page<Map<String,Object>> page, ItemPreApp itemPreApp){
		return itemPreAppDao.findItemPreAppManageListPageBySql(page, itemPreApp);
	}	
	
	@Override
	public Page<Map<String,Object>> getApplyListByNo(Page<Map<String,Object>> page,String no,String supplCode){
		return itemPreAppDao.getApplyListByNo(page,no,supplCode);
	}
	
	@Override
	public Page<Map<String,Object>> getItemPreAppPhotoListByNo(Page<Map<String,Object>> page, ItemPreApp itemPreApp){
		return itemPreAppDao.getItemPreAppPhotoListByNo(page,itemPreApp);
	}
	
	@Override
	public Page<Map<String,Object>> getMeasureByCondition(Page<Map<String,Object>> page,String no,Integer pk){
		return itemPreAppDao.getMeasureByCondition(page,no,pk);
	}
	
	@Override
	public Result updatePreItemAppStatus(ItemPreApp itemPreApp){
		return itemPreAppDao.updatePreItemAppStatus(itemPreApp);
	}	
	
	@Override
	public Map<String,Object> existPreMeasure(Integer pk,String supplCode,String preAppNo){
		return itemPreAppDao.existPreMeasure(pk,supplCode,preAppNo);
	}
	
	@Override
	public Page<Map<String,Object>> goClhxdJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		return itemPreAppDao.goClhxdJqGrid(page, itemPreMeasure);
	}
	
	@Override
	public List<Map<String,Object>> findOtherMaterial(String no){
		return itemPreAppDao.findOtherMaterial(no);
	}
	
	@Override
	public Map<String,Object> getMeasureInfoByPk(Integer pk){
		return itemPreAppDao.getMeasureInfoByPk(pk);
	}
	
	@Override
	public boolean hasInSetReq(String custCode,String itemType1){
		List<Map<String,Object>> list = itemPreAppDao.hasInSetReq(custCode,itemType1);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean containServiceItem(String no){
		List<Map<String,Object>> list = itemPreAppDao.containServiceItem(no);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	@Override
	public Map<String,Object> getCanInOutPlan(String itemType1,String custCode){
		return itemPreAppDao.getCanInOutPlan(itemType1, custCode);
	}

	@Override
	public List<Map<String,Object>> getItemType2Opt(String itemType1){
		return itemPreAppDao.getItemType2Opt(itemType1);
	}
	
	@Override
	public Page<Map<String,Object>> goYyxxzJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		return itemPreAppDao.goYyxxzJqGrid(page,itemPreMeasure);
	}
	
	@Override
	public boolean judgeItemInTitles(String itemCode){
		List<Map<String,Object>> list = itemPreAppDao.judgeItemInTitles(itemCode);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public Map<String,Object> getItemNum(String itemCode,Double qty){
		List<Map<String,Object>> list = itemPreAppDao.getItemNum(itemCode,qty);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Page<Map<String,Object>> goPreApplyJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		return itemPreAppDao.goPreApplyJqGrid(page,itemPreMeasure);
	}
	
	@Override
	public Page<Map<String,Object>> goYlclJqGrid(Page<Map<String,Object>> page, String custCode,String appNo,String itemCodes){
		return itemPreAppDao.goYlclJqGrid(page,custCode,appNo,itemCodes);
	}
	
	@Override
	public Map<String,Object> getMeasureInfoByNo(String no){
		return itemPreAppDao.getMeasureInfoByNo(no);
	}
	
	@Override
	public Page<Map<String,Object>> goKsxzJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure){
		return itemPreAppDao.goKsxzJqGrid(page,itemPreMeasure);
	}
	
	@Override
	public Result AddllForProc(ItemPreMeasure itemPreMeasure, String xml){
		return itemPreAppDao.AddllForProc(itemPreMeasure,xml);
	}
	
	@Override
	public boolean isStayAddPage(String preAppNo){
		return itemPreAppDao.isStayAddPage(preAppNo);
	}
	
	@Override
	public Map<String,Object> findMessageInfo(Integer pk){
		return itemPreAppDao.findMessageInfo(pk);
	}
	
	@Override
	public List<Map<String,Object>> custItemConf(String custCode){
		return itemPreAppDao.custItemConf(custCode);
	}
	
	@Override
	public Map<String,Object> getFsArea(String custCode){
		return itemPreAppDao.getFsArea(custCode);
	}
	
	@Override
	public Map<String,Object> getCustPayInfo(String custCode){
		return itemPreAppDao.getCustPayInfo(custCode);
	}
	
	@Override
	public Map<String,Object> getSendCount(String no){
		return itemPreAppDao.getSendCount(no);
	}

	@Override
	public Page<Map<String,Object>> goItemAppDetailJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure){
		return itemPreAppDao.goItemAppDetailJqGrid(page,itemPreMeasure);
	}
	
	@Override
	public Map<String,Object> checkCost(String custCode,String materWorkType2){
		return itemPreAppDao.checkCost(custCode,materWorkType2);
	}
	
	@Override
	public Result checkQty(String custCode,String appNo ,String itemType1,String xml){
		return itemPreAppDao.checkQty(custCode,appNo,itemType1,xml);
	}
	
	@Override
	public List<Map<String,Object>> checkQtyByItemType2(String custCode,String appNo ,String itemType1){
		return itemPreAppDao.checkQtyByItemType2(custCode,appNo,itemType1);
	}
	
	@Override
	public Map<String,Object> checkCustStatus(String custCode){
		return itemPreAppDao.checkCustStatus(custCode);
	}
	
	@Override
	public Map<String,Object> checkCustPay(String appNo){
		return itemPreAppDao.checkCustPay(appNo);
	}

	@Override
	public Page<Map<String,Object>> goCodeJqGrid(Page<Map<String,Object>> page,ItemPreApp itemPreApp){
		return itemPreAppDao.goCodeJqGrid(page,itemPreApp);
	}

	@Override
	public Page<Map<String,Object>> goCodeDetailJqGrid(Page<Map<String,Object>> page,String no){
		return itemPreAppDao.goCodeDetailJqGrid(page, no);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridSend(Page<Map<String,Object>> page, String no){
		return itemPreAppDao.goJqGridSend(page, no);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridSendMx(Page<Map<String,Object>> page, String no){
		return itemPreAppDao.goJqGridSendMx(page, no);
	}
	
	@Override
	public Map<String, Object> getItemInfoByCode(String itemCode, String itemType1, String itemType2, String no, String custCode){
		return itemPreAppDao.getItemInfoByCode(itemCode, itemType1, itemType2, no, custCode);
	}
	
	@Override
	public Map<String, Object> getIntProd(String descr, String custCode){
		return itemPreAppDao.getIntProd(descr, custCode);
	}

	@Override
	public Page<Map<String, Object>> getWorkerAppItemList(
			Page<Map<String, Object>> page, Integer custWkPk,String custCode) {
		// TODO Auto-generated method stub
		return itemPreAppDao.getWorkerAppItemList(page,custWkPk,custCode);
	}
	
	@Override
	public Page<Map<String, Object>> getWorkerItemApp(Page<Map<String,Object>> page, String no) {
		// TODO Auto-generated method stub
		return itemPreAppDao.getWorkerItemApp(page,no);
	}
	
	@Override
	public Page<Map<String, Object>> getItemAppDetail(Page<Map<String,Object>> page, String no) {
		// TODO Auto-generated method stub
		return itemPreAppDao.getItemAppDetail(page,no);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridSendCountView(Page<Map<String,Object>> page, String no){
		return itemPreAppDao.goJqGridSendCountView(page, no);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGirdUnItemAppMaterial(Page<Map<String,Object>> page, String custCode, String itemType1, String itemType2, String whCode, String supplCode){
		return itemPreAppDao.goJqGirdUnItemAppMaterial(page, custCode, itemType1, itemType2, whCode, supplCode);
	}

	@Override
	public Integer getSendCountNum(String no){
		return itemPreAppDao.getSendCountNum(no);
	}
	
	public Map<String,Object> getRatedMatrial(String custCode,String workType12){
		return itemPreAppDao.getRatedMatrial(custCode,workType12);
	}
	
	@Override
	public Page<Map<String, Object>> goJddrJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return itemPreAppDao.goJddrJqGrid(page, itemPreMeasure);
	}

	@Override
	public String checkIsSpecReq(String itemType1) {
		return itemPreAppDao.checkIsSpecReq(itemType1);
	}

	@Override
	public Page<Map<String, Object>> goLlcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return itemPreAppDao.goLlcbJqGrid(page, itemPreMeasure);
	}

	@Override
	public Page<Map<String, Object>> goCgazcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return itemPreAppDao.goCgazcbJqGrid(page, itemPreMeasure);
	}

	@Override
	public Page<Map<String, Object>> goYgazcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return itemPreAppDao.goYgazcbJqGrid(page, itemPreMeasure);
	}
	
	public Map<String,Object> getAppliedMoney(String custCode,String workType12){
		return itemPreAppDao.getAppliedMoney(custCode,workType12);
	}
	
	@Override
	public Map<String,Object> getLastSendSupplier(String custCode){
		return itemPreAppDao.getLastSendSupplier(custCode);
	}

	@Override
	public Page<Map<String, Object>> goIntProDetailJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return itemPreAppDao.goIntProDetailJqGrid(page, itemPreMeasure);
	}
	
	public Page<Map<String, Object>> getWaterItemList(Page<Map<String, Object>> page, String  custCode) {
		return itemPreAppDao.getWaterItemList(page, custCode);
	}
	
	public Page<Map<String, Object>> getWaterItemDetail(Page<Map<String, Object>> page, String  no) {
		return itemPreAppDao.getWaterItemDetail(page, no);
	}

	@Override
	public Page<Map<String, Object>> goAutoOrderJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return itemPreAppDao.goAutoOrderJqGrid(page, itemPreMeasure);
	}

	@Override
	public Result autoOrderForProc(ItemPreMeasure itemPreMeasure, String xml) {
		return itemPreAppDao.autoOrderForProc(itemPreMeasure, xml);
	}

	@Override
	public void updateRemarks(ItemPreMeasure itemPreMeasure) {
		itemPreAppDao.updateRemarks(itemPreMeasure);
	}
	
	@Override
	public List<Map<String, Object>> findNoSendYunPic(){
		return this.itemPreAppDao.findNoSendYunPic();
	}
	
	@Override
	public boolean isExistsSaleIndependence(String custCode){
		return this.itemPreAppDao.isExistsSaleIndependence(custCode);
	}

	@Override
	public void doNotify(ItemPreMeasure itemPreMeasure) {
		this.itemPreAppDao.doNotify(itemPreMeasure);
	}

	@Override
	public String checkKgQty(ItemPreMeasure itemPreMeasure) {
		return this.itemPreAppDao.checkKgQty(itemPreMeasure);
	}

	@Override
	public List<Map<String, Object>> isStopWork(String custCode) {
		return this.itemPreAppDao.isStopWork(custCode);
	}

	@Override
	public List<Map<String, Object>> checkSendCmpWh(ItemPreMeasure itemPreMeasure) {
		return this.itemPreAppDao.checkSendCmpWh(itemPreMeasure);
	}

	@Override
	public Page<Map<String, Object>> goJqGridItemPlanSoftSend(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return this.itemPreAppDao.goJqGridItemPlanSoftSend(page,itemPreMeasure);
	}

	@Override
	public String getSoftSendCount(String custCode, String itemType1) {
		return this.itemPreAppDao.getSoftSendCount(custCode, itemType1);
	}

	@Override
	public Page<Map<String, Object>> findItemShouldOrderJqGrid(
			Page<Map<String, Object>> page, Customer customer) {
		
		return itemPreAppDao.findItemShouldOrderJqGrid(page, customer);
	}

	@Override
	public Result doBatchSave(ItemPreApp itemPreApp) {

		return itemPreAppDao.doBatchSave(itemPreApp);
	}
	
	@Override
	public Result doBackstageOrder(ItemPreMeasure itemPreMeasure) {
		return this.itemPreAppDao.doBackstageOrder(itemPreMeasure);
	}

	@Override
	public Page<Map<String, Object>> goXqazcbJqGrid(Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		return this.itemPreAppDao.goXqazcbJqGrid(page, itemPreMeasure);
	}

	@Override
	public Map<String, Object> getByPreAppNo(String preAppNo) {
		return itemPreAppDao.getByPreAppNo(preAppNo);
	}
	
	
}
