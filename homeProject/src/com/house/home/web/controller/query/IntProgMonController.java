package com.house.home.web.controller.query;

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
import com.house.home.entity.project.ItemChg;
import com.house.home.service.query.IntProgMonService;

@Controller
@RequestMapping("/admin/IntProgMon")
public class IntProgMonController extends BaseController {
	@Autowired
	private IntProgMonService intProgMonService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if(customer.getDateTo()==null){
			customer.setDateTo(new Date());
		}
		intProgMonService.findPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goNotInstallJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getNotInstallJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		intProgMonService.findNotInstallPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goNotDeliverJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getNotDeliverJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		intProgMonService.findNotDeliverPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description 未下单明细分页查询
	 * @author	created by zb
	 * @date	2018-10-20--下午6:04:08
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goNotAppJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goNotAppJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		
		intProgMonService.findNotAppPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 未安排工人明细
	 * @author	created by zb
	 * @date	2019-9-6--下午6:10:23
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goNotSetWorkerJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getNotSetWorkerJqGrid(HttpServletRequest request,	
			HttpServletResponse response,Customer customer) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		intProgMonService.findNotSetWorkerPageBySql(page, customer);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, Customer customer) throws Exception {
		customer.setDateTo(new Date());
		return new ModelAndView("admin/query/intProgMon/intProgMon_list")
				.addObject("customer", customer);
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Customer customer,String countType){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		String titleName="";
		if("1".equals(countType)){
			intProgMonService.findPageBySql(page, customer);
			if("CG".equals(customer.getItemType2())){
				titleName="集成进度跟踪_橱柜未出货明细";
			}else{
				titleName="集成进度跟踪_衣柜未出货明细";
			}
		}else if("2".equals(countType)){
			intProgMonService.findNotInstallPageBySql(page, customer);
			if("CG".equals(customer.getItemType2())){
				titleName="集成进度跟踪_橱柜未安装明细";
			}else{
				titleName="集成进度跟踪_衣柜未安装明细";
			}
		}else if("3".equals(countType)){
			intProgMonService.findNotDeliverPageBySql(page, customer);
			if("CG".equals(customer.getItemType2())){
				titleName="集成进度跟踪_橱柜未交付明细";
			}else{
				titleName="集成进度跟踪_衣柜未交付明细";
			}
		}else if("4".equals(countType)){
			intProgMonService.findNotAppPageBySql(page, customer);
			if("CG".equals(customer.getItemType2())){
				titleName="集成进度跟踪_橱柜未下单明细";
			}else{
				titleName="集成进度跟踪_衣柜未下单明细";
			}
		} else if ("5".equals(countType)) {
            intProgMonService.findNotSetWorkerPageBySql(page, customer);
            
            if ("CG".equals(customer.getItemType2())) {
                titleName = "集成进度跟踪_橱柜未安排工人明细";
            } else {
                titleName = "集成进度跟踪_衣柜未安排工人明细";
            }
        }
		
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				titleName+"_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
