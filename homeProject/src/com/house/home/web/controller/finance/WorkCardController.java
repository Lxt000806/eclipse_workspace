package com.house.home.web.controller.finance;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.finance.WorkCard;
import com.house.home.service.finance.WorkCardService;

@Controller
@RequestMapping("/admin/workCard")
public class WorkCardController extends BaseController{
	@Autowired
	private WorkCardService workCardService;
	

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,WorkCard workCard) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		workCardService.findPageBySql(page, workCard);
		return new WebPage<Map<String,Object>>(page);

	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,WorkCard workCard){
		
		return new ModelAndView("admin/finance/workCard/workCard_code").addObject("workCard",workCard);
	}
	
	@RequestMapping("/getWorkCard")
	@ResponseBody
	public JSONObject getWorkCard(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		WorkCard workCard= workCardService.get(WorkCard.class, id);
		if(workCard == null){
			return this.out("系统中不存在卡号："+id, false);
		}
		return this.out(workCard, true);
	}
}
