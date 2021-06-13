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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ConDayCalcRule;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.project.WorkType12;
import com.house.home.service.basic.ConDayCalcRuleService;

@Controller
@RequestMapping("/admin/conDayCalcRule")
public class ConDayCalcRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConDayCalcRuleController.class);

	@Autowired
	private ConDayCalcRuleService conDayCalcRuleService;

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
			HttpServletResponse response, ConDayCalcRule conDayCalcRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		conDayCalcRuleService.findPageBySql(page, conDayCalcRule);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ConDayCalcRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/conDayCalcRule/conDayCalcRule_list");
	}
	/**
	 * ConDayCalcRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/conDayCalcRule/conDayCalcRule_code");
	}
	/**
	 * 跳转到新增ConDayCalcRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增ConDayCalcRule页面");
		ConDayCalcRule conDayCalcRule = null;
		if (StringUtils.isNotBlank(id)){
			conDayCalcRule = conDayCalcRuleService.get(ConDayCalcRule.class, Integer.parseInt(id));
			conDayCalcRule.setPk(null);
		}else{
			conDayCalcRule = new ConDayCalcRule();
		}
		conDayCalcRule.setM_umState("A");
		return new ModelAndView("admin/basic/conDayCalcRule/conDayCalcRule_save")
			.addObject("conDayCalcRule", conDayCalcRule);
	}
	/**
	 * 跳转到修改ConDayCalcRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ConDayCalcRule页面");
		ConDayCalcRule conDayCalcRule = null;
		if (StringUtils.isNotBlank(id)){
			conDayCalcRule = conDayCalcRuleService.get(ConDayCalcRule.class, Integer.parseInt(id));
		}else{
			conDayCalcRule = new ConDayCalcRule();
		}
		conDayCalcRule.setM_umState("M");
		return new ModelAndView("admin/basic/conDayCalcRule/conDayCalcRule_save")
			.addObject("conDayCalcRule", conDayCalcRule);
	}
	
	/**
	 * 跳转到查看ConDayCalcRule页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看ConDayCalcRule页面");
		ConDayCalcRule conDayCalcRule = conDayCalcRuleService.get(ConDayCalcRule.class, Integer.parseInt(id));
		conDayCalcRule.setM_umState("V");
		return new ModelAndView("admin/basic/conDayCalcRule/conDayCalcRule_save")
				.addObject("conDayCalcRule", conDayCalcRule);
	}
	/**
	 * 添加ConDayCalcRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	@ResponseBody
	public void doSave(HttpServletRequest request, HttpServletResponse response, ConDayCalcRule conDayCalcRule){
		logger.debug("添加ConDayCalcRule开始");
		try{
			conDayCalcRule.setActionLog("ADD");
			conDayCalcRule.setExpired("F");
			conDayCalcRule.setLastUpdate(new Date());
			conDayCalcRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			List<Map<String,Object>> isRepeatedList=this.conDayCalcRuleService.isRepeated(conDayCalcRule);
			if(isRepeatedList.size()>0 && isRepeatedList!=null){
				String workerClassifyDescr= isRepeatedList.get(0).get("WorkerClassifyDescr").toString();
				String workType12Descr= isRepeatedList.get(0).get("WorkType12Descr").toString();
				ServletUtils.outPrintFail(request, response, "已存在【"+workerClassifyDescr+"】,【"+workType12Descr+"】的记录");
			}else{
				this.conDayCalcRuleService.save(conDayCalcRule);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加ConDayCalcRule失败");
		}
	}
	
	/**
	 * 修改ConDayCalcRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ConDayCalcRule conDayCalcRule){
		logger.debug("修改ConDayCalcRule开始");
		try{
			conDayCalcRule.setActionLog("EDIT");
			conDayCalcRule.setLastUpdate(new Date());
			conDayCalcRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			List<Map<String,Object>> isRepeatedList=this.conDayCalcRuleService.isRepeated(conDayCalcRule);
			if(isRepeatedList.size()>0 && isRepeatedList!=null){
				String workerClassifyDescr= isRepeatedList.get(0).get("WorkerClassifyDescr").toString();
				String workType12Descr= isRepeatedList.get(0).get("WorkType12Descr").toString();
				ServletUtils.outPrintFail(request, response, "已存在【"+workerClassifyDescr+"】,【"+workType12Descr+"】的记录");
			}else{
				this.conDayCalcRuleService.update(conDayCalcRule);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "修改ConDayCalcRule失败");
		}
	}
	
	/**
	 * 删除ConDayCalcRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ConDayCalcRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ConDayCalcRule编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ConDayCalcRule conDayCalcRule = conDayCalcRuleService.get(ConDayCalcRule.class, Integer.parseInt(deleteId));
				if(conDayCalcRule == null)
					continue;
				conDayCalcRuleService.delete(conDayCalcRule);
			}
		}
		logger.debug("删除ConDayCalcRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ConDayCalcRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ConDayCalcRule conDayCalcRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		conDayCalcRuleService.findPageBySql(page, conDayCalcRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工期计算规则_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
