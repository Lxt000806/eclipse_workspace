package com.house.home.web.controller.query;

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
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.service.query.SplCheckAnalysisService;
@Controller
@RequestMapping("/admin/splCheckAnalysis")
public class SplCheckAnalysisController extends BaseController {
	
	@Autowired
	private SplCheckAnalysisService splCheckAnalysisService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		if(uc != null){
			splCheckOut.setItemRight(uc.getItemRight());
		}
		splCheckAnalysisService.findPageBySql(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}
		
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		splCheckOut.setConfirmDateFrom(DateUtil.startOfTheMonth(new Date()));
		splCheckOut.setConfirmDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/splCheckAnalysis/splCheckAnalysis_list").addObject("data", splCheckOut);
	}
	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response, SplCheckOut splCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc = getUserContext(request);
		if(uc != null){
			splCheckOut.setItemRight(uc.getItemRight());
		}
		splCheckAnalysisService.findPageBySql(page, splCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"供应商结算分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		return new ModelAndView("admin/query/splCheckAnalysis/splCheckAnalysis_view").addObject("data", splCheckOut);
	}

	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetail(HttpServletRequest request,	HttpServletResponse response, SplCheckOut splCheckOut) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc = getUserContext(request);
		if(uc != null){
			splCheckOut.setItemRight(uc.getItemRight());
		}
		splCheckAnalysisService.goJqGridDetail(page, splCheckOut);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/doExcelForView")
	public void doExcelForView(HttpServletRequest request,HttpServletResponse response, SplCheckOut splCheckOut){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		UserContext uc = getUserContext(request);
		if(uc != null){
			splCheckOut.setItemRight(uc.getItemRight());
		}
		splCheckAnalysisService.goJqGridDetail(page, splCheckOut);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"供应商结算分析详情_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
