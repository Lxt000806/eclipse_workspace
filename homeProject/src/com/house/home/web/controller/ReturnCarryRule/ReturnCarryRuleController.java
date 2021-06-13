package com.house.home.web.controller.ReturnCarryRule;

import java.util.Date;
import java.util.List;
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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.ReturnCarryRule;
import com.house.home.service.basic.ReturnCarryRuleService;

@Controller
@RequestMapping("/admin/ReturnCarryRule")
public class ReturnCarryRuleController extends BaseController { 	
	@Autowired
	private ReturnCarryRuleService returnCarryRuleService;

	/**	
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*
	 * 新增跳转退货搬运费表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response, ReturnCarryRule returnCarryRule) throws Exception {
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRule_list").addObject("returnCarryRule", returnCarryRule).
				addObject("czybh", this.getUserContext(request).getCzybh());
	}
	/*this.getUserContext(request).getCzybh()   调用权限*/
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,ReturnCarryRule returnCarryRule) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		returnCarryRuleService.findPageBySql(page, returnCarryRule);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goItemJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getItemJqGrid(HttpServletRequest request,
			HttpServletResponse response,String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		returnCarryRuleService.findItemPageBySql(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	/*
	 *退货搬运费保存 
	 * */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增材料套餐包页面");
		ReturnCarryRule returnCarryRule = new ReturnCarryRule();		
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRule_save")
			.addObject("returnCarryRule", returnCarryRule).addObject("czy", this.getUserContext(request).getCzybh());
	}
	
	/**
	 *退货搬运费保存 
	 *
	 */
	@RequestMapping("/doreturnCarryRuleSave")
	public void doPurchaseSave(HttpServletRequest request,HttpServletResponse response,ReturnCarryRule returnCarryRule){
		logger.debug("退货搬运费新增开始");		
		try {
			returnCarryRule.setLastUpdate(new Date());				
			returnCarryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			returnCarryRule.setLastUpdate(new Date());
			returnCarryRule.setM_umState("A");
			returnCarryRule.setExpired("F");
			System.out.println(returnCarryRule.getCalType()+"ss"+"\n");
			ReturnCarryRule ino = this.returnCarryRuleService.getByNo(returnCarryRule.getNo(),returnCarryRule.getNo1());
			
			if (ino!=null){
				ServletUtils.outPrintFail(request, response, "编号重复！");
				return;
			}	
		
			Result result = this.returnCarryRuleService.doReturnCarryRuleSave(returnCarryRule);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "退货搬运费新增失败");
		}
	}
	//编辑
	@RequestMapping("/doreturnCarryRuleUpdate")
	public void doreturnCarryRuleUpdate(HttpServletRequest request,HttpServletResponse response,ReturnCarryRule returnCarryRule){
		logger.debug("退货搬运费编辑开始");		
		try {
			returnCarryRule.setLastUpdate(new Date());				
			returnCarryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			returnCarryRule.setLastUpdate(new Date());
			returnCarryRule.setM_umState("M");
			returnCarryRule.setExpired("F"); 		
			ReturnCarryRule ino = this.returnCarryRuleService.getByNo(returnCarryRule.getNo(),returnCarryRule.getNo1());
			
			if (ino!=null){
				ServletUtils.outPrintFail(request, response, "编号重复！");
				return;
			}	
		
			Result result = this.returnCarryRuleService.doReturnCarryRuleSave(returnCarryRule);
				if (result.isSuccess()){
					ServletUtils.outPrintSuccess(request, response,"保存成功");
				}else{
					ServletUtils.outPrintFail(request, response,result.getInfo());
				}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "退货搬运费新增失败");
		}
	}


//	/**
//	 * 删除
//	 * @param request
//	 * @param response
//	 * @param roleId
//	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String deleteIds){
		logger.debug("删除材料分类开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "材料分类不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ReturnCarryRule returnCarryRule = this.returnCarryRuleService.get(ReturnCarryRule.class, deleteId);
				returnCarryRule.setExpired("T");
				returnCarryRule.setM_umState("M");			
				returnCarryRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				Result result = returnCarryRuleService.deleteForProc(returnCarryRule);
				if ("1".equals(result.getCode())){
					ServletUtils.outPrintSuccess(request, response, result.getInfo());
				}else{
					ServletUtils.outPrintFail(request, response, result.getInfo());
				}
			}
		}
		logger.debug(" IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/*
	 *退货搬运费编辑页面 
	 * */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到退货搬运费规则管理页面");
		ReturnCarryRule returnCarryRule = null;
		if (StringUtils.isNotBlank(id)){
			returnCarryRule = returnCarryRuleService.get(ReturnCarryRule.class, id);
		}else{
			returnCarryRule = new ReturnCarryRule();
		}
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRule_update")
			.addObject("returnCarryRule", returnCarryRule).addObject("czy", this.getUserContext(request).getCzybh());
	}

	/*
	 *退货搬运费查看页面 
	 * */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到退货搬运费规则管理页面");
		ReturnCarryRule returnCarryRule = null;
		if (StringUtils.isNotBlank(id)){
			returnCarryRule = returnCarryRuleService.get(ReturnCarryRule.class, id);
		}else{
			returnCarryRule = new ReturnCarryRule();
		}
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRule_View")
			.addObject("returnCarryRule", returnCarryRule).addObject("czy", this.getUserContext(request).getCzybh());
	}
	//匹配材料新增
	@RequestMapping("/goItemSave")
	public ModelAndView goWorkAdd(HttpServletRequest request,HttpServletResponse response){
		logger.debug("匹配材料增加");
		String czy = this.getUserContext(request).getCzybh();
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRuleItem_add").addObject("czy", czy);
	}
	@RequestMapping("/goItemUpdate")
	public ModelAndView goWorkUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("匹配材料修改");
		Map<String,String[]> map = request.getParameterMap();
		String czy = this.getUserContext(request).getCzybh();
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRuleItem_update").addObject("map", map).addObject("czy",czy);		
	}
	@RequestMapping("/goItemView")
	public ModelAndView goWorkView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("匹配材料察看");
		Map<String, String[]> map = request.getParameterMap();
		return new ModelAndView("admin/basic/returnCarryRule/ReturnCarryRuleItem_detail").addObject("map", map);	
	}
}
