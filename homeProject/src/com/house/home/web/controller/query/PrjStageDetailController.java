package com.house.home.web.controller.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjStageDetailService;
import com.house.home.service.query.PrjStageProgAnalyService;

@Controller
@RequestMapping("/admin/prjStageDetail")
public class PrjStageDetailController extends BaseController{
	
	@Autowired
	private PrjStageDetailService prjStageDetailService;
	
	/**
	 * 工程部阶段进度明细
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjStageDetailService.findPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 工程部阶段进度分析分析-主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		Customer customer=new Customer();
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(new Date());
		return new ModelAndView("admin/query/prjStageDetail/prjStageDetail_list").addObject("customer",customer);
	}
	
	/**
	 * 工程部阶段进度分析分析-输出excel
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response
			,Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		prjStageDetailService.findPageBySql(page,customer);
		getExcelList(request);
		
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工程部阶段进度明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
