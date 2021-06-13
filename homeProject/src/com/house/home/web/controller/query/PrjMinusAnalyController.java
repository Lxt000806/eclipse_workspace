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
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.basic.CustTypeService;
import com.house.home.service.query.PrjMinusAnalyService;

@Controller
@RequestMapping("/admin/prjMinusAnaly")
public class PrjMinusAnalyController extends BaseController{
	@Autowired
	private PrjMinusAnalyService prjMinusAnalyService;
	@Autowired
	private CustTypeService custTypeService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response,Customer customer) throws Exception{
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null && !"".equals(datefromString)){
			customer.setDateFrom(DateFormatString(datefromString) );
		}   
		if(customer.getDateTo()==null && !"".equals(datetoString)){
			customer.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		prjMinusAnalyService.findPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 明细查看——按项目经理、设计师分
	 * @author	created by zb
	 * @date	2019-9-11--上午11:12:11
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getDetailJqGrid(HttpServletRequest request ,
			HttpServletResponse response,Customer customer) {
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		prjMinusAnalyService.findDetailPageBySql(page,customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(DateUtil.endOfTheMonth(new Date()));
		CustType custType = new CustType();
		custType.setIsPartDecorate("0");
		custType.setType("1");
		customer.setCustType(custTypeService.getSelectCustType(custType));
		
		return new ModelAndView("admin/query/prjMinusAnaly/prjMinusAnaly_list").addObject("customer",customer);
	}
	/**
	 * 明细查看——按项目经理、设计师分
	 * @author	created by zb
	 * @date	2019-9-11--下午2:42:32
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	@ResponseBody
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		return new ModelAndView("admin/query/prjMinusAnaly/prjMinusAnaly_view")
			.addObject("customer",customer);
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
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null && !"".equals(datefromString)){
			customer.setDateFrom(DateFormatString(datefromString) );
		}   
		if(customer.getDateTo()==null && !"".equals(datetoString)){
			customer.setDateTo(DateFormatString(datetoString));
		}
		prjMinusAnalyService.findPageBySql(page,customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"结算工地减项分析_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
}
