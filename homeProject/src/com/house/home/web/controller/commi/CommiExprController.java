package com.house.home.web.controller.commi;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.commi.CommiExpr;
import com.house.home.service.commi.CommiExprService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/commiExpr")
public class CommiExprController extends BaseController{
	@Autowired
	private  CommiExprService commiExprService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response,CommiExpr commiExpr) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		commiExprService.findPageBySql(page, commiExpr);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, CommiExpr commiExpr) throws Exception {
		if(commiExpr.getPk() != null){
			commiExpr = commiExprService.get(CommiExpr.class, commiExpr.getPk());
		}
		
		return new ModelAndView("admin/commi/commiExpr/commiExpr_save")
		.addObject("commiExpr", commiExpr);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, CommiExpr commiExpr) throws Exception {
		if(commiExpr.getPk() != null){
			commiExpr = commiExprService.get(CommiExpr.class, commiExpr.getPk());
		}
		
		return new ModelAndView("admin/commi/commiExpr/commiExpr_update")
		.addObject("commiExpr", commiExpr);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, CommiExpr commiExpr) throws Exception {
		if(commiExpr.getPk() != null){
			commiExpr = commiExprService.get(CommiExpr.class, commiExpr.getPk());
		}
		
		return new ModelAndView("admin/commi/commiExpr/commiExpr_view")
		.addObject("commiExpr", commiExpr);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, CommiExpr commiExpr){
		logger.debug("添加公式开始");
		try{
			commiExpr.setActionLog("ADD");
			commiExpr.setLastUpdate(new Date());
			commiExpr.setExpired("F");
			commiExpr.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiExprService.save(commiExpr);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加公式失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, CommiExpr commiExpr){
		logger.debug("编辑计公式开始");
		try{
			if(commiExpr.getPk() == null){
				ServletUtils.outPrintFail(request, response, "添加公式失败");
			}
			
			CommiExpr ce = new CommiExpr();
			ce = commiExprService.get(CommiExpr.class, commiExpr.getPk());
			ce.setDescr(commiExpr.getDescr());
			ce.setExpr(commiExpr.getExpr());
			ce.setExprRemarks(commiExpr.getExprRemarks());
			ce.setRemarks(commiExpr.getRemarks());
			ce.setActionLog("Edit");
			ce.setLastUpdate(new Date());
			ce.setExpired("F");
			ce.setLastUpdatedBy(getUserContext(request).getCzybh());
			ce.setRightCardinalExpr(commiExpr.getRightCardinalExpr());
			ce.setRightCardinalExprRemarks(commiExpr.getRightCardinalExprRemarks());
			this.commiExprService.update(ce);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑公式失败");
		}
	}
	
	@RequestMapping("/doExpired")
	public void doExpired(HttpServletRequest request,HttpServletResponse response, CommiExpr commiExpr){
		logger.debug("公式过期开始");
		try{
			if(commiExpr.getPk() == null){
				ServletUtils.outPrintFail(request, response, "公式过期失败");
			}
			
			CommiExpr ce = new CommiExpr();
			ce = commiExprService.get(CommiExpr.class, commiExpr.getPk());
			ce.setActionLog("Edit");
			ce.setLastUpdate(new Date());
			ce.setExpired("T");
			ce.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.commiExprService.update(ce);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "公式过期失败");
		}
	}
}
