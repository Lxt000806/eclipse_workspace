package com.house.home.web.controller.query;

import java.util.Date;
import java.util.List;
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
import com.house.home.service.query.DesignManRankingService;
@Controller
@RequestMapping("/admin/designManRanking")
public class DesignManRankingController extends BaseController{
	@Autowired
	private  DesignManRankingService designManRankingService;
	@Autowired
	private CustTypeService custTypeService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer, String dateFrom, String dateTo) throws Exception {
		customer.setDateFrom(DateUtil.parse(dateFrom, "yyyy-MM"));
		customer.setDateTo(DateUtil.addMonth(DateUtil.parse(dateTo, "yyyy-MM"))); //推迟一个月，方便计算
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		designManRankingService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//默认需要统计的客户类型
		List<CustType> listCustType = custTypeService.findByDefaultStatic(); 
		String defaultStaticCustType = "";
		for (CustType custType: listCustType) {
			defaultStaticCustType = defaultStaticCustType + "," + custType.getCode();
		}
		if (!defaultStaticCustType.equals("")) {
			defaultStaticCustType = defaultStaticCustType.substring(1);
		}
		return new ModelAndView("admin/query/designManRanking/designManRanking_list")
			.addObject("defaultStaticCustType", defaultStaticCustType);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer, String dateFrom, String dateTo){
		customer.setDateFrom(DateUtil.parse(dateFrom, "yyyy-MM"));
		customer.setDateTo(DateUtil.addMonth(DateUtil.parse(dateTo, "yyyy-MM"))); //推迟一个月，方便计算
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		designManRankingService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"设计师排名_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
