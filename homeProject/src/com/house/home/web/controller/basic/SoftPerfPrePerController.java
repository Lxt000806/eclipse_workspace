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
import com.house.home.entity.basic.MainCommiPerc;
import com.house.home.entity.basic.SoftPerfPrePer;
import com.house.home.service.basic.SoftPerfPrePerService;

@Controller
@RequestMapping("/admin/softPerfPrePer")
public class SoftPerfPrePerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SoftPerfPrePerController.class);

	@Autowired
	private SoftPerfPrePerService softPerfPrePerService;

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
			HttpServletResponse response, SoftPerfPrePer softPerfPrePer) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		softPerfPrePerService.findPageBySql(page, softPerfPrePer);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 跳转到新增SoftPerfPrePer页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增软装提成预发比例页面");
		SoftPerfPrePer softPerfPrePer = null;
		if (StringUtils.isNotBlank(id)){
			softPerfPrePer = softPerfPrePerService.get(SoftPerfPrePer.class, Integer.parseInt(id));
			softPerfPrePer.setPk(null);
		}else{
			softPerfPrePer = new SoftPerfPrePer();
		}
		softPerfPrePer.setM_umState("A");
		return new ModelAndView("admin/basic/profitPerf/profitPerf_softPerfPrePer_save")
			.addObject("softPerfPrePer", softPerfPrePer);
	}
	/**
	 * 跳转到修改SoftPerfPrePer页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改软装提成预发比例页面");
		SoftPerfPrePer softPerfPrePer = null;
		if (StringUtils.isNotBlank(id)){
			softPerfPrePer = softPerfPrePerService.get(SoftPerfPrePer.class, Integer.parseInt(id));
		}else{
			softPerfPrePer = new SoftPerfPrePer();
		}
		softPerfPrePer.setM_umState("M");
		return new ModelAndView("admin/basic/profitPerf/profitPerf_softPerfPrePer_save")
			.addObject("softPerfPrePer", softPerfPrePer);
	}
	
	/**
	 * 跳转到查看SoftPerfPrePer页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看软装提成预发比例页面");
		SoftPerfPrePer softPerfPrePer = null;
		if (StringUtils.isNotBlank(id)){
			softPerfPrePer = softPerfPrePerService.get(SoftPerfPrePer.class, Integer.parseInt(id));
		}else{
			softPerfPrePer = new SoftPerfPrePer();
		}
		softPerfPrePer.setM_umState("V");
		return new ModelAndView("admin/basic/profitPerf/profitPerf_softPerfPrePer_save")
				.addObject("softPerfPrePer", softPerfPrePer);
	}
	/**
	 * 添加软装提成预发比例
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, SoftPerfPrePer softPerfPrePer){
		logger.debug("添加软装提成预发比例开始");
		try{
			if(softPerfPrePerService.hasDepartment1(softPerfPrePer)){
				ServletUtils.outPrintFail(request, response, "已存在一级部门记录，请重新选择一级部门");
				return;
			}
			softPerfPrePer.setLastUpdate(new Date());
			softPerfPrePer.setLastUpdatedBy(getUserContext(request).getCzybh());
			softPerfPrePer.setExpired("F");
			softPerfPrePer.setActionLog("ADD");
			softPerfPrePerService.save(softPerfPrePer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加软装提成预发比例失败");
		}
	}
	
	/**
	 * 修改SoftPerfPrePer
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SoftPerfPrePer softPerfPrePer){
		logger.debug("修改软装提成预发比例开始");
		try{
			if(softPerfPrePerService.hasDepartment1(softPerfPrePer)){
				ServletUtils.outPrintFail(request, response, "已存在一级部门记录，请重新选择一级部门");
				return;
			}
			softPerfPrePer.setLastUpdate(new Date());
			softPerfPrePer.setLastUpdatedBy(getUserContext(request).getCzybh());
			softPerfPrePer.setActionLog("EDIT");
			this.softPerfPrePerService.update(softPerfPrePer);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改软装提成预发比例失败");
		}
	}
	
	
	/**
	 * 删除SoftPerfPrePer
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除软装提成预发比例开始");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "软装提成预发比例pk不能为空,删除失败");
			return;
		}
		SoftPerfPrePer softPerfPrePer = softPerfPrePerService.get(SoftPerfPrePer.class, Integer.parseInt(id));
		if(softPerfPrePer == null){
			ServletUtils.outPrintFail(request, response, "选择记录不存在");
			return;
		}
		softPerfPrePerService.delete(softPerfPrePer);
		logger.debug("删除SoftPerfPrePer IDS={} 完成",id);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}
	

	/**
	 *SoftPerfPrePer导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, SoftPerfPrePer softPerfPrePer){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		softPerfPrePerService.findPageBySql(page, softPerfPrePer);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"软装提成预发比例_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
