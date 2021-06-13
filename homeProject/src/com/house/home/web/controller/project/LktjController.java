package com.house.home.web.controller.project;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.service.design.CustomerService;
import com.house.home.service.project.PrjProgCheckService;

@Controller
@RequestMapping("/admin/lktj")
public class LktjController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LktjController.class);

	@Autowired
	private PrjProgCheckService prjProgCheckService;
	
	@Autowired
	private CustomerService customerService;

	
	/**
	 * 来客统计列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Customer customer=new Customer();
		customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
		customer.setEndDate(DateUtil.endOfTheMonth(new Date()));
		customer.setCustType(customerService.getIsDefaultStatic());
		
		return new ModelAndView("admin/query/lktj/lktj_list").addObject("customer", customer).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
	
	/**
	 * 来客统计    统计方式按明细	//
	 * @throws Exception
	 */
	@RequestMapping("/lktjMx")
	@ResponseBody
	public WebPage<Map<String,Object>> prjProgCheckMx(HttpServletRequest request,
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (customer.getBeginDate()==null){
			customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			customer.setEndDate(new Date());	
		}
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		if("1".equals(customer.getRole())){
			customer.setRole("00");
		}else if ("2".equals(customer.getRole())){
			customer.setRole("01");
		}
		customer.setCzybh(this.getUserContext(request).getCzybh());
		customer.setBeginDate(new java.sql.Timestamp(customer.getBeginDate().getTime()));
		customer.setEndDate(new java.sql.Timestamp(customer.getEndDate().getTime()));
		customerService.findPageBySqlTJFS(page, customer,orderBy,direction);//TJFS 统计方式
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/doExcelchecklktj")
	public void doExcelchecklktj(HttpServletRequest request, 
			HttpServletResponse response, Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		if (customer.getBeginDate()==null){
			customer.setBeginDate(DateUtil.startOfTheMonth(new Date()));
			customer.setEndDate(new Date());	
		}
		String orderBy="";
		String direction="";
		if(StringUtils.isNotBlank(request.getParameter("sidx"))){
			orderBy=request.getParameter("sidx");
		}
		if(StringUtils.isNotBlank(page.getPageOrder())){
			direction=page.getPageOrder();
		}
		if("1".equals(customer.getRole())){
			customer.setRole("00");
		}else if ("2".equals(customer.getRole())){
			customer.setRole("01");
		}
		customer.setCzybh(this.getUserContext(request).getCzybh());
		customerService.findPageBySqlTJFS(page, customer, orderBy, direction);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"来客统计_"+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss"), columnList, titleList, sumList);
	}
	
}
