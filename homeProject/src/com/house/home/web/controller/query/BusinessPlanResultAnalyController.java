package com.house.home.web.controller.query;

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

import antlr.collections.List;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemApp;
import com.house.home.entity.insales.ItemReq;
import com.house.home.service.query.BusinessPlanResultAnalyService;
import com.house.home.service.workflow.DepartmentService;
@Controller
@RequestMapping("/admin/businessPlanResultAnaly")
public class BusinessPlanResultAnalyController extends BaseController {
	@Autowired
	private BusinessPlanResultAnalyService businessPlanResultAnalyService;
	@Autowired
	private DepartmentService departmentService;
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
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		businessPlanResultAnalyService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,	
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		businessPlanResultAnalyService.findBusinessDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		String department=departmentService.getChildDeptStr(getUserContext(request).getCzybh().toString());
		customer.setDepartment(department);
		customer.setDateFrom(new Date());
		return new ModelAndView("admin/query/businessPlanResultAnaly/businessPlanResultAnaly_list")
			.addObject("customer", customer);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/businessPlanResultAnaly/businessPlanResultAnaly_view")
			.addObject("customer", customer);
	}
	
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		businessPlanResultAnalyService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业务员计划结果分析"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer,String excelName){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		businessPlanResultAnalyService.findBusinessDetail(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				excelName+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 检查部门权限（本部门+权限部门）
	 * @param request
	 * @param response
	 * @param no
	 */
	@RequestMapping("/checkDeptAuth")
	public void checkDeptAuth(HttpServletRequest request,HttpServletResponse response,String department,String empCode){
		try{
			boolean flag=false;
			String custRight=getUserContext(request).getCustRight();
			String czybh=getUserContext(request).getCzybh();
			
			if(StringUtils.isNotBlank(custRight)){
				if("1".equals(custRight.trim())){//查看本人
					if(empCode.equals(czybh.trim())){
						flag=true;
					}
				}else if("2".equals(custRight.trim())){//查看本部门，操作员本部门+权限部门列表包含所选部门，可查看
					Page<Map<String,Object>> page = this.newPageForJqGrid(request);
					page.setPageSize(-1);
					departmentService.findDeptList(page, getUserContext(request).getCzybh(), "","1");
					for (int i = 0; i < page.getResult().size(); i++) {
						if(page.getResult().get(i).containsValue(department)){
							flag=true;
							break;
						}
					}
				}else if("3".equals(custRight.trim())){//查看所有
					flag=true;
				}
			}
			
			if(flag){
				ServletUtils.outPrintSuccess(request, response);
			}else{
				ServletUtils.outPrintFail(request, response, "没有查看权限！");
				return;
			}
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "点击失败");
			e.printStackTrace();
		}
	}
}
