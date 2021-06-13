package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.house.home.entity.basic.WorkingDate;
import com.house.home.service.basic.WorkingDateService;

@Controller
@RequestMapping("/admin/workingDate")
public class WorkingDateController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(WorkingDateController.class);

	@Autowired
	private WorkingDateService workingDateService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			WorkingDate workingDate) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		workingDateService.findPageBySql(page, workingDate);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList() {
		return new ModelAndView("admin/basic/workingDate/workingDate_list");
	}

	@RequestMapping("/goEdit")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, WorkingDate workingDate) {
		return new ModelAndView("admin/basic/workingDate/workingDate_edit")
				.addObject("workingDate", workingDate);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, WorkingDate workingDate) {
		return new ModelAndView("admin/basic/workingDate/workingDate_edit")
			.addObject("workingDate", workingDate);
	}
	
	@RequestMapping("/goHoliSet")
	public ModelAndView goHoliSet(HttpServletRequest request,
			HttpServletResponse response, WorkingDate workingDate) {
		workingDate.setDateFrom(DateUtil.startOfTheYear(new Date()));
		workingDate.setDateTo(DateUtil.endOfTheYear(new Date()));
		return new ModelAndView("admin/basic/workingDate/workingDate_holiSet")
			.addObject("workingDate", workingDate);
	}
	
	/**
	 * 工作日期类型初始化
	 * @author	created by zb
	 * @date	2020-3-3--下午2:39:03
	 * @param request
	 * @param response
	 * @param workingDate
	 */
	@RequestMapping("/doDateInit")
	public void doDateInit(HttpServletRequest request,
			HttpServletResponse response, WorkingDate workingDate) {
		logger.debug("工作日期类型初始化");
		try {
			this.workingDateService.doDateInit(workingDate);
			ServletUtils.outPrint(request, response, true, "保存成功", null, true);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工作日期类型初始化失败");
		}
	}
	/**
	 * 工作日期类型更新
	 * @author	created by zb
	 * @date	2020-3-3--下午2:39:13
	 * @param request
	 * @param response
	 * @param workingDate
	 */
	@RequestMapping("/doUpdateHoliType")
	public void doUpdateHoliType(HttpServletRequest request,
			HttpServletResponse response, WorkingDate workingDate) {
		logger.debug("工作日期类型更新");
		try {
			this.workingDateService.doUpdateHoliType(workingDate);
			ServletUtils.outPrint(request, response, true, "保存成功", null, true);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工作日期类型更新");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, WorkingDate workingDate) {
		logger.debug("工作日期安排编辑");
		try {
			this.workingDateService.doUpdate(workingDate);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "工作日期安排编辑失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, WorkingDate workingDate) {
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		workingDateService.findPageBySql(page, workingDate);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"工作日期安排_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
