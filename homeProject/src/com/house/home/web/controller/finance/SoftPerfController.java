package com.house.home.web.controller.finance;

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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.EmpForSoftPerf;
import com.house.home.entity.finance.SoftPerf;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.finance.SoftPerfService;
@Controller
@RequestMapping("/admin/softPerf")
public class SoftPerfController extends BaseController{
	
	@Autowired
	private SoftPerfService softPerfService;
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,SoftPerf softPerf) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		softPerfService.findPageBySql(page, softPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJListqGrid(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfService.findListPageBySql(page, softPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *获取统计周期编号
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,SoftPerf softPerf){
		
		return new ModelAndView("admin/finance/softPerf/softPerf_code").addObject("softPerf",softPerf);
	}
	
	/**
	 * 根据id查询softPerf详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSoftPerf")
	@ResponseBody
	public JSONObject getSoftPerf(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SoftPerf softPerf= softPerfService.get(SoftPerf.class, id);
		if(softPerf == null){
			return this.out("系统中不存在No="+id+"的周期编号", false);
		}
		return this.out(softPerf, true);
	}
	
	@RequestMapping("/goCountSoftPerJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCountSoftPerJqGrid(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfService.findCountSoftPerPageBySql(page, softPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/findCountSoftPerIndPageBySql")
	@ResponseBody
	public WebPage<Map<String,Object>> findCountSoftPerIndPageBySql(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfService.findCountSoftPerIndPageBySql(page, softPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEmpInfoJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getEmpInfoJqGrid(HttpServletRequest request,
			HttpServletResponse response,Employee employee) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfService.findEmpInfoPageBySql(page, employee);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param softPerf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReportJqGrid(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfService.findReportPageBySql(page, softPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goReportDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getReportDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfService.findReportDetailPageBySql(page, softPerf);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {

		return new ModelAndView("admin/finance/softPerf/softPerf_list");
	}
	
	@RequestMapping("/goCountComplete")
	public ModelAndView goCountComplete(HttpServletRequest request,
			HttpServletResponse response,String no ) throws Exception {
		
		//doGetSoftPerDetail(no,this.getUserContext(request).getCzybh());
		return new ModelAndView("admin/finance/softPerf/softPerf_count").addObject("no",no)
				.addObject("costRight", getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,String no ) throws Exception {
		
		return new ModelAndView("admin/finance/softPerf/softPerf_count")
		.addObject("no",no).addObject("view", "view")
		.addObject("costRight", getUserContext(request).getCostRight());
	}
	
	@RequestMapping("/goSoftPerfDetail")
	public ModelAndView goSoftPerfDetail(HttpServletRequest request,
			HttpServletResponse response,Integer pk) throws Exception {
		Map<String, Object> map= softPerfService.getSoftPerfDetail(pk);
		
		return new ModelAndView("admin/finance/softPerf/softPerf_softPerfDetail").addObject("map",map)
				.addObject("costRight", getUserContext(request).getCostRight());
	
	}
	
	//效果和上面一样，上面的要调用jqgrid，这个直接json转换
	@RequestMapping("/goSoftPerfDetail1")
	public ModelAndView goSoftPerfDetail1(HttpServletRequest request,
			HttpServletResponse response,Map<String , Object> map) throws Exception {
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		System.out.println(jsonObject.get("custdescr"));
		System.out.println(request.getParameter("map"));
		return new ModelAndView("admin/finance/softPerf/softPerf_softPerfDetail").addObject("map",jsonObject);
	
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdateEmp")
	public ModelAndView goUpdateEmp(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {

		return new ModelAndView("admin/finance/softPerf/softPerf_updateEmp");
	}
	
	/**
	 * 报表
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReport")
	public ModelAndView goReport(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {

		return new ModelAndView("admin/finance/softPerf/softPerf_report").addObject("softPerf",softPerf);
	}
	
	@RequestMapping("/goViewReport")
	public ModelAndView goViewReport(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {

		return new ModelAndView("admin/finance/softPerf/softPerf_viewReport")
		.addObject("softPerf", softPerf)
		.addObject("costRight", getUserContext(request).getCostRight());
	}
	
	/**
	 * 新增周期页面
	 * @param request
	 * @param response
	 * @param softPerf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response,SoftPerf softPerf) throws Exception {

		return new ModelAndView("admin/finance/softPerf/softPerf_addPeriod");
	}
	
	/**
	 * 编辑周期页面
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdatePeriod")
	public ModelAndView goUpdatePeriod(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		SoftPerf softPerf=null;
		if(StringUtils.isNotBlank(no)){
			softPerf= softPerfService.get(SoftPerf.class, no);
		}
		
		return new ModelAndView("admin/finance/softPerf/softPerf_updatePeriod")
		.addObject("softPerf", softPerf);
	}
	
	/**
	 * 新增员工信息页面
	 * @param request
	 * @param response
	 * @param empForSoftPerf
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSaveEmpInfo")
	public ModelAndView goSaveEmpInfo(HttpServletRequest request,
			HttpServletResponse response,EmpForSoftPerf empForSoftPerf) throws Exception {

		return new ModelAndView("admin/finance/softPerf/softPerf_saveEmpInfo");
	}
	
	/**
	 * 编辑员工信息页面
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdateEmpInfo")
	public ModelAndView goUpdateEmpInfo(HttpServletRequest request,
			HttpServletResponse response,Integer pk) throws Exception {
		Employee employee=null;
		EmpForSoftPerf empForSoftPerf=null;
		empForSoftPerf =softPerfService.get(EmpForSoftPerf.class, pk);
		if(StringUtils.isNotBlank(empForSoftPerf.getEmpCode())){
			employee=employeeService.get(Employee.class, empForSoftPerf.getEmpCode());
		}
		
		return new ModelAndView("admin/finance/softPerf/softPerf_updateEmpInfo")
		.addObject("empForSoftPerf", empForSoftPerf)
		.addObject("emp", employee);
	}
	
	/**
	 * 查看员工信息
	 * @param request
	 * @param response
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewEmpInfo")
	public ModelAndView goViewEmpInfo(HttpServletRequest request,
			HttpServletResponse response,Integer pk) throws Exception {
		Employee employee=null;
		EmpForSoftPerf empForSoftPerf=null;
		empForSoftPerf =softPerfService.get(EmpForSoftPerf.class, pk);
		if(StringUtils.isNotBlank(empForSoftPerf.getEmpCode())){
			employee=employeeService.get(Employee.class, empForSoftPerf.getEmpCode());
		}
		
		return new ModelAndView("admin/finance/softPerf/softPerf_viewEmpInfo")
		.addObject("empForSoftPerf", empForSoftPerf)
		.addObject("emp", employee);
	}
	
	/**
	 * 新增归属员工信息
	 * @param request
	 * @param response
	 * @param empForSoftPerf
	 */
	@RequestMapping("/doSaveEmpInfo")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response ,EmpForSoftPerf empForSoftPerf){
		logger.debug("新增员工信息");
		try{
			empForSoftPerf.setLastUpdate(new Date());
			empForSoftPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			empForSoftPerf.setExpired("F");
			empForSoftPerf.setActionLog("ADD");
			
			this.softPerfService.save(empForSoftPerf);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增员工信息失败");
		}
	}
	
	/**
	 * 编辑归属员工信息
	 * @param request
	 * @param response
	 * @param empForSoftPerf
	 */
	@RequestMapping("/doUpdateEmpInfo")
	public void doUpdateEmpInfo(HttpServletRequest request ,
			HttpServletResponse response ,EmpForSoftPerf empForSoftPerf){
		logger.debug("新增员工信息");
		try{
			empForSoftPerf.setLastUpdate(new Date());
			empForSoftPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			empForSoftPerf.setExpired("F");
			empForSoftPerf.setActionLog("EDIT");
			
			this.softPerfService.update(empForSoftPerf);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增员工信息失败");
		}
	}
	
	/**
	 *  新增统计周期
	 * @param request
	 * @param response
	 * @param softPerf
	 */
	@RequestMapping("/doSavePeriod")
	public void doSavePeriod(HttpServletRequest request ,
			HttpServletResponse response ,SoftPerf softPerf){
		logger.debug("新增统计周期");
		try{
			if(softPerf.getMon() != null){
				List<Map<String, Object>> list = softPerfService.checkMonExists("", softPerf.getMon());
				if(list != null){
					ServletUtils.outPrintFail(request, response, "新增统计周期失败,月份已经存在,不能重复新增");
					return;
				}
			} else {
				ServletUtils.outPrintFail(request, response, "新增统计周期失败,月份不能为空");
				return;
			}
			
			softPerf.setNo(	softPerfService.getSeqNo("tSoftPerf"));
			softPerf.setLastUpdate(new Date());
			softPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			softPerf.setExpired("F");
			softPerf.setActionLog("Add");
			
			this.softPerfService.save(softPerf);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	
	@RequestMapping("/doUpdatePeriod")
	public void doUpdatePeriod(HttpServletRequest request ,
			HttpServletResponse response ,SoftPerf softPerf){
		logger.debug("编辑统计周期");
		try{
			if(softPerf.getMon() != null){
				List<Map<String, Object>> list = softPerfService.checkMonExists("", softPerf.getMon());
				if(list != null){
					ServletUtils.outPrintFail(request, response, "编辑统计周期失败,月份已经存在");
					return;
				}
			} else {
				ServletUtils.outPrintFail(request, response, "编辑统计周期失败,月份不能为空");
				return;
			}
			
			softPerf.setLastUpdate(new Date());
			softPerf.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			softPerf.setExpired("F");
			softPerf.setActionLog("EDIT");
			
			this.softPerfService.update(softPerf);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增统计周期失败");
		}
	}
	
	@RequestMapping("/isExistsPeriod")
	@ResponseBody
	public String isExistsPeriod(HttpServletRequest request,HttpServletResponse response,
			String no,String beginDate){
	
		return softPerfService.isExistsPeriod(no,beginDate);
	}
	
	@RequestMapping("/checkStatus")
	@ResponseBody
	public String checkStatus(HttpServletRequest request,HttpServletResponse response,
			String no){
		
		return softPerfService.checkStatus(no);
	}
	
	@RequestMapping("/doSaveCount")
	public void doSaveCount(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("删除PrjProg开始");
		
		this.softPerfService.doSaveCount(no);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doCount")
	public void doGetSoftPerDetail(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("删除PrjProg开始");
		
		this.softPerfService.doGetSoftPerDetail(no,this.getUserContext(request).getCzybh());
		
		ServletUtils.outPrintSuccess(request, response,"生成数据成功");

	}
	
	@RequestMapping("/doSaveCountBack")
	public void doSaveCountBack(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("删除PrjProg开始");
		
		this.softPerfService.doSaveCountBack(no);
		
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 主页面excel
	 * @param request
	 * @param response
	 * @param softPerf
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SoftPerf softPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		softPerfService.findListPageBySql(page, softPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"业绩统计周期_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 员工信息页面excel
	 * @param request
	 * @param response
	 * @param employee
	 */
	@RequestMapping("/doEmpInfoExcel")
	public void doEmpInfoExcel(HttpServletRequest request ,HttpServletResponse response,
			Employee employee){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		softPerfService.findEmpInfoPageBySql(page, employee);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"员工信息调整_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	 
	@RequestMapping("/doCountExcel")
	 public void doCountExcel(HttpServletRequest request ,HttpServletResponse response,
			SoftPerf softPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		softPerfService.findCountSoftPerPageBySql(page, softPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装业绩_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
	
	 @RequestMapping("/doReportExcel")
	 public void doReportExcel(HttpServletRequest request ,HttpServletResponse response,
			SoftPerf softPerf){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		softPerfService.findReportPageBySql(page, softPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"报表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
	 
	 @RequestMapping("/doExcelReportDetail")
	 public void doExcelReportDetail(HttpServletRequest request ,HttpServletResponse response,
			SoftPerf softPerf,String designMan1,String buyer1){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		String title="";
		if(StringUtils.isNotBlank(designMan1)){
			softPerf.setDesignMan(designMan1);
		}
		if(StringUtils.isNotBlank(buyer1)){
			softPerf.setBuyer(buyer1);
		}
		if(StringUtils.isNotBlank(softPerf.getDesignMan())){
			title="设计师";
		}else if(StringUtils.isNotBlank(softPerf.getBusinessMan())){
			title="业务员";
		}else{
			title="买手";
		}
		softPerfService.findReportDetailPageBySql(page, softPerf);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"报表明细-按"+title+"_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	 }
	
	
}
