package com.house.framework.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.entity.Dict;
import com.house.framework.entity.DictItem;
import com.house.framework.service.DictItemService;
import com.house.framework.service.DictService;

@Controller
@RequestMapping("/admin/dictItem")
public class DictItemController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(DictItemController.class);

	@Autowired
	private DictItemService dictItemService;
	@Autowired
	private DictService dictService;
	@Resource(name = "dictCacheManager")
	private ICacheManager dictCacheManager;
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<DictItem> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,DictItem dictItem) throws Exception {
		
		Page<DictItem> page = this.newPageForJqGrid(request);
		this.dictItemService.findPage(page, dictItem);
		return new WebPage<DictItem>(page);
	}
	/**
	 * 跳转到字典元素列表
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response, DictItem dictItem){
		logger.debug("跳转到字典元素列表");
		Dict dict = this.dictService.get(dictItem.getDictId());
		Assert.notNull(dict, "字典元素对应的字典对象为空");
		return new ModelAndView("system/dict/dictItem_list")
				.addObject("dictItem", dictItem)
				.addObject("inUse", CommonConstant.STATUS_USABLE)
				.addObject("dictType", request.getParameter("dictType"))
				.addObject("flag", CommonConstant.STATUS_USABLE.equals(dict.getStatus()));
		}
	
	/**
	 * 跳转到新增字典元素页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, Long dictId){
		int nextNum = this.dictItemService.getNextNum(dictId);
		
		return new ModelAndView("system/dict/dictItem_save")
					.addObject("dictId", dictId)
					.addObject("nextNum", nextNum)
					.addObject("dictType", request.getParameter("dictType"));
	}
	
	/**
	 * 跳转到修改字典元素页面
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Long itemId){
		DictItem dictItem = this.dictItemService.get(itemId);
		
		return new ModelAndView("system/dict/dictItem_update")
					.addObject("dictItem", dictItem)
					.addObject("dictType", request.getParameter("dictType"));
	}
	
	/**
	 * 跳转到查看字典元素页面
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, Long itemId){
		DictItem dictItem = this.dictItemService.get(itemId);
		
		return new ModelAndView("system/dict/dictItem_detail")
					.addObject("dictItem", dictItem);
	}
	
	//-------------------------------------------------------------------------------------------------
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, DictItem dictItem, BindingResult bindingResult){
		springValidator.validate(dictItem, bindingResult);
		if(!validateDictItemCode(dictItem.getItemCode(), null, dictItem.getDictId())){
			bindingResult.rejectValue("itemCode", "","字典元素编码 ["+dictItem.getItemCode()+"] 已存在");
		}
		if(!validateDictItemName(dictItem.getItemLabel(), null, dictItem.getDictId())){
			bindingResult.rejectValue("itemLabel", "","字典元素文本 ["+dictItem.getItemLabel()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
		this.dictItemService.save(dictItem);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response);
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, DictItem dictItem, BindingResult bindingResult){
		springValidator.validate(dictItem, bindingResult);
		if(!validateDictItemCode(dictItem.getItemCode(), request.getParameter("oldItemCode"), dictItem.getDictId())){
			bindingResult.rejectValue("itemCode", "","字典元素编码 ["+dictItem.getItemCode()+"] 已存在");
		}
		if(!validateDictItemName(dictItem.getItemLabel(), request.getParameter("oldItemLabel"), dictItem.getDictId())){
			bindingResult.rejectValue("itemLabel", "","字典元素文本 ["+dictItem.getItemLabel()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		this.dictItemService.update(dictItem);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response);
	}
	
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response){
		String itemIds = request.getParameter("itemIds");
		if(StringUtils.isBlank(itemIds)){
			ServletUtils.outPrintFail(request, response, "字典元素ID不能为空");
			return ;
		}
		this.dictItemService.delete(IdUtil.splitIds(itemIds));
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response, "删除成功");
	}
	
	@RequestMapping("/doLoadTree")
	public void doLoadTree(HttpServletRequest request, HttpServletResponse response, Long id){
		List<DictItem> list = this.dictItemService.getByDictId(id,null);
		ServletUtils.outPrintMsg(request, response, this.mapper(list));
		return ;
	}
	
	@RequestMapping("/doEnable")
	public void doEnable(HttpServletRequest request, HttpServletResponse response, Long dictItemId){
		if(dictItemId == null){
			ServletUtils.outPrintFail(request, response, "字典元素ID不能为空");
			return ;
		}
		this.dictItemService.setStatus(dictItemId, CommonConstant.STATUS_USABLE);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response, "启用成功");
	}
	
	@RequestMapping("/doDisable")
	public void doDisable(HttpServletRequest request, HttpServletResponse response, Long dictItemId){
		if(dictItemId == null){
			ServletUtils.outPrintFail(request, response, "字典元素ID不能为空");
			return ;
		}
		this.dictItemService.setStatus(dictItemId, CommonConstant.STATUS_UNUSABLE);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response, "禁用成功");
	}
	
	@RequestMapping("/doValidateCode")
	public void doValidateCode(HttpServletRequest request, HttpServletResponse response, Long dictId, String itemCode){
		if(!validateDictItemCode(itemCode, null, dictId)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		ServletUtils.outPrintSuccess(request, response);
	}
	
	@RequestMapping("/doValidateLabel")
	public void doValidateValue(HttpServletRequest request, HttpServletResponse response, Long dictId){
		String itemLabel = request.getParameter("itemLabel");
		if(!validateDictItemName(itemLabel, null, dictId)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		ServletUtils.outPrintSuccess(request, response);
	}
	
	//-------------------------------------------------------------------------------------------
	private boolean validateDictItemName(String itemLabel, String oldItemLabel, Long dictId){
		if(StringUtils.isBlank(itemLabel) || dictId == null)
			return false;
		if(StringUtils.isNotBlank(oldItemLabel)){
			if(oldItemLabel.trim().equals(itemLabel))
				return true;
		}
		return this.dictItemService.getByDictIdAndLabel(dictId, itemLabel.trim()) == null;
	}
	
	private boolean validateDictItemCode(String itemCode, String oldItemCode, Long dictId){
		if(StringUtils.isBlank(itemCode))
			return false;
		if(StringUtils.isNotBlank(oldItemCode)){
			if(oldItemCode.trim().equals(itemCode))
				return true;
		}
		return this.dictItemService.getByDictIdAndItemCode(dictId, itemCode.trim()) == null;
	}
	
	private String mapper(List<DictItem> list) {
		if(list == null || list.size() < 1)
			return "[]";
		List<Map<String, Object>> itemList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(DictItem item : list){
			map = this.mapper(item);
			if(map != null)
				itemList.add(map);
		}
		return JSON.toJSONString(itemList);
	}
	
	private Map<String, Object> mapper(DictItem item) {
		if(item == null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		boolean usable = CommonConstant.STATUS_USABLE.equals(item.getStatus());
		map.put("id", item.getItemId()+"_item");
		map.put("pId", item.getDictId());
		map.put("name", usable ? item.getItemLabel() : "<span style='color:red;font-style:italic;'>"+item.getItemLabel()+"[禁用]</span>");
		map.put("nodeType", "item");
		map.put("usable", usable);
		return map;
	}
}
