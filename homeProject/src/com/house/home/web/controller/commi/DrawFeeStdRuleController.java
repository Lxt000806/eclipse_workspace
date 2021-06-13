package com.house.home.web.controller.commi;

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
import com.house.home.entity.basic.LongFeeRule;
import com.house.home.entity.commi.CommiExpr;
import com.house.home.entity.commi.DrawFeeStdRule;
import com.house.home.service.commi.DrawFeeStdRuleService;

@Controller
@RequestMapping("/admin/drawFeeStdRule")
public class DrawFeeStdRuleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DrawFeeStdRuleController.class);

	@Autowired
	private DrawFeeStdRuleService drawFeeStdRuleService;

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
			HttpServletResponse response, DrawFeeStdRule drawFeeStdRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		drawFeeStdRuleService.findPageBySql(page, drawFeeStdRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDetailJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDetailJqGrid(HttpServletRequest request,
			HttpServletResponse response, DrawFeeStdRule drawFeeStdRule) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		drawFeeStdRuleService.goDetailJqGrid(page, drawFeeStdRule);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * DrawFeeStdRule列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/drawFeeStdRule/drawFeeStdRule_list");
	}
	/**
	 * DrawFeeStdRule查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/commi/drawFeeStdRule/drawFeeStdRule_code");
	}
	/**
	 * 跳转到新增DrawFeeStdRule页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增DrawFeeStdRule页面");
		DrawFeeStdRule drawFeeStdRule = null;
		if (StringUtils.isNotBlank(id)){
			drawFeeStdRule = drawFeeStdRuleService.get(DrawFeeStdRule.class, Integer.parseInt(id));
			drawFeeStdRule.setPk(null);
		}else{
			drawFeeStdRule = new DrawFeeStdRule();
		}
		drawFeeStdRule.setM_umState("A");
		return new ModelAndView("admin/commi/drawFeeStdRule/drawFeeStdRule_save")
			.addObject("drawFeeStdRule", drawFeeStdRule);
	}
	
	/**
	 * 跳转到修改DrawFeeStdRule页面
	 * @return
	 */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改DrawFeeStdRule页面");
		DrawFeeStdRule drawFeeStdRule = drawFeeStdRuleService.get(DrawFeeStdRule.class, pk);
		drawFeeStdRule.setM_umState("C");
		
		return new ModelAndView("admin/commi/drawFeeStdRule/drawFeeStdRule_save")
			.addObject("drawFeeStdRule", drawFeeStdRule);
	}
	
	/**
	 * 跳转到修改DrawFeeStdRule页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到修改DrawFeeStdRule页面");
		DrawFeeStdRule drawFeeStdRule = drawFeeStdRuleService.get(DrawFeeStdRule.class, pk);
		drawFeeStdRule.setM_umState("M");
		
		return new ModelAndView("admin/commi/drawFeeStdRule/drawFeeStdRule_save")
			.addObject("drawFeeStdRule", drawFeeStdRule);
	}
	
	/**
	 * 跳转到查看DrawFeeStdRule页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			Integer pk){
		logger.debug("跳转到查看DrawFeeStdRule页面");
		DrawFeeStdRule drawFeeStdRule = drawFeeStdRuleService.get(DrawFeeStdRule.class, pk);
		drawFeeStdRule.setM_umState("V");
		return new ModelAndView("admin/commi/drawFeeStdRule/drawFeeStdRule_save")
				.addObject("drawFeeStdRule", drawFeeStdRule);
	}
	
	/**
	 * 跳转到新增明细页面
	 * @return
	 */
	@RequestMapping("/goAddDetail")
	public ModelAndView goAddDetail(HttpServletRequest request, HttpServletResponse response, DrawFeeStdRule drawFeeStdRule){
		logger.debug("跳转到新增明细页面");
		return new ModelAndView("admin/commi/drawFeeStdRule/tab_detail_add")
				.addObject("drawFeeStdRule", drawFeeStdRule);
	}
	
	/**
	 * 添加DrawFeeStdRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, DrawFeeStdRule drawFeeStdRule){
		logger.debug("添加DrawFeeStdRule开始");
		try {	
			drawFeeStdRule.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			Result result = this.drawFeeStdRuleService.doSaveProc(drawFeeStdRule);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "保存失败");
		}
	}
	
	/**
	 * 修改DrawFeeStdRule
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, DrawFeeStdRule drawFeeStdRule){
		logger.debug("修改DrawFeeStdRule开始");
		try{
			drawFeeStdRule.setExpired("F");
			drawFeeStdRule.setLastUpdate(new Date());
			drawFeeStdRule.setLastUpdatedBy(getUserContext(request).getCzybh());
			drawFeeStdRule.setActionLog("EDIT");
			this.drawFeeStdRuleService.update(drawFeeStdRule);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改DrawFeeStdRule失败");
		}
	}
	
	/**
	 * 删除DrawFeeStdRule
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除DrawFeeStdRule开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "DrawFeeStdRule编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				DrawFeeStdRule drawFeeStdRule = drawFeeStdRuleService.get(DrawFeeStdRule.class, Integer.parseInt(deleteId));
				if(drawFeeStdRule == null)
					continue;
				drawFeeStdRuleService.delete(drawFeeStdRule);
			}
		}
		logger.debug("删除DrawFeeStdRule IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *DrawFeeStdRule导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, DrawFeeStdRule drawFeeStdRule){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		drawFeeStdRuleService.findPageBySql(page, drawFeeStdRule);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"绘图费公司标准规则管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/goCommiStdDesignRule")
	public ModelAndView goCommiExpr(HttpServletRequest request, HttpServletResponse response)  {
		
		return new ModelAndView("admin/commi/commiStdDesignRule/commiStdDesignRule_list");
	}

}
