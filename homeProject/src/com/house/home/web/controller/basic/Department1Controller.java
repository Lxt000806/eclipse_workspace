package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Company;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.workflow.Department;
import com.house.home.service.basic.Department1Service;
import com.house.home.service.basic.EmployeeService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

@Controller
@RequestMapping("/admin/department1")
public class Department1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(Department1Controller.class);

	@Autowired
	private Department1Service department1Service;
	@Autowired
	private EmployeeService employeeService;
	
	@Resource(name = "departmentCacheManager")
	private ICacheManager departmentCacheManager;
	
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
			HttpServletResponse response,Department1 department1) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		department1Service.findPageBySql(page, department1);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Department1 department1){
		
		return new ModelAndView("admin/basic/department1/department1_code").addObject("department1",department1);
	}
	
	/**
	 * 一级部门列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/department1/department1_list");
	}
	
	/**
	 * 跳转到新增一级部门页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, Department1 dpartment1){
		logger.debug("跳转到新增一级部门页面");
		Department1 department1 = null;
		if (StringUtils.isNotBlank(dpartment1.getCode())) {
			department1 = department1Service.getByCode(dpartment1.getCode());
			//复制时清空部门编号
			if("C".equals(dpartment1.getM_umState())){
				department1.setCode("");
			}
				
			if(StringUtils.isNotBlank(department1.getCmpCode())){
				Company company = department1Service.get(Company.class,department1.getCmpCode());
				if(company!=null){
					department1.setCompName(company.getDesc1());
				}
			}
		} else {
			department1 = new Department1();
		}
		department1.setM_umState(dpartment1.getM_umState());		
		return new ModelAndView("admin/basic/department1/department1_save")
			.addObject("department1", department1);
	}
	/**
	 * 跳转到修改一级部门页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改一级部门页面");
		Department1 department1 = null;
		if (StringUtils.isNotBlank(id)){
			department1 = department1Service.get(Department1.class, id);
		}else{
			department1 = new Department1();
		}
		
		return new ModelAndView("admin/basic/department1/department1_update")
			.addObject("department1", department1);
	}
	
	/**
	 * 跳转到查看一级部门页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看一级部门页面");
		Department1 department1 = department1Service.get(Department1.class, id);
		
		return new ModelAndView("admin/basic/department1/department1_detail")
				.addObject("department1", department1);
	}
	/**
	 * 添加一级部门
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, Department1 department1) {
		logger.debug("添加一级部门开始");
		try {
			if (department1.getPlanNum() == null) {
				department1.setPlanNum(0);
			}
			String str = department1Service.getSeqNo("tDepartment1");
			if (str == null) {
				ServletUtils.outPrintFail(request, response, "一级部门编号获取失败");
				return;
			}
			String str2 = department1Service.getSeqNo("tDepartment");
			if (str2 == null) {
				ServletUtils.outPrintFail(request, response, "部门编号获取失败");
				return;
			}
			
			if (StringUtils.isBlank(department1.getIsOutChannel())) {
                department1.setIsOutChannel("0");
            }
			
			department1.setCode(str);
			department1.setDesc1(department1.getDesc2());
			department1.setExpired("F");
			department1.setLastUpdate(new Date());
			department1.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			department1.setActionLog("ADD");
			department1.setDepartment(str2);
			
			// 同步部门表
			Department department = new Department(str2,
					department1.getCmpCode(), department1.getDesc2(),
					department1.getDesc2(), department1.getLastUpdate(),
					department1.getLastUpdatedBy(), department1.getExpired(),
					department1.getActionLog(), department1.getPlanNum(),
					department1.getDispSeq(), department1.getBusiType(),
					department1.getDepType(), "", str2, "", "1","1");
			
			department.setIsOutChannel(department1.getIsOutChannel());
			
			this.department1Service.save(department1);
			this.department1Service.save(department);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "添加一级部门失败");
		}
	}
	
	/**
	 * 修改一级部门
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, 
			Department1 department1){
		logger.debug("修改一级部门开始");
		try{
		    
		    if (StringUtils.isBlank(department1.getIsOutChannel())) {
                department1.setIsOutChannel("0");
            }
		    
			department1.setDesc1(department1.getDesc2());
			department1.setLastUpdate(new Date());
			department1.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			department1.setActionLog("EDIT");
			Department dpt=department1Service.get(Department.class,department1.getDepartment());
			// 同步部门表
			Department department = new Department(department1.getDepartment(),
					department1.getCmpCode(), department1.getDesc2(),
					department1.getDesc2(), department1.getLastUpdate(),
					department1.getLastUpdatedBy(), department1.getExpired(),
					department1.getActionLog(), department1.getPlanNum(),
					department1.getDispSeq(), department1.getBusiType(),
					department1.getDepType(), "", department1.getDepartment(), "", dpt.getIsActual(),dpt.getIsProcDept());
			
			department.setIsOutChannel(department1.getIsOutChannel());
			
			this.department1Service.update(department1);
			this.department1Service.update(department);
			//过期时，也过期子部门
			if("T".equals(department1.getExpired())){
				this.department1Service.expiredChild(department1);
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改一级部门失败");
		}
	}
	
	/**
	 * 删除一级部门
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除一级部门开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "一级部门编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Department1 department1 = this.department1Service.get(Department1.class, deleteId);
				if(department1 == null)
					continue;
				this.department1Service.delete(department1);
			}
		}
		departmentCacheManager.refresh();
		logger.debug("删除一级部门 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/changeDepartment1")
	public void changeDepartment1(HttpServletRequest request,HttpServletResponse response,String code) {
		logger.debug("异步加载二级部门");
		List<Department2> list = (List<Department2>) departmentCacheManager.get("department1_"+code+"_list");
		StringBuffer sb = new StringBuffer("");
		sb.append("<option value=\"\">请选择...</option>");
		if (list != null && list.size()>0){
			for (Department2 at :list){
				at.setDepLeader(employeeService.getDepLeader(at.getCode()));
				sb.append("<option value=\"")
				.append(at.getCode()).append("\">")
				.append(at.getCode()).append(" ")
				.append(at.getDesc2()).append(" ").append(at.getDepLeader())
				.append("</option>");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strSelect", sb.toString());
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(jsonObject.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/changeDepartment1s")
	public void changeDepartment1s(HttpServletRequest request,HttpServletResponse response,String code,
			String depType,String czybh) {
		logger.debug("异步加载二级部门");
		String virtualRootLabel = "请选择";
		String virtualRootId = "_VIRTUAL_RO0T_ID_";
		String sqlValueKey = "code";
		String sqlLableKey = "desc1";
		String retStr = "[]";
		/*if (StringUtils.isBlank(code)){ update by cjg 20200619 一级部门不设置必填，兼容部门类型联动下拉框
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
		}*/
		String sql = "select a.code,a.desc1 from tDepartment2 a ";
		//update by cjg 20200619 一级部门不设置必填，兼容部门类型联动下拉框
		if(StringUtils.isNotBlank(code)){
			sql+="inner join (select * from fStrToTable('"+code+"',',')) t on a.Department1=t.item ";
		}
		sql+="where a.Expired='F' " ;
		if (StringUtils.isNotBlank(depType)){
			sql+="and a.DepType in (select * from fStrToTable('"+depType+"',',')) ";
		}
		if (StringUtils.isNotBlank(czybh)) {
			sql+="and (exists(select 1 from tCZYDept in_a where in_a.Department2=a.Code and in_a.CZYBH='"+czybh+"') "
				+"or exists (select 1 from tCZYDept in_b where in_b.Department1=a.Department1 and isnull(in_b.Department2, '') = '' and in_b.CZYBH='"+czybh+"')) "; // 二级部门没有，一级部门有，则取一级部门下的所有二级部门 add by zb on 20190913
		}
		sql+="order by a.Department1";
		
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
				item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey)+" "+employeeService.getDepLeader(map.get(sqlValueKey).toString()));
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
		
	}
	@RequestMapping(value = "/byDepType/{type}/{pCode}") 
	@ResponseBody
	public JSONObject findDepType(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.department1Service.findDepType(type, pCode);
		return this.out(regionList, true);
	}
	
	@RequestMapping(value = "/byDepAll/{type}/{pCode}") 
	@ResponseBody
	public JSONObject findDepAll(@PathVariable Integer type,@PathVariable String pCode,HttpServletRequest request){
		List<Map<String,Object>> regionList = this.department1Service.findDepAll(type, pCode);
		return this.out(regionList, true);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Department1 department1){
		try {
			Page<Map<String, Object>>page= this.newPageForJqGrid(request);
			page.setPageSize(-1);
			department1Service.findPageBySql(page, department1);
			getExcelList(request);
			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"一级部门表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
