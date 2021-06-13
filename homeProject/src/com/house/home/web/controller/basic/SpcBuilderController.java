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
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.service.basic.EmployeeService;
import com.house.home.service.basic.SpcBuilderService;

@Controller
@RequestMapping("/admin/spcBuilder")
public class SpcBuilderController extends BaseController{
	
	@Autowired
	private SpcBuilderService spcBuilderService;
	@Autowired
	private EmployeeService employeeService;
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,SpcBuilder spcBuilder) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		spcBuilderService.findPageBySql(page, spcBuilder);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goDelivJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goDelivJqGrid(HttpServletRequest request,
			HttpServletResponse response,String code,String builderNum) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		spcBuilderService.findDelivPageBySql(page, code,builderNum);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 *ResrCust列表
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response  ) throws Exception{
		
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_list").addObject("czybh", this.getUserContext(request).getCzybh());

	}
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request ,
			HttpServletResponse response  ) throws Exception{
		
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_save");

	}
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request ,
			HttpServletResponse response,String code ) throws Exception{
		SpcBuilder spcBuilder =null;
		spcBuilder=spcBuilderService.get(SpcBuilder.class, code);
		String leaderDescr="";
		if(StringUtils.isNotBlank(spcBuilder.getLeaderCode())){
			leaderDescr=employeeService.get(Employee.class, spcBuilder.getLeaderCode()).getNameChi();
		}
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_update").addObject("spcBuilder",spcBuilder)
				.addObject("leaderDescr", leaderDescr);

	}
	
	@RequestMapping("/goLeaderUpdate")
	public ModelAndView goLeaderUpdate(HttpServletRequest request ,
			HttpServletResponse response,String code ) throws Exception{
		SpcBuilder spcBuilder =null;
		spcBuilder=spcBuilderService.get(SpcBuilder.class, code);
		String leaderDescr="";
		if(StringUtils.isNotBlank(spcBuilder.getLeaderCode())){
			leaderDescr=employeeService.get(Employee.class, spcBuilder.getLeaderCode()).getNameChi();
		}
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_leaderUpdate").addObject("spcBuilder",spcBuilder)
				.addObject("leaderDescr", leaderDescr);

	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request ,
			HttpServletResponse response,String code  ) throws Exception{
		SpcBuilder spcBuilder=null;
		spcBuilder=spcBuilderService.get(SpcBuilder.class, code);
		String leaderDescr="";
		if(StringUtils.isNotBlank(spcBuilder.getLeaderCode())){
			leaderDescr=employeeService.get(Employee.class, spcBuilder.getLeaderCode()).getNameChi();
		}
		
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_view")
		.addObject("spcBuilder",spcBuilder).addObject("leaderDescr", leaderDescr);

	}
	
	
	@RequestMapping("/goAdd")
	public ModelAndView goAdd(HttpServletRequest request,HttpServletResponse response,SpcBuilder spcBuilder,
			 String arr){
		logger.debug("新增采购明细开始"); 
		
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_add")
		.addObject("arr",arr).addObject("spcBuilder", spcBuilder);
	}
	
	@RequestMapping("/goDeliv")
	public ModelAndView goDeliv(HttpServletRequest request,HttpServletResponse response,
			 String delivNums,String code,String descr){
		logger.debug("新增采购明细开始"); 
		
		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_deliv")
			.addObject("code",code).addObject("descr", descr).addObject("delivNums",delivNums);
	}
	 
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,SpcBuilder spcBuilder){
		logger.debug("转盘新增保存");
		try {
			SpcBuilder sb=spcBuilderService.getByDescr(spcBuilder.getDescr());
			if(sb!=null){
				ServletUtils.outPrintFail(request, response, "已经存在名称为"+spcBuilder.getDescr()+"的专盘!");
				return;
			}
			spcBuilder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			spcBuilder.setTemp("A");
			String detailJson = request.getParameter("detailJson");
			spcBuilder.setDetailJson(detailJson);
			
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "项目名称表无记录");
				return;
			}
			Result result = this.spcBuilderService.doSave(spcBuilder);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增专盘失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,SpcBuilder spcBuilder){
		logger.debug("部分到货开始");
		try {
			
			spcBuilder.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			spcBuilder.setTemp("M");
			String detailJson = request.getParameter("detailJson");
			spcBuilder.setDetailJson(detailJson);
			if(StringUtils.isBlank(detailJson)){
				ServletUtils.outPrintFail(request, response, "项目名称表无记录");
				return;
			}
			Result result = this.spcBuilderService.doUpdate(spcBuilder);
			if (result.isSuccess()){
				ServletUtils.outPrintSuccess(request, response,"保存成功");
			}else{
				ServletUtils.outPrintFail(request, response,result.getInfo());
			}
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "修改专盘失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			SpcBuilder spcBuilder){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		spcBuilderService.findPageBySql(page, spcBuilder);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"专盘管理信息表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	@RequestMapping("/getSpcBuilder")
	@ResponseBody
	public JSONObject getSpcBuilder(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SpcBuilder spcBuilder = spcBuilderService.get(SpcBuilder.class, id);
		if(spcBuilder == null){
			return this.out("系统中不存在code="+id+"的专盘名称信息", false);
		}
		return this.out(spcBuilder, true);
	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,SpcBuilder spcBuilder) throws Exception {

		return new ModelAndView("admin/basic/spcBuilder/spcBuilder_code");
	}
}
