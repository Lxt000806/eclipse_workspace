package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Role;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustCheckData;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.BaseItemChgService;
import com.house.home.service.project.CustCheckDataService;
import com.house.home.service.project.GcxxglService;
import com.house.home.service.project.ItemChgService;

@Controller
@RequestMapping("/admin/gcxxgl")
public class GcxxglController extends BaseController{

	@Autowired
	private CustomerService customerService;
	@Autowired
	private GcxxglService gcxxglService;
	@Autowired
	private CustStakeholderService custStakeholderService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustCheckDataService custCheckDataService;
	@Autowired
	private ItemChgService itemChgService;
	@Autowired
	private BaseItemChgService baseItemChgService;
	
	/**
	 * 主页面查看
	 * @param request 
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		gcxxglService.findPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		gcxxglService.findDetailPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDelayDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDelayDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		gcxxglService.findDelayDetailPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goTotalDelayJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goTotalDelayJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		gcxxglService.findTotalDelayPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/**
	 *工程信息列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ,Customer customer) throws Exception{
		customer.setStatus("4");
		customer.setConstructStatus("0,1,2,3");
		
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "gcxxglSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		String colModel = FileHelper.readTxtFile(filePath+fileName);
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_list").addObject("customer",customer)
				.addObject("colModel", colModel);
	}

	/**
	 * 工程信息录入页面
	 * @return
	 * 
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,HttpServletResponse response,
			Customer ct){
		logger.debug("跳转到工程信息页面");
		Customer customer=null;
		Employee employee=null;
		Employee emp=null;
		Employee empl=null;
		Employee emplo=null;
		
		//CustStakeholder custStakeholder=custStakeholderService.get(clazz, id);
		customer=customerService.get(Customer.class, ct.getCode());
		customer.setPlanPrjDescr(ct.getPlanPrjDescr());
		customer.setNowPrjDescr(ct.getNowPrjDescr());
		customer.setBusinessFlowDescr(ct.getBusinessFlowDescr());
		customer.setDelayDay(ct.getDelayDay());
		customer.setPrjManCost(ct.getPrjManCost());
		customer.setConsManCost(ct.getConsManCost());
		customer.setJczj(ct.getJczj());
		customer.setZjxhj(ct.getZjxhj());
		customer.setSoftCost(ct.getSoftCost());
		customer.setBaseCost(ct.getBaseCost());
		customer.setInteCost(ct.getInteCost());
		customer.setMainCost(ct.getMainCost());
		customer.setPreloftsMan(ct.getPreloftsMan());
		customer.setOldPreloftsMan(ct.getPreloftsMan());
		customer.setDepartment2(ct.getDepartment2());
		customer.setHavePay(ct.getHavePay());
		customer.setCustCountCost(ct.getCustCountCost());
		System.out.println(customer.getMainItemOk());
		if(customer.getProjectMan()!=null&&customer.getProjectMan()!=""){
			employee=employeeService.get(Employee.class, customer.getProjectMan());
			customer.setProjectManDescr(employee==null?"":employee.getNameChi());
		}
		if(customer.getCheckMan()!=null&&customer.getCheckMan()!=""){
			emp=employeeService.get(Employee.class, customer.getCheckMan());
			customer.setCheckManDescr(emp==null?"":emp.getNameChi());
		}
		if(ct.getPreloftsMan()!=null&&ct.getPreloftsMan()!=""){
			empl=employeeService.get(Employee.class, ct.getPreloftsMan());
			customer.setPreloftsManDescr(empl==null?"":empl.getNameChi());
		}
		if(customer.getDesignMan()!=null&&customer.getDesignMan()!=""){
			emplo=employeeService.get(Employee.class, customer.getDesignMan());
			customer.setDesignManDescr(emplo==null?"":emplo.getNameChi());
		}
		String designPicStatus=customerService.getDesignPicStatus(customer.getCode());
		
		customerService.isHoliConstruct(customer);
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_save")
					.addObject("customer",customer).addObject("employee",emp)
					.addObject("getConstDay",this.gcxxglService.getConstDay())
					// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//					.addObject("updateConfirmDate",this.czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),759))
//					.addObject("updateCustCheckDate",this.czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(),760))
					.addObject("updateConfirmDate",this.czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0310","修改开工令时间"))
					.addObject("updateCustCheckDate",this.czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0310","修改实际结算日期"))
					.addObject("designPicStatus",designPicStatus);
	}
	/**
	 * 录入项目经理
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goInsertPrjMan")
	public ModelAndView goInsertPrjMan(HttpServletRequest request,HttpServletResponse response,
			Customer customer){
		logger.debug("跳转到工程信息页面");
		
		Customer ct = customerService.get(Customer.class, customer.getCode());

		return new ModelAndView("admin/project/gcxxgl/gcxxgl_insertPrjMan")
					.addObject("customer",ct);
	}
	
	/**
	 * 修改项目经理
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goUpdatePrjMan")
	public ModelAndView goUpdatePrjMan(HttpServletRequest request,HttpServletResponse response,
			Customer customer){
		logger.debug("跳转到工程信息页面");
		Employee employee = new Employee();
		Customer ct = customerService.get(Customer.class, customer.getCode());
		if(StringUtils.isNotBlank(ct.getProjectMan())){
			employee = employeeService.get(Employee.class, ct.getProjectMan());
		}
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_updatePrjMan")
					.addObject("customer",ct).addObject("employee", employee);
	}
	
	/**
	 * 编辑客户电话
	 * @return
	 * 
	 */
	@RequestMapping("/goUpdatePhone")
	public ModelAndView goUpdatePhone(HttpServletRequest request,HttpServletResponse response,
			String  no){
		logger.debug("跳转到编辑客户电话页面");
		Customer customer=null;
		customer=customerService.get(Customer.class, no);
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_updatePhone")
					.addObject("customer",customer);
	}
	/**
	 * 编辑软装设计师
	 * 
	 * @return
	 * 
	 */
	@RequestMapping("/goUpdateDesign")
	public ModelAndView goUpdateDesign(HttpServletRequest request,HttpServletResponse response,
			String  no,String empCode,Integer softPK){
		logger.debug("跳转到修改设计师页面");
		Customer customer=null;
		CustStakeholder custStakeholder =null;
		CustStakeholder softStakeholder =null;
		Employee employee=null;
		Employee softSteward=null;
		Integer softStewardPk=gcxxglService.getCustStakeholderPk(no,"65");
		if(softStewardPk!=null){
			softStakeholder=custStakeholderService.get(CustStakeholder.class, softStewardPk);
		}
		if(softStakeholder!=null){
			softSteward=employeeService.get(Employee.class,softStakeholder.getEmpCode());
		}
		customer=customerService.get(Customer.class, no);
			if(softPK!=null){
				custStakeholder=custStakeholderService.get(CustStakeholder.class, softPK);
				customer.setSoftPK(softPK);
				customer.setSoftEmpCode(custStakeholder.getEmpCode());
				employee=employeeService.get(Employee.class, custStakeholder.getEmpCode());
				customer.setDesignManDescr(employee.getNameChi());
			}
			if(softSteward!=null){
				customer.setSoftSteward(softSteward.getNumber());
				customer.setSoftStewardDescr(softSteward.getNameChi());
				customer.setSoftStewardPk(softStakeholder.getPk());
			}
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_updateDesigner")
					.addObject("customer",customer);
	}
	
	/**
	 * 编辑主材管家
	 * 
	 * @return
	 * 
	 */
	@RequestMapping("/goUpdateMainManager")
	public ModelAndView goUpdateMainManager(HttpServletRequest request,HttpServletResponse response,
			String  no){
		logger.debug("跳转到主材管家修改页面");
		Customer customer=null;
		CustStakeholder mainManager =null;
		Employee employee=null;
		Integer mainManagePk=gcxxglService.getCustStakeholderPk(no,"34");
		customer=customerService.get(Customer.class, no);
		if(mainManagePk!=null){
			customer.setGxrPK(mainManagePk);
			mainManager=custStakeholderService.get(CustStakeholder.class, mainManagePk);
		}
		if(mainManager!=null){
			employee=employeeService.get(Employee.class,mainManager.getEmpCode());
		}
		customer.setM_umState("A");
		if(employee!=null){
			customer.setEmpCode(employee.getNumber());
			customer.setStakeholder(employee.getNameChi());
			customer.setM_umState("M");
		} 
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_updateMainManager")
					.addObject("customer",customer);
	}
	
	/**
	 * 编辑集成设计师
	 * @return
	 * 
	 */
	@RequestMapping("/goUpdateInteDesign")
	public ModelAndView goUpdateInteDesign(HttpServletRequest request,HttpServletResponse response,
			String  code,String jcdsMan,String CGDesignCode,Integer cgPk){
		logger.debug("跳转到修改集成设计师页面");
		Customer customer=null;
		Employee employee=null;
		if(code!=null&&code!=""){
			customer=customerService.get(Customer.class, code);
		}
		if(jcdsMan!=""&&jcdsMan!=null){
			employee=employeeService.get(Employee.class, jcdsMan);
			customer.setInteEmpCode(jcdsMan);
			customer.setDesignManDescr(employee.getNameChi());
		}
		if(StringUtils.isNotBlank(CGDesignCode)){
			employee=employeeService.get(Employee.class, CGDesignCode);
			customer.setCGDesignCode(CGDesignCode);
			customer.setCGDesignerDescr(employee.getNameChi());
			customer.setCgPk(cgPk);
		}
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_updateInteDesign")
					.addObject("customer",customer);
	}
	
	@RequestMapping("/goUpdateRealAddress")
	public ModelAndView goUpdateRealAddress(HttpServletRequest request,HttpServletResponse response,
			String  no){
		logger.debug("跳转到修改实际地址页面");
		Customer customer=null;
		customer=customerService.get(Customer.class, no);
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_updateRealAddress")
					.addObject("customer",customer);
	}
	
	/**
	 * 查看页面
	 * @return
	 * 
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,
			Customer ct){
		logger.debug("跳转到查看页面");
		Customer customer=null;
		Employee employee=null;
		Employee emp=null;
		Employee empl=null;
		Employee emplo=null;
		
		//CustStakeholder custStakeholder=custStakeholderService.get(clazz, id);
		customer=customerService.get(Customer.class, ct.getCode());
		customer.setPlanPrjDescr(ct.getPlanPrjDescr());
		customer.setNowPrjDescr(ct.getNowPrjDescr());
		customer.setBusinessFlowDescr(ct.getBusinessFlowDescr());
		customer.setDelayDay(ct.getDelayDay());
		customer.setPrjManCost(ct.getPrjManCost());
		customer.setConsManCost(ct.getConsManCost());
		customer.setJczj(ct.getJczj());
		customer.setZjxhj(ct.getZjxhj());
		customer.setSoftCost(ct.getSoftCost());
		customer.setBaseCost(ct.getBaseCost());
		customer.setInteCost(ct.getInteCost());
		customer.setMainCost(ct.getMainCost());
		customer.setPreloftsMan(ct.getPreloftsMan());
		customer.setOldPreloftsMan(ct.getPreloftsMan());
		customer.setDepartment2(ct.getDepartment2());
		customer.setHavePay(ct.getHavePay());
		customer.setCustCountCost(ct.getCustCountCost());
		if(customer.getProjectMan()!=null&&customer.getProjectMan()!=""){
			employee=employeeService.get(Employee.class, customer.getProjectMan());
			customer.setProjectManDescr(employee==null?"":employee.getNameChi());
		}
		if(customer.getCheckMan()!=null&&customer.getCheckMan()!=""){
			emp=employeeService.get(Employee.class, customer.getCheckMan());
			customer.setCheckManDescr(emp==null?"":emp.getNameChi());
		}
		if(ct.getPreloftsMan()!=null&&ct.getPreloftsMan()!=""){
			empl=employeeService.get(Employee.class, ct.getPreloftsMan());
			customer.setPreloftsManDescr(empl==null?"":empl.getNameChi());
		}
		if(customer.getDesignMan()!=null&&customer.getDesignMan()!=""){
			emplo=employeeService.get(Employee.class, customer.getDesignMan());
			customer.setDesignManDescr(emplo==null?"":emplo.getNameChi());
		}
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_view")
					.addObject("customer",customer).addObject("employee",emp);
	}
	
	/**
	 * 批量编辑
	 * @return
	 * 
	 */
	@RequestMapping("/goBatchUpdate")
	public ModelAndView goBatchUpdate(HttpServletRequest request,HttpServletResponse response,
			Customer customer){
		logger.debug("跳转到批量修改页面");
		
		customer.setConstructStatus("0,1,2,3");
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_volumeUpdate")
					.addObject("customer",customer);
	}
	
	/**
	 * 工地统计分析
	 * @return
	 * 
	 */
	@RequestMapping("/goGdtjfx")
	public ModelAndView goGdtjfx(HttpServletRequest request,HttpServletResponse response,
			Customer customer){
		logger.debug("跳转到工地统计分析页面");
		
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_prjCheck")
					.addObject("customer",customer);
	}
		
	@RequestMapping("/goCustCheckData")
	public ModelAndView goCustCheckData(HttpServletRequest request,HttpServletResponse response,
			String custCode){
		logger.debug("跳转到工地统计分析页面");
		CustCheckData custCheckData=null;
		Customer customer=null;
		if(StringUtils.isNotBlank(custCode)){
			custCheckData =custCheckDataService.get(CustCheckData.class, custCode);
			customer=customerService.get(Customer.class, custCode);
		}
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_custCheckData")
					.addObject("custCheckData",custCheckData).addObject("customer", customer);
	}
	/**
	 * 工程完工before工程结算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/beforegcjs")
	public void beforegcjs(HttpServletRequest request, HttpServletResponse response, String custCode){
		logger.debug("工程完工客户结算");
		try{
			int i = itemChgService.getCountByCustCode(custCode);
			if (i>0){
				ServletUtils.outPrintFail(request, response, "客户存在申请状态的材料增减单，不能进行结算操作！");
			}
			int j = baseItemChgService.getCountByCustCode(custCode);
			if (j>0){
				ServletUtils.outPrintFail(request, response, "客户存在申请状态的基础增减单，不能进行结算操作！");
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程结算失败");
		}
	}
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request,HttpServletResponse response,
			String custCode){
		logger.debug("跳转到工地结算页面");
		Customer customer = customerService.get(Customer.class, custCode);;
		String leaderNumber = "",
				leaderName = "",
				department1 = "",
				department2 = "";
		
		Employee leader = new Employee();
		if(StringUtils.isNotBlank(customer.getPrjDeptLeader())){
			leader = customerService.get(Employee.class, customer.getPrjDeptLeader());
			department1=leader.getDepartment1();
			department2=leader.getDepartment2();
		}else{
			if (StringUtils.isNotBlank(customer.getProjectMan())) {
				leader = employeeService.getDepLeaderByEmpNum(customer.getProjectMan());
				Employee projectMan = customerService.get(Employee.class, customer.getProjectMan());
				department1=projectMan.getDepartment1();
				department2=projectMan.getDepartment2();
			}
		}
		if (leader != null) {
            leaderNumber = leader.getNumber();
            leaderName = leader.getNameChi();
        }
		return new ModelAndView("admin/project/gcxxgl/gcxxgl_check")
				.addObject("customer", customer)
				.addObject("leaderNumber", leaderNumber)
				.addObject("leaderDescr", leaderName)
				.addObject("dept1", department1)
				.addObject("dept2", department2);
	}
	
		
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,HttpServletResponse response,Customer customer){
		logger.debug("工程信息录入开始");
		Customer ct =null;
		String projectMan;
		try {
			if(StringUtils.isNotBlank(customer.getCode())){ // customer.getCode()!=null&&customer.getCode()!="" 判断改写by zjf 
				ct=customerService.get(Customer.class,customer.getCode());
				projectMan=ct.getProjectMan();
				ct.setProjectMan(customer.getProjectMan());
				ct.setCheckMan(customer.getCheckMan());
				ct.setConsRcvDate(customer.getConsRcvDate());
				ct.setSendJobDate(customer.getSendJobDate());
				ct.setBeginComDate(customer.getBeginComDate());
				ct.setConfirmBegin(customer.getConfirmBegin());
				ct.setConstructDay(customer.getConstructDay());
				ct.setConstructStatus(customer.getConstructStatus());
				ct.setConsEndDate(customer.getConsEndDate());
				ct.setDelayDay(customer.getDelayDay());
				ct.setPlanCheckOut(customer.getPlanCheckOut());
				ct.setIsComplain(customer.getIsComplain());
				ct.setConsArea(customer.getConsArea());
				ct.setIntMsrDate(customer.getIntMsrDate());
				ct.setIntDlyDay(customer.getIntDlyDay());
				ct.setHaveCheck(customer.getHaveCheck());
				ct.setHaveReturn(customer.getHaveReturn());
				ct.setHavePhoto(customer.getHavePhoto());
				ct.setMainItemOk(customer.getMainItemOk());
				ct.setLastUpdate(new Date());
				ct.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				ct.setRelCust(customer.getRelCust());
				this.customerService.update(ct);
				this.gcxxglService.updateCustomer(customer.getCode(),customer.getProjectMan(),projectMan,
						this.getUserContext(request).getCzybh());
			}
			
				this.gcxxglService.updatePreloftsMan(customer.getCode(),customer.getPreloftsMan(),customer.getPreloftsManDescr(),
						this.getUserContext(request).getCzybh(),customer.getOldPreloftsMan());
				
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}

	}
	
	
	/**
	 * 客户电话——编辑
	 * @return
	 * 
	 * */
	@RequestMapping("/doUpdateMobile1")
	public void doUpdateMobile1(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("修改客户电话");
		Customer ct = null;
		ct=customerService.get(Customer.class, customer.getCode());
		try{
			ct.setMobile1(customer.getMobile1());
			customer.setLastUpdate(new Date());
			customerService.update(ct);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 软装设计师——编辑
	 * @return
	 * 
	 * */
	@RequestMapping("/doUpdateDesigner")
	public void doUpdateDesigner(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("编辑软装设计师");
		Customer ct = null;
		CustStakeholder custStakeholder =null;
		CustStakeholder softSteward = new CustStakeholder();
		ct=customerService.get(Customer.class, customer.getCode());
		Result result=null;
		try{
			if(customer.getSoftPK()!=null){
					custStakeholder=custStakeholderService.get(CustStakeholder.class, customer.getSoftPK());
					custStakeholder.setM_umState("M");
					custStakeholder.setIsRight("1".equals(this.getUserContext(request).getCzybh().trim())?1:0);
					custStakeholder.setEmpCode(customer.getSoftEmpCode());
					custStakeholder.setLastUpdate(new Date());
					custStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					custStakeholder.setExpired("F");
					result =this.custStakeholderService.updateGcxxglDesigner(custStakeholder);
					if (result.isSuccess()) {
						ServletUtils.outPrintSuccess(request, response, "保存成功");
					} else {
						ServletUtils.outPrintFail(request, response, "软装设计师"+result.getInfo());
					}
			}else {
				if(StringUtils.isNotEmpty(customer.getSoftEmpCode())){
					CustStakeholder cs=new CustStakeholder();
					cs.setCustCode(customer.getCode());
					cs.setRole("50");
					cs.setIsRight("1".equals(this.getUserContext(request).getCzybh().trim())?1:0);
					cs.setEmpCode(customer.getSoftEmpCode());
					cs.setLastUpdate(new Date());
					cs.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					cs.setExpired("F");
					cs.setActionLog("Add");
					cs.setM_umState("A");
					cs.setPk(0);
					result =this.custStakeholderService.updateGcxxglDesigner(cs);
					if (result.isSuccess()) {
						ServletUtils.outPrintSuccess(request, response, "保存成功");
					} else {
						ServletUtils.outPrintFail(request, response, "软装管家"+result.getInfo());
					}
				}
			}
			
			if("A".equals(customer.getSoftStewardStatus())){
				softSteward.setCustCode(customer.getCode());
				softSteward.setRole("65");
				softSteward.setActionLog("add");
				softSteward.setIsRight("1".equals(this.getUserContext(request).getCzybh().trim())?1:0);
				softSteward.setEmpCode(customer.getSoftSteward());
				softSteward.setLastUpdate(new Date());
				softSteward.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				softSteward.setExpired("F");
				softSteward.setM_umState("A");
				softSteward.setPk(0);
				if(StringUtils.isNotBlank(softSteward.getEmpCode())){
					result =this.custStakeholderService.updateGcxxglDesigner(softSteward);
					if (result.isSuccess()) {
						ServletUtils.outPrintSuccess(request, response, "保存成功");
					} else {
						ServletUtils.outPrintFail(request, response, result.getInfo());
					}
				}
			}else if("M".equals(customer.getSoftStewardStatus())){
				softSteward=custStakeholderService.get(CustStakeholder.class, customer.getSoftStewardPk());
				softSteward.setM_umState("M");
				softSteward.setIsRight("1".equals(this.getUserContext(request).getCzybh().trim())?1:0);
				softSteward.setEmpCode(customer.getSoftSteward());
				softSteward.setLastUpdate( new Date());
				softSteward.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				softSteward.setExpired("F");
				result =this.custStakeholderService.updateGcxxglDesigner(softSteward);
				if (result.isSuccess()) {
					ServletUtils.outPrintSuccess(request, response, "保存成功");
				} else {
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response,result.getInfo());
		}
	}
	
	/**
	 * 集成设计师——编辑
	 * @return
	 * 
	 * */
	@RequestMapping("/doUpdateInteDesigner")
	public void doUpdateInteDesigner(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("编辑集成设计师");
		Customer ct = null;
		CustStakeholder custStakeholder =new CustStakeholder();
		ct=customerService.get(Customer.class, customer.getCode());
		try{
			if(customer.getInteEmpCode()==null||customer.getInteEmpCode()==""){
				custStakeholderService.doDelInteEmp(customer.getCode());
			}
			if(customer.getCgPk()!=null&&(customer.getCGDesignCode()==null||customer.getCGDesignCode()=="")){
				custStakeholderService.doDelCGEmp(customer.getCgPk());
			}
			if(customer.getInteEmpCode()!=null&&customer.getInteEmpCode()!=""){
				custStakeholderService.doDelInteEmp(customer.getCode());
				custStakeholder.setEmpCode(customer.getInteEmpCode());
				custStakeholder.setLastUpdate(new Date());
				custStakeholder.setCustCode(customer.getCode());
				custStakeholder.setRole("11");
				custStakeholder.setExpired("F");
				custStakeholder.setActionLog("Add");
				custStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custStakeholder.setActionLog("Edit");
				custStakeholderService.save(custStakeholder);
			}
			if(customer.getCgPk()!=null&&customer.getCGDesignCode()!=null&&customer.getCGDesignCode()!=""){
				custStakeholder=custStakeholderService.get(CustStakeholder.class,customer.getCgPk());
				custStakeholder.setEmpCode(customer.getCGDesignCode());
				custStakeholder.setLastUpdate(new Date());
				custStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				custStakeholder.setActionLog("Edit");
				custStakeholderService.update(custStakeholder);
			}else if(customer.getCGDesignCode()!=null&&customer.getCGDesignCode()!=""){
				CustStakeholder cs=new CustStakeholder();
				cs.setCustCode(customer.getCode());
				cs.setRole("61");
				cs.setEmpCode(customer.getCGDesignCode());
				cs.setLastUpdate(new Date());
				cs.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				cs.setExpired("F");
				cs.setActionLog("Add");
				custStakeholderService.save(cs);
			}
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doUpdateRealAddress")
	public void doUpdateRealAddress(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("修改客户实际地址电话");
		Customer ct = null;
		ct=customerService.get(Customer.class, customer.getCode());
		try{
			ct.setRealAddress(customer.getRealAddress());
			ct.setLastUpdate(new Date());
			ct.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			customerService.update(ct);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doChengeCheckMan")
	public void doChengeCheckMan(HttpServletRequest request, HttpServletResponse response,
			String code,String chengeCheckMan){
		logger.debug("批量修改开始");
		try{
			//["Ct000221","Ct000222"]
			String[] custCode = code.split("\"");
			for (int i=1;i<custCode.length+1;i++){
				gcxxglService.doChengeCheckMan(custCode[i], chengeCheckMan,
						new Date(), this.getUserContext(request).getCzybh());
				i=i+1;
			}
				ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doSaveCols")
	public void doSaveCols(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		String colModel = request.getParameter("jsonString");
		String filePath = SystemConfig.getProperty("other", "", "photo").replace("other", "gcxxglSet");
		String fileName = this.getUserContext(request).getCzybh().trim()+".txt";
		
		net.sf.json.JSONArray arryCols = net.sf.json.JSONArray.fromObject(colModel);
		for (int i = 0; i < arryCols.size(); i++) { 
            net.sf.json.JSONObject jsonObject = arryCols.getJSONObject(i);
            String name = (String) jsonObject.get("name");
            if (name.equals("remarks")){
            	jsonObject.put("formatter", "cutStr");
            }
            if (name.equals("crtdate")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("setdate")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("signdate")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("delivdate")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("enddate")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("lastupdate")){
            	jsonObject.put("formatter", "formatTime");
            }
            if (name.equals("signdatefirst")){
            	jsonObject.put("formatter", "formatDate");
            }
        }
		
		String str = arryCols.toString().replace("\"cutStr\"", "cutStr")
				.replace("\"formatTime\"", "formatTime")
				.replace("\"formatDate\"", "formatDate");
		
		if (FileHelper.strToFile(filePath, fileName, str)){
			ServletUtils.outPrintSuccess(request, response, "设置成功！");
		}else{
			ServletUtils.outPrintFail(request, response, "设置失败！");
		}
		
	}
	
	@RequestMapping("/doUpdateCustCheckData")
	public void doUpdateCustCheckData(HttpServletRequest request, HttpServletResponse response,
			CustCheckData custCheckData){
		logger.debug("批量修改开始");
		try{
			CustCheckData ccd=null;
			if(StringUtils.isNotBlank(custCheckData.getCustCode())){
				ccd=custCheckDataService.get(CustCheckData.class, custCheckData.getCustCode());	
				if(ccd!=null){
					ccd.setToiletNum(custCheckData.getToiletNum());
					ccd.setBedroomNum(custCheckData.getBedroomNum());
					ccd.setKitchDoorType(custCheckData.getKitchDoorType());
					ccd.setBalconyNum(custCheckData.getBalconyNum());
					ccd.setIsBuildWall(custCheckData.getIsBuildWall());
					ccd.setIsWood(custCheckData.getIsWood());
					ccd.setBalconyTitle(custCheckData.getBalconyTitle());
					ccd.setLastUpdate(new Date());
					ccd.setActionLog("EDIT");
					ccd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					this.custCheckDataService.update(ccd);
				}else{
					custCheckData.setLastUpdate(new Date());
					custCheckData.setLastUpdatedBy(this.getUserContext(request).getCzybh());
					custCheckData.setExpired("F");
					custCheckData.setActionLog("ADD");
					this.custCheckDataService.save(custCheckData);
				}
				
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doInsertPrjMan")
	public void doInsertPrjMan(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("修改项目经理");
		Customer ct = null;
		ct=customerService.get(Customer.class, customer.getCode());
		
		try{
			this.gcxxglService.updateCustomer(customer.getCode(),customer.getProjectMan(),ct.getProjectMan(),
					this.getUserContext(request).getCzybh());
			ct.setProjectMan(customer.getProjectMan());
			this.customerService.update(ct);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doUpdatePrjMan")
	public void doUpdatePrjMan(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("修改项目经理");
		Customer ct = null;
		ct=customerService.get(Customer.class, customer.getCode());
		try{
			this.gcxxglService.updateCustomer(customer.getCode(),customer.getProjectMan(),ct.getProjectMan(),
					this.getUserContext(request).getCzybh());
			
			ct.setProjectMan(customer.getProjectMan());
			this.customerService.update(ct);

			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doUpdateMainManager")
	public void doUpdateMainManager(HttpServletRequest request,HttpServletResponse response,Customer customer){
		logger.debug("修改项目经理");
		CustStakeholder custStakeholder = new CustStakeholder();
		try{
			custStakeholder.setIsRight(0);
			custStakeholder.setPk(customer.getGxrPK());
			custStakeholder.setCustCode(customer.getCode());
			custStakeholder.setRole("34");
			custStakeholder.setEmpCode(customer.getEmpCode());
			custStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custStakeholder.setM_umState(customer.getM_umState());
			custStakeholder.setExpired("F");
			Result result = this.custStakeholderService.doUpdateMainManager(custStakeholder);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		gcxxglService.findPageBySql(page, customer,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"施工合同信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

	@RequestMapping("/doExcelForDelayDetail")
	public void doExcelForDelayDetail(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		gcxxglService.findDelayDetailPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"拖期明细信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 工程结算
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/dogcjs")
	public void dogcjs(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("工程结算");
		try{
			Customer oldCustomer = customerService.get(Customer.class,customer.getCode());
			if(!"5".equals(oldCustomer.getStatus())||!"1".equals(oldCustomer.getCheckStatus())){
				ServletUtils.outPrintFail(request, response, "客户结算状态改变，不可保存");
				return;
			}
			oldCustomer.setPrjDeptLeader(customer.getPrjDeptLeader());
			oldCustomer.setPrjDepartment1(customer.getPrjDepartment1());
			oldCustomer.setPrjDepartment2(customer.getPrjDepartment2());
			oldCustomer.setCheckOutDate(customer.getCheckOutDate());
			customerService.update(oldCustomer);
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "工程结算失败");
		}
	}
}
