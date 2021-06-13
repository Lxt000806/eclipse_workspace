package com.house.home.web.controller.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.house.home.service.query.QdhbfxService;

@Controller
@RequestMapping("/admin/qdhbfx")
public class QdhbfxController extends BaseController{
	
	@Autowired
	private QdhbfxService qdhbfxService;
	@Autowired
	private CustTypeService custTypeService;
	
	/**
	 * 签单环比分析
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Customer customer,String role,String serch) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		String dateFromString=request.getParameter("dateFrom");
		String dateToString=request.getParameter("dateTo");
		if(StringUtils.isBlank(customer.getExpired())){
			customer.setExpired("F");
		}
		if("T".equals(customer.getExpired())){
			customer.setExpired("T,F");
		}
		customer.setDateFrom(DateFormatString(dateFromString));
		customer.setDateTo(DateFormatString(dateToString));
		qdhbfxService.findPageBySql(page,customer,role );
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 签单环比分析-主页面
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
		List<CustType> listCustType = custTypeService.findByDefaultStatic(); //默认需要统计的客户类型
		String defaultStaticCustType = "";
		for (CustType custType: listCustType) {
			defaultStaticCustType = defaultStaticCustType + "," + custType.getCode();
		}
		if (!defaultStaticCustType.equals("")) {
			defaultStaticCustType = defaultStaticCustType.substring(1);
		}
		customer.setCustType(defaultStaticCustType);
		
		return new ModelAndView("admin/query/qdhbfx/qdhbfx_list").addObject("customer",customer);
	}

	/**
	 * 签单环比分析-输出excel
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response
			,Customer customer,String role,String serch){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		String dateFromString=request.getParameter("dateFrom");
		String dateToString=request.getParameter("dateTo");
		customer.setDateFrom(DateFormatString(dateFromString));
		customer.setDateTo(DateFormatString(dateToString));
		if(StringUtils.isBlank(customer.getExpired())){
			customer.setExpired("F");
		}
		if("T".equals(customer.getExpired())){
			customer.setExpired("T,F");
		}
		qdhbfxService.findPageBySql(page,customer,role );
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"签单环比分析表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public  Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = new Date();
		}
        return date;
	}
	
	
}
