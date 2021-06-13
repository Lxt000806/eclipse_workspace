package com.house.home.web.controller.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.LongFeeRule;
import com.house.home.entity.design.EmpWorkPlan;
import com.house.home.service.design.EmpWorkPlanService;

@Controller
@RequestMapping("/admin/empWorkPlan")
public class EmpWorkPlanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(EmpWorkPlanController.class);

	@Autowired
	private EmpWorkPlanService empWorkPlanService;

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
			HttpServletResponse response, EmpWorkPlan empWorkPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		empWorkPlanService.findPageBySql(page, empWorkPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * EmpWorkPlan列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmpWorkPlan empWorkPlan=new EmpWorkPlan();
		Employee employee=empWorkPlanService.get(Employee.class, getUserContext(request).getEmnum());
		empWorkPlan.setPlanCzy(getUserContext(request).getEmnum());
		empWorkPlan.setPlanCzyDescr(employee.getNameChi());
		return new ModelAndView("admin/design/empWorkPlan/empWorkPlan_list").addObject("empWorkPlan", empWorkPlan);
	}
	/**
	 * EmpWorkPlan查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/empWorkPlan/empWorkPlan_code");
	}
	/**
	 * 跳转到新增设计计划页面
	 * @return
	 */
	@RequestMapping("/goAddDesignPlan")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增EmpWorkPlan页面");
		EmpWorkPlan empWorkPlan = new EmpWorkPlan();
		empWorkPlan.setPlanCzy(getUserContext(request).getCzybh());
		empWorkPlan.setPlanCzyType("2");
		empWorkPlan.setPlanBeginDate(DateUtil.getFirstDayOfWeek(new Date()));
		empWorkPlan.setWeekType(empWorkPlanService.isExistsThisPlan(empWorkPlan));
		return new ModelAndView("admin/design/empWorkPlan/empWorkPlan_addDesignPlan")
			.addObject("empWorkPlan", empWorkPlan);
	}
	/**
	 * 跳转到新增业务计划页面
	 * @return
	 */
	@RequestMapping("/goAddBusinessPlan")
	public ModelAndView goAddBusinessPlan(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增EmpWorkPlan页面");
		EmpWorkPlan empWorkPlan = new EmpWorkPlan();
		empWorkPlan.setPlanCzy(getUserContext(request).getCzybh());
		empWorkPlan.setPlanCzyType("1");
		empWorkPlan.setPlanBeginDate(DateUtil.getFirstDayOfWeek(new Date()));
		empWorkPlan.setWeekType(empWorkPlanService.isExistsThisPlan(empWorkPlan));
		return new ModelAndView("admin/design/empWorkPlan/empWorkPlan_addBusinessPlan")
			.addObject("empWorkPlan", empWorkPlan);
	}
	/**
	 * 跳转到修改EmpWorkPlan页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改EmpWorkPlan页面");
		EmpWorkPlan empWorkPlan = null;
		if (StringUtils.isNotBlank(id)){
			empWorkPlan = empWorkPlanService.get(EmpWorkPlan.class, Integer.parseInt(id));
		}else{
			empWorkPlan = new EmpWorkPlan();
		}
		
		return new ModelAndView("admin/design/empWorkPlan/empWorkPlan_update")
			.addObject("empWorkPlan", empWorkPlan);
	}
	
	/**
	 * 跳转到查看EmpWorkPlan页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看EmpWorkPlan页面");
		EmpWorkPlan empWorkPlan = empWorkPlanService.get(EmpWorkPlan.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/design/empWorkPlan/empWorkPlan_view")
				.addObject("empWorkPlan", empWorkPlan);
	}
	/**
	 * 添加EmpWorkPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, EmpWorkPlan empWorkPlan){
		logger.debug("添加EmpWorkPlan开始");
		try {	
			empWorkPlan.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.empWorkPlanService.doSaveProc(empWorkPlan);
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
	 * 修改EmpWorkPlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, EmpWorkPlan empWorkPlan){
		logger.debug("修改EmpWorkPlan开始");
		try{
			empWorkPlan.setLastUpdate(new Date());
			empWorkPlan.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.empWorkPlanService.update(empWorkPlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改EmpWorkPlan失败");
		}
	}
	
	/**
	 * 删除EmpWorkPlan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除EmpWorkPlan开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "EmpWorkPlan编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				EmpWorkPlan empWorkPlan = empWorkPlanService.get(EmpWorkPlan.class, Integer.parseInt(deleteId));
				if(empWorkPlan == null)
					continue;
				empWorkPlan.setExpired("T");
				empWorkPlanService.update(empWorkPlan);
			}
		}
		logger.debug("删除EmpWorkPlan IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *EmpWorkPlan导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, EmpWorkPlan empWorkPlan){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		empWorkPlanService.findPageBySql(page, empWorkPlan);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"前端计划与分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 查询DetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, EmpWorkPlan empWorkPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		empWorkPlanService.findDetailPageBySql(page, empWorkPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询DetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goViewJqGrid(HttpServletRequest request,
			HttpServletResponse response, EmpWorkPlan empWorkPlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		empWorkPlanService.findViewPageBySql(page, empWorkPlan);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 是否已存在本周和下周计划
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isExistsPlan")
	@ResponseBody
	public String isExistsPlan(HttpServletRequest request,
			HttpServletResponse response, EmpWorkPlan empWorkPlan) throws Exception {
		String isExistsThis=empWorkPlanService.isExistsThisPlan(empWorkPlan);
		String isExistsNext=empWorkPlanService.isExistsNextPlan(empWorkPlan);
		if("2".equals(isExistsThis) && "1".equals(isExistsNext)){
			return "1";
		}
		return "0";
	}
}
