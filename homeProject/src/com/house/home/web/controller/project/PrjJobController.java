package com.house.home.web.controller.project;

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
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.fileUpload.FileUploadRule;
import com.house.framework.commons.fileUpload.impl.PrjProgNewUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjJob;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjJobService;

@Controller
@RequestMapping("/admin/prjJob")
public class PrjJobController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjJobController.class);

	@Autowired
	private PrjJobService prjJobService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustTypeService custTypeService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjJob prjJob) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobService.findPageBySql(page, prjJob);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrjJob列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
//  add by hc  集成测量分析   2017/11/21   begin 
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrjJob prjJob=new PrjJob();
		prjJob.setAppDateform(DateUtil.startOfTheMonth(new Date()));
		prjJob.setAppDateto(new Date());
		Customer customer = new Customer();
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
		
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/query/prjJob/prjJob_list").addObject("prjJob", prjJob).
				addObject("czybh", this.getUserContext(request).getCzybh()).addObject("customer",customer);
	}
	//return new ModelAndView("admin/project/prjJob/prjJob_list")   mark by hc 
	/**
	 * 集成测量分析    统计方式按明细	//
	 * @throws Exception
	 */
	@RequestMapping("/prjJobApply")
	@ResponseBody
	public WebPage<Map<String,Object>> prjJobApply(HttpServletRequest request,
			HttpServletResponse response,PrjJob prjJob) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (prjJob.getAppDateform()==null){
			prjJob.setAppDateform(DateUtil.startOfTheMonth(new Date()));
			prjJob.setAppDateto(new Date());	
		}
		prjJob.setAppDateform(new java.sql.Timestamp(prjJob.getAppDateform().getTime()));
		prjJob.setAppDateto(new java.sql.Timestamp(prjJob.getAppDateto().getTime()));
		prjJob.setsType(prjJob.getStatistcsMethod());//设置类型
		prjJobService.findPageBySqlTJFS(page, prjJob);//TJFS 统计方式
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	/*
	 *PrjJob 明细查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看巡检分析页面");
		PrjJob prjJob = null;
		Customer customer=null;
		if (StringUtils.isNotBlank(id)){
			prjJob = prjJobService.get(PrjJob.class, id);
		}else{
			prjJob = new PrjJob();
		}
		if (prjJob.getCustCode()!=null){
			customer = customerService.get(Customer.class, prjJob.getCustCode());
		}else{
			customer = new Customer();
		}
		if (prjJob.getAppCzy()!=null) {
			Employee employee= null;
			employee = employeeService.get(Employee.class, prjJob.getAppCzy());
			prjJob.setAppCzyDescr(employee==null?"":employee.getNameChi());
		}
		if(prjJob.getDealCzy()!=null){
			Employee employee=null;
			employee=employeeService.get(Employee.class,prjJob.getDealCzy());
			prjJob.setDealCzyDescr(employee==null?"":employee.getNameChi());
		}
		//通过橱柜和衣柜的编号 取到 橱柜和衣柜的名称
		Map<String, Object>  list= prjJobService.getCupName(prjJob.getCupBrand());			
		if ((list!=null)&&(list.size()>0)) {
			prjJob.setCupBrandDescr(list.get("Descr").toString());	
		}
		Map<String, Object>  list1= prjJobService.getWarName(prjJob.getWarBrand());			
		if ((list1!=null)&&(list1.size()>0)) {
			prjJob.setWarBrandDescr(list1.get("Descr").toString());	
		}
		return new ModelAndView("admin/query/prjJob/prjJobCheck_View")
			.addObject("prjJob", prjJob).addObject("customer",customer);
	}
	
	/**
	 * 查询集成测量图片列表
	 * @param request
	 * @param response
	 * @param itemPreApp
	 * @return
	 */
	@RequestMapping("/gojcclPictureListJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> gojcclPictureListJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrjJob prjJob){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobService.getJcclPhotoListByNo(page, prjJob);
		return new WebPage<Map<String,Object>>(page);		
	}
	
	/**
	 * 获取图片路径
	 * @param request
	 * @param response
	 * @param photoName
	 * @return
	 */
	@RequestMapping("/getprjJobPhoto")
	@ResponseBody
	public PrjJob getprjJobPhoto(HttpServletRequest request, HttpServletResponse response,String photoName){
	    
		PrjJob prjJob = new PrjJob();
		
		FileUploadRule progNewUploadRule = PrjProgNewUploadRule.fromUploadedFile(photoName);
		prjJob.setPhotoPath(FileUploadUtils.getFileUrl(progNewUploadRule.getFullName()));
		
		return prjJob;
	}
	
	/**
	 * 打包下载集成测量图片
	 * @param request
	 * @param response
	 * @param no
	 */
	@SuppressWarnings("null")
	@RequestMapping("/downLoad")
	public void downLoad(HttpServletRequest request,HttpServletResponse response,String no){
		try{
			String urlString = FileUploadUtils.DOWNLOAD_URL+PrjProgNewUploadRule.FIRST_LEVEL_PATH;
			String zip = SystemConfig.getProperty("prjProgNew", "", "photo")+no+"_"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss")+".zip";
			List<Map<String,Object>> list = prjJobService.getPhotoList(no);
			StringBuilder[] files = null;
			if(list != null && list.size() > 0){
				files = new StringBuilder[list.size()];
				for(int i=0;i<list.size();i++){
					files[i] = new StringBuilder();
					files[i].append(urlString+list.get(i).get("photoName").toString().substring(0,5)+"/"+list.get(i).get("photoName"));
				}				
			}				        
			ServletUtils.downLoadFiles(request,response,files,zip,true);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doExceljccl")
	public void doExcelcheckgdxj(HttpServletRequest request, 
			HttpServletResponse response, PrjJob prjJob){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjJob.setsType(prjJob.getStatistcsMethod());
		prjJobService.findPageBySqlTJFS(page, prjJob);
		getExcelList(request);
		String excelname=null;
		if ("1".equals(prjJob.getStatistcsMethod())){
			excelname ="集成测量分析_申报明细";
		}else if ("2".equals(prjJob.getStatistcsMethod())){
			excelname ="集成测量分析_未申报明细";
		}
		else if ("3".equals(prjJob.getStatistcsMethod())){
			excelname ="集成测量分析_按项目经理";		
				}
		else if ("4".equals(prjJob.getStatistcsMethod())){
			excelname ="集成测量分析_按工程部";
		}
		else if ("5".equals(prjJob.getStatistcsMethod())){
			excelname ="集成测量分析_按集成设计师";
		}
		else if ("6".equals(prjJob.getStatistcsMethod())){
			excelname ="集成测量分析_按集成部";
		}
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				excelname+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	// add by hc 2017/11/24  end 
	
	/**
	 * PrjJob查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/prjJob/prjJob_code");
	}
	/**
	 * 跳转到新增PrjJob页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrjJob页面");
		PrjJob prjJob = null;
		if (StringUtils.isNotBlank(id)){
			prjJob = prjJobService.get(PrjJob.class, id);
			prjJob.setNo(null);
		}else{
			prjJob = new PrjJob();
		}
		
		return new ModelAndView("admin/project/prjJob/prjJob_save")
			.addObject("prjJob", prjJob);
	}
	/**
	 * 跳转到修改PrjJob页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrjJob页面");
		PrjJob prjJob = null;
		if (StringUtils.isNotBlank(id)){
			prjJob = prjJobService.get(PrjJob.class, id);
		}else{
			prjJob = new PrjJob();
		}
		
		return new ModelAndView("admin/project/prjJob/prjJob_update")
			.addObject("prjJob", prjJob);
	}
	
	/**
	 * 跳转到查看PrjJob页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrjJob页面");
		PrjJob prjJob = prjJobService.get(PrjJob.class, id);
		
		return new ModelAndView("admin/project/prjJob/prjJob_detail")
				.addObject("prjJob", prjJob);
	}
	/**
	 * 添加PrjJob
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrjJob prjJob){
		logger.debug("添加PrjJob开始");
		try{
			String str = prjJobService.getSeqNo("tPrjJob");
			prjJob.setNo(str);
			prjJob.setLastUpdate(new Date());
			prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
			prjJob.setExpired("F");
			this.prjJobService.save(prjJob);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrjJob失败");
		}
	}
	
	/**
	 * 修改PrjJob
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrjJob prjJob){
		logger.debug("修改PrjJob开始");
		try{
			prjJob.setLastUpdate(new Date());
			prjJob.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prjJobService.update(prjJob);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrjJob失败");
		}
	}
	
	/**
	 * 删除PrjJob
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrjJob开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrjJob编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrjJob prjJob = prjJobService.get(PrjJob.class, deleteId);
				if(prjJob == null)
					continue;
				prjJob.setExpired("T");
				prjJobService.update(prjJob);
			}
		}
		logger.debug("删除PrjJob IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *PrjJob导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, PrjJob prjJob){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjJobService.findPageBySql(page, prjJob);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridDetail(HttpServletRequest request,
			HttpServletResponse response, PrjJob prjJob) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjJobService.findAllBySql(page, prjJob);
		return new WebPage<Map<String,Object>>(page);
	}
}
