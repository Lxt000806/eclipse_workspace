package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustComplaint;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustService;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustProblemService;
import com.house.home.service.project.CustServiceService;

@Controller
@RequestMapping("/admin/custService")
public class CustServiceController extends BaseController{
	@Autowired
	private  CustServiceService custServiceService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private Department2Service department2Service;
	@Autowired
	private CustProblemService custProblemService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustService custService) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custServiceService.findPageBySql(page, custService);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/custService/custService_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		String repMan = this.getUserContext(request).getCzybh().trim();
		Employee employee = employeeService.get(Employee.class, repMan);
		return new ModelAndView("admin/project/custService/custService_save")
			.addObject("employee", employee)
			.addObject("repDate", new Date());
	}
	
	/**
	 * @Description: ????????????????????????????????????
	 * @author	created by zb
	 * @date	2018-8-8--??????4:52:33
	 * @param request
	 * @param response
	 * @param no ??????
	 * @param m_umState ???????????????M-?????????C-?????????V-??????
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/goUpdate", "/goComplete", "goView"})
	public ModelAndView goUpdate(HttpServletRequest request, 
			HttpServletResponse response, String no,String m_umState) throws Exception {
		Map<String, Object> customer = new HashMap<String, Object>();
		String repManDescr = null;
		String serviceManDescr = null;
		CustService custService = this.custServiceService.get(CustService.class, no);
		Employee employee = new Employee();
		if (StringUtils.isNotBlank(custService.getRepMan())) {
			employee = employeeService.get(Employee.class, custService.getRepMan());
			if (null == employee) {
				repManDescr = "??????????????????";
			}else{
				repManDescr = employee.getNameChi();
			}
		}
		if (StringUtils.isNotBlank(custService.getServiceMan())) {
			employee = employeeService.get(Employee.class, custService.getServiceMan());
			if (null == employee) {
				serviceManDescr = "??????????????????";
			}else{
				serviceManDescr = employee.getNameChi();
			}
		}
		if (StringUtils.isNotBlank(custService.getCustCode())) {
			customer = GetCustDetailByCode(request, response, custService.getCustCode());
		} else {
			customer.put("Address", custService.getAddress());
		}
		return new ModelAndView("admin/project/custService/custService_update")
			.addObject("customer", customer)
			.addObject("custService", custService)
			.addObject("repManDescr", repManDescr)
			.addObject("serviceManDescr", serviceManDescr)
			.addObject("m_umState", m_umState);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,CustService custService){
		logger.debug("????????????");
		try {
			String noString = custServiceService.getSeqNo("tCustService");
			custService.setNo(noString);
			custService.setLastUpdate(new Date());
			custService.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custService.setExpired("F");
			custService.setActionLog("ADD");
			//???????????????????????????
			if("true".equals(custService.getIsFromCustProblem())){
				String repMan = this.getUserContext(request).getCzybh().trim();
				Employee employee = employeeService.get(Employee.class, repMan);
				CustProblem custProblem =  custServiceService.get(CustProblem.class, custService.getCustProblemPK());
				custProblem.setStatus("2");
				Date date = new Date();
				custProblem.setRcvDate(date);
				custProblem.setDealRemarks((DateUtil.getMonth(date)+1)+"???"+DateUtil.getDay(date)+"??? "+employee.getNameChi()+
						":????????????????????????"+custService.getDealMan()+"\n"+custProblem.getDealRemarks());
				custServiceService.update(custProblem);
			}
			this.custServiceService.save(custService);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,CustService custService){
		logger.debug("????????????");
		try {
			CustService cs = this.custServiceService.get(CustService.class, custService.getNo());
			custService.setLastUpdate(new Date());
			custService.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			custService.setActionLog("Edit");
			if ("C".equals(custService.getM_umState())) {
				custService.setStatus("2");
				if(custService.getCustProblemPK() != null ){
					CustProblem custProblem = custServiceService.get(CustProblem.class, custService.getCustProblemPK());
					if(custProblem != null){
						custProblemService.dealCustCompaint(custProblem.getNo(),custService.getCustProblemPK(),DateUtil.DateToString(new Date(), "yyyy-MM-dd"),custProblem.getDealRemarks());
					}
				}
			}
			if (cs==null) {
				this.custServiceService.save(custService);
			}else {
				this.custServiceService.update(custService);
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("??????custService??????");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					CustService custService = this.custServiceService.get(CustService.class, deleteId);
					this.custServiceService.delete(custService);
					ServletUtils.outPrintSuccess(request, response, "????????????");
				}
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustService custService){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custServiceService.findPageBySql(page, custService);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * @Description: ??????code???????????????????????????????????????????????????--department2???
	 * @author	created by zb
	 * @date	2018-8-8--??????9:17:32
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/GetCustDetailByCode")
	@ResponseBody
	public Map<String, Object> GetCustDetailByCode(HttpServletRequest request, HttpServletResponse response,
			String code){
		Map<String, Object> customer = null;
		if (StringUtils.isNotBlank(code)) {
			customer = custServiceService.getCustDetailByCode(code);

			return customer;
		} else {
			return customer;
		}
	}
	
	//??????????????????????????????
	@RequestMapping("/GetCustomerByCode")
	@ResponseBody
	public Customer GetCustomerByCode(HttpServletRequest request, HttpServletResponse response,
			String code){
		Customer customer = null;
		if (StringUtils.isNotBlank(code)) {
			customer = customerService.get(Customer.class, code);
			if (StringUtils.isNotBlank(customer.getProjectMan())) {
				Employee employee = employeeService.get(Employee.class, customer.getProjectMan());
				customer.setProjectManDescr(employee.getNameChi());
				if (StringUtils.isNotBlank(employee.getDepartment2())) {
					Department2 department2 = department2Service.get(Department2.class, employee.getDepartment2());
					customer.setDepartment2Descr(department2.getDesc1());
				}
				
			}
			return customer;
		} else {
			return customer;
		}
	}
	
	@RequestMapping("/doConfirmApply")
	public void doConfirmApply(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("??????custService??????");
		if(StringUtils.isBlank(no)){
			ServletUtils.outPrintFail(request, response, "????????????????????????,????????????");
			return;
		}
		try{
			CustService custService = custServiceService.get(CustService.class, no);
			if(!"0".equals(custService.getStatus().trim())){
				ServletUtils.outPrintFail(request, response, "???????????????????????????,?????????????????????");
				return;
			}
			custService.setStatus("1");
			custService.setLastUpdate(new Date());
			custService.setActionLog("EDIT");
			custService.setLastUpdatedBy(getUserContext(request).getCzybh());
			custServiceService.update(custService);
			ServletUtils.outPrintSuccess(request, response, "????????????");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "????????????");
		}
	}
}
