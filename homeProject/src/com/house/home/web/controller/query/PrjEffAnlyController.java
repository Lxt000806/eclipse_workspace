package com.house.home.web.controller.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.service.query.PrjEffAnlyService;

@Controller
@RequestMapping("/admin/PrjEffAnly")
public class PrjEffAnlyController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrjEffAnlyController.class);

	@Autowired
	private PrjEffAnlyService prjEffAnlyService;
	
	//-----表格查询相关

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request, HttpServletResponse response, Date dateFrom, 
			Date dateTo, String department2s, String custTypes, String sType, String builderCode) throws Exception {//新增项目名称查询字段（builderCode） ADD By zb
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		List<Map<String, Object>> list = prjEffAnlyService.goJqGrid(dateFrom, dateTo, department2s, custTypes, sType, builderCode);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/goJqGridView")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridView(HttpServletRequest request, HttpServletResponse response, Date dateFrom, 
			Date dateTo, String department2s, String custTypes, String dept2Code, String custType, String constructType, 
			String builderCode) throws Exception {//新增项目名称查询字段（builderCode） ADD By zb
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prjEffAnlyService.goJqGridView(page, dateFrom, dateTo, department2s, custTypes, dept2Code, custType, constructType, builderCode);
		return new WebPage<Map<String,Object>>(page);
	}
	
	//-----页面跳转相关

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到工地效率分析首页");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateFrom", DateUtil.startOfTheMonth(new Date()));
		map.put("dateTo", DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/query/prjEffAnly/prjEffAnly_list").addObject("data", map);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, Date dateFrom, Date dateTo,
			String department2s, String custTypes, String dept2Code, String custType, String constructType, 
			String builderCode) throws Exception {//新增项目名称查询字段（builderCode） ADD By zb
		logger.debug("跳转到工地效率分析详情页");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateFrom", dateFrom);
		map.put("dateTo", dateTo);
		map.put("department2s", department2s);
		map.put("custTypes", custTypes);
		map.put("custType", custType);
		map.put("constructType", constructType);
		map.put("dept2Code", dept2Code);
		map.put("builderCode", builderCode);
		return new ModelAndView("admin/query/prjEffAnly/prjEffAnly_view").addObject("data", map);
	}
	//-----操作相关
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, Date dateFrom, Date dateTo,
						String department2s, String custTypes, String sType, String builderCode){
		List<Map<String, Object>> list = prjEffAnlyService.goJqGrid(dateFrom, dateTo, department2s, custTypes, sType, builderCode);//新增项目名称查询字段（builderCode） ADD By zb
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, list,
				"工地效率分析-"+("1".equals(sType)?"工程部":"工地类型")+"-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
}
