package com.house.home.web.controller.basic;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.StringUtils;
import com.house.framework.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.entity.basic.CourseType;
import com.house.home.service.basic.CourseTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin/courseType")
public class CourseTypeController extends BaseController{
	@Autowired
	private  CourseTypeService courseTypeService;
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("admin/basic/courseType/courseType_list");
	}
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, CourseType courseType) {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		courseTypeService.findPageBySql(page, courseType);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, 
			HttpServletResponse response, CourseType cseType) {
		CourseType courseType = null;
		if (StringUtils.isNotBlank(cseType.getCode())) {
			courseType = courseTypeService.getByCode(cseType.getCode());
		} else {
			courseType = new CourseType();
		}
		courseType.setM_umState(cseType.getM_umState());
		return new ModelAndView("admin/basic/courseType/courseType_update")
			.addObject("courseType", courseType);
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response,
			CourseType courseType) {
		try {
			CourseType ct1 = this.courseTypeService.get(CourseType.class, courseType.getCode());
			courseType.setLastUpdate(new Date());
			courseType.setActionLog("EDIT");
			if (ct1 != null) {
				this.courseTypeService.update(courseType);
				ServletUtils.outPrintSuccess(request, response);
			} else {
				this.courseTypeService.save(courseType);
				ServletUtils.outPrintSuccess(request, response);
			}
		} catch(Exception e) {
			ServletUtils.outPrintFail(request, response, "修改课程类型失败");
		}
	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response) {
		CourseType courseType = new CourseType();
		return new ModelAndView("admin/basic/courseType/courseType_save")
			.addObject("courseType", courseType);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response,CourseType courseType) {
		try {
			CourseType ct1 = courseTypeService.getByCode(courseType.getCode());
			if (ct1 != null) {
				ServletUtils.outPrintFail(request, response, "编号已存在");
				return;
			}
			courseType.setExpired("F");
			courseType.setLastUpdate(new Date());
			courseType.setActionLog("ADD");
			this.courseTypeService.save(courseType);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e) {
			ServletUtils.outPrintFail(request, response, "添加课程类型失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			CourseType courseType){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		courseTypeService.findPageBySql(page, courseType);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"课程类型表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}

}
