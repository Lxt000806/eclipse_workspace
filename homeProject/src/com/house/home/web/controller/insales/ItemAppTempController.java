package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.house.home.entity.insales.ItemAppTemp;
import com.house.home.entity.insales.ItemAppTempArea;
import com.house.home.entity.insales.ItemAppTempAreaDetail;
import com.house.home.service.insales.ItemAppTempAreaService;
import com.house.home.service.insales.ItemAppTempService;

@Controller
@RequestMapping("/admin/itemAppTemp")
public class ItemAppTempController extends BaseController{
	@Autowired
	private  ItemAppTempService itemAppTempService;
	@Autowired
	private ItemAppTempAreaService itemAppTempAreaService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppTemp itemAppTemp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppTempService.findItemAPPTempPageBySql(page, itemAppTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description: TODO 领料申请模板面积区间分页查询
	 * @author	created by zb
	 * @date	2018-9-9--上午10:06:57
	 */
	@RequestMapping("/goAreaJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getAreaJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppTemp itemAppTemp) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppTempService.findAreaPageBySql(page, itemAppTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description: TODO 领料申请明细分页查询 
	 * @author	created by zb
	 * @date	2018-9-9--上午11:10:57
	 */
	@RequestMapping("/goAreaDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getAreaDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, ItemAppTempArea itemAppTempArea) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppTempService.findAreaDetailPageBySql(page, itemAppTempArea);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_save");
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, String no) throws Exception {
		ItemAppTemp itemAppTemp = itemAppTempService.get(ItemAppTemp.class, no);
		if (StringUtils.isNotBlank(itemAppTemp.getItemType1())) {
			itemAppTemp.setItemType1(itemAppTemp.getItemType1().trim());
		}
		if (StringUtils.isNotBlank(itemAppTemp.getWorkType12())) {
			itemAppTemp.setWorkType12(itemAppTemp.getWorkType12().trim());
		}
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_update")
			.addObject("itemAppTemp", itemAppTemp);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, String no, String m_umState) throws Exception {
		ItemAppTemp itemAppTemp = itemAppTempService.get(ItemAppTemp.class, no);
		if (StringUtils.isNotBlank(itemAppTemp.getItemType1())) {
			itemAppTemp.setItemType1(itemAppTemp.getItemType1().trim());
		}
		if (StringUtils.isNotBlank(itemAppTemp.getWorkType12())) {
			itemAppTemp.setWorkType12(itemAppTemp.getWorkType12().trim());
		}
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_update")
			.addObject("itemAppTemp", itemAppTemp)
			.addObject("m_umState", m_umState);
	}
	
	//TODO 面积区间管理
	@RequestMapping("/goArea")
	public ModelAndView goArea(HttpServletRequest request,
			HttpServletResponse response, String no, String m_umState) throws Exception {
		ItemAppTemp itemAppTemp = itemAppTempService.get(ItemAppTemp.class, no);
		if (StringUtils.isNotBlank(itemAppTemp.getItemType1())) {
			itemAppTemp.setItemType1(itemAppTemp.getItemType1().trim());
		}
		if (StringUtils.isNotBlank(itemAppTemp.getWorkType12())) {
			itemAppTemp.setWorkType12(itemAppTemp.getWorkType12().trim());
		}
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_area")
			.addObject("itemAppTemp", itemAppTemp)
			.addObject("m_umState", m_umState);
	}
	
	//TODO 详细增加
	@RequestMapping("/goAreaSave")
	public ModelAndView goAreaSave(HttpServletRequest request,
			HttpServletResponse response, String m_umState, String itemType1, String iatno) throws Exception {
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_areaSave")
			.addObject("m_umState", m_umState)
			.addObject("itemType1", itemType1)
			.addObject("iatno", iatno);
	}
	
	//TODO 详细编辑
	@RequestMapping("/goAreaUpdate")
	public ModelAndView goAreaUpdate(HttpServletRequest request,
			HttpServletResponse response, String no, String m_umState, String itemType1) throws Exception {
		ItemAppTempArea itemAppTempArea = itemAppTempAreaService.get(ItemAppTempArea.class, no);
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_areaUpdate")
			.addObject("itemAppTempArea", itemAppTempArea)
			.addObject("m_umState", m_umState)
			.addObject("itemType1", itemType1);
	}
	
	//TODO 材料增加
	@RequestMapping("/goDetailSave")
	public ModelAndView goDetailSave(HttpServletRequest request,
			HttpServletResponse response, String m_umState, String pks, String itCodes,
			String itemType1) throws Exception {
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_areaDetailSave")
			.addObject("m_umState", m_umState)
			.addObject("pks",pks)
			.addObject("itCodes",itCodes)
			.addObject("itemType1", itemType1);
	}
	
	//TODO 材料编辑
	@RequestMapping("/goDetailUpdate")
	public ModelAndView goDetailUpdate(HttpServletRequest request,
			HttpServletResponse response, String pks, String itCodes, String itemType1, ItemAppTempAreaDetail iatad) throws Exception {
		return new ModelAndView("admin/insales/itemAppTemp/itemAppTemp_areaDetailSave")
			.addObject("m_umState", iatad.getM_umState())
			.addObject("pks",pks)
			.addObject("itCodes",itCodes)
			.addObject("itemType1", itemType1)
			.addObject("itemAppTempAreaDetail", iatad);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,ItemAppTemp itemAppTemp){
		logger.debug("领料申请模板新增");
		try {
			itemAppTemp.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			/*执行存储过程*/
			Result result = this.itemAppTempService.doSave(itemAppTemp);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	//TODO 领料申请面积区间——存储过程
	@RequestMapping("/doAreaSave")
	public void doAreaSave(HttpServletRequest request,HttpServletResponse response,ItemAppTempArea itemAppTempArea){
		logger.debug("领料申请面积区间——存储过程");
		try {
			itemAppTempArea.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			// 执行存储过程
			Result result = this.itemAppTempService.doAreaSave(itemAppTempArea);
			
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemAppTemp itemAppTemp){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppTempService.findItemAPPTempPageBySql(page, itemAppTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"领料申请模板管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * @Description: TODO 根据材料编号获取材料信息（只可增加字段，不可删除）
	 * @author	created by zb
	 * @date	2018-9-10--上午11:29:19
	 * @param request
	 * @param response
	 * @param code 材料编号
	 * @return
	 */
	@RequestMapping("/getItemByCode")
	@ResponseBody
	public Map<String, Object> getItemByCode(HttpServletRequest request, HttpServletResponse response,
			String code){
		Map<String, Object> item = null;
		if (StringUtils.isNotBlank(code)) {
			item = itemAppTempService.getItemByCode(code);

			return item;
		} else {
			return item;
		}
	}
}
