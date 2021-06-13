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
import com.house.home.entity.project.ItemCheck;
import com.house.home.service.project.ItemCheckService;

@Controller
@RequestMapping("/admin/itemCheck")
public class ItemCheckController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemCheckController.class);

	@Autowired
	private ItemCheckService itemCheckService;

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
			HttpServletResponse response, ItemCheck itemCheck) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemCheckService.findPageBySql(page, itemCheck);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemCheck列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemCheck/itemCheck_list");
	}
	/**
	 * ItemCheck查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemCheck/itemCheck_code");
	}
	/**
	 * 跳转到新增ItemCheck页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemCheck页面");
		ItemCheck itemCheck = null;
		if (StringUtils.isNotBlank(id)){
			itemCheck = itemCheckService.get(ItemCheck.class, id);
			itemCheck.setNo(null);
		}else{
			itemCheck = new ItemCheck();
		}
		
		return new ModelAndView("admin/project/itemCheck/itemCheck_save")
			.addObject("itemCheck", itemCheck);
	}
	/**
	 * 跳转到修改ItemCheck页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemCheck页面");
		ItemCheck itemCheck = null;
		if (StringUtils.isNotBlank(id)){
			itemCheck = itemCheckService.get(ItemCheck.class, id);
		}else{
			itemCheck = new ItemCheck();
		}
		
		return new ModelAndView("admin/project/itemCheck/itemCheck_update")
			.addObject("itemCheck", itemCheck);
	}
	
	/**
	 * 跳转到查看ItemCheck页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemCheck页面");
		ItemCheck itemCheck = itemCheckService.get(ItemCheck.class, id);
		
		return new ModelAndView("admin/project/itemCheck/itemCheck_detail")
				.addObject("itemCheck", itemCheck);
	}
	/**
	 * 添加ItemCheck
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemCheck itemCheck){
		logger.debug("添加ItemCheck开始");
		try{
			String str = itemCheckService.getSeqNo("tItemCheck");
			itemCheck.setNo(str);
			itemCheck.setLastUpdate(new Date());
			itemCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			itemCheck.setExpired("F");
			this.itemCheckService.save(itemCheck);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemCheck失败");
		}
	}
	
	/**
	 * 修改ItemCheck
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemCheck itemCheck){
		logger.debug("修改ItemCheck开始");
		try{
			itemCheck.setLastUpdate(new Date());
			itemCheck.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemCheckService.update(itemCheck);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemCheck失败");
		}
	}
	
	/**
	 * 删除ItemCheck
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemCheck开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemCheck编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemCheck itemCheck = itemCheckService.get(ItemCheck.class, deleteId);
				if(itemCheck == null)
					continue;
				itemCheck.setExpired("T");
				itemCheckService.update(itemCheck);
			}
		}
		logger.debug("删除ItemCheck IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemCheck导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemCheck itemCheck){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemCheckService.findPageBySql(page, itemCheck);
	}
	@RequestMapping("/isCheckItem")
	@ResponseBody
	public  boolean isCheckItem(HttpServletRequest request, 
			HttpServletResponse response, ItemCheck itemCheck){
			return itemCheckService.isCheckItem(itemCheck.getCustCode(), itemCheck.getItemType1());
	}
}
