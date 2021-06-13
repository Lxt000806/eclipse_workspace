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

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.project.CupMeasure;
import com.house.home.service.project.CupMeasureService;

@Controller
@RequestMapping("/admin/cupInstallConfirm")
public class CupMeasureController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CupMeasureController.class);

	@Autowired
	private CupMeasureService cupMeasureService;

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
			HttpServletResponse response, CupMeasure cupMeasure) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		cupMeasureService.findPageBySql(page, cupMeasure);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CupMeasure列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/project/cupMeasure/cupMeasure_list");
	}
	/**
	 * 跳转到查看CupMeasure页面
	 * @return
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到查看CupMeasure页面");
		String m_umState=request.getParameter("m_umState").toString();
		JSONObject jsonObject=JSONObject.parseObject(request.getParameter("map"));
		jsonObject.put("m_umState", m_umState);
		return new ModelAndView("admin/project/cupMeasure/cupMeasure_confirm")
				.addObject("map", jsonObject);
	}
	/**
	 * 跳转到确认CupMeasure页面
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
	public void doConfirm(HttpServletRequest request, HttpServletResponse response, CupMeasure cupMeasure){
		logger.debug("修改CupMeasure开始");
		try{
			cupMeasure.setConfirmCzy(getUserContext(request).getEmnum());
			cupMeasure.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.cupMeasureService.confirm(cupMeasure);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CupMeasure失败");
		}
	}
	
	/**
	 * 删除CupMeasure
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CupMeasure开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CupMeasure编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CupMeasure cupMeasure = cupMeasureService.get(CupMeasure.class, deleteId);
				if(cupMeasure == null)
					continue;
				cupMeasure.setExpired("T");
				cupMeasureService.update(cupMeasure);
			}
		}
		logger.debug("删除CupMeasure IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CupMeasure导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CupMeasure cupMeasure){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		cupMeasureService.findPageBySql(page, cupMeasure);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"橱柜安装确认_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
