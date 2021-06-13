package com.house.home.web.controller.basic;

import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.BuilderNum;
import com.house.home.service.basic.BuilderNumService;

@Controller
@RequestMapping("/admin/builderNum")
public class BuilderNumController extends BaseController{
	@Autowired
	private BuilderNumService builderNumService;
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,BuilderNum builderNum) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		builderNumService.findPageBySql(page, builderNum);
		return new WebPage<Map<String,Object>>(page);

	}
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,BuilderNum builderNum){
		
		return new ModelAndView("admin/basic/builderNum/builderNum_code").addObject("builderNum",builderNum);
	}
	/**
	 * 跳转到新增BuilderNum页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BuilderNum页面");
		BuilderNum builderNum = null;
		if (StringUtils.isNotBlank(id)){
			builderNum = builderNumService.get(BuilderNum.class, Integer.parseInt(id));
			builderNum.setPk(null);
		}else{
			builderNum = new BuilderNum();
		}
		builderNum.setBuilderDelivCode(request.getParameter("builderDelivCode"));
		return new ModelAndView("admin/basic/builderNum/builderNum_save")
			.addObject("builderNum", builderNum);
	}
	/**
	 * 跳转到新增BuilderNum页面
	 * @return
	 */
	@RequestMapping("/goMultiAdd")
	public ModelAndView goMultiAdd(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BuilderNum页面");
		BuilderNum builderNum = null;
		if (StringUtils.isNotBlank(id)){
			builderNum = builderNumService.get(BuilderNum.class, Integer.parseInt(id));
			builderNum.setPk(null);
		}else{
			builderNum = new BuilderNum();
		}
		builderNum.setBuilderDelivCode(request.getParameter("builderDelivCode"));
		return new ModelAndView("admin/basic/builderNum/builderNum_multiAdd")
			.addObject("builderNum", builderNum);
	}
	/**
	 * 跳转到修改BuilderNum页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BuilderNum页面");
		BuilderNum builderNum = null;
		if (StringUtils.isNotBlank(id)){
			builderNum = builderNumService.get(BuilderNum.class, Integer.parseInt(id));
		}else{
			builderNum = new BuilderNum();
		}
		builderNum.setBuilderDelivCode(request.getParameter("builderDelivCode"));
		builderNum.setPk(Integer.parseInt(request.getParameter("pk")));
		builderNum.setBuilderNum(request.getParameter("builderNum"));
		return new ModelAndView("admin/basic/builderNum/builderNum_update")
			.addObject("builderNum", builderNum);
	}
	
	/**
	 * 跳转到查看BuilderNum页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BuilderNum页面");
		BuilderNum builderNum = null;
		if (StringUtils.isNotBlank(id)){
			builderNum = builderNumService.get(BuilderNum.class, Integer.parseInt(id));
		}else{
			builderNum = new BuilderNum();
		}
		builderNum.setBuilderDelivCode(request.getParameter("builderDelivCode"));
		builderNum.setPk(Integer.parseInt(request.getParameter("pk")));
		builderNum.setBuilderNum(request.getParameter("builderNum"));
		return new ModelAndView("admin/basic/builderNum/builderNum_detail")
			.addObject("builderNum", builderNum);
	}
	/**
	 * 添加BuilderNum
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BuilderNum builderNum){
		logger.debug("添加BuilderNum开始");
		try{
			builderNum.setExpired("F");
			builderNum.setActionLog("ADD");
			builderNum.setLastUpdate(new Date());
			builderNum.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.builderNumService.save(builderNum);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "保存失败，请检查该项目中是否已经存在相同楼号！");
		}
	}
	
	/**
	 * 修改BuilderNum
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BuilderNum builderNum){
		logger.debug("修改BuilderNum开始");
		try{
			builderNum.setExpired("F");
			builderNum.setActionLog("ADD");
			builderNum.setLastUpdate(new Date());
			builderNum.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.builderNumService.update(builderNum);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败，请检查该项目中是否已经存在相同楼号！");
		}
	}
	
	/**
	 * 删除BuilderNum
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BuilderNum开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BuilderNum编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BuilderNum builderNum = builderNumService.get(BuilderNum.class, Integer.parseInt(deleteId));
				if(builderNum == null)
					continue;
				builderNumService.delete(builderNum);
			}
		}
		logger.debug("删除BuilderNum IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	@RequestMapping("/getBuilderNum")
	@ResponseBody
	public JSONObject getBuilderNum(HttpServletRequest request,HttpServletResponse response,String builderNumCode){
		BuilderNum builderNum= new BuilderNum();
		builderNum.setBuilderNum(request.getParameter("id"));
		return this.out(builderNum, true);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/goJqGrid2")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid2(HttpServletRequest request,
			HttpServletResponse response,BuilderNum builderNum) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page=builderNumService.findNumByCode(page, builderNum.getBuilderDelivCode());
		List<Map<String,Object>> list = page.getResult();
		Collections.sort(list, new ListCompareUtil("ordername"));
		if(page.isAutoCount()){
			page.setTotalCount(list.size());
		}
		page.setPageSize(10000);
		page.setResult(list);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 批量增加楼号信息
	 * @param request
	 * @param response 
	 * @param qz 前缀
	 * @param beginNum 开始序号
	 * @param endNum 结束序号
	 * @param builderDelivCode 批次号
	 */
	@RequestMapping("/multiAdd")
	public void multiAdd(HttpServletRequest request, HttpServletResponse response, String qz,Integer beginNum,Integer endNum,String builderDelivCode){
		logger.debug("批量增加楼号信息开始");
		try{
			String lastUpdatedBy=getUserContext(request).getCzybh();
			builderNumService.multiAdd(qz, beginNum, endNum, builderDelivCode, lastUpdatedBy);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存失败，请检查该项目中是否已经存在相同楼号！");
		}
	}
}
