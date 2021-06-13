package com.house.home.web.controller.basic;

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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.WorkType12Dept;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustLog;
import com.house.home.service.basic.WorkType12DeptService;

@Controller
@RequestMapping("/admin/workType12Dept")
public class WorkType12DeptController extends BaseController{
	
	@Autowired
	private WorkType12DeptService workType12DeptService;
	
	/**
	 * 查询主表
	 * @param request
	 * @param response
	 * @param workType12Dept
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,WorkType12Dept workType12Dept) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workType12DeptService.findPageBySql(page, workType12Dept);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 主表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/workType12Dept/workType12Dept_list");
	}

	/**
	 * 新增
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新工种组分组页面");
		WorkType12Dept workType12Dept= new WorkType12Dept();
		
		return new ModelAndView("admin/basic/workType12Dept/workType12Dept_save")
			.addObject("workType12Dept", workType12Dept);
	}
	
	/**
	 * 复制
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response,String code){
		logger.debug("跳转到复制工种分组页面");
		WorkType12Dept workType12Dept= new WorkType12Dept();
		workType12Dept =workType12DeptService.get(WorkType12Dept.class, code);
		
		return new ModelAndView("admin/basic/workType12Dept/workType12Dept_copy")
			.addObject("workType12Dept", workType12Dept);
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String code){
		logger.debug("跳转到编辑工种分组页面");
		WorkType12Dept workType12Dept= new WorkType12Dept();
		workType12Dept =workType12DeptService.get(WorkType12Dept.class, code);
		
		return new ModelAndView("admin/basic/workType12Dept/workType12Dept_update")
			.addObject("workType12Dept", workType12Dept);
	}
	
	/**
	 * 查看
	 * @param request
	 * @param response
	 * @param code
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String code){
		logger.debug("跳转到查看工种分组页面");
		WorkType12Dept workType12Dept= new WorkType12Dept();
		workType12Dept =workType12DeptService.get(WorkType12Dept.class, code);
		
		return new ModelAndView("admin/basic/workType12Dept/workType12Dept_view")
			.addObject("workType12Dept", workType12Dept);
	}
	
	/**
	 * 保存操作
	 * @param request
	 * @param response
	 * @param workType12Dept
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request ,
			HttpServletResponse response , WorkType12Dept workType12Dept){
		logger.debug("新增工种分组开始");
		try{
			if (workType12DeptService.getIsExists(workType12Dept.getCode())){
				ServletUtils.outPrintFail(request, response, "系统中已经存在编号为"+workType12Dept.getCode()+"的班组分组!");
				return;
			}
			if (workType12DeptService.getIsExistsDescr(workType12Dept.getDescr(),workType12Dept.getWorkType12())){
				ServletUtils.outPrintFail(request, response, "系统中存在相同名称和工种的工种分组，不允许保存");
				return;
			}
			workType12Dept.setLastUpdate(new Date());
			workType12Dept.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			workType12Dept.setExpired("F");
			workType12Dept.setActionLog("Add");
			this.workType12DeptService.save(workType12Dept);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加工种分组失败");
		}
	}
	
	/**
	 * 编辑操作
	 * @param request
	 * @param response
	 * @param workType12Dept
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request ,
			HttpServletResponse response , WorkType12Dept workType12Dept){
		logger.debug("编辑工种分组开始");
		try{
			if (workType12DeptService.getIsExistsDescrByCode(workType12Dept.getCode(),workType12Dept.getDescr(),workType12Dept.getWorkType12())){
				ServletUtils.outPrintFail(request, response, "系统中存在相同名称和工种的工种分组，不允许保存");
				return;
			}
			workType12Dept.setLastUpdate(new Date());
			workType12Dept.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			workType12Dept.setActionLog("Edit");
			this.workType12DeptService.update(workType12Dept);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改工种分组失败");
		}
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param workType12Dept
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			WorkType12Dept workType12Dept){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		workType12DeptService.findPageBySql(page, workType12Dept);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种分组表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
