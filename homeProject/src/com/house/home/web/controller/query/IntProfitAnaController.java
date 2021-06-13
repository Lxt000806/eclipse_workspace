package com.house.home.web.controller.query;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.IntProfitAnaService;

@Controller
@RequestMapping("/admin/intProfitAna")
public class IntProfitAnaController extends BaseController { 
	@Autowired
	private IntProfitAnaService intProfitAnaService;
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intProfitAnaService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}

	/**
	 * intProfitAna列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		customer.setSendDateFrom(DateUtil.startOfTheMonth(new Date()));
		customer.setSendDateTo(DateUtil.endOfTheMonth(new Date()));	
		return new ModelAndView("admin/query/intProfitAna/intProfitAna_list").addObject("Customer", customer);
	}
	/**
	 * intProfitAna列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, String custCode) throws Exception {
		Map<String, Object>map=intProfitAnaService.findDetailHead(custCode);
		Map<String, Object>newMap=new HashMap<String, Object>();//新的map存放处理掉末尾多余0以及利率改成百分比
		for (String key:map.keySet()) {
			if(!("intPerf").equals(key)&&!("cupPerf").equals(key)&&!("custcode").equals(key)&&!("totalPerf").equals(key)){
				newMap.put(key, map.get(key).toString().substring(0,map.get(key).toString().lastIndexOf(".")+3));
			}else if(("intPerf").equals(key)||("cupPerf").equals(key)||("totalPerf").equals(key)){
				newMap.put(key, new  DecimalFormat("##0.00").format(Float.parseFloat(map.get(key).toString())*100)+"%");
			}else if(("custcode").equals(key)){
				newMap.put(key, map.get(key));
			}
		}
		
		Xtcs intInsCalTyp = intProfitAnaService.get(Xtcs.class, "IntInsCalTyp");
		Xtcs cupInsCalTyp = intProfitAnaService.get(Xtcs.class, "CupInsCalTyp");
		
		return new ModelAndView("admin/query/intProfitAna/intProfitAna_view").addObject("map", newMap)
				.addObject("intInsCalTyp", intInsCalTyp.getQz()).addObject("cupInsCalTyp", cupInsCalTyp.getQz());
	}
	/**
	 * 查看明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewDetail")
	public ModelAndView goViewDetail(HttpServletRequest request,
			HttpServletResponse response, String appNo) throws Exception {
		return new ModelAndView("admin/query/intProfitAna/intProfitAna_viewDetail").addObject("appNo", appNo);
	}
	/**
	 *导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response,Customer customer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		intProfitAnaService.findPageBySql(page, customer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"集成利润率分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
