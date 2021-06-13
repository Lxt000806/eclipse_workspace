package com.house.home.web.controller.basic;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.fileUpload.MultipartFormData;
import com.house.framework.commons.fileUpload.impl.EmpPicUploadRule;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileUploadServerMgr;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.oss.OssConfigure;
import com.house.framework.commons.utils.oss.OssManager;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Company;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Emppic;
import com.house.home.entity.basic.EmpTranLog;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Position;
import com.house.home.entity.workflow.Department;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.EmpExpService;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.EmpPicService;
import com.house.home.service.basic.PositionService;
import com.house.home.service.basic.XtcsService;

@Controller
@RequestMapping("/admin/employee")
public class EmployeeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmpPicService empPicService;
	@Autowired
	private PositionService positionService;
	@Autowired 
	private Department2Service department2Service;
	@Autowired
	EmpExpService empExpService;
	@Autowired
	CzybmService czybmService;
	@Autowired 
	private XtcsService xtcsService;
	String ysPath;
	Emppic emppic = new Emppic();
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,HttpServletResponse response,Employee employee) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);		
		employeeService.findPageBySql(page, employee, uc);
		return new WebPage<Map<String,Object>>(page);
	}	
	@RequestMapping("/getEmployee")
	@ResponseBody
	public JSONObject getEmployee(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Employee employee = employeeService.get(Employee.class, id);
		if(employee == null){
			return this.out("系统中不存在code="+id+"的员工信息", false);
		}
		if(StringUtils.isNotBlank(employee.getDepartment1())){
			Department1 department1 = employeeService.get(Department1.class, employee.getDepartment1());
			employee.setDepartment1Descr(department1==null?"":department1.getDesc1());
		}else{
			employee.setDepartment1Descr("");
		}
		if(StringUtils.isNotBlank(employee.getDepartment2())){
			Department2 department2 = employeeService.get(Department2.class, employee.getDepartment2());
			employee.setDepartment2Descr(department2==null?"":department2.getDesc1());
		}else{
			employee.setDepartment2Descr("");
		}
		if(StringUtils.isNotBlank(employee.getDepartment())){
			Department department = new Department();
			department = employeeService.get(Department.class, employee.getDepartment());
			if(department != null && StringUtils.isNotBlank(department.getCmpCode())){
				Company company = employeeService.get(Company.class, department.getCmpCode());
				employee.setCmpDescr(company == null?"":company.getDesc2());
				employee.setCmpCode(company == null?"":company.getCode());
			}
		}
		if(StringUtils.isNotBlank(employee.getPosition())){
			Position position = new Position();
			position = positionService.get(Position.class, employee.getPosition());
			if(position != null){
				employee.setPositionDescr(position.getDesc2());
			}
		}
		
		return this.out(employee, true);
	}
	
	/**
	 * 跳转到员工列表
	 * 
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Employee employee = new Employee();
		UserContext uc = getUserContext(request);
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		if (czybmService.hasAuthByCzybh(uc.getCzybh(),1220)) {	
		if(czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(),"0007","查看")){
			//具有查看全部内容的权限 
			employee.setValidVP("1");
			employee.setIdValidDate(new Date());
			return new ModelAndView("admin/basic/employee/employee_list").addObject("employee",employee);
		}else{
			//只具有查看通讯录的权限 
			employee.setValidVP("2");
			employee.setIdValidDate(new Date());
			return new ModelAndView("admin/basic/employee/employee_list").addObject("employee", employee);
	    }	
	}	
	/**
	 * 员工列表code
	 * 
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,HttpServletResponse response,Employee employee) throws Exception {
		//薪酬调用，设置入职时间范围，15号之前的，按上个月时间1到31号，15号之后的，这个月1号到今天
		if(StringUtils.isNotBlank(employee.getIsAddSalary())){
			Date criticalDate=DateUtil.getPreMonth(0, 16);//这个月16号
			Date today=new Date();//今天
			Date firtOfLastMonth=DateUtil.getPreMonth(1, 1);//上个月第一天
			if(today.getTime()<criticalDate.getTime()){
				employee.setJoinDateFrom(DateUtil.startOfTheMonth(firtOfLastMonth));
				employee.setJoinDateTo(DateUtil.endOfTheMonth(firtOfLastMonth));
			}else {
				employee.setJoinDateFrom(DateUtil.startOfTheMonth(today));
				employee.setJoinDateTo(today);
			}
		}
		String cmpnyCode = xtcsService.getQzById("CmpnyCode");
		if("02".equals(cmpnyCode)){
			employee.setPosition("22,261,1010");
		} else {
			employee.setPosition("94,250,456");
		}
		return new ModelAndView("admin/basic/employee/employee_code").addObject("employee", employee);
	}
	
	/**
	 * 跳转到新增员工页面
	 * 
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增员工页面");
		Employee employee = new Employee();
		employee.setJoinDate(DateUtil.getNow());
		return new ModelAndView("admin/basic/employee/employee_save").addObject("employee", employee);
	}
	/**
	 * 跳转到复制员工页面
	 * 
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response,String id){
		Employee employee = null;
		Position position=null;
		Department2 part2 =null;
		employee = employeeService.get(Employee.class,id);
		employee.setStatus(employee.getStatus().trim());
		employee.setJoinDate(new Date());
		if (StringUtils.isNotBlank(employee.getPosition())) {
			 position =positionService.get(Position.class, employee.getPosition());
			 if (position!=null) {
				 employee.setPositionDescr(position.getDesc2());
				 employee.setPositionType(position.getType());
			 }
		}
		if (StringUtils.isNotBlank(employee.getOldDept())) {
			part2 = department2Service.get(Department2.class, employee.getOldDept());
			if (part2!=null) {
				employee.setOldDepartment1(part2.getDepartment1());
			}
		}
		if (StringUtils.isNotBlank(employee.getSecondPosition())) {
			 position =positionService.get(Position.class, employee.getSecondPosition());
			 if (position!=null) {
				 employee.setSecondPositionDescr(position.getDesc2());
				 employee.setSecondPositionType(position.getType());
			 }
		}
		if(StringUtils.isNotBlank(employee.getDepartment())){
			Department department = employeeService.get(Department.class, employee.getDepartment());
			employee.setDepartmentDescr(department==null?"":department.getDesc1());
		}
		return new ModelAndView("admin/basic/employee/employee_copy").addObject("employee", employee);
	}
	/**
	 * 跳转到修改员工页面
	 * 
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, String id,String seniority){
		logger.debug("跳转到修改员工页面");
		Employee employee = null;
		Position position=null;
		Department2 part2 =null;
		try {
			if (StringUtils.isNotBlank(id)){
				employee = employeeService.get(Employee.class,id);
			}
			if (employee!=null) {
				employee.setStatus(employee.getStatus().trim());
				
				Emppic empPic = empPicService.get(Emppic.class, id);
				if (empPic != null) {
                    String url = FileUploadUtils.getFileUrl(empPic.getPhotoName());
                    employee.setUrl(url);
                    employee.setPhotoName(empPic.getPhotoName());
                }
				
				if (StringUtils.isNotBlank(employee.getPosition())) {
					 position =positionService.get(Position.class, employee.getPosition());
					 if (position!=null) {
						 employee.setPositionDescr(position.getDesc2());
						 employee.setPositionType(position.getType());
					 }
				}
				if (StringUtils.isNotBlank(employee.getOldDept())) {
					part2 = department2Service.get(Department2.class, employee.getOldDept());
					if (part2!=null) {
						employee.setOldDepartment1(part2.getDepartment1());
					}
				}
				if (StringUtils.isNotBlank(employee.getSecondPosition())) {
					 position =positionService.get(Position.class, employee.getSecondPosition());
					 if (position!=null) {
						 employee.setSecondPositionDescr(position.getDesc2());
						 employee.setSecondPositionType(position.getType());
					 }
				}
				if(StringUtils.isNotBlank(employee.getDepartment1())){
					Department1 department1 = employeeService.get(Department1.class, employee.getDepartment1());
					employee.setDepartment1Descr(department1==null?"":department1.getDesc1());
				}
				if(StringUtils.isNotBlank(employee.getDepartment2())){
					Department2 department2 = employeeService.get(Department2.class, employee.getDepartment2());
					employee.setDepartment2Descr(department2==null?"":department2.getDesc1());
				}
				if(StringUtils.isNotBlank(employee.getDepartment())){
					Department department = employeeService.get(Department.class, employee.getDepartment());
					employee.setDepartmentDescr(department==null?"":department.getDesc1());
				}
				if (StringUtils.isNotBlank(seniority)) {
					employee.setSeniority(seniority);
				}
			}	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/basic/employee/employee_update").addObject("employee", employee);
	}
	
	/**
	 * 跳转到查看员工页面
	 *
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, String id,String seniority){
		logger.debug("跳转到查看员工页面");
		Employee employee = null;
		Position position=null;
		Department2 part2 =null;

		employee = employeeService.get(Employee.class, id);
		employee.setStatus(employee.getStatus().trim());
		
		Emppic empPic = empPicService.get(Emppic.class, id);
        if (empPic != null) {
            String url = FileUploadUtils.getFileUrl(empPic.getPhotoName());
            employee.setUrl(url);
            employee.setPhotoName(empPic.getPhotoName());
        }
		
        
		if (StringUtils.isNotBlank(employee.getPosition())) {
			 position =positionService.get(Position.class, employee.getPosition());
			 if (position!=null) {
				 employee.setPositionDescr(position.getDesc2());
			 }
		}
		if (StringUtils.isNotBlank(employee.getSecondPosition())) {
			 position =positionService.get(Position.class, employee.getSecondPosition());
			 if (position!=null) {
				 employee.setSecondPositionDescr(position.getDesc2());
			 }
		}
		if (StringUtils.isNotBlank(employee.getOldDept())) {
			part2 = department2Service.get(Department2.class, employee.getOldDept());
			if (part2!=null) {
				employee.setOldDepartment1(part2.getDepartment1());
			}
		}
		if(StringUtils.isNotBlank(employee.getDepartment())){
			Department department = employeeService.get(Department.class, employee.getDepartment());
			employee.setDepartmentDescr(department==null?"":department.getDesc1());
		}
		if (StringUtils.isNotBlank(seniority)) {
			employee.setSeniority(seniority);
		}
		return new ModelAndView("admin/basic/employee/employee_detail").addObject("employee", employee);
	}
	@RequestMapping("/goPerAdd")
	public ModelAndView goPerformanceAdd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("员工信息证书新增");
		return new 	ModelAndView("admin/basic/employee/employee_performance_add");
	}
	@RequestMapping("goPerUpdate")
	public ModelAndView goPerUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("员工信息证书编辑");
		Map<String, String[]> map = request.getParameterMap();
		return new ModelAndView("admin/basic/employee/employee_performance_update").addObject("map", map);
	} 
	@RequestMapping("/goPerView")
	public ModelAndView goPreView(HttpServletRequest request, HttpServletResponse response){
		logger.debug("员工信息证书查看");
		Map<String, String[]> map = request.getParameterMap();
		return new ModelAndView("admin/basic/employee/employee_performance_detail").addObject("map", map);
	}	
	@RequestMapping("/goWorkAdd")
	public ModelAndView goWorkAdd(HttpServletRequest request,HttpServletResponse response){
		logger.debug("员工信息工作经增加");
		return new ModelAndView("admin/basic/employee/employee_work_add");
	}
	@RequestMapping("/goWorkUpdate")
	public ModelAndView goWorkUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("员工信息工作经验修改");
		Map<String,String[]> map = request.getParameterMap();
		return new ModelAndView("admin/basic/employee/employee_work_update").addObject("map", map);		
	}
	@RequestMapping("/goWorkView")
	public ModelAndView goWorkView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("员工信息工作经验察看");
		Map<String, String[]> map = request.getParameterMap();
		return new ModelAndView("admin/basic/employee/employee_work_view").addObject("map", map);	
	}
	
	/**
	 * 修改员工
	 * 
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,Employee employee){
		logger.debug("修改员工开始");
		try{
			Employee xt = this.employeeService.get(Employee.class, employee.getNumber());
			if (xt!=null){
				this.employeeService.update(employee);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.employeeService.save(employee);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改员工失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除员工
	 * 
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response,String deleteIds){
		logger.debug("删除员工开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "员工编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Employee employee = this.employeeService.get(Employee.class, deleteId);
				if(employee == null)
					continue;
				this.employeeService.delete(employee);
			}
		}
		logger.debug("删除员工 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	@RequestMapping("/checkBlur")
	public void checkBlur(HttpServletRequest request, HttpServletResponse response, String id, String info){
		logger.debug("check员工开始");
		try{
			if (StringUtils.isNotBlank(id)){
				Employee employee = employeeService.get(Employee.class, id);
				if (employee!=null){
					JSONObject obj = new JSONObject();
					obj.put("descr", employee.getNameChi());
					ServletUtils.outPrintSuccess(request, response, obj);
				}else{
					if (StringUtils.isBlank(info)){
						info = "信息";
					}
					ServletUtils.outPrintFail(request, response, "该"+info+"不存在，请重新输入");
				}
			}else{
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "获取员工失败");
		}
	}
	
	/**
     * 重构之后的新增员工信息接口
     * 
     * @param request
     * @param response
     * @param employee
     */
    @RequestMapping("/doSaveNew")
    public void doSaveNew(HttpServletRequest request,
            HttpServletResponse response, Employee employee) {

        try {
            
            if (StringUtils.isNotBlank(employee.getIdnum())
                    && employeeService.existsIdNumExceptNumber(employee.getIdnum(), null, "innerEmployee")) {

                ServletUtils.outPrintFail(request, response, "新增员工信息失败：身份证号码重复");
                return;
            }
            
            employee.setNumber(employeeService.getSeqNo("tEmployee"));
            employee.setM_umState("A");
            employee.setLastUpdate(new Date());
            employee.setLastUpdatedBy(getUserContext(request).getCzybh());
            employee.setActionLog("ADD");
            employee.setExpired("F");
            
            Result result = employeeService.doEmpforInfo(employee);
            if ("1".equals(result.getCode())) {
                ServletUtils.outPrintSuccess(request, response, "新增员工信息成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "新增员工信息失败：程序异常");
        }

    }
	
	/**
	 * 重构后的员工信息复制接口
	 * 
	 * @param request
	 * @param response
	 * @param employee
	 */
    @RequestMapping("/doCopyNew")
    public void doCopyNew(HttpServletRequest request,
            HttpServletResponse response, Employee employee) {

        try {
            
            if (StringUtils.isNotBlank(employee.getIdnum())
                    && employeeService.existsIdNumExceptNumber(employee.getIdnum(), null, "innerEmployee")) {

                ServletUtils.outPrintFail(request, response, "复制员工信息失败：身份证号码重复");
                return;
            }
            
            employee.setNumber(employeeService.getSeqNo("tEmployee"));
            employee.setM_umState("C");
            employee.setLastUpdate(new Date());
            employee.setLastUpdatedBy(getUserContext(request).getCzybh());
            employee.setActionLog("ADD");
            employee.setExpired("F");
            
            Result result = employeeService.doEmpforInfo(employee);
            if ("1".equals(result.getCode())) {
                ServletUtils.outPrintSuccess(request, response, "复制员工信息成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "复制员工信息失败：程序异常");
        }

    }
	
	/**
	 * 重构之后的员工信息更新接口
	 * 
	 * @param request
	 * @param response
	 * @param employee
	 */
    @RequestMapping("/doUpdateNew")
    public void doUpdateNew(HttpServletRequest request,
            HttpServletResponse response, Employee employee) {

        try {

            if (StringUtils.isBlank(employee.getNumber())) {
                ServletUtils.outPrintFail(request, response, "更新员工信息失败：员工编号为空");
                return;
            }
            
            if (StringUtils.isNotBlank(employee.getIdnum())
                    && employeeService.existsIdNumExceptNumber(employee.getIdnum(), employee.getNumber(), "innerEmployee")) {

                ServletUtils.outPrintFail(request, response, "更新员工信息失败：身份证号码重复");
                return;
            }
            
            employee.setM_umState("M");
            employee.setLastUpdate(new Date());
            employee.setLastUpdatedBy(getUserContext(request).getCzybh());
            employee.setActionLog("EDIT");
            
            Result result = employeeService.doEmpforInfo(employee);
            if ("1".equals(result.getCode())) {
                ServletUtils.outPrintSuccess(request, response, "员工信息更新成功");
            } else {
                ServletUtils.outPrintFail(request, response, result.getInfo());
            }

        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "员工信息更新失败：程序异常");
        }

    }
	
	
	/**
	 * 员工图片上传接口
	 * 
	 * @param request
	 * @param response
	 * @author 张海洋
	 */
	@RequestMapping("/uploadEmpPic")
    public void uploadEmpPic(HttpServletRequest request,
            HttpServletResponse response) {
	    
	    try {
	        
            MultipartFormData multipartFormData = new MultipartFormData(request);
            
            FileItem fileItem = multipartFormData.getFileItem();
            if (fileItem == null) {
                ServletUtils.outPrintFail(request, response, "上传员工图片失败：无法获取上传图片");
                return;
            }
            
            EmpPicUploadRule empPicUploadRule = new EmpPicUploadRule(fileItem.getName());
            Result uploadResult = FileUploadUtils.upload(fileItem.getInputStream(), 
            		empPicUploadRule.getFileName(),empPicUploadRule.getFilePath());
            String baseFileUrl = OssConfigure.cdnAccessUrl+"/";
            
            if (!uploadResult.isSuccess()) {
                ServletUtils.outPrintFail(request, response, "上传员工图片失败：内部转存失败");
                return;
            }
            
            Map<String, String> datas = new HashMap<String, String>();
            datas.put("empPicFullName", empPicUploadRule.getFullName());
            datas.put("empPicUrl", baseFileUrl + empPicUploadRule.getFullName());

            ServletUtils.outPrintSuccess(request, response, "上传员工图片成功", datas);
	        
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "上传员工图片失败：上传接口异常");
        }
    }
	
	/**
	 * 判断除某个员工编号外的记录中是否存在某个中文名
	 * 新增员工时判断员工编号非空的记录（全部记录）
	 * 更新员工时判断员工编号除此员工编号之外的记录
	 * 
	 * @param nameChi
	 * @param number
	 * @return
	 * @author 张海洋
	 */
	@RequestMapping("/existsNameChiExceptNumber")
	@ResponseBody
	public boolean validateEmpNameChi(String nameChi, String number) {
	    return employeeService.existsNameChiExceptNumber(nameChi, number);
	}
	
	/**
	 * 验证中文姓名是否存在
	 * 
	 */
	public Map<String,Object> validNameChi(String nameChi){
		Map<String, Object> name = employeeService.validNameChi(nameChi);
		return name;
	}	
	/**
	 * 验证员工号和身份证是否存在
	 * 
	 */
	public Map<String,Object> validNum(String number,String idnum){
		Map<String, Object> name = employeeService.validNum(number,idnum);
		return name;
	}	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,
			HttpServletResponse response, Employee employee) {
		try {
			Page<Map<String, Object>> page = this.newPage(request);
			UserContext uc = this.getUserContext(request);
			page.setPageSize(-1);
			employeeService.findPageBySql(page, employee, uc);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response,
					page.getResult(),
					"员工信息_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
					columnList, titleList, sumList);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 复选框联动复选框
	 * 
	 */
	@RequestMapping("/getProTypeTreeOpt")
	@ResponseBody
	public JSONObject getItemType2TreeOpt(HttpServletRequest request, HttpServletResponse response, String postype){
		StringBuilder html = new StringBuilder();
		html.append("[{\"id\":\"_VIRTUAL_RO0T_ID_\",\"isParent\":true,\"name\":\"请选择\",\"open\":true,\"pId\":\"\"},");
		List<Map<String,Object>> list = employeeService.getProTypeOpt(postype.trim());
		if(list != null && list.size()>0 ){
			for(int i=0;i<list.size();i++){
				html.append("{\"id\":\""+list.get(i).get("Code").toString().trim()+"\",\"name\":\""+list.get(i).get("fd").toString().trim()+"\",\"pId\":\"_VIRTUAL_RO0T_ID_\"}");
				if(i < list.size()-1){
					html.append(",");
				}
			}
		}
		html.append("]");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("html", html.toString());
		return jsonObject;
	}
	/**
	 * 职位类型单选框联动复选框,已经修改为复选联动复选
	 *
	 */
	/*@RequestMapping("/changePosTypes")
	public void changePromTypes(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载职位类型");
		String virtualRootLabel = "请选择";
		String virtualRootId = "_VIRTUAL_RO0T_ID_";
		String sqlValueKey = "code";
		String sqlLableKey = "desc2";
		String retStr = "[]";
		if (StringUtils.isBlank(code)){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("strSelect", retStr);
			try {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(jsonObject.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String sql = "select a.code,a.desc2 "
				+"from tPosition a inner join (select * from fStrToTable('"+code+"',',')) t on a.type=t.item "
				+"where a.Expired='F' order by a.code";
		List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = new HashMap<String, Object>();
		item.put("id", virtualRootId);
		item.put("pId", "");
		item.put("name", virtualRootLabel);
		item.put("isParent", true);
		item.put("open", true);
		rsList.add(item);
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size() > 0){
			for(Map<String, Object> map : list){
				item = new HashMap<String, Object>();
				item.put("id", map.get(sqlValueKey));
				item.put("pId", virtualRootId);
				item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey));
				rsList.add(item);
			}
		}
		retStr = JSON.toJSONString(rsList);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", retStr);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	//获取最新的图片
	public String getPhotoName(String number){
		String photoName = "";
		Map<String, Object> map = employeeService.getPhotoName(number);
		if (map!=null && map.get("PhotoName")!=null) {
			photoName = map.get("PhotoName").toString();	
		}
		return photoName;
	}
	/**
	 * 根据所属部门编号获取一二三级部门编号
	 * @param tableName
	 * @param code
	 * @return
	 */
	@RequestMapping("/getCodeByDept")
	@ResponseBody
	public String getCodeByDept(String tableName,String department){
		return employeeService.getCodeByDept(tableName, department);
	}
	
	@RequestMapping("/getEmpAuthority")
	@ResponseBody
	public boolean getEmpAuthority(HttpServletRequest request,HttpServletResponse response
			,String empCode){
		return employeeService.hasEmpAuthority(this.getUserContext(request).getCzybh(), empCode);
	}
	/**
	 * 跳转到员工薪资录入列表
	 * 
	 */
	@RequestMapping("/goSalaryInput")
	public ModelAndView goSalaryInput(HttpServletRequest request,HttpServletResponse response,Employee employee) throws Exception {
		employee.setM_umState("M");
		return new ModelAndView("admin/basic/employee/employee_salaryInput_list").addObject("employee", employee);
	    	
	}
	@RequestMapping("/goSalaryUpdate")
	public ModelAndView goSalaryUpdate(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("跳转到修改员工页面");
		Employee employee = null;
		try {
			if (StringUtils.isNotBlank(id)){
				employee = employeeService.get(Employee.class,id);
			}
		} catch (Exception e) {
		
		}
		return new ModelAndView("admin/basic/employee/employee_salaryInput_update").addObject("employee", employee);
	}
	
	/**
	 * 跳转到员工薪资录入列表
	 * 
	 */
	@RequestMapping("/goSalaryView")
	public ModelAndView goSalaryView(HttpServletRequest request,HttpServletResponse response,Employee employee) throws Exception {
		employee.setM_umState("V");
		return new ModelAndView("admin/basic/employee/employee_salaryInput_list").addObject("employee", employee);
	    	
	}
	
	/**
	 * 薪资——修改
	 * @return
	 * */             
	@RequestMapping("/doUpdateSalary")
	public void doUpdateSalary(HttpServletRequest request,HttpServletResponse response,Employee employee){
		logger.debug("修改库存信息");
		try{	
			Employee em =employeeService.get(Employee.class,employee.getNumber());
			em.setBasicWage(employee.getBasicWage());
			em.setPerSocialInsur(employee.getPerSocialInsur());
			em.setComSocialInsur(employee.getComSocialInsur());
			em.setInsurSignCmp(employee.getInsurSignCmp());
			em.setActionLog("EDIT");
			em.setLastUpdate(new Date());
			em.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			employeeService.update(em);
			ServletUtils.outPrintSuccess(request, response,"保存成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改失败");
		}
	}
	
	/**
	 * 获取员工领导的部门
	 * @param tableName
	 * @param code
	 * @return
	 */
	@RequestMapping("/getDeptByLeader")
	@ResponseBody
	public Map<String, Object> getDeptByLeader(String number){
		return employeeService.getDeptByLeader(number);
	}
	
	/**
	 * 获取员工修改历史记录
	 * @param tableName
	 * @param code
	 * @return
	 */
	@RequestMapping("/goEmpTranLogJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goEmpTranLogJqGrid(HttpServletRequest request,
			HttpServletResponse response,EmpTranLog empTranLog) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		employeeService.findPageBySql_empTranLog(page, empTranLog);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 跳转员工修改历史查询列表
	 * @param employee 
	 * 
	 */
	@RequestMapping("/goEmpTranLogView")
	public ModelAndView goEmpTranLogView(HttpServletRequest request,HttpServletResponse response,EmpTranLog empTranLog) throws Exception {
		empTranLog.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(), -1)));
		empTranLog.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(), -1)));
		return new ModelAndView("admin/basic/employee/employee_empTranLogView").addObject("empTranLog", empTranLog);
	    	
	}
	
	/**
	 * 导出员工修改历史查询列表
	 * @param employee 
	 * 
	 */
	@RequestMapping("/doEmpTranLogExcel")
	public void doEmpTranLogExcel(HttpServletRequest request,
			HttpServletResponse response, EmpTranLog empTranLog) {
		try {
			Page<Map<String, Object>> page = this.newPage(request);
			page.setPageSize(-1);
			employeeService.findPageBySql_empTranLog(page, empTranLog);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response,
					page.getResult(),
					"员工信息修改历史_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
					columnList, titleList, sumList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
