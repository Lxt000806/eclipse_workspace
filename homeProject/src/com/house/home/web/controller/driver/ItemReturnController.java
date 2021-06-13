package com.house.home.web.controller.driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.driver.ItemReturn;
import com.house.home.service.driver.ItemReturnService;

@Controller
@RequestMapping("/admin/itemReturn")
public class ItemReturnController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemReturnController.class);

	@Autowired
	private ItemReturnService itemReturnService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReturn itemReturn) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturnService.findPageBySql(page, itemReturn);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemReturn列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/driver/itemReturn/itemReturn_list");
	}
	/**
	 * ItemReturn查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/driver/itemReturn/itemReturn_code");
	}
	/**
	 * 跳转到新增ItemReturn页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemReturn页面");
		ItemReturn itemReturn = null;
		if (StringUtils.isNotBlank(id)){
			itemReturn = itemReturnService.get(ItemReturn.class, id);
			itemReturn.setNo(null);
		}else{
			itemReturn = new ItemReturn();
		}
		
		return new ModelAndView("admin/driver/itemReturn/itemReturn_save")
			.addObject("itemReturn", itemReturn);
	}
	/**
	 * 跳转到修改ItemReturn页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemReturn页面");
		ItemReturn itemReturn = null;
		if (StringUtils.isNotBlank(id)){
			itemReturn = itemReturnService.get(ItemReturn.class, id);
		}else{
			itemReturn = new ItemReturn();
		}
		
		return new ModelAndView("admin/driver/itemReturn/itemReturn_update")
			.addObject("itemReturn", itemReturn);
	}
	
	/**
	 * 跳转到查看ItemReturn页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemReturn页面");
		ItemReturn itemReturn = itemReturnService.get(ItemReturn.class, id);
		
		return new ModelAndView("admin/driver/itemReturn/itemReturn_detail")
				.addObject("itemReturn", itemReturn);
	}
	/**
	 * 添加ItemReturn
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemReturn itemReturn){
		logger.debug("添加ItemReturn开始");
		try{
			String str = itemReturnService.getSeqNo("tItemReturn");
			itemReturn.setNo(str);
			itemReturn.setLastUpdate(new Date());
			itemReturn.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemReturn.setExpired("F");
			this.itemReturnService.save(itemReturn);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemReturn失败");
		}
	}
	
	/**
	 * 修改ItemReturn
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemReturn itemReturn){
		logger.debug("修改ItemReturn开始");
		try{
			itemReturn.setLastUpdate(new Date());
			itemReturn.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemReturnService.update(itemReturn);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemReturn失败");
		}
	}
	
	/**
	 * 删除ItemReturn
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemReturn开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemReturn编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemReturn itemReturn = itemReturnService.get(ItemReturn.class, deleteId);
				if(itemReturn == null)
					continue;
				itemReturn.setExpired("T");
				itemReturnService.update(itemReturn);
			}
		}
		logger.debug("删除ItemReturn IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemReturn导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReturn itemReturn){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemReturnService.findPageBySql(page, itemReturn);
	}
	/**
	 * 查询goReturnDetailJqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReturnDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReturnDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReturn itemReturn) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturnService.findReturnDetail(page, itemReturn);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 跳转到新增退货页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goAddRetunDetail")
	public ModelAndView goAddSendDetail(HttpServletRequest request,
			HttpServletResponse response,ItemReturn itemReturn) throws Exception {

		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_returnDetail_add")
			.addObject("itemReturn", itemReturn);
	}
	/**
	 * 查询goReturnDetailAddJqGrid表格数据-新增退货
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReturnDetailAddJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goSendDetailAddJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReturn itemReturn) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturn.setItemRight(getUserContext(request).getItemRight());
		itemReturnService.findReturnDetailAdd(page, itemReturn);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 查询goReturnDetailMngJqGrid表格数据 配送管理-明细管理-退货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReturnDetailMngJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReturnDetailMngJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReturn itemReturn) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturn.setItemRight(getUserContext(request).getItemRight());
		itemReturnService.findReturnDetailMng(page, itemReturn);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *ItemReturn导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doReturnDetailMngExcel")
	public void doReturnDetailMngExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReturn itemReturn){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemReturn.setItemRight(getUserContext(request).getItemRight());
		itemReturnService.findReturnDetailMng(page, itemReturn);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"配送明细管理（退货）_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 退货取消
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCancel")
	public void doCancel(HttpServletRequest request, HttpServletResponse response, ItemReturn itemReturn){
		logger.debug("退货取消");
		try{
			itemReturn.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemReturnService.doCancel(itemReturn);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "操作失败！");
		}
	}
	/**
	 * 查询goReturnDetailMngJqGrid表格数据 配送管理-明细查询-退货明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goReturnDetailQryJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goReturnDetailQryJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemReturn itemReturn) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemReturn.setItemRight(getUserContext(request).getItemRight());
		itemReturnService.findReturnDetailQry(page, itemReturn);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 *ItemReturn导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doReturnDetailQryExcel")
	public void doReturnDetailQryExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemReturn itemReturn){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemReturn.setItemRight(getUserContext(request).getItemRight());
		itemReturnService.findReturnDetailQry(page, itemReturn);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"明细查询（退货）_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 
	 * 自动生成搬运费
	 * @param request
	 * @param response
	 * @param itemReturn
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/autoCteateFee")
	public List<Map<String, Object>> autoCteateFee(HttpServletRequest request,
			HttpServletResponse response, ItemReturn itemReturn) {
		logger.debug("自动生成搬运费");
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {	
			String rt =request.getParameter("itemReturnJson");
		    JSONObject jsonObject = JSON.parseObject(rt);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  itemReturnJson=jsonArray.toString();
			itemReturn.setItemReturnJson(itemReturnJson);
			itemReturn.setLastUpdatedBy(getUserContext(request).getCzybh());
			list =itemReturnService.autoCteateFee(itemReturn);
		} catch (Exception e) {
		}
		return list;
	}
	/**
	 * 跳转到查看退货图片页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goViewReturnPhoto")
	public ModelAndView goViewSendPhoto(HttpServletRequest request,
			HttpServletResponse response,ItemReturn itemReturn) throws Exception {

		return new ModelAndView("admin/insales/itemSendBatch/itemSendBatch_returnDetail_viewPhoto")
			.addObject("itemReturn", itemReturn);
	}
}
