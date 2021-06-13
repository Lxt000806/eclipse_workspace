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
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.basic.CzybmService;
import com.house.home.service.query.CustomerCljsfxService;

@Controller
@RequestMapping("/admin/customerCljsfx")
public class CustomerCljsfxController extends BaseController { 
	@Autowired
	private CustomerCljsfxService customerCljsfxService;
	@Autowired
	private CzybmService czybmService;
	@Autowired
	private CustTypeService custTypeService;
	
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
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		//customer.setCustType("1"); //只统计1.家装客户
		customerCljsfxService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * customerCljsfx列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		CustType custType = new CustType();
		custType.setType("1");
		custType.setIsAddAllInfo("1");
		String needCode = custTypeService.getNeedCode(custType);
		customer.setCustType(needCode); //非独立销售的清单客户 modify by zb on 20190420
//		customer.setCustType("1"); //默认统计1.家装客户
		customer.setCustCheckDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setCustCheckDateTo(new Date());
		
		// 20200415 mark by xzp 统一用czybmService.hasGNQXByCzybh
//		boolean canViewProfit = czybmService.hasAuthByCzybh(this.getUserContext(request).getCzybh(), 638); //638.毛利查看权限
		boolean canViewProfit=czybmService.hasGNQXByCzybh(this.getUserContext(request).getCzybh(), "0519", "毛利查看");
		return new ModelAndView("admin/query/customerCljsfx/customerCljsfx_list")
			.addObject("Customer", customer)
			.addObject("canViewProfit", canViewProfit);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		//customer.setCustType("1"); //只统计1.家装客户
		customerCljsfxService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料结算分析-主材明细_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
		
}
