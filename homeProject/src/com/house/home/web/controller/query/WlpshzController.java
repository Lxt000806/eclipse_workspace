package com.house.home.web.controller.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Item;
import com.house.home.entity.project.ItemChg;
import com.house.home.service.query.WlpshzService;

@Controller
@RequestMapping("/admin/wlpshz")
public class WlpshzController extends BaseController {
	
	@Autowired
	private WlpshzService wlpshzService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Item item) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		item.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		item.setDateTo(DateFormatString(request.getParameter("dateTo")));
		wlpshzService.findPageBySql(page, item);
		System.out.println(page.getResult());
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goTileJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getTileJqGrid(HttpServletRequest request,	
			HttpServletResponse response,String date) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wlpshzService.findTilePageBySql(page, date);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goToiletJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getToiletJqGrid(HttpServletRequest request,	
			HttpServletResponse response,String date) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wlpshzService.findToiletPageBySql(page, date);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goFloorJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getFloorJqGrid(HttpServletRequest request,	
			HttpServletResponse response,String date) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wlpshzService.findFloorPageBySql(page, date);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response,Item item) throws Exception {
		logger.debug("主材物流配送");
		item.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		item.setDateTo(new Date());
		return new ModelAndView("admin/query/wlpshz/wlpshz_list").addObject("item",item);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,String date) throws Exception {
		logger.debug("主材物流配送明细");
		return new ModelAndView("admin/query/wlpshz/wlpshz_detail").addObject("date",date);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Item item){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		item.setDateFrom(DateFormatString(request.getParameter("dateFrom")));
		item.setDateTo(DateFormatString(request.getParameter("dateTo")));
		wlpshzService.findPageBySql(page, item);
		getExcelList(request);
		List<String> list = new ArrayList<String>();
		String[] sTitles=new String[]{"瓷砖|","地板|","卫浴|"};
		int sTitlesIndex=0;
		if (titleList!=null && titleList.size()>0){
			for (String str : titleList){
				if ("24".equals(str) || "48".equals(str) || "72".equals(str)||"超时".equals(str)){
					str = sTitles[sTitlesIndex] + str;
					System.out.println(str);
					if("瓷砖|超时".equals(str)||"地板|超时".equals(str)){
						sTitlesIndex++;
					}
				}
				list.add(str);
			}
			sTitlesIndex++;
			titleList = list;
		}
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"主材物流配送汇总_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
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
