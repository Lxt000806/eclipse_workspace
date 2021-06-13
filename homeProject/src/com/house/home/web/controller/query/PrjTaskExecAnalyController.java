package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjTaskExecAnalyService;

@Controller
@RequestMapping("/admin/prjTaskExecAnaly")
public class PrjTaskExecAnalyController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(PrjTaskExecAnalyController.class);

	@Autowired
	private PrjTaskExecAnalyService prjTaskExecAnalyService;
	@Autowired
	private PrjDelayAnalyController prjDelayAnalyController;
	

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("跳转到施工任务执行分析主页");
		Map<String, Object> map = new HashMap<String, Object>();
		UserContext uc = getUserContext(request);
		Employee e = prjTaskExecAnalyService.get(Employee.class, uc.getEmnum());
		map.put("dateFrom", DateUtil.addDate(new Date(), -30));
		map.put("dateTo", DateUtil.getNow());
		map.put("department1", e.getDepartment1());
		map.put("department2", e.getDepartment2());
		map.put("custRight", this.getUserContext(request).getCustRight());
		return new ModelAndView("admin/query/prjTaskExecAnaly/prjTaskExecAnaly_list").addObject("data", map);
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request, HttpServletResponse response, Date dateFrom, Date dateTo, String department1, String department2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjTaskExecAnalyService.goJqGrid(page, dateFrom, dateTo, department1, department2);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, String no, Date dateFrom, Date dateTo, String rcvCZY) throws Exception {
		logger.debug("跳转到查看页面");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dateFromView", DateUtil.DateToString(dateFrom, "yyyy-MM-dd"));
		map.put("dateToView", DateUtil.DateToString(dateTo, "yyyy-MM-dd"));
		map.put("rcvCZY", rcvCZY);
		return new ModelAndView("admin/query/prjTaskExecAnaly/prjTaskExecAnaly_view").addObject("data", map);
	}	
	
	@RequestMapping("/goJqGridView")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridView(HttpServletRequest request, HttpServletResponse response,
													 Date dateFrom, Date dateTo, String rcvCZY) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjTaskExecAnalyService.goJqGridView(page, dateFrom, dateTo, rcvCZY);
		return new WebPage<Map<String,Object>>(page);
	}	
	
	@RequestMapping("/goJqGrid_prjDelayNoTrrigerTask")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_prjDelayNoTrrigerTask(HttpServletRequest request, HttpServletResponse response,
													 Date dateFrom, Date dateTo, String rcvCZY) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjTaskExecAnalyService.goJqGrid_prjDelayNoTrrigerTask(page, dateFrom, dateTo, rcvCZY);
		return new WebPage<Map<String,Object>>(page);
	}	

	@RequestMapping("/goPrjProgView")
	public ModelAndView goPrjProgView(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		return prjDelayAnalyController.goView(request, response, customer);
	}
}
