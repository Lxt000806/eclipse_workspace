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
import com.house.home.service.query.itemCheckAnalysisService;
@Controller
@RequestMapping("/admin/itemCheckAnalysis")
public class itemCheckAnalysisController extends BaseController {
	@Autowired
	private itemCheckAnalysisService itemCheckAnalysisService;
	/**
	 * 查询itemCheckAnalysisJqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setItemType12("ZC".equals(customer.getItemType1())?customer.getItemType12ZC():customer.getItemType12RZ());
		itemCheckAnalysisService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.getPreMonth(2, 21));
		customer.setDateTo(DateUtil.getPreMonth(1, 20));	
		return new ModelAndView("admin/query/itemCheckAnalysis/itemCheckAnalysis_list");
	}
}
