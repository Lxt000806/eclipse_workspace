package com.house.home.web.controller.commi;

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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.commi.CommiStdDesignRule;
import com.house.home.service.commi.CommiStdDesignRuleService;

@Controller
@RequestMapping("/admin/commiStdDesignRule")
public class CommiStdDesignRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CommiStdDesignRuleController.class);

	@Autowired
	private CommiStdDesignRuleService commiStdDesignRuleService;

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
			HttpServletResponse response, CommiStdDesignRule commiStdDesignRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiStdDesignRuleService.findPageBySql(page, commiStdDesignRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * CommiStdDesignRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/commiStdDesignRule/commiStdDesignRule_code");
	}
	/**
	 * 跳转到新增CommiStdDesignRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增提成标准设计费规则页面");
		CommiStdDesignRule commiStdDesignRule = null;
		if (StringUtils.isNotBlank(id)){
			commiStdDesignRule = commiStdDesignRuleService.get(CommiStdDesignRule.class, Integer.parseInt(id));
			commiStdDesignRule.setPk(null);
		}else{
			commiStdDesignRule = new CommiStdDesignRule();
		}
		commiStdDesignRule.setM_umState("A");
		return new ModelAndView("admin/commi/commiStdDesignRule/commiStdDesignRule_save")
			.addObject("commiStdDesignRule", commiStdDesignRule);
	}
	/**
	 * 跳转到修改CommiStdDesignRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改提成标准设计费规则页面");
		CommiStdDesignRule commiStdDesignRule = null;
		if (StringUtils.isNotBlank(id)){
			commiStdDesignRule = commiStdDesignRuleService.get(CommiStdDesignRule.class, Integer.parseInt(id));
		}else{
			commiStdDesignRule = new CommiStdDesignRule();
		}
		commiStdDesignRule.setM_umState("M");
		return new ModelAndView("admin/commi/commiStdDesignRule/commiStdDesignRule_save")
			.addObject("commiStdDesignRule", commiStdDesignRule);
	}
	
	/**
	 * 跳转到查看CommiStdDesignRule页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看提成标准设计费规则页面");
		CommiStdDesignRule commiStdDesignRule = null;
		if (StringUtils.isNotBlank(id)){
			commiStdDesignRule = commiStdDesignRuleService.get(CommiStdDesignRule.class, Integer.parseInt(id));
		}else{
			commiStdDesignRule = new CommiStdDesignRule();
		}
		commiStdDesignRule.setM_umState("V");
		return new ModelAndView("admin/commi/commiStdDesignRule/commiStdDesignRule_save")
				.addObject("commiStdDesignRule", commiStdDesignRule);
	}
	/**
	 * 添加CommiStdDesignRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CommiStdDesignRule commiStdDesignRule){
		logger.debug("添加CommiStdDesignRule开始");
		try{
			if(commiStdDesignRuleService.checkExistDescr(commiStdDesignRule)){
				ServletUtils.outPrintFail(request, response, "名称已存在,新增失败");
				return;
			}
			commiStdDesignRule.setLastUpdate(new Date());
			commiStdDesignRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			commiStdDesignRule.setExpired("F");
			commiStdDesignRule.setActionLog("ADD");
			this.commiStdDesignRuleService.save(commiStdDesignRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加提成标准设计费规则失败");
		}
	}
	
	/**
	 * 修改CommiStdDesignRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CommiStdDesignRule commiStdDesignRule){
		logger.debug("修改CommiStdDesignRule开始");
		try{
			if(commiStdDesignRule.getPk() == null){
				ServletUtils.outPrintFail(request, response, "提成标准设计费PK不能为空,修改失败");
				return;
			}
			CommiStdDesignRule modifiCommiStdDesignRule = commiStdDesignRuleService.get(CommiStdDesignRule.class, commiStdDesignRule.getPk());
			if(modifiCommiStdDesignRule == null){
				ServletUtils.outPrintFail(request, response, "提成标准设计费记录不存在,修改失败");
				return;
			}
			if(commiStdDesignRuleService.checkExistDescr(commiStdDesignRule)){
				ServletUtils.outPrintFail(request, response, "名称已存,修改失败");
				return;
			}
			modifiCommiStdDesignRule.setDescr(commiStdDesignRule.getDescr());
			modifiCommiStdDesignRule.setStdDesignFeeAmount(commiStdDesignRule.getStdDesignFeeAmount());
			modifiCommiStdDesignRule.setStdDesignFeePrice(commiStdDesignRule.getStdDesignFeePrice());
			modifiCommiStdDesignRule.setLastUpdate(new Date());
			modifiCommiStdDesignRule.setExpired(commiStdDesignRule.getExpired());
			modifiCommiStdDesignRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			modifiCommiStdDesignRule.setActionLog("EDIT");
			this.commiStdDesignRuleService.update(modifiCommiStdDesignRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改提成标准设计费失败");
		}
	}

	
	/**
	 * 删除CommiStdDesignRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除CommiStdDesignRule开始");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "提成标准设计费规则pk不能为空,删除失败");
			return;
		}
		CommiStdDesignRule commiStdDesignRule = commiStdDesignRuleService.get(CommiStdDesignRule.class, Integer.parseInt(id));
		if(commiStdDesignRule == null){
			ServletUtils.outPrintFail(request, response, "提成标准设计费规则记录不能为空,删除失败");
			return;
		}
		if(commiStdDesignRuleService.checkExistDrawFeeStdRuleByCommiStdDesignRulePK(Integer.parseInt(id))){
			ServletUtils.outPrintFail(request, response, "绘图费公司标准规则已使用该规则,删除失败");
			return;
		}
		
		commiStdDesignRuleService.delete(commiStdDesignRule);
		logger.debug("删除commiStdDesignRule IDS={} 完成",id);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CommiStdDesignRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CommiStdDesignRule commiStdDesignRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		commiStdDesignRuleService.findPageBySql(page, commiStdDesignRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"CommiStdDesignRule_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
