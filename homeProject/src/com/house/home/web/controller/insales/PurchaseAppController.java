package com.house.home.web.controller.insales;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.web.controller.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.house.home.service.insales.PurchaseAppService;

import org.springframework.web.bind.annotation.RequestMapping;

import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssConfigure;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.PurchaseApp;
import com.house.home.entity.insales.WareHouse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;

@Controller
@RequestMapping("/admin/purchaseApp")
public class PurchaseAppController extends BaseController{
	@Autowired
	private  PurchaseAppService purchaseAppService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		purchaseAppService.findPageBySql(page, purchaseApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/exportingPurchaseAppDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> exportingPurchaseAppDetailJqGrid(
	        HttpServletRequest request, HttpServletResponse response, PurchaseApp purchaseApp) {
	    
	    Page<Map<String, Object>> page = newPageForJqGrid(request);
	    
        purchaseAppService.exportingPurchaseAppDetails(page, purchaseApp);
	    
	    return new WebPage<Map<String,Object>>(page);
	}
	
    @RequestMapping("/goDetailJqGrid")
    @ResponseBody
    public  WebPage<Map<String , Object>> goDetailJqGrid(HttpServletRequest request, 
            HttpServletResponse response,PurchaseApp purchaseApp) throws Exception{

        Page<Map<String, Object>> page=this.newPageForJqGrid(request);
        purchaseAppService.findDetailPageBySql(page, purchaseApp);
        return new WebPage<Map<String,Object>>(page);
    }
    
    @RequestMapping("/goPurchConJqGrid")
    @ResponseBody
    public  WebPage<Map<String , Object>> goPurchConJqGrid(HttpServletRequest request, 
            HttpServletResponse response,PurchaseApp purchaseApp) throws Exception{

        Page<Map<String, Object>> page=this.newPageForJqGrid(request);
        purchaseAppService.findPurchConPageBySql(page, purchaseApp);
        return new WebPage<Map<String,Object>>(page);
    }
    
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {
		
//		purchaseApp.setDateFrom(DateUtil.startOfTheMonth(new Date()));
//		purchaseApp.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		System.out.println(SystemConfig.getProperty("serverUrl", "", "refreshCache"));
		System.out.println(OssConfigure.accessKeyId);
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_list")
		.addObject("purchaseApp", purchaseApp);
	}
	
    @RequestMapping("/goCode")
    public ModelAndView goCode(HttpServletRequest request, HttpServletResponse response,
            PurchaseApp purchaseApp) {

        return new ModelAndView("admin/insales/purchaseApp/purchaseApp_code")
                .addObject("purchaseApp", purchaseApp);
    }

    @RequestMapping("/getPurchaseApp")
    @ResponseBody
    public JSONObject getPurchase(HttpServletRequest request, HttpServletResponse response,
            String id) {
        
        if (StringUtils.isEmpty(id)) {
            return this.out("传入的No为空", false);
        }
        
        PurchaseApp purchaseApp = purchaseAppService.get(PurchaseApp.class, id);
        
        if (purchaseApp == null) {
            return this.out("系统中不存在No=" + id + "的采购申请单", false);
        }
        
        return this.out(purchaseApp, true);
    }
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {
		
		Employee employee = new Employee();
		employee = purchaseAppService.get(Employee.class, this.getUserContext(request).getEmnum());
		purchaseApp.setAppDate(new Date());
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_save")
		.addObject("purchaseApp", purchaseApp).addObject("employee", employee)
		.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {

		Employee employee = new Employee();
		WareHouse wareHouse = new WareHouse();
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getAppCZY())){
				employee = purchaseAppService.get(Employee.class, purchaseApp.getAppCZY());
			}
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getWhCode())){
				wareHouse = purchaseAppService.get(WareHouse.class, purchaseApp.getWhCode());
			}
			
			purchaseApp.setAppDate(new Date());
		}
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_update")
		.addObject("purchaseApp", purchaseApp).addObject("employee", employee)
		.addObject("czybh", this.getUserContext(request).getCzybh()).addObject("wareHouse", wareHouse);
	}
	
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {

		Employee employee = new Employee();
		WareHouse wareHouse = new WareHouse();
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getAppCZY())){
				employee = purchaseAppService.get(Employee.class, purchaseApp.getAppCZY());
			}
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getWhCode())){
				wareHouse = purchaseAppService.get(WareHouse.class, purchaseApp.getWhCode());
			}
			
			purchaseApp.setAppDate(new Date());
		}
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_confirm")
		.addObject("purchaseApp", purchaseApp).addObject("employee", employee)
		.addObject("czybh", this.getUserContext(request).getCzybh()).addObject("wareHouse", wareHouse);
	}
	
	@RequestMapping("/goReConfirm")
	public ModelAndView goReConfirm(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {

		Employee employee = new Employee();
		WareHouse wareHouse = new WareHouse();
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getAppCZY())){
				employee = purchaseAppService.get(Employee.class, purchaseApp.getAppCZY());
			}
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getWhCode())){
				wareHouse = purchaseAppService.get(WareHouse.class, purchaseApp.getWhCode());
			}
			
			purchaseApp.setAppDate(new Date());
		}
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_reConfirm")
		.addObject("purchaseApp", purchaseApp).addObject("employee", employee)
		.addObject("czybh", this.getUserContext(request).getCzybh()).addObject("wareHouse", wareHouse);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {

		Employee employee = new Employee();
		WareHouse wareHouse = new WareHouse();
		if(StringUtils.isNotBlank(purchaseApp.getNo())){
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getAppCZY())){
				employee = purchaseAppService.get(Employee.class, purchaseApp.getAppCZY());
			}
			if(purchaseApp != null && StringUtils.isNotBlank(purchaseApp.getWhCode())){
				wareHouse = purchaseAppService.get(WareHouse.class, purchaseApp.getWhCode());
			}
			
			purchaseApp.setAppDate(new Date());
		}
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_view")
		.addObject("purchaseApp", purchaseApp).addObject("employee", employee)
		.addObject("czybh", this.getUserContext(request).getCzybh()).addObject("wareHouse", wareHouse);
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,PurchaseApp purchaseApp) throws Exception {
		
		Employee employee = new Employee();
		employee = purchaseAppService.get(Employee.class, this.getUserContext(request).getEmnum());
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_add")
		.addObject("purchaseApp", purchaseApp).addObject("employee", employee);
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) throws Exception {
		
		Item item = new Item();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		String itemCode= jsonObject.get("itemcode").toString();
		
		if(StringUtils.isNotBlank(itemCode)){
			item = purchaseAppService.get(Item.class, itemCode);
		}
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_addUpdate")
				.addObject("map", jsonObject).addObject("item", item).addObject("purchaseApp", purchaseApp);
	}
	
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) throws Exception {
		
		Item item = new Item();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		String itemCode= jsonObject.get("itemcode").toString();
		
		if(StringUtils.isNotBlank(itemCode)){
			item = purchaseAppService.get(Item.class, itemCode);
		}
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_addView")
				.addObject("map", jsonObject).addObject("item", item);
	}
	
	@RequestMapping("/goOrderAnalyse")
	public ModelAndView goOrderAnalyse(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) throws Exception {
		
		WareHouse wareHouse = new WareHouse();
		if(StringUtils.isNotBlank(purchaseApp.getWhCode())){
			wareHouse = purchaseAppService.get(WareHouse.class, purchaseApp.getWhCode());
		}
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, -30);
	    
		purchaseApp.setDateFrom(calendar.getTime());
		purchaseApp.setDateTo(new Date());
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_orderAnalyse")
		.addObject("purchaseApp", purchaseApp).addObject("wareHouse", wareHouse);
	}
	
	@RequestMapping("/goPurchaseAppCon")
	public ModelAndView goPurchaseAppCon(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) throws Exception {
		
		return new ModelAndView("admin/insales/purchaseApp/purchaseApp_con")
		.addObject("purchaseApp", purchaseApp);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) {
		logger.debug("采购新增");
		try {
			String detailJson = request.getParameter("detailJson");
			purchaseApp.setM_umState("A");
			purchaseApp.setDetailJson(detailJson);
			purchaseApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			purchaseApp.setStatus("0");
			Result result = this.purchaseAppService.doSave(purchaseApp);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) {
		logger.debug("采购编辑");
		try {
			String detailJson = request.getParameter("detailJson");
			purchaseApp.setM_umState("M");
			purchaseApp.setDetailJson(detailJson);
			purchaseApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			Result result = this.purchaseAppService.doSave(purchaseApp);
			if (result.isSuccess()) {
				ServletUtils.outPrintSuccess(request, response, "保存成功");
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doConfirm")
	public void doConfirm(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) {
		logger.debug("采购申请审核");
		try {
			if(StringUtils.isBlank(purchaseApp.getNo())){
				
				ServletUtils.outPrintFail(request, response, "审核失败");
				return;
			}
			
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(!"0".equals(purchaseApp.getStatus())){
				ServletUtils.outPrintFail(request, response, "审核失败,该申请单不是申请状态");
				return;
			}
			
			purchaseApp.setStatus("1");
			purchaseApp.setConfirmCZY(this.getUserContext(request).getCzybh());
			purchaseApp.setConfirmDate(new Date());
			purchaseApp.setLastUpdate(new Date());
			purchaseApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.purchaseAppService.update(purchaseApp);
			ServletUtils.outPrintSuccess(request, response, "审核通过");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doConfirmCancel")
	public void doConfirmCancel(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) {
		logger.debug("采购申请审核");
		try {
			if(StringUtils.isBlank(purchaseApp.getNo())){
				
				ServletUtils.outPrintFail(request, response, "审核失败");
				return;
			}
			
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(!"0".equals(purchaseApp.getStatus())){
				ServletUtils.outPrintFail(request, response, "审核失败,该申请单不是申请状态");
				return;
			}
			
			purchaseApp.setStatus("2");
			purchaseApp.setConfirmCZY(this.getUserContext(request).getCzybh());
			purchaseApp.setConfirmDate(new Date());
			purchaseApp.setLastUpdate(new Date());
			purchaseApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.purchaseAppService.update(purchaseApp);
			ServletUtils.outPrintSuccess(request, response, "审核通过");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doConfirmBack")
	public void doConfirmBack(HttpServletRequest request,
			HttpServletResponse response, PurchaseApp purchaseApp) {
		logger.debug("采购申请审核退回");
		try {
			if(StringUtils.isBlank(purchaseApp.getNo())){
				
				ServletUtils.outPrintFail(request, response, "审核退回失败");
				return;
			}
			
			boolean canReconfrim = purchaseAppService.checkCanReConfirm(purchaseApp.getNo());
			if(!canReconfrim){
				ServletUtils.outPrintFail(request, response, "存在打开或已确认的采购订单，无法进行反审核");
				return;
			}
			
			purchaseApp = purchaseAppService.get(PurchaseApp.class, purchaseApp.getNo());
			if(!"1".equals(purchaseApp.getStatus())){
				ServletUtils.outPrintFail(request, response, "审核退回失败,该申请单不是审核状态");
				return;
			}
			purchaseApp.setStatus("0");
			purchaseApp.setConfirmCZY(null);
			purchaseApp.setConfirmDate(null);
			purchaseApp.setLastUpdate(new Date());
			purchaseApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.purchaseAppService.update(purchaseApp);
			
			ServletUtils.outPrintSuccess(request, response, "审核退回成功");

		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "审核退回失败");
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getPurchItemData")
	@ResponseBody
	public Map<String, Object> getPurchItemData(HttpServletRequest request,HttpServletResponse response,
			PurchaseApp purchaseApp){
		logger.debug("ajax获取数据");  
		
		List<Map<String, Object>> list= this.purchaseAppService.getPurchItemData(purchaseApp);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		
		return new HashMap<String, Object>();
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			PurchaseApp purchaseApp){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		purchaseAppService.findPageBySql(page, purchaseApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"采购申请_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doConExcel")
	public void doConExcel(HttpServletRequest request ,HttpServletResponse response,
			PurchaseApp purchaseApp){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		purchaseAppService.findPurchConPageBySql(page, purchaseApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"采购跟踪_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/getItemSendQty")
	@ResponseBody
	public List<Map<String, Object>> getItemSendQty(HttpServletRequest request,HttpServletResponse response,
			PurchaseApp purchaseApp){
		logger.debug("ajax获取数据");  
		String detailJson = request.getParameter("detailJson");
		purchaseApp.setDetailJson(detailJson);
		List<Map<String, Object>> list= this.purchaseAppService.getItemSendQty(purchaseApp);
		if(list != null && list.size() >0){
			return list;
		}
		
		return new ArrayList<Map<String, Object>>();
	}
}
