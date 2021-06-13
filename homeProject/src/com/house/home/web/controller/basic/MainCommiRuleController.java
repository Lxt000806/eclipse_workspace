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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.MainCommiRule;
import com.house.home.entity.basic.UpgWithhold;
import com.house.home.service.basic.MainCommiRuleService;

@Controller
@RequestMapping("/admin/mainCommiRule")
public class MainCommiRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MainCommiRuleController.class);

	@Autowired
	private MainCommiRuleService mainCommiRuleService;

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
			HttpServletResponse response, MainCommiRule mainCommiRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiRuleService.findPageBySql(page, mainCommiRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goJqGridDetail")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridDetail(HttpServletRequest request,
			HttpServletResponse response, MainCommiRule mainCommiRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiRuleService.findDetailByNo(page, mainCommiRule);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * MainCommiRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mainCommiRule/mainCommiRule_list");
	}
	/**
	 * MainCommiRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mainCommiRule/mainCommiRule_code");
	}
	/**
	 * 跳转到新增MainCommiRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			MainCommiRule mcr){
		logger.debug("跳转到新增MainCommiRule页面");
		MainCommiRule mainCommiRule= null;
		if("A".equals(mcr.getM_umState())){
			mainCommiRule = new MainCommiRule();
		}else{
			mainCommiRule=mainCommiRuleService.getByNo(mcr.getNo());
		}
		mainCommiRule.setM_umState(mcr.getM_umState());
		return new ModelAndView("admin/basic/mainCommiRule/mainCommiRule_save")
			.addObject("mainCommiRule", mainCommiRule);
	}
	
	@RequestMapping("/goMainCommiRuleDetail")
	public ModelAndView goMainCommiRuleDetail(HttpServletRequest request, HttpServletResponse response,MainCommiRule mainCommiRule,String str){
		logger.debug("跳转到新增主材提成规则明细页面");
		mainCommiRule.setItemType1("ZC");
		mainCommiRule.setLastUpdatedBy(getUserContext(request).getCzybh());
		return new ModelAndView("admin/basic/mainCommiRule/mainCommiRule_mainCommiRuleItem")
			.addObject("mainCommiRule", mainCommiRule).addObject("mainCommiRuleItemPkArray",str);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, MainCommiRule mainCommiRule){
		logger.debug("添加MainCommiRule开始");
		try{
			MainCommiRule mcr = mainCommiRuleService.getByNo(mainCommiRule.getNo());
			if(mcr==null){
				mainCommiRule.setActionLog("ADD");
				mainCommiRule.setM_umState("A");
			}else{
				mainCommiRule.setActionLog("EDIT");
				mainCommiRule.setM_umState("M");
			}
			mainCommiRule.setLastUpdate(new Date());
			mainCommiRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.mainCommiRuleService.doSave(mainCommiRule);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加MainCommiRule失败");
		}
	}
	
	/**
	 * 修改MainCommiRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, MainCommiRule mainCommiRule){
		logger.debug("修改MainCommiRule开始");
		try{
			mainCommiRule.setLastUpdate(new Date());
			mainCommiRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.mainCommiRuleService.update(mainCommiRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改MainCommiRule失败");
		}
	}
	
	/**
	 * 删除MainCommiRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除MainCommiRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "MainCommiRule编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				MainCommiRule mainCommiRule = mainCommiRuleService.get(MainCommiRule.class, deleteId);
				if(mainCommiRule == null)
					continue;
				mainCommiRule.setExpired("T");
				mainCommiRuleService.update(mainCommiRule);
			}
		}
		logger.debug("删除MainCommiRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *MainCommiRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, MainCommiRule mainCommiRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainCommiRuleService.findPageBySql(page, mainCommiRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"MainCommiRule_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
