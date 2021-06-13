package com.house.home.web.controller.query;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.GdysfxSerivce;

@Controller
@RequestMapping("/admin/gdysfx")
public class GdysfxController extends BaseController {
	
	@Autowired
	private GdysfxSerivce gdysfxSerivce;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response,Customer customer) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(customer.getDateFrom()==null && !"".equals(datefromString)){
			customer.setDateFrom(DateFormatString(datefromString) );
		}   
		if(customer.getDateTo()==null && !"".equals(datetoString)){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		gdysfxSerivce.findPageBySql(page,customer,orderBy,direction);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));
		
		return new ModelAndView("admin/query/gdysfx/gdysfx_list").addObject("customer",customer);
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
