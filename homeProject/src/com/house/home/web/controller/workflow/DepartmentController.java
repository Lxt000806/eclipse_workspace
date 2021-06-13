package com.house.home.web.controller.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.workflow.Department;
import com.house.home.service.workflow.DepartmentService;

@Controller
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, Department department) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = this.getUserContext(request);
		departmentService.findPageBySql(page, department,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * Department列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/workflow/department/department_list");
	}
	/**
	 * Department查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,Department department) throws Exception {

		return new ModelAndView("admin/workflow/department/department_code").addObject("department", department);
	}
	/**
	 * 根据id查询部门详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDepartment")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的code为空", false);
		}
		Department department= departmentService.get(Department.class, id);
		if(department == null){
			return this.out("系统中不存在Code="+id+"的部门", false);
		}
		return this.out(department, true);
	}
	/**
	 * 跳转到新增Department页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增Department页面");
		String m_umState=request.getParameter("m_umState").toString();
		JSONObject jsonObject=new JSONObject();
		if(!"A".equals(m_umState)){
			jsonObject=JSONObject.parseObject(request.getParameter("map"));
			//jsonObject.put("hasEmp", departmentService.hasEmp(jsonObject.getString("code").toString()).size());
		}
		jsonObject.put("m_umState", m_umState);
		return new ModelAndView("admin/workflow/department/department_save")
				.addObject("map", jsonObject);
	}
	
	/**
	 * 跳转到修改Department页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到修改Department页面");
		return goSave(request, response);
	}
	
	/**
	 * 跳转到查看Department页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看Department页面");
		return goSave(request, response);
	}
	/**
	 * 添加Department
	 * @param request
	 * @param response
	 * @param department
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Department department){
		logger.debug("添加Department开始");
		try {	
			department.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			
			if (StringUtils.isBlank(department.getIsOutChannel())) {
                department.setIsOutChannel("0");
            }
			
			Result result = this.departmentService.doSaveProc(department);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}

	/**
	 *Department导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Department department){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = this.getUserContext(request);
		departmentService.findPageBySql(page, department, uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"Department_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 跳转到查看DepEmp列表页面
	 * @return
	 */
	@RequestMapping("/goDepEmp")
	public ModelAndView goDepEmp(HttpServletRequest request, HttpServletResponse response,Department department){
		logger.debug("跳转到查看DepEmp列表页面");
		department= departmentService.get(Department.class, department.getCode());
		return new ModelAndView("admin/workflow/department/department_depEmp").addObject("department", department);
	}
	/**
	 * 跳转到新增DepEmp页面
	 * @return
	 */
	@RequestMapping("/goDepEmpAdd")
	public ModelAndView goDepEmpAdd(HttpServletRequest request, HttpServletResponse response,Department department){
		logger.debug("跳转到新增DepEmp页面");
		return new ModelAndView("admin/workflow/department/department_depEmp_add").addObject("department", department);
	}
	/**
	 * 查询EmpJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goEmpJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goEmpJqGrid(HttpServletRequest request,
			HttpServletResponse response, Department department) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		departmentService.findEmpBySql(page, department);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到修改上级部门页面
	 * @return
	 */
	@RequestMapping("/goModifyHigherDept")
	public ModelAndView goModifyHigherDept(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到修改上级部门页面");
		JSONObject jsonObject=new JSONObject();
		jsonObject=JSONObject.parseObject(request.getParameter("map"));
		return new ModelAndView("admin/workflow/department/department_modifyHigherDept")
				.addObject("map", jsonObject);
	}
	/**
	 * 跳转到部门过期页面
	 * @return
	 */
	@RequestMapping("/goDeptExpired")
	public ModelAndView goDeptExpired(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到部门过期页面");
		JSONObject jsonObject=new JSONObject();
		jsonObject=JSONObject.parseObject(request.getParameter("map"));
		List<Map<String,Object>> nodes = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> lowerDeptList =departmentService.findLowerDeptBySql(jsonObject.get("code").toString());
		Integer minLevel=Integer.parseInt(lowerDeptList.get(0).get("level").toString());//最小级别
		Integer maxLevel=Integer.parseInt(lowerDeptList.get(lowerDeptList.size()-1).get("level").toString());//最大级别
		for (int i = minLevel; i <= maxLevel; i++) {
			for (int j = 0; j < lowerDeptList.size(); j++) {
				if(i==Integer.parseInt(lowerDeptList.get(j).get("level").toString())){
					Map<String, Object> node=new HashMap<String, Object>();
					Map<String, Object> lowerDeptMap=lowerDeptList.get(j);
					node.put("id", lowerDeptMap.get("code").toString());
					node.put("pId", i==minLevel?"-1":lowerDeptMap.get("higherDep").toString());
					node.put("name", lowerDeptMap.get("desc2").toString());
					node.put("isParent", Integer.parseInt(lowerDeptMap.get("level").toString())<maxLevel);
					node.put("open", i==minLevel?true:false);
					node.put("nocheck", "F".equals(jsonObject.get("expired").toString())?true:false);
					node.put("checked", i==minLevel?true:false);
					nodes.add(node);
				}
			}
		}
		String json = "[]";
		if(null != nodes && nodes.size()>0)
			json = JSON.toJSONString(nodes);
		return new ModelAndView("admin/workflow/department/department_deptExpired")
				.addObject("map", jsonObject).addObject("nodes", json);
	}
	
}
