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
import com.house.home.service.query.CljsfxService;

@Controller
@RequestMapping("/admin/cljsfx")
public class CljsfxController extends BaseController{
	
	@Autowired
	private CljsfxService itemSettlementService;
	
	/**
	 * 材料结算分析-主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		Customer customer=new Customer();
		customer.setItemType1("ZC");
		customer.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setDateTo(new Date());
		return new ModelAndView("admin/query/cljsfx/cljsfx_list").addObject("customer",customer);
	}
	
	/**
	 *材料结算分析查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,Customer customer){
		logger.debug("跳转到查看材料结算分析页面");		
		return new ModelAndView("admin/query/cljsfx/cljsfx_view")
			.addObject("customer",customer);
	}
	
	/**
	 * 材料结算分析—查看页面
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetail(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemSettlementService.findPageBySqlDetail(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 材料结算分析
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);		
		itemSettlementService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * 材料结算分析-输出excel
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response
			,Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		itemSettlementService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料结算分析表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
