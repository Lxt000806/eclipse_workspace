package com.house.home.web.controller.query;

import java.text.SimpleDateFormat;
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
import com.house.home.entity.design.Customer;
import com.house.home.service.query.NetCustAnalyService;

@Controller
@RequestMapping("/admin/custServiceDispatchAnlay")
public class CustServiceDispatchAnlayController extends BaseController{
	
	@Autowired
	private NetCustAnalyService custServiceDispatchAnlayService;
	
	/**
	 * 客服本人派单分析
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
		customer.setCzybh(getUserContext(request).getCzybh());
		customer.setStatistcsMethod("1");
		custServiceDispatchAnlayService.findPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 客服本人派单分析-主页面
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
		customer.setCustType(custServiceDispatchAnlayService.getDefaultCustType());
		return new ModelAndView("admin/query/custServiceDispatchAnlay/custServiceDispatchAnlay_list").addObject("customer",customer);
	}

	/**
	 * 客服本人派单分析-输出excel
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
		custServiceDispatchAnlayService.findPageBySql(page,customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"客服本人派单分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
