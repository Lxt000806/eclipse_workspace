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
import com.house.home.service.basic.XtcsService;
import com.house.home.service.query.CustomerClqdtjService;

@Controller
@RequestMapping("/admin/customerClqdtj")
public class CustomerClqdtjController extends BaseController { 
	@Autowired
	private CustomerClqdtjService customerClqdtjService;
	@Autowired
	private XtcsService xtcsService;
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
//		customer.setCustType("1"); //只统计1.家装客户
		customerClqdtjService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * customerClqdtj列表
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
		customer.setSignDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setSignDateTo(new Date());
		
		String cmpCode = xtcsService.getQzById("CmpnyCode").trim(); //客户代码 01.福州 02.厦门 03.长乐
		
		return new ModelAndView("admin/query/customerClqdtj/customerClqdtj_list")
				.addObject("customer", customer)
				.addObject("cmpCode", cmpCode);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		//customer.setCustType("1"); //只统计1.家装客户 remove by zzr 2019/03/08
		customerClqdtjService.findPageBySql(page, customer);
		
//		String cmpCode = xtcsService.getQzById("CmpnyCode").trim(); //客户代码 01.福州 02.厦门 03.长乐
//		if (cmpCode == "02") {
//			getExcelList(request);
//			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
//					"材料签单统计-主材明细_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList);
//		} else {
//			getExcelList(request);
//			ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
//					"材料签单统计-主材明细_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList);
//		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料签单统计-主材明细_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}	
}
