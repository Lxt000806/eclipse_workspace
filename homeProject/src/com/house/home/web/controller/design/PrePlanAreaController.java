package com.house.home.web.controller.design;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.service.design.PrePlanAreaService;
@Controller
@RequestMapping("/admin/prePlanArea")
public class PrePlanAreaController extends BaseController{
	@Autowired
	private  PrePlanAreaService prePlanAreaService;
	
	/**
	 * 获取空间编号
	 * @param request
	 * @param response
	 * @param prePlanArea
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,PrePlanArea prePlanArea) throws Exception {

		return new ModelAndView("admin/design/prePlanArea/prePlanArea_code")
			.addObject("prePlanArea", prePlanArea);
	}
	
	/**
	 * 根据id查询空间信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPrePlanArea")
	@ResponseBody
	public JSONObject getPrePlanArea(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		if(!NumberUtils.isNumber(id)){
			return this.out("系统中不存在code="+id+"的空间信息", false);
		}
		PrePlanArea prePlanArea = prePlanAreaService.get(PrePlanArea.class, Integer.parseInt(id));
		if(prePlanArea == null){
			return this.out("系统中不存在code="+id+"的空间信息", false);
		}
		return this.out(prePlanArea, true);
	}

}
