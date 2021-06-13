package com.house.home.web.controller.supplier;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.entity.insales.SalesInvoiceDetail;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.insales.SalesInvoiceService;


@Controller      
@RequestMapping("/admin/salesOutWarehouse")
public class SalesOutWarehouseController extends BaseController{
	@Autowired
	private SalesInvoiceService salesInvoiceService;
	@Autowired
	private CzybmService czybmService;
	

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_list");
	}	
	
	
	// 开销售单
	@RequestMapping("/goSave")
	public ModelAndView goSalesOrder(HttpServletRequest request,
			HttpServletResponse response, String isEditDiscPercentage, String chageStatus) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Map<String, Object> salesInvoice = new HashMap<String, Object>();
		salesInvoice.put("type", "S");
		salesInvoice.put("m_umState","A");
		salesInvoice.put("status", "APLY");
		salesInvoice.put("date",new Date());
		salesInvoice.put("getitemdate",new Date());
		salesInvoice.put("isEditDiscPercentage", isEditDiscPercentage);
		salesInvoice.put("chageStatus", chageStatus);
		return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_salesOrder")
			.addObject("salesInvoice", salesInvoice)
			.addObject("czybm",czybm);
	}
	
	
	// 退回
	@RequestMapping("/goReturn")
	public ModelAndView goReback(HttpServletRequest request,
			HttpServletResponse response, String isEditDiscPercentage, String chageStatus) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Map<String, Object> salesInvoice = new HashMap<String, Object>();
		salesInvoice.put("type", "R");
		salesInvoice.put("m_umState","R");
		salesInvoice.put("status", "APLY");
		salesInvoice.put("date",new Date());
		salesInvoice.put("getitemdate",new Date());
		salesInvoice.put("isEditDiscPercentage", isEditDiscPercentage);
		salesInvoice.put("chageStatus", chageStatus);
		return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_salesOrder")
			.addObject("salesInvoice", salesInvoice)
			.addObject("czybm",czybm);
	}
	
	// 编辑
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "M");
		map.put("isEditDiscPercentage", salesInvoice.getIsEditDiscPercentage());
		map.put("chageStatus", salesInvoice.getChageStatus());
		return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 审核
	@RequestMapping("/goConfirm")
	public ModelAndView goReview(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "C");
		map.put("isEditDiscPercentage", salesInvoice.getIsEditDiscPercentage());
		map.put("chageStatus", 0);
		return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 查看
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "V");
		map.put("chageStatus", 0);
		return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 销售明细——增加
		@RequestMapping("/goDetailSave")
		public ModelAndView goDetailSave(HttpServletRequest request, HttpServletResponse response, 
				String itCodes, String m_umState, String itemType1, String m_sType, String costRight) throws Exception {
			return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_detailSave")
				.addObject("itCodes", itCodes)
				.addObject("m_umState", m_umState)
				.addObject("itemType1", itemType1)
				.addObject("m_sType", m_sType)
				.addObject("costRight", costRight);
		}
		
		// 销售明细——编辑
		@RequestMapping("/goDetailUpdate")
		public ModelAndView goDetailUpdate(HttpServletRequest request, HttpServletResponse response, 
				String itCodes, String m_umState, String itemType1, String m_sType, SalesInvoiceDetail salesInvoiceDetail, String costRight) throws Exception {
			return new ModelAndView("admin/supplier/salesOutWarehouse/salesOutWarehouse_detailSave")
				.addObject("itCodes", itCodes)
				.addObject("m_umState", m_umState)
				.addObject("itemType1", itemType1)
				.addObject("m_sType", m_sType)
				.addObject("salesInvoiceDetail", salesInvoiceDetail)
				.addObject("costRight", costRight);
		}
		
		// 销售退回明细——增加
		@RequestMapping("/goRebackDetailSave")
		public ModelAndView goRebackDetailSave(HttpServletRequest request, HttpServletResponse response, 
				String itCodes, String m_umState, String itemType1, String m_sType,String sino) throws Exception {
			return new ModelAndView("admin/insales/salesInvoice/salesInvoice_rebackDetailSave")
				.addObject("sino", sino)
				.addObject("itCodes", itCodes)
				.addObject("m_umState", m_umState)
				.addObject("itemType1", itemType1)
				.addObject("m_sType", m_sType);
		}
        
		// 导出
		@RequestMapping("/doExcel")
		public void doExcel(HttpServletRequest request, 
				HttpServletResponse response, SalesInvoice salesInvoice){
			Page<Map<String,Object>> page = this.newPage(request);
			page.setPageSize(-1);
			salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"销售订单-"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		}
	
}



