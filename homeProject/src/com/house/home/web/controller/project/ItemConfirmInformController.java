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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.ItemConfirmInform;
import com.house.home.service.project.ItemConfirmInformService;
import com.house.home.bean.project.ItemConfirmInformBean;

@Controller
@RequestMapping("/admin/itemConfirmInform")
public class ItemConfirmInformController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ItemConfirmInformController.class);

	@Autowired
	private ItemConfirmInformService itemConfirmInformService;

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
			HttpServletResponse response, ItemConfirmInform itemConfirmInform) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemConfirmInformService.findPageBySql(page, itemConfirmInform);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ItemConfirmInform列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemConfirmInform/itemConfirmInform_list");
	}
	/**
	 * ItemConfirmInform查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/itemConfirmInform/itemConfirmInform_code");
	}
	/**
	 * 跳转到新增ItemConfirmInform页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ItemConfirmInform页面");
		ItemConfirmInform itemConfirmInform = null;
		if (StringUtils.isNotBlank(id)){
			itemConfirmInform = itemConfirmInformService.get(ItemConfirmInform.class, Integer.parseInt(id));
			itemConfirmInform.setPk(null);
		}else{
			itemConfirmInform = new ItemConfirmInform();
		}
		
		return new ModelAndView("admin/project/itemConfirmInform/itemConfirmInform_save")
			.addObject("itemConfirmInform", itemConfirmInform);
	}
	/**
	 * 跳转到修改ItemConfirmInform页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ItemConfirmInform页面");
		ItemConfirmInform itemConfirmInform = null;
		if (StringUtils.isNotBlank(id)){
			itemConfirmInform = itemConfirmInformService.get(ItemConfirmInform.class, Integer.parseInt(id));
		}else{
			itemConfirmInform = new ItemConfirmInform();
		}
		
		return new ModelAndView("admin/project/itemConfirmInform/itemConfirmInform_update")
			.addObject("itemConfirmInform", itemConfirmInform);
	}
	
	/**
	 * 跳转到查看ItemConfirmInform页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ItemConfirmInform页面");
		ItemConfirmInform itemConfirmInform = itemConfirmInformService.get(ItemConfirmInform.class, Integer.parseInt(id));
		
		return new ModelAndView("admin/project/itemConfirmInform/itemConfirmInform_detail")
				.addObject("itemConfirmInform", itemConfirmInform);
	}
	/**
	 * 添加ItemConfirmInform
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ItemConfirmInform itemConfirmInform){
		logger.debug("添加ItemConfirmInform开始");
		try{
			this.itemConfirmInformService.save(itemConfirmInform);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ItemConfirmInform失败");
		}
	}
	
	/**
	 * 修改ItemConfirmInform
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ItemConfirmInform itemConfirmInform){
		logger.debug("修改ItemConfirmInform开始");
		try{
			itemConfirmInform.setLastUpdate(new Date());
			itemConfirmInform.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.itemConfirmInformService.update(itemConfirmInform);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ItemConfirmInform失败");
		}
	}
	
	/**
	 * 删除ItemConfirmInform
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ItemConfirmInform开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ItemConfirmInform编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ItemConfirmInform itemConfirmInform = itemConfirmInformService.get(ItemConfirmInform.class, Integer.parseInt(deleteId));
				if(itemConfirmInform == null)
					continue;
				itemConfirmInform.setExpired("T");
				itemConfirmInformService.update(itemConfirmInform);
			}
		}
		logger.debug("删除ItemConfirmInform IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}



}
