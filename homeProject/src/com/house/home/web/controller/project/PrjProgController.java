package com.house.home.web.controller.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.resp.UploadPhotoResp;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.PrjProblem;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.PrjProgConfirm;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.entity.project.ProgTempDt;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.PersonMessageService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.CustWorkerAppService;
import com.house.home.service.project.PrjProblemService;
import com.house.home.service.project.PrjProgCheckService;
import com.house.home.service.project.PrjProgConfirmService;
import com.house.home.service.project.PrjProgPhotoService;
import com.house.home.service.project.PrjProgService;
import com.house.home.service.project.PrjProgTempService;

@Controller
@RequestMapping("/admin/prjProg")
public class PrjProgController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjProgController.class);

	@Autowired
	private PrjProgService prjProgService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PrjProgTempService prjProgTempService;
	@Autowired 
	private PrjProgPhotoService prjProgPhotoService;
	@Autowired
	private PrjProgCheckService prjProgCheckService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PrjProgConfirmService prjProgConfirmService;
	@Autowired
	private PersonMessageService personMessageService;
	@Autowired
	private CustWorkerAppService custWorkerAppService;
	@Autowired
	private PrjProblemService prjProblemService;
	
	
	String savePhotoPath;
	String savePath;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goUpdateStopJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goUpdateStopJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findUpdateStopPageBySql(page, prjProg,this.getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findPageBySql(page, prjProg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goBuilderRepJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getBuilderRepJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findBuilderRepPageBySql(page, prjProg);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goCustComplainJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCustComplainJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findCustComplainPageBySql(page, prjProg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goConfirmJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getConfirmJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		UserContext uc = getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findConfirmPageBySql(page, prjProg,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrjProgUpdateJDJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPrjProgUpdateJDJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findPrjProgUpdateJDPageBySql(page, prjProg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPrjLogJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrjLogJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProg prjProg) throws Exception {
		if(prjProg.getIsDelay()==null){
			prjProg.setIsDelay("");
			
		}
		if(prjProg.getLogDescr()==null){
			prjProg.setLogDescr("");
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findPrjLogPageBySql(page, prjProg);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCustWorkerAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goCustWorkerAppJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findCustWorkerAppPageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goAppNoArrangeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goAppNoArrangeJqGrid(HttpServletRequest request,
			HttpServletResponse response,CustWorkerApp custWorkerApp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custWorkerAppService.findAppNoArrangePageBySql(page, custWorkerApp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goPrjProblemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPrjProblemJqGrid(HttpServletRequest request,
			HttpServletResponse response,PrjProblem prjProblem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProblemService.findPrjProblemPageBySql(page, prjProblem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 获取工程进度模板明细
	 * @author	created by zb
	 * @date	2020-4-3--下午6:57:46
	 * @param request
	 * @param response
	 * @param progTempDt
	 * @return
	 */
	@RequestMapping("/getProgTempDt")	
	@ResponseBody
	public ProgTempDt getProgTempDt(HttpServletRequest request ,HttpServletResponse response,ProgTempDt progTempDt){
		ProgTempDt pTempDt = new ProgTempDt(); 
		ProgTempDt pTempDt2 = this.prjProgService.getProgTemDt(progTempDt);
		if(null != pTempDt2) {
			return pTempDt2;
		}
		return pTempDt;
	}
	
	/**
	 * PrjProg_list列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Customer customer=new Customer();
		customer.setDepartment1("03");
		customer.setStatus("4");

		return new ModelAndView("admin/project/prjProg/prjProg_list").addObject("customer", customer);
	}
	
	/**
	 * 跳转到模板设定页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			Customer ct){
		logger.debug("跳转到新增PrjProg页面");
		Customer customer = null;
		PrjProgTemp prjProgTemp= null;
		customer=customerService.get(Customer.class, ct.getCode());
		customer.setIsPrjProgTemp(ct.getIsPrjProgTemp());
		customer.setProjectManDescr(ct.getProjectManDescr());
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
			prjProgTemp=prjProgTempService.get(PrjProgTemp.class,customer.getPrjProgTempNo());
			customer.setPrjProgTempNoDescr(prjProgTemp==null?"":prjProgTemp.getDescr());
			customer.setPrjProgTempType(prjProgTemp==null?"":prjProgTemp.getType());
		}
		if(customer.getConfirmBegin()==null){
			customer.setConfirmBegin(new Date());
		}
		
		return new ModelAndView("admin/project/prjProg/prjProg_save")
			.addObject("customer", customer);
	}
	
	/**
	 * 跳转到工程进度编辑
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到工程进度编辑");
		PrjProg prjProg=new PrjProg();
		Customer customer=null;
		PrjProgTemp prjProgTemp= null;
		Employee employee =null;
		customer = customerService.get(Customer.class, id);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		employee=employeeService.get(Employee.class, customer.getDesignMan());
		if(StringUtils.isNotBlank(customer.getDesignMan())){
			customer.setDesignManDescr(employee.getNameChi());
		}
		
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
			prjProgTemp=prjProgTempService.get(PrjProgTemp.class,customer.getPrjProgTempNo());
			customer.setPrjProgTempNoDescr(prjProgTemp==null?"":prjProgTemp.getDescr());
		}
		return new ModelAndView("admin/project/prjProg/prjProg_update").addObject("customer", customer).addObject("prjProg",prjProg);
	}
	
	/**
	 * 跳转到工地验收页面
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response
			,PrjProg prjProg ){
		logger.debug("跳转到工地验收页面");
		
		prjProg.setDepartment1("03");
		
		return new ModelAndView("admin/project/prjProg/prjProg_confirm").addObject("prjProg", prjProg);
		
	}
	
	/**
	 * 跳转到整改验收页面
	 * @return
	 */
	@RequestMapping("/goModifyConfirm")
	public ModelAndView goModifyConfirm(HttpServletRequest request, HttpServletResponse response,
			PrjProgCheck prjProgCheck ){
		logger.debug("跳转到工地验收页面");
		prjProgCheck.setStatusZG("1");
		prjProgCheck.setDepartment1("03");
		return new ModelAndView("admin/project/prjProg/prjProg_modifyConfirm").addObject("prjProgCheck",prjProgCheck);
	}
	
	/**
	 *跳转到延误备注页面 
	 * @return
	 **/
	@RequestMapping("/goPrgRemark")
	public ModelAndView goPrgRemark(HttpServletRequest request, HttpServletResponse response,
			String id){
		logger.debug("跳转到工地验收页面");
		Customer customer=null;
		String unshow="";
		customer=customerService.get(Customer.class, id);
		if("5".equals(customer.getConstructStatus())){
			unshow="0,1,2,3,4,6,7";
		}else{
			unshow="5";
		}
		return new ModelAndView("admin/project/prjProg/prjProg_prgRemark").addObject("customer",customer).addObject("unshow",unshow);
	}
	
	/**
	 * 跳转到工程进度——按项目经理查看
	 * @return
	 */
	@RequestMapping("/goProjectManView")
	public ModelAndView goProjectManView(HttpServletRequest request, HttpServletResponse response
			){
		logger.debug("跳转到按项目经理查看页面");
		Employee employee=new Employee();
		employee.setDepartment1("03");
		return new ModelAndView("admin/project/prjProg/prjProg_view_projectMan").addObject("employee",employee);
	}
	
	/**
	 * 跳转到工程进度查看页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrjProg页面");

		Customer customer=null;
		PrjProgTemp prjProgTemp=null;
		customer = customerService.get(Customer.class, id);
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
		prjProgTemp=prjProgTempService.get(PrjProgTemp.class,customer.getPrjProgTempNo());
		customer.setPrjProgTempNoDescr(prjProgTemp.getDescr());
		}
		return new ModelAndView("admin/project/prjProg/prjProg_view").addObject("customer", customer);
	}
	
	@RequestMapping("/goUpdateStop")
	public ModelAndView goUpdateStop(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增PrjProg页面");

		return new ModelAndView("admin/project/prjProg/prjProg_updateStop");
	}
	
	/**
	 * 跳转工程进度——编辑——新增
	 * @return 
	 * 
	 **/
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		logger.debug("跳转到工程进度——增加");
		prjProg.setPlanBegin(new Date());
		prjProg.setPlanEnd(new Date());
		return new ModelAndView("admin/project/prjProg/prjProg_add").addObject("prjProg",prjProg);
	}
	
	@RequestMapping("/doUpdateAdd")
	public void doUpdateAdd(HttpServletRequest request, HttpServletResponse response, PrjProg prjProg){
		logger.debug("添加PrjProg开始");
		try{
			prjProg.setLastUpdate(new Date());
			prjProg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProg.setActionLog("ADD");
			prjProg.setExpired("F");
			this.prjProgService.save(prjProg);
			
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 跳转到工程进度——编辑——快速新增
	 * @return 
	 * 
	 **/
	@RequestMapping("/goFastAdd")
	public ModelAndView goFastAdd(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到工程快速进度——增加");
		PrjProg prjProg= new PrjProg();
		prjProg.setPlanBegin(new Date());
		prjProg.setPlanEnd(new Date());
		return new ModelAndView("admin/project/prjProg/prjProg_fast_add").addObject("prjProg",prjProg);
	}
	
	/**
	 * 跳转到工程进度——编辑——复制
	 * @return 
	 * 
	 **/
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		logger.debug("跳转到工程快速进度——增加");
		
		return new ModelAndView("admin/project/prjProg/prjProg_add").addObject("prjProg",prjProg);
	}
	
	/**
	 *跳转到工程进度——编辑——编辑
	 *@return
	 * 
	 **/
	@RequestMapping("/goUpdateUpdate")
	public ModelAndView goUpdateUpdate(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		logger.debug("跳转到工程快速进度——增加");
			
		return new ModelAndView("admin/project/prjProg/prjProg_addUpdate").addObject("prjProg",prjProg);
		
	} 
	
	@RequestMapping("/doUpdateUpdate")
	public void doUpdateUpdate(HttpServletRequest request, HttpServletResponse response, PrjProg prjProg){
		logger.debug("添加PrjProg开始");
		PrjProg pp=null;
		try{
			
			pp=prjProgService.get(PrjProg.class,prjProg.getPk());
			pp.setPrjItem(prjProg.getPrjItem());
			pp.setPlanBegin(prjProg.getPlanBegin());
			pp.setPlanEnd(prjProg.getPlanEnd());
			pp.setBeginDate(prjProg.getBeginDate());
			pp.setEndDate(prjProg.getEndDate());
			pp.setLastUpdate(new Date());
			pp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			pp.setActionLog("Edit");
			pp.setExpired("F");
			this.prjProgService.update(pp);
			
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doUpdateDelete")
	public void doUpdateDelete(HttpServletRequest request, HttpServletResponse response, PrjProg prjProg){
		logger.debug("添加PrjProg开始");
		try{
			PrjProg pp=new PrjProg();
			
			pp=prjProgService.get(PrjProg.class, prjProg.getPk());
			
			this.prjProgService.delete(pp);
			
			ServletUtils.outPrintSuccess(request, response,"删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 跳转到工程进度——编辑——查看
	 * @return 
	 * 
	 **/
	@RequestMapping("/goAddView")
	public ModelAndView goAddView(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		logger.debug("跳转到工程快速进度——增加");
		prjProg.setReadonly("1");
		PrjProg pp=new PrjProg();
		if(prjProg.getPk()!=null){
			pp=prjProgService.get(PrjProg.class, prjProg.getPk());
			prjProg.setAdjPlanEnd(pp.getAdjPlanEnd());
		}
		return new ModelAndView("admin/project/prjProg/prjProg_updateView").addObject("prjProg",prjProg);
	}
	
	/**
	 * 跳转到工程进度——查看——查看
	 * @return 
	 * 
	 **/
	@RequestMapping("/goViewView")
	public ModelAndView goViewView(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		logger.debug("跳转到工程快速进度——增加");
		PrjProg pp=new PrjProg();
		pp=prjProgService.get(PrjProg.class, prjProg.getPk());
		prjProg.setAdjPlanEnd(pp.getAdjPlanEnd());
		return new ModelAndView("admin/project/prjProg/prjProg_viewView").addObject("prjProg",prjProg);
	
	}
	
	/**
	 * 跳转到工程进度——查看——查看
	 * @return 
	 * 
	 **/
	@RequestMapping("/goViewConsPhoto")
	public ModelAndView goViewConsPhoto(HttpServletRequest request, HttpServletResponse response,
			PrjProgPhoto prjProgPhoto){
		logger.debug("工程进度查看施工图片");
		
		return new ModelAndView("admin/project/prjProg/prjProg_viewContPhoto").addObject("prjProgPhoto",prjProgPhoto);
	
	}
	
	/**
	 * 跳转到工程进度——查看——查看
	 * @return 
	 * 
	 **/
	@RequestMapping("/goViewCheckPhoto")
	public ModelAndView goViewCheckPhoto(HttpServletRequest request, HttpServletResponse response,
			PrjProgPhoto prjProgPhoto){
		logger.debug("工程进度查看施工图片");
		
		return new ModelAndView("admin/project/prjProg/prjProg_checkPhoto").addObject("prjProgPhoto",prjProgPhoto);
	
	}
	
	/**
	 * 跳转到工程进度——查看——查看
	 * @return 
	 * 
	 **/
	@RequestMapping("/goViewConfPhoto")
	public ModelAndView goViewConfPhoto(HttpServletRequest request, HttpServletResponse response,
			PrjProgPhoto prjProgPhoto){
		logger.debug("工程进度查看施工图片");
		
		return new ModelAndView("admin/project/prjProg/prjProg_confirmPhoto").addObject("prjProgPhoto",prjProgPhoto);
	
	}
	
	
	
	/**
	 * 跳转到工程进度——查看——查看
	 * @return 
	 * 
	 **/
	@RequestMapping("/goPrjLog")
	public ModelAndView goPrjLog(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		logger.debug("跳转到工程快速进度——增加");
		Customer customer=new Customer();
		Employee employee=null;
		customer=customerService.get(Customer.class, prjProg.getCustCode());
		//employee=employeeService.get(Employee.class, customer.getProjectMan());
		//customer.setProjectManDescr(employee.getNameChi());
		
		return new ModelAndView("admin/project/prjProg/prjProg_prjLog")
			.addObject("prjProg",prjProg).addObject("customer",customer);
	
	}
	
	/**
	 * 跳转到巡检验收查询 
	 * 
	 **/
	@RequestMapping("/goCheckAndConfirm")
	public ModelAndView goCheckAndConfirm(HttpServletRequest request, HttpServletResponse response,PrjProgCheck prjProgCheck){
		logger.debug("跳转到工程快速进度——增加");
		
		prjProgCheck.setDepartment1("03");

		return new ModelAndView("admin/project/prjProg/prjProg_checkAndConfirm").addObject("customer",prjProgCheck);
	}
	
	/**
	 * 跳转到查看PrjProg页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrjProg页面");
		PrjProg prjProg = prjProgService.get(PrjProg.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/prjProg/prjProg_detail")
				.addObject("prjProg", prjProg);
	}
	
	/**
	 * 跳转到查看工程进度——工地验收——查看
	 * @return
	 */
	@RequestMapping("/goViewGDYS")
	public ModelAndView goViewGDYS(HttpServletRequest request, HttpServletResponse response
			,PrjProg prjprog){
		logger.debug("跳转到查看PrjProg页面");
		
		PrjProg pp =null;
		Customer customer=null;	

		pp=prjProgService.get(PrjProg.class, prjprog.getPk());
		pp.setPhotoLastUpDate(prjprog.getPhotoLastUpDate());
		customer=customerService.get(Customer.class, pp.getCustCode());
		if(customer!=null){
			pp.setAddress(customer.getAddress());
		}
		return new ModelAndView("admin/project/prjProg/prjProg_view_GDYS").addObject("prjProg", pp);
	}
	
	/**
	 * 跳转到查看工程进度——工地验收——查看
	 * @return
	 */
	@RequestMapping("/goGDYS")
	public ModelAndView goGDYSDetail(HttpServletRequest request, HttpServletResponse response
			,String no ){
		logger.debug("跳转到查看PrjProg页面");
		PrjProgConfirm prjProgConfirm=null;
		Customer customer=null;	
		String htmlStr = "";

		prjProgConfirm=prjProgConfirmService.get(PrjProgConfirm.class, no);
		if(prjProgConfirm.getCustScore()!=null){
			prjProgConfirm.setCustScoreComfirm(prjProgConfirm.getCustScore().toString()+"星");
		}
			customer=customerService.get(Customer.class, prjProgConfirm.getCustCode());
		if(customer!=null){
			prjProgConfirm.setAddress(customer.getAddress());
		}
		List<Map<String, Object>> confirmCustomFiledList = prjProgConfirmService.getConfirmCustomFiledList(prjProgConfirm.getPrjItem(),no );
		for (int i = 0; i < confirmCustomFiledList.size(); i++) {
			Map<String, Object> confirmCustomFiledMap=confirmCustomFiledList.get(i);
			if("1".equals(confirmCustomFiledMap.get("Type"))) {
				htmlStr+="<li>"
						+"<label>"+confirmCustomFiledMap.get("Descr").toString()+"</label>\n"
						+"<input type='text' style='width:160px;'  value="+confirmCustomFiledMap.get("Value").toString()+" readonly='readonly'/>"
						+"</li>";
			}
			if("2".equals(confirmCustomFiledMap.get("Type").toString())) {
				htmlStr+="<li>"
						+"<label class='control-textarea' >"+confirmCustomFiledMap.get("Descr").toString()+"</label>\n"
						+"<textarea style='width:160px' rows='2' readonly='readonly'>"+confirmCustomFiledMap.get("Value").toString()+"</textarea>"
						+"</li>";
			}
		}
		return new ModelAndView("admin/project/prjProg/prjprog_GDYSDetail").addObject("prjProgConfirm", prjProgConfirm)
				.addObject("htmlStr", htmlStr);
	}
	
	@RequestMapping("/goViewAllPic")
	public ModelAndView goViewAllPic(HttpServletRequest request, HttpServletResponse response
			,String no ){
		logger.debug("跳转到查看PrjProg页面");
		PrjProgConfirm prjProgConfirm=null;
		PrjProgPhoto prjProgPhoto =new PrjProgPhoto();
		Customer customer=null;	
		String photoPath= FileUploadUtils.DOWNLOAD_URL+PrjProgNewUploadRule.FIRST_LEVEL_PATH;
		prjProgConfirm=prjProgConfirmService.get(PrjProgConfirm.class, no);
			customer=customerService.get(Customer.class, prjProgConfirm.getCustCode());
		if(customer!=null){
			prjProgConfirm.setAddress(customer.getAddress());
		}
		prjProgPhoto.setCustCode(prjProgConfirm.getCustCode());
		prjProgPhoto.setRefNo(prjProgConfirm.getNo());
		//prjProgPhotoService.updatePhotoPustStatus(prjProgPhoto);
		return new ModelAndView("admin/project/prjProg/prjProg_allPic").addObject("prjProgConfirm", prjProgConfirm)
				.addObject("photoPath",photoPath);
	}
	
	/**
	 * 跳转到查看工程进度——工地验收——验收
	 * @return
	 */
	@RequestMapping("/goUpdateConfirm")
	public ModelAndView goUpdateConfirm(HttpServletRequest request, HttpServletResponse response
			,String no ){
		logger.debug("跳转到查看PrjProg页面");
		PrjProgConfirm prjProgConfirm=null;
		Customer customer=null;	
		prjProgConfirm=prjProgConfirmService.get(PrjProgConfirm.class, no);
		if(prjProgConfirm.getCustScore()!=null){
			prjProgConfirm.setCustScoreComfirm(prjProgConfirm.getCustScore().toString()+"星");
		}
		customer=customerService.get(Customer.class, prjProgConfirm.getCustCode());
			if(customer!=null){
				prjProgConfirm.setAddress(customer.getAddress());
			}
				
		
		return new ModelAndView("admin/project/prjProg/prjProg_updateConfirm").addObject("prjProgConfirm", prjProgConfirm);
	}
	
	/**
	 * 跳转到工程进度——整改验收——查看
	 * @return
	 */
	@RequestMapping("/goViewGDXJ")
	public ModelAndView goViewGDXJ(HttpServletRequest request, HttpServletResponse response
			,String id,String a){
		logger.debug("跳转到查看PrjProg页面");
		Customer customer=null;
		PrjProgCheck prjProgCheck=null;
		prjProgCheck=prjProgCheckService.get(PrjProgCheck.class, id);
		if(prjProgCheck.getCustScore()!=null){
			prjProgCheck.setCustScoreCheck(prjProgCheck.getCustScore().toString()+"星");
		}
		customer= customerService.get(Customer.class, prjProgCheck.getCustCode());
		prjProgCheck.setAddress(customer.getAddress());
		prjProgCheck.setRemainModifyTime(a);
		return new ModelAndView("admin/project/prjProg/prjProg_confirmCheck2").addObject("prjProgCheck", prjProgCheck);
	}
	
	/**
	 * 跳转到工程进度——整改验收——验收
	 * @return
	 */
	@RequestMapping("/goConfirmCheck")
	public ModelAndView goConfirmCheck(HttpServletRequest request, HttpServletResponse response
			,PrjProgCheck ppc){
		logger.debug("跳转到查看PrjProg页面");
		Customer customer=null;
		PrjProgCheck prjProgCheck=null;
		prjProgCheck=prjProgCheckService.get(PrjProgCheck.class, ppc.getNo());
		customer= customerService.get(Customer.class, prjProgCheck.getCustCode());
		prjProgCheck.setAddress(customer.getAddress());
		prjProgCheck.setProjectMan(ppc.getProjectMan());
		prjProgCheck.setPrjDescr(ppc.getPrjDescr());
		prjProgCheck.setRemainModifyTime(ppc.getRemainModifyTime());
		return new ModelAndView("admin/project/prjProg/prjProg_confirmCheck1").addObject("prjProgCheck", prjProgCheck);
	}
	
	/**
	 * 跳转到工程进度——整改验收——查看
	 * @return
	 */
	@RequestMapping("/goConfrimCheck")
	public ModelAndView goConfrimCheck(HttpServletRequest request, HttpServletResponse response
			,String id){
		logger.debug("跳转到查看PrjProg页面");
		
		PrjProgCheck prjProgCheck=null;
		prjProgCheck=prjProgCheckService.get(PrjProgCheck.class, id);
		
		return new ModelAndView("admin/project/prjProg/prjProj_ConfiemCheck").addObject("prjProgCheck", prjProgCheck);
	}
	
	/**
	 * 跳转到工程进度——整改验收——查看
	 * @return
	 */
	@RequestMapping("/goCheck")
	public ModelAndView goCheck(HttpServletRequest request, HttpServletResponse response
			,PrjProg prjProg){
		logger.debug("跳转到查看PrjProg页面");
		
		PrjProg pp =null;
		Customer customer=null;
		Employee employee=null;
		pp=prjProgService.get(PrjProg.class, prjProg.getPk());
		pp.setProjectMan(prjProg.getProjectMan());
		pp.setPrjDescr(prjProg.getPrjDescr());
		pp.setPhotoLastUpDate(prjProg.getPhotoLastUpDate());
		customer=customerService.get(Customer.class, pp.getCustCode());
		if(customer!=null){
			pp.setAddress(customer.getAddress());
		}
		pp.setConfirmDate(new Date());
		pp.setConfirmCZY(this.getUserContext(request).getCzybh());
		employee=employeeService.get(Employee.class, pp.getConfirmCZY());
		pp.setConfirmCZYDescr(this.getUserContext(request).getZwxm());
		pp.setConfirmDesc("");
		return new ModelAndView("admin/project/prjProg/prjProg_checkOut_GDYS").addObject("prjProg", pp);
	}
	
	/**
	 * 查看图片页面
	 * 
	 * */
	@RequestMapping("/goPicture")
	public ModelAndView goPicture(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg ){
		
		PrjProgNewUploadRule rule = new PrjProgNewUploadRule(prjProg.getPhotoName(), prjProg.getPhotoName().substring(0, 5));
		String photoPath= FileUploadUtils.getFileUrl(rule.getFullName());
		prjProg.setPhotoPath(photoPath);
		
		return new ModelAndView("admin/project/prjProg/prjProg_picture").addObject("prjProg",prjProg);
	}
	
	/**
	 * 查看图片页面
	 * 
	 * */
	@RequestMapping("/goPictureView")
	public ModelAndView goPictureView(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg ){
		
		PrjProgNewUploadRule rule = new PrjProgNewUploadRule(prjProg.getPhotoName(), prjProg.getPhotoName().substring(0, 5));
		String photoPath= FileUploadUtils.getFileUrl(rule.getFullName());
		prjProg.setPhotoPath(photoPath);
		
		return new ModelAndView("admin/project/prjProg/prjProg_pictureView").addObject("prjProg",prjProg);
	}
	
	/**
	 * 查看图片页面
	 * 
	 * */
	@RequestMapping("/goXjPicture")
	public ModelAndView goXjPicture(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg ){
		PrjProgNewUploadRule rule = 
				new PrjProgNewUploadRule(prjProg.getPhotoName(),prjProg.getPhotoName().substring(0, 5));
		prjProg.setPhotoPath(FileUploadUtils.getFileUrl(rule.getFullName()));
		
		return new ModelAndView("admin/project/prjProg/prjProg_xjPicture").addObject("prjProg",prjProg);
	}
	/**
	 * 删除巡检图片
	 * */
	@RequestMapping("/ajaxDelPicture")	
	@ResponseBody
	public PrjProg delPicture(HttpServletRequest request ,HttpServletResponse response,PrjProg prjProg){
		
		System.out.println("1");
		this.prjProgService.doDelPicture(prjProg.getPhotoName());
		
		return prjProg;
	}
	
	/**
	 * 查看图片页面
	 * 
	 * */
	@RequestMapping("/ajaxGetPicture")
	@ResponseBody
	public PrjProg getPicture(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		PrjProgNewUploadRule rule = 
				new PrjProgNewUploadRule(prjProg.getPhotoName(), prjProg.getPhotoName().substring(0, 5));
		String photoPath= FileUploadUtils.getFileUrl(rule.getFullName());
		
		if(prjProg.getReadonly().equals("1") || prjProg.getReadonly().equals("2") 
				|| prjProg.getReadonly().equals("3") || prjProg.getReadonly().equals("4")){
			prjProg.setPhotoPath(photoPath);
		}
		
		return prjProg;
	
	}
	
	@RequestMapping("/ajaxSavePhotoName")
	@ResponseBody
	public PrjProg savePhotoName(HttpServletRequest request, HttpServletResponse response,PrjProg prjProg){
		
		try {
			PrjProgPhoto ppp=new PrjProgPhoto();	
			ppp.setCustCode(prjProg.getCustCode());
			ppp.setPrjItem(prjProg.getPrjItem());
			ppp.setPhotoName(savePhotoPath);
			ppp.setLastUpdate(new Date());
			ppp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			ppp.setActionLog("add");
			ppp.setExpired("F");
			ppp.setType("1");
			ppp.setIsPushCust("1");
			this.prjProgPhotoService.save(ppp);
			prjProg.setPhotoPath(savePath);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
		
		return prjProg;
	
	}
	
	
	/**
	 * 添加模板设定 保存
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("添加PrjProg开始");
		try{
			Customer ct =null;
			ct=customerService.get(Customer.class, customer.getCode());
			if(customer.getPrjProgTempNo()!=null){
				ct.setPrjProgTempNo(customer.getPrjProgTempNo());
				ct.setConfirmBegin(customer.getConfirmBegin());
				ct.setPrjProgTempNoDescr(customer.getPrjProgTempNoDescr());
				ct.setConfirmEnd(ct.getConfirmBegin());
			}
				this.customerService.prjProgDeleteCustCode(ct.getCode());//删除原有数据
				//插入新的模板明细
				this.prjProgService.doSavePrjProg(ct.getCode(), ct.getConfirmBegin(),ct.getConfirmEnd(),
						this.getUserContext(request).getCzybh(), ct.getPrjProgTempNo(),customer.getPrjProgTempType())  ;
					
				//this.prjProgService.doSavePrjProgBeginDate();
				//更新客户表的PrjProgTempNo、ConfirmBegin
				this.customerService.update(ct);
				
				//回滚：设置模板的开工时间时，同时更新第一个节点的开工时间 modify by zb on 20200413
				Map<String, Object>  map= prjProgService.getMaxPk(customer.getCode());
				int pk = (Integer) map.get("PK");
				this.prjProgService.updatePrjProgForProc(pk, "1", ct.getConfirmBegin(), 
						this.getUserContext(request).getCzybh(),null,null);
				
				ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/updateIsPushCust")
	public void updateIsPushCust(HttpServletRequest request, HttpServletResponse response, PrjProg prjProg){
		logger.debug("添加PrjProg开始");
		try{
				prjProgService.updateIsPushCust(prjProg.getPk(),prjProg.getIsPushCust());
				ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/updateIsPushCustAll")
	public void updateIsPushCustAll(HttpServletRequest request, HttpServletResponse response,PrjProgPhoto prjProgPhoto){
		logger.debug("添加PrjProg开始");
		try{
				prjProgService.updateIsPushCustAll(prjProgPhoto);
				ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doUpdateConfirm")
	public void doUpdateConfirm(HttpServletRequest request, HttpServletResponse response, PrjProgConfirm ppConfirm){
		logger.debug("添加PrjProg开始");
		try{
			PrjProgConfirm prjProgConfirm=new PrjProgConfirm();
			if("0".equals(ppConfirm.getIsPass())){
				prjProgConfirm=prjProgConfirmService.get(PrjProgConfirm.class, ppConfirm.getNo());
				prjProgConfirm.setPrjLevel(null);
				prjProgConfirm.setIsPass("0");
				prjProgConfirm.setRemarks(ppConfirm.getRemarks());
				this.prjProgConfirmService.update(prjProgConfirm);
				this.prjProgService.doUpdateConfirm(ppConfirm.getCustCode(),ppConfirm.getPrjItem(),this.getUserContext(request).getCzybh(),ppConfirm.getRemarks(),ppConfirm.getPrjLevel(),ppConfirm.getIsPass());//删除原有数据

			}
			if("1".equals(ppConfirm.getIsPass())){
				prjProgConfirm=prjProgConfirmService.get(PrjProgConfirm.class, ppConfirm.getNo());
				prjProgConfirm.setPrjLevel(ppConfirm.getPrjLevel());
				prjProgConfirm.setIsPass("1");
				prjProgConfirm.setRemarks(ppConfirm.getRemarks());
				this.prjProgConfirmService.update(prjProgConfirm);
				this.prjProgService.doUpdateConfirm(ppConfirm.getCustCode(),ppConfirm.getPrjItem(),this.getUserContext(request).getCzybh(),ppConfirm.getRemarks(),ppConfirm.getPrjLevel(),ppConfirm.getIsPass());//删除原有数据

			}
			
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 修改PrjProg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjProg prjProg){
		logger.debug("修改PrjProg开始");
		
		try{
			String detailJson = request.getParameter("detailJson");
			Employee employee =null;
			prjProg.setDetailJson(detailJson);
			prjProg.setCustCode(prjProg.getCode());
			prjProg.setLastUpdate(new Date());
			prjProg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProg.setConfirmCZY(this.getUserContext(request).getCzybh());
			prjProg.setConfirmDate(new Date());
			employee=employeeService.get(Employee.class,prjProg.getConfirmCZY());
			prjProg.setConfirmDesc(employee.getNameChi());
			
		if(StringUtils.isBlank(detailJson)){
			ServletUtils.outPrintFail(request, response, "无工程进度,无法保存");
			return;
		}
		Result result = this.prjProgService.doPrjProgUpdate(prjProg);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "部分到货修改失败");
		}
	}
	
	@RequestMapping("/doUpdateCustStatus")
	public void doUpdateCustStatus(HttpServletRequest request, HttpServletResponse response, PrjProg prjProg){
		logger.debug("修改PrjProg开始");
		try{
			String detailJson = request.getParameter("detailJson");
			prjProg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProg.setDetailJson(detailJson);
		if(StringUtils.isBlank(detailJson)){
			ServletUtils.outPrintFail(request, response, "无选择数据,无法修改状态");
			return;
		}
		Result result = this.prjProgService.doUpdateCustStatus(prjProg);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "部分到货修改失败");
		}
	}

	/**
	 * 延误备注
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping("/doPrgRemark")
	public void doPrgRemark(HttpServletRequest request, HttpServletResponse response, Customer customer){
		logger.debug("修改PrjProg开始");
		try{
			Customer ct = customerService.get(Customer.class, customer.getCode());
			if(ct!=null){
				ct.setLastUpdate(new Date());
				ct.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				if(!ct.getConstructStatus().equals(customer.getConstructStatus())){
					ct.setConStaDate(new Date());
				}
				if(!customer.getPrgRemark().equals(ct.getPrgRemark())){
					ct.setPrgRmkDate(new Date());
				}
				ct.setConstructStatus(customer.getConstructStatus());
				ct.setPrgRemark(customer.getPrgRemark());
				ct.setBuildSta(customer.getBuildSta());
				ct.setRelCust(customer.getRelCust());
				
				this.customerService.update(ct);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	/**
	 * 工地巡检——验收确认——通过
	 * 
	 */
	@RequestMapping("/doCheck")
	public void doCheck( HttpServletRequest request,HttpServletResponse response,PrjProg prjProg){
		logger.debug("验收确认通过，开始");
		try {
			PrjProg pp= prjProgService.get(PrjProg.class, prjProg.getPk());
			pp.setActionLog("Edit");
			pp.setLastUpdate(new Date());
			pp.setConfirmDate(prjProg.getConfirmDate());
			pp.setConfirmDesc("");
			pp.setConfirmCZY(this.getUserContext(request).getCzybh());
			pp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.prjProgService.update(pp);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	 * 工地巡检——验收确认——重拍
	 * 
	 */
	@RequestMapping("/doReturn")
	public void doReturn( HttpServletRequest request,HttpServletResponse response,PrjProg prjProg){
		logger.debug("验收确认， 重拍");
		PrjProgConfirm prjProgConfirm= new PrjProgConfirm();
		
		try {
			PrjProg pp= prjProgService.get(PrjProg.class, prjProg.getPk());
			pp.setActionLog("Edit");
			pp.setLastUpdate(new Date());
			pp.setConfirmDesc(prjProg.getConfirmDesc());
			pp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			pp.setEndDate(null);
			this.prjProgService.update(pp);
			
			String str = prjProgConfirmService.getSeqNo("tPrjProgConfirm");
			prjProgConfirm.setNo(str);
			prjProgConfirm.setCustCode(prjProg.getCustCode());
			prjProgConfirm.setDate(new Date());
			prjProgConfirm.setLastUpdate(new Date());
			prjProgConfirm.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			prjProgConfirm.setPrjItem(prjProg.getPrjItem());
			prjProgConfirm.setIsPass("0");
			prjProgConfirm.setExpired("F");
			prjProgConfirm.setActionLog("Add");
			prjProgConfirm.setRemarks(prjProg.getConfirmDesc());
			this.prjProgConfirmService.saveOrUpdate(prjProgConfirm);
			
			PersonMessage personMessage =new PersonMessage();
			personMessage.setMsgType("10");
			personMessage.setMsgRelNo(str);
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvCzy(prjProg.getProjectMan());
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setMsgText(prjProg.getAddress()+":"+prjProg.getPrjDescr()+"节点验收不通过,请进入进度页面查看原因，并整改。");
			personMessage.setRcvType("1");
			
			personMessage.setMsgRelCustCode(prjProg.getCustCode());
			this.personMessageService.save(personMessage);
			
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	 * 工地巡检——验收确认——通过
	 * 
	 */
	@RequestMapping("/doConfirmCheck")
	public void doConfrimCheck( HttpServletRequest request,HttpServletResponse response,PrjProgCheck prjProgCheck){
		logger.debug("验收确认通过，开始");
		try {
			PrjProgCheck ppc=null;
			ppc=prjProgCheckService.get(PrjProgCheck.class, prjProgCheck.getNo());
			ppc.setConfirmCzy(this.getUserContext(request).getCzybh());
			ppc.setConfirmDate(new Date());
			ppc.setLastUpdate(new Date());
			ppc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.prjProgService.update(ppc);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	/**
	 * 工地巡检——验收确认——通过
	 * 
	 */
	@RequestMapping("/doConfirmReturn")
	public void doConfrimReturn( HttpServletRequest request,HttpServletResponse response,PrjProgCheck prjProgCheck){
		logger.debug("验收确认， 重拍");
		try {
			PrjProgCheck ppc=null;
			ppc=prjProgCheckService.get(PrjProgCheck.class, prjProgCheck.getNo());
			ppc.setModifyComplete("0");
			ppc.setLastUpdate(new Date());
			ppc.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			this.prjProgService.update(ppc);

			PersonMessage personMessage =new PersonMessage();
			personMessage.setMsgType("7");
			personMessage.setMsgRelNo(prjProgCheck.getNo());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvCzy(prjProgCheck.getProjectMan());
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setMsgText(prjProgCheck.getAddress()+":"+prjProgCheck.getPrjDescr()+"你的整改单需要整改，请立即处理");
			personMessage.setMsgRelCustCode(ppc.getCustCode());
			personMessage.setRcvType("1");
			this.personMessageService.save(personMessage);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "操作失败");
		}
	}
	
	
	/**
	 * 删除PrjProg
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrjProg开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrjProg编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjProg prjProg = prjProgService.get(PrjProg.class, Integer.parseInt(deleteId));
				if(prjProg == null)
					continue;
				prjProg.setExpired("T");
				prjProgService.update(prjProg);
			}
		}
		logger.debug("删除PrjProg IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	/**
	 * 工程巡检图片上传(app)
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/uploadPrjProgCheck")
	public void uploadPrjProgCheck(HttpServletRequest request,
			HttpServletResponse response) {

		//StringBuilder msg = new StringBuilder();
		//JSONObject json = new JSONObject();
		//UploadPhotoEvt evt = new UploadPhotoEvt();
		UploadPhotoResp respon = new UploadPhotoResp();

		try {
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String no = multipartFormData.getParameter("no");
			String custCode = multipartFormData.getParameter("custCode");
			String prjItem = multipartFormData.getParameter("prjItem");
			String lastUpdatedBy = multipartFormData.getParameter("lastUpdatedBy");
			String photoPath = "";
			String fileNameNew = "";
			String firstFileName = "";
			// 获取多个上传文件
			List<String> fileNameList = new ArrayList<String>();
			List<String> fileRealPathList = new ArrayList<String>();
			// 遍历上传文件写入磁盘
			List<FileItem> attachments = multipartFormData.getFileItems();
            for (FileItem attatchment : attachments) {
            	firstFileName = attatchment.getName().substring(
            			attatchment.getName().lastIndexOf("\\") + 1);
				String formatName = firstFileName
						.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
				fileNameNew = System.currentTimeMillis()+getUserContext(request).getCzybh().trim()+formatName;
                PrjProgNewUploadRule rule =
                        new PrjProgNewUploadRule(fileNameNew, fileNameNew.substring(0,5));
                Result uploadResult = FileUploadUtils.upload(attatchment.getInputStream(), 
                		rule.getFileName(),rule.getFilePath());
                fileNameList.add(fileNameNew);
                fileRealPathList.add(FileUploadUtils.getFileUrl(rule.getFullName()));
                if (!uploadResult.isSuccess()) {
                    ServletUtils.outPrintFail(request, response, "图片上传失败：内部转存错误");
                    return;
                }
            }
			if (StringUtils.isNotBlank(prjItem) && StringUtils.isNotBlank(custCode)){
				if (fileNameList!=null && fileNameList.size()>0){
					for (String str : fileNameList){
						PrjProgPhoto photo = new PrjProgPhoto();
						photo.setCustCode(custCode);
						photo.setPrjItem(prjItem);
						photo.setPhotoName(str);
						photo.setLastUpdate(new Date());
						if (StringUtils.isNotBlank(lastUpdatedBy)){//delphi调用接口用
							photo.setLastUpdatedBy(lastUpdatedBy);
						}else{
							photo.setLastUpdatedBy(getUserContext(request).getCzybh());
						}
						photo.setActionLog("Add");
						photo.setExpired("F");
						photo.setType("3");//工地巡检
						photo.setRefNo(no);
						photo.setIsPushCust("1");
						photo.setIsSendYun("1");
						photo.setSendDate(new Date());
						prjProgPhotoService.save(photo);
					}
				}
			}
			respon.setPhotoPathList(fileRealPathList);
			respon.setPhotoNameList(fileNameList);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
	}
	
	/**
	 * 新增模板时 判断是否已存在验收时间或者结束时间
	 * 
	 * */
	@RequestMapping("/isConfirm")
	@ResponseBody
	public Integer isConfirm(String custCode){
		Map<String, Object> map= this.prjProgService.isConfirm(custCode);
		int pk=0;
		if(map==null){
			return pk;
		}else{
			 pk = (Integer) map.get("pk");
			return pk;
		}		
	}
	
	
	/**
	 *ajax顺延工程进度
	 */
	@RequestMapping("/doPostPone")
	@ResponseBody
	public PrjProg doPostPone(HttpServletRequest request,HttpServletResponse response,
			PrjProg prjProg){
		logger.debug("ajax获取数据");  
		
		prjProg.setPostPoneEndDate(prjProg.getPostPoneDate());

		this.prjProgService.doPostPone(prjProg.getPostPoneDate(),prjProg.getPostPoneEndDate(),prjProg.getCustCode(),prjProg.getPlanBegin());
	
		return prjProg;
	}
	
	/**
	 *ajax顺延工程进度
	 */
	@RequestMapping("/doReturnCheck")
	@ResponseBody
	public PrjProg doReturnCheck(HttpServletRequest request,HttpServletResponse response,
			PrjProg prjProg){
		logger.debug("ajax获取数据");  
		
		this.prjProgService.doReturnCheck(prjProg.getPk(),this.getUserContext(request).getCzybh());
	
		return prjProg;
	}
	
	@RequestMapping("/getPrjProgPK")
	@ResponseBody
	public boolean getPrjProgPK(HttpServletRequest request,HttpServletResponse response,
			String code){
		logger.debug("ajax获取数据");  
		
		return 	this.prjProgService.getPrjProgPK(code);
	
	}
	
	
	
	/**
	 *PrjProg导出工地验收Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		UserContext uc = getUserContext(request);
		page.setPageSize(-1);
		customerService.findPrjProgPageBySql(page, customer, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工程进度_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到计划进度编排
	 * @return
	 */
	@RequestMapping("/goProgArrange")
	public ModelAndView goProgArrange(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到工程进度编辑");
		PrjProg prjProg=new PrjProg();
		Customer customer=null;
		PrjProgTemp prjProgTemp= null;
		Employee employee =null;
		customer = customerService.get(Customer.class, id);
		customer.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		employee=employeeService.get(Employee.class, customer.getDesignMan());
		if(StringUtils.isNotBlank(customer.getDesignMan())){
			customer.setDesignManDescr(employee.getNameChi());
		}
		
		if(StringUtils.isNotBlank(customer.getPrjProgTempNo())){
			prjProgTemp=prjProgTempService.get(PrjProgTemp.class,customer.getPrjProgTempNo());
			customer.setPrjProgTempNoDescr(prjProgTemp==null?"":prjProgTemp.getDescr());
		}
		return new ModelAndView("admin/project/prjProg/prjProg_progArrange").addObject("customer", customer).addObject("prjProg",prjProg);
	}
	/**
	 * 跳转到从模板导入
	 * @return
	 */
	@RequestMapping("/goImportTemp")
	public ModelAndView goImportTemp(HttpServletRequest request, HttpServletResponse response, 
			String tempNo,String prjItems,String custCode){
		logger.debug("跳转到工程进度编辑");
		return new ModelAndView("admin/project/prjProg/prjProg_importTemp")
			.addObject("tempNo", tempNo)
			.addObject("prjItems",prjItems)
			.addObject("custCode", custCode);
	}
	/**
	 * 跳转工程进度——编辑——新增
	 * @return 
	 * 
	 **/
	@RequestMapping("/goArrangeAdd")
	public ModelAndView goArrangeAdd(HttpServletRequest request, HttpServletResponse response,String prjItems){
		logger.debug("跳转到工程进度——增加");
		return new ModelAndView("admin/project/prjProg/prjProg_progArrange_add").addObject("prjItems",prjItems);
	}
	/**
	 * 计划进度编排保存
	 * @author cjg
	 * @date 2019-9-13
	 * @param prjProg
	 * @return
	 */
	@RequestMapping("/doProgArrange")
	public void doSave(HttpServletRequest request,HttpServletResponse response,PrjProg prjProg){
		logger.debug("保存");		
		System.out.println(prjProg.getConfirmBegin());
		try {	
			Result result = this.prjProgService.doProgArrange(prjProg);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 跳转到异常工地管理 add by cjm 2019-11-28
	 * @return
	 */
	@RequestMapping("/goAbnormalSites")
	public ModelAndView goAbnormalSites(HttpServletRequest request, HttpServletResponse response, 
			String department2,String prjMan){
		logger.debug("跳转到异常工地管理");
		String projectMan="";
		String projectManDescr="";
		if(StringUtils.isNotBlank(prjMan)){
			projectMan = prjMan.split("\\|")[0];
			projectManDescr = prjMan.split("\\|")[1];
		}
		return new ModelAndView("admin/project/prjProg/prjProg_abnormalSites")
		        .addObject("department2",department2)
		        .addObject("projectMan",projectMan)
		        .addObject("projectManDescr",projectManDescr);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLongTimeStopJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goLongTimeStopJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findLongTimeStopPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 长期停工导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel_longTimeStop")
	public void doLongTimeStopExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjProgService.findLongTimeStopPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"长期停工_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWaitFirstCheckJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWaitFirstCheckJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findWaitFirstCheckPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 长期停工导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel_waitFirstCheck")
	public void doWaitFirstCheckExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjProgService.findWaitFirstCheckPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"待初检工地_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goWaitCustWorkAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goWaitCustWorkAppJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findWaitCustWorkAppPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 长期停工导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel_waitCustWorkApp")
	public void doWaitCustWorkAppExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjProgService.findWaitCustWorkAppPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"待申请工人工地_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goTimeOutEndJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goTimeOutEndJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjProgService.findTimeOutEndPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 长期停工导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel_timeOutEnd")
	public void doTimeOutEndExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjProgService.findTimeOutEndPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"未按时完工_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
