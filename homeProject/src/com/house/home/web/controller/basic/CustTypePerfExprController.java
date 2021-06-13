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
import com.house.home.entity.basic.CustTypePerfExpr;
import com.house.home.service.basic.CustTypePerfExprService;

@Controller
@RequestMapping("/admin/custTypePerfExpr")
public class CustTypePerfExprController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustTypePerfExprController.class);

	@Autowired
	private CustTypePerfExprService custTypePerfExprService;

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
			HttpServletResponse response, CustTypePerfExpr custTypePerfExpr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		custTypePerfExprService.findPageBySql(page, custTypePerfExpr);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CustTypePerfExpr列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypePerfExpr/custTypePerfExpr_list");
	}
	/**
	 * CustTypePerfExpr查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/custTypePerfExpr/custTypePerfExpr_code");
	}
	/**
	 * 跳转到新增CustTypePerfExpr页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("跳转到新增CustTypePerfExpr页面");
		CustTypePerfExpr custTypePerfExpr = null;
		if (StringUtils.isNotBlank(id)){
			custTypePerfExpr = custTypePerfExprService.get(CustTypePerfExpr.class, Integer.parseInt(id));
			custTypePerfExpr.setPk(null);
		}else{
			custTypePerfExpr = new CustTypePerfExpr();
		}
		custTypePerfExpr.setCustType(custType);
		return new ModelAndView("admin/basic/custTypePerfExpr/custTypePerfExpr_save")
			.addObject("custTypePerfExpr", custTypePerfExpr);
	}
	/**
	 * 跳转到修改CustTypePerfExpr页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("跳转到修改CustTypePerfExpr页面");
		CustTypePerfExpr custTypePerfExpr = null;
		if (StringUtils.isNotBlank(id)){
			custTypePerfExpr = custTypePerfExprService.get(CustTypePerfExpr.class, Integer.parseInt(id));
		}else{
			custTypePerfExpr = new CustTypePerfExpr();
		}
		custTypePerfExpr.setCustType(custType);
		return new ModelAndView("admin/basic/custTypePerfExpr/custTypePerfExpr_update")
			.addObject("custTypePerfExpr", custTypePerfExpr);
	}
	
	/**
	 * 跳转到查看CustTypePerfExpr页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("跳转到查看CustTypePerfExpr页面");
		CustTypePerfExpr custTypePerfExpr = custTypePerfExprService.get(CustTypePerfExpr.class, Integer.parseInt(id));
		custTypePerfExpr.setCustType(custType);
		return new ModelAndView("admin/basic/custTypePerfExpr/custTypePerfExpr_detail")
				.addObject("custTypePerfExpr", custTypePerfExpr);
	}
	/**
	 * 添加CustTypePerfExpr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CustTypePerfExpr custTypePerfExpr){
		logger.debug("添加CustTypePerfExpr开始");
		try{
			custTypePerfExpr.setExpired("F");
			custTypePerfExpr.setLastUpdate(new Date());
			custTypePerfExpr.setLastUpdatedBy(getUserContext(request).getCzybh());
			custTypePerfExpr.setActionLog("ADD");
			this.custTypePerfExprService.save(custTypePerfExpr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加custTypePerfExpr失败");
		}
	}
	
	/**
	 * 修改CustTypePerfExpr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CustTypePerfExpr custTypePerfExpr){
		logger.debug("修改CustTypePerfExpr开始");
		try{
			if (!"T".equals(custTypePerfExpr.getExpired())) {
				custTypePerfExpr.setExpired("F");
			}
			custTypePerfExpr.setLastUpdate(new Date());
			custTypePerfExpr.setLastUpdatedBy(getUserContext(request).getCzybh());
			custTypePerfExpr.setActionLog("EDIT");
			this.custTypePerfExprService.update(custTypePerfExpr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustTypePerfExpr失败");
		}
	}
	
	/**
	 * 删除CustTypePerfExpr
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("删除CustTypePerfExpr开始");
		try{
			CustTypePerfExpr custTypePerfExpr = custTypePerfExprService.get(CustTypePerfExpr.class, Integer.parseInt(id));
			custTypePerfExpr.setCustType(custType);
			custTypePerfExpr.setExpired("T");
			custTypePerfExprService.update(custTypePerfExpr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CustTypePerfExpr失败");
		}
	}
	
	/**
	 *CustTypePerfExpr导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CustTypePerfExpr custTypePerfExpr){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		custTypePerfExprService.findPageBySql(page, custTypePerfExpr);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"分段业绩公式明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
