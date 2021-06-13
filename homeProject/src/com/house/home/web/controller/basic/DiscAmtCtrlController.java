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
import com.house.home.entity.basic.DiscAmtCtrl;
import com.house.home.service.basic.DiscAmtCtrlService;

@Controller
@RequestMapping("/admin/discAmtCtrl")
public class DiscAmtCtrlController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(DiscAmtCtrlController.class);

	@Autowired
	private DiscAmtCtrlService discAmtCtrlService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request,
			DiscAmtCtrl discAmtCtrl) throws Exception {
		Page<Map<String, Object>> page = this.newPageForJqGrid(request);
		discAmtCtrlService.findPageBySql(page, discAmtCtrl);
		return new WebPage<Map<String, Object>>(page);
	}

	@RequestMapping("/goList")
	public ModelAndView goList() {
		return new ModelAndView("admin/basic/discAmtCtrl/discAmtCtrl_list");
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, DiscAmtCtrl discAmtCtrl) {
		return new ModelAndView("admin/basic/discAmtCtrl/discAmtCtrl_save")
			.addObject("discAmtCtrl", discAmtCtrl);
	}

	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, DiscAmtCtrl discAmtCtrl) {
		return new ModelAndView("admin/basic/discAmtCtrl/discAmtCtrl_save")
				.addObject("discAmtCtrl", discAmtCtrl);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, DiscAmtCtrl discAmtCtrl) {
		return new ModelAndView("admin/basic/discAmtCtrl/discAmtCtrl_save")
			.addObject("discAmtCtrl", discAmtCtrl);
	}

	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,
			HttpServletResponse response, DiscAmtCtrl discAmtCtrl) {
		logger.debug("优惠控制管理保存");
		try {
			discAmtCtrl.setLastUpdate(new Date());
			discAmtCtrl.setLastUpdatedBy(getUserContext(request).getCzybh());
			discAmtCtrl.setExpired("F");
			discAmtCtrl.setActionLog("ADD");
			this.discAmtCtrlService.save(discAmtCtrl);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "优惠控制管理保存失败");
		}
	}

	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,
			HttpServletResponse response, DiscAmtCtrl discAmtCtrl) {
		logger.debug("优惠控制管理编辑");
		try {
			discAmtCtrl.setLastUpdate(new Date());
			discAmtCtrl.setLastUpdatedBy(getUserContext(request).getCzybh());
			discAmtCtrl.setActionLog("Edit");
			this.discAmtCtrlService.update(discAmtCtrl);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "优惠控制管理编辑失败");
		}
	}

	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, DiscAmtCtrl discAmtCtrl) {
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		discAmtCtrlService.findPageBySql(page, discAmtCtrl);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"优惠控制管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
