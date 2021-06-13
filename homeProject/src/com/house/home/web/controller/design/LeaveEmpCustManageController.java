package com.house.home.web.controller.design;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.LeaveEmpCustManageService;

@Controller
@RequestMapping("/admin/leaveEmpCustManage")
public class LeaveEmpCustManageController extends BaseController{
	
	@Autowired
	private LeaveEmpCustManageService leaveEmpCustManageService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		UserContext uc =this.getUserContext(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null && !"".equals(datefromString) && datefromString!= null){
			customer.setDateFrom(DateFormatString(datefromString) );
		}   
		if(customer.getDateTo()==null && !"".equals(datetoString) && datetoString!= null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		leaveEmpCustManageService.findPageBySql(page, customer,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		
		Customer customer = new Customer();
		customer.setDateFrom(DateUtil.addMonth(new Date(),-1));
		customer.setDateTo(new Date());
		
		return new ModelAndView("admin/design/leaveEmpCustManage/leaveEmpCustManage_list").addObject("customer", customer);
	}
	
	@RequestMapping("/goUpdateBusinessMan")
	public ModelAndView goUpdateBusinessMan(HttpServletRequest request ,
			HttpServletResponse response,Customer customer ) throws Exception{
		
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null && !"".equals(datefromString) && datefromString!= null){
			customer.setDateFrom(DateFormatString(datefromString) );
		}   
		if(customer.getDateTo()==null && !"".equals(datetoString) && datetoString!= null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		return new ModelAndView("admin/design/leaveEmpCustManage/leaveEmpCustManage_updateBusMan").addObject("customer", customer);
	}
	
	@RequestMapping("/goMultiUpdate")
	public ModelAndView goMultiUpdate(HttpServletRequest request ,
			HttpServletResponse response ) throws Exception{
		
		Customer customer = new Customer();
		
		return new ModelAndView("admin/design/leaveEmpCustManage/leaveEmpCustManage_updateStakeholder").addObject("customer", customer);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/doChgStakeholder")
	public void doChgStakeholder(HttpServletRequest request, HttpServletResponse response){
		logger.debug("批量修改开始");
		try{
			String dataString = request.getParameter("dataStering");
			String showType = request.getParameter("showType");
			String stakeholder = request.getParameter("stakeholder");
			List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(dataString);
			
			Result result= leaveEmpCustManageService.doChgStakeholder(list, showType, stakeholder, this.getUserContext(request).getCzybh());
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}
			
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(customer.getDateFrom()==null){
			customer.setDateFrom(DateFormatString(datefromString));
		}
		if(customer.getDateTo()==null){
			customer.setDateTo(DateFormatString(datetoString));
		}
		UserContext uc = this.getUserContext(request);
		page.setPageSize(-1);
		leaveEmpCustManageService.findPageBySql(page,customer,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"离职人员客户管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
