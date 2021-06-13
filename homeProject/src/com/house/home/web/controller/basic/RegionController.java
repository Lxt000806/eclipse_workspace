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
import com.house.home.entity.basic.Company;
import com.house.home.entity.basic.Region;
import com.house.home.service.basic.RegionService;

@Controller
@RequestMapping("/admin/region")
public class RegionController extends BaseController{

	@Autowired
	private RegionService regionService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Region region) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		regionService.findPageBySql(page, region);
		return new WebPage<Map<String,Object>>(page);
	}
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/basic/region/region_list");
	}
	
	/**
	 * 跳转到新增region页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response){
		logger.debug("跳转到新增项目区域管理页面");
		return new ModelAndView("admin/basic/region/region_save");
	}
	/**
	 * 跳转到修改region页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到修改项目区域管理页面");
		Region region = null;
		if (StringUtils.isNotBlank(id)){
			region = regionService.get(Region.class, id);
			region.setValidDescr(region.getDescr());
			if (StringUtils.isNotBlank(region.getCmpCode())) {
				Company company = regionService.get(Company.class, region.getCmpCode());
				region.setCmpDescr(company.getDesc2());
			}
		}else{
			region = new Region();
		}
		return new ModelAndView("admin/basic/region/region_update")
			.addObject("region", region);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response,String id){
		logger.debug("跳转到查看项目区域管理页面");
		Region region = null;
		if (StringUtils.isNotBlank(id)){
			region = regionService.get(Region.class, id);
			if (StringUtils.isNotBlank(region.getCmpCode())) {
				Company company = regionService.get(Company.class, region.getCmpCode());
				region.setCmpDescr(company.getDesc2());
			}
		}else{
			region = new Region();
		}
		return new ModelAndView("admin/basic/region/region_detail")
			.addObject("region", region);
	}
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, Region region){
		logger.debug("添加region开始");
		try{
			if (validCode(region.getCode())==true) {
				ServletUtils.outPrintFail(request, response,"区域编号重复");
				return;
			}
			if (validDescr(region.getDescr())==true) {
				ServletUtils.outPrintFail(request, response,"区域名称重复");
				return;
			}
			region.setLastUpdate(new Date());
			region.setLastUpdatedBy(getUserContext(request).getCzybh());
			region.setExpired("F");
		    region.setActionLog("ADD");
			this.regionService.save(region);
			ServletUtils.outPrintSuccess(request, response,"添加项目区域管理成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加项目区域管理失败");
		}
	}
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, Region region){
		logger.debug("修改项目区域管理开始");
		try{
			Region xt = this.regionService.get(Region.class, region.getCode());
			if (xt!=null){
				if ("2".equals(region.getValidDescr())) {
					if (validDescr(region.getDescr())==true) {
						ServletUtils.outPrintFail(request, response,"区域名称重复");
						return;
					}
				}
				region.setActionLog("EDIT");
				region.setLastUpdate(new Date());
				region.setLastUpdatedBy(getUserContext(request).getCzybh());
				this.regionService.update(region);
				ServletUtils.outPrintSuccess(request, response);
			}else{
				this.regionService.save(region);
				ServletUtils.outPrintSuccess(request, response);
			}
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改项目区域管理失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, Region region) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		regionService.findPageBySql(page,region);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"项目区域管理_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	//公用验证code,descr是否重复
	public boolean validCode(String code){
		return regionService.validCode(code);
		
	}
	public boolean validDescr(String descr) {
		return regionService.validDescr(descr);
		
	}
}
