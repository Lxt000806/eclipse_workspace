package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.MathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.IntReplenishDT;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.ItemPreAppService;
import com.house.home.service.insales.SupplierService;

@Controller
@RequestMapping("/admin/itemApp")
public class ItemAppController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemAppController.class);

	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ItemPreAppService itemPreAppService;
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemApp.setItemRight(getUserContext(request).getItemRight());
		//itemAppService.findPageBySql(page, itemApp);
		itemAppService.goJqGridItemApp(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????JqGridCode????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridCode")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCode(HttpServletRequest request,
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.findPageBySqlCode(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemApp??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		itemApp.setExpired("F");
		itemApp.setStatus("OPEN,CONFIRMED,CONRETURN,WAITCON");
		itemApp.setCostRight(getUserContext(request).getCostRight());
		itemApp.setItemRight(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_list").addObject("itemApp", itemApp);
	}
	
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, ItemApp itemApp) throws Exception {

		if (StringUtils.isNotBlank(itemApp.getSupplCode())){
			Supplier supplier = supplierService.get(Supplier.class, itemApp.getSupplCode());
			if (supplier!=null){
				itemApp.setSupplCodeDescr(supplier.getDescr());
			}
		}
		return new ModelAndView("admin/insales/itemApp/itemApp_code").addObject("itemApp", itemApp);
	}
	
	/**
	 * ??????id????????????????????????
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemApp")
	@ResponseBody
	public JSONObject getSupplier(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("?????????id??????", false);
		}
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		if(itemApp == null){
			return this.out("??????????????????code="+id+"???????????????", false);
		}
		return this.out(itemApp, true);
	}
	
	/**
	 * ???????????????ItemApp??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????ItemApp??????");
/*		ItemApp itemApp = null;
		String postData = request.getParameter("postData");
		if(StringUtils.isNotEmpty(postData)){
			JSONObject obj = JSONObject.parseObject(postData);
			id = obj.getString("no");
		}
		if (StringUtils.isNotBlank(id)){
			itemApp = itemAppService.get(ItemApp.class, id);
		}else{
			itemApp = new ItemApp();
			itemApp.setStatus("OPEN");
			itemApp.setType("S");
			itemApp.setIsSetItem("0");
			itemApp.setIsService(0);
			itemApp.setDelivType("1");
		}
		*/
		Xtcs xtcs=itemPreAppService.get(Xtcs.class, "AftCustCode");
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		itemPreMeasure.setM_umState("A");
		itemPreMeasure.setIsSetItem("0");
		itemPreMeasure.setIsService("0");
		itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
		itemPreMeasure.setItemAppType("0");
		itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		itemPreMeasure.setItemAppStatus("OPEN");
		itemPreMeasure.setOtherCost(0.0);
		itemPreMeasure.setAppNo("???????????????");
		itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
		itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		String[] itemRights = itemPreMeasure.getItemRightForSupplier().split(",");
		if(itemRights != null && itemRights.length == 1){
			itemPreMeasure.setItemType1(itemRights[0]);
		}
		itemPreMeasure.setAddFromPage("itemApp_list");
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll")
			.addObject("itemPreMeasure", itemPreMeasure).addObject("AftCustCode", xtcs.getQz());
	}
	/**
	 * ???????????????ItemApp??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id,String no,String type){
/*		logger.debug("???????????????ItemApp??????");
		ItemApp itemApp = null;
		if (StringUtils.isNotBlank(id)){
			itemApp = itemAppService.get(ItemApp.class, id);
		}else{
			itemApp = new ItemApp();
		}
		
		return new ModelAndView("admin/itemApp/itemApp_update")
			.addObject("itemApp", itemApp);*/
		logger.debug("???????????????????????????");
		Xtcs xtcs=itemPreAppService.get(Xtcs.class, "AftCustCode");
		if("S".equals(type)){
			Map<String,Object> map = itemAppService.getItemAppInfo(no);
			ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
			BeanConvertUtil.mapToBean(map, itemPreMeasure);
			itemPreMeasure.setM_umState("M");
			itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
			itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
			itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
			itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
			return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll")
						.addObject("itemPreMeasure", itemPreMeasure)
						.addObject("AftCustCode", xtcs.getQz());
		}
		Map<String,Object> map = itemAppService.getItemReturnInfo(no);
		ItemApp itemApp = new ItemApp();
		BeanConvertUtil.mapToBean(map, itemApp);
		itemApp.setM_umState("M");
		itemApp.setCostRight(getUserContext(request).getCostRight());
		itemApp.setItemRight(getUserContext(request).getItemRight().trim());
		itemApp.setCzybh(getUserContext(request).getCzybh().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_llth").addObject("itemApp", itemApp);
	}
	
	/**
	 * ???????????????ItemApp??????
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????ItemApp??????");
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		
		return new ModelAndView("admin/itemApp/itemApp_detail")
				.addObject("itemApp", itemApp);
	}
	/**
	 * ??????????????????
	 * @author	created by zb
	 * @date	2020-1-1--??????3:52:41
	 */
	@RequestMapping("/goEntrustProcessSign")
	public ModelAndView goEntrustProcessSign(HttpServletRequest request, HttpServletResponse response, 
			String no, String custAddress){
		logger.debug("?????????????????????????????????");
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		itemApp.setCustAddress(custAddress);
		return new ModelAndView("admin/insales/itemApp/itemApp_entrustProcessSign")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * ??????ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("??????ItemApp??????");
		try{
			ItemApp xt = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "ItemApp??????");
				return;
			}
			String str = itemAppService.getSeqNo("tItemApp");
			itemApp.setNo(str);
			//this.itemAppService.save(itemApp);
//			String detailJson = request.getParameter("detailJson");
//			if(StringUtils.isNotEmpty(detailJson)){
//				JSONArray detailArray = JSONArray.parseArray(detailJson);
//				if(!detailArray.isEmpty()){
//					List<ItemAppDetail> itemAppDetailList = Lists.newArrayList();
//					for(int i = 0; i < detailArray.size(); i ++){
//						ItemAppDetail detail = detailArray.getObject(i, ItemAppDetail.class);
//						detail.setLastUpdate(new Date());
//						itemAppDetailList.add(detail);
//					}
//					itemApp.setItemAppDetailList(itemAppDetailList);
//				}
//			}
//			if(itemApp.getItemAppDetailList().isEmpty()){
//				ServletUtils.outPrintFail(request, response,"?????????????????????????????????????????????");
//			}
			this.itemAppService.doItemAppForProc(itemApp);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????ItemApp??????");
		}
	}
	
	/**
	 * ??????ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("??????ItemApp??????");
		try{
			ItemApp xt = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if (xt!=null){
				this.itemAppService.update(itemApp);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.itemAppService.save(itemApp);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????ItemApp??????");
		}
	}
	
	/**
	 * ??????ItemApp
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????ItemApp??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemApp??????????????????,????????????");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemApp itemApp = itemAppService.get(ItemApp.class, deleteId);
				if(itemApp == null)
					continue;
				itemApp.setExpired("T");
				itemAppService.update(itemApp);
			}
		}
		logger.debug("??????ItemApp IDS={} ??????",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"????????????");
	}

	/**
	 *ItemApp??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppService.findPageBySql(page, itemApp);
	}
	//---??????
	/**
	 * ????????????-??????????????????
	 * @param request
	 * @param response
	 * @param oldNo
	 * @param reqPks
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getReturnAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReturnAddJqGrid(HttpServletRequest request,
			HttpServletResponse response,String oldNo,String reqPks,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.getReturnAddJqGrid(page,oldNo,reqPks,custCode );
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param itemCode
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getReturnDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReturnDetailJqGrid(HttpServletRequest request,HttpServletResponse response,String itemCode,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.getReturnDetailJqGrid(page,itemCode,custCode );
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????,????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getItemReturnDTJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemReturnDTJqGrid(HttpServletRequest request,HttpServletResponse response,String no,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.getItemReturnDTJqGrid(page,no,custCode );
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSendAppDtlJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSendAppDtlJqGrid(HttpServletRequest request,HttpServletResponse response,String no,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.getSendAppDtlJqGrid(page,no,custCode );
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPSendAppDtlJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPSendAppDtlJqGrid(HttpServletRequest request,HttpServletResponse response,String no,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.getPSendAppDtlJqGrid(page,no,custCode );
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param custCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getShortageJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getShortageJqGrid(HttpServletRequest request,HttpServletResponse response,String no,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.getShortageJqGrid(page,no,custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????-???????????????
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param appDateFrom
	 * @param appDateTo
	 * @param mainManCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUnCheckJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goUnCheckJqGrid(HttpServletRequest request, HttpServletResponse response,
	        String itemType1, String itemType2, Date appDateFrom, Date appDateTo, String mainManCode) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request); 
		if(StringUtils.isNotBlank(itemType1)){
			itemType1 = "'"+itemType1.trim()+"'";
		}else{
			itemType1 = "'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'";
		}
		itemAppService.goUnCheckJqGrid(page, itemType1, itemType2, appDateFrom, appDateTo, mainManCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????-??????????????????
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param mainManCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConfirmReturnJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goConfirmReturnJqGrid(HttpServletRequest request, HttpServletResponse response,
	        String itemType1, String itemType2, String mainManCode) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(itemType1)){
			itemType1 = "'"+itemType1.trim()+"'";
		}else{
			itemType1 = "'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'";
		}
		itemAppService.goConfirmReturnJqGrid(page, itemType1, itemType2, mainManCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????-????????????????????????
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param supplCode
	 * @param confirmDateFrom
	 * @param confirmDateTo
	 * @param mainManCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUnReceiveBySplJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goUnReceiveBySplJqGrid(HttpServletRequest request, HttpServletResponse response,
	        String itemType1, String itemType2, String supplCode, Date confirmDateFrom, Date confirmDateTo,
	        String mainManCode,String delivType) {
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(itemType1)){
			itemType1 = "'"+itemType1.trim()+"'";
		}else{
			itemType1 = "'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'";
		}
		itemAppService.goUnReceiveBySplJqGrid(page, itemType1, itemType2, supplCode, confirmDateFrom, confirmDateTo, mainManCode,delivType);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????-????????????????????????
	 * @param request
	 * @param response
	 * @param itemType1
	 * @param supplCode
	 * @param arriveDateFrom
	 * @param arriveDateTo
	 * @param mainManCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUnSendBySplJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goUnSendBySplJqGrid(HttpServletRequest request,HttpServletResponse response,
	        String itemType1, String itemType2, String supplCode, Date arriveDateFrom, Date arriveDateTo,
	        String mainManCode,String prjRegion,String region,String delivType,Date notifySendDateFrom, Date notifySendDateTo) {
	    
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(itemType1)){
			itemType1 = "'"+itemType1.trim()+"'";
		}else{
			itemType1 = "'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'";
		}
		itemAppService.goUnSendBySplJqGrid(page, itemType1, itemType2, supplCode, arriveDateFrom, arriveDateTo, mainManCode, prjRegion, region,delivType,notifySendDateFrom,notifySendDateTo);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailQueryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailQueryJqGrid(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(itemApp.getItemType1())){
			itemApp.setItemType1("'"+itemApp.getItemType1().trim()+"'");
		}else{
			itemApp.setItemType1("'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'");
		}
		itemAppService.goDetailQueryJqGrid(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ?????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridLlglSendList")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridLlglSendList(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(itemApp.getItemType1())){
			itemApp.setItemType1("'"+itemApp.getItemType1().trim()+"'");
		}else{
			itemApp.setItemType1("'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'");
		}
		itemAppService.goJqGridLlglSendList(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param fhNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridFhDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridFhDetail(HttpServletRequest request, HttpServletResponse response, String fhNo) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.goJqGridFhDetail(page, fhNo);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridPrintBatch")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridPrintBatch(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isBlank(itemApp.getItemType1())){
			itemApp.setItemType1(getUserContext(request).getItemRight().trim());
		}
		itemAppService.goJqGridPrintBatch(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	//----??????
	@RequestMapping("/goLlxxth")
	public ModelAndView goLlxxth(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("???????????????????????????");
		ItemApp itemApp = new ItemApp();
		itemApp.setCostRight(getUserContext(request).getCostRight());
		//itemApp.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		itemApp.setItemRight(getUserContext(request).getItemRight().trim());
		itemApp.setNo("???????????????");
		itemApp.setStatus("OPEN");
		itemApp.setType("R");
		itemApp.setM_umState("A");
		itemApp.setOtherCost(0.0);
		itemApp.setCzybh(getUserContext(request).getCzybh().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_llth").addObject("itemApp", itemApp);
	}
	@RequestMapping("/goLlxxthAdd")
	public ModelAndView goLlxxthAdd(HttpServletRequest request, HttpServletResponse response, String custCode,String oldNo,String reqPks){
		logger.debug("?????????????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("custCode", custCode);
		map.put("oldNo", oldNo);
		map.put("reqPks", reqPks);
		map.put("m_umState", "R");
		return new ModelAndView("admin/insales/itemApp/itemApp_llthAdd").addObject("data", map);
	}
	@RequestMapping("/goLlxxthDetail")
	public ModelAndView goLlxxthDetail(HttpServletRequest request, HttpServletResponse response, String custCode,String itemCode){
		logger.debug("?????????????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("custCode", custCode);
		map.put("itemCode", itemCode);
		map.put("m_umState", "V");
		return new ModelAndView("admin/insales/itemApp/itemApp_llthDetail").addObject("data", map);
	}	
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response, String id,String no,String type,String materWorkType2,String custType){
		logger.debug("???????????????????????????");
		if("S".equals(type)){
			Map<String,Object> map = itemAppService.getItemAppInfo(no);
			Xtcs xtcs=itemPreAppService.get(Xtcs.class, "AftCustCode");
			ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
			BeanConvertUtil.mapToBean(map, itemPreMeasure);
			itemPreMeasure.setM_umState("C");
			itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
			itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
			itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
			if(StringUtils.isNotBlank(itemPreMeasure.getWareHouseCode())){
				itemPreMeasure.setWareHouseDescr(itemPreAppService.get(WareHouse.class, itemPreMeasure.getWareHouseCode()).getDesc1());
			}
			if(itemPreAppService.hasInSetReq(itemPreMeasure.getCustCode(), itemPreMeasure.getItemType1())){
				itemPreMeasure.setHasInSetReq("1");
			}else{
				itemPreMeasure.setHasInSetReq("0");
			}
			itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
			Map<String,Object> payMap = itemPreAppService.checkCustPay(no);
			return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll")
			.addObject("itemPreMeasure", itemPreMeasure)
			.addObject("AftCustCode", xtcs.getQz())
			.addObject("payMap", payMap);
		}
		Map<String,Object> map = itemAppService.getItemReturnInfo(no);
		ItemApp itemApp = new ItemApp();
		BeanConvertUtil.mapToBean(map, itemApp);
		itemApp.setM_umState("C");
		itemApp.setCostRight(getUserContext(request).getCostRight());
		itemApp.setItemRight(getUserContext(request).getItemRight().trim());
		itemApp.setCzybh(getUserContext(request).getCzybh().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_llth").addObject("itemApp", itemApp);
	}
	
	@RequestMapping("/goSend")
	public ModelAndView goSend(HttpServletRequest request, HttpServletResponse response,String no,String custCode,String m_umState,String whcode,String whcodeDescr){
		logger.debug("?????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("m_umState",m_umState);
		map.put("no", no);
		map.put("custCode", custCode);
		map.put("sendDate", new Date());
		map.put("whcode", whcode);
		map.put("whcodeDescr", whcodeDescr);
		map.put("itemRight", getUserContext(request).getItemRight().trim());
		
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		if(itemApp != null && StringUtils.isNotBlank(itemApp.getSupplCode())){
			map.put("supplCode", itemApp.getSupplCode());
			Supplier supplier = itemAppService.get(Supplier.class, itemApp.getSupplCode());
			if(supplier != null){
				map.put("supplCodeDescr", supplier.getDescr());
			}else{
				map.put("supplCodeDescr", "");
			}
		}
		map.put("delivType", itemApp.getDelivType());
		map.put("sendCount", this.itemPreAppService.getSendCountNum(no));
		map.put("confirmDate", itemApp.getConfirmDate());
		map.put("itemType1", itemApp.getItemType1().trim());
		map.put("notifySendDate", itemApp.getNotifySendDate());
		return new ModelAndView("admin/insales/itemApp/itemApp_send").addObject("data", map);
	}
	
	@RequestMapping("/goSendBatch")
	public ModelAndView goSendBatch(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("?????????????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		map.put("m_umState","S");
		map.put("no", no);
		map.put("custCode", itemApp.getCustCode());
		map.put("sendDate", new Date());
		map.put("whcode", itemApp.getWhcode());
		if(StringUtils.isNotBlank(itemApp.getWhcode())) map.put("whcodeDescr", itemAppService.get(WareHouse.class, itemApp.getWhcode()).getDesc1());
		map.put("delivType", "1");
		map.put("itemType1", itemApp.getItemType1().trim());
		map.put("remarks", itemApp.getRemarks());
		map.put("sendCount", this.itemPreAppService.getSendCountNum(no));
		map.put("confirmDate", itemApp.getConfirmDate());
		return new ModelAndView("admin/insales/itemApp/itemApp_sendBatch").addObject("data", map);
	}
	
	@RequestMapping("/goShortage")
	public ModelAndView goShortage(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("?????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		map.put("m_umState","S");
		map.put("no", no);
		map.put("custCode", itemApp.getCustCode());
		return new ModelAndView("admin/insales/itemApp/itemApp_shortage").addObject("data", map);
	}
	
	@RequestMapping("/goSendMemo")
	public ModelAndView goSendMemo(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("???????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		map.put("m_umState","M");
		map.put("no", no);
		map.put("status", itemApp.getStatus().trim());
		map.put("arriveDate", itemApp.getArriveDate());
		map.put("delivType", itemApp.getDelivType());
		map.put("splRemark", itemApp.getSplRemark());
		return new ModelAndView("admin/insales/itemApp/itemApp_sendMemo").addObject("data", map);
	}
	
	@RequestMapping("/goConnSendBatch")
	public ModelAndView goConnSendBatch(HttpServletRequest request, HttpServletResponse response,String no,String sendBatchNo){
		logger.debug("???????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		map.put("m_umState","M");
		map.put("no", no);
		map.put("custCode", itemApp.getCustCode());
		map.put("sendBatchNo", sendBatchNo);
		return new ModelAndView("admin/insales/itemApp/itemApp_connectSendBatch").addObject("data", map);
	}
	
	@RequestMapping("/goDesignerConf")
	public ModelAndView goDesignerConf(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("??????????????????????????????");

		Map<String,Object> map = itemAppService.getItemAppInfo(no);
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		BeanConvertUtil.mapToBean(map, itemPreMeasure);
		itemPreMeasure.setM_umState("Q");
		itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
		itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
		itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll").addObject("itemPreMeasure", itemPreMeasure);
	}
	
	@RequestMapping("/goLlgz")
	public ModelAndView goLlgz(HttpServletRequest request, HttpServletResponse response){
		logger.debug("???????????????????????????");

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("itemRight", "('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		map.put("appDateTo", DateUtil.addDate(new Date(), -3));
		map.put("confirmDateTo", DateUtil.addDate(new Date(), -1));
		map.put("arriveDateTo", DateUtil.addDate(new Date(), -1));
		map.put("itemRightForSupplier", getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_llgz").addObject("data", map);
	}
	
	@RequestMapping("/goLlgzView")
	public ModelAndView goLlgzView(HttpServletRequest request, HttpServletResponse response, String type, String no){
		logger.debug("?????????????????????????????????");
		if("S".equals(type)){
			Xtcs xtcs=itemPreAppService.get(Xtcs.class, "AftCustCode");
			Map<String,Object> map = itemAppService.getItemAppInfo(no);
			ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
			BeanConvertUtil.mapToBean(map, itemPreMeasure);
			itemPreMeasure.setM_umState("V");
			itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
			itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
			itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
			itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
			itemPreMeasure.setSendCount(this.itemPreAppService.getSendCountNum(no));
			return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll")
				.addObject("itemPreMeasure", itemPreMeasure)
				.addObject("AftCustCode", xtcs.getQz());
		}
		Map<String,Object> map = itemAppService.getItemReturnInfo(no);
		ItemApp itemApp = new ItemApp();
		BeanConvertUtil.mapToBean(map, itemApp);
		itemApp.setM_umState("V");
		itemApp.setCostRight(getUserContext(request).getCostRight());
		itemApp.setItemRight(getUserContext(request).getItemRight().trim());
		itemApp.setCzybh(getUserContext(request).getCzybh().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_llth").addObject("itemApp", itemApp);
	}
	
	@RequestMapping("/goLlgzMemo")
	public ModelAndView goLlgzMemo(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("?????????????????????????????????");
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("m_umState","M");
		map.put("no",no);
		map.put("followRemark", itemApp.getFollowRemark());
		return new ModelAndView("admin/insales/itemApp/itemApp_lldgzMemo").addObject("data", map);
	}
	
	@RequestMapping("/goDetailQuery")
	public ModelAndView goDetailQuery(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("???????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("costRight", getUserContext(request).getCostRight());
		map.put("itemRight", "('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		map.put("expired", "F");
		map.put("status", "OPEN,CONFIRMED,SEND,RETURN,CONRETURN,WAITCON");
		map.put("m_umState", "V");
		map.put("itemRightForSupplierCode", getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_detailQuery").addObject("data", map);
	}
	
	@RequestMapping("/goLlglSendList")
	public ModelAndView goLlglSendList(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("??????????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("m_umState", "V");
		map.put("itemRight", "('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		if(itemAppService.getCZYGNQX(getUserContext(request).getCzybh().trim(),"0102","????????????") != null){
			map.put("confirmRight", "1");
		}else{
			map.put("confirmRight", "0");
		}
		map.put("itemRightForSupplier", getUserContext(request).getItemRight().trim());
		return new ModelAndView("admin/insales/itemApp/itemApp_llglSendList").addObject("data", map);
	}
	
	@RequestMapping("/goFhDetail")
	public ModelAndView goFhDetail(HttpServletRequest request, HttpServletResponse response, String fhNo){
		logger.debug("???????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("fhNo", fhNo);
		return new ModelAndView("admin/insales/itemApp/itemApp_fhDetail").addObject("data", map);
	}
	
	@RequestMapping("/goArriveConfirm")
	public ModelAndView goArriveConfirm(HttpServletRequest request, HttpServletResponse response, String no, String confirmStatus, String confirmReason, String projectManRemark){
		logger.debug("???????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("m_umState", "M");
		map.put("confirmStatus", confirmStatus);
		map.put("confirmReason", confirmReason);
		map.put("projectManRemark", projectManRemark);
		return new ModelAndView("admin/insales/itemApp/itemApp_arriveConfirm").addObject("data", map);
	}
	
	@RequestMapping("/goItemAppMsg")
	public ModelAndView goItemAppMsg(HttpServletRequest request, HttpServletResponse response, String no, String custCode){
		logger.debug("??????????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("custCode", custCode);
		map.put("m_umState", "S");
		Result result = itemAppService.getPhoneMessage("", custCode, no, getUserContext(request).getCzybh().trim());
		map.put("message", result.getInfo());
		List<Map<String,Object>> list = itemAppService.getPhoneList(custCode);
		if(list != null && list.size() > 0){
			map.put("phoneList", list);
		}else{
			map.put("phoneList", "[]");
		}
		return new ModelAndView("admin/insales/itemApp/itemApp_msg").addObject("data", map);
	}
	
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("?????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		if(StringUtils.isNotBlank(itemApp.getPrintCZY())){
			map.put("printCZYDescr",itemAppService.get(Employee.class, itemApp.getPrintCZY()).getNameChi());
		}
		if(StringUtils.isNotBlank(itemApp.getWhcode())){
			map.put("isManagePosi", itemAppService.get(WareHouse.class, itemApp.getWhcode()).getIsManagePosi());
			map.put("whcodeDescr", itemAppService.get(WareHouse.class, itemApp.getWhcode()).getDesc1());
		}
		map.put("no", no);
		map.put("printTimes", itemApp.getPrintTimes());
		map.put("printCZY", itemApp.getPrintCZY());
		map.put("whcode", itemApp.getWhcode());
		map.put("printDate", itemApp.getPrintDate());
		map.put("custCode", itemApp.getCustCode());
		map.put("CZYDescr", getUserContext(request).getZwxm());
		return new ModelAndView("admin/insales/itemApp/itemApp_print").addObject("data", map);
	}
	
	@RequestMapping("/goPrintBatch")
	public ModelAndView goPrintBatch(HttpServletRequest request, HttpServletResponse response){
		logger.debug("???????????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("itemRight", "('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		map.put("expired", "F");
		map.put("status","CONFIRMED");
		return new ModelAndView("admin/insales/itemApp/itemApp_printBatch").addObject("data", map);
	}

	@RequestMapping("/goContAdd")
	public ModelAndView goContAdd(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????????????????");
		ItemPreMeasure itemPreMeasure = new ItemPreMeasure();
		itemPreMeasure.setM_umState("A");
		itemPreMeasure.setIsSetItem("0");
		itemPreMeasure.setIsService("0");
		itemPreMeasure.setCostRight(getUserContext(request).getCostRight());
		itemPreMeasure.setItemAppType("0");
		itemPreMeasure.setItemRight("('"+getUserContext(request).getItemRight().trim().replace(",", "','")+"')");
		itemPreMeasure.setItemAppStatus("OPEN");
		itemPreMeasure.setOtherCost(0.0);
		itemPreMeasure.setAppNo("???????????????");
		itemPreMeasure.setCzybh(getUserContext(request).getCzybh());
		itemPreMeasure.setItemRightForSupplier(getUserContext(request).getItemRight().trim());
		String[] itemRights = itemPreMeasure.getItemRightForSupplier().split(",");
		if(itemRights != null && itemRights.length == 1){
			itemPreMeasure.setItemType1(itemRights[0]);
		}
		itemPreMeasure.setContinuityAdd("1");
		return new ModelAndView("admin/insales/itemPreApp/itemPreAppManage_list_addll")
			.addObject("itemPreMeasure", itemPreMeasure);
	}	
	//---??????
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param whcode
	 * @return
	 */
	@RequestMapping("/getWHOperator")
	@ResponseBody
	public boolean getWHOperator(HttpServletRequest request, HttpServletResponse response,String whcode){
		String czybh = getUserContext(request).getCzybh().trim();
		Map<String,Object> map = itemAppService.getWHOperator(czybh,whcode);
		if(map != null ){
			return true;
		}
		return false;
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doLlglthSave")
	public void doLlglthSave(HttpServletRequest request, HttpServletResponse response,ItemApp itemApp){		
		logger.debug("????????????????????????");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			String detailJson = request.getParameter("detailJson");
			itemApp.setAppCzy(getUserContext(request).getCzybh());

			Result result = itemAppService.doLlglthSave(itemApp,XmlConverUtil.jsonToXmlNoHead(detailJson));
			if(result.isSuccess()){
				returnMap.put("success", true);
				returnMap.put("info", result.getInfo());
				ServletUtils.outPrintSuccess(request, response, returnMap);
			}else{
				returnMap.put("success", false);
				returnMap.put("info", result.getInfo());
				ServletUtils.outPrintFail(request, response, returnMap);
			}
		}catch(Exception e){
			returnMap.put("success", false);
			returnMap.put("info", "????????????????????????");
			ServletUtils.outPrintFail(request, response, returnMap);
			e.printStackTrace();
		}
	}
	/**
	 * ???????????????/??????????????????
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping("/getSendReturnCheckRight")
	@ResponseBody
	public Map<String,Object> getSendReturnCheckRight(HttpServletRequest request, HttpServletResponse response,String type, String no){
		String czybh = getUserContext(request).getCzybh().trim();
		String mkdm="";
		String gnmc="";
		if("S".equals(type)){
			mkdm += "0102";
			gnmc += "????????????";
		}else{
			mkdm += "0102";
			gnmc += "????????????";
		}
		Map<String,Object> map = itemAppService.getCZYGNQX(czybh,mkdm,gnmc);
		Map<String,Object> returnMap = new HashMap<String, Object>();
		if(map != null ){
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if(itemApp != null && !(!"CONRETURN".equals(itemApp.getStatus().trim()) && !"OPEN".equals(itemApp.getStatus().trim())
					&& !"WAITCON".equals(itemApp.getStatus().trim()))){
				returnMap.put("hasCheckRight", true) ;
			}else{
				returnMap.put("hasCheckRight", false) ;
				returnMap.put("errorFlag", true) ;
				returnMap.put("errorInfo", "???????????????????????????,??????????????????") ;
			}
		}else{
			returnMap.put("hasCheckRight", false) ;
		}
		return returnMap;
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param type
	 * @param no
	 */
	@RequestMapping("/getUnCheckRight")
	public void getUnCheckRight(HttpServletRequest request, HttpServletResponse response,String type,String no){
		try{
			String czybh = getUserContext(request).getCzybh().trim();
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if("R".equals(itemApp.getType())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????");
				return;
			}
			if("S".equals(itemApp.getType())){
				if(itemAppService.getCZYGNQX(czybh,"0102","???????????????") == null ){
					ServletUtils.outPrintFail(request, response, "??????????????????????????????");
					return;
				}
				List<Map<String, Object>> list=itemAppService.hasCutNum(no);
				if(list!=null && list.size()>0){
					Integer submitNum=Integer.parseInt(list.get(0).get("submitNum").toString());
					Integer appNum=Integer.parseInt(list.get(0).get("appNum").toString());
					if(submitNum>0){
						ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????");
						return;
					}else if(appNum>0){
						ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
						return;
					}
				}
			}
			if(!"CONFIRMED".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????,???????????????????????????");
				return;
			}
			if(!"0".equals(itemApp.getSplStatus()) && !"1".equals(itemApp.getSplStatus()) && !"4".equals(itemApp.getSplStatus()) && !"5".equals(itemApp.getSplStatus())){
				ServletUtils.outPrintFail(request, response, "?????????????????????????????????????????????????????????????????????????????????????????????????????????,???????????????????????????");
				return;
			}
			if(itemAppService.getSendQtyByNo(no) > 0.0){
				ServletUtils.outPrintFail(request, response, "????????????"+no+"?????????????????????????????????????????????");
				return;
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "?????????????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doUnCheck")
	public void doUnCheck(HttpServletRequest request, HttpServletResponse response,String no){
		try{
			Map<String,Object> map = itemAppService.doUnCheck(no);
			if(map != null){
				if(Integer.valueOf(map.get("ret").toString()) == 0){
					ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????????????????");
				}else{
					ServletUtils.outPrintSuccess(request, response);
				}
			}else{
				ServletUtils.outPrintFail(request, response, "?????????????????????");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "?????????????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param m_umState
	 */
	@RequestMapping("/getGoSendBefore")
	public void getGoSendBefore(HttpServletRequest request,HttpServletResponse response,String no,String m_umState){
		try{
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if("OPEN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
				return;
			}
			if("SEND".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????");
				return;
			}
			if("CANCEL".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "?????????????????????,????????????");
				return;
			}
			if("RETURN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????,????????????");
				return;
			}
			if("CONRETURN".equals(itemApp.getStatus().trim()) || "WAITCON".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
				return;
			}
			if("P".equals(m_umState)){
				if(!"0".equals(itemApp.getSplStatus().trim())){
					if(!"5".equals(itemApp.getSplStatus().trim()) && "JC".equals(itemApp.getItemType1().trim())){
						ServletUtils.outPrintFail(request, response, "?????????????????????????????????,????????????");
						return;
					}
				}
				if(itemAppService.getCZYGNQX(getUserContext(request).getCzybh().trim(), "0102", "????????????????????????") == null ){
					if(!"0".equals(itemApp.getSplStatus()) && !"1".equals(itemApp.getSplStatus())){
						ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????????????????,????????????????????????");
						return;
					}
				}
			}
			if(itemAppService.getSendQtyByNo(no) > 0.0){
				ServletUtils.outPrintFail(request, response, "????????????"+no+"????????????????????????????????????"+("P".equals(m_umState)?"?????????":"??????")+"??????");
				return;
			}
			if("ZC".equals(itemApp.getItemType1().trim())){
				if(itemAppService.isComplete(no, "").size()>0){
					ServletUtils.outPrintFail(request, response, "????????????"+no+"???????????????????????????????????????????????????"+("P".equals(m_umState)?"?????????":"??????")+"??????");
					return;
				}
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param m_umState
	 * @param sendDate
	 * @param whcode
	 * @param supplCode
	 * @param checkSupplCode
	 * @param supplCodes
	 */
	@RequestMapping("/getDoSendBefore")
	public void getDoSendBefore(HttpServletRequest request,HttpServletResponse response,
						String m_umState,Date sendDate,String whcode,
						String supplCode,boolean checkSupplCode,String supplCodes,String no,String custCode){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		try{
			if(sendDate == null){
				returnMap.put("code", -1);
				returnMap.put("msg", "?????????????????????");
				ServletUtils.outPrintFail(request, response,returnMap);
				return;
			}else if(DateUtil.dateDiff(DateUtil.startOfTheDay(sendDate),new Date()) > 0){
				returnMap.put("code", -1);
				returnMap.put("msg", "?????????????????????????????????????????????????????????????????????");
				ServletUtils.outPrintFail(request, response, returnMap);
				return;
			}
			
			if("S".equals(m_umState)){
				if(StringUtils.isBlank(whcode)){
					returnMap.put("code", -1);
					returnMap.put("msg", "?????????????????????");
					ServletUtils.outPrintFail(request, response, returnMap);
					return;
				}
				if(itemAppService.getWHOperator(getUserContext(request).getCzybh().trim(), whcode) == null){
					returnMap.put("code", -1);
					returnMap.put("msg", "????????????" +itemAppService.get(WareHouse.class, whcode).getDesc1().trim()+ "??????????????????");
					ServletUtils.outPrintFail(request, response, returnMap);
					return;
				}
				
			}else{
				if(StringUtils.isBlank(supplCode)){
					returnMap.put("code", -1);
					returnMap.put("msg", "?????????????????????");
					ServletUtils.outPrintFail(request, response, returnMap);
					return;
				}
				if(checkSupplCode){
					String[] supplCodeArr = supplCodes.split(",");
					for(int i=0;i<supplCodeArr.length;i++){
						if(!supplCodeArr[i].trim().equals(supplCode.trim())){
							returnMap.put("code",400002);
							returnMap.put("checkSupplCode", checkSupplCode);
							returnMap.put("msg", "???????????????????????????????????????????????????????????????");
							ServletUtils.outPrintSuccess(request, response, returnMap);
							return;
						}
					}
				}
			}
			Map<String,Object> balanceMap = itemAppService.getBalance(no, custCode);
			if (balanceMap != null) {
				returnMap.put("code",400003);
				returnMap.put("msg", "??????"+balanceMap.get("PayNum").toString()+"????????????????????????"
								+balanceMap.get("ShouldBalance").toString()+"???????????????????????????");
				ServletUtils.outPrintSuccess(request, response, returnMap);
				return;
			}
			returnMap.put("code",1);
			ServletUtils.outPrintSuccess(request, response,returnMap);
		}catch(Exception e){
			returnMap.put("code", -1);
			returnMap.put("msg", "??????????????????");
			ServletUtils.outPrintFail(request, response, returnMap);
			e.printStackTrace();
		}
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param m_umState
	 * @param no
	 * @param whcode
	 * @param sendDate
	 * @param itemSendBatchNo
	 * @param supplCode
	 */
	@RequestMapping("/doSend")
	public void doSend(HttpServletRequest request,HttpServletResponse response,String m_umState,String no,String whcode,Date sendDate,String itemSendBatchNo,String supplCode, String manySendRsn,String delivType,String delayReson,String delayRemark){
		try{
			System.out.println(manySendRsn);
			String detailJson = request.getParameter("detailJson");
			Result result = itemAppService.doSendForXml(m_umState,no,whcode,sendDate,itemSendBatchNo,supplCode,getUserContext(request).getCzybh().trim(),XmlConverUtil.jsonToXmlNoHead(detailJson), manySendRsn,delivType,delayReson,delayRemark);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ???????????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoSendBatchBefore")
	public void getGoSendBatchBefore(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if("OPEN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
				return;
			}
			if("SEND".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????");
				return;
			}
			if("CANCEL".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "?????????????????????,????????????");
				return;
			}
			if("RETURN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????,????????????");
				return;
			}
			if("CONRETURN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}	
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param whcode
	 * @param isSelNum
	 * @param theSendQty
	 */
	@RequestMapping("/getDoSendBatchBefore")
	public void getDoSendBatchBefore(HttpServletRequest request,HttpServletResponse response,
						String whcode,Integer isSelNum,Double theSendQty,String pks,String itemType1,String no,String custCode){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		try{
			if(StringUtils.isBlank(whcode)){
				returnMap.put("code", -1);
				returnMap.put("msg", "?????????????????????");
				ServletUtils.outPrintFail(request, response,returnMap);
				return;
			}
			if(isSelNum == null || isSelNum == 0){
				returnMap.put("code", -1);
				returnMap.put("msg", "????????????????????????????????????????????????????????????????????????????????????????????????0???");
				ServletUtils.outPrintFail(request, response, returnMap);
				return;
			}
			if(itemAppService.getWHOperator(getUserContext(request).getCzybh().trim(), whcode) == null){
				returnMap.put("code", -1);
				returnMap.put("msg", "????????????" +itemAppService.get(WareHouse.class, whcode).getDesc1().trim()+ "??????????????????");
				ServletUtils.outPrintFail(request, response, returnMap);
				return;
			}
			if("ZC".equals(itemType1.trim())){
				if(itemAppService.isComplete("", pks).size()>0){
					returnMap.put("code", -1);
					returnMap.put("msg", "??????????????????????????????????????????????????????");
					ServletUtils.outPrintFail(request, response, returnMap);
					return;
				}
			}
			
			Map<String,Object> balanceMap = itemAppService.getBalance(no, custCode);
			if (balanceMap != null) {
				returnMap.put("code",400003);
				returnMap.put("msg", "??????"+balanceMap.get("PayNum").toString()+"????????????????????????"
								+balanceMap.get("ShouldBalance").toString()+"???????????????????????????");
				ServletUtils.outPrintSuccess(request, response, returnMap);
				return;
			}
			
			returnMap.put("code",1);
			returnMap.put("theSendQty", theSendQty);
			ServletUtils.outPrintSuccess(request, response,returnMap);
		}catch(Exception e){
			returnMap.put("code", -1);
			returnMap.put("msg", "????????????");
			ServletUtils.outPrintFail(request, response, returnMap);
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param whcode
	 * @param itemType1
	 * @param custCode
	 * @param sendDate
	 * @param remarks
	 * @param itemSendBatchNo
	 */
	@RequestMapping("/doSendBatch")
	public void doSendBatch(HttpServletRequest request,HttpServletResponse response,
						String no,String whcode,String itemType1,String custCode,Date sendDate,
						String remarks,String itemSendBatchNo, String manySendRsn,String delayReson,
						String delayRemark, String delivType){
		try{
			String detailJson = request.getParameter("detailJson");
			Result result = itemAppService.doSendBatchForXml(no,whcode,itemType1,custCode,
			        sendDate,remarks,itemSendBatchNo,getUserContext(request).getCzybh().trim(),
			        XmlConverUtil.jsonToXmlNoHead(detailJson), manySendRsn,delayReson,
			        delayRemark, delivType);
			
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoShortageBefore")
	public void getGoShortageBefore(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if("OPEN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????????????????");
				return;
			}
			if("SEND".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????");
				return;
			}
			if("CANCEL".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "?????????????????????,????????????????????????");
				return;
			}
			if("RETURN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????,????????????????????????");
				return;
			}
			if("CONRETURN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????,????????????????????????");
				return;
			}
			if(!itemAppService.isSendPartNo(no)){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????,????????????????????????!");
				return;
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/doShortage")
	public void doShortage(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			String detailJson = request.getParameter("detailJson");
			Result result = itemAppService.doShortage(no,getUserContext(request).getCzybh().trim(),XmlConverUtil.jsonToXmlNoHead(detailJson));
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????????????????");
			e.printStackTrace();
		}
	}	
	/**
	 * ???????????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoSendMemoBefore")
	public void getGoSendMemoBefore(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if(!"CONFIRMED".equals(itemApp.getStatus().trim()) && !"SEND".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????/???????????????,????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param arriveDate
	 * @param delivType
	 * @param splRemark
	 */
	@RequestMapping("/doSendMemo")
	public void doSendMemo(HttpServletRequest request,HttpServletResponse response,
							String no,Date arriveDate,String delivType,String splRemark){
		try{
			if(StringUtils.isBlank(delivType)){
				ServletUtils.outPrintFail(request, response, "?????????????????????!");
				return;
			}
			Result result = itemAppService.doSendMemo(no,arriveDate,delivType,splRemark,getUserContext(request).getCzybh().trim());
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ???????????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoConnSendBatchBefore")
	public void getGoConnSendBatchBefore(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			
			if(!"R".equals(itemApp.getType().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param sendBatchNo
	 * @param custCode
	 */
	@RequestMapping("/doConnSendBatch")
	public void doConnSendBatch(HttpServletRequest request,HttpServletResponse response,
							String no,String sendBatchNo,String custCode){
		try{
			if(itemAppService.isExistSendBatch(no, sendBatchNo, custCode)){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????!");
				return;
			}
			System.out.println(sendBatchNo);
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			itemApp.setSendBatchNo(sendBatchNo);
			itemAppService.update(itemApp);
			
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoDesignerConfBefore")
	public void getGoDesignerConfBefore(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			
			if(!"4".equals(itemApp.getSplStatus().trim()) || !"S".equals(itemApp.getType().trim()) || !"JC".equals(itemApp.getItemType1().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
				return;
			}
			
			if("SEND".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????Excel
	 * @param request
	 * @param response
	 * @param dataTableName
	 * @param itemType1
	 * @param mainManCode
	 * @param supplCode
	 * @param appDateFrom
	 * @param appDateTo
	 * @param confirmDateFrom
	 * @param confirmDateTo
	 * @param arriveDateFrom
	 * @param arriveDateTo
	 */
	@RequestMapping("/doLlgzExcel")
	public void doLlgzExcel(HttpServletRequest request, HttpServletResponse response,
							String dataTableName, String itemType1, String itemType2, String mainManCode, String supplCode,
							Date appDateFrom, Date appDateTo, Date confirmDateFrom, Date confirmDateTo,
							Date arriveDateFrom, Date arriveDateTo,String prjRegion,String region,String delivType,
							Date notifySendDateFrom, Date notifySendDateTo){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		String title = "";
		if(StringUtils.isNotBlank(itemType1)){
			itemType1 = "'"+itemType1.trim()+"'";
		}else{
			itemType1 = "'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'";
		}
		if("unCheckDataTable".equals(dataTableName)){
			title += "??????????????????";
			itemAppService.goUnCheckJqGrid(page, itemType1, itemType2, appDateFrom, appDateTo, mainManCode);
		}else if("confirmReturnDataTable".equals(dataTableName)){
			title += "?????????????????????";
			itemAppService.goConfirmReturnJqGrid(page, itemType1, itemType2, mainManCode);
		}else if("unReceiveBySplDataTable".equals(dataTableName)){
			title += "???????????????????????????";
			itemAppService.goUnReceiveBySplJqGrid(page, itemType1, itemType2, supplCode, confirmDateFrom, confirmDateTo, mainManCode,delivType);
		}else {
			title += "???????????????????????????";
			itemAppService.goUnSendBySplJqGrid(page, itemType1, itemType2, supplCode, arriveDateFrom, arriveDateTo, mainManCode, prjRegion, region,delivType,notifySendDateFrom,notifySendDateTo);
		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),title+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param followRemark
	 */
	@RequestMapping("/doLldgzMemo")
	public void doLldgzMemo(HttpServletRequest request, HttpServletResponse response, String no, String followRemark){
		try{
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			itemApp.setFollowRemark(followRemark);
			itemApp.setActionLog("EDIT");
			itemAppService.update(itemApp);
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "???????????????????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????Excel
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doExcelDetailQuery")
	public void doExcelDetailQuery(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		if(StringUtils.isNotBlank(itemApp.getItemType1())){
			itemApp.setItemType1("'"+itemApp.getItemType1().trim()+"'");
		}else{
			itemApp.setItemType1("'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'");
		}
		itemAppService.goDetailQueryJqGrid(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"??????????????????-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
	/**
	 * ?????????????????????????????????2??????
	 * @param request
	 * @param response
	 * @param itemType1
	 * @return
	 */
	@RequestMapping("/getItemType2TreeOpt")
	@ResponseBody
	public JSONObject getItemType2TreeOpt(HttpServletRequest request, HttpServletResponse response, String itemType1){
		StringBuilder html = new StringBuilder();
		html.append("[{\"id\":\"_VIRTUAL_RO0T_ID_\",\"isParent\":true,\"name\":\"?????????\",\"open\":true,\"pId\":\"\"},");
		List<Map<String,Object>> list = itemPreAppService.getItemType2Opt(itemType1.trim());
		if(list != null && list.size()>0 ){
			for(int i=0;i<list.size();i++){
				html.append("{\"id\":\""+list.get(i).get("Code").toString().trim()+"\",\"name\":\""+list.get(i).get("fd").toString().trim()+"\",\"pId\":\"_VIRTUAL_RO0T_ID_\"}");
				if(i < list.size()-1){
					html.append(",");
				}
			}
		}
		html.append("]");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("html", html.toString());
		return jsonObject;
	}
	/**
	 * ??????????????????Excel
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doExcelLlglSendList")
	public void doExcelLlglSendList(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		if(StringUtils.isNotBlank(itemApp.getItemType1())){
			itemApp.setItemType1("'"+itemApp.getItemType1().trim()+"'");
		}else{
			itemApp.setItemType1("'"+getUserContext(request).getItemRight().trim().replace(",", "','")+"'");
		}
		itemAppService.goJqGridLlglSendList(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"???????????????-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
	/**
	 * ??????????????????Excel
	 * @param request
	 * @param response
	 * @param fhNo
	 */
	@RequestMapping("/doExcelFhDetail")
	public void doExcelFhDetail(HttpServletRequest request, HttpServletResponse response, String fhNo){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppService.goJqGridFhDetail(page, fhNo);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"????????????-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
	/**
	 * ???????????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param confirmStatus
	 */
	@RequestMapping("/getGoArriveConfirmBefore")
	public void getGoArriveConfirmBefore(HttpServletRequest request,HttpServletResponse response,String no,String confirmStatus){
		try{
			
			ItemAppSend itemAppSend = itemAppService.get(ItemAppSend.class, no);
			if(!itemAppSend.getConfirmStatus().equals(confirmStatus)){
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????????????????");
				return;
			}
			
			if("1".equals(confirmStatus)){
				ServletUtils.outPrintFail(request, response, "???????????????????????????????????????????????????????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ??????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param projectManRemark
	 */
	@RequestMapping("/doSaveArriveConfirm")
	public void doSaveArriveConfirm(HttpServletRequest request,HttpServletResponse response,String no,String projectManRemark){
		try{
			Result result = itemAppService.doSaveArriveConfirm(no, projectManRemark, getUserContext(request).getCzybh().trim());
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "????????????");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 * @param message
	 * @param detailJson
	 */
	@RequestMapping("/doSaveSendSMS")
	public void doSaveSendSMS(HttpServletRequest request,HttpServletResponse response,String no,String message,String detailJson){
		try{
			Result result = itemAppService.doSaveSendSMS(no, message, getUserContext(request).getCzybh().trim(),XmlConverUtil.jsonToXmlNoHead(detailJson));
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, "????????????");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/getGoBeforePrint")
	public void getGoBeforePrint(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			if(!"RZ".equals(itemApp.getItemType1().trim()) &&!"JZ".equals(itemApp.getItemType1().trim()) && !"ZC".equals(itemApp.getItemType1().trim()) && !"CONFIRMED".equals(itemApp.getStatus().trim()) 
					&& !"SEND".equals(itemApp.getStatus().trim()) && !"RETURN".equals(itemApp.getStatus().trim())&& !"CONRETURN".equals(itemApp.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????????????????????????????????????????,????????????");
				return;
			}
			Customer customer=itemAppService.get(Customer.class, itemApp.getCustCode());
			if("OPEN".equals(itemApp.getStatus().trim())&&!"7".equals(customer.getConstructStatus())){
				ServletUtils.outPrintFail(request, response, "??????????????????????????????,????????????");
				return;
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
			e.printStackTrace();
		}
	}
	/***
	 * ??????Excel??????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doExcelItemAppList")
	public void doExcelItemAppList(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemApp.setItemRight(getUserContext(request).getItemRight());
		itemAppService.goJqGridItemApp(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),"????????????-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmm"), columnList, titleList, sumList);
	
	}
	/**
	 * ???????????????????????????
	 * @param request
	 * @param response
	 * @param nos
	 * @param from
	 * @param checkFH
	 * @param whcode
	 */
	@RequestMapping("/getBeforeDoPrint")
	public void getBeforeDoPrint(HttpServletRequest request, HttpServletResponse response, String nos, String from, String checkFH, String whcode){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String[] noAttr = nos.replace("','", ",").split(",");
			StringBuilder canPrintNos = new StringBuilder(); //???????????????????????????
			StringBuilder problemPrintNos = new StringBuilder();//?????????????????????
			for(int i=0;i<noAttr.length;i++){
				if("zx".equals(from)){
					if(!itemAppService.getBeforeDoPrintZX(noAttr[i])){
						problemPrintNos.append("???"+noAttr[i]+"???<br/>");
					}else{
						canPrintNos.append("'"+noAttr[i]+"',");
					}
				}else if("wfh".equals(from) || "fhwl".equals(from)){
					if(!itemAppService.getBeforeDoPrintWFH(noAttr[i], checkFH, whcode)){
						problemPrintNos.append("???"+noAttr[i]+"???<br/>");
					}else{
						canPrintNos.append("'"+noAttr[i]+"',");
					}
				}else{
					canPrintNos.append("'"+noAttr[i]+"',");
				}
			}
			if(problemPrintNos.length() > 0){
				if("zx".equals(from)){
					map.put("errorInfo", "???????????????:<br/>"+problemPrintNos.toString()+"?????????????????????????????????");
				}else if("wfh".equals(from) || "fhwl".equals(from)){
					map.put("errorInfo", "???????????????:<br/>"+problemPrintNos.toString()+"????????????????????????");
				}
				map.put("rs", false);
			}else{
				map.put("rs", true);
			}
			if(canPrintNos.length() > 0){
				canPrintNos.deleteCharAt(canPrintNos.length()-1);
			}
			map.put("canPrintNos", canPrintNos.toString());
			ServletUtils.outPrintSuccess(request, response, map);
		}catch(Exception e){
			map.put("rs", false);
			map.put("errorInfo", "????????????");
			ServletUtils.outPrintFail(request, response, map);
			e.printStackTrace();
		}
	}
	/**
	 * ????????????????????????
	 * @param request
	 * @param response
	 * @param nos
	 */
	@RequestMapping("doUpdatePrint")
	public void doUpdatePrint(HttpServletRequest request, HttpServletResponse response, String nos){
		try{
			if(StringUtils.isNotBlank(nos)){
				String[] noAttr = nos.replace("','", ",").split(",");
				Date printDate = new Date();
				String printCZY = getUserContext(request).getCzybh();
				for(int i=0;i<noAttr.length;i++){
					ItemApp itemApp = itemAppService.get(ItemApp.class, noAttr[i]);
					if(StringUtils.isNotBlank(itemApp.getNo())){
						itemApp.setPrintCZY(printCZY);
						itemApp.setPrintDate(printDate);
						itemApp.setPrintTimes(itemApp.getPrintTimes()+1);
						itemAppService.update(itemApp);
					}
				}
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "????????????????????????");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/isExistFSGR")
	public String isExistFSGR(HttpServletRequest request, HttpServletResponse response, String custCode){
		
		//??????????????????????????????????????????
		Customer customer = itemAppService.get(Customer.class, custCode);
		if(customer != null && StringUtils.isNotBlank(customer.getConstructStatus()) && "7".equals(customer.getConstructStatus().trim())){
			return "false";
		}
		
		return itemAppService.isExistFSGR(custCode);
	}

	
	@RequestMapping("/doSubmitCheck")
	@ResponseBody
	public void doSubmitCheck(HttpServletRequest request,HttpServletResponse response,String custCode,
		String appNo,String m_umState,String itemType1,String isCupboard,String itemType2,String custType,String chgNo){
		
		StringBuilder alarmStr = new StringBuilder();
		
		Map<String,Object> custPayInfoMap = itemPreAppService.getCustPayInfo(custCode);
		Double firstPay=Double.valueOf(custPayInfoMap.get("firstPay").toString());
		Double secondPay=Double.valueOf(custPayInfoMap.get("secondPay").toString());
		Double thirdPay=Double.valueOf(custPayInfoMap.get("thirdPay").toString());
		Double fourPay=Double.valueOf(custPayInfoMap.get("fourPay").toString());
		Double designFee=Double.valueOf(custPayInfoMap.get("designFee").toString());
		Double chgFee=Double.valueOf(custPayInfoMap.get("zjje").toString());
		Double custPay=Double.valueOf(custPayInfoMap.get("payFee").toString());
//		String isPayCtrl="";
		
		String payNumStr = "";
		String payNum = "";
		Double payPer = 0.0;
		Double diffAmount = 0.0;
		Double stdDesignFee = 0.0;
		Double resultDesignFee = 0.0;
		Double chgPer = 0.0;
		Double isRcvDesignFee = 0.0;
		
		Map<String,Object> map = itemPreAppService.checkCustPay(appNo);
		
		if(map != null){
			payNum = map.get("PayNum").toString();
			payPer = Double.valueOf(map.get("PayPer").toString());
			diffAmount = Double.valueOf(map.get("DiffAmount").toString());
			stdDesignFee = Double.valueOf(map.get("StdDesignFee").toString());
			chgPer = Double.valueOf(map.get("ChgPer").toString());
			
			if("1".equals(map.get("IsRcvDesignFee").toString())){
				isRcvDesignFee = 1.0;
			}
			
			if("2".equals(map.get("DesignFeeType").toString())){//"1".equals(map.get("Custtype"))  && "2".equals(payType) ??????????????????
				resultDesignFee = stdDesignFee;
			}else{
				resultDesignFee = designFee;
			}

			if("1".equals(payNum)){
				if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay*payPer-custPay, 4) > diffAmount){
					alarmStr.append("??????:");
					if(resultDesignFee * isRcvDesignFee > 0){
						alarmStr.append(MathUtil.stripTrailingZeros(resultDesignFee));
					}
					
					if(chgFee * chgPer > 0){
						alarmStr.append("+"+MathUtil.stripTrailingZeros(chgFee*chgPer));
					}else if(chgFee * chgPer < 0){
						alarmStr.append(MathUtil.stripTrailingZeros(chgFee*chgPer));
					}
					
					if(resultDesignFee * isRcvDesignFee > 0 || chgFee * chgPer != 0){
						alarmStr.append("+");
					}
					alarmStr.append(MathUtil.stripTrailingZeros(firstPay));
					if(payPer == 1){
						alarmStr.append("-");
					}else{
						alarmStr.append("*"+MathUtil.stripTrailingZeros(payPer*100)+"%-");
					}
					alarmStr.append(MathUtil.stripTrailingZeros(custPay)+"="
									+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay*payPer-custPay, 4)));
				}
			}else if("2".equals(payNum)){
				if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay*payPer-custPay, 4) > diffAmount){
					alarmStr.append("??????:"+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay*payPer-custPay, 4)));
				}
			}else if("3".equals(payNum)){
				if(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay > diffAmount){
					if(fourPay == 0){//????????????????????????????????????
						payNumStr = "??????:";
					}else{
						payNumStr = "??????:";
					}
		        	if(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay > thirdPay){
		    	            alarmStr.append(payNumStr+MathUtil.stripTrailingZeros(thirdPay)+ " ??????:"
		    	            			    +MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay-thirdPay, 4)));
		        	}else{
		        		alarmStr.append(payNumStr+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay*payPer-custPay, 4)));
		        	}
				}
			}else if("4".equals(payNum)){
		        if(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay+fourPay*payPer-custPay,4) > diffAmount){
		        	alarmStr.append("??????:"+MathUtil.stripTrailingZeros(MathUtil.round(resultDesignFee*isRcvDesignFee+chgFee*chgPer+firstPay+secondPay+thirdPay+fourPay*payPer-custPay, 4)));
		        }
			}
			
		}
		
/*		if(alarmStr.length()>0){
			isPayCtrl="0";
		}else{
			isPayCtrl="1";
		}
		*/
		Result result = this.itemAppService.doSubmitCheck(custCode, appNo, m_umState,getUserContext(request).getCzybh(), alarmStr.toString(), isCupboard, itemType1,itemType2,custType,chgNo);
		ServletUtils.outPrintSuccess(request, response,result.getInfo()+"&"+result.getJson());
	}
	
	@ResponseBody
	@RequestMapping("/isExistJZSD")
	public String isExistJZSD(HttpServletRequest request, HttpServletResponse response, String custCode,String itemType2){
		
		//??????????????????????????????????????????
		Customer customer = itemAppService.get(Customer.class, custCode);
		if(customer != null && StringUtils.isNotBlank(customer.getConstructStatus()) && "7".equals(customer.getConstructStatus().trim())){
			return "false";
		}
		
		return itemAppService.isExistJZSD(custCode,itemType2);
	}
	
	@RequestMapping("/goSendBySuppl")
	public ModelAndView goSendBySuppl(HttpServletRequest request, HttpServletResponse response,String no,String custCode,String m_umState,String whcode,String whcodeDescr){
		return goSend(request, response, no, custCode, m_umState, whcode, whcodeDescr);
	}
	@RequestMapping("/goSoldOut")
	public ModelAndView goSoldOut(HttpServletRequest request,
			HttpServletResponse response, WareHouse wareHouse) throws Exception {
			return new ModelAndView("admin/insales/itemApp/itemApp_soldOut")
				.addObject("wareHouse", wareHouse);
	}
	@ResponseBody
	@RequestMapping("/hasZero")
	public String hasZero(HttpServletRequest request, HttpServletResponse response, String no){
		List<Map<String, Object>> list=itemAppService.hasZero(no);
		if(list.size()>0){
			return "1";
		}
		return "0";
	}
	@RequestMapping("/goWhReceive")
	public ModelAndView goWhReceive(HttpServletRequest request, HttpServletResponse response,String no,String custCode,String m_umState,String whcode,String whcodeDescr){
		logger.debug("?????????????????????");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("custCode", custCode);
		map.put("itemRight", getUserContext(request).getItemRight().trim());
		
		ItemApp itemApp = itemAppService.get(ItemApp.class, no);
		if(itemApp != null && StringUtils.isNotBlank(itemApp.getSupplCode())){
			map.put("supplCode", itemApp.getSupplCode());
			Supplier supplier = itemAppService.get(Supplier.class, itemApp.getSupplCode());
			if(supplier != null){
				map.put("supplCodeDescr", supplier.getDescr());
			}else{
				map.put("supplCodeDescr", "");
			}
		}
		if(itemApp.getIntRepPK()!=null){
			if("JC".equals(itemApp.getItemType1().trim())&&itemApp.getIntRepPK()>0){
				IntReplenishDT intReplenishDt= itemAppService.get(IntReplenishDT.class, itemApp.getIntRepPK());
				if(intReplenishDt != null){
					map.put("intReplenishNo",intReplenishDt.getNo() );
				}	
			}
		}
		map.put("remarks", itemApp.getRemarks());
		map.put("delivType", itemApp.getDelivType());
		map.put("sendCount", this.itemPreAppService.getSendCountNum(no));
		map.put("confirmDate", itemApp.getConfirmDate());
		map.put("itemType1", itemApp.getItemType1().trim());
		ItemType1 itemType1=itemAppService.get(ItemType1.class, itemApp.getItemType1());
		if(StringUtils.isNotBlank(itemType1.getWhCode())){
			WareHouse wareHouse=itemAppService.get(WareHouse.class, itemType1.getWhCode());
			map.put("whcode", wareHouse.getCode());
			map.put("whcodeDescr", wareHouse.getDesc1());
			map.put("itemType1", itemApp.getItemType1());
		}
		return new ModelAndView("admin/insales/itemApp/itemApp_whReceive").addObject("data", map);
	}
	@RequestMapping("/doWhReceive")
	public void doWhReceive(HttpServletRequest request,HttpServletResponse response,
							String no,String whcode, String remarks){
		try{
			
			ItemApp itemApp = itemAppService.get(ItemApp.class, no);
			itemApp.setWhcode(whcode);
			itemApp.setRemarks(remarks);
			itemApp.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = itemAppService.doWhReceive(itemApp);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"????????????");
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
			e.printStackTrace();
		}
	}
	/**
	 * ????????????????????????
	 * @author	created by zb
	 * @date	2020-1-1--??????4:05:18
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doEntrustProcessSign")
	public void doEntrustProcessSign(HttpServletRequest request,HttpServletResponse response,
		ItemApp itemApp){
		try{
			if (StringUtils.isBlank(itemApp.getNo())) {
				ServletUtils.outPrintFail(request, response, "??????????????????");
				return;
			}
			this.itemAppService.updateEntrustProcess(itemApp, getUserContext(request).getCzybh());
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????????????????");
			e.printStackTrace();
		}
	}
}
