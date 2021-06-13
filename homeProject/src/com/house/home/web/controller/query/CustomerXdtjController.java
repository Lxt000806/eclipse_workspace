package com.house.home.web.controller.query;

import java.util.Date;
import java.util.List;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.bean.query.CustomerXdtjBean_Detail;
import com.house.home.bean.query.CustomerXdtjBean_GroupByBuilder;
import com.house.home.bean.query.CustomerXdtjBean_GroupByCustType;
import com.house.home.bean.query.CustomerXdtjBean_GroupByDept1;
import com.house.home.bean.query.CustomerXdtjBean_GroupByDept2;
import com.house.home.bean.query.CustomerXdtjBean_GroupByTeam;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.query.CustomerXdtjService;

@Controller
@RequestMapping("/admin/customerXdtj")
public class CustomerXdtjController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerXdtjController.class);
	
	@Autowired
	private CustTypeService custTypeService;
	
	@Autowired
	private CustomerXdtjService customerXdtjService;
	
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
		customer.setCzybh(this.getUserContext(request).getCzybh());
		customerXdtjService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_GroupByDept1")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid_GroupByDept1(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		customer.setCzybh(this.getUserContext(request).getCzybh());
		customerXdtjService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 下定签单统计主界面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		
		if (customer.getCustType() == null){
			List<CustType> listCustType = custTypeService.findByDefaultStatic(); //默认需要统计的客户类型
			String defaultStaticCustType = "";
			for (CustType custType: listCustType) {
				defaultStaticCustType = defaultStaticCustType + "," + custType.getCode();
			}
			if (!defaultStaticCustType.equals("")) {
				defaultStaticCustType = defaultStaticCustType.substring(1);
			}
			customer.setCustType(defaultStaticCustType);
		}
		
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));
		
		return new ModelAndView("admin/query/customerXdtj/customerXdtj_list").addObject("customer", customer);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		customer.setCzybh(this.getUserContext(request).getCzybh());
		customerXdtjService.findPageBySql(page, customer);
//		List<?> list = null;
//		if (customer.getStatistcsMethod().equalsIgnoreCase("1")) {
//			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerXdtjBean_Detail.class);
//		} else if (customer.getStatistcsMethod().equalsIgnoreCase("2")) {
//			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerXdtjBean_GroupByDept1.class);
//		} else if (customer.getStatistcsMethod().equalsIgnoreCase("3")) {
//			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerXdtjBean_GroupByDept2.class);
//		} else if (customer.getStatistcsMethod().equalsIgnoreCase("4")) {
//			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerXdtjBean_GroupByTeam.class);
//		} else if (customer.getStatistcsMethod().equalsIgnoreCase("5")) {
//			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerXdtjBean_GroupByBuilder.class);
//		} else if (customer.getStatistcsMethod().equalsIgnoreCase("6")) {
//			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustomerXdtjBean_GroupByCustType.class);
//		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"下定签单统计_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
}
