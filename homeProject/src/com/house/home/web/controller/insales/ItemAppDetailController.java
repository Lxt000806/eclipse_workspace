package com.house.home.web.controller.insales;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.excel.ExportExcel;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.ItemAppDetail;
import com.house.home.service.insales.ItemAppDetailService;

@Controller
@RequestMapping("/admin/itemAppDetail")
public class ItemAppDetailController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemAppDetailController.class);

	@Autowired
	private ItemAppDetailService itemAppDetailService;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppDetail itemAppDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppDetailService.findPageBySql(page, itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goPurchJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getPurchJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppDetail itemAppDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppDetailService.findPurchPageBySql(page, itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询预算导入JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goImportJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getImportJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppDetail itemAppDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppDetailService.findImportPageBySql(page, itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询领料退回JqGrid表格数据
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridReturn")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridReturn(HttpServletRequest request,
			HttpServletResponse response,ItemAppDetail itemAppDetail) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppDetailService.findPageBySql_return(page, itemAppDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 导出数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		String exportData = request.getParameter("exportData");
		if(StringUtils.isEmpty(exportData)){
			logger.error("导出查询结果数据传入的参数为空!");
			return;
		}
		JSONObject dataObj = JSONObject.parseObject(exportData);
		JSONArray colHeader = dataObj.getJSONArray("colHeader");
		if(colHeader != null && !colHeader.isEmpty()){
			List<String> colNames = Lists.newArrayList();
			List<String> colLabels = Lists.newArrayList();
			for(int i = 0; i < colHeader.size(); i ++){
				JSONObject obj = colHeader.getJSONObject(i);
				colNames.add(obj.getString("colName"));
				colLabels.add(obj.getString("colLabel"));
			}
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			page.setPageSize(100000000);
			ItemAppDetail itemAppDetail = new ItemAppDetail();
			itemAppDetail.setNo(dataObj.getString("no"));
			itemAppDetail.setCustCode(dataObj.getString("custCode"));
			itemAppDetailService.findPageBySql(page, itemAppDetail);
			String exportTitle = dataObj.getString("exportTitle");
			exportTitle = StringUtils.isEmpty(exportTitle)?"导出数据":exportTitle;
			String fileName = exportTitle+DateUtil.DateToString(new Date(),"yyyyMMddHHmmss")+".xlsx";
			try {
				new ExportExcel(exportTitle,colLabels).setMapDataList(page.getResult(), colNames).write(response, fileName).dispose();
			} catch (IOException e) {
				logger.error("导出查询结果数据至excel文件出错："+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 领料明细已有项新增
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailExists")
	public ModelAndView goItemAppDetailExists(HttpServletRequest request,
			HttpServletResponse response, ItemAppDetail itemAppDetail) throws Exception {
		String postData = request.getParameter("postData");
		JSONObject postParam = new JSONObject();
		if(StringUtils.isNotEmpty(postData)){
			postParam = JSONObject.parseObject(postData);
		}
		return new ModelAndView("admin/insales/itemAppDetail/itemAppDetail_exists").addObject("postParam", postParam);
	}
	
	/**
	 * 领料明细退回新增
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailExistsReturn")
	public ModelAndView goItemAppDetailExistsReturn(HttpServletRequest request,
			HttpServletResponse response, ItemAppDetail itemAppDetail) throws Exception {
		String postData = request.getParameter("postData");
		JSONObject postParam = new JSONObject();
		if(StringUtils.isNotEmpty(postData)){
			postParam = JSONObject.parseObject(postData);
		}
		return new ModelAndView("admin/insales/itemAppDetail/itemAppDetail_exists_return").addObject("postParam", postParam);
	}
	
	/**
	 * 领料明细退回新增jqgrid
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailExistsReturnJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemAppDetailExistsReturnJqGrid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String no = request.getParameter("no");
		String unSelected = request.getParameter("unSelected");
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("no", no);
		param.put("unSelected", unSelected);
		itemAppDetailService.findItemAppDetailExistsReturn(page, param);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 领料明细快速新增
	 * @param request
	 * @param response
	 * @param itemAppDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailFast")
	public ModelAndView goItemAppDetailFast(HttpServletRequest request,
			HttpServletResponse response, ItemAppDetail itemAppDetail) throws Exception {
		String postData = request.getParameter("postData");
		JSONObject postParam = new JSONObject();
		if(StringUtils.isNotEmpty(postData)){
			postParam = JSONObject.parseObject(postData);
		}
		return new ModelAndView("admin/insales/itemAppDetail/itemAppDetail_fast").addObject("postParam", postParam);
	}
	
	/**
	 * ItemAppDetail列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ItemAppDetail itemAppDetail) throws Exception {

		Page<Map<String,Object>> page = this.newPage(request);
		itemAppDetailService.findPageBySql(page, itemAppDetail);

		return new ModelAndView("admin/itemAppDetail/itemAppDetail_list")
			.addObject(CommonConstant.PAGE_KEY, page)
			.addObject("itemAppDetail", itemAppDetail);
	}
	
	/**
	 * 跳转到新增ItemAppDetail页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemAppDetail页面");
		ItemAppDetail itemAppDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemAppDetail = itemAppDetailService.get(ItemAppDetail.class, Integer.parseInt(id));
			itemAppDetail.setPk(null);
		}else{
			itemAppDetail = new ItemAppDetail();
		}
		
		return new ModelAndView("admin/itemAppDetail/itemAppDetail_save")
			.addObject("itemAppDetail", itemAppDetail);
	}
	/**
	 * 跳转到修改ItemAppDetail页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemAppDetail页面");
		ItemAppDetail itemAppDetail = null;
		if (StringUtils.isNotBlank(id)){
			itemAppDetail = itemAppDetailService.get(ItemAppDetail.class, Integer.parseInt(id));
		}else{
			itemAppDetail = new ItemAppDetail();
		}
		
		return new ModelAndView("admin/itemAppDetail/itemAppDetail_update")
			.addObject("itemAppDetail", itemAppDetail);
	}
	
	/**
	 * 跳转到查看ItemAppDetail页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemAppDetail页面");
		ItemAppDetail itemAppDetail = itemAppDetailService.get(ItemAppDetail.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/itemAppDetail/itemAppDetail_detail")
				.addObject("itemAppDetail", itemAppDetail);
	}
	/**
	 * 添加ItemAppDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemAppDetail itemAppDetail){
		logger.debug("添加ItemAppDetail开始");
		try{
			ItemAppDetail xt = this.itemAppDetailService.get(ItemAppDetail.class, itemAppDetail.getPk());
			if (xt!=null){
				ServletUtils.outPrintFail(request, response, "ItemAppDetail重复");
				return;
			}
			this.itemAppDetailService.save(itemAppDetail);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemAppDetail失败");
		}
	}
	
	/**
	 * 修改ItemAppDetail
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemAppDetail itemAppDetail){
		logger.debug("修改ItemAppDetail开始");
		try{
			ItemAppDetail xt = this.itemAppDetailService.get(ItemAppDetail.class, itemAppDetail.getPk());
			if (xt!=null){
				this.itemAppDetailService.update(itemAppDetail);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.itemAppDetailService.save(itemAppDetail);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemAppDetail失败");
		}
	}
	
	/**
	 * 删除ItemAppDetail
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemAppDetail开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemAppDetail编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemAppDetail itemAppDetail = itemAppDetailService.get(ItemAppDetail.class, Integer.parseInt(deleteId));
				if(itemAppDetail == null)
					continue;
				itemAppDetail.setExpired("T");
				itemAppDetailService.update(itemAppDetail);
			}
		}
		logger.debug("删除ItemAppDetail IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemAppDetail导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemAppDetail itemAppDetail){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppDetailService.findPageBySql(page, itemAppDetail);
	}
	
	/**
	 * 已有项新增
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailExistsJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemAppDetailExistsJqGrid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		Map<String,Object> param = new HashMap<String,Object>();
		itemAppDetailService.findItemAppDetailExists(page, param);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 快速新增
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goItemAppDetailFastJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goItemAppDetailFastJqGrid(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		Map<String,Object> param = new HashMap<String,Object>();
		itemAppDetailService.findItemAppDetailFast(page, param);
		return new WebPage<Map<String,Object>>(page);
	}
	

}
