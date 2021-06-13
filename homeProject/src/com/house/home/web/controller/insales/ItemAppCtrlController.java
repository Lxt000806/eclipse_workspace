package com.house.home.web.controller.insales;

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
import com.house.home.entity.insales.ItemAppCtrl;
import com.house.home.service.insales.ItemAppCtrlService;

@Controller
@RequestMapping("/admin/itemAppCtrl")
public class ItemAppCtrlController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemAppCtrlController.class);

	@Autowired
	private ItemAppCtrlService itemAppCtrlService;

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
			HttpServletResponse response, ItemAppCtrl itemAppCtrl) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppCtrlService.findPageBySql(page, itemAppCtrl);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemAppCtrl列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemAppCtrl/itemAppCtrl_list");
	}
	/**
	 * ItemAppCtrl查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/itemAppCtrl/itemAppCtrl_code");
	}
	/**
	 * 跳转到新增ItemAppCtrl页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemAppCtrl页面");
		ItemAppCtrl itemAppCtrl = null;
		if (StringUtils.isNotBlank(id)){
			itemAppCtrl = itemAppCtrlService.get(ItemAppCtrl.class, id);
			itemAppCtrl.setCustType(null);
		}else{
			itemAppCtrl = new ItemAppCtrl();
		}
		
		return new ModelAndView("admin/insales/itemAppCtrl/itemAppCtrl_save")
			.addObject("itemAppCtrl", itemAppCtrl);
	}
	/**
	 * 跳转到修改ItemAppCtrl页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemAppCtrl页面");
		ItemAppCtrl itemAppCtrl = null;
		if (StringUtils.isNotBlank(id)){
			itemAppCtrl = itemAppCtrlService.get(ItemAppCtrl.class, id);
		}else{
			itemAppCtrl = new ItemAppCtrl();
		}
		
		return new ModelAndView("admin/insales/itemAppCtrl/itemAppCtrl_update")
			.addObject("itemAppCtrl", itemAppCtrl);
	}
	
	/**
	 * 跳转到查看ItemAppCtrl页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemAppCtrl页面");
		ItemAppCtrl itemAppCtrl = itemAppCtrlService.get(ItemAppCtrl.class, id);
		
		return new ModelAndView("admin/insales/itemAppCtrl/itemAppCtrl_detail")
				.addObject("itemAppCtrl", itemAppCtrl);
	}
	/**
	 * 添加ItemAppCtrl
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemAppCtrl itemAppCtrl){
		logger.debug("添加ItemAppCtrl开始");
		try{
			String str = itemAppCtrlService.getSeqNo("tItemAppCtrl");
			itemAppCtrl.setCustType(str);
			this.itemAppCtrlService.save(itemAppCtrl);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemAppCtrl失败");
		}
	}
	
	/**
	 * 修改ItemAppCtrl
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemAppCtrl itemAppCtrl){
		logger.debug("修改ItemAppCtrl开始");
		try{
			this.itemAppCtrlService.update(itemAppCtrl);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemAppCtrl失败");
		}
	}
	
	/**
	 * 删除ItemAppCtrl
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemAppCtrl开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemAppCtrl编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemAppCtrl itemAppCtrl = itemAppCtrlService.get(ItemAppCtrl.class, deleteId);
				if(itemAppCtrl == null)
					continue;
				itemAppCtrlService.update(itemAppCtrl);
			}
		}
		logger.debug("删除ItemAppCtrl IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ItemAppCtrl导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ItemAppCtrl itemAppCtrl){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		itemAppCtrlService.findPageBySql(page, itemAppCtrl);
	}

}
