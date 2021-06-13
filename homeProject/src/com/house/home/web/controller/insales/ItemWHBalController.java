package com.house.home.web.controller.insales;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.service.basic.ItemService;
import com.house.home.service.insales.ItemWHBalService;

@Controller
@RequestMapping("/admin/itemWHBal")
public class ItemWHBalController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemWHBalController.class);

	@Autowired
	private ItemWHBalService itemWHBalService;
	@Autowired
	private ItemService itemService;

	/**
	 * 查询JqGrid表格数据(按明细)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemWHBalService.findPageBySql(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据(按明细)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemWHBalService.findPurchPageBySql(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询JqGrid表格数据(按材料汇总)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
//		itemWHBalService.findPageGroupByItem(page, itemWHBal);
		itemWHBalService.findPageBySql_kcyecx(page, itemWHBal);
		// 增加【库存金额】，成本查看权限控制 add by zb
		if (!"1".equals(this.getUserContext(request).getCostRight())) {
			List<Map<String,Object>> result = page.getResult();
			for (Map<String, Object> map : result) {
				map.put("costamount", "");
			}
		}
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 按材料类型2查询（库存余额查询模块）
	 * @author	created by zb
	 * @date	2018-12-8--下午4:35:07
	 * @param request
	 * @param response
	 * @param itemWHBal
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goItemType2JqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemType2JqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemWHBalService.findItemType2PageBySql(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 库存余额订货分析
	 * @param request
	 * @param response
	 * @param itemWHBal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goOrderAnalysisQuery")
	@ResponseBody
	public WebPage<Map<String,Object>> goOrderAnalysisQuery(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if("2".equals(this.getUserContext(request).getCzylb())){
			itemWHBal.setCzybh(this.getUserContext(request).getCzybh());
		}
		itemWHBalService.findOrderAnalysis(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemWHBal列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		if (StringUtils.isBlank(itemWHBal.getConstructStatus())){
			itemWHBal.setConstructStatus("1");
		}
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_list")
			.addObject("itemWHBal", itemWHBal);
	}
	/**
	 * ItemWHBal查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_code");
	}
	/**
	 * 跳转到新增ItemWHBal页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemWHBal页面");
		ItemWHBal itemWHBal = null;
		if (StringUtils.isNotBlank(id)){
			itemWHBal = itemWHBalService.get(ItemWHBal.class, id);
			itemWHBal.setWhCode(null);
		}else{
			itemWHBal = new ItemWHBal();
		}
		
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_save")
			.addObject("itemWHBal", itemWHBal);
	}
	/**
	 * 跳转到修改ItemWHBal页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemWHBal页面");
		ItemWHBal itemWHBal = null;
		if (StringUtils.isNotBlank(id)){
			itemWHBal = itemWHBalService.get(ItemWHBal.class, id);
		}else{
			itemWHBal = new ItemWHBal();
		}
		
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_update")
			.addObject("itemWHBal", itemWHBal);
	}
	
	/**
	 * 跳转到查看ItemWHBal页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemWHBal页面");
		ItemWHBal itemWHBal = itemWHBalService.get(ItemWHBal.class, id);
		
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_detail")
				.addObject("itemWHBal", itemWHBal);
	}
	
	/**
	 * 跳转到修改销售策略页面
	 * @return
	 */
	@RequestMapping("/goModifySale")
	public ModelAndView goModifySale(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改销售策略页面");
		Item item = itemService.get(Item.class, id);
		
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_modifySale")
				.addObject("item", item);
	}
	
	/**
	 * 修改销售策略
	 * @return
	 */
	@RequestMapping("/doModifySale")
	public void doModifySale(HttpServletRequest request, HttpServletResponse response, 
			Item item){
		logger.debug("跳转到修改销售策略页面");
		try{
			Item it = itemService.get(Item.class, item.getCode());
			it.setSaleStragy(item.getSaleStragy());
			itemService.update(it);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改销售策略失败");
		}
	}
	
	/**
	 * 添加ItemWHBal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemWHBal itemWHBal){
		logger.debug("添加ItemWHBal开始");
		try{
			String str = itemWHBalService.getSeqNo("tItemWHBal");
			itemWHBal.setWhCode(str);
			itemWHBal.setLastUpdate(new Date());
			itemWHBal.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemWHBal.setExpired("F");
			this.itemWHBalService.save(itemWHBal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemWHBal失败");
		}
	}
	
	/**
	 * 修改ItemWHBal
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemWHBal itemWHBal){
		logger.debug("修改ItemWHBal开始");
		try{
			itemWHBal.setLastUpdate(new Date());
			itemWHBal.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemWHBalService.update(itemWHBal);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemWHBal失败");
		}
	}
	
	/**
	 * 删除ItemWHBal
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemWHBal开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemWHBal编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemWHBal itemWHBal = itemWHBalService.get(ItemWHBal.class, deleteId);
				if(itemWHBal == null)
					continue;
				itemWHBal.setExpired("T");
				itemWHBalService.update(itemWHBal);
			}
		}
		logger.debug("删除ItemWHBal IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemWHBal导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemWHBal itemWHBal){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemWHBalService.findPageBySql_ckzxp(page, itemWHBal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"仓库滞销品分析_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 查询JqGrid表格数据(按明细)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid_ckzxp")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid_ckzxp(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		if (itemWHBal.getDateFrom()==null && itemWHBal.getDateTo()==null){
			itemWHBal.setDateFrom(DateUtil.addDate(new Date(), -60));
			itemWHBal.setDateTo(new Date());
		}
		itemWHBalService.findPageBySql_ckzxp(page, itemWHBal);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * ItemWHBal列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList_ckzxp")
	public ModelAndView goList_ckzxp(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
		itemWHBal.setDateFrom(DateUtil.addDate(new Date(), -60));
		itemWHBal.setDateTo(new Date());
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_list_ckzxp")
			.addObject("itemWHBal", itemWHBal);
	}
	
	@RequestMapping("/goOrderAnalysis")
	public ModelAndView goOrderAnalysis(HttpServletRequest request,
			HttpServletResponse response, ItemWHBal itemWHBal) throws Exception {
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, -30);
	    
	    itemWHBal.setDateFrom(calendar.getTime());
	    itemWHBal.setDateTo(new Date());
		
		return new ModelAndView("admin/insales/itemWHBal/itemWHBal_orderAnalysis")
			.addObject("itemWHBal", itemWHBal);
	}
	
	@RequestMapping("/doExcelList")
	public void doExcelList(HttpServletRequest request ,HttpServletResponse response,
			ItemWHBal itemWHBal){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		itemWHBalService.findPageBySql(page, itemWHBal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"库存余额明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	@RequestMapping("/doExcelItemList")
	public void doExcelItemList(HttpServletRequest request ,HttpServletResponse response,
			ItemWHBal itemWHBal){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
//		itemWHBalService.findPageGroupByItem(page, itemWHBal);
		itemWHBalService.findPageBySql_kcyecx(page, itemWHBal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"库存余额按材料汇总_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/doOrderAnalysisQueryExcel")
	public void doOrderAnalysisQueryExcel(HttpServletRequest request ,HttpServletResponse response,
			ItemWHBal itemWHBal){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		if("2".equals(this.getUserContext(request).getCzylb())){
			itemWHBal.setCzybh(this.getUserContext(request).getCzybh());
		}
		itemWHBalService.findOrderAnalysis(page, itemWHBal);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"库存余额订货分析"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
