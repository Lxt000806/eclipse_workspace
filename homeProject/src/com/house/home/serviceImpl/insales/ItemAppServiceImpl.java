package com.house.home.serviceImpl.insales;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemApp;
import com.house.home.service.insales.ItemAppService;

@SuppressWarnings("serial")
@Service
public class ItemAppServiceImpl extends BaseServiceImpl implements ItemAppService {

	@Autowired
	private ItemAppDao itemAppDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemApp itemApp){
		return itemAppDao.findPageBySql(page, itemApp);
	}

	@Override
	public Result doItemAppForProc(ItemApp itemApp) {
		return itemAppDao.doItemAppForProc(itemApp);
	}

	@Override
	public Result doReturnItemAppForProc(ItemApp itemApp) {
		return null;
	}

	@Override
	public Result doSendItemAppForProc(ItemApp itemApp) {
		if (itemApp.getM_umState().trim().equals("P")) {
			return itemAppDao.doLlglSendBySupplForProc(itemApp);
		} else if (itemApp.getM_umState().trim().equals("S")) {
			return itemAppDao.doLlglSendForProc(itemApp);
		} else {
			return null;
		}
	}

	@Override
	public Result doPSendItemAppForProc(ItemApp itemApp) {
		return null;
	}

	@Override
	public Map<String,Object> getByNo(String id) {
		return itemAppDao.getByNo(id);
	}

	@Override
	public Result doItemAppBackForProc(ItemApp itemApp) {
		return itemAppDao.doItemAppBackForProc(itemApp);
	}

	@Override
	public Page<Map<String, Object>> findDetailBySql(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemAppDao.findDetailBySql(page, itemApp);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlCode(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemAppDao.findPageBySqlCode(page, itemApp);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_itemReturn(Page<Map<String,Object>> page,
			ItemApp itemApp) {
		return itemAppDao.findPageBySql_itemReturn(page,itemApp);
	}

	@Override
	public Page<Map<String, Object>> findPageByPreAppNo(
			Page<Map<String, Object>> page, String id) {
		return itemAppDao.findPageByPreAppNo(page,id);
	}
	
	@Override
	public boolean calcIsAutoArriveDate(String supplCode) {
		return itemAppDao.calcIsAutoArriveDate(supplCode);
	}
	
	@Override
	public Page<Map<String, Object>> findPageByProductType(Page<Map<String, Object>> page, 
			ItemApp itemApp) {
		return itemAppDao.findPageByProductType(page, itemApp);
	}
	
	@Override
	public boolean calcIsTimeout(ItemApp itemApp) {
		return itemAppDao.calcIsTimeout(itemApp);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxxcx(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemAppDao.findPageBySql_khxxcx(page,itemApp);
	}

	@Override
	public Page<Map<String, Object>> findDetailListBySql(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemAppDao.findDetailListBySql(page, itemApp);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridItemApp(Page<Map<String,Object>> page, ItemApp itemApp){
		return itemAppDao.goJqGridItemApp(page, itemApp);
	}
	
	@Override
	public Page<Map<String,Object>> getReturnAddJqGrid(Page<Map<String,Object>> page, String oldNo,String reqPks,String custCode){
		return itemAppDao.getReturnAddJqGrid(page, oldNo, reqPks,custCode);
	}
	
	@Override
	public Page<Map<String,Object>> getReturnDetailJqGrid(Page<Map<String,Object>> page, String itemCode,String custCode){
		return itemAppDao.getReturnDetailJqGrid(page, itemCode,custCode);
	}
	
	@Override
	public Map<String,Object> getWHOperator(String czybh,String whcode){
		return itemAppDao.getWHOperator(czybh,whcode);
	}
	
	@Override
	public Result doLlglthSave(ItemApp itemApp, String xml){
		return itemAppDao.doLlglthSave(itemApp,xml);
	}
	
	@Override
	public Map<String,Object> getItemAppInfo(String no){
		return itemAppDao.getItemAppInfo(no);
	}

	@Override
	public Map<String,Object> getItemReturnInfo(String no){
		return itemAppDao.getItemReturnInfo(no);
	}

	@Override
	public Page<Map<String,Object>> getItemReturnDTJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		return itemAppDao.getItemReturnDTJqGrid(page,no,custCode);
	}
	
	@Override
	public Map<String,Object> getCZYGNQX(String czybh,String mkdm,String gnmc){
		return itemAppDao.getCZYGNQX(czybh, mkdm, gnmc);
	}
	
	@Override
	public Double getSendQtyByNo(String no){
		Double qty = 0.0;
		List<Map<String,Object>> list = itemAppDao.getSendQtyByNo(no);
		if(list != null && list.size() > 0){
			qty = Double.valueOf(list.get(0).get("sumSendQty").toString());
		}
		return qty;
	}
	
	@Override
	public Map<String,Object> doUnCheck(String no){
		return itemAppDao.doUnCheck(no);
	}

	@Override
	public Page<Map<String,Object>> getSendAppDtlJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		return itemAppDao.getSendAppDtlJqGrid(page,no,custCode);
	}
	
	@Override
	public Result doSendForXml(String m_umState,String no,String whcode,Date sendDate,String itemSendBatchNo,String supplCode,String czybh, String xml, String manySendRsn,String delivType,String delayReson,String delayRemark){
		if("S".equals(m_umState)){
			if("4".equals(delivType)){
				return itemAppDao.doSendBySupplForXml(m_umState, no, sendDate, supplCode, czybh, xml,manySendRsn,itemSendBatchNo);
			}else{
				return itemAppDao.doSendForXml(m_umState,no,whcode,sendDate,itemSendBatchNo,czybh,xml, manySendRsn,delivType,delayReson,delayRemark);
			}
		}else{
			return itemAppDao.doSendBySupplForXml(m_umState, no, sendDate, supplCode, czybh, xml,manySendRsn,itemSendBatchNo);
		}
	}

	@Override
	public Page<Map<String,Object>> getPSendAppDtlJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		return itemAppDao.getPSendAppDtlJqGrid(page,no,custCode);
	}
	
	@Override
	public Result doSendBatchForXml(String no,String whcode,String itemType1,
	        String custCode,Date sendDate,String remarks,String itemSendBatchNo,
	        String czybh, String xml, String manySendRsn,String delayReson,
	        String delayRemark, String delivType){
	    
		return itemAppDao.doSendBatchForXml(no,whcode,itemType1,custCode,sendDate,
		        remarks,itemSendBatchNo,czybh,xml, manySendRsn,delayReson,
		        delayRemark, delivType);
	}	
	
	@Override
	public boolean isSendPartNo(String no){
		return itemAppDao.isSendPartNo(no);
	}

	@Override
	public Page<Map<String,Object>> getShortageJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		return itemAppDao.getShortageJqGrid(page,no,custCode);
	}
	
	@Override
	public Result doShortage(String no,String czybh, String xml){
		return itemAppDao.doShortage(no,czybh,xml);
	}
	
	@Override
	public Result doSendMemo(String no,Date arriveDate,String delivType,String splRemarks,String czybh){
		return itemAppDao.doSendMemo(no,arriveDate,delivType,splRemarks,czybh);
	}	
	
	@Override
	public boolean isExistSendBatch(String no,String sendBatchNo,String custCode){
		return itemAppDao.isExistSendBatch(no,sendBatchNo,custCode);
	}

	@Override
	public Page<Map<String,Object>> goUnCheckJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, Date appDateFrom, Date appDateTo, String mainManCode){
		return itemAppDao.goUnCheckJqGrid(page, itemType1, itemType2, appDateFrom, appDateTo, mainManCode);
	}

	@Override
	public Page<Map<String,Object>> goConfirmReturnJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String mainManCode){
		return itemAppDao.goConfirmReturnJqGrid(page, itemType1, itemType2, mainManCode);
	}

	@Override
	public Page<Map<String,Object>> goUnReceiveBySplJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String supplCode, Date confirmDateFrom, Date confirmDateTo, String mainManCode,String delivType){
		return itemAppDao.goUnReceiveBySplJqGrid(page, itemType1, itemType2, supplCode, confirmDateFrom, confirmDateTo, mainManCode,delivType);
	}

	@Override
	public Page<Map<String,Object>> goUnSendBySplJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String supplCode, Date arriveDateFrom, Date arriveDateTo, String mainManCode,
			String prjRegion,String region,String delivType,Date notifySendDateFrom, Date notifySendDateTo){
		return itemAppDao.goUnSendBySplJqGrid(page, itemType1, itemType2, supplCode, arriveDateFrom, arriveDateTo, mainManCode, prjRegion, region,delivType,notifySendDateFrom,notifySendDateTo);
	}

	@Override
	public Page<Map<String,Object>> goDetailQueryJqGrid(Page<Map<String,Object>> page, ItemApp itemApp){
		return itemAppDao.goDetailQueryJqGrid(page, itemApp);
	}

	@Override
	public Page<Map<String,Object>> goJqGridLlglSendList(Page<Map<String,Object>> page, ItemApp itemApp){
		return itemAppDao.goJqGridLlglSendList(page, itemApp);
	}

	@Override
	public Page<Map<String,Object>> goJqGridFhDetail(Page<Map<String,Object>> page, String fhNo){
		return itemAppDao.goJqGridFhDetail(page, fhNo);
	}
	
	@Override
	public Result doSaveArriveConfirm(String no, String projectManRemark, String czybh){
		return itemAppDao.doSaveArriveConfirm(no, projectManRemark, czybh);
	}	
	
	@Override
	public Result getPhoneMessage(String module, String custCode, String no, String czybh){
		return itemAppDao.getPhoneMessage(module, custCode, no, czybh);
	}
	
	@Override
	public List<Map<String,Object>> getPhoneList(String custCode){
		return itemAppDao.getPhoneList(custCode);
	}
	
	@Override
	public Result doSaveSendSMS(String no, String message, String czybh, String xml){
		return itemAppDao.doSaveSendSMS(no, message, czybh, xml);
	}

	@Override
	public Page<Map<String,Object>> goJqGridPrintBatch(Page<Map<String,Object>> page, ItemApp itemApp){
		return itemAppDao.goJqGridPrintBatch(page, itemApp);
	}
	
	@Override
	public boolean getBeforeDoPrintZX(String no){
		return itemAppDao.getBeforeDoPrintZX(no);
	}
	
	@Override
	public boolean getBeforeDoPrintWFH(String no, String checkFH, String whcode){
		return itemAppDao.getBeforeDoPrintWFH(no, checkFH, whcode);
	}

	@Override
	public double getTotalQty(String custCode) {
		return itemAppDao.getTotalQty(custCode);
	}

	@Override
	public Page<Map<String, Object>> goJqGridDishesSend(Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemAppDao.goJqGridDishesSend(page, itemApp);
	}

	@Override
	public String isExistFSGR(String custCode) {
		return itemAppDao.isExistFSGR(custCode);
	}

	@Override
	public Result doSubmitCheck(String custCode,String appNo,String m_umState,String czy,
			String isPayCtrl,String isCupboard,String itemType1,String itemType2,String custType,String chgNo) {
		return itemAppDao.doSubmitCheck(custCode,appNo,m_umState,czy,isPayCtrl,isCupboard,itemType1,itemType2,custType,chgNo);
	}

	@Override
	public List<Map<String, Object>> checkCost(String no) {
		return itemAppDao.checkCost(no);
	}

	@Override
	public void doSuplCheck(ItemApp itemApp) {
		itemAppDao.doSuplCheck(itemApp);
	}

	@Override
	public String isExistJZSD(String custCode, String itemType2) {
		return itemAppDao.isExistJZSD(custCode, itemType2);
	}

	@Override
	public Page<Map<String, Object>> goJqGridPuFeeDetail(Page<Map<String, Object>> page, String puno) {
		return itemAppDao.goJqGridPuFeeDetail(page, puno);
	}

	@Override
	public Result doCheckApp(ItemApp itemApp) {
		return itemAppDao.doCheckApp(itemApp);
	}

	@Override
	public Page<Map<String, Object>> goJqGridItemDetail(Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemAppDao.goJqGridItemDetail(page, itemApp);
	}

	@Override
	public void doCheckAppR(ItemApp itemApp) {
		itemAppDao.doCheckAppR(itemApp);
		if("1".equals(itemApp.getCanPass())){
			itemAppDao.doPassCheckAppR(itemApp);
		}else{
			itemAppDao.doNotPassCheckAppR(itemApp);
		}
	}

	@Override
	public List<Map<String, Object>> hasZero(String no) {
		return itemAppDao.hasZero(no);
	}

	@Override
	public Page<Map<String, Object>> goNotInstallDetailJqGrid(Page<Map<String, Object>> page, Customer customer) {
		return itemAppDao.goNotInstallDetailJqGrid(page, customer);
	}

	@Override
	public Page<Map<String, Object>> goIntReplenishDetailJqGrid(Page<Map<String, Object>> page, Customer customer) {
		return itemAppDao.goIntReplenishDetailJqGrid(page, customer);
	}

	@Override
	public void updateEntrustProcess(ItemApp itemApp, String czybh) {
		ItemApp updateItemApp = this.get(ItemApp.class, itemApp.getNo());
		updateItemApp.setEntrustProcStatus(itemApp.getEntrustProcStatus());
		updateItemApp.setEntrustProcSendDate(itemApp.getEntrustProcSendDate());
		updateItemApp.setLastUpdate(new Date());
		updateItemApp.setLastUpdatedBy(czybh);
		updateItemApp.setActionLog("EDIT");
		this.update(updateItemApp);
	}

	@Override
	public Result doWhReceive(ItemApp itemApp) {
		return itemAppDao.doWhReceive(itemApp);
	}

	@Override
	public List<Map<String, Object>> hasCutNum(String no) {
		return itemAppDao.hasCutNum(no);
	}

	@Override
	public List<Map<String, Object>> isComplete(String no, String pks) {
		return itemAppDao.isComplete(no, pks);
	}

	@Override
	public String isMaterialSendJob(ItemApp ia) {
		return itemAppDao.isMaterialSendJob(ia);
	}

	@Override
	public Map<String, Object> getBalance(String no, String custCode) {
		return itemAppDao.getBalance(no, custCode);
	}
	
}
