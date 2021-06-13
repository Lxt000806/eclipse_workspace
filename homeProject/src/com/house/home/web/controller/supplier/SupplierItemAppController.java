package com.house.home.web.controller.supplier;

import java.util.Date;
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
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.impl.CustDocUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.PathUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SysLog;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.Supplier;
import com.house.home.entity.insales.WareHouse;
import com.house.home.entity.project.IntProduce;
import com.house.home.entity.project.IntProgDetail;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustDocService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.insales.ItemAppService;
import com.house.home.service.insales.PurchaseService;
import com.house.home.service.insales.SupplierService;
import com.house.home.service.insales.WareHouseService;
import com.house.home.service.project.IntProduceService;

@Controller
@RequestMapping("/admin/supplierItemApp")
public class SupplierItemAppController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SupplierItemAppController.class);

	@Autowired
	private ItemAppService itemAppService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private Department2Service department2Service;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private CustDocService custDocService;
	@Autowired
	private IntProduceService intProduceService;
	
	private void resetItemApp(ItemApp itemApp) {
		if (itemApp == null) {
			return;
		}
		
		if (StringUtils.isNotBlank(itemApp.getCustCode())){
			Customer customer = customerService.get(Customer.class, itemApp.getCustCode());
			if (customer!=null){
				itemApp.setCustArea(customer.getArea());
				itemApp.setCustAddress(customer.getAddress());
				itemApp.setCustDocumentNo(customer.getDocumentNo());
				
				if (StringUtils.isNotBlank(customer.getProjectMan())) {
					Employee employee = employeeService.get(Employee.class, customer.getProjectMan());
					if (employee != null) {
						if (StringUtils.isNotBlank(employee.getDepartment2())) {
							Department2 department2 = department2Service.get(Department2.class, employee.getDepartment2());
							if (department2 != null) {
								itemApp.setProjectDept2Descr(department2.getDesc2()); //获取项目经理所在二级部门的名称
							}
						} 
					}
				}
			}
		}
		
		if (StringUtils.isNotBlank(itemApp.getSupplCode())){
			Supplier supplier = supplierService.get(Supplier.class, itemApp.getSupplCode());
			if (supplier!=null){
				itemApp.setSupplCodeDescr(supplier.getDescr());
			}
		}
		
		if ("1".equalsIgnoreCase(itemApp.getSendType())) { //1.供应商直送
			itemApp.setIsAutoArriveDate(itemAppService.calcIsAutoArriveDate(itemApp.getSupplCode()));
		} else {
			itemApp.setIsAutoArriveDate(false);
			if (StringUtils.isNotBlank(itemApp.getWhcode())){
				WareHouse wareHouse = wareHouseService.get(WareHouse.class, itemApp.getWhcode());
				if (wareHouse!=null){
					itemApp.setWhcodeDescr(wareHouse.getDesc1());
				}
			}
		}
		if(StringUtils.isNotBlank(itemApp.getPuno())){
			Purchase purchase=purchaseService.get(Purchase.class, itemApp.getPuno());
			itemApp.setSplAmount(purchase.getSplAmount());
			itemApp.setPuSplStatus(purchase.getSplStatus());
			itemApp.setCheckConfirmRemarks(purchase.getCheckConfirmRemarks());
			itemApp.setAmount(purchase.getAmount());
			if(StringUtils.isNotBlank(purchase.getCheckOutNo())){
				SplCheckOut splCheckOut=purchaseService.get(SplCheckOut.class, purchase.getCheckOutNo());
				itemApp.setCheckStatus(splCheckOut.getStatus());
				
			}
		}
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (StringUtils.isBlank(page.getPageOrderBy())){
			page.setPageOrderBy("a.LastUpdate");
			page.setPageOrder("desc");
		}
		if (StringUtils.isBlank(itemApp.getModule())) {
			itemApp.setStatus("CONFIRMED");
			itemApp.setModule("SupplierItemApp");
		}	
		itemApp.setType("S"); // S.发货
//		itemApp.setSendType("1"); // 2.供应商直送
		itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemAppService.findPageBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 获取生产进度登记显示列表
	 * @author	created by zb
	 * @date	2020-2-28--下午2:51:16
	 * @param request
	 * @param response
	 * @param intProduce
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIntProduceJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getIntProduceJqGrid(HttpServletRequest request,	
			HttpServletResponse response,IntProduce intProduce) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intProduce.setSupplCode(this.getUserContext(request).getEmnum());
		intProduceService.findPageBySql(page, intProduce);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemApp列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		String supplItemType1="";
		if("2".equals(this.getUserContext(request).getCzylb())){
			supplItemType1=supplierService.getItemType1(this.getUserContext(request).getCzybh());
		}
		boolean hasAuthCheckApp = false;
		if(czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1845)
				||czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1508) ){
			hasAuthCheckApp= true;
		}
		return new ModelAndView("admin/supplier/itemApp/itemApp_list")
			.addObject("itemApp", itemApp)
			.addObject("czybh", this.getUserContext(request).getCzybh())
			.addObject("supplItemType1", supplItemType1.trim())
		    .addObject("hasAuthCheckApp",hasAuthCheckApp);
	}
	
	/**
	 * 跳转到修改ItemApp页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemApp页面");
		
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		resetItemApp(itemApp);		
		Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
		if(czybm != null){
			itemApp.setSupplyRecvModel(czybm.getSupplyRecvModel().trim());
		}else{
			itemApp.setSupplyRecvModel("3");
		}
		return new ModelAndView("admin/supplier/itemApp/itemApp_update")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * 跳转到查看ItemApp页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id,String splstatus){
		logger.debug("跳转到查看ItemApp页面");

		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		resetItemApp(itemApp);
		return new ModelAndView("admin/supplier/itemApp/itemApp_view")
			.addObject("itemApp", itemApp).addObject("splstatus", splstatus)
			.addObject("projectCostRight", this.getUserContext(request).getProjectCostRight())
			.addObject("costRight",this.getUserContext(request).getCostRight());
	}
	
	/**
	 * 跳转到查看ItemApp明细页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemApp页面");
		
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		return new ModelAndView("admin/itemApp/itemApp_detail")
				.addObject("itemApp", itemApp);
	}
	/**
	 * 跳转到生产进度登记页面
	 * @author	created by zb
	 * @date	2020-2-28--上午11:06:47
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goIntProduce")
	public ModelAndView goIntProduce(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到生产进度登记页面");
		return new ModelAndView("admin/supplier/itemApp/itemApp_intProduce");
	}
	/**
	 * 跳转到生产进度登记新增页面
	 * @author	created by zb
	 * @date	2020-2-28--下午3:39:22
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goIntProduceAdd")
	public ModelAndView goIntProduceAdd(HttpServletRequest request, HttpServletResponse response,
			IntProduce intProduce){
		logger.debug("跳转到生产进度登记新增页面");
		intProduce.setSupplCode(this.getUserContext(request).getEmnum());
		Supplier supplier = new Supplier();
		if (StringUtils.isNotBlank(intProduce.getSupplCode())) {
			supplier = this.intProduceService.get(Supplier.class, intProduce.getSupplCode());
		}
		return new ModelAndView("admin/supplier/itemApp/itemApp_intProduceAdd")
			.addObject("intProduce", intProduce)
			.addObject("supplier", supplier);
	}
	/**
	 * 跳转到生产进度登记编辑页面
	 * @author	created by zb
	 * @date	2020-2-28--下午5:41:35
	 * @param request
	 * @param response
	 * @param intProduce
	 * @return
	 */
	@RequestMapping("/goIntProduceEdit")
	public ModelAndView goIntProduceEdit(HttpServletRequest request, HttpServletResponse response,
			IntProduce intProduce){
		logger.debug("跳转到生产进度登记编辑页面");
		IntProduce intProduce2 = this.intProduceService.get(IntProduce.class, intProduce.getPk());
		Supplier supplier = new Supplier();
		Customer customer = new Customer();
		if (null != intProduce2) {
			if (StringUtils.isNotBlank(intProduce2.getSupplCode())) {
				supplier = this.intProduceService.get(Supplier.class, intProduce2.getSupplCode());
			}
			if (StringUtils.isNotBlank(intProduce2.getCustCode())) {
				customer = this.intProduceService.get(Customer.class, intProduce2.getCustCode());
			}
		}
		if (null != customer) {
			intProduce2.setCustName(customer.getDescr());
			intProduce2.setAddress(customer.getAddress());
		}
		intProduce2.setM_umState(intProduce.getM_umState());
		return new ModelAndView("admin/supplier/itemApp/itemApp_intProduceAdd")
			.addObject("intProduce", intProduce2)
			.addObject("supplier", supplier);
	}
	
	/**
	 * 修改ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("修改ItemApp开始");
		
		try{
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单，修改失败");
				return;
			}
			
			if (!"2".equals(ia.getSplStatus().trim())) { // 2.已接收
				ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】状态,无法进行修改操作!");
				return;
			}
			String detailJson = request.getParameter("detailJson");
			ia.setDetailJson(detailJson);
			ia.setM_umState("SM"); //SM.供应商修改
			ia.setSplStatus(itemApp.getSplStatus());
			ia.setProductType(itemApp.getProductType());
			ia.setArriveDate(itemApp.getArriveDate());
			ia.setSplRemark(itemApp.getSplRemark());
			ia.setOtherCost(itemApp.getOtherCost());
			ia.setOtherCostAdj(itemApp.getOtherCostAdj());
			ia.setLastUpdate(new Date());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ia.setExpired("F");
			ia.setActionLog("EDIT");
			ia.setArriveSplDate(itemApp.getArriveSplDate()); //到厂日期
//			this.itemAppService.update(ia);
			Result result = this.itemAppService.doItemAppForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}			
//			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemApp失败");
		}
	}
	
	/**
	 * 接收ItemApp
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doReceive")
	public void doReceive(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("接收ItemApp开始");
		
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "领料单号不能为空,接收失败!");
			return;
		};
		
		try {
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行接收操作!");
				return;
			}
			
			if (!"CONFIRMED".equals(ia.getStatus().trim())) {
				ServletUtils.outPrintFail(request, response, "领料单未处于【已审核】状态,无法进行接收操作!");
				return;
			}
			
			if (!"0".equalsIgnoreCase(ia.getSplStatus().trim()) && // 0.未接收
				!"1".equalsIgnoreCase(ia.getSplStatus().trim())) { // 1.接收退回 
				ServletUtils.outPrintFail(request, response, "只有【未接收】或【接收退回】的领料单才能进行接收操作!");
				return;
			}
			
			ia.setM_umState("Rev"); // Rev.供应商接收
			ia.setSplStatus("2"); // 2.已接收
			ia.setProductType(itemApp.getProductType());
			ia.setArriveDate(itemApp.getArriveDate());			
			ia.setSplRemark(itemApp.getSplRemark());
			ia.setOtherCost(itemApp.getOtherCost());
			ia.setOtherCostAdj(itemApp.getOtherCostAdj());
			ia.setLastUpdate(new Date());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ia.setExpired("F");
			ia.setActionLog("EDIT");
//			this.itemAppService.update(ia); //改用存储过程
//			ServletUtils.outPrintSuccess(request, response, "接收成功!", null);
			Result result = this.itemAppService.doItemAppForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法接收该领料单!");
		}
	}
	
	/**
	 * 接收ItemApp
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doReturn")
	public void doReturn(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("退回ItemApp开始");
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,退回失败");
			return;
		};
		
		try {
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行退回操作!");
				return;
			}
			
			if (!"2".equals(ia.getSplStatus().trim())) {
				ServletUtils.outPrintFail(request, response, "领料单未处于【接收】状态,无法进行退回操作!");
				return;
			}
			
			ia.setM_umState("RevR"); //RevR.接收退回
			ia.setSplStatus("1"); // 1.接收退回
			ia.setSplRemark(itemApp.getSplRemark());
			ia.setLastUpdate(new Date());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ia.setExpired("F");
			ia.setActionLog("EDIT");
//			ia.setStatus("OPEN");
//			this.itemAppService.update(ia); //改用存储过程
//			ServletUtils.outPrintSuccess(request, response, "退回成功", null);

			Result result = this.itemAppService.doItemAppForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法退回该领料单");
		}
	}
	
	/**
	 * 检查能否进行发货操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckSend")
	public void doCheckSend(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("ItemApp检查发货开始");
		
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,发货失败");
			return;
		};
		
		try {
			CustType custType = new CustType();
			Customer customer = new Customer();
			
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行退回操作!");
				return;
			}
			if(StringUtils.isNotBlank(ia.getCustCode())){
				customer = customerService.get(Customer.class, ia.getCustCode());
				if(customer != null && StringUtils.isNotBlank(customer.getCustType())){
					custType = customerService.get(CustType.class, customer.getCustType());
				}
			}
			
			if("2".equals(ia.getSendType().trim())){
				String authStr = getUserContext(request).getAuthStr();
				boolean authFlag = false;
				if(StringUtils.isNotBlank(authStr)){
					String[] authArr = authStr.split(",");
					for(int i = 0;i < authArr.length;i++){
						if("SUPPLIER_ITEMAPP_WAREHOUSESEND".equals(authArr[i])){
							authFlag = true;
							break;
						}
					}
				}
				if(!authFlag){
					ServletUtils.outPrintFail(request, response, "您没有仓库发货权限,无法进行发货操作");
					return;
				}	
			}
			
			Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
			if( czybm == null){
				ServletUtils.outPrintFail(request, response, "发货操作出现异常!");
				return;
			}
			//集成供应商，领料单要处于已确认未发货才能做发货
			//"JC".equals(ia.getItemType1().trim()) -> "2".equals(czybm.getSupplyRecvModel().trim()) update by zzr 2018/05/29
			if ("2".equals(czybm.getSupplyRecvModel().trim())) { 
				if (!"5".equals(ia.getSplStatus().trim())) { // 5.已确认未发货
					ServletUtils.outPrintFail(request, response, "领料单未处于【已确认未发货】状态,无法进行发货操作!");
					return;
				}
			} else {
				// 2.已接收;4 已确认;5 已确认未发货
				if (!"2".equals(ia.getSplStatus().trim()) && !"4".equals(ia.getSplStatus().trim()) && !"5".equals(ia.getSplStatus().trim())) { 
					ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】、【已确认】、【已确认未发货】状态,无法进行发货操作!");
					return;
				}
			}
			Supplier supplier = supplierService.get(Supplier.class, czybm.getEmnum());
			String isMaterialSendJob = itemAppService.isMaterialSendJob(ia);
			// 3.必须通知发货 2.可选通知发货 1.下单立即发货
			if ("3".equals(supplier.getSendMode().trim())) {
				if("false".equals(isMaterialSendJob) && !"3".equals(custType.getIsPartDecorate())){
					ServletUtils.outPrintFail(request, response, "未收到发货通知，不允许发货。");
					return;
				}
			}
			else if ("2".equals(supplier.getSendMode().trim())) { 
				if("false".equals(isMaterialSendJob)){
					ServletUtils.outPrintSuccess(request, response, "确认是否发货", "2");
					return;
				}
			}
			ServletUtils.outPrintSuccess(request, response, "可以发货", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "该领料单无法进行发货");
		}
	}
	
	/**
	 * 跳转到发货页面
	 * @return
	 */
	@RequestMapping("/goSend")
	public ModelAndView goSend(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到发货页面");
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);	
		resetItemApp(itemApp);
		itemApp.setIsTimeout(itemAppService.calcIsTimeout(itemApp));
		/*if (itemApp.getIsTimeout()) {
			itemApp.setDelayResonUnshowValue("0");
		};*/
		itemApp.setDelayResonUnshowValue(itemApp.getIsTimeout()?"0,11,12,13,14":"11,12,13,14");
		itemApp.setSendDate(new Date());
		
		String sendUrl = null;
		if ("1".equalsIgnoreCase(itemApp.getSendType().trim())) { //供应商直送
			sendUrl = "admin/supplier/itemApp/itemApp_send";
		} else { //仓库发货
			sendUrl = "admin/supplier/itemApp/itemApp_sendByWh";
		}
		return new ModelAndView(sendUrl).addObject("itemApp", itemApp)
				.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 查询明细JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetail(HttpServletRequest request,	
			HttpServletResponse response, String id) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		itemAppService.findDetailBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 检查能否进行修改操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckUpdate")
	public void doCheckUpdate(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("检查能否修改ItemApp");
		
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,修改失败");
			return;
		};
		
		try {
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行修改操作!");
				return;
			}
			
			if (!"2".equals(ia.getSplStatus().trim())) { // 2.已接收
				ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】状态,无法进行修改操作!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "可以修改", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "该领料单无法进行修改操作");
		}
	}

	/**
	 * 修改ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSend")
	public void doSend(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("ItemApp发货开始");
		try{
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单");
				return;
			}
			
			if("ZC".equals(ia.getItemType1().trim())){
				if(itemAppService.isComplete(itemApp.getNo(), "").size()>0){
					ServletUtils.outPrintFail(request, response, "领料单【"+itemApp.getNo()+"】存在未加工入库的材料，不允许进行发货");
					return;
				}
			}
			
			Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
			if( czybm == null){
				ServletUtils.outPrintFail(request, response, "发货操作出现异常!");
				return;
			}
			
			//集成供应商，领料单要处于已确认未发货才能做发货
			//"JC".equals(ia.getItemType1().trim()) -> "2".equals(czybm.getSupplyRecvModel().trim()) update by zzr 2018/05/29
			if ("2".equals(czybm.getSupplyRecvModel().trim())) { 
				if (!"5".equals(ia.getSplStatus().trim())) { // 5.已确认未发货
					ServletUtils.outPrintFail(request, response, "领料单未处于【已确认未发货】状态,无法进行发货操作!");
					return;
				}
			} else {
				// 2.已接收;4 已确认;5 已确认未发货
				if (!"2".equals(ia.getSplStatus().trim()) && !"4".equals(ia.getSplStatus().trim()) && !"5".equals(ia.getSplStatus().trim())) { 
					ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】、【已确认】、【已确认未发货】状态,无法进行发货操作!");
					return;
				}
			}
			
			ia.setM_umState("P");
			ia.setWhcode("1000");
			if (StringUtils.isNotBlank(itemApp.getDelayReson())) {
				ia.setDelayReson(itemApp.getDelayReson());
			} else {
				ia.setDelayReson("0");
			}
			ia.setSendDate(new Date());
			ia.setSendCzy(this.getUserContext(request).getCzybh());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ia.setExpired("F");
			ia.setDelayRemark(itemApp.getDelayRemark());
			ia.setOtherCost(itemApp.getOtherCost());
			ia.setOtherCostAdj(itemApp.getOtherCostAdj());
			ia.setSplRemark(itemApp.getSplRemark()); //发货时要填写备注 add by zb on 20200227
			
			Result result = this.itemAppService.doSendItemAppForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "发货失败");
		}
	}
	
	/**
	 * 检查能否进行接收操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckReceive")
	public void doCheckReceive(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("检查能否接收ItemApp");
		
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,接收失败");
			return;
		};
		
		try {
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行接收操作!");
				return;
			}
			
			if (!"CONFIRMED".equals(ia.getStatus().trim())) {
				ServletUtils.outPrintFail(request, response, "领料单未处于【已审核】状态,无法进行接收操作!");
				return;
			}
			
			if (!"0".equalsIgnoreCase(ia.getSplStatus().trim()) && // 0.未接收
				!"1".equalsIgnoreCase(ia.getSplStatus().trim())) { // 1.接收退回 
				ServletUtils.outPrintFail(request, response, "只有【未接收】或【接收退回】的领料单才能进行接收操作!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "可以接收", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "该领料单无法进行接收操作");
		}
	}
	
	/**
	 * 跳转到接收ItemApp页面
	 * @return
	 */
	@RequestMapping("/goReceive")
	public ModelAndView goReceive(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到接收ItemApp页面");
		
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		resetItemApp(itemApp);
		if (itemApp.getIsAutoArriveDate() == true) {
			Page<Map<String, Object>> arg = new Page<Map<String,Object>>();
			itemApp.setArriveDate((Date)itemAppService.findPageByProductType(arg, itemApp).getResult().get(0).get("arrivedate"));
		}
		itemApp.setProductType(""); //接收时，默认产品类型为空，必填
		Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
		if(czybm != null){
			itemApp.setSupplyRecvModel(czybm.getSupplyRecvModel().trim());
		}else{
			itemApp.setSupplyRecvModel("1");
		}
		return new ModelAndView("admin/supplier/itemApp/itemApp_receive")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * 检查能否进行退回操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckReturn")
	public void doCheckReturn(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("检查能否退回ItemApp");
		
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,退回失败");
			return;
		};
		
		try {
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行退回操作!");
				return;
			}
			
			if (!"2".equals(ia.getSplStatus().trim())) {
				ServletUtils.outPrintFail(request, response, "领料单未处于【接收】状态,无法进行退回操作!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "可以退回", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "该领料单无法进行退回操作");
		}
	}
	
	/**
	 * 跳转到退回ItemApp页面
	 * @return
	 */
	@RequestMapping("/goReturn")
	public ModelAndView goReturn(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到退回ItemApp页面");
		
		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		resetItemApp(itemApp);
		return new ModelAndView("admin/supplier/itemApp/itemApp_return")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * 跳转到批量打印ItemApp页面
	 * @return
	 */
	@RequestMapping("/goQPrint")
	public ModelAndView goQPrint(HttpServletRequest request, HttpServletResponse response, 
			ItemApp itemApp){
		logger.debug("跳转到批量打印ItemApp页面");

		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/supplier/itemApp/itemApp_qPrint")
			.addObject("itemApp", itemApp)
			.addObject("czybh", this.getUserContext(request).getCzybh());
	}
	/**
	 * 跳转到批量接收ItemApp页面
	 * @return
	 */
	@RequestMapping("/goReceiveItemAppBatch")
	public ModelAndView goReceiveItemAppBatch(HttpServletRequest request, 
			HttpServletResponse response){
		logger.debug("跳转到批量接收ItemApp页面");
		ItemApp itemApp = new ItemApp();
		itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		resetItemApp(itemApp);
		return new ModelAndView("admin/supplier/itemApp/itemApp_receiveBatch")
			.addObject("itemApp", itemApp);
	}
	/**
	 * 批量接收ItemApp
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doReceiveBatch")
	public void doReceiveBatch(HttpServletRequest request, HttpServletResponse response,
			String ids, Date arrivedate){
		logger.debug("批量接收ItemApp开始");
		
		if(StringUtils.isBlank(ids)){
			ServletUtils.outPrintFail(request, response, "领料单号不能为空,接收失败!");
			return;
		};
		
		try {
			String arr[] = ids.split(",");
			int ifail=0,isuccess=0;
			for (String str : arr){
				ItemApp ia = this.itemAppService.get(ItemApp.class, str);
				if (ia==null || !"CONFIRMED".equals(ia.getStatus().trim()) 
						|| (!"0".equals(ia.getSplStatus().trim()) && !"1".equals(ia.getSplStatus()))){
					ifail++;
					continue;
				}
				ia.setSplStatus("2"); // 2.已接收
				
				/*
				if (arrivedate != null) {
					ia.setArriveDate(arrivedate);
				} else {
					Page<Map<String, Object>> arg = new Page<Map<String,Object>>();
					ia.setArriveDate((Date)itemAppService.findPageByProductType(arg, ia).getResult().get(0).get("arrivedate"));
				}
				*/
				if ("1".equalsIgnoreCase(ia.getSendType().trim())) { //1.供应商直送
					if (itemAppService.calcIsAutoArriveDate(ia.getSupplCode())) {
						Page<Map<String, Object>> arg = new Page<Map<String,Object>>();
						ia.setArriveDate((Date)itemAppService.findPageByProductType(arg, ia).getResult().get(0).get("arrivedate"));
					} else {
						ia.setArriveDate(arrivedate);
					}
				} else { //2.仓库发货
					ia.setArriveDate(arrivedate);
				}
				
				ia.setLastUpdate(new Date());
				ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				ia.setExpired("F");
				ia.setActionLog("EDIT");
//				this.itemAppService.update(ia); //改用存储过程
				ia.setM_umState("Rev"); // Rev.供应商接收
				Result result = this.itemAppService.doItemAppForProc(ia);
				if (result.isSuccess()){
					isuccess++;
				} else {
					ifail++;
				}	
			}
			ServletUtils.outPrintSuccess(request, response, "接收成功【"+isuccess+"】条，失败【"+ifail+"】条！", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "接收失败!");
		}
	}
	/**
	 * 跳转到批量发货ItemApp页面
	 * @return
	 */
	@RequestMapping("/goSendItemAppBatch")
	public ModelAndView goSendItemAppBatch(HttpServletRequest request, 
			HttpServletResponse response){
		logger.debug("跳转到批量接收ItemApp页面");
		ItemApp itemApp = new ItemApp();
		Supplier supplier = supplierService.get(Supplier.class, this.getUserContext(request).getEmnum().trim());
		
		Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
		
		//集成需要控制供应商状态为已确认未发货才能发货，其他为已接收才能发货
		//"JC".equals(ia.getItemType1().trim()) -> "2".equals(czybm.getSupplyRecvModel().trim()) update by zzr 2018/05/29
		if ("2".equals(czybm.getSupplyRecvModel().trim())) { 
			itemApp.setSplStatus("5");
			itemApp.setSplStatusDescr("已确认未发货");
		} else {
			itemApp.setSplStatus("2,4,5");
			itemApp.setSplStatusDescr("已接收,已确认,已确认未发货");
		}
		return new ModelAndView("admin/supplier/itemApp/itemApp_sendBatch")
			.addObject("itemApp", itemApp);
	}
	/**
	 * 批量发货ItemApp
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doSendBatch")
	public void doSendBatch(HttpServletRequest request, HttpServletResponse response, String ids){
		logger.debug("批量发货ItemApp开始");
		
		if(StringUtils.isBlank(ids)){
			ServletUtils.outPrintFail(request, response, "领料单号不能为空,发货失败!");
			return;
		};
		
		try {
			String arr[] = ids.split(",");
			int ifail=0,isuccess=0,itimeout=0;
			String timeoutNo = "";
			Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
			for (String str : arr){
				ItemApp ia = this.itemAppService.get(ItemApp.class, str);
				Customer customer = new Customer();
				CustType custType = new CustType();
				
				if(StringUtils.isNotBlank(ia.getCustCode())){
					customer = customerService.get(Customer.class, ia.getCustCode());
					if(customer != null && StringUtils.isNotBlank(customer.getCustType())){
						custType = customerService.get(CustType.class, customer.getCustType());
					}
				}
				
				//非第二种模式，其它供应商“接收”、“已确认”、“已确认未发货”状态可以发货 modifiy by zzr 2018/05/29 begin
				boolean failFlag = false;
				if("2".equals(czybm.getSupplyRecvModel().trim())){
					if (!"5".equals(ia.getSplStatus().trim())) { // 5.已确认未发货
						failFlag = true;
					}
				}else{
					if(!"2".equals(ia.getSplStatus().trim()) && !"4".equals(ia.getSplStatus().trim()) && !"5".equals(ia.getSplStatus().trim())){
						failFlag = true;
					}
				}
				//非第二种模式，其它供应商“接收”、“已确认”、“已确认未发货”状态可以发货 modifiy by zzr 2018/05/29 end
				
/*				boolean authFlag = false;
				if("2".equals(ia.getSendType().trim())){
					String authStr = getUserContext(request).getAuthStr();
					if(StringUtils.isNotBlank(authStr)){
						String[] authArr = authStr.split(",");
						for(int i = 0;i < authArr.length;i++){
							if("SUPPLIER_ITEMAPP_WAREHOUSESEND".equals(authArr[i])){
								authFlag = true;
								break;
							}
						}
					}
				}else{
					authFlag = true;
				}*/

				if (ia==null || czybm == null || failFlag){// || !authFlag
					ifail++;
					continue;
				}
				if (itemAppService.calcIsTimeout(ia)) {
					itimeout++;
					ifail++;
					timeoutNo  = timeoutNo + "【" + str.trim() + "】";
					continue;
				}
				if ("1".equalsIgnoreCase(ia.getSendType().trim())) {
					ia.setM_umState("P");
					ia.setWhcode("1000");
				} else {
					ia.setM_umState("S");
				}
				ia.setSendDate(new Date());
				ia.setSendCzy(this.getUserContext(request).getCzybh());
				ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				ia.setExpired("F");
				
				if("ZC".equals(ia.getItemType1().trim())){
					if(itemAppService.isComplete(ia.getNo(), "").size()>0){
						ServletUtils.outPrintFail(request, response, "领料单【"+ia.getNo()+"】存在未加工入库的材料，不允许进行发货");
						return;
					}
				}
				
				Supplier supplier = supplierService.get(Supplier.class, czybm.getEmnum());
				String isMaterialSendJob = itemAppService.isMaterialSendJob(ia);
				// 3.必须通知发货 2.可选通知发货 1.下单立即发货
				if ("3".equals(supplier.getSendMode().trim())) {
					if("false".equals(isMaterialSendJob) && !"3".equals(custType.getIsPartDecorate())){
						ServletUtils.outPrintFail(request, response, "领料单【"+ia.getNo()+"】未收到发货通知，不允许发货。");
						return;
					}
				}
				
				Result result = this.itemAppService.doSendItemAppForProc(ia);
				if (result.isSuccess()){
					isuccess++;
				} else {
					ifail++;
				}
			}
			String msg = "发货成功【"+isuccess+"】条，失败【"+ifail+"】条，其中超过发货时限【"+itimeout+"】条！";
			if (itimeout>0) {
				msg = msg + "以下单据超时，请单独发货：" + timeoutNo;
			}
			ServletUtils.outPrintSuccess(request, response, msg, null);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "发货失败!");
		}
	}
	
	/**
	 * 计算预计到货日期
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 */
	@RequestMapping("/doCalcArriveDateBySendDay")
	@ResponseBody
	public WebPage<Map<String,Object>> doCalcArriveDateBySendDay(HttpServletRequest request,	
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		logger.debug("开始计算ItemApp的预计到货日期");
		ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
		ia.setProductType(itemApp.getProductType());
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.findPageByProductType(page, ia);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemApp itemApp){
		if(StringUtils.isBlank(itemApp.getNo())){
			SysLog sysLog = new SysLog();
			sysLog.setTitle("页面访问日志");
			sysLog.setType(SysLog.TYPE_EXCEPTION);
			sysLog.setAppType(SysLog.TYPE_BS);
			sysLog.setRemoteAddr(com.house.framework.commons.utils.StringUtils.getRemoteAddr(request));
			sysLog.setUserAgent(StringUtils.substring(request.getHeader("user-agent"),0,300));
			sysLog.setRequestUrl("/admin/supplierItemApp/doExcel");
			sysLog.setParams(request.getParameterMap());
			sysLog.setMethod(request.getMethod());
			sysLog.setOperId(getUserContext(request).getCzybh());
			sysLog.setOperDate(new Date());
			this.itemAppService.save(sysLog);
			return;
		}
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppService.findDetailBySql(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"领料明细_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
	/**
	 * 跳转到ItemApp明细查询页面
	 * @return
	 */
	@RequestMapping("/goDetailList")
	public ModelAndView goDetailList(HttpServletRequest request, HttpServletResponse response, 
			ItemApp itemApp){
		logger.debug("跳转到接收ItemApp页面");
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemApp.setConfirmDateFrom(new Date());
		itemApp.setConfirmDateTo(new Date());
		return new ModelAndView("admin/supplier/itemApp/itemApp_detailList")
			.addObject("itemApp", itemApp)
			.addObject("czybh", this.getUserContext(request).getCzybh())
			.addObject("projectCostRight", this.getUserContext(request).getProjectCostRight());
	}
	
	/**
	 * 明细查询表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetailList")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetailList(HttpServletRequest request,	
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemAppService.findDetailListBySql(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doExcelDetailList")
	public void doExcelDetailList(HttpServletRequest request, 
			HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemApp.setSupplCode(this.getUserContext(request).getEmnum());
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemAppService.findDetailListBySql(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"领料明细查询_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
	/**
	 * 修改ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSendByWh")
	public void doSendByWh(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("ItemApp发货开始");
		try{
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单");
				return;
			}
			
			if("ZC".equals(ia.getItemType1().trim())){
				if(itemAppService.isComplete(itemApp.getNo(), "").size()>0){
					ServletUtils.outPrintFail(request, response, "领料单【"+itemApp.getNo()+"】存在未加工入库的材料，不允许进行发货");
					return;
				}
			}
			
			Czybm czybm = itemAppService.get(Czybm.class, getUserContext(request).getCzybh());
			if( czybm == null){
				ServletUtils.outPrintFail(request, response, "发货操作出现异常!");
				return;
			}
			
			//集成供应商，领料单要处于已确认未发货才能做发货
			//"JC".equals(ia.getItemType1().trim()) -> "2".equals(czybm.getSupplyRecvModel().trim()) update by zzr 2018/05/29
			if ("2".equals(czybm.getSupplyRecvModel().trim())) { 
				if (!"5".equals(ia.getSplStatus().trim())) { // 5.已确认未发货
					ServletUtils.outPrintFail(request, response, "领料单未处于【已确认未发货】状态,无法进行发货操作!");
					return;
				}
			} else {
				// 2.已接收;4 已确认;5 已确认未发货
				if (!"2".equals(ia.getSplStatus().trim()) && !"4".equals(ia.getSplStatus().trim()) && !"5".equals(ia.getSplStatus().trim())) {
					ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】、【已确认】、【已确认未发货】状态,无法进行发货操作!");
					return;
				}
			}
			
			WareHouse wareHouse = wareHouseService.get(WareHouse.class, itemApp.getWhcode());
			//Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh()); remove by zzr 2018/05/29
			if (!wareHouseService.hasWhRight(czybm, wareHouse)) { 
				ServletUtils.outPrintFail(request, response, "您没有此仓库的权限,无法进行发货操作!");
				return;
			}
			
			ia.setM_umState("S");
			ia.setWhcode(itemApp.getWhcode());
			ia.setSendDate(new Date());
			ia.setSendCzy(this.getUserContext(request).getCzybh());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ia.setExpired("F");
			
			Result result = this.itemAppService.doSendItemAppForProc(ia);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			} else {
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "发货失败");
		}
	}
	
	/**
	 * 检查能否进行供应商确认操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckSplConfirm")
	public void doCheckSplConfirm(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("检查能否确认ItemApp");
		
		if(StringUtils.isBlank(itemApp.getNo())){
			ServletUtils.outPrintFail(request, response, "ItemApp编号不能为空,修改失败");
			return;
		};
		
		try {
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单,无法进行修改操作!");
				return;
			}
			
			if (!"2".equals(ia.getSplStatus().trim())) { // 2.已接收
				ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】状态,无法进行确认操作!");
				return;
			}
			
			if (!"JC".equals(ia.getItemType1().trim())) { // 集成领料单才需做供应商确认
				ServletUtils.outPrintFail(request, response, "集成领料单才需做供应商确认操作!");
				return;
			}
			
			ServletUtils.outPrintSuccess(request, response, "可以确认", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "该领料单无法进行确认操作");
		}
	}
	
	/**
	 * 跳转到供应商确认界面
	 * @return
	 */
	@RequestMapping("/goSplConfirm")
	public ModelAndView goSplConfirm(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到供应商确认页面");

		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		resetItemApp(itemApp);
		return new ModelAndView("admin/supplier/itemApp/itemApp_splConfirm")
			.addObject("itemApp", itemApp);
	}
	
	/**
	 * 修改ItemApp
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSplConfirm")
	public void doSplConfirm(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("修改ItemApp开始");
		
		try{
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if (ia == null) {
				ServletUtils.outPrintFail(request, response, "没有找到领料单，修改失败");
				return;
			}
			
			if (!"2".equals(ia.getSplStatus().trim())) { // 2.已接收
				ServletUtils.outPrintFail(request, response, "领料单未处于【已接收】状态,无法进行确认操作!");
				return;
			}
			
			if (!"JC".equals(ia.getItemType1().trim())) { // 集成领料单才需做供应商确认
				ServletUtils.outPrintFail(request, response, "集成领料单才需做供应商确认操作!");
				return;
			}
			
			String detailJson = request.getParameter("detailJson");
			ia.setDetailJson(detailJson);
			ia.setM_umState("SQ"); //SQ.供应商确认
			ia.setLastUpdate(new Date());
			ia.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ia.setExpired("F");
			ia.setActionLog("EDIT");
			if("1".equals(itemApp.getCanPass())){//直接审核通过
				itemApp.setLastUpdatedBy(getUserContext(request).getCzybh());
				itemAppService.doSuplCheck(itemApp);
				ServletUtils.outPrintSuccess(request, response,"操作成功！");
			}else{
				Result result = this.itemAppService.doItemAppForProc(ia);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,result.getInfo());
				} else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}			
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "供应商确认ItemApp失败");
		}
	}

	/**
	 * 跳转到水槽下单查询页面
	 * @return
	 */
	@RequestMapping("/goDishesSend")
	public ModelAndView goDishesSend(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("跳转到水槽下单页面");
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/supplier/itemApp/itemApp_dishesSend").addObject("itemApp", itemApp);
	}
	
	/**
	 * 明细查询表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDishesSend")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDishesSend(HttpServletRequest request,	 HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemAppService.goJqGridDishesSend(page, itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doExcelDishesSend")
	public void doExcelCupBoardSend(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemApp.setCzybh(this.getUserContext(request).getCzybh());
		itemAppService.goJqGridDishesSend(page, itemApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"水槽下单查询_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	/**
	 * 供应商下单金额是否小于我们下单金额
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkCost")
	public List<Map<String, Object>> checkCost(HttpServletRequest request,
			HttpServletResponse response,String no) {
		logger.debug("供应商下单金额是否小于我们下单金额");
		List<Map<String, Object>> list = itemAppService.checkCost(no);
		return list;
	}

	@RequestMapping("/doNotInstall")
	public ModelAndView doNotInstall(HttpServletRequest request, HttpServletResponse response, ItemApp itemApp){
		logger.debug("跳转到不能安装页面");
		IntProgDetail intProgDetail = new IntProgDetail();
		if(StringUtils.isNotBlank(itemApp.getNo())){
			ItemApp ia = this.itemAppService.get(ItemApp.class, itemApp.getNo());
			if(ia != null){
				Customer customer = this.itemAppService.get(Customer.class, ia.getCustCode());
				if(customer != null){
					intProgDetail.setAddress(customer.getAddress());
				}
				intProgDetail.setCustCode(ia.getCustCode());
				intProgDetail.setIsCupboard(ia.getIsCupboard());
			}
		}
		intProgDetail.setDate(new Date());
		intProgDetail.setType("2");
		intProgDetail.setResPart("2");
		return new ModelAndView("admin/supplier/itemApp/itemApp_doNotInstall").addObject("data", intProgDetail);
	}
	
	@RequestMapping("/doSaveNotInstall")
	public void doSaveNotInstall(HttpServletRequest request, HttpServletResponse response, IntProgDetail intProgDetail){
		logger.debug("保存IntProgDetail开始");
		try{
			intProgDetail.setActionLog("ADD");
			intProgDetail.setLastUpdatedBy(getUserContext(request).getCzybh());
			intProgDetail.setLastUpdate(new Date());
			intProgDetail.setExpired("F");
			this.itemAppService.save(intProgDetail);
			ServletUtils.outPrintSuccess(request, response,"操作成功！");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	/**
	 * 生产进度登记新增保存
	 * @author	created by zb
	 * @date	2020-3-4--上午9:38:46
	 * @param request
	 * @param response
	 * @param intProgDetail
	 */
	@RequestMapping("/doIntProduceSave")
	public void doIntProduceSave(HttpServletRequest request, HttpServletResponse response, IntProduce intProduce){
		logger.debug("生产进度登记新增保存开始");
		try{
			List<Map<String, Object>> custIntProgList = this.intProduceService.getCustIntProg(intProduce);
			List<Map<String, Object>> intProduceList = this.intProduceService.getIntProduce(intProduce);
			if (null != custIntProgList && custIntProgList.size()>0) {
				if (null != intProduceList && intProduceList.size() == 0) {
					this.intProduceService.doIntProdSave(intProduce);
					ServletUtils.outPrintSuccess(request, response,"操作成功！");
				} else {
					ServletUtils.outPrintFail(request, response, "楼盘、供应商、是否橱柜信息重复，不允许添加");
				}
			} else {
				ServletUtils.outPrintFail(request, response, "楼盘、供应商、是否橱柜与下单信息不一致，不允许保存");
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	@RequestMapping("/doIntProduceUpdate")
	public void doIntProduceUpdate(HttpServletRequest request, HttpServletResponse response, IntProduce intProduce){
		logger.debug("生产进度登记修改保存开始");
		try{
			if (null != intProduce.getPk()) {
				this.intProduceService.doIntProdUpdate(intProduce);
				ServletUtils.outPrintSuccess(request, response,"操作成功！");
			} else {
				ServletUtils.outPrintFail(request, response, "操作失败:缺少PK");
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	/**
	 * 跳转到结算申请页面
	 * @return
	 */
	@RequestMapping("/goCheckApp")
	public ModelAndView goCheckApp(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemApp页面");

		ItemApp itemApp = itemAppService.get(ItemApp.class, id);
		resetItemApp(itemApp);
		
		boolean hasAuthToCheckComfirm= false; 
		if(czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1845)){ 
			hasAuthToCheckComfirm= true;
		}
		return new ModelAndView("admin/supplier/itemApp/itemApp_checkApp")
			.addObject("itemApp", itemApp)
		    .addObject("hasAuthToCheckComfirm", hasAuthToCheckComfirm);
	}
	/**
	 * 查询明细goJqGridPuFeeDetail表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridPuFeeDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridPuFeeDetail(HttpServletRequest request,	
			HttpServletResponse response, String puno) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.goJqGridPuFeeDetail(page, puno);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增采购费用明细页面
	 * @return
	 */
	@RequestMapping("/goAddCheckApp")
	public ModelAndView goAddCheckApp(HttpServletRequest request, HttpServletResponse response){
		String m_umState=request.getParameter("m_umState").toString();
		return new ModelAndView("admin/supplier/itemApp/itemApp_addCheckApp")
				.addObject("m_umState", m_umState);
	}
	/**
	 * 跳转到修改采购费用明细页面
	 * @return
	 */
	@RequestMapping("/goUpdateCheckApp")
	public ModelAndView goUpdateCheckApp(HttpServletRequest request, HttpServletResponse response){
		String m_umState=request.getParameter("m_umState").toString();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/supplier/itemApp/itemApp_addCheckApp")
				.addObject("map", jsonObject).addObject("m_umState", m_umState);
	}
	/**
	 * 跳转到查看采购费用明细页面
	 * @return
	 */
	@RequestMapping("/goViewCheckApp")
	public ModelAndView goViewCheckApp(HttpServletRequest request, HttpServletResponse response){
		return goUpdateCheckApp(request, response);
	}
	/**
	 * 结算申请保存
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/doCheckApp")
	@ResponseBody
	public void doCheckApp(HttpServletRequest request,HttpServletResponse response,ItemApp itemApp){
		itemApp.setLastUpdatedBy(getUserContext(request).getCzybh());
		Result result = this.itemAppService.doCheckApp(itemApp);
		ServletUtils.outPrintSuccess(request, response,result.getInfo());
	}
	/**
	 * 查询明细goJqGridItemDetail表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridItemDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridItemDetail(HttpServletRequest request,	
			HttpServletResponse response, ItemApp itemApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.goJqGridItemDetail(page,itemApp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到不能安裝明细页面
	 * @return
	 */
	@RequestMapping("/goNotInstallDetail")
	public ModelAndView goNotInstallDetail(HttpServletRequest request, HttpServletResponse response, Customer customer){
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/supplier/itemApp/itemApp_notInstallDetail");
	}
	/**
	 * 查询明细goJqGridItemDetail表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goNotInstallDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridItemDetail(HttpServletRequest request,	
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.goNotInstallDetailJqGrid(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 图片文件查看下载地址
	 * 
	 * */
	public static String getCustDocDownloadPath(HttpServletRequest request, String fileName,String fileCustCode){
		String path = getCustDocPath(fileName,fileCustCode);
		return FileUploadUtils.DOWNLOAD_URL+path;
	}
	/**
	 * 获取文件上传地址
	 * 
	 * */
	public static String getCustDocPath(String fileName,String fileCustCode){
		String custDocNameNew = CustDocUploadRule.FIRST_LEVEL_PATH;
		if (StringUtils.isBlank(custDocNameNew)){
			fileName = "";
		}
		if (StringUtils.isNotBlank(fileName)){
			return custDocNameNew + fileCustCode + "/";
		}else{
			return CustDocUploadRule.FIRST_LEVEL_PATH;
		}
	}
	@RequestMapping("/goLoadIntPic")
	public ModelAndView goLoadIntPic(HttpServletRequest request ,
			HttpServletResponse response,String code,String confirmRemark ) throws Exception{
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, code);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1104);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0215", "管理员权限");
		String url = getCustDocDownloadPath(request,"000000",code);

		return new ModelAndView("admin/supplier/itemApp/itemApp_loadIntPic")
		.addObject("customer",customer).addObject("hasAuthByCzybh",hasAuthByCzybh).addObject("url",url);
	}
	/**
	 * 跳转到补货明细页面
	 * @return
	 */
	@RequestMapping("/goIntReplenishDetail")
	public ModelAndView goIntReplenishDetail(HttpServletRequest request, HttpServletResponse response, Customer customer){
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/supplier/itemApp/itemApp_intReplenishDetail");
	}
	/**
	 * 查询明细goIntReplenishDetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goIntReplenishDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goIntReplenishDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppService.goIntReplenishDetailJqGrid(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}
}
