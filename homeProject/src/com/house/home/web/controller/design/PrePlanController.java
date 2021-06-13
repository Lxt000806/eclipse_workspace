package com.house.home.web.controller.design;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.design.PrePlan;
import com.house.home.service.design.PrePlanService;
import com.house.home.bean.design.PrePlanBean;

@Controller
@RequestMapping("/admin/prePlan")
public class PrePlanController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PrePlanController.class);

	@Autowired
	private PrePlanService prePlanService;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, PrePlan prePlan) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		prePlanService.findPageBySql(page, prePlan);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * PrePlan列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/prePlan/prePlan_list");
	}
	/**
	 * PrePlan查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/design/prePlan/prePlan_code");
	}
	/**
	 * 跳转到新增PrePlan页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增PrePlan页面");
		PrePlan prePlan = null;
		if (StringUtils.isNotBlank(id)){
			prePlan = prePlanService.get(PrePlan.class, id);
			prePlan.setNo(null);
		}else{
			prePlan = new PrePlan();
		}
		
		return new ModelAndView("admin/design/prePlan/prePlan_save")
			.addObject("prePlan", prePlan);
	}
	/**
	 * 跳转到修改PrePlan页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改PrePlan页面");
		PrePlan prePlan = null;
		if (StringUtils.isNotBlank(id)){
			prePlan = prePlanService.get(PrePlan.class, id);
		}else{
			prePlan = new PrePlan();
		}
		
		return new ModelAndView("admin/design/prePlan/prePlan_update")
			.addObject("prePlan", prePlan);
	}
	
	/**
	 * 跳转到查看PrePlan页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看PrePlan页面");
		PrePlan prePlan = prePlanService.get(PrePlan.class, id);
		
		return new ModelAndView("admin/design/prePlan/prePlan_detail")
				.addObject("prePlan", prePlan);
	}
	/**
	 * 添加PrePlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, PrePlan prePlan){
		logger.debug("添加PrePlan开始");
		try{
			String str = prePlanService.getSeqNo("tPrePlan");
			prePlan.setNo(str);
			prePlan.setLastUpdate(new Date());
			prePlan.setLastUpdatedBy(getUserContext(request).getCzybh());
			prePlan.setExpired("F");
			this.prePlanService.save(prePlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加PrePlan失败");
		}
	}
	
	/**
	 * 修改PrePlan
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, PrePlan prePlan){
		logger.debug("修改PrePlan开始");
		try{
			prePlan.setLastUpdate(new Date());
			prePlan.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.prePlanService.update(prePlan);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改PrePlan失败");
		}
	}
	
	/**
	 * 删除PrePlan
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除PrePlan开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "PrePlan编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				PrePlan prePlan = prePlanService.get(PrePlan.class, deleteId);
				if(prePlan == null)
					continue;
				prePlan.setExpired("T");
				prePlanService.update(prePlan);
			}
		}
		logger.debug("删除PrePlan IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}



}
