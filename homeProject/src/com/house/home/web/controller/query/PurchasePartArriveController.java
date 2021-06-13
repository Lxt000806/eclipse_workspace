package com.house.home.web.controller.query;

import java.util.Date;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.bean.WebPage;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemReq;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.query.PurchasePartArriveService;

@Controller
@RequestMapping("/admin/purchasePartArrive")
public class PurchasePartArriveController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PurchasePartArriveController.class);

	@Autowired
	private PurchasePartArriveService purchasePartArriveService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, Purchase purchase) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchasePartArriveService.findPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}	

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response,Purchase purchase) throws Exception {
		UserContext uc=this.getUserContext(request);
		purchase.setCostRight(uc.getCostRight().trim());
		purchase.setArriveDate(new Date());
		return new ModelAndView("admin/query/purchasePartArrive/purchasePartArrive_list");
	}

	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, Purchase purchase){
		UserContext uc=this.getUserContext(request);
		purchase.setCostRight(uc.getCostRight().trim());
		return new ModelAndView("admin/query/purchasePartArrive/purchasePartArrive_detail")
		.addObject("purchase",purchase)
		.addObject("statistcsMethod", purchase.getStatistcsMethod())
		.addObject("itemType2", purchase.getItemType2())
		.addObject("costRight", purchase.getCostRight());
	}
	
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGridDetail(HttpServletRequest request,
			HttpServletResponse response, Purchase purchase) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchasePartArriveService.findDetailPageBySql(page, purchase);
		return new WebPage<Map<String,Object>>(page);
	}	


	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		purchasePartArriveService.findPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"采购单部分到货统计_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doDetailExcel")
	public void doDetailExcel(HttpServletRequest request, 
			HttpServletResponse response, Purchase purchase){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		purchasePartArriveService.findDetailPageBySql(page, purchase);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"采购单部分到货统计明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
