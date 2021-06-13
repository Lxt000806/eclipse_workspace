package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.DesignFeeSd;
import com.house.home.service.basic.DesignFeeSdService;
@Controller
@RequestMapping("/admin/designFeeSd")
public class DesignFeeSdController extends BaseController{
	@Autowired
	private DesignFeeSdService designFeeSdService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, DesignFeeSd designFeeSd) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		designFeeSdService.findPageBySql(page, designFeeSd);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/basic/designFeeSd/designFeeSd_list");
	}
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,
			HttpServletResponse response, DesignFeeSd designFeeSd) throws Exception {
		return new ModelAndView("admin/basic/designFeeSd/designFeeSd_win")
			.addObject("designFeeSd", designFeeSd);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, DesignFeeSd designFeeSd) throws Exception {
		return new ModelAndView("admin/basic/designFeeSd/designFeeSd_win")
			.addObject("designFeeSd", designFeeSd);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,DesignFeeSd designFeeSd){
		logger.debug("新增保存");
		try {
			DesignFeeSd dfs = this.designFeeSdService.getDesignFByPositCustT(designFeeSd);
			if (null != dfs) {
				ServletUtils.outPrintFail(request, response, "此设计师级别和客户类型已经存在，不允许插入");
			} else {
				designFeeSd.setLastUpdate(new Date());
				designFeeSd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				designFeeSd.setExpired("F");
				designFeeSd.setActionLog("ADD");
				this.designFeeSdService.save(designFeeSd);
				ServletUtils.outPrintSuccess(request, response, "新增成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,DesignFeeSd designFeeSd,
			String oldPosition, String oldCustType){
		logger.debug("编辑保存");
		try {
			DesignFeeSd dfs = this.designFeeSdService.getDesignFByPositCustT(designFeeSd);
			if (null != dfs && (!oldPosition.equals(designFeeSd.getPosition()) || !oldCustType.equals(designFeeSd.getCustType()))) {
				ServletUtils.outPrintFail(request, response, "此设计师级别和客户类型已经存在，不允许插入");
			} else {
				designFeeSd.setLastUpdate(new Date());
				designFeeSd.setLastUpdatedBy(this.getUserContext(request).getCzybh());
				designFeeSd.setExpired("F");
				designFeeSd.setActionLog("Edit");
				this.designFeeSdService.update(designFeeSd);
				ServletUtils.outPrintSuccess(request, response, "编辑成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "编辑失败");
		}
	}
	
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除设计费标准开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "pk不能为空,删除失败");
			return;
		}
		try{
			List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
			for(String deleteId : deleteIdList){
				if(deleteId != null){
					// 不能传空值到存储过程中去
					DesignFeeSd designFeeSd = this.designFeeSdService.get(DesignFeeSd.class, Integer.parseInt(deleteId));
					this.designFeeSdService.delete(designFeeSd);
					ServletUtils.outPrintSuccess(request, response, "删除成功");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "删除设计费标准失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, DesignFeeSd designFeeSd){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		designFeeSdService.findPageBySql(page, designFeeSd);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"设计师费用配置_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
