package com.house.framework.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.conf.LogModuleConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.log.LoggerObject;
import com.house.framework.service.OperLogService;
import com.house.home.entity.basic.Czybm;
import com.house.home.service.basic.CzybmService;

@Controller
@RequestMapping("/admin/log")
public class OperLogController extends BaseController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(OperLogController.class);

	@Autowired
	private OperLogService operLogService;
	@Autowired
	private CzybmService czybmService;

	/**
	 * 日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, LoggerObject logObj, String startTime, String endTime) throws Exception {

		Page page = this.newPage(request);

		operLogService.findPage(page, logObj, null, startTime,endTime);
		
		List<Czybm> userList = this.czybmService.loadAll(Czybm.class);

		return new ModelAndView("system/log/log_list").addObject(CommonConstant.PAGE_KEY, page)
				.addObject("logObj", logObj)
				.addObject("startTime", startTime)
				.addObject("endTime", endTime)
				.addObject("userJSON", JSON.toJSONString(userList))
				.addObject("ABSTRACT_DICT_LOG_MODULE", LogModuleConstant.ABSTRACT_DICT_LOG_MODULE);
	}

	/**
	 * 日志明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,
			HttpServletResponse response, String logId) throws Exception {

		Page page = this.newPage(request);

		operLogService.findPage(page, null, logId, null, null);

		return new ModelAndView("system/log/log_detail")
				.addObject("ABSTRACT_DICT_LOG_MODULE", LogModuleConstant.ABSTRACT_DICT_LOG_MODULE)
				.addObject(CommonConstant.PAGE_KEY, page);
		
	}
	
	/**
	 * wap平台日志列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/goListWap")
	public ModelAndView goListWap(HttpServletRequest request,
			HttpServletResponse response, LoggerObject logObj, String startTime, String endTime, String userName) throws Exception {
		
		Page page = this.newPage(request);
		
		operLogService.findPageWap(page, logObj, null, startTime,endTime);
		
		return new ModelAndView("system/log/wap_log_list").addObject(CommonConstant.PAGE_KEY, page)
				.addObject("logObj", logObj)
				.addObject("startTime", startTime)
				.addObject("endTime", endTime)
				.addObject("ABSTRACT_DICT_LOG_MODULE", LogModuleConstant.ABSTRACT_DICT_LOG_MODULE);
	}
	
	/**
	 * 日志明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/goDetailWap")
	public ModelAndView goDetailWap(HttpServletRequest request,
			HttpServletResponse response, String logId) throws Exception {
		
		Page page = this.newPage(request);
		
		operLogService.findPageWap(page, null, logId, null, null);
		
		return new ModelAndView("system/log/wap_log_detail")
		.addObject("ABSTRACT_DICT_LOG_MODULE", LogModuleConstant.ABSTRACT_DICT_LOG_MODULE)
		.addObject(CommonConstant.PAGE_KEY, page);
		
	}
	
}
