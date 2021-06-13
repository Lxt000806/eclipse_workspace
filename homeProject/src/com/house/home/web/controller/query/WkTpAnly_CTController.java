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
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.WorkType12;
import com.house.home.service.query.WkTpAnly_CTService;

@Controller
@RequestMapping("/admin/WkTpAnly_CT")
public class WkTpAnly_CTController extends BaseController{
	
	@Autowired
	private WkTpAnly_CTService wkTpAnly_CTService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,WorkType12 workType12) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		this.wkTpAnly_CTService.findPageBySql(page,workType12);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,WorkType12 workType12,String layOut,String area) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(StringUtils.isNotBlank(request.getParameter("dateFrom"))&&workType12.getDateFrom()==null){
			workType12.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		}
		if(StringUtils.isNotBlank(request.getParameter("dateTo"))&&workType12.getDateTo()==null){
			workType12.setDateTo(DateFormatString(request.getParameter("dateTo")));
		}
		this.wkTpAnly_CTService.findDetailPageBySql(page,workType12,layOut,area);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, WorkType12 workType12) throws Exception {
		workType12.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		workType12.setDateTo(DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/query/wkTpAnly_CT/wkTpAnly_CT_list").addObject("workType12", workType12);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, WorkType12 workType12,String layOut,String area) throws Exception {
		if(StringUtils.isNotBlank(workType12.getCode())){
			workType12.setCode(workType12.getCode().trim());
		}
		return new ModelAndView("admin/query/wkTpAnly_CT/wkTpAnly_CT_view")
		.addObject("workType12", workType12)
		.addObject("area", area).addObject("layOut", layOut);
	}
	
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			WorkType12 workType12){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		wkTpAnly_CTService.findPageBySql(page, workType12);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工种施工分析_按工地类型"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	public  Date DateFormatString(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			date = null;
		}
        return date;
	}
}
