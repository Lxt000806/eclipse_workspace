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
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.ConFeeChg;
import com.house.home.entity.project.WorkCostDetail;
import com.house.home.service.project.ConFeeChgService;

@Controller
@RequestMapping("/admin/conFeeChg")
public class ConFeeChgController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConFeeChgController.class);

	@Autowired
	private ConFeeChgService conFeeChgService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, ConFeeChg conFeeChg) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		conFeeChgService.findPageBySql(page, conFeeChg,getUserContext(request));
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * ConFeeChg列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/conFeeChg/conFeeChg_list");
	}
	/**
	 * ConFeeChg查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/conFeeChg/conFeeChg_code");
	}
	/**
	 * 跳转到新增ConFeeChg页面
	 * @return
	 */
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request, HttpServletResponse response, 
			ConFeeChg conFeeChg){
		logger.debug("跳转到新增ConFeeChg页面");
		if("A".equals(conFeeChg.getM_umState())){
			conFeeChg.setIsCupboard("0");
			conFeeChg.setChgType("1");
			conFeeChg.setIsService("0");
			conFeeChg.setChgAmount(0.0);
		}
		return new ModelAndView("admin/project/conFeeChg/conFeeChg_save")
			.addObject("conFeeChg", conFeeChg);
	}
	/**
	 * 跳转到增减业绩页面
	 * @return
	 */
	@RequestMapping("/goPerformance")
	public ModelAndView goPerformance(HttpServletRequest request, HttpServletResponse response, 
			ConFeeChg conFeeChg){
		logger.debug("跳转到修改ConFeeChg页面");
		return new ModelAndView("admin/project/conFeeChg/conFeeChg_performance")
			.addObject("conFeeChg", conFeeChg);
	}
	/**
	 * 跳转到修改ConFeeChg页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改ConFeeChg页面");
		ConFeeChg conFeeChg = null;
		if (StringUtils.isNotBlank(id)){
			conFeeChg = conFeeChgService.get(ConFeeChg.class, Integer.parseInt(id));
		}else{
			conFeeChg = new ConFeeChg();
		}
		
		return new ModelAndView("admin/project/conFeeChg/conFeeChg_update")
			.addObject("conFeeChg", conFeeChg);
	}
	
	/**
	 * 跳转到查看ConFeeChg页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			ConFeeChg conFeeChg){
		logger.debug("跳转到查看ConFeeChg页面");
		return new ModelAndView("admin/project/conFeeChg/conFeeChg_save")
				.addObject("conFeeChg", conFeeChg);
	}
	/**
	 * 添加ConFeeChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, ConFeeChg conFeeChg){
		logger.debug("添加ConFeeChg开始");
		try{
			conFeeChg.setLastUpdatedBy(getUserContext(request).getCzybh());
			conFeeChg.setAppCzy(getUserContext(request).getCzybh());
			conFeeChg.setConfirmCzy(getUserContext(request).getCzybh());
			Result result = this.conFeeChgService.doSaveProc(conFeeChg);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,"错误信息："+result.getInfo());
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加ConFeeChg失败");
		}
	}
	
	/**
	 * 修改ConFeeChg
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, ConFeeChg conFeeChg){
		logger.debug("修改ConFeeChg开始");
		try{
			conFeeChg.setLastUpdate(new Date());
			conFeeChg.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.conFeeChgService.update(conFeeChg);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改ConFeeChg失败");
		}
	}
	
	/**
	 * 删除ConFeeChg
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除ConFeeChg开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "ConFeeChg编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				ConFeeChg conFeeChg = conFeeChgService.get(ConFeeChg.class, Integer.parseInt(deleteId));
				if(conFeeChg == null)
					continue;
				conFeeChg.setExpired("T");
				conFeeChgService.update(conFeeChg);
			}
		}
		logger.debug("删除ConFeeChg IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *ConFeeChg导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, ConFeeChg conFeeChg){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		conFeeChgService.findPageBySql(page, conFeeChg,getUserContext(request));
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"合同费用增减管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	/**
	 * 检查是否能够计算业绩
	 * @param request
	 * @param response
	 * @param conFeeChg
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPerformance")
	public List<Map<String, Object>> checkPerformance(HttpServletRequest request,
			HttpServletResponse response, ConFeeChg conFeeChg) {
		logger.debug("检查是否能够计算业绩");
		List<Map<String, Object>> list = conFeeChgService.checkPerformance(conFeeChg);
		return list;
	}
	/**
	 * 检查是否能够保存计算业绩
	 * @param request
	 * @param response
	 * @param conFeeChg
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkSavePerformance")
	public List<Map<String, Object>> checkSavePerformance(HttpServletRequest request,
			HttpServletResponse response, ConFeeChg conFeeChg) {
		logger.debug("检查是否能够保存计算业绩");
		List<Map<String, Object>> list = conFeeChgService.checkSavePerformance(conFeeChg);
		return list;
	}
	/**
	 * 计算业绩
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doPerformance")
	public void doPerformance(HttpServletRequest request, HttpServletResponse response, ConFeeChg conFeeChg){
		logger.debug("计算业绩");
		try{
			conFeeChg.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.conFeeChgService.doPerformance(conFeeChg);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "计算业绩");
		}
	}
}
