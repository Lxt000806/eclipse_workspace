package com.house.home.web.controller.project;

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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.project.ItemPreMeasureService;

@Controller
@RequestMapping("/admin/itemPreMeasure")
public class ItemPreMeasureController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemPreMeasureController.class);

	@Autowired
	private ItemPreMeasureService itemPreMeasureService;

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
			HttpServletResponse response, ItemPreMeasure itemPreMeasure) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemPreMeasureService.findPageBySql(page, itemPreMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemPreMeasure列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemPreMeasure/itemPreMeasure_list");
	}
	/**
	 * ItemPreMeasure查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemPreMeasure/itemPreMeasure_code");
	}
	/**
	 * 跳转到新增ItemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemPreMeasure页面");
		ItemPreMeasure itemPreMeasure = null;
		if (StringUtils.isNotBlank(id)){
			itemPreMeasure = itemPreMeasureService.get(ItemPreMeasure.class, Integer.parseInt(id));
			itemPreMeasure.setPk(null);
		}else{
			itemPreMeasure = new ItemPreMeasure();
		}
		
		return new ModelAndView("admin/project/itemPreMeasure/itemPreMeasure_save")
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 跳转到修改ItemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemPreMeasure页面");
		ItemPreMeasure itemPreMeasure = null;
		if (StringUtils.isNotBlank(id)){
			itemPreMeasure = itemPreMeasureService.get(ItemPreMeasure.class, Integer.parseInt(id));
		}else{
			itemPreMeasure = new ItemPreMeasure();
		}
		
		return new ModelAndView("admin/project/itemPreMeasure/itemPreMeasure_update")
			.addObject("itemPreMeasure", itemPreMeasure);
	}
	
	/**
	 * 跳转到查看ItemPreMeasure页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemPreMeasure页面");
		ItemPreMeasure itemPreMeasure = itemPreMeasureService.get(ItemPreMeasure.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/itemPreMeasure/itemPreMeasure_detail")
				.addObject("itemPreMeasure", itemPreMeasure);
	}
	/**
	 * 添加ItemPreMeasure
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("添加ItemPreMeasure开始");
		try{
			this.itemPreMeasureService.save(itemPreMeasure);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemPreMeasure失败");
		}
	}
	
	/**
	 * 修改ItemPreMeasure
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		logger.debug("修改ItemPreMeasure开始");
		try{
			itemPreMeasure.setLastUpdate(new Date());
			itemPreMeasure.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemPreMeasureService.update(itemPreMeasure);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemPreMeasure失败");
		}
	}
	
	/**
	 * 删除ItemPreMeasure
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemPreMeasure开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemPreMeasure编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemPreMeasure itemPreMeasure = itemPreMeasureService.get(ItemPreMeasure.class, Integer.parseInt(deleteId));
				if(itemPreMeasure == null)
					continue;
				itemPreMeasure.setExpired("T");
				itemPreMeasureService.update(itemPreMeasure);
			}
		}
		logger.debug("删除ItemPreMeasure IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemPreMeasure导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemPreMeasure itemPreMeasure){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemPreMeasureService.findPageBySql(page, itemPreMeasure);
	}

}
