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
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjCtrlCheckAnalysisService;

@Controller
@RequestMapping("/admin/prjCtrlCheckAnalysis")
public class PrjCtrlCheckAnalysisController extends BaseController { 
	@Autowired
	PrjCtrlCheckAnalysisService prjCtrlCheckAnalysisService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param giftApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjCtrlCheckAnalysisService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		prjCtrlCheckAnalysisService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地发包结算分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * PrjManCheck列表
	 * @param request
	 * @param response 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, 
			HttpServletResponse response,  Customer customer) throws Exception {
		customer.setConfirmDateFrom(DateUtil.startOfTheMonth(new Date())); 
		customer.setConfirmDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/prjCtrlCheckAnalysis/prjCtrlCheckAnalysis_list").addObject("customer",customer);	
	}
	
}
