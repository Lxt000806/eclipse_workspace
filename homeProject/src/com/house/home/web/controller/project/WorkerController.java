package com.house.home.web.controller.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.CustDocUploadRule;
import com.house.framework.commons.fileUpload.impl.WorkerAvatarPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.home.client.service.evt.WokerCostApplyEvt;
import com.house.home.entity.basic.Builder;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Region;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.Worker;
import com.house.home.service.basic.BuilderService;
import com.house.home.service.basic.Department1Service;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.SpcBuilderService;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.WorkType12Service;
import com.house.home.service.project.WorkerService;

@Controller
@RequestMapping("/admin/worker")
public class WorkerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

	@Autowired
	private WorkerService workerService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired 
	private BuilderService builderService;
	@Autowired 
	private SpcBuilderService spcBuilderService;
	@Autowired
	private Department1Service department1Service;
	@Autowired
	private WorkType12Service workType12Service;
	
	@Autowired
	private Department2Service department2Service;
	
	/**
	 * 工种、分组联动下拉栏
	 * zb
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workType12/{type}/{pCode}")
	@ResponseBody
	public JSONObject getItemType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> workType12List = this.workType12Service.findWorkType12Dept(type, pCode,this.getUserContext(request));
		return this.out(workType12List, true);
	}
	
	/**
	 * 一级、二级区域联动下拉栏
	 * @param type
	 * @param pCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/region/{type}/{pCode}") //获取类型1,2,3
	@ResponseBody
	public JSONObject getRegion(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.workerService.findRegion(type, pCode);
		return this.out(regionList, true);
	}
	
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
			HttpServletResponse response, Worker worker) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findPageBySql(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/goMemberJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goMemberJqGrid(HttpServletRequest request,
	        HttpServletResponse response, Worker worker) {
	    
	    Page<Map<String,Object>> page = newPageForJqGrid(request);
	    workerService.findMemberPageBySql(page, worker);
	    
	    return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * worker_list的goJqGrid
	 * zb
	 * @param request
	 * @param response
	 * @param worker
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridList")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridList(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findPageBySqlList(page, worker, this.getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	
	
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCodeJqGrid(HttpServletRequest request,
			HttpServletResponse response, Worker worker) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findCodePageBySql(page, worker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goOnDoDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getOnDoDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, String workerCode,String department2) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findOnDoDetailPageBySql(page, workerCode,department2);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWorkerWorkType12JqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goWorkerWorkType12JqGrid(HttpServletRequest request,
			HttpServletResponse response, String workerCode) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findWorkerWorkType12PageBySql(page, workerCode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goWorkType12JqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> goWorkType12JqGrid(HttpServletRequest request,
			HttpServletResponse response, String workType12Strings) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workerService.findWorkType12PageBySql(page, workType12Strings);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doOnDoDetailExcel")
	public void doOnDoDetailExcel(HttpServletRequest request ,HttpServletResponse response,
			String workerCode,String department2){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		workerService.findOnDoDetailPageBySql(page,workerCode,department2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"班组在建情况表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * worker列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/worker/worker_list");
	}
	
	@RequestMapping("/goViewOnDoDetail")
	public ModelAndView goViewOnDoDetail(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
			CustWorker custWorker=new CustWorker();
			custWorker.setWorkerCode(code);
		return new ModelAndView("admin/project/worker/worker_viewOndoDetail").addObject("custWorker",custWorker);
	}
	
	@RequestMapping("/goUpdateWorker")
	public ModelAndView goUpdateWorker(HttpServletRequest request,
			HttpServletResponse response,String code) throws Exception {
			Worker worker =new Worker();
			if(StringUtils.isNotBlank(code)){
				worker=workerService.get(Worker.class, code);
				worker.setPrjRegionCode(worker.getPrjRegionCode()==null?null:worker.getPrjRegionCode().trim());
			}
		return new ModelAndView("admin/project/worker/worker_updateWorker").addObject("worker",worker);
	}

	/**
	 * worker查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Worker worker) throws Exception {

		return new ModelAndView("admin/project/worker/worker_code").addObject("worker",worker);
	}
	
	@RequestMapping("/goNewCode")
	public ModelAndView goNewCode(HttpServletRequest request,
			HttpServletResponse response,Worker worker,String custCode ,String normDay) throws Exception {
		Customer customer =new Customer();
		Employee employee =new Employee();
		CustType custType = new CustType();
		Builder builder=new Builder();
		SpcBuilder spcBuilder=new SpcBuilder();
		Department1 department1 =new Department1();
		Worker befWorkType12Worker=new Worker();
		Region region =new Region();
		String prjRegionDescr="";
		if(StringUtils.isNotBlank(custCode)){
			customer=customerService.get(Customer.class, custCode);
			if(StringUtils.isNotBlank(customer.getProjectMan())){
				employee=employeeService.get(Employee.class, customer.getProjectMan());
			}
			if(StringUtils.isNotBlank(employee.getDepartment1())){
				department1=department1Service.get(Department1.class, employee.getDepartment1());
				//employee.setDepartment1(department1.getCode()+"|"+department1.getDesc1());
			}
			
			String workerCode = workerService.getBefWorkType12Emp(custCode, worker.getWorkType12Query());
			if (StringUtils.isNotBlank(workerCode)) {
				befWorkType12Worker = workerService.get(Worker.class, workerCode);
			}
			
			if("1".equals(employee.getIsSupvr())){
				employee.setIsSupvrDescr("是");
			}else{
				employee.setIsSupvrDescr("否");
			}
			if(StringUtils.isNotBlank(employee.getPrjLevel())){
				if("1".equals(employee.getPrjLevel())){
					employee.setPrjLevelDescr("1星");
				}else if("2".equals(employee.getPrjLevel())){
					employee.setPrjLevelDescr("2星");
				}else if("3".equals(employee.getPrjLevel())){
					employee.setPrjLevelDescr("3星");
				} else{
					employee.setPrjLevelDescr("");
				}
			}
			worker.setPrjRegionCode(worker.getPrjRegionCode()==null?null:worker.getPrjRegionCode().trim());
			customer.setProjectManDescr(employee.getNumber()+"|"+employee.getNameChi());
			//customer.setProjectManDescr(employee.getNameChi()==null?"":employee.getNameChi());
			customer.setNormConstructDay(normDay);
			if(StringUtils.isNotBlank(customer.getBuilderCode())){
				builder=builderService.get(Builder.class,customer.getBuilderCode());
				if(StringUtils.isNotBlank(builder.getRegionCode())){
					region=builderService.get(Region.class, builder.getRegionCode());
					builder.setRegionCode(region.getCode()+"|"+region.getDescr());
				}
				if(StringUtils.isNotBlank(builder.getSpcBuilder())){
					spcBuilder=spcBuilderService.get(SpcBuilder.class, builder.getSpcBuilder());
					if(spcBuilder!=null){
						customer.setSpcBuilder(spcBuilder.getCode()+"|"+spcBuilder.getDescr());
					}
				}
			}
			if(StringUtils.isNotBlank(customer.getCustType())){
				custType = customerService.get(CustType.class, customer.getCustType());
			}
			Map<String, Object> map=customerService.getPrjRegion(custCode);
			if(map != null && map.get("prjRegionDescr") != null){
				prjRegionDescr=map.get("prjRegionDescr").toString();
			}
		}
		return new ModelAndView("admin/project/worker/worker_newCode").addObject("worker",worker)
				.addObject("customer",customer)
				.addObject("employee",employee)
				.addObject("builder",builder)
				.addObject("spcBuilder",spcBuilder)
				.addObject("czybm",	this.getUserContext(request).getCzybh())
				.addObject("befWorkType12Worker", befWorkType12Worker)
				.addObject("custType", custType)
				.addObject("prjRegionDescr", prjRegionDescr);
	}
	
	/**
	 * 根据id查询详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getWorker")
	@ResponseBody
	public JSONObject getWorker(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Worker worker = workerService.get(Worker.class, id);
		if(worker == null){
			return this.out("系统中不存在code="+id+"的材料信息", false);
		}
		return this.out(worker, true);
	}
	
	/**
	 * 跳转到worker新增页面
	 * zb
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
		Worker worker = new Worker();
		return new ModelAndView("admin/basic/worker/worker_save")
			.addObject("worker", worker);
	}
	
	/**
	 * 跳转到修改worker页面
	 * zb
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改worker页面");
		Worker worker = null;
		Employee employee=null;
		
		if (StringUtils.isNotBlank(id)){
			worker = workerService.get(Worker.class, id);
		}else{
			worker = new Worker();
		}
		if (StringUtils.isNotBlank(worker.getIntroduceEmp())) {
			worker.setIntroduceEmp(worker.getIntroduceEmp().trim());
			employee=employeeService.get(Employee.class, worker.getIntroduceEmp());
			if (employee!=null) {
				worker.setIntroduceEmpDescr(employee.getNameChi());
			}else {
				worker.setIntroduceEmpDescr("未找到");
			}
		}
		if (StringUtils.isNotBlank(worker.getEmpCode())) {
			worker.setEmpCode(worker.getEmpCode().trim());
			employee=employeeService.get(Employee.class, worker.getEmpCode());
			worker.setEmpCodeDescr(employee.getNameChi());
			worker.setIsSupvr(employee.getIsSupvr());
			worker.setPrjLevel(employee.getPrjLevel());
		}
		if (StringUtils.isNotBlank(worker.getPhone())) {
			worker.setPhone(worker.getPhone().replaceAll("(\\d{3})(\\d{4})([\\d{2}\\*{2}|\\d{4}])", "$1 $2 $3"));/*手机正则*/
		}
		if (StringUtils.isNotBlank(worker.getIdnum())) {
			worker.setIdnum(worker.getIdnum().replaceAll("(\\d{6})(\\d{8})([\\d{4}|\\d{3}x|\\d{3}X])", "$1 $2 $3"));
		}
		if(worker.getEfficiency()!=null){
			worker.setEfficiency(worker.getEfficiency().stripTrailingZeros());/*去零*/
		}
		if(StringUtils.isNotBlank(worker.getWorkType12())){
			worker.setWorkType12(worker.getWorkType12().trim());
		}
		if (StringUtils.isNotBlank(worker.getWorkType12Dept())) {
			worker.setWorkType12Dept(worker.getWorkType12Dept().trim());
		}
		
		if (StringUtils.isNotBlank(worker.getDepartment2())) {
			worker.setDepartment2Descr(department2Service.getByCode(worker.getDepartment2()).getDesc2());
		}
		
		return new ModelAndView("admin/basic/worker/worker_update")
			.addObject("worker", worker);
	}
	
	/**
	 * 跳转到修改worker属性页面
	 * zb
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/goUpdateAttribute")
	public ModelAndView goUpdateAttribute(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改worker属性页面");
		Worker worker = null;
		Employee employee=null;
		
		if (StringUtils.isNotBlank(id)){
			worker = workerService.get(Worker.class, id);
		}else{
			worker = new Worker();
		}
		if (StringUtils.isNotBlank(worker.getIntroduceEmp())) {
			worker.setIntroduceEmp(worker.getIntroduceEmp().trim());
			employee=employeeService.get(Employee.class, worker.getIntroduceEmp());
			if (employee!=null) {
				worker.setIntroduceEmpDescr(employee.getNameChi());
			}else {
				worker.setIntroduceEmpDescr("未找到");
			}
		}
		if (StringUtils.isNotBlank(worker.getEmpCode())) {
			worker.setEmpCode(worker.getEmpCode().trim());
			employee=employeeService.get(Employee.class, worker.getEmpCode());
			worker.setEmpCodeDescr(employee.getNameChi());
			worker.setIsSupvr(employee.getIsSupvr());
			worker.setPrjLevel(employee.getPrjLevel());
		}
		if (StringUtils.isNotBlank(worker.getPhone())) {
			worker.setPhone(worker.getPhone().replaceAll("(\\d{3})(\\d{4})([\\d{2}\\*{2}|\\d{4}])", "$1 $2 $3"));/*手机正则*/
		}
		if (StringUtils.isNotBlank(worker.getIdnum())) {
			worker.setIdnum(worker.getIdnum().replaceAll("(\\d{6})(\\d{8})([\\d{4}|\\d{3}x|\\d{3}X])", "$1 $2 $3"));
		}
		if(worker.getEfficiency()!=null){
			worker.setEfficiency(worker.getEfficiency().stripTrailingZeros());/*去零*/
		}
		if(StringUtils.isNotBlank(worker.getWorkType12())){
			worker.setWorkType12(worker.getWorkType12().trim());
		}
		if (StringUtils.isNotBlank(worker.getWorkType12Dept())) {
			worker.setWorkType12Dept(worker.getWorkType12Dept().trim());
		}
		
		if (StringUtils.isNotBlank(worker.getDepartment2())) {
			worker.setDepartment2Descr(department2Service.getByCode(worker.getDepartment2()).getDesc2());
		}
		
		return new ModelAndView("admin/basic/worker/worker_update_attribute")
			.addObject("worker", worker);
	}
	
	@RequestMapping("/goWorkerWorkType12")
	public ModelAndView goWorkerWorkType12(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到设置承接工种页面");
		Worker worker = null;
		worker = workerService.get(Worker.class, id);
		WorkType12 workType12 = workerService.get(WorkType12.class, worker.getWorkType12());
		worker.setWorkType12Descr(workType12.getDescr());
		return new ModelAndView("admin/basic/worker/worker_workerWorkType12")
			.addObject("worker", worker);
	}
	
	@RequestMapping("/goUpdateLaborCmp")
	public ModelAndView goUpdateLaborCmp(HttpServletRequest request,
	        HttpServletResponse response, String workerCode) {
	    
	    Worker worker = workerService.get(Worker.class, workerCode);
	    worker = worker != null ? worker : new Worker();
	    
	    return new ModelAndView("admin/basic/worker/worker_updateLaborCmp")
	        .addObject("worker", worker);
	}
	
	@RequestMapping("/doUpdateLaborCmp")
	public void doUpdateLaborCmp(HttpServletRequest request,
	        HttpServletResponse response, Worker worker) {
	    
	    try {
    	    if (StringUtils.isBlank(worker.getCode())) {
                ServletUtils.outPrintFail(request, response, "更新劳务公司失败，班组编号为空");
                return;
            }
    	    
    	    Worker originalWorker = workerService.get(Worker.class, worker.getCode());
    	    if (originalWorker == null) {
                ServletUtils.outPrintFail(request, response, "更新劳务公司失败，不存在此班组");
                return;
            }
    	    
    	    originalWorker.setLaborCmpCode(worker.getLaborCmpCode());
    	    workerService.update(originalWorker);
    	    
    	    ServletUtils.outPrintSuccess(request, response, "更新劳务公司成功");
	    } catch (Exception e) {
	        ServletUtils.outPrintFail(request, response, "更新劳务公司失败，程序异常");
            throw new RuntimeException(e);
        }
	}
	
	@RequestMapping("/goWorkerWorkType12Add")
	public ModelAndView goWorkerWorkType12Add(HttpServletRequest request, HttpServletResponse response, 
			String workType12Strings){
		logger.debug("跳转到设置承接工种页面");
		Worker worker = new Worker();
		worker.setWorkType12Strings(workType12Strings);
		return new ModelAndView("admin/basic/worker/worker_workerWorkType12_add")
			.addObject("worker", worker);
	} 
	
	@RequestMapping("/goRegisterMall")
	public ModelAndView goRegisterMall(HttpServletRequest request, HttpServletResponse response, 
			String workerCode){
		logger.debug("跳转到注册自装通页面");
		Worker worker = workerService.get(Worker.class, workerCode);
		return new ModelAndView("admin/basic/worker/worker_registerMall")
			.addObject("worker", worker);
	} 
	
	/**
	 * 跳转到查看worker页面
	 * zb
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看worker页面");
		Worker worker = null;
		Employee employee=null;
		
		if (StringUtils.isNotBlank(id)){
			worker = workerService.get(Worker.class, id);
		}else{
			worker = new Worker();
		}
		try {
			if (StringUtils.isNotBlank(worker.getIntroduceEmp())) {
				worker.setIntroduceEmp(worker.getIntroduceEmp().trim());
				employee=employeeService.get(Employee.class, worker.getIntroduceEmp());
				worker.setIntroduceEmpDescr(employee.getNameChi());
			}
			if (StringUtils.isNotBlank(worker.getEmpCode())) {
				worker.setEmpCode(worker.getEmpCode().trim());
				employee=employeeService.get(Employee.class, worker.getEmpCode());
				worker.setEmpCodeDescr(employee.getNameChi());
				worker.setIsSupvr(employee.getIsSupvr());
				worker.setPrjLevel(employee.getPrjLevel());
			}
			if (StringUtils.isNotBlank(worker.getPhone())) {
				worker.setPhone(worker.getPhone().replaceAll("(\\d{3})(\\d{4})([\\d{2}\\*{2}|\\d{4}])", "$1 $2 $3"));/*手机正则*/
			}
			if (StringUtils.isNotBlank(worker.getIdnum())) {
				worker.setIdnum(worker.getIdnum().replaceAll("(\\d{6})(\\d{8})([\\d{4}|\\d{3}x|\\d{3}X])", "$1 $2 $3"));
			}
			if(worker.getEfficiency()!=null){
				worker.setEfficiency(worker.getEfficiency().stripTrailingZeros());/*去零*/
			}
			if(StringUtils.isNotBlank(worker.getWorkType12())){
				worker.setWorkType12(worker.getWorkType12().trim());
			}
			
			if (StringUtils.isNotBlank(worker.getWorkType12Dept())) {
				worker.setWorkType12Dept(worker.getWorkType12Dept().trim());
			}
			
			if (StringUtils.isNotBlank(worker.getDepartment2())) {
				worker.setDepartment2Descr(department2Service.getByCode(worker.getDepartment2()).getDesc2());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("admin/basic/worker/worker_view")
			.addObject("worker", worker);
	}
	
	/**
	 * 检查IdNum
	 * zb
	 * @param request
	 * @param response
	 * @param idNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkIdNum")
	public String checkIdNum(HttpServletRequest request, HttpServletResponse response, String idNum) {
		idNum = idNum.replaceAll("\\s*", "");
		Worker idNumCheck = workerService.getByIdnum(idNum);
		if(idNumCheck != null){
			return "false";
		}else{
			return "true";
		}
	}
	
	/**
	 * 新增worker信息
	 * @author	created by zb
	 * @date	2018-7-17--下午3:40:53
	 * @param request
	 * @param response
	 * @param worker
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Worker worker){
		logger.debug("添加worker开始");
		try{
			worker.setM_umState("A");/*操作类型*/
			worker.setPhone(worker.getPhone().replaceAll("\\s*", ""));
			worker.setIdnum(worker.getIdnum().replaceAll("\\s*", ""));
			worker.setLastUpdate(new Date());
			worker.setLastUpdatedBy(getUserContext(request).getCzybh());
			worker.setExpired("F");
			worker.setActionLog("ADD");
			/*执行存储过程*/
			Result result = this.workerService.doSave(worker);
		
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加worker失败");
		}
	}
	
	/**
	 * 修改worker和修改工人属性
	 * @author	created by zb
	 * @date	2018-7-17--下午4:06:07
	 * @param request
	 * @param response
	 * @param worker
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Worker worker){
		logger.debug("修改worker开始");
		try{
			/*如果操作类型不为"Z"就赋值为"M"*/
			if (!"Z".equals(worker.getM_umState())) {
				worker.setM_umState("M");
			}
			worker.setPhone(worker.getPhone().replaceAll("\\s*", ""));
			worker.setIdnum(worker.getIdnum().replaceAll("\\s*", ""));
			worker.setLastUpdatedBy(getUserContext(request).getCzybh());
			worker.setLastUpdate(new Date());
			worker.setActionLog("EDIT");
			
			Result result = this.workerService.doSave(worker);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"修改成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改worker失败");
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param worker
	 */
	@RequestMapping("/doUpdateWorker")
	public void doUpdateWorker(HttpServletRequest request, HttpServletResponse response, Worker worker){
		logger.debug("修改worker开始");
		try{
			Worker wk=new Worker();
			wk=workerService.get(Worker.class, worker.getCode());
			wk.setVehicle(worker.getVehicle());
			wk.setAddress(worker.getAddress());
			wk.setPrjRegionCode(worker.getPrjRegionCode());
			wk.setSpcBuilder(worker.getSpcBuilder());
			wk.setRcvPrjType(worker.getRcvPrjType());
			wk.setRemarks(worker.getRemarks());
			wk.setLastUpdate(new Date());
			wk.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			wk.setActionLog("Edit");
			this.workerService.update(wk);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
	/**
	 * 删除worker
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除worker开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "worker编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Worker worker = workerService.get(Worker.class, deleteId);
				if(worker == null)
					continue;
				worker.setExpired("T");
				workerService.update(worker);
			}
		}
		logger.debug("删除worker IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/addMember")
	public ModelAndView addMember(HttpServletRequest request,
	        HttpServletResponse response, String postData) {
	    
	    JSONObject object = JSON.parseObject(postData);
        
	    return new ModelAndView("admin/basic/worker/worker_addMember")
	        .addObject("map", object);
    }

    @RequestMapping("/updateMember")
    public ModelAndView updateMember(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/basic/worker/worker_updateMember")
            .addObject("map", object);
    }
    
    @RequestMapping("/viewMember")
    public ModelAndView viewMember(HttpServletRequest request,
            HttpServletResponse response, String postData) {
        
        JSONObject object = JSON.parseObject(postData);
        
        return new ModelAndView("admin/basic/worker/worker_viewMember")
        .addObject("map", object);
    }
    
    @RequestMapping("/members/checkCanDelete/{memberCode}")
    @ResponseBody
    public boolean checkCanDeleteMember(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String memberCode) {
        
        return workerService.checkCanDeleteMember(memberCode);
    }
	
	/**
	 * worker导出Excel
	 * zb
	 * @param request
	 * @param response
	 * @param worker
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Worker worker){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workerService.findPageBySqlList(page, worker, this.getUserContext(request));
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工人信息表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doSaveWorkerWorkType12ForProc")
	public void doSaveWorkerWorkType12ForProc(HttpServletRequest request,HttpServletResponse response,Worker worker){
		logger.debug("保存");		
		try {	
			worker.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String xmlData = worker.getDetailJson();
			Result result = this.workerService.saveWorkerWorkType12ForProc(worker,xmlData);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/uploadWorkerAvatarPic")
	public void uploadWorkerAvatarPic(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 上传成功后返回数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			
			// 解析参数
			MultipartFormData multipartFormData = new MultipartFormData(request);
			String workerCode=multipartFormData.getParameter("workerCode");
			
			FileItem fileItem = multipartFormData.getFileItem();
			
			// 工人头像命名规则
			WorkerAvatarPicUploadRule workerAvatarPicUploadRule = new WorkerAvatarPicUploadRule(fileItem.getName(), workerCode);

			// 调用上传工具将文件上传到服务
//			Result result = FileUploadUtils.upload(fileItem.getInputStream(), workerAvatarPicUploadRule);
			OssManager.uploadFile(fileItem.getInputStream(), workerAvatarPicUploadRule.getFileName(), "workerAvatarPic/"+workerCode, workerAvatarPicUploadRule.getFileName());
			dataMap.put("avatarPic", workerAvatarPicUploadRule.getFullName());
			ServletUtils.outPrintSuccess(request, response, dataMap);

		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "上传失败");
		}
		
	}
	
	@RequestMapping("/doRegisterMall")
	public void doRegisterMall(HttpServletRequest request, HttpServletResponse response, Worker worker){
		logger.debug("注册自装通开始");
		try{
			Worker wk=new Worker();
			wk=workerService.get(Worker.class, worker.getCode());
			wk.setPersonalProfile(worker.getPersonalProfile());
			wk.setIsRegisterMall(worker.getIsRegisterMall());
			wk.setAvatarPic(worker.getAvatarPic());
			wk.setLastUpdate(new Date());
			wk.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			wk.setActionLog("Edit");
			this.workerService.update(wk);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改自装通信息失败");
		}
	}
}
