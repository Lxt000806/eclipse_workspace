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
import com.house.home.entity.basic.SegDiscRule;
import com.house.home.service.basic.SegDiscRuleService;

@Controller
@RequestMapping("/admin/segDiscRule")
public class SegDiscRuleController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(SegDiscRuleController.class);

	@Autowired
	private SegDiscRuleService segDiscRuleService;

	/**
	 * 查询JqGrid表格数据
	 * @author	created by zb
	 * @date	2019-8-1--上午11:21:28
	 * @param request
	 * @param segDiscRule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			SegDiscRule segDiscRule) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		segDiscRuleService.findPageBySql(page, segDiscRule);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList() {
		return new ModelAndView("admin/basic/segDiscRule/segDiscRule_list");
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, SegDiscRule segDiscRule) {
			segDiscRule.setDirectorDiscAmount(0.0);
		return new ModelAndView("admin/basic/segDiscRule/segDiscRule_save")
			.addObject("segDiscRule", segDiscRule);
	}

	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, SegDiscRule segDiscRule) {
		return new ModelAndView("admin/basic/segDiscRule/segDiscRule_save")
				.addObject("segDiscRule", segDiscRule);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, SegDiscRule segDiscRule) {
		return new ModelAndView("admin/basic/segDiscRule/segDiscRule_save")
			.addObject("segDiscRule", segDiscRule);
	}

	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, SegDiscRule segDiscRule) {
		logger.debug("分段优惠管理保存");
		try {
			segDiscRule.setLastUpdate(new Date());
			segDiscRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			segDiscRule.setExpired("F");
			segDiscRule.setActionLog("ADD");
			this.segDiscRuleService.save(segDiscRule);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "分段优惠管理保存失败");
		}
	}

	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, SegDiscRule segDiscRule) {
		logger.debug("分段优惠管理编辑");
		try {
			segDiscRule.setLastUpdate(new Date());
			segDiscRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			segDiscRule.setActionLog("Edit");
			this.segDiscRuleService.update(segDiscRule);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "分段优惠管理编辑失败");
		}
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, SegDiscRule segDiscRule) {
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		segDiscRuleService.findPageBySql(page, segDiscRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"分段优惠管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
