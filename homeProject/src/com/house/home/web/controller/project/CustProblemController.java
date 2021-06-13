package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.project.CustComplaint;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.project.CustProblemService;

@Controller
@RequestMapping("/admin/custProblem")
public class CustProblemController extends BaseController {

	@Autowired
	private CustProblemService custProblemService;
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 查询JqGrid表格数据
	 * 
	 * @param request
	 * @param response
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CustProblem custProblem)
			throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		custProblem.setLastUpdatedBy(getUserContext(request).getCzybh().trim());
		custProblemService.findPageBySql(page, custProblem, uc);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping(value = "/promType/{type}/{pCode}") //问题分类
	@ResponseBody
	public JSONObject getPromType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.custProblemService.findPromType1(type, pCode);
		return this.out(regionList, true);
	}
	
	@RequestMapping(value = "/promRsn/{type}/{pCode}") //问题分类
	@ResponseBody
	public JSONObject getPromRsn(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.custProblemService.findPromRsn(type, pCode);
		return this.out(regionList, true);
	}
	
	/**
	 * 跳转到custItemConfStat
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,CustProblem custProblem) throws Exception {
		Employee employee = custProblemService.get(Employee.class,getUserContext(request).getCzybh());
		return new ModelAndView("admin/project/custProblem/custProblem_list").addObject("employee", employee);
	}

	/**
	 * 跳转到处理custProblem页面
	 * 
	 * @return
	 */
	@RequestMapping("/goDeal")
	public ModelAndView goDeal(HttpServletRequest request,
			HttpServletResponse response, String pk, String addessStatus,
			String mobile1, String projectDept3, String designManDescr,
			String designManPhone, String projectManPhone, Date checkOutDate,
			String compStatus, String remarks, String custType, String address,
			String custCode, String custdescr, String projectManDescr,
			String projectDept2, Date crtDate, String source, String crtCZY,Date jsCrtDate,Date rcvDate) {
		logger.debug("跳转到修改custProblem页面");
		CustProblem custProblem = null;
		if (StringUtils.isNotBlank(pk)) {
			custProblem = custProblemService.get(CustProblem.class,
					Integer.parseInt(pk));
		} else {
			custProblem = new CustProblem();
		}
		custProblem.setAddessStatus(addessStatus);
		custProblem.setMobile1(mobile1);
		custProblem.setProjectDept3(projectDept3);
		custProblem.setDesignManDescr(designManDescr);
		custProblem.setDesignManPhone(designManPhone);
		custProblem.setProjectManPhone(projectManPhone);
		custProblem.setCheckOutDate(checkOutDate);
		custProblem.setCompStatus(compStatus);
		custProblem.setRemarks(remarks);
		if(StringUtils.isNotBlank(custType)){
			custProblem.setCustType(custType.trim());
		}
		custProblem.setAddress(address);
		custProblem.setCustCode(custCode);
		custProblem.setCustDescr(custdescr);
		custProblem.setProjectManDescr(projectManDescr);
		custProblem.setProjectDept2(projectDept2);
		custProblem.setCrtDate(crtDate);
		custProblem.setSource(source);
		custProblem.setCrtCZY(crtCZY);
		custProblem.setJsCrtDate(jsCrtDate);
		custProblem.setRcvDate(rcvDate);
		custProblem.setDealDate(new Date());
		return new ModelAndView("admin/project/custProblem/custProblem_deal")
				.addObject("custProblem", custProblem);
	}

	/**
	 * 跳转到接收custProblem页面
	 * 
	 * @return
	 */
	@RequestMapping("/goRcv")
	public ModelAndView goRcv(HttpServletRequest request,
			HttpServletResponse response, String pk, String addessStatus,
			String mobile1, String projectDept3, String designManDescr,
			String designManPhone, String projectManPhone, Date checkOutDate,
			String compStatus, String remarks, String custType, String address,
			String custCode, String custdescr, String projectManDescr,
			String projectDept2, Date crtDate, String source, String crtCZY,Date jsCrtDate,Date rcvDate) {
		logger.debug("跳转到接收custProblem页面");
		CustProblem custProblem = null;
		if (StringUtils.isNotBlank(pk)) {
			custProblem = custProblemService.get(CustProblem.class,
					Integer.parseInt(pk));
		} else {
			custProblem = new CustProblem();
		}
		custProblem.setAddessStatus(addessStatus);
		custProblem.setMobile1(mobile1);
		custProblem.setProjectDept3(projectDept3);
		custProblem.setDesignManDescr(designManDescr);
		custProblem.setDesignManPhone(designManPhone);
		custProblem.setProjectManPhone(projectManPhone);
		custProblem.setCheckOutDate(checkOutDate);
		custProblem.setCompStatus(compStatus);
		custProblem.setRemarks(remarks);
		if(StringUtils.isNotBlank(custType)){
			custProblem.setCustType(custType.trim());
		}
		custProblem.setAddress(address);
		custProblem.setCustCode(custCode);
		custProblem.setCustDescr(custdescr);
		custProblem.setProjectManDescr(projectManDescr);
		custProblem.setProjectDept2(projectDept2);
		custProblem.setCrtDate(crtDate);
		custProblem.setSource(source);
		custProblem.setCrtCZY(crtCZY);
		custProblem.setJsCrtDate(jsCrtDate);
		custProblem.setRcvDate(new Date());
		return new ModelAndView("admin/project/custProblem/custProblem_rcv")
				.addObject("custProblem", custProblem);
	}
	

	/**
	 * 跳转到查看custProblem页面
	 * 
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, String pk, String addessStatus,
			String mobile1, String projectDept3, String designManDescr,
			String designManPhone, String projectManPhone, Date checkOutDate,
			String compStatus, String remarks, String custType, String address,
			String custCode, String custdescr, String projectManDescr,
			String projectDept2, Date crtDate, String source, String crtCZY,Date jsCrtDate,Date rcvDate) {
		logger.debug("跳转到查看custProblem页面");
		CustProblem custProblem = null;
		if (StringUtils.isNotBlank(pk)) {
			custProblem = custProblemService.get(CustProblem.class,
					Integer.parseInt(pk));
		} else {
			custProblem = new CustProblem();
		}
		custProblem.setAddessStatus(addessStatus);
		custProblem.setMobile1(mobile1);
		custProblem.setProjectDept3(projectDept3);
		custProblem.setDesignManDescr(designManDescr);
		custProblem.setDesignManPhone(designManPhone);
		custProblem.setProjectManPhone(projectManPhone);
		custProblem.setCheckOutDate(checkOutDate);
		custProblem.setCompStatus(compStatus);
		custProblem.setRemarks(remarks);
		custProblem.setCustType(custType.trim());
		custProblem.setAddress(address);
		custProblem.setCustCode(custCode);
		custProblem.setCustDescr(custdescr);
		custProblem.setProjectManDescr(projectManDescr);
		custProblem.setProjectDept2(projectDept2);
		custProblem.setCrtDate(crtDate);
		custProblem.setSource(source);
		custProblem.setCrtCZY(crtCZY);
		custProblem.setJsCrtDate(jsCrtDate);
		custProblem.setRcvDate(rcvDate);
		return new ModelAndView("admin/project/custProblem/custProblem_view")
				.addObject("custProblem", custProblem);
	}
	
	/**
	 * 导出Excel
	 * 
	 * @param request
	 * @param response
	 * @param dateFrom
	 * @param dateTo
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response,CustProblem custProblem) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = this.getUserContext(request);
		custProblem.setDealCZY(getUserContext(request).getCzybh().trim());
		custProblemService.findPageBySql(page, custProblem, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,
				page.getResult(),
				"客户投诉处理_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
				columnList, titleList, sumList);
	}
	/**
	 * 客户投诉处理
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doDeal")
	public void doDeal(HttpServletRequest request,
			HttpServletResponse response,String no,Integer pk, String dealDate,String dealRemarks) {
		custProblemService.dealCustCompaint(no,pk,dealDate,dealRemarks);
	}
	
	/**
	 * 修改custProblem
	 * 
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doRcv")
	public void doRcv(HttpServletRequest request,
			HttpServletResponse response,String planDealDate,Integer pk,String dealRemarks) {
		logger.debug("接收custProblem开始");
		custProblemService.doRcv(planDealDate, pk, new Date(),dealRemarks);
	}
	
	/**
	 * 跳转到修改custProblem页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam Map<String, String> parameters) {
	    
		logger.debug("跳转到修改PrjRegion页面");
		CustProblem custProblem =new CustProblem();
		custProblem.setAddress(parameters.get("address"));
		custProblem.setDealRemarks(parameters.get("dealRemarks"));
		custProblem.setRemarks(parameters.get("remarks"));
		custProblem.setPk(new Integer(parameters.get("pk")));
		
		return new ModelAndView("admin/project/custProblem/custProblem_update")
			.addObject("custProblem", custProblem);
	}
	
	/**
	 * 修改custProblem
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustProblem custProblem){
		logger.debug("修改PrjRegion开始");
		try{
			if(!"T".equals(custProblem.getExpired())){
				custProblem.setExpired("F");
			}
			custProblem.setLastUpdate(new Date());
			custProblem.setActionLog("EDIT");
			custProblem.setLastUpdatedBy(getUserContext(request).getCzybh());
			custProblemService.doUpdate(custProblem);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 集成进度问题
	 * 
	 * @param request
	 * @param response
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGridDetail(HttpServletRequest request,
			HttpServletResponse response, CustProblem custProblem)
			throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		custProblemService.findAllBySql(page, custProblem);
		return new WebPage<Map<String, Object>>(page);
	}
	/**
	 * 转售后
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goToCustService")
	public ModelAndView goToCustService(HttpServletRequest request, 
			HttpServletResponse response,Integer pk, String custCode ,String promRsnDescr,String custDescr) throws Exception {
		CustProblem custProblem = custProblemService.get(CustProblem.class, pk);
		CustComplaint custComplaint = custProblemService.get(CustComplaint.class,custProblem.getNo());
		custProblem.setCustCode(custCode);
		custProblem.setCustDescr(custDescr);
		custProblem.setPk(pk);
		String repMan = this.getUserContext(request).getCzybh().trim();
		Employee employee = employeeService.get(Employee.class, repMan);
		return new ModelAndView("admin/project/custService/custService_save")
			.addObject("employee", employee)
			.addObject("repDate", new Date())
			.addObject("custProblem", custProblem)
			.addObject("custComplaint", custComplaint)
			.addObject("isFromCustProblem", true);
	}
}
