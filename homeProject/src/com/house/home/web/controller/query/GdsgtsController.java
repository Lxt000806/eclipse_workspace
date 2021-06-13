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
import com.house.home.service.query.GdsgtsService;

@Controller
@RequestMapping("/admin/gdsgts")
public class GdsgtsController extends BaseController{
	@Autowired
	private GdsgtsService gdsgtsService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response,Customer customer,String isSign ) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		gdsgtsService.findPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		Customer customer = new Customer();
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));
		
		return new ModelAndView("admin/query/gdsgts/gdsgts_list").addObject("customer", customer);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer,String isSign){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		gdsgtsService.findPageBySql(page,customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工地施工天数分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
