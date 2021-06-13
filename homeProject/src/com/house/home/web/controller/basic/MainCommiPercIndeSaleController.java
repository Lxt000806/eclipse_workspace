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
import com.house.home.entity.basic.MainCommiPercIndeSale;
import com.house.home.entity.project.PrjWithHold;
import com.house.home.service.basic.MainCommiPercIndeSaleService;

@Controller
@RequestMapping("/admin/mainCommiPercIndeSale")
public class MainCommiPercIndeSaleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MainCommiPercIndeSaleController.class);

	@Autowired
	private MainCommiPercIndeSaleService mainCommiPercIndeSaleService;

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
			HttpServletResponse response, MainCommiPercIndeSale mainCommiPercIndeSale) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		mainCommiPercIndeSaleService.findPageBySql(page, mainCommiPercIndeSale);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * MainCommiPercIndeSale列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mainCommiPercIndeSale/mainCommiPercIndeSale_list");
	}
	/**
	 * MainCommiPercIndeSale查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/mainCommiPercIndeSale/mainCommiPercIndeSale_code");
	}
	/**
	 * 跳转到新增MainCommiPercIndeSale页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增MainCommiPercIndeSale页面");
		MainCommiPercIndeSale mainCommiPercIndeSale = null;
		if (StringUtils.isNotBlank(id)){
			mainCommiPercIndeSale = mainCommiPercIndeSaleService.get(MainCommiPercIndeSale.class, Integer.parseInt(id));
			mainCommiPercIndeSale.setPk(null);
		}else{
			mainCommiPercIndeSale = new MainCommiPercIndeSale();
		}
		mainCommiPercIndeSale.setM_umState("A");
		return new ModelAndView("admin/finance/mainCommi/mainCommi_mainCommiPercIndeSale_save")
			.addObject("mainCommiPercIndeSale", mainCommiPercIndeSale);
	}
	/**
	 * 跳转到修改MainCommiPercIndeSale页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改MainCommiPercIndeSale页面");
		MainCommiPercIndeSale mainCommiPercIndeSale = null;
		if (StringUtils.isNotBlank(id)){
			mainCommiPercIndeSale = mainCommiPercIndeSaleService.get(MainCommiPercIndeSale.class, Integer.parseInt(id));
		}else{
			mainCommiPercIndeSale = new MainCommiPercIndeSale();
		}
		mainCommiPercIndeSale.setM_umState("M");
		return new ModelAndView("admin/finance/mainCommi/mainCommi_mainCommiPercIndeSale_save")
			.addObject("mainCommiPercIndeSale", mainCommiPercIndeSale);
	}
	
	/**
	 * 跳转到查看MainCommiPercIndeSale页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看MainCommiPercIndeSale页面");
		MainCommiPercIndeSale mainCommiPercIndeSale = mainCommiPercIndeSaleService.get(MainCommiPercIndeSale.class, Integer.parseInt(id));
		mainCommiPercIndeSale.setM_umState("V");
		return new ModelAndView("admin/finance/mainCommi/mainCommi_mainCommiPercIndeSale_save")
				.addObject("mainCommiPercIndeSale", mainCommiPercIndeSale);
	}
	/**
	 * 添加MainCommiPercIndeSale
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, MainCommiPercIndeSale mainCommiPercIndeSale){
		logger.debug("添加独立销售提成比例开始");
		try{
			if(mainCommiPercIndeSaleService.checkExistMainCommiPercIndeSale(mainCommiPercIndeSale)){
				ServletUtils.outPrintFail(request, response, "存在相同编号和销售类型记录，请重新填写");
			}
			mainCommiPercIndeSale.setLastUpdate(new Date());
			mainCommiPercIndeSale.setLastUpdatedBy(getUserContext(request).getCzybh());
			mainCommiPercIndeSale.setExpired("F");
			mainCommiPercIndeSale.setActionLog("ADD");	
			this.mainCommiPercIndeSaleService.save(mainCommiPercIndeSale);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加独立销售提成比例失败");
		}
	}
	
	/**
	 * 修改MainCommiPercIndeSale
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, MainCommiPercIndeSale mainCommiPercIndeSale){
		logger.debug("修改MainCommiPercIndeSale开始");
		try{
			if(mainCommiPercIndeSaleService.checkExistMainCommiPercIndeSale(mainCommiPercIndeSale)){
				ServletUtils.outPrintFail(request, response, "存在相同编号和销售类型记录，请重新填写");
			}
			mainCommiPercIndeSale.setLastUpdate(new Date());
			mainCommiPercIndeSale.setLastUpdatedBy(getUserContext(request).getCzybh());
			mainCommiPercIndeSale.setActionLog("EDIT");
			this.mainCommiPercIndeSaleService.update(mainCommiPercIndeSale);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改独立销售提成比例失败");
		}
	}
	
	/**
	 * 删除MainCommiPercIndeSale
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String id){
		logger.debug("删除MainCommiPercIndeSale开始");
		if(StringUtils.isBlank(id)){
			ServletUtils.outPrintFail(request, response, "独立销售提成比例编号不能为空,删除失败");
			return;
		}
		MainCommiPercIndeSale mainCommiPercIndeSale = mainCommiPercIndeSaleService.get(MainCommiPercIndeSale.class, Integer.parseInt(id));
		if(mainCommiPercIndeSale == null){
			ServletUtils.outPrintFail(request, response, "选择记录不存在");
			return;
		}
		mainCommiPercIndeSaleService.delete(mainCommiPercIndeSale);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *MainCommiPercIndeSale导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, MainCommiPercIndeSale mainCommiPercIndeSale){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		mainCommiPercIndeSaleService.findPageBySql(page, mainCommiPercIndeSale);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"MainCommiPercIndeSale_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
