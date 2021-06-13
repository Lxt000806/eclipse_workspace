package com.house.home.web.controller.basic;

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
import com.house.home.entity.basic.ItemCommiRule;
import com.house.home.service.basic.ItemCommiRuleService;

@Controller
@RequestMapping("/admin/itemCommiRule")
public class ItemCommiRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemCommiRuleController.class);

	@Autowired
	private ItemCommiRuleService itemCommiRuleService;

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
			HttpServletResponse response, ItemCommiRule itemCommiRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemCommiRuleService.findPageBySql(page, itemCommiRule);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemCommiRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemCommiRule/itemCommiRule_list");
	}
	/**
	 * ItemCommiRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/itemCommiRule/itemCommiRule_code");
	}
	/**
	 * 跳转到新增ItemCommiRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemCommiRule页面");
		ItemCommiRule itemCommiRule = null;
		if (StringUtils.isNotBlank(id)){
			itemCommiRule = itemCommiRuleService.get(ItemCommiRule.class, Integer.parseInt(id));
			itemCommiRule.setPk(null);
		}else{
			itemCommiRule = new ItemCommiRule();
		}
		
		return new ModelAndView("admin/basic/itemCommiRule/itemCommiRule_save")
			.addObject("itemCommiRule", itemCommiRule);
	}
	/**
	 * 跳转到修改ItemCommiRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemCommiRule页面");
		ItemCommiRule itemCommiRule = null;
		if (StringUtils.isNotBlank(id)){
			itemCommiRule = itemCommiRuleService.get(ItemCommiRule.class, Integer.parseInt(id));
		}else{
			itemCommiRule = new ItemCommiRule();
		}
		itemCommiRule.setM_umState("M");
		return new ModelAndView("admin/basic/itemCommiRule/itemCommiRule_update")
			.addObject("itemCommiRule", itemCommiRule);
	}
	
	/**
	 * 跳转到查看ItemCommiRule页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemCommiRule页面");
		ItemCommiRule itemCommiRule = itemCommiRuleService.get(ItemCommiRule.class, Integer.parseInt(id));
		itemCommiRule.setM_umState("V");
		return new ModelAndView("admin/basic/itemCommiRule/itemCommiRule_update")
				.addObject("itemCommiRule", itemCommiRule);
	}
	/**
	 * 添加ItemCommiRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemCommiRule itemCommiRule){
		logger.debug("添加ItemCommiRule开始");
		try{
			itemCommiRule.setExpired("F");
			itemCommiRule.setLastUpdate(new Date());
			itemCommiRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemCommiRule.setActionLog("ADD");
			this.itemCommiRuleService.save(itemCommiRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemCommiRule失败");
		}
	}
	
	/**
	 * 修改ItemCommiRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemCommiRule itemCommiRule){
		logger.debug("修改ItemCommiRule开始");
		try{
			itemCommiRule.setActionLog("EDIT");
			itemCommiRule.setLastUpdate(new Date());
			itemCommiRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemCommiRuleService.update(itemCommiRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemCommiRule失败");
		}
	}
	
	/**
	 * 删除ItemCommiRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemCommiRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemCommiRule编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemCommiRule itemCommiRule = itemCommiRuleService.get(ItemCommiRule.class, Integer.parseInt(deleteId));
				if(itemCommiRule == null)
					continue;
				itemCommiRuleService.delete(itemCommiRule);
			}
		}
		logger.debug("删除ItemCommiRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemCommiRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemCommiRule itemCommiRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemCommiRuleService.findPageBySql(page, itemCommiRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"材料提成点配置_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
