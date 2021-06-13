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
import com.house.home.entity.basic.CtrlExpr;
import com.house.home.service.basic.CtrlExprService;

@Controller
@RequestMapping("/admin/ctrlExpr")
public class CtrlExprController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CtrlExprController.class);

	@Autowired
	private CtrlExprService ctrlExprService;

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
			HttpServletResponse response, CtrlExpr ctrlExpr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		ctrlExprService.findPageBySql(page, ctrlExpr);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * CtrlExpr列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/ctrlExpr/ctrlExpr_list");
	}
	/**
	 * CtrlExpr查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/ctrlExpr/ctrlExpr_code");
	}
	/**
	 * 跳转到新增CtrlExpr页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("跳转到新增CtrlExpr页面");
		CtrlExpr ctrlExpr = null;
		if (StringUtils.isNotBlank(id)){
			ctrlExpr = ctrlExprService.get(CtrlExpr.class, Integer.parseInt(id));
			ctrlExpr.setPk(null);
		}else{
			ctrlExpr = new CtrlExpr();
		}
		ctrlExpr.setCustType(custType);
		return new ModelAndView("admin/basic/ctrlExpr/ctrlExpr_save")
			.addObject("ctrlExpr", ctrlExpr);
	}
	/**
	 * 跳转到修改CtrlExpr页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("跳转到修改CtrlExpr页面");
		CtrlExpr ctrlExpr = null;
		if (StringUtils.isNotBlank(id)){
			ctrlExpr = ctrlExprService.get(CtrlExpr.class, Integer.parseInt(id));
		}else{
			ctrlExpr = new CtrlExpr();
		}
		ctrlExpr.setCustType(custType);
		return new ModelAndView("admin/basic/ctrlExpr/ctrlExpr_update")
			.addObject("ctrlExpr", ctrlExpr);
	}
	
	/**
	 * 跳转到查看CtrlExpr页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id,String custType){
		logger.debug("跳转到查看CtrlExpr页面");
		CtrlExpr ctrlExpr = null;
		if (StringUtils.isNotBlank(id)){
			ctrlExpr = ctrlExprService.get(CtrlExpr.class, Integer.parseInt(id));
		}else{
			ctrlExpr = new CtrlExpr();
		}
		ctrlExpr.setCustType(custType);
		return new ModelAndView("admin/basic/ctrlExpr/ctrlExpr_detail")
				.addObject("ctrlExpr", ctrlExpr);
	}
	/**
	 * 添加CtrlExpr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, CtrlExpr ctrlExpr){
		logger.debug("添加CtrlExpr开始");
		try{
			ctrlExpr.setExpired("F");
			ctrlExpr.setLastUpdate(new Date());
			ctrlExpr.setLastUpdatedBy(getUserContext(request).getCzybh());
			ctrlExpr.setActionLog("ADD");
			this.ctrlExprService.save(ctrlExpr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加CtrlExpr失败");
		}
	}
	
	/**
	 * 修改CtrlExpr
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, CtrlExpr ctrlExpr){
		logger.debug("修改CtrlExpr开始");
		try{
			if (!"T".equals(ctrlExpr.getExpired())) {
				ctrlExpr.setExpired("F");
			}
			ctrlExpr.setLastUpdate(new Date());
			ctrlExpr.setLastUpdatedBy(getUserContext(request).getCzybh());
			ctrlExpr.setActionLog("EDIT");
			this.ctrlExprService.update(ctrlExpr);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改CtrlExpr失败");
		}
	}
	
	/**
	 * 删除CtrlExpr
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除CtrlExpr开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "CtrlExpr编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				CtrlExpr ctrlExpr = ctrlExprService.get(CtrlExpr.class, Integer.parseInt(deleteId));
				if(ctrlExpr == null)
					continue;
				ctrlExpr.setExpired("T");
				ctrlExprService.update(ctrlExpr);
			}
		}
		logger.debug("删除CtrlExpr IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *CtrlExpr导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, CtrlExpr ctrlExpr){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		ctrlExprService.findPageBySql(page, ctrlExpr);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"分段发包明细_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
