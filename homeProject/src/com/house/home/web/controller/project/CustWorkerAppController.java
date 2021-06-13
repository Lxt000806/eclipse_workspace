package com.house.home.web.controller.project;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.SpecItemReq;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustWorkerAppService;
import com.house.home.service.project.CustWorkerService;
import com.house.home.service.project.WorkCostDetailService;
import com.house.home.service.project.WorkerService;
import com.house.home.web.controller.insales.PurchaseController;
import com.sun.org.apache.xpath.internal.operations.And;

@Controller
@RequestMapping("/admin/CustWorkerApp")
public class CustWorkerAppController extends BaseController{
	
	private static final Logger logger =LoggerFactory.getLogger(PurchaseController.class);

	@Autowired
	private CustWorkerAppService custWorkerAppService;
	@Autowired
	private CustWorkerService custWorkerService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PersonMessageService personMessageService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private WorkCostDetailService workCostDetailService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		custWorkerAppService.findPageBySql(page, custWorkerApp,uc.getCzybh(), uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWorkerDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWorkerDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findWorkerDetailPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getReturnDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getReturnDetail(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.getReturnDetail(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrintJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrintJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerApp.setStatus("1");
		custWorkerAppService.findPrintPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goNoArrGRJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getNoArrGRJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findNoArrGRPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goNoArrGdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getNoArrGdJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findNoArrGdPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goHasArrGdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getHasArrGdJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findHasArrGdPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goQQGZLJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getQQGZLJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findQQGZLPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goFSGZLJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getFSGZLJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findFSGZLPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSMGZLJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getSMGZLJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findSMGZLPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goMZGZLJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getMZGZLJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findMZGZLPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getWorkerArrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getWorkerArrJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findWorkerArrPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goZFDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goZFDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findZFDetailPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goItemArrJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemArrJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findItemArrPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goItemArrDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemArrDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Integer pk,String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findItemArrDetailPageBySql(page, pk,custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWorkTypeBefJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWorkTypeBefJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findWorkTypeBefPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 *ResrCust列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response , CustWorkerApp custWorkerApp) throws Exception{
		logger.debug("跳转到资源客户主页面");
		
		custWorkerApp.setStatus("1");
		custWorkerApp.setCzybh(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_list")
			.addObject("CustWorkerApp",custWorkerApp).addObject("czybm",this.getUserContext(request).getCzybh());
	}
	
	/**
	 *ResrCust列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goPrint")
	public ModelAndView goPrint(HttpServletRequest request ,
			HttpServletResponse response , CustWorkerApp custWorkerApp) throws Exception{
		logger.debug("跳转到资源客户主页面");
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_print")
			.addObject("CustWorkerApp",custWorkerApp);
	}
	
	
	/**
	 *工程部工人申请——新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response , CustWorkerApp custWorkerApp) throws Exception{
		logger.debug("跳转到资源客户主页面");

		Date today = DateUtil.getToday();
		custWorkerApp.setAppDateFrom(today);
		custWorkerApp.setAppDateTo(DateUtil.addDay(today, 14));
		custWorkerApp.setAppDate(new Date());
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_save")
			.addObject("CustWorkerApp",custWorkerApp).addObject("czybm",this.getUserContext(request).getCzybh());
	}
	
	/**
	 *工程部工人申请——新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goSaveArr")
	public ModelAndView goSaveArr(HttpServletRequest request ,
			HttpServletResponse response , Integer pk,String prjnormday,String prjRegion
			,String arrTimes) throws Exception{
		logger.debug("跳转到工人申请页面");
		CustWorkerApp custWorkerApp =null;
		CustWorker custWorker =new CustWorker();
		Customer customer=new Customer();
		Worker worker=null;
		String isWaterItemCtrl="";
		if(pk!=null){
			custWorkerApp=custWorkerAppService.get(CustWorkerApp.class,pk);
		}
		if(custWorkerApp.getWorkerCode()!=null){
			worker=workerService.get(Worker.class, custWorkerApp.getWorkerCode());
			custWorkerApp.setWorkerDescr(worker==null?"":worker.getNameChi());
		}
		customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
		if(customer!=null){
			isWaterItemCtrl=customer.getIsWaterItemCtrl();
		}
		String workTypeConDay= custWorkerAppService.getWorkTypeConDay(customer.getCode(), custWorkerApp.getWorkType12()); 
		custWorkerApp.setCustDescr(customer.getDescr());
		custWorkerApp.setAddress(customer.getAddress());
		custWorkerApp.setAppDateFrom(DateUtil.getToday());
		custWorkerApp.setAppDateTo(DateUtil.addDay(custWorkerApp.getAppDateFrom(), 14));
		
		//是否满足1.上工种初检验收 2.材料到货 3.款项已齐   重构by cjg 20200806
		String befWorkType12ConfString=custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "1");
		String itemArrive=custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "2");
		String moneyCtrl = custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "3");
		
		//在安排橱柜安装、衣柜安装工人时显示以上内容。供安装部经理查看。 add by zb on 20200108
		SpecItemReq specItemReq = new SpecItemReq();
		if (StringUtils.isNotBlank(custWorkerApp.getCustCode()) && StringUtils.isNotBlank(custWorkerApp.getWorkType12())) {
			if ("17".equals(custWorkerApp.getWorkType12().trim()) || "18".equals(custWorkerApp.getWorkType12().trim())) {
				specItemReq = custWorkerAppService.get(SpecItemReq.class, custWorkerApp.getCustCode());
			}
		}
		String befTaskCompleted = getBefTaskComplete(request, custWorkerApp);
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_saveArr")
			.addObject("custWorkerApp",custWorkerApp)
			.addObject("custWorker",custWorker).addObject("prjRegion", prjRegion)
			.addObject("prjnormday",prjnormday).addObject("workTypeConDay", workTypeConDay)
			.addObject("arrTimes", arrTimes).addObject("befWorkType12ConfString",befWorkType12ConfString)
			.addObject("itemArrive",itemArrive).addObject("isWaterItemCtrl",isWaterItemCtrl)
			.addObject("specItemReq", specItemReq).addObject("moneyCtrl", moneyCtrl)
			.addObject("befTaskCompleted", befTaskCompleted).addObject("isHoliConstruct", customer.getIsHoliConstruct());
	}
	
	/**
	 *工程部工人申请——新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goUpdateArr")
	public ModelAndView goUpdateArr(HttpServletRequest request ,
			HttpServletResponse response , Integer pk,String prjnormday,String prjRegion
			,String arrTimes) throws Exception{
		logger.debug("跳转到资源客户主页面");
		CustWorkerApp custWorkerApp =null;
		Worker worker=null;
		Customer customer=new Customer();
		CustWorker custWorker=null;
		String isWaterItemCtrl="";
		if(pk!=null){
			custWorkerApp=custWorkerAppService.get(CustWorkerApp.class,pk);
		}
		if(custWorkerApp.getWorkerCode()!=null){
			worker=workerService.get(Worker.class, custWorkerApp.getWorkerCode());
			custWorkerApp.setWorkerDescr(worker==null?"":worker.getNameChi());
		}
		if(custWorkerApp.getCustWorkPk()!=null){
			custWorker=custWorkerService.get(CustWorker.class, custWorkerApp.getCustWorkPk());
			custWorkerApp.setConstructStatus(custWorker==null?"":custWorker.getStatus());
			custWorkerApp.setComeDelayType(custWorker.getComeDelayType());
		}
		customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
		if(customer!=null){
			isWaterItemCtrl=customer.getIsWaterItemCtrl();
		}
		
		//是否满足1.上工种初检验收 2.材料到货 3.款项已齐   重构by cjg 20200806
		String befWorkType12ConfString=custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "1");
		String itemArrive=custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "2");
		String moneyCtrl = custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "3");

		
		customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
		String workTypeConDay= custWorkerAppService.getWorkTypeConDay(customer.getCode(), custWorkerApp.getWorkType12()); 
		custWorkerApp.setCustDescr(customer.getDescr());
		custWorkerApp.setAddress(customer.getAddress());
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean isWaterCtrlMan = czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),1495);
		boolean isWaterCtrlMan=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0321", "修改水电发包");

		//在安排橱柜安装、衣柜安装工人时显示以上内容。供安装部经理查看。 add by zb on 20200108
		SpecItemReq specItemReq = new SpecItemReq();
		if (StringUtils.isNotBlank(custWorkerApp.getCustCode()) && StringUtils.isNotBlank(custWorkerApp.getWorkType12())) {
			if ("17".equals(custWorkerApp.getWorkType12().trim()) || "18".equals(custWorkerApp.getWorkType12().trim())) {
				specItemReq = custWorkerAppService.get(SpecItemReq.class, custWorkerApp.getCustCode());
			}
		}
		String befTaskCompleted = getBefTaskComplete(request, custWorkerApp);
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_updateArr")
			.addObject("custWorkerApp",custWorkerApp).addObject("custWorker",custWorker)
			.addObject("prjnormday",prjnormday).addObject("workTypeConDay", workTypeConDay)
			.addObject("prjRegion", prjRegion).addObject("arrTimes",arrTimes).addObject("befWorkType12ConfString",befWorkType12ConfString)
			.addObject("itemArrive",itemArrive).addObject("isWaterItemCtrl",isWaterItemCtrl)
			.addObject("isWaterCtrlMan", isWaterCtrlMan).addObject("specItemReq", specItemReq).addObject("moneyCtrl", moneyCtrl)
			.addObject("befTaskCompleted", befTaskCompleted).addObject("isHoliConstruct", customer.getIsHoliConstruct());
	}
	
	
	/**
	 *工程部工人申请——新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goUpdateApp")
	public ModelAndView goUpdateApp(HttpServletRequest request ,
			HttpServletResponse response , Integer pk) throws Exception{
		logger.debug("跳转到资源客户主页面");
		CustWorkerApp custWorkerApp =null;
		Customer customer=new Customer();
		if(pk!=null){
			custWorkerApp=custWorkerAppService.get(CustWorkerApp.class,pk);
			customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
			custWorkerApp.setCustDescr(customer.getDescr());
			custWorkerApp.setAddress(customer.getAddress());
			custWorkerApp.setProjectMan(customer.getProjectMan());
		}
		if(customer.getProjectMan()!=""&&customer.getProjectMan()!=null){
			custWorkerApp.setProjectManDescr(customerService.get(Employee.class, customer.getProjectMan()).getNameChi());
		}
		custWorkerApp.setWorkType12(custWorkerApp.getWorkType12().trim());
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_updateApp")
			.addObject("custWorkerApp",custWorkerApp).addObject("czybm",this.getUserContext(request).getCzybh());
	}
	
	/**
	 *工程部工人申请——新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response , Integer pk,String arrTimes) throws Exception{
		logger.debug("跳转到资源客户主页面");
		CustWorkerApp custWorkerApp =null;
		Customer customer=new Customer();
		CustWorker custWorker=null;
		Worker worker =null;
		if(pk!=null){
			custWorkerApp=custWorkerAppService.get(CustWorkerApp.class,pk);
		}
		if(custWorkerApp.getWorkerCode()!=null){
			worker=workerService.get(Worker.class, custWorkerApp.getWorkerCode());
			custWorkerApp.setWorkerDescr(worker==null?"":worker.getNameChi());
		}
		if(custWorkerApp.getCustWorkPk()!=null){
			custWorker=custWorkerService.get(CustWorker.class, custWorkerApp.getCustWorkPk());
			custWorkerApp.setConstructStatus(custWorker==null?"":custWorker.getStatus());
		}
		
		//是否满足1.上工种初检验收 2.材料到货 3.款项已齐   重构by cjg 20200806
		String befWorkType12ConfString=custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "1");
		String itemArrive=custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "2");
		String moneyCtrl = custWorkerAppService.getCanArr(custWorkerApp.getCustCode(), custWorkerApp.getWorkType12(), "3");
		
		customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
		custWorkerApp.setAddress(customer.getAddress());
		custWorkerApp.setCustDescr(customer.getDescr());
		String befTaskCompleted = getBefTaskComplete(request, custWorkerApp);
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_view")
			.addObject("custWorkerApp",custWorkerApp).
			addObject("custWorker",custWorker).addObject("arrTimes", arrTimes).addObject("befWorkType12ConfString",befWorkType12ConfString)
			.addObject("itemArrive",itemArrive).addObject("isWaterItemCtrl",customer.getIsWaterItemCtrl())
			.addObject("moneyCtrl",moneyCtrl)
			.addObject("befTaskCompleted",befTaskCompleted)
			.addObject("isHoliConstruct", customer.getIsHoliConstruct());
	}
	
	
	@RequestMapping("/goViewWorker")
	public ModelAndView goViewWorker(HttpServletRequest request ,
			HttpServletResponse response , String custCode,String prjnormday,String workType12) throws Exception{
		logger.debug("跳转到查看工人明细");
		Customer customer=new Customer();
		customer=customerService.get(Customer.class, custCode);
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewWorker")
			.addObject("customer",customer).addObject("prjnormday",prjnormday).addObject("workType12", workType12);
	}
	
	@RequestMapping("/goViewItem")
	public ModelAndView goViewItem(HttpServletRequest request ,
			HttpServletResponse response , String custCode,String workType12) throws Exception{
		logger.debug("跳转到查看材料到货情况");
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewItem")
			.addObject("custCode",custCode).addObject("workType12",workType12);
	}
	
	@RequestMapping("/goViewBef")
	public ModelAndView goViewBef(HttpServletRequest request ,
			HttpServletResponse response , String custCode,String workType12) throws Exception{
		logger.debug("跳转到上工种验收情况");
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewBef")
			.addObject("custCode",custCode).addObject("workType12",workType12);
	}
	
	@RequestMapping("/goViewWorkerArr")
	public ModelAndView goViewWorkerArr(HttpServletRequest request ,
			HttpServletResponse response , Customer customer) throws Exception{
		logger.debug("跳转到查看安排情况");
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewWorkerArr")
			.addObject("customer",customer);
	}
	
	
	/**
	 *工程部工人申请——新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */ 
	@RequestMapping("/goWorkerDetail")
	public ModelAndView goWorkerDetail(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("跳转到资源客户主页面");
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_workerDetail");
	}
	
	@RequestMapping("/goViewAutoArr")
	public ModelAndView goViewAutoArr(HttpServletRequest request ,HttpServletResponse response){
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewAutoArr");
	}
	
	@RequestMapping("/goViewReturn")
	public ModelAndView goViewReturn(HttpServletRequest request ,HttpServletResponse response){
		CustWorkerApp custWorkerApp = new CustWorkerApp();
		custWorkerApp.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		custWorkerApp.setDateTo(DateUtil.endOfTheMonth(new Date()));
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewReturn")
		.addObject("custWorkerApp",custWorkerApp);
	}
	
	@RequestMapping("/goViewZF")
	public ModelAndView goViewZF(HttpServletRequest request ,HttpServletResponse response,String custCode){
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewZF").addObject("custCode",custCode);
	}
	
	@RequestMapping("/goViewBefTask")
	public ModelAndView goViewBefTask(HttpServletRequest request ,HttpServletResponse response
			,String custCode,String workType12){
		
		return new ModelAndView("admin/project/custWorkerApp/custWorkerApp_viewBefTask")
		.addObject("custCode",custCode)
		.addObject("workType12", workType12);
	}

	/**
	 *工程部工人申请新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response ,CustWorkerApp custWorkerApp){
		logger.debug("新增资源客户信息开始");
			Worker worker=new Worker();
		try{
			CustWorkerApp cwa=custWorkerAppService.getByCode(custWorkerApp.getCustCode(),custWorkerApp.getWorkType12());
			if (cwa!=null){
				if(cwa.getStatus().equals("1")){
					ServletUtils.outPrintFail(request, response, "已安排工人");//该楼盘存在待安排的同种工人申请单，无法再次申请
					return ;
				}else {
					if(StringUtils.isNotBlank(cwa.getWorkerCode())){
						worker=workerService.get(Worker.class, cwa.getWorkerCode());
					}
					ServletUtils.outPrintFail(request, response, "该楼盘已申请过同工种的工人，当前工人是"
								+worker.getNameChi()+",是否继续申请");
					return;
				}
			}
			if(custWorkerApp.getAppDate()==null){
				custWorkerApp.setAppDate(new Date());
			}
			custWorkerApp.setActionLog("Add");
			custWorkerApp.setExpired("F");
			custWorkerApp.setLastUpdate(new Date());
			custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.custWorkerAppService.save(custWorkerApp);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 *工程部工人申请新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doSave2")
	public void doSave2(HttpServletRequest request ,
			HttpServletResponse response ,CustWorkerApp custWorkerApp){
		logger.debug("新增工地工人安排开始");
		try{
			if(custWorkerApp.getAppDate()==null){
				custWorkerApp.setAppDate(new Date());
			}
			custWorkerApp.setActionLog("Add");
			custWorkerApp.setExpired("F");
			custWorkerApp.setLastUpdate(new Date());
			custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.custWorkerAppService.save(custWorkerApp);

			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 *工程部工人申请新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doUpdateApp")
	public void doUpdateApp(HttpServletRequest request ,
			HttpServletResponse response ,CustWorkerApp custWorkerApp){
		logger.debug("新增资源客户信息开始");
		CustWorkerApp cwa=custWorkerAppService.get(CustWorkerApp.class, custWorkerApp.getPk());
		try{
			if(custWorkerApp.getAppDate()==null){
				custWorkerApp.setAppDate(cwa.getAppDate());
			}
			if(custWorkerApp.getAppComeDate()==null){
				custWorkerApp.setAppComeDate(cwa.getAppComeDate());
			}
			CustWorkerApp cWApp=custWorkerAppService.getByCode(custWorkerApp.getCustCode(),custWorkerApp.getWorkType12());
			if (cWApp!=null&& !cWApp.getPk().equals(custWorkerApp.getPk())){
					ServletUtils.outPrintFail(request, response, "该楼盘已申请过工种:"+custWorkerApp.getWorkType12());
					return;
			}
			custWorkerApp.setActionLog("Edit");
			custWorkerApp.setExpired("F");
			custWorkerApp.setLastUpdate(new Date());
			custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custWorkerApp.setProgTempAlarmPk(cwa.getProgTempAlarmPk());
			this.custWorkerAppService.update(custWorkerApp);

			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工人安排失败");
		}
	}
	
	
	/**
	 *工程部工人申请新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doSaveArr")	
	public void doSaveArr(HttpServletRequest request ,
			HttpServletResponse response ,CustWorkerApp custWorkerApp){
		logger.debug("工人安排保存");
		String planEndString=request.getParameter("planEnd");
		Date planEnd =DateFormatString1(planEndString);
		CustWorkerApp cwa=custWorkerAppService.get(CustWorkerApp.class, custWorkerApp.getPk());
		CustWorker custWorker=new CustWorker();
		Customer customer=null;
		try{
			CustWorkerApp cwa1=custWorkerAppService.getByWorkerCode(custWorkerApp.getCustCode(),
					custWorkerApp.getWorkType12(),custWorkerApp.getWorkerCode());
			if(custWorkerApp.getAppDate()==null){
				custWorkerApp.setAppDate(cwa1.getAppDate());
			}
			if(custWorkerApp.getAppComeDate()==null){
				custWorkerApp.setAppComeDate(cwa1.getAppComeDate());
			}
			custWorkerApp.setActionLog("Edit");
			custWorkerApp.setExpired("F");
			custWorkerApp.setAppDate(cwa.getAppDate());
			custWorkerApp.setLastUpdate(new Date());
			custWorkerApp.setStatus("2");
			custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custWorkerApp.setProgTempAlarmPk(cwa.getProgTempAlarmPk());
			custWorkerApp.setArrDate((cwa.getArrDate()!=null?cwa.getArrDate():DateUtil.getNow()));
			
			custWorker.setPlanEnd(planEnd);
			custWorker.setEndDate(custWorkerApp.getEndDate());
			custWorker.setConstructDay(custWorkerApp.getConstructDay());
			custWorker.setStatus("1");
			custWorker.setIsSysArrange("0");
			custWorker.setWorkerCode(custWorkerApp.getWorkerCode());
			custWorker.setWorkType12(custWorkerApp.getWorkType12());
			custWorker.setCustCode(custWorkerApp.getCustCode());
			custWorker.setComeDate(custWorkerApp.getComeDate());
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custWorker.setRemarks(custWorker.getRemarks());
			custWorker.setAciontLog("Add");
			custWorker.setExpired("F");
			custWorker.setComeDelayType(custWorkerApp.getComeDelayType());
			Serializable serializable = custWorkerService.save(custWorker);//生成工地工人安排数据
			//Map<String , Object> map= custWorkerAppService.getCustPk();
			int pk = Integer.parseInt(serializable.toString());
			custWorkerApp.setCustWorkPk(pk);
			this.custWorkerAppService.update(custWorkerApp);//保存工人申请安排
			
			//安排完工人自动读掉消息
			this.custWorkerAppService.readMsg(cwa);
			
			if(StringUtils.isNotBlank(custWorkerApp.getCustCode())&&"02".equals(custWorkerApp.getWorkType12())){
				customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
				if(customer!=null){
					customer.setIsWaterItemCtrl(custWorkerApp.getIsWaterItemCtrl());
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					customerService.update(customer);
				}
			}
			//工地工人安排、工人申请管理，如果工种对应的开始施工节点开始时间为空，自动填写对应施工节点的开始时间。 add by zb on 20191126
			custWorkerService.updateBeginDateByWorkType12(custWorker);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工人安排失败");
		}
	}
	
	/**
	 *工程部工人申请新增
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/doUpdateArr")
	public void doUpdateArr(HttpServletRequest request ,
			HttpServletResponse response ,CustWorkerApp custWorkerApp){
		logger.debug("修改工人安排开始");
		String planEndString=request.getParameter("planEnd");
		String endDateString=request.getParameter("endDate");
		Date planEnd =DateFormatString1(planEndString);
		Date endDate=null;
		Customer customer=null;

		if(endDateString!=""&&endDateString!=null){
			 endDate =DateFormatString1(endDateString);
		}
		CustWorkerApp cwa=custWorkerAppService.get(CustWorkerApp.class, custWorkerApp.getPk());
		CustWorker custWorker =new CustWorker();
		try{
			CustWorkerApp cwa1=custWorkerAppService.getByWorkerCode(custWorkerApp.getCustCode(),custWorkerApp.getWorkType12(),custWorkerApp.getWorkerCode());
			if (cwa1!=null&&!cwa1.getPk().equals(custWorkerApp.getPk())){
					ServletUtils.outPrintFail(request, response, "此楼盘该工种已存在该工人，无法再次安排");
					return ;
			}
			
			if(StringUtils.isNotBlank(custWorkerApp.getWorkerCode())){
				if(custWorkerApp.getAppDate()==null){
					custWorkerApp.setAppDate(cwa.getAppDate());
				}
				if(custWorkerApp.getAppComeDate()==null){
					custWorkerApp.setAppComeDate(cwa.getAppComeDate());
				}
				if(custWorkerApp.getComeDate()==null){
					custWorkerApp.setComeDate(cwa.getComeDate());
				}
				
				custWorkerApp.setActionLog("Edit");
				custWorkerApp.setExpired("F");
				custWorkerApp.setStatus("2");
				custWorkerApp.setAppDate(cwa.getAppDate());
				custWorkerApp.setLastUpdate(new Date());
				custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custWorkerApp.setCustWorkPk(cwa.getCustWorkPk());
				custWorkerApp.setProgTempAlarmPk(cwa.getProgTempAlarmPk());
				custWorkerApp.setArrDate((cwa.getArrDate()!=null?cwa.getArrDate():DateUtil.getNow()));
				this.custWorkerAppService.update(custWorkerApp);
				if(custWorkerApp.getCustWorkPk()!=null){
					custWorker=custWorkerAppService.get(CustWorker.class, custWorkerApp.getCustWorkPk());
					if(custWorker!=null){
						custWorker.setComeDate(custWorkerApp.getComeDate());
						custWorker.setWorkerCode(custWorkerApp.getWorkerCode());
						custWorker.setConstructDay(custWorkerApp.getConstructDay());
						custWorker.setIsSysArrange("0");
						custWorker.setPlanEnd(planEnd);
						custWorker.setEndDate(endDate);
						custWorker.setStatus(custWorkerApp.getConstructStatus());
						custWorker.setLastUpdate(new Date());
						custWorker.setLastUpdatedBy(this.getUserContext(request).getCzybh());
						custWorker.setComeDelayType(custWorkerApp.getComeDelayType());
						custWorkerService.update(custWorker);
					}
				}else{
					custWorker.setStatus("1");
					custWorker.setWorkerCode(custWorkerApp.getWorkerCode());
					custWorker.setWorkType12(custWorkerApp.getWorkType12());
					custWorker.setCustCode(custWorkerApp.getCustCode());
					custWorker.setComeDate(custWorkerApp.getComeDate());
					custWorker.setLastUpdate(new Date());
					custWorker.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					custWorker.setRemarks(custWorker.getRemarks());
					custWorker.setConstructDay(custWorkerApp.getConstructDay());
					custWorker.setIsSysArrange("0");
					custWorker.setPlanEnd(planEnd);
					custWorker.setEndDate(endDate);
					custWorker.setStatus(custWorkerApp.getConstructStatus());
					custWorker.setAciontLog("Add");
					custWorker.setExpired("F");
					custWorker.setComeDelayType(custWorkerApp.getComeDelayType());
					custWorkerService.save(custWorker);
					Map<String , Object> map= custWorkerAppService.getCustPk();
					int pk = (Integer) map.get("pk");
					custWorkerApp.setCustWorkPk(pk);
					this.custWorkerAppService.update(custWorkerApp);
					
					//安排完工人自动读掉消息
					this.custWorkerAppService.readMsg(cwa);
				
				}
			}else{
				if(custWorkerApp.getAppDate()==null){
					custWorkerApp.setAppDate(cwa.getAppDate());
				}
				if(custWorkerApp.getAppComeDate()==null){
					custWorkerApp.setAppComeDate(cwa.getAppComeDate());
				}
				if(custWorkerApp.getComeDate()==null){
					custWorkerApp.setComeDate(cwa.getComeDate());
				}
				custWorkerApp.setActionLog("Edit");
				custWorkerApp.setExpired("F");
				custWorkerApp.setStatus("1");
				custWorkerApp.setComeDate(null);
				custWorkerApp.setLastUpdate(new Date());
				custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custWorker=custWorkerService.get(CustWorker.class, cwa.getCustWorkPk());
				if(custWorker!=null){
					custWorkerService.delete(custWorker);
				}
				this.custWorkerAppService.update(custWorkerApp);
		
				
			}
			if(StringUtils.isNotBlank(custWorkerApp.getCustCode())&&"02".equals(custWorkerApp.getWorkType12())){
				customer=customerService.get(Customer.class, custWorkerApp.getCustCode());
				if(customer!=null){
					customer.setIsWaterItemCtrl(custWorkerApp.getIsWaterItemCtrl());
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					customerService.update(customer);
				}
			}

			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工人安排失败");
		}
	}
	@RequestMapping("/ajaxGetProjectManDescr")
	@ResponseBody
	public CustWorkerApp ajaxGetProjectManDescr(HttpServletRequest request, HttpServletResponse response,String no){
		Employee employee=null;
		CustWorkerApp custWorkerApp=new CustWorkerApp();
		
		if(no!=null){
			employee=employeeService.get(Employee.class, no);
			custWorkerApp.setProjectMan(no);
			custWorkerApp.setProjectManDescr(employee.getNameChi());
			return custWorkerApp;
		}else{
			custWorkerApp.setProjectMan("");
			custWorkerApp.setProjectManDescr("");
			return custWorkerApp;
		}
	}
	
	@RequestMapping("/getConstructDay")
	@ResponseBody
	public int getConstructDay(HttpServletRequest request,HttpServletResponse response,
			String custCode,String workerCode){
		
		Map<String, Object> map= this.custWorkerAppService.getConstructDay(custCode, workerCode);
		
		int constructDay=(Integer)map.get("constructDay");
		
		return constructDay;
	}
	
	@RequestMapping("/isNeedZF")
	@ResponseBody
	public void isNeedZF(HttpServletRequest request,HttpServletResponse response,
			String custCode){
		if("true".equals(this.custWorkerAppService.isNeedZF(custCode))){
			ServletUtils.outPrintSuccess(request, response);
		}else{
			ServletUtils.outPrintFail(request, response,"");
		}
	}
	
	
	@RequestMapping("/doDel")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除PrjProg开始");
		
		this.custWorkerAppService.doDel(pk);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doReturn")
	public void deReturn(HttpServletRequest request, HttpServletResponse response, CustWorkerApp cwa){
		logger.debug("删除PrjProg开始");
			CustWorkerApp custWorkerApp=null;
			Customer customer =null;
			WorkType12 workType12=null;
			PersonMessage personMessage=new PersonMessage();
			custWorkerApp=custWorkerAppService.get(CustWorkerApp.class, cwa.getPk());
			if(custWorkerApp!=null){
				custWorkerApp.setStatus("0");
				custWorkerApp.setRemarks(custWorkerApp.getRemarks()==null?"":custWorkerApp.getRemarks()+"/t/t退回原因："+cwa.getRemarks());
				custWorkerApp.setLastUpdate(new Date());
				custWorkerApp.setLastUpdatedBy( this.getUserContext(request).getCzybh());
				custWorkerApp.setActionLog("Edit");
				custWorkerAppService.update(custWorkerApp);
				workType12=customerService.get(WorkType12.class, custWorkerApp.getWorkType12());
				customer = customerService.get(Customer.class, custWorkerApp.getCustCode());
				if(customer!=null){
					personMessage.setMsgType("17");
					personMessage.setMsgText(customer.getAddress()+":申请"+workType12.getDescr()+"工人被退回，原因:"+cwa.getRemarks());
					personMessage.setMsgRelNo(null);
					personMessage.setMsgRelCustCode(custWorkerApp.getCustCode());
					personMessage.setCrtDate(new Date());
					personMessage.setSendDate(new Date());
					personMessage.setRcvType("1");
					personMessage.setRcvCzy(customer.getProjectMan());
					personMessage.setIsPush("1");
					personMessage.setPushStatus("0");
					personMessage.setRcvStatus("0");
					personMessageService.save(personMessage);
				}
			}
			
		ServletUtils.outPrintSuccess(request, response,"退回成功");
	}
	
	@RequestMapping("/doCancel")
	public void doCancel(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("删除PrjProg开始");
		
		this.custWorkerAppService.doCancel(pk,this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"取消成功");
	}
	
	@RequestMapping("/doAutoArr")
	public void doAutoArr(HttpServletRequest request, HttpServletResponse response){
		logger.debug("自动安排开始");
		
		this.custWorkerAppService.doAutoArr(this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"自动安排成功");
	}
	
	@RequestMapping("/getNeedWorkType2Req")
	@ResponseBody
	public boolean getNeedWorkType2Req(HttpServletRequest request,HttpServletResponse response,
			String custCode,String workType12){
		
		return custWorkerAppService.getNeedWorkType2Req(custCode,workType12);
	}
	
	@RequestMapping("/getNotify")
	@ResponseBody
	public boolean getNotify(HttpServletRequest request,HttpServletResponse response,
			CustWorkerApp custWorkerApp){
		CustWorkerApp cwa1=custWorkerAppService.getByWorkerCode(custWorkerApp.getCustCode(),
				custWorkerApp.getWorkType12(),custWorkerApp.getWorkerCode());
		if (cwa1!=null){
			ServletUtils.outPrintFail(request, response, "此楼盘该工种已存在该工人，是否再次安排");
				return false ;
		}
		ServletUtils.outPrintFail(request, response, "通过");
		return true;
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustWorkerApp custWorkerApp){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = this.getUserContext(request);
		custWorkerAppService.findPageBySql(page, custWorkerApp,uc.getCzybh(), uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人申请表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doReturnDetailExcel")
	public void doReturnDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			CustWorkerApp custWorkerApp){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		custWorkerAppService.getReturnDetail(page, custWorkerApp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工人申请退回表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public  Date DateFormatString1(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
        return date;
	}

	public String getHasPaid(HttpServletRequest request,CustWorkerApp custWorkerApp){
		String result = "满足";
		boolean checkPayInfoFlag = true;
		CustWorkerAppEvt evt = new CustWorkerAppEvt();
		evt.setCustCode(custWorkerApp.getCustCode());
		if("11".equals(custWorkerApp.getWorkType12())){
			Map<String,Object> map = custWorkerAppService.specialReqForApply(evt);
			if( map != null){
				if("0".equals(map.get("hasReq").toString())){
					checkPayInfoFlag = true;//当无瓷砖需求，判断款项是否交齐
				}else{
					checkPayInfoFlag = false; //有瓷砖需求，只要存在瓷砖发货或者审核通过的领料单，可以申请防水班组
				}
			}
		}
		evt.setWorkType12(custWorkerApp.getWorkType12());
		WorkType12 cwa = custWorkerAppService.get(WorkType12.class, evt.getWorkType12());
		
		if(cwa.getPayNum() != null){
			int moneyCtrl = 5000;
			Map<String,Object> payType = custWorkerAppService.getCustPayType(evt.getCustCode());
			if(Integer.parseInt(cwa.getPayNum().trim()) > 1&&"0.0".equals(payType.get("FourPay").toString())){
				cwa.setPayNum(Integer.parseInt(cwa.getPayNum().trim())-1+"");
			}
			Map<String,Object> isSign = custWorkerAppService.isSignEmp(this.getUserContext(request).getCzybh().trim());
			if("1".equals(isSign.get("IsSupvr").toString().trim())){
				moneyCtrl = 5000;
			}
			Map<String,Object> map = custWorkerAppService.checkCustPay(evt,cwa.getPayNum());

			Double shouldBanlance = Double.parseDouble(String.valueOf(map.get("shouldBanlance").toString()));
			if(shouldBanlance > moneyCtrl){
				result = "不满足";
			}
		}
		return result;
	}
	
	public String getBefTaskComplete(HttpServletRequest request,CustWorkerApp custWorkerApp){
		String result = "完成";
		List<Map<String, Object>> list = custWorkerAppService.getBefTaskComplete(custWorkerApp);
		if(list!=null && list.size()>0){
			result = "未完成";
		}
		
		return result;
	}
	
	@RequestMapping("/goDeJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goDeJqGrid(
			HttpServletRequest request, HttpServletResponse response,
			CustWorker custWorker) throws Exception {
		WorkType12 workType12 = custWorkerAppService.get(WorkType12.class, custWorker.getWorkType12());
		WorkType2 workType2 = new WorkType2();
		custWorker.setWorkType2(workType12.getOfferWorkType2());
		custWorker.setSalaryCtrlType("0");
		if(StringUtils.isNotEmpty(workType12.getOfferWorkType2())){
			workType2 = workerService.get(WorkType2.class, workType12.getOfferWorkType2());
			if(workType2!=null){
				custWorker.setSalaryCtrlType(workType2.getSalaryCtrlType());
			}
		}
		
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.goDeJqGrid(page, custWorker);
		return new WebPage<Map<String, Object>>(page);
	}
}
