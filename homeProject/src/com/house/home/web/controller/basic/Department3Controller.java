package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Department3;
import com.house.home.entity.workflow.Department;
import com.house.home.service.basic.Department2Service;
import com.house.home.service.basic.Department3Service;

@Controller
@RequestMapping("/admin/department3")
public class Department3Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(Department3Controller.class);

	@Autowired
	private Department2Service department2Service;
	@Autowired
	private Department3Service department3Service;
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
			HttpServletResponse response,Department3 department3) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		department3Service.findPageBySql(page, department3);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 三级部门列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Department3 department3) throws Exception {

		return new ModelAndView("admin/basic/department3/department3_list");
	}
	
	/**
	 * 跳转到新增三级部门页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,Department3 dpartment3){
		logger.debug("跳转到新增三级部门页面");
		Department3 department3 = null;
		if (StringUtils.isNotBlank(dpartment3.getCode())) {
			department3 = department3Service.getByCode(dpartment3.getCode());
			if("C".equals(dpartment3.getM_umState())){
				department3.setCode("");
			}
		}else{
			department3 = new Department3();
		}
		department3.setM_umState(dpartment3.getM_umState());	
		department3.setDepartment1(dpartment3.getDepartment1());
		return new ModelAndView("admin/basic/department3/department3_save")
			.addObject("department3", department3);
	}
	/**
	 * 跳转到修改三级部门页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改三级部门页面");
		Department3 department3 = null;
		if (StringUtils.isNotBlank(id)){
			department3 = department3Service.get(Department3.class, id);
			Department2 department2 = department2Service.get(Department2.class, department3.getDepartment2());
			if (department2!=null){
				department3.setDepartment1(department2.getDepartment1());
			}
		}else{
			department3 = new Department3();
		}
		
		return new ModelAndView("admin/basic/department3/department3_update")
			.addObject("department3", department3);
	}
	
	/**
	 * 跳转到查看三级部门页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看三级部门页面");
		Department3 department3 = department3Service.get(Department3.class, id);
		Department2 department2 = department2Service.get(Department2.class, department3.getDepartment2());
		if (department2!=null){
			department3.setDepartment1(department2.getDepartment1());
		}
		
		return new ModelAndView("admin/basic/department3/department3_detail")
				.addObject("department3", department3);
	}
	/**
	 * 添加三级部门
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Department3 department3){
		logger.debug("添加三级部门开始");
		try{
			if(department3.getPlanNum()==null){
				department3.setPlanNum(0);
			}
			String str = department3Service.getSeqNo("tDepartment3");
			if(str==null){
				ServletUtils.outPrintFail(request, response, "三级部门编号获取失败");
				return;
			}
			String str2 = department2Service.getSeqNo("tDepartment");
			if (str2 == null) {
				ServletUtils.outPrintFail(request, response, "部门编号获取失败");
				return;
			}
			
			if (StringUtils.isBlank(department3.getIsOutChannel())) {
                department3.setIsOutChannel("0");
            }
			
			department3.setCode(str);
			department3.setDesc1(department3.getDesc2());
			department3.setExpired("F");
			department3.setLastUpdate(new Date());
			department3.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			department3.setActionLog("ADD");
			department3.setDepartment(str2);
			
			// 同步部门表
			Department1 department1= department3Service.get(Department1.class, department3.getDepartment1());//一级部门
			Department2 department2= department3Service.get(Department2.class, department3.getDepartment2());//二级部门
			String path=department1.getDepartment()+"/"+department2.getDepartment()+"/"+str2;//拼接路径
			Department department = new Department(department3.getDepartment(),
					department1.getCmpCode(), department3.getDesc2(),
					department3.getDesc2(), department3.getLastUpdate(),
					department3.getLastUpdatedBy(), department3.getExpired(),
					department3.getActionLog(), department3.getPlanNum(),
					0, "",
					department3.getDepType(), department2.getDepartment(), path, "", "1", "0");
			
			department.setIsOutChannel(department3.getIsOutChannel());
						
			this.department3Service.save(department);
			this.department3Service.save(department3);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加三级部门失败");
		}
	}
	
	/**
	 * 修改三级部门
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, 
			Department3 department3){
		logger.debug("修改三级部门开始");
		try{
		    if (StringUtils.isBlank(department3.getIsOutChannel())) {
		        department3.setIsOutChannel("0");
		    }
		    
			department3.setDesc1(department3.getDesc2());
			department3.setLastUpdate(new Date());
			department3.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			department3.setActionLog("EDIT");
			
			Department dpt=department3Service.get(Department.class,department3.getDepartment());
			// 同步部门表
			Department1 department1= department3Service.get(Department1.class, department3.getDepartment1());//一级部门
			Department2 department2= department3Service.get(Department2.class, department3.getDepartment2());//二级部门
			String path=department1.getDepartment()+"/"+department2.getDepartment()+"/"+department3.getDepartment();//拼接路径
			Department department = new Department(department3.getDepartment(),
					department1.getCmpCode(), department3.getDesc2(),
					department3.getDesc2(), department3.getLastUpdate(),
					department3.getLastUpdatedBy(), department3.getExpired(),
					department3.getActionLog(), department3.getPlanNum(),
					0, "",
					department3.getDepType(), department2.getDepartment(), path, "", dpt.getIsActual(),dpt.getIsProcDept());
			
			department.setIsOutChannel(department3.getIsOutChannel());
						
			this.department3Service.update(department);
			this.department2Service.update(department3);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改三级部门失败");
		}
	}
	
	/**
	 * 删除三级部门
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除三级部门开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "三级部门编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				Department3 department3 = this.department3Service.get(Department3.class, deleteId);
				if(department3 == null)
					continue;
				this.department3Service.delete(department3);
			}
		}
		departmentCacheManager.refresh();
		logger.debug("删除三级部门 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Department3 department3){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		department3Service.findPageBySql(page, department3);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"三级部门表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 获取三级部门
	 * @param request
	 * @param response
	 * @param department2
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Department3 department3){
		
		return new ModelAndView("admin/basic/department3/department3_code").addObject("department3",department3);
	}
	


}
