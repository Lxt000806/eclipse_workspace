package com.house.home.web.controller.insales;

import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.service.insales.ItemBatchHeaderService;
import com.house.home.service.project.WorkCostService;

@Controller
@RequestMapping("/admin/itemBatchHeader")
public class ItemBatchHeaderController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemBatchHeaderController.class);

	@Autowired
	private ItemBatchHeaderService itemBatchHeaderService;
	@Autowired
	private WorkCostService workCostService;
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
			HttpServletResponse response, ItemBatchHeader itemBatchHeader) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemBatchHeader.setItemRight(getUserContext(request).getItemRight());
		itemBatchHeaderService.findPageBySql(page, itemBatchHeader);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemBatchHeader列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_list");
	}

	/**
	 * 跳转到新增ItemBatchHeader页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemBatchHeader页面");
		ItemBatchHeader itemBatchHeader = new ItemBatchHeader();
		itemBatchHeader.setBatchType("1");
		itemBatchHeader.setNo("保存时生成");
		itemBatchHeader.setCrtCzy(getUserContext(request).getEmnum());
		itemBatchHeader.setCrtCzyDescr(workCostService.findNameByEmnum(getUserContext(request).getEmnum()));
		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_save")
			.addObject("itemBatchHeader", itemBatchHeader);
	}
	/**
	 * 跳转到快速新增页面
	 * @return
	 */
	@RequestMapping("/goQuickSave")
	public ModelAndView goQuickSave(HttpServletRequest request, HttpServletResponse response, 
			ItemBatchHeader itemBatchHeader){
		if(StringUtils.isNotBlank(itemBatchHeader.getItCode())){
			try {
				itemBatchHeader.setItemType2(itemBatchHeaderService.getItemType2(itemBatchHeader.getItCode()).get(0).get("ItemType2").toString());
			} catch (Exception e) {
			
			}
		}
		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_quickSave")
			.addObject("itemBatchHeader", itemBatchHeader);
	}
	/**
	 * 跳转到修改ItemBatchHeader页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemBatchHeader页面");
		ItemBatchHeader itemBatchHeader = null;
		if (StringUtils.isNotBlank(id)){
			itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class, id);
		}else{
			itemBatchHeader = new ItemBatchHeader();
		}
		try {
			itemBatchHeader.setCrtCzyDescr(request.getParameter("crtCzyDescr").toString());
		} catch (Exception e) {
			
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getWorkType12())){
			itemBatchHeader.setWorkType12(itemBatchHeader.getWorkType12().trim());
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getCustType())){
			itemBatchHeader.setCustType(itemBatchHeader.getCustType().trim());
		}
		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_update")
			.addObject("itemBatchHeader", itemBatchHeader);
	}
	
	/**
	 * 跳转到查看ItemBatchHeader页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemBatchHeader页面");
		ItemBatchHeader itemBatchHeader = null;
		if (StringUtils.isNotBlank(id)){
			itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class, id);
		}else{
			itemBatchHeader = new ItemBatchHeader();
		}
		try {
			itemBatchHeader.setCrtCzyDescr(request.getParameter("crtCzyDescr").toString());
		} catch (Exception e) {
			
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getWorkType12())){
			itemBatchHeader.setWorkType12(itemBatchHeader.getWorkType12().trim());
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getCustType())){
			itemBatchHeader.setCustType(itemBatchHeader.getCustType().trim());
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getCustCode())){	
			Customer customer = itemBatchHeaderService.get(Customer.class, itemBatchHeader.getCustCode());
			itemBatchHeader.setAddress(customer==null?"":customer.getAddress());
		}
		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_detail")
				.addObject("itemBatchHeader", itemBatchHeader);
	}
	

	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param workCost
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, ItemBatchHeader itemBatchHeader){
		logger.debug("保存");		
		try {	
			itemBatchHeader.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			if (!"T".equals(itemBatchHeader.getExpired())) {
				itemBatchHeader.setExpired("F");
			}
			if (StringUtils.isBlank(itemBatchHeader.getDispSeq())) {
				itemBatchHeader.setDispSeq("0");
			}
			String itemBatchDetail =request.getParameter("itemBatchDetailJson");
		    JSONObject jsonObject = JSON.parseObject(itemBatchDetail);
			JSONArray jsonArray = JSON.parseArray(jsonObject.get("detailJson").toString());//先转化成json数组  
			String  itemBatchDetailJson=jsonArray.toString();
			itemBatchHeader.setItemBatchDetailJson(itemBatchDetailJson);
			Result result = this.itemBatchHeaderService.doSaveProc(itemBatchHeader);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 修改ItemBatchHeader
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemBatchHeader itemBatchHeader){
		logger.debug("修改ItemBatchHeader开始");
		try{
			itemBatchHeader.setLastUpdate(new Date());
			itemBatchHeader.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemBatchHeaderService.update(itemBatchHeader);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemBatchHeader失败");
		}
	}
	
	/**
	 * 删除ItemBatchHeader
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemBatchHeader开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemBatchHeader编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemBatchHeader itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class, deleteId);
				if(itemBatchHeader == null)
					continue;
				itemBatchHeader.setExpired("T");
				itemBatchHeaderService.update(itemBatchHeader);
			}
		}
		logger.debug("删除ItemBatchHeader IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemBatchHeader导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemBatchHeader itemBatchHeader){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemBatchHeader.setItemRight(getUserContext(request).getItemRight());
		itemBatchHeaderService.findPageBySql(page, itemBatchHeader);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料批次管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 根据id查询材料详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemBatchHeader")
	@ResponseBody
	public JSONObject getItemBatchHeader(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		ItemBatchHeader itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class, id);
		if(itemBatchHeader== null){
			return this.out("系统中不存在code="+id+"的材料批次信息", false);
		}
		return this.out(itemBatchHeader, true);
	}
	/**
	 * 获取批次编号
	 * @param request
	 * @param response
	 * @param ItemBatchHeader
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,ItemBatchHeader itemBatchHeader) throws Exception {

		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_code")
			.addObject("itemBatchHeader", itemBatchHeader);
	}
	
	/**
	 * 跳转到撤回ItemBatchHeader页面
	 * @return
	 */
	@RequestMapping("/goBack")
	public ModelAndView goCancel(HttpServletRequest request, HttpServletResponse response, 
			String no){
		logger.debug("跳转到修改ItemBatchHeader页面");
		ItemBatchHeader itemBatchHeader = null;
		if (StringUtils.isNotBlank(no)){
			itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class, no);
		}
		if(itemBatchHeader==null){
			ServletUtils.outPrintFail(request, response, "该材料批次不存在,不能做撤回操作！");	
		}
	
		return new ModelAndView("admin/insales/itemBatchHeader/itemBatchHeader_back")
			.addObject("itemBatchHeader", itemBatchHeader);
	}
	
	/**
	 * 撤回
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doBack")
	public void doBack(HttpServletRequest request, HttpServletResponse response,String no){
		logger.debug("修改ItemBatchHeader开始");
		try{
			ItemBatchHeader itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class,no);
			if(itemBatchHeader==null){
				ServletUtils.outPrintFail(request, response, "该材料批次不存在,不能做撤回操作！");
				return;	
			}
			if(!"1".equals(itemBatchHeader.getStatus())){
				ServletUtils.outPrintFail(request, response, "只能对已确认材料批次做撤回操作!");
				return;	
			}
			if(itemBatchHeaderService.hasChgItemBatch(no)){
				ServletUtils.outPrintFail(request, response, "不能对已做增减的材料批次做撤回操作!");
				return;	
			}
			itemBatchHeader.setLastUpdate(new Date());
			itemBatchHeader.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemBatchHeader.setStatus("0");
			this.itemBatchHeaderService.update(itemBatchHeader);
			ServletUtils.outPrintSuccess(request, response,"操作成功！");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "撤回失败");
		}
	}
	/**
	 * 检查能否进行撤回操作
	 * @param request
	 * @param response
	 * @param itemApp
	 */
	@RequestMapping("/goVerifyBack")
	public void goVerifyItemUp(HttpServletRequest request, HttpServletResponse response, String no){
		logger.debug("VerifyItemUp检查修改开始");	
		if(StringUtils.isBlank(no)){
			ServletUtils.outPrintFail(request, response, "材料批次编号不能为空");
			return;
		};
		try {
			ItemBatchHeader itemBatchHeader = itemBatchHeaderService.get(ItemBatchHeader.class,no);
			if(itemBatchHeader==null){
				ServletUtils.outPrintFail(request, response, "该材料批次不存在,不能做撤回操作！");
				return;	
			}
			if(!"1".equals(itemBatchHeader.getStatus())){
				ServletUtils.outPrintFail(request, response, "只能对已确认材料批次做撤回操作!");
				return;	
			}
			if(itemBatchHeaderService.hasChgItemBatch(no)){
				ServletUtils.outPrintFail(request, response, "不能对已做增减的材料批次做撤回操作!");
				return;	
			}
			
			ServletUtils.outPrintSuccess(request, response, "可以进行进行撤回操作", null);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "无法进行修改");
		}
	}
	
}
