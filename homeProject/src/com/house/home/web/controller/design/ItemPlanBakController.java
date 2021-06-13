package com.house.home.web.controller.design;

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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.BaseItemPlanBak;
import com.house.home.entity.design.ItemPlanBak;
import com.house.home.service.design.ItemPlanBakService;

@Controller
@RequestMapping("/admin/itemPlanBak")
public class ItemPlanBakController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPlanBakController.class);

	@Autowired
	private ItemPlanBakService itemPlanBakService;

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
			HttpServletResponse response, ItemPlanBak itemPlanBak) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPlanBakService.findPageBySql(page, itemPlanBak);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemPlanBak列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/itemPlanBak/itemPlanBak_list");
	}
	/**
	 * ItemPlanBak查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,ItemPlanBak itemPlanBak) throws Exception {
		
		return new ModelAndView("admin/design/itemPlanBak/itemPlanBak_code").addObject("itemPlanBak", itemPlanBak);
	}
	/**
	 * 跳转到新增ItemPlanBak页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPlanBak页面");
		ItemPlanBak itemPlanBak = null;
		if (StringUtils.isNotBlank(id)){
			itemPlanBak = itemPlanBakService.get(ItemPlanBak.class, id);
			itemPlanBak.setNo(null);
		}else{
			itemPlanBak = new ItemPlanBak();
		}
		
		return new ModelAndView("admin/design/itemPlanBak/itemPlanBak_save")
			.addObject("itemPlanBak", itemPlanBak);
	}
	/**
	 * 跳转到修改ItemPlanBak页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPlanBak页面");
		ItemPlanBak itemPlanBak = null;
		if (StringUtils.isNotBlank(id)){
			itemPlanBak = itemPlanBakService.get(ItemPlanBak.class, id);
		}else{
			itemPlanBak = new ItemPlanBak();
		}
		
		return new ModelAndView("admin/design/itemPlanBak/itemPlanBak_update")
			.addObject("itemPlanBak", itemPlanBak);
	}
	
	/**
	 * 跳转到查看ItemPlanBak页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPlanBak页面");
		ItemPlanBak itemPlanBak = itemPlanBakService.get(ItemPlanBak.class, id);
		
		return new ModelAndView("admin/design/itemPlanBak/itemPlanBak_detail")
				.addObject("itemPlanBak", itemPlanBak);
	}
	/**
	 * 添加ItemPlanBak
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemPlanBak itemPlanBak){
		logger.debug("添加ItemPlanBak开始");
		try{
			String str = itemPlanBakService.getSeqNo("tItemPlanBak");
			itemPlanBak.setNo(str);
			itemPlanBak.setLastUpdate(new Date());
			itemPlanBak.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemPlanBak.setExpired("F");
			this.itemPlanBakService.save(itemPlanBak);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemPlanBak失败");
		}
	}
	
	/**
	 * 修改ItemPlanBak
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPlanBak itemPlanBak){
		logger.debug("修改ItemPlanBak开始");
		try{
			itemPlanBak.setLastUpdate(new Date());
			itemPlanBak.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPlanBakService.update(itemPlanBak);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPlanBak失败");
		}
	}
	
	/**
	 * 删除ItemPlanBak
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPlanBak开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPlanBak编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPlanBak itemPlanBak = itemPlanBakService.get(ItemPlanBak.class, deleteId);
				if(itemPlanBak == null)
					continue;
				itemPlanBak.setExpired("T");
				itemPlanBakService.update(itemPlanBak);
			}
		}
		logger.debug("删除ItemPlanBak IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemPlanBak导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPlanBak itemPlanBak){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPlanBakService.findPageBySql(page, itemPlanBak);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"ItemPlanBak_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * 根据id查询ItemPlanBak详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getItemPlanBak")
	@ResponseBody
	public JSONObject getBaseItemPlanBak(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		ItemPlanBak itemPlanBak= itemPlanBakService.get(ItemPlanBak.class, id);
		if(itemPlanBak == null){
			return this.out("系统中不存在No="+id+"的备份", false);
		}
		return this.out(itemPlanBak, true);
	}

}
