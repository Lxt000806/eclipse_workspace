package com.house.home.web.controller.insales;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Item;
import com.house.home.entity.basic.ItemType2;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemAppDetail;
import com.house.home.entity.insales.PurchArr;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseApp;
import com.house.home.entity.insales.PurchaseDelay;
import com.house.home.entity.insales.PurchaseDetail;
import com.house.home.entity.insales.PurchaseFee;
import com.house.home.entity.insales.SalesInvoiceDetail;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.ItemService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.PurchaseDelayService;
import com.house.home.service.insales.PurchaseDetailService;
import com.house.home.service.insales.PurchaseService;
import com.house.home.service.insales.SupplierService;
import com.house.home.service.insales.WareHouseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller      
@RequestMapping("/admin/purchase")
public class PurchaseController extends BaseController{
	
	private static final Logger logger =LoggerFactory.getLogger(PurchaseController.class);
	
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
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PurchaseDelayService purchaseDelayService;
	
	/**
	 * 采购单结算查看
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchaseJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPurchaseJqGrid(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseService.findPageBySql_gysjs(page, purchase);
		
		return new WebPage<Map<String,Object>>(page);
	}
	 
	/**
	 * 采购单结算查看-该供应商的所有结算单
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchaseJqGridAll")
	@ResponseBody
	public WebPage<Map<String,Object>> goPurchaseJqGridAll(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchase.setSupplier(this.getUserContext(request).getEmnum());
		purchase.setStatus("CONFIRMED");
		purchaseService.findPageBySql_gysjsAll(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 采购明细jqgrid
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetail(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseService.getDetailByNo(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 采购单结算新增jqgrid
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchaseExistsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPurchaseExistsJqGrid(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseService.findPageBySql_gysjsAdd(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
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
		UserContext uc = getUserContext(request);
		String itemType1= uc.getItemRight();
		purchase.setItemRight(itemType1);
		purchaseService.findPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Purchase purchase) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		purchase.setItemRight(this.getUserContext(request).getItemRight());
		purchaseService.findDetailPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/goPurchJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getPurchJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PurchaseDetail purchaseDetail) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		Purchase purchase =new Purchase();
		purchase=purchaseService.get(Purchase.class, purchaseDetail.getPuno());
		if(purchase!=null && (purchase.getOldPUNo()==null ||"".equals(purchase.getOldPUNo())) 
															&& "R".equals(purchase.getType())){
			purchaseDetail.setM_umState("R");
		}
		purchaseDetailService.findPurchPageBySql(page, purchaseDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPurchGZJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getPurchGZJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Purchase purchase) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		purchaseService.findPurchGZPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getAppItemPage")
	@ResponseBody
	public WebPage<Map<String,Object>> getAppItemPage(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchase.setItemRight(this.getUserContext(request).getItemRight());
		purchaseService.findAppItemPageBy(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getPurchFeeDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchFeeDetail(HttpServletRequest request,
			HttpServletResponse response,PurchaseFee purchaseFee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseService.getPurchFeeDetail(page, purchaseFee);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getPurchFeeList")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchFeeList(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseService.getPurchFeeList(page, purchase);
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
		UserContext uc=getUserContext(request);
		if(purchase.getStatus()==null){
			purchase.setStatus("OPEN");
			purchase.setCzybh(this.getUserContext(request).getCzybh());
		}
		return new ModelAndView("admin/insales/purchase/purchase_list")
			.addObject("purchase",purchase)
			.addObject("czybh",this.getUserContext(request).getCzybh())
			.addObject("costRight",this.getUserContext(request).getCostRight())
			;
	}
	/**
	 *获取原采购单
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Purchase purchase){
		
		return new ModelAndView("admin/insales/purchase/purchase_code").addObject("purchase",purchase);
	}
	
	/**
	 * 根据id查询Purchase详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPurchase")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Purchase purchase= purchaseService.get(Purchase.class, id);
		if(purchase == null){
			return this.out("系统中不存在No="+id+"的采购单", false);
		}
		return this.out(purchase, true);
	}
	
	@RequestMapping("/goPurchFeeList")
	public ModelAndView goPurchFeeList(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase) throws Exception{
		logger.debug("跳转到采购入库页面");
		
		if(StringUtils.isNotBlank(purchase.getNo())){
			purchase = purchaseService.get(Purchase.class, purchase.getNo());
			purchase.setStatus(purchase.getStatus().trim());
		}
		
		return new ModelAndView("admin/insales/purchase/purchase_purchFeeList")
					.addObject("purchase",purchase);
	}
	
	@RequestMapping("/goUpdateBillStatus")
	public ModelAndView goUpdateBillStatus(HttpServletRequest request,HttpServletResponse response,
			PurchaseFee purchaseFee) throws Exception{
		logger.debug("跳转到采购入库页面");
		
		return new ModelAndView("admin/insales/purchase/purchase_purchFeeStatus")
					.addObject("purchaseFee",purchaseFee);
	}
	
	@RequestMapping("/goPurchFeeSave")
	public ModelAndView goPurchFeeSave(HttpServletRequest request,HttpServletResponse response,
			PurchaseFee purchaseFee) throws Exception{
		logger.debug("跳转到采购入库页面");
		WareHouse wareHouse = null;
		String whDescr = "";
		if("M".equals(purchaseFee.getM_umState())){
			if(StringUtils.isNotBlank(purchaseFee.getNo())){
				purchaseFee = purchaseService.get(PurchaseFee.class, purchaseFee.getNo());
				if(StringUtils.isNotBlank(purchaseFee.getWhcode())){
					wareHouse = wareHouseService.get(WareHouse.class, purchaseFee.getWhcode());
					if(wareHouse != null){
						whDescr = wareHouse.getDesc1();
					}
				}
			}
			purchaseFee.setM_umState("M");
		}else {
			purchaseFee.setBillStatus("0");
			purchaseFee.setM_umState("A");
		}
		purchaseFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		
		return new ModelAndView("admin/insales/purchase/purchase_purchFeeSave")
					.addObject("purchaseFee",purchaseFee)
					.addObject("whDescr",whDescr);
	}
	
	@RequestMapping("/goPurchFeeView")
	public ModelAndView goPurchFeeView(HttpServletRequest request,HttpServletResponse response,
			PurchaseFee purchaseFee) throws Exception{
		logger.debug("跳转到采购入库页面");
		WareHouse wareHouse = null;
		String whDescr = "";
		if("M".equals(purchaseFee.getM_umState())){
			if(StringUtils.isNotBlank(purchaseFee.getNo())){
				purchaseFee = purchaseService.get(PurchaseFee.class, purchaseFee.getNo());
				if(StringUtils.isNotBlank(purchaseFee.getWhcode())){
					wareHouse = wareHouseService.get(WareHouse.class, purchaseFee.getWhcode());
					if(wareHouse != null){
						whDescr = wareHouse.getDesc1();
					}
				}
			}
			purchaseFee.setM_umState("M");
		}else {
			purchaseFee.setM_umState("A");
		}
		purchaseFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		
		return new ModelAndView("admin/insales/purchase/purchase_purchFeeView")
					.addObject("purchaseFee",purchaseFee)
					.addObject("whDescr",whDescr);
	}
	
	@RequestMapping("/goAddPurchFee")
	public ModelAndView goAddPurchFee(HttpServletRequest request,HttpServletResponse response,
			PurchaseFee purchaseFee) throws Exception{
		logger.debug("跳转到采购入库页面");
		
		return new ModelAndView("admin/insales/purchase/purchase_purchFeeAdd")
					.addObject("purchaseFee",purchaseFee);
	}
	
	@RequestMapping("/goPartArrPurchFee")
	public ModelAndView goPartArrPurchFee(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase) throws Exception{
		logger.debug("跳转到采购入库页面");
		//String array = request.getParameter("tableData1");
		System.out.println(request.getParameter("tableData1"));
		//JSONObject jsonObject=JSONObject.parseObject(request.getParameter("tableData1"));
		//System.out.println(jsonObject);
		return new ModelAndView("admin/insales/purchase/purchase_partArrPurchFee")
					.addObject("purchase",purchase)
					.addObject("jsonObject", request.getParameter("tableData1"));
	}
	
	/**
	 * 采购入库
	 * @return
	 * @throws Exception 
	 * 
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase) throws Exception{
		logger.debug("跳转到采购入库页面");
		String itemRight;
		
		System.out.println(request.getParameter("json"));
		
		itemRight= this.purchaseService.getItemRight(this.getUserContext(request).getCzybh());
		String[] itemType1Right = itemRight.split(",");
		if(itemType1Right.length==1){
			purchase.setItemType1(itemRight);
		}
		purchase.setDelivType("1");
		if(StringUtils.isBlank(purchase.getPurType())){
			purchase.setType("S");
		}else{
			purchase.setType("R");
		}
		Employee employee= null;
		purchase.setDate(new Date());
		purchase.setStatus("OPEN");
		purchase.setIsCheckOut("0");
		purchase.setOtherCost(0d);
		purchase.setAmount(0d);
		purchase.setOtherCostAdj(0d);
		purchase.setFirstAmount(0d);
		purchase.setSecondAmount(0d);
		purchase.setRemainAmount(0d);
		
		
		purchase.setApplyMan(this.getUserContext(request).getCzybh());
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		
		return new ModelAndView("admin/insales/purchase/purchase_save")
					.addObject("purchase",purchase)
					.addObject("czybh",this.getUserContext(request).getCzybh())
					.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 * 采购到工地
	 * @return
	 * 
	 */
	@RequestMapping("/goPreSave")
	public ModelAndView goPreSave(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		logger.debug("跳转到采购到工地页面");
		if(StringUtils.isBlank(purchase.getDeliveType())){
			purchase.setDelivType("2");
		}else {
			purchase.setDelivType("1");
		}if(StringUtils.isBlank(purchase.getPurType())){
			purchase.setType("S");
		}else{
			purchase.setType("R");
		}
			Employee employee= null;
			purchase.setDate(new Date());
			purchase.setStatus("OPEN");
			purchase.setIsCheckOut("0");
			purchase.setOtherCost(0d);
			purchase.setAmount(0d);
			purchase.setOtherCostAdj(0d);
			purchase.setFirstAmount(0d);
			purchase.setSecondAmount(0d);
			purchase.setRemainAmount(0d);
			purchase.setApplyMan(this.getUserContext(request).getCzybh());
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		
		return new ModelAndView("admin/insales/purchase/purchase_GD")
					.addObject("purchase",purchase);
	}
	
	@RequestMapping("/goWHBack")
	public ModelAndView goWHBack(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		logger.debug("跳转到采购入库页面");
		String itemRight;
		
		itemRight= this.purchaseService.getItemRight(this.getUserContext(request).getCzybh());
		String[] itemType1Right = itemRight.split(",");
		if(itemType1Right.length==1){
			purchase.setItemType1(itemRight);
		}
		purchase.setDelivType("1");
		purchase.setType("R");
		Employee employee= null;
		purchase.setDate(new Date());
		purchase.setStatus("OPEN");
		purchase.setIsCheckOut("0");
		purchase.setOtherCost(0d);
		purchase.setAmount(0d);
		purchase.setOtherCostAdj(0d);
		purchase.setFirstAmount(0d);
		purchase.setSecondAmount(0d);
		purchase.setRemainAmount(0d);
		
		purchase.setApplyMan(this.getUserContext(request).getCzybh());
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		
		return new ModelAndView("admin/insales/purchase/purchase_whBack")
					.addObject("purchase",purchase)
					.addObject("czybh",this.getUserContext(request).getCzybh())
					.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	/**
	 * 工地退回
	 * @return
	 * 
	 */
	@RequestMapping("/goReturnPreSave")
	public ModelAndView goReturnPreSave(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		logger.debug("跳转到采购入库页面");
		if(StringUtils.isBlank(purchase.getDeliveType())){
			purchase.setDelivType("2");
		}else {
			purchase.setDelivType("1");
		}if(StringUtils.isBlank(purchase.getPurType())){
			purchase.setType("S");
		}else{
			purchase.setType("R");
		}
			Employee employee= null;
			purchase.setDate(new Date());
			purchase.setStatus("OPEN");
			purchase.setIsCheckOut("0");
			purchase.setOtherCost(0d);
			purchase.setAmount(0d);
			purchase.setOtherCostAdj(0d);
			purchase.setFirstAmount(0d);
			purchase.setSecondAmount(0d);
			purchase.setRemainAmount(0d);
			purchase.setApplyMan(this.getUserContext(request).getCzybh());
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		
		return new ModelAndView("admin/insales/purchase/purchase_GD")
					.addObject("purchase",purchase);
	}
	
	@RequestMapping("/goPurchaseExists")
	public ModelAndView goPurchaseExists(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		boolean isEdit = true;
		if (purchase!=null && StringUtils.isNotBlank(purchase.getSupplier())){
			Supplier supplier = supplierService.get(Supplier.class, purchase.getSupplier());
			if (supplier!=null){
				purchase.setSupplierDescr(supplier.getDescr());
				if (supplier.getItemType1()!=null && "JZ".equals(supplier.getItemType1().trim())){
					isEdit = false;
				}
			}
		}
		
		return new ModelAndView("admin/insales/purchase/purchase_exists")
			.addObject("purchase", purchase)
			.addObject("isEdit", isEdit);
	}
	
	/**
	 * 采购退回
	 * @return
	 * 
	 */
	@RequestMapping("/goReturnSave")
	public ModelAndView goReturnSave(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		logger.debug("跳转到采购入库页面");
		
		Employee employee= null;
		purchase.setDate(new Date());
		purchase.setStatus("OPEN");
		purchase.setType("R");
		purchase.setOtherCost(0d);
		purchase.setAmount(0d);
		purchase.setOtherCostAdj(0d);
		purchase.setFirstAmount(0d);
		purchase.setSecondAmount(0d);
		purchase.setRemainAmount(0d);
		purchase.setApplyMan(this.getUserContext(request).getCzybh());
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		return new ModelAndView("admin/insales/purchase/purchase_return")
		.addObject("purchase",purchase)
		.addObject("czybh",this.getUserContext(request).getCzybh())
		.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 *新增明细 
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail,String whcode){
		logger.debug("新增采购明细开始"); 
		if(StringUtils.isNotBlank(whcode)){
			purchaseDetail.setM_umState("R");
		}
		
		purchaseDetail.setItcode("");
		String detailJson = request.getParameter("detailJson");
		purchaseDetail.setDetailJson(detailJson);
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		
		
		
		return new ModelAndView("admin/insales/purchase/purchase_add")
		.addObject("purchaseDetail",purchaseDetail)
		.addObject("costRight",this.getUserContext(request)
				.getCostRight()).addObject("whcode", whcode);
	}
	
	@RequestMapping("/goAddReturn")
	public ModelAndView goAddReturn(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("新增采购明细开始"); 
		
		purchaseDetail.setItcode("");
		String detailJson = request.getParameter("detailJson");
		purchaseDetail.setDetailJson(detailJson);
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/insales/purchase/purchase_addReturn")
		.addObject("purchaseDetail",purchaseDetail)
		.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goReturnAdd")
	public ModelAndView goReturnAdd(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("新增采购明细开始"); 
		
		purchaseDetail.setItcode("");
		String detailJson = request.getParameter("detailJson");
		purchaseDetail.setDetailJson(detailJson);
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/insales/purchase/purchase_return_add")
		.addObject("purchaseDetail",purchaseDetail)
		.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	/**
	 * 快速新增
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 **/
	@RequestMapping("/goFastAdd")
	public ModelAndView goFastAdd(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		Item item =new Item();
		String supplDescr ="";
		if(StringUtils.isNotBlank(purchase.getSupplier())){
			supplDescr=supplierService.get(Supplier.class, purchase.getSupplier()).getDescr();
		}
		item.setPuno(purchase.getNo());
		item.setSupplCode(purchase.getSupplier());
		item.setItemType1(purchase.getItemType1());
		return new ModelAndView("admin/insales/purchase/purchase_fast_add")
			.addObject("item",item).addObject("purchase", purchase)
			.addObject("supplDescr", supplDescr)
				.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goReturnFastAdd")
	public ModelAndView goReturnFastAdd(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		Item item =new Item();
		item.setPuno(purchase.getOldNo()==null?purchase.getNo():purchase.getOldNo());
		item.setSupplCode(purchase.getSupplier());
		item.setItemType1(purchase.getItemType1());
		return new ModelAndView("admin/insales/purchase/purchase_return_fastAdd").addObject("item",item);
	}
	
	@RequestMapping("/goAppItem")
	public ModelAndView goAppItem(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		purchase.setAppItemDateTo(DateUtil.addDate(new Date(), 1));
		if(StringUtils.isNotBlank(this.getUserContext(request).getItemRight())){
			purchase.setItemRight(this.getUserContext(request).getItemRight().replaceAll(",", "','"));
		}
		String itemRight=this.getUserContext(request).getItemRight();
		if(itemRight.split(",").length==1){
			purchase.setItemType1(itemRight);
		}
		return new ModelAndView("admin/insales/purchase/purchase_appItem").addObject("purchase",purchase);
	}
	
	/**
	 * 从采购申请导入
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goImportFromPurApps")
	public ModelAndView goImportFromPurApps(HttpServletRequest request,
	        HttpServletResponse response, PurchaseApp purchaseApp) {
	    
	    return new ModelAndView("admin/insales/purchase/purchase_importFromPurApps")
	            .addObject("purchaseApp", purchaseApp);
	}
	
	/**
	 * 从预算导入
	 * 
	 * */
	@RequestMapping("/goImport")
	public ModelAndView goImport(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase,String arr){
			logger.debug("跳转到预算导入");
			 ItemAppDetail itemAppDetail = new ItemAppDetail();
			itemAppDetail.setPuno(purchase.getOldNo());
			itemAppDetail.setCustCode(purchase.getCustCode());
			itemAppDetail.setItemType1(purchase.getItemType1());
			return new ModelAndView("admin/insales/purchase/purchase_import")
			.addObject("arr",arr).addObject("itemAppDetail",itemAppDetail);
	}
	
	@RequestMapping("/goReturnImport")
	public ModelAndView goReturnImport(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase,String arr){
			logger.debug("跳转到预算导入");
			ItemAppDetail itemAppDetail = new ItemAppDetail();
			itemAppDetail.setPuno(purchase.getOldNo());
			itemAppDetail.setCustCode(purchase.getCustCode());
			itemAppDetail.setItemType1(purchase.getItemType1());
		return new ModelAndView("admin/insales/purchase/purchase_return_import").addObject("arr",arr).addObject("itemAppDetail",itemAppDetail);
	}
	
	 /**
	  * 从销售单导入 
	  *  
	  **/
	@RequestMapping("/goImportSale")
	public ModelAndView goImportSale(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		
		SalesInvoiceDetail salesInvoiceDetail=new SalesInvoiceDetail();
		salesInvoiceDetail.setXZKZ(1);
		salesInvoiceDetail.setItemType1(purchase.getItemType1());
		salesInvoiceDetail.setPuno(purchase.getOldNo());
		
		return new ModelAndView("admin/insales/purchase/purchase_importSale").addObject("salesInvoiceDetail",salesInvoiceDetail);
	}
	
	/**
	 *采购退回确认 销售导入 
	 *
	 **/
	@RequestMapping("/goImportSale1")
	public ModelAndView goImportSale1(HttpServletRequest request,HttpServletResponse response,
			Purchase purchase){
		
		SalesInvoiceDetail salesInvoiceDetail=new SalesInvoiceDetail();
		salesInvoiceDetail.setItemType1(purchase.getItemType1());
		salesInvoiceDetail.setPuno(purchase.getOldNo());
		
		return new ModelAndView("admin/insales/purchase/purchase_importSale").addObject("salesInvoiceDetail",salesInvoiceDetail);
	}
	
	
	/**
	 *新增页面查看
	 *
	 **/
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request, HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("跳转到采购单查看");
		
		if(purchaseDetail.getPuno()==null){
				purchaseDetail.setPuno("");
		}
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/insales/purchase/purchase_addview")
		.addObject("purchaseDetail",purchaseDetail)
		.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	/**
	 *新增页面-编辑
	 * 
	 **/
	@RequestMapping("/goAddUpdate")
	public ModelAndView goAddUpdate(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail,String whCode){
		logger.debug("跳转到采购新增编辑");
		if(StringUtils.isNotBlank(whCode)){
			purchaseDetail.setM_umState("R");
		}else {
			purchaseDetail.setM_umState("A");
		}
		Item item =null;
		if(purchaseDetail.getPuno()==null){
			purchaseDetail.setPuno("");
		}
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/insales/purchase/purchase_addUpdate")
		.addObject("purchaseDetail",purchaseDetail)
		.addObject("costRight",this.getUserContext(request).getCostRight())
		.addObject("whCode", whCode);
	}
	
	@RequestMapping("/goAddUpdateReturn")
	public ModelAndView goAddUpdateReturn(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("跳转到采购新增编辑");
		Item item =null;
		if(purchaseDetail.getPuno()==null){
			purchaseDetail.setPuno("");
		}
		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		return new ModelAndView("admin/insales/purchase/purchase_addUpdateReturn")
		.addObject("purchaseDetail",purchaseDetail)
		.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 *ajax获取明细 
	 */
	@RequestMapping("/getAjaxDetail")
	@ResponseBody
	public PurchaseDetail getAjaxDetail(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("ajax获取数据");  
		
		purchaseDetail.setPuno("");

		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
	
		return purchaseDetail;
	}
	
	@RequestMapping("/getAjaxQtyCalDetail")
	@ResponseBody
	public PurchaseDetail getAjaxQtyCalDetail(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("ajax获取数据");  

		purchaseDetail= this.purchaseDetailService.getQtyCal(purchaseDetail);
	
		return purchaseDetail;
	}
	
	/**
	 *ajax获取明细 
	 */
	@RequestMapping("/getAjaxDetail1")
	@ResponseBody
	public Item getAjaxDetail1(HttpServletRequest request,HttpServletResponse response,
			PurchaseDetail purchaseDetail){
		logger.debug("ajax获取数据");  
		Item item= new Item();
		purchaseDetail.setPuno("");//用来解决存储过程pGetItemQty中 no<>puno

		purchaseDetail= this.purchaseDetailService.getPurchaseDetail(purchaseDetail);
		item.setPurqty(purchaseDetail.getPurqty());
		item.setSaleqty(purchaseDetail.getSaleqty());
		item.setApplyqty(purchaseDetail.getApplyqty());
		item.setAppqty(purchaseDetail.getAppqty());
		return item;
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
		Customer customer = null;
		
		
		purchase = purchaseService.get(Purchase.class,id);
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		wareHouse = wareHouseService.get(WareHouse.class, purchase.getWhcode());
		supplier = supplierService.get(Supplier.class,purchase.getSupplier());
		customer = customerService.get(Customer.class,purchase.getCustCode());
		if(purchase!=null && (purchase.getOldPUNo()==null ||"".equals(purchase.getOldPUNo())) 
				&& "R".equals(purchase.getType())){
			purchase.setM_umState("R");
		}
		purchase.setStatus("CONFIRMED");
		purchase.setSupDescr(supplier==null?"":supplier.getDescr());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
		purchase.setAddress(customer==null?"":customer.getAddress());
		
		return new ModelAndView("admin/insales/purchase/purchase_purchReturnCheckOut")
			.addObject("purchase",purchase)
			.addObject("czybh",this.getUserContext(request).getCzybh())
			.addObject("costRight",this.getUserContext(request).getCostRight());
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
	 * 跳转到查看Purchase页面-其他页面调用
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看Purchase页面");
		Map<String,Object> map = purchaseService.getByNo(id);
		Purchase purchase = new Purchase();
		BeanConvertUtil.mapToBean(map, purchase);
		
		return new ModelAndView("admin/insales/purchase/purchase_view")
				.addObject("purchase", purchase);
	}
	
	/** 
	 *查看页面
	 *@return 
	 *
	 */
	@RequestMapping("/goViewNew")
	public ModelAndView goViewNew(HttpServletRequest request,HttpServletResponse response,
			String id){
		logger.debug("跳转到查看页面");
		Supplier supplier=null;
		Purchase purchase=null;
		Employee employee= null;
		WareHouse wareHouse = null;
		Customer customer=null;
		purchase = purchaseService.get(Purchase.class,id);
		if(StringUtils.isNotBlank(purchase.getApplyMan())){
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
		}
		if(StringUtils.isBlank(purchase.getDelivType())){
			purchase.setDelivType("2");
		}
		if(StringUtils.isNotBlank(purchase.getCustCode())){
			customer= customerService.get(Customer.class, purchase.getCustCode());
		}
		wareHouse = wareHouseService.get(WareHouse.class,purchase.getWhcode()==null?"":purchase.getWhcode());
		supplier = supplierService.get(Supplier.class,purchase.getSupplier());	
		purchase.setSupDescr(supplier==null?"":supplier.getDescr());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
		purchase.setAddress(customer==null?"":customer.getAddress()); 
		purchase.setDocumentNo(customer==null?"":customer.getDocumentNo());

		purchase.setFromPage(request.getParameter("fromPage"));
		purchase.setIsService(request.getParameter("isService"));
		return new ModelAndView("admin/insales/purchase/purchase_viewNew")
			.addObject("purchase",purchase)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/** 
	 *查看页面
	 *@return 
	 *
	 */
	@RequestMapping("/goViewNew2")
	public ModelAndView goViewNew2(HttpServletRequest request,HttpServletResponse response,
			String id){
		logger.debug("跳转到查看页面");
		Supplier supplier=null;
		Purchase purchase=null;
		Employee employee= null;
		WareHouse wareHouse = null;
		Customer customer=null;
		purchase = purchaseService.get(Purchase.class,id);
		if(StringUtils.isNotBlank(purchase.getApplyMan())){
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
		}
		if(StringUtils.isBlank(purchase.getDelivType())){
			purchase.setDelivType("2");
		}
		if(StringUtils.isNotBlank(purchase.getCustCode())){
			customer= customerService.get(Customer.class, purchase.getCustCode());
		}
		wareHouse = wareHouseService.get(WareHouse.class,purchase.getWhcode()==null?"":purchase.getWhcode());
		supplier = supplierService.get(Supplier.class,purchase.getSupplier());	
		purchase.setSupDescr(supplier==null?"":supplier.getDescr());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
		purchase.setAddress(customer==null?"":customer.getAddress()); 
		purchase.setDocumentNo(customer==null?"":customer.getDocumentNo());
		purchase.setFromPage(request.getParameter("fromPage"));
		purchase.setIsService(request.getParameter("isService"));
		return new ModelAndView("admin/insales/purchase/purchase_viewNew2")
			.addObject("purchase",purchase)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 * 编辑
	 * @return
	 * 
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response,
			String id){
			logger.debug("跳转到编辑页面");
			Supplier supplier=null;
			Purchase purchase=null;
			Employee employee= null;
			WareHouse wareHouse = null;
			Customer customer = null;
			purchase = purchaseService.get(Purchase.class,id);
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
			wareHouse = wareHouseService.get(WareHouse.class, purchase.getWhcode());
			supplier = supplierService.get(Supplier.class,purchase.getSupplier());
			customer = customerService.get(Customer.class,purchase.getCustCode());
			purchase.setSupDescr(supplier==null?"":supplier.getDescr());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
			purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
			purchase.setCustDescr(customer==null?"":customer.getDescr());
			purchase.setAddress(customer==null?"":customer.getAddress());
			purchase.setDocumentNo(customer==null?"":customer.getDocumentNo());
			
			if(purchase.getType().equals("R")){
				return new ModelAndView("admin/insales/purchase/purchase_updateReturn")
					.addObject("purchase",purchase)
					.addObject("czybh",this.getUserContext(request).getCzybh())
					.addObject("costRight",this.getUserContext(request).getCostRight());
			}else{
				return new ModelAndView("admin/insales/purchase/purchase_update")
				.addObject("purchase",purchase)
				.addObject("czybh",this.getUserContext(request).getCzybh())
				.addObject("costRight",this.getUserContext(request).getCostRight());
			}
			
	}
	
	/**
	 * 编辑
	 * @return
	 * 
	 */
	@RequestMapping("/goPartUpdate")
	public ModelAndView goPartUpdate(HttpServletRequest request,HttpServletResponse response,
			String id){
			logger.debug("跳转到编辑页面");
			Supplier supplier=null;
			Purchase purchase=null;
			Employee employee= null;
			WareHouse wareHouse = null;
			Customer customer = null;
			purchase = purchaseService.get(Purchase.class,id);
			employee = employeeService.get(Employee.class, purchase.getApplyMan());
			wareHouse = wareHouseService.get(WareHouse.class, purchase.getWhcode());
			supplier = supplierService.get(Supplier.class,purchase.getSupplier());
			customer = customerService.get(Customer.class,purchase.getCustCode());
			purchase.setSupDescr(supplier==null?"":supplier.getDescr());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
			purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
			purchase.setCustDescr(customer==null?"":customer.getDescr());
			purchase.setAddress(customer==null?"":customer.getAddress());
			purchase.setDocumentNo(customer==null?"":customer.getDocumentNo());
			
				return new ModelAndView("admin/insales/purchase/purchase_partUpdate")
					.addObject("purchase",purchase)
					.addObject("czybh",this.getUserContext(request).getCzybh())
					.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	
	
	/**
	 *明细查询 
	 * 
	 **/
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request ,
			HttpServletResponse response , Purchase purchase) throws Exception{
		purchase.setStatus("OPEN,CONFIRMED");
		
		return new ModelAndView("admin/insales/purchase/purchase_detail")
			.addObject("purchase",purchase)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	 
	@RequestMapping("/goViewPurchFee")
	public ModelAndView goViewPurchFee(HttpServletRequest request ,
			HttpServletResponse response , Purchase purchase) throws Exception{
		
		return new ModelAndView("admin/insales/purchase/purchase_viewPurchFee")
			.addObject("purchase",purchase);
	}
	
	/**
	 *明细查询 
	 * 
	 **/
	@RequestMapping("/goPurchGZ")
	public ModelAndView goPurchGZ(HttpServletRequest request ,
			HttpServletResponse response , Purchase purchase) throws Exception{
		Employee employee= null;
		purchase.setApplyMan(this.getUserContext(request).getCzybh());
		employee = employeeService.get(Employee.class, purchase.getApplyMan());
		purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
		
		return new ModelAndView("admin/insales/purchase/purchase_purchGZ")
			.addObject("purchase",purchase)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	
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
			purchase.setSupDescr(supplier==null?"":supplier.getDescr());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
			purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
			boolean existsPurchFee = purchaseService.existsPurchFee(id);
			return new ModelAndView("admin/insales/purchase/purchase_partarrive")
			.addObject("purchase",purchase)
			.addObject("czybh",this.getUserContext(request).getCzybh())
			.addObject("costRight",this.getUserContext(request).getCostRight())
			.addObject("existsPurchFee", existsPurchFee);
	}
	/**
	 * 分摊
	 * @return
	 * 
	 */
	@RequestMapping("/goApportionFee")
	public ModelAndView goApportionFee(HttpServletRequest request,HttpServletResponse response,
			String puno){
			logger.debug("跳转到部分到货页面");
			PurchaseFee purchaseFee =  new PurchaseFee();
			purchaseFee.setPuno(puno);
			return new ModelAndView("admin/insales/purchase/purchase_apportionFee")
			.addObject("purchaseFee",purchaseFee);
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
			purchase.setSupDescr(supplier==null?"":supplier.getDescr());
			purchase.setApplyManDescr(employee==null?"":employee.getNameChi());
			purchase.setWHCodeDescr(wareHouse==null?"":wareHouse.getDesc1());
			boolean existsPurchFee = purchaseService.existsPurchFee(id);
			return new ModelAndView("admin/insales/purchase/purchase_allarrive")
				.addObject("purchase",purchase)
				.addObject("czybh",this.getUserContext(request).getCzybh())
				.addObject("costRight",this.getUserContext(request).getCostRight())
				.addObject("existsPurchFee",existsPurchFee);
	}
	
	/**
	 *到货单查询 
	 * 
	 **/
	@RequestMapping("/goArrDetail")
	public ModelAndView goArrDetail(HttpServletRequest request ,
			HttpServletResponse response , Purchase purchase) throws Exception{
		
		String costRight=this.getUserContext(request).getCostRight();
		
		return new ModelAndView("admin/insales/purchase/purchase_arrDetail")
			.addObject("purchase",purchase).addObject("costRight",costRight);
	}
	
	/**
	 * 采购跟踪——延期
	 *
	 */
	@RequestMapping("/goPurchDelay")
	public ModelAndView goPurchDealy(HttpServletRequest request, HttpServletResponse response
			,PurchaseDelay purchaseDelay){
		PurchaseDelay purchaseDelay1 = new PurchaseDelay();
		
		if(purchaseDelay.getPk()!=null){
			purchaseDelay1 = purchaseDelayService.get(PurchaseDelay.class, purchaseDelay.getPk());
		}else {
			purchaseDelay1.setPuno(purchaseDelay.getPuno());
		}
		
		return new ModelAndView("admin/insales/purchase/purchase_postpone").addObject("purchaseDelay1",purchaseDelay1);
	}
	
	/**
	 * 采购跟踪——延期
	 *
	 */
	@RequestMapping("/goViewGZ")
	public ModelAndView goViewGZ(HttpServletRequest request, HttpServletResponse response
			,String no){
		Purchase purchase=null;
		purchase=purchaseService.get(Purchase.class, no);
		return new ModelAndView("admin/insales/purchase/purchase_view_GZ").addObject("purchase",purchase);
	}
	
	/**
	 * 采购跟踪——延期
	 *
	 */
	@RequestMapping("/goViewArrDetail")
	public ModelAndView goViewArrDetail(HttpServletRequest request, HttpServletResponse response
			,PurchArr purchArr){
		/*PurchArr purchArr=new PurchArr();
		if(no!=""){
			purchArr=purchArrService.get(PurchArr.class,no);
		}else{
			purchArr.setNo("1");
			purchArr.setPuno("1");
		}*/
		return new ModelAndView("admin/insales/purchase/purchase_view_arrDetail").addObject("purchArr",purchArr);
	}
	
	/**
	 * 采购跟踪——到货
	 *
	 */
	@RequestMapping("/goPurchArrive")
	public ModelAndView goPurchArrive(HttpServletRequest request, HttpServletResponse response
			,String no){
		Purchase purchase= null;
		purchase = purchaseService.get(Purchase.class, no);
		
		return new ModelAndView("admin/insales/purchase/purchase_purchArr").addObject("purchase",purchase);
	}

	/**
	 * 采购单——打印 
	 */
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request,HttpServletResponse response,
			String no){
		
			Purchase purchase=null;
			Employee employee=null;
			purchase = purchaseService.get(Purchase.class, no);
			purchase.setCzybh(this.getUserContext(request).getCzybh());
			employee = employeeService.get(Employee.class, purchase.getCzybh());
			purchase.setCZYDescr(employee==null?"":employee.getNameChi());
			purchase.setLogoName("logo.jpg");
			
			if("RZ".equals(purchase.getItemType1().trim())){
				
				Xtcs xtcs = purchaseService.get(Xtcs.class, "SoftLogo");
				if(xtcs == null){
					purchase.setLogoName(" ");
				} else {
					purchase.setLogoName(xtcs.getQz());
				}
				
			}
			
			return new ModelAndView("admin/insales/purchase/purchase_print")
			.addObject("purchase",purchase)
			.addObject("costRight",this.getUserContext(request).getCostRight());
	} 
	
	/**
	 *部分到货 保存
	 *
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("部分到货开始");
		try {
			purchase.setM_umState("P");
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
	 *采购入库保存 
	 *
	 */
	@RequestMapping("/doPurchaseSave")
	public void doPurchaseSave(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("采购入库新增开始");
		String planEndString=request.getParameter("arriveDate");
		Date arriveDate =DateFormatString(planEndString);
		try {
			if(purchase.getDate()==null){
				purchase.setDate(DateFormatString(request.getParameter("date")));
			}
			purchase.setTemp(1);
			purchase.setArriveDate(arriveDate);
			String detailJson = request.getParameter("detailJson");
			purchase.setDetailJson(detailJson);
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			purchase.setLastUpdate(new Date());

			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}
			Result result = this.purchaseService.doPurchaseSave(purchase);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "采购入库失败");
		}
	}
	
	/**
	 *采购入库保存 
	 *
	 */
	@RequestMapping("/doWHBackSave")
	public void doWHBackSave(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("仓库直接退回开始");
		String planEndString=request.getParameter("arriveDate");
		Date arriveDate =DateFormatString(planEndString);
		try {
			if(purchase.getDate()==null){
				purchase.setDate(DateFormatString(request.getParameter("date")));
			}
			purchase.setTemp(1);
			purchase.setArriveDate(arriveDate);
			String detailJson = request.getParameter("detailJson");
			purchase.setDetailJson(detailJson);
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			purchase.setLastUpdate(new Date());

			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}
			Result result = this.purchaseService.doPurchaseSave(purchase);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "仓库退回失败");
		}
	}
	
	/**
	 * 编辑
	 * 
	 * */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("采购入库编辑开始");
		try {
			purchase.setLastUpdate(new Date());
			purchase.setTemp(2);
			String detailJson = request.getParameter("detailJson");
			purchase.setDetailJson(detailJson);
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());

			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}
			purchase.setOldNo(purchase.getOldPUNo());
			Result result = this.purchaseService.doPurchaseSave(purchase);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑入库失败");
		}
	}
	
	@RequestMapping("/doPartUpdate")
	public void doPartUpdate(HttpServletRequest request ,HttpServletResponse response ,Purchase purchase){
		Purchase purchase2 =new Purchase();
		try {
			if(purchase.getNo()!=""&&purchase.getNo()!=null){
				purchase2=purchaseService.get(Purchase.class, purchase.getNo());
			}
			purchase2.setRemarks(purchase.getRemarks());
			this.purchaseService.update(purchase2);
			
			ServletUtils.outPrintSuccess(request, response,"编辑成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
		
	}
	
	@RequestMapping("/doUpdateBillStatus")
	public void doUpdateBillStatus(HttpServletRequest request ,HttpServletResponse response ,PurchaseFee purchaseFee){
		try {
			PurchaseFee pf = null;
			pf = purchaseService.get(PurchaseFee.class, purchaseFee.getNo());
			pf.setBillStatus(purchaseFee.getBillStatus());
			pf.setLastUpdate(new Date());
			pf.setLastUpdatedBy( this.getUserContext(request).getCzybh());
			purchaseService.update(pf);
			ServletUtils.outPrintSuccess(request, response,"编辑成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
		
	}
	
	@RequestMapping("/doPurchFeeSave")
	public void doPurchFeeSave(HttpServletRequest request ,HttpServletResponse response ,PurchaseFee purchaseFee){
		try {
			purchaseFee.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.purchaseService.doPurchFeeSave(purchaseFee);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		
	}
	
	
	
	/**
	 *采购入库
	 * 
	 **/
	@RequestMapping("/ajaxDoReturn")
	@ResponseBody
	public Purchase ajaxDoReturn(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		Purchase purchase2 =new Purchase();
		Purchase purchase3=new Purchase();
		Map<String,Object> map=this.purchaseService.ajaxDoReturn(purchase.getNo());
		if(map!=null){
			purchase2.setNo((String)map.get("puno"));
			return purchase2;
		}else{
			return purchase3;
			
		}
	}
	
	
	@RequestMapping("/doReturnSave")
	public void doReturnSave(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("采购入库新增开始");
		try {
			purchase.setTemp(1);
			purchase.setOldPUNo(purchase.getOldNo());
			String detailJson = request.getParameter("detailJson");
			purchase.setDetailJson(detailJson);
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			purchase.setLastUpdate(new Date());

			if(detailJson.equals("[]")){
				ServletUtils.outPrintFail(request, response, "明细表无数据保存无意义");
				return;
			}
			Result result = this.purchaseService.doPurchaseSave(purchase);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "采购入库增加失败");
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
				purchase.setType("R");
				purchase.setConfirmCZY(this.getUserContext(request).getCzybh());
				purchase.setConfirmDate(new Date());
				purchase.setLastUpdate(new Date());
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
	
	@RequestMapping("/doPurchaseDelay")
	public void doPurchaseDelay(HttpServletRequest request ,HttpServletResponse response,
			PurchaseDelay purchaseDelay){
		logger.debug("延期修改开始");
			Purchase purchase =null;
	
		try {
			purchase = purchaseService.get(Purchase.class, purchaseDelay.getPuno());
			purchase.setArriveDate(purchaseDelay.getArriveDate());
			purchase.setArriveRemark(purchaseDelay.getRemarks());
			purchaseDelay.setLastUpdate(new Date());
			purchaseDelay.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.purchaseService.update(purchase);
			this.purchaseDelayService.saveOrUpdate(purchaseDelay);
			ServletUtils.outPrintSuccess(request, response);
			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,"采购延期失败");
		}
	}
	
	/**
	 *采购跟踪——到货保存
	 *
	 */
	@RequestMapping("/doGZArrive")
	public void doGZArrive(HttpServletRequest request ,HttpServletResponse response,
			Purchase purchase){
		logger.debug("采购跟踪——到货状态");
		try {
			Purchase purchase1 = purchaseService.get(Purchase.class, purchase.getNo());
			purchase1.setLastUpdate(new Date());
			purchase1.setLastUpdatedBy(getUserContext(request).getCzybh());
			purchase1.setArriveStatus(purchase.getArriveStatus());
			purchase1.setArriveRemark(purchase.getArriveRemark());
			
			this.purchaseService.update(purchase1);
			ServletUtils.outPrintSuccess(request, response);
			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,"到货状态修改失败");
		}
	}
	
	@RequestMapping("/doAppItem")
	public void doAppItem(HttpServletRequest request,HttpServletResponse response,Purchase purchase){
		logger.debug("部分到货开始");
		try {
			purchase.setM_umState("M");
			purchase.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String detailJson = request.getParameter("detailJson");
			purchase.setDetailJson(detailJson);

			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "没有选择确认订货的采购单");
				return;
			}
			Result result = this.purchaseService.doAppItem(purchase);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "部分到货修改失败");
		}
	}
	
	@RequestMapping("/cancelAppItem")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("采购订单撤销");
		
		this.purchaseService.cancelAppItem(no,this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"撤销成功");
	}
	
	@RequestMapping("/getAjaxArriveDay")
	@ResponseBody
	public ItemType2 getAjaxArriveDay(HttpServletRequest request ,HttpServletResponse response,
			String code){
		ItemType2 itemType2=new ItemType2();
		if(StringUtils.isBlank(code)){
			return null;
		}
		Map<String, Object > map =this.purchaseService.getAjaxArriveDay(code);
		
		itemType2.setArriveDay((Integer)map.get("ArriveDay"));
		
		return itemType2;
	}
	
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Purchase purchase){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		String itemType1= uc.getItemRight();
		purchase.setItemRight(itemType1);
		page.setPageSize(-1);
		purchaseService.findPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"采购明细单_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			Purchase purchase){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		String itemType1= uc.getItemRight();
		purchase.setItemRight(itemType1);
		page.setPageSize(-1);
		purchaseService.findDetailPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"采购明细表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	
	/**
	 * 导出Excel
	 * 
	 */ 
	/*@RequestMapping(value = "/export")
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
	}*/
	
	public  Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = null;
		}
        return date;
	}
	
	@RequestMapping("/whRight")
	@ResponseBody
	public boolean whRight(HttpServletRequest request,HttpServletResponse response,String whCode){
		if(this.purchaseService.whRight(whCode, this.getUserContext(request).getCzybh())){
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping("/getProjectCost")
	@ResponseBody
	public double getProjectCost(HttpServletRequest request ,HttpServletResponse response,String itcode){
		
		return this.purchaseService.getProjectCost(itcode);
	
	}
	
	@RequestMapping("/getChangeParameter")
	@ResponseBody
	public List<Map<String, Object>> getChangeParameter(HttpServletRequest request ,HttpServletResponse response,
			String custCode,String itemType2,String itemType1 ){
		List<Map<String, Object>> list;
				list=this.purchaseService.getChengeParameter(custCode,itemType1,itemType2);
			return list;
	
	}
	
	@RequestMapping("/getChangeParameter2")
	@ResponseBody
	public List<Map<String, Object>> getChangeParameter2(HttpServletRequest request ,HttpServletResponse response,
			String custCode,String itemType2,String itemType1 ){
		List<Map<String, Object>> list;
				list=this.purchaseService.getChengeParameter2(custCode,itemType1,itemType2);
			return list;
	
	}
	
	@RequestMapping("/getChangeParameter3")
	@ResponseBody
	public List<Map<String, Object>> getChangeParameter3(HttpServletRequest request ,HttpServletResponse response,
			String custCode,String itemType2,String itemType1,String supplierCode){
		List<Map<String, Object>> list;
			list=this.purchaseService.getChengeParameter3(custCode,itemType1,itemType2,supplierCode);
			return list;
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param splCheckOut
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchaseFeeDetailGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchaseFeeDetailGrid(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseService.findPageBySql_purchaseFeeDetail(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}
}
