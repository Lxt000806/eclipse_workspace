package com.house.home.web.controller.basic;

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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ExcelTask;
import com.house.home.service.basic.ExcelTaskService;

@Controller
@RequestMapping("/admin/excelTask")
public class ExcelTaskController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ExcelTaskController.class);

	@Autowired
	private ExcelTaskService excelTaskService;

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
			HttpServletResponse response, ExcelTask excelTask) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		excelTask.setLastUpdatedBy(getUserContext(request).getCzybh());
		excelTaskService.findPageBySql(page, excelTask);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ExcelTask列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/excelTask/excelTask_list");
	}
	/**
	 * ExcelTask查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/excelTask/excelTask_code");
	}
	/**
	 * 跳转到新增ExcelTask页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ExcelTask页面");
		ExcelTask excelTask = null;
		if (StringUtils.isNotBlank(id)){
			excelTask = excelTaskService.get(ExcelTask.class, Integer.parseInt(id));
			excelTask.setPk(null);
		}else{
			excelTask = new ExcelTask();
		}
		
		return new ModelAndView("admin/basic/excelTask/excelTask_save")
			.addObject("excelTask", excelTask);
	}
	/**
	 * 跳转到修改ExcelTask页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ExcelTask页面");
		ExcelTask excelTask = null;
		if (StringUtils.isNotBlank(id)){
			excelTask = excelTaskService.get(ExcelTask.class, Integer.parseInt(id));
		}else{
			excelTask = new ExcelTask();
		}
		
		return new ModelAndView("admin/basic/excelTask/excelTask_update")
			.addObject("excelTask", excelTask);
	}
	
	/**
	 * 跳转到查看ExcelTask页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ExcelTask页面");
		ExcelTask excelTask = excelTaskService.get(ExcelTask.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/basic/excelTask/excelTask_detail")
				.addObject("excelTask", excelTask);
	}
	/**
	 * 添加ExcelTask
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ExcelTask excelTask){
		logger.debug("添加ExcelTask开始");
		try{
			this.excelTaskService.save(excelTask);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ExcelTask失败");
		}
	}
	
	/**
	 * 修改ExcelTask
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ExcelTask excelTask){
		logger.debug("修改ExcelTask开始");
		try{
			excelTask.setLastUpdate(new Date());
			excelTask.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.excelTaskService.update(excelTask);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ExcelTask失败");
		}
	}
	
	/**
	 * 删除ExcelTask
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ExcelTask开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ExcelTask编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ExcelTask excelTask = excelTaskService.get(ExcelTask.class, Integer.parseInt(deleteId));
				if(excelTask == null)
					continue;
				excelTask.setExpired("T");
				excelTaskService.update(excelTask);
			}
		}
		logger.debug("删除ExcelTask IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ExcelTask导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ExcelTask excelTask){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		excelTaskService.findPageBySql(page, excelTask);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ExcelTask_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
