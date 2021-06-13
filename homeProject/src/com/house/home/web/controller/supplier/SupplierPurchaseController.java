package com.house.home.web.controller.supplier;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.commons.utils.excel.ExportExcel;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseDetail;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.PurchaseDetailService;
import com.house.home.service.insales.PurchaseService;
import com.house.home.service.insales.SupplierService;
import com.house.home.service.insales.WareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller      
@RequestMapping("/admin/supplierPurchase")
public class SupplierPurchaseController extends BaseController{
	
	private static final Logger logger =LoggerFactory.getLogger(SupplierPurchaseController.class);
	
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private PurchaseDetailService purchaseDetailService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private SupplierService supplierService;
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
			HttpServletResponse response ,Purchase purchase) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		//purchase.setSupplier(this.getUserContext(request).getEmnum());
		purchase.setCzybh(this.getUserContext(request).getCzybh());
		purchaseService.findPageBySql1(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPurchJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getPurchJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PurchaseDetail purchaseDetail) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		
		purchaseDetailService.findPurchPageBySql(page, purchaseDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 *Purchase列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response , Purchase purchase) throws Exception{
		if(purchase.getStatus()==null){
		purchase.setStatus("OPEN");
		purchase.setCzybh(this.getUserContext(request).getCzybh());
		}
		return new ModelAndView("admin/supplier/purchase/purchase_list")
		.addObject("purchase",purchase)
		.addObject("czybh",this.getUserContext(request).getCzybh());
	}
	
	
	/**
	 *新增明细 
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("新增采购明细开始");  
		//PurchaseDetail purchaseDetail=null;
		purchaseDetail=new PurchaseDetail();
		//purchaseDetail.setItcode("020100");
		String detailJson = request.getParameter("detailJson");
		purchaseDetail.setDetailJson(detailJson);
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/supplier/purchase/purchase_add")
		.addObject("purchaseDetail",purchaseDetail);
		
	}
	
	/**
	 *采购退回确认
	 */
	@RequestMapping("/goPurchReturnCheckOut")
	public ModelAndView goPurchReturnChackOut(HttpServletRequest request,HttpServletResponse response,
			String id){
		logger.debug("跳转到采购退回确认");
		Purchase purchase=null;
		Employee employee= null;
		WareHouse wareHouse = null;
		Supplier supplier = null;
		purchase = purchaseService.get(Purchase.class,id);
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		wareHouse = wareHouseService.get(WareHouse.class, purchase.getWhcode());
		supplier = supplierService.get(Supplier.class,purchase.getSupplier());
		purchase.setStatus("CONFIRMED");
		purchase.setSupDescr(supplier.getDescr());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
		return new ModelAndView("admin/supplier/purchase/purchase_purchReturnCheckOut")
		.addObject("purchase",purchase).addObject("czybh",this.getUserContext(request).getCzybh());
	}

	/**
	 *采购退回页面里的查看 
	 *
	 */
	@RequestMapping("/goPurchView")
	public ModelAndView goPurchView(HttpServletRequest request,HttpServletResponse response,
			Integer id){
		logger.debug("跳转到查看页面");
		PurchaseDetail purchaseDetail=null;
		Item item= null;
		purchaseDetail= purchaseDetailService.get(PurchaseDetail.class, id);
		item = itemService.get(Item.class,purchaseDetail.getItcode());
		purchaseDetail.setItdescr(item.getDescr());
		String detailJson = request.getParameter("detailJson");
		purchaseDetail.setDetailJson(detailJson);
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/supplier/purchase/purchase_view1")
		.addObject("purchaseDetail",purchaseDetail);
	}
	/** 
	 *查看页面
	 *@return 
	 *
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,
			String id){
		logger.debug("跳转到查看页面");
		Supplier supplier=null;
		Purchase purchase=null;
		Employee employee= null;
		WareHouse wareHouse = null;
		purchase = purchaseService.get(Purchase.class,id);
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		wareHouse = wareHouseService.get(WareHouse.class,purchase.getWhcode()==null?"":purchase.getWhcode());
		supplier = supplierService.get(Supplier.class,purchase.getSupplier());	
		purchase.setSupDescr(supplier.getDescr());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());

		return new ModelAndView("admin/supplier/purchase/purchase_view")
		.addObject("purchase",purchase);
	}
	
	/**
	 * 部分到货
	 * @return
	 * 
	 */
	@RequestMapping("/goPartArrive")
	public ModelAndView goPartArrive(HttpServletRequest request,HttpServletResponse response,
			String id){
			logger.debug("跳转到部分到货页面");
			Supplier supplier=null;
			Purchase purchase=null;
			Employee employee= null;
			WareHouse wareHouse = null;
			purchase = purchaseService.get(Purchase.class,id);
			purchase.setArriveDate(purchase.getArriveDate()==null?new Date():purchase.getArriveDate());
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
			wareHouse = wareHouseService.get(WareHouse.class, purchase.getWhcode());
			supplier = supplierService.get(Supplier.class,purchase.getSupplier());
			purchase.setSupDescr(supplier.getDescr());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
			purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
			return new ModelAndView("admin/supplier/purchase/purchase_partarrive")
			.addObject("purchase",purchase).addObject("czybh",this.getUserContext(request).getCzybh());
	}
	/**
	 * 部分到货
	 * @return
	 * 
	 */
	@RequestMapping("/goAllArrive")
	public ModelAndView goAllArrive(HttpServletRequest request,HttpServletResponse response,
			String id){
			logger.debug("跳转到全部到货页面");
			Purchase purchase=null;
			Employee employee= null;
			WareHouse wareHouse = null;
			Supplier supplier = null;
			purchase = purchaseService.get(Purchase.class,id);
			purchase.setArriveDate(purchase.getArriveDate()==null?new Date():purchase.getArriveDate());
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
			wareHouse = wareHouseService.get(WareHouse.class, purchase.getWhcode());
			supplier = supplierService.get(Supplier.class,purchase.getSupplier());
			purchase.setSupDescr(supplier.getDescr());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
			purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
			return new ModelAndView("admin/supplier/purchase/purchase_allarrive")
			.addObject("purchase",purchase).addObject("czybh",this.getUserContext(request).getCzybh());
	}

	/**
	 *部分到货 保存
	 *
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("部分到货开始");
		try {
			purchase.setDate(new Date());
			purchase.setConfirmDate(new Date());
			purchase.setConfirmCZY(this.getUserContext(request).getCzybh());
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			purchase.setExpired("F");
			String detailJson = request.getParameter("detailJson");
			purchase.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "无采购明细,请选择采购明细");
				return;
			}
			Result result = this.purchaseService.doPurchaseDetail(purchase);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
			}catch (Exception e) {
				ServletUtils.outPrintFail(request, response, "部分到货修改失败");
			}
		}

	/**
	 *采购退回确认 
	 */
	@RequestMapping("/doReturnCheckOut")
	public void doReturnCheckOut(HttpServletRequest request ,HttpServletResponse response ,
			Purchase purchase ){
			logger.debug("采购退回确认开始");
			try {
				purchase.setStatus("CONFIRMED");
				purchase.setDate(new Date());
				purchase.setType("R");
				purchase.setTemp(4);
				purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				String detailJson = request.getParameter("detailJson");
				purchase.setDetailJson(detailJson);
				if(StringUtils.isBlank(detailJson)){
					ServletUtils.outPrintFail(request, response, "无采购明细,请选择采购明细");
					return;
				}
				Result result = this.purchaseService.doPurchReturnCheckOut(purchase);
					if (result.isSuccess()){
						ServletUtils.outPrintSuccess(request, response,"保存成功");
					}else{
						ServletUtils.outPrintFail(request, response,result.getInfo());
					}
				}catch (Exception e) {
					ServletUtils.outPrintFail(request, response, "采购退回确认失败");
				}
		}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Purchase purchase){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		purchaseService.findPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"采购明细单_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping(value = "/export")
	public void exExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtil.isEmpty(exportData)){
			logger.error("导出查询结果失败");
			return ;
		}
		JSONObject dataObj = JSONObject.parseObject(exportData);
		JSONArray colHeader = dataObj.getJSONArray("colHeader");
		if(colHeader != null&& !colHeader.isEmpty()){
			List<String> colNames = Lists.newArrayList();
			List<String> colLabels = Lists.newArrayList();
			for(int i = 0; i < colHeader.size(); i ++){
				JSONObject obj = colHeader.getJSONObject(i);
				colNames.add(obj.getString("colName"));
				colLabels.add(obj.getString("colLabel"));
			}
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			page.setPageSize(100000000);
			PurchaseDetail purchaseDetail = new PurchaseDetail();
			purchaseDetail.setPuno(dataObj.getString("puno"));
			purchaseDetailService.findPurchPageBySql(page, purchaseDetail);
			String exportTitle = dataObj.getString("exportTitle");
			exportTitle = StringUtils.isEmpty(exportTitle)?"导出数据":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("导出查询结果数据至excel文件出错："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
