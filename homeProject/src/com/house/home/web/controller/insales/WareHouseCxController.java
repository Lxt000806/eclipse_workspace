package com.house.home.web.controller.insales;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.ItemAppDetail;
import com.house.home.entity.insales.ItemTransaction;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.insales.PurchaseDetail;
import com.house.home.entity.insales.SalesInvoiceDetail;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.ItemAppDetailService;
import com.house.home.service.insales.ItemTransactionService;
import com.house.home.service.insales.ItemWHBalService;
import com.house.home.service.insales.PurchaseDetailService;
import com.house.home.service.insales.SalesInvoiceDetailService;
import com.house.home.service.insales.WareHouseService;

@Controller
@RequestMapping("/admin/wareHouseCx")
public class WareHouseCxController extends BaseController {
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ItemWHBalService itemWHBalService;
	@Autowired
	private ItemTransactionService itemTransactionService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemAppDetailService itemAppDetailService;
	@Autowired
	private PurchaseDetailService purchaseDetailService;
	@Autowired
	private SalesInvoiceDetailService salesInvoiceDetailService;
	
	/**
	 * ??????JqGrid????????????
	 * @param request
	 * @param response
	 * @param wareHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,WareHouse wareHouse) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wareHouseService.findPageBySql(page, wareHouse);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goItemWHBalJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>>  goItemWHBalJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemWHBalService.findPageBySql(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goItemTransactionJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>>  goItemTransactionJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemTransaction itemTransaction) throws Exception {
		UserContext uc=this.getUserContext(request);
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemTransactionService.findPageBySql(page, itemTransaction,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goSalesInvoiceDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSalesInvoiceDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,SalesInvoiceDetail salesInvoiceDetail) throws Exception{
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		salesInvoiceDetailService.findPageBySql(page, salesInvoiceDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????????????????
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailCheckedJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>>  goItemAppDetailCheckedJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppDetail itemAppDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppDetailService.findCheckedPageBySql(page,itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ???????????????????????????
	 * @return
	 */
	@RequestMapping("/goPurchaseDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goPurchaseDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,PurchaseDetail purchaseDetail) throws Exception{
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		purchaseDetailService.findNotArriPageBySql(page, purchaseDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ????????????????????????
	 * @return
	 */
	@RequestMapping("/goItemAppDetailAppliedJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemAppDetailAppliedJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppDetail itemAppDetail) throws Exception{
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppDetailService.findAppliedPageBySql(page,itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ??????????????????
	 * @return
	 */
	@RequestMapping("/goItemSampleDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemSampleDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemWHBal itemWHBal) throws Exception{
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemWHBalService.findItemSampleDetailPageBySql(page,itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ????????????????????????
	 * @author	created by zb
	 * @date	2018-12-8--??????11:59:01
	 * @param request
	 * @param response
	 * @param itemWHBal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWHPosiBalJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getWHPosiBalJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemWHBal itemWHBal) throws Exception{
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemWHBalService.findWHPosiBalPageBySql(page,itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response,WareHouse wareHouse){
		Page<Map<String,Object>> page = this.newPage(request);
		wareHouseService.findPageBySql(page, wareHouse);
		return new ModelAndView("admin/insales/wareHouseCx/wareHouseCx_list")
				.addObject(CommonConstant.PAGE_KEY, page)
				.addObject("wareHouse", wareHouse);
	}
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("???????????????wareHouseCx??????");
		UserContext uc=this.getUserContext(request);
		WareHouse wareHouse =wareHouseService.get(WareHouse.class, id);
		wareHouse.setCostRight(uc.getCostRight().trim());
		//resetWareHouse(wareHouse);
		return new ModelAndView("admin/insales/wareHouseCx/wareHouseCx_detail")
		.addObject("wareHouse", wareHouse);
	}
	@RequestMapping("/goAnalyseDetail")
	public ModelAndView goAnalyseDetail(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("???????????????wareHouseCx??????");
		Item item =itemService.get(Item.class, id);
		//resetWareHouse(wareHouse);
		return new ModelAndView("admin/insales/wareHouseCx/wareHouseCx_analyse")
		.addObject("item", item);
	}
	/**
	 *wareHouse??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WareHouse wareHouse){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		wareHouseService.findPageBySql(page, wareHouse);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *itemWHBal??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doItemWHBalExcel")
	public void doItemWHBalExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemWHBal itemWHBal){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemWHBalService.findPageBySql(page, itemWHBal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 *ItemTransaction??????Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doItemTransactionExcel")
	public void doItemTransactionExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemTransaction itemTransaction){
		UserContext uc=this.getUserContext(request);
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemTransactionService.findPageBySql(page, itemTransaction,uc);
		getExcelList(request);
		List<String> list = new ArrayList<String>();
		String[] sTitles=new String[]{"??????|","??????|","??????|"};
		int sTitlesIndex=0;
		if (titleList!=null && titleList.size()>0){
			for (String str : titleList){
				if ("??????".equals(str) || "??????".equals(str) || "??????".equals(str)){
					str = sTitles[sTitlesIndex] + str;
					if("1".equals(this.getUserContext(request).getCostRight())){
						if(str.indexOf("??????")!=-1){
							 sTitlesIndex++;
						}
					}else{
						 sTitlesIndex++;
					}
				}
				list.add(str);
			}
			titleList = list;
		}
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"??????????????????_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * ??????wareHouse??????
	 */
	public void resetWareHouse(WareHouse wareHouse){
		
	}
}
