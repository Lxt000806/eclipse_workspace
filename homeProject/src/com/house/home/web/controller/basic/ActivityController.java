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
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Activity;
import com.house.home.entity.basic.cmpActivity;
import com.house.home.service.basic.ActivityService;
import com.house.home.service.basic.cmpActivityService;

@Controller
@RequestMapping("/admin/activity")
public class ActivityController extends BaseController{

	@Autowired
	private ActivityService activityService;
	@Autowired
	private cmpActivityService cmpActivityService;
	
	
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response,Activity activity) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		activityService.findPageBySql(page, activity);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,HttpServletResponse response,Activity activity){
		//Page<Map<String,Object>> page = this.newPage(request);
		return new ModelAndView("admin/basic/activity/activity_list").addObject("activity", activity);
	}
	
	/**
	 *获取活动编号
	 * 
	 **/
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest  request ,
				HttpServletResponse response,Activity activity){
		return new ModelAndView("admin/basic/activity/activity_code").addObject("activity",activity);
	}
	
	/**
	 * 根据id查询activity详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getActivity")
	@ResponseBody
	public JSONObject getActivity(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Activity activity= activityService.get(Activity.class, id);
		if(activity == null){
			return this.out("系统中不存在No="+id+"的活动", false);
		}
		// 手动输入时，要控制是有效编号 add by zb on 20190826
		Boolean isWork = this.activityService.checkActivity(activity);
		if(!isWork){
			return this.out("该活动编号无效", false);
		}
		return this.out(activity, true);
	}
	@RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,HttpServletResponse response){
		logger.debug("跳转到添加activity页面");
		return new ModelAndView("admin/basic/activity/activity_save");
	}	
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("跳转到添加activity页面");
		Activity activity =null;
		if(StringUtils.isNotBlank(id)){
			activity = activityService.get(Activity.class, id);
			System.out.println(activity.getCmpActNo());
			if (StringUtils.isNotBlank(activity.getCmpActNo())) {
				cmpActivity cmp = cmpActivityService.get(cmpActivity.class, activity.getCmpActNo());
				System.out.println("1");
				if (cmp!=null) {
					activity.setCmpActDescr(cmp.getDescr());
				}
			}
		}else {
			activity = new Activity();
		}
		return new ModelAndView("admin/basic/activity/activity_update").addObject("activity", activity);
	}
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response,String id){
		logger.debug("跳转到查看activity页面");
		Activity activity = null;
		if (StringUtils.isNotBlank(id)) {
			activity = activityService.get(Activity.class, id);
			if(StringUtils.isNotBlank(activity.getCmpActNo())){
				cmpActivity cmp = cmpActivityService.get(cmpActivity.class,activity.getCmpActNo());
				if (cmp!=null) {
					activity.setCmpActDescr(cmp.getDescr());
				}
			}
		}else {
			activity = new Activity();
		}
		return 	new ModelAndView("admin/basic/activity/activity_detail").addObject("activity", activity);
	}
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, Activity activity){
		logger.debug("添加activity开始");
		try {
			String str = activityService.getSeqNo("tActivity");
			System.out.println(str);
			activity.setNo(str);
			activity.setLastUpdate(new Date());
			activity.setExpired("F");
			activity.setActionLog("ADD");
			activity.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.activityService.save(activity);	
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "添加activity失败");
		}
	}
	@RequestMapping("doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,Activity activity){
		try {
			activity.setLastUpdate(new Date());
			activity.setActionLog("EDIT");
		    this.activityService.update(activity);
		    ServletUtils.outPrintSuccess(request, response);
			
		} catch (Exception e) {
			ServletUtils.outPrintFail(request, response,"修改activity失败");
		}
	}
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, Activity activity) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		activityService.findPageBySql(page,activity);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"展会活动管理_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
}
