package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.HashMap;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.WHCheckOut;
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.finance.WHCheckOutService;
import com.house.home.service.insales.WareHouseService;
@Controller
@RequestMapping("/admin/whCheckOut")
public class WHCheckOutController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WHCheckOutController.class);

	@Autowired
	private WHCheckOutService whCheckOutService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private CzybmService czybmService;
	
	
	private void resetWHCheckOut(WHCheckOut whCheckOut){
		if (whCheckOut!=null){
			if (StringUtils.isNotBlank(whCheckOut.getAppCZY())){
				Czybm czybm = czybmService.get(Czybm.class, whCheckOut.getAppCZY());
				if (czybm!=null){
					whCheckOut.setAppCZYDescr(czybm.getZwxm());
				}
			}
			if (StringUtils.isNotBlank(whCheckOut.getConfirmCZY())){
				Czybm czybm = czybmService.get(Czybm.class, whCheckOut.getConfirmCZY());
				if (czybm!=null){
					whCheckOut.setConfirmCZYDescr(czybm.getZwxm());
				}
			}
			if (StringUtils.isNotBlank(whCheckOut.getWhCode())){
				WareHouse wareHouse = wareHouseService.get(WareHouse.class, whCheckOut.getWhCode());
				if (wareHouse!=null){
					whCheckOut.setWhDescr(wareHouse.getDesc1());
				}
			}
			
		}
	}
	
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param whCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		whCheckOutService.findPageBySql(page, whCheckOut,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * WHCheckOut??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, WHCheckOut whCheckOut) throws Exception {
		
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_list").addObject("whCheckOut", whCheckOut);
	}
	
	/**
	 * ???????????????WHCheckOut??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????whCheckOut??????");
		WHCheckOut  whCheckOut = null;
		String postData = request.getParameter("postData");
		if(StringUtils.isNotEmpty(postData)){
			JSONObject obj = JSONObject.parseObject(postData);
			id = obj.getString("no");
		}
		if (StringUtils.isNotBlank(id)){
			whCheckOut = whCheckOutService.get(WHCheckOut.class, id);
		}else{
			whCheckOut = new WHCheckOut();
			whCheckOut.setAppCZY(this.getUserContext(request).getCzybh());
			whCheckOut.setDate(DateUtil.getNow());
			whCheckOut.setStatus("1");
			resetWHCheckOut(whCheckOut);
		}
		
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_save")
			.addObject("whCheckOut", whCheckOut);
	}

	/**
	 * ???????????????WHCheckOut??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????WHCheckOut??????");
		WHCheckOut whCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			whCheckOut = whCheckOutService.get(WHCheckOut.class, id);
			resetWHCheckOut(whCheckOut);
		}else{
			whCheckOut = new WHCheckOut();
		}
		
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_update")
			.addObject("whCheckOut", whCheckOut);
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckUpdate")
	public void doCheckUpdate(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("WHCheckOut??????????????????");
		
		if(StringUtils.isBlank(whCheckOut.getNo())){
			ServletUtils.outPrintFail(request, response, "WHCheckOut??????????????????,????????????");
			return;
		};
		
		try {
			WHCheckOut ia = this.whCheckOutService.get(WHCheckOut.class, whCheckOut.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "?????????????????????,????????????????????????!");
				return;
			}
			
			if (!"1".equals(ia.getStatus().trim())) { // 1??????
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????,????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "??????????????????", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	/**
	 * ???????????????WHCheckOut??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????WHCheckOut??????");
		WHCheckOut whCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			whCheckOut = whCheckOutService.get(WHCheckOut.class, id);
			resetWHCheckOut(whCheckOut);
		}else{
			whCheckOut = new WHCheckOut();
		}	
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_view")
			.addObject("whCheckOut", whCheckOut);
	}
	/**
	 * ???????????????WHCheckOut??????
	 * @return
	 */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????WHCheckOut??????");
		WHCheckOut whCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			whCheckOut = whCheckOutService.get(WHCheckOut.class, id);
			whCheckOut.setConfirmCZY(this.getUserContext(request).getCzybh());
			whCheckOut.setConfirmDate(DateUtil.getNow());
			whCheckOut.setCheckDate(DateUtil.getNow());
			resetWHCheckOut(whCheckOut);
		}else{
			whCheckOut = new WHCheckOut();
		}
		
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_check")
			.addObject("whCheckOut", whCheckOut);
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckCheck")
	public void doCheckCheck(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("WHCheckOut??????????????????");
		
		if(StringUtils.isBlank(whCheckOut.getNo())){
			ServletUtils.outPrintFail(request, response, "WHCheckOut??????????????????,????????????");
			return;
		};
		
		try {
			WHCheckOut ia = this.whCheckOutService.get(WHCheckOut.class, whCheckOut.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "?????????????????????,????????????????????????!");
				return;
			}
			
			if (!"1".equals(ia.getStatus().trim())) { // 1??????
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????,????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "??????????????????", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "??????????????????????????????");
		}
	}
	
	/**
	 * ??????????????????WHCheckOut??????
	 * @return
	 */
	@RequestMapping("/goCheckReturn")
	public ModelAndView goCheckReturn(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("??????????????????WHCheckOut??????");
		WHCheckOut whCheckOut = null;
		if (StringUtils.isNotBlank(id)){
			whCheckOut = whCheckOutService.get(WHCheckOut.class, id);
			resetWHCheckOut(whCheckOut);
		}else{
			whCheckOut = new WHCheckOut();
		}
		
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_checkReturn")
			.addObject("whCheckOut", whCheckOut);
	}
	/**
	 * ?????????????????????????????????
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckCheckReturn")
	public void doCheckCheckReturn(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("WHCheckOut?????????????????????");
		
		if(StringUtils.isBlank(whCheckOut.getNo())){
			ServletUtils.outPrintFail(request, response, "WHCheckOut??????????????????,???????????????");
			return;
		};
		
		try {
			WHCheckOut ia = this.whCheckOutService.get(WHCheckOut.class, whCheckOut.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "?????????????????????,???????????????????????????!");
				return;
			}
			
			if (!"2".equals(ia.getStatus().trim())) { // 1??????
				ServletUtils.outPrintFail(request, response, "????????????????????????????????????,???????????????????????????!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "?????????????????????", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "?????????????????????????????????");
		}
	}
	/**
	 * ??????JqGrid ??????????????????
	 * @param request
	 * @param response
	 * @param whCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		whCheckOutService.findDetailPageBySql(page, whCheckOut,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * WHCheckOutDetail??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, WHCheckOut whCheckOut) throws Exception {
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_detail").addObject("whCheckOut", whCheckOut);
	}
	/**
	 * ???????????????jqgrid
	 * @param request
	 * @param response
	 * @param whCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWHCheckOutItemAppSendAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> WHCheckOutItemAppSendAddJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppSend itemAppSend) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppSend.setItemRight(this.getUserContext(request).getItemRight());
		whCheckOutService.findWHCheckOutItemAppSendAdd(page, itemAppSend);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWHCheckOutItemAppSendAdd")
	public ModelAndView goItemAppDetailFast(HttpServletRequest request,
			HttpServletResponse response, ItemAppSend itemAppSend) throws Exception {
		String postData = request.getParameter("postData");
		JSONObject postParam = new JSONObject();
		if(StringUtils.isNotEmpty(postData)){
			postParam = JSONObject.parseObject(postData);
			
		}
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_itemAppSend").addObject("postParam", postParam);
	}
	
	/**
	 * ??????JqGrid????????????,?????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_itemAppSendDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_itemAppSendDetail(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whCheckOutService.findItemAppSendDetailBySql(page, whCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/goJqGrid_salesInvoiceDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_salesInvoiceDetail(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whCheckOutService.findSalesInvoiceDetailBySql(page, whCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ???????????????jqgrid
	 * @param request
	 * @param response
	 * @param whCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWHCheckOutSalesInvoiceAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> WHCheckOutSalesInvoiceAddJqGrid(HttpServletRequest request,
			HttpServletResponse response,SalesInvoice salesInvoice) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whCheckOutService.findWHCheckOutSalesInvoiceAdd(page,salesInvoice);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWHCheckOutSalesInvoiceAdd")
	public ModelAndView goSalesInvoiceDetailFast(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		String postData = request.getParameter("postData");
		JSONObject postParam = new JSONObject();
		if(StringUtils.isNotEmpty(postData)){
			postParam = JSONObject.parseObject(postData);
		}
		return new ModelAndView("admin/finance/whCheckOut/whCheckOut_salesInvoice").addObject("postParam", postParam);
	}
	/**
	 * ??????whCheckOut
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("??????whCheckOut??????");
		try{
			whCheckOut.setM_umState("A");
			whCheckOut.setDate(new Date());
			whCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			whCheckOut.setExpired("F");
			/*String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  itemAppsendDetailJson=jsonArray.toString();
			whCheckOut.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//????????????json?????? 
			String salesInvoiceDetailJson=jsonArray2.toString();
			whCheckOut.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			if((StringUtils.isBlank(itemAppsendDetailJson))&&(StringUtils.isBlank(salesInvoiceDetailJson))){
				ServletUtils.outPrintFail(request, response,"??????????????????????????????????????????????????????");
				return;
			}*/
			Result result = this.whCheckOutService.doWHCheckOutForProc(whCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("??????whCheckOut??????");
		try{
			whCheckOut.setM_umState("M");
			whCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  itemAppsendDetailJson=jsonArray.toString();
			whCheckOut.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//????????????json?????? 
			String salesInvoiceDetailJson=jsonArray2.toString();
			whCheckOut.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			if((StringUtils.isBlank(itemAppsendDetailJson))&&(StringUtils.isBlank(salesInvoiceDetailJson))){
				ServletUtils.outPrintFail(request, response,"??????????????????????????????????????????????????????");
				return;
			}*/
			Result result = this.whCheckOutService.doWHCheckOutForProc(whCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????????????????");
		}
	}
	@RequestMapping("/doCheckPass")
	public void doCheckPass(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("??????whCheckOut??????");
		try{
			whCheckOut.setStatus("2");
			whCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			whCheckOut.setExpired("F");
			/*String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  itemAppsendDetailJson=jsonArray.toString();
			whCheckOut.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//????????????json?????? 
			String salesInvoiceDetailJson=jsonArray2.toString();
			whCheckOut.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			if((StringUtils.isBlank(itemAppsendDetailJson))&&(StringUtils.isBlank(salesInvoiceDetailJson))){
				ServletUtils.outPrintFail(request, response,"??????????????????????????????????????????????????????");
				return;
			}*/
			Result result = this.whCheckOutService.doWHCheckOutCheckForProc(whCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????whCheckOut??????");
		}
	}
	@RequestMapping("/doCheckCancel")
	public void doCheckCancel(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("??????whCheckOut??????");
		try{
			whCheckOut.setStatus("3");
			whCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			whCheckOut.setExpired("F");
			/*String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  itemAppsendDetailJson=jsonArray.toString();
			whCheckOut.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//????????????json?????? 
			String salesInvoiceDetailJson=jsonArray2.toString();
			whCheckOut.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			if((StringUtils.isBlank(itemAppsendDetailJson))&&(StringUtils.isBlank(salesInvoiceDetailJson))){
				ServletUtils.outPrintFail(request, response,"??????????????????????????????????????????????????????");
				return;
			}*/
			Result result = this.whCheckOutService.doWHCheckOutCheckForProc(whCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "??????????????????");
		}
	}
	@RequestMapping("/doCheckBack")
	public void doCheckBack(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		logger.debug("?????????whCheckOut??????");
		try{
			whCheckOut.setStatus("4");
			whCheckOut.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*String itemAppsendDetail =request.getParameter("itemAppsendDetailJson");
			String salesInvoiceDetail =request.getParameter("salesInvoiceDetailJson");
			JSONObject jsonObject = JSONObject.parseObject(itemAppsendDetail);
			JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("detailJson").toString());//????????????json??????  
			String  itemAppsendDetailJson=jsonArray.toString();
			whCheckOut.setItemAppsendDetailJson(itemAppsendDetailJson);
			JSONObject jsonObject2 = JSONObject.parseObject(salesInvoiceDetail);
			JSONArray jsonArray2 = JSONArray.parseArray(jsonObject2.get("detailJson").toString());//????????????json?????? 
			String salesInvoiceDetailJson=jsonArray2.toString();
			whCheckOut.setSalesInvoiceDetailJson(salesInvoiceDetailJson);
			if((StringUtils.isBlank(itemAppsendDetailJson))&&(StringUtils.isBlank(salesInvoiceDetailJson))){
				ServletUtils.outPrintFail(request, response,"??????????????????????????????????????????????????????");
				return;
			}*/
			Result result = this.whCheckOutService.doWHCheckOutCheckForProc(whCheckOut);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "???????????????");
		}
	}
	/**
	 * ??????JqGrid????????????,???????????????2??????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_totalByItemType2")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_totalByItemType2(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whCheckOutService.findTotalByItemType2BySql(page, whCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ??????JqGrid????????????,???????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_totalByBrand")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_totalByBrand(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whCheckOutService.findTotalByBrandBySql(page, whCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doWHCheckOutExcel")
	public void doWHCheckOutExcel(HttpServletRequest request, 
			HttpServletResponse response, WHCheckOut whCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		whCheckOutService.findPageBySql(page, whCheckOut,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doWHCheckOutDetailExcel")
	public void doWHCheckOutDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, WHCheckOut whCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		whCheckOutService.findDetailPageBySql(page, whCheckOut,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * ??????JqGrid????????????,???????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_totalByCompany")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_totalByCompany(HttpServletRequest request,
			HttpServletResponse response,WHCheckOut whCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		whCheckOutService.findTotalByCompanyBySql(page, whCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *?????????????????????????????????
	 * @param request
	 * @param response
	 * @param splCheckOut
	 */
	@ResponseBody
	@RequestMapping("/doGenWHCheckOutSendFee")
	public void GenOtherCost(HttpServletRequest request, HttpServletResponse response, WHCheckOut whCheckOut){
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			whCheckOut.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
			map = whCheckOutService.doGenWHCheckOutSendFee(whCheckOut);
			ServletUtils.outPrint(request, response, true, "????????????", map, true);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
}
