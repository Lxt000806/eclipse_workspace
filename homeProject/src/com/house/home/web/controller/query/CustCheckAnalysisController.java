package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.bean.query.CustCheckAnalysisBean_GroupByDept1;
import com.house.home.bean.query.CustCheckAnalysisBean_GroupByDept2;
import com.house.home.bean.query.CustCheckAnalysisBean_GrouyByCustType;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.query.CustCheckAnalysisService;
@Controller
@RequestMapping("/admin/custCheckAnalysis")
public class CustCheckAnalysisController extends BaseController {
	@Autowired
	private CustTypeService custTypeService;
	@Autowired
	private CustCheckAnalysisService custCheckAnalysisService;
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
		custCheckAnalysisService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
		
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));	
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
		return new ModelAndView("admin/query/custCheckAnalysis/custCheckAnalysis_list");
	}
	@RequestMapping("/doExcel")
	public  void doExcel(HttpServletRequest request,HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(10000);
		custCheckAnalysisService.findPageBySql(page, customer);
		List<?> list = null;
		if (customer.getStatistcsMethod().equalsIgnoreCase("1")) {
			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustCheckAnalysisBean_GroupByDept1.class);
		} else if (customer.getStatistcsMethod().equalsIgnoreCase("2")) {
			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustCheckAnalysisBean_GroupByDept2.class);
		} else if (customer.getStatistcsMethod().equalsIgnoreCase("3")) {
			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustCheckAnalysisBean_GrouyByCustType.class);
		} else if (customer.getStatistcsMethod().equalsIgnoreCase("4")) {
			list = BeanConvertUtil.mapToBeanList(page.getResult(), CustCheckAnalysisBean_GrouyByCustType.class);
		}
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, list,
				"客户结算分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Date dateFrom, Date dateTo, String role,
								String statistcsMethod, String department1, String department2, String custtype, String constructType, String isContainSoft) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
   	  	map.put("dateFrom", dateFrom);
   	  	map.put("dateTo", dateTo);
   	  	map.put("role", role);
   	  	map.put("statistcsMethod", statistcsMethod);
   	  	map.put("department1", department1);
   	  	map.put("department2", department2);
   	  	map.put("custtype", custtype);
   	  	map.put("constructType", constructType);
   	  	map.put("isContainSoft", isContainSoft);
		return new ModelAndView("admin/query/custCheckAnalysis/custCheckAnalysis_view").addObject("data", map);
	}

	@RequestMapping("/goJqGridCheckDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridCheckDetail(HttpServletRequest request,	HttpServletResponse response, Date dateFrom, Date dateTo, String role,
															String statistcsMethod, String department1, String department2, String custtype, String constructType, String isContainSoft) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custCheckAnalysisService.goJqGridCheckDetail(page, dateFrom, dateTo, role, statistcsMethod, department1, department2, custtype, constructType, isContainSoft);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridReturnDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridReturnDetail(HttpServletRequest request,	HttpServletResponse response, Date dateFrom, Date dateTo, String role,
															String statistcsMethod, String department1, String department2, String custtype, String constructType, String isContainSoft, int returnFlag) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custCheckAnalysisService.goJqGridReturnDetail(page, dateFrom, dateTo, role, statistcsMethod, department1, department2, custtype, constructType, isContainSoft, returnFlag);
		return new WebPage<Map<String,Object>>(page);
	}
}
