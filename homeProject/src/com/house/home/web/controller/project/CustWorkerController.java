package com.house.home.web.controller.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.house.framework.commons.fileUpload.impl.WorkerSignPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.WorkerAppEvt;
import com.house.home.client.service.resp.WorkerPrjItemResp;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.JobType;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.entity.project.WorkSign;
import com.house.home.entity.project.Worker;
import com.house.home.entity.project.WorkerArrange;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustWorkerAppService;
import com.house.home.service.project.CustWorkerService;
import com.house.home.service.project.WorkCostService;
import com.house.home.service.project.WorkerArrangeService;
import com.house.home.service.project.WorkerService;
import com.house.home.service.query.PrjDelayAnalyService;

@Controller
@RequestMapping("/admin/custWorker")
public class CustWorkerController extends BaseController{
	private static final String CustWorker = null;
	@Autowired
	private CustWorkerService custWorkerService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustWorkerAppService custWorkerAppService;
	@Autowired
	private WorkCostService workCostService;
	@Autowired
	private PrjDelayAnalyService prjDelayAnalyService;
	@Autowired
	private WorkerArrangeService workerArrangeService;
	
	/**
	 * 主页面查询
	 * @param request
	 * @param response
	 * @param custWorker
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping("/goJqGrid")
		@ResponseBody
		public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
				HttpServletResponse response,CustWorker custWorker) throws Exception {
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			UserContext uc = this.getUserContext(request);
			String czybh=uc.getCzybh();
			custWorkerService.findPageBySql(page, custWorker,czybh, uc);
			return new WebPage<Map<String,Object>>(page);
		}
	 
	 @RequestMapping("/goViewSignJqGrid")
		@ResponseBody
		public WebPage<Map<String,Object>> goViewSignJqGrid(HttpServletRequest request,
				HttpServletResponse response,CustWorker custWorker) throws Exception {
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			String czybh=this.getUserContext(request).getCzybh();
			custWorkerService.findViewSignPageBySql(page, custWorker,czybh);
			return new WebPage<Map<String,Object>>(page);
		}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param custWorker
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWorkerDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWorkerDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		custWorkerService.findWorkerDetailPageBySql(page, custWorker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWaterAftInsItemAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWaterAftInsItemAppJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		custWorkerService.findWaterAftInsItemAppPageBySql(page, custWorker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWorkSignPic")
	@ResponseBody
	public WebPage<Map<String,Object>> goWorkSignPic(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		custWorkerService.getWorkSignPicBySql(page, custWorker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *工地工人安排列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response , CustWorker custWorker) throws Exception{
		logger.debug("工地工人主页面");
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 963);
		boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0321", "管理");
		
		custWorker.setStatus("1");
		custWorker.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/project/custWorker/custWorker_list")
			.addObject("custWorker",custWorker).addObject("czybm",this.getUserContext(request).getCzybh().trim())
			.addObject("hasAuthByCzybh", hasAuthByCzybh);
	}
	
	/**
	 * 新增页面
	 * @param request
	 * @param response
	 * @param custWorker
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response , CustWorker custWorker) throws Exception{
		logger.debug("工地工人安排主页面");
		
		Date today = DateUtil.getToday();
		custWorker.setComeDateFrom(today);
		custWorker.setComeDateTo(DateUtil.addDay(today, 14));
		return new ModelAndView("admin/project/custWorker/custWorker_save")
		.addObject("custWorker", custWorker).addObject("czybm",this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response , Integer pk,Integer apppk) throws Exception{
		logger.debug("工地工人编辑");

		CustWorker custWorker = null;
		Worker worker=null;
		Customer customer=null;
		Employee employee =null; 
		custWorker=custWorkerService.get(CustWorker.class, pk);
		
		custWorker.setWorkType12(custWorker.getWorkType12().trim());
		if(StringUtils.isNotBlank(custWorker.getWorkerCode())){
			worker=workerService.get(Worker.class, custWorker.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorker.getCustCode())){
			customer=customerService.get(Customer.class, custWorker.getCustCode());
		}
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			employee=employeeService.get(Employee.class,customer.getProjectMan());
		}
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean isWaterCtrlMan = czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),1495);
		boolean isWaterCtrlMan=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0321", "修改水电发包");
		return new ModelAndView("admin/project/custWorker/custWorker_update")
		.addObject("customer", customer)
		.addObject("czybm", this.getUserContext(request).getCzybh().trim())
		.addObject("employee", employee)
		.addObject("custWorker", custWorker)
		.addObject("worker", worker).addObject("apppk", apppk)
		.addObject("isWaterItemCtrl",customer.getIsWaterItemCtrl())
		.addObject("isWaterCtrlMan", isWaterCtrlMan);
	}
	
	@RequestMapping("/goModifyWater")
	public ModelAndView goModifyWater(HttpServletRequest request ,
			HttpServletResponse response , Integer pk,Integer apppk) throws Exception{
		logger.debug("工地工人编辑水电发包");
		String isWaterCostPay = "0";	//是否水电发放 默认否-0 add by zb on 20200327
		CustWorker custWorker = null;
		Worker worker=null;
		Customer customer=null;
		Employee employee =null; 
		custWorker=custWorkerService.get(CustWorker.class, pk);
		
		custWorker.setWorkType12(custWorker.getWorkType12().trim());
		if(StringUtils.isNotBlank(custWorker.getWorkerCode())){
			worker=workerService.get(Worker.class, custWorker.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorker.getCustCode())){
			customer=customerService.get(Customer.class, custWorker.getCustCode());
		}
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			employee=employeeService.get(Employee.class,customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(custWorker.getCustCode())){
			isWaterCostPay = workCostService.isWaterCostPay(custWorker.getCustCode());
		}
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean isWaterCtrlMan = czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),1495);
		boolean isWaterCtrlMan=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0321", "修改水电发包");
		return new ModelAndView("admin/project/custWorker/custWorker_modifyWater")
		.addObject("customer", customer)
		.addObject("czybm", this.getUserContext(request).getCzybh())
		.addObject("employee", employee)
		.addObject("custWorker", custWorker)
		.addObject("worker", worker).addObject("apppk", apppk)
		.addObject("isWaterItemCtrl",customer.getIsWaterItemCtrl())
		.addObject("isWaterCtrlMan", isWaterCtrlMan)
		.addObject("isWaterCostPay", isWaterCostPay);
	}
	
	/**
	 * 查看
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response , Integer pk,Integer actualDays) throws Exception{
		logger.debug("工地工人编辑");
		CustWorker custWorker = null;
		Worker worker=null;
		Customer customer=null;
		Employee employee =null;
		custWorker=custWorkerService.get(CustWorker.class, pk);
		custWorker.setWorkType12(custWorker.getWorkType12().trim());
		if(StringUtils.isNotBlank(custWorker.getWorkerCode())){
			worker=workerService.get(Worker.class, custWorker.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorker.getCustCode())){
			customer=customerService.get(Customer.class, custWorker.getCustCode());
		}
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			employee=employeeService.get(Employee.class,customer.getProjectMan());
		}
		return new ModelAndView("admin/project/custWorker/custWorker_view")
		.addObject("customer", customer).addObject("czybm", this.getUserContext(request).getCzybh())
		.addObject("employee", employee).addObject("custWorker", custWorker)
		.addObject("worker", worker).addObject("isWaterItemCtrl",customer.getIsWaterItemCtrl())
		.addObject("actualDays",actualDays);
	}
	
	@RequestMapping("/goWaterAftInsItemApp")
	public ModelAndView goWaterAftInsItemApp(HttpServletRequest request ,
			HttpServletResponse response , CustWorker custWorker) throws Exception{
		logger.debug("查看水电后期安装材料页面");
		
		return new ModelAndView("admin/project/custWorker/custWorker_waterAftInsItemApp")
			.addObject("custWorker",custWorker);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWorkerDetail")
	public ModelAndView goWorkerDetail(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("跳转到资源客户主页面");
		
		return new ModelAndView("admin/project/custWorkerApp/custWorker_workerDetail");
	}
	
	@RequestMapping("/goViewSign")
	public ModelAndView goViewSign(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		logger.debug("跳转到资源客户主页面");
		CustWorker custWorker=new CustWorker();
		custWorker.setDateFrom(DateUtil.addMonth(new Date(), -1));
		custWorker.setDateTo(new Date());
		return new ModelAndView("admin/project/custWorker/custWorker_viewSign").addObject("custWorker",custWorker)
				.addObject("czybm", this.getUserContext(request).getCzybh());
	}
	/**
	 * 增加查看某个工地签到明细按钮
	 * @author	created by zb
	 * @date	2020-2-25--下午3:18:42
	 * @param request
	 * @param response
	 * @param custCode
	 * @param workType12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewSignDetail")
	public ModelAndView goViewSignDetail(HttpServletRequest request,
			HttpServletResponse response, String custCode, String workType12) throws Exception {
		return new ModelAndView("admin/project/custWorker/custWorker_view_signDetail")
			.addObject("custCode", custCode)
			.addObject("workType12",workType12);
	}
	
	@RequestMapping("/goViewPrjProg")
	public ModelAndView goViewPrjProg(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		PrjProgTemp prjProgTemp=null;
		customer = prjDelayAnalyService.get(Customer.class, customer.getCode());
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
			prjProgTemp=prjDelayAnalyService.get(PrjProgTemp.class,customer.getPrjProgTempNo());
			customer.setPrjProgTempNoDescr(prjProgTemp.getDescr());
		}
		String custTypeDescr="";
		if(StringUtils.isNotBlank(customer.getCustType())){
			custTypeDescr = customerService.get(CustType.class, customer.getCustType()).getDesc1();
		}
		String buildStatusDescr="";
		buildStatusDescr=prjDelayAnalyService.getMoreInfo(customer.getCode()).get("buildstatusdescr")+"";
		String planEnd="";
		Date planEndDate=null;
		int delayDays=0;
		if(customer.getConfirmBegin()!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar =new GregorianCalendar(); 
			calendar.setTime(customer.getConfirmBegin());
			calendar.add(Calendar.DATE,customer.getConstructDay());
			planEndDate = calendar.getTime();
			delayDays=Integer.parseInt(((new Date().getTime()-planEndDate.getTime())/1000/60/60/24)+"");
			planEnd=sdf.format(planEndDate);
		}
		Map<String,Object> customerPayMap = customerService.getCustomerPayByCode(customer.getCode());
		Map<String,Object> balanceMap=customerService.getShouldBanlance(customer.getCode());
		customerPayMap.put("zjzje", customerService.getCustomerZjzjeByCode(customer.getCode()));
		customerPayMap.put("haspay", customerService.getCustomerHaspayByCode(customer.getCode()));
		return new ModelAndView("admin/query/prjDelayAnaly/prjDelayAnaly_view")
			.addObject("customer", customer)
			.addObject("customerPayMap", customerPayMap)
			.addObject("balanceMap", balanceMap)
			.addObject("planEnd",planEnd)
			.addObject("delayDays",delayDays)
			.addObject("custTypeDescr",custTypeDescr)
			.addObject("buildStatusDescr", buildStatusDescr);
	}
	
	/**
	 * 跳转到延误原因页面
	 * @return
	 */
	@RequestMapping("/goDelayRemark")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改JobType页面");
		CustWorker custWorker = null;
		if (pk!=null){
			custWorker = custWorkerService.get(CustWorker.class, pk);
		}else{
			custWorker = new CustWorker();
		}
		return new ModelAndView("admin/project/custWorker/custWorker_delayRemark")
			.addObject("custWorker", custWorker);
	}
	
	/**
	 * 新增保持操作
	 * @param request
	 * @param response
	 * @param custWorker
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response ,CustWorker custWorker){
		logger.debug("工人工地安排新增");
		Customer customer=null;
		try{
			
			if("1".equals(custWorker.getCheckArrFlag()) && custWorkerService.getIsExistsWorkerArr(custWorker)){
				ServletUtils.outPrintFail(request, response, "该工地已安排同一工人！");
				return;
			}
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custWorker.setExpired("F");
			custWorker.setAciontLog("Add");
			custWorker.setIsSysArrange("0");
			if("02".equals(custWorker.getWorkType12())){
				customer=customerService.get(Customer.class, custWorker.getCustCode());
				if(customer!=null){
					customer.setIsWaterItemCtrl(custWorker.getIsWaterItemCtrl());
					customer.setLastUpdate(new Date());
					customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					customerService.update(customer);
				}
			}
			
			custWorkerService.save(custWorker);
			//工地工人安排、工人申请管理，如果工种对应的开始施工节点开始时间为空，自动填写对应施工节点的开始时间。 add by zb on 20191126
			custWorkerService.updateBeginDateByWorkType12(custWorker);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param custWorker
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response ,CustWorker custWorker,Integer apppk){
		logger.debug("工人工地安排编辑");
		CustWorker cw=null;
		CustWorkerApp custWorkerApp=null;
		Customer customer=null;
		try{
			if(custWorker.getPk()!=null){
				cw=custWorkerService.get(CustWorker.class, custWorker.getPk());
				cw.setLastUpdate(new Date());
				cw.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				cw.setAciontLog("Edit");
				cw.setWorkerCode(custWorker.getWorkerCode());
				cw.setRemarks(custWorker.getRemarks());
				cw.setStatus(custWorker.getStatus());
				cw.setConstructDay(custWorker.getConstructDay());
				cw.setPlanEnd(custWorker.getPlanEnd());
				if(custWorker.getEndDate()!=null){
					cw.setEndDate(custWorker.getEndDate());
				}
				if(custWorker.getComeDate()!=null){
					cw.setComeDate(custWorker.getComeDate());
				}
				if(!"".equals(apppk)&&apppk!=null){
					custWorkerApp=custWorkerAppService.get(CustWorkerApp.class, apppk);
					custWorkerApp.setWorkerCode(cw.getWorkerCode());
					custWorkerApp.setComeDate(cw.getComeDate());
					custWorkerApp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					custWorkerApp.setLastUpdate(new Date());
					custWorkerApp.setActionLog("Edit");
					this.custWorkerAppService.update(custWorkerApp);
				}
				if("1".equals(custWorker.getCheckArrFlag()) && custWorkerService.getIsExistsWorkerArr(cw)){
					ServletUtils.outPrintFail(request, response, "该工地已安排同一工人！");
					return;
				}
				if("02".equals(custWorker.getWorkType12())){
					customer=customerService.get(Customer.class, custWorker.getCustCode());
					if(customer!=null){
						customer.setIsWaterItemCtrl(custWorker.getIsWaterItemCtrl());
						customer.setLastUpdate(new Date());
						customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
						customerService.update(customer);
					}
				}
				
				this.custWorkerService.update(cw);
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDel")
	public void doDel(HttpServletRequest request ,
			HttpServletResponse response ,Integer pk){
		logger.debug("工人工地安排删除");
		CustWorker cw=null;
		try{
			if(pk!=null){
			    List<Map<String, Object>> workerArranges =
			            workerArrangeService.getWorkerArrangeByCustWorkPk(pk);
			    			    
			    if (workerArranges.size() > 0) {
			        ServletUtils.outPrintFail(request, response, "删除失败，请从工人排班管理中进行退号删除");
			        return;
                }
			    
				cw=custWorkerService.get(CustWorker.class, pk);
				this.customerService.delete(cw);
			}
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除失败");
		}
	}
	
	/**
	 * 获取客户工作标准工期
	 * @param request
	 * @param response
	 * @param custCode
	 * @param workType12
	 * @return
	 */
	@RequestMapping("/getConstructDay")
	@ResponseBody
	public String getConstructDay(HttpServletRequest request,HttpServletResponse response,
			String custCode,String workType12){

		return custWorkerAppService.getWorkTypeConDay(custCode, workType12);
	}
	
	@RequestMapping("/goViewAllPic")
	public ModelAndView goViewAllPic(HttpServletRequest request, HttpServletResponse response
			,String no ,String custCode){
		logger.debug("跳转查看签到图片页面");
		WorkerSignPicUploadRule rule = new WorkerSignPicUploadRule("",custCode,"");
		String photoPath= FileUploadUtils.DOWNLOAD_URL+rule.getFilePath();
		
		//workSign = custWorkerService.get(WorkSign.class, no);
		return new ModelAndView("admin/project/custWorker/custWorker_allPic")
				.addObject("photoPath",photoPath).addObject("no",no);
	}
	
	@RequestMapping("/updateIsPushCust")
	public void updateIsPushCust(HttpServletRequest request, HttpServletResponse response, CustWorker custWorker){
		logger.debug("编辑isPustCust");
		try{
				custWorkerService.updateIsPushCust(custWorker);
				ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/updateIsPushCustAll")
	public void updateIsPushCustAll(HttpServletRequest request, HttpServletResponse response,CustWorker custWorker){
		logger.debug("编辑");
		try{
			custWorkerService.updateIsPushCustAll(custWorker);
				ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param custWorker
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustWorker custWorker){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = this.getUserContext(request);
		String czybh=uc.getCzybh();
		custWorker.setIsDoExcel("1");
		custWorkerService.findPageBySql(page, custWorker,czybh, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地工人安排表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	@RequestMapping("/getCustWorker")
	@ResponseBody
	public JSONObject getCustWorker(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		CustWorker custWorker = custWorkerService.get(CustWorker.class, id);
		if(custWorker == null){
			return this.out("系统中不存在pk="+id+"的材工人安排记录", false);
		}
		return this.out(custWorker, true);
	}
	
	
	@RequestMapping("/getWorkType12Dept")
	@ResponseBody
	public List<Map<String, Object>> getWorkType12Dept(HttpServletRequest request,HttpServletResponse response,String workType12){
		
		return custWorkerService.getWorkType12Dept(workType12);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		return new ModelAndView("admin/project/custWorker/custWorker_code").addObject("custWorker",custWorker);
	}
	
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCodeJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustWorker custWorker) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerService.findCodePageBySql(page, custWorker);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/doSignComp")
	@ResponseBody
	public void signComp(HttpServletRequest request,HttpServletResponse response, Integer pk){
		try{
			CustWorker custWorker=custWorkerService.get(CustWorker.class, pk);
			WorkerAppEvt evt = new WorkerAppEvt();
			WorkerPrjItemResp respon = new WorkerPrjItemResp();
			evt.setCode(custWorker.getWorkerCode());
			evt.setCustCode(custWorker.getCustCode());
			evt.setWorkType12(custWorker.getWorkType12());
			evt.setCustWkPk(pk);
			List<Map<String, Object>> list = workerService.getNotCompeletePrjItem(evt);
			System.out.println(list.get(0).get("PrjItem2"));
			for(int i=0;i<list.size();i++){
				WorkSign workSign = new WorkSign();
				workSign.setCustCode(list.get(i).get("CustCode").toString());
				workSign.setWorkerCode(list.get(i).get("WorkerCode").toString());
				workSign.setPrjItem2(list.get(i).get("PrjItem2").toString());
				workSign.setAddress(list.get(i).get("Address").toString());
				workSign.setCrtDate(new Date());
				workSign.setCustWkPk(Integer.parseInt(list.get(i).get("CustWkPk").toString()));
				workSign.setNo(workerService.getSeqNo("tWorkSign"));
				workSign.setIsLeaveProblem("0");
				workSign.setLeaveProblemRemark(null);
				workSign.setLastUpdatedBy(getUserContext(request).getCzybh());
				workSign.setIsComplete("1");
				workerService.save(workSign);
			}
			ServletUtils.outPrintSuccess(request, response,"签到完成成功");
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "签到完成失败");
		}
	}
	
	/**
	 * 修改延误原因
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDelayRemark")
	public void doDelayRemark(HttpServletRequest request, HttpServletResponse response, CustWorker cw){
		logger.debug("修改custWorker开始");
		try{
			CustWorker custWorker=custWorkerService.get(CustWorker.class, cw.getPk());
			custWorker.setComeDelayType(cw.getComeDelayType());
			custWorker.setEndDelayType(cw.getEndDelayType());
			custWorker.setSignDelayType(cw.getSignDelayType());
			custWorker.setLastUpdate(new Date());
			custWorker.setLastUpdatedBy(getUserContext(request).getCzybh());
			custWorker.setAciontLog("EDIT");
			this.custWorkerService.update(custWorker);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改custWorker失败");
		}
	}
	
	/**
	 * 工人签到表单条记录设置完成
	 * 
	 * @param request
	 * @param response
	 * @param workSignPk
	 * @author 张海洋
	 */
	@RequestMapping("/doComplete")
    public void doComplete(HttpServletRequest request, HttpServletResponse response, Integer workSignPk) {
	    
	    try {
	        
            Result result = custWorkerService.doComplete(request, response,
                    workSignPk, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "更新失败，程序异常");
        }
	    
    }
	
	/**
	 * 工人签到表单条记录设置未完成
	 * 
	 * @param request
	 * @param response
	 * @param workSignPk
	 * @author 张海洋
	 */
    @RequestMapping("/undoComplete")
    public void undoComplete(HttpServletRequest request, HttpServletResponse response, Integer workSignPk) {
        
        try {
            
            Result result = custWorkerService.undoComplete(request, response,
                    workSignPk, getUserContext(request));
            
            if (result.isSuccess()) {
                ServletUtils.outPrintSuccess(request, response, result.getInfo());
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "更新失败，程序异常");
        }
        
    }
    
    @RequestMapping("/goViewLog")
	public ModelAndView goViewLog(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		return new ModelAndView("admin/project/custWorker/custWorker_viewLog").addObject("custWorker",custWorker);
	}
    
	/**
	 * 
	 * @param request
	 * @param response
	 * @param custWorker
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLogJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLogJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		custWorkerService.goLogJqGrid(page, custWorker);
		return new WebPage<Map<String,Object>>(page);
	}
}
