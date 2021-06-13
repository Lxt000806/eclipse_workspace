package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.design.Customer;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemBalAdjHeader;
import com.house.home.entity.insales.ItemTransferHeader;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.ItemChg;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.insales.ItemTransferHeaderService;
import com.house.home.service.insales.WareHouseService;
import com.house.home.serviceImpl.query.ItemSzQueryServiceImpl;

@Controller
@RequestMapping("/admin/itemTransferHeader")
public class ItemTransferHeaderController extends BaseController{
	@Autowired
	private ItemTransferHeaderService itemTransferHeaderService; 
	@Autowired
	private ItemService itemService;
	@Autowired 
	private EmployeeService employeeService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ItemType2Service itemType2Service;
	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,HttpServletResponse response
			,ItemTransferHeader itemTransferHeader) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemTransferHeaderService.findPageBySql(page,itemTransferHeader);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/getDetailBySql")
	@ResponseBody
	public WebPage<Map<String,Object>> getDetailBySql(HttpServletRequest request,HttpServletResponse response
			,ItemTransferHeader itemTransferHeader) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemTransferHeaderService.findDetailPageBySql(page,itemTransferHeader);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ‘按供应商汇总’页签，参考仓库调整。
	 * @author	created by zb
	 * @date	2019-4-18--下午4:44:57
	 */
	@RequestMapping("/goOrderBySupplJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getOrderBySupplJqGrid(HttpServletRequest request, HttpServletResponse response,
			ItemTransferHeader itemTransferHeader) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemTransferHeaderService.findOrderBySupplPageBySql(page, itemTransferHeader);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response
			,ItemTransferHeader itemTransferHeader) throws Exception {
		
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_list")
		.addObject("itemTransferHeader",itemTransferHeader);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response
			,ItemTransferHeader itemTransferHeader) throws Exception {
		Employee employee=null;
		String costRight=this.getUserContext(request).getCostRight();
		itemTransferHeader.setStatus("1");
		itemTransferHeader.setAppDate(new Date());
		itemTransferHeader.setDate(new Date());
		itemTransferHeader.setAppEmp(this.getUserContext(request).getCzybh());
		employee=employeeService.get(Employee.class, this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_save")
		.addObject("itemTransferHeader",itemTransferHeader).addObject("employee",employee)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("costRight",costRight );
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response
			,String no) throws Exception {
		Employee employee=null;
		WareHouse fromWHouse=null;
		WareHouse toWHouse=null;
		String costRight=this.getUserContext(request).getCostRight();

		ItemTransferHeader itemTransferHeader=null;
		itemTransferHeader=itemService.get(ItemTransferHeader.class, no);
		if(itemTransferHeader!=null){
			fromWHouse=wareHouseService.get(WareHouse.class, itemTransferHeader.getFromWHCode());
			toWHouse = wareHouseService.get(WareHouse.class, itemTransferHeader.getToWHCode());
		}
		if(StringUtils.isNotBlank(itemTransferHeader.getAppEmp())){
			employee=employeeService.get(Employee.class, itemTransferHeader.getAppEmp());
		}
		
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_update")
		.addObject("itemTransferHeader",itemTransferHeader).addObject("employee",employee)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("fromWHouse",fromWHouse )
		.addObject("costRight",costRight )
		.addObject("toWHouse",toWHouse );
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response
			,String no) throws Exception {
		Employee employee=null;
		WareHouse fromWHouse=null;
		WareHouse toWHouse=null;
		String costRight=this.getUserContext(request).getCostRight();
		ItemTransferHeader itemTransferHeader=null;
		
		itemTransferHeader=itemService.get(ItemTransferHeader.class, no);
		if(itemTransferHeader!=null){
			fromWHouse=wareHouseService.get(WareHouse.class, itemTransferHeader.getFromWHCode());
			toWHouse = wareHouseService.get(WareHouse.class, itemTransferHeader.getToWHCode());
		}
		if(StringUtils.isNotBlank(itemTransferHeader.getAppEmp())){
			employee=employeeService.get(Employee.class, itemTransferHeader.getAppEmp());
		}
		if(StringUtils.isNotBlank(itemTransferHeader.getConfirmEmp())){
			Employee e = employeeService.get(Employee.class, itemTransferHeader.getConfirmEmp());
			if (null != e) { //修改emp为‘1’时的错误 modify by zb on 20190818
				itemTransferHeader.setConfirmCZYDescr(e.getNameChi());
			} else {
				itemTransferHeader.setConfirmCZYDescr("未找到员工姓名");
			}
		}
		
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_view")
		.addObject("itemTransferHeader",itemTransferHeader).addObject("employee",employee)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("fromWHouse",fromWHouse )
		.addObject("costRight",costRight )
		.addObject("toWHouse",toWHouse );
	}
	
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response
			,String no) throws Exception {
		Employee employee=null;
		WareHouse fromWHouse=null;
		WareHouse toWHouse=null;
		String costRight=this.getUserContext(request).getCostRight();
		ItemTransferHeader itemTransferHeader=null;
		itemTransferHeader=itemService.get(ItemTransferHeader.class, no);
		if(itemTransferHeader!=null){
			fromWHouse=wareHouseService.get(WareHouse.class, itemTransferHeader.getFromWHCode());
			toWHouse = wareHouseService.get(WareHouse.class, itemTransferHeader.getToWHCode());
		}
		if(StringUtils.isNotBlank(itemTransferHeader.getAppEmp())){
			employee=employeeService.get(Employee.class, itemTransferHeader.getAppEmp());
		}
		itemTransferHeader.setConfirmEmp(this.getUserContext(request).getCzybh());
		itemTransferHeader.setConfirmDate(new Date());
		itemTransferHeader.setConfirmCZYDescr(this.getUserContext(request).getZwxm());
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_check")
		.addObject("itemTransferHeader",itemTransferHeader).addObject("employee",employee)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("fromWHouse",fromWHouse )
		.addObject("costRight",costRight )
		.addObject("toWHouse",toWHouse );
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response
			,ItemTransferHeader itemTransferHeader,String noRepeat,String itCodeArr) throws Exception {
		Employee employee=null;
		itemTransferHeader.setStatus("1");
		itemTransferHeader.setAppDate(new Date());
		itemTransferHeader.setDate(new Date());
		itemTransferHeader.setAppEmp(this.getUserContext(request).getCzybh());
		employee=employeeService.get(Employee.class, this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_add")
		.addObject("itemTransferHeader",itemTransferHeader).addObject("employee",employee)
		.addObject("czybh",this.getUserContext(request).getCzybh()).addObject("noRepeat", noRepeat)
		.addObject("itCodeArr", itCodeArr)
		.addObject("costRight", this.getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request, HttpServletResponse response
			, String fromWHCode,String toWHCode,String trfQty ,String itCode ,String detailRemarks
			,String noRepeat,String itCodeArr) throws Exception {
		Item item =null;
		ItemType2 itemType2=null;
		if(StringUtils.isNotBlank(itCode)){
			item=itemService.get(Item.class, itCode);
		}
		if(item.getItemType2()!=null){
			itemType2=itemType2Service.get(ItemType2.class, item.getItemType2());
		}
		
		ItemTransferHeader itemTransferHeader =new ItemTransferHeader();
		itemTransferHeader.setItCode(itCode);
		itemTransferHeader.setFromWHCode(fromWHCode);
		itemTransferHeader.setToWHCode(toWHCode);
		itemTransferHeader.setTrfQty(trfQty);
		itemTransferHeader.setDetailRemarks(detailRemarks);
		
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_addUpdate")
		.addObject("itemTransferHeader", itemTransferHeader)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("item", item).addObject("itemType2", itemType2)
		.addObject("noRepeat", noRepeat)
		.addObject("itCodeArr", itCodeArr).addObject("costRight", this.getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request, HttpServletResponse response
			, String fromWHCode,String toWHCode,String trfQty ,String itCode ,String detailRemarks
			,String fromQty,String toQty) throws Exception {//做到这
		Item item =null;
		ItemType2 itemType2=null;
		if(StringUtils.isNotBlank(itCode)){
			item=itemService.get(Item.class, itCode);
		}
		if(item.getItemType2()!=null){
			itemType2=itemType2Service.get(ItemType2.class, item.getItemType2());
		}
		
		ItemTransferHeader itemTransferHeader =new ItemTransferHeader();
		itemTransferHeader.setItCode(itCode);
		itemTransferHeader.setFromWHCode(fromWHCode);
		itemTransferHeader.setToWHCode(toWHCode);
		itemTransferHeader.setTrfQty(trfQty);
		itemTransferHeader.setDetailRemarks(detailRemarks);
		
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_addView")
		.addObject("itemTransferHeader", itemTransferHeader)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("item", item).addObject("itemType2", itemType2)
		.addObject("costRight", this.getUserContext(request).getCostRight())
		.addObject("toQty",toQty)
		.addObject("fromQty",fromQty);
	}
	
	@RequestMapping("/goFastAdd")
	public ModelAndView goFastAdd(HttpServletRequest request, HttpServletResponse response
			,ItemTransferHeader itemTransferHeader,String noRepeat,String itCodeArr) throws Exception {
		Employee employee=null;
		itemTransferHeader.setStatus("1");
		itemTransferHeader.setAppDate(new Date());
		itemTransferHeader.setDate(new Date());
		itemTransferHeader.setAppEmp(this.getUserContext(request).getCzybh());
		employee=employeeService.get(Employee.class, this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/insales/itemTransferHeader/itemTransferHeader_fastAdd")
		.addObject("itemTransferHeader",itemTransferHeader).addObject("employee",employee)
		.addObject("czybh",this.getUserContext(request).getCzybh()).addObject("noRepeat", noRepeat)
		.addObject("itCodeArr", itCodeArr).addObject("costRight", this.getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,
			ItemTransferHeader itemTransferHeader){
		logger.debug("仓库转移新增");
		try {
			itemTransferHeader.setM_umState("A");
			String detailJson = request.getParameter("detailJson");
			itemTransferHeader.setDetailJson(detailJson);
			itemTransferHeader.setLastUpdate(new Date());
			itemTransferHeader.setUseGIT("0");
			itemTransferHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result =this.itemTransferHeaderService.doSaveItemTransfer(itemTransferHeader) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "仓库转移新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,
			ItemTransferHeader itemTransferHeader){
		logger.debug("仓库转移修改");
		try {
			itemTransferHeader.setM_umState("M");
			String detailJson = request.getParameter("detailJson");
			itemTransferHeader.setDetailJson(detailJson);
			itemTransferHeader.setLastUpdate(new Date());
			itemTransferHeader.setUseGIT("0");
			itemTransferHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result =this.itemTransferHeaderService.doUpdateItemTransfer(itemTransferHeader) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "仓库转移修改失败");
		}
	}
	
	@RequestMapping("/doSaveCheck")
	public void doSaveCheck(HttpServletRequest request,HttpServletResponse response,
			ItemTransferHeader itemTransferHeader){
		logger.debug("仓库转移审核");
		try {
			itemTransferHeader.setM_umState("C");
			String detailJson = request.getParameter("detailJson");
			itemTransferHeader.setDetailJson(detailJson);
			itemTransferHeader.setLastUpdate(new Date());
			itemTransferHeader.setStatus("2");
			itemTransferHeader.setUseGIT("0");
			itemTransferHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result =this.itemTransferHeaderService.doSaveCheckItemTransfer(itemTransferHeader) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "仓库转移审核失败");
		}
	}
	
	@RequestMapping("/doSaveCancelCheck")
	public void doSaveCancelCheck(HttpServletRequest request,HttpServletResponse response,
			ItemTransferHeader itemTransferHeader){
		logger.debug("仓库转移审核");
		try {
			itemTransferHeader.setM_umState("C");
			String detailJson = request.getParameter("detailJson");
			itemTransferHeader.setDetailJson(detailJson);
			itemTransferHeader.setLastUpdate(new Date());
			itemTransferHeader.setStatus("3");
			itemTransferHeader.setUseGIT("0");
			itemTransferHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result =this.itemTransferHeaderService.doSaveCheckItemTransfer(itemTransferHeader) ;
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "仓库转移审核取消失败");
		}
	}
	
	@RequestMapping("/getQty")
	@ResponseBody 	
	public String[] getQty(HttpServletRequest request,HttpServletResponse response,String fromWHCode
			,String toWHCode,String itCode){
		logger.debug("ajax获取数据"); 
		Map<String , Object> fromQtyMap=itemTransferHeaderService.getFromQty(itCode, fromWHCode);
		Map<String , Object> toQtyMap=itemTransferHeaderService.getFromQty(itCode, toWHCode);
		String fromQty="";
		String AvgCost="";
		if(fromQtyMap!=null){
			fromQty=itemTransferHeaderService.getFromQty(itCode, fromWHCode).get("QtyCal").toString();
		}else{
			fromQty="0";
		}
		if(fromQtyMap!=null){
			AvgCost=itemTransferHeaderService.getFromQty(itCode, fromWHCode).get("AvgCost").toString();
		}else {
			AvgCost="0";
		}
		String toQty=itemTransferHeaderService.getToQty(itCode, toWHCode);
		String posiQty=itemTransferHeaderService.getPostQty(itCode, fromWHCode);
		String[] str={fromQty==null?"0":fromQty,AvgCost==null?"0":AvgCost,toQty,posiQty};
		return str;
	
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			ItemTransferHeader itemTransferHeader){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		itemTransferHeaderService.findPageBySql(page, itemTransferHeader);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"仓库转移明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	
	
}
