package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.RcvAct;
import com.house.home.service.basic.RcvActService;

@Controller
@RequestMapping("/admin/rcvAct")
public class RcvActController extends BaseController {
	@Autowired
	private RcvActService rcvActService;

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("admin/basic/rcvAct/rcvAct_list");
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, RcvAct rcvAct) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		rcvActService.findPageBySql(page, rcvAct);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, 
			HttpServletResponse response, RcvAct rAct) {
		RcvAct rcvAct = null;
		if (StringUtils.isNotBlank(rAct.getCode())) {
			rcvAct = rcvActService.getByCode(rAct.getCode());
		} else {
			rcvAct = new RcvAct();
		}
		return new ModelAndView("admin/basic/rcvAct/rcvAct_update")
			.addObject("rcvAct", rcvAct);
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,
			RcvAct rcvAct) {
		try {
			RcvAct xt1 = this.rcvActService.get(RcvAct.class, rcvAct.getCode());
			RcvAct xt2 = rcvActService.getByDescr(rcvAct.getDescr(),rcvAct.getCode());
			if (xt2 != null) {
				ServletUtils.outPrintFail(request, response, "账户名称重复");
				return;
			}
			rcvAct.setLastUpdate(new Date());
			rcvAct.setActionLog("EDIT");
			if (xt1 != null) {
				this.rcvActService.update(rcvAct);
				ServletUtils.outPrintSuccess(request, response);
			} else {
				this.rcvActService.save(rcvAct);
				ServletUtils.outPrintSuccess(request, response);
			}
		} catch(Exception e) {
			ServletUtils.outPrintFail(request, response, "修改收款账户失败");
		}
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
		RcvAct rcvAct = new RcvAct();
		return new ModelAndView("admin/basic/rcvAct/rcvAct_save")
			.addObject("rcvAct", rcvAct);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,RcvAct rcvAct) {
		try {
			RcvAct xt1 = rcvActService.getByCode(rcvAct.getCode());
			RcvAct xt2 = rcvActService.getByDescr(rcvAct.getDescr(),rcvAct.getCode());
			if (xt1 != null) {
				ServletUtils.outPrintFail(request, response, "账户编号已存在");
				return;
			}else if (xt2 != null) {
				ServletUtils.outPrintFail(request, response, "账户名称重复");
				return;
			}
			rcvAct.setExpired("F");
			rcvAct.setLastUpdate(new Date());
			rcvAct.setActionLog("ADD");
			this.rcvActService.save(rcvAct);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e) {
			ServletUtils.outPrintFail(request, response, "添加收款账户失败");
		}
	}
	
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			RcvAct rAct) {
		RcvAct rcvAct = rcvActService.get(RcvAct.class, rAct.getCode());
		return new ModelAndView("admin/basic/rcvAct/rcvAct_detail")
			.addObject("rcvAct", rcvAct);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			RcvAct rcvAct){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		rcvActService.findPageBySql(page, rcvAct);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"收款账户表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
