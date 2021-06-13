package com.house.home.web.controller.project;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.WaterAreaCfm;
import com.house.home.service.project.WaterAreaCfmService;

@Controller
@RequestMapping("/admin/waterAreaCfm")
public class WaterAreaCfmController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WaterAreaCfmController.class);

	@Autowired
	private WaterAreaCfmService waterAreaCfmService;

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
			HttpServletResponse response, WaterAreaCfm waterAreaCfm) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		waterAreaCfmService.findPageBySql(page, waterAreaCfm);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * WaterAreaCfm列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView("admin/project/waterAreaCfm/waterAreaCfm_list");
	}
	/**
	 * 跳转到查看WaterAreaCfm页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看WaterAreaCfm页面");
		String m_umState=request.getParameter("m_umState").toString();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		jsonObject.put("m_umState", m_umState);
		return new ModelAndView("admin/project/waterAreaCfm/waterAreaCfm_confirm")
				.addObject("map", jsonObject);
	}
	/**
	 * 跳转到确认WaterAreaCfm页面
	 * @return
	 */
	@RequestMapping("/goConfirm")
	public ModelAndView goConfirm(HttpServletRequest request, HttpServletResponse response){
		return goView(request, response);
	}
	
	/**
	 * 确认
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doConfirm")
	public void doConfirm(HttpServletRequest request, HttpServletResponse response, WaterAreaCfm wac){
		logger.debug("确认");
		try{
			WaterAreaCfm waterAreaCfm=new WaterAreaCfm();
			waterAreaCfm = waterAreaCfmService.get(WaterAreaCfm.class, wac.getCustCode());
			waterAreaCfm.setStatus("2");
			waterAreaCfm.setActionLog("EDIT");
			waterAreaCfm.setConfirmDate(new Date());
			waterAreaCfm.setConfirmCzy(getUserContext(request).getCzybh());
			waterAreaCfm.setConfirmRemarks(wac.getConfirmRemarks().replaceAll("	", "kk").replaceAll(" ", "hh"));
			waterAreaCfm.setLastUpdate(new Date());
			waterAreaCfm.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.waterAreaCfmService.update(waterAreaCfm);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改WaterAreaCfm失败");
		}
	}
	
	/**
	 *WaterAreaCfm导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WaterAreaCfm waterAreaCfm){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		waterAreaCfmService.findPageBySql(page, waterAreaCfm);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"防水施工面积确认_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
