package com.house.home.web.controller.basic;

import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.service.basic.ConfirmCustomFiledService;

import org.springframework.web.bind.annotation.RequestMapping;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.home.entity.basic.ConfirmCustomFiled;
import com.house.home.entity.commi.DesignCommiRule;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;

@Controller
@RequestMapping("/admin/confirmCustomFiled")
public class ConfirmCustomFiledController extends BaseController{
	@Autowired
	private  ConfirmCustomFiledService confirmCustomFiledService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,ConfirmCustomFiled confirmCustomFiled) throws Exception{

		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		confirmCustomFiledService.findPageBySql(page, confirmCustomFiled);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/confirmCustomFiled/confirmCustomFiled_list");
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, ConfirmCustomFiled confirmCustomFiled) throws Exception {

		return new ModelAndView("admin/basic/confirmCustomFiled/confirmCustomFiled_save")
			.addObject("confirmCustomFiled", confirmCustomFiled);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, ConfirmCustomFiled confirmCustomFiled) throws Exception {
		
		if(StringUtils.isNotBlank(confirmCustomFiled.getCode())){
			confirmCustomFiled = confirmCustomFiledService.get(ConfirmCustomFiled.class, confirmCustomFiled.getCode());
		}
			
		return new ModelAndView("admin/basic/confirmCustomFiled/confirmCustomFiled_update")
		.addObject("confirmCustomFiled", confirmCustomFiled);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,
			HttpServletResponse response, ConfirmCustomFiled confirmCustomFiled) throws Exception {
		
		if(StringUtils.isNotBlank(confirmCustomFiled.getCode())){
			confirmCustomFiled = confirmCustomFiledService.get(ConfirmCustomFiled.class, confirmCustomFiled.getCode());
		}
		
		return new ModelAndView("admin/basic/confirmCustomFiled/confirmCustomFiled_copy")
		.addObject("confirmCustomFiled", confirmCustomFiled);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, ConfirmCustomFiled confirmCustomFiled) throws Exception {

		if(StringUtils.isNotBlank(confirmCustomFiled.getCode())){
			confirmCustomFiled = confirmCustomFiledService.get(ConfirmCustomFiled.class, confirmCustomFiled.getCode());
		}
		
		return new ModelAndView("admin/basic/confirmCustomFiled/confirmCustomFiled_view")
			.addObject("confirmCustomFiled", confirmCustomFiled);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, ConfirmCustomFiled confirmCustomFiled){
		logger.debug("验收填写项目保存开始");
		try{
			
			if(StringUtils.isNotBlank(confirmCustomFiled.getDescr())){
				List<Map<String, Object>> list = confirmCustomFiledService.checkDescrExists(confirmCustomFiled.getDescr(), "");
				if(list.size()>0){
					ServletUtils.outPrintFail(request, response, "新增验收填写项目失败,名称已经存在");
					return;
				}
			}
			
			String str = confirmCustomFiledService.getSeqNo("tConfirmCustomFiled");
			confirmCustomFiled.setCode(str);
			confirmCustomFiled.setLastUpdate(new Date());
			confirmCustomFiled.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			confirmCustomFiled.setExpired("F");
			confirmCustomFiled.setActionLog("ADD");
			
			confirmCustomFiledService.save(confirmCustomFiled);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增验收填写项目失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, ConfirmCustomFiled confirmCustomFiled){
		logger.debug("验收填写项目编辑开始");
		try{
			
			if(StringUtils.isNotBlank(confirmCustomFiled.getDescr())){
				List<Map<String, Object>> list = confirmCustomFiledService.checkDescrExists(confirmCustomFiled.getDescr(), confirmCustomFiled.getCode());
				if(list.size()>0){
					ServletUtils.outPrintFail(request, response, "编辑验收填写项目失败,名称已经存在");
					return;
				}
			}
			
			confirmCustomFiled.setLastUpdate(new Date());
			confirmCustomFiled.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			confirmCustomFiled.setActionLog("EDIT");
			
			confirmCustomFiledService.update(confirmCustomFiled);
			
			ServletUtils.outPrintSuccess(request, response);
			
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑验收填写项目失败");
		}
	}
	
}
