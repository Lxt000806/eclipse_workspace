package com.house.home.web.controller.project;

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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.Worker;
import com.house.home.service.project.WaterCtrlRepService;

@RequestMapping("/admin/waterCtrlRep")
@Controller
public class WaterCtrlRepController extends BaseController{
	@Autowired 
	private WaterCtrlRepService waterCtrlRepService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response ,CustWorker custWorker) throws Exception {
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(custWorker.getDateFrom()==null){
			custWorker.setDateFrom(DateFormatString(datefromString));
		}
		if(custWorker.getDateTo()==null){
			custWorker.setDateTo(DateFormatString(datetoString));
		}
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		waterCtrlRepService.findPageBySql(page,custWorker);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,CustWorker custWorker) throws Exception {
		
		custWorker.setDateFrom(DateUtil.startOfTheMonth(DateUtil.addMonth(new Date(), -1)));
		custWorker.setDateTo(DateUtil.endOfTheMonth(DateUtil.addMonth(new Date(), -1)));
		return new ModelAndView("admin/project/waterCtrlRep/waterCtrlRep_list");
		
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CustWorker custWorker){
		Page<Map<String, Object>>page= this.newPage(request);
		String datefromString = request.getParameter("dateFrom");
		String datetoString = request.getParameter("dateTo");
		if(custWorker.getDateFrom()==null){
			custWorker.setDateFrom(DateFormatString(datefromString));
		}
		if(custWorker.getDateTo()==null){
			custWorker.setDateTo(DateFormatString(datetoString));
		}
		page.setPageSize(-1);
		waterCtrlRepService.findPageBySql(page, custWorker);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"防水发包奖惩报表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
