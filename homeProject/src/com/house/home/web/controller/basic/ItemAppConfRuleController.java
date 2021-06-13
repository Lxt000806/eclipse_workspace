package com.house.home.web.controller.basic;

import java.util.Date;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemAppConfRule;
import com.house.home.entity.basic.ItemAppConfRuleDetail;
import com.house.home.service.basic.ItemAppConfRuleService;

@Controller
@RequestMapping("/admin/itemAppConfRule")
public class ItemAppConfRuleController extends BaseController{

	@Autowired
	private ItemAppConfRuleService itemAppConfRuleService;
	private static final Logger logger = LoggerFactory.getLogger(IntMeasureBrandController.class);
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppConfRule itemAppConfRule) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
	
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemAppConfRuleService.findPageBySql(page, itemAppConfRule,uc);
		return new WebPage<Map<String,Object>>(page);
	}
	//明细查询
	@RequestMapping("goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response,ItemAppConfRuleDetail itemAppConfRuleDetail) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		itemAppConfRuleService.findDetailPageBySql(page,itemAppConfRuleDetail);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRule_list");
	}
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRule_save");
	}
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response,String id){
		ItemAppConfRule itemAppConfRule = null;		
		if (StringUtils.isNotBlank(id)) {
			itemAppConfRule = itemAppConfRuleService.get(ItemAppConfRule.class,id);
			System.out.println(itemAppConfRule.getItemType1());
			if(itemAppConfRule != null){
				itemAppConfRule.setCustType(itemAppConfRule.getCustType() != null ? itemAppConfRule.getCustType().trim() : "");
			}
		}
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRule_update").addObject("itemAppConfRule", itemAppConfRule);
	}
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,String id){
		ItemAppConfRule itemAppConfRule = null;		
		if (StringUtils.isNotBlank(id)) {
			itemAppConfRule = itemAppConfRuleService.get(ItemAppConfRule.class,id);
			if(itemAppConfRule != null){
				itemAppConfRule.setCustType(itemAppConfRule.getCustType() != null ? itemAppConfRule.getCustType().trim() : "");
			}
		}
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRule_view").addObject("itemAppConfRule", itemAppConfRule);
	}
	
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response,String id){
		ItemAppConfRule itemAppConfRule = null;		
		if (StringUtils.isNotBlank(id)) {
			itemAppConfRule = itemAppConfRuleService.get(ItemAppConfRule.class,id);
			if(itemAppConfRule != null){
				itemAppConfRule.setCustType(itemAppConfRule.getCustType() != null ? itemAppConfRule.getCustType().trim() : "");
			}
		}
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRule_copy").addObject("itemAppConfRule", itemAppConfRule);
	}
	//明细添加
	@RequestMapping("/goDetailAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response,ItemAppConfRule itemAppConfRule){
		String lastUpdatedBy = this.getUserContext(request).getCzybh();
		itemAppConfRule.setLastUpdatedBy(lastUpdatedBy);
		System.out.println(itemAppConfRule.getItemType2Com()+"dsa");
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRuleDetail_add").addObject("itemAppConfRule", itemAppConfRule);
	}
	//明细更新
	@RequestMapping("/goDetailUpdate")
	public ModelAndView goDetailUpdate(HttpServletRequest request,HttpServletResponse response,ItemAppConfRule itemAppConfRule){
		String lastUpdatedBy = this.getUserContext(request).getCzybh();
		itemAppConfRule.setLastUpdatedBy(lastUpdatedBy);
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRuleDetail_update").addObject("itemAppConfRule", itemAppConfRule);
	}
	//明细查看
	@RequestMapping("/goDetailView")
	public ModelAndView goDetailView(HttpServletRequest request,HttpServletResponse response){
		Map<String, String[]> map = request.getParameterMap();
		return new ModelAndView("admin/basic/itemAppConfRule/itemAppConfRuleDetail_view").addObject("map", map);
	}
	//保存操作
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,ItemAppConfRule itemAppConfRule){
		try {
			itemAppConfRule.setM_umState("A");
			itemAppConfRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			System.out.println(itemAppConfRule.getPayType()+"\n"+"hahaha");
			/*
			 *使用存储过程实现编号增长
			String no = itemAppConfRuleService.getSeqNo("tItemAppConfRule");
			if(StringUtils.isNotBlank(no)){
				itemAppConfRule.setNo(no);
			}*/
			Result result = itemAppConfRuleService.doItemAppConfRuleForProc(itemAppConfRule);
			
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response,"添领料审核规则管理记录 失败 ");
		}
	}
	//更新操作
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,ItemAppConfRule itemAppConfRule){
		try {
			itemAppConfRule.setM_umState("M");
			itemAppConfRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			System.out.println(itemAppConfRule.getPayType()+"\n"+"hahaha");
			/*
			 *使用存储过程实现编号增长
			String no = itemAppConfRuleService.getSeqNo("tItemAppConfRule");
			if(StringUtils.isNotBlank(no)){
				itemAppConfRule.setNo(no);
			}*/
			Result result = itemAppConfRuleService.doItemAppConfRuleForProc(itemAppConfRule);
			
			if(result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,result.getInfo());
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response,"添加领料审核规则管理记录 失败 ");
		}
	}
	/**
	 *itemAppConfRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, ItemAppConfRule itemAppConfRule) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		itemAppConfRuleService.findPageBySql(page,itemAppConfRule,uc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"领料审核规则管理_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
}
