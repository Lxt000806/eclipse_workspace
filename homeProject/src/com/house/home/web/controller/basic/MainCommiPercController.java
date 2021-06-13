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
import com.house.home.service.basic.MainCommiPercService;

@Controller
@RequestMapping("/admin/mainCommiPerc")
public class MainCommiPercController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MainCommiPercController.class);

	@Autowired
	private MainCommiPercService mainCommiPercService;

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
			HttpServletResponse response, MainCommiPerc mainCommiPerc) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiPercService.findPageBySql(page, mainCommiPerc);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * MainCommiPerc列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mainCommiPerc/mainCommiPerc_list");
	}
	/**
	 * MainCommiPerc查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mainCommiPerc/mainCommiPerc_code");
	}
	/**
	 * 跳转到新增MainCommiPerc页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增MainCommiPerc页面");
		MainCommiPerc mainCommiPerc = null;
		if (StringUtils.isNotBlank(id)){
			mainCommiPerc = mainCommiPercService.get(MainCommiPerc.class, id);
			mainCommiPerc.setCode(null);
		}else{
			mainCommiPerc = new MainCommiPerc();
		}
		mainCommiPerc.setM_umState("A");
		return new ModelAndView("admin/finance/mainCommi/mainCommi_mainCommiPerc_save")
			.addObject("mainCommiPerc", mainCommiPerc);
	}
	/**
	 * 跳转到修改MainCommiPerc页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改MainCommiPerc页面");
		MainCommiPerc mainCommiPerc = null;
		if (StringUtils.isNotBlank(id)){
			mainCommiPerc = mainCommiPercService.get(MainCommiPerc.class, id);
		}else{
			mainCommiPerc = new MainCommiPerc();
		}
		mainCommiPerc.setM_umState("M");
		return new ModelAndView("admin/finance/mainCommi/mainCommi_mainCommiPerc_save")
			.addObject("mainCommiPerc", mainCommiPerc);
	}
	
	/**
	 * 跳转到查看MainCommiPerc页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看MainCommiPerc页面");
		MainCommiPerc mainCommiPerc = mainCommiPercService.get(MainCommiPerc.class, id);
		mainCommiPerc.setM_umState("V");
		return new ModelAndView("admin/finance/mainCommi/mainCommi_mainCommiPerc_save")
				.addObject("mainCommiPerc", mainCommiPerc);
	}
	/**
	 * 添加MainCommiPerc
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, MainCommiPerc mainCommiPerc){
		logger.debug("添加MainCommiPerc开始");
		try{
			mainCommiPerc.setLastUpdate(new Date());
			mainCommiPerc.setLastUpdatedBy(getUserContext(request).getCzybh());
			mainCommiPerc.setExpired("F");
			mainCommiPerc.setActionLog("ADD");
			if(mainCommiPercService.checkExistMainCommiPerc(mainCommiPerc)){
				ServletUtils.outPrintFail(request, response, "编号或名称已存在，请重新填写");
				return;
			}
			this.mainCommiPercService.save(mainCommiPerc);
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加主材提成比例失败");
		}
	}
	
	/**
	 * 修改MainCommiPerc
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, MainCommiPerc mainCommiPerc){
		logger.debug("修改MainCommiPerc开始");
		try{
			mainCommiPerc.setLastUpdate(new Date());
			mainCommiPerc.setLastUpdatedBy(getUserContext(request).getCzybh());
			if(mainCommiPercService.checkExistMainCommiPerc(mainCommiPerc)){
				ServletUtils.outPrintFail(request, response, "编号或名称已存在，请重新填写");
				return;
			}
			this.mainCommiPercService.update(mainCommiPerc);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改主材提成比例失败");
		}
	}
	
	/**
	 * 删除MainCommiPerc
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除MainCommiPerc开始");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "主材提成比例编号不能为空,删除失败");
			return;
		}
		MainCommiPerc mainCommiPerc = mainCommiPercService.get(MainCommiPerc.class, id);
		if(mainCommiPerc == null){
			ServletUtils.outPrintFail(request, response, "选择记录不存在");
			return;
		}
		mainCommiPercService.delete(mainCommiPerc);
		logger.debug("删除MainCommiPerc IDS={} 完成",id);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *MainCommiPerc导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, MainCommiPerc mainCommiPerc){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainCommiPercService.findPageBySql(page, mainCommiPerc);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"MainCommiPerc_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
