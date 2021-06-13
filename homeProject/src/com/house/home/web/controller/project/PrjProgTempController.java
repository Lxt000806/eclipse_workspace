package com.house.home.web.controller.project;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.service.project.PrjProgTempService;

@Controller
@RequestMapping("/admin/prjProgTemp")
public class PrjProgTempController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(PrjProgController.class);
	
	@Autowired
	private PrjProgTempService prjProgTempService;

	/** 
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,PrjProgTemp prjProgTemp) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		prjProgTempService.findPageBySql(page, prjProgTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *获取原采购单
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,PrjProgTemp prjProgTemp){
		
		return new ModelAndView("admin/project/prjProgTemp/prjProgTemp_code").addObject("prjProgTemp",prjProgTemp);
	}
	/**
	 * 根据id查询Purchase详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPrjProgTemp")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		PrjProgTemp prjProgTemp= prjProgTempService.get(PrjProgTemp.class, id);
		if(prjProgTemp == null){
			return this.out("系统中不存在No="+id+"的工程进度模板", false);
		}
		return this.out(prjProgTemp, true);
	}
	
	
}






