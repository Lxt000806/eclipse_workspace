package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Region2;
import com.house.home.service.basic.Region2Service;

@Controller
@RequestMapping("/admin/region2")
public class Region2Controller extends BaseController{

	@Autowired
	private Region2Service region2Service;

	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Region2 region2) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		region2Service.findPageBySql(page, region2);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response,Region2 region2){
		//Page<Map<String,Object>> page = this.newPage(request);
		return new ModelAndView("admin/basic/region2/region2_list").addObject("region2", region2);
	}
	/**
	 * 跳转到新增region页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增项目区域管理2页面");
		return new ModelAndView("admin/basic/region2/region2_save");
	}
	/**
	 * 跳转到修改region页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到修改项目区域管理2页面");
		Region2 region2 = null;
		if (StringUtils.isNotBlank(id)){
			region2= region2Service.get(Region2.class, id);
			region2.setValidDescr(region2.getDescr());
		}else{
			region2 = new Region2();
		}
		return new ModelAndView("admin/basic/region2/region2_update")
			.addObject("region2", region2);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看项目区域管理2页面");
		Region2 region2 = null;
		if (StringUtils.isNotBlank(id)){
			region2 = region2Service.get(Region2.class, id);
		}else{
			region2 = new Region2();
		}
		return new ModelAndView("admin/basic/region2/region2_detail")
			.addObject("region2", region2);
	}
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Region2 region2){
		logger.debug("添加region2开始");
		try{
			if (validDescr(region2.getDescr())==true) {
				ServletUtils.outPrintFail(request, response,"区域名称2重复");
				return;
			}
			String code = region2Service.getSeqNo("tRegion2");
			region2.setCode(code);
			region2.setLastUpdate(new Date());
			region2.setLastUpdatedBy(getUserContext(request).getCzybh());
			region2.setExpired("F");
		    region2.setActionLog("ADD");
			this.region2Service.save(region2);
			ServletUtils.outPrintSuccess(request, response,"添加项目区域管理2成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加项目区域管理2失败");
		}
	}
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Region2 region2){
		logger.debug("修改项目区域管理2开始");
		try{
			Region2 xt = this.region2Service.get(Region2.class, region2.getCode());
			if (xt!=null){
				if ("2".equals(region2.getValidDescr())) {
					if (validDescr(region2.getDescr())==true) {
						ServletUtils.outPrintFail(request, response,"区域名称重复");
						return;
					}
				}
				region2.setActionLog("EDIT");
				region2.setLastUpdate(new Date());
				System.out.println(region2.getLastUpdate());
				region2.setLastUpdatedBy(getUserContext(request).getCzybh());
				this.region2Service.update(region2);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改项目区域管理2失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, Region2 region2) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		region2Service.findPageBySql(page,region2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"项目区域管理2_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	public boolean validDescr(String descr) {
		return region2Service.validDescr(descr);	
	}
}
