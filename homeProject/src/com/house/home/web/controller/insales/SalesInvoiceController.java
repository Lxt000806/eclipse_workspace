package com.house.home.web.controller.insales;

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
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SaleCust;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.entity.insales.SalesInvoiceDetail;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.insales.SalesInvoiceService;

@Controller      
@RequestMapping("/admin/salesInvoice")
public class SalesInvoiceController extends BaseController{
	private static final Logger logger =LoggerFactory.getLogger(SalesInvoice.class);

	@Autowired
	private SalesInvoiceService salesInvoiceService;
	@Autowired
	private CzybmService czybmService;
	
	/** 
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
		@RequestMapping("/goJqGrid")
		@ResponseBody
		public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
				HttpServletResponse response ,SalesInvoice salesInvoice) throws Exception{
			//在操作员编码表中搜索材料权限--add by zb
			Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
			salesInvoice.setItemRight(czybm.getItemRight());
			Page<Map<String,Object>> page= this.newPageForJqGrid(request);
			salesInvoiceService.findPageBySql(page, salesInvoice, czybm);
			return new WebPage<Map<String,Object>>(page);
		}
		
	/**
	 * @Description:  销售订单管理分页查询
	 * @author	created by zb
	 * @date	2018-9-14--下午3:02:43
	 */
	@RequestMapping("/goSalesInvoiceJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getSalesInvoiceJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SalesInvoice salesInvoice) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		return new WebPage<Map<String,Object>>(page);
	}
	
	// 销售单明细分页查询
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,String no) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findDetailPageBySql(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description:  采购单明细分页查询
	 * @author	created by zb
	 * @date	2018-9-26--下午5:05:06
	 */
	@RequestMapping("/goPurDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getPurDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,String sino) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findPurDetailPageBySql(page, sino);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description:  仓库结存分页查询
	 * @author	created by zb
	 * @date	2018-9-19--上午9:25:38
	 */
	@RequestMapping("/goItemWHBalJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getItemWHBalJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,ItemWHBal itemWHBal) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findItemWHBalPageBySql(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
		
	/**
	 * @Description:  批次材料分页查询
	 * @author	created by zb
	 * @date	2018-9-27--下午6:02:02
	 * @param ibdno 批次编号
	 * @return
	 */
	@RequestMapping("/goItemBatchJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getItemBatchJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,String ibdno) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findItemBatchPageBySql(page, ibdno);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description:  销售明细查询——销售订单管理用
	 * @author	created by zb
	 * @date	2018-9-28--下午4:10:49
	 * @param sino 原销售单号
	 */
	@RequestMapping("/goSalesInvoiceDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getSalesInvoiceDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,String sino) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoiceDetailPageBySql(page, sino);
		return new WebPage<Map<String,Object>>(page);
	}
	
	// 明细分页查询
	@RequestMapping("/goDetailViewPageBySql")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailViewPageBySql(HttpServletRequest request ,
			HttpServletResponse response ,SalesInvoice salesInvoice) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findDetailViewPageBySql(page, salesInvoice);
		return new WebPage<Map<String,Object>>(page);
	}
	
		@RequestMapping("/goCode")
		public ModelAndView goCode(HttpServletRequest  request ,
					HttpServletResponse response,SalesInvoice salesInvoice){
			
			return new ModelAndView("admin/insales/salesInvoice/salesInvoice_code").addObject("salesInvoice",salesInvoice);
		}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_list");
	}
		
	// 新增客户
	@RequestMapping("/goCustSave")
	public ModelAndView goCustSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_custSave");
	}
	
	// 开销售单
	@RequestMapping("/goSalesOrder")
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
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", salesInvoice)
			.addObject("czybm",czybm);
	}
	
	// 退回
	@RequestMapping("/goReback")
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
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
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
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 审核
	@RequestMapping("/goReview")
	public ModelAndView goReview(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "C");
		map.put("isEditDiscPercentage", salesInvoice.getIsEditDiscPercentage());
		map.put("chageStatus", 0);
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 反审核
	@RequestMapping("/goUnreview")
	public ModelAndView goUnreview(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "B");
		map.put("isEditDiscPercentage", salesInvoice.getIsEditDiscPercentage());
		map.put("chageStatus", 0);
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 付款
	@RequestMapping("/goPay")
	public ModelAndView goPay(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "F");
		map.put("isEditDiscPercentage", salesInvoice.getIsEditDiscPercentage());
		map.put("chageStatus", salesInvoice.getChageStatus());
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 发货
	@RequestMapping("/goSendGoods")
	public ModelAndView goSendGoods(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		map.put("m_umState", "D");
		map.put("isEditDiscPercentage", salesInvoice.getIsEditDiscPercentage());
		map.put("chageStatus", 0);
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 发货授权
	@RequestMapping("/goSendGoodsPermit")
	public ModelAndView goSendGoodsPermit(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_authorize")
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
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_salesOrder")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 明细查询
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_detailView");
	}
	
	// 销售单打印
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request,
			HttpServletResponse response, SalesInvoice salesInvoice) throws Exception {
		Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		salesInvoiceService.findSalesInvoicePageBySql(page, salesInvoice);
		Map<String, Object> map = page.getResult().get(0);
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_print")
			.addObject("salesInvoice", map)
			.addObject("czybm",czybm);
	}
	
	// 销售明细——增加
	@RequestMapping("/goDetailSave")
	public ModelAndView goDetailSave(HttpServletRequest request, HttpServletResponse response, 
			String itCodes, String m_umState, String itemType1, String m_sType, String costRight) throws Exception {
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_detailSave")
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
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_detailSave")
			.addObject("itCodes", itCodes)
			.addObject("m_umState", m_umState)
			.addObject("itemType1", itemType1)
			.addObject("m_sType", m_sType)
			.addObject("salesInvoiceDetail", salesInvoiceDetail)
			.addObject("costRight", costRight);
	}
	
	// 跳转批次导入页面
	@RequestMapping("/goBatchImport")
	public ModelAndView goBatchImport(HttpServletRequest request, HttpServletResponse response, 
			String itemType1, String czybhCode, String itCodes) throws Exception {
		Map<String, Object> map = salesInvoiceService.getItemBatchHeader(itemType1, czybhCode);
		return new ModelAndView("admin/insales/salesInvoice/salesInvoice_batchImport")
			.addObject("czybhCode", czybhCode)
			.addObject("map", map)
			.addObject("itemType1", itemType1)
			.addObject("itCodes", itCodes);
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
	
	// 销售客户新增
	@RequestMapping("/doCustSave")
	public void doCustSave(HttpServletRequest request,HttpServletResponse response,
			SaleCust saleCust){
		logger.debug("客户新增");
		try {
			saleCust.setMobile1(saleCust.getMobile1().replaceAll("\\s*", ""));
			saleCust.setMobile2(saleCust.getMobile2().replaceAll("\\s*", ""));
			if (StringUtils.isNotBlank(saleCust.getRemDate1())) {
				// 将字符串转换成date，再转换成time，再由Long转换成string
				Date strDate = DateUtil.parse(saleCust.getRemDate1(), "yyyy-MM-dd");
				saleCust.setRemDate1(Long.toString(strDate.getTime()));
			} else {
				saleCust.setRemDate1("18991230");
			}
			if (StringUtils.isNotBlank(saleCust.getRemDate2())) {
				saleCust.setRemDate2(Long.toString(DateUtil.parse(saleCust.getRemDate2(), "yyyy-MM-dd").getTime()));
			} else {
				saleCust.setRemDate2("18991230");
			}
			saleCust.setLastUpdatedBy(this.getUserContext(request).getCzybh());

			Result result = this.salesInvoiceService.doCustSave(saleCust);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	//  销售单存储过程
	@RequestMapping("/doSalesOrder")
	public void doSalesOrder(HttpServletRequest request,HttpServletResponse response,
			SalesInvoice salesInvoice){
		logger.debug("销售单保存数据");
		try {
			if ("A".equals(salesInvoice.getM_umState()) || "R".equals(salesInvoice.getM_umState())) {
				salesInvoice.setNo("*");
			}
			salesInvoice.setExpired("F");
			salesInvoice.setLastUpdatedBy(this.getUserContext(request).getCzybh());

			Result result = this.salesInvoiceService.doSalesOrder(salesInvoice);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}

	
	// 发货授权保存
	@RequestMapping("/doAuthorizeSave")
	@ResponseBody
	public void doAuthorizeSave(HttpServletRequest request, HttpServletResponse response, 
			SalesInvoice salesInvoice) {
		try {
			SalesInvoice si = this.salesInvoiceService.get(SalesInvoice.class, salesInvoice.getNo());
			if(null == si){
				ServletUtils.outPrintFail(request, response, "数据库无该数据");
			}else{
				si.setLastUpdate(new Date());
				si.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				si.setIsAuthorized(salesInvoice.getIsAuthorized());
				si.setAuthorizer(salesInvoice.getAuthorizer());
				this.salesInvoiceService.update(si);
				ServletUtils.outPrintSuccess(request, response, "编辑成功");
			}
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
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
	
	// 根据操作员编号获取员工信息
	@RequestMapping("/getEmpDescrByCZYBH")
	@ResponseBody
	public JSONObject getEmpDescrByCZYBH(HttpServletRequest request, HttpServletResponse response, 
			String czybh) {
		Employee employee = salesInvoiceService.getEmpDescrByCZYBH(czybh);
		return this.out(employee, true);
	}	
	
	/**
	 * 根据id查询SalesInvoice详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSalesInvoice")
	@ResponseBody
	public JSONObject getSalesInvoice(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SalesInvoice salesInvoice =salesInvoiceService.get(SalesInvoice.class, id);
		if(salesInvoice == null){
			return this.out("系统中不存在code="+id+"的材料信息", false);
		}
		return this.out(salesInvoice, true);
	}
	
	// 是否授权查询
	@RequestMapping("/isAuthorized")
	@ResponseBody
	public boolean isAuthorized(String no) {
		boolean result = salesInvoiceService.isAuthorized(no);
		return result;
	}
	
	/**
	 * @Description:  根据两个编码获取数量
	 * @author	created by zb
	 * @date	2018-9-19--下午2:52:39
	 * @param itCode 材料编码
	 * @param whCode 仓库编码
	 * @return
	 */
	@RequestMapping("/getQtyNow")
	@ResponseBody
	public JSONObject getQtyNow(HttpServletRequest request, HttpServletResponse response, 
			String itCode, String whCode) {
		Map<String, Object> map = salesInvoiceService.getQtyNow(itCode, whCode);
		return this.out(map, true);
	}
	
	/**
	 * @Description:  根据材料编号获取材料信息
	 * @author	created by zb
	 * @date	2018-9-28--上午9:54:07
	 */
	@RequestMapping("/getItemInfo")
	@ResponseBody
	public JSONObject getItemInfo(HttpServletRequest request, HttpServletResponse response, 
			String itCode) {
		Map<String, Object> map = salesInvoiceService.getItemInfo(itCode);
		return this.out(map, true);
	}
	
	// 根据no获取采购记录
	@RequestMapping("/getPurchaseCount")
	@ResponseBody
	public Boolean getPurchaseCount(HttpServletRequest request, HttpServletResponse response, 
			String no) {
		Boolean b = salesInvoiceService.getPurchaseCount(no);
		return b;
	}
	
	
}
