package com.house.home.web.controller.query;

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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.query.EmployeeDdphService;
@Controller
@RequestMapping("/admin/employeeDdph")
public class EmployeeDdphController extends BaseController {
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private EmployeeDdphService employeeDdphService;
	@Autowired
	private  EmployeeService employeeService;
	@Autowired
	private CzybmService czybmService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeDdphService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goSignJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSignJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeDdphService.findSignPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goSignSetJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSignSetJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeDdphService.findSignSetPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goNowSignJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goNowSignJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeDdphService.findNowSignPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCrtJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCrtJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeDdphService.findCrtPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goSetJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSetJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeDdphService.findSetPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}	
	@RequestMapping("/goQdJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goQdJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		employeeDdphService.findQdyjPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}	
	@RequestMapping("/goZjJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goZjJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		employeeDdphService.findZjyjPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
		if (customer.getCustType() == null){
			List<CustType> listCustType = custTypeService.findByDefaultStatic(); //默认需要统计的客户类型
			String defaultStaticCustType = "";
			for (CustType custType: listCustType) {
				defaultStaticCustType = defaultStaticCustType + "," + custType.getCode();
			}
			if (!defaultStaticCustType.equals("")) {
				defaultStaticCustType = defaultStaticCustType.substring(1);
			}
			customer.setCustType(defaultStaticCustType);
		}
		return new ModelAndView("admin/query/employeeDdph/employeeDdph_list");
	}
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,Customer customer){
		
		return new ModelAndView("admin/query/employeeDdph/employeeDdph_detail")
		.addObject("customer", customer);
				
	}

	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		employeeDdphService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单排行_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doSignExcel")
	public  void doSignExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		employeeDdphService.findSignPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doSignSetExcel")
	public  void doSignSetExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		employeeDdphService.findSignSetPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"统计期存单信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doNowSignExcel")
	public  void doNowSignExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		employeeDdphService.findNowSignPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"当前有效存单信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doCrtExcel")
	public  void doCrtExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		employeeDdphService.findCrtPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"接待信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doSetExcel")
	public  void doSetExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		employeeDdphService.findSetPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"下定信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doQdExcel")
	public  void doQdExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		employeeDdphService.findQdyjPageBySql(page, customer, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单业绩信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doZjExcel")
	public  void doZjExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		employeeDdphService.findZjyjPageBySql(page, customer, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"增减业绩信息_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 检查能否进行查看操作
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/doCheckView")
	public void doCheckView(HttpServletRequest request, HttpServletResponse response, Employee employee){
		logger.debug("检查查看开始");
		
		try {
			if(StringUtils.isBlank(employee.getNumber())){
				ServletUtils.outPrintFail(request, response, "员工编号不能为空,查看失败");
				return;
			};
			Employee ia = employeeService.get(Employee.class, employee.getNumber());
			Czybm czybm = czybmService.get(Czybm.class, this.getUserContext(request).getCzybh());
			if (!employeeDdphService.hasViewRight(czybm, ia)) { 
				ServletUtils.outPrintFail(request, response, "您无权查看该员工的客户信息!");
				return;
				
			}
			ServletUtils.outPrintSuccess(request, response, "可以进行查看", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "查看失败");
		}
	}
}
