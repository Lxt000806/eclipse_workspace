package com.house.home.web.controller.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Role;
import com.house.framework.service.RoleService;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.RollService;
import com.house.home.service.design.CustStakeholderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.design.LeaveEmpCustManageService;

@Controller
@RequestMapping("/admin/custStakeholder")
public class CustStakeholderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustStakeholderController.class);

	@Autowired
	private CustStakeholderService custStakeholderService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private RollService rollService;
	@Autowired
	private LeaveEmpCustManageService leaveEmpCustManageService;
	
	
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustStakeholder custStakeholder) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc =this.getUserContext(request);
		custStakeholderService.findPageBySql(page, custStakeholder,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustStakeholder??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/custStakeholder/custStakeholder_list");
	}
	
	/**
	 * ???????????????CustStakeholder??????
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("???????????????CustStakeholder??????");
		CustStakeholder custStakeholder = null;
		if (StringUtils.isNotBlank(id)){
			custStakeholder = custStakeholderService.get(CustStakeholder.class, Integer.parseInt(id));
			custStakeholder.setPk(null);
		}else{
			custStakeholder = new CustStakeholder();
		}
		
		return new ModelAndView("admin/design/custStakeholder/custStakeholder_save")
			.addObject("custStakeholder", custStakeholder);
	}
	/**
	 * ???????????????CustStakeholder??????
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("???????????????CustStakeholder??????");
		CustStakeholder custStakeholder = null;
		Customer customer =new Customer();
		Employee employee=new Employee();
		Roll roll=new Roll();
		
		if (pk != null){
			custStakeholder = custStakeholderService.get(CustStakeholder.class,pk);
			if(StringUtils.isNotBlank(custStakeholder.getCustCode())){
				customer=customerService.get(Customer.class, custStakeholder.getCustCode());
			}
			if(StringUtils.isNotBlank(custStakeholder.getRole())){
				roll = rollService.get(Roll.class, custStakeholder.getRole());
			}
			if(StringUtils.isNotBlank(custStakeholder.getEmpCode())){
				employee=employeeService.get(Employee.class, custStakeholder.getEmpCode());
			}
			
		}else{
			custStakeholder = new CustStakeholder();
		}
		
		return new ModelAndView("admin/design/custStakeholder/custStakeholder_update")
			.addObject("custStakeholder", custStakeholder).addObject("customer",customer).addObject("employee", employee)
			.addObject("roll", roll);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("???????????????CustStakeholder??????");
		CustStakeholder custStakeholder = null;
		Customer customer =new Customer();
		Employee employee=new Employee();
		Roll roll=new Roll();
		if (pk != null){
			custStakeholder = custStakeholderService.get(CustStakeholder.class,pk);
			if(StringUtils.isNotBlank(custStakeholder.getCustCode())){
				customer=customerService.get(Customer.class, custStakeholder.getCustCode());
			}
			if(StringUtils.isNotBlank(custStakeholder.getRole())){
				roll = rollService.get(Roll.class, custStakeholder.getRole());
			}
			if(StringUtils.isNotBlank(custStakeholder.getEmpCode())){
				employee=employeeService.get(Employee.class, custStakeholder.getEmpCode());
			}
		}else{
			custStakeholder = new CustStakeholder();
		}
		
		return new ModelAndView("admin/design/custStakeholder/custStakeholder_copy")
			.addObject("custStakeholder", custStakeholder).addObject("customer",customer).addObject("employee", employee)
			.addObject("roll", roll);
	}
	
	/**
	 * ???????????????CustStakeholder??????
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("???????????????CustStakeholder??????");
		CustStakeholder custStakeholder = null;
		Customer customer =new Customer();
		Employee employee=new Employee();
		Roll roll=new Roll();
		if (pk != null){
			custStakeholder = custStakeholderService.get(CustStakeholder.class,pk);
			if(StringUtils.isNotBlank(custStakeholder.getCustCode())){
				customer=customerService.get(Customer.class, custStakeholder.getCustCode());
			}
			if(StringUtils.isNotBlank(custStakeholder.getRole())){
				roll = rollService.get(Roll.class, custStakeholder.getRole());
			}
			if(StringUtils.isNotBlank(custStakeholder.getEmpCode())){
				employee=employeeService.get(Employee.class, custStakeholder.getEmpCode());
			}
		}else{
			custStakeholder = new CustStakeholder();
		}
		
		return new ModelAndView("admin/design/custStakeholder/custStakeholder_view")
			.addObject("custStakeholder", custStakeholder).addObject("customer",customer).addObject("employee", employee)
			.addObject("roll", roll);
	}
	
	@RequestMapping("/goMultiUpdate")
	public ModelAndView goMultiUpdate(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		
		Customer customer = new Customer();
		
		return new ModelAndView("admin/design/custStakeholder/leaveEmpCustManage_updateStakeholder").addObject("customer", customer);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/doChgStakeholder")
	public void doChgStakeholder(HttpServletRequest request, HttpServletResponse response){
		logger.debug("??????????????????");
		try{
			String dataString = request.getParameter("dataStering");
			String showType = request.getParameter("showType");
			String stakeholder = request.getParameter("stakeholder");
			List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(dataString);
			
			leaveEmpCustManageService.doChgStakeholder(list, showType, stakeholder, this.getUserContext(request).getCzybh());
			
			ServletUtils.outPrintSuccess(request, response,"????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	/**
	 * ??????CustStakeholder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustStakeholder custStakeholder){
		logger.debug("??????CustStakeholder??????");
		try{
			custStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custStakeholder.setM_umState("A");
			custStakeholder.setExpired("F");
			custStakeholder.setPk(0);	
			// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//			boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1176);
			boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0204", "????????????");
 			custStakeholder.setIsRight(hasAuthByCzybh?1:0);
			Result result = this.custStakeholderService.doSave(custStakeholder);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustStakeholder??????");
		}
	}
	
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request, HttpServletResponse response, CustStakeholder custStakeholder){
		logger.debug("??????CustStakeholder??????");
		try{
			custStakeholder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custStakeholder.setM_umState("C");
			custStakeholder.setExpired("F");
			custStakeholder.setPk(0);
			// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//			boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1176);
			boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0204", "????????????");
			custStakeholder.setIsRight(hasAuthByCzybh?1:0);
			Result result = this.custStakeholderService.doSave(custStakeholder);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustStakeholder??????");
		}
	}
	
	/**
	 * ??????CustStakeholder
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustStakeholder custStakeholder){
		logger.debug("??????CustStakeholder??????");
		try{			
			// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//			boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1176);
			boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0204", "????????????");
			custStakeholder.setM_umState("M");
			custStakeholder.setExpired("F");
			custStakeholder.setIsRight(hasAuthByCzybh?1:0);
			custStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = this.custStakeholderService.updateForProc(custStakeholder);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "??????CustStakeholder??????");
		}
	}
	
	@RequestMapping("/doDel")
	public void doDel(HttpServletRequest request, HttpServletResponse response, Integer pk){
		logger.debug("??????CustStakeholder??????");
		try{	
			Customer customer=new Customer();
			CustStakeholder custStakeholder=new CustStakeholder();
			if(pk!=null){
				custStakeholder=custStakeholderService.get(CustStakeholder.class, pk);
			}else{
				ServletUtils.outPrintFail(request, response,"????????????,??????pk");
				return;
			}
			customer=customerService.get(Customer.class, custStakeholder.getCustCode());
			if("00".equals(custStakeholder.getRole())||"01".equals(custStakeholder.getRole())){//00:designer,01:businessMan
				if("00".equals(custStakeholder.getRole())){
					if(1==custStakeholderService.onlyDesigner(custStakeholder.getCustCode())){
						ServletUtils.outPrintFail(request, response,"??????"+customer.getAddress()+"???????????????????????????????????????????????????");
						return;
					}
				}else {
					if(1==custStakeholderService.onlyBusinessMan(custStakeholder.getCustCode())){
						ServletUtils.outPrintFail(request, response,"??????"+customer.getAddress()+"???????????????????????????????????????????????????");
						return;
					}
				}
			}
			// 20200415 mark by xzp ?????????czybmService.hasGNQXByCzybh
//			boolean hasAuthByCzybh=czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 1176);
			boolean hasAuthByCzybh=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0204", "????????????");
			custStakeholder.setM_umState("D");
			custStakeholder.setIsRight(hasAuthByCzybh?1:0);
			custStakeholder.setLastUpdatedBy(getUserContext(request).getCzybh());
			Result result = this.custStakeholderService.updateForProc(custStakeholder);
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response, result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response, result.getInfo());
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}

	/**
	 *CustStakeholder??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustStakeholder custStakeholder){
			Page<Map<String, Object>>page= this.newPage(request);
			UserContext uc = getUserContext(request);
			page.setPageSize(-1);
			custStakeholderService.findPageBySql(page, custStakeholder,uc);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
					"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		}

}
