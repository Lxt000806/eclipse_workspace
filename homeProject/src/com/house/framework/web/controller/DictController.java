package com.house.framework.web.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.house.framework.service.DictService;

@Controller
@RequestMapping("/admin/dict")
public class DictController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(DictController.class);

	@Autowired
	private DictService dictService;
	@Resource(name = "dictCacheManager")
	private ICacheManager dictCacheManager;
	
	@RequestMapping("/goSysDictMain")
	public ModelAndView goSysDictMain(HttpServletRequest request, HttpServletResponse response, Long initId){
		logger.debug("跳转到菜单框架页面");
		
		return new ModelAndView("system/dict/dict_main")
					.addObject("initId", initId)
					.addObject("dictType", CommonConstant.SYSTEM_LEVEL);
	}
	
	@RequestMapping("/goUserDictMain")
	public ModelAndView goUserDictMain(HttpServletRequest request, HttpServletResponse response, Long initId){
		logger.debug("跳转到菜单框架页面");
		
		return new ModelAndView("system/dict/dict_main")
					.addObject("initId", initId)
					.addObject("dictType", CommonConstant.USER_LEVEL);
	}
	
	/**
	 * 跳转到菜单树页面，进行菜单维护
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goTree")
	public ModelAndView goTree(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到菜单树页面");
		String dictType = request.getParameter("dictType");
		
		List<Dict> dictList = this.dictService.getByDictType(dictType, null);
		
		String dictJson = "[]";
		if(null != dictList && dictList.size()>0)
			dictJson = JSON.toJSONString(dictList);
		
		return new ModelAndView("system/dict/dict_tree")
					.addObject("dictJson", dictJson)
					.addObject("defaultId", request.getParameter("defaultId"))
					.addObject("dictType", dictType)
					.addObject("unUsable", CommonConstant.STATUS_UNUSABLE);
	}
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Dict> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,Dict dict) throws Exception {
		
		Page<Dict> page = this.newPageForJqGrid(request);
		this.dictService.findPage(page, dict);
		return new WebPage<Dict>(page);
	}
	/**
	 * 跳转到字典列表页面
	 * @return
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response, Dict dict){
		
		return new ModelAndView("system/dict/dict_list")
					.addObject("dict", dict)
					.addObject("inUse", CommonConstant.STATUS_USABLE);
	}
	
	/**
	 * 跳转到新增字典页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("system/dict/dict_save")
					.addObject("dictType", request.getParameter("dictType"));
	}
	
	/**
	 * 跳转到修改字典页面
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, Long dictId){
		
		Dict dict = this.dictService.get(dictId);
		
		return new ModelAndView("system/dict/dict_update")
					.addObject("dict", dict);
	}
	
	
	//-------------------------------------------------------------------------------------------------
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Dict dict, BindingResult bindingResult){
		springValidator.validate(dict, bindingResult);
		if(!validateDictName(dict.getDictName(), null)){
			bindingResult.rejectValue("dictName", "","字典名称 ["+dict.getDictName()+"] 已存在");
		}
		
		if(!validateDictCode(dict.getDictCode(), null)){
			bindingResult.rejectValue("dictCode", "","字典编码 ["+dict.getDictCode()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		this.dictService.save(dict);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response);
		
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Dict dict, BindingResult bindingResult){
		springValidator.validate(dict, bindingResult);
		if(!validateDictName(dict.getDictName(), request.getParameter("oldDictName"))){
			bindingResult.rejectValue("dictName", "","字典名称 ["+dict.getDictName()+"] 已存在");
		}
		if(!validateDictCode(dict.getDictCode(), request.getParameter("oldDictCode"))){
			bindingResult.rejectValue("dictCode", "","字典编码 ["+dict.getDictCode()+"] 已存在");
		}
		if(bindingResult.hasErrors()){
			ServletUtils.outPrintFail(request, response, ServletUtils.convertErrors2Msg(bindingResult));
			return;
		}
		
		this.dictService.update(dict);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response);
	}
	
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "字典ID不能为空");
			return ;
		}
		this.dictService.delete(IdUtil.splitIds(deleteIds));
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response, "删除成功");
	}

	
	@RequestMapping("/doEnable")
	public void doEnable(HttpServletRequest request, HttpServletResponse response, Long dictId){
		if(dictId == null){
			ServletUtils.outPrintFail(request, response, "字典ID不能为空");
			return ;
		}
		this.dictService.setStatus(dictId, CommonConstant.STATUS_USABLE);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response, "启用成功");
	}
	
	@RequestMapping("/doDisable")
	public void doDisable(HttpServletRequest request, HttpServletResponse response, Long dictId){
		if(dictId == null){
			ServletUtils.outPrintFail(request, response, "字典ID不能为空");
			return ;
		}
		this.dictService.setStatus(dictId, CommonConstant.STATUS_UNUSABLE);
		dictCacheManager.refresh();
		ServletUtils.outPrintSuccess(request, response, "禁用成功");
	}
	
	@RequestMapping("/doValidate")
	public void doValidate(HttpServletRequest request, HttpServletResponse response){
		if(!validateDictName(request.getParameter("dictName"), null)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		
		if(!validateDictCode(request.getParameter("dictCode"), null)){
			ServletUtils.outPrintFail(request, response, "已存在");
			return ;
		}
		ServletUtils.outPrintSuccess(request, response, "成功");
		return ;
	}
	
	//-------------------------------------------------------------------------------------------
	private boolean validateDictName(String dictName, String oldDictName){
		if(StringUtils.isBlank(dictName))
			return false;
		if(StringUtils.isNotBlank(oldDictName)){
			if(oldDictName.trim().equals(dictName))
				return true;
		}
		return this.dictService.getByDictName(dictName.trim()) == null;
	}
	
	private boolean validateDictCode(String dictCode, String oldDictCode){
		if(StringUtils.isBlank(dictCode))
			return false;
		if(StringUtils.isNotBlank(oldDictCode)){
			if(oldDictCode.trim().equals(dictCode))
				return true;
		}
		return this.dictService.getByDictCode(dictCode.trim()) == null;
	}
}
