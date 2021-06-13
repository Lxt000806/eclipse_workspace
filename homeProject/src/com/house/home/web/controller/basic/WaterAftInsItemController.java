package com.house.home.web.controller.basic;

import java.util.Date;
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

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.WaterAftInsItem;
import com.house.home.service.basic.ItemType2Service;
import com.house.home.service.basic.WaterAftInsItemService;

@Controller
@RequestMapping("/admin/waterAftInsItem")
public class WaterAftInsItemController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(WaterAftInsItemController.class);
	
	
	@Autowired
	private WaterAftInsItemService waterAftInsItemService;
	@Autowired
	private ItemType2Service itemType2Service;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, WaterAftInsItem waterAftInsItem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		waterAftInsItemService.findPageBySql(page, waterAftInsItem);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, WaterAftInsItem waterAftInsItem) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		waterAftInsItemService.findPageBySqlForDetail(page, waterAftInsItem);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WaterAftInsItemRuler列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_list");
	}
	/**
	 * WaterAftInsItemRuler查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemSendNode/itemSendNode_code");
	}
	/**
	 * 跳转到新增WaterAftInsItemRuler页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增WaterAftInsItemRuler页面");
		WaterAftInsItem waterAftInsItem = new WaterAftInsItem();
		waterAftInsItem.setM_umState("A");
		
		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_save")
			.addObject("waterAftInsItem", waterAftInsItem);
	}
	
	
	/**
	 * 跳转到修改WaterAftInsItemRuler页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String no){
		logger.debug("跳转到修改WaterAftInsItemRuler页面");
		WaterAftInsItem waterAftInsItem = null;
		if (StringUtils.isNotBlank(no)){
			waterAftInsItem = waterAftInsItemService.get(WaterAftInsItem.class, no);
			waterAftInsItem.setItemType1(itemType2Service.getItemType1ByItemType2(waterAftInsItem.getItemType2().trim()).get("ItemType1").toString().trim());
		}else{
			waterAftInsItem = new WaterAftInsItem();
		}
		waterAftInsItem.setM_umState("M");
		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_save")
			.addObject("waterAftInsItem", waterAftInsItem);
	}
	
	/**
	 * 跳转到查看WaterAftInsItemRuler页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String no){
		logger.debug("跳转到查看WaterAftInsItemRuler页面");
		WaterAftInsItem waterAftInsItem = null;
		if (StringUtils.isNotBlank(no)){
			waterAftInsItem = waterAftInsItemService.get(WaterAftInsItem.class, no);
			waterAftInsItem.setItemType1(itemType2Service.getItemType1ByItemType2(waterAftInsItem.getItemType2().trim()).get("ItemType1").toString().trim());
		}else{
			waterAftInsItem = new WaterAftInsItem();
		}
		waterAftInsItem.setM_umState("V");
		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_save")
			.addObject("waterAftInsItem", waterAftInsItem);
	}
	

	@RequestMapping("/goDetailAdd")
	public ModelAndView goDetailAdd(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增WaterAftInsItemRuler详情页面");
		WaterAftInsItem waterAftInsItem = new WaterAftInsItem();
		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_detailSave")
				.addObject("WaterAftInsItem", waterAftInsItem);
	}
	
	@RequestMapping("/goDetailUpdate")
	public ModelAndView goDetailUpdate(HttpServletRequest request, HttpServletResponse response, WaterAftInsItem waterAftInsItem){
		logger.debug("跳转到编辑WaterAftInsItemRuler详情页面");
		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_detailSave")
				.addObject("WaterAftInsItem", waterAftInsItem);
	}
	
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request, HttpServletResponse response, WaterAftInsItem waterAftInsItem){
		logger.debug("跳转到查看WaterAftInsItemRuler详情页面");
		return new ModelAndView("admin/basic/waterAftInsItem/waterAftInsItem_detailSave")
				.addObject("WaterAftInsItem", waterAftInsItem);
	}
	/**
	 * 添加WaterAftInsItemRuler
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSaveForProc")
	public void doSaveForProc(HttpServletRequest request,HttpServletResponse response,WaterAftInsItem waterAftInsItem){
		logger.debug("保存");		
		try {	
			waterAftInsItem.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			String xmlData = waterAftInsItem.getDetailJson();
			Result result = this.waterAftInsItemService.saveForProc(waterAftInsItem,xmlData);
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
	 *WaterAftInsItemRuler导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WaterAftInsItem waterAftInsItem){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		waterAftInsItemService.findPageBySql(page, waterAftInsItem);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"水电后期安装材料配置管理表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
}
